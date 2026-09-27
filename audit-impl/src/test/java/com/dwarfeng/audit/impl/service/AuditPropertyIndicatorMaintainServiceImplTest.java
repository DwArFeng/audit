package com.dwarfeng.audit.impl.service;

import com.dwarfeng.audit.stack.bean.entity.AuditCategory;
import com.dwarfeng.audit.stack.bean.entity.AuditPropertyIndicator;
import com.dwarfeng.audit.stack.bean.key.AuditPropertyIndicatorKey;
import com.dwarfeng.audit.stack.service.AuditCategoryMaintainService;
import com.dwarfeng.audit.stack.service.AuditPropertyIndicatorMaintainService;
import org.apache.commons.beanutils.BeanUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.*;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring/application-context*.xml")
public class AuditPropertyIndicatorMaintainServiceImplTest {

    @Autowired
    private AuditCategoryMaintainService auditCategoryMaintainService;
    @Autowired
    private AuditPropertyIndicatorMaintainService auditPropertyIndicatorMaintainService;

    private AuditCategory auditCategory;
    private List<AuditPropertyIndicator> auditPropertyIndicators;

    @Before
    public void setUp() {
        auditCategory = new AuditCategory(null, true, "test_category_" + UUID.randomUUID(), "remark");
        auditPropertyIndicators = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            AuditPropertyIndicator auditPropertyIndicator = new AuditPropertyIndicator(
                    null, "label_" + i, 0, "default_string", 1L, 1.0, true, new Date(), i
            );
            auditPropertyIndicators.add(auditPropertyIndicator);
        }
    }

    @After
    public void tearDown() {
        auditCategory = null;
        auditPropertyIndicators.clear();
    }

    @Test
    public void testForCrud() throws Exception {
        try {
            auditCategory.setKey(auditCategoryMaintainService.insert(auditCategory));
            AuditPropertyIndicator auditPropertyIndicator = auditPropertyIndicators.get(0);
            auditPropertyIndicator.setKey(new AuditPropertyIndicatorKey(
                    auditCategory.getKey().getStringId(), "test.property_id." + UUID.randomUUID()
            ));
            auditPropertyIndicatorMaintainService.insertOrUpdate(auditPropertyIndicator);
            assertTrue(auditPropertyIndicatorMaintainService.exists(auditPropertyIndicator.getKey()));
            AuditPropertyIndicator testAuditPropertyIndicator = auditPropertyIndicatorMaintainService.get(
                    auditPropertyIndicator.getKey()
            );
            assertEquals(
                    BeanUtils.describe(auditPropertyIndicator), BeanUtils.describe(testAuditPropertyIndicator)
            );

            auditPropertyIndicator.setLabel("updated label");
            auditPropertyIndicatorMaintainService.update(auditPropertyIndicator);
            testAuditPropertyIndicator = auditPropertyIndicatorMaintainService.get(auditPropertyIndicator.getKey());
            assertEquals(
                    BeanUtils.describe(auditPropertyIndicator), BeanUtils.describe(testAuditPropertyIndicator)
            );

            auditPropertyIndicatorMaintainService.deleteIfExists(auditPropertyIndicator.getKey());
            assertFalse(auditPropertyIndicatorMaintainService.exists(auditPropertyIndicator.getKey()));
        } finally {
            for (AuditPropertyIndicator auditPropertyIndicator : auditPropertyIndicators) {
                if (Objects.isNull(auditPropertyIndicator.getKey())) {
                    continue;
                }
                auditPropertyIndicatorMaintainService.deleteIfExists(auditPropertyIndicator.getKey());
            }
            if (Objects.nonNull(auditCategory.getKey())) {
                auditCategoryMaintainService.deleteIfExists(auditCategory.getKey());
            }
        }
    }

    @Test
    public void testForAuditCategoryCascade() throws Exception {
        try {
            auditCategory.setKey(auditCategoryMaintainService.insert(auditCategory));
            for (int i = 0; i < auditPropertyIndicators.size(); i++) {
                AuditPropertyIndicator auditPropertyIndicator = auditPropertyIndicators.get(i);
                auditPropertyIndicator.setKey(new AuditPropertyIndicatorKey(
                        auditCategory.getKey().getStringId(), "test.property_id." + i + "." + UUID.randomUUID()
                ));
                auditPropertyIndicatorMaintainService.insertOrUpdate(auditPropertyIndicator);
            }

            assertEquals(
                    auditPropertyIndicators.size(),
                    auditPropertyIndicatorMaintainService.lookupAsList(
                            AuditPropertyIndicatorMaintainService.CHILD_FOR_AUDIT_CATEGORY,
                            new Object[]{auditCategory.getKey()}
                    ).size()
            );

            auditCategoryMaintainService.deleteIfExists(auditCategory.getKey());

            assertEquals(
                    0,
                    auditPropertyIndicatorMaintainService.lookupAsList(
                            AuditPropertyIndicatorMaintainService.CHILD_FOR_AUDIT_CATEGORY,
                            new Object[]{auditCategory.getKey()}
                    ).size()
            );

            for (AuditPropertyIndicator auditPropertyIndicator : auditPropertyIndicators) {
                assertFalse(auditPropertyIndicatorMaintainService.exists(auditPropertyIndicator.getKey()));
            }
        } finally {
            for (AuditPropertyIndicator auditPropertyIndicator : auditPropertyIndicators) {
                if (Objects.isNull(auditPropertyIndicator.getKey())) {
                    continue;
                }
                auditPropertyIndicatorMaintainService.deleteIfExists(auditPropertyIndicator.getKey());
            }
            if (Objects.nonNull(auditCategory.getKey())) {
                auditCategoryMaintainService.deleteIfExists(auditCategory.getKey());
            }
        }
    }

    @Test
    public void testForChildForAuditCategoryOrderAsc() throws Exception {
        try {
            auditCategory.setKey(auditCategoryMaintainService.insert(auditCategory));
            for (int i = auditPropertyIndicators.size() - 1; i >= 0; i--) {
                AuditPropertyIndicator auditPropertyIndicator = auditPropertyIndicators.get(i);
                auditPropertyIndicator.setKey(new AuditPropertyIndicatorKey(
                        auditCategory.getKey().getStringId(), "test.property_id." + i + "." + UUID.randomUUID()
                ));
                auditPropertyIndicatorMaintainService.insertOrUpdate(auditPropertyIndicator);
            }

            List<AuditPropertyIndicator> result = auditPropertyIndicatorMaintainService.lookupAsList(
                    AuditPropertyIndicatorMaintainService.CHILD_FOR_AUDIT_CATEGORY_ORDER_ASC,
                    new Object[]{auditCategory.getKey()}
            );

            assertEquals(auditPropertyIndicators.size(), result.size());
            for (int i = 0; i < result.size(); i++) {
                assertEquals(i, result.get(i).getOrder());
            }
        } finally {
            for (AuditPropertyIndicator auditPropertyIndicator : auditPropertyIndicators) {
                if (Objects.isNull(auditPropertyIndicator.getKey())) {
                    continue;
                }
                auditPropertyIndicatorMaintainService.deleteIfExists(auditPropertyIndicator.getKey());
            }
            if (Objects.nonNull(auditCategory.getKey())) {
                auditCategoryMaintainService.deleteIfExists(auditCategory.getKey());
            }
        }
    }
}
