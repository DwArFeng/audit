package com.dwarfeng.audit.stack.service;

import com.dwarfeng.audit.stack.bean.entity.InspectionAlarm;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.service.BatchCrudService;
import com.dwarfeng.subgrade.stack.service.EntireLookupService;
import com.dwarfeng.subgrade.stack.service.PresetLookupService;

/**
 * 自动审计报警。自动审计报警是不可变的历史事件维护服务。
 *
 * @author DwArFeng
 * @since 1.1.0
 */
public interface InspectionAlarmMaintainService extends BatchCrudService<LongIdKey, InspectionAlarm>,
        EntireLookupService<InspectionAlarm>, PresetLookupService<InspectionAlarm> {

    // region 预设查询 - 级联

    String CHILD_FOR_INSPECTION = "child_for_inspection";
    String CHILD_FOR_INSPECTION_TASK = "child_for_inspection_task";
    String CHILD_FOR_INSPECTOR_INFO = "child_for_inspector_info";

    // endregion

    // region 预设查询 - UI

    /**
     * @since 1.3.0
     */
    String HAPPENED_DATE_DESC = "happened_date_desc";

    /**
     * @since 1.3.0
     */
    String CHILD_FOR_INSPECTION_HAPPENED_DATE_DESC = "child_for_inspection_happened_date_desc";

    /**
     * @since 1.3.0
     */
    String CHILD_FOR_INSPECTION_TASK_HAPPENED_DATE_DESC = "child_for_inspection_task_happened_date_desc";

    /**
     * @since 1.3.0
     */
    String CHILD_FOR_INSPECTOR_INFO_HAPPENED_DATE_DESC = "child_for_inspector_info_happened_date_desc";

    // endregion
}
