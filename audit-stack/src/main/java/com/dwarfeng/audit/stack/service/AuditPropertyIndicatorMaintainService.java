package com.dwarfeng.audit.stack.service;

import com.dwarfeng.audit.stack.bean.entity.AuditPropertyIndicator;
import com.dwarfeng.audit.stack.bean.key.AuditPropertyIndicatorKey;
import com.dwarfeng.subgrade.stack.service.BatchCrudService;
import com.dwarfeng.subgrade.stack.service.EntireLookupService;
import com.dwarfeng.subgrade.stack.service.PresetLookupService;

/**
 * 审计属性指示器维护服务。
 *
 * @author DwArFeng
 * @since 1.0.0-beta
 */
public interface AuditPropertyIndicatorMaintainService extends
        BatchCrudService<AuditPropertyIndicatorKey, AuditPropertyIndicator>,
        EntireLookupService<AuditPropertyIndicator>, PresetLookupService<AuditPropertyIndicator> {

    // region 预设查询 - 级联

    String CHILD_FOR_AUDIT_CATEGORY = "child_for_audit_category";

    // endregion
}
