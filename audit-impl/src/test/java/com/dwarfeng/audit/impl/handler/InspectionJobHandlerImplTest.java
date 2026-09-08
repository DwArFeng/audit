package com.dwarfeng.audit.impl.handler;

import com.dwarfeng.audit.impl.handler.inspector.groovy.GroovyInspectorRegistry;
import com.dwarfeng.audit.sdk.util.Constants;
import com.dwarfeng.audit.stack.bean.entity.Inspection;
import com.dwarfeng.audit.stack.bean.entity.InspectionTask;
import com.dwarfeng.audit.stack.bean.entity.InspectorInfo;
import com.dwarfeng.audit.stack.handler.InspectionJobHandler;
import com.dwarfeng.audit.stack.service.InspectionMaintainService;
import com.dwarfeng.audit.stack.service.InspectionTaskMaintainService;
import com.dwarfeng.audit.stack.service.InspectorInfoMaintainService;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Objects;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * {@link InspectionJobHandlerImpl} 的集成测试。
 *
 * @author DwArFeng
 * @since 1.1.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring/application-context*.xml")
public class InspectionJobHandlerImplTest {

    @Autowired
    private InspectionMaintainService inspectionMaintainService;
    @Autowired
    private InspectionTaskMaintainService inspectionTaskMaintainService;
    @Autowired
    private InspectorInfoMaintainService inspectorInfoMaintainService;
    @Autowired
    private InspectionJobHandler inspectionJobHandler;

    @Test
    public void testExecute() throws Exception {
        Inspection inspection = new Inspection(null, "inspection", true, "remark");
        InspectorInfo inspectorInfo = null;
        LongIdKey taskKey = null;
        try {
            inspection.setKey(inspectionMaintainService.insertOrUpdate(inspection));
            String script = "import com.dwarfeng.audit.impl.handler.inspector.groovy.Processor\n" +
                    "import com.dwarfeng.audit.stack.handler.Inspector\n" +
                    "class JobTestProcessor implements Processor {\n" +
                    "  void inspect(Inspector.Context context) { }\n" +
                    "}\n";
            inspectorInfo = new InspectorInfo(
                    null, inspection.getKey(), 0, true, GroovyInspectorRegistry.INSPECTOR_TYPE, script, "remark"
            );
            inspectorInfo.setKey(inspectorInfoMaintainService.insertOrUpdate(inspectorInfo));

            taskKey = inspectionJobHandler.create(
                    new com.dwarfeng.audit.stack.bean.dto.InspectionJobCreateInfo(inspection.getKey())
            ).getInspectionTaskKey();
            inspectionJobHandler.execute(
                    new com.dwarfeng.audit.stack.bean.dto.InspectionJobExecuteInfo(taskKey)
            );

            InspectionTask task = inspectionTaskMaintainService.get(taskKey);
            assertEquals(Constants.INSPECTION_TASK_STATUS_FINISHED, task.getStatus());
            assertNotNull(task.getStartedDate());
            assertNotNull(task.getEndedDate());
            assertNotNull(task.getDuration());
        } finally {
            if (Objects.nonNull(taskKey)) {
                inspectionTaskMaintainService.deleteIfExists(taskKey);
            }
            if (Objects.nonNull(inspectorInfo) && Objects.nonNull(inspectorInfo.getKey())) {
                inspectorInfoMaintainService.deleteIfExists(inspectorInfo.getKey());
            }
            if (Objects.nonNull(inspection.getKey())) {
                inspectionMaintainService.deleteIfExists(inspection.getKey());
            }
        }
    }
}
