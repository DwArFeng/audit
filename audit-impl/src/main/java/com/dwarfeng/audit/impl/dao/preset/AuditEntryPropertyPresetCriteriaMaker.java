package com.dwarfeng.audit.impl.dao.preset;

import com.dwarfeng.audit.stack.service.AuditEntryPropertyMaintainService;
import com.dwarfeng.subgrade.sdk.hibernate.criteria.PresetCriteriaMaker;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Objects;

@Component
public class AuditEntryPropertyPresetCriteriaMaker implements PresetCriteriaMaker {

    @SuppressWarnings("SwitchStatementWithTooFewBranches")
    @Override
    public void makeCriteria(DetachedCriteria criteria, String preset, Object[] objs) {
        switch (preset) {
            case AuditEntryPropertyMaintainService.CHILD_FOR_AUDIT_ENTRY:
                childForAuditEntry(criteria, objs);
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
}
