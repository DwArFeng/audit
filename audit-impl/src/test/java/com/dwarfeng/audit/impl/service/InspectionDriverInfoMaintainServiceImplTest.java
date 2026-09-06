package com.dwarfeng.audit.impl.service;

import com.dwarfeng.audit.stack.bean.entity.Inspection;
import com.dwarfeng.audit.stack.bean.entity.InspectionDriverInfo;
import com.dwarfeng.audit.stack.service.InspectionDriverInfoMaintainService;
import com.dwarfeng.audit.stack.service.InspectionMaintainService;
import org.apache.commons.beanutils.BeanUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring/application-context*.xml")
public class InspectionDriverInfoMaintainServiceImplTest {

    @Autowired
    private InspectionMaintainService inspectionMaintainService;
    @Autowired
    private InspectionDriverInfoMaintainService inspectionDriverInfoMaintainService;

    @Test
    public void testForCrud() throws Exception {
        Inspection inspection = new Inspection(null, "name", true, "remark");
        InspectionDriverInfo driverInfo = new InspectionDriverInfo(null, null, true, "type", "param", "remark");
        try {
            inspection.setKey(inspectionMaintainService.insertOrUpdate(inspection));
            driverInfo.setInspectionKey(inspection.getKey());
            driverInfo.setKey(inspectionDriverInfoMaintainService.insertOrUpdate(driverInfo));
            assertTrue(inspectionDriverInfoMaintainService.exists(driverInfo.getKey()));
            assertEquals(
                    BeanUtils.describe(driverInfo),
                    BeanUtils.describe(inspectionDriverInfoMaintainService.get(driverInfo.getKey()))
            );
            driverInfo.setRemark("updated");
            inspectionDriverInfoMaintainService.update(driverInfo);
            assertEquals(
                    BeanUtils.describe(driverInfo),
                    BeanUtils.describe(inspectionDriverInfoMaintainService.get(driverInfo.getKey()))
            );
            inspectionDriverInfoMaintainService.deleteIfExists(driverInfo.getKey());
            assertFalse(inspectionDriverInfoMaintainService.exists(driverInfo.getKey()));
        } finally {
            if (driverInfo.getKey() != null) {
                inspectionDriverInfoMaintainService.deleteIfExists(driverInfo.getKey());
            }
            if (inspection.getKey() != null) {
                inspectionMaintainService.deleteIfExists(inspection.getKey());
            }
        }
    }

    @Test
    public void testForInspectionCascade() throws Exception {
        Inspection inspection = new Inspection(null, "name", true, "remark");
        InspectionDriverInfo driverInfo = new InspectionDriverInfo(null, null, true, "type", "param", "remark");
        try {
            inspection.setKey(inspectionMaintainService.insertOrUpdate(inspection));
            driverInfo.setInspectionKey(inspection.getKey());
            driverInfo.setKey(inspectionDriverInfoMaintainService.insertOrUpdate(driverInfo));
            assertEquals(
                    1,
                    inspectionDriverInfoMaintainService.lookupAsList(
                            InspectionDriverInfoMaintainService.CHILD_FOR_INSPECTION,
                            new Object[]{inspection.getKey()}
                    ).size()
            );
            inspectionMaintainService.deleteIfExists(inspection.getKey());
            assertEquals(
                    0,
                    inspectionDriverInfoMaintainService.lookupAsList(
                            InspectionDriverInfoMaintainService.CHILD_FOR_INSPECTION,
                            new Object[]{inspection.getKey()}
                    ).size()
            );
            assertFalse(inspectionDriverInfoMaintainService.exists(driverInfo.getKey()));
        } finally {
            if (driverInfo.getKey() != null) {
                inspectionDriverInfoMaintainService.deleteIfExists(driverInfo.getKey());
            }
            if (inspection.getKey() != null) {
                inspectionMaintainService.deleteIfExists(inspection.getKey());
            }
        }
    }
}
