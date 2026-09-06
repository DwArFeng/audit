package com.dwarfeng.audit.impl.dao.preset;

import com.dwarfeng.audit.stack.service.AuditEntryPropertyMaintainService;
import com.dwarfeng.subgrade.sdk.hibernate.criteria.PresetCriteriaMaker;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class AuditEntryPropertyPresetCriteriaMaker implements PresetCriteriaMaker {

    @Override
    public void makeCriteria(DetachedCriteria criteria, String preset, Object[] objs) {
        switch (preset) {
            case AuditEntryPropertyMaintainService.CHILD_FOR_AUDIT_ENTRY:
                childForAuditEntry(criteria, objs);
                break;
            case AuditEntryPropertyMaintainService.CHILD_FOR_AUDIT_ENTRIES:
                childForAuditEntries(criteria, objs);
                break;
            default:
                throw new IllegalArgumentException("无法识别的预设: " + preset);
        }
    }

    private void childForAuditEntry(DetachedCriteria criteria, Object[] objs) {
        try {
            if (Objects.isNull(objs[0])) {
                criteria.add(Restrictions.isNull("auditEntryLongId"));
            } else {
                LongIdKey longIdKey = (LongIdKey) objs[0];
                criteria.add(Restrictions.eqOrIsNull("auditEntryLongId", longIdKey.getLongId()));
            }
        } catch (Exception e) {
            throw new IllegalArgumentException("非法的参数:" + Arrays.toString(objs));
        }
    }

    private void childForAuditEntries(DetachedCriteria criteria, Object[] objs) {
        try {
            if (!(objs[0] instanceof List<?>)) {
                throw new IllegalArgumentException("非法的参数:" + Arrays.toString(objs));
            }
            List<Long> longIds = ((List<?>) objs[0]).stream()
                    .map(LongIdKey.class::cast)
                    .map(LongIdKey::getLongId)
                    .collect(Collectors.toList());
            if (longIds.isEmpty()) {
                criteria.add(Restrictions.sqlRestriction("1 = 0"));
            } else {
                criteria.add(Restrictions.in("auditEntryLongId", longIds));
            }
        } catch (Exception e) {
            throw new IllegalArgumentException("非法的参数:" + Arrays.toString(objs));
        }
    }
}
