package com.dwarfeng.audit.impl.handler.inspector.groovy;

import com.dwarfeng.audit.sdk.handler.inspector.AbstractInspectorRegistry;
import com.dwarfeng.audit.stack.exception.InspectorException;
import com.dwarfeng.audit.stack.exception.InspectorMakeException;
import com.dwarfeng.audit.stack.handler.Inspector;
import com.dwarfeng.dutil.basic.io.IOUtil;
import com.dwarfeng.dutil.basic.io.StringOutputStream;
import groovy.lang.GroovyClassLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;

/**
 * Groovy 审计器注册。
 *
 * <p>
 * 该注册实现将参数作为 Groovy 源码编译为 {@link Processor}，并为每次构造请求创建独立的审计器实例。
 *
 * @author DwArFeng
 * @since 1.1.0
 */
@Component
public class GroovyInspectorRegistry extends AbstractInspectorRegistry {

    public static final String INSPECTOR_TYPE = "groovy_inspector";

    private static final Logger LOGGER = LoggerFactory.getLogger(GroovyInspectorRegistry.class);

    private final ApplicationContext ctx;

    public GroovyInspectorRegistry(ApplicationContext ctx) {
        super(INSPECTOR_TYPE);
        this.ctx = ctx;
    }

    @Override
    public String provideLabel() {
        return "Groovy 审计器";
    }

    @Override
    public String provideDescription() {
        return "通过 Groovy 脚本检查审计记录，并按需创建自动审计报警。";
    }

    @Override
    public String provideExampleParam() {
        try {
            Resource resource = ctx.getResource("classpath:groovy/ExampleInspectorProcessor.groovy");
            String example;
            try (InputStream sin = resource.getInputStream();
                 StringOutputStream sout = new StringOutputStream(StandardCharsets.UTF_8, true)) {
                IOUtil.trans(sin, sout, 4096);
                sout.flush();
                example = sout.toString();
            }
            return example;
        } catch (Exception e) {
            LOGGER.warn("读取文件 classpath:groovy/ExampleInspectorProcessor.groovy 时出现异常", e);
            return "";
        }
    }

    @Override
    public Inspector makeInspector(String type, String param) throws InspectorException {
        try (GroovyClassLoader classLoader = new GroovyClassLoader()) {
            // 通过 Groovy 脚本生成处理器。
            Class<?> aClass = classLoader.parseClass(param);
            Processor processor = (Processor) aClass.newInstance();
            // 生成并返回审计器。
            return ctx.getBean(GroovyInspector.class, ctx, processor);
        } catch (Exception e) {
            throw new InspectorMakeException(e);
        }
    }

    @Override
    public String toString() {
        return "GroovyInspectorRegistry{" +
                "ctx=" + ctx +
                ", inspectorType='" + inspectorType + '\'' +
                '}';
    }
}
