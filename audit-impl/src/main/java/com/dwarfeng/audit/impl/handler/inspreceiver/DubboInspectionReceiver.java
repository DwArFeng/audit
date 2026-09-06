package com.dwarfeng.audit.impl.handler.inspreceiver;

import com.dwarfeng.audit.sdk.handler.inspreceiver.AbstractInspectionReceiver;
import com.dwarfeng.subgrade.sdk.exception.ServiceExceptionHelper;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.dwarfeng.subgrade.stack.exception.ServiceExceptionMapper;
import com.dwarfeng.subgrade.stack.log.LogLevel;
import com.dwarfeng.subgrade.stack.service.Service;
import org.apache.dubbo.config.ProtocolConfig;
import org.apache.dubbo.config.RegistryConfig;
import org.apache.dubbo.config.ServiceConfig;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Dubbo 接收器。
 *
 * @author DwArFeng
 * @since 1.1.0
 */
@Component
public class DubboInspectionReceiver extends AbstractInspectionReceiver {

    public static final String RECEIVER_TYPE = "dubbo";

    private final ApplicationContext ctx;
    private final RegistryConfig registry;
    private final ProtocolConfig protocol;

    @Value("${com.dwarfeng.audit.inspection_receiver.dubbo.provider.group}")
    private String group;

    private final Lock lock = new ReentrantLock();
    private ServiceConfig<DubboInspectionReceiverService> serviceConfig;

    @SuppressWarnings({"SpringJavaInjectionPointsAutowiringInspection", "RedundantSuppression"})
    public DubboInspectionReceiver(
            ApplicationContext ctx, RegistryConfig registry, @Qualifier("dubbo") ProtocolConfig protocol
    ) {
        super(RECEIVER_TYPE);
        this.ctx = ctx;
        this.registry = registry;
        this.protocol = protocol;
    }

    @Override
    protected void doStart() {
        lock.lock();
        try {
            DubboInspectionReceiverService service = ctx.getBean(DubboInspectionReceiverService.class);
            ServiceConfig<DubboInspectionReceiverService> config = new ServiceConfig<>();
            config.setRegistry(registry);
            config.setProtocol(protocol);
            config.setGroup(group);
            config.setInterface(DubboInspectionReceiverService.class);
            config.setRef(service);
            config.export();
            serviceConfig = config;
        } finally {
            lock.unlock();
        }
    }

    @Override
    protected void doStop() {
        lock.lock();
        try {
            if (Objects.nonNull(serviceConfig)) {
                serviceConfig.unexport();
                serviceConfig = null;
            }
        } finally {
            lock.unlock();
        }
    }

    public interface DubboInspectionReceiverService extends Service {

        boolean execute(LongIdKey inspectionKey) throws ServiceException;
    }

    @org.springframework.stereotype.Service("inspectionReceiver.dubboInspectionReceiverServiceImpl")
    public class DubboInspectionReceiverServiceImpl implements DubboInspectionReceiverService {

        private final ServiceExceptionMapper sem;

        public DubboInspectionReceiverServiceImpl(ServiceExceptionMapper sem) {
            this.sem = sem;
        }

        @Override
        public boolean execute(LongIdKey inspectionKey) throws ServiceException {
            try {
                context.execute(inspectionKey);
                return true;
            } catch (Exception e) {
                throw ServiceExceptionHelper.logParse("发生异常", LogLevel.WARN, e, sem);
            }
        }
    }
}
