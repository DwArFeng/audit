# Quick Start - 快速开始

## 确认系统需求

- CPU：2 核以上。
- 内存：4G 以上。
- 硬盘：100G 以上。
- CentOS 7。
- JRE 1.8。
- MySQL 8.0+。
- Redis 5.0+。
- Zookeeper 3.5+。
- snowflake-distributed-service 2.0.2.a。

## 获取软件包

从 Github 上获取软件包，软件包可以从 Github 的 Release 页面下载。

## 解压软件包

软件包的名称格式为 `audit-all-he-${version}-release.tar.gz`，其中 `${version}` 为软件包的版本号。

使用工具软件，将软件包上传至服务器 `/usr/local` 目录下，解压软件包。

```shell
cd /usr/local
tar -zxvf audit-all-he-${version}-release.tar.gz
mv audit-all-he-${version}-release/audit-all-he-${version} audit
```

## 数据库初始化

连接到 MySQL 数据库，执行如下 SQL 语句：

```sql
SET NAMES utf8mb4;

CREATE DATABASE IF NOT EXISTS `audit`
    DEFAULT CHARACTER SET utf8mb4
    COLLATE utf8mb4_0900_bin;

USE `audit`;

CREATE TABLE IF NOT EXISTS `tbl_audit_category` (
  `id` varchar(255) COLLATE utf8mb4_0900_bin NOT NULL,
  `created_datamark` varchar(100) COLLATE utf8mb4_0900_bin DEFAULT NULL,
  `enabled` bit(1) NOT NULL,
  `modified_datamark` varchar(100) COLLATE utf8mb4_0900_bin DEFAULT NULL,
  `name` varchar(50) COLLATE utf8mb4_0900_bin DEFAULT NULL,
  `remark` varchar(200) COLLATE utf8mb4_0900_bin DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_bin;

CREATE TABLE IF NOT EXISTS `tbl_inspection` (
  `id` bigint NOT NULL,
  `created_datamark` varchar(100) COLLATE utf8mb4_0900_bin DEFAULT NULL,
  `enabled` bit(1) NOT NULL,
  `modified_datamark` varchar(100) COLLATE utf8mb4_0900_bin DEFAULT NULL,
  `name` varchar(50) COLLATE utf8mb4_0900_bin NOT NULL,
  `remark` varchar(200) COLLATE utf8mb4_0900_bin DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_bin;

CREATE TABLE IF NOT EXISTS `tbl_inspection_alarm_type_indicator` (
  `id` varchar(100) COLLATE utf8mb4_0900_bin NOT NULL,
  `label` varchar(50) COLLATE utf8mb4_0900_bin NOT NULL,
  `remark` varchar(200) COLLATE utf8mb4_0900_bin DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_bin;

CREATE TABLE IF NOT EXISTS `tbl_inspection_driver_support` (
  `id` varchar(100) COLLATE utf8mb4_0900_bin NOT NULL,
  `description` text COLLATE utf8mb4_0900_bin,
  `example_param` text COLLATE utf8mb4_0900_bin,
  `label` varchar(50) COLLATE utf8mb4_0900_bin NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_bin;

CREATE TABLE IF NOT EXISTS `tbl_inspector_support` (
  `id` varchar(100) COLLATE utf8mb4_0900_bin NOT NULL,
  `description` text COLLATE utf8mb4_0900_bin,
  `example_param` text COLLATE utf8mb4_0900_bin,
  `label` varchar(50) COLLATE utf8mb4_0900_bin NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_bin;

CREATE TABLE IF NOT EXISTS `tbl_audit_entry` (
  `id` bigint NOT NULL,
  `category_id` varchar(255) COLLATE utf8mb4_0900_bin DEFAULT NULL,
  `created_date` datetime(6) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK1onxnv7o9n3jnopvvxmdutx40` (`category_id`),
  CONSTRAINT `FK1onxnv7o9n3jnopvvxmdutx40` FOREIGN KEY (`category_id`) REFERENCES `tbl_audit_category` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_bin;

