package com.dwarfeng.audit.impl.service.telqos;

import com.dwarfeng.audit.stack.handler.InspectionReceiver;
import com.dwarfeng.audit.stack.service.InspectionReceiverQosService;
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
 * 自动审计接收器操作指令。
 *
 * <p>
 * 该指令用于查看当前正在使用的自动审计接收器，以及全部可用的自动审计接收器。
 *
 * @author DwArFeng
 * @since 1.1.0
 */
@TelqosCommand
public class InspectionReceiverCommand extends CliCommand {

    @SuppressWarnings({"SpellCheckingInspection", "GrazieInspectionRunner", "RedundantSuppression"})
    private static final String IDENTITY = "ireceiver";

    // region 指令选项

    private static final String COMMAND_OPTION_CURRENT = "current";
    private static final String COMMAND_OPTION_ALL = "all";

    private static final String[] COMMAND_OPTION_ARRAY = new String[]{
            COMMAND_OPTION_CURRENT,
            COMMAND_OPTION_ALL
    };

    // endregion

    private final InspectionReceiverQosService inspectionReceiverQosService;

    public InspectionReceiverCommand(InspectionReceiverQosService inspectionReceiverQosService) {
        super(IDENTITY);
        this.inspectionReceiverQosService = inspectionReceiverQosService;
    }

    @Override
    protected DescriptionProvider provideDescriptionProvider() {
        return context -> "自动审计接收器操作/查看";
    }

    @Override
    protected CliSyntaxProvider provideCliSyntaxProvider() {
        return this::cliSyntaxProvider;
    }

    private String cliSyntaxProvider(CommandDescriptor.Context context) throws Exception {
        String identity = context.getRuntimeIdentity();
        String[] patterns = new String[]{
                identity + " " + CliCommandUtil.concatOptionPrefix(COMMAND_OPTION_CURRENT),
                identity + " " + CliCommandUtil.concatOptionPrefix(COMMAND_OPTION_ALL)
        };
        return CliCommandUtil.cliSyntax(patterns);
    }

    @Override
    protected List<Option> provideOptions() {
        List<Option> list = new ArrayList<>();
        list.add(Option.builder(COMMAND_OPTION_CURRENT).optionalArg(true).hasArg(false)
                .desc("查看当前自动审计接收器").build());
        list.add(Option.builder(COMMAND_OPTION_ALL).optionalArg(true).hasArg(false)
                .desc("查看全部自动审计接收器").build());
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
            case COMMAND_OPTION_CURRENT:
                printCurrent(context);
                break;
            case COMMAND_OPTION_ALL:
                printAll(context);
                break;
            default:
                throw new IllegalStateException("不应该执行到此处, 请联系开发人员");
        }
    }

    private void printCurrent(CommandExecutor.Context context) throws Exception {
        InspectionReceiver currentReceiver = inspectionReceiverQosService.currentReceiver();
        context.sendMessage("current inspection receiver:");
        context.sendMessage(String.format("  %s", currentReceiver));
    }

    private void printAll(CommandExecutor.Context context) throws Exception {
        List<InspectionReceiver> receivers = inspectionReceiverQosService.allReceivers();
        context.sendMessage("all inspection receivers:");
        int total = receivers.size();
        int index = 0;
        for (InspectionReceiver receiver : receivers) {
            context.sendMessage(String.format("  %d/%d: %s", ++index, total, receiver));
        }
    }
}
