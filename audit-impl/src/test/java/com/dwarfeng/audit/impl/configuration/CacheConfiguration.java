package com.dwarfeng.audit.impl.configuration;

import com.dwarfeng.audit.sdk.bean.BeanMapper;
import com.dwarfeng.audit.sdk.bean.entity.*;
import com.dwarfeng.audit.sdk.bean.key.formatter.AuditEntryPropertyStringKeyFormatter;
import com.dwarfeng.audit.sdk.bean.key.formatter.AuditPropertyIndicatorStringKeyFormatter;
import com.dwarfeng.audit.sdk.bean.key.formatter.InspectorVariableStringKeyFormatter;
import com.dwarfeng.audit.stack.bean.entity.*;
import com.dwarfeng.audit.stack.bean.key.AuditEntryPropertyKey;
import com.dwarfeng.audit.stack.bean.key.AuditPropertyIndicatorKey;
import com.dwarfeng.audit.stack.bean.key.InspectorVariableKey;
import com.dwarfeng.subgrade.impl.bean.MapStructBeanTransformer;
import com.dwarfeng.subgrade.impl.cache.RedisBatchBaseCache;
import com.dwarfeng.subgrade.sdk.redis.formatter.LongIdStringKeyFormatter;
import com.dwarfeng.subgrade.sdk.redis.formatter.StringIdStringKeyFormatter;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;

@Configuration
public class CacheConfiguration {

    private final RedisTemplate<String, ?> template;

    @Value("${com.dwarfeng.audit.cache.prefix.entity.audit_category}")
    private String auditCategoryPrefix;
    @Value("${com.dwarfeng.audit.cache.prefix.entity.audit_property_indicator}")
    private String auditPropertyIndicatorPrefix;
    @Value("${com.dwarfeng.audit.cache.prefix.entity.audit_entry}")
    private String auditEntryPrefix;
    @Value("${com.dwarfeng.audit.cache.prefix.entity.audit_entry_property}")
    private String auditEntryPropertyPrefix;
    @Value("${cache.prefix.entity.inspection_alarm_type_indicator}")
    private String inspectionAlarmTypeIndicatorPrefix;
    @Value("${cache.prefix.entity.inspection}")
    private String inspectionPrefix;
    @Value("${cache.prefix.entity.inspection_alarm}")
    private String inspectionAlarmPrefix;
    @Value("${cache.prefix.entity.inspection_driver_info}")
    private String inspectionDriverInfoPrefix;
    @Value("${cache.prefix.entity.inspection_driver_support}")
    private String inspectionDriverSupportPrefix;
    @Value("${cache.prefix.entity.inspection_task}")
    private String inspectionTaskPrefix;
    @Value("${cache.prefix.entity.inspection_task_event}")
    private String inspectionTaskEventPrefix;
    @Value("${cache.prefix.entity.inspector_info}")
    private String inspectorInfoPrefix;
    @Value("${cache.prefix.entity.inspector_support}")
    private String inspectorSupportPrefix;
    @Value("${cache.prefix.entity.inspector_variable}")
    private String inspectorVariablePrefix;

    public CacheConfiguration(RedisTemplate<String, ?> template) {
        this.template = template;
    }

    @Bean
    @SuppressWarnings("unchecked")
    public RedisBatchBaseCache<StringIdKey, AuditCategory, FastJsonAuditCategory> auditCategoryCacheDelegate() {
        return new RedisBatchBaseCache<>(
                (RedisTemplate<String, FastJsonAuditCategory>) template,
                new StringIdStringKeyFormatter(auditCategoryPrefix),
                new MapStructBeanTransformer<>(AuditCategory.class, FastJsonAuditCategory.class, BeanMapper.class)
        );
    }

    @Bean
    @SuppressWarnings("unchecked")
    public RedisBatchBaseCache<AuditPropertyIndicatorKey, AuditPropertyIndicator, FastJsonAuditPropertyIndicator>
    auditPropertyIndicatorCacheDelegate() {
        return new RedisBatchBaseCache<>(
                (RedisTemplate<String, FastJsonAuditPropertyIndicator>) template,
                new AuditPropertyIndicatorStringKeyFormatter(auditPropertyIndicatorPrefix),
                new MapStructBeanTransformer<>(
                        AuditPropertyIndicator.class, FastJsonAuditPropertyIndicator.class, BeanMapper.class
                )
        );
    }

    @Bean
    @SuppressWarnings("unchecked")
    public RedisBatchBaseCache<LongIdKey, AuditEntry, FastJsonAuditEntry> auditEntryCacheDelegate() {
        return new RedisBatchBaseCache<>(
                (RedisTemplate<String, FastJsonAuditEntry>) template,
                new LongIdStringKeyFormatter(auditEntryPrefix),
                new MapStructBeanTransformer<>(AuditEntry.class, FastJsonAuditEntry.class, BeanMapper.class)
        );
    }

    @Bean
    @SuppressWarnings("unchecked")
    public RedisBatchBaseCache<AuditEntryPropertyKey, AuditEntryProperty, FastJsonAuditEntryProperty>
    auditEntryPropertyCacheDelegate() {
        return new RedisBatchBaseCache<>(
                (RedisTemplate<String, FastJsonAuditEntryProperty>) template,
                new AuditEntryPropertyStringKeyFormatter(auditEntryPropertyPrefix),
                new MapStructBeanTransformer<>(
                        AuditEntryProperty.class, FastJsonAuditEntryProperty.class, BeanMapper.class
                )
        );
    }

