package com.dwarfeng.audit.sdk.bean;

import com.dwarfeng.audit.sdk.bean.dto.*;
import com.dwarfeng.audit.sdk.bean.entity.*;
import com.dwarfeng.audit.sdk.bean.key.*;
import com.dwarfeng.audit.stack.bean.dto.*;
import com.dwarfeng.audit.stack.bean.entity.*;
import com.dwarfeng.audit.stack.bean.key.AuditEntryPropertyKey;
import com.dwarfeng.audit.stack.bean.key.AuditPropertyIndicatorKey;
import com.dwarfeng.audit.stack.bean.key.InspectorVariableKey;
import com.dwarfeng.subgrade.sdk.bean.key.*;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;

@Mapper
public interface BeanMapper {

    // region Subgrade Key

    FastJsonLongIdKey longIdKeyToFastJson(LongIdKey longIdKey);

    @InheritInverseConfiguration
    LongIdKey longIdKeyFromFastJson(FastJsonLongIdKey fastJsonLongIdKey);

    FastJsonStringIdKey stringIdKeyToFastJson(StringIdKey stringIdKey);

    @InheritInverseConfiguration
    StringIdKey stringIdKeyFromFastJson(FastJsonStringIdKey fastJsonStringIdKey);

    JSFixedFastJsonLongIdKey longIdKeyToJSFixedFastJson(LongIdKey longIdKey);

    @InheritInverseConfiguration
    LongIdKey longIdKeyFromJSFixedFastJson(JSFixedFastJsonLongIdKey jSFixedFastJsonLongIdKey);

    WebInputLongIdKey longIdKeyToWebInput(LongIdKey longIdKey);

    @InheritInverseConfiguration
    LongIdKey longIdKeyFromWebInput(WebInputLongIdKey webInputLongIdKey);

    WebInputStringIdKey stringIdKeyToWebInput(StringIdKey stringIdKey);

    @InheritInverseConfiguration
    StringIdKey stringIdKeyFromWebInput(WebInputStringIdKey webInputStringIdKey);

    // endregion

    // region Audit Key

    FastJsonAuditEntryPropertyKey auditEntryPropertyKeyToFastJson(AuditEntryPropertyKey auditEntryPropertyKey);

    @InheritInverseConfiguration
    AuditEntryPropertyKey auditEntryPropertyKeyFromFastJson(
            FastJsonAuditEntryPropertyKey fastJsonAuditEntryPropertyKey
    );

    FastJsonAuditPropertyIndicatorKey auditPropertyIndicatorKeyToFastJson(
            AuditPropertyIndicatorKey auditPropertyIndicatorKey
    );

    @InheritInverseConfiguration
    AuditPropertyIndicatorKey auditPropertyIndicatorKeyFromFastJson(
            FastJsonAuditPropertyIndicatorKey fastJsonAuditPropertyIndicatorKey
    );

    JSFixedFastJsonAuditEntryPropertyKey auditEntryPropertyKeyToJSFixedFastJson(
            AuditEntryPropertyKey auditEntryPropertyKey
    );

    @InheritInverseConfiguration
    AuditEntryPropertyKey auditEntryPropertyKeyFromJSFixedFastJson(
            JSFixedFastJsonAuditEntryPropertyKey jSFixedFastJsonAuditEntryPropertyKey
    );

    JSFixedFastJsonAuditPropertyIndicatorKey auditPropertyIndicatorKeyToJSFixedFastJson(
            AuditPropertyIndicatorKey auditPropertyIndicatorKey
    );

    @InheritInverseConfiguration
    AuditPropertyIndicatorKey auditPropertyIndicatorKeyFromJSFixedFastJson(
            JSFixedFastJsonAuditPropertyIndicatorKey jSFixedFastJsonAuditPropertyIndicatorKey
    );

    WebInputAuditEntryPropertyKey auditEntryPropertyKeyToWebInput(AuditEntryPropertyKey auditEntryPropertyKey);

    @InheritInverseConfiguration
    AuditEntryPropertyKey auditEntryPropertyKeyFromWebInput(
            WebInputAuditEntryPropertyKey webInputAuditEntryPropertyKey
    );

    WebInputAuditPropertyIndicatorKey auditPropertyIndicatorKeyToWebInput(
            AuditPropertyIndicatorKey auditPropertyIndicatorKey
    );

