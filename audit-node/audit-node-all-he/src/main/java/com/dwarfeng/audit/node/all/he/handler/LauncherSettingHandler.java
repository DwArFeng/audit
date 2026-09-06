package com.dwarfeng.audit.node.all.he.handler;

import com.dwarfeng.subgrade.stack.handler.Handler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class LauncherSettingHandler implements Handler {

    @Value("${com.dwarfeng.audit.launcher.reset_inspector_support}")
    private boolean resetInspectorSupport;

    @Value("${com.dwarfeng.audit.launcher.start_audit_record_delay}")
    private long startAuditRecordDelay;
    @Value("${com.dwarfeng.audit.launcher.start_reset_delay}")
    private long startResetDelay;

    @Value("${com.dwarfeng.audit.launcher.online_inspection_task_check_delay}")
    private long onlineInspectionTaskCheckDelay;
    @Value("${com.dwarfeng.audit.launcher.enable_inspection_task_check_delay}")
    private long enableInspectionTaskCheckDelay;

    public boolean isResetInspectorSupport() {
        return resetInspectorSupport;
    }

    public long getStartAuditRecordDelay() {
        return startAuditRecordDelay;
    }

    public void setStartAuditRecordDelay(long startAuditRecordDelay) {
        this.startAuditRecordDelay = startAuditRecordDelay;
    }

    public long getStartResetDelay() {
        return startResetDelay;
    }

    public void setStartResetDelay(long startResetDelay) {
        this.startResetDelay = startResetDelay;
    }

    public long getOnlineInspectionTaskCheckDelay() {
        return onlineInspectionTaskCheckDelay;
    }

    public long getEnableInspectionTaskCheckDelay() {
        return enableInspectionTaskCheckDelay;
    }

    @Override
    public String toString() {
        return "LauncherSettingHandler{" +
                "resetInspectorSupport=" + resetInspectorSupport +
                ", startAuditRecordDelay=" + startAuditRecordDelay +
                ", startResetDelay=" + startResetDelay +
                ", onlineInspectionTaskCheckDelay=" + onlineInspectionTaskCheckDelay +
                ", enableInspectionTaskCheckDelay=" + enableInspectionTaskCheckDelay +
                '}';
    }
}
