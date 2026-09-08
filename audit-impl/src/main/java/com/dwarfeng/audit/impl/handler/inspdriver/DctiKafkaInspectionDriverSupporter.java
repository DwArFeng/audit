package com.dwarfeng.audit.impl.handler.inspdriver;

import com.dwarfeng.audit.sdk.handler.InspectionDriverSupporter;
import org.springframework.stereotype.Component;

/**
 * Dcti 标准数据采集接口 Kafka 自动审计驱动支持器。
 *
 * @author DwArFeng
 * @since 1.1.0
 */
@Component
public class DctiKafkaInspectionDriverSupporter implements InspectionDriverSupporter {

    public static final String SUPPORT_TYPE = "dcti_kafka_inspection_driver";

    @Override
    public String provideType() {
        return SUPPORT_TYPE;
    }

    @Override
    public String provideLabel() {
        return "Dcti 标准数据采集接口 Kafka 自动审计驱动器";
    }

    @Override
    public String provideDescription() {
        return "从 Kafka 中接收到标准 Dcti 数据，并根据接收到的数据主键触发不同的自动审计。";
    }

    @Override
    public String provideExampleParam() {
        return "692653993448435712";
    }
}
