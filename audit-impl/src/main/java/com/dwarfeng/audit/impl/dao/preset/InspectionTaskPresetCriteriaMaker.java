package com.dwarfeng.audit.impl.dao.preset;

import com.dwarfeng.audit.sdk.util.Constants;
import com.dwarfeng.audit.stack.service.InspectionTaskMaintainService;
import com.dwarfeng.subgrade.sdk.hibernate.criteria.PresetCriteriaMaker;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.Objects;

@Component
public class InspectionTaskPresetCriteriaMaker implements PresetCriteriaMaker {

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
            default:
                throw new IllegalArgumentException("无法识别的预设: " + preset);
        }
    }

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
}
