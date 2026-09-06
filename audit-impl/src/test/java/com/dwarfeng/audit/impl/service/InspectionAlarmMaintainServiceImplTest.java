package com.dwarfeng.audit.impl.service;

import com.dwarfeng.audit.stack.bean.entity.Inspection;
import com.dwarfeng.audit.stack.bean.entity.InspectionAlarm;
import com.dwarfeng.audit.stack.bean.entity.InspectionTask;
import com.dwarfeng.audit.stack.bean.entity.InspectorInfo;
import com.dwarfeng.audit.stack.service.InspectionAlarmMaintainService;
import com.dwarfeng.audit.stack.service.InspectionMaintainService;
import com.dwarfeng.audit.stack.service.InspectionTaskMaintainService;
import com.dwarfeng.audit.stack.service.InspectorInfoMaintainService;
import org.apache.commons.beanutils.BeanUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring/application-context*.xml")
public class InspectionAlarmMaintainServiceImplTest {

    @Autowired
    private InspectionMaintainService inspectionMaintainService;
    @Autowired
    private InspectionTaskMaintainService inspectionTaskMaintainService;
    @Autowired
    private InspectorInfoMaintainService inspectorInfoMaintainService;
    @Autowired
    private InspectionAlarmMaintainService inspectionAlarmMaintainService;

    private InspectionTask newTask() {
        Date date = new Date(0);
        return new InspectionTask(null, null, 0, date, date, date, 0L, date, date, date, date, "message");
    }

    private InspectionAlarm newAlarm() {
        return new InspectionAlarm(null, null, null, null, new Date(0), "type", "message");
    }

    @Test
    public void testForCrud() throws Exception {
        InspectionAlarm alarm = newAlarm();
        try {
            alarm.setKey(inspectionAlarmMaintainService.insertOrUpdate(alarm));
            assertTrue(inspectionAlarmMaintainService.exists(alarm.getKey()));
            assertEquals(
                    BeanUtils.describe(alarm),
                    BeanUtils.describe(inspectionAlarmMaintainService.get(alarm.getKey()))
            );
            alarm.setMessage("updated");
            inspectionAlarmMaintainService.update(alarm);
            assertEquals(
                    BeanUtils.describe(alarm),
                    BeanUtils.describe(inspectionAlarmMaintainService.get(alarm.getKey()))
            );
            inspectionAlarmMaintainService.deleteIfExists(alarm.getKey());
            assertFalse(inspectionAlarmMaintainService.exists(alarm.getKey()));
        } finally {
            if (alarm.getKey() != null) {
                inspectionAlarmMaintainService.deleteIfExists(alarm.getKey());
            }
        }
    }

    @Test
    public void testForInspectionCascade() throws Exception {
        Inspection inspection = new Inspection(null, "name", true, "remark");
        InspectionAlarm alarm = newAlarm();
        try {
            inspection.setKey(inspectionMaintainService.insertOrUpdate(inspection));
            alarm.setInspectionKey(inspection.getKey());
            alarm.setKey(inspectionAlarmMaintainService.insertOrUpdate(alarm));
            assertEquals(
                    1,
                    inspectionAlarmMaintainService.lookupAsList(
                            InspectionAlarmMaintainService.CHILD_FOR_INSPECTION, new Object[]{inspection.getKey()}
                    ).size()
            );
            inspectionMaintainService.deleteIfExists(inspection.getKey());
            assertEquals(
                    0,
                    inspectionAlarmMaintainService.lookupAsList(
                            InspectionAlarmMaintainService.CHILD_FOR_INSPECTION, new Object[]{inspection.getKey()}
                    ).size()
            );
            assertTrue(inspectionAlarmMaintainService.exists(alarm.getKey()));
            assertNull(inspectionAlarmMaintainService.get(alarm.getKey()).getInspectionKey());
        } finally {
            if (alarm.getKey() != null) {
                inspectionAlarmMaintainService.deleteIfExists(alarm.getKey());
            }
            if (inspection.getKey() != null) {
                inspectionMaintainService.deleteIfExists(inspection.getKey());
            }
        }
    }

    @Test
    public void testForInspectionTaskCascade() throws Exception {
        InspectionTask task = newTask();
        InspectionAlarm alarm = newAlarm();
        try {
            task.setKey(inspectionTaskMaintainService.insertOrUpdate(task));
            alarm.setInspectionTaskKey(task.getKey());
            alarm.setKey(inspectionAlarmMaintainService.insertOrUpdate(alarm));
            assertEquals(
                    1,
                    inspectionAlarmMaintainService.lookupAsList(
                            InspectionAlarmMaintainService.CHILD_FOR_INSPECTION_TASK, new Object[]{task.getKey()}
                    ).size()
            );
            inspectionTaskMaintainService.deleteIfExists(task.getKey());
            assertEquals(
                    0,
                    inspectionAlarmMaintainService.lookupAsList(
                            InspectionAlarmMaintainService.CHILD_FOR_INSPECTION_TASK, new Object[]{task.getKey()}
                    ).size()
            );
            assertTrue(inspectionAlarmMaintainService.exists(alarm.getKey()));
            assertNull(inspectionAlarmMaintainService.get(alarm.getKey()).getInspectionTaskKey());
        } finally {
            if (alarm.getKey() != null) {
                inspectionAlarmMaintainService.deleteIfExists(alarm.getKey());
            }
            if (task.getKey() != null) {
                inspectionTaskMaintainService.deleteIfExists(task.getKey());
            }
        }
    }

    @Test
    public void testForInspectorInfoCascade() throws Exception {
        InspectorInfo inspectorInfo = new InspectorInfo(null, null, 0, true, "type", "param", "remark");
        InspectionAlarm alarm = newAlarm();
        try {
            inspectorInfo.setKey(inspectorInfoMaintainService.insertOrUpdate(inspectorInfo));
            alarm.setInspectorInfoKey(inspectorInfo.getKey());
            alarm.setKey(inspectionAlarmMaintainService.insertOrUpdate(alarm));
            assertEquals(
                    1,
                    inspectionAlarmMaintainService.lookupAsList(
                            InspectionAlarmMaintainService.CHILD_FOR_INSPECTOR_INFO,
                            new Object[]{inspectorInfo.getKey()}
                    ).size()
            );
            inspectorInfoMaintainService.deleteIfExists(inspectorInfo.getKey());
            assertEquals(
                    0,
                    inspectionAlarmMaintainService.lookupAsList(
                            InspectionAlarmMaintainService.CHILD_FOR_INSPECTOR_INFO,
                            new Object[]{inspectorInfo.getKey()}
                    ).size()
            );
            assertTrue(inspectionAlarmMaintainService.exists(alarm.getKey()));
            assertNull(inspectionAlarmMaintainService.get(alarm.getKey()).getInspectorInfoKey());
        } finally {
            if (alarm.getKey() != null) {
                inspectionAlarmMaintainService.deleteIfExists(alarm.getKey());
            }
            if (inspectorInfo.getKey() != null) {
                inspectorInfoMaintainService.deleteIfExists(inspectorInfo.getKey());
            }
        }
    }
}
