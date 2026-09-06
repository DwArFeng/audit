package com.dwarfeng.audit.impl.handler;

import com.dwarfeng.audit.sdk.util.Constants;
import com.dwarfeng.audit.stack.bean.dto.InspectorVariableInspectInfo;
import com.dwarfeng.audit.stack.bean.dto.InspectorVariableInspectResult;
import com.dwarfeng.audit.stack.bean.dto.InspectorVariableRemoveInfo;
import com.dwarfeng.audit.stack.bean.dto.InspectorVariableUpsertInfo;
import com.dwarfeng.audit.stack.bean.entity.InspectorVariable;
import com.dwarfeng.audit.stack.bean.key.InspectorVariableKey;
import com.dwarfeng.audit.stack.handler.InspectorVariableOperateHandler;
import com.dwarfeng.audit.stack.service.InspectorVariableMaintainService;
import com.dwarfeng.subgrade.sdk.exception.HandlerExceptionHelper;
import com.dwarfeng.subgrade.sdk.interceptor.analyse.BehaviorAnalyse;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.exception.HandlerException;
import org.springframework.stereotype.Component;

import javax.annotation.Nullable;
import java.util.Date;
import java.util.Objects;

/**
 * 审计器变量操作处理器实现。
 *
 * @author DwArFeng
 * @since 1.1.0
 */
@Component
public class InspectorVariableOperateHandlerImpl implements InspectorVariableOperateHandler {

    private final InspectorVariableMaintainService inspectorVariableMaintainService;
    private final HandlerValidator handlerValidator;

    public InspectorVariableOperateHandlerImpl(
            InspectorVariableMaintainService inspectorVariableMaintainService,
            HandlerValidator handlerValidator
    ) {
        this.inspectorVariableMaintainService = inspectorVariableMaintainService;
        this.handlerValidator = handlerValidator;
    }

    @Nullable
    @BehaviorAnalyse
    @Override
    public InspectorVariableInspectResult inspect(InspectorVariableInspectInfo info) throws HandlerException {
        try {
            return inspect0(info);
        } catch (Exception e) {
            throw HandlerExceptionHelper.parse(e);
        }
    }

    private InspectorVariableInspectResult inspect0(InspectorVariableInspectInfo info) throws Exception {
        // 展开参数。
        LongIdKey inspectorInfoKey = info.getInspectorInfoKey();
        String inspectorVariableId = info.getInspectorVariableId();

        // 确认审计器信息存在。
        handlerValidator.makeSureInspectorInfoExists(inspectorInfoKey);

        // 调用维护服务获取审计器变量。
        InspectorVariableKey inspectorVariableKey = new InspectorVariableKey(
                inspectorInfoKey.getLongId(), inspectorVariableId
        );
        InspectorVariable inspectorVariable = inspectorVariableMaintainService.getIfExists(inspectorVariableKey);

        // 如果审计器变量不存在，则返回 null。
        if (Objects.isNull(inspectorVariable)) {
            return null;
        }

        // 构建返回值并返回。
        int valueType = inspectorVariable.getValueType();
        Object value;
        switch (valueType) {
            case Constants.INSPECTOR_VARIABLE_VALUE_TYPE_STRING:
                value = inspectorVariable.getStringValue();
                break;
            case Constants.INSPECTOR_VARIABLE_VALUE_TYPE_LONG:
                value = inspectorVariable.getLongValue();
                break;
            case Constants.INSPECTOR_VARIABLE_VALUE_TYPE_DOUBLE:
                value = inspectorVariable.getDoubleValue();
                break;
            case Constants.INSPECTOR_VARIABLE_VALUE_TYPE_BOOLEAN:
                value = inspectorVariable.getBooleanValue();
                break;
            case Constants.INSPECTOR_VARIABLE_VALUE_TYPE_DATE:
                value = inspectorVariable.getDateValue();
                break;
            default:
                throw new IllegalStateException("非法的 valueType 值: " + valueType);
        }
        return new InspectorVariableInspectResult(valueType, value);
    }

    @BehaviorAnalyse
    @Override
    public void upsert(InspectorVariableUpsertInfo info) throws HandlerException {
        try {
            upsert0(info);
        } catch (Exception e) {
            throw HandlerExceptionHelper.parse(e);
        }
    }

    private void upsert0(InspectorVariableUpsertInfo info) throws Exception {
        // 展开参数。
        LongIdKey inspectorInfoKey = info.getInspectorInfoKey();
        String inspectorVariableId = info.getInspectorVariableId();
        int valueType = info.getValueType();
        Object value = info.getValue();

        // 确认审计器信息存在，审计器变量值类型有效。
        handlerValidator.makeSureInspectorInfoExists(inspectorInfoKey);
        handlerValidator.makeSureVariableValueTypeValid(valueType, value);

        // 构建审计器变量。
        InspectorVariable inspectorVariable = new InspectorVariable(
                new InspectorVariableKey(inspectorInfoKey.getLongId(), inspectorVariableId),
                valueType, null, null, null, null, null
        );
        if (Objects.nonNull(value)) {
            switch (valueType) {
                case Constants.INSPECTOR_VARIABLE_VALUE_TYPE_STRING:
                    inspectorVariable.setStringValue((String) value);
                    break;
                case Constants.INSPECTOR_VARIABLE_VALUE_TYPE_LONG:
                    inspectorVariable.setLongValue((Long) value);
                    break;
                case Constants.INSPECTOR_VARIABLE_VALUE_TYPE_DOUBLE:
                    inspectorVariable.setDoubleValue((Double) value);
                    break;
                case Constants.INSPECTOR_VARIABLE_VALUE_TYPE_BOOLEAN:
                    inspectorVariable.setBooleanValue((Boolean) value);
                    break;
                case Constants.INSPECTOR_VARIABLE_VALUE_TYPE_DATE:
                    inspectorVariable.setDateValue((Date) value);
                    break;
                default:
                    throw new IllegalStateException("不应该执行到此处, 请联系开发人员");
            }
        }

        // 调用维护服务插入/更新审计器变量。
        inspectorVariableMaintainService.insertOrUpdate(inspectorVariable);
    }

    @BehaviorAnalyse
    @Override
    public void remove(InspectorVariableRemoveInfo info) throws HandlerException {
        try {
            remove0(info);
        } catch (Exception e) {
            throw HandlerExceptionHelper.parse(e);
        }
    }

    private void remove0(InspectorVariableRemoveInfo info) throws Exception {
        // 展开参数。
        LongIdKey inspectorInfoKey = info.getInspectorInfoKey();
        String inspectorVariableId = info.getInspectorVariableId();

        // 确认审计器信息、审计器变量存在。
        handlerValidator.makeSureInspectorInfoExists(inspectorInfoKey);
        InspectorVariableKey inspectorVariableKey = new InspectorVariableKey(
                inspectorInfoKey.getLongId(), inspectorVariableId
        );
        handlerValidator.makeSureInspectorVariableExists(inspectorVariableKey);

        // 调用维护服务删除审计器变量。
        inspectorVariableMaintainService.delete(inspectorVariableKey);
    }
}
