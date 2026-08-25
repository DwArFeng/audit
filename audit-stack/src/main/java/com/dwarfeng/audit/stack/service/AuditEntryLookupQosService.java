package com.dwarfeng.audit.stack.service;

import com.dwarfeng.audit.stack.bean.dto.AuditEntryCompositeLookupInfo;
import com.dwarfeng.audit.stack.bean.dto.AuditEntryGroupedLookupInfo;
import com.dwarfeng.audit.stack.bean.dto.AuditEntryLookupResult;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.dwarfeng.subgrade.stack.service.Service;

/**
 * 审计条目查询服务质量服务。
 *
 * @author DwArFeng
 * @since 1.0.0-beta
 */
public interface AuditEntryLookupQosService extends Service {

    /**
     * 执行组合查询。
     *
     * @param info 组合查询信息。
     * @return 查询结果。
     * @throws ServiceException 服务异常。
     */
    AuditEntryLookupResult lookupComposite(AuditEntryCompositeLookupInfo info) throws ServiceException;

    /**
     * 执行分组查询。
     *
     * @param info 分组查询信息。
     * @return 查询结果。
     * @throws ServiceException 服务异常。
     */
    AuditEntryLookupResult lookupGrouped(AuditEntryGroupedLookupInfo info) throws ServiceException;
}
