# ChangeLog

## Beta_1.0.0_20260822_build_A

### 功能构建

- 实现预设重置器。
  - com.dwarfeng.audit.impl.handler.resetter.CronResetter。
  - com.dwarfeng.audit.impl.handler.resetter.DubboResetter。
  - com.dwarfeng.audit.impl.handler.resetter.FixedDelayResetter。
  - com.dwarfeng.audit.impl.handler.resetter.FixedRateResetter。
  - com.dwarfeng.audit.impl.handler.resetter.NeverResetter。

- 实现核心机制。
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
