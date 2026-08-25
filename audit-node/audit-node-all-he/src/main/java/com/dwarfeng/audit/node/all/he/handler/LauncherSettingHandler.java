package com.dwarfeng.audit.node.all.he.handler;

import com.dwarfeng.subgrade.stack.handler.Handler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class LauncherSettingHandler implements Handler {

    @Value("${com.dwarfeng.audit.launcher.start_audit_record_delay}")
    private long startAuditRecordDelay;

    public long getStartAuditRecordDelay() {
        return startAuditRecordDelay;
    }

    public void setStartAuditRecordDelay(long startAuditRecordDelay) {
        this.startAuditRecordDelay = startAuditRecordDelay;
    }

    @Override
    public String toString() {
        return "LauncherSettingHandler{" +
                "startAuditRecordDelay=" + startAuditRecordDelay +
                '}';
    }
}
