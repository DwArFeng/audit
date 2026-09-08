package com.dwarfeng.audit.impl.handler.inspdriver;

import com.dwarfeng.audit.sdk.handler.InspectionDriverProvider;
import com.dwarfeng.audit.stack.handler.InspectionDriver;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * Cron 驱动提供器。
 *
 * @author DwArFeng
 * @since 1.1.0
 */
@Component
public class CronInspectionDriverProvider implements InspectionDriverProvider {

    public static final String SUPPORT_TYPE = "cron_inspection_driver";
    private final InspectionDriver inspectionDriver;

    public CronInspectionDriverProvider(ThreadPoolTaskScheduler scheduler) {
        inspectionDriver = new ScheduledInspectionDriver(scheduler, ScheduledInspectionDriver.Mode.CRON);
    }

    @Override
    public boolean supportType(String type) {
        return Objects.equals(SUPPORT_TYPE, type);
    }

    @Override
    public InspectionDriver provide() {
        return inspectionDriver;
    }
}
