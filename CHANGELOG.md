# ChangeLog

## Release_1.3.0_20261008_build_A

### 功能构建

- (无)

### Bug 修复

- (无)

### 功能移除

- (无)

---

## Release_1.2.1_20260927_build_A

### 功能构建

- Wiki 编写。
  - docs/wiki/zh-CN/CompileBySource.md。

- 增加预设查询。
  - com.dwarfeng.audit.stack.service.AuditCategoryMaintainService.ID_LIKE。
  - com.dwarfeng.audit.stack.service.AuditCategoryMaintainService.NAME_LIKE。
  - com.dwarfeng.audit.stack.service.AuditPropertyIndicatorMaintainService.CHILD_FOR_AUDIT_CATEGORY_ORDER_ASC。
  - com.dwarfeng.audit.stack.service.InspectorSupportMaintainService.ID_LIKE。
  - com.dwarfeng.audit.stack.service.InspectorSupportMaintainService.LABEL_LIKE。

- 优化部分 Hibernate 实体的 JPA 注解。
  - com.dwarfeng.audit.impl.bean.entity.HibernateAuditEntryProperty。

### Bug 修复

- (无)

### 功能移除

- (无)

---

## Release_1.2.0_20260919_build_A

### 功能构建

- Wiki 编写。
  - docs/wiki/zh-CN/QuickStart.md。

- 增加预设查询。
  - com.dwarfeng.audit.stack.service.AuditEntryMaintainService.CREATED_DATE_DESC。

- 重构审计条目查询机制。
  - com.dwarfeng.audit.stack.bean.dto.AuditEntryCompositeLookupInfo。
  - com.dwarfeng.audit.stack.bean.dto.AuditEntryGroupedLookupInfo。

- 部分 DTO 实体的内置常量提升至常量类。
  - com.dwarfeng.audit.stack.bean.dto.AuditEntryGroupedLookupInfo。

- 优化部分 DTO 实体字段的初始值。
  - com.dwarfeng.audit.sdk.bean.dto.WebInputAuditEntryGroupedLookupInfo。
  - com.dwarfeng.audit.sdk.bean.dto.WebInputAuditEntryCompositeLookupInfo。
  - com.dwarfeng.audit.stack.bean.dto.AuditEntryCompositeLookupInfo。
  - com.dwarfeng.audit.stack.bean.dto.AuditEntryGroupedLookupInfo。

- `audit-stack` 子模块类优化注释、文档注释格式、代码换行格式。
  - com.dwarfeng.audit.stack.bean.dto.InspectorVariableUpsertInfo。

### Bug 修复

- 修复部分 JSFixedFastJson 实体 long 字段处理中的错误。
  - com.dwarfeng.audit.sdk.bean.dto.JSFixedFastJsonAuditEntryLookupResult。

### 功能移除

- (无)

---

## Release_1.1.2_20260912_build_A

### 功能构建

- 更新 README.md。

- Wiki 更新。
  - docs/wiki/zh-CN/VersionBlacklist.md。
  - docs/wiki/zh-CN/Introduction.md。

### Bug 修复

- 修复部分 WebInput 实体字段 @JSONField 主键中 name 属性的错误。
  - com.dwarfeng.audit.sdk.bean.dto.WebInputAuditRecordInfo。
  - com.dwarfeng.audit.sdk.bean.dto.WebInputInspectionAlarmCreateInfo。
  - com.dwarfeng.audit.sdk.bean.dto.WebInputInspectorVariableInspectInfo。
  - com.dwarfeng.audit.sdk.bean.dto.WebInputInspectorVariableRemoveInfo。
  - com.dwarfeng.audit.sdk.bean.dto.WebInputInspectorVariableUpsertInfo。

### 功能移除

- (无)

---

## Release_1.1.1_20260911_build_A

### 功能构建

- Wiki 编写。
  - docs/wiki/zh-CN/SystemRequirements.md。

- 优化项目的启动脚本。
  - binres/audit-start.sh。

- 集成 `dcti` 与 `dwarfeng-dct` 组件。
  - 新增相关依赖及 Spring 配置。
  - 支持相关 Telqos 指令、服务异常映射及异常码偏移配置。

