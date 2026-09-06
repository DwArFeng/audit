package com.dwarfeng.audit.impl.service;

import com.dwarfeng.audit.stack.bean.entity.InspectorInfo;
import com.dwarfeng.audit.stack.bean.entity.InspectorVariable;
import com.dwarfeng.audit.stack.bean.key.InspectorVariableKey;
import com.dwarfeng.audit.stack.service.InspectorInfoMaintainService;
import com.dwarfeng.audit.stack.service.InspectorVariableMaintainService;
import org.apache.commons.beanutils.BeanUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;
import java.util.UUID;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring/application-context*.xml")
public class InspectorVariableMaintainServiceImplTest {

    @Autowired
    private InspectorInfoMaintainService inspectorInfoMaintainService;
    @Autowired
    private InspectorVariableMaintainService inspectorVariableMaintainService;

    private InspectorVariable newVariable(InspectorInfo inspectorInfo) {
        return new InspectorVariable(
                new InspectorVariableKey(inspectorInfo.getKey().getLongId(), "test-" + UUID.randomUUID()),
                0, "value", 1L, 1.0, true, new Date(0)
        );
    }

    @Test
    public void testForCrud() throws Exception {
        InspectorInfo inspectorInfo = new InspectorInfo(null, null, 0, true, "type", "param", "remark");
        InspectorVariable variable = null;
        try {
            inspectorInfo.setKey(inspectorInfoMaintainService.insertOrUpdate(inspectorInfo));
            variable = newVariable(inspectorInfo);
            variable.setKey(inspectorVariableMaintainService.insertOrUpdate(variable));
            assertTrue(inspectorVariableMaintainService.exists(variable.getKey()));
            assertEquals(
                    BeanUtils.describe(variable),
                    BeanUtils.describe(inspectorVariableMaintainService.get(variable.getKey()))
            );
            variable.setStringValue("updated");
            inspectorVariableMaintainService.update(variable);
            assertEquals(
                    BeanUtils.describe(variable),
                    BeanUtils.describe(inspectorVariableMaintainService.get(variable.getKey()))
            );
            inspectorVariableMaintainService.deleteIfExists(variable.getKey());
            assertFalse(inspectorVariableMaintainService.exists(variable.getKey()));
        } finally {
            if (variable != null && variable.getKey() != null) {
                inspectorVariableMaintainService.deleteIfExists(variable.getKey());
            }
            if (inspectorInfo.getKey() != null) {
                inspectorInfoMaintainService.deleteIfExists(inspectorInfo.getKey());
            }
        }
    }

    @Test
    public void testForInspectorInfoCascade() throws Exception {
        InspectorInfo inspectorInfo = new InspectorInfo(null, null, 0, true, "type", "param", "remark");
        InspectorVariable variable = null;
        try {
            inspectorInfo.setKey(inspectorInfoMaintainService.insertOrUpdate(inspectorInfo));
            variable = newVariable(inspectorInfo);
            variable.setKey(inspectorVariableMaintainService.insertOrUpdate(variable));
            assertEquals(
                    1,
                    inspectorVariableMaintainService.lookupAsList(
                            InspectorVariableMaintainService.CHILD_FOR_INSPECTOR_INFO,
                            new Object[]{inspectorInfo.getKey()}
                    ).size()
            );
            inspectorInfoMaintainService.deleteIfExists(inspectorInfo.getKey());
            assertEquals(
                    0,
                    inspectorVariableMaintainService.lookupAsList(
                            InspectorVariableMaintainService.CHILD_FOR_INSPECTOR_INFO,
                            new Object[]{inspectorInfo.getKey()}
                    ).size()
            );
            assertFalse(inspectorVariableMaintainService.exists(variable.getKey()));
        } finally {
            if (variable != null && variable.getKey() != null) {
                inspectorVariableMaintainService.deleteIfExists(variable.getKey());
            }
            if (inspectorInfo.getKey() != null) {
                inspectorInfoMaintainService.deleteIfExists(inspectorInfo.getKey());
            }
        }
    }
}
