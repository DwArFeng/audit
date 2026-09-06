package com.dwarfeng.audit.impl.handler;

import com.dwarfeng.audit.stack.exception.InspectionReceiverException;
import com.dwarfeng.audit.stack.handler.ConsumeHandler;
import com.dwarfeng.audit.stack.handler.InspectionReceiver;
import com.dwarfeng.audit.stack.handler.InspectionReceiverHandler;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.exception.HandlerException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * 接收器处理器实现。
 *
 * @author DwArFeng
 * @since 1.1.0
 */
@Component
public class InspectionReceiverHandlerImpl implements InspectionReceiverHandler {

    private final ConsumeHandler<LongIdKey> consumeHandler;
    private final List<InspectionReceiver> receivers;

    @Value("${com.dwarfeng.audit.inspection_receiver.type}")
    private String receiverType;

    private InspectionReceiver receiver;
    private final InternalReceiverContext receiverContext = new InternalReceiverContext();

    public InspectionReceiverHandlerImpl(
            ConsumeHandler<LongIdKey> consumeHandler, List<InspectionReceiver> receivers
    ) {
        this.consumeHandler = consumeHandler;
        this.receivers = Optional.ofNullable(receivers).orElse(Collections.emptyList());
    }

    @PostConstruct
    public void init() throws HandlerException {
        receivers.forEach(item -> item.init(receiverContext));
        receiver = receivers.stream().filter(item -> item.supportType(receiverType)).findAny().orElseThrow(
                () -> new HandlerException("未知的接收器类型: " + receiverType)
        );
    }

    @Override
    public InspectionReceiver current() {
        return receiver;
    }

    @Override
    public List<InspectionReceiver> all() {
        return receivers;
    }

    private class InternalReceiverContext implements InspectionReceiver.Context {

        @Override
        public void execute(LongIdKey inspectionKey) throws InspectionReceiverException {
            try {
                consumeHandler.accept(inspectionKey);
            } catch (Exception e) {
                throw new InspectionReceiverException(e);
            }
        }

        @Override
        public String toString() {
            return "InternalReceiverContext{}";
        }
    }
}
