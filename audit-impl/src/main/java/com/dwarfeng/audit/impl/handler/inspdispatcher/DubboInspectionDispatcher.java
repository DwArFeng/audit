package com.dwarfeng.audit.impl.handler.inspdispatcher;

import com.dwarfeng.audit.impl.handler.inspreceiver.DubboInspectionReceiver;
import com.dwarfeng.audit.sdk.handler.inspdispatcher.AbstractInspectionDispatcher;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import org.apache.dubbo.config.ReferenceConfig;
import org.apache.dubbo.config.RegistryConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Dubbo 调度器。
 *
 * <p>
 * 基于 Dubbo 实现的调度器，利用服务提供者机制将自动审计执行请求负载均衡到多个接收节点。
 *
 * @author DwArFeng
 * @since 1.1.0
 */
@Component
public class DubboInspectionDispatcher extends AbstractInspectionDispatcher {

    public static final String DISPATCHER_TYPE = "dubbo";

    private static final Logger LOGGER = LoggerFactory.getLogger(DubboInspectionDispatcher.class);

    private final RegistryConfig registry;

    @Value("${com.dwarfeng.audit.dubbo.provider.group:}")
    private String group;

    private final Lock lock = new ReentrantLock();
    private ReferenceConfig<DubboInspectionReceiver.DubboInspectionReceiverService> referenceConfig;

    @SuppressWarnings({"SpringJavaInjectionPointsAutowiringInspection", "RedundantSuppression"})
    public DubboInspectionDispatcher(RegistryConfig registry) {
        super(DISPATCHER_TYPE);
        this.registry = registry;
    }

    @Override
    protected void doStart() {
        lock.lock();
        try {
            LOGGER.info("Dubbo 调度器开启...");
            ReferenceConfig<DubboInspectionReceiver.DubboInspectionReceiverService> config = new ReferenceConfig<>();
            config.setRegistry(registry);
            config.setGroup(group);
            config.setCheck(false);
            config.setInterface(DubboInspectionReceiver.DubboInspectionReceiverService.class);
            config.setScope("remote");
            referenceConfig = config;
        } finally {
            lock.unlock();
        }
    }

    @Override
    protected void doStop() {
        lock.lock();
        try {
            LOGGER.info("Dubbo 调度器关闭...");
            if (Objects.nonNull(referenceConfig)) {
                referenceConfig.destroy();
                referenceConfig = null;
            }
        } finally {
            lock.unlock();
        }
    }

    @Override
    protected void doDispatch(LongIdKey inspectionKey) throws Exception {
        lock.lock();
        try {
            referenceConfig.get().execute(inspectionKey);
        } finally {
            lock.unlock();
        }
    }
}
