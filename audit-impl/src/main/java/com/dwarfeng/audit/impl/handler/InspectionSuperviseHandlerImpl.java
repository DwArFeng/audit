package com.dwarfeng.audit.impl.handler;

import com.dwarfeng.audit.stack.handler.InspectionDispatchHandler;
import com.dwarfeng.audit.stack.handler.InspectionDriveHandler;
import com.dwarfeng.audit.stack.handler.InspectionSuperviseHandler;
import com.dwarfeng.subgrade.impl.handler.CuratorDistributedLockHandler;
import com.dwarfeng.subgrade.impl.handler.Worker;
import com.dwarfeng.subgrade.sdk.interceptor.analyse.BehaviorAnalyse;
import com.dwarfeng.subgrade.stack.exception.HandlerException;
import org.apache.curator.framework.CuratorFramework;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 自动审计主管处理器实现。
 *
 * @author DwArFeng
 * @since 1.1.0
 */
@Component
public class InspectionSuperviseHandlerImpl implements InspectionSuperviseHandler {

    private final CuratorDistributedLockHandler handler;

    public InspectionSuperviseHandlerImpl(
            CuratorFramework curatorFramework,
            @Value("${com.dwarfeng.audit.curator.latch_path.inspection_supervise.leader_latch}")
            String leaderLatchPath,
            InspectionSuperviseWorker worker
    ) {
        handler = new CuratorDistributedLockHandler(curatorFramework, leaderLatchPath, worker);
    }

    @BehaviorAnalyse
    @Override
    public boolean isOnline() {
        return handler.isOnline();
    }

    @BehaviorAnalyse
    @Override
    public void online() throws HandlerException {
        handler.online();
    }

    @BehaviorAnalyse
    @Override
    public void offline() throws HandlerException {
        handler.offline();
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

    @BehaviorAnalyse
    @Override
    public boolean isLockHolding() {
        return handler.isLockHolding();
    }

    @BehaviorAnalyse
    @Override
    public boolean isWorking() {
        return handler.isWorking();
    }

    /**
     * 自动审计主管工作器。
     *
     * <p>
     * 该工作器在主管开始工作时启动调度和驱动机制，在主管停止工作时按逆序停止驱动和调度机制。
     *
     * @author DwArFeng
     * @since 1.1.0
     */
    @Component
    public static class InspectionSuperviseWorker implements Worker {

        private static final Logger LOGGER = LoggerFactory.getLogger(InspectionSuperviseWorker.class);

        private final InspectionDispatchHandler inspectionDispatchHandler;
        private final InspectionDriveHandler driveHandler;

        public InspectionSuperviseWorker(
                InspectionDispatchHandler inspectionDispatchHandler,
                InspectionDriveHandler driveHandler
        ) {
            this.inspectionDispatchHandler = inspectionDispatchHandler;
            this.driveHandler = driveHandler;
        }

        @Override
        public void work() throws Exception {
            LOGGER.info("自动审计主管处理器开始工作...");
            inspectionDispatchHandler.start();
            driveHandler.start();
        }

        @Override
        public void rest() throws Exception {
            LOGGER.info("自动审计主管处理器停止工作...");
            driveHandler.stop();
            inspectionDispatchHandler.stop();
        }
    }
}
