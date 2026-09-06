package com.dwarfeng.audit.impl.service;

import com.dwarfeng.audit.stack.bean.entity.Inspection;
import com.dwarfeng.audit.stack.bean.entity.InspectionTask;
import com.dwarfeng.audit.stack.service.InspectionMaintainService;
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
public class InspectionTaskMaintainServiceImplTest {

    @Autowired
    private InspectionMaintainService inspectionMaintainService;
    @Autowired
    private InspectionTaskMaintainService inspectionTaskMaintainService;

    private InspectionTask newTask() {
        Date date = new Date(0);
        return new InspectionTask(null, null, 0, date, date, date, 0L, date, date, date, date, "message");
    }

    @Test
    public void testForCrud() throws Exception {
        Inspection inspection = new Inspection(null, "name", true, "remark");
        InspectionTask task = newTask();
        try {
            inspection.setKey(inspectionMaintainService.insertOrUpdate(inspection));
            task.setInspectionKey(inspection.getKey());
            task.setKey(inspectionTaskMaintainService.insertOrUpdate(task));
            assertTrue(inspectionTaskMaintainService.exists(task.getKey()));
            assertEquals(
                    BeanUtils.describe(task),
                    BeanUtils.describe(inspectionTaskMaintainService.get(task.getKey()))
            );
            task.setAnchorMessage("updated");
            inspectionTaskMaintainService.update(task);
            assertEquals(
                    BeanUtils.describe(task),
                    BeanUtils.describe(inspectionTaskMaintainService.get(task.getKey()))
            );
            inspectionTaskMaintainService.deleteIfExists(task.getKey());
            assertFalse(inspectionTaskMaintainService.exists(task.getKey()));
        } finally {
            if (task.getKey() != null) {
                inspectionTaskMaintainService.deleteIfExists(task.getKey());
            }
            if (inspection.getKey() != null) {
                inspectionMaintainService.deleteIfExists(inspection.getKey());
            }
        }
    }

    @Test
    public void testForInspectionCascade() throws Exception {
        Inspection inspection = new Inspection(null, "name", true, "remark");
        InspectionTask task = newTask();
        try {
            inspection.setKey(inspectionMaintainService.insertOrUpdate(inspection));
            task.setInspectionKey(inspection.getKey());
            task.setKey(inspectionTaskMaintainService.insertOrUpdate(task));
            assertEquals(
                    1,
                    inspectionTaskMaintainService.lookupAsList(
                            InspectionTaskMaintainService.CHILD_FOR_INSPECTION, new Object[]{inspection.getKey()}
                    ).size()
            );
            inspectionMaintainService.deleteIfExists(inspection.getKey());
            assertEquals(
                    0,
                    inspectionTaskMaintainService.lookupAsList(
                            InspectionTaskMaintainService.CHILD_FOR_INSPECTION, new Object[]{inspection.getKey()}
                    ).size()
            );
            assertFalse(inspectionTaskMaintainService.exists(task.getKey()));
        } finally {
            if (task.getKey() != null) {
                inspectionTaskMaintainService.deleteIfExists(task.getKey());
            }
            if (inspection.getKey() != null) {
                inspectionMaintainService.deleteIfExists(inspection.getKey());
            }
        }
    }
}
