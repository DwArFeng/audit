package com.dwarfeng.audit.impl.handler;

import com.dwarfeng.audit.sdk.util.Constants;
import com.dwarfeng.audit.stack.bean.dto.*;
import com.dwarfeng.audit.stack.bean.entity.Inspection;
import com.dwarfeng.audit.stack.bean.entity.InspectionTask;
import com.dwarfeng.audit.stack.handler.InspectionTaskOperateHandler;
import com.dwarfeng.audit.stack.service.InspectionMaintainService;
import com.dwarfeng.audit.stack.service.InspectionTaskMaintainService;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Objects;

import static org.junit.Assert.*;

/**
 * {@link InspectionTaskOperateHandlerImpl} 的集成测试。
 *
 * @author DwArFeng
 * @since 1.1.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring/application-context*.xml")
public class InspectionTaskOperateHandlerImplTest {

    @Autowired
    private InspectionMaintainService inspectionMaintainService;
    @Autowired
    private InspectionTaskMaintainService inspectionTaskMaintainService;
    @Autowired
    private InspectionTaskOperateHandler inspectionTaskOperateHandler;

    @Test
    public void testCreateStartBeatUpdateModalFinish() throws Exception {
        Inspection inspection = new Inspection(null, "inspection", true, "remark");
        LongIdKey taskKey = null;
        try {
            inspection.setKey(inspectionMaintainService.insertOrUpdate(inspection));

            taskKey = inspectionTaskOperateHandler.create(new InspectionTaskCreateInfo(inspection.getKey()))
                    .getInspectionTaskKey();
            InspectionTask task = inspectionTaskMaintainService.get(taskKey);
            assertEquals(Constants.INSPECTION_TASK_STATUS_CREATED, task.getStatus());
            assertNull(task.getAnchorMessage());
            assertNotNull(task.getCreatedDate());
            assertNotNull(task.getShouldExpireDate());

            inspectionTaskOperateHandler.start(new InspectionTaskStartInfo(taskKey));
            task = inspectionTaskMaintainService.get(taskKey);
            assertEquals(Constants.INSPECTION_TASK_STATUS_PROCESSING, task.getStatus());
            assertNotNull(task.getStartedDate());
            assertNotNull(task.getShouldDieDate());

            long oldShouldDieDate = task.getShouldDieDate().getTime();
            inspectionTaskOperateHandler.beat(new InspectionTaskBeatInfo(taskKey));
            task = inspectionTaskMaintainService.get(taskKey);
            assertTrue(task.getShouldDieDate().getTime() >= oldShouldDieDate);

            inspectionTaskOperateHandler.updateModal(new InspectionTaskUpdateModalInfo(taskKey, "update modal"));
            task = inspectionTaskMaintainService.get(taskKey);
            assertEquals("update modal", task.getAnchorMessage());

            inspectionTaskOperateHandler.finish(new InspectionTaskFinishInfo(taskKey));
            task = inspectionTaskMaintainService.get(taskKey);
            assertEquals(Constants.INSPECTION_TASK_STATUS_FINISHED, task.getStatus());
            assertNotNull(task.getEndedDate());
            assertNotNull(task.getDuration());
            assertEquals("update modal", task.getAnchorMessage());
        } finally {
            if (Objects.nonNull(taskKey)) {
                inspectionTaskMaintainService.deleteIfExists(taskKey);
            }
            if (Objects.nonNull(inspection.getKey())) {
                inspectionMaintainService.deleteIfExists(inspection.getKey());
            }
        }
    }

    @Test
    public void testFailExpireAndDie() throws Exception {
        Inspection inspection = new Inspection(null, "inspection", true, "remark");
        LongIdKey failedTaskKey = null;
        LongIdKey expiredTaskKey = null;
        LongIdKey diedTaskKey = null;
        try {
            inspection.setKey(inspectionMaintainService.insertOrUpdate(inspection));

            failedTaskKey = inspectionTaskOperateHandler.create(new InspectionTaskCreateInfo(inspection.getKey()))
                    .getInspectionTaskKey();
            inspectionTaskOperateHandler.fail(new InspectionTaskFailInfo(failedTaskKey));
            InspectionTask failedTask = inspectionTaskMaintainService.get(failedTaskKey);
            assertEquals(Constants.INSPECTION_TASK_STATUS_FAILED, failedTask.getStatus());
            assertNotNull(failedTask.getEndedDate());
            assertNotNull(failedTask.getDuration());

            expiredTaskKey = inspectionTaskOperateHandler.create(new InspectionTaskCreateInfo(inspection.getKey()))
                    .getInspectionTaskKey();
            inspectionTaskOperateHandler.expire(new InspectionTaskExpireInfo(expiredTaskKey));
            InspectionTask expiredTask = inspectionTaskMaintainService.get(expiredTaskKey);
            assertEquals(Constants.INSPECTION_TASK_STATUS_EXPIRED, expiredTask.getStatus());
            assertNotNull(expiredTask.getExpiredDate());
            assertNotNull(expiredTask.getEndedDate());
            assertNotNull(expiredTask.getDuration());

            diedTaskKey = inspectionTaskOperateHandler.create(new InspectionTaskCreateInfo(inspection.getKey()))
                    .getInspectionTaskKey();
            inspectionTaskOperateHandler.start(new InspectionTaskStartInfo(diedTaskKey));
            inspectionTaskOperateHandler.die(new InspectionTaskDieInfo(diedTaskKey));
            InspectionTask diedTask = inspectionTaskMaintainService.get(diedTaskKey);
            assertEquals(Constants.INSPECTION_TASK_STATUS_DIED, diedTask.getStatus());
            assertNotNull(diedTask.getDiedDate());
            assertNotNull(diedTask.getEndedDate());
            assertNotNull(diedTask.getDuration());
        } finally {
            if (Objects.nonNull(diedTaskKey)) {
                inspectionTaskMaintainService.deleteIfExists(diedTaskKey);
            }
            if (Objects.nonNull(expiredTaskKey)) {
                inspectionTaskMaintainService.deleteIfExists(expiredTaskKey);
            }
            if (Objects.nonNull(failedTaskKey)) {
                inspectionTaskMaintainService.deleteIfExists(failedTaskKey);
            }
            if (Objects.nonNull(inspection.getKey())) {
                inspectionMaintainService.deleteIfExists(inspection.getKey());
            }
        }
    }
}
