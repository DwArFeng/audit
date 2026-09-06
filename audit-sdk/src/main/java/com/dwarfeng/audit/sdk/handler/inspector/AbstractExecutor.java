package com.dwarfeng.audit.sdk.handler.inspector;

import com.dwarfeng.audit.stack.handler.Inspector.Context;
import com.dwarfeng.audit.stack.handler.Inspector.Executor;

/**
 * 审计器执行器的抽象实现。
 *
 * @author DwArFeng
 * @since 1.1.0
 */
public abstract class AbstractExecutor implements Executor {

    protected Context context;

    @Override
    public void init(Context context) {
        this.context = context;
    }

    @Override
    public String toString() {
        return "AbstractExecutor{" +
                "context=" + context +
                '}';
    }
}
