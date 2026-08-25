package com.dwarfeng.audit.impl.handler;

import com.dwarfeng.audit.stack.bean.entity.AuditCategory;
import com.dwarfeng.audit.stack.bean.entity.AuditPropertyIndicator;
import com.dwarfeng.audit.stack.handler.AuditRecordLocalCacheHandler;
import com.dwarfeng.audit.stack.service.AuditCategoryMaintainService;
import com.dwarfeng.audit.stack.service.AuditPropertyIndicatorMaintainService;
import com.dwarfeng.audit.stack.struct.AuditRecordLocalCache;
import com.dwarfeng.subgrade.impl.handler.Fetcher;
import com.dwarfeng.subgrade.impl.handler.GeneralLocalCacheHandler;
import com.dwarfeng.subgrade.sdk.interceptor.analyse.BehaviorAnalyse;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.exception.HandlerException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 审计记录本地缓存处理器实现。
 *
 * @author DwArFeng
 * @since 1.0.0-beta
 */
@Component
public class AuditRecordLocalCacheHandlerImpl implements AuditRecordLocalCacheHandler {

    private final GeneralLocalCacheHandler<StringIdKey, AuditRecordLocalCache> handler;

    public AuditRecordLocalCacheHandlerImpl(AuditRecordLocalCacheFetcher auditRecordLocalCacheFetcher) {
        handler = new GeneralLocalCacheHandler<>(auditRecordLocalCacheFetcher);
    }

    @BehaviorAnalyse
    @Override
    public boolean exists(StringIdKey key) throws HandlerException {
        return handler.exists(key);
    }

    @BehaviorAnalyse
    @Override
    public AuditRecordLocalCache get(StringIdKey key) throws HandlerException {
        return handler.get(key);
    }

    @BehaviorAnalyse
    @Override
    public boolean remove(StringIdKey key) {
        return handler.remove(key);
    }

    @BehaviorAnalyse
    @Override
    public void clear() {
        handler.clear();
    }

    @Component
    public static class AuditRecordLocalCacheFetcher implements Fetcher<StringIdKey, AuditRecordLocalCache> {

        private final AuditCategoryMaintainService auditCategoryMaintainService;
        private final AuditPropertyIndicatorMaintainService auditPropertyIndicatorMaintainService;

        public AuditRecordLocalCacheFetcher(
                AuditCategoryMaintainService auditCategoryMaintainService,
                AuditPropertyIndicatorMaintainService auditPropertyIndicatorMaintainService
        ) {
            this.auditCategoryMaintainService = auditCategoryMaintainService;
            this.auditPropertyIndicatorMaintainService = auditPropertyIndicatorMaintainService;
        }

        @Override
        @BehaviorAnalyse
        @Transactional(
                transactionManager = "hibernateTransactionManager", readOnly = true, rollbackFor = Exception.class
        )
        public boolean exists(StringIdKey key) throws Exception {
            return auditCategoryMaintainService.exists(key);
        }

        @Override
        @BehaviorAnalyse
        @Transactional(
                transactionManager = "hibernateTransactionManager", readOnly = true, rollbackFor = Exception.class
        )
        public AuditRecordLocalCache fetch(StringIdKey key) throws Exception {
            AuditCategory auditCategory = auditCategoryMaintainService.get(key);
            List<AuditPropertyIndicator> indicators = auditPropertyIndicatorMaintainService.lookupAsList(
                    AuditPropertyIndicatorMaintainService.CHILD_FOR_AUDIT_CATEGORY, new Object[]{key}
            );

            Map<String, AuditPropertyIndicator> indicatorMap = new HashMap<>();
            for (AuditPropertyIndicator indicator : indicators) {
                indicatorMap.put(indicator.getKey().getPropertyStringId(), indicator);
            }

            return new AuditRecordLocalCache(auditCategory, indicatorMap);
        }
    }
}