    @InheritInverseConfiguration
    AuditPropertyIndicatorKey auditPropertyIndicatorKeyFromWebInput(
            WebInputAuditPropertyIndicatorKey webInputAuditPropertyIndicatorKey
    );

    FastJsonInspectorVariableKey inspectorVariableKeyToFastJson(InspectorVariableKey inspectorVariableKey);

    @InheritInverseConfiguration
    InspectorVariableKey inspectorVariableKeyFromFastJson(FastJsonInspectorVariableKey fastJsonInspectorVariableKey);

    JSFixedFastJsonInspectorVariableKey inspectorVariableKeyToJSFixedFastJson(
            InspectorVariableKey inspectorVariableKey
    );

    @InheritInverseConfiguration
    InspectorVariableKey inspectorVariableKeyFromJSFixedFastJson(
            JSFixedFastJsonInspectorVariableKey jSFixedFastJsonInspectorVariableKey
    );

    WebInputInspectorVariableKey inspectorVariableKeyToWebInput(InspectorVariableKey inspectorVariableKey);

    @InheritInverseConfiguration
    InspectorVariableKey inspectorVariableKeyFromWebInput(
            WebInputInspectorVariableKey webInputInspectorVariableKey
    );

    // endregion

    // region Audit Entity

    FastJsonAuditCategory auditCategoryToFastJson(AuditCategory auditCategory);

    @InheritInverseConfiguration
    AuditCategory auditCategoryFromFastJson(FastJsonAuditCategory fastJsonAuditCategory);

    FastJsonAuditEntry auditEntryToFastJson(AuditEntry auditEntry);

    @InheritInverseConfiguration
    AuditEntry auditEntryFromFastJson(FastJsonAuditEntry fastJsonAuditEntry);

    FastJsonAuditEntryProperty auditEntryPropertyToFastJson(AuditEntryProperty auditEntryProperty);

    @InheritInverseConfiguration
    AuditEntryProperty auditEntryPropertyFromFastJson(FastJsonAuditEntryProperty fastJsonAuditEntryProperty);

    FastJsonAuditPropertyIndicator auditPropertyIndicatorToFastJson(AuditPropertyIndicator auditPropertyIndicator);

    @InheritInverseConfiguration
    AuditPropertyIndicator auditPropertyIndicatorFromFastJson(
            FastJsonAuditPropertyIndicator fastJsonAuditPropertyIndicator
    );

    JSFixedFastJsonAuditCategory auditCategoryToJSFixedFastJson(AuditCategory auditCategory);

    @InheritInverseConfiguration
    AuditCategory auditCategoryFromJSFixedFastJson(JSFixedFastJsonAuditCategory jSFixedFastJsonAuditCategory);

    JSFixedFastJsonAuditEntry auditEntryToJSFixedFastJson(AuditEntry auditEntry);

    @InheritInverseConfiguration
    AuditEntry auditEntryFromJSFixedFastJson(JSFixedFastJsonAuditEntry jSFixedFastJsonAuditEntry);

    JSFixedFastJsonAuditEntryProperty auditEntryPropertyToJSFixedFastJson(AuditEntryProperty auditEntryProperty);

    @InheritInverseConfiguration
    AuditEntryProperty auditEntryPropertyFromJSFixedFastJson(
            JSFixedFastJsonAuditEntryProperty jSFixedFastJsonAuditEntryProperty
    );

    JSFixedFastJsonAuditPropertyIndicator auditPropertyIndicatorToJSFixedFastJson(
            AuditPropertyIndicator auditPropertyIndicator
    );

    @InheritInverseConfiguration
    AuditPropertyIndicator auditPropertyIndicatorFromJSFixedFastJson(
            JSFixedFastJsonAuditPropertyIndicator jSFixedFastJsonAuditPropertyIndicator
    );

    WebInputAuditCategory auditCategoryToWebInput(AuditCategory auditCategory);

    @InheritInverseConfiguration
    AuditCategory auditCategoryFromWebInput(WebInputAuditCategory webInputAuditCategory);

    WebInputAuditPropertyIndicator auditPropertyIndicatorToWebInput(AuditPropertyIndicator auditPropertyIndicator);

    @InheritInverseConfiguration
    AuditPropertyIndicator auditPropertyIndicatorFromWebInput(
            WebInputAuditPropertyIndicator webInputAuditPropertyIndicator
    );

    FastJsonInspectionAlarmTypeIndicator inspectionAlarmTypeIndicatorToFastJson(
            InspectionAlarmTypeIndicator inspectionAlarmTypeIndicator
    );

