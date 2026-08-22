package com.dwarfeng.audit.node.all.he.configuration;

import com.dwarfeng.audit.sdk.bean.BeanMapper;
import com.dwarfeng.audit.sdk.bean.entity.FastJsonAuditCategory;
import com.dwarfeng.audit.sdk.bean.entity.FastJsonAuditEntry;
import com.dwarfeng.audit.sdk.bean.entity.FastJsonAuditEntryProperty;
import com.dwarfeng.audit.sdk.bean.entity.FastJsonAuditPropertyIndicator;
import com.dwarfeng.audit.sdk.bean.key.formatter.AuditEntryPropertyStringKeyFormatter;
import com.dwarfeng.audit.sdk.bean.key.formatter.AuditPropertyIndicatorStringKeyFormatter;
import com.dwarfeng.audit.stack.bean.entity.AuditCategory;
import com.dwarfeng.audit.stack.bean.entity.AuditEntry;
import com.dwarfeng.audit.stack.bean.entity.AuditEntryProperty;
import com.dwarfeng.audit.stack.bean.entity.AuditPropertyIndicator;
import com.dwarfeng.audit.stack.bean.key.AuditEntryPropertyKey;
import com.dwarfeng.audit.stack.bean.key.AuditPropertyIndicatorKey;
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
}
