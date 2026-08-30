package com.dwarfeng.audit.impl.service.telqos;

import com.dwarfeng.audit.stack.bean.entity.AuditPropertyIndicator;
import com.dwarfeng.audit.stack.service.AuditRecordQosService;
import com.dwarfeng.audit.stack.struct.AuditRecordLocalCache;
import com.dwarfeng.springtelqos.sdk.command.CliCommand;
import com.dwarfeng.springtelqos.sdk.configuration.TelqosCommand;
import com.dwarfeng.springtelqos.sdk.util.CliCommandUtil;
import com.dwarfeng.springtelqos.stack.command.CommandDescriptor;
import com.dwarfeng.springtelqos.stack.command.CommandExecutor;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Option;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * 审计记录本地缓存操作指令。
 *
 * <p>
 * 该指令用于查看和清理审计记录本地缓存，包括查看指定审计类别的详细信息以及清除整个本地缓存等操作。
 *
 * @author DwArFeng
 * @since 1.0.0-beta
 */
@TelqosCommand
public class AuditRecordLocalCacheCommand extends CliCommand {

    @SuppressWarnings({"SpellCheckingInspection", "GrazieInspectionRunner", "RedundantSuppression"})
    private static final String IDENTITY = "arlc";

    // region 指令选项

    private static final String COMMAND_OPTION_LOOKUP = "l";
    private static final String COMMAND_OPTION_CLEAR = "c";

    private static final String[] COMMAND_OPTION_ARRAY = new String[]{
            COMMAND_OPTION_LOOKUP,
            COMMAND_OPTION_CLEAR
    };

    // endregion

    private final AuditRecordQosService auditRecordQosService;

    public AuditRecordLocalCacheCommand(AuditRecordQosService auditRecordQosService) {
        super(IDENTITY);
        this.auditRecordQosService = auditRecordQosService;
    }

    @Override
    protected DescriptionProvider provideDescriptionProvider() {
        return context -> "审计记录本地缓存操作";
    }

    @Override
    protected CliSyntaxProvider provideCliSyntaxProvider() {
        return this::cliSyntaxProvider;
    }

    private String cliSyntaxProvider(CommandDescriptor.Context context) throws Exception {
        String identity = context.getRuntimeIdentity();
        String[] patterns = new String[]{
                identity + " " + CliCommandUtil.concatOptionPrefix(COMMAND_OPTION_LOOKUP) + " category-id",
                identity + " " + CliCommandUtil.concatOptionPrefix(COMMAND_OPTION_CLEAR)
        };
        return CliCommandUtil.cliSyntax(patterns);
    }

    @Override
    protected List<Option> provideOptions() {
        List<Option> list = new ArrayList<>();
        list.add(
                Option.builder(COMMAND_OPTION_LOOKUP).optionalArg(true).hasArg(true).type(String.class)
                        .argName("category-id").desc("查看指定审计类别的详细信息，如果本地缓存中不存在，则尝试抓取").build()
        );
        list.add(Option.builder(COMMAND_OPTION_CLEAR).optionalArg(true).hasArg(false).desc("清除缓存").build());
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
                handleClear(context);
                break;
            default:
                throw new IllegalStateException("不应该执行到此处, 请联系开发人员");
        }
    }

    private void handleLookup(CommandExecutor.Context context, CommandLine cmd) throws Exception {
        String categoryId = (String) cmd.getParsedOptionValue(COMMAND_OPTION_LOOKUP);
        AuditRecordLocalCache recordLocalCache = auditRecordQosService.getAuditRecordLocalCache(
                new StringIdKey(categoryId)
        );
        if (Objects.isNull(recordLocalCache)) {
            context.sendMessage("not exists!");
            return;
        }
        context.sendMessage(String.format("audit category: %s", recordLocalCache.getAuditCategory()));
        context.sendMessage("audit property indicators:");
        int index = 0;
        for (Map.Entry<String, AuditPropertyIndicator> entry :
                recordLocalCache.getAuditPropertyIndicatorMap().entrySet()) {
            context.sendMessage(String.format("  %-3d key:%s value:%s", ++index, entry.getKey(), entry.getValue()));
        }
    }

    private void handleClear(CommandExecutor.Context context) throws Exception {
        auditRecordQosService.clearAuditRecordLocalCache();
        context.sendMessage("缓存已清空");
    }
}
