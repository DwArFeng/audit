package com.dwarfeng.audit.stack.service;

import com.dwarfeng.audit.stack.bean.entity.InspectorVariable;
import com.dwarfeng.audit.stack.bean.key.InspectorVariableKey;
import com.dwarfeng.subgrade.stack.service.BatchCrudService;
import com.dwarfeng.subgrade.stack.service.EntireLookupService;
import com.dwarfeng.subgrade.stack.service.PresetLookupService;

/**
 * 审计器变量维护服务。
 *
 * @author DwArFeng
 * @since 1.1.0
 */
public interface InspectorVariableMaintainService extends BatchCrudService<InspectorVariableKey, InspectorVariable>,
        EntireLookupService<InspectorVariable>, PresetLookupService<InspectorVariable> {

    // region 预设查询 - 级联

    String CHILD_FOR_INSPECTOR_INFO = "child_for_inspector_info";

    // endregion
}
