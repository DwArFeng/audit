package com.dwarfeng.audit.impl.service;

import com.dwarfeng.audit.stack.handler.ConsumeHandler;
import com.dwarfeng.audit.stack.handler.InspectionReceiveHandler;
import com.dwarfeng.audit.stack.handler.InspectionReceiver;
import com.dwarfeng.audit.stack.handler.InspectionReceiverHandler;
import com.dwarfeng.audit.stack.service.InspectionReceiverQosService;
import com.dwarfeng.audit.stack.struct.InspectionReceiverConsumerStatus;
import com.dwarfeng.subgrade.sdk.exception.ServiceExceptionHelper;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.dwarfeng.subgrade.stack.exception.ServiceExceptionMapper;
import com.dwarfeng.subgrade.stack.log.LogLevel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.PreDestroy;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 自动审计接收器 QoS 服务实现。
 *
 * @author DwArFeng
 * @since 1.1.0
 */
@Service
public class InspectionReceiverQosServiceImpl implements InspectionReceiverQosService {

    private static final Logger LOGGER = LoggerFactory.getLogger(InspectionReceiverQosServiceImpl.class);

    private final InspectionReceiveHandler inspectionReceiveHandler;
    private final InspectionReceiverHandler inspectionReceiverHandler;
    private final ConsumeHandler<LongIdKey> consumeHandler;

    private final ServiceExceptionMapper sem;

    private final Lock lock = new ReentrantLock();

    private boolean startFlag = false;

    public InspectionReceiverQosServiceImpl(
            InspectionReceiveHandler inspectionReceiveHandler,
            InspectionReceiverHandler inspectionReceiverHandler,
            ConsumeHandler<LongIdKey> consumeHandler,
            ServiceExceptionMapper sem
    ) {
        this.inspectionReceiveHandler = inspectionReceiveHandler;
        this.inspectionReceiverHandler = inspectionReceiverHandler;
        this.consumeHandler = consumeHandler;
        this.sem = sem;
    }

    @PreDestroy
    private void dispose() throws Exception {
        lock.lock();
        try {
            stop0();
        } finally {
            lock.unlock();
        }
    }

    @Override
    public boolean isStarted() throws ServiceException {
        lock.lock();
        try {
            return startFlag;
        } catch (Exception e) {
            throw ServiceExceptionHelper.logParse("判断自动审计接收服务是否启动时发生异常", LogLevel.WARN, e, sem);
        } finally {
            lock.unlock();
        }
    }

    @Override
    public void start() throws ServiceException {
        lock.lock();
        try {
            start0();
        } catch (Exception e) {
            throw ServiceExceptionHelper.logParse("启动自动审计接收服务时发生异常", LogLevel.WARN, e, sem);
        } finally {
            lock.unlock();
        }
    }

    private void start0() throws Exception {
        if (startFlag) {
            return;
        }

        try {
            LOGGER.info("开启自动审计接收服务...");
            consumeHandler.start();
            inspectionReceiveHandler.start();
            startFlag = true;
        } catch (Exception e) {
            LOGGER.warn("开启自动审计接收服务时发生异常, 将尝试关闭消费服务, 异常信息如下: ", e);
            startFlag = false;
            try {
                consumeHandler.stop();
                inspectionReceiveHandler.stop();
            } catch (Exception e1) {
                LOGGER.warn("开启自动审计接收服务时发生异常, 且关闭消费服务时发生异常, 关闭服务的异常信息如下: ", e1);
            }
            throw e;
        }
    }

    @Override
    public void stop() throws ServiceException {
        lock.lock();
        try {
            stop0();
        } catch (Exception e) {
            throw ServiceExceptionHelper.logParse("停止自动审计接收服务时发生异常", LogLevel.WARN, e, sem);
        } finally {
            lock.unlock();
        }
    }

    private void stop0() throws Exception {
        if (!startFlag) {
            return;
        }

        try {
            LOGGER.info("关闭自动审计接收服务...");
            inspectionReceiveHandler.stop();
            try {
                Thread.sleep(1000);
            } catch (Exception ignored) {
            }
            consumeHandler.stop();
            startFlag = false;
        } catch (Exception e) {
            LOGGER.warn("关闭自动审计接收服务时发生异常, 异常信息如下: ", e);
            throw e;
        }
    }

    @Override
    public InspectionReceiver currentReceiver() throws ServiceException {
        lock.lock();
        try {
            return inspectionReceiverHandler.current();
        } catch (Exception e) {
            throw ServiceExceptionHelper.logParse("获取当前正在使用的自动审计接收器时发生异常", LogLevel.WARN, e, sem);
        } finally {
            lock.unlock();
        }
    }

    @Override
    public List<InspectionReceiver> allReceivers() throws ServiceException {
        lock.lock();
        try {
            return inspectionReceiverHandler.all();
        } catch (Exception e) {
            throw ServiceExceptionHelper.logParse("获取全部自动审计接收器时发生异常", LogLevel.WARN, e, sem);
        } finally {
            lock.unlock();
        }
    }

    @Override
    public InspectionReceiverConsumerStatus getConsumerStatus() throws ServiceException {
        lock.lock();
        try {
            return new InspectionReceiverConsumerStatus(
                    consumeHandler.bufferedSize(),
                    consumeHandler.getBufferSize(),
                    consumeHandler.getThread(),
                    consumeHandler.isIdle()
            );
        } catch (Exception e) {
            throw ServiceExceptionHelper.logParse("获取自动审计接收消费者状态时发生异常", LogLevel.WARN, e, sem);
        } finally {
            lock.unlock();
        }
    }

    @Override
    public void setConsumerParameters(Integer bufferSize, Integer thread) throws ServiceException {
        lock.lock();
        try {
            consumeHandler.setBufferParameters(
                    Objects.isNull(bufferSize) ? consumeHandler.getBufferSize() : bufferSize,
                    consumeHandler.getBatchSize(),
                    consumeHandler.getMaxIdleTime()
            );
            consumeHandler.setThread(
                    Objects.isNull(thread) ? consumeHandler.getThread() : thread
            );
        } catch (Exception e) {
            throw ServiceExceptionHelper.logParse("设置自动审计接收消费者参数时发生异常", LogLevel.WARN, e, sem);
        } finally {
            lock.unlock();
        }
    }

    @Override
    public String toString() {
        return "InspectionReceiverQosServiceImpl{" +
                "inspectionReceiveHandler=" + inspectionReceiveHandler +
                ", inspectionReceiverHandler=" + inspectionReceiverHandler +
                ", consumeHandler=" + consumeHandler +
                ", sem=" + sem +
                ", lock=" + lock +
                ", startFlag=" + startFlag +
                '}';
    }
}
