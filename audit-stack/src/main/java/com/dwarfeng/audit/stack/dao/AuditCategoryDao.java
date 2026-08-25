package com.dwarfeng.audit.stack.dao;

import com.dwarfeng.audit.stack.bean.entity.AuditCategory;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.dao.BatchBaseDao;
import com.dwarfeng.subgrade.stack.dao.EntireLookupDao;
import com.dwarfeng.subgrade.stack.dao.PresetLookupDao;

/**
 * 审计类别数据访问层。
 *
 * @author DwArFeng
 * @since 1.0.0-beta
 */
public interface AuditCategoryDao extends BatchBaseDao<StringIdKey, AuditCategory>, EntireLookupDao<AuditCategory>,
        PresetLookupDao<AuditCategory> {
}
