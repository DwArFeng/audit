package com.dwarfeng.audit.impl.handler;

import com.dwarfeng.audit.stack.handler.Resetter;
import com.dwarfeng.audit.stack.handler.ResetterHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * 重置器处理器实现。
 *
 * <p>
 * 该处理器收集当前 Spring 上下文中启用的重置器，并在初始化后为它们提供统一的重置上下文。
 *
 * @author DwArFeng
 * @since 1.0.0-beta
 */
@Component
public class ResetterHandlerImpl implements ResetterHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(ResetterHandlerImpl.class);

    private final ResetProcessor resetProcessor;

    private final List<Resetter> resetters;

    private final InternalResetterContext resetterContext = new InternalResetterContext();

    public ResetterHandlerImpl(ResetProcessor resetProcessor, List<Resetter> resetters) {
        this.resetProcessor = resetProcessor;
        this.resetters = Optional.ofNullable(resetters).orElse(Collections.emptyList());
    }

    /**
     * 初始化全部重置器。
     */
    @PostConstruct
    public void init() {
        LOGGER.info("初始化重置器...");
        resetters.forEach(resetter -> resetter.init(resetterContext));
    }

    @Override
    public List<Resetter> all() {
        return resetters;
    }

    private class InternalResetterContext implements Resetter.Context {

        @Override
        public void resetAuditRecord() throws Exception {
            resetProcessor.resetAuditRecord();
        }

        @Override
        public void resetInspectionSupervise() throws Exception {
            resetProcessor.resetInspectionSupervise();
        }

        @Override
        public void resetInspectionJob() throws Exception {
            resetProcessor.resetInspectionJob();
        }
    }
}
