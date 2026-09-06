package com.dwarfeng.audit.stack.service;

import com.dwarfeng.audit.stack.bean.entity.InspectionTaskEvent;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.service.BatchCrudService;
import com.dwarfeng.subgrade.stack.service.EntireLookupService;
import com.dwarfeng.subgrade.stack.service.PresetLookupService;

/**
 * 自动审计任务事件维护服务。
 *
 * @author DwArFeng
 * @since 1.1.0
 */
public interface InspectionTaskEventMaintainService extends BatchCrudService<LongIdKey, InspectionTaskEvent>,
        EntireLookupService<InspectionTaskEvent>, PresetLookupService<InspectionTaskEvent> {

    // region 预设查询 - 级联

    String CHILD_FOR_INSPECTION_TASK = "child_for_inspection_task";

    // endregion
}