    @Bean
    @SuppressWarnings("unchecked")
    public RedisBatchBaseCache<StringIdKey, InspectionAlarmTypeIndicator, FastJsonInspectionAlarmTypeIndicator>
    inspectionAlarmTypeIndicatorRedisBatchBaseCache() {
        return new RedisBatchBaseCache<>(
                (RedisTemplate<String, FastJsonInspectionAlarmTypeIndicator>) template,
                new StringIdStringKeyFormatter(inspectionAlarmTypeIndicatorPrefix),
                new MapStructBeanTransformer<>(
                        InspectionAlarmTypeIndicator.class, FastJsonInspectionAlarmTypeIndicator.class, BeanMapper.class
                )
        );
    }

    @Bean
    @SuppressWarnings("unchecked")
    public RedisBatchBaseCache<LongIdKey, Inspection, FastJsonInspection> inspectionRedisBatchBaseCache() {
        return new RedisBatchBaseCache<>(
                (RedisTemplate<String, FastJsonInspection>) template,
                new LongIdStringKeyFormatter(inspectionPrefix),
                new MapStructBeanTransformer<>(Inspection.class, FastJsonInspection.class, BeanMapper.class)
        );
    }

    @Bean
    @SuppressWarnings("unchecked")
    public RedisBatchBaseCache<LongIdKey, InspectionAlarm, FastJsonInspectionAlarm>
    inspectionAlarmRedisBatchBaseCache() {
        return new RedisBatchBaseCache<>(
                (RedisTemplate<String, FastJsonInspectionAlarm>) template,
                new LongIdStringKeyFormatter(inspectionAlarmPrefix),
                new MapStructBeanTransformer<>(
                        InspectionAlarm.class, FastJsonInspectionAlarm.class, BeanMapper.class
                )
        );
    }

    @Bean
    @SuppressWarnings("unchecked")
    public RedisBatchBaseCache<LongIdKey, InspectionDriverInfo, FastJsonInspectionDriverInfo>
    inspectionDriverInfoRedisBatchBaseCache() {
        return new RedisBatchBaseCache<>(
                (RedisTemplate<String, FastJsonInspectionDriverInfo>) template,
                new LongIdStringKeyFormatter(inspectionDriverInfoPrefix),
                new MapStructBeanTransformer<>(
                        InspectionDriverInfo.class, FastJsonInspectionDriverInfo.class, BeanMapper.class
                )
        );
    }

    @Bean
    @SuppressWarnings("unchecked")
    public RedisBatchBaseCache<StringIdKey, InspectionDriverSupport, FastJsonInspectionDriverSupport>
    inspectionDriverSupportRedisBatchBaseCache() {
        return new RedisBatchBaseCache<>(
                (RedisTemplate<String, FastJsonInspectionDriverSupport>) template,
                new StringIdStringKeyFormatter(inspectionDriverSupportPrefix),
                new MapStructBeanTransformer<>(
                        InspectionDriverSupport.class, FastJsonInspectionDriverSupport.class, BeanMapper.class
                )
        );
    }

    @Bean
    @SuppressWarnings("unchecked")
    public RedisBatchBaseCache<LongIdKey, InspectionTask, FastJsonInspectionTask>
    inspectionTaskRedisBatchBaseCache() {
        return new RedisBatchBaseCache<>(
                (RedisTemplate<String, FastJsonInspectionTask>) template,
                new LongIdStringKeyFormatter(inspectionTaskPrefix),
                new MapStructBeanTransformer<>(InspectionTask.class, FastJsonInspectionTask.class, BeanMapper.class)
        );
    }

    @Bean
    @SuppressWarnings("unchecked")
    public RedisBatchBaseCache<LongIdKey, InspectionTaskEvent, FastJsonInspectionTaskEvent>
    inspectionTaskEventRedisBatchBaseCache() {
        return new RedisBatchBaseCache<>(
                (RedisTemplate<String, FastJsonInspectionTaskEvent>) template,
                new LongIdStringKeyFormatter(inspectionTaskEventPrefix),
                new MapStructBeanTransformer<>(
                        InspectionTaskEvent.class, FastJsonInspectionTaskEvent.class, BeanMapper.class
                )
        );
    }

    @Bean
    @SuppressWarnings("unchecked")
    public RedisBatchBaseCache<LongIdKey, InspectorInfo, FastJsonInspectorInfo> inspectorInfoRedisBatchBaseCache() {
        return new RedisBatchBaseCache<>(
                (RedisTemplate<String, FastJsonInspectorInfo>) template,
                new LongIdStringKeyFormatter(inspectorInfoPrefix),
                new MapStructBeanTransformer<>(InspectorInfo.class, FastJsonInspectorInfo.class, BeanMapper.class)
        );
    }

    @Bean
    @SuppressWarnings("unchecked")
    public RedisBatchBaseCache<StringIdKey, InspectorSupport, FastJsonInspectorSupport>
    inspectorSupportRedisBatchBaseCache() {
        return new RedisBatchBaseCache<>(
                (RedisTemplate<String, FastJsonInspectorSupport>) template,
                new StringIdStringKeyFormatter(inspectorSupportPrefix),
                new MapStructBeanTransformer<>(InspectorSupport.class, FastJsonInspectorSupport.class, BeanMapper.class)
        );
    }

    @Bean
    @SuppressWarnings("unchecked")
    public RedisBatchBaseCache<InspectorVariableKey, InspectorVariable, FastJsonInspectorVariable>
    inspectorVariableRedisBatchBaseCache() {
        return new RedisBatchBaseCache<>(
                (RedisTemplate<String, FastJsonInspectorVariable>) template,
                new InspectorVariableStringKeyFormatter(inspectorVariablePrefix),
                new MapStructBeanTransformer<>(
                        InspectorVariable.class, FastJsonInspectorVariable.class, BeanMapper.class
                )
        );
    }
}
