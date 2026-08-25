package com.dwarfeng.audit.stack.dao;

import com.dwarfeng.audit.stack.bean.entity.AuditEntry;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.dao.BatchBaseDao;
import com.dwarfeng.subgrade.stack.dao.EntireLookupDao;
import com.dwarfeng.subgrade.stack.dao.PresetLookupDao;

/**
 * 审计条目数据访问层。
 *
 * @author DwArFeng
 * @since 1.0.0-beta
 */
public interface AuditEntryDao extends BatchBaseDao<LongIdKey, AuditEntry>, EntireLookupDao<AuditEntry>,
        PresetLookupDao<AuditEntry> {
}
