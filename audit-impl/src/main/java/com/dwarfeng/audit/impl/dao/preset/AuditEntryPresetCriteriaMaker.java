package com.dwarfeng.audit.impl.dao.preset;

import com.dwarfeng.audit.stack.service.AuditEntryMaintainService;
import com.dwarfeng.subgrade.sdk.hibernate.criteria.PresetCriteriaMaker;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Objects;

@Component
public class AuditEntryPresetCriteriaMaker implements PresetCriteriaMaker {

    @Override
    public void makeCriteria(DetachedCriteria criteria, String preset, Object[] objs) {
        switch (preset) {
            case AuditEntryMaintainService.CHILD_FOR_AUDIT_CATEGORY:
                childForAuditCategory(criteria, objs);
                break;
            case AuditEntryMaintainService.CREATED_DATE_DESC:
                createdDateDesc(criteria, objs);
                break;
            default:
                throw new IllegalArgumentException("无法识别的预设: " + preset);
        }
    }

    private void childForAuditCategory(DetachedCriteria criteria, Object[] objs) {
        try {
            if (Objects.isNull(objs[0])) {
                criteria.add(Restrictions.isNull("categoryStringId"));
            } else {
                StringIdKey stringIdKey = (StringIdKey) objs[0];
                criteria.add(Restrictions.eqOrIsNull("categoryStringId", stringIdKey.getStringId()));
            }
        } catch (Exception e) {
            throw new IllegalArgumentException("非法的参数:" + Arrays.toString(objs));
        }
    }

    private void createdDateDesc(DetachedCriteria criteria, Object[] objs) {
        try {
            criteria.addOrder(Order.desc("createdDate"));
            criteria.addOrder(Order.desc("longId"));
        } catch (Exception e) {
            throw new IllegalArgumentException("非法的参数:" + Arrays.toString(objs));
        }
    }
}
