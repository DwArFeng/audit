package com.dwarfeng.audit.impl.handler;

import com.dwarfeng.audit.stack.handler.InspectionReceiveHandler;
import com.dwarfeng.audit.stack.handler.InspectionReceiverHandler;
import com.dwarfeng.subgrade.impl.handler.GeneralStartableHandler;
import com.dwarfeng.subgrade.impl.handler.Worker;
import com.dwarfeng.subgrade.sdk.interceptor.analyse.BehaviorAnalyse;
import com.dwarfeng.subgrade.stack.exception.HandlerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * 接收处理器实现。
 *
 * @author DwArFeng
 * @since 1.1.0
 */
@Component
public class InspectionReceiveHandlerImpl implements InspectionReceiveHandler {

    private final GeneralStartableHandler handler;

    public InspectionReceiveHandlerImpl(InspectionReceiveWorker inspectionReceiveWorker) {
        handler = new GeneralStartableHandler(inspectionReceiveWorker);
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

    /**
     * 接收工作器。
     *
     * @author DwArFeng
     * @since 1.1.0
     */
    @Component
    public static class InspectionReceiveWorker implements Worker {

        private static final Logger LOGGER = LoggerFactory.getLogger(InspectionReceiveWorker.class);

        private final InspectionReceiverHandler inspectionReceiverHandler;

        public InspectionReceiveWorker(InspectionReceiverHandler inspectionReceiverHandler) {
            this.inspectionReceiverHandler = inspectionReceiverHandler;
        }

        @Override
        public void work() throws Exception {
            LOGGER.info("接收器开始工作...");
            inspectionReceiverHandler.current().start();
        }

        @Override
        public void rest() throws Exception {
            LOGGER.info("接收器停止工作...");
            inspectionReceiverHandler.current().stop();
        }
    }
}
