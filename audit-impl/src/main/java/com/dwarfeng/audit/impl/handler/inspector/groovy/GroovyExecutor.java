package com.dwarfeng.audit.impl.handler.inspector.groovy;

import com.dwarfeng.audit.sdk.handler.inspector.AbstractExecutor;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * Groovy 审计器执行器。
 *
 * @author DwArFeng
 * @since 1.1.0
 */
@Component("groovyInspectorRegistry.groovyExecutor")
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class GroovyExecutor extends AbstractExecutor {

    private final Processor processor;

    public GroovyExecutor(Processor processor) {
        this.processor = processor;
    }

    @Override
    public void inspect() throws Exception {
        processor.inspect(context);
    }

    @Override
    public String toString() {
        return "GroovyExecutor{" +
                "processor=" + processor +
                ", context=" + context +
                '}';
    }
}
