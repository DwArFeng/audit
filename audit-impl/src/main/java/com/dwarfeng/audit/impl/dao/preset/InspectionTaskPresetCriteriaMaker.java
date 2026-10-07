package com.dwarfeng.audit.impl.dao.preset;

import com.dwarfeng.audit.sdk.util.Constants;
import com.dwarfeng.audit.stack.service.InspectionTaskMaintainService;
import com.dwarfeng.subgrade.sdk.hibernate.criteria.PresetCriteriaMaker;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class InspectionTaskPresetCriteriaMaker implements PresetCriteriaMaker {

    private static final Set<Integer> TO_PURGED_STATUS_SET;

    static {
        Set<Integer> TO_PURGED_STATUS_SET_DEJA_VU = new HashSet<>();
        TO_PURGED_STATUS_SET_DEJA_VU.add(Constants.INSPECTION_TASK_STATUS_FINISHED);
        TO_PURGED_STATUS_SET_DEJA_VU.add(Constants.INSPECTION_TASK_STATUS_FAILED);
        TO_PURGED_STATUS_SET_DEJA_VU.add(Constants.INSPECTION_TASK_STATUS_EXPIRED);
        TO_PURGED_STATUS_SET_DEJA_VU.add(Constants.INSPECTION_TASK_STATUS_DIED);
        TO_PURGED_STATUS_SET = Collections.unmodifiableSet(TO_PURGED_STATUS_SET_DEJA_VU);
    }

    @Override
    public void makeCriteria(DetachedCriteria criteria, String preset, Object[] objs) {
        switch (preset) {
            case InspectionTaskMaintainService.CHILD_FOR_INSPECTION:
                childForInspection(criteria, objs);
                break;
            case InspectionTaskMaintainService.SHOULD_EXPIRE:
                shouldExpire(criteria, objs);
                break;
            case InspectionTaskMaintainService.SHOULD_DIE:
                shouldDie(criteria, objs);
                break;
            case InspectionTaskMaintainService.TO_PURGED:
                toPurged(criteria, objs);
                break;
            case InspectionTaskMaintainService.CREATED_DATE_DESC:
                createdDateDesc(criteria, objs);
                break;
            default:
                throw new IllegalArgumentException("无法识别的预设: " + preset);
        }
    }

    @SuppressWarnings("DuplicatedCode")
    private void childForInspection(DetachedCriteria criteria, Object[] objs) {
        try {
            if (Objects.isNull(objs[0])) {
                criteria.add(Restrictions.isNull("inspectionLongId"));
            } else {
                LongIdKey longIdKey = (LongIdKey) objs[0];
                criteria.add(Restrictions.eqOrIsNull("inspectionLongId", longIdKey.getLongId()));
            }
        } catch (Exception e) {
            throw new IllegalArgumentException("非法的参数:" + Arrays.toString(objs));
        }
    }

    private void shouldExpire(DetachedCriteria criteria, Object[] objs) {
        try {
            criteria.add(Restrictions.in("status", Collections.singletonList(
                    Constants.INSPECTION_TASK_STATUS_CREATED
            )));
            criteria.add(Restrictions.le("shouldExpireDate", new Date()));
        } catch (Exception e) {
            throw new IllegalArgumentException("非法的参数:" + Arrays.toString(objs));
        }
    }

    private void shouldDie(DetachedCriteria criteria, Object[] objs) {
        try {
            criteria.add(Restrictions.in("status", Collections.singletonList(
                    Constants.INSPECTION_TASK_STATUS_PROCESSING
            )));
            criteria.add(Restrictions.le("shouldDieDate", new Date()));
        } catch (Exception e) {
            throw new IllegalArgumentException("非法的参数:" + Arrays.toString(objs));
        }
    }

    private void toPurged(DetachedCriteria criteria, Object[] objs) {
        try {
            criteria.add(Restrictions.in("status", TO_PURGED_STATUS_SET));
            criteria.add(Restrictions.lt("endedDate", objs[0]));
            criteria.addOrder(Order.asc("endedDate"));
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
