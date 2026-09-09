package com.dwarfeng.audit.impl.service.telqos;

import com.dwarfeng.audit.stack.service.InspectionDispatchQosService;
import com.dwarfeng.springtelqos.sdk.command.CliCommand;
import com.dwarfeng.springtelqos.sdk.configuration.TelqosCommand;
import com.dwarfeng.springtelqos.sdk.util.CliCommandUtil;
import com.dwarfeng.springtelqos.stack.command.CommandDescriptor;
import com.dwarfeng.springtelqos.stack.command.CommandExecutor;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Option;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.List;

/**
 * 自动审计调度功能状态查看指令。
 *
 * <p>
 * 该指令用于查看自动审计调度功能的当前启动状态。
 *
 * @author DwArFeng
 * @since 1.1.0
 */
@TelqosCommand
public class InspectionDispatchCommand extends CliCommand {

    @SuppressWarnings({"SpellCheckingInspection", "GrazieInspectionRunner", "RedundantSuppression"})
    private static final String IDENTITY = "idispatch";

    // region 指令选项

    private static final String COMMAND_OPTION_STATUS = "status";

    private static final String[] COMMAND_OPTION_ARRAY = new String[]{
            COMMAND_OPTION_STATUS
    };

    // endregion

    private final InspectionDispatchQosService inspectionDispatchQosService;

    public InspectionDispatchCommand(InspectionDispatchQosService inspectionDispatchQosService) {
        super(IDENTITY);
        this.inspectionDispatchQosService = inspectionDispatchQosService;
    }

    @Override
    protected DescriptionProvider provideDescriptionProvider() {
        return context -> "自动审计调度功能状态查看";
    }

    @Override
    protected CliSyntaxProvider provideCliSyntaxProvider() {
        return this::cliSyntaxProvider;
    }

    private String cliSyntaxProvider(CommandDescriptor.Context context) throws Exception {
        return CliCommandUtil.cliSyntax(
                context.getRuntimeIdentity() + " " + CliCommandUtil.concatOptionPrefix(COMMAND_OPTION_STATUS)
        );
    }

    @Override
    protected List<Option> provideOptions() {
        List<Option> list = new ArrayList<>();
        list.add(Option.builder(COMMAND_OPTION_STATUS).optionalArg(true).hasArg(false)
                .desc("查看自动审计调度处理器状态").build());
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
        context.sendMessage(String.format("started: %b.", inspectionDispatchQosService.isStarted()));
    }
}
