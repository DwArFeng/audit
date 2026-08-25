package com.dwarfeng.audit.impl.handler;

import com.dwarfeng.audit.sdk.util.Constants;
import com.dwarfeng.audit.stack.bean.dto.AuditRecordData;
import com.dwarfeng.audit.stack.bean.dto.AuditRecordInfo;
import com.dwarfeng.audit.stack.bean.entity.AuditCategory;
import com.dwarfeng.audit.stack.bean.entity.AuditEntry;
import com.dwarfeng.audit.stack.bean.entity.AuditEntryProperty;
import com.dwarfeng.audit.stack.bean.entity.AuditPropertyIndicator;
import com.dwarfeng.audit.stack.bean.key.AuditEntryPropertyKey;
import com.dwarfeng.audit.stack.bean.key.AuditPropertyIndicatorKey;
import com.dwarfeng.audit.stack.exception.AuditRecordHandlerStoppedException;
import com.dwarfeng.audit.stack.handler.AuditRecordLocalCacheHandler;
import com.dwarfeng.audit.stack.handler.ConsumeHandler;
import com.dwarfeng.audit.stack.struct.AuditRecordLocalCache;
import com.dwarfeng.dutil.develop.backgr.AbstractTask;
import com.dwarfeng.subgrade.sdk.exception.HandlerExceptionHelper;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.exception.HandlerException;
import com.dwarfeng.subgrade.stack.generation.KeyGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 审计记录处理器。
 *
 * @author DwArFeng
 * @since 1.0.0-beta
 */
