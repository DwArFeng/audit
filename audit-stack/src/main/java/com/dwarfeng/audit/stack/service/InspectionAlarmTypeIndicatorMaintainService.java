package com.dwarfeng.audit.stack.service;

import com.dwarfeng.audit.stack.bean.entity.InspectionAlarmTypeIndicator;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.service.BatchCrudService;
import com.dwarfeng.subgrade.stack.service.EntireLookupService;
import com.dwarfeng.subgrade.stack.service.PresetLookupService;

/**
 * 自动审计报警类型指示器维护服务。
 *
 * @author DwArFeng
 * @since 1.1.0
 */
public interface InspectionAlarmTypeIndicatorMaintainService extends
        BatchCrudService<StringIdKey, InspectionAlarmTypeIndicator>, EntireLookupService<InspectionAlarmTypeIndicator>,
        PresetLookupService<InspectionAlarmTypeIndicator> {
}
