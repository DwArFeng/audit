package com.dwarfeng.audit.impl.handler.inspdriver;

import com.dwarfeng.audit.sdk.handler.InspectionDriverSupporter;
import org.springframework.stereotype.Component;

/**
 * Cron 驱动器支持器。
 *
 * @author DwArFeng
 * @since 1.1.0
 */
@Component
public class CronInspectionDriverSupporter implements InspectionDriverSupporter {

    @Override
    public String provideType() {
        return CronInspectionDriverProvider.SUPPORT_TYPE;
    }

    @Override
    public String provideLabel() {
        return "Cron 驱动器";
    }

    @Override
    public String provideDescription() {
        return "根据指定的 Cron 表达式定时驱动自动审计";
    }

    @Override
    public String provideExampleParam() {
        return "0/2 * * * * *";
    }
}
