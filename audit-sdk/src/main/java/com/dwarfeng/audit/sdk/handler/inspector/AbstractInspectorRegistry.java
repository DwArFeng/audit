package com.dwarfeng.audit.sdk.handler.inspector;

import com.dwarfeng.audit.sdk.handler.InspectorMaker;
import com.dwarfeng.audit.sdk.handler.InspectorSupporter;

import java.util.Objects;

/**
 * 抽象审计器注册。
 *
 * @author DwArFeng
 * @since 1.1.0
 */
public abstract class AbstractInspectorRegistry implements InspectorMaker, InspectorSupporter {

    protected String inspectorType;

    public AbstractInspectorRegistry() {
    }

    public AbstractInspectorRegistry(String inspectorType) {
        this.inspectorType = inspectorType;
    }

    @Override
    public boolean supportType(String type) {
        return Objects.equals(inspectorType, type);
    }

    @Override
    public String provideType() {
        return inspectorType;
    }

    public String getInspectorType() {
        return inspectorType;
    }

    public void setInspectorType(String inspectorType) {
        this.inspectorType = inspectorType;
    }

    @Override
    public String toString() {
        return "AbstractInspectorRegistry{" +
                "inspectorType='" + inspectorType + '\'' +
                '}';
    }
}
