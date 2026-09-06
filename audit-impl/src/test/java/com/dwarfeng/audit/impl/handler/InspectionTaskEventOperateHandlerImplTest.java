package com.dwarfeng.audit.impl.handler;

import com.dwarfeng.audit.sdk.util.Constants;
import com.dwarfeng.audit.stack.bean.dto.InspectionTaskEventCreateInfo;
import com.dwarfeng.audit.stack.bean.dto.InspectionTaskEventCreateResult;
import com.dwarfeng.audit.stack.bean.entity.Inspection;
import com.dwarfeng.audit.stack.bean.entity.InspectionTask;
import com.dwarfeng.audit.stack.bean.entity.InspectionTaskEvent;
import com.dwarfeng.audit.stack.handler.InspectionTaskEventOperateHandler;
import com.dwarfeng.audit.stack.service.InspectionMaintainService;
import com.dwarfeng.audit.stack.service.InspectionTaskEventMaintainService;
import com.dwarfeng.audit.stack.service.InspectionTaskMaintainService;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
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
 * {@link InspectionTaskEventOperateHandlerImpl} 的集成测试。
 *
 * @author DwArFeng
 * @since 1.1.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring/application-context*.xml")
public class InspectionTaskEventOperateHandlerImplTest {

    @Autowired
    private InspectionMaintainService inspectionMaintainService;
    @Autowired
    private InspectionTaskMaintainService inspectionTaskMaintainService;
    @Autowired
    private InspectionTaskEventMaintainService inspectionTaskEventMaintainService;
    @Autowired
    private InspectionTaskEventOperateHandler inspectionTaskEventOperateHandler;

    @Test
    public void testCreate() throws Exception {
        Inspection inspection = new Inspection(null, "inspection", true, "remark");
        InspectionTask inspectionTask = new InspectionTask(
                null, null, Constants.INSPECTION_TASK_STATUS_CREATED, new Date(), null, null, null,
                new Date(System.currentTimeMillis() + 60000L), null, null, null, "message"
        );
        LongIdKey eventKey = null;
        try {
            inspection.setKey(inspectionMaintainService.insertOrUpdate(inspection));
            inspectionTask.setInspectionKey(inspection.getKey());
            inspectionTask.setKey(inspectionTaskMaintainService.insertOrUpdate(inspectionTask));

            Date happenedDate = new Date(1000L);
            InspectionTaskEventCreateResult result = inspectionTaskEventOperateHandler.create(
                    new InspectionTaskEventCreateInfo(inspectionTask.getKey(), happenedDate, "message")
            );
            eventKey = result.getInspectionTaskEventKey();
            InspectionTaskEvent event = inspectionTaskEventMaintainService.get(eventKey);
            assertNotNull(event);
            assertEquals(inspectionTask.getKey(), event.getInspectionTaskKey());
            assertEquals(happenedDate, event.getHappenedDate());
            assertEquals("message", event.getMessage());
        } finally {
            if (Objects.nonNull(eventKey)) {
                inspectionTaskEventMaintainService.deleteIfExists(eventKey);
            }
            if (Objects.nonNull(inspectionTask.getKey())) {
                inspectionTaskMaintainService.deleteIfExists(inspectionTask.getKey());
            }
            if (Objects.nonNull(inspection.getKey())) {
                inspectionMaintainService.deleteIfExists(inspection.getKey());
            }
        }
    }
}
