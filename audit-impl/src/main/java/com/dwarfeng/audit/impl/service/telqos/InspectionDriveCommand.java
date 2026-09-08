package com.dwarfeng.audit.impl.service.telqos;

import com.dwarfeng.audit.stack.service.InspectionDriveQosService;
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
 * 自动审计驱动功能状态查看指令。
 *
 * <p>
 * 该指令用于查看自动审计驱动功能的当前启动状态。
 *
 * @author DwArFeng
 * @since 1.1.0
 */
@TelqosCommand
public class InspectionDriveCommand extends CliCommand {

    @SuppressWarnings({"SpellCheckingInspection", "GrazieInspectionRunner", "RedundantSuppression"})
    private static final String IDENTITY = "idrive";

    // region 指令选项

    private static final String COMMAND_OPTION_STATUS = "status";

    private static final String[] COMMAND_OPTION_ARRAY = new String[]{
            COMMAND_OPTION_STATUS
    };

    // endregion

    private final InspectionDriveQosService inspectionDriveQosService;

    public InspectionDriveCommand(InspectionDriveQosService inspectionDriveQosService) {
        super(IDENTITY);
        this.inspectionDriveQosService = inspectionDriveQosService;
    }

    @Override
    protected DescriptionProvider provideDescriptionProvider() {
        return context -> "自动审计驱动功能状态查看";
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
                .desc("查看自动审计驱动处理器状态").build());
        return list;
    }

    @Override
    protected void executeWithCmd(CommandExecutor.Context context, CommandLine cmd) throws Exception {
        Pair<String, Integer> pair = CliCommandUtil.analyseCommand(cmd, COMMAND_OPTION_ARRAY);
        if (pair.getRight() != 1) {
            context.sendMessage(CliCommandUtil.optionMismatchMessage(COMMAND_OPTION_ARRAY));
            context.sendMessage(context.getCommandManual(context.getRuntimeIdentity()));
            return;
        }
        context.sendMessage(String.format("started: %b.", inspectionDriveQosService.isStarted()));
    }
}
