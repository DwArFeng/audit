package com.dwarfeng.audit.stack.service;

import com.dwarfeng.audit.stack.bean.entity.InspectorSupport;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.service.BatchCrudService;
import com.dwarfeng.subgrade.stack.service.EntireLookupService;
import com.dwarfeng.subgrade.stack.service.PresetLookupService;

/**
 * 审计器支持维护服务。
 *
 * @author DwArFeng
 * @since 1.1.0
 */
public interface InspectorSupportMaintainService extends BatchCrudService<StringIdKey, InspectorSupport>,
        EntireLookupService<InspectorSupport>, PresetLookupService<InspectorSupport> {
}
