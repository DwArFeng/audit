package com.dwarfeng.audit.sdk.bean;

import com.dwarfeng.audit.sdk.bean.dto.FastJsonAuditEntryLookupResult;
import com.dwarfeng.audit.sdk.bean.dto.JSFixedFastJsonAuditEntryLookupResult;
import com.dwarfeng.audit.sdk.bean.dto.WebInputAuditEntryCompositeLookupInfo;
import com.dwarfeng.audit.sdk.bean.dto.WebInputAuditEntryGroupedLookupInfo;
import com.dwarfeng.audit.sdk.bean.entity.*;
import com.dwarfeng.audit.sdk.bean.key.*;
import com.dwarfeng.audit.stack.bean.dto.AuditEntryCompositeLookupInfo;
import com.dwarfeng.audit.stack.bean.dto.AuditEntryGroupedLookupInfo;
import com.dwarfeng.audit.stack.bean.dto.AuditEntryLookupResult;
import com.dwarfeng.audit.stack.bean.entity.AuditCategory;
import com.dwarfeng.audit.stack.bean.entity.AuditEntry;
import com.dwarfeng.audit.stack.bean.entity.AuditEntryProperty;
import com.dwarfeng.audit.stack.bean.entity.AuditPropertyIndicator;
import com.dwarfeng.audit.stack.bean.key.AuditEntryPropertyKey;
import com.dwarfeng.audit.stack.bean.key.AuditPropertyIndicatorKey;
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

    // endregion
}
