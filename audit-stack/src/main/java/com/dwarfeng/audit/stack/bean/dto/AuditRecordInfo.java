package com.dwarfeng.audit.stack.bean.dto;

import com.dwarfeng.subgrade.stack.bean.dto.Dto;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;

import java.util.Map;

/**
 * 审计记录信息。
 *
 * <p>
 * 该 DTO 表示一次待记录的审计请求，创建时间由记录处理器在消费阶段生成。
 *
 * @author DwArFeng
 * @since 1.0.0-beta
 */
public class AuditRecordInfo implements Dto {

    private static final long serialVersionUID = -3926760795414253029L;

    private StringIdKey categoryKey;
    private Map<String, Object> properties;

    public AuditRecordInfo() {
    }

    public AuditRecordInfo(StringIdKey categoryKey, Map<String, Object> properties) {
        this.categoryKey = categoryKey;
        this.properties = properties;
    }

    public StringIdKey getCategoryKey() {
        return categoryKey;
    }

    public void setCategoryKey(StringIdKey categoryKey) {
        this.categoryKey = categoryKey;
    }

    public Map<String, Object> getProperties() {
        return properties;
    }

    public void setProperties(Map<String, Object> properties) {
        this.properties = properties;
    }

    @Override
    public String toString() {
        return "AuditRecordInfo{" +
                "categoryKey=" + categoryKey +
                ", properties=" + properties +
                '}';
    }
}
