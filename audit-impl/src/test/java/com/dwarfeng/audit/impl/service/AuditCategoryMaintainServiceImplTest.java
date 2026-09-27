package com.dwarfeng.audit.impl.service;

import com.dwarfeng.audit.stack.bean.entity.AuditCategory;
import com.dwarfeng.audit.stack.service.AuditCategoryMaintainService;
import org.apache.commons.beanutils.BeanUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring/application-context*.xml")
public class AuditCategoryMaintainServiceImplTest {

    @Autowired
    private AuditCategoryMaintainService auditCategoryMaintainService;

    private AuditCategory auditCategory;

    @Before
    public void setUp() {
        auditCategory = new AuditCategory(null, true, "test_category_" + UUID.randomUUID(), "remark");
    }

    @After
    public void tearDown() {
        auditCategory = null;
    }

    @Test
    public void testForCrud() throws Exception {
        try {
            auditCategory.setKey(auditCategoryMaintainService.insert(auditCategory));
            assertTrue(auditCategoryMaintainService.exists(auditCategory.getKey()));
            AuditCategory testAuditCategory = auditCategoryMaintainService.get(auditCategory.getKey());
            assertEquals(BeanUtils.describe(auditCategory), BeanUtils.describe(testAuditCategory));

            auditCategory.setRemark("updated remark");
            auditCategoryMaintainService.update(auditCategory);
            testAuditCategory = auditCategoryMaintainService.get(auditCategory.getKey());
            assertEquals(BeanUtils.describe(auditCategory), BeanUtils.describe(testAuditCategory));

            auditCategoryMaintainService.deleteIfExists(auditCategory.getKey());
            assertFalse(auditCategoryMaintainService.exists(auditCategory.getKey()));
        } finally {
            if (Objects.nonNull(auditCategory.getKey())) {
                auditCategoryMaintainService.deleteIfExists(auditCategory.getKey());
            }
        }
    }

    @Test
    public void testForIdLikeAndNameLike() throws Exception {
        try {
            auditCategory.setKey(auditCategoryMaintainService.insert(auditCategory));

            List<AuditCategory> idLikeResult = auditCategoryMaintainService.lookupAsList(
                    AuditCategoryMaintainService.ID_LIKE,
                    new Object[]{auditCategory.getKey().getStringId()}
            );
            assertEquals(1, idLikeResult.size());
            assertEquals(BeanUtils.describe(auditCategory), BeanUtils.describe(idLikeResult.get(0)));

            List<AuditCategory> nameLikeResult = auditCategoryMaintainService.lookupAsList(
                    AuditCategoryMaintainService.NAME_LIKE,
                    new Object[]{auditCategory.getName()}
            );
            assertEquals(1, nameLikeResult.size());
            assertEquals(BeanUtils.describe(auditCategory), BeanUtils.describe(nameLikeResult.get(0)));
        } finally {
            if (Objects.nonNull(auditCategory.getKey())) {
                auditCategoryMaintainService.deleteIfExists(auditCategory.getKey());
            }
        }
    }
}
