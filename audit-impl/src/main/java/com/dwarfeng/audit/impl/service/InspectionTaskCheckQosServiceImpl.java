package com.dwarfeng.audit.impl.service;

import com.dwarfeng.audit.stack.handler.InspectionTaskCheckHandler;
import com.dwarfeng.audit.stack.service.InspectionTaskCheckQosService;
import com.dwarfeng.subgrade.sdk.exception.ServiceExceptionHelper;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.dwarfeng.subgrade.stack.exception.ServiceExceptionMapper;
import com.dwarfeng.subgrade.stack.log.LogLevel;
import org.springframework.stereotype.Service;

/**
 * 自动审计任务检查 QoS 服务实现。
 *
 * @author DwArFeng
 * @since 1.1.0
 */
@Service
public class InspectionTaskCheckQosServiceImpl implements InspectionTaskCheckQosService {

    private final InspectionTaskCheckHandler inspectionTaskCheckHandler;
    private final ServiceExceptionMapper sem;

    public InspectionTaskCheckQosServiceImpl(
            InspectionTaskCheckHandler inspectionTaskCheckHandler,
            ServiceExceptionMapper sem
    ) {
        this.inspectionTaskCheckHandler = inspectionTaskCheckHandler;
        this.sem = sem;
    }

    @Override
    public boolean isOnline() throws ServiceException {
        try {
            return inspectionTaskCheckHandler.isOnline();
        } catch (Exception e) {
            throw ServiceExceptionHelper.logParse("判断自动审计任务检查处理器是否上线时发生异常", LogLevel.WARN, e, sem);
        }
    }

    @Override
    public void online() throws ServiceException {
        try {
            inspectionTaskCheckHandler.online();
        } catch (Exception e) {
            throw ServiceExceptionHelper.logParse("上线自动审计任务检查处理器时发生异常", LogLevel.WARN, e, sem);
        }
    }

    @Override
    public void offline() throws ServiceException {
        try {
            inspectionTaskCheckHandler.offline();
        } catch (Exception e) {
            throw ServiceExceptionHelper.logParse("下线自动审计任务检查处理器时发生异常", LogLevel.WARN, e, sem);
        }
    }

    @Override
    public boolean isLockHolding() throws ServiceException {
        try {
            return inspectionTaskCheckHandler.isLockHolding();
        } catch (Exception e) {
            throw ServiceExceptionHelper.logParse(
                    "判断自动审计任务检查处理器是否正在持有锁时发生异常", LogLevel.WARN, e, sem
            );
        }
    }

    @Override
    public boolean isStarted() throws ServiceException {
        try {
            return inspectionTaskCheckHandler.isStarted();
        } catch (Exception e) {
            throw ServiceExceptionHelper.logParse("判断自动审计任务检查处理器是否启动时发生异常", LogLevel.WARN, e, sem);
        }
    }

    @Override
    public void start() throws ServiceException {
        try {
            inspectionTaskCheckHandler.start();
        } catch (Exception e) {
            throw ServiceExceptionHelper.logParse("自动审计任务检查处理器启动时发生异常", LogLevel.WARN, e, sem);
        }
    }

    @Override
    public void stop() throws ServiceException {
        try {
            inspectionTaskCheckHandler.stop();
        } catch (Exception e) {
            throw ServiceExceptionHelper.logParse("自动审计任务检查处理器停止时发生异常", LogLevel.WARN, e, sem);
        }
    }

    @Override
    public boolean isWorking() throws ServiceException {
        try {
            return inspectionTaskCheckHandler.isWorking();
        } catch (Exception e) {
            throw ServiceExceptionHelper.logParse("判断自动审计任务检查处理器是否正在工作时发生异常", LogLevel.WARN, e, sem);
        }
    }
}
