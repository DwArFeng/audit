package com.dwarfeng.audit.stack.dao;

import com.dwarfeng.audit.stack.bean.entity.InspectionAlarmTypeIndicator;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.dao.BatchBaseDao;
import com.dwarfeng.subgrade.stack.dao.EntireLookupDao;
import com.dwarfeng.subgrade.stack.dao.PresetLookupDao;

/**
 * 自动审计报警类型指示器数据访问层。
 *
 * @author DwArFeng
 * @since 1.1.0
 */
public interface InspectionAlarmTypeIndicatorDao extends BatchBaseDao<StringIdKey, InspectionAlarmTypeIndicator>,
        EntireLookupDao<InspectionAlarmTypeIndicator>, PresetLookupDao<InspectionAlarmTypeIndicator> {
}
