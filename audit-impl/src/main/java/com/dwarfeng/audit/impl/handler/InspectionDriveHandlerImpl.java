package com.dwarfeng.audit.impl.handler;

import com.dwarfeng.audit.stack.bean.entity.Inspection;
import com.dwarfeng.audit.stack.bean.entity.InspectionDriverInfo;
import com.dwarfeng.audit.stack.exception.InspectionDriverException;
import com.dwarfeng.audit.stack.handler.InspectionDriveHandler;
import com.dwarfeng.audit.stack.handler.InspectionDriveLocalCacheHandler;
import com.dwarfeng.audit.stack.handler.InspectionDriver;
import com.dwarfeng.audit.stack.service.InspectionMaintainService;
import com.dwarfeng.audit.stack.struct.InspectionDriveLocalCache;
import com.dwarfeng.subgrade.impl.handler.GeneralStartableHandler;
import com.dwarfeng.subgrade.impl.handler.Worker;
import com.dwarfeng.subgrade.sdk.interceptor.analyse.BehaviorAnalyse;
import com.dwarfeng.subgrade.stack.exception.HandlerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 驱动处理器实现。
 *
 * @author DwArFeng
 * @since 1.1.0
 */
@Component
public class InspectionDriveHandlerImpl implements InspectionDriveHandler {

    private final GeneralStartableHandler handler;

    public InspectionDriveHandlerImpl(InspectionDriveWorker inspectionDriveWorker) {
        handler = new GeneralStartableHandler(inspectionDriveWorker);
    }

    @Override
    @BehaviorAnalyse
    public boolean isStarted() {
        return handler.isStarted();
    }

    @Override
    @BehaviorAnalyse
    public void start() throws HandlerException {
        handler.start();
    }

    @Override
    @BehaviorAnalyse
    public void stop() throws HandlerException {
        handler.stop();
    }

    @Component
    public static class InspectionDriveWorker implements Worker {

        private static final Logger LOGGER = LoggerFactory.getLogger(InspectionDriveWorker.class);
        private final InspectionMaintainService inspectionMaintainService;
        private final InspectionDriveLocalCacheHandler inspectionDriveLocalCacheHandler;
        private final Set<InspectionDriver> usedDrivers = new HashSet<>();

        public InspectionDriveWorker(
                InspectionMaintainService inspectionMaintainService,
                InspectionDriveLocalCacheHandler inspectionDriveLocalCacheHandler
        ) {
            this.inspectionMaintainService = inspectionMaintainService;
            this.inspectionDriveLocalCacheHandler = inspectionDriveLocalCacheHandler;
        }

        @Override
        public void work() throws Exception {
            LOGGER.info("驱动器开始工作...");
            List<Inspection> inspections = inspectionMaintainService.lookupAsList();
            boolean successFlag = true;
            for (Inspection inspection : inspections) {
                if (!inspection.isEnabled()) {
                    continue;
                }
                InspectionDriveLocalCache cache = inspectionDriveLocalCacheHandler.get(inspection.getKey());
                if (cache == null) {
                    throw new InspectionDriverException("无法在本地缓存中找到有效的自动审计驱动上下文: " + inspection.getKey());
                }
                if (!registerDrivers(cache.getDriverMap())) {
                    successFlag = false;
                }
            }
            if (successFlag) {
                LOGGER.info("所有自动审计驱动器信息注册成功");
            } else {
                LOGGER.warn("至少一条自动审计驱动器信息注册失败，请查看警报日志以了解详细原因");
            }
        }

        private boolean registerDrivers(Map<InspectionDriverInfo, InspectionDriver> driverMap) {
            boolean successFlag = true;
            for (Map.Entry<InspectionDriverInfo, InspectionDriver> entry : driverMap.entrySet()) {
                try {
                    entry.getValue().register(entry.getKey());
                    usedDrivers.add(entry.getValue());
                } catch (Exception e) {
                    successFlag = false;
                    LOGGER.warn("驱动器信息 {} 注册失败，将忽略此条注册信息", entry.getKey(), e);
                }
            }
            return successFlag;
        }

        @Override
        public void rest() throws Exception {
            try {
                for (InspectionDriver inspectionDriver : usedDrivers) {
                    inspectionDriver.unregisterAll();
                }
            } finally {
                usedDrivers.clear();
            }
        }
    }
}
