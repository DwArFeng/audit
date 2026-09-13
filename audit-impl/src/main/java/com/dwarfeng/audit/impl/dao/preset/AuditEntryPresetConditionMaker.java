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
                    lookupInfo.getEndCreatedDate(), lookupInfo.getCompositeItems(), propertyAliasIndex
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
                        lookupItem.getEndCreatedDate(), lookupItem.getCompositeItems(), propertyAliasIndex
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
            List<?> compositeItems, AtomicInteger propertyAliasIndex
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

        if (Objects.nonNull(compositeItems)) {
            for (Object compositeItem : compositeItems) {
                if (compositeItem instanceof AuditEntryCompositeLookupInfo.CompositeItem) {
                    AuditEntryCompositeLookupInfo.CompositeItem item =
                            (AuditEntryCompositeLookupInfo.CompositeItem) compositeItem;
                    if (!item.isEnabled()) {
                        continue;
                    }
                    predicateClauses.add(makeCompositeItemPredicate(
                            item.getPropertyId(), item.getPropertyType(), item.getFirstCondition(),
                            item.getSecondCondition(), propertyAliasIndex
                    ));
                } else if (compositeItem instanceof AuditEntryGroupedLookupInfo.CompositeItem) {
                    AuditEntryGroupedLookupInfo.CompositeItem item =
                            (AuditEntryGroupedLookupInfo.CompositeItem) compositeItem;
                    if (!item.isEnabled()) {
                        continue;
                    }
                    predicateClauses.add(makeCompositeItemPredicate(
                            item.getPropertyId(), item.getPropertyType(), item.getFirstCondition(),
                            item.getSecondCondition(), propertyAliasIndex
                    ));
                } else {
                    throw new IllegalArgumentException("非法的组合查询项: " + compositeItem);
                }
            }
        }

        return WhereHelper.and(predicateClauses);
    }

    private PredicateClause makeCompositeItemPredicate(
            String propertyId, int propertyType, Object firstCondition, Object secondCondition,
            AtomicInteger propertyAliasIndex
    ) {
        Objects.requireNonNull(propertyId, "属性 ID 不能为 null");

        String propertyAlias = PROPERTY_ALIAS_PREFIX + propertyAliasIndex.getAndIncrement();
        List<PredicateClause> predicateClauses = new ArrayList<>();
        predicateClauses.add(WhereHelper.eqExpression(
                propertyAlias + ".auditEntryLongId", ENTITY_ALIAS + ".longId"
        ));
        predicateClauses.add(WhereHelper.eq(propertyAlias + ".propertyStringId", propertyId));
        predicateClauses.add(WhereHelper.eq(propertyAlias + ".propertyType", propertyType));
        addPropertyValuePredicates(
                predicateClauses, propertyType, propertyAlias, firstCondition, secondCondition
        );
        return WhereHelper.exists(
                HibernateAuditEntryProperty.class, propertyAlias, WhereHelper.and(predicateClauses)
        );
    }

    private void addPropertyValuePredicates(
            List<PredicateClause> predicateClauses, int propertyType, String propertyAlias,
            Object firstCondition, Object secondCondition
    ) {
        switch (propertyType) {
            case Constants.PROPERTY_TYPE_STRING:
                predicateClauses.add(WhereHelper.like(
                        propertyAlias + ".stringValue", (String) firstCondition, MatchType.ANYWHERE
                ));
                break;
            case Constants.PROPERTY_TYPE_LONG:
                Long longFirstCondition = Objects.isNull(firstCondition) ?
                        Long.MIN_VALUE : Long.parseLong(firstCondition.toString());
                Long longSecondCondition = Objects.isNull(secondCondition) ?
                        Long.MAX_VALUE : Long.parseLong(secondCondition.toString());
                predicateClauses.add(WhereHelper.between(
                        propertyAlias + ".longValue", longFirstCondition, longSecondCondition
                ));
                break;
            case Constants.PROPERTY_TYPE_DOUBLE:
                Double doubleFirstCondition = Objects.isNull(firstCondition) ?
                        Double.MIN_VALUE : Double.parseDouble(firstCondition.toString());
                Double doubleSecondCondition = Objects.isNull(secondCondition) ?
                        Double.MAX_VALUE : Double.parseDouble(secondCondition.toString());
                predicateClauses.add(WhereHelper.between(
                        propertyAlias + ".doubleValue", doubleFirstCondition, doubleSecondCondition
                ));
                break;
            case Constants.PROPERTY_TYPE_BOOLEAN:
                predicateClauses.add(WhereHelper.eq(propertyAlias + ".booleanValue", firstCondition));
                break;
            case Constants.PROPERTY_TYPE_DATE:
                Date dateFirstCondition = Objects.isNull(firstCondition) ?
                        null : new Date(Long.parseLong(firstCondition.toString()));
                Date dateSecondCondition = Objects.isNull(secondCondition) ?
                        null : new Date(Long.parseLong(secondCondition.toString()));
                String columnName = propertyAlias + ".dateValue";
                if (dateFirstCondition != null && dateSecondCondition != null) {
                    predicateClauses.add(WhereHelper.between(
                            columnName, dateFirstCondition, dateSecondCondition
                    ));
                } else if (dateFirstCondition != null) {
                    predicateClauses.add(WhereHelper.ge(columnName, dateFirstCondition));
                } else if (dateSecondCondition != null) {
                    predicateClauses.add(WhereHelper.le(columnName, dateSecondCondition));
                }
                break;
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
