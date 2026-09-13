package com.dwarfeng.audit.stack.bean.dto;

import com.dwarfeng.subgrade.stack.bean.dto.Dto;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;

import java.util.Date;
import java.util.List;

/**
 * 审计条目组合查询信息。
 *
 * @author DwArFeng
 * @since 1.0.0-beta
 */
public class AuditEntryCompositeLookupInfo implements Dto {

    private static final long serialVersionUID = 5794843597397115496L;

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
     * 组合查询项列表。
     */
    private List<CompositeItem> compositeItems;

    public AuditEntryCompositeLookupInfo() {
    }

    public AuditEntryCompositeLookupInfo(
            PagingInfo pagingInfo, StringIdKey categoryKey, LongIdKey auditEntryKey, Date startCreatedDate,
            Date endCreatedDate, List<CompositeItem> compositeItems
    ) {
        this.pagingInfo = pagingInfo;
        this.categoryKey = categoryKey;
        this.auditEntryKey = auditEntryKey;
        this.startCreatedDate = startCreatedDate;
        this.endCreatedDate = endCreatedDate;
        this.compositeItems = compositeItems;
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

    public List<CompositeItem> getCompositeItems() {
        return compositeItems;
    }

    public void setCompositeItems(List<CompositeItem> compositeItems) {
        this.compositeItems = compositeItems;
    }

    @Override
    public String toString() {
        return "AuditEntryCompositeLookupInfo{" +
                "pagingInfo=" + pagingInfo +
                ", categoryKey=" + categoryKey +
                ", auditEntryKey=" + auditEntryKey +
                ", startCreatedDate=" + startCreatedDate +
                ", endCreatedDate=" + endCreatedDate +
                ", compositeItems=" + compositeItems +
                '}';
    }

    /**
     * 组合查询项。
     *
     * @author DwArFeng
     * @since 1.0.0-beta
     */
    public static class CompositeItem implements Dto {

        private static final long serialVersionUID = -6010169825392203161L;

        /**
         * 属性 ID。
         */
        private String propertyId;

        /**
         * 属性类型。
         */
        private int propertyType;

        /**
         * 第一条件。
         */
        private Object firstCondition;

        /**
         * 第二条件。
         */
        private Object secondCondition;

        /**
         * 是否启用。
         */
        private boolean enabled;

        public CompositeItem() {
        }

        public CompositeItem(
                String propertyId, Integer propertyType, Object firstCondition, Object secondCondition, boolean enabled
        ) {
            this.propertyId = propertyId;
            this.propertyType = propertyType;
            this.firstCondition = firstCondition;
            this.secondCondition = secondCondition;
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

        public Object getFirstCondition() {
            return firstCondition;
        }

        public void setFirstCondition(Object firstCondition) {
            this.firstCondition = firstCondition;
        }

        public Object getSecondCondition() {
            return secondCondition;
        }

        public void setSecondCondition(Object secondCondition) {
            this.secondCondition = secondCondition;
        }

        public boolean isEnabled() {
            return enabled;
        }

        public void setEnabled(boolean enabled) {
            this.enabled = enabled;
        }

        @Override
        public String toString() {
            return "CompositeItem{" +
                    "propertyId='" + propertyId + '\'' +
                    ", propertyType=" + propertyType +
                    ", firstCondition=" + firstCondition +
                    ", secondCondition=" + secondCondition +
                    ", enabled=" + enabled +
                    '}';
        }
    }
}
