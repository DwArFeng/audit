package com.dwarfeng.audit.sdk.handler;

/**
 * 审计器支持器。
 *
 * @author DwArFeng
 * @since 1.1.0
 */
public interface InspectorSupporter {

    /**
     * 提供审计器类型。
     *
     * @return 审计器类型。
     */
    String provideType();

    /**
     * 提供审计器标签。
     *
     * @return 审计器标签。
     */
    String provideLabel();

    /**
     * 提供审计器描述。
     *
     * @return 审计器描述。
     */
    String provideDescription();

    /**
     * 提供审计器示例参数。
     *
     * @return 审计器示例参数。
     */
    String provideExampleParam();
}
