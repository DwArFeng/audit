package com.dwarfeng.audit.impl.handler;

import com.dwarfeng.audit.stack.handler.InspectionDispatcher;
import com.dwarfeng.audit.stack.handler.InspectionDispatcherHandler;
import com.dwarfeng.subgrade.stack.exception.HandlerException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * 调度器处理器实现。
 *
 * @author DwArFeng
 * @since 1.1.0
 */
@Component
public class InspectionDispatcherHandlerImpl implements InspectionDispatcherHandler {

    private final List<InspectionDispatcher> dispatchers;

    @Value("${com.dwarfeng.audit.inspection_dispatcher.type:injvm}")
    private String dispatcherType;

    private InspectionDispatcher dispatcher;

    public InspectionDispatcherHandlerImpl(List<InspectionDispatcher> dispatchers) {
        this.dispatchers = Optional.ofNullable(dispatchers).orElse(Collections.emptyList());
    }

    @PostConstruct
    public void init() throws HandlerException {
        dispatcher = dispatchers.stream().filter(item -> item.supportType(dispatcherType)).findAny().orElseThrow(
                () -> new HandlerException("未知的调度器类型: " + dispatcherType)
        );
    }

    @Override
    public InspectionDispatcher current() {
        return dispatcher;
    }

    @Override
    public List<InspectionDispatcher> all() {
        return dispatchers;
    }
}
