package com.dwarfeng.audit.stack.dao;

import com.dwarfeng.audit.stack.bean.entity.InspectionTask;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.dao.BatchBaseDao;
import com.dwarfeng.subgrade.stack.dao.EntireLookupDao;
import com.dwarfeng.subgrade.stack.dao.PresetLookupDao;

/**
 * 自动审计任务数据访问层。
 *
 * @author DwArFeng
 * @since 1.1.0
 */
public interface InspectionTaskDao extends BatchBaseDao<LongIdKey, InspectionTask>, EntireLookupDao<InspectionTask>,
        PresetLookupDao<InspectionTask> {
}
