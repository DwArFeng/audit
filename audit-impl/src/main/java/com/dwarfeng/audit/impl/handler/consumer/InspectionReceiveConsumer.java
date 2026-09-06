package com.dwarfeng.audit.impl.handler.consumer;

import com.dwarfeng.audit.impl.handler.Consumer;
import com.dwarfeng.audit.stack.bean.dto.InspectionJobCreateInfo;
import com.dwarfeng.audit.stack.bean.dto.InspectionJobCreateResult;
import com.dwarfeng.audit.stack.bean.dto.InspectionJobExecuteInfo;
import com.dwarfeng.audit.stack.handler.InspectionJobHandler;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Objects;

/**
 * 自动审计接收消费者。
 *
 * <p>
 * 消费接收器提交的自动审计主键，先创建任务，再异步执行任务。
 *
 * @author DwArFeng
 * @since 1.1.0
 */
public class InspectionReceiveConsumer implements Consumer<LongIdKey> {

    private static final Logger LOGGER = LoggerFactory.getLogger(InspectionReceiveConsumer.class);

    private final InspectionJobHandler inspectionJobHandler;

    public InspectionReceiveConsumer(InspectionJobHandler inspectionJobHandler) {
        this.inspectionJobHandler = inspectionJobHandler;
    }

    @Override
    public void consume(List<LongIdKey> inspectionKeys) {
        if (Objects.isNull(inspectionKeys) || inspectionKeys.isEmpty()) {
            return;
        }
        for (LongIdKey inspectionKey : inspectionKeys) {
            try {
                InspectionJobCreateResult createResult = inspectionJobHandler.create(
                        new InspectionJobCreateInfo(inspectionKey)
                );
                inspectionJobHandler.execute(
                        new InspectionJobExecuteInfo(createResult.getInspectionTaskKey())
                );
            } catch (Exception e) {
                LOGGER.error("创建或执行自动审计任务失败，审计主键: {}", inspectionKey, e);
            }
        }
    }
}
