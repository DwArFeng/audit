package com.dwarfeng.audit.impl.service.telqos;

import com.alibaba.fastjson.JSON;
import com.dwarfeng.audit.sdk.bean.dto.WebInputAuditEntryCompositeLookupInfo;
import com.dwarfeng.audit.sdk.bean.dto.WebInputAuditEntryGroupedLookupInfo;
import com.dwarfeng.audit.sdk.bean.entity.FastJsonAuditEntry;
import com.dwarfeng.audit.stack.bean.dto.AuditEntryCompositeLookupInfo;
import com.dwarfeng.audit.stack.bean.dto.AuditEntryGroupedLookupInfo;
import com.dwarfeng.audit.stack.bean.dto.AuditEntryLookupResult;
import com.dwarfeng.audit.stack.bean.entity.AuditEntry;
import com.dwarfeng.audit.stack.service.AuditEntryLookupQosService;
import com.dwarfeng.springtelqos.sdk.command.CliCommand;
import com.dwarfeng.springtelqos.sdk.configuration.TelqosCommand;
import com.dwarfeng.springtelqos.sdk.util.CliCommandUtil;
import com.dwarfeng.springtelqos.stack.command.CommandDescriptor;
import com.dwarfeng.springtelqos.stack.command.CommandExecutor;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Option;
import org.apache.commons.lang3.tuple.Pair;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 审计条目查询指令。
 *
 * <p>
 * 该指令支持组合查询和分组查询，并支持从 JSON 字符串或 JSON 文件中读取查询参数，以格式化 JSON 输出查询结果。
 *
 * @author DwArFeng
 * @since 1.0.0-beta
 */
@TelqosCommand
public class AuditEntryLookupCommand extends CliCommand {

    @SuppressWarnings({"SpellCheckingInspection", "GrazieInspectionRunner", "RedundantSuppression"})
    private static final String IDENTITY = "aelookup";

    // region 指令选项

    private static final String COMMAND_OPTION_COMPOSITE = "c";
    private static final String COMMAND_OPTION_GROUPED = "g";

    private static final String[] COMMAND_OPTION_ARRAY = new String[]{
            COMMAND_OPTION_COMPOSITE,
            COMMAND_OPTION_GROUPED
    };

    private static final String COMMAND_SUB_OPTION_JSON = "json";
    private static final String COMMAND_SUB_OPTION_JSON_FILE = "jf";
    private static final String COMMAND_SUB_OPTION_JSON_FILE_LONG_OPT = "json-file";

    // endregion

    private final AuditEntryLookupQosService auditEntryLookupQosService;

    public AuditEntryLookupCommand(AuditEntryLookupQosService auditEntryLookupQosService) {
        super(IDENTITY);
        this.auditEntryLookupQosService = auditEntryLookupQosService;
    }

    @Override
    protected DescriptionProvider provideDescriptionProvider() {
        return context -> "审计条目查询";
    }

    @Override
    protected CliSyntaxProvider provideCliSyntaxProvider() {
        return this::cliSyntaxProvider;
    }

    private String cliSyntaxProvider(CommandDescriptor.Context context) throws Exception {
        String identity = context.getRuntimeIdentity();
        String[] patterns = new String[]{
                identity + " " + CliCommandUtil.concatOptionPrefix(COMMAND_OPTION_COMPOSITE) + " [" +
                        CliCommandUtil.concatOptionPrefix(COMMAND_SUB_OPTION_JSON) + " json-string] [" +
                        CliCommandUtil.concatOptionPrefix(COMMAND_SUB_OPTION_JSON_FILE) + " json-file]",
                identity + " " + CliCommandUtil.concatOptionPrefix(COMMAND_OPTION_GROUPED) + " [" +
                        CliCommandUtil.concatOptionPrefix(COMMAND_SUB_OPTION_JSON) + " json-string] [" +
                        CliCommandUtil.concatOptionPrefix(COMMAND_SUB_OPTION_JSON_FILE) + " json-file]"
        };
        return CliCommandUtil.cliSyntax(patterns);
    }

