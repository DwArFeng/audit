package com.dwarfeng.audit.stack.dao;

import com.dwarfeng.audit.stack.bean.entity.InspectionDriverInfo;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.dao.BatchBaseDao;
import com.dwarfeng.subgrade.stack.dao.EntireLookupDao;
import com.dwarfeng.subgrade.stack.dao.PresetLookupDao;

/**
 * 自动审计驱动器信息数据访问层。
 *
 * @author DwArFeng
 * @since 1.1.0
 */
public interface InspectionDriverInfoDao extends BatchBaseDao<LongIdKey, InspectionDriverInfo>,
        EntireLookupDao<InspectionDriverInfo>, PresetLookupDao<InspectionDriverInfo> {
}
