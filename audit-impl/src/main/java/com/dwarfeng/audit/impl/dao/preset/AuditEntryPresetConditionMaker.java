package com.dwarfeng.audit.impl.dao.preset;

import com.dwarfeng.audit.impl.bean.entity.HibernateAuditEntryProperty;
import com.dwarfeng.audit.sdk.util.Constants;
import com.dwarfeng.audit.stack.bean.dto.AuditEntryCompositeLookupInfo;
import com.dwarfeng.audit.stack.bean.dto.AuditEntryGroupedLookupInfo;
import com.dwarfeng.audit.stack.service.AuditEntryMaintainService;
import com.dwarfeng.subgrade.sdk.hibernate.hql.*;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 审计条目预设 HQL 条件生成器。
 *
 * <p>
 * 属性条件通过相关存在子查询生成。每个子查询只匹配一个属性行，因此组合查询和递归分组查询不会引入实体重复，
 * 实体查询与计数查询也能够使用完全相同的谓词结构。
 *
 * @author DwArFeng
 * @since 1.0.0-beta
 */
@Component
public class AuditEntryPresetConditionMaker implements PresetConditionMaker {

    private static final String ENTITY_ALIAS = "pojo";
    private static final String PROPERTY_ALIAS_PREFIX = "property";

    @Override
    public void makeCondition(HqlCondition condition, String preset, Object[] objs) {
        switch (preset) {
            case AuditEntryMaintainService.COMPOSITE_LOOKUP:
                compositeLookup(condition, objs);
                break;
            case AuditEntryMaintainService.GROUPED_LOOKUP:
                groupedLookup(condition, objs);
                break;
            default:
                throw new IllegalArgumentException("无法识别的预设: " + preset);
        }
    }

    private void compositeLookup(HqlCondition condition, Object[] objs) {
        try {
            AuditEntryCompositeLookupInfo lookupInfo = (AuditEntryCompositeLookupInfo) objs[0];
            AtomicInteger propertyAliasIndex = new AtomicInteger(0);
            condition.addWhereClause(makeLookupItemPredicate(
                    lookupInfo.getCategoryKey(), lookupInfo.getAuditEntryKey(), lookupInfo.getStartCreatedDate(),
                    lookupInfo.getEndCreatedDate(), lookupInfo.getPropertyConditions(), propertyAliasIndex
            ));
            addOrderByClauses(condition);
        } catch (Exception e) {
            throw new IllegalArgumentException("非法的组合查询参数", e);
        }
    }

    private void groupedLookup(HqlCondition condition, Object[] objs) {
        try {
            AuditEntryGroupedLookupInfo lookupInfo = (AuditEntryGroupedLookupInfo) objs[0];
            AtomicInteger propertyAliasIndex = new AtomicInteger(0);
            Set<AuditEntryGroupedLookupInfo.QueryGroup> visiting = Collections.newSetFromMap(new IdentityHashMap<>());
            condition.addWhereClause(makeGroupPredicate(
                    lookupInfo.getLogicOperator(), lookupInfo.getLookupItems(), lookupInfo.getQueryGroups(),
                    propertyAliasIndex, visiting
            ));
            addOrderByClauses(condition);
        } catch (Exception e) {
            throw new IllegalArgumentException("非法的分组查询参数", e);
        }
    }

    private PredicateClause makeGroupPredicate(
            int logicOperator, List<AuditEntryGroupedLookupInfo.LookupItem> lookupItems,
            List<AuditEntryGroupedLookupInfo.QueryGroup> queryGroups, AtomicInteger propertyAliasIndex,
            Set<AuditEntryGroupedLookupInfo.QueryGroup> visiting
    ) {
        List<PredicateClause> predicateClauses = new ArrayList<>();

        if (Objects.nonNull(lookupItems)) {
            for (AuditEntryGroupedLookupInfo.LookupItem lookupItem : lookupItems) {
                if (Objects.isNull(lookupItem) || !lookupItem.isEnabled()) {
                    continue;
                }
                predicateClauses.add(makeLookupItemPredicate(
                        lookupItem.getCategoryKey(), lookupItem.getAuditEntryKey(), lookupItem.getStartCreatedDate(),
                        lookupItem.getEndCreatedDate(), lookupItem.getPropertyConditions(), propertyAliasIndex
                ));
            }
        }

        if (Objects.nonNull(queryGroups)) {
            for (AuditEntryGroupedLookupInfo.QueryGroup queryGroup : queryGroups) {
                if (Objects.isNull(queryGroup) || !queryGroup.isEnabled()) {
                    continue;
                }
                if (!visiting.add(queryGroup)) {
                    throw new IllegalArgumentException("查询组不能循环引用");
                }
                try {
                    predicateClauses.add(makeGroupPredicate(
                            queryGroup.getLogicOperator(), queryGroup.getLookupItems(), queryGroup.getQueryGroups(),
                            propertyAliasIndex, visiting
                    ));
                } finally {
                    visiting.remove(queryGroup);
                }
            }
        }

        switch (logicOperator) {
            case Constants.LOGIC_OPERATOR_AND:
                return WhereHelper.and(predicateClauses);
            case Constants.LOGIC_OPERATOR_OR:
                return WhereHelper.or(predicateClauses);
            default:
                throw new IllegalArgumentException("非法的逻辑连接符: " + logicOperator);
        }
    }

