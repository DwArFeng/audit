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

    private static final long serialVersionUID = 7119955977837533108L;

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
                    Optional.ofNullable(webInput.getCompositeItems()).map(
                            f -> f.stream().map(WebInputCompositeItem::toStackBean).collect(Collectors.toList())
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
     * 组合查询项列表。
     */
    @JSONField(name = "composite_items")
    @Valid
    private List<WebInputCompositeItem> compositeItems;

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

    public List<WebInputCompositeItem> getCompositeItems() {
        return compositeItems;
    }

    public void setCompositeItems(List<WebInputCompositeItem> compositeItems) {
        this.compositeItems = compositeItems;
    }

    @Override
    public String toString() {
        return "WebInputAuditEntryCompositeLookupInfo{" +
                "pagingInfo=" + pagingInfo +
                ", categoryKey=" + categoryKey +
                ", auditEntryKey=" + auditEntryKey +
                ", startCreatedDate=" + startCreatedDate +
                ", endCreatedDate=" + endCreatedDate +
                ", compositeItems=" + compositeItems +
                '}';
    }

    /**
     * WebInput 组合查询项。
     *
     * @author DwArFeng
     * @since 1.0.0-beta
     */
    public static class WebInputCompositeItem implements Dto {

        private static final long serialVersionUID = -538227165460834780L;

        public static AuditEntryCompositeLookupInfo.CompositeItem toStackBean(WebInputCompositeItem webInput) {
            if (Objects.isNull(webInput)) {
                return null;
            } else {
                return new AuditEntryCompositeLookupInfo.CompositeItem(
                        webInput.getPropertyId(),
                        webInput.getPropertyType(),
                        webInput.getFirstCondition(),
                        webInput.getSecondCondition(),
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

        @JSONField(name = "first_condition")
        private Object firstCondition;

        @JSONField(name = "second_condition")
        private Object secondCondition;

        /**
         * 是否启用。
         */
        @JSONField(name = "enabled")
        private boolean enabled;

        public WebInputCompositeItem() {
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
            return "WebInputCompositeItem{" +
                    "propertyId='" + propertyId + '\'' +
                    ", propertyType=" + propertyType +
                    ", firstCondition=" + firstCondition +
                    ", secondCondition=" + secondCondition +
                    ", enabled=" + enabled +
                    '}';
        }
    }
}
