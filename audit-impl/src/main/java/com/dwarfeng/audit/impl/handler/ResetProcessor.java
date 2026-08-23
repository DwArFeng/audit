package com.dwarfeng.audit.impl.handler;

import com.dwarfeng.audit.stack.handler.AuditRecordHandler;
import com.dwarfeng.audit.stack.handler.AuditRecordLocalCacheHandler;
import com.dwarfeng.audit.stack.handler.PushHandler;
import com.dwarfeng.subgrade.sdk.exception.HandlerExceptionHelper;
import com.dwarfeng.subgrade.stack.exception.HandlerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 重置处理器。
 *
 * <p>
 * 该处理器将审计记录功能的停止、缓存清理和状态恢复操作串行化，确保不同触发入口执行相同的重置流程。
 *
 * @author DwArFeng
 * @since 1.0.0-beta
 */
@Component
public class ResetProcessor {

    private static final Logger LOGGER = LoggerFactory.getLogger(ResetProcessor.class);

    private final AuditRecordHandler auditRecordHandler;
    private final AuditRecordLocalCacheHandler auditRecordLocalCacheHandler;
    private final PushHandler pushHandler;

    private final Lock lock = new ReentrantLock();

    public ResetProcessor(
            AuditRecordHandler auditRecordHandler,
            AuditRecordLocalCacheHandler auditRecordLocalCacheHandler,
            PushHandler pushHandler
    ) {
        this.auditRecordHandler = auditRecordHandler;
        this.auditRecordLocalCacheHandler = auditRecordLocalCacheHandler;
        this.pushHandler = pushHandler;
    }

    /**
     * 重置审计记录功能。
     *
     * @throws HandlerException 处理器异常。
     */
    public void resetAuditRecord() throws HandlerException {
        lock.lock();
        try {
            doResetAuditRecord();
        } catch (Exception e) {
            throw HandlerExceptionHelper.parse(e);
        } finally {
            lock.unlock();
        }
    }

    private void doResetAuditRecord() throws Exception {
        // 获取当前审计记录处理器的状态。
        boolean auditRecordStarted = auditRecordHandler.isStarted();

        // 停止审计记录处理器，以妥善处理缓存中的剩余审计记录。
        auditRecordHandler.stop();

        // 清空审计记录配置缓存，使后续记录重新加载审计类别及属性指示器。
        auditRecordLocalCacheHandler.clear();

        // 如果审计记录处理器之前已经启动，则恢复其运行状态。
        if (auditRecordStarted) {
            auditRecordHandler.start();
        }

        // 消息推送。
        try {
            pushHandler.auditRecordReset();
        } catch (Exception e) {
            LOGGER.warn("推送审核记录功能重置消息时发生异常, 本次消息将不会被推送, 异常信息如下: ", e);
        }
    }
}