CREATE TABLE IF NOT EXISTS `tbl_audit_property_indicator` (
  `audit_category_id` varchar(255) COLLATE utf8mb4_0900_bin NOT NULL,
  `property_id` varchar(100) COLLATE utf8mb4_0900_bin NOT NULL,
  `created_datamark` varchar(100) COLLATE utf8mb4_0900_bin DEFAULT NULL,
  `default_boolean_value` bit(1) DEFAULT NULL,
  `default_date_value` datetime(6) DEFAULT NULL,
  `default_double_value` double DEFAULT NULL,
  `default_long_value` bigint DEFAULT NULL,
  `default_string_value` varchar(200) COLLATE utf8mb4_0900_bin DEFAULT NULL,
  `label` varchar(50) COLLATE utf8mb4_0900_bin DEFAULT NULL,
  `modified_datamark` varchar(100) COLLATE utf8mb4_0900_bin DEFAULT NULL,
  `column_order` int NOT NULL,
  `property_type` int NOT NULL,
  PRIMARY KEY (`audit_category_id`,`property_id`),
  CONSTRAINT `FK65ik3lgn8kcwpo6ttbi1gxxlf` FOREIGN KEY (`audit_category_id`) REFERENCES `tbl_audit_category` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_bin;

CREATE TABLE IF NOT EXISTS `tbl_audit_entry_property` (
  `audit_entry_id` bigint NOT NULL,
  `property_id` varchar(100) COLLATE utf8mb4_0900_bin NOT NULL,
  `boolean_value` bit(1) DEFAULT NULL,
  `date_value` datetime(6) DEFAULT NULL,
  `double_value` double DEFAULT NULL,
  `long_value` bigint DEFAULT NULL,
  `property_type` int NOT NULL,
  `string_value` varchar(200) COLLATE utf8mb4_0900_bin DEFAULT NULL,
  PRIMARY KEY (`audit_entry_id`,`property_id`),
  CONSTRAINT `FK7qg0ja1ne13q5onubfva2b4ot` FOREIGN KEY (`audit_entry_id`) REFERENCES `tbl_audit_entry` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_bin;

CREATE TABLE IF NOT EXISTS `tbl_inspection_driver_info` (
  `id` bigint NOT NULL,
  `created_datamark` varchar(100) COLLATE utf8mb4_0900_bin DEFAULT NULL,
  `enabled` bit(1) NOT NULL,
  `inspection_id` bigint DEFAULT NULL,
  `modified_datamark` varchar(100) COLLATE utf8mb4_0900_bin DEFAULT NULL,
  `param` text COLLATE utf8mb4_0900_bin,
  `remark` varchar(200) COLLATE utf8mb4_0900_bin DEFAULT NULL,
  `type` varchar(50) COLLATE utf8mb4_0900_bin NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKpwj6q935u7g23y53qq1ecl5f7` (`inspection_id`),
  CONSTRAINT `FKpwj6q935u7g23y53qq1ecl5f7` FOREIGN KEY (`inspection_id`) REFERENCES `tbl_inspection` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_bin;

CREATE TABLE IF NOT EXISTS `tbl_inspector_info` (
  `id` bigint NOT NULL,
  `created_datamark` varchar(100) COLLATE utf8mb4_0900_bin DEFAULT NULL,
  `enabled` bit(1) NOT NULL,
  `column_index` int NOT NULL,
  `inspection_id` bigint DEFAULT NULL,
  `modified_datamark` varchar(100) COLLATE utf8mb4_0900_bin DEFAULT NULL,
  `param` text COLLATE utf8mb4_0900_bin,
  `remark` varchar(200) COLLATE utf8mb4_0900_bin DEFAULT NULL,
  `type` varchar(50) COLLATE utf8mb4_0900_bin NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKch1fmhv7dxeac17ceyvnjkbbp` (`inspection_id`),
  CONSTRAINT `FKch1fmhv7dxeac17ceyvnjkbbp` FOREIGN KEY (`inspection_id`) REFERENCES `tbl_inspection` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_bin;

CREATE TABLE IF NOT EXISTS `tbl_inspection_task` (
  `id` bigint NOT NULL,
  `anchor_message` varchar(12450) COLLATE utf8mb4_0900_bin DEFAULT NULL,
  `created_date` datetime(6) DEFAULT NULL,
  `died_date` datetime(6) DEFAULT NULL,
  `duration` bigint DEFAULT NULL,
  `ended_date` datetime(6) DEFAULT NULL,
  `expired_date` datetime(6) DEFAULT NULL,
  `inspection_id` bigint DEFAULT NULL,
  `should_die_date` datetime(6) DEFAULT NULL,
  `should_expire_date` datetime(6) DEFAULT NULL,
  `started_date` datetime(6) DEFAULT NULL,
  `status` int NOT NULL,
  `inspection` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKej9n6s3mvsp1nli6w6h6r7eb6` (`inspection_id`),
  CONSTRAINT `FKej9n6s3mvsp1nli6w6h6r7eb6` FOREIGN KEY (`inspection_id`) REFERENCES `tbl_inspection` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_bin;

CREATE TABLE IF NOT EXISTS `tbl_inspection_task_event` (
  `id` bigint NOT NULL,
  `happened_date` datetime(6) DEFAULT NULL,
  `inspection_task_id` bigint DEFAULT NULL,
  `message` varchar(12450) COLLATE utf8mb4_0900_bin DEFAULT NULL,
  `inspection_task` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKff4x3jm2ab3b9c724ggq4kpev` (`inspection_task`),
  KEY `FKsq954q1j6aqd1h1h87b996rkq` (`inspection_task_id`),
  CONSTRAINT `FKff4x3jm2ab3b9c724ggq4kpev` FOREIGN KEY (`inspection_task`) REFERENCES `tbl_inspection_task` (`id`),
  CONSTRAINT `FKsq954q1j6aqd1h1h87b996rkq` FOREIGN KEY (`inspection_task_id`) REFERENCES `tbl_inspection_task` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_bin;

CREATE TABLE IF NOT EXISTS `tbl_inspection_alarm` (
  `id` bigint NOT NULL,
  `happened_date` datetime(6) DEFAULT NULL,
  `inspection_id` bigint DEFAULT NULL,
  `inspection_task_id` bigint DEFAULT NULL,
  `inspector_info_id` bigint DEFAULT NULL,
  `message` varchar(200) COLLATE utf8mb4_0900_bin DEFAULT NULL,
  `type` varchar(50) COLLATE utf8mb4_0900_bin DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKqbqip1b3qvy73b0ex6n27orcr` (`inspection_id`),
  KEY `FK9ujbdaxek2lcd4wagalod7e8y` (`inspection_task_id`),
  KEY `FKsybnwsel4hfm50kgdns3sw200` (`inspector_info_id`),
  CONSTRAINT `FK9ujbdaxek2lcd4wagalod7e8y` FOREIGN KEY (`inspection_task_id`) REFERENCES `tbl_inspection_task` (`id`),
  CONSTRAINT `FKqbqip1b3qvy73b0ex6n27orcr` FOREIGN KEY (`inspection_id`) REFERENCES `tbl_inspection` (`id`),
  CONSTRAINT `FKsybnwsel4hfm50kgdns3sw200` FOREIGN KEY (`inspector_info_id`) REFERENCES `tbl_inspector_info` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_bin;

CREATE TABLE IF NOT EXISTS `tbl_inspector_variable` (
  `inspector_info_id` bigint NOT NULL,
  `variable_id` varchar(100) COLLATE utf8mb4_0900_bin NOT NULL,
  `boolean_value` bit(1) DEFAULT NULL,
  `date_value` datetime(6) DEFAULT NULL,
  `double_value` double DEFAULT NULL,
  `long_value` bigint DEFAULT NULL,
  `string_value` text COLLATE utf8mb4_0900_bin,
  `value_type` int NOT NULL,
  PRIMARY KEY (`inspector_info_id`,`variable_id`),
  CONSTRAINT `FKhvp7q7d1xm09uceti6l4fy0q2` FOREIGN KEY (`inspector_info_id`) REFERENCES `tbl_inspector_info` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_bin;

INSERT INTO `tbl_audit_category`
    (`id`, `created_datamark`, `enabled`, `modified_datamark`, `name`, `remark`)
VALUES
    ('quickstart.webapi', 'quickstart', b'1', 'quickstart', 'QuickStart WebAPI Audit',
     'Records WebAPI calls for the automatic audit demonstration.')
ON DUPLICATE KEY UPDATE
    `enabled` = VALUES(`enabled`),
    `name` = VALUES(`name`),
    `remark` = VALUES(`remark`);

INSERT INTO `tbl_audit_property_indicator`
    (`audit_category_id`, `property_id`, `created_datamark`, `default_boolean_value`,
     `default_date_value`, `default_double_value`, `default_long_value`, `default_string_value`,
     `label`, `modified_datamark`, `column_order`, `property_type`)
VALUES
    ('quickstart.webapi', 'requestMethod', 'quickstart', NULL, NULL, NULL, NULL, 'GET',
     'Request Method', 'quickstart', 10, 0),
    ('quickstart.webapi', 'requestUri', 'quickstart', NULL, NULL, NULL, NULL, '/api/quickstart',
     'Request URI', 'quickstart', 20, 0),
    ('quickstart.webapi', 'message', 'quickstart', NULL, NULL, NULL, NULL, 'QuickStart request',
     'Message', 'quickstart', 30, 0),
    ('quickstart.webapi', 'result', 'quickstart', NULL, NULL, NULL, NULL, 'success',
     'Result', 'quickstart', 40, 0),
    ('quickstart.webapi', 'success', 'quickstart', b'1', NULL, NULL, NULL, NULL,
     'Success', 'quickstart', 50, 3)
ON DUPLICATE KEY UPDATE
    `default_boolean_value` = VALUES(`default_boolean_value`),
    `default_string_value` = VALUES(`default_string_value`),
    `label` = VALUES(`label`),
    `column_order` = VALUES(`column_order`),
    `property_type` = VALUES(`property_type`);

INSERT INTO `tbl_audit_entry` (`id`, `category_id`, `created_date`)
VALUES
    (910000000000000001, 'quickstart.webapi', NOW(6)),
    (910000000000000002, 'quickstart.webapi', NOW(6)),
    (910000000000000003, 'quickstart.webapi', NOW(6))
ON DUPLICATE KEY UPDATE
    `category_id` = VALUES(`category_id`),
    `created_date` = VALUES(`created_date`);

INSERT INTO `tbl_audit_entry_property`
    (`audit_entry_id`, `property_id`, `boolean_value`, `date_value`, `double_value`,
     `long_value`, `property_type`, `string_value`)
VALUES
    (910000000000000001, 'requestMethod', NULL, NULL, NULL, NULL, 0, 'GET'),
    (910000000000000001, 'requestUri', NULL, NULL, NULL, NULL, 0, '/api/quickstart/one'),
    (910000000000000001, 'message', NULL, NULL, NULL, NULL, 0, 'First QuickStart request'),
    (910000000000000001, 'result', NULL, NULL, NULL, NULL, 0, 'success'),
    (910000000000000001, 'success', b'1', NULL, NULL, NULL, 3, NULL),
    (910000000000000002, 'requestMethod', NULL, NULL, NULL, NULL, 0, 'POST'),
    (910000000000000002, 'requestUri', NULL, NULL, NULL, NULL, 0, '/api/quickstart/two'),
    (910000000000000002, 'message', NULL, NULL, NULL, NULL, 0, 'Second QuickStart request'),
    (910000000000000002, 'result', NULL, NULL, NULL, NULL, 0, 'success'),
    (910000000000000002, 'success', b'1', NULL, NULL, NULL, 3, NULL),
    (910000000000000003, 'requestMethod', NULL, NULL, NULL, NULL, 0, 'GET'),
    (910000000000000003, 'requestUri', NULL, NULL, NULL, NULL, 0, '/api/quickstart/three'),
    (910000000000000003, 'message', NULL, NULL, NULL, NULL, 0, 'Third QuickStart request'),
    (910000000000000003, 'result', NULL, NULL, NULL, NULL, 0, 'success'),
    (910000000000000003, 'success', b'1', NULL, NULL, NULL, 3, NULL)
ON DUPLICATE KEY UPDATE
    `boolean_value` = VALUES(`boolean_value`),
    `property_type` = VALUES(`property_type`),
    `string_value` = VALUES(`string_value`);

INSERT INTO `tbl_inspection`
    (`id`, `created_datamark`, `enabled`, `modified_datamark`, `name`, `remark`)
VALUES
    (1001, 'quickstart', b'1', 'quickstart', 'QuickStart WebAPI Rate Audit',
     'Creates an alarm when at least three WebAPI audit records appear within fifteen seconds.')
ON DUPLICATE KEY UPDATE
    `enabled` = VALUES(`enabled`),
    `name` = VALUES(`name`),
    `remark` = VALUES(`remark`);

INSERT INTO `tbl_inspection_alarm_type_indicator` (`id`, `label`, `remark`)
VALUES
    ('webapi_audit_rate_threshold', 'WebAPI Audit Rate Threshold',
     'The number of WebAPI audit records exceeded the QuickStart threshold.')
ON DUPLICATE KEY UPDATE
    `label` = VALUES(`label`),
    `remark` = VALUES(`remark`);

INSERT INTO `tbl_inspection_driver_support` (`id`, `description`, `example_param`, `label`)
VALUES
    ('cron_inspection_driver', 'Triggers an automatic audit according to a Cron expression.',
     '0/2 * * * * *', 'Cron Driver')
ON DUPLICATE KEY UPDATE
    `description` = VALUES(`description`),
    `example_param` = VALUES(`example_param`),
    `label` = VALUES(`label`);

INSERT INTO `tbl_inspector_support` (`id`, `description`, `example_param`, `label`)
VALUES
    ('groovy_inspector', 'Checks audit records with a Groovy script and creates alarms when needed.',
     'See the inspector configuration inserted below.', 'Groovy Inspector')
ON DUPLICATE KEY UPDATE
    `description` = VALUES(`description`),
    `example_param` = VALUES(`example_param`),
    `label` = VALUES(`label`);

INSERT INTO `tbl_inspection_driver_info`
    (`id`, `created_datamark`, `enabled`, `inspection_id`, `modified_datamark`, `param`, `remark`, `type`)
VALUES
    (1002, 'quickstart', b'1', 1001, 'quickstart', '0/2 * * * * *',
     'Runs the WebAPI rate audit every two seconds.', 'cron_inspection_driver')
ON DUPLICATE KEY UPDATE
    `enabled` = VALUES(`enabled`),
    `inspection_id` = VALUES(`inspection_id`),
    `param` = VALUES(`param`),
    `remark` = VALUES(`remark`),
    `type` = VALUES(`type`);

INSERT INTO `tbl_inspector_info`
    (`id`, `created_datamark`, `enabled`, `column_index`, `inspection_id`, `modified_datamark`, `param`, `remark`, `type`)
VALUES
    (1003, 'quickstart', b'1', 0, 1001, 'quickstart',
'import com.dwarfeng.audit.impl.handler.inspector.groovy.Processor
import com.dwarfeng.audit.stack.bean.dto.AuditEntryCompositeLookupInfo
import com.dwarfeng.audit.stack.bean.dto.InspectionAlarmCreateInfo
import com.dwarfeng.audit.stack.handler.Inspector
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey
import java.util.Collections
import java.util.Date

class QuickStartWebapiRateInspector implements Processor {

    @Override
    void inspect(Inspector.Context context) {
        Date now = new Date()
        Date start = new Date(now.time - 15000L)
        def result = context.lookupComposite(
                new AuditEntryCompositeLookupInfo(
                        new PagingInfo(0, 1),
                        new StringIdKey("quickstart.webapi"),
                        null,
                        start,
                        now,
                        Collections.emptyList()
                )
        )
        if (result.count >= 3) {
            context.createInspectionAlarm(
                    new InspectionAlarmCreateInfo(
                            context.inspection.key,
                            context.inspectionTask.key,
                            context.inspectorInfo.key,
                            "webapi_audit_rate_threshold",
                            "Detected ${result.count} WebAPI audit records in the latest 15 seconds"
                    )
            )
        }
    }
}
',
     'Creates an alarm when at least three QuickStart WebAPI audit records are found within fifteen seconds.',
     'groovy_inspector')
ON DUPLICATE KEY UPDATE
    `enabled` = VALUES(`enabled`),
    `column_index` = VALUES(`column_index`),
    `inspection_id` = VALUES(`inspection_id`),
    `param` = VALUES(`param`),
    `remark` = VALUES(`remark`),
    `type` = VALUES(`type`);
```

## 最小化配置

下文列出了启动程序需要改动的最少的配置文件，每个配置文件中仅展示需要改动的配置项。

`conf/curator/connection.properties` 文件中配置 curator 连接信息。

```properties
com.dwarfeng.audit.curator.connect.connect_string=your-host-here:2181
```

`conf/database/connection.properties` 文件中配置数据库连接信息。

```properties
com.dwarfeng.audit.jdbc.url=jdbc:mysql://your-host-here:3306/audit?serverTimezone=Asia/Shanghai
com.dwarfeng.audit.jdbc.username=root
com.dwarfeng.audit.jdbc.password=your-password-here
```

`conf/dubbo/connection.properties` 文件中配置 dubbo 连接信息。

```properties
com.dwarfeng.audit.dubbo.registry.zookeeper.address=zookeeper://your-host-here:2181
com.dwarfeng.audit.dubbo.protocol.dubbo.host=your-host-here
```

`conf/redis/connection.properties` 文件中配置 redis 连接信息。

```properties
com.dwarfeng.audit.redis.hostName=your-host-here
com.dwarfeng.audit.redis.port=6379
com.dwarfeng.audit.redis.password=your-password-here
```

`conf/audit/inspection-receiver.properties` 文件中配置自动审计接收器类型。本文使用单节点 JVM 内部接收器承接自动审计任务。

```properties
com.dwarfeng.audit.inspection_receiver.type=injvm
```

`conf/audit/inspection-dispatcher.properties` 文件中配置自动审计调度器类型。本文使用单节点 JVM 内部调度器触发自动审计任务。

```properties
com.dwarfeng.audit.inspection_dispatcher.type=injvm
```

`conf/audit/push.properties` 文件中配置推送器类型。本文不接入外部推送通道，可使用 `drain` 推送器丢弃推送事件。

```properties
com.dwarfeng.audit.pusher.type=drain
```

## 修改可选配置

下文列出了本文演示链路需要启用的 `opt/` 配置。发布包中对应实现默认以注释形式提供，可直接将对应文件调整为本节展示的完整配置。

`opt/opt-inspection-dispatcher.xml` 自动审计调度器可选配置。需要启用 Injvm 自动审计调度器。

```xml
<?xml version="1.0" encoding="UTF-8"?>
<!-- 以下注释用于抑制 idea 中 .md 的警告，实际并无错误，在使用时可以连同本注释一起删除。 -->
<!--suppress SpringXmlModelInspection -->
<beans
        xmlns:context="http://www.springframework.org/schema/context"
        xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
        xmlns="http://www.springframework.org/schema/beans"
        xsi:schemaLocation="http://www.springframework.org/schema/beans
        http://www.springframework.org/schema/beans/spring-beans.xsd
        http://www.springframework.org/schema/context
        http://www.springframework.org/schema/context/spring-context.xsd"
>

    <!-- 扫描自动审计调度器的实现包。 -->
    <context:component-scan
            base-package="com.dwarfeng.audit.impl.handler.inspdispatcher" use-default-filters="false"
    >
        <!-- 加载 InjvmInspectionDispatcher。 -->
        <context:include-filter
                type="assignable"
                expression="com.dwarfeng.audit.impl.handler.inspdispatcher.InjvmInspectionDispatcher"
        />
    </context:component-scan>
</beans>
```

`opt/opt-inspection-receiver.xml` 自动审计接收器可选配置。需要启用 Injvm 自动审计接收器。

```xml
<?xml version="1.0" encoding="UTF-8"?>
<!-- 以下注释用于抑制 idea 中 .md 的警告，实际并无错误，在使用时可以连同本注释一起删除。 -->
<!--suppress SpringXmlModelInspection -->
<beans
        xmlns:context="http://www.springframework.org/schema/context"
        xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
        xmlns="http://www.springframework.org/schema/beans"
        xsi:schemaLocation="http://www.springframework.org/schema/beans
        http://www.springframework.org/schema/beans/spring-beans.xsd
        http://www.springframework.org/schema/context
        http://www.springframework.org/schema/context/spring-context.xsd"
>

    <!-- 扫描自动审计接收器的实现包。 -->
    <context:component-scan
            base-package="com.dwarfeng.audit.impl.handler.inspreceiver" use-default-filters="false"
    >
        <!-- 加载 InjvmInspectionReceiver。 -->
        <context:include-filter
                type="assignable"
                expression="com.dwarfeng.audit.impl.handler.inspreceiver.InjvmInspectionReceiver"
        />
    </context:component-scan>
</beans>
```

`opt/opt-pusher.xml` 推送器可选配置。需要启用 Drain 推送器，与 `com.dwarfeng.audit.pusher.type=drain` 对应。

```xml
<?xml version="1.0" encoding="UTF-8"?>
<!-- 以下注释用于抑制 idea 中 .md 的警告，实际并无错误，在使用时可以连同本注释一起删除。 -->
<!--suppress SpringXmlModelInspection -->
<beans
        xmlns:context="http://www.springframework.org/schema/context"
        xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
        xmlns="http://www.springframework.org/schema/beans"
        xsi:schemaLocation="http://www.springframework.org/schema/beans
        http://www.springframework.org/schema/beans/spring-beans.xsd
        http://www.springframework.org/schema/context
        http://www.springframework.org/schema/context/spring-context.xsd"
>

    <!-- 扫描推送器的实现包。 -->
    <context:component-scan
            base-package="com.dwarfeng.audit.impl.handler.pusher" use-default-filters="false"
    >
        <!-- 加载 DrainPusher。 -->
        <context:include-filter
                type="assignable" expression="com.dwarfeng.audit.impl.handler.pusher.DrainPusher"
        />
    </context:component-scan>
</beans>
```

`opt/opt-inspector.xml` 审计器可选配置。需要启用 Groovy 审计器执行初始化脚本中的演示规则。

```xml
<?xml version="1.0" encoding="UTF-8"?>
<!-- 以下注释用于抑制 idea 中 .md 的警告，实际并无错误，在使用时可以连同本注释一起删除。 -->
<!--suppress SpringXmlModelInspection -->
<beans
        xmlns:context="http://www.springframework.org/schema/context"
        xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
        xmlns="http://www.springframework.org/schema/beans"
        xsi:schemaLocation="http://www.springframework.org/schema/beans
        http://www.springframework.org/schema/beans/spring-beans.xsd
        http://www.springframework.org/schema/context
        http://www.springframework.org/schema/context/spring-context.xsd"
>

    <!-- 加载 GroovyInspector。 -->
    <context:component-scan base-package="com.dwarfeng.audit.impl.handler.inspector"/>
</beans>
```

`opt/opt-inspection-driver.xml` 自动审计驱动器可选配置。需要启用 Cron 自动审计驱动器定时触发演示规则。

```xml
<?xml version="1.0" encoding="UTF-8"?>
<!-- 以下注释用于抑制 idea 中 .md 的警告，实际并无错误，在使用时可以连同本注释一起删除。 -->
<!--suppress SpringXmlModelInspection -->
<beans
        xmlns:context="http://www.springframework.org/schema/context"
        xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
        xmlns="http://www.springframework.org/schema/beans"
        xsi:schemaLocation="http://www.springframework.org/schema/beans
        http://www.springframework.org/schema/beans/spring-beans.xsd
        http://www.springframework.org/schema/context
        http://www.springframework.org/schema/context/spring-context.xsd"
>

    <!-- 扫描自动审计驱动器实现包。 -->
    <context:component-scan
            base-package="com.dwarfeng.audit.impl.handler.inspdriver" use-default-filters="false"
    >
        <!-- 加载 CronInspectionDriver。 -->
        <context:include-filter
                type="assignable"
                expression="com.dwarfeng.audit.impl.handler.inspdriver.CronInspectionDriverProvider"
        />
        <context:include-filter
                type="assignable"
                expression="com.dwarfeng.audit.impl.handler.inspdriver.CronInspectionDriverSupporter"
        />
    </context:component-scan>
</beans>
```

## 启动程序

在 `/usr/local/audit` 目录下执行如下命令：

```shell
sh  bin/audit-start.sh
```

1. 观察数据库，程序会在 `tbl_inspection_task` 表中写入自动审计任务数据。
2. 观察数据库，程序会在 `tbl_inspection_task_event` 表中写入任务执行过程中的事件数据。
3. 观察数据库，程序会在 `tbl_inspection_alarm` 表中写入 `webapi_audit_rate_threshold` 类型的演示告警数据。

如果程序启动时距离初始化脚本执行已超过十五秒，请重新执行 `docs/database/audit-quickstart.sql`，以写入当前时间的演示审计记录。

## 停止程序

在 `/usr/local/audit` 目录下执行如下命令：

```shell
sh  bin/audit-stop.sh
```
