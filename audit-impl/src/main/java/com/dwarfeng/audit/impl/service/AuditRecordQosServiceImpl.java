package com.dwarfeng.audit.impl.service;

import com.dwarfeng.audit.stack.bean.dto.AuditRecordData;
import com.dwarfeng.audit.stack.bean.dto.AuditRecordInfo;
import com.dwarfeng.audit.stack.handler.AuditRecordHandler;
import com.dwarfeng.audit.stack.handler.AuditRecordLocalCacheHandler;
import com.dwarfeng.audit.stack.handler.ConsumeHandler;
import com.dwarfeng.audit.stack.service.AuditRecordQosService;
import com.dwarfeng.audit.stack.struct.AuditRecordLocalCache;
import com.dwarfeng.subgrade.sdk.exception.ServiceExceptionHelper;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.dwarfeng.subgrade.stack.exception.ServiceExceptionMapper;
import com.dwarfeng.subgrade.stack.log.LogLevel;
import org.springframework.stereotype.Component;

@Component
public class AuditRecordQosServiceImpl implements AuditRecordQosService {

    private final AuditRecordHandler auditRecordHandler;
    private final AuditRecordLocalCacheHandler auditRecordLocalCacheHandler;
    private final ConsumeHandler<AuditRecordData> consumeHandler;

    private final ServiceExceptionMapper sem;

    public AuditRecordQosServiceImpl(
            AuditRecordHandler auditRecordHandler,
            AuditRecordLocalCacheHandler auditRecordLocalCacheHandler,
            ConsumeHandler<AuditRecordData> consumeHandler,
            ServiceExceptionMapper sem
    ) {
        this.auditRecordHandler = auditRecordHandler;
        this.auditRecordLocalCacheHandler = auditRecordLocalCacheHandler;
        this.consumeHandler = consumeHandler;
        this.sem = sem;
    }

    @Override
    public AuditRecordLocalCache getAuditRecordLocalCache(StringIdKey categoryKey) throws ServiceException {
        try {
            return auditRecordLocalCacheHandler.get(categoryKey);
        } catch (Exception e) {
            throw ServiceExceptionHelper.logParse("从本地缓存中获取审计记录本地缓存时发生异常", LogLevel.WARN, e, sem);
        }
    }

    @Override
    public void clearAuditRecordLocalCache() throws ServiceException {
        try {
            auditRecordLocalCacheHandler.clear();
        } catch (Exception e) {
            throw ServiceExceptionHelper.logParse("清除审计记录本地缓存时发生异常", LogLevel.WARN, e, sem);
        }
    }

    @Override
    public boolean isLogicStarted() throws ServiceException {
        try {
            return auditRecordHandler.isStarted();
        } catch (Exception e) {
            throw ServiceExceptionHelper.logParse("获取逻辑侧记录服务是否已经开始时发生异常", LogLevel.WARN, e, sem);
        }
    }

    @Override
    public void logicStart() throws ServiceException {
        try {
            auditRecordHandler.start();
        } catch (Exception e) {
            throw ServiceExceptionHelper.logParse("开启逻辑侧记录服务时发生异常", LogLevel.WARN, e, sem);
        }
    }

    @Override
    public void logicStop() throws ServiceException {
        try {
            auditRecordHandler.stop();
        } catch (Exception e) {
            throw ServiceExceptionHelper.logParse("关闭逻辑侧记录服务时发生异常", LogLevel.WARN, e, sem);
        }
    }

    @Override
    public void record(AuditRecordInfo auditRecordInfo) throws ServiceException {
        try {
            auditRecordHandler.record(auditRecordInfo);
        } catch (Exception e) {
            throw ServiceExceptionHelper.logParse("记录数据时发生异常", LogLevel.WARN, e, sem);
        }
    }

    @Override
    public LogicStatus getLogicStatus() throws ServiceException {
        try {
            return new LogicStatus(
                    auditRecordHandler.bufferedSize(), auditRecordHandler.getBufferSize(),
                    auditRecordHandler.getThread(), auditRecordHandler.isIdle()
            );
        } catch (Exception e) {
            throw ServiceExceptionHelper.logParse("获取逻辑侧消费者状态时发生异常", LogLevel.WARN, e, sem);
        }
    }

    @Override
    public void setLogicParameters(Integer bufferSize, Integer thread) throws ServiceException {
        try {
            auditRecordHandler.setBufferSize(bufferSize == null ? auditRecordHandler.getBufferSize() : bufferSize);
            auditRecordHandler.setThread(thread == null ? auditRecordHandler.getThread() : thread);
        } catch (Exception e) {
            throw ServiceExceptionHelper.logParse("设置逻辑侧消费者参数时发生异常", LogLevel.WARN, e, sem);
        }
    }

    @Override
    public PersistenceStatus getPersistenceStatus() throws ServiceException {
        try {
            return new PersistenceStatus(
                    consumeHandler.bufferedSize(), consumeHandler.getBufferSize(),
                    consumeHandler.getBatchSize(), consumeHandler.getMaxIdleTime(), consumeHandler.getThread(),
                    consumeHandler.isIdle()
            );
        } catch (Exception e) {
            throw ServiceExceptionHelper.logParse("获取持久侧消费者状态时发生异常", LogLevel.WARN, e, sem);
        }
    }

    @Override
    public void setPersistenceParameters(Integer bufferSize, Integer batchSize, Long maxIdleTime, Integer thread)
            throws ServiceException {
        try {
            consumeHandler.setBufferParameters(
                    bufferSize == null ? consumeHandler.getBufferSize() : bufferSize,
                    batchSize == null ? consumeHandler.getBatchSize() : batchSize,
                    maxIdleTime == null ? consumeHandler.getMaxIdleTime() : maxIdleTime
            );
            consumeHandler.setThread(thread == null ? consumeHandler.getThread() : thread);
        } catch (Exception e) {
            throw ServiceExceptionHelper.logParse("设置持久侧消费者参数时发生异常", LogLevel.WARN, e, sem);
        }
    }
}
