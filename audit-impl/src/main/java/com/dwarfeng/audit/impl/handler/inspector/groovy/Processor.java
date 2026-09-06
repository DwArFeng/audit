package com.dwarfeng.audit.impl.handler.inspector.groovy;

import com.dwarfeng.audit.stack.handler.Inspector;

/**
 * Groovy 处理器。
 *
 * @author DwArFeng
 * @since 1.1.0
 */
public interface Processor {

    /**
     * 执行审计检查。
     *
     * <p>
     * 该方法被调用时，需要按照预定的逻辑检查审计记录，并通过 {@link Inspector.Context} 获取审计上下文及操作服务。
     *
     * @param context 审计器上下文。
     * @throws Exception 方法执行过程中发生的任何异常。
     */
    void inspect(Inspector.Context context) throws Exception;
}
