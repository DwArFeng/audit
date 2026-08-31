package com.dwarfeng.audit.api.integration.subgrade;

import com.dwarfeng.audit.stack.bean.dto.AuditRecordInfo;
import com.dwarfeng.audit.stack.service.AuditRecordService;
import com.dwarfeng.subgrade.sdk.exception.HandlerExceptionHelper;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.exception.HandlerException;
import com.dwarfeng.subgrade.stack.handler.AuditRecordHandler;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 审计记录处理器的实现。
 *
 * @author DwArFeng
 * @since 1.0.0-beta
 */
@Component
public class AuditRecordHandlerImpl implements AuditRecordHandler {

    private final AuditRecordService auditRecordService;

    public AuditRecordHandlerImpl(AuditRecordService auditRecordService) {
        this.auditRecordService = auditRecordService;
    }

    /**
     * 记录审计信息。
     *
     * @param categoryId 审计类别 ID。
     * @param properties 审计属性组成的映射。
     * @throws HandlerException 处理器异常。
     */
    @Override
    public void record(String categoryId, Map<String, Object> properties) throws HandlerException {
        try {
            auditRecordService.record(new AuditRecordInfo(new StringIdKey(categoryId), properties));
        } catch (Exception e) {
            throw HandlerExceptionHelper.parse(e);
        }
    }
}
