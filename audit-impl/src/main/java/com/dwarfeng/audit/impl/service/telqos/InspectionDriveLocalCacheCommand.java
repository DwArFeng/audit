package com.dwarfeng.audit.impl.service.telqos;

import com.dwarfeng.audit.stack.bean.entity.InspectionDriverInfo;
import com.dwarfeng.audit.stack.handler.InspectionDriver;
import com.dwarfeng.audit.stack.service.InspectionDriveQosService;
import com.dwarfeng.audit.stack.struct.InspectionDriveLocalCache;
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
import java.util.Map;
import java.util.Objects;

/**
 * 自动审计驱动本地缓存操作指令。
 *
 * <p>
 * 该指令用于查看指定自动审计的驱动本地缓存，以及清除全部自动审计驱动本地缓存。
 *
 * @author DwArFeng
 * @since 1.1.0
 */
@TelqosCommand
public class InspectionDriveLocalCacheCommand extends CliCommand {

    @SuppressWarnings({"SpellCheckingInspection", "GrazieInspectionRunner", "RedundantSuppression"})
    private static final String IDENTITY = "idlc";

    // region 指令选项

    private static final String COMMAND_OPTION_LOOKUP = "l";
    private static final String COMMAND_OPTION_CLEAR = "c";

    private static final String[] COMMAND_OPTION_ARRAY = new String[]{
            COMMAND_OPTION_LOOKUP,
            COMMAND_OPTION_CLEAR
    };

    // endregion

    private final InspectionDriveQosService inspectionDriveQosService;

    public InspectionDriveLocalCacheCommand(InspectionDriveQosService inspectionDriveQosService) {
        super(IDENTITY);
        this.inspectionDriveQosService = inspectionDriveQosService;
    }

    @Override
    protected DescriptionProvider provideDescriptionProvider() {
        return context -> "自动审计驱动本地缓存操作";
    }

    @Override
    protected CliSyntaxProvider provideCliSyntaxProvider() {
        return this::cliSyntaxProvider;
    }

    private String cliSyntaxProvider(CommandDescriptor.Context context) throws Exception {
        String identity = context.getRuntimeIdentity();
        String[] patterns = new String[]{
                identity + " " + CliCommandUtil.concatOptionPrefix(COMMAND_OPTION_LOOKUP) + " inspection-id",
                identity + " " + CliCommandUtil.concatOptionPrefix(COMMAND_OPTION_CLEAR)
        };
        return CliCommandUtil.cliSyntax(patterns);
    }

    @Override
    protected List<Option> provideOptions() {
        List<Option> list = new ArrayList<>();
        list.add(Option.builder(COMMAND_OPTION_LOOKUP).optionalArg(true).hasArg(true).type(Number.class)
                .argName("inspection-id").desc("查看指定自动审计的驱动本地缓存").build());
        list.add(Option.builder(COMMAND_OPTION_CLEAR).optionalArg(true).hasArg(false).desc("清除驱动本地缓存").build());
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
            case COMMAND_OPTION_LOOKUP:
                handleLookup(context, cmd);
                break;
            case COMMAND_OPTION_CLEAR:
                inspectionDriveQosService.clearLocalCache();
                context.sendMessage("本地缓存已清除");
                break;
            default:
                throw new IllegalStateException("不应该执行到此处, 请联系开发人员");
        }
    }

    private void handleLookup(CommandExecutor.Context context, CommandLine cmd) throws Exception {
        long inspectionId;
        try {
            inspectionId = ((Number) cmd.getParsedOptionValue(COMMAND_OPTION_LOOKUP)).longValue();
        } catch (Exception e) {
            context.sendMessage("命令行格式错误，正确的格式为: " + context.getRuntimeIdentity() + " " +
                    CliCommandUtil.concatOptionPrefix(COMMAND_OPTION_LOOKUP) + " inspection-id");
            context.sendMessage("请留意选项 " + COMMAND_OPTION_LOOKUP + " 后接参数的类型应该是数字 ");
            return;
        }

        InspectionDriveLocalCache localCache = inspectionDriveQosService.getInspectionDriveLocalCache(
                new LongIdKey(inspectionId)
        );
        if (Objects.isNull(localCache)) {
            context.sendMessage("not exists!");
            return;
        }
        context.sendMessage(String.format("inspection: %s", localCache.getInspection()));
        context.sendMessage("inspection drivers:");
        int index = 0;
        for (Map.Entry<InspectionDriverInfo, InspectionDriver> entry : localCache.getDriverMap().entrySet()) {
            index++;
            context.sendMessage(String.format("  %-3d %s", index, entry.getKey()));
            context.sendMessage(String.format("  %-3d %s", index, entry.getValue()));
        }
    }
}
