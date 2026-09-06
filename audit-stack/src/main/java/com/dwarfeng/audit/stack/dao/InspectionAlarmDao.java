package com.dwarfeng.audit.stack.dao;

import com.dwarfeng.audit.stack.bean.entity.InspectionAlarm;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.dao.BatchBaseDao;
import com.dwarfeng.subgrade.stack.dao.EntireLookupDao;
import com.dwarfeng.subgrade.stack.dao.PresetLookupDao;

/**
 * 自动审计报警。自动审计报警是不可变的历史事件数据访问层。
 *
 * @author DwArFeng
 * @since 1.1.0
 */
public interface InspectionAlarmDao extends BatchBaseDao<LongIdKey, InspectionAlarm>, EntireLookupDao<InspectionAlarm>,
        PresetLookupDao<InspectionAlarm> {
}
