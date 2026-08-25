package com.dwarfeng.audit.stack.bean.dto;

import com.dwarfeng.subgrade.stack.bean.dto.Dto;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 审计条目组合查询信息。
 *
 * @author DwArFeng
 * @since 1.0.0-beta
 */
public class AuditEntryCompositeLookupInfo implements Dto {

    private static final long serialVersionUID = -334503079489523961L;

    /**
     * 分页信息。
     */
    private PagingInfo pagingInfo;

    /**
     * 审计类别主键。
     */
    private StringIdKey categoryKey;

    /**
     * 审计条目主键。
     *
     * <p>
     * 该字段为 <code>null</code> 时不限制审计条目主键，否则进行等值查询。
     */
    private LongIdKey auditEntryKey;

    /**
     * 创建时间起始值。
     */
    private Date startCreatedDate;

    /**
     * 创建时间结束值。
     */
    private Date endCreatedDate;

    /**
     * 属性条件列表。
     */
    private List<PropertyCondition> propertyConditions = new ArrayList<>();

    public AuditEntryCompositeLookupInfo() {
    }

    public AuditEntryCompositeLookupInfo(
            PagingInfo pagingInfo, StringIdKey categoryKey, LongIdKey auditEntryKey, Date startCreatedDate,
            Date endCreatedDate, List<PropertyCondition> propertyConditions
    ) {
        this.pagingInfo = pagingInfo;
        this.categoryKey = categoryKey;
        this.auditEntryKey = auditEntryKey;
        this.startCreatedDate = startCreatedDate;
        this.endCreatedDate = endCreatedDate;
        this.propertyConditions = propertyConditions;
    }

    public PagingInfo getPagingInfo() {
        return pagingInfo;
    }

    public void setPagingInfo(PagingInfo pagingInfo) {
        this.pagingInfo = pagingInfo;
    }

    public StringIdKey getCategoryKey() {
        return categoryKey;
    }

    public void setCategoryKey(StringIdKey categoryKey) {
        this.categoryKey = categoryKey;
    }

    public LongIdKey getAuditEntryKey() {
        return auditEntryKey;
    }

    public void setAuditEntryKey(LongIdKey auditEntryKey) {
        this.auditEntryKey = auditEntryKey;
    }

    public Date getStartCreatedDate() {
        return startCreatedDate;
    }

    public void setStartCreatedDate(Date startCreatedDate) {
        this.startCreatedDate = startCreatedDate;
    }

    public Date getEndCreatedDate() {
        return endCreatedDate;
    }

    public void setEndCreatedDate(Date endCreatedDate) {
        this.endCreatedDate = endCreatedDate;
    }

    public List<PropertyCondition> getPropertyConditions() {
        return propertyConditions;
    }

    public void setPropertyConditions(List<PropertyCondition> propertyConditions) {
        this.propertyConditions = propertyConditions;
    }

    @Override
    public String toString() {
        return "AuditEntryCompositeLookupInfo{" +
                "pagingInfo=" + pagingInfo +
                ", categoryKey=" + categoryKey +
                ", auditEntryKey=" + auditEntryKey +
                ", startCreatedDate=" + startCreatedDate +
                ", endCreatedDate=" + endCreatedDate +
                ", propertyConditions=" + propertyConditions +
                '}';
    }

    /**
     * 属性条件。
     *
     * @author DwArFeng
     * @since 1.0.0-beta
     */
    public static class PropertyCondition implements Dto {

        private static final long serialVersionUID = 7958581393922166888L;

        /**
         * 属性 ID。
         */
        private String propertyId;

        /**
         * 属性类型。
         */
        private int propertyType;

        /**
         * 属性值。
         */
        private Object propertyValue;

        /**
         * 是否启用。
         */
        private boolean enabled = true;

        public PropertyCondition() {
        }

        public PropertyCondition(String propertyId, int propertyType, Object propertyValue, boolean enabled) {
            this.propertyId = propertyId;
            this.propertyType = propertyType;
            this.propertyValue = propertyValue;
            this.enabled = enabled;
        }

        public String getPropertyId() {
            return propertyId;
        }

        public void setPropertyId(String propertyId) {
            this.propertyId = propertyId;
        }

        public int getPropertyType() {
            return propertyType;
        }

        public void setPropertyType(int propertyType) {
            this.propertyType = propertyType;
        }

        public Object getPropertyValue() {
            return propertyValue;
        }

        public void setPropertyValue(Object propertyValue) {
            this.propertyValue = propertyValue;
        }

        public boolean isEnabled() {
            return enabled;
        }

        public void setEnabled(boolean enabled) {
            this.enabled = enabled;
        }

        @Override
        public String toString() {
            return "PropertyCondition{" +
                    "propertyId='" + propertyId + '\'' +
                    ", propertyType=" + propertyType +
                    ", propertyValue=" + propertyValue +
                    ", enabled=" + enabled +
                    '}';
        }
    }
}
