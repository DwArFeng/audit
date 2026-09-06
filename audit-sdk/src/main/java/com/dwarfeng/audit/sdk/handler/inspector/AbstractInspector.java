package com.dwarfeng.audit.sdk.handler.inspector;

import com.dwarfeng.audit.stack.exception.InspectorException;
import com.dwarfeng.audit.stack.handler.Inspector;

/**
 * 审计器的抽象实现。
 *
 * @author DwArFeng
 * @since 1.1.0
 */
public abstract class AbstractInspector implements Inspector {

    @Override
    public Executor newExecutor() throws InspectorException {
        try {
            return doNewExecutor();
        } catch (InspectorException e) {
            throw e;
        } catch (Exception e) {
            throw new InspectorException(e);
        }
    }

    protected abstract Executor doNewExecutor() throws Exception;

    @Override
    public String toString() {
        return "AbstractInspector{}";
    }
}
