package com.dwarfeng.audit.impl.service;

import com.dwarfeng.audit.stack.bean.dto.InspectionJobExecuteInfo;
import com.dwarfeng.audit.stack.handler.InspectionJobHandler;
import com.dwarfeng.audit.stack.service.InspectionJobService;
import com.dwarfeng.subgrade.sdk.exception.ServiceExceptionHelper;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.dwarfeng.subgrade.stack.exception.ServiceExceptionMapper;
import com.dwarfeng.subgrade.stack.log.LogLevel;
import org.springframework.stereotype.Service;

/**
 * 自动审计作业服务实现。
 *
 * @author DwArFeng
 * @since 1.1.0
 */
@Service
public class InspectionJobServiceImpl implements InspectionJobService {

    private final InspectionJobHandler inspectionJobHandler;
    private final ServiceExceptionMapper sem;

    public InspectionJobServiceImpl(InspectionJobHandler inspectionJobHandler, ServiceExceptionMapper sem) {
        this.inspectionJobHandler = inspectionJobHandler;
        this.sem = sem;
    }

    @Override
    public void execute(InspectionJobExecuteInfo info) throws ServiceException {
        try {
            inspectionJobHandler.execute(info);
        } catch (Exception e) {
            throw ServiceExceptionHelper.logParse("执行自动审计作业时发生异常", LogLevel.WARN, e, sem);
        }
    }
}
