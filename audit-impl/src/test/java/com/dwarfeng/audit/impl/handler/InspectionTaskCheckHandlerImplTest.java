package com.dwarfeng.audit.impl.handler;

import com.dwarfeng.audit.sdk.util.Constants;
import com.dwarfeng.audit.stack.bean.entity.Inspection;
import com.dwarfeng.audit.stack.bean.entity.InspectionTask;
import com.dwarfeng.audit.stack.service.InspectionMaintainService;
import com.dwarfeng.audit.stack.service.InspectionTaskMaintainService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;
import java.util.Objects;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * {@link InspectionTaskCheckHandlerImpl} 的集成测试。
 *
 * @author DwArFeng
 * @since 1.1.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring/application-context*.xml")
public class InspectionTaskCheckHandlerImplTest {

    @Autowired
    private InspectionMaintainService inspectionMaintainService;
    @Autowired
    private InspectionTaskMaintainService inspectionTaskMaintainService;
    @Autowired
    private InspectionTaskCheckHandlerImpl.InspectionTaskCheckWorker inspectionTaskCheckWorker;

    @Test
    public void testExpireCheckAndDieCheck() throws Exception {
        Inspection inspection = new Inspection(null, "inspection", true, "remark");
        InspectionTask taskToExpire = new InspectionTask(
                null, null, Constants.INSPECTION_TASK_STATUS_CREATED, new Date(), null, null, null,
                new Date(System.currentTimeMillis() - 1000L), null, null, null, "message"
        );
        InspectionTask taskToDie = new InspectionTask(
                null, null, Constants.INSPECTION_TASK_STATUS_PROCESSING, new Date(), new Date(), null, null,
                null, new Date(System.currentTimeMillis() - 1000L), null, null, "message"
        );
        try {
            inspection.setKey(inspectionMaintainService.insertOrUpdate(inspection));
            taskToExpire.setInspectionKey(inspection.getKey());
            taskToExpire.setKey(inspectionTaskMaintainService.insertOrUpdate(taskToExpire));
            taskToDie.setInspectionKey(inspection.getKey());
            taskToDie.setKey(inspectionTaskMaintainService.insertOrUpdate(taskToDie));

            inspectionTaskCheckWorker.expireCheck();
            inspectionTaskCheckWorker.dieCheck();

            InspectionTask expiredTask = inspectionTaskMaintainService.get(taskToExpire.getKey());
            assertEquals(Constants.INSPECTION_TASK_STATUS_EXPIRED, expiredTask.getStatus());
            assertNotNull(expiredTask.getExpiredDate());
            InspectionTask diedTask = inspectionTaskMaintainService.get(taskToDie.getKey());
            assertEquals(Constants.INSPECTION_TASK_STATUS_DIED, diedTask.getStatus());
            assertNotNull(diedTask.getDiedDate());
        } finally {
            if (Objects.nonNull(taskToDie.getKey())) {
                inspectionTaskMaintainService.deleteIfExists(taskToDie.getKey());
            }
            if (Objects.nonNull(taskToExpire.getKey())) {
                inspectionTaskMaintainService.deleteIfExists(taskToExpire.getKey());
            }
            if (Objects.nonNull(inspection.getKey())) {
                inspectionMaintainService.deleteIfExists(inspection.getKey());
            }
        }
    }
}