    @InheritInverseConfiguration
    InspectionAlarmTypeIndicator inspectionAlarmTypeIndicatorFromFastJson(
            FastJsonInspectionAlarmTypeIndicator fastJsonInspectionAlarmTypeIndicator
    );

    FastJsonInspection inspectionToFastJson(Inspection inspection);

    @InheritInverseConfiguration
    Inspection inspectionFromFastJson(FastJsonInspection fastJsonInspection);

    FastJsonInspectionAlarm inspectionAlarmToFastJson(InspectionAlarm inspectionAlarm);

    @InheritInverseConfiguration
    InspectionAlarm inspectionAlarmFromFastJson(FastJsonInspectionAlarm fastJsonInspectionAlarm);

    FastJsonInspectionDriverInfo inspectionDriverInfoToFastJson(InspectionDriverInfo inspectionDriverInfo);

    @InheritInverseConfiguration
    InspectionDriverInfo inspectionDriverInfoFromFastJson(FastJsonInspectionDriverInfo fastJsonInspectionDriverInfo);

    FastJsonInspectionDriverSupport inspectionDriverSupportToFastJson(InspectionDriverSupport inspectionDriverSupport);

    @InheritInverseConfiguration
    InspectionDriverSupport inspectionDriverSupportFromFastJson(
            FastJsonInspectionDriverSupport fastJsonInspectionDriverSupport
    );

    FastJsonInspectionTask inspectionTaskToFastJson(InspectionTask inspectionTask);

    @InheritInverseConfiguration
    InspectionTask inspectionTaskFromFastJson(FastJsonInspectionTask fastJsonInspectionTask);

    FastJsonInspectionTaskEvent inspectionTaskEventToFastJson(InspectionTaskEvent inspectionTaskEvent);

    @InheritInverseConfiguration
    InspectionTaskEvent inspectionTaskEventFromFastJson(FastJsonInspectionTaskEvent fastJsonInspectionTaskEvent);

    FastJsonInspectorInfo inspectorInfoToFastJson(InspectorInfo inspectorInfo);

    @InheritInverseConfiguration
    InspectorInfo inspectorInfoFromFastJson(FastJsonInspectorInfo fastJsonInspectorInfo);

    FastJsonInspectorSupport inspectorSupportToFastJson(InspectorSupport inspectorSupport);

    @InheritInverseConfiguration
    InspectorSupport inspectorSupportFromFastJson(FastJsonInspectorSupport fastJsonInspectorSupport);

    FastJsonInspectorVariable inspectorVariableToFastJson(InspectorVariable inspectorVariable);

    @InheritInverseConfiguration
    InspectorVariable inspectorVariableFromFastJson(FastJsonInspectorVariable fastJsonInspectorVariable);

    JSFixedFastJsonInspection inspectionToJSFixedFastJson(Inspection inspection);

    @InheritInverseConfiguration
    Inspection inspectionFromJSFixedFastJson(JSFixedFastJsonInspection jSFixedFastJsonInspection);

    JSFixedFastJsonInspectionAlarm inspectionAlarmToJSFixedFastJson(InspectionAlarm inspectionAlarm);

    @InheritInverseConfiguration
    InspectionAlarm inspectionAlarmFromJSFixedFastJson(JSFixedFastJsonInspectionAlarm jSFixedFastJsonInspectionAlarm);

    JSFixedFastJsonInspectionDriverInfo inspectionDriverInfoToJSFixedFastJson(
            InspectionDriverInfo inspectionDriverInfo
    );

    @InheritInverseConfiguration
    InspectionDriverInfo inspectionDriverInfoFromJSFixedFastJson(
            JSFixedFastJsonInspectionDriverInfo jSFixedFastJsonInspectionDriverInfo
    );

    JSFixedFastJsonInspectionTask inspectionTaskToJSFixedFastJson(InspectionTask inspectionTask);

    @InheritInverseConfiguration
    InspectionTask inspectionTaskFromJSFixedFastJson(JSFixedFastJsonInspectionTask jSFixedFastJsonInspectionTask);

    JSFixedFastJsonInspectionTaskEvent inspectionTaskEventToJSFixedFastJson(InspectionTaskEvent inspectionTaskEvent);

