package com.dwarfeng.audit.stack.bean.dto;

import com.dwarfeng.subgrade.stack.bean.dto.Dto;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 审计条目分组查询信息。
 *
 * @author DwArFeng
 * @since 1.0.0-beta
 */
public class AuditEntryGroupedLookupInfo implements Dto {

    private static final long serialVersionUID = 5539171105183425625L;

    public static final int LOGIC_OPERATOR_AND = 0;
    public static final int LOGIC_OPERATOR_OR = 1;

    /**
     * 分页信息。
     */
    private PagingInfo pagingInfo;

    /**
     * 逻辑连接符。
     */
    private int logicOperator = LOGIC_OPERATOR_AND;

    /**
     * 查询项列表。
     */
    private List<LookupItem> lookupItems = new ArrayList<>();

    /**
     * 子查询组列表。
     */
    private List<QueryGroup> queryGroups = new ArrayList<>();

    public AuditEntryGroupedLookupInfo() {
    }

    public AuditEntryGroupedLookupInfo(
            PagingInfo pagingInfo, int logicOperator, List<LookupItem> lookupItems, List<QueryGroup> queryGroups
    ) {
        this.pagingInfo = pagingInfo;
        this.logicOperator = logicOperator;
        this.lookupItems = lookupItems;
        this.queryGroups = queryGroups;
    }

    public PagingInfo getPagingInfo() {
        return pagingInfo;
    }

    public void setPagingInfo(PagingInfo pagingInfo) {
        this.pagingInfo = pagingInfo;
    }

    public int getLogicOperator() {
        return logicOperator;
    }

    public void setLogicOperator(int logicOperator) {
        this.logicOperator = logicOperator;
    }

    public List<LookupItem> getLookupItems() {
        return lookupItems;
    }

    public void setLookupItems(List<LookupItem> lookupItems) {
        this.lookupItems = lookupItems;
    }

    public List<QueryGroup> getQueryGroups() {
        return queryGroups;
    }

    public void setQueryGroups(List<QueryGroup> queryGroups) {
        this.queryGroups = queryGroups;
    }

    @Override
    public String toString() {
        return "AuditEntryGroupedLookupInfo{" +
                "pagingInfo=" + pagingInfo +
                ", logicOperator=" + logicOperator +
                ", lookupItems=" + lookupItems +
                ", queryGroups=" + queryGroups +
                '}';
    }

    /**
     * 查询组。
     *
     * @author DwArFeng
     * @since 1.0.0-beta
     */
    public static class QueryGroup implements Dto {

        private static final long serialVersionUID = 5934209154794377969L;

        /**
         * 逻辑连接符。
         */
        private int logicOperator = LOGIC_OPERATOR_AND;

        /**
         * 查询项列表。
         */
        private List<LookupItem> lookupItems = new ArrayList<>();

        /**
         * 子查询组列表。
         */
        private List<QueryGroup> queryGroups = new ArrayList<>();

        /**
         * 是否启用。
         */
        private boolean enabled = true;

        public QueryGroup() {
        }

        public QueryGroup(
                int logicOperator, List<LookupItem> lookupItems, List<QueryGroup> queryGroups, boolean enabled
        ) {
            this.logicOperator = logicOperator;
            this.lookupItems = lookupItems;
            this.queryGroups = queryGroups;
            this.enabled = enabled;
        }

        public int getLogicOperator() {
            return logicOperator;
        }

        public void setLogicOperator(int logicOperator) {
            this.logicOperator = logicOperator;
        }

        public List<LookupItem> getLookupItems() {
            return lookupItems;
        }

        public void setLookupItems(List<LookupItem> lookupItems) {
            this.lookupItems = lookupItems;
        }

        public List<QueryGroup> getQueryGroups() {
            return queryGroups;
        }

        public void setQueryGroups(List<QueryGroup> queryGroups) {
            this.queryGroups = queryGroups;
        }

        public boolean isEnabled() {
            return enabled;
        }

        public void setEnabled(boolean enabled) {
            this.enabled = enabled;
        }

        @Override
        public String toString() {
            return "QueryGroup{" +
                    "logicOperator=" + logicOperator +
                    ", lookupItems=" + lookupItems +
                    ", queryGroups=" + queryGroups +
                    ", enabled=" + enabled +
                    '}';
        }
    }

    /**
     * 查询项。
     *
     * @author DwArFeng
     * @since 1.0.0-beta
     */
    public static class LookupItem implements Dto {

        private static final long serialVersionUID = -7059669763676999011L;

        /**
         * 审计类别主键。
         */
        private StringIdKey categoryKey;

        /**
         * 审计条目主键。
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

        /**
         * 是否启用。
         */
        private boolean enabled = true;

        public LookupItem() {
        }

        public LookupItem(
                StringIdKey categoryKey, LongIdKey auditEntryKey, Date startCreatedDate, Date endCreatedDate,
                List<PropertyCondition> propertyConditions, boolean enabled
        ) {
            this.categoryKey = categoryKey;
            this.auditEntryKey = auditEntryKey;
            this.startCreatedDate = startCreatedDate;
            this.endCreatedDate = endCreatedDate;
            this.propertyConditions = propertyConditions;
            this.enabled = enabled;
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

        public boolean isEnabled() {
            return enabled;
        }

        public void setEnabled(boolean enabled) {
            this.enabled = enabled;
        }

        @Override
        public String toString() {
            return "LookupItem{" +
                    "categoryKey=" + categoryKey +
                    ", auditEntryKey=" + auditEntryKey +
                    ", startCreatedDate=" + startCreatedDate +
                    ", endCreatedDate=" + endCreatedDate +
                    ", propertyConditions=" + propertyConditions +
                    ", enabled=" + enabled +
                    '}';
        }
    }

    /**
     * 属性条件。
     *
     * @author DwArFeng
     * @since 1.0.0-beta
     */
    public static class PropertyCondition implements Dto {

        private static final long serialVersionUID = 3920543317375591065L;

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
