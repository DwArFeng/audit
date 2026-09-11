package com.dwarfeng.audit.impl.handler.pusher;

import com.alibaba.fastjson.JSON;
import com.dwarfeng.audit.sdk.bean.entity.FastJsonInspectionAlarm;
import com.dwarfeng.audit.sdk.bean.entity.FastJsonInspectionTask;
import com.dwarfeng.audit.sdk.handler.pusher.AbstractPusher;
import com.dwarfeng.audit.stack.bean.dto.PurgeFinishedResult;
import com.dwarfeng.audit.stack.bean.entity.InspectionAlarm;
import com.dwarfeng.audit.stack.bean.entity.InspectionTask;
import com.dwarfeng.subgrade.stack.exception.HandlerException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * 将信息输出至日志的推送器。
 *
 * @author DwArFeng
 * @since 1.0.0-beta
 */
@Component
public class LogPusher extends AbstractPusher {

    public static final String PUSHER_TYPE = "log";

    private static final Logger LOGGER = LoggerFactory.getLogger(LogPusher.class);

    private static final String LEVEL_TRACE = "TRACE";
    private static final String LEVEL_DEBUG = "DEBUG";
    private static final String LEVEL_INFO = "INFO";
    private static final String LEVEL_WARN = "WARN";
    private static final String LEVEL_ERROR = "ERROR";

    @Value("${com.dwarfeng.audit.pusher.log.log_level}")
    private String logLevel;

    public LogPusher() {
        super(PUSHER_TYPE);
    }

    @Override
    public void auditRecordReset() throws HandlerException {
        String title = "推送审核记录重置消息:";
        String message = StringUtils.EMPTY;
        logData(title, message);
    }

    @Override
    public void inspectionSuperviseReset() throws HandlerException {
        String title = "推送自动审计主管重置消息:";
        String message = StringUtils.EMPTY;
        logData(title, message);
    }

    @Override
    public void inspectionTaskFinished(InspectionTask inspectionTask) throws HandlerException {
        String title = "推送自动审计任务完成消息:";
        String message = String.format(
                "自动审计任务:\n%s",
                JSON.toJSONString(FastJsonInspectionTask.of(inspectionTask), true)
        );
        logData(title, message);
    }

    @Override
    public void inspectionTaskFailed(InspectionTask inspectionTask) throws HandlerException {
        String title = "推送自动审计任务失败消息:";
        String message = String.format(
                "自动审计任务:\n%s",
                JSON.toJSONString(FastJsonInspectionTask.of(inspectionTask), true)
        );
        logData(title, message);
    }

    @Override
    public void inspectionTaskExpired(InspectionTask inspectionTask) throws HandlerException {
        String title = "推送自动审计任务过期消息:";
        String message = String.format(
                "自动审计任务:\n%s",
                JSON.toJSONString(FastJsonInspectionTask.of(inspectionTask), true)
        );
        logData(title, message);
    }

    @Override
    public void inspectionTaskDied(InspectionTask inspectionTask) throws HandlerException {
        String title = "推送自动审计任务死亡消息:";
        String message = String.format(
                "自动审计任务:\n%s",
                JSON.toJSONString(FastJsonInspectionTask.of(inspectionTask), true)
        );
        logData(title, message);
    }

    @Override
    public void inspectionJobReset() throws HandlerException {
        String title = "推送自动审计作业重置消息:";
        String message = StringUtils.EMPTY;
        logData(title, message);
    }

    @Override
    public void inspectionAlarmCreated(InspectionAlarm inspectionAlarm) throws HandlerException {
        String title = "推送自动审计报警创建消息:";
        String message = JSON.toJSONString(FastJsonInspectionAlarm.of(inspectionAlarm), true);
        logData(title, message);
    }

    @Override
    public void purgeFinished(PurgeFinishedResult result) throws HandlerException {
        String title = "推送清除完成消息:";
        String message = Objects.toString(result);
        logData(title, message);
    }

    @Override
    public void purgeFailed() throws HandlerException {
        String title = "推送清除失败消息:";
        String message = StringUtils.EMPTY;
        logData(title, message);
    }

    private void logData(String title, String message) throws HandlerException {
        String logLevel = this.logLevel.toUpperCase();
        logString(title, logLevel);
        if (StringUtils.isNotEmpty(message)) {
            logString(message, logLevel);
        }
    }

    private void logString(String title, String logLevel) throws HandlerException {
        switch (logLevel) {
            case LEVEL_TRACE:
                LOGGER.trace(title);
                return;
            case LEVEL_DEBUG:
                LOGGER.debug(title);
                return;
            case LEVEL_INFO:
                LOGGER.info(title);
                return;
            case LEVEL_WARN:
                LOGGER.warn(title);
                return;
            case LEVEL_ERROR:
                LOGGER.error(title);
                return;
            default:
                throw new HandlerException("未知的日志等级: " + logLevel);
        }
    }

    @Override
    public String toString() {
        return "LogPusher{" +
                "logLevel='" + logLevel + '\'' +
                ", pusherType='" + pusherType + '\'' +
                '}';
    }
}
