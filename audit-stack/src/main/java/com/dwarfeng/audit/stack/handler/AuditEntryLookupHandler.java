package com.dwarfeng.audit.stack.handler;

import com.dwarfeng.audit.stack.bean.dto.AuditEntryCompositeLookupInfo;
import com.dwarfeng.audit.stack.bean.dto.AuditEntryGroupedLookupInfo;
import com.dwarfeng.audit.stack.bean.dto.AuditEntryLookupResult;
import com.dwarfeng.subgrade.stack.exception.HandlerException;
import com.dwarfeng.subgrade.stack.handler.Handler;

/**
 * 审计条目查询处理器。
 *
 * @author DwArFeng
 * @since 1.0.0-beta
 */
public interface AuditEntryLookupHandler extends Handler {

    /**
     * 执行组合查询。
     *
     * @param info 组合查询信息。
     * @return 查询结果。
     * @throws HandlerException 处理器异常。
     */
    AuditEntryLookupResult lookupComposite(AuditEntryCompositeLookupInfo info) throws HandlerException;

    /**
     * 执行分组查询。
     *
     * @param info 分组查询信息。
     * @return 查询结果。
     * @throws HandlerException 处理器异常。
     */
    AuditEntryLookupResult lookupGrouped(AuditEntryGroupedLookupInfo info) throws HandlerException;
}
