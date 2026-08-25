package com.dwarfeng.audit.impl.service;

import com.dwarfeng.audit.stack.bean.entity.AuditCategory;
import com.dwarfeng.audit.stack.bean.entity.AuditEntry;
import com.dwarfeng.audit.stack.bean.entity.AuditEntryProperty;
import com.dwarfeng.audit.stack.bean.key.AuditEntryPropertyKey;
import com.dwarfeng.audit.stack.service.AuditCategoryMaintainService;
import com.dwarfeng.audit.stack.service.AuditEntryMaintainService;
import com.dwarfeng.audit.stack.service.AuditEntryPropertyMaintainService;
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
public class AuditEntryPropertyMaintainServiceImplTest {

    @Autowired
    private AuditCategoryMaintainService auditCategoryMaintainService;
    @Autowired
    private AuditEntryMaintainService auditEntryMaintainService;
    @Autowired
    private AuditEntryPropertyMaintainService auditEntryPropertyMaintainService;

    private AuditCategory auditCategory;
    private AuditEntry auditEntry;
    private List<AuditEntryProperty> auditEntryProperties;

    @Before
    public void setUp() {
        auditCategory = new AuditCategory(null, true, "test_category_" + UUID.randomUUID(), "remark");
        Date now = new Date();
        auditEntry = new AuditEntry(null, null, now);
        auditEntryProperties = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            AuditEntryProperty auditEntryProperty = new AuditEntryProperty(
                    null, 0, "string_value_" + i, 1L, 1.0, true, now
            );
            auditEntryProperties.add(auditEntryProperty);
        }
    }

    @After
    public void tearDown() {
        auditCategory = null;
        auditEntry = null;
        auditEntryProperties.clear();
    }

    @Test
    public void testForCrud() throws Exception {
        try {
            auditCategory.setKey(auditCategoryMaintainService.insert(auditCategory));
            auditEntry.setCategoryKey(auditCategory.getKey());
            auditEntry.setKey(auditEntryMaintainService.insert(auditEntry));

            AuditEntryProperty auditEntryProperty = auditEntryProperties.get(0);
            auditEntryProperty.setKey(new AuditEntryPropertyKey(
                    auditEntry.getKey().getLongId(), "test.property_id." + UUID.randomUUID()
            ));
            auditEntryPropertyMaintainService.insertOrUpdate(auditEntryProperty);
            assertTrue(auditEntryPropertyMaintainService.exists(auditEntryProperty.getKey()));
            AuditEntryProperty testAuditEntryProperty = auditEntryPropertyMaintainService.get(
                    auditEntryProperty.getKey()
            );
            assertEquals(BeanUtils.describe(auditEntryProperty), BeanUtils.describe(testAuditEntryProperty));

            auditEntryProperty.setStringValue("updated string value");
            auditEntryPropertyMaintainService.update(auditEntryProperty);
            testAuditEntryProperty = auditEntryPropertyMaintainService.get(auditEntryProperty.getKey());
            assertEquals(BeanUtils.describe(auditEntryProperty), BeanUtils.describe(testAuditEntryProperty));

            auditEntryPropertyMaintainService.deleteIfExists(auditEntryProperty.getKey());
            assertFalse(auditEntryPropertyMaintainService.exists(auditEntryProperty.getKey()));
        } finally {
            for (AuditEntryProperty auditEntryProperty : auditEntryProperties) {
                if (Objects.isNull(auditEntryProperty.getKey())) {
                    continue;
                }
                auditEntryPropertyMaintainService.deleteIfExists(auditEntryProperty.getKey());
            }
            if (Objects.nonNull(auditEntry.getKey())) {
                auditEntryMaintainService.deleteIfExists(auditEntry.getKey());
            }
            if (Objects.nonNull(auditCategory.getKey())) {
                auditCategoryMaintainService.deleteIfExists(auditCategory.getKey());
            }
        }
    }

    @Test
    public void testForAuditEntryCascade() throws Exception {
        try {
            auditCategory.setKey(auditCategoryMaintainService.insert(auditCategory));
            auditEntry.setCategoryKey(auditCategory.getKey());
            auditEntry.setKey(auditEntryMaintainService.insert(auditEntry));

            for (int i = 0; i < auditEntryProperties.size(); i++) {
                AuditEntryProperty auditEntryProperty = auditEntryProperties.get(i);
                auditEntryProperty.setKey(new AuditEntryPropertyKey(
                        auditEntry.getKey().getLongId(), "test.property_id." + i + "." + UUID.randomUUID()
                ));
                auditEntryPropertyMaintainService.insertOrUpdate(auditEntryProperty);
            }

            assertEquals(
                    auditEntryProperties.size(),
                    auditEntryPropertyMaintainService.lookupAsList(
                            AuditEntryPropertyMaintainService.CHILD_FOR_AUDIT_ENTRY,
                            new Object[]{auditEntry.getKey()}
                    ).size()
            );

            auditEntryMaintainService.deleteIfExists(auditEntry.getKey());

            assertEquals(
                    0,
                    auditEntryPropertyMaintainService.lookupAsList(
                            AuditEntryPropertyMaintainService.CHILD_FOR_AUDIT_ENTRY,
                            new Object[]{auditEntry.getKey()}
                    ).size()
            );

            for (AuditEntryProperty auditEntryProperty : auditEntryProperties) {
                assertFalse(auditEntryPropertyMaintainService.exists(auditEntryProperty.getKey()));
            }
        } finally {
            for (AuditEntryProperty auditEntryProperty : auditEntryProperties) {
                if (Objects.isNull(auditEntryProperty.getKey())) {
                    continue;
                }
                auditEntryPropertyMaintainService.deleteIfExists(auditEntryProperty.getKey());
            }
            if (Objects.nonNull(auditEntry.getKey())) {
                auditEntryMaintainService.deleteIfExists(auditEntry.getKey());
            }
            if (Objects.nonNull(auditCategory.getKey())) {
                auditCategoryMaintainService.deleteIfExists(auditCategory.getKey());
            }
        }
    }
}
