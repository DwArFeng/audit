package com.dwarfeng.audit.impl.service;

import com.dwarfeng.audit.stack.handler.InspectionSuperviseHandler;
import com.dwarfeng.audit.stack.service.InspectionSuperviseQosService;
import com.dwarfeng.subgrade.sdk.exception.ServiceExceptionHelper;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.dwarfeng.subgrade.stack.exception.ServiceExceptionMapper;
import com.dwarfeng.subgrade.stack.log.LogLevel;
import org.springframework.stereotype.Service;

/**
 * 自动审计主管 QoS 服务实现。
 *
 * @author DwArFeng
 * @since 1.1.0
 */
@Service
public class InspectionSuperviseQosServiceImpl implements InspectionSuperviseQosService {

    private final InspectionSuperviseHandler inspectionSuperviseHandler;
    private final ServiceExceptionMapper sem;

    public InspectionSuperviseQosServiceImpl(
            InspectionSuperviseHandler inspectionSuperviseHandler,
            ServiceExceptionMapper sem
    ) {
        this.inspectionSuperviseHandler = inspectionSuperviseHandler;
        this.sem = sem;
    }

    @Override
    public boolean isOnline() throws ServiceException {
        try {
            return inspectionSuperviseHandler.isOnline();
        } catch (Exception e) {
            throw ServiceExceptionHelper.logParse("判断自动审计主管处理器是否上线时发生异常", LogLevel.WARN, e, sem);
        }
    }

    @Override
    public void online() throws ServiceException {
        try {
            inspectionSuperviseHandler.online();
        } catch (Exception e) {
            throw ServiceExceptionHelper.logParse("上线自动审计主管处理器时发生异常", LogLevel.WARN, e, sem);
        }
    }

    @Override
    public void offline() throws ServiceException {
        try {
            inspectionSuperviseHandler.offline();
        } catch (Exception e) {
            throw ServiceExceptionHelper.logParse("下线自动审计主管处理器时发生异常", LogLevel.WARN, e, sem);
        }
    }

    @Override
    public boolean isLockHolding() throws ServiceException {
        try {
            return inspectionSuperviseHandler.isLockHolding();
        } catch (Exception e) {
            throw ServiceExceptionHelper.logParse(
                    "判断自动审计主管处理器是否正在持有锁时发生异常", LogLevel.WARN, e, sem
            );
        }
    }

    @Override
    public boolean isStarted() throws ServiceException {
        try {
            return inspectionSuperviseHandler.isStarted();
        } catch (Exception e) {
            throw ServiceExceptionHelper.logParse("判断自动审计主管处理器是否启动时发生异常", LogLevel.WARN, e, sem);
        }
    }

    @Override
    public void start() throws ServiceException {
        try {
            inspectionSuperviseHandler.start();
        } catch (Exception e) {
            throw ServiceExceptionHelper.logParse("自动审计主管处理器启动时发生异常", LogLevel.WARN, e, sem);
        }
    }

    @Override
    public void stop() throws ServiceException {
        try {
            inspectionSuperviseHandler.stop();
        } catch (Exception e) {
            throw ServiceExceptionHelper.logParse("自动审计主管处理器停止时发生异常", LogLevel.WARN, e, sem);
        }
    }

    @Override
    public boolean isWorking() throws ServiceException {
        try {
            return inspectionSuperviseHandler.isWorking();
        } catch (Exception e) {
            throw ServiceExceptionHelper.logParse(
                    "判断自动审计主管处理器是否正在工作时发生异常", LogLevel.WARN, e, sem
            );
        }
    }
}
