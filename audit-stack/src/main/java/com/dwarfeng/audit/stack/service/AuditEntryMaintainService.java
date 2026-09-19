package com.dwarfeng.audit.stack.service;

import com.dwarfeng.audit.stack.bean.entity.AuditEntry;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.service.BatchCrudService;
import com.dwarfeng.subgrade.stack.service.EntireLookupService;
import com.dwarfeng.subgrade.stack.service.PresetLookupService;

/**
 * 审计条目维护服务。
 *
 * @author DwArFeng
 * @since 1.0.0-beta
 */
public interface AuditEntryMaintainService extends BatchCrudService<LongIdKey, AuditEntry>,
        EntireLookupService<AuditEntry>, PresetLookupService<AuditEntry> {

    // region 预设查询 - 级联

    String CHILD_FOR_AUDIT_CATEGORY = "child_for_audit_category";

    // endregion

    // region 预设查询 - 业务逻辑

    String COMPOSITE_LOOKUP = "composite_lookup";
    String GROUPED_LOOKUP = "grouped_lookup";

    // endregion

    // region 预设查询 - UI

    /**
     * @since 1.0.0-beta
     */
    String CREATED_DATE_DESC = "created_date_desc";

    // endregion
}
