package com.dwarfeng.audit.stack.service;

import com.dwarfeng.audit.stack.bean.entity.InspectorInfo;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.service.BatchCrudService;
import com.dwarfeng.subgrade.stack.service.EntireLookupService;
import com.dwarfeng.subgrade.stack.service.PresetLookupService;

/**
 * 审计器信息维护服务。
 *
 * @author DwArFeng
 * @since 1.1.0
 */
public interface InspectorInfoMaintainService extends BatchCrudService<LongIdKey, InspectorInfo>,
        EntireLookupService<InspectorInfo>, PresetLookupService<InspectorInfo> {

    // region 预设查询 - 级联

    String CHILD_FOR_INSPECTION = "child_for_inspection";

    // endregion
}
