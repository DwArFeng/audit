package com.dwarfeng.audit.impl.service.telqos;

import com.dwarfeng.audit.stack.service.AuditRecordQosService;
import com.dwarfeng.audit.stack.service.AuditRecordQosService.PersistenceStatus;
import com.dwarfeng.springtelqos.sdk.command.CliCommand;
import com.dwarfeng.springtelqos.sdk.configuration.TelqosCommand;
import com.dwarfeng.springtelqos.sdk.util.CliCommandUtil;
import com.dwarfeng.springtelqos.stack.command.CommandDescriptor;
import com.dwarfeng.springtelqos.stack.command.CommandExecutor;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Option;
import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ScheduledFuture;

/**
 * 持久侧消费者操作指令。
 *
 * <p>
 * 该指令用于操作和查看持久侧消费者，包括查看消费者状态、设置消费者参数等功能。支持持续输出消费者状态。
 *
 * @author DwArFeng
 * @since 1.0.0-beta
 */
@TelqosCommand
public class AuditRecordPersistenceConsumerCommand extends CliCommand {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuditRecordPersistenceConsumerCommand.class);

    @SuppressWarnings({"SpellCheckingInspection", "GrazieInspectionRunner", "RedundantSuppression"})
    private static final String IDENTITY = "arpcsu";

    // region 指令选项

    private static final String COMMAND_OPTION_L = "l";
    private static final String COMMAND_OPTION_S = "s";

    private static final String[] COMMAND_OPTION_ARRAY = new String[]{
            COMMAND_OPTION_L,
            COMMAND_OPTION_S
    };

    private static final String COMMAND_SUB_OPTION_H = "h";

    // endregion

    private final AuditRecordQosService auditRecordQosService;
    private final ThreadPoolTaskScheduler scheduler;

    public AuditRecordPersistenceConsumerCommand(
            AuditRecordQosService auditRecordQosService,
            ThreadPoolTaskScheduler scheduler
    ) {
        super(IDENTITY);
        this.auditRecordQosService = auditRecordQosService;
        this.scheduler = scheduler;
    }

    @Override
    protected DescriptionProvider provideDescriptionProvider() {
        return context -> "持久侧消费者操作";
    }

    @Override
    protected CliSyntaxProvider provideCliSyntaxProvider() {
        return this::cliSyntaxProvider;
    }

    private String cliSyntaxProvider(CommandDescriptor.Context context) throws Exception {
        String identity = context.getRuntimeIdentity();
        String[] patterns = new String[]{
                identity + " " + CliCommandUtil.concatOptionPrefix(COMMAND_OPTION_L) + " [" +
                        CliCommandUtil.concatOptionPrefix(COMMAND_SUB_OPTION_H) + "]",
                identity + " " + CliCommandUtil.concatOptionPrefix(COMMAND_OPTION_S) +
                        " [-b val] [-a val] [-m val] [-t val]"
        };
        return CliCommandUtil.cliSyntax(patterns);
    }

    @SuppressWarnings("DuplicatedCode")
    @Override
    protected List<Option> provideOptions() {
        List<Option> list = new ArrayList<>();
        list.add(Option.builder(COMMAND_OPTION_L).optionalArg(true).hasArg(false).desc("查看消费者状态").build());
        list.add(Option.builder(COMMAND_SUB_OPTION_H).desc("持续输出").build());
        list.add(Option.builder(COMMAND_OPTION_S).optionalArg(true).hasArg(false).desc("设置消费者参数").build());
        list.add(Option.builder("b").optionalArg(true).hasArg(true).type(Number.class)
                .argName("buffer-size").desc("缓冲器的大小").build());
        list.add(Option.builder("a").optionalArg(true).hasArg(true).type(Number.class)
                .argName("batch-size").desc("数据的批处理量").build());
        list.add(Option.builder("m").optionalArg(true).hasArg(true).type(Number.class)
                .argName("max-idle-time").desc("最大空闲时间").build());
        list.add(Option.builder("t").optionalArg(true).hasArg(true).type(Number.class)
                .argName("thread").desc("消费者的线程数量").build());
        return list;
    }

    @SuppressWarnings("DuplicatedCode")
    @Override
    protected void executeWithCmd(CommandExecutor.Context context, CommandLine cmd) throws Exception {
        Pair<String, Integer> pair = CliCommandUtil.analyseCommand(cmd, COMMAND_OPTION_ARRAY);
        if (pair.getRight() != 1) {
            context.sendMessage(CliCommandUtil.optionMismatchMessage(COMMAND_OPTION_ARRAY));
            context.sendMessage(context.getCommandManual(context.getRuntimeIdentity()));
            return;
        }
        switch (pair.getLeft()) {
            case COMMAND_OPTION_L:
                handleL(context, cmd);
                break;
            case COMMAND_OPTION_S:
                handleS(context, cmd);
                break;
            default:
                throw new IllegalStateException("不应该执行到此处, 请联系开发人员");
        }
    }

    private void handleL(CommandExecutor.Context context, CommandLine cmd) throws Exception {
        // 如果命令行中包含 COMMAND_SUB_OPTION_H 选项，则持续输出。
        if (cmd.hasOption(COMMAND_SUB_OPTION_H)) {
            ScheduledFuture<?> future = scheduler.scheduleWithFixedDelay(
                    () -> {
                        try {
                            printConsumerStatus(context);
                        } catch (Exception e) {
                            LOGGER.warn("持续输出持久侧消费者状态时发生异常, 异常信息如下: ", e);
                        }
                    },
                    1000
            );
            // 等待用户输入任意字符，然后停止持续输出。
            context.sendMessage("输入任意字符停止持续输出");
            context.receiveMessage();
            future.cancel(true);
        } else {
            printConsumerStatus(context);
        }
    }

    private void handleS(CommandExecutor.Context context, CommandLine cmd) throws Exception {
        Integer newBufferSize = null;
        Integer newBatchSize = null;
        Long newMaxIdleTime = null;
        Integer newThread = null;
        try {
            if (cmd.hasOption("b")) newBufferSize = Integer.parseInt(cmd.getOptionValue("b"));
            if (cmd.hasOption("a")) newBatchSize = Integer.parseInt(cmd.getOptionValue("a"));
            if (cmd.hasOption("m")) newMaxIdleTime = Long.parseLong(cmd.getOptionValue("m"));
            if (cmd.hasOption("t")) newThread = Integer.parseInt(cmd.getOptionValue("t"));
        } catch (Exception e) {
            LOGGER.warn("解析命令选项时发生异常，异常信息如下", e);
            context.sendMessage("命令行格式错误，正确的格式为: " + context.getRuntimeIdentity() + " " +
                    CliCommandUtil.concatOptionPrefix(COMMAND_OPTION_S) +
                    " [-b val] [-a val] [-m val] [-t val]");
            context.sendMessage("请留意选项 b,a,m,t 后接参数的类型应该是数字 ");
            return;
        }

        PersistenceStatus persistenceStatus = auditRecordQosService.getPersistenceStatus();
        int bufferSize = Objects.nonNull(newBufferSize) ? newBufferSize : persistenceStatus.getBufferSize();
        int batchSize = Objects.nonNull(newBatchSize) ? newBatchSize : persistenceStatus.getBatchSize();
        long maxIdleTime = Objects.nonNull(newMaxIdleTime) ? newMaxIdleTime : persistenceStatus.getMaxIdleTime();
        int thread = Objects.nonNull(newThread) ? newThread : persistenceStatus.getThread();
        auditRecordQosService.setPersistenceParameters(bufferSize, batchSize, maxIdleTime, thread);

        context.sendMessage("设置完成，持久侧消费者新的参数为: ");
        printConsumerStatus(context);
    }

    private void printConsumerStatus(CommandExecutor.Context context) throws Exception {
        PersistenceStatus persistenceStatus = auditRecordQosService.getPersistenceStatus();
        String format = "buffered-size:%-7d " +
                "buffer-size:%-7d " +
                "batch-size:%-7d " +
                "max-idle-time:%-10d " +
                "thread:%-3d " +
                "idle:%b";
        context.sendMessage(
                String.format(
                        format, persistenceStatus.getBufferedSize(), persistenceStatus.getBufferSize(),
                        persistenceStatus.getBatchSize(), persistenceStatus.getMaxIdleTime(),
                        persistenceStatus.getThread(), persistenceStatus.isIdle()
                )
        );
    }
}
