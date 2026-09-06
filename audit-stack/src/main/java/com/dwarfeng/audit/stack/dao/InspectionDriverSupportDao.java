package com.dwarfeng.audit.stack.dao;

import com.dwarfeng.audit.stack.bean.entity.InspectionDriverSupport;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.dao.BatchBaseDao;
import com.dwarfeng.subgrade.stack.dao.EntireLookupDao;
import com.dwarfeng.subgrade.stack.dao.PresetLookupDao;

/**
 * 自动审计驱动器支持数据访问层。
 *
 * @author DwArFeng
 * @since 1.1.0
 */
public interface InspectionDriverSupportDao extends BatchBaseDao<StringIdKey, InspectionDriverSupport>,
        EntireLookupDao<InspectionDriverSupport>, PresetLookupDao<InspectionDriverSupport> {
}