    private PredicateClause makeLookupItemPredicate(
            StringIdKey categoryKey, LongIdKey auditEntryKey, Date startCreatedDate, Date endCreatedDate,
            List<?> propertyConditions, AtomicInteger propertyAliasIndex
    ) {
        List<PredicateClause> predicateClauses = new ArrayList<>();

        predicateClauses.add(WhereHelper.eq(ENTITY_ALIAS + ".categoryStringId", categoryKey.getStringId()));
        if (Objects.nonNull(auditEntryKey)) {
            predicateClauses.add(WhereHelper.eq(ENTITY_ALIAS + ".longId", auditEntryKey.getLongId()));
        }

        Date actualStartCreatedDate = Objects.isNull(startCreatedDate) ? minimumDate() : startCreatedDate;
        Date actualEndCreatedDate = Objects.isNull(endCreatedDate) ? maximumDate() : endCreatedDate;
        predicateClauses.add(WhereHelper.between(
                ENTITY_ALIAS + ".createdDate", actualStartCreatedDate, actualEndCreatedDate
        ));

        if (Objects.nonNull(propertyConditions)) {
            for (Object propertyCondition : propertyConditions) {
                if (propertyCondition instanceof AuditEntryCompositeLookupInfo.PropertyCondition) {
                    AuditEntryCompositeLookupInfo.PropertyCondition condition =
                            (AuditEntryCompositeLookupInfo.PropertyCondition) propertyCondition;
                    if (!condition.isEnabled()) {
                        continue;
                    }
                    predicateClauses.add(makePropertyPredicate(
                            condition.getPropertyId(), condition.getPropertyType(), condition.getPropertyValue(),
                            propertyAliasIndex
                    ));
                } else if (propertyCondition instanceof AuditEntryGroupedLookupInfo.PropertyCondition) {
                    AuditEntryGroupedLookupInfo.PropertyCondition condition =
                            (AuditEntryGroupedLookupInfo.PropertyCondition) propertyCondition;
                    if (!condition.isEnabled()) {
                        continue;
                    }
                    predicateClauses.add(makePropertyPredicate(
                            condition.getPropertyId(), condition.getPropertyType(), condition.getPropertyValue(),
                            propertyAliasIndex
                    ));
                } else {
                    throw new IllegalArgumentException("非法的属性条件: " + propertyCondition);
                }
            }
        }

        return WhereHelper.and(predicateClauses);
    }

    private PredicateClause makePropertyPredicate(
            String propertyId, int propertyType, Object propertyValue, AtomicInteger propertyAliasIndex
    ) {
        Objects.requireNonNull(propertyId, "属性 ID 不能为 null");
        Objects.requireNonNull(propertyValue, "属性值不能为 null");

        String propertyAlias = PROPERTY_ALIAS_PREFIX + propertyAliasIndex.getAndIncrement();
        List<PredicateClause> predicateClauses = new ArrayList<>();
        predicateClauses.add(WhereHelper.eqExpression(
                propertyAlias + ".auditEntryLongId", ENTITY_ALIAS + ".longId"
        ));
        predicateClauses.add(WhereHelper.eq(propertyAlias + ".propertyStringId", propertyId));
        predicateClauses.add(WhereHelper.eq(propertyAlias + ".propertyType", propertyType));
        predicateClauses.add(WhereHelper.eq(
                propertyAlias + "." + propertyValueFieldName(propertyType), propertyValue
        ));
        return WhereHelper.exists(
                HibernateAuditEntryProperty.class, propertyAlias, WhereHelper.and(predicateClauses)
        );
    }

    private String propertyValueFieldName(int propertyType) {
        switch (propertyType) {
            case Constants.PROPERTY_TYPE_STRING:
                return "stringValue";
            case Constants.PROPERTY_TYPE_LONG:
                return "longValue";
            case Constants.PROPERTY_TYPE_DOUBLE:
                return "doubleValue";
            case Constants.PROPERTY_TYPE_BOOLEAN:
                return "booleanValue";
            case Constants.PROPERTY_TYPE_DATE:
                return "dateValue";
            default:
                throw new IllegalArgumentException("非法的属性类型: " + propertyType);
        }
    }

    private void addOrderByClauses(HqlCondition condition) {
        condition.addOrderByClause(OrderByHelper.desc(ENTITY_ALIAS + ".createdDate"));
        condition.addOrderByClause(OrderByHelper.desc(ENTITY_ALIAS + ".longId"));
    }

    private Date minimumDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.set(1000, Calendar.JANUARY, 1, 0, 0, 0);
        return calendar.getTime();
    }

    private Date maximumDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.set(9999, Calendar.DECEMBER, 31, 23, 59, 59);
        return calendar.getTime();
    }
}