@Component
public class AuditRecordProcessor {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuditRecordProcessor.class);

    private final ConsumeHandler<AuditRecordData> auditRecordConsumeHandler;

    private final ThreadPoolTaskExecutor executor;
    private final ThreadPoolTaskScheduler scheduler;

    private final Consumer consumer;
    private final ConsumeBuffer consumeBuffer;

    @Value("${com.dwarfeng.audit.audit_record.consumer_thread}")
    private int thread;
    @Value("${com.dwarfeng.audit.audit_record.threshold.warn}")
    private double warnThreshold;

    private final Lock lock = new ReentrantLock();
    private final List<ConsumeTask> processingConsumeTasks = new ArrayList<>();
    private final List<ConsumeTask> endingConsumeTasks = new ArrayList<>();

    private boolean startFlag = false;
    ScheduledFuture<?> capacityCheckFuture = null;

    public AuditRecordProcessor(
            ConsumeHandler<AuditRecordData> auditRecordConsumeHandler,
            ThreadPoolTaskExecutor executor,
            ThreadPoolTaskScheduler scheduler,
            Consumer consumer,
            ConsumeBuffer consumeBuffer
    ) {
        this.auditRecordConsumeHandler = auditRecordConsumeHandler;
        this.executor = executor;
        this.scheduler = scheduler;
        this.consumer = consumer;
        this.consumeBuffer = consumeBuffer;
    }

    public void record(AuditRecordInfo auditRecordInfo) throws HandlerException {
        lock.lock();
        try {
            internalRecord(auditRecordInfo);
        } catch (Exception e) {
            throw HandlerExceptionHelper.parse(e);
        } finally {
            lock.unlock();
        }
    }

    private void internalRecord(AuditRecordInfo auditRecordInfo) throws Exception {
        // 判断是否允许记录，如果不允许，直接报错。
        if (!startFlag) {
            throw new AuditRecordHandlerStoppedException();
        }
        consumeBuffer.accept(auditRecordInfo);
    }

    public int bufferedSize() {
        lock.lock();
        try {
            return consumeBuffer.bufferedSize();
        } finally {
            lock.unlock();
        }
    }

    public int getBufferSize() {
        lock.lock();
        try {
            return consumeBuffer.getBufferSize();
        } finally {
            lock.unlock();
        }
    }

    public void setBufferSize(int bufferSize) {
        lock.lock();
        try {
            consumeBuffer.setBufferSize(bufferSize);
        } finally {
            lock.unlock();
        }
    }

    public int getThread() {
        lock.lock();
        try {
            return thread;
        } finally {
            lock.unlock();
        }
    }

    public void setThread(int thread) {
        lock.lock();
        try {
            thread = Math.max(thread, 1);
            int delta = thread - this.thread;
            this.thread = thread;
            if (!startFlag) {
                return;
            }
            if (delta > 0) {
                for (int i = 0; i < delta; i++) {
                    ConsumeTask consumeTask = new ConsumeTask(consumeBuffer, consumer);
                    executor.execute(consumeTask);
                    processingConsumeTasks.add(consumeTask);
                }
            } else if (delta < 0) {
                endingConsumeTasks.removeIf(AbstractTask::isFinished);
                for (int i = 0; i < -delta; i++) {
                    ConsumeTask consumeTask = processingConsumeTasks.remove(0);
                    consumeTask.shutdown();
                    endingConsumeTasks.add(consumeTask);
                }
            }
        } finally {
            lock.unlock();
        }
    }

    @SuppressWarnings("DuplicatedCode")
    public boolean isIdle() {
        lock.lock();
        try {
            if (consumeBuffer.bufferedSize() > 0) {
                return false;
            }
            if (!processingConsumeTasks.isEmpty()) {
                return false;
            }
            endingConsumeTasks.removeIf(AbstractTask::isFinished);
            return endingConsumeTasks.isEmpty();
        } finally {
            lock.unlock();
        }
    }

    public void workerWork() throws Exception {
        lock.lock();
        try {
            if (startFlag) {
                return;
            }
            LOGGER.info("审计记录逻辑侧消费者启动...");
            auditRecordConsumeHandler.start();

            LOGGER.info("审计记录处理器启动...");
            consumeBuffer.block();
            for (int i = 0; i < thread; i++) {
                ConsumeTask consumeTask = new ConsumeTask(consumeBuffer, consumer);
                executor.execute(consumeTask);
                processingConsumeTasks.add(consumeTask);
            }
            capacityCheckFuture = scheduler.scheduleAtFixedRate(() -> {
                double ratio = (double) consumeBuffer.bufferedSize() / (double) consumeBuffer.getBufferSize();
                if (ratio >= warnThreshold) {
                    String message = "逻辑侧的待消费元素占用缓存比例为 {}，超过报警值 {}，请检查";
                    LOGGER.warn(message, ratio, warnThreshold);
                }
            }, Constants.SCHEDULER_CHECK_INTERVAL);

            try {
                Thread.sleep(1000);
            } catch (InterruptedException ignored) {
            }

            startFlag = true;
        } finally {
            lock.unlock();
        }
    }

    @SuppressWarnings("DuplicatedCode")
    public void workerRest() throws Exception {
        lock.lock();
        try {
            if (!startFlag) {
                return;
            }

            try {
                Thread.sleep(1000);
            } catch (InterruptedException ignored) {
            }

            LOGGER.info("审计记录逻辑侧消费者关闭...");
            if (Objects.nonNull(capacityCheckFuture)) {
                capacityCheckFuture.cancel(true);
                capacityCheckFuture = null;
            }
            processingConsumeTasks.forEach(ConsumeTask::shutdown);
            endingConsumeTasks.addAll(processingConsumeTasks);
            processingConsumeTasks.clear();
            consumeBuffer.unblock();
            processRemainingElement();
            endingConsumeTasks.removeIf(AbstractTask::isFinished);
            if (!endingConsumeTasks.isEmpty()) {
                LOGGER.info("审计记录逻辑侧消费者中的线程还未完全结束, 等待线程结束...");
                endingConsumeTasks.forEach(
                        task -> {
                            try {
                                task.awaitFinish();
                            } catch (InterruptedException ignored) {
                            }
                        }
                );
            }
            processingConsumeTasks.clear();
            endingConsumeTasks.clear();
            LOGGER.info("审计记录逻辑侧消费者已经妥善处理数据, 消费线程结束");

            LOGGER.info("审计消费者关闭...");
            auditRecordConsumeHandler.stop();

            startFlag = false;
        } finally {
            lock.unlock();
        }
    }

    private void processRemainingElement() {
        // 如果没有剩余元素，直接跳过。
        if (consumeBuffer.bufferedSize() <= 0) {
            return;
        }

        LOGGER.info("消费审计记录逻辑侧消费者中剩余的元素 {} 个...", consumeBuffer.bufferedSize());
        LOGGER.info("审计记录逻辑侧消费者中剩余的元素过多时，需要较长时间消费，请耐心等待...");
        ScheduledFuture<?> scheduledFuture = scheduler.scheduleAtFixedRate(
                () -> {
                    String message = "消费审计记录逻辑侧消费者中剩余的元素 {} 个，请耐心等待...";
                    LOGGER.info(message, consumeBuffer.bufferedSize());
                },
                new Date(System.currentTimeMillis() + Constants.SCHEDULER_CHECK_INTERVAL),
                Constants.SCHEDULER_CHECK_INTERVAL
        );
        AuditRecordInfo auditRecordInfo2Consume;
        while (Objects.nonNull(auditRecordInfo2Consume = consumeBuffer.poll())) {
            try {
                consumer.consume(auditRecordInfo2Consume);
            } catch (Exception e) {
                LOGGER.warn("审计记录逻辑侧消费者消费元素时发生异常, 抛弃 AuditRecordInfo: {}", auditRecordInfo2Consume, e);
            }
        }
        scheduledFuture.cancel(true);
    }

    @Component
    public static final class Consumer {

        private final AuditRecordLocalCacheHandler auditRecordLocalCacheHandler;

        private final KeyGenerator<LongIdKey> keyGenerator;
        private final ConsumeHandler<AuditRecordData> recordConsumeHandler;

        private final HandlerValidator handlerValidator;

        public Consumer(
                AuditRecordLocalCacheHandler auditRecordLocalCacheHandler,
                KeyGenerator<LongIdKey> keyGenerator,
                ConsumeHandler<AuditRecordData> recordConsumeHandler,
                HandlerValidator handlerValidator
        ) {
            this.auditRecordLocalCacheHandler = auditRecordLocalCacheHandler;
            this.keyGenerator = keyGenerator;
            this.recordConsumeHandler = recordConsumeHandler;
            this.handlerValidator = handlerValidator;
        }

        public void consume(AuditRecordInfo auditRecordInfo) throws HandlerException {
            try {
                // 确认审计记录信息合法。
                handlerValidator.makeSureAuditRecordInfoValid(auditRecordInfo);

                // 展开参数。
                StringIdKey categoryKey = auditRecordInfo.getCategoryKey();
                Map<String, Object> properties = auditRecordInfo.getProperties();

                // 确认审计类别存在且处于启用状态。
                handlerValidator.makeSureAuditCategoryExists(categoryKey);
                handlerValidator.makeSureAuditCategoryEnabled(categoryKey);

                AuditRecordLocalCache localCache = auditRecordLocalCacheHandler.get(categoryKey);
                AuditCategory auditCategory = localCache.getAuditCategory();
                Map<String, AuditPropertyIndicator> indicatorMap = localCache.getAuditPropertyIndicatorMap();

                // 对属性的每个入口进行校验。
                for (Map.Entry<String, Object> property : properties.entrySet()) {
                    // 确认审计审计属性指示器存在。
                    AuditPropertyIndicator indicator = indicatorMap.get(property.getKey());
                    if (Objects.isNull(indicator)) {
                        handlerValidator.makeSureAuditPropertyIndicatorExists(
                                new AuditPropertyIndicatorKey(categoryKey.getStringId(), property.getKey())
                        );
                    }
                    // 确认审计审计属性值有效。
                    handlerValidator.makeSureAuditPropertyValueValid(
                            property.getKey(), indicator.getPropertyType(), property.getValue()
                    );
                }

                // 生成审计条目主键。
                LongIdKey auditEntryKey = keyGenerator.generate();

                // 构造审计条目。
                AuditEntry auditEntry = new AuditEntry(auditEntryKey, auditCategory.getKey(), new Date());
                // 构造审计条目属性列表。
                List<AuditEntryProperty> auditEntryProperties = new ArrayList<>();
                for (Map.Entry<String, AuditPropertyIndicator> indicatorEntry : indicatorMap.entrySet()) {
                    String propertyId = indicatorEntry.getKey();
                    AuditPropertyIndicator indicator = indicatorEntry.getValue();
                    Object value = properties.containsKey(propertyId)
                            ? properties.get(propertyId) : makeDefaultValue(indicator);
                    auditEntryProperties.add(
                            makeAuditEntryProperty(auditEntryKey, propertyId, indicator, value)
                    );
                }

                // 构造审计记录数据，并将其放入下游批量消费缓冲区。
                recordConsumeHandler.accept(new AuditRecordData(auditEntry, auditEntryProperties));
            } catch (HandlerException e) {
                throw e;
            } catch (Exception e) {
                throw HandlerExceptionHelper.parse(e);
            }
        }

        private Object makeDefaultValue(AuditPropertyIndicator indicator) {
            switch (indicator.getPropertyType()) {
                case Constants.PROPERTY_TYPE_STRING:
                    return indicator.getDefaultStringValue();
                case Constants.PROPERTY_TYPE_LONG:
                    return indicator.getDefaultLongValue();
                case Constants.PROPERTY_TYPE_DOUBLE:
                    return indicator.getDefaultDoubleValue();
                case Constants.PROPERTY_TYPE_BOOLEAN:
                    return indicator.getDefaultBooleanValue();
                case Constants.PROPERTY_TYPE_DATE:
                    Date defaultDateValue = indicator.getDefaultDateValue();
                    return Objects.isNull(defaultDateValue) ? null : new Date(defaultDateValue.getTime());
                default:
                    throw new IllegalStateException("无效的属性类型: " + indicator.getPropertyType());
            }
        }

        private AuditEntryProperty makeAuditEntryProperty(
                LongIdKey entryKey, String propertyId, AuditPropertyIndicator indicator, Object value
        ) {
            String stringValue = null;
            Long longValue = null;
            Double doubleValue = null;
            Boolean booleanValue = null;
            Date dateValue = null;
            switch (indicator.getPropertyType()) {
                case Constants.PROPERTY_TYPE_STRING:
                    stringValue = (String) value;
                    break;
                case Constants.PROPERTY_TYPE_LONG:
                    longValue = (Long) value;
                    break;
                case Constants.PROPERTY_TYPE_DOUBLE:
                    doubleValue = (Double) value;
                    break;
                case Constants.PROPERTY_TYPE_BOOLEAN:
                    booleanValue = (Boolean) value;
                    break;
                case Constants.PROPERTY_TYPE_DATE:
                    dateValue = (Date) value;
                    break;
                default:
                    throw new IllegalStateException("无效的属性类型: " + indicator.getPropertyType());
            }
            return new AuditEntryProperty(
                    new AuditEntryPropertyKey(entryKey.getLongId(), propertyId),
                    indicator.getPropertyType(), stringValue, longValue, doubleValue, booleanValue, dateValue
            );
        }
    }

    @Component
    public static class ConsumeBuffer {

        @Value("${com.dwarfeng.audit.audit_record.buffer_size}")
        private int bufferSize;

        private final Lock lock = new ReentrantLock();
        private final Condition provideCondition = lock.newCondition();
        private final Condition consumeCondition = lock.newCondition();
        private final List<AuditRecordInfo> buffer = new ArrayList<>();

        private boolean blockEnabled = true;

        public void accept(AuditRecordInfo auditRecordInfo) {
            lock.lock();
            try {
                while (buffer.size() >= bufferSize) {
                    provideCondition.awaitUninterruptibly();
                }

                buffer.add(auditRecordInfo);
                consumeCondition.signalAll();
            } finally {
                lock.unlock();
            }
        }

        public AuditRecordInfo poll() {
            lock.lock();
            try {
                /*
                 * 线程阻塞的逻辑。
                 *   [buffer]当中没有任何元素，便阻塞，否则不会阻塞。
                 *   以上条件发生的前提是 [runningFlag] 必须为 true，一旦 [runningFlag] 为 false，则其余参数为任何值都
                 *   不能够阻塞。
                 */
                while ((buffer.isEmpty() && blockEnabled)) {
                    consumeCondition.awaitUninterruptibly();
                }

                // 取出第一个 AuditRecordInfo，并判断 buffer 中为空的情形。
                AuditRecordInfo auditRecordInfo = null;
                if (!buffer.isEmpty()) {
                    auditRecordInfo = buffer.remove(0);
                }

                provideCondition.signalAll();
                return auditRecordInfo;
            } finally {
                lock.unlock();
            }
        }

        public int bufferedSize() {
            lock.lock();
            try {
                return buffer.size();
            } finally {
                lock.unlock();
            }
        }

        public int getBufferSize() {
            lock.lock();
            try {
                return bufferSize;
            } finally {
                lock.unlock();
            }
        }

        public void setBufferSize(int bufferSize) {
            lock.lock();
            try {
                this.bufferSize = Math.max(bufferSize, 1);

                provideCondition.signalAll();
                consumeCondition.signalAll();
            } finally {
                lock.unlock();
            }
        }

        public void block() {
            lock.lock();
            try {
                this.blockEnabled = true;
                this.provideCondition.signalAll();
                this.consumeCondition.signalAll();
            } finally {
                lock.unlock();
            }
        }

        public void unblock() {
            lock.lock();
            try {
                this.blockEnabled = false;
                this.provideCondition.signalAll();
                this.consumeCondition.signalAll();
            } finally {
                lock.unlock();
            }
        }
    }

    private static final class ConsumeTask extends AbstractTask {

        private static final Logger LOGGER = LoggerFactory.getLogger(ConsumeTask.class);

        private final ConsumeBuffer consumeBuffer;
        private final Consumer consumer;

        private final AtomicBoolean runningFlag = new AtomicBoolean(true);

        private ConsumeTask(ConsumeBuffer consumeBuffer, Consumer consumer) {
            this.consumeBuffer = consumeBuffer;
            this.consumer = consumer;
        }

        @Override
        protected void todo() {
            while (runningFlag.get()) {
                AuditRecordInfo auditRecordInfo = null;
                try {
                    auditRecordInfo = consumeBuffer.poll();
                    if (Objects.isNull(auditRecordInfo)) {
                        return;
                    }
                    consumer.consume(auditRecordInfo);
                } catch (Exception e) {
                    if (Objects.nonNull(auditRecordInfo)) {
                        LOGGER.warn("审计记录处理器消费元素时发生异常, 丢弃 AuditRecordInfo: {}", auditRecordInfo, e);
                    }
                }
            }
            LOGGER.info("记录线程退出...");
        }

        public void shutdown() {
            runningFlag.set(false);
        }
    }
}
