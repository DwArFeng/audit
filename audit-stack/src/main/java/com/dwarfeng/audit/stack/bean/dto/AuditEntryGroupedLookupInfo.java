package com.dwarfeng.audit.stack.bean.dto;

import com.dwarfeng.subgrade.stack.bean.dto.Dto;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;

import java.util.Date;
import java.util.List;

/**
 * 审计条目分组查询信息。
 *
 * @author DwArFeng
 * @since 1.0.0-beta
 */
public class AuditEntryGroupedLookupInfo implements Dto {

    private static final long serialVersionUID = -5964508476557915256L;

    /**
     * 分页信息。
     */
    private PagingInfo pagingInfo;

    /**
     * 逻辑连接符。
     *
     * <p>
     * int 枚举，可能的状态为：
     * <ul>
     *     <li>与</li>
     *     <li>或</li>
     * </ul>
     * 详细值参考 sdk 模块的常量工具类。
     */
    private int logicOperator;

    /**
     * 查询项列表。
     */
    private List<LookupItem> lookupItems;

    /**
     * 子查询组列表。
     */
    private List<QueryGroup> queryGroups;

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
         *
         * <p>
         * int 枚举，可能的状态为：
         * <ul>
         *     <li>与</li>
         *     <li>或</li>
         * </ul>
         * 详细值参考 sdk 模块的常量工具类。
         */
        private int logicOperator;

        /**
         * 查询项列表。
         */
        private List<LookupItem> lookupItems;

        /**
         * 子查询组列表。
         */
        private List<QueryGroup> queryGroups;

        /**
         * 是否启用。
         */
        private boolean enabled;

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

        private static final long serialVersionUID = 6191492247569371682L;

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
         * 组合查询项列表。
         */
        private List<CompositeItem> compositeItems;

        /**
         * 是否启用。
         */
        private boolean enabled;

        public LookupItem() {
        }

        public LookupItem(
                StringIdKey categoryKey, LongIdKey auditEntryKey, Date startCreatedDate, Date endCreatedDate,
                List<CompositeItem> compositeItems, boolean enabled
        ) {
            this.categoryKey = categoryKey;
            this.auditEntryKey = auditEntryKey;
            this.startCreatedDate = startCreatedDate;
            this.endCreatedDate = endCreatedDate;
            this.compositeItems = compositeItems;
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

        public List<CompositeItem> getCompositeItems() {
            return compositeItems;
        }

        public void setCompositeItems(List<CompositeItem> compositeItems) {
            this.compositeItems = compositeItems;
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
                    ", compositeItems=" + compositeItems +
                    ", enabled=" + enabled +
                    '}';
        }
    }

    /**
     * 组合查询项。
     *
     * @author DwArFeng
     * @since 1.0.0-beta
     */
    public static class CompositeItem implements Dto {

        private static final long serialVersionUID = 2200892017396399910L;

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
