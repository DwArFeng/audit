package com.dwarfeng.audit.impl.configuration;

import com.alibaba.fastjson.parser.ParserConfig;
import com.dwarfeng.audit.sdk.bean.entity.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FastJsonConfiguration {

    private static final Logger LOGGER = LoggerFactory.getLogger(FastJsonConfiguration.class);

    public FastJsonConfiguration() {
        LOGGER.info("正在配置 FastJson autotype 白名单");
        ParserConfig.getGlobalInstance().addAccept(FastJsonAuditCategory.class.getCanonicalName());
        ParserConfig.getGlobalInstance().addAccept(FastJsonAuditPropertyIndicator.class.getCanonicalName());
        ParserConfig.getGlobalInstance().addAccept(FastJsonAuditEntry.class.getCanonicalName());
        ParserConfig.getGlobalInstance().addAccept(FastJsonAuditEntryProperty.class.getCanonicalName());
        ParserConfig.getGlobalInstance().addAccept(FastJsonInspectionAlarmTypeIndicator.class.getCanonicalName());
        ParserConfig.getGlobalInstance().addAccept(FastJsonInspection.class.getCanonicalName());
        ParserConfig.getGlobalInstance().addAccept(FastJsonInspectionAlarm.class.getCanonicalName());
        ParserConfig.getGlobalInstance().addAccept(FastJsonInspectionDriverInfo.class.getCanonicalName());
        ParserConfig.getGlobalInstance().addAccept(FastJsonInspectionDriverSupport.class.getCanonicalName());
        ParserConfig.getGlobalInstance().addAccept(FastJsonInspectionTask.class.getCanonicalName());
        ParserConfig.getGlobalInstance().addAccept(FastJsonInspectionTaskEvent.class.getCanonicalName());
        ParserConfig.getGlobalInstance().addAccept(FastJsonInspectorInfo.class.getCanonicalName());
        ParserConfig.getGlobalInstance().addAccept(FastJsonInspectorSupport.class.getCanonicalName());
        ParserConfig.getGlobalInstance().addAccept(FastJsonInspectorVariable.class.getCanonicalName());
        LOGGER.debug("FastJson autotype 白名单配置完毕");
    }
}
