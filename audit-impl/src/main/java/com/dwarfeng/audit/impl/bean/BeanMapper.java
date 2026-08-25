package com.dwarfeng.audit.impl.bean;

import com.dwarfeng.audit.impl.bean.entity.HibernateAuditCategory;
import com.dwarfeng.audit.impl.bean.entity.HibernateAuditEntry;
import com.dwarfeng.audit.impl.bean.entity.HibernateAuditEntryProperty;
import com.dwarfeng.audit.impl.bean.entity.HibernateAuditPropertyIndicator;
import com.dwarfeng.audit.impl.bean.key.HibernateAuditEntryPropertyKey;
import com.dwarfeng.audit.impl.bean.key.HibernateAuditPropertyIndicatorKey;
import com.dwarfeng.audit.stack.bean.entity.AuditCategory;
import com.dwarfeng.audit.stack.bean.entity.AuditEntry;
import com.dwarfeng.audit.stack.bean.entity.AuditEntryProperty;
import com.dwarfeng.audit.stack.bean.entity.AuditPropertyIndicator;
import com.dwarfeng.audit.stack.bean.key.AuditEntryPropertyKey;
import com.dwarfeng.audit.stack.bean.key.AuditPropertyIndicatorKey;
import com.dwarfeng.subgrade.sdk.bean.key.HibernateLongIdKey;
import com.dwarfeng.subgrade.sdk.bean.key.HibernateStringIdKey;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface BeanMapper {

    // region Subgrade Key

    HibernateLongIdKey longIdKeyToHibernate(LongIdKey longIdKey);

    @InheritInverseConfiguration
    LongIdKey longIdKeyFromHibernate(HibernateLongIdKey hibernateLongIdKey);

    HibernateStringIdKey stringIdKeyToHibernate(StringIdKey stringIdKey);

    @InheritInverseConfiguration
    StringIdKey stringIdKeyFromHibernate(HibernateStringIdKey hibernateStringIdKey);

    // endregion

    // region Audit Key

    HibernateAuditEntryPropertyKey auditEntryPropertyKeyToHibernate(AuditEntryPropertyKey auditEntryPropertyKey);

    @InheritInverseConfiguration
    AuditEntryPropertyKey auditEntryPropertyKeyFromHibernate(
            HibernateAuditEntryPropertyKey hibernateAuditEntryPropertyKey
    );

    HibernateAuditPropertyIndicatorKey auditPropertyIndicatorKeyToHibernate(
            AuditPropertyIndicatorKey auditPropertyIndicatorKey
    );

    @InheritInverseConfiguration
    AuditPropertyIndicatorKey auditPropertyIndicatorKeyFromHibernate(
            HibernateAuditPropertyIndicatorKey hibernateAuditPropertyIndicatorKey
    );

    // endregion

    // region Audit Entity

    @Mapping(target = "modifiedDatamark", ignore = true)
    @Mapping(target = "stringId", ignore = true)
    @Mapping(target = "createdDatamark", ignore = true)
    @Mapping(target = "auditPropertyIndicatorSet", ignore = true)
    @Mapping(target = "auditEntrySet", ignore = true)
    HibernateAuditCategory auditCategoryToHibernate(AuditCategory auditCategory);

    @InheritInverseConfiguration
    AuditCategory auditCategoryFromHibernate(HibernateAuditCategory hibernateAuditCategory);

    @Mapping(target = "longId", ignore = true)
    @Mapping(target = "categoryStringId", ignore = true)
    @Mapping(target = "auditEntryPropertySet", ignore = true)
    @Mapping(target = "auditCategory", ignore = true)
    HibernateAuditEntry auditEntryToHibernate(AuditEntry auditEntry);

    @InheritInverseConfiguration
    AuditEntry auditEntryFromHibernate(HibernateAuditEntry hibernateAuditEntry);

    @Mapping(target = "propertyStringId", ignore = true)
    @Mapping(target = "auditEntryLongId", ignore = true)
    @Mapping(target = "auditEntry", ignore = true)
    HibernateAuditEntryProperty auditEntryPropertyToHibernate(AuditEntryProperty auditEntryProperty);

    @InheritInverseConfiguration
    AuditEntryProperty auditEntryPropertyFromHibernate(HibernateAuditEntryProperty hibernateAuditEntryProperty);

    @Mapping(target = "propertyStringId", ignore = true)
    @Mapping(target = "modifiedDatamark", ignore = true)
    @Mapping(target = "createdDatamark", ignore = true)
    @Mapping(target = "auditCategoryStringId", ignore = true)
    @Mapping(target = "auditCategory", ignore = true)
    HibernateAuditPropertyIndicator auditPropertyIndicatorToHibernate(AuditPropertyIndicator auditPropertyIndicator);

    @InheritInverseConfiguration
    AuditPropertyIndicator auditPropertyIndicatorFromHibernate(
            HibernateAuditPropertyIndicator hibernateAuditPropertyIndicator
    );

    // endregion
}
