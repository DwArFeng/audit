package com.dwarfeng.audit.impl.handler;

import com.dwarfeng.audit.stack.bean.entity.Inspection;
import com.dwarfeng.audit.stack.handler.InspectionDriveLocalCacheHandler;
import com.dwarfeng.audit.stack.handler.InspectionJobLocalCacheHandler;
import com.dwarfeng.audit.stack.handler.ResetHandler;
import com.dwarfeng.audit.stack.service.InspectionMaintainService;
import com.dwarfeng.audit.stack.struct.InspectionDriveLocalCache;
import com.dwarfeng.audit.stack.struct.InspectionJobLocalCache;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertNotSame;

/**
 * {@link ResetHandlerImpl} 的集成测试。
 *
 * @author DwArFeng
 * @since 1.1.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring/application-context*.xml")
public class ResetHandlerImplTest {

    @Autowired
    private InspectionMaintainService inspectionMaintainService;
    @Autowired
    private InspectionDriveLocalCacheHandler inspectionDriveLocalCacheHandler;
    @Autowired
    private InspectionJobLocalCacheHandler inspectionJobLocalCacheHandler;
    @Autowired
    private ResetHandler resetHandler;

    @Test
    public void testResetInspectionJob() throws Exception {
        Inspection inspection = new Inspection(null, "inspection", true, "remark");
        try {
            inspection.setKey(inspectionMaintainService.insertOrUpdate(inspection));

            InspectionJobLocalCache beforeReset = inspectionJobLocalCacheHandler.get(inspection.getKey());
            resetHandler.resetInspectionJob();
            InspectionJobLocalCache afterReset = inspectionJobLocalCacheHandler.get(inspection.getKey());

            assertNotSame(beforeReset, afterReset);
        } finally {
            if (inspection.getKey() != null) {
                inspectionMaintainService.deleteIfExists(inspection.getKey());
            }
        }
    }

    @Test
    public void testResetInspectionSupervise() throws Exception {
        Inspection inspection = new Inspection(null, "inspection", true, "remark");
        try {
            inspection.setKey(inspectionMaintainService.insertOrUpdate(inspection));

            InspectionDriveLocalCache beforeReset = inspectionDriveLocalCacheHandler.get(inspection.getKey());
            resetHandler.resetInspectionSupervise();
            InspectionDriveLocalCache afterReset = inspectionDriveLocalCacheHandler.get(inspection.getKey());

            assertNotSame(beforeReset, afterReset);
        } finally {
            if (inspection.getKey() != null) {
                inspectionMaintainService.deleteIfExists(inspection.getKey());
            }
        }
    }
}
