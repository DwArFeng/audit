package com.dwarfeng.audit.impl.bean;

import com.dwarfeng.audit.impl.bean.entity.*;
import com.dwarfeng.audit.impl.bean.key.HibernateAuditEntryPropertyKey;
import com.dwarfeng.audit.impl.bean.key.HibernateAuditPropertyIndicatorKey;
import com.dwarfeng.audit.impl.bean.key.HibernateInspectorVariableKey;
import com.dwarfeng.audit.stack.bean.entity.*;
import com.dwarfeng.audit.stack.bean.key.AuditEntryPropertyKey;
import com.dwarfeng.audit.stack.bean.key.AuditPropertyIndicatorKey;
import com.dwarfeng.audit.stack.bean.key.InspectorVariableKey;
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

    HibernateInspectorVariableKey inspectorVariableKeyToHibernate(InspectorVariableKey inspectorVariableKey);

    @InheritInverseConfiguration
    InspectorVariableKey inspectorVariableKeyFromHibernate(HibernateInspectorVariableKey hibernateInspectorVariableKey);

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

    @Mapping(target = "stringId", ignore = true)
    HibernateInspectionAlarmTypeIndicator inspectionAlarmTypeIndicatorToHibernate(
            InspectionAlarmTypeIndicator inspectionAlarmTypeIndicator
    );

    @InheritInverseConfiguration
    InspectionAlarmTypeIndicator inspectionAlarmTypeIndicatorFromHibernate(
            HibernateInspectionAlarmTypeIndicator hibernateInspectionAlarmTypeIndicator
    );

    @Mapping(target = "modifiedDatamark", ignore = true)
    @Mapping(target = "createdDatamark", ignore = true)
    @Mapping(target = "inspectorInfoSet", ignore = true)
    @Mapping(target = "inspectionTaskSet", ignore = true)
    @Mapping(target = "inspectionDriverInfoSet", ignore = true)
    @Mapping(target = "inspectionAlarmSet", ignore = true)
    @Mapping(target = "longId", ignore = true)
    HibernateInspection inspectionToHibernate(Inspection inspection);

    @InheritInverseConfiguration
    Inspection inspectionFromHibernate(HibernateInspection hibernateInspection);

    @Mapping(target = "inspectorInfo", ignore = true)
    @Mapping(target = "inspectionTask", ignore = true)
    @Mapping(target = "inspection", ignore = true)
    @Mapping(target = "inspectorInfoLongId", ignore = true)
    @Mapping(target = "inspectionTaskLongId", ignore = true)
    @Mapping(target = "inspectionLongId", ignore = true)
    @Mapping(target = "longId", ignore = true)
    HibernateInspectionAlarm inspectionAlarmToHibernate(InspectionAlarm inspectionAlarm);

    @InheritInverseConfiguration
    InspectionAlarm inspectionAlarmFromHibernate(HibernateInspectionAlarm hibernateInspectionAlarm);

    @Mapping(target = "modifiedDatamark", ignore = true)
    @Mapping(target = "createdDatamark", ignore = true)
    @Mapping(target = "inspection", ignore = true)
    @Mapping(target = "inspectionLongId", ignore = true)
    @Mapping(target = "longId", ignore = true)
    HibernateInspectionDriverInfo inspectionDriverInfoToHibernate(InspectionDriverInfo inspectionDriverInfo);

    @InheritInverseConfiguration
    InspectionDriverInfo inspectionDriverInfoFromHibernate(HibernateInspectionDriverInfo hibernateInspectionDriverInfo);

    @Mapping(target = "stringId", ignore = true)
    HibernateInspectionDriverSupport inspectionDriverSupportToHibernate(
            InspectionDriverSupport inspectionDriverSupport
    );

    @InheritInverseConfiguration
    InspectionDriverSupport inspectionDriverSupportFromHibernate(
            HibernateInspectionDriverSupport hibernateInspectionDriverSupport
    );

    @Mapping(target = "inspectionTaskEventSet", ignore = true)
    @Mapping(target = "inspectionAlarmSet", ignore = true)
    @Mapping(target = "inspection", ignore = true)
    @Mapping(target = "inspectionLongId", ignore = true)
    @Mapping(target = "longId", ignore = true)
    HibernateInspectionTask inspectionTaskToHibernate(InspectionTask inspectionTask);

    @InheritInverseConfiguration
    InspectionTask inspectionTaskFromHibernate(HibernateInspectionTask hibernateInspectionTask);

    @Mapping(target = "inspectionTask", ignore = true)
    @Mapping(target = "inspectionTaskLongId", ignore = true)
    @Mapping(target = "longId", ignore = true)
    HibernateInspectionTaskEvent inspectionTaskEventToHibernate(InspectionTaskEvent inspectionTaskEvent);

    @InheritInverseConfiguration
    InspectionTaskEvent inspectionTaskEventFromHibernate(HibernateInspectionTaskEvent hibernateInspectionTaskEvent);

    @Mapping(target = "modifiedDatamark", ignore = true)
    @Mapping(target = "createdDatamark", ignore = true)
    @Mapping(target = "inspectorVariableSet", ignore = true)
    @Mapping(target = "inspectionAlarmSet", ignore = true)
    @Mapping(target = "inspection", ignore = true)
    @Mapping(target = "inspectionLongId", ignore = true)
    @Mapping(target = "longId", ignore = true)
    HibernateInspectorInfo inspectorInfoToHibernate(InspectorInfo inspectorInfo);

    @InheritInverseConfiguration
    InspectorInfo inspectorInfoFromHibernate(HibernateInspectorInfo hibernateInspectorInfo);

    @Mapping(target = "stringId", ignore = true)
    HibernateInspectorSupport inspectorSupportToHibernate(InspectorSupport inspectorSupport);

    @InheritInverseConfiguration
    InspectorSupport inspectorSupportFromHibernate(HibernateInspectorSupport hibernateInspectorSupport);

    @Mapping(target = "inspectorInfo", ignore = true)
    @Mapping(target = "variableStringId", ignore = true)
    @Mapping(target = "inspectorInfoLongId", ignore = true)
    HibernateInspectorVariable inspectorVariableToHibernate(InspectorVariable inspectorVariable);

    @InheritInverseConfiguration
    InspectorVariable inspectorVariableFromHibernate(HibernateInspectorVariable hibernateInspectorVariable);

    // endregion
}
