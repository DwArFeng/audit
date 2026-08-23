package com.dwarfeng.audit.impl.handler.pusher;

import com.dwarfeng.audit.sdk.handler.pusher.AbstractPusher;
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
        logData();
    }

    private void logData() throws HandlerException {
        String currentLogLevel = StringUtils.upperCase(logLevel);
        logString(currentLogLevel);
    }

    private void logString(String currentLogLevel) throws HandlerException {
        switch (currentLogLevel) {
            case LEVEL_TRACE:
                LOGGER.trace("推送审核记录重置消息:");
                return;
            case LEVEL_DEBUG:
                LOGGER.debug("推送审核记录重置消息:");
                return;
            case LEVEL_INFO:
                LOGGER.info("推送审核记录重置消息:");
                return;
            case LEVEL_WARN:
                LOGGER.warn("推送审核记录重置消息:");
                return;
            case LEVEL_ERROR:
                LOGGER.error("推送审核记录重置消息:");
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
