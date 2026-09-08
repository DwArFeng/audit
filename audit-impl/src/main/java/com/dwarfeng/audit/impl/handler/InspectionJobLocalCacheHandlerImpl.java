package com.dwarfeng.audit.impl.handler;

import com.dwarfeng.audit.stack.bean.entity.Inspection;
import com.dwarfeng.audit.stack.bean.entity.InspectorInfo;
import com.dwarfeng.audit.stack.handler.InspectionJobLocalCacheHandler;
import com.dwarfeng.audit.stack.handler.Inspector;
import com.dwarfeng.audit.stack.handler.InspectorHandler;
import com.dwarfeng.audit.stack.service.InspectionMaintainService;
import com.dwarfeng.audit.stack.service.InspectorInfoMaintainService;
import com.dwarfeng.audit.stack.struct.InspectionJobLocalCache;
import com.dwarfeng.subgrade.impl.handler.Fetcher;
import com.dwarfeng.subgrade.impl.handler.GeneralLocalCacheHandler;
import com.dwarfeng.subgrade.sdk.interceptor.analyse.BehaviorAnalyse;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.exception.HandlerException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * 自动审计作业本地缓存处理器实现。
 *
 * @author DwArFeng
 * @since 1.1.0
 */
@Component
public class InspectionJobLocalCacheHandlerImpl implements InspectionJobLocalCacheHandler {

    private static final Comparator<InspectorInfo> INSPECTOR_INFO_COMPARATOR = Comparator
            .comparingInt(InspectorInfo::getIndex)
            .thenComparing(info -> info.getKey() == null ? Long.MAX_VALUE : info.getKey().getLongId());

    private final GeneralLocalCacheHandler<LongIdKey, InspectionJobLocalCache> handler;

    public InspectionJobLocalCacheHandlerImpl(InspectionJobLocalCacheFetcher fetcher) {
        this.handler = new GeneralLocalCacheHandler<>(fetcher);
    }

    @Override
    @BehaviorAnalyse
    public boolean exists(LongIdKey key) throws HandlerException {
        return handler.exists(key);
    }

    @Override
    @BehaviorAnalyse
    public InspectionJobLocalCache get(LongIdKey key) throws HandlerException {
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
    public static class InspectionJobLocalCacheFetcher implements Fetcher<LongIdKey, InspectionJobLocalCache> {

        private final InspectionMaintainService inspectionMaintainService;
        private final InspectorInfoMaintainService inspectorInfoMaintainService;
        private final InspectorHandler inspectorHandler;

        public InspectionJobLocalCacheFetcher(
                InspectionMaintainService inspectionMaintainService,
                InspectorInfoMaintainService inspectorInfoMaintainService,
                InspectorHandler inspectorHandler
        ) {
            this.inspectionMaintainService = inspectionMaintainService;
            this.inspectorInfoMaintainService = inspectorInfoMaintainService;
            this.inspectorHandler = inspectorHandler;
        }

        @Override
        @BehaviorAnalyse
        @Transactional(
                transactionManager = "hibernateTransactionManager", readOnly = true, rollbackFor = Exception.class
        )
        public boolean exists(LongIdKey key) throws Exception {
            return inspectionMaintainService.exists(key);
        }

        @Override
        @BehaviorAnalyse
        @Transactional(
                transactionManager = "hibernateTransactionManager", readOnly = true, rollbackFor = Exception.class
        )
        public InspectionJobLocalCache fetch(LongIdKey key) throws Exception {
            Inspection inspection = inspectionMaintainService.get(key);
            List<InspectorInfo> inspectorInfos = inspectorInfoMaintainService.lookupAsList(
                    InspectorInfoMaintainService.CHILD_FOR_INSPECTION, new Object[]{key}
            );
            inspectorInfos.sort(INSPECTOR_INFO_COMPARATOR);

            Map<LongIdKey, Inspector> inspectorMap = new HashMap<>();
            for (InspectorInfo inspectorInfo : inspectorInfos) {
                if (!Objects.equals(key, inspectorInfo.getInspectionKey())) {
                    throw new IllegalStateException("审计器所属自动审计不匹配: " + inspectorInfo.getKey());
                }
                if (inspectorInfo.getKey() != null && inspectorMap.containsKey(inspectorInfo.getKey())) {
                    throw new IllegalStateException("审计器信息主键重复: " + inspectorInfo.getKey());
                }
                if (inspectorInfo.isEnabled()) {
                    inspectorMap.put(
                            inspectorInfo.getKey(), inspectorHandler.make(inspectorInfo.getType(), inspectorInfo.getParam())
                    );
                }
            }

            return new InspectionJobLocalCache(inspection, inspectorInfos, inspectorMap);
        }
    }
}
