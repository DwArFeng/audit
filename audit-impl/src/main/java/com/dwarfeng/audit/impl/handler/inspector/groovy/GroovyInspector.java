package com.dwarfeng.audit.impl.handler.inspector.groovy;

import com.dwarfeng.audit.sdk.handler.inspector.AbstractInspector;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * Groovy 审计器。
 *
 * @author DwArFeng
 * @since 1.1.0
 */
@Component("groovyInspectorRegistry.groovyInspector")
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class GroovyInspector extends AbstractInspector {

    private final ApplicationContext ctx;

    private final Processor processor;

    public GroovyInspector(ApplicationContext ctx, Processor processor) {
        this.ctx = ctx;
        this.processor = processor;
    }

    @Override
    protected Executor doNewExecutor() {
        return ctx.getBean(GroovyExecutor.class, processor);
    }

    @Override
    public String toString() {
        return "GroovyInspector{" +
                "ctx=" + ctx +
                ", processor=" + processor +
                '}';
    }
}
