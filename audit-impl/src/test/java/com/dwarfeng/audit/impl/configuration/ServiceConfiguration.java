package com.dwarfeng.audit.impl.configuration;

import com.dwarfeng.audit.impl.service.operation.AuditCategoryCrudOperation;
import com.dwarfeng.audit.impl.service.operation.AuditEntryCrudOperation;
import com.dwarfeng.audit.stack.bean.entity.AuditCategory;
import com.dwarfeng.audit.stack.bean.entity.AuditEntry;
import com.dwarfeng.audit.stack.bean.entity.AuditEntryProperty;
import com.dwarfeng.audit.stack.bean.entity.AuditPropertyIndicator;
import com.dwarfeng.audit.stack.bean.key.AuditEntryPropertyKey;
import com.dwarfeng.audit.stack.bean.key.AuditPropertyIndicatorKey;
import com.dwarfeng.audit.stack.cache.AuditEntryPropertyCache;
import com.dwarfeng.audit.stack.cache.AuditPropertyIndicatorCache;
import com.dwarfeng.audit.stack.dao.AuditCategoryDao;
import com.dwarfeng.audit.stack.dao.AuditEntryDao;
import com.dwarfeng.audit.stack.dao.AuditEntryPropertyDao;
import com.dwarfeng.audit.stack.dao.AuditPropertyIndicatorDao;
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

    @Value("${com.dwarfeng.audit.cache.timeout.entity.audit_property_indicator}")
    private long auditPropertyIndicatorTimeout;

    @Value("${com.dwarfeng.audit.cache.timeout.entity.audit_entry_property}")
    private long auditEntryPropertyTimeout;

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
            AuditEntryPropertyCache auditEntryPropertyCache
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
}