- 优化项目中部分类的代码结构。
  - com.dwarfeng.audit.impl.handler.pusher.LogPusher。

- 新增实体操作服务。
  - com.dwarfeng.audit.stack.service.InspectorVariableOperateService。

- 优化文件格式。
  - 优化 `.gitignore` 文件的格式。

### Bug 修复

- (无)

### 功能移除

- (无)

---

## Release_1.1.0_20260910_build_A

### 功能构建

- Wiki 编写。
  - docs/wiki/zh-CN/VersionBlacklist.md。

- 更新 README.md。

- Wiki 更新。
  - docs/wiki/zh-CN/Introduction.md。

- 实现运维指令。
  - com.dwarfeng.audit.impl.service.telqos.InspectionConsumeCommand。
  - com.dwarfeng.audit.impl.service.telqos.InspectionDispatchCommand。
  - com.dwarfeng.audit.impl.service.telqos.InspectionDispatcherCommand。
  - com.dwarfeng.audit.impl.service.telqos.InspectionDriveCommand。
  - com.dwarfeng.audit.impl.service.telqos.InspectionDriveLocalCacheCommand。
  - com.dwarfeng.audit.impl.service.telqos.InspectionJobCommand。
  - com.dwarfeng.audit.impl.service.telqos.InspectionJobLocalCacheCommand。
  - com.dwarfeng.audit.impl.service.telqos.InspectionReceiveCommand。
  - com.dwarfeng.audit.impl.service.telqos.InspectionReceiverCommand。
  - com.dwarfeng.audit.impl.service.telqos.InspectionSuperviseCommand。
  - com.dwarfeng.audit.impl.service.telqos.InspectionTaskCheckCommand。
  - com.dwarfeng.audit.impl.service.telqos.PurgeCommand。
  - com.dwarfeng.audit.impl.service.telqos.SupportCommand。

- 实现预设自动审计驱动器。
  - com.dwarfeng.audit.impl.handler.inspdriver.CronInspectionDriverProvider。
  - com.dwarfeng.audit.impl.handler.inspdriver.DctiKafkaInspectionDriverProvider。
  - com.dwarfeng.audit.impl.handler.inspdriver.FixedDelayInspectionDriverProvider。
  - com.dwarfeng.audit.impl.handler.inspdriver.FixedRateInspectionDriverProvider。

- 实现预设自动审计调度器。
  - com.dwarfeng.audit.impl.handler.inspdispatcher.DrainInspectionDispatcher。
  - com.dwarfeng.audit.impl.handler.inspdispatcher.InjvmInspectionDispatcher。
  - com.dwarfeng.audit.impl.handler.inspdispatcher.KafkaInspectionDispatcher。
  - com.dwarfeng.audit.impl.handler.inspdispatcher.DubboInspectionDispatcher。

- 实现预设自动审计接收器。
  - com.dwarfeng.audit.impl.handler.inspreceiver.DoNothingInspectionReceiver。
  - com.dwarfeng.audit.impl.handler.inspreceiver.InjvmInspectionReceiver。
  - com.dwarfeng.audit.impl.handler.inspreceiver.KafkaInspectionReceiver。
  - com.dwarfeng.audit.impl.handler.inspreceiver.DubboInspectionReceiver。

- 实现预设审计器。
  - com.dwarfeng.audit.impl.handler.inspector.groovy.GroovyInspectorRegistry。

- 实现核心机制。
  - 清除机制。
  - 自动审计主管机制。
  - 自动审计主管重置机制。
  - 自动审计驱动机制。
  - 自动审计调度机制。
  - 自动审计接收机制。
  - 自动审计任务检查机制。
  - 自动审计作业机制。
  - 审计机制。

- 优化部分 DTO 的字段。
  - com.dwarfeng.audit.stack.bean.dto.AuditEntryLookupResult。

- 增加预设查询。
  - com.dwarfeng.audit.stack.service.InspectionTaskMaintainService.TO_PURGED。
  - com.dwarfeng.audit.stack.service.InspectionTaskMaintainService.SHOULD_EXPIRE。
  - com.dwarfeng.audit.stack.service.InspectionTaskMaintainService.SHOULD_DIE。
  - com.dwarfeng.audit.stack.service.AuditEntryPropertyMaintainService.CHILD_FOR_AUDIT_ENTRIES。

