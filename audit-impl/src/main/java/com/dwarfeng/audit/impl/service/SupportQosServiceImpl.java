package com.dwarfeng.audit.impl.service;

import com.dwarfeng.audit.stack.handler.SupportHandler;
import com.dwarfeng.audit.stack.service.SupportQosService;
import com.dwarfeng.subgrade.sdk.exception.ServiceExceptionHelper;
import com.dwarfeng.subgrade.stack.exception.HandlerException;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.dwarfeng.subgrade.stack.exception.ServiceExceptionMapper;
import com.dwarfeng.subgrade.stack.log.LogLevel;
import org.springframework.stereotype.Service;

/**
 * 支持 QoS 服务实现。
 *
 * @author DwArFeng
 * @since 1.1.0
 */
@Service
public class SupportQosServiceImpl implements SupportQosService {

    private final SupportHandler supportHandler;

    private final ServiceExceptionMapper sem;

    public SupportQosServiceImpl(SupportHandler supportHandler, ServiceExceptionMapper sem) {
        this.supportHandler = supportHandler;
        this.sem = sem;
    }

    @Override
    public void resetInspectionDriver() throws ServiceException {
        try {
            supportHandler.resetInspectionDriver();
        } catch (HandlerException e) {
            throw ServiceExceptionHelper.logParse("重置自动审计驱动器时发生异常", LogLevel.WARN, e, sem);
        }
    }

    @Override
    public void resetInspector() throws ServiceException {
        try {
            supportHandler.resetInspector();
        } catch (HandlerException e) {
            throw ServiceExceptionHelper.logParse("重置审计器时发生异常", LogLevel.WARN, e, sem);
        }
    }
}
