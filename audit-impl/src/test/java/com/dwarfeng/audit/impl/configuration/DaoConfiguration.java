package com.dwarfeng.audit.impl.configuration;

import com.dwarfeng.audit.impl.bean.BeanMapper;
import com.dwarfeng.audit.impl.bean.entity.*;
import com.dwarfeng.audit.impl.bean.key.HibernateAuditEntryPropertyKey;
import com.dwarfeng.audit.impl.bean.key.HibernateAuditPropertyIndicatorKey;
import com.dwarfeng.audit.impl.bean.key.HibernateInspectorVariableKey;
import com.dwarfeng.audit.impl.dao.preset.*;
import com.dwarfeng.audit.stack.bean.entity.*;
import com.dwarfeng.audit.stack.bean.key.AuditEntryPropertyKey;
import com.dwarfeng.audit.stack.bean.key.AuditPropertyIndicatorKey;
import com.dwarfeng.audit.stack.bean.key.InspectorVariableKey;
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
    private final InspectionAlarmTypeIndicatorPresetCriteriaMaker inspectionAlarmTypeIndicatorPresetCriteriaMaker;
    private final InspectionPresetCriteriaMaker inspectionPresetCriteriaMaker;
    private final InspectionAlarmPresetCriteriaMaker inspectionAlarmPresetCriteriaMaker;
    private final InspectionDriverInfoPresetCriteriaMaker inspectionDriverInfoPresetCriteriaMaker;
    private final InspectionDriverSupportPresetCriteriaMaker inspectionDriverSupportPresetCriteriaMaker;
    private final InspectionTaskPresetCriteriaMaker inspectionTaskPresetCriteriaMaker;
    private final InspectionTaskEventPresetCriteriaMaker inspectionTaskEventPresetCriteriaMaker;
    private final InspectorInfoPresetCriteriaMaker inspectorInfoPresetCriteriaMaker;
    private final InspectorSupportPresetCriteriaMaker inspectorSupportPresetCriteriaMaker;
    private final InspectorVariablePresetCriteriaMaker inspectorVariablePresetCriteriaMaker;

    @Value("${com.dwarfeng.audit.hibernate.jdbc.batch_size}")
    private int batchSize;

    public DaoConfiguration(
            HibernateTemplate template,
            AuditCategoryPresetCriteriaMaker auditCategoryPresetCriteriaMaker,
            AuditPropertyIndicatorPresetCriteriaMaker auditPropertyIndicatorPresetCriteriaMaker,
            AuditEntryPresetCriteriaMaker auditEntryPresetCriteriaMaker,
            AuditEntryPresetConditionMaker auditEntryPresetConditionMaker,
            AuditEntryPropertyPresetCriteriaMaker auditEntryPropertyPresetCriteriaMaker,
            InspectionAlarmTypeIndicatorPresetCriteriaMaker inspectionAlarmTypeIndicatorPresetCriteriaMaker,
            InspectionPresetCriteriaMaker inspectionPresetCriteriaMaker,
            InspectionAlarmPresetCriteriaMaker inspectionAlarmPresetCriteriaMaker,
            InspectionDriverInfoPresetCriteriaMaker inspectionDriverInfoPresetCriteriaMaker,
            InspectionDriverSupportPresetCriteriaMaker inspectionDriverSupportPresetCriteriaMaker,
            InspectionTaskPresetCriteriaMaker inspectionTaskPresetCriteriaMaker,
            InspectionTaskEventPresetCriteriaMaker inspectionTaskEventPresetCriteriaMaker,
            InspectorInfoPresetCriteriaMaker inspectorInfoPresetCriteriaMaker,
            InspectorSupportPresetCriteriaMaker inspectorSupportPresetCriteriaMaker,
            InspectorVariablePresetCriteriaMaker inspectorVariablePresetCriteriaMaker
    ) {
        this.template = template;
        this.auditCategoryPresetCriteriaMaker = auditCategoryPresetCriteriaMaker;
        this.auditPropertyIndicatorPresetCriteriaMaker = auditPropertyIndicatorPresetCriteriaMaker;
        this.auditEntryPresetCriteriaMaker = auditEntryPresetCriteriaMaker;
        this.auditEntryPresetConditionMaker = auditEntryPresetConditionMaker;
        this.auditEntryPropertyPresetCriteriaMaker = auditEntryPropertyPresetCriteriaMaker;
        this.inspectionAlarmTypeIndicatorPresetCriteriaMaker = inspectionAlarmTypeIndicatorPresetCriteriaMaker;
        this.inspectionPresetCriteriaMaker = inspectionPresetCriteriaMaker;
        this.inspectionAlarmPresetCriteriaMaker = inspectionAlarmPresetCriteriaMaker;
        this.inspectionDriverInfoPresetCriteriaMaker = inspectionDriverInfoPresetCriteriaMaker;
        this.inspectionDriverSupportPresetCriteriaMaker = inspectionDriverSupportPresetCriteriaMaker;
        this.inspectionTaskPresetCriteriaMaker = inspectionTaskPresetCriteriaMaker;
        this.inspectionTaskEventPresetCriteriaMaker = inspectionTaskEventPresetCriteriaMaker;
        this.inspectorInfoPresetCriteriaMaker = inspectorInfoPresetCriteriaMaker;
        this.inspectorSupportPresetCriteriaMaker = inspectorSupportPresetCriteriaMaker;
        this.inspectorVariablePresetCriteriaMaker = inspectorVariablePresetCriteriaMaker;
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

    @Bean
    public HibernateBatchBaseDao<StringIdKey, HibernateStringIdKey, InspectionAlarmTypeIndicator,
            HibernateInspectionAlarmTypeIndicator> inspectionAlarmTypeIndicatorHibernateBatchBaseDao() {
        return new HibernateBatchBaseDao<>(
                template,
                new MapStructBeanTransformer<>(StringIdKey.class, HibernateStringIdKey.class, BeanMapper.class),
                new MapStructBeanTransformer<>(
                        InspectionAlarmTypeIndicator.class, HibernateInspectionAlarmTypeIndicator.class,
                        BeanMapper.class
                ),
                HibernateInspectionAlarmTypeIndicator.class,
                new DefaultDeletionMod<>(),
                batchSize
        );
    }

    @Bean
    public HibernateEntireLookupDao<InspectionAlarmTypeIndicator, HibernateInspectionAlarmTypeIndicator>
    inspectionAlarmTypeIndicatorHibernateEntireLookupDao() {
        return new HibernateEntireLookupDao<>(
                template,
                new MapStructBeanTransformer<>(
                        InspectionAlarmTypeIndicator.class, HibernateInspectionAlarmTypeIndicator.class,
                        BeanMapper.class
                ),
                HibernateInspectionAlarmTypeIndicator.class
        );
    }

    @Bean
    public HibernatePresetLookupDao<InspectionAlarmTypeIndicator, HibernateInspectionAlarmTypeIndicator>
    inspectionAlarmTypeIndicatorHibernatePresetLookupDao() {
        return new HibernatePresetLookupDao<>(
                template,
                new MapStructBeanTransformer<>(
                        InspectionAlarmTypeIndicator.class, HibernateInspectionAlarmTypeIndicator.class,
                        BeanMapper.class
                ),
                HibernateInspectionAlarmTypeIndicator.class,
                inspectionAlarmTypeIndicatorPresetCriteriaMaker
        );
    }

    @Bean
    public HibernateBatchBaseDao<LongIdKey, HibernateLongIdKey, Inspection, HibernateInspection>
    inspectionHibernateBatchBaseDao() {
        return new HibernateBatchBaseDao<>(
                template,
                new MapStructBeanTransformer<>(LongIdKey.class, HibernateLongIdKey.class, BeanMapper.class),
                new MapStructBeanTransformer<>(Inspection.class, HibernateInspection.class, BeanMapper.class),
                HibernateInspection.class,
                new DefaultDeletionMod<>(),
                batchSize
        );
    }

    @Bean
    public HibernateEntireLookupDao<Inspection, HibernateInspection> inspectionHibernateEntireLookupDao() {
        return new HibernateEntireLookupDao<>(
                template,
                new MapStructBeanTransformer<>(Inspection.class, HibernateInspection.class, BeanMapper.class),
                HibernateInspection.class
        );
    }

    @Bean
    public HibernatePresetLookupDao<Inspection, HibernateInspection> inspectionHibernatePresetLookupDao() {
        return new HibernatePresetLookupDao<>(
                template,
                new MapStructBeanTransformer<>(Inspection.class, HibernateInspection.class, BeanMapper.class),
                HibernateInspection.class,
                inspectionPresetCriteriaMaker
        );
    }

    @Bean
    public HibernateBatchBaseDao<LongIdKey, HibernateLongIdKey, InspectionAlarm, HibernateInspectionAlarm>
    inspectionAlarmHibernateBatchBaseDao() {
        return new HibernateBatchBaseDao<>(
                template,
                new MapStructBeanTransformer<>(LongIdKey.class, HibernateLongIdKey.class, BeanMapper.class),
                new MapStructBeanTransformer<>(
                        InspectionAlarm.class, HibernateInspectionAlarm.class, BeanMapper.class
                ),
                HibernateInspectionAlarm.class,
                new DefaultDeletionMod<>(),
                batchSize
        );
    }

    @Bean
    public HibernateEntireLookupDao<InspectionAlarm, HibernateInspectionAlarm>
    inspectionAlarmHibernateEntireLookupDao() {
        return new HibernateEntireLookupDao<>(
                template,
                new MapStructBeanTransformer<>(
                        InspectionAlarm.class, HibernateInspectionAlarm.class, BeanMapper.class
                ),
                HibernateInspectionAlarm.class
        );
    }

    @Bean
    public HibernatePresetLookupDao<InspectionAlarm, HibernateInspectionAlarm>
    inspectionAlarmHibernatePresetLookupDao() {
        return new HibernatePresetLookupDao<>(
                template,
                new MapStructBeanTransformer<>(
                        InspectionAlarm.class, HibernateInspectionAlarm.class, BeanMapper.class
                ),
                HibernateInspectionAlarm.class,
                inspectionAlarmPresetCriteriaMaker
        );
    }

    @Bean
    public HibernateBatchBaseDao<LongIdKey, HibernateLongIdKey, InspectionDriverInfo,
            HibernateInspectionDriverInfo> inspectionDriverInfoHibernateBatchBaseDao() {
        return new HibernateBatchBaseDao<>(
                template,
                new MapStructBeanTransformer<>(LongIdKey.class, HibernateLongIdKey.class, BeanMapper.class),
                new MapStructBeanTransformer<>(
                        InspectionDriverInfo.class, HibernateInspectionDriverInfo.class, BeanMapper.class
                ),
                HibernateInspectionDriverInfo.class,
                new DefaultDeletionMod<>(),
                batchSize
        );
    }

    @Bean
    public HibernateEntireLookupDao<InspectionDriverInfo, HibernateInspectionDriverInfo>
    inspectionDriverInfoHibernateEntireLookupDao() {
        return new HibernateEntireLookupDao<>(
                template,
                new MapStructBeanTransformer<>(
                        InspectionDriverInfo.class, HibernateInspectionDriverInfo.class, BeanMapper.class
                ),
                HibernateInspectionDriverInfo.class
        );
    }

    @Bean
    public HibernatePresetLookupDao<InspectionDriverInfo, HibernateInspectionDriverInfo>
    inspectionDriverInfoHibernatePresetLookupDao() {
        return new HibernatePresetLookupDao<>(
                template,
                new MapStructBeanTransformer<>(
                        InspectionDriverInfo.class, HibernateInspectionDriverInfo.class, BeanMapper.class
                ),
                HibernateInspectionDriverInfo.class,
                inspectionDriverInfoPresetCriteriaMaker
        );
    }

    @Bean
    public HibernateBatchBaseDao<StringIdKey, HibernateStringIdKey, InspectionDriverSupport,
            HibernateInspectionDriverSupport> inspectionDriverSupportHibernateBatchBaseDao() {
        return new HibernateBatchBaseDao<>(
                template,
                new MapStructBeanTransformer<>(StringIdKey.class, HibernateStringIdKey.class, BeanMapper.class),
                new MapStructBeanTransformer<>(
                        InspectionDriverSupport.class, HibernateInspectionDriverSupport.class, BeanMapper.class
                ),
                HibernateInspectionDriverSupport.class,
                new DefaultDeletionMod<>(),
                batchSize
        );
    }

    @Bean
    public HibernateEntireLookupDao<InspectionDriverSupport, HibernateInspectionDriverSupport>
    inspectionDriverSupportHibernateEntireLookupDao() {
        return new HibernateEntireLookupDao<>(
                template,
                new MapStructBeanTransformer<>(
                        InspectionDriverSupport.class, HibernateInspectionDriverSupport.class, BeanMapper.class
                ),
                HibernateInspectionDriverSupport.class
        );
    }

    @Bean
    public HibernatePresetLookupDao<InspectionDriverSupport, HibernateInspectionDriverSupport>
    inspectionDriverSupportHibernatePresetLookupDao() {
        return new HibernatePresetLookupDao<>(
                template,
                new MapStructBeanTransformer<>(
                        InspectionDriverSupport.class, HibernateInspectionDriverSupport.class, BeanMapper.class
                ),
                HibernateInspectionDriverSupport.class,
                inspectionDriverSupportPresetCriteriaMaker
        );
    }

    @Bean
    public HibernateBatchBaseDao<LongIdKey, HibernateLongIdKey, InspectionTask, HibernateInspectionTask>
    inspectionTaskHibernateBatchBaseDao() {
        return new HibernateBatchBaseDao<>(
                template,
                new MapStructBeanTransformer<>(LongIdKey.class, HibernateLongIdKey.class, BeanMapper.class),
                new MapStructBeanTransformer<>(InspectionTask.class, HibernateInspectionTask.class, BeanMapper.class),
                HibernateInspectionTask.class,
                new DefaultDeletionMod<>(),
                batchSize
        );
    }

    @Bean
    public HibernateEntireLookupDao<InspectionTask, HibernateInspectionTask> inspectionTaskHibernateEntireLookupDao() {
        return new HibernateEntireLookupDao<>(
                template,
                new MapStructBeanTransformer<>(InspectionTask.class, HibernateInspectionTask.class, BeanMapper.class),
                HibernateInspectionTask.class
        );
    }

    @Bean
    public HibernatePresetLookupDao<InspectionTask, HibernateInspectionTask> inspectionTaskHibernatePresetLookupDao() {
        return new HibernatePresetLookupDao<>(
                template,
                new MapStructBeanTransformer<>(InspectionTask.class, HibernateInspectionTask.class, BeanMapper.class),
                HibernateInspectionTask.class,
                inspectionTaskPresetCriteriaMaker
        );
    }

    @Bean
    public HibernateBatchBaseDao<LongIdKey, HibernateLongIdKey, InspectionTaskEvent,
            HibernateInspectionTaskEvent> inspectionTaskEventHibernateBatchBaseDao() {
        return new HibernateBatchBaseDao<>(
                template,
                new MapStructBeanTransformer<>(LongIdKey.class, HibernateLongIdKey.class, BeanMapper.class),
                new MapStructBeanTransformer<>(
                        InspectionTaskEvent.class, HibernateInspectionTaskEvent.class, BeanMapper.class
                ),
                HibernateInspectionTaskEvent.class,
                new DefaultDeletionMod<>(),
                batchSize
        );
    }

    @Bean
    public HibernateEntireLookupDao<InspectionTaskEvent, HibernateInspectionTaskEvent>
    inspectionTaskEventHibernateEntireLookupDao() {
        return new HibernateEntireLookupDao<>(
                template,
                new MapStructBeanTransformer<>(
                        InspectionTaskEvent.class, HibernateInspectionTaskEvent.class, BeanMapper.class
                ),
                HibernateInspectionTaskEvent.class
        );
    }

    @Bean
    public HibernatePresetLookupDao<InspectionTaskEvent, HibernateInspectionTaskEvent>
    inspectionTaskEventHibernatePresetLookupDao() {
        return new HibernatePresetLookupDao<>(
                template,
                new MapStructBeanTransformer<>(
                        InspectionTaskEvent.class, HibernateInspectionTaskEvent.class, BeanMapper.class
                ),
                HibernateInspectionTaskEvent.class,
                inspectionTaskEventPresetCriteriaMaker
        );
    }

    @Bean
    public HibernateBatchBaseDao<LongIdKey, HibernateLongIdKey, InspectorInfo, HibernateInspectorInfo>
    inspectorInfoHibernateBatchBaseDao() {
        return new HibernateBatchBaseDao<>(
                template,
                new MapStructBeanTransformer<>(LongIdKey.class, HibernateLongIdKey.class, BeanMapper.class),
                new MapStructBeanTransformer<>(InspectorInfo.class, HibernateInspectorInfo.class, BeanMapper.class),
                HibernateInspectorInfo.class,
                new DefaultDeletionMod<>(),
                batchSize
        );
    }

    @Bean
    public HibernateEntireLookupDao<InspectorInfo, HibernateInspectorInfo> inspectorInfoHibernateEntireLookupDao() {
        return new HibernateEntireLookupDao<>(
                template,
                new MapStructBeanTransformer<>(InspectorInfo.class, HibernateInspectorInfo.class, BeanMapper.class),
                HibernateInspectorInfo.class
        );
    }

    @Bean
    public HibernatePresetLookupDao<InspectorInfo, HibernateInspectorInfo> inspectorInfoHibernatePresetLookupDao() {
        return new HibernatePresetLookupDao<>(
                template,
                new MapStructBeanTransformer<>(InspectorInfo.class, HibernateInspectorInfo.class, BeanMapper.class),
                HibernateInspectorInfo.class,
                inspectorInfoPresetCriteriaMaker
        );
    }

    @Bean
    public HibernateBatchBaseDao<StringIdKey, HibernateStringIdKey, InspectorSupport, HibernateInspectorSupport>
    inspectorSupportHibernateBatchBaseDao() {
        return new HibernateBatchBaseDao<>(
                template,
                new MapStructBeanTransformer<>(StringIdKey.class, HibernateStringIdKey.class, BeanMapper.class),
                new MapStructBeanTransformer<>(
                        InspectorSupport.class, HibernateInspectorSupport.class, BeanMapper.class
                ),
                HibernateInspectorSupport.class,
                new DefaultDeletionMod<>(),
                batchSize
        );
    }

    @Bean
    public HibernateEntireLookupDao<InspectorSupport, HibernateInspectorSupport>
    inspectorSupportHibernateEntireLookupDao() {
        return new HibernateEntireLookupDao<>(
                template,
                new MapStructBeanTransformer<>(
                        InspectorSupport.class, HibernateInspectorSupport.class, BeanMapper.class
                ),
                HibernateInspectorSupport.class
        );
    }

    @Bean
    public HibernatePresetLookupDao<InspectorSupport, HibernateInspectorSupport>
    inspectorSupportHibernatePresetLookupDao() {
        return new HibernatePresetLookupDao<>(
                template,
                new MapStructBeanTransformer<>(
                        InspectorSupport.class, HibernateInspectorSupport.class, BeanMapper.class
                ),
                HibernateInspectorSupport.class,
                inspectorSupportPresetCriteriaMaker
        );
    }

    @Bean
    public HibernateBatchBaseDao<InspectorVariableKey, HibernateInspectorVariableKey, InspectorVariable,
            HibernateInspectorVariable> inspectorVariableHibernateBatchBaseDao() {
        return new HibernateBatchBaseDao<>(
                template,
                new MapStructBeanTransformer<>(
                        InspectorVariableKey.class, HibernateInspectorVariableKey.class, BeanMapper.class
                ),
                new MapStructBeanTransformer<>(
                        InspectorVariable.class, HibernateInspectorVariable.class, BeanMapper.class
                ),
                HibernateInspectorVariable.class,
                new DefaultDeletionMod<>(),
                batchSize
        );
    }

    @Bean
    public HibernateEntireLookupDao<InspectorVariable, HibernateInspectorVariable>
    inspectorVariableHibernateEntireLookupDao() {
        return new HibernateEntireLookupDao<>(
                template,
                new MapStructBeanTransformer<>(
                        InspectorVariable.class, HibernateInspectorVariable.class, BeanMapper.class
                ),
                HibernateInspectorVariable.class
        );
    }

    @Bean
    public HibernatePresetLookupDao<InspectorVariable, HibernateInspectorVariable>
    inspectorVariableHibernatePresetLookupDao() {
        return new HibernatePresetLookupDao<>(
                template,
                new MapStructBeanTransformer<>(
                        InspectorVariable.class, HibernateInspectorVariable.class, BeanMapper.class
                ),
                HibernateInspectorVariable.class,
                inspectorVariablePresetCriteriaMaker
        );
    }
}
