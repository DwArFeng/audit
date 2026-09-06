package com.dwarfeng.audit.stack.dao;

import com.dwarfeng.audit.stack.bean.entity.InspectorInfo;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.dao.BatchBaseDao;
import com.dwarfeng.subgrade.stack.dao.EntireLookupDao;
import com.dwarfeng.subgrade.stack.dao.PresetLookupDao;

/**
 * 审计器信息数据访问层。
 *
 * @author DwArFeng
 * @since 1.1.0
 */
public interface InspectorInfoDao extends BatchBaseDao<LongIdKey, InspectorInfo>, EntireLookupDao<InspectorInfo>,
        PresetLookupDao<InspectorInfo> {
}
