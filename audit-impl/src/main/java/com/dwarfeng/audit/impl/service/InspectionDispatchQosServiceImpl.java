package com.dwarfeng.audit.impl.service;

import com.dwarfeng.audit.stack.handler.InspectionDispatchHandler;
import com.dwarfeng.audit.stack.handler.InspectionDispatcher;
import com.dwarfeng.audit.stack.handler.InspectionDispatcherHandler;
import com.dwarfeng.audit.stack.service.InspectionDispatchQosService;
import com.dwarfeng.subgrade.sdk.exception.ServiceExceptionHelper;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.dwarfeng.subgrade.stack.exception.ServiceExceptionMapper;
import com.dwarfeng.subgrade.stack.log.LogLevel;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 自动审计调度 QoS 服务实现。
 *
 * @author DwArFeng
 * @since 1.1.0
 */
@Service
public class InspectionDispatchQosServiceImpl implements InspectionDispatchQosService {

    private final InspectionDispatchHandler inspectionDispatchHandler;
    private final InspectionDispatcherHandler inspectionDispatcherHandler;
    private final ServiceExceptionMapper sem;

    public InspectionDispatchQosServiceImpl(
            InspectionDispatchHandler inspectionDispatchHandler,
            InspectionDispatcherHandler inspectionDispatcherHandler,
            ServiceExceptionMapper sem
    ) {
        this.inspectionDispatchHandler = inspectionDispatchHandler;
        this.inspectionDispatcherHandler = inspectionDispatcherHandler;
        this.sem = sem;
    }

    @Override
    public boolean isStarted() throws ServiceException {
        try {
            return inspectionDispatchHandler.isStarted();
        } catch (Exception e) {
            throw ServiceExceptionHelper.logParse("判断自动审计调度服务是否启动时发生异常", LogLevel.WARN, e, sem);
        }
    }

    @Override
    public void start() throws ServiceException {
        try {
            inspectionDispatchHandler.start();
        } catch (Exception e) {
            throw ServiceExceptionHelper.logParse("启动自动审计调度服务时发生异常", LogLevel.WARN, e, sem);
        }
    }

    @Override
    public void stop() throws ServiceException {
        try {
            inspectionDispatchHandler.stop();
        } catch (Exception e) {
            throw ServiceExceptionHelper.logParse("停止自动审计调度服务时发生异常", LogLevel.WARN, e, sem);
        }
    }

    @Override
    public InspectionDispatcher currentDispatcher() throws ServiceException {
        try {
            return inspectionDispatcherHandler.current();
        } catch (Exception e) {
            throw ServiceExceptionHelper.logParse("获取当前正在使用的自动审计调度器时发生异常", LogLevel.WARN, e, sem);
        }
    }

    @Override
    public List<InspectionDispatcher> allDispatchers() throws ServiceException {
        try {
            return inspectionDispatcherHandler.all();
        } catch (Exception e) {
            throw ServiceExceptionHelper.logParse("获取全部自动审计调度器时发生异常", LogLevel.WARN, e, sem);
        }
    }
}
