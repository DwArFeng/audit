package com.dwarfeng.audit.impl.handler.inspdriver;

import com.dwarfeng.audit.sdk.handler.InspectionDriverSupporter;
import org.springframework.stereotype.Component;

/**
 * 固定间隔驱动器支持器。
 *
 * @author DwArFeng
 * @since 1.1.0
 */
@Component
public class FixedDelayInspectionDriverSupporter implements InspectionDriverSupporter {

    @Override
    public String provideType() {
        return FixedDelayInspectionDriverProvider.SUPPORT_TYPE;
    }

    @Override
    public String provideLabel() {
        return "固定间隔驱动器";
    }

    @Override
    public String provideDescription() {
        return "按照固定间隔驱动自动审计，延迟执行后会顺延后续执行";
    }

    @Override
    public String provideExampleParam() {
        return "60000";
    }
}
