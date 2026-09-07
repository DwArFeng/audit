package com.dwarfeng.audit.impl.handler.inspdispatcher;

import com.dwarfeng.audit.impl.handler.inspreceiver.InjvmInspectionReceiver;
import com.dwarfeng.audit.sdk.handler.inspdispatcher.AbstractInspectionDispatcher;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Injvm 调度器。
 *
 * <p>
 * 该调度器适用于单节点服务，直接调用虚拟机内部接收器的调度调用器。
 *
 * @author DwArFeng
 * @since 1.1.0
 */
@Component
public class InjvmInspectionDispatcher extends AbstractInspectionDispatcher {

    public static final String DISPATCHER_TYPE = "injvm";

    private static final Logger LOGGER = LoggerFactory.getLogger(InjvmInspectionDispatcher.class);

    private final InjvmInspectionReceiver inspectionReceiver;

    public InjvmInspectionDispatcher(InjvmInspectionReceiver inspectionReceiver) {
        super(DISPATCHER_TYPE);
        this.inspectionReceiver = inspectionReceiver;
    }

    @Override
    protected void doStart() {
        LOGGER.info("Injvm 调度器上线...");
    }

    @Override
    protected void doStop() {
        LOGGER.info("Injvm 调度器下线...");
    }

    @Override
    protected void doDispatch(LongIdKey inspectionKey) {
        InjvmInspectionReceiver.InjvmDispatcherCaller caller = inspectionReceiver.getDispatcherCaller();
        if (!caller.isStarted()) {
            throw new IllegalStateException("Injvm 接收器未启动");
        }
        caller.execute(inspectionKey);
    }

    @Override
    public String toString() {
        return "InjvmInspectionDispatcher{" +
                "inspectionReceiver=" + inspectionReceiver +
                ", dispatcherType='" + dispatcherType + '\'' +
                '}';
    }
}
