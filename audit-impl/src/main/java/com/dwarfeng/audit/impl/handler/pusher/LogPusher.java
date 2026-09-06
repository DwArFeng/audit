package com.dwarfeng.audit.impl.handler.pusher;

import com.alibaba.fastjson.JSON;
import com.dwarfeng.audit.sdk.bean.entity.FastJsonInspectionAlarm;
import com.dwarfeng.audit.sdk.handler.pusher.AbstractPusher;
import com.dwarfeng.audit.stack.bean.entity.InspectionAlarm;
import com.dwarfeng.subgrade.stack.exception.HandlerException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

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
        logData("推送审核记录重置消息:");
    }

    @Override
    public void inspectionAlarmCreated(InspectionAlarm inspectionAlarm) throws HandlerException {
        logData("推送自动审计报警创建消息:");
        logData(JSON.toJSONString(FastJsonInspectionAlarm.of(inspectionAlarm), true));
    }

    private void logData(String message) throws HandlerException {
        String currentLogLevel = StringUtils.upperCase(logLevel);
        logString(message, currentLogLevel);
    }

    private void logString(String message, String currentLogLevel) throws HandlerException {
        switch (currentLogLevel) {
            case LEVEL_TRACE:
                LOGGER.trace(message);
                return;
            case LEVEL_DEBUG:
                LOGGER.debug(message);
                return;
            case LEVEL_INFO:
                LOGGER.info(message);
                return;
            case LEVEL_WARN:
                LOGGER.warn(message);
                return;
            case LEVEL_ERROR:
                LOGGER.error(message);
                return;
            default:
                throw new HandlerException("未知的日志等级: " + currentLogLevel);
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
