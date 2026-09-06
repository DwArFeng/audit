package com.dwarfeng.audit.impl.service;

import com.dwarfeng.audit.stack.bean.entity.Inspection;
import com.dwarfeng.audit.stack.bean.entity.InspectorInfo;
import com.dwarfeng.audit.stack.service.InspectionMaintainService;
import com.dwarfeng.audit.stack.service.InspectorInfoMaintainService;
import org.apache.commons.beanutils.BeanUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring/application-context*.xml")
public class InspectorInfoMaintainServiceImplTest {

    @Autowired
    private InspectionMaintainService inspectionMaintainService;
    @Autowired
    private InspectorInfoMaintainService inspectorInfoMaintainService;

    @Test
    public void testForCrud() throws Exception {
        Inspection inspection = new Inspection(null, "name", true, "remark");
        InspectorInfo inspectorInfo = new InspectorInfo(null, null, 0, true, "type", "param", "remark");
        try {
            inspection.setKey(inspectionMaintainService.insertOrUpdate(inspection));
            inspectorInfo.setInspectionKey(inspection.getKey());
            inspectorInfo.setKey(inspectorInfoMaintainService.insertOrUpdate(inspectorInfo));
            assertTrue(inspectorInfoMaintainService.exists(inspectorInfo.getKey()));
            assertEquals(
                    BeanUtils.describe(inspectorInfo),
                    BeanUtils.describe(inspectorInfoMaintainService.get(inspectorInfo.getKey()))
            );
            inspectorInfo.setRemark("updated");
            inspectorInfoMaintainService.update(inspectorInfo);
            assertEquals(
                    BeanUtils.describe(inspectorInfo),
                    BeanUtils.describe(inspectorInfoMaintainService.get(inspectorInfo.getKey()))
            );
            inspectorInfoMaintainService.deleteIfExists(inspectorInfo.getKey());
            assertFalse(inspectorInfoMaintainService.exists(inspectorInfo.getKey()));
        } finally {
            if (inspectorInfo.getKey() != null) {
                inspectorInfoMaintainService.deleteIfExists(inspectorInfo.getKey());
            }
            if (inspection.getKey() != null) {
                inspectionMaintainService.deleteIfExists(inspection.getKey());
            }
        }
    }

    @Test
    public void testForInspectionCascade() throws Exception {
        Inspection inspection = new Inspection(null, "name", true, "remark");
        InspectorInfo inspectorInfo = new InspectorInfo(null, null, 0, true, "type", "param", "remark");
        try {
            inspection.setKey(inspectionMaintainService.insertOrUpdate(inspection));
            inspectorInfo.setInspectionKey(inspection.getKey());
            inspectorInfo.setKey(inspectorInfoMaintainService.insertOrUpdate(inspectorInfo));
            assertEquals(
                    1,
                    inspectorInfoMaintainService.lookupAsList(
                            InspectorInfoMaintainService.CHILD_FOR_INSPECTION, new Object[]{inspection.getKey()}
                    ).size()
            );
            inspectionMaintainService.deleteIfExists(inspection.getKey());
            assertEquals(
                    0,
                    inspectorInfoMaintainService.lookupAsList(
                            InspectorInfoMaintainService.CHILD_FOR_INSPECTION, new Object[]{inspection.getKey()}
                    ).size()
            );
            assertFalse(inspectorInfoMaintainService.exists(inspectorInfo.getKey()));
        } finally {
            if (inspectorInfo.getKey() != null) {
                inspectorInfoMaintainService.deleteIfExists(inspectorInfo.getKey());
            }
            if (inspection.getKey() != null) {
                inspectionMaintainService.deleteIfExists(inspection.getKey());
            }
        }
    }
}
