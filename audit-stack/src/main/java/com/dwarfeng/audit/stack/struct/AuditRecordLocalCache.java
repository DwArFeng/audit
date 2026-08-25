package com.dwarfeng.audit.stack.struct;

import com.dwarfeng.audit.stack.bean.entity.AuditCategory;
import com.dwarfeng.audit.stack.bean.entity.AuditPropertyIndicator;

import java.util.Map;

/**
 * 审计记录本地缓存。
 *
 * <p>
 * 该缓存保存审计记录处理过程中使用的审计类别及其全部属性指示器，
 * 用于避免审计记录消费时重复查询关联数据。
 *
 * @author DwArFeng
 * @since 1.0.0-beta
 */
public final class AuditRecordLocalCache {

    private final AuditCategory auditCategory;
    private final Map<String, AuditPropertyIndicator> auditPropertyIndicatorMap;

    public AuditRecordLocalCache(
            AuditCategory auditCategory, Map<String, AuditPropertyIndicator> auditPropertyIndicatorMap
    ) {
        this.auditCategory = auditCategory;
        this.auditPropertyIndicatorMap = auditPropertyIndicatorMap;
    }

    public AuditCategory getAuditCategory() {
        return auditCategory;
    }

    public Map<String, AuditPropertyIndicator> getAuditPropertyIndicatorMap() {
        return auditPropertyIndicatorMap;
    }

    @Override
    public String toString() {
        return "AuditRecordLocalCache{" +
                "auditCategory=" + auditCategory +
                ", auditPropertyIndicatorMap=" + auditPropertyIndicatorMap +
                '}';
    }
}
