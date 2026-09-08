package com.dwarfeng.audit.stack.service;

import com.dwarfeng.audit.stack.bean.entity.InspectionTask;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.service.BatchCrudService;
import com.dwarfeng.subgrade.stack.service.EntireLookupService;
import com.dwarfeng.subgrade.stack.service.PresetLookupService;

/**
 * 自动审计任务维护服务。
 *
 * @author DwArFeng
 * @since 1.1.0
 */
public interface InspectionTaskMaintainService extends BatchCrudService<LongIdKey, InspectionTask>,
        EntireLookupService<InspectionTask>, PresetLookupService<InspectionTask> {

    // region 预设查询 - 级联

    String CHILD_FOR_INSPECTION = "child_for_inspection";

    // endregion

    // region 预设查询 - 业务逻辑

    /**
     * @since 1.1.0
     */
    String SHOULD_EXPIRE = "should_expire";

    /**
     * @since 1.1.0
     */
    String SHOULD_DIE = "should_die";

    /**
     * @since 1.1.0
     */
    String TO_PURGED = "to_purged";

    // endregion
}
