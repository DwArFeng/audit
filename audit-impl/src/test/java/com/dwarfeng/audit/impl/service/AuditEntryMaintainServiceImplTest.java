package com.dwarfeng.audit.impl.service;

import com.dwarfeng.audit.stack.bean.entity.AuditCategory;
import com.dwarfeng.audit.stack.bean.entity.AuditEntry;
import com.dwarfeng.audit.stack.service.AuditCategoryMaintainService;
import com.dwarfeng.audit.stack.service.AuditEntryMaintainService;
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
public class AuditEntryMaintainServiceImplTest {

    @Autowired
    private AuditCategoryMaintainService auditCategoryMaintainService;
    @Autowired
    private AuditEntryMaintainService auditEntryMaintainService;

    private AuditCategory auditCategory;
    private List<AuditEntry> auditEntries;

    @Before
    public void setUp() {
        auditCategory = new AuditCategory(null, true, "test_category_" + UUID.randomUUID(), "remark");
        auditEntries = new ArrayList<>();
        Date now = new Date();
        for (int i = 0; i < 5; i++) {
            AuditEntry auditEntry = new AuditEntry(null, null, now);
            auditEntries.add(auditEntry);
        }
    }

    @After
    public void tearDown() {
        auditCategory = null;
        auditEntries.clear();
    }

    @Test
    public void testForCrud() throws Exception {
        try {
            auditCategory.setKey(auditCategoryMaintainService.insert(auditCategory));
            AuditEntry auditEntry = auditEntries.get(0);
            auditEntry.setCategoryKey(auditCategory.getKey());
            auditEntry.setKey(auditEntryMaintainService.insert(auditEntry));
            assertTrue(auditEntryMaintainService.exists(auditEntry.getKey()));
            AuditEntry testAuditEntry = auditEntryMaintainService.get(auditEntry.getKey());
            assertEquals(BeanUtils.describe(auditEntry), BeanUtils.describe(testAuditEntry));

            auditEntryMaintainService.deleteIfExists(auditEntry.getKey());
            assertFalse(auditEntryMaintainService.exists(auditEntry.getKey()));
        } finally {
            for (AuditEntry auditEntry : auditEntries) {
                if (Objects.isNull(auditEntry.getKey())) {
                    continue;
                }
                auditEntryMaintainService.deleteIfExists(auditEntry.getKey());
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
            for (AuditEntry auditEntry : auditEntries) {
                auditEntry.setCategoryKey(auditCategory.getKey());
                auditEntry.setKey(auditEntryMaintainService.insert(auditEntry));
            }

            assertEquals(
                    auditEntries.size(),
                    auditEntryMaintainService.lookupAsList(
                            AuditEntryMaintainService.CHILD_FOR_AUDIT_CATEGORY,
                            new Object[]{auditCategory.getKey()}
                    ).size()
            );

            auditCategoryMaintainService.deleteIfExists(auditCategory.getKey());

            assertEquals(
                    0,
                    auditEntryMaintainService.lookupAsList(
                            AuditEntryMaintainService.CHILD_FOR_AUDIT_CATEGORY,
                            new Object[]{auditCategory.getKey()}
                    ).size()
            );

            for (AuditEntry auditEntry : auditEntries) {
                assertTrue(auditEntryMaintainService.exists(auditEntry.getKey()));
                AuditEntry testAuditEntry = auditEntryMaintainService.get(auditEntry.getKey());
                assertNull(testAuditEntry.getCategoryKey());
            }
        } finally {
            for (AuditEntry auditEntry : auditEntries) {
                if (Objects.isNull(auditEntry.getKey())) {
                    continue;
                }
                auditEntryMaintainService.deleteIfExists(auditEntry.getKey());
            }
            if (Objects.nonNull(auditCategory.getKey())) {
                auditCategoryMaintainService.deleteIfExists(auditCategory.getKey());
            }
        }
    }
}
