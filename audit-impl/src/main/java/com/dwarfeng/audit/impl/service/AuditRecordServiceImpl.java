package com.dwarfeng.audit.impl.service;

import com.dwarfeng.audit.stack.bean.dto.AuditRecordInfo;
import com.dwarfeng.audit.stack.handler.AuditRecordHandler;
import com.dwarfeng.audit.stack.service.AuditRecordService;
import com.dwarfeng.subgrade.sdk.exception.ServiceExceptionHelper;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.dwarfeng.subgrade.stack.exception.ServiceExceptionMapper;
import com.dwarfeng.subgrade.stack.log.LogLevel;
import org.springframework.stereotype.Component;

/**
 * 审计记录服务实现。
 */
@Component
public class AuditRecordServiceImpl implements AuditRecordService {

    private final AuditRecordHandler auditRecordHandler;
    private final ServiceExceptionMapper sem;

    public AuditRecordServiceImpl(AuditRecordHandler auditRecordHandler, ServiceExceptionMapper sem) {
        this.auditRecordHandler = auditRecordHandler;
        this.sem = sem;
    }

    @Override
    public void record(AuditRecordInfo auditRecordInfo) throws ServiceException {
        try {
            auditRecordHandler.record(auditRecordInfo);
        } catch (Exception e) {
            throw ServiceExceptionHelper.logParse("记录数据时发生异常", LogLevel.WARN, e, sem);
        }
    }
}
