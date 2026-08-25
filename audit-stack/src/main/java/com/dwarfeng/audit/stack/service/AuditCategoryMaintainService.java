package com.dwarfeng.audit.stack.service;

import com.dwarfeng.audit.stack.bean.entity.AuditCategory;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.service.BatchCrudService;
import com.dwarfeng.subgrade.stack.service.EntireLookupService;
import com.dwarfeng.subgrade.stack.service.PresetLookupService;

/**
 * 审计类别维护服务。
 *
 * @author DwArFeng
 * @since 1.0.0-beta
 */
public interface AuditCategoryMaintainService extends BatchCrudService<StringIdKey, AuditCategory>,
        EntireLookupService<AuditCategory>, PresetLookupService<AuditCategory> {
}
