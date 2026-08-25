package com.dwarfeng.audit.impl.configuration;

import com.dwarfeng.audit.impl.bean.BeanMapper;
import com.dwarfeng.audit.impl.bean.entity.HibernateAuditCategory;
import com.dwarfeng.audit.impl.bean.entity.HibernateAuditEntry;
import com.dwarfeng.audit.impl.bean.entity.HibernateAuditEntryProperty;
import com.dwarfeng.audit.impl.bean.entity.HibernateAuditPropertyIndicator;
import com.dwarfeng.audit.impl.bean.key.HibernateAuditEntryPropertyKey;
import com.dwarfeng.audit.impl.bean.key.HibernateAuditPropertyIndicatorKey;
import com.dwarfeng.audit.impl.dao.preset.*;
import com.dwarfeng.audit.stack.bean.entity.AuditCategory;
import com.dwarfeng.audit.stack.bean.entity.AuditEntry;
import com.dwarfeng.audit.stack.bean.entity.AuditEntryProperty;
import com.dwarfeng.audit.stack.bean.entity.AuditPropertyIndicator;
import com.dwarfeng.audit.stack.bean.key.AuditEntryPropertyKey;
import com.dwarfeng.audit.stack.bean.key.AuditPropertyIndicatorKey;
import com.dwarfeng.subgrade.impl.bean.MapStructBeanTransformer;
import com.dwarfeng.subgrade.impl.dao.HibernateBatchBaseDao;
import com.dwarfeng.subgrade.impl.dao.HibernateEntireLookupDao;
import com.dwarfeng.subgrade.impl.dao.HibernateHqlPresetLookupDao;
import com.dwarfeng.subgrade.impl.dao.HibernatePresetLookupDao;
import com.dwarfeng.subgrade.sdk.bean.key.HibernateLongIdKey;
import com.dwarfeng.subgrade.sdk.bean.key.HibernateStringIdKey;
import com.dwarfeng.subgrade.sdk.hibernate.modification.DefaultDeletionMod;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.hibernate5.HibernateTemplate;

@Configuration
public class DaoConfiguration {

    private final HibernateTemplate template;

    private final AuditCategoryPresetCriteriaMaker auditCategoryPresetCriteriaMaker;
    private final AuditPropertyIndicatorPresetCriteriaMaker auditPropertyIndicatorPresetCriteriaMaker;
    private final AuditEntryPresetCriteriaMaker auditEntryPresetCriteriaMaker;
    private final AuditEntryPresetConditionMaker auditEntryPresetConditionMaker;
    private final AuditEntryPropertyPresetCriteriaMaker auditEntryPropertyPresetCriteriaMaker;

    @Value("${com.dwarfeng.audit.hibernate.jdbc.batch_size}")
    private int batchSize;

    public DaoConfiguration(
            HibernateTemplate template,
            AuditCategoryPresetCriteriaMaker auditCategoryPresetCriteriaMaker,
            AuditPropertyIndicatorPresetCriteriaMaker auditPropertyIndicatorPresetCriteriaMaker,
            AuditEntryPresetCriteriaMaker auditEntryPresetCriteriaMaker,
            AuditEntryPresetConditionMaker auditEntryPresetConditionMaker,
            AuditEntryPropertyPresetCriteriaMaker auditEntryPropertyPresetCriteriaMaker
    ) {
        this.template = template;
        this.auditCategoryPresetCriteriaMaker = auditCategoryPresetCriteriaMaker;
        this.auditPropertyIndicatorPresetCriteriaMaker = auditPropertyIndicatorPresetCriteriaMaker;
        this.auditEntryPresetCriteriaMaker = auditEntryPresetCriteriaMaker;
        this.auditEntryPresetConditionMaker = auditEntryPresetConditionMaker;
        this.auditEntryPropertyPresetCriteriaMaker = auditEntryPropertyPresetCriteriaMaker;
    }

    @Bean
    public HibernateBatchBaseDao<StringIdKey, HibernateStringIdKey, AuditCategory, HibernateAuditCategory>
    auditCategoryHibernateBatchBaseDao() {
        return new HibernateBatchBaseDao<>(
                template,
                new MapStructBeanTransformer<>(StringIdKey.class, HibernateStringIdKey.class, BeanMapper.class),
                new MapStructBeanTransformer<>(AuditCategory.class, HibernateAuditCategory.class, BeanMapper.class),
                HibernateAuditCategory.class,
                new DefaultDeletionMod<>(),
                batchSize
        );
    }

    @Bean
    public HibernateEntireLookupDao<AuditCategory, HibernateAuditCategory> auditCategoryHibernateEntireLookupDao() {
        return new HibernateEntireLookupDao<>(
                template,
                new MapStructBeanTransformer<>(AuditCategory.class, HibernateAuditCategory.class, BeanMapper.class),
                HibernateAuditCategory.class
        );
    }

    @Bean
    public HibernatePresetLookupDao<AuditCategory, HibernateAuditCategory> auditCategoryHibernatePresetLookupDao() {
        return new HibernatePresetLookupDao<>(
                template,
                new MapStructBeanTransformer<>(AuditCategory.class, HibernateAuditCategory.class, BeanMapper.class),
                HibernateAuditCategory.class,
                auditCategoryPresetCriteriaMaker
        );
    }

