package com.dwarfeng.audit.impl.handler.inspdriver;

import com.dwarfeng.audit.sdk.handler.InspectionDriverSupporter;
import org.springframework.stereotype.Component;

/**
 * 固定频率驱动器支持器。
 *
 * @author DwArFeng
 * @since 1.1.0
 */
@Component
public class FixedRateInspectionDriverSupporter implements InspectionDriverSupporter {

    @Override
    public String provideType() {
        return FixedRateInspectionDriverProvider.SUPPORT_TYPE;
    }

    @Override
    public String provideLabel() {
        return "固定频率驱动器";
    }

    @Override
    public String provideDescription() {
        return "按照固定频率驱动自动审计，延迟执行后会提前后续执行以维持频率";
    }

    @Override
    public String provideExampleParam() {
        return "60000";
    }
}
