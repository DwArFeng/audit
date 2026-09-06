package com.dwarfeng.audit.stack.service;

import com.dwarfeng.audit.stack.bean.entity.InspectionDriverInfo;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.service.BatchCrudService;
import com.dwarfeng.subgrade.stack.service.EntireLookupService;
import com.dwarfeng.subgrade.stack.service.PresetLookupService;

/**
 * 自动审计驱动器信息维护服务。
 *
 * @author DwArFeng
 * @since 1.1.0
 */
public interface InspectionDriverInfoMaintainService extends BatchCrudService<LongIdKey, InspectionDriverInfo>,
        EntireLookupService<InspectionDriverInfo>, PresetLookupService<InspectionDriverInfo> {

    // region 预设查询 - 级联

    String CHILD_FOR_INSPECTION = "child_for_inspection";

    // endregion
}
