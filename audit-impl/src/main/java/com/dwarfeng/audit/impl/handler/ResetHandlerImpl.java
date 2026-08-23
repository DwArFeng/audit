package com.dwarfeng.audit.impl.handler;

import com.dwarfeng.audit.stack.handler.ResetHandler;
import com.dwarfeng.audit.stack.handler.Resetter;
import com.dwarfeng.audit.stack.handler.ResetterHandler;
import com.dwarfeng.subgrade.impl.handler.GeneralStartableHandler;
import com.dwarfeng.subgrade.impl.handler.Worker;
import com.dwarfeng.subgrade.sdk.interceptor.analyse.BehaviorAnalyse;
import com.dwarfeng.subgrade.stack.exception.HandlerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 重置处理器实现。
 *
 * <p>
 * 该处理器统一管理当前启用重置器的生命周期，并将手动重置请求转交给重置处理器执行。
 *
 * @author DwArFeng
 * @since 1.0.0-beta
 */
@Component
public class ResetHandlerImpl implements ResetHandler {

    private final GeneralStartableHandler startableHandler;

    private final ResetProcessor resetProcessor;

    private final Lock lock = new ReentrantLock();

    public ResetHandlerImpl(ResetWorker resetWorker, ResetProcessor resetProcessor) {
        this.startableHandler = new GeneralStartableHandler(resetWorker);
        this.resetProcessor = resetProcessor;
    }

    @BehaviorAnalyse
    @Override
    public boolean isStarted() {
        lock.lock();
        try {
            return startableHandler.isStarted();
        } finally {
            lock.unlock();
        }
    }

    @BehaviorAnalyse
    @Override
    public void start() throws HandlerException {
        lock.lock();
        try {
            startableHandler.start();
        } finally {
            lock.unlock();
        }
    }

    @BehaviorAnalyse
    @Override
    public void stop() throws HandlerException {
        lock.lock();
        try {
            startableHandler.stop();
        } finally {
            lock.unlock();
        }
    }

    @BehaviorAnalyse
    @Override
    public void resetAuditRecord() throws HandlerException {
        lock.lock();
        try {
            resetProcessor.resetAuditRecord();
        } finally {
            lock.unlock();
        }
    }

    /**
     * 重置器生命周期工作器。
     *
     * <p>
     * 单个重置器启动或停止失败不会影响其它重置器继续执行。
     *
     * @author DwArFeng
     * @since 1.0.0-beta
     */
    @Component
    public static class ResetWorker implements Worker {

        private static final Logger LOGGER = LoggerFactory.getLogger(ResetWorker.class);

        private final ResetterHandler resetterHandler;

        public ResetWorker(ResetterHandler resetterHandler) {
            this.resetterHandler = resetterHandler;
        }

        @Override
        public void work() throws Exception {
            List<Resetter> resetters = resetterHandler.all();
            LOGGER.info("启动重置器, 共 {} 个", resetters.size());
            for (Resetter resetter : resetters) {
                try {
                    resetter.start();
                } catch (Exception e) {
                    LOGGER.warn("重置器 {} 启动时发生异常, 将不会启动, 异常信息如下: ", resetter, e);
                }
            }
        }

        @Override
        public void rest() throws Exception {
            List<Resetter> resetters = resetterHandler.all();
            LOGGER.info("停止重置器, 共 {} 个", resetters.size());
            for (Resetter resetter : resetters) {
                try {
                    resetter.stop();
                } catch (Exception e) {
                    LOGGER.warn("重置器 {} 停止时发生异常, 将不会停止, 异常信息如下: ", resetter, e);
                }
            }
        }
    }
}
