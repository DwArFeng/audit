package com.dwarfeng.audit.impl.handler;

import com.dwarfeng.audit.sdk.handler.InspectionDriverSupporter;
import com.dwarfeng.audit.sdk.handler.InspectorSupporter;
import com.dwarfeng.audit.stack.bean.entity.InspectionDriverSupport;
import com.dwarfeng.audit.stack.bean.entity.InspectorSupport;
import com.dwarfeng.audit.stack.handler.SupportHandler;
import com.dwarfeng.audit.stack.service.InspectionDriverSupportMaintainService;
import com.dwarfeng.audit.stack.service.InspectorSupportMaintainService;
import com.dwarfeng.subgrade.sdk.exception.HandlerExceptionHelper;
import com.dwarfeng.subgrade.sdk.interceptor.analyse.BehaviorAnalyse;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.exception.HandlerException;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 支持处理器实现。
 *
 * @author DwArFeng
 * @since 1.1.0
 */
@Component
public class SupportHandlerImpl implements SupportHandler {

    private final InspectionDriverSupportMaintainService inspectionDriverSupportMaintainService;
    private final InspectorSupportMaintainService inspectorSupportMaintainService;

    private final List<InspectionDriverSupporter> inspectionDriverSupporters;
    private final List<InspectorSupporter> inspectorSupporters;

    public SupportHandlerImpl(
            InspectionDriverSupportMaintainService inspectionDriverSupportMaintainService,
            InspectorSupportMaintainService inspectorSupportMaintainService,
            List<InspectionDriverSupporter> inspectionDriverSupporters,
            List<InspectorSupporter> inspectorSupporters
    ) {
        this.inspectionDriverSupportMaintainService = inspectionDriverSupportMaintainService;
        this.inspectorSupportMaintainService = inspectorSupportMaintainService;
        this.inspectionDriverSupporters = inspectionDriverSupporters;
        this.inspectorSupporters = inspectorSupporters;
    }

    @Override
    @BehaviorAnalyse
    public void resetInspectionDriver() throws HandlerException {
        try {
            doResetInspectionDriver();
        } catch (Exception e) {
            throw HandlerExceptionHelper.parse(e);
        }
    }

    private void doResetInspectionDriver() throws Exception {
        // 清除现有自动审计驱动器支持信息。
        List<StringIdKey> inspectionDriverKeys = inspectionDriverSupportMaintainService.lookupAsList().stream()
                .map(InspectionDriverSupport::getKey).collect(Collectors.toList());
        inspectionDriverSupportMaintainService.batchDelete(inspectionDriverKeys);
        // 根据当前注册的自动审计驱动器支持器重新生成支持信息。
        List<InspectionDriverSupport> inspectionDriverSupports = inspectionDriverSupporters.stream().map(
                supporter -> new InspectionDriverSupport(
                        new StringIdKey(supporter.provideType()),
                        supporter.provideLabel(),
                        supporter.provideDescription(),
                        supporter.provideExampleParam()
                )
        ).collect(Collectors.toList());
        inspectionDriverSupportMaintainService.batchInsert(inspectionDriverSupports);
    }

    @Override
    @BehaviorAnalyse
    public void resetInspector() throws HandlerException {
        try {
            doResetInspector();
        } catch (Exception e) {
            throw HandlerExceptionHelper.parse(e);
        }
    }

    private void doResetInspector() throws Exception {
        // 清除现有审计器支持信息。
        List<StringIdKey> inspectorKeys = inspectorSupportMaintainService.lookupAsList().stream()
                .map(InspectorSupport::getKey).collect(Collectors.toList());
        inspectorSupportMaintainService.batchDelete(inspectorKeys);
        // 根据当前注册的审计器支持器重新生成审计器支持信息。
        List<InspectorSupport> inspectorSupports = inspectorSupporters.stream().map(
                supporter -> new InspectorSupport(
                        new StringIdKey(supporter.provideType()),
                        supporter.provideLabel(),
                        supporter.provideDescription(),
                        supporter.provideExampleParam()
                )
        ).collect(Collectors.toList());
        inspectorSupportMaintainService.batchInsert(inspectorSupports);
    }
}
