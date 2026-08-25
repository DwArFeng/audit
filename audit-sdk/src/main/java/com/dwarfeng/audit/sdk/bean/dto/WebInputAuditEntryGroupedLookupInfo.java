package com.dwarfeng.audit.sdk.bean.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.audit.sdk.util.ValidAuditPropertyType;
import com.dwarfeng.audit.stack.bean.dto.AuditEntryGroupedLookupInfo;
import com.dwarfeng.subgrade.sdk.bean.key.WebInputLongIdKey;
import com.dwarfeng.subgrade.sdk.bean.key.WebInputStringIdKey;
import com.dwarfeng.subgrade.stack.bean.dto.Dto;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * WebInput 审计条目分组查询信息。
 *
 * @author DwArFeng
 * @since 1.0.0-beta
 */
public class WebInputAuditEntryGroupedLookupInfo implements Dto {

    private static final long serialVersionUID = 3318642679619863333L;

    public static AuditEntryGroupedLookupInfo toStackBean(WebInputAuditEntryGroupedLookupInfo webInput) {
        if (Objects.isNull(webInput)) {
            return null;
        } else {
            return new AuditEntryGroupedLookupInfo(
                    webInput.getPagingInfo(),
                    webInput.getLogicOperator(),
                    Optional.ofNullable(webInput.getLookupItems()).map(
                            f -> f.stream().map(WebInputLookupItem::toStackBean).collect(Collectors.toList())
                    ).orElse(null),
                    Optional.ofNullable(webInput.getQueryGroups()).map(
                            f -> f.stream().map(WebInputQueryGroup::toStackBean).collect(Collectors.toList())
                    ).orElse(null)
            );
        }
    }

    /**
     * 分页信息。
     */
    @JSONField(name = "paging_info")
    private PagingInfo pagingInfo;

    /**
     * 逻辑连接符。
     */
    @JSONField(name = "logic_operator")
    private int logicOperator = AuditEntryGroupedLookupInfo.LOGIC_OPERATOR_AND;

    /**
     * 查询项列表。
     */
    @JSONField(name = "lookup_items")
    @Valid
    private List<WebInputLookupItem> lookupItems;

    /**
     * 子查询组列表。
     */
    @JSONField(name = "query_groups")
    @Valid
    private List<WebInputQueryGroup> queryGroups;

