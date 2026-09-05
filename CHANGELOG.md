# ChangeLog

## Release_1.1.0_20260905_build_A

### 功能构建

- (无)

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
