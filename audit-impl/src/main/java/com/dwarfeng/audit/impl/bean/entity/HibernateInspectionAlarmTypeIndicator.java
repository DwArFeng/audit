package com.dwarfeng.audit.impl.bean.entity;

import com.dwarfeng.audit.sdk.util.Constraints;
import com.dwarfeng.subgrade.sdk.bean.key.HibernateStringIdKey;
import com.dwarfeng.subgrade.stack.bean.Bean;

import javax.persistence.*;
import java.util.Optional;

@Entity
@IdClass(HibernateStringIdKey.class)
@Table(name = "tbl_inspection_alarm_type_indicator")
public class HibernateInspectionAlarmTypeIndicator implements Bean {

    private static final long serialVersionUID = -5421790906500297379L;

    // region 主键

    @Id
    @Column(name = "id", length = Constraints.LENGTH_STRING_ID, nullable = false, unique = true)
    private String stringId;

    // endregion

    // region 主属性字段

    @Column(name = "label", length = Constraints.LENGTH_LABEL, nullable = false)
    private String label;

    @Column(name = "remark", length = Constraints.LENGTH_REMARK)
    private String remark;

    // endregion

    public HibernateInspectionAlarmTypeIndicator() {
    }

    // region 映射用属性区

    public HibernateStringIdKey getKey() {
        return Optional.ofNullable(stringId).map(HibernateStringIdKey::new).orElse(null);
    }

    public void setKey(HibernateStringIdKey key) {
        this.stringId = Optional.ofNullable(key).map(HibernateStringIdKey::getStringId).orElse(null);
    }

    // endregion

    // region 常规属性区

    public String getStringId() {
        return stringId;
    }

    public void setStringId(String stringId) {
        this.stringId = stringId;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    // endregion

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "stringId = " + stringId + ", " +
                "label = " + label + ", " +
                "remark = " + remark + ")";
    }
}
