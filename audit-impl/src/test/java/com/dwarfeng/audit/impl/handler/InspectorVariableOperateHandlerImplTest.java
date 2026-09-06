package com.dwarfeng.audit.impl.handler;

import com.dwarfeng.audit.sdk.util.Constants;
import com.dwarfeng.audit.stack.bean.dto.InspectorVariableInspectInfo;
import com.dwarfeng.audit.stack.bean.dto.InspectorVariableInspectResult;
import com.dwarfeng.audit.stack.bean.dto.InspectorVariableRemoveInfo;
import com.dwarfeng.audit.stack.bean.dto.InspectorVariableUpsertInfo;
import com.dwarfeng.audit.stack.bean.entity.Inspection;
import com.dwarfeng.audit.stack.bean.entity.InspectorInfo;
import com.dwarfeng.audit.stack.bean.key.InspectorVariableKey;
import com.dwarfeng.audit.stack.exception.InvalidVariableValueTypeException;
import com.dwarfeng.audit.stack.exception.VariableValueTypeMismatchException;
import com.dwarfeng.audit.stack.handler.InspectorVariableOperateHandler;
import com.dwarfeng.audit.stack.service.InspectionMaintainService;
import com.dwarfeng.audit.stack.service.InspectorInfoMaintainService;
import com.dwarfeng.audit.stack.service.InspectorVariableMaintainService;
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
 * {@link InspectorVariableOperateHandlerImpl} 的集成测试。
 *
 * @author DwArFeng
 * @since 1.1.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring/application-context*.xml")
public class InspectorVariableOperateHandlerImplTest {

    @Autowired
    private InspectionMaintainService inspectionMaintainService;
    @Autowired
    private InspectorInfoMaintainService inspectorInfoMaintainService;
    @Autowired
    private InspectorVariableMaintainService inspectorVariableMaintainService;
    @Autowired
    private InspectorVariableOperateHandler inspectorVariableOperateHandler;

    @Test
    public void testOperate() throws Exception {
        Inspection inspection = new Inspection(null, "inspection", true, "remark");
        InspectorInfo inspectorInfo = new InspectorInfo(null, null, 0, true, "type", "param", "remark");
        String inspectorVariableId = "variable";
        try {
            inspection.setKey(inspectionMaintainService.insertOrUpdate(inspection));
            inspectorInfo.setInspectionKey(inspection.getKey());
            inspectorInfo.setKey(inspectorInfoMaintainService.insertOrUpdate(inspectorInfo));

            InspectorVariableInspectInfo inspectInfo = new InspectorVariableInspectInfo(
                    inspectorInfo.getKey(), inspectorVariableId
            );
            assertNull(inspectorVariableOperateHandler.inspect(inspectInfo));

            assertUpsertAndInspect(inspectorInfo.getKey(), inspectorVariableId,
                    Constants.INSPECTOR_VARIABLE_VALUE_TYPE_STRING, "value");
            assertUpsertAndInspect(inspectorInfo.getKey(), inspectorVariableId,
                    Constants.INSPECTOR_VARIABLE_VALUE_TYPE_LONG, 1L);
            assertUpsertAndInspect(inspectorInfo.getKey(), inspectorVariableId,
                    Constants.INSPECTOR_VARIABLE_VALUE_TYPE_DOUBLE, 1.0D);
            assertUpsertAndInspect(inspectorInfo.getKey(), inspectorVariableId,
                    Constants.INSPECTOR_VARIABLE_VALUE_TYPE_BOOLEAN, true);
            Date dateValue = new Date(1000L);
            assertUpsertAndInspect(inspectorInfo.getKey(), inspectorVariableId,
                    Constants.INSPECTOR_VARIABLE_VALUE_TYPE_DATE, dateValue);

            inspectorVariableOperateHandler.remove(
                    new InspectorVariableRemoveInfo(inspectorInfo.getKey(), inspectorVariableId)
            );
            assertNull(inspectorVariableOperateHandler.inspect(inspectInfo));
        } finally {
            if (Objects.nonNull(inspectorInfo.getKey())) {
                inspectorVariableMaintainService.deleteIfExists(new InspectorVariableKey(
                        inspectorInfo.getKey().getLongId(), inspectorVariableId
                ));
            }
            if (Objects.nonNull(inspectorInfo.getKey())) {
                inspectorInfoMaintainService.deleteIfExists(inspectorInfo.getKey());
            }
            if (Objects.nonNull(inspection.getKey())) {
                inspectionMaintainService.deleteIfExists(inspection.getKey());
            }
        }
    }

    @Test
    public void testUpsertWithInvalidValue() throws Exception {
        Inspection inspection = new Inspection(null, "inspection", true, "remark");
        InspectorInfo inspectorInfo = new InspectorInfo(null, null, 0, true, "type", "param", "remark");
        try {
            inspection.setKey(inspectionMaintainService.insertOrUpdate(inspection));
            inspectorInfo.setInspectionKey(inspection.getKey());
            inspectorInfo.setKey(inspectorInfoMaintainService.insertOrUpdate(inspectorInfo));

            try {
                inspectorVariableOperateHandler.upsert(new InspectorVariableUpsertInfo(
                        inspectorInfo.getKey(), "variable", -1, "value"
                ));
                fail("应抛出 InvalidVariableValueTypeException");
            } catch (InvalidVariableValueTypeException ignored) {
            }

            try {
                inspectorVariableOperateHandler.upsert(new InspectorVariableUpsertInfo(
                        inspectorInfo.getKey(), "variable", Constants.INSPECTOR_VARIABLE_VALUE_TYPE_STRING, 1L
                ));
                fail("应抛出 VariableValueTypeMismatchException");
            } catch (VariableValueTypeMismatchException ignored) {
            }
        } finally {
            if (Objects.nonNull(inspectorInfo.getKey())) {
                inspectorVariableMaintainService.deleteIfExists(new InspectorVariableKey(
                        inspectorInfo.getKey().getLongId(), "variable"
                ));
            }
            if (Objects.nonNull(inspectorInfo.getKey())) {
                inspectorInfoMaintainService.deleteIfExists(inspectorInfo.getKey());
            }
            if (Objects.nonNull(inspection.getKey())) {
                inspectionMaintainService.deleteIfExists(inspection.getKey());
            }
        }
    }

    private void assertUpsertAndInspect(
            LongIdKey inspectorInfoKey, String inspectorVariableId, int valueType, Object value
    ) throws Exception {
        inspectorVariableOperateHandler.upsert(new InspectorVariableUpsertInfo(
                inspectorInfoKey, inspectorVariableId, valueType, value
        ));
        InspectorVariableInspectResult result = inspectorVariableOperateHandler.inspect(
                new InspectorVariableInspectInfo(inspectorInfoKey, inspectorVariableId)
        );
        assertNotNull(result);
        assertEquals(valueType, result.getValueType());
        assertEquals(value, result.getValue());
    }
}
