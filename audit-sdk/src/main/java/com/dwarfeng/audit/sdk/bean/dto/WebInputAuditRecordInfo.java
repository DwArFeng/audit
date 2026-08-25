package com.dwarfeng.audit.sdk.bean.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.audit.stack.bean.dto.AuditRecordInfo;
import com.dwarfeng.subgrade.sdk.bean.key.WebInputStringIdKey;
import com.dwarfeng.subgrade.stack.bean.Bean;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Map;
import java.util.Objects;

/**
 * WebInput 审计记录信息。
 *
 * @author DwArFeng
 * @since 1.0.0-beta
 */
public class WebInputAuditRecordInfo implements Bean {

    private static final long serialVersionUID = 2727554637889215308L;

    public static AuditRecordInfo toStackBean(WebInputAuditRecordInfo webInputAuditRecordInfo) {
        if (Objects.isNull(webInputAuditRecordInfo)) {
            return null;
        } else {
            return new AuditRecordInfo(
                    WebInputStringIdKey.toStackBean(webInputAuditRecordInfo.getCategoryKey()),
                    webInputAuditRecordInfo.getProperties()
            );
        }
    }

    @JSONField(name = "categoryKey")
    @Valid
    @NotNull
    private WebInputStringIdKey categoryKey;

    @JSONField(name = "properties")
    @NotNull
    private Map<String, Object> properties;

    public WebInputAuditRecordInfo() {
    }

    public WebInputStringIdKey getCategoryKey() {
        return categoryKey;
    }

    public void setCategoryKey(WebInputStringIdKey categoryKey) {
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
        return "WebInputAuditRecordInfo{" +
                "categoryKey=" + categoryKey +
                ", properties=" + properties +
                '}';
    }
}
