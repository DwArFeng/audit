package com.dwarfeng.audit.impl.handler;

import com.dwarfeng.audit.stack.bean.dto.InspectionAlarmCreateInfo;
import com.dwarfeng.audit.stack.bean.dto.InspectionAlarmCreateResult;
import com.dwarfeng.audit.stack.bean.entity.Inspection;
import com.dwarfeng.audit.stack.bean.entity.InspectionAlarm;
import com.dwarfeng.audit.stack.bean.entity.InspectionTask;
import com.dwarfeng.audit.stack.bean.entity.InspectorInfo;
import com.dwarfeng.audit.stack.exception.InspectorInfoInspectionMismatchException;
import com.dwarfeng.audit.stack.handler.InspectionAlarmOperateHandler;
import com.dwarfeng.audit.stack.service.InspectionAlarmMaintainService;
import com.dwarfeng.audit.stack.service.InspectionMaintainService;
import com.dwarfeng.audit.stack.service.InspectionTaskMaintainService;
import com.dwarfeng.audit.stack.service.InspectorInfoMaintainService;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;
import java.util.Objects;

import static org.junit.Assert.*;

/**
 * {@link InspectionAlarmOperateHandlerImpl} 的集成测试。
 *
 * @author DwArFeng
 * @since 1.1.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring/application-context*.xml")
public class InspectionAlarmOperateHandlerImplTest {

    @Autowired
    private InspectionMaintainService inspectionMaintainService;
    @Autowired
    private InspectionTaskMaintainService inspectionTaskMaintainService;
    @Autowired
    private InspectorInfoMaintainService inspectorInfoMaintainService;
    @Autowired
    private InspectionAlarmMaintainService inspectionAlarmMaintainService;
    @Autowired
    private InspectionAlarmOperateHandler inspectionAlarmOperateHandler;

    @Test
    public void testCreate() throws Exception {
        Inspection inspection = new Inspection(null, "inspection", true, "remark");
        InspectionTask inspectionTask = new InspectionTask(
                null, null, 0, new Date(), null, null, null, null, null, null, null, "message"
        );
        InspectorInfo inspectorInfo = new InspectorInfo(null, null, 0, true, "type", "param", "remark");
        LongIdKey inspectionAlarmKey = null;
        try {
            inspection.setKey(inspectionMaintainService.insertOrUpdate(inspection));
            inspectionTask.setInspectionKey(inspection.getKey());
            inspectionTask.setKey(inspectionTaskMaintainService.insertOrUpdate(inspectionTask));
            inspectorInfo.setInspectionKey(inspection.getKey());
            inspectorInfo.setKey(inspectorInfoMaintainService.insertOrUpdate(inspectorInfo));

            Date startDate = new Date();
            InspectionAlarmCreateResult result = inspectionAlarmOperateHandler.create(
                    new InspectionAlarmCreateInfo(
                            inspection.getKey(), inspectionTask.getKey(), inspectorInfo.getKey(), "type", "message"
                    )
            );
            Date endDate = new Date();

            inspectionAlarmKey = result.getInspectionAlarmKey();
            InspectionAlarm inspectionAlarm = inspectionAlarmMaintainService.get(inspectionAlarmKey);
            assertNotNull(inspectionAlarm);
            assertEquals(inspection.getKey(), inspectionAlarm.getInspectionKey());
            assertEquals(inspectionTask.getKey(), inspectionAlarm.getInspectionTaskKey());
            assertEquals(inspectorInfo.getKey(), inspectionAlarm.getInspectorInfoKey());
            assertFalse(inspectionAlarm.getHappenedDate().before(startDate));
            assertFalse(inspectionAlarm.getHappenedDate().after(endDate));
            assertEquals("type", inspectionAlarm.getType());
            assertEquals("message", inspectionAlarm.getMessage());
        } finally {
            if (Objects.nonNull(inspectionAlarmKey)) {
                inspectionAlarmMaintainService.deleteIfExists(inspectionAlarmKey);
            }
            if (Objects.nonNull(inspectorInfo.getKey())) {
                inspectorInfoMaintainService.deleteIfExists(inspectorInfo.getKey());
            }
            if (Objects.nonNull(inspectionTask.getKey())) {
                inspectionTaskMaintainService.deleteIfExists(inspectionTask.getKey());
            }
            if (Objects.nonNull(inspection.getKey())) {
                inspectionMaintainService.deleteIfExists(inspection.getKey());
            }
        }
    }

    @Test
    public void testCreateWithMismatchedInspectorInfo() throws Exception {
        Inspection inspection = new Inspection(null, "inspection", true, "remark");
        Inspection anotherInspection = new Inspection(null, "another-inspection", true, "remark");
        InspectionTask inspectionTask = new InspectionTask(
                null, null, 0, new Date(), null, null, null, null, null, null, null, "message"
        );
        InspectorInfo inspectorInfo = new InspectorInfo(null, null, 0, true, "type", "param", "remark");
        try {
            inspection.setKey(inspectionMaintainService.insertOrUpdate(inspection));
            anotherInspection.setKey(inspectionMaintainService.insertOrUpdate(anotherInspection));
            inspectionTask.setInspectionKey(inspection.getKey());
            inspectionTask.setKey(inspectionTaskMaintainService.insertOrUpdate(inspectionTask));
            inspectorInfo.setInspectionKey(anotherInspection.getKey());
            inspectorInfo.setKey(inspectorInfoMaintainService.insertOrUpdate(inspectorInfo));

            try {
                inspectionAlarmOperateHandler.create(
                        new InspectionAlarmCreateInfo(
                                inspection.getKey(), inspectionTask.getKey(), inspectorInfo.getKey(), "type", "message"
                        )
                );
                fail("应抛出 InspectorInfoInspectionMismatchException");
            } catch (InspectorInfoInspectionMismatchException ignored) {
            }
        } finally {
            if (Objects.nonNull(inspectorInfo.getKey())) {
                inspectorInfoMaintainService.deleteIfExists(inspectorInfo.getKey());
            }
            if (Objects.nonNull(inspectionTask.getKey())) {
                inspectionTaskMaintainService.deleteIfExists(inspectionTask.getKey());
            }
            if (Objects.nonNull(anotherInspection.getKey())) {
                inspectionMaintainService.deleteIfExists(anotherInspection.getKey());
            }
            if (Objects.nonNull(inspection.getKey())) {
                inspectionMaintainService.deleteIfExists(inspection.getKey());
            }
        }
    }
}
