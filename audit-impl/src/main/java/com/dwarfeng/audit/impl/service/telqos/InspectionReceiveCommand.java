package com.dwarfeng.audit.impl.service.telqos;

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
 * 自动审计接收功能控制指令。
 *
 * <p>
 * 该指令用于控制自动审计接收功能的上线和下线，以及查询自动审计接收功能的当前状态。
 *
 * @author DwArFeng
 * @since 1.1.0
 */
@TelqosCommand
public class InspectionReceiveCommand extends CliCommand {

    @SuppressWarnings({"SpellCheckingInspection", "GrazieInspectionRunner", "RedundantSuppression"})
    private static final String IDENTITY = "ireceive";

    // region 指令选项

    private static final String COMMAND_OPTION_STATUS = "status";
    private static final String COMMAND_OPTION_ONLINE = "online";
    private static final String COMMAND_OPTION_OFFLINE = "offline";

    private static final String[] COMMAND_OPTION_ARRAY = new String[]{
            COMMAND_OPTION_STATUS,
            COMMAND_OPTION_ONLINE,
            COMMAND_OPTION_OFFLINE
    };

    // endregion

    private final InspectionReceiverQosService inspectionReceiverQosService;

    public InspectionReceiveCommand(InspectionReceiverQosService inspectionReceiverQosService) {
        super(IDENTITY);
        this.inspectionReceiverQosService = inspectionReceiverQosService;
    }

    @Override
    protected DescriptionProvider provideDescriptionProvider() {
        return context -> "自动审计接收功能上线/下线";
    }

    @Override
    protected CliSyntaxProvider provideCliSyntaxProvider() {
        return this::cliSyntaxProvider;
    }

    private String cliSyntaxProvider(CommandDescriptor.Context context) throws Exception {
        String identity = context.getRuntimeIdentity();
        String[] patterns = new String[]{
                identity + " " + CliCommandUtil.concatOptionPrefix(COMMAND_OPTION_STATUS),
                identity + " " + CliCommandUtil.concatOptionPrefix(COMMAND_OPTION_ONLINE),
                identity + " " + CliCommandUtil.concatOptionPrefix(COMMAND_OPTION_OFFLINE)
        };
        return CliCommandUtil.cliSyntax(patterns);
    }

    @Override
    protected List<Option> provideOptions() {
        List<Option> list = new ArrayList<>();
        list.add(Option.builder(COMMAND_OPTION_STATUS).optionalArg(true).hasArg(false).desc("查看自动审计接收功能当前状态").build());
        list.add(Option.builder(COMMAND_OPTION_ONLINE).optionalArg(true).hasArg(false).desc("上线服务").build());
        list.add(Option.builder(COMMAND_OPTION_OFFLINE).optionalArg(true).hasArg(false).desc("下线服务").build());
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
        switch (pair.getLeft()) {
            case COMMAND_OPTION_STATUS:
                context.sendMessage("自动审计接收功能当前状态: " + (inspectionReceiverQosService.isStarted() ? "上线" : "下线"));
                break;
            case COMMAND_OPTION_ONLINE:
                inspectionReceiverQosService.start();
                context.sendMessage("自动审计接收功能已上线!");
                break;
            case COMMAND_OPTION_OFFLINE:
                inspectionReceiverQosService.stop();
                context.sendMessage("自动审计接收功能已下线!");
                break;
            default:
                throw new IllegalStateException("不应该执行到此处, 请联系开发人员");
        }
    }
}
