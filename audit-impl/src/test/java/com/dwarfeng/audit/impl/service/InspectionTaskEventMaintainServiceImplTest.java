package com.dwarfeng.audit.impl.service;

import com.dwarfeng.audit.stack.bean.entity.InspectionTask;
import com.dwarfeng.audit.stack.bean.entity.InspectionTaskEvent;
import com.dwarfeng.audit.stack.service.InspectionTaskEventMaintainService;
import com.dwarfeng.audit.stack.service.InspectionTaskMaintainService;
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
public class InspectionTaskEventMaintainServiceImplTest {

    @Autowired
    private InspectionTaskMaintainService inspectionTaskMaintainService;
    @Autowired
    private InspectionTaskEventMaintainService inspectionTaskEventMaintainService;

    private InspectionTask newTask() {
        Date date = new Date(0);
        return new InspectionTask(null, null, 0, date, date, date, 0L, date, date, date, date, "message");
    }

    @Test
    public void testForCrud() throws Exception {
        InspectionTask task = newTask();
        InspectionTaskEvent event = new InspectionTaskEvent(null, null, new Date(0), "message");
        try {
            task.setKey(inspectionTaskMaintainService.insertOrUpdate(task));
            event.setInspectionTaskKey(task.getKey());
            event.setKey(inspectionTaskEventMaintainService.insertOrUpdate(event));
            assertTrue(inspectionTaskEventMaintainService.exists(event.getKey()));
            assertEquals(
                    BeanUtils.describe(event),
                    BeanUtils.describe(inspectionTaskEventMaintainService.get(event.getKey()))
            );
            event.setMessage("updated");
            inspectionTaskEventMaintainService.update(event);
            assertEquals(
                    BeanUtils.describe(event),
                    BeanUtils.describe(inspectionTaskEventMaintainService.get(event.getKey()))
            );
            inspectionTaskEventMaintainService.deleteIfExists(event.getKey());
            assertFalse(inspectionTaskEventMaintainService.exists(event.getKey()));
        } finally {
            if (event.getKey() != null) {
                inspectionTaskEventMaintainService.deleteIfExists(event.getKey());
            }
            if (task.getKey() != null) {
                inspectionTaskMaintainService.deleteIfExists(task.getKey());
            }
        }
    }

    @Test
    public void testForInspectionTaskCascade() throws Exception {
        InspectionTask task = newTask();
        InspectionTaskEvent event = new InspectionTaskEvent(null, null, new Date(0), "message");
        try {
            task.setKey(inspectionTaskMaintainService.insertOrUpdate(task));
            event.setInspectionTaskKey(task.getKey());
            event.setKey(inspectionTaskEventMaintainService.insertOrUpdate(event));
            assertEquals(
                    1,
                    inspectionTaskEventMaintainService.lookupAsList(
                            InspectionTaskEventMaintainService.CHILD_FOR_INSPECTION_TASK,
                            new Object[]{task.getKey()}
                    ).size()
            );
            inspectionTaskMaintainService.deleteIfExists(task.getKey());
            assertEquals(
                    0,
                    inspectionTaskEventMaintainService.lookupAsList(
                            InspectionTaskEventMaintainService.CHILD_FOR_INSPECTION_TASK,
                            new Object[]{task.getKey()}
                    ).size()
            );
            assertFalse(inspectionTaskEventMaintainService.exists(event.getKey()));
        } finally {
            if (event.getKey() != null) {
                inspectionTaskEventMaintainService.deleteIfExists(event.getKey());
            }
            if (task.getKey() != null) {
                inspectionTaskMaintainService.deleteIfExists(task.getKey());
            }
        }
    }
}
