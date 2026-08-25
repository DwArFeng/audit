package com.dwarfeng.audit.stack.dao;

import com.dwarfeng.audit.stack.bean.entity.AuditEntryProperty;
import com.dwarfeng.audit.stack.bean.key.AuditEntryPropertyKey;
import com.dwarfeng.subgrade.stack.dao.BatchBaseDao;
import com.dwarfeng.subgrade.stack.dao.EntireLookupDao;
import com.dwarfeng.subgrade.stack.dao.PresetLookupDao;

/**
 * 审计条目属性数据访问层。
 *
 * @author DwArFeng
 * @since 1.0.0-beta
 */
public interface AuditEntryPropertyDao extends BatchBaseDao<AuditEntryPropertyKey, AuditEntryProperty>,
        EntireLookupDao<AuditEntryProperty>, PresetLookupDao<AuditEntryProperty> {
}
