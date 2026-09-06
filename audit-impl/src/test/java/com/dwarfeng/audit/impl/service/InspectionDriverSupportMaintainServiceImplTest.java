package com.dwarfeng.audit.impl.service;

import com.dwarfeng.audit.stack.bean.entity.InspectionDriverSupport;
import com.dwarfeng.audit.stack.service.InspectionDriverSupportMaintainService;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import org.apache.commons.beanutils.BeanUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.UUID;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring/application-context*.xml")
public class InspectionDriverSupportMaintainServiceImplTest {

    @Autowired
    private InspectionDriverSupportMaintainService inspectionDriverSupportMaintainService;

    @Test
    public void testForCrud() throws Exception {
        InspectionDriverSupport support = new InspectionDriverSupport(
                new StringIdKey("test-" + UUID.randomUUID()), "label", "description", "exampleParam"
        );
        try {
            support.setKey(inspectionDriverSupportMaintainService.insertOrUpdate(support));
            assertTrue(inspectionDriverSupportMaintainService.exists(support.getKey()));
            assertEquals(
                    BeanUtils.describe(support),
                    BeanUtils.describe(inspectionDriverSupportMaintainService.get(support.getKey()))
            );
            support.setDescription("updated");
            inspectionDriverSupportMaintainService.update(support);
            assertEquals(
                    BeanUtils.describe(support),
                    BeanUtils.describe(inspectionDriverSupportMaintainService.get(support.getKey()))
            );
            inspectionDriverSupportMaintainService.deleteIfExists(support.getKey());
            assertFalse(inspectionDriverSupportMaintainService.exists(support.getKey()));
        } finally {
            if (support.getKey() != null) {
                inspectionDriverSupportMaintainService.deleteIfExists(support.getKey());
            }
        }
    }
}