    @Override
    protected List<Option> provideOptions() {
        List<Option> list = new ArrayList<>();
        list.add(Option.builder(COMMAND_OPTION_COMPOSITE).desc("组合查询").build());
        list.add(Option.builder(COMMAND_OPTION_GROUPED).desc("分组查询").build());
        list.add(Option.builder(COMMAND_SUB_OPTION_JSON).hasArg(true).type(String.class).desc("JSON 字符串").build());
        list.add(Option.builder(COMMAND_SUB_OPTION_JSON_FILE).longOpt(COMMAND_SUB_OPTION_JSON_FILE_LONG_OPT)
                .hasArg(true).type(File.class).desc("JSON 文件").build());
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
            case COMMAND_OPTION_COMPOSITE:
                handleComposite(context, cmd);
                break;
            case COMMAND_OPTION_GROUPED:
                handleGrouped(context, cmd);
                break;
            default:
                throw new IllegalStateException("不应该执行到此处, 请联系开发人员");
        }
    }

    private void handleComposite(CommandExecutor.Context context, CommandLine cmd) throws Exception {
        AuditEntryCompositeLookupInfo info;

        // 如果有 -json 选项，则从选项中获取 JSON，转化为 AuditEntryCompositeLookupInfo。
        if (cmd.hasOption(COMMAND_SUB_OPTION_JSON)) {
            String json = (String) cmd.getParsedOptionValue(COMMAND_SUB_OPTION_JSON);
            info = WebInputAuditEntryCompositeLookupInfo.toStackBean(
                    JSON.parseObject(json, WebInputAuditEntryCompositeLookupInfo.class)
            );
        }
        // 如果有 --json-file 选项，则从选项中获取 JSON 文件，转化为 AuditEntryCompositeLookupInfo。
        else if (cmd.hasOption(COMMAND_SUB_OPTION_JSON_FILE)) {
            File jsonFile = (File) cmd.getParsedOptionValue(COMMAND_SUB_OPTION_JSON_FILE);
            try (FileInputStream in = new FileInputStream(jsonFile)) {
                info = WebInputAuditEntryCompositeLookupInfo.toStackBean(
                        JSON.parseObject(in, WebInputAuditEntryCompositeLookupInfo.class)
                );
            }
        } else {
            // 暂时未实现。
            throw new UnsupportedOperationException("not supported yet");
        }

        // 执行组合查询。
        AuditEntryLookupResult result = auditEntryLookupQosService.lookupComposite(info);

        // 输出结果。
        printLookupResult(context, result);
    }

    private void handleGrouped(CommandExecutor.Context context, CommandLine cmd) throws Exception {
        AuditEntryGroupedLookupInfo info;

        // 如果有 -json 选项，则从选项中获取 JSON，转化为 AuditEntryGroupedLookupInfo。
        if (cmd.hasOption(COMMAND_SUB_OPTION_JSON)) {
            String json = (String) cmd.getParsedOptionValue(COMMAND_SUB_OPTION_JSON);
            info = WebInputAuditEntryGroupedLookupInfo.toStackBean(
                    JSON.parseObject(json, WebInputAuditEntryGroupedLookupInfo.class)
            );
        }
        // 如果有 --json-file 选项，则从选项中获取 JSON 文件，转化为 AuditEntryGroupedLookupInfo。
        else if (cmd.hasOption(COMMAND_SUB_OPTION_JSON_FILE)) {
            File jsonFile = (File) cmd.getParsedOptionValue(COMMAND_SUB_OPTION_JSON_FILE);
            try (FileInputStream in = new FileInputStream(jsonFile)) {
                info = WebInputAuditEntryGroupedLookupInfo.toStackBean(
                        JSON.parseObject(in, WebInputAuditEntryGroupedLookupInfo.class)
                );
            }
        } else {
            // 暂时未实现。
            throw new UnsupportedOperationException("not supported yet");
        }

        // 执行分组查询。
        AuditEntryLookupResult result = auditEntryLookupQosService.lookupGrouped(info);

        // 输出结果。
        printLookupResult(context, result);
    }

    private void printLookupResult(CommandExecutor.Context context, AuditEntryLookupResult result) throws Exception {
        if (Objects.isNull(result)) {
            context.sendMessage("查询结果: null");
            return;
        }
        context.sendMessage("查询结果: ");
        context.sendMessage("  currentPage: " + result.getCurrentPage());
        context.sendMessage("  totalPages: " + result.getTotalPages());
        context.sendMessage("  rows: " + result.getRows());
        context.sendMessage("  count: " + result.getCount());
        processLookupResultData(context, result.getData());
    }

    private void processLookupResultData(CommandExecutor.Context context, List<AuditEntry> data) throws Exception {
        if (Objects.isNull(data)) {
            context.sendMessage("  data: null");
            return;
        }
        while (true) {
            CliCommandUtil.CropResult cropResult = CliCommandUtil.cropData(context, data, "数据总数: " + data.size());
            if (cropResult.isExitFlag()) {
                break;
            }
            context.sendMessage("");
            for (int i = cropResult.getBeginIndex(); i < cropResult.getEndIndex(); i++) {
                AuditEntry item = data.get(i);
                printLookupResultDataItem(context, i, cropResult.getEndIndex(), item);
            }
        }
    }

    private void printLookupResultDataItem(
            CommandExecutor.Context context, int i, int endIndex, AuditEntry item
    ) throws Exception {
        context.sendMessage(String.format("索引: %d/%d", i, endIndex));
        if (Objects.isNull(item)) {
            context.sendMessage("null");
        } else {
            context.sendMessage(JSON.toJSONString(FastJsonAuditEntry.of(item), true));
        }
        context.sendMessage("");
    }
}
