package com.dwarfeng.audit.stack.service;

import com.dwarfeng.audit.stack.bean.dto.AuditRecordInfo;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.dwarfeng.subgrade.stack.service.Service;

/**
 * 审计记录服务。
 *
 * @author DwArFeng
 * @since 1.0.0-beta
 */
public interface AuditRecordService extends Service {

    /**
     * 记录。
     *
     * @param auditRecordInfo 记录信息。
     * @throws ServiceException 服务异常。
     */
    void record(AuditRecordInfo auditRecordInfo) throws ServiceException;
}
