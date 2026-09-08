package com.dwarfeng.audit.sdk.handler;

/**
 * 驱动器支持信息提供器。
 *
 * @author DwArFeng
 * @since 1.1.0
 */
public interface InspectionDriverSupporter {

    String provideType();

    String provideLabel();

    String provideDescription();

    String provideExampleParam();
}
