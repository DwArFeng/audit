package com.dwarfeng.audit.impl.handler;

import com.dwarfeng.audit.stack.handler.InspectionDispatchHandler;
import com.dwarfeng.audit.stack.handler.InspectionDispatcherHandler;
import com.dwarfeng.subgrade.impl.handler.GeneralStartableHandler;
import com.dwarfeng.subgrade.impl.handler.Worker;
import com.dwarfeng.subgrade.sdk.interceptor.analyse.BehaviorAnalyse;
import com.dwarfeng.subgrade.stack.exception.HandlerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * 调度处理器实现。
 *
 * @author DwArFeng
 * @since 1.1.0
 */
@Component
public class InspectionDispatchHandlerImpl implements InspectionDispatchHandler {

    private final GeneralStartableHandler handler;

    public InspectionDispatchHandlerImpl(InspectionDispatchWorker inspectionDispatchWorker) {
        handler = new GeneralStartableHandler(inspectionDispatchWorker);
    }

    @BehaviorAnalyse
    @Override
    public boolean isStarted() {
        return handler.isStarted();
    }

    @BehaviorAnalyse
    @Override
    public void start() throws HandlerException {
        handler.start();
    }

    @BehaviorAnalyse
    @Override
    public void stop() throws HandlerException {
        handler.stop();
    }

    @Component
    public static class InspectionDispatchWorker implements Worker {

        private static final Logger LOGGER = LoggerFactory.getLogger(InspectionDispatchWorker.class);
        private final InspectionDispatcherHandler inspectionDispatcherHandler;

        public InspectionDispatchWorker(InspectionDispatcherHandler inspectionDispatcherHandler) {
            this.inspectionDispatcherHandler = inspectionDispatcherHandler;
        }

        @Override
        public void work() throws Exception {
            LOGGER.info("调度器开始工作...");
            inspectionDispatcherHandler.current().start();
        }

        @Override
        public void rest() throws Exception {
            LOGGER.info("调度器停止工作...");
            inspectionDispatcherHandler.current().stop();
        }
    }
}
