package com.dwarfeng.audit.impl.handler;

import com.dwarfeng.audit.sdk.handler.InspectionDriverProvider;
import com.dwarfeng.audit.stack.exception.InspectionDriverException;
import com.dwarfeng.audit.stack.exception.UnsupportedInspectionDriverTypeException;
import com.dwarfeng.audit.stack.handler.InspectionDispatcherHandler;
import com.dwarfeng.audit.stack.handler.InspectionDriver;
import com.dwarfeng.audit.stack.handler.InspectionDriverHandler;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.exception.HandlerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * 驱动器处理器实现。
 *
 * @author DwArFeng
 * @since 1.1.0
 */
@Component
public class InspectionDriverHandlerImpl implements InspectionDriverHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(InspectionDriverHandlerImpl.class);

    private final InspectionDispatcherHandler inspectionDispatcherHandler;
    private final List<InspectionDriverProvider> inspectionDriverProviders;
    private final InternalDriverContext driverContext = new InternalDriverContext();

    public InspectionDriverHandlerImpl(
            InspectionDispatcherHandler inspectionDispatcherHandler,
            List<InspectionDriverProvider> inspectionDriverProviders
    ) {
        this.inspectionDispatcherHandler = inspectionDispatcherHandler;
        this.inspectionDriverProviders = Optional.ofNullable(inspectionDriverProviders).orElse(Collections.emptyList());
    }

    @PostConstruct
    public void init() {
        LOGGER.info("初始化自动审计驱动器...");
        inspectionDriverProviders.stream().map(InspectionDriverProvider::provide)
                .forEach(driver -> driver.init(driverContext));
    }

    @Override
    public InspectionDriver find(String type) throws HandlerException {
        return inspectionDriverProviders.stream().filter(provider -> provider.supportType(type))
                .map(InspectionDriverProvider::provide).findAny()
                .orElseThrow(() -> new UnsupportedInspectionDriverTypeException(type));
    }

    private class InternalDriverContext implements InspectionDriver.Context {

        @Override
        public void execute(LongIdKey inspectionKey) throws InspectionDriverException {
            try {
                inspectionDispatcherHandler.current().dispatch(inspectionKey);
            } catch (Exception e) {
                throw new InspectionDriverException(e);
            }
        }
    }
}
