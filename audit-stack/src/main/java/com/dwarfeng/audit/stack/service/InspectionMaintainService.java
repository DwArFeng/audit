package com.dwarfeng.audit.stack.service;

import com.dwarfeng.audit.stack.bean.entity.Inspection;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.service.BatchCrudService;
import com.dwarfeng.subgrade.stack.service.EntireLookupService;
import com.dwarfeng.subgrade.stack.service.PresetLookupService;

/**
 * 自动审计维护服务。
 *
 * @author DwArFeng
 * @since 1.1.0
 */
public interface InspectionMaintainService extends BatchCrudService<LongIdKey, Inspection>,
        EntireLookupService<Inspection>, PresetLookupService<Inspection> {
}
