package com.dwarfeng.audit.impl.service;

import com.dwarfeng.audit.stack.bean.entity.Inspection;
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
public class InspectionMaintainServiceImplTest {

    @Autowired
    private InspectionMaintainService inspectionMaintainService;

    @Test
    public void testForCrud() throws Exception {
        Inspection inspection = new Inspection(null, "name", true, "remark");
        try {
            inspection.setKey(inspectionMaintainService.insertOrUpdate(inspection));
            assertTrue(inspectionMaintainService.exists(inspection.getKey()));
            assertEquals(
                    BeanUtils.describe(inspection),
                    BeanUtils.describe(inspectionMaintainService.get(inspection.getKey()))
            );
            inspection.setRemark("updated");
            inspectionMaintainService.update(inspection);
            assertEquals(
                    BeanUtils.describe(inspection),
                    BeanUtils.describe(inspectionMaintainService.get(inspection.getKey()))
            );
            inspectionMaintainService.deleteIfExists(inspection.getKey());
            assertFalse(inspectionMaintainService.exists(inspection.getKey()));
        } finally {
            if (inspection.getKey() != null) {
                inspectionMaintainService.deleteIfExists(inspection.getKey());
            }
        }
    }
}
