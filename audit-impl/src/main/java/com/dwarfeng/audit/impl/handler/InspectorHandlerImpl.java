package com.dwarfeng.audit.impl.handler;

import com.dwarfeng.audit.sdk.handler.InspectorMaker;
import com.dwarfeng.audit.stack.exception.InspectorException;
import com.dwarfeng.audit.stack.exception.UnsupportedInspectorTypeException;
import com.dwarfeng.audit.stack.handler.Inspector;
import com.dwarfeng.audit.stack.handler.InspectorHandler;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 审计器处理器实现。
 *
 * @author DwArFeng
 * @since 1.1.0
 */
@Component
public class InspectorHandlerImpl implements InspectorHandler {

    private final List<InspectorMaker> inspectorMakers;

    public InspectorHandlerImpl(List<InspectorMaker> inspectorMakers) {
        this.inspectorMakers = inspectorMakers;
    }

    @Override
    public Inspector make(String type, String param) throws InspectorException {
        try {
            // 从注册的构造器中选择支持指定类型的实现。
            for (InspectorMaker maker : inspectorMakers) {
                if (maker.supportType(type)) {
                    return maker.makeInspector(type, param);
                }
            }
            throw new UnsupportedInspectorTypeException(type);
        } catch (InspectorException e) {
            throw e;
        } catch (Exception e) {
            throw new InspectorException(e);
        }
    }
}
