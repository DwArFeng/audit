package com.dwarfeng.audit.stack.dao;

import com.dwarfeng.audit.stack.bean.entity.AuditPropertyIndicator;
import com.dwarfeng.audit.stack.bean.key.AuditPropertyIndicatorKey;
import com.dwarfeng.subgrade.stack.dao.BatchBaseDao;
import com.dwarfeng.subgrade.stack.dao.EntireLookupDao;
import com.dwarfeng.subgrade.stack.dao.PresetLookupDao;

/**
 * 审计属性指示器数据访问层。
 *
 * @author DwArFeng
 * @since 1.0.0-beta
 */
public interface AuditPropertyIndicatorDao extends BatchBaseDao<AuditPropertyIndicatorKey, AuditPropertyIndicator>,
        EntireLookupDao<AuditPropertyIndicator>, PresetLookupDao<AuditPropertyIndicator> {
}
