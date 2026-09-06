package com.dwarfeng.audit.impl.service;

import com.dwarfeng.audit.stack.bean.entity.InspectionAlarmTypeIndicator;
import com.dwarfeng.audit.stack.service.InspectionAlarmTypeIndicatorMaintainService;
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
public class InspectionAlarmTypeIndicatorMaintainServiceImplTest {

    @Autowired
    private InspectionAlarmTypeIndicatorMaintainService inspectionAlarmTypeIndicatorMaintainService;

    @Test
    public void testForCrud() throws Exception {
        InspectionAlarmTypeIndicator indicator = new InspectionAlarmTypeIndicator(
                new StringIdKey("test-" + UUID.randomUUID()), "label", "remark"
        );
        try {
            indicator.setKey(inspectionAlarmTypeIndicatorMaintainService.insertOrUpdate(indicator));
            assertTrue(inspectionAlarmTypeIndicatorMaintainService.exists(indicator.getKey()));
            assertEquals(
                    BeanUtils.describe(indicator),
                    BeanUtils.describe(inspectionAlarmTypeIndicatorMaintainService.get(indicator.getKey()))
            );
            indicator.setRemark("updated");
            inspectionAlarmTypeIndicatorMaintainService.update(indicator);
            assertEquals(
                    BeanUtils.describe(indicator),
                    BeanUtils.describe(inspectionAlarmTypeIndicatorMaintainService.get(indicator.getKey()))
            );
            inspectionAlarmTypeIndicatorMaintainService.deleteIfExists(indicator.getKey());
            assertFalse(inspectionAlarmTypeIndicatorMaintainService.exists(indicator.getKey()));
        } finally {
            if (indicator.getKey() != null) {
                inspectionAlarmTypeIndicatorMaintainService.deleteIfExists(indicator.getKey());
            }
        }
    }
}
