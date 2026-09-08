package com.dwarfeng.audit.sdk.handler.pusher;

import com.dwarfeng.audit.stack.bean.entity.InspectionAlarm;
import com.dwarfeng.audit.stack.bean.entity.InspectionTask;
import com.dwarfeng.subgrade.stack.exception.HandlerException;

/**
 * 推送器适配器。
 *
 * <p>
 * 该类对所有事件推送方法提供空实现。插件实现推送器时建议继承该类，只重写真正需要处理的事件；当推送接口增加新事件时，
 * 旧插件仍可保持兼容。
 *
 * @author DwArFeng
 * @since 1.0.0-beta
 */
public abstract class PusherAdapter extends AbstractPusher {

    public PusherAdapter() {
        super();
    }

    public PusherAdapter(String pusherType) {
        super(pusherType);
    }

    @SuppressWarnings("RedundantThrows")
    @Override
    public void auditRecordReset() throws HandlerException {
    }

    @SuppressWarnings("RedundantThrows")
    @Override
    public void inspectionTaskFinished(InspectionTask inspectionTask) throws HandlerException {
    }

    @SuppressWarnings("RedundantThrows")
    @Override
    public void inspectionTaskFailed(InspectionTask inspectionTask) throws HandlerException {
    }

    @SuppressWarnings("RedundantThrows")
    @Override
    public void inspectionTaskExpired(InspectionTask inspectionTask) throws HandlerException {
    }

    @SuppressWarnings("RedundantThrows")
    @Override
    public void inspectionTaskDied(InspectionTask inspectionTask) throws HandlerException {
    }

    @SuppressWarnings("RedundantThrows")
    @Override
    public void inspectionJobReset() throws HandlerException {
    }

    @SuppressWarnings("RedundantThrows")
    @Override
    public void inspectionAlarmCreated(InspectionAlarm inspectionAlarm) throws HandlerException {
    }

    @Override
    public String toString() {
        return "PusherAdapter{" +
                "pusherType='" + pusherType + '\'' +
                '}';
    }
}