- 新建实体以及维护服务，并通过单元测试。
  - com.dwarfeng.audit.stack.bean.entity.Inspection。
  - com.dwarfeng.audit.stack.bean.entity.InspectionAlarm。
  - com.dwarfeng.audit.stack.bean.entity.InspectionAlarmTypeIndicator。
  - com.dwarfeng.audit.stack.bean.entity.InspectionDriverInfo。
  - com.dwarfeng.audit.stack.bean.entity.InspectionDriverSupport。
  - com.dwarfeng.audit.stack.bean.entity.InspectionTask。
  - com.dwarfeng.audit.stack.bean.entity.InspectionTaskEvent。
  - com.dwarfeng.audit.stack.bean.entity.InspectorInfo。
  - com.dwarfeng.audit.stack.bean.entity.InspectorSupport。
  - com.dwarfeng.audit.stack.bean.entity.InspectorVariable。

- 依赖升级。
  - 升级 `jackson` 依赖版本为 `2.21.5` 以规避漏洞。
  - 升级 `spring-terminator` 依赖版本为 `2.0.3.a` 以规避漏洞。
  - 升级 `dwarfeng-datamark` 依赖版本为 `2.2.1.a` 以规避漏洞。

### Bug 修复

- (无)

### 功能移除

- (无)

---

## Beta_1.0.0_20260905_build_A

### 功能构建

- Wiki 编写。
  - docs/wiki/zh-CN/Contents.md。
  - docs/wiki/zh-CN/Introduction.md。
  - docs/wiki/zh-CN/README.md。
  - docs/wiki/en-US/Contents.md。
  - docs/wiki/en-US/Introduction.md。
  - docs/wiki/en-US/README.md。

- `README.md` 更新。

- 新增 `subgrade` 项目的集成组件。
  - com.dwarfeng.audit.api.integration.subgrade.AuditRecordHandlerImpl。

- 新增 api 模块。

- 完成 `audit-distribute` 模块，打包测试通过。

- 实现运维指令。
  - com.dwarfeng.audit.impl.service.telqos.AuditEntryLookupCommand。
  - com.dwarfeng.audit.impl.service.telqos.AuditRecordCommand。
  - com.dwarfeng.audit.impl.service.telqos.AuditRecordLocalCacheCommand。
  - com.dwarfeng.audit.impl.service.telqos.AuditRecordLogicConsumerCommand。
  - com.dwarfeng.audit.impl.service.telqos.AuditRecordPersistenceConsumerCommand。
  - com.dwarfeng.audit.impl.service.telqos.ResetCommand。

- 实现预设推送器。
  - com.dwarfeng.audit.impl.handler.pusher.DrainPusher。
  - com.dwarfeng.audit.impl.handler.pusher.LogPusher。
  - com.dwarfeng.audit.impl.handler.pusher.MultiPusher。
  - com.dwarfeng.audit.impl.handler.pusher.NativeKafkaPusher。

- 实现预设重置器。
  - com.dwarfeng.audit.impl.handler.resetter.CronResetter。
  - com.dwarfeng.audit.impl.handler.resetter.DubboResetter。
  - com.dwarfeng.audit.impl.handler.resetter.FixedDelayResetter。
  - com.dwarfeng.audit.impl.handler.resetter.FixedRateResetter。
  - com.dwarfeng.audit.impl.handler.resetter.NeverResetter。

- 实现核心机制。
  - 推送机制。
  - 重置机制。

- 实现核心服务。
  - com.dwarfeng.audit.stack.service.AuditEntryLookupService。
  - com.dwarfeng.audit.stack.service.AuditRecordService。

- 完成 `audit-node-all-he` 模块，启动测试通过。

- 建立实体以及维护服务，并通过单元测试。
  - com.dwarfeng.audit.stack.bean.entity.AuditCategory。
  - com.dwarfeng.audit.stack.bean.entity.AuditEntry。
  - com.dwarfeng.audit.stack.bean.entity.AuditEntryProperty。
  - com.dwarfeng.audit.stack.bean.entity.AuditPropertyIndicator。

- 项目结构建立，清理测试通过。

### Bug 修复

- (无)

### 功能移除

- (无)
