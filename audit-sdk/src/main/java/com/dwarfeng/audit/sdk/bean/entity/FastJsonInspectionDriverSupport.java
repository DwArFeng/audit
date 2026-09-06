package com.dwarfeng.audit.sdk.bean.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.audit.stack.bean.entity.InspectionDriverSupport;
import com.dwarfeng.subgrade.sdk.bean.key.FastJsonStringIdKey;
import com.dwarfeng.subgrade.stack.bean.Bean;

import java.util.Objects;

/**
 * FastJson 自动审计驱动器支持。
 *
 * @author DwArFeng
 * @since 1.1.0
 */
public class FastJsonInspectionDriverSupport implements Bean {

    private static final long serialVersionUID = 3147878262043327183L;

    public static FastJsonInspectionDriverSupport of(InspectionDriverSupport inspectionDriverSupport) {
        if (Objects.isNull(inspectionDriverSupport)) {
            return null;
        } else {
            return new FastJsonInspectionDriverSupport(
                    FastJsonStringIdKey.of(inspectionDriverSupport.getKey()),
                    inspectionDriverSupport.getLabel(),
                    inspectionDriverSupport.getDescription(),
                    inspectionDriverSupport.getExampleParam()
            );
        }
    }

    @JSONField(name = "key", ordinal = 1)
    private FastJsonStringIdKey key;

    @JSONField(name = "label", ordinal = 2)
    private String label;

    @JSONField(name = "description", ordinal = 3)
    private String description;

    @JSONField(name = "example_param", ordinal = 4)
    private String exampleParam;

    public FastJsonInspectionDriverSupport() {
    }

    public FastJsonInspectionDriverSupport(
            FastJsonStringIdKey key, String label, String description, String exampleParam
    ) {
        this.key = key;
        this.label = label;
        this.description = description;
        this.exampleParam = exampleParam;
    }

    public FastJsonStringIdKey getKey() {
        return key;
    }

    public void setKey(FastJsonStringIdKey key) {
        this.key = key;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getExampleParam() {
        return exampleParam;
    }

    public void setExampleParam(String exampleParam) {
        this.exampleParam = exampleParam;
    }

    @Override
    public String toString() {
        return "FastJsonInspectionDriverSupport{" +
                "key=" + key +
                ", label='" + label + '\'' +
                ", description='" + description + '\'' +
                ", exampleParam='" + exampleParam + '\'' +
                '}';
    }
}
