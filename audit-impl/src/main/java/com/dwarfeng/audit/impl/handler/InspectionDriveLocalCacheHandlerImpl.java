package com.dwarfeng.audit.impl.handler;

import com.dwarfeng.audit.stack.bean.entity.Inspection;
import com.dwarfeng.audit.stack.bean.entity.InspectionDriverInfo;
import com.dwarfeng.audit.stack.handler.InspectionDriveLocalCacheHandler;
import com.dwarfeng.audit.stack.handler.InspectionDriver;
import com.dwarfeng.audit.stack.handler.InspectionDriverHandler;
import com.dwarfeng.audit.stack.service.InspectionDriverInfoMaintainService;
import com.dwarfeng.audit.stack.service.InspectionMaintainService;
import com.dwarfeng.audit.stack.struct.InspectionDriveLocalCache;
import com.dwarfeng.subgrade.impl.handler.Fetcher;
import com.dwarfeng.subgrade.impl.handler.GeneralLocalCacheHandler;
import com.dwarfeng.subgrade.sdk.interceptor.analyse.BehaviorAnalyse;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.exception.HandlerException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * 驱动本地缓存处理器实现。
 *
 * @author DwArFeng
 * @since 1.1.0
 */
@Component
public class InspectionDriveLocalCacheHandlerImpl implements InspectionDriveLocalCacheHandler {

    private final GeneralLocalCacheHandler<LongIdKey, InspectionDriveLocalCache> handler;

    public InspectionDriveLocalCacheHandlerImpl(InspectionDriveLocalCacheFetcher fetcher) {
        handler = new GeneralLocalCacheHandler<>(fetcher);
    }

    @Override
    @BehaviorAnalyse
    public boolean exists(LongIdKey key) throws HandlerException {
        return handler.exists(key);
    }

    @Override
    @BehaviorAnalyse
    public InspectionDriveLocalCache get(LongIdKey key) throws HandlerException {
        return handler.get(key);
    }

    @Override
    @BehaviorAnalyse
    public boolean remove(LongIdKey key) {
        return handler.remove(key);
    }

    @Override
    @BehaviorAnalyse
    public void clear() {
        handler.clear();
    }

    @Component
    public static class InspectionDriveLocalCacheFetcher implements Fetcher<LongIdKey, InspectionDriveLocalCache> {

        private final InspectionMaintainService inspectionMaintainService;
        private final InspectionDriverInfoMaintainService driverInfoMaintainService;
        private final InspectionDriverHandler inspectionDriverHandler;

        public InspectionDriveLocalCacheFetcher(
                InspectionMaintainService inspectionMaintainService,
                InspectionDriverInfoMaintainService driverInfoMaintainService,
                InspectionDriverHandler inspectionDriverHandler
        ) {
            this.inspectionMaintainService = inspectionMaintainService;
            this.driverInfoMaintainService = driverInfoMaintainService;
            this.inspectionDriverHandler = inspectionDriverHandler;
        }

        @Override
        @BehaviorAnalyse
        @Transactional(transactionManager = "hibernateTransactionManager", readOnly = true, rollbackFor = Exception.class)
        public boolean exists(LongIdKey key) throws Exception {
            Inspection inspection = inspectionMaintainService.getIfExists(key);
            return Objects.nonNull(inspection) && inspection.isEnabled();
        }

        @Override
        @BehaviorAnalyse
        @Transactional(transactionManager = "hibernateTransactionManager", readOnly = true, rollbackFor = Exception.class)
        public InspectionDriveLocalCache fetch(LongIdKey key) throws Exception {
            Inspection inspection = inspectionMaintainService.get(key);
            List<InspectionDriverInfo> driverInfos = driverInfoMaintainService.lookupAsList(
                    InspectionDriverInfoMaintainService.CHILD_FOR_INSPECTION, new Object[]{key}
            );
            Map<InspectionDriverInfo, InspectionDriver> driverMap = new HashMap<>();
            for (InspectionDriverInfo driverInfo : driverInfos) {
                if (driverInfo.isEnabled()) {
                    driverMap.put(driverInfo, inspectionDriverHandler.find(driverInfo.getType()));
                }
            }
            return new InspectionDriveLocalCache(inspection, driverMap);
        }
    }
}