    public WebInputAuditEntryGroupedLookupInfo() {
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

    public List<WebInputLookupItem> getLookupItems() {
        return lookupItems;
    }

    public void setLookupItems(List<WebInputLookupItem> lookupItems) {
        this.lookupItems = lookupItems;
    }

    public List<WebInputQueryGroup> getQueryGroups() {
        return queryGroups;
    }

    public void setQueryGroups(List<WebInputQueryGroup> queryGroups) {
        this.queryGroups = queryGroups;
    }

    @Override
    public String toString() {
        return "WebInputAuditEntryGroupedLookupInfo{" +
                "pagingInfo=" + pagingInfo +
                ", logicOperator=" + logicOperator +
                ", lookupItems=" + lookupItems +
                ", queryGroups=" + queryGroups +
                '}';
    }

    /**
     * WebInput 查询组。
     *
     * @author DwArFeng
     * @since 1.0.0-beta
     */
    public static class WebInputQueryGroup implements Dto {

        private static final long serialVersionUID = 8784528560377084948L;

        public static AuditEntryGroupedLookupInfo.QueryGroup toStackBean(WebInputQueryGroup webInput) {
            if (Objects.isNull(webInput)) {
                return null;
            } else {
                return new AuditEntryGroupedLookupInfo.QueryGroup(
                        webInput.getLogicOperator(),
                        Optional.ofNullable(webInput.getLookupItems()).map(
                                f -> f.stream().map(WebInputLookupItem::toStackBean).collect(Collectors.toList())
                        ).orElse(null),
                        Optional.ofNullable(webInput.getQueryGroups()).map(
                                f -> f.stream().map(WebInputQueryGroup::toStackBean).collect(Collectors.toList())
                        ).orElse(null),
                        webInput.isEnabled()
                );
            }
        }

        /**
         * 逻辑连接符。
         */
        @JSONField(name = "logic_operator")
        private int logicOperator = AuditEntryGroupedLookupInfo.LOGIC_OPERATOR_AND;

        /**
         * 查询项列表。
         */
        @JSONField(name = "lookup_items")
        @Valid
        private List<WebInputLookupItem> lookupItems;

        /**
         * 子查询组列表。
         */
        @JSONField(name = "query_groups")
        @Valid
        private List<WebInputQueryGroup> queryGroups;

        /**
         * 是否启用。
         */
        @JSONField(name = "enabled")
        private boolean enabled = true;

        public WebInputQueryGroup() {
        }

        public int getLogicOperator() {
            return logicOperator;
        }

        public void setLogicOperator(int logicOperator) {
            this.logicOperator = logicOperator;
        }

        public List<WebInputLookupItem> getLookupItems() {
            return lookupItems;
        }

        public void setLookupItems(List<WebInputLookupItem> lookupItems) {
            this.lookupItems = lookupItems;
        }

        public List<WebInputQueryGroup> getQueryGroups() {
            return queryGroups;
        }

        public void setQueryGroups(List<WebInputQueryGroup> queryGroups) {
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
            return "WebInputQueryGroup{" +
                    "logicOperator=" + logicOperator +
                    ", lookupItems=" + lookupItems +
                    ", queryGroups=" + queryGroups +
                    ", enabled=" + enabled +
                    '}';
        }
    }

    /**
     * WebInput 查询项。
     *
     * @author DwArFeng
     * @since 1.0.0-beta
     */
    public static class WebInputLookupItem implements Dto {

        private static final long serialVersionUID = 4734050634226162376L;

        public static AuditEntryGroupedLookupInfo.LookupItem toStackBean(WebInputLookupItem webInput) {
            if (Objects.isNull(webInput)) {
                return null;
            } else {
                return new AuditEntryGroupedLookupInfo.LookupItem(
                        WebInputStringIdKey.toStackBean(webInput.getCategoryKey()),
                        WebInputLongIdKey.toStackBean(webInput.getAuditEntryKey()),
                        webInput.getStartCreatedDate(),
                        webInput.getEndCreatedDate(),
                        Optional.ofNullable(webInput.getPropertyConditions()).map(
                                f -> f.stream().map(WebInputPropertyCondition::toStackBean).collect(Collectors.toList())
                        ).orElse(null),
                        webInput.isEnabled()
                );
            }
        }

        /**
         * 审计类别主键。
         */
        @JSONField(name = "category_key")
        @Valid
        private WebInputStringIdKey categoryKey;

        /**
         * 审计条目主键。
         */
        @JSONField(name = "audit_entry_key")
        @Valid
        private WebInputLongIdKey auditEntryKey;

        /**
         * 创建时间起始值。
         */
        @JSONField(name = "start_created_date")
        private Date startCreatedDate;

        /**
         * 创建时间结束值。
         */
        @JSONField(name = "end_created_date")
        private Date endCreatedDate;

        /**
         * 属性条件列表。
         */
        @JSONField(name = "property_conditions")
        @Valid
        private List<WebInputPropertyCondition> propertyConditions;

        /**
         * 是否启用。
         */
        @JSONField(name = "enabled")
        private boolean enabled = true;

        public WebInputLookupItem() {
        }

        public WebInputStringIdKey getCategoryKey() {
            return categoryKey;
        }

        public void setCategoryKey(WebInputStringIdKey categoryKey) {
            this.categoryKey = categoryKey;
        }

        public WebInputLongIdKey getAuditEntryKey() {
            return auditEntryKey;
        }

        public void setAuditEntryKey(WebInputLongIdKey auditEntryKey) {
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

        public List<WebInputPropertyCondition> getPropertyConditions() {
            return propertyConditions;
        }

        public void setPropertyConditions(List<WebInputPropertyCondition> propertyConditions) {
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
            return "WebInputLookupItem{" +
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
     * WebInput 属性条件。
     *
     * @author DwArFeng
     * @since 1.0.0-beta
     */
    public static class WebInputPropertyCondition implements Dto {

        private static final long serialVersionUID = 3216342054704041389L;

        public static AuditEntryGroupedLookupInfo.PropertyCondition toStackBean(WebInputPropertyCondition webInput) {
            if (Objects.isNull(webInput)) {
                return null;
            } else {
                return new AuditEntryGroupedLookupInfo.PropertyCondition(
                        webInput.getPropertyId(),
                        webInput.getPropertyType(),
                        webInput.getPropertyValue(),
                        webInput.isEnabled()
                );
            }
        }

        /**
         * 属性 ID。
         */
        @JSONField(name = "property_id")
        @NotNull
        @NotEmpty
        private String propertyId;

        /**
         * 属性类型。
         */
        @JSONField(name = "property_type")
        @ValidAuditPropertyType
        private int propertyType;

        /**
         * 属性值。
         */
        @JSONField(name = "property_value")
        private Object propertyValue;

        /**
         * 是否启用。
         */
        @JSONField(name = "enabled")
        private boolean enabled = true;

        public WebInputPropertyCondition() {
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
            return "WebInputPropertyCondition{" +
                    "propertyId='" + propertyId + '\'' +
                    ", propertyType=" + propertyType +
                    ", propertyValue=" + propertyValue +
                    ", enabled=" + enabled +
                    '}';
        }
    }
}