    @InheritInverseConfiguration
    InspectionTaskEvent inspectionTaskEventFromJSFixedFastJson(
            JSFixedFastJsonInspectionTaskEvent jSFixedFastJsonInspectionTaskEvent
    );

    JSFixedFastJsonInspectorInfo inspectorInfoToJSFixedFastJson(InspectorInfo inspectorInfo);

    @InheritInverseConfiguration
    InspectorInfo inspectorInfoFromJSFixedFastJson(JSFixedFastJsonInspectorInfo jSFixedFastJsonInspectorInfo);

    JSFixedFastJsonInspectorVariable inspectorVariableToJSFixedFastJson(InspectorVariable inspectorVariable);

    @InheritInverseConfiguration
    InspectorVariable inspectorVariableFromJSFixedFastJson(
            JSFixedFastJsonInspectorVariable jSFixedFastJsonInspectorVariable
    );

    WebInputInspectionAlarmTypeIndicator inspectionAlarmTypeIndicatorToWebInput(
            InspectionAlarmTypeIndicator inspectionAlarmTypeIndicator
    );

    @InheritInverseConfiguration
    InspectionAlarmTypeIndicator inspectionAlarmTypeIndicatorFromWebInput(
            WebInputInspectionAlarmTypeIndicator webInputInspectionAlarmTypeIndicator
    );

    WebInputInspection inspectionToWebInput(Inspection inspection);

    @InheritInverseConfiguration
    Inspection inspectionFromWebInput(WebInputInspection webInputInspection);

    WebInputInspectionDriverInfo inspectionDriverInfoToWebInput(InspectionDriverInfo inspectionDriverInfo);

    @InheritInverseConfiguration
    InspectionDriverInfo inspectionDriverInfoFromWebInput(WebInputInspectionDriverInfo webInputInspectionDriverInfo);

    WebInputInspectorInfo inspectorInfoToWebInput(InspectorInfo inspectorInfo);

    @InheritInverseConfiguration
    InspectorInfo inspectorInfoFromWebInput(WebInputInspectorInfo webInputInspectorInfo);

    // endregion

    // region Audit DTO

    FastJsonAuditEntryLookupResult auditEntryLookupResultToFastJson(AuditEntryLookupResult auditEntryLookupResult);

    @InheritInverseConfiguration
    AuditEntryLookupResult auditEntryLookupResultFromFastJson(
            FastJsonAuditEntryLookupResult fastJsonAuditEntryLookupResult
    );

    JSFixedFastJsonAuditEntryLookupResult auditEntryLookupResultToJSFixedFastJson(
            AuditEntryLookupResult auditEntryLookupResult
    );

    @InheritInverseConfiguration
    AuditEntryLookupResult auditEntryLookupResultFromJSFixedFastJson(
            JSFixedFastJsonAuditEntryLookupResult jSFixedFastJsonAuditEntryLookupResult
    );

    WebInputAuditEntryCompositeLookupInfo auditEntryCompositeLookupInfoToWebInput(
            AuditEntryCompositeLookupInfo auditEntryCompositeLookupInfo
    );

    @InheritInverseConfiguration
    AuditEntryCompositeLookupInfo auditEntryCompositeLookupInfoFromWebInput(
            WebInputAuditEntryCompositeLookupInfo webInputAuditEntryCompositeLookupInfo
    );

    WebInputAuditEntryGroupedLookupInfo auditEntryGroupedLookupInfoToWebInput(
            AuditEntryGroupedLookupInfo auditEntryGroupedLookupInfo
    );

    @InheritInverseConfiguration
    AuditEntryGroupedLookupInfo auditEntryGroupedLookupInfoFromWebInput(
            WebInputAuditEntryGroupedLookupInfo webInputAuditEntryGroupedLookupInfo
    );

    WebInputAuditRecordInfo auditRecordInfoToWebInput(AuditRecordInfo auditRecordInfo);

    @InheritInverseConfiguration
    AuditRecordInfo auditRecordInfoFromWebInput(WebInputAuditRecordInfo webInputAuditRecordInfo);

    FastJsonInspectionAlarmCreateResult inspectionAlarmCreateResultToFastJson(
            InspectionAlarmCreateResult inspectionAlarmCreateResult
    );

    @InheritInverseConfiguration
    InspectionAlarmCreateResult inspectionAlarmCreateResultFromFastJson(
            FastJsonInspectionAlarmCreateResult fastJsonInspectionAlarmCreateResult
    );

