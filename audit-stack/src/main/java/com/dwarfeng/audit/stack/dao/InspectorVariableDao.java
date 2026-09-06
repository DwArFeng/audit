package com.dwarfeng.audit.stack.dao;

import com.dwarfeng.audit.stack.bean.entity.InspectorVariable;
import com.dwarfeng.audit.stack.bean.key.InspectorVariableKey;
import com.dwarfeng.subgrade.stack.dao.BatchBaseDao;
import com.dwarfeng.subgrade.stack.dao.EntireLookupDao;
import com.dwarfeng.subgrade.stack.dao.PresetLookupDao;

/**
 * 审计器变量数据访问层。
 *
 * @author DwArFeng
 * @since 1.1.0
 */
public interface InspectorVariableDao extends BatchBaseDao<InspectorVariableKey, InspectorVariable>,
        EntireLookupDao<InspectorVariable>, PresetLookupDao<InspectorVariable> {
}
