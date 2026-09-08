package com.dwarfeng.audit.impl.service.telqos;

import com.dwarfeng.audit.stack.service.InspectionJobQosService;
import com.dwarfeng.springtelqos.sdk.command.CliCommand;
import com.dwarfeng.springtelqos.sdk.configuration.TelqosCommand;
import com.dwarfeng.springtelqos.sdk.util.CliCommandUtil;
import com.dwarfeng.springtelqos.stack.command.CommandDescriptor;
import com.dwarfeng.springtelqos.stack.command.CommandExecutor;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Option;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.List;

/**
 * 自动审计作业操作指令。
 *
 * <p>
 * 该指令用于创建并执行指定自动审计的作业，执行结果可以在自动审计任务事件中查看。
 *
 * @author DwArFeng
 * @since 1.1.0
 */
@TelqosCommand
public class InspectionJobCommand extends CliCommand {

    @SuppressWarnings({"SpellCheckingInspection", "GrazieInspectionRunner", "RedundantSuppression"})
    private static final String IDENTITY = "ijob";

    // region 指令选项

    private static final String COMMAND_OPTION_EXECUTE = "execute";

    private static final String[] COMMAND_OPTION_ARRAY = new String[]{
            COMMAND_OPTION_EXECUTE
    };

    // endregion

    private final InspectionJobQosService inspectionJobQosService;

    public InspectionJobCommand(InspectionJobQosService inspectionJobQosService) {
        super(IDENTITY);
        this.inspectionJobQosService = inspectionJobQosService;
    }

    @Override
    protected DescriptionProvider provideDescriptionProvider() {
        return context -> "自动审计作业操作";
    }

    @Override
    protected CliSyntaxProvider provideCliSyntaxProvider() {
        return this::cliSyntaxProvider;
    }

    private String cliSyntaxProvider(CommandDescriptor.Context context) throws Exception {
        return CliCommandUtil.cliSyntax(
                context.getRuntimeIdentity() + " " + CliCommandUtil.concatOptionPrefix(COMMAND_OPTION_EXECUTE) +
                        " inspection-id"
        );
    }

    @Override
    protected List<Option> provideOptions() {
        List<Option> list = new ArrayList<>();
        list.add(Option.builder().longOpt(COMMAND_OPTION_EXECUTE).optionalArg(true).hasArg(true).type(Number.class)
                .argName("inspection-id").desc("执行指定自动审计的作业").build());
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
        handleExecute(context, cmd);
    }

    private void handleExecute(CommandExecutor.Context context, CommandLine cmd) throws Exception {
        long inspectionId;
        try {
            inspectionId = ((Number) cmd.getParsedOptionValue(COMMAND_OPTION_EXECUTE)).longValue();
        } catch (Exception e) {
            context.sendMessage("命令行格式错误，正确的格式为: " + context.getRuntimeIdentity() + " " +
                    CliCommandUtil.concatOptionPrefix(COMMAND_OPTION_EXECUTE) + " inspection-id");
            context.sendMessage("请留意选项 " + COMMAND_OPTION_EXECUTE + " 后接参数的类型应该是数字 ");
            return;
        }

        inspectionJobQosService.execute(new LongIdKey(inspectionId));
        context.sendMessage("执行完成，可以在自动审计任务事件中查看执行结果");
    }
}