    FastJsonInspectorVariableInspectResult inspectorVariableInspectResultToFastJson(
            InspectorVariableInspectResult inspectorVariableInspectResult
    );

    @InheritInverseConfiguration
    InspectorVariableInspectResult inspectorVariableInspectResultFromFastJson(
            FastJsonInspectorVariableInspectResult fastJsonInspectorVariableInspectResult
    );

    JSFixedFastJsonInspectionAlarmCreateResult inspectionAlarmCreateResultToJSFixedFastJson(
            InspectionAlarmCreateResult inspectionAlarmCreateResult
    );

    @InheritInverseConfiguration
    InspectionAlarmCreateResult inspectionAlarmCreateResultFromJSFixedFastJson(
            JSFixedFastJsonInspectionAlarmCreateResult jSFixedFastJsonInspectionAlarmCreateResult
    );

    JSFixedFastJsonInspectorVariableInspectResult inspectorVariableInspectResultToJSFixedFastJson(
            InspectorVariableInspectResult inspectorVariableInspectResult
    );

    @InheritInverseConfiguration
    InspectorVariableInspectResult inspectorVariableInspectResultFromJSFixedFastJson(
            JSFixedFastJsonInspectorVariableInspectResult jSFixedFastJsonInspectorVariableInspectResult
    );

    WebInputInspectionAlarmCreateInfo inspectionAlarmCreateInfoToWebInput(
            InspectionAlarmCreateInfo inspectionAlarmCreateInfo
    );

    @InheritInverseConfiguration
    InspectionAlarmCreateInfo inspectionAlarmCreateInfoFromWebInput(
            WebInputInspectionAlarmCreateInfo webInputInspectionAlarmCreateInfo
    );

    WebInputInspectorVariableInspectInfo inspectorVariableInspectInfoToWebInput(
            InspectorVariableInspectInfo inspectorVariableInspectInfo
    );

    @InheritInverseConfiguration
    InspectorVariableInspectInfo inspectorVariableInspectInfoFromWebInput(
            WebInputInspectorVariableInspectInfo webInputInspectorVariableInspectInfo
    );

    WebInputInspectorVariableRemoveInfo inspectorVariableRemoveInfoToWebInput(
            InspectorVariableRemoveInfo inspectorVariableRemoveInfo
    );

    @InheritInverseConfiguration
    InspectorVariableRemoveInfo inspectorVariableRemoveInfoFromWebInput(
            WebInputInspectorVariableRemoveInfo webInputInspectorVariableRemoveInfo
    );

    WebInputInspectorVariableUpsertInfo inspectorVariableUpsertInfoToWebInput(
            InspectorVariableUpsertInfo inspectorVariableUpsertInfo
    );

    @InheritInverseConfiguration
    InspectorVariableUpsertInfo inspectorVariableUpsertInfoFromWebInput(
            WebInputInspectorVariableUpsertInfo webInputInspectorVariableUpsertInfo
    );

    FastJsonInspectionTaskCreateResult inspectionTaskCreateResultToFastJson(
            InspectionTaskCreateResult inspectionTaskCreateResult
    );

    @InheritInverseConfiguration
    InspectionTaskCreateResult inspectionTaskCreateResultFromFastJson(
            FastJsonInspectionTaskCreateResult fastJsonInspectionTaskCreateResult
    );

    JSFixedFastJsonInspectionTaskCreateResult inspectionTaskCreateResultToJSFixedFastJson(
            InspectionTaskCreateResult inspectionTaskCreateResult
    );

    @InheritInverseConfiguration
    InspectionTaskCreateResult inspectionTaskCreateResultFromJSFixedFastJson(
            JSFixedFastJsonInspectionTaskCreateResult jSFixedFastJsonInspectionTaskCreateResult
    );

    WebInputInspectionTaskBeatInfo inspectionTaskBeatInfoToWebInput(InspectionTaskBeatInfo inspectionTaskBeatInfo);

    @InheritInverseConfiguration
    InspectionTaskBeatInfo inspectionTaskBeatInfoFromWebInput(
            WebInputInspectionTaskBeatInfo webInputInspectionTaskBeatInfo
    );

    WebInputInspectionTaskCreateInfo inspectionTaskCreateInfoToWebInput(
            InspectionTaskCreateInfo inspectionTaskCreateInfo
    );

    @InheritInverseConfiguration
    InspectionTaskCreateInfo inspectionTaskCreateInfoFromWebInput(
            WebInputInspectionTaskCreateInfo webInputInspectionTaskCreateInfo
    );

