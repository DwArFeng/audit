package com.dwarfeng.audit.stack.dao;

import com.dwarfeng.audit.stack.bean.entity.InspectionTaskEvent;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.dao.BatchBaseDao;
import com.dwarfeng.subgrade.stack.dao.EntireLookupDao;
import com.dwarfeng.subgrade.stack.dao.PresetLookupDao;

/**
 * 自动审计任务事件数据访问层。
 *
 * @author DwArFeng
 * @since 1.1.0
 */
public interface InspectionTaskEventDao extends BatchBaseDao<LongIdKey, InspectionTaskEvent>,
        EntireLookupDao<InspectionTaskEvent>, PresetLookupDao<InspectionTaskEvent> {
}
