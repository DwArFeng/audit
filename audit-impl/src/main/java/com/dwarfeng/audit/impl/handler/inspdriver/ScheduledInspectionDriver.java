package com.dwarfeng.audit.impl.handler.inspdriver;

import com.dwarfeng.audit.sdk.handler.inspdriver.AbstractInspectionDriver;
import com.dwarfeng.audit.stack.bean.entity.InspectionDriverInfo;
import com.dwarfeng.audit.stack.exception.InspectionDriverException;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 计划任务驱动器。
 *
 * <p>
 * 该实现集中维护计划任务的注册、取消和停机标记，具体调度方式由模式决定。
 *
 * @author DwArFeng
 * @since 1.1.0
 */
final class ScheduledInspectionDriver extends AbstractInspectionDriver {

    private static final Logger LOGGER = LoggerFactory.getLogger(ScheduledInspectionDriver.class);

    enum Mode {
        CRON, FIXED_RATE, FIXED_DELAY
    }

    private final ThreadPoolTaskScheduler scheduler;
    private final Mode mode;
    private final Lock lock = new ReentrantLock();
    private final Set<ScheduledFuture<?>> futures = new HashSet<>();
    private final Set<ScheduledProcessor> processors = new HashSet<>();

    ScheduledInspectionDriver(ThreadPoolTaskScheduler scheduler, Mode mode) {
        this.scheduler = scheduler;
        this.mode = mode;
    }

    @Override
    public void register(InspectionDriverInfo driverInfo) throws InspectionDriverException {
        lock.lock();
        try {
            ScheduledProcessor processor = new ScheduledProcessor(driverInfo.getInspectionKey());
            ScheduledFuture<?> future;
            switch (mode) {
                case CRON:
                    future = scheduler.schedule(processor, new CronTrigger(driverInfo.getParam()));
                    break;
                case FIXED_RATE:
                    future = scheduler.scheduleAtFixedRate(processor, Long.parseLong(driverInfo.getParam()));
                    break;
                case FIXED_DELAY:
                    future = scheduler.scheduleWithFixedDelay(processor, Long.parseLong(driverInfo.getParam()));
                    break;
                default:
                    throw new IllegalStateException("未知的计划驱动模式: " + mode);
            }
            processors.add(processor);
            futures.add(future);
        } catch (Exception e) {
            throw new InspectionDriverException(e);
        } finally {
            lock.unlock();
        }
    }

    @Override
    public void unregisterAll() {
        lock.lock();
        try {
            futures.forEach(future -> future.cancel(true));
            processors.forEach(ScheduledProcessor::shutdown);
            futures.clear();
            processors.clear();
        } finally {
            lock.unlock();
        }
    }

    private class ScheduledProcessor implements Runnable {

        private final LongIdKey inspectionKey;
        private final Lock processorLock = new ReentrantLock();
        private boolean runningFlag = true;

        private ScheduledProcessor(LongIdKey inspectionKey) {
            this.inspectionKey = inspectionKey;
        }

        @Override
        public void run() {
            processorLock.lock();
            try {
                if (!runningFlag) {
                    return;
                }
                context.execute(inspectionKey);
            } catch (Exception e) {
                LOGGER.warn("自动审计 {} 执行动作时出现异常，放弃本次执行", inspectionKey, e);
            } finally {
                processorLock.unlock();
            }
        }

        private void shutdown() {
            processorLock.lock();
            try {
                runningFlag = false;
            } finally {
                processorLock.unlock();
            }
        }
    }
}
