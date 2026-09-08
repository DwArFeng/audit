package com.dwarfeng.audit.impl.handler;

import com.dwarfeng.audit.sdk.handler.Pusher;
import com.dwarfeng.audit.stack.bean.entity.InspectionAlarm;
import com.dwarfeng.audit.stack.bean.entity.InspectionTask;
import com.dwarfeng.audit.stack.handler.PushHandler;
import com.dwarfeng.subgrade.stack.exception.HandlerException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * 推送处理器实现。
 *
 * <p>
 * 该实现根据配置选择一个推送器，并将所有推送事件委托给选中的推送器。推送器本身可以通过可选配置包进行替换，
 * 核心机制不需要感知具体协议。
 *
 * @author DwArFeng
 * @since 1.0.0-beta
 */
@Component
public class PushHandlerImpl implements PushHandler {

    private final List<Pusher> pushers;

    @Value("${com.dwarfeng.audit.pusher.type}")
    private String pusherType;

    private Pusher pusher;

    public PushHandlerImpl(List<Pusher> pushers) {
        this.pushers = Optional.ofNullable(pushers).orElse(Collections.emptyList());
    }

    @PostConstruct
    public void init() throws HandlerException {
        pusher = pushers.stream().filter(item -> item.supportType(pusherType)).findAny().orElseThrow(
                () -> new HandlerException("未知的推送器类型: " + pusherType)
        );
    }

    @Override
    public void auditRecordReset() throws HandlerException {
        pusher.auditRecordReset();
    }

    @Override
    public void inspectionSuperviseReset() throws HandlerException {
        pusher.inspectionSuperviseReset();
    }

    @Override
    public void inspectionTaskFinished(InspectionTask inspectionTask) throws HandlerException {
        pusher.inspectionTaskFinished(inspectionTask);
    }

    @Override
    public void inspectionTaskFailed(InspectionTask inspectionTask) throws HandlerException {
        pusher.inspectionTaskFailed(inspectionTask);
    }

    @Override
    public void inspectionTaskExpired(InspectionTask inspectionTask) throws HandlerException {
        pusher.inspectionTaskExpired(inspectionTask);
    }

    @Override
    public void inspectionTaskDied(InspectionTask inspectionTask) throws HandlerException {
        pusher.inspectionTaskDied(inspectionTask);
    }

    @Override
    public void inspectionJobReset() throws HandlerException {
        pusher.inspectionJobReset();
    }

    @Override
    public void inspectionAlarmCreated(InspectionAlarm inspectionAlarm) throws HandlerException {
        pusher.inspectionAlarmCreated(inspectionAlarm);
    }

    @Override
    public String toString() {
        return "PushHandlerImpl{" +
                "pushers=" + pushers +
                ", pusherType='" + pusherType + '\'' +
                ", pusher=" + pusher +
                '}';
    }
}
