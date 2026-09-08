package com.dwarfeng.audit.sdk.handler.inspdriver;

import com.dwarfeng.audit.stack.handler.InspectionDriver;

/**
 * 驱动器的抽象实现。
 *
 * @author DwArFeng
 * @since 1.1.0
 */
public abstract class AbstractInspectionDriver implements InspectionDriver {

    protected Context context;

    @Override
    public void init(Context context) {
        this.context = context;
    }

    @Override
    public String toString() {
        return "AbstractInspectionDriver{" +
                "context=" + context +
                '}';
    }
}
