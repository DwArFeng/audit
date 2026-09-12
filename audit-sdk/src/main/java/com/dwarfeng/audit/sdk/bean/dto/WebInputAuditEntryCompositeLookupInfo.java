package com.dwarfeng.audit.sdk.bean.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.audit.sdk.util.ValidAuditPropertyType;
import com.dwarfeng.audit.stack.bean.dto.AuditEntryCompositeLookupInfo;
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
 * WebInput 审计条目组合查询信息。
 *
 * @author DwArFeng
 * @since 1.0.0-beta
 */
public class WebInputAuditEntryCompositeLookupInfo implements Dto {

    private static final long serialVersionUID = -2314730884519437369L;

    public static AuditEntryCompositeLookupInfo toStackBean(WebInputAuditEntryCompositeLookupInfo webInput) {
        if (Objects.isNull(webInput)) {
            return null;
        } else {
            return new AuditEntryCompositeLookupInfo(
                    webInput.getPagingInfo(),
                    WebInputStringIdKey.toStackBean(webInput.getCategoryKey()),
                    WebInputLongIdKey.toStackBean(webInput.getAuditEntryKey()),
                    webInput.getStartCreatedDate(),
                    webInput.getEndCreatedDate(),
                    Optional.ofNullable(webInput.getPropertyConditions()).map(
                            f -> f.stream().map(WebInputPropertyCondition::toStackBean).collect(Collectors.toList())
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

    public WebInputAuditEntryCompositeLookupInfo() {
    }

    public PagingInfo getPagingInfo() {
        return pagingInfo;
    }

    public void setPagingInfo(PagingInfo pagingInfo) {
        this.pagingInfo = pagingInfo;
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

    @Override
    public String toString() {
        return "WebInputAuditEntryCompositeLookupInfo{" +
                "pagingInfo=" + pagingInfo +
                ", categoryKey=" + categoryKey +
                ", auditEntryKey=" + auditEntryKey +
                ", startCreatedDate=" + startCreatedDate +
                ", endCreatedDate=" + endCreatedDate +
                ", propertyConditions=" + propertyConditions +
                '}';
    }

    /**
     * WebInput 属性条件。
     *
     * @author DwArFeng
     * @since 1.0.0-beta
     */
    public static class WebInputPropertyCondition implements Dto {

        private static final long serialVersionUID = -669516799308020270L;

        public static AuditEntryCompositeLookupInfo.PropertyCondition toStackBean(WebInputPropertyCondition webInput) {
            if (Objects.isNull(webInput)) {
                return null;
            } else {
                return new AuditEntryCompositeLookupInfo.PropertyCondition(
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
        private boolean enabled;

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
