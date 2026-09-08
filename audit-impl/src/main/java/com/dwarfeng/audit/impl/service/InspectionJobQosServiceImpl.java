package com.dwarfeng.audit.impl.service;

import com.dwarfeng.audit.stack.bean.dto.InspectionJobCreateInfo;
import com.dwarfeng.audit.stack.bean.dto.InspectionJobCreateResult;
import com.dwarfeng.audit.stack.bean.dto.InspectionJobExecuteInfo;
import com.dwarfeng.audit.stack.handler.InspectionJobHandler;
import com.dwarfeng.audit.stack.handler.InspectionJobLocalCacheHandler;
import com.dwarfeng.audit.stack.service.InspectionJobQosService;
import com.dwarfeng.audit.stack.struct.InspectionJobLocalCache;
import com.dwarfeng.subgrade.sdk.exception.ServiceExceptionHelper;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.dwarfeng.subgrade.stack.exception.ServiceExceptionMapper;
import com.dwarfeng.subgrade.stack.log.LogLevel;
import org.springframework.stereotype.Service;

/**
 * 自动审计作业 QoS 服务实现。
 *
 * @author DwArFeng
 * @since 1.1.0
 */
@Service
public class InspectionJobQosServiceImpl implements InspectionJobQosService {

    private final InspectionJobLocalCacheHandler inspectionJobLocalCacheHandler;
    private final InspectionJobHandler inspectionJobHandler;
    private final ServiceExceptionMapper sem;

    public InspectionJobQosServiceImpl(
            InspectionJobLocalCacheHandler inspectionJobLocalCacheHandler,
            InspectionJobHandler inspectionJobHandler,
            ServiceExceptionMapper sem
    ) {
        this.inspectionJobLocalCacheHandler = inspectionJobLocalCacheHandler;
        this.inspectionJobHandler = inspectionJobHandler;
        this.sem = sem;
    }

    @Override
    public InspectionJobLocalCache getInspectionJobLocalCache(LongIdKey inspectionKey) throws ServiceException {
        try {
            return inspectionJobLocalCacheHandler.get(inspectionKey);
        } catch (Exception e) {
            throw ServiceExceptionHelper.logParse("获取指定自动审计的作业本地缓存时发生异常", LogLevel.WARN, e, sem);
        }
    }

    @Override
    public void clearLocalCache() throws ServiceException {
        try {
            inspectionJobLocalCacheHandler.clear();
        } catch (Exception e) {
            throw ServiceExceptionHelper.logParse("清除自动审计作业本地缓存时发生异常", LogLevel.WARN, e, sem);
        }
    }

    @Override
    public void execute(LongIdKey inspectionKey) throws ServiceException {
        try {
            InspectionJobCreateResult result = inspectionJobHandler.create(
                    new InspectionJobCreateInfo(inspectionKey)
            );
            inspectionJobHandler.execute(new InspectionJobExecuteInfo(result.getInspectionTaskKey()));
        } catch (Exception e) {
            throw ServiceExceptionHelper.logParse("手动创建并执行指定自动审计作业时发生异常", LogLevel.WARN, e, sem);
        }
    }
}
