package com.dwarfeng.audit.impl.handler.inspreceiver;

import com.dwarfeng.audit.sdk.handler.inspreceiver.AbstractInspectionReceiver;
import com.dwarfeng.audit.stack.exception.InspectionReceiverNotStartException;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Injvm 接收器。
 *
 * <p>
 * 该接收器适用于单节点服务，提供虚拟机内部的直接调用方式。
 *
 * @author DwArFeng
 * @since 1.1.0
 */
@Component
public class InjvmInspectionReceiver extends AbstractInspectionReceiver {

    public static final String RECEIVER_TYPE = "injvm";

    private static final Logger LOGGER = LoggerFactory.getLogger(InjvmInspectionReceiver.class);
    private final InjvmDispatcherCaller dispatcherCaller = new InjvmDispatcherCaller();

    public InjvmInspectionReceiver() {
        super(RECEIVER_TYPE);
    }

    @Override
    protected void doStart() {
        LOGGER.info("Injvm 接收器上线...");
    }

    @Override
    protected void doStop() {
        LOGGER.info("Injvm 接收器下线...");
    }

    public InjvmDispatcherCaller getDispatcherCaller() {
        return dispatcherCaller;
    }

    public class InjvmDispatcherCaller {

        public boolean isStarted() {
            return InjvmInspectionReceiver.this.isStarted();
        }

        public void execute(LongIdKey inspectionKey) {
            try {
                if (!isStarted()) {
                    throw new InspectionReceiverNotStartException();
                }
                InjvmInspectionReceiver.this.context.execute(inspectionKey);
            } catch (Exception e) {
                LOGGER.warn("接收器调用执行动作时发生异常, 将忽略自动审计执行 1 次, 相关 inspectionKey 为 {}", inspectionKey, e);
            }
        }
    }
}
