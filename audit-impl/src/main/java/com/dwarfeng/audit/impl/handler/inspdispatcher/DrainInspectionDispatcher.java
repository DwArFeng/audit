package com.dwarfeng.audit.impl.handler.inspdispatcher;

import com.dwarfeng.audit.sdk.handler.inspdispatcher.AbstractInspectionDispatcher;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * 丢弃所有调度请求的调度器。
 *
 * <p>
 * 该调度器用于测试和调试，不应该在生产环境中使用。
 *
 * @author DwArFeng
 * @since 1.1.0
 */
@Component
public class DrainInspectionDispatcher extends AbstractInspectionDispatcher {

    public static final String DISPATCHER_TYPE = "drain";
    private static final Logger LOGGER = LoggerFactory.getLogger(DrainInspectionDispatcher.class);

    public DrainInspectionDispatcher() {
        super(DISPATCHER_TYPE);
    }

    @Override
    protected void doStart() {
        LOGGER.info("Drain 调度器启动, 该调度器仅用于测试和调试, 不应该在生产环境中使用");
    }

    @Override
    protected void doStop() {
        LOGGER.info("Drain 调度器停止, 该调度器仅用于测试和调试, 不应该在生产环境中使用");
    }

    @Override
    protected void doDispatch(LongIdKey inspectionKey) {
        LOGGER.info("Drain 调度器接到调度请求并丢弃, 自动审计主键: {}", inspectionKey);
    }

    @Override
    public String toString() {
        return "DrainInspectionDispatcher{" +
                "dispatcherType='" + dispatcherType + '\'' +
                '}';
    }
}