    @Bean
    public HibernateBatchBaseDao<AuditPropertyIndicatorKey, HibernateAuditPropertyIndicatorKey, AuditPropertyIndicator,
            HibernateAuditPropertyIndicator> auditPropertyIndicatorHibernateBatchBaseDao() {
        return new HibernateBatchBaseDao<>(
                template,
                new MapStructBeanTransformer<>(
                        AuditPropertyIndicatorKey.class, HibernateAuditPropertyIndicatorKey.class, BeanMapper.class
                ),
                new MapStructBeanTransformer<>(
                        AuditPropertyIndicator.class, HibernateAuditPropertyIndicator.class, BeanMapper.class
                ),
                HibernateAuditPropertyIndicator.class,
                new DefaultDeletionMod<>(),
                batchSize
        );
    }

    @Bean
    public HibernateEntireLookupDao<AuditPropertyIndicator, HibernateAuditPropertyIndicator>
    auditPropertyIndicatorHibernateEntireLookupDao() {
        return new HibernateEntireLookupDao<>(
                template,
                new MapStructBeanTransformer<>(
                        AuditPropertyIndicator.class, HibernateAuditPropertyIndicator.class, BeanMapper.class
                ),
                HibernateAuditPropertyIndicator.class
        );
    }

    @Bean
    public HibernatePresetLookupDao<AuditPropertyIndicator, HibernateAuditPropertyIndicator>
    auditPropertyIndicatorHibernatePresetLookupDao() {
        return new HibernatePresetLookupDao<>(
                template,
                new MapStructBeanTransformer<>(
                        AuditPropertyIndicator.class, HibernateAuditPropertyIndicator.class, BeanMapper.class
                ),
                HibernateAuditPropertyIndicator.class,
                auditPropertyIndicatorPresetCriteriaMaker
        );
    }

    @Bean
    public HibernateBatchBaseDao<LongIdKey, HibernateLongIdKey, AuditEntry, HibernateAuditEntry>
    auditEntriesupportHibernateBatchBaseDao() {
        return new HibernateBatchBaseDao<>(
                template,
                new MapStructBeanTransformer<>(LongIdKey.class, HibernateLongIdKey.class, BeanMapper.class),
                new MapStructBeanTransformer<>(AuditEntry.class, HibernateAuditEntry.class, BeanMapper.class),
                HibernateAuditEntry.class,
                new DefaultDeletionMod<>(),
                batchSize
        );
    }

    @Bean
    public HibernateEntireLookupDao<AuditEntry, HibernateAuditEntry> auditEntriesupportHibernateEntireLookupDao() {
        return new HibernateEntireLookupDao<>(
                template,
                new MapStructBeanTransformer<>(AuditEntry.class, HibernateAuditEntry.class, BeanMapper.class),
                HibernateAuditEntry.class
        );
    }

    @Bean
    public HibernatePresetLookupDao<AuditEntry, HibernateAuditEntry> auditEntriesupportHibernatePresetLookupDao() {
        return new HibernatePresetLookupDao<>(
                template,
                new MapStructBeanTransformer<>(AuditEntry.class, HibernateAuditEntry.class, BeanMapper.class),
                HibernateAuditEntry.class,
                auditEntryPresetCriteriaMaker
        );
    }

    @Bean
    public HibernateHqlPresetLookupDao<AuditEntry, HibernateAuditEntry>
    auditEntriesupportHibernateHqlPresetLookupDao() {
        return new HibernateHqlPresetLookupDao<>(
                template,
                new MapStructBeanTransformer<>(AuditEntry.class, HibernateAuditEntry.class, BeanMapper.class),
                HibernateAuditEntry.class,
                auditEntryPresetConditionMaker
        );
    }

    @Bean
    public HibernateBatchBaseDao<AuditEntryPropertyKey, HibernateAuditEntryPropertyKey, AuditEntryProperty,
            HibernateAuditEntryProperty> auditEntryPropertyHibernateBatchBaseDao() {
        return new HibernateBatchBaseDao<>(
                template,
                new MapStructBeanTransformer<>(
                        AuditEntryPropertyKey.class, HibernateAuditEntryPropertyKey.class, BeanMapper.class
                ),
                new MapStructBeanTransformer<>(
                        AuditEntryProperty.class, HibernateAuditEntryProperty.class, BeanMapper.class
                ),
                HibernateAuditEntryProperty.class,
                new DefaultDeletionMod<>(),
                batchSize
        );
    }

    @Bean
    public HibernateEntireLookupDao<AuditEntryProperty, HibernateAuditEntryProperty>
    auditEntryPropertyHibernateEntireLookupDao() {
        return new HibernateEntireLookupDao<>(
                template,
                new MapStructBeanTransformer<>(
                        AuditEntryProperty.class, HibernateAuditEntryProperty.class, BeanMapper.class
                ),
                HibernateAuditEntryProperty.class
        );
    }

    @Bean
    public HibernatePresetLookupDao<AuditEntryProperty, HibernateAuditEntryProperty>
    auditEntryPropertyHibernatePresetLookupDao() {
        return new HibernatePresetLookupDao<>(
                template,
                new MapStructBeanTransformer<>(
                        AuditEntryProperty.class, HibernateAuditEntryProperty.class, BeanMapper.class
                ),
                HibernateAuditEntryProperty.class,
                auditEntryPropertyPresetCriteriaMaker
        );
    }
}
