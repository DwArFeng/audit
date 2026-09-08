package com.dwarfeng.audit.node.all.he.launcher;

import com.dwarfeng.audit.node.all.he.handler.LauncherSettingHandler;
import com.dwarfeng.audit.stack.service.*;
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

    public static void main(String[] args) {
        ApplicationUtil.launch(new String[]{
                "classpath:spring/application-context*.xml",
                "file:opt/opt*.xml",
                "file:optext/opt*.xml"
        }, ctx -> {
            // 根据启动器设置处理器的设置，选择性重置审计器。
            mayResetInspector(ctx);

            // 根据启动器设置处理器的设置，选择性重置自动审计驱动器。
            mayResetInspectionDriver(ctx);

            // 根据启动器设置处理器的设置，选择性地开启审计记录服务。
            mayStartAuditRecord(ctx);

            // 根据启动器设置处理器的设置，选择性地开启重置服务。
            mayStartReset(ctx);

            // 根据启动器设置处理器的设置，选择性地上线自动审计任务检查服务。
            mayOnlineInspectionTaskCheck(ctx);
            // 根据启动器设置处理器的设置，选择性地启动自动审计任务检查服务。
            mayEnableInspectionTaskCheck(ctx);

            // 根据启动器设置处理器的设置，选择性地启动自动审计接收服务。
            mayStartInspectionReceiver(ctx);
        });
    }

    private static void mayResetInspector(ApplicationContext ctx) {
        // 获取启动器设置处理器，用于获取启动器设置，并按照设置选择性执行功能。
        LauncherSettingHandler launcherSettingHandler = ctx.getBean(LauncherSettingHandler.class);

        // 如果不重置审计器，则返回。
        if (!launcherSettingHandler.isResetInspectorSupport()) {
            return;
        }

        // 重置审计器支持。
        LOGGER.info("重置审计器支持...");
        SupportQosService supportQosService = ctx.getBean(SupportQosService.class);
        try {
            supportQosService.resetInspector();
        } catch (ServiceException e) {
            LOGGER.warn("审计器支持重置失败，异常信息如下", e);
        }
    }

    private static void mayResetInspectionDriver(ApplicationContext ctx) {
        // 获取启动器设置处理器，用于获取启动器设置，并按照设置选择性执行功能。
        LauncherSettingHandler launcherSettingHandler = ctx.getBean(LauncherSettingHandler.class);

        // 如果不重置自动审计驱动器，则返回。
        if (!launcherSettingHandler.isResetInspectionDriverSupport()) {
            return;
        }

        // 重置自动审计驱动器支持。
        LOGGER.info("重置自动审计驱动器支持...");
        SupportQosService supportQosService = ctx.getBean(SupportQosService.class);
        try {
            supportQosService.resetInspectionDriver();
        } catch (ServiceException e) {
            LOGGER.warn("自动审计驱动器支持重置失败，异常信息如下", e);
        }
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

    private static void mayStartReset(ApplicationContext ctx) {
        // 获取启动器设置处理器，用于获取启动器设置，并按照设置选择性执行功能。
        LauncherSettingHandler launcherSettingHandler = ctx.getBean(LauncherSettingHandler.class);

        // 获取程序中的 ThreadPoolTaskScheduler，用于处理计划任务。
        ThreadPoolTaskScheduler scheduler = ctx.getBean(ThreadPoolTaskScheduler.class);

        // 处理重置服务的启动选项。
        ResetQosService resetQosService = ctx.getBean(ResetQosService.class);

        // 重置服务是否启动。
        long startResetDelay = launcherSettingHandler.getStartResetDelay();
        if (startResetDelay == 0) {
            LOGGER.info("立即启动重置服务...");
            try {
                resetQosService.start();
            } catch (ServiceException e) {
                LOGGER.error("无法启动重置服务，异常原因如下", e);
            }
        } else if (startResetDelay > 0) {
            LOGGER.info("{} 毫秒后启动重置服务...", startResetDelay);
            scheduler.schedule(
                    () -> {
                        LOGGER.info("启动重置服务...");
                        try {
                            resetQosService.start();
                        } catch (ServiceException e) {
                            LOGGER.error("无法启动重置服务，异常原因如下", e);
                        }
                    },
                    new Date(System.currentTimeMillis() + startResetDelay)
            );
        }
    }

    private static void mayOnlineInspectionTaskCheck(ApplicationContext ctx) {
        // 获取启动器设置处理器，用于获取启动器设置，并按照设置选择性执行功能。
        LauncherSettingHandler launcherSettingHandler = ctx.getBean(LauncherSettingHandler.class);

        // 获取程序中的 ThreadPoolTaskScheduler，用于处理计划任务。
        ThreadPoolTaskScheduler scheduler = ctx.getBean(ThreadPoolTaskScheduler.class);

        // 获取自动审计任务检查 QOS 服务。
        InspectionTaskCheckQosService inspectionTaskCheckQosService =
                ctx.getBean(InspectionTaskCheckQosService.class);

        // 判断自动审计任务检查处理器是否上线自动审计任务检查服务，并按条件执行不同的操作。
        long onlineInspectionTaskCheckDelay = launcherSettingHandler.getOnlineInspectionTaskCheckDelay();
        if (onlineInspectionTaskCheckDelay == 0) {
            LOGGER.info("立即上线自动审计任务检查服务...");
            try {
                inspectionTaskCheckQosService.online();
            } catch (ServiceException e) {
                LOGGER.error("无法上线自动审计任务检查服务，异常原因如下", e);
            }
        } else if (onlineInspectionTaskCheckDelay > 0) {
            LOGGER.info("{} 毫秒后上线自动审计任务检查服务...", onlineInspectionTaskCheckDelay);
            scheduler.schedule(
                    () -> {
                        LOGGER.info("上线自动审计任务检查服务...");
                        try {
                            inspectionTaskCheckQosService.online();
                        } catch (ServiceException e) {
                            LOGGER.error("无法上线自动审计任务检查服务，异常原因如下", e);
                        }
                    },
                    new Date(System.currentTimeMillis() + onlineInspectionTaskCheckDelay)
            );
        }
    }

    private static void mayEnableInspectionTaskCheck(ApplicationContext ctx) {
        // 获取启动器设置处理器，用于获取启动器设置，并按照设置选择性执行功能。
        LauncherSettingHandler launcherSettingHandler = ctx.getBean(LauncherSettingHandler.class);

        // 获取程序中的 ThreadPoolTaskScheduler，用于处理计划任务。
        ThreadPoolTaskScheduler scheduler = ctx.getBean(ThreadPoolTaskScheduler.class);

        // 获取自动审计任务检查 QOS 服务。
        InspectionTaskCheckQosService inspectionTaskCheckQosService =
                ctx.getBean(InspectionTaskCheckQosService.class);

        // 判断自动审计任务检查处理器是否启动自动审计任务检查服务，并按条件执行不同的操作。
        long enableInspectionTaskCheckDelay = launcherSettingHandler.getEnableInspectionTaskCheckDelay();
        if (enableInspectionTaskCheckDelay == 0) {
            LOGGER.info("立即启动自动审计任务检查服务...");
            try {
                inspectionTaskCheckQosService.start();
            } catch (ServiceException e) {
                LOGGER.error("无法启动自动审计任务检查服务，异常原因如下", e);
            }
        } else if (enableInspectionTaskCheckDelay > 0) {
            LOGGER.info("{} 毫秒后启动自动审计任务检查服务...", enableInspectionTaskCheckDelay);
            scheduler.schedule(
                    () -> {
                        LOGGER.info("启动自动审计任务检查服务...");
                        try {
                            inspectionTaskCheckQosService.start();
                        } catch (ServiceException e) {
                            LOGGER.error("无法启动自动审计任务检查服务，异常原因如下", e);
                        }
                    },
                    new Date(System.currentTimeMillis() + enableInspectionTaskCheckDelay)
            );
        }
    }

    private static void mayStartInspectionReceiver(ApplicationContext ctx) {
        // 获取启动器设置处理器，用于获取启动器设置，并按照设置选择性执行功能。
        LauncherSettingHandler launcherSettingHandler = ctx.getBean(LauncherSettingHandler.class);

        // 获取程序中的 ThreadPoolTaskScheduler，用于处理计划任务。
        ThreadPoolTaskScheduler scheduler = ctx.getBean(ThreadPoolTaskScheduler.class);

        // 获取自动审计接收 QoS 服务。
        InspectionReceiverQosService inspectionReceiverQosService =
                ctx.getBean(InspectionReceiverQosService.class);

        // 判断自动审计接收服务是否启动，并按条件执行不同的操作。
        long startInspectionReceiverDelay = launcherSettingHandler.getStartInspectionReceiverDelay();
        if (startInspectionReceiverDelay == 0) {
            LOGGER.info("立即启动自动审计接收服务...");
            try {
                inspectionReceiverQosService.start();
            } catch (ServiceException e) {
                LOGGER.error("无法启动自动审计接收服务，异常原因如下", e);
            }
        } else if (startInspectionReceiverDelay > 0) {
            LOGGER.info("{} 毫秒后启动自动审计接收服务...", startInspectionReceiverDelay);
            scheduler.schedule(
                    () -> {
                        LOGGER.info("启动自动审计接收服务...");
                        try {
                            inspectionReceiverQosService.start();
                        } catch (ServiceException e) {
                            LOGGER.error("无法启动自动审计接收服务，异常原因如下", e);
                        }
                    },
                    new Date(System.currentTimeMillis() + startInspectionReceiverDelay)
            );
        }
    }
}
