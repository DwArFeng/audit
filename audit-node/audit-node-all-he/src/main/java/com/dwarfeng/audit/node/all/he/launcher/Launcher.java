package com.dwarfeng.audit.node.all.he.launcher;

import com.dwarfeng.audit.node.all.he.handler.LauncherSettingHandler;
import com.dwarfeng.audit.stack.service.AuditRecordQosService;
import com.dwarfeng.springterminator.sdk.util.ApplicationUtil;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

import java.util.Date;

/**
 * 程序启动器。
 *
 * @author DwArFeng
 * @since 1.0.0-beta
 */
public class Launcher {

    private final static Logger LOGGER = LoggerFactory.getLogger(Launcher.class);

    @SuppressWarnings("Convert2MethodRef")
    public static void main(String[] args) {
        ApplicationUtil.launch(new String[]{
                "classpath:spring/application-context*.xml",
                "file:opt/opt*.xml",
                "file:optext/opt*.xml"
        }, ctx -> {
            // 根据启动器设置处理器的设置，选择性地开启审计记录服务。
            mayStartAuditRecord(ctx);
        });
    }

    private static void mayStartAuditRecord(ApplicationContext ctx) {
        // 获取启动器设置处理器，用于获取启动器设置，并按照设置选择性执行功能。
        LauncherSettingHandler launcherSettingHandler = ctx.getBean(LauncherSettingHandler.class);

        // 获取程序中的 ThreadPoolTaskScheduler，用于处理计划任务。
        ThreadPoolTaskScheduler scheduler = ctx.getBean(ThreadPoolTaskScheduler.class);

        // 处理审计记录服务的启动选项。
        AuditRecordQosService auditRecordQosService = ctx.getBean(AuditRecordQosService.class);

        // 判断是否开启审计记录服务。
        long startAuditRecordDelay = launcherSettingHandler.getStartAuditRecordDelay();
        if (startAuditRecordDelay == 0) {
            LOGGER.info("立即启动审计记录服务...");
            try {
                auditRecordQosService.logicStart();
            } catch (ServiceException e) {
                LOGGER.error("无法启动审计记录服务，异常原因如下", e);
            }
        } else if (startAuditRecordDelay > 0) {
            LOGGER.info("{} 毫秒后启动审计记录服务...", startAuditRecordDelay);
            scheduler.schedule(
                    () -> {
                        LOGGER.info("启动审计记录服务...");
                        try {
                            auditRecordQosService.logicStart();
                        } catch (ServiceException e) {
                            LOGGER.error("无法启动审计记录服务，异常原因如下", e);
                        }
                    },
                    new Date(System.currentTimeMillis() + startAuditRecordDelay)
            );
        }
    }
}
