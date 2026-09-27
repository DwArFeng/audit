package com.dwarfeng.audit.impl.service;

import com.dwarfeng.audit.stack.bean.entity.InspectorSupport;
import com.dwarfeng.audit.stack.service.InspectorSupportMaintainService;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import org.apache.commons.beanutils.BeanUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;
import java.util.UUID;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring/application-context*.xml")
public class InspectorSupportMaintainServiceImplTest {

    @Autowired
    private InspectorSupportMaintainService inspectorSupportMaintainService;

    @Test
    public void testForCrud() throws Exception {
        InspectorSupport support = new InspectorSupport(
                new StringIdKey("test-" + UUID.randomUUID()), "label", "description", "exampleParam"
        );
        try {
            support.setKey(inspectorSupportMaintainService.insertOrUpdate(support));
            assertTrue(inspectorSupportMaintainService.exists(support.getKey()));
            assertEquals(
                    BeanUtils.describe(support),
                    BeanUtils.describe(inspectorSupportMaintainService.get(support.getKey()))
            );
            support.setDescription("updated");
            inspectorSupportMaintainService.update(support);
            assertEquals(
                    BeanUtils.describe(support),
                    BeanUtils.describe(inspectorSupportMaintainService.get(support.getKey()))
            );
            inspectorSupportMaintainService.deleteIfExists(support.getKey());
            assertFalse(inspectorSupportMaintainService.exists(support.getKey()));
        } finally {
            if (support.getKey() != null) {
                inspectorSupportMaintainService.deleteIfExists(support.getKey());
            }
        }
    }

    @Test
    public void testForIdLikeAndLabelLike() throws Exception {
        InspectorSupport support = new InspectorSupport(
                new StringIdKey("test-" + UUID.randomUUID()), "label-" + UUID.randomUUID(),
                "description", "exampleParam"
        );
        try {
            support.setKey(inspectorSupportMaintainService.insertOrUpdate(support));

            List<InspectorSupport> idLikeResult = inspectorSupportMaintainService.lookupAsList(
                    InspectorSupportMaintainService.ID_LIKE,
                    new Object[]{support.getKey().getStringId()}
            );
            assertEquals(1, idLikeResult.size());
            assertEquals(BeanUtils.describe(support), BeanUtils.describe(idLikeResult.get(0)));

            List<InspectorSupport> labelLikeResult = inspectorSupportMaintainService.lookupAsList(
                    InspectorSupportMaintainService.LABEL_LIKE,
                    new Object[]{support.getLabel()}
            );
            assertEquals(1, labelLikeResult.size());
            assertEquals(BeanUtils.describe(support), BeanUtils.describe(labelLikeResult.get(0)));
        } finally {
            if (support.getKey() != null) {
                inspectorSupportMaintainService.deleteIfExists(support.getKey());
            }
        }
    }
}
