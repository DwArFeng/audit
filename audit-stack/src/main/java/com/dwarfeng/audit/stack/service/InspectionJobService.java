package com.dwarfeng.audit.stack.service;

import com.dwarfeng.audit.stack.bean.dto.InspectionJobExecuteInfo;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.dwarfeng.subgrade.stack.service.Service;

/**
 * 自动审计作业服务。
 *
 * @author DwArFeng
 * @since 1.1.0
 */
public interface InspectionJobService extends Service {

    /**
     * 执行自动审计作业。
     *
     * @param info 自动审计作业执行信息。
     * @throws ServiceException 服务异常。
     */
    void execute(InspectionJobExecuteInfo info) throws ServiceException;
}
