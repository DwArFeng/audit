package com.dwarfeng.audit.impl.configuration;

import com.dwarfeng.audit.impl.service.operation.*;
import com.dwarfeng.audit.stack.bean.entity.*;
import com.dwarfeng.audit.stack.bean.key.AuditEntryPropertyKey;
import com.dwarfeng.audit.stack.bean.key.AuditPropertyIndicatorKey;
import com.dwarfeng.audit.stack.bean.key.InspectorVariableKey;
import com.dwarfeng.audit.stack.cache.*;
import com.dwarfeng.audit.stack.dao.*;
import com.dwarfeng.subgrade.impl.generation.DenseUuidStringKeyGenerator;
import com.dwarfeng.subgrade.impl.generation.ExceptionKeyGenerator;
import com.dwarfeng.subgrade.impl.service.CustomBatchCrudService;
import com.dwarfeng.subgrade.impl.service.DaoOnlyEntireLookupService;
import com.dwarfeng.subgrade.impl.service.DaoOnlyPresetLookupService;
import com.dwarfeng.subgrade.impl.service.GeneralBatchCrudService;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.log.LogLevel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServiceConfiguration {

    private final ServiceExceptionMapperConfiguration serviceExceptionMapperConfiguration;
    private final GenerateConfiguration generateConfiguration;

    private final AuditCategoryCrudOperation auditCategoryCrudOperation;
    private final AuditCategoryDao auditCategoryDao;
    private final AuditPropertyIndicatorDao auditPropertyIndicatorDao;
    private final AuditPropertyIndicatorCache auditPropertyIndicatorCache;
    private final AuditEntryCrudOperation auditEntryCrudOperation;
    private final AuditEntryDao auditEntryDao;
    private final AuditEntryPropertyDao auditEntryPropertyDao;
    private final AuditEntryPropertyCache auditEntryPropertyCache;
    private final InspectionAlarmTypeIndicatorDao inspectionAlarmTypeIndicatorDao;
    private final InspectionAlarmTypeIndicatorCache inspectionAlarmTypeIndicatorCache;
    private final InspectionCrudOperation inspectionCrudOperation;
    private final InspectionDao inspectionDao;
    private final InspectionAlarmDao inspectionAlarmDao;
    private final InspectionAlarmCache inspectionAlarmCache;
    private final InspectionDriverInfoDao inspectionDriverInfoDao;
    private final InspectionDriverInfoCache inspectionDriverInfoCache;
    private final InspectionDriverSupportDao inspectionDriverSupportDao;
    private final InspectionDriverSupportCache inspectionDriverSupportCache;
    private final InspectionTaskCrudOperation inspectionTaskCrudOperation;
    private final InspectionTaskDao inspectionTaskDao;
    private final InspectionTaskEventDao inspectionTaskEventDao;
    private final InspectionTaskEventCache inspectionTaskEventCache;
    private final InspectorInfoCrudOperation inspectorInfoCrudOperation;
    private final InspectorInfoDao inspectorInfoDao;
    private final InspectorSupportDao inspectorSupportDao;
    private final InspectorSupportCache inspectorSupportCache;
    private final InspectorVariableDao inspectorVariableDao;
    private final InspectorVariableCache inspectorVariableCache;

    @Value("${com.dwarfeng.audit.cache.timeout.entity.audit_property_indicator}")
    private long auditPropertyIndicatorTimeout;

    @Value("${com.dwarfeng.audit.cache.timeout.entity.audit_entry_property}")
    private long auditEntryPropertyTimeout;
    @Value("${cache.timeout.entity.inspection_alarm_type_indicator}")
    private long inspectionAlarmTypeIndicatorTimeout;
    @Value("${cache.timeout.entity.inspection_alarm}")
    private long inspectionAlarmTimeout;
    @Value("${cache.timeout.entity.inspection_driver_info}")
    private long inspectionDriverInfoTimeout;
    @Value("${cache.timeout.entity.inspection_driver_support}")
    private long inspectionDriverSupportTimeout;
    @Value("${cache.timeout.entity.inspection_task_event}")
    private long inspectionTaskEventTimeout;
    @Value("${cache.timeout.entity.inspector_support}")
    private long inspectorSupportTimeout;
    @Value("${cache.timeout.entity.inspector_variable}")
    private long inspectorVariableTimeout;

    public ServiceConfiguration(
            ServiceExceptionMapperConfiguration serviceExceptionMapperConfiguration,
            GenerateConfiguration generateConfiguration,
            AuditCategoryCrudOperation auditCategoryCrudOperation,
            AuditCategoryDao auditCategoryDao,
            AuditPropertyIndicatorDao auditPropertyIndicatorDao,
            AuditPropertyIndicatorCache auditPropertyIndicatorCache,
            AuditEntryCrudOperation auditEntryCrudOperation,
            AuditEntryDao auditEntryDao,
            AuditEntryPropertyDao auditEntryPropertyDao,
            AuditEntryPropertyCache auditEntryPropertyCache,
            InspectionAlarmTypeIndicatorDao inspectionAlarmTypeIndicatorDao,
            InspectionAlarmTypeIndicatorCache inspectionAlarmTypeIndicatorCache,
            InspectionCrudOperation inspectionCrudOperation,
            InspectionDao inspectionDao,
            InspectionAlarmDao inspectionAlarmDao,
            InspectionAlarmCache inspectionAlarmCache,
            InspectionDriverInfoDao inspectionDriverInfoDao,
            InspectionDriverInfoCache inspectionDriverInfoCache,
            InspectionDriverSupportDao inspectionDriverSupportDao,
            InspectionDriverSupportCache inspectionDriverSupportCache,
            InspectionTaskCrudOperation inspectionTaskCrudOperation,
            InspectionTaskDao inspectionTaskDao,
            InspectionTaskEventDao inspectionTaskEventDao,
            InspectionTaskEventCache inspectionTaskEventCache,
            InspectorInfoCrudOperation inspectorInfoCrudOperation,
            InspectorInfoDao inspectorInfoDao,
            InspectorSupportDao inspectorSupportDao,
            InspectorSupportCache inspectorSupportCache,
            InspectorVariableDao inspectorVariableDao,
            InspectorVariableCache inspectorVariableCache
    ) {
        this.serviceExceptionMapperConfiguration = serviceExceptionMapperConfiguration;
        this.generateConfiguration = generateConfiguration;
        this.auditCategoryCrudOperation = auditCategoryCrudOperation;
        this.auditCategoryDao = auditCategoryDao;
        this.auditPropertyIndicatorDao = auditPropertyIndicatorDao;
        this.auditPropertyIndicatorCache = auditPropertyIndicatorCache;
        this.auditEntryCrudOperation = auditEntryCrudOperation;
        this.auditEntryDao = auditEntryDao;
        this.auditEntryPropertyDao = auditEntryPropertyDao;
        this.auditEntryPropertyCache = auditEntryPropertyCache;
        this.inspectionAlarmTypeIndicatorDao = inspectionAlarmTypeIndicatorDao;
        this.inspectionAlarmTypeIndicatorCache = inspectionAlarmTypeIndicatorCache;
        this.inspectionCrudOperation = inspectionCrudOperation;
        this.inspectionDao = inspectionDao;
        this.inspectionAlarmDao = inspectionAlarmDao;
        this.inspectionAlarmCache = inspectionAlarmCache;
        this.inspectionDriverInfoDao = inspectionDriverInfoDao;
        this.inspectionDriverInfoCache = inspectionDriverInfoCache;
        this.inspectionDriverSupportDao = inspectionDriverSupportDao;
        this.inspectionDriverSupportCache = inspectionDriverSupportCache;
        this.inspectionTaskCrudOperation = inspectionTaskCrudOperation;
        this.inspectionTaskDao = inspectionTaskDao;
        this.inspectionTaskEventDao = inspectionTaskEventDao;
        this.inspectionTaskEventCache = inspectionTaskEventCache;
        this.inspectorInfoCrudOperation = inspectorInfoCrudOperation;
        this.inspectorInfoDao = inspectorInfoDao;
        this.inspectorSupportDao = inspectorSupportDao;
        this.inspectorSupportCache = inspectorSupportCache;
        this.inspectorVariableDao = inspectorVariableDao;
        this.inspectorVariableCache = inspectorVariableCache;
    }

    @Bean
    public CustomBatchCrudService<StringIdKey, AuditCategory> auditCategoryCustomBatchCrudService() {
        return new CustomBatchCrudService<>(
                serviceExceptionMapperConfiguration.mapServiceExceptionMapper(),
                LogLevel.WARN,
                auditCategoryCrudOperation,
                new DenseUuidStringKeyGenerator()
        );
    }

    @Bean
    public DaoOnlyEntireLookupService<AuditCategory> auditCategoryDaoOnlyEntireLookupService() {
        return new DaoOnlyEntireLookupService<>(
                serviceExceptionMapperConfiguration.mapServiceExceptionMapper(),
                LogLevel.WARN,
                auditCategoryDao
        );
    }

    @Bean
    public DaoOnlyPresetLookupService<AuditCategory> auditCategoryDaoOnlyPresetLookupService() {
        return new DaoOnlyPresetLookupService<>(
                serviceExceptionMapperConfiguration.mapServiceExceptionMapper(),
                LogLevel.WARN,
                auditCategoryDao
        );
    }

    @Bean
    public GeneralBatchCrudService<AuditPropertyIndicatorKey, AuditPropertyIndicator>
    auditPropertyIndicatorGeneralBatchCrudService() {
        return new GeneralBatchCrudService<>(
                serviceExceptionMapperConfiguration.mapServiceExceptionMapper(),
                LogLevel.WARN,
                auditPropertyIndicatorDao,
                auditPropertyIndicatorCache,
                new ExceptionKeyGenerator<>(),
                auditPropertyIndicatorTimeout
        );
    }

    @Bean
    public DaoOnlyEntireLookupService<AuditPropertyIndicator> auditPropertyIndicatorDaoOnlyEntireLookupService() {
        return new DaoOnlyEntireLookupService<>(
                serviceExceptionMapperConfiguration.mapServiceExceptionMapper(),
                LogLevel.WARN,
                auditPropertyIndicatorDao
        );
    }

    @Bean
    public DaoOnlyPresetLookupService<AuditPropertyIndicator> auditPropertyIndicatorDaoOnlyPresetLookupService() {
        return new DaoOnlyPresetLookupService<>(
                serviceExceptionMapperConfiguration.mapServiceExceptionMapper(),
                LogLevel.WARN,
                auditPropertyIndicatorDao
        );
    }

    @Bean
    public CustomBatchCrudService<LongIdKey, AuditEntry> auditEntryCustomBatchCrudService() {
        return new CustomBatchCrudService<>(
                serviceExceptionMapperConfiguration.mapServiceExceptionMapper(),
                LogLevel.WARN,
                auditEntryCrudOperation,
                generateConfiguration.snowflakeLongIdKeyGenerator()
        );
    }

    @Bean
    public DaoOnlyEntireLookupService<AuditEntry> auditEntryDaoOnlyEntireLookupService() {
        return new DaoOnlyEntireLookupService<>(
                serviceExceptionMapperConfiguration.mapServiceExceptionMapper(),
                LogLevel.WARN,
                auditEntryDao
        );
    }

    @Bean
    public DaoOnlyPresetLookupService<AuditEntry> auditEntryDaoOnlyPresetLookupService() {
        return new DaoOnlyPresetLookupService<>(
                serviceExceptionMapperConfiguration.mapServiceExceptionMapper(),
                LogLevel.WARN,
                auditEntryDao
        );
    }

    @Bean
    public GeneralBatchCrudService<AuditEntryPropertyKey, AuditEntryProperty>
    auditEntryPropertyGeneralBatchCrudService() {
        return new GeneralBatchCrudService<>(
                serviceExceptionMapperConfiguration.mapServiceExceptionMapper(),
                LogLevel.WARN,
                auditEntryPropertyDao,
                auditEntryPropertyCache,
                new ExceptionKeyGenerator<>(),
                auditEntryPropertyTimeout
        );
    }

    @Bean
    public DaoOnlyEntireLookupService<AuditEntryProperty> auditEntryPropertyDaoOnlyEntireLookupService() {
        return new DaoOnlyEntireLookupService<>(
                serviceExceptionMapperConfiguration.mapServiceExceptionMapper(),
                LogLevel.WARN,
                auditEntryPropertyDao
        );
    }

    @Bean
    public DaoOnlyPresetLookupService<AuditEntryProperty> auditEntryPropertyDaoOnlyPresetLookupService() {
        return new DaoOnlyPresetLookupService<>(
                serviceExceptionMapperConfiguration.mapServiceExceptionMapper(),
                LogLevel.WARN,
                auditEntryPropertyDao
        );
    }

    @Bean
    public GeneralBatchCrudService<StringIdKey, InspectionAlarmTypeIndicator>
    inspectionAlarmTypeIndicatorGeneralBatchCrudService() {
        return new GeneralBatchCrudService<>(
                serviceExceptionMapperConfiguration.mapServiceExceptionMapper(),
                LogLevel.WARN,
                inspectionAlarmTypeIndicatorDao,
                inspectionAlarmTypeIndicatorCache,
                new ExceptionKeyGenerator<>(),
                inspectionAlarmTypeIndicatorTimeout
        );
    }

    @Bean
    public DaoOnlyEntireLookupService<InspectionAlarmTypeIndicator>
    inspectionAlarmTypeIndicatorDaoOnlyEntireLookupService() {
        return new DaoOnlyEntireLookupService<>(
                serviceExceptionMapperConfiguration.mapServiceExceptionMapper(),
                LogLevel.WARN,
                inspectionAlarmTypeIndicatorDao
        );
    }

    @Bean
    public DaoOnlyPresetLookupService<InspectionAlarmTypeIndicator>
    inspectionAlarmTypeIndicatorDaoOnlyPresetLookupService() {
        return new DaoOnlyPresetLookupService<>(
                serviceExceptionMapperConfiguration.mapServiceExceptionMapper(),
                LogLevel.WARN,
                inspectionAlarmTypeIndicatorDao
        );
    }

    @Bean
    public CustomBatchCrudService<LongIdKey, Inspection> inspectionCustomBatchCrudService() {
        return new CustomBatchCrudService<>(
                serviceExceptionMapperConfiguration.mapServiceExceptionMapper(),
                LogLevel.WARN,
                inspectionCrudOperation,
                generateConfiguration.snowflakeLongIdKeyGenerator()
        );
    }

    @Bean
    public DaoOnlyEntireLookupService<Inspection> inspectionDaoOnlyEntireLookupService() {
        return new DaoOnlyEntireLookupService<>(
                serviceExceptionMapperConfiguration.mapServiceExceptionMapper(),
                LogLevel.WARN,
                inspectionDao
        );
    }

    @Bean
    public DaoOnlyPresetLookupService<Inspection> inspectionDaoOnlyPresetLookupService() {
        return new DaoOnlyPresetLookupService<>(
                serviceExceptionMapperConfiguration.mapServiceExceptionMapper(),
                LogLevel.WARN,
                inspectionDao
        );
    }

    @Bean
    public GeneralBatchCrudService<LongIdKey, InspectionAlarm> inspectionAlarmGeneralBatchCrudService() {
        return new GeneralBatchCrudService<>(
                serviceExceptionMapperConfiguration.mapServiceExceptionMapper(),
                LogLevel.WARN,
                inspectionAlarmDao,
                inspectionAlarmCache,
                generateConfiguration.snowflakeLongIdKeyGenerator(),
                inspectionAlarmTimeout
        );
    }

    @Bean
    public DaoOnlyEntireLookupService<InspectionAlarm> inspectionAlarmDaoOnlyEntireLookupService() {
        return new DaoOnlyEntireLookupService<>(
                serviceExceptionMapperConfiguration.mapServiceExceptionMapper(),
                LogLevel.WARN,
                inspectionAlarmDao
        );
    }

    @Bean
    public DaoOnlyPresetLookupService<InspectionAlarm> inspectionAlarmDaoOnlyPresetLookupService() {
        return new DaoOnlyPresetLookupService<>(
                serviceExceptionMapperConfiguration.mapServiceExceptionMapper(),
                LogLevel.WARN,
                inspectionAlarmDao
        );
    }

    @Bean
    public GeneralBatchCrudService<LongIdKey, InspectionDriverInfo> inspectionDriverInfoGeneralBatchCrudService() {
        return new GeneralBatchCrudService<>(
                serviceExceptionMapperConfiguration.mapServiceExceptionMapper(),
                LogLevel.WARN,
                inspectionDriverInfoDao,
                inspectionDriverInfoCache,
                generateConfiguration.snowflakeLongIdKeyGenerator(),
                inspectionDriverInfoTimeout
        );
    }

    @Bean
    public DaoOnlyEntireLookupService<InspectionDriverInfo> inspectionDriverInfoDaoOnlyEntireLookupService() {
        return new DaoOnlyEntireLookupService<>(
                serviceExceptionMapperConfiguration.mapServiceExceptionMapper(),
                LogLevel.WARN,
                inspectionDriverInfoDao
        );
    }

    @Bean
    public DaoOnlyPresetLookupService<InspectionDriverInfo> inspectionDriverInfoDaoOnlyPresetLookupService() {
        return new DaoOnlyPresetLookupService<>(
                serviceExceptionMapperConfiguration.mapServiceExceptionMapper(),
                LogLevel.WARN,
                inspectionDriverInfoDao
        );
    }

    @Bean
    public GeneralBatchCrudService<StringIdKey, InspectionDriverSupport>
    inspectionDriverSupportGeneralBatchCrudService() {
        return new GeneralBatchCrudService<>(
                serviceExceptionMapperConfiguration.mapServiceExceptionMapper(),
                LogLevel.WARN,
                inspectionDriverSupportDao,
                inspectionDriverSupportCache,
                new ExceptionKeyGenerator<>(),
                inspectionDriverSupportTimeout
        );
    }

    @Bean
    public DaoOnlyEntireLookupService<InspectionDriverSupport> inspectionDriverSupportDaoOnlyEntireLookupService() {
        return new DaoOnlyEntireLookupService<>(
                serviceExceptionMapperConfiguration.mapServiceExceptionMapper(),
                LogLevel.WARN,
                inspectionDriverSupportDao
        );
    }

    @Bean
    public DaoOnlyPresetLookupService<InspectionDriverSupport> inspectionDriverSupportDaoOnlyPresetLookupService() {
        return new DaoOnlyPresetLookupService<>(
                serviceExceptionMapperConfiguration.mapServiceExceptionMapper(),
                LogLevel.WARN,
                inspectionDriverSupportDao
        );
    }

    @Bean
    public CustomBatchCrudService<LongIdKey, InspectionTask> inspectionTaskCustomBatchCrudService() {
        return new CustomBatchCrudService<>(
                serviceExceptionMapperConfiguration.mapServiceExceptionMapper(),
                LogLevel.WARN,
                inspectionTaskCrudOperation,
                generateConfiguration.snowflakeLongIdKeyGenerator()
        );
    }

    @Bean
    public DaoOnlyEntireLookupService<InspectionTask> inspectionTaskDaoOnlyEntireLookupService() {
        return new DaoOnlyEntireLookupService<>(
                serviceExceptionMapperConfiguration.mapServiceExceptionMapper(),
                LogLevel.WARN,
                inspectionTaskDao
        );
    }

    @Bean
    public DaoOnlyPresetLookupService<InspectionTask> inspectionTaskDaoOnlyPresetLookupService() {
        return new DaoOnlyPresetLookupService<>(
                serviceExceptionMapperConfiguration.mapServiceExceptionMapper(),
                LogLevel.WARN,
                inspectionTaskDao
        );
    }

    @Bean
    public GeneralBatchCrudService<LongIdKey, InspectionTaskEvent> inspectionTaskEventGeneralBatchCrudService() {
        return new GeneralBatchCrudService<>(
                serviceExceptionMapperConfiguration.mapServiceExceptionMapper(),
                LogLevel.WARN,
                inspectionTaskEventDao,
                inspectionTaskEventCache,
                generateConfiguration.snowflakeLongIdKeyGenerator(),
                inspectionTaskEventTimeout
        );
    }

    @Bean
    public DaoOnlyEntireLookupService<InspectionTaskEvent> inspectionTaskEventDaoOnlyEntireLookupService() {
        return new DaoOnlyEntireLookupService<>(
                serviceExceptionMapperConfiguration.mapServiceExceptionMapper(),
                LogLevel.WARN,
                inspectionTaskEventDao
        );
    }

    @Bean
    public DaoOnlyPresetLookupService<InspectionTaskEvent> inspectionTaskEventDaoOnlyPresetLookupService() {
        return new DaoOnlyPresetLookupService<>(
                serviceExceptionMapperConfiguration.mapServiceExceptionMapper(),
                LogLevel.WARN,
                inspectionTaskEventDao
        );
    }

    @Bean
    public CustomBatchCrudService<LongIdKey, InspectorInfo> inspectorInfoCustomBatchCrudService() {
        return new CustomBatchCrudService<>(
                serviceExceptionMapperConfiguration.mapServiceExceptionMapper(),
                LogLevel.WARN,
                inspectorInfoCrudOperation,
                generateConfiguration.snowflakeLongIdKeyGenerator()
        );
    }

    @Bean
    public DaoOnlyEntireLookupService<InspectorInfo> inspectorInfoDaoOnlyEntireLookupService() {
        return new DaoOnlyEntireLookupService<>(
                serviceExceptionMapperConfiguration.mapServiceExceptionMapper(),
                LogLevel.WARN,
                inspectorInfoDao
        );
    }

    @Bean
    public DaoOnlyPresetLookupService<InspectorInfo> inspectorInfoDaoOnlyPresetLookupService() {
        return new DaoOnlyPresetLookupService<>(
                serviceExceptionMapperConfiguration.mapServiceExceptionMapper(),
                LogLevel.WARN,
                inspectorInfoDao
        );
    }

    @Bean
    public GeneralBatchCrudService<StringIdKey, InspectorSupport> inspectorSupportGeneralBatchCrudService() {
        return new GeneralBatchCrudService<>(
                serviceExceptionMapperConfiguration.mapServiceExceptionMapper(),
                LogLevel.WARN,
                inspectorSupportDao,
                inspectorSupportCache,
                new ExceptionKeyGenerator<>(),
                inspectorSupportTimeout
        );
    }

    @Bean
    public DaoOnlyEntireLookupService<InspectorSupport> inspectorSupportDaoOnlyEntireLookupService() {
        return new DaoOnlyEntireLookupService<>(
                serviceExceptionMapperConfiguration.mapServiceExceptionMapper(),
                LogLevel.WARN,
                inspectorSupportDao
        );
    }

    @Bean
    public DaoOnlyPresetLookupService<InspectorSupport> inspectorSupportDaoOnlyPresetLookupService() {
        return new DaoOnlyPresetLookupService<>(
                serviceExceptionMapperConfiguration.mapServiceExceptionMapper(),
                LogLevel.WARN,
                inspectorSupportDao
        );
    }

    @Bean
    public GeneralBatchCrudService<InspectorVariableKey, InspectorVariable>
    inspectorVariableGeneralBatchCrudService() {
        return new GeneralBatchCrudService<>(
                serviceExceptionMapperConfiguration.mapServiceExceptionMapper(),
                LogLevel.WARN,
                inspectorVariableDao,
                inspectorVariableCache,
                new ExceptionKeyGenerator<>(),
                inspectorVariableTimeout
        );
    }

    @Bean
    public DaoOnlyEntireLookupService<InspectorVariable> inspectorVariableDaoOnlyEntireLookupService() {
        return new DaoOnlyEntireLookupService<>(
                serviceExceptionMapperConfiguration.mapServiceExceptionMapper(),
                LogLevel.WARN,
                inspectorVariableDao
        );
    }

    @Bean
    public DaoOnlyPresetLookupService<InspectorVariable> inspectorVariableDaoOnlyPresetLookupService() {
        return new DaoOnlyPresetLookupService<>(
                serviceExceptionMapperConfiguration.mapServiceExceptionMapper(),
                LogLevel.WARN,
                inspectorVariableDao
        );
    }
}