    WebInputInspectionTaskDieInfo inspectionTaskDieInfoToWebInput(InspectionTaskDieInfo inspectionTaskDieInfo);

    @InheritInverseConfiguration
    InspectionTaskDieInfo inspectionTaskDieInfoFromWebInput(
            WebInputInspectionTaskDieInfo webInputInspectionTaskDieInfo
    );

    WebInputInspectionTaskEventCreateInfo inspectionTaskEventCreateInfoToWebInput(
            InspectionTaskEventCreateInfo inspectionTaskEventCreateInfo
    );

    @InheritInverseConfiguration
    InspectionTaskEventCreateInfo inspectionTaskEventCreateInfoFromWebInput(
            WebInputInspectionTaskEventCreateInfo webInputInspectionTaskEventCreateInfo
    );

    WebInputInspectionTaskExpireInfo inspectionTaskExpireInfoToWebInput(
            InspectionTaskExpireInfo inspectionTaskExpireInfo
    );

    @InheritInverseConfiguration
    InspectionTaskExpireInfo inspectionTaskExpireInfoFromWebInput(
            WebInputInspectionTaskExpireInfo webInputInspectionTaskExpireInfo
    );

    WebInputInspectionTaskFailInfo inspectionTaskFailInfoToWebInput(InspectionTaskFailInfo inspectionTaskFailInfo);

    @InheritInverseConfiguration
    InspectionTaskFailInfo inspectionTaskFailInfoFromWebInput(
            WebInputInspectionTaskFailInfo webInputInspectionTaskFailInfo
    );

    WebInputInspectionTaskFinishInfo inspectionTaskFinishInfoToWebInput(
            InspectionTaskFinishInfo inspectionTaskFinishInfo
    );

    @InheritInverseConfiguration
    InspectionTaskFinishInfo inspectionTaskFinishInfoFromWebInput(
            WebInputInspectionTaskFinishInfo webInputInspectionTaskFinishInfo
    );

    WebInputInspectionTaskStartInfo inspectionTaskStartInfoToWebInput(InspectionTaskStartInfo inspectionTaskStartInfo);

    @InheritInverseConfiguration
    InspectionTaskStartInfo inspectionTaskStartInfoFromWebInput(
            WebInputInspectionTaskStartInfo webInputInspectionTaskStartInfo
    );

    WebInputInspectionTaskUpdateModalInfo inspectionTaskUpdateModalInfoToWebInput(
            InspectionTaskUpdateModalInfo inspectionTaskUpdateModalInfo
    );

    @InheritInverseConfiguration
    InspectionTaskUpdateModalInfo inspectionTaskUpdateModalInfoFromWebInput(
            WebInputInspectionTaskUpdateModalInfo webInputInspectionTaskUpdateModalInfo
    );

    FastJsonInspectionJobCreateResult inspectionJobCreateResultToFastJson(
            InspectionJobCreateResult inspectionJobCreateResult
    );

    @InheritInverseConfiguration
    InspectionJobCreateResult inspectionJobCreateResultFromFastJson(
            FastJsonInspectionJobCreateResult fastJsonInspectionJobCreateResult
    );

    JSFixedFastJsonInspectionJobCreateResult inspectionJobCreateResultToJSFixedFastJson(
            InspectionJobCreateResult inspectionJobCreateResult
    );

    @InheritInverseConfiguration
    InspectionJobCreateResult inspectionJobCreateResultFromJSFixedFastJson(
            JSFixedFastJsonInspectionJobCreateResult jSFixedFastJsonInspectionJobCreateResult
    );

    WebInputInspectionJobCreateInfo inspectionJobCreateInfoToWebInput(
            InspectionJobCreateInfo inspectionJobCreateInfo
    );

    @InheritInverseConfiguration
    InspectionJobCreateInfo inspectionJobCreateInfoFromWebInput(
            WebInputInspectionJobCreateInfo webInputInspectionJobCreateInfo
    );

    WebInputInspectionJobExecuteInfo inspectionJobExecuteInfoToWebInput(
            InspectionJobExecuteInfo inspectionJobExecuteInfo
    );

    @InheritInverseConfiguration
    InspectionJobExecuteInfo inspectionJobExecuteInfoFromWebInput(
            WebInputInspectionJobExecuteInfo webInputInspectionJobExecuteInfo
    );

    // endregion
}
