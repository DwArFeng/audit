package com.dwarfeng.audit.impl.service;

import com.dwarfeng.audit.stack.handler.ResetHandler;
import com.dwarfeng.audit.stack.handler.Resetter;
import com.dwarfeng.audit.stack.handler.ResetterHandler;
import com.dwarfeng.audit.stack.service.ResetQosService;
import com.dwarfeng.subgrade.sdk.exception.ServiceExceptionHelper;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.dwarfeng.subgrade.stack.exception.ServiceExceptionMapper;
import com.dwarfeng.subgrade.stack.log.LogLevel;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 重置 QOS 服务实现。
 *
 * @author DwArFeng
 * @since 1.0.0-beta
 */
@Service
public class ResetQosServiceImpl implements ResetQosService {

    private final ResetterHandler resetterHandler;
    private final ResetHandler resetHandler;

    private final ServiceExceptionMapper sem;

    public ResetQosServiceImpl(
            ResetterHandler resetterHandler,
            ResetHandler resetHandler,
            ServiceExceptionMapper sem
    ) {
        this.resetterHandler = resetterHandler;
        this.resetHandler = resetHandler;
        this.sem = sem;
    }

    @Override
    public List<Resetter> all() throws ServiceException {
        try {
            return resetterHandler.all();
        } catch (Exception e) {
            throw ServiceExceptionHelper.logParse("列出在用的全部重置器时发生异常", LogLevel.WARN, e, sem);
        }
    }

    @Override
    public boolean isStarted() throws ServiceException {
        try {
            return resetHandler.isStarted();
        } catch (Exception e) {
            throw ServiceExceptionHelper.logParse("判断重置服务是否启动时发生异常", LogLevel.WARN, e, sem);
        }
    }

    @Override
    public void start() throws ServiceException {
        try {
            resetHandler.start();
        } catch (Exception e) {
            throw ServiceExceptionHelper.logParse("重置服务启动时发生异常", LogLevel.WARN, e, sem);
        }
    }

    @Override
    public void stop() throws ServiceException {
        try {
            resetHandler.stop();
        } catch (Exception e) {
            throw ServiceExceptionHelper.logParse("重置服务停止时发生异常", LogLevel.WARN, e, sem);
        }
    }

    @Override
    public void resetAuditRecord() throws ServiceException {
        try {
            resetHandler.resetAuditRecord();
        } catch (Exception e) {
            throw ServiceExceptionHelper.logParse("重置审计记录功能时发生异常", LogLevel.WARN, e, sem);
        }
    }

    @Override
    public void resetInspectionJob() throws ServiceException {
        try {
            resetHandler.resetInspectionJob();
        } catch (Exception e) {
            throw ServiceExceptionHelper.logParse("重置自动审计作业功能时发生异常", LogLevel.WARN, e, sem);
        }
    }
}
