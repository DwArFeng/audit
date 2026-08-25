package com.dwarfeng.audit.stack.service;

import com.dwarfeng.audit.stack.bean.entity.AuditEntryProperty;
import com.dwarfeng.audit.stack.bean.key.AuditEntryPropertyKey;
import com.dwarfeng.subgrade.stack.service.BatchCrudService;
import com.dwarfeng.subgrade.stack.service.EntireLookupService;
import com.dwarfeng.subgrade.stack.service.PresetLookupService;

/**
 * 审计条目属性维护服务。
 *
 * @author DwArFeng
 * @since 1.0.0-beta
 */
public interface AuditEntryPropertyMaintainService extends BatchCrudService<AuditEntryPropertyKey, AuditEntryProperty>,
        EntireLookupService<AuditEntryProperty>, PresetLookupService<AuditEntryProperty> {

    // region 预设查询 - 级联

    String CHILD_FOR_AUDIT_ENTRY = "child_for_audit_entry";

    // endregion
}
