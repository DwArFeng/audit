package com.dwarfeng.audit.impl.sdk.util;

import com.dwarfeng.audit.sdk.util.ServiceExceptionCodes;
import com.dwarfeng.audit.sdk.util.ServiceExceptionHelper;
import com.dwarfeng.audit.stack.exception.*;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import org.junit.Test;

import java.util.Map;

import static org.junit.Assert.assertSame;

/**
 * {@link ServiceExceptionHelper} 的测试。
 *
 * @author DwArFeng
 * @since 1.1.0
 */
public class ServiceExceptionHelperTest {

    @Test
    public void testPutDefaultDestination() {
        Map<Class<? extends Exception>, ServiceException.Code> destination =
                ServiceExceptionHelper.putDefaultDestination(null);

        assertSame(ServiceExceptionCodes.INSPECTION_NOT_EXISTS, destination.get(InspectionNotExistsException.class));
        assertSame(ServiceExceptionCodes.INSPECTION_TASK_NOT_EXISTS,
                destination.get(InspectionTaskNotExistsException.class));
        assertSame(ServiceExceptionCodes.INSPECTOR_INFO_NOT_EXISTS,
                destination.get(InspectorInfoNotExistsException.class));
        assertSame(ServiceExceptionCodes.INSPECTION_TASK_INSPECTION_MISMATCH,
                destination.get(InspectionTaskInspectionMismatchException.class));
        assertSame(ServiceExceptionCodes.INSPECTOR_INFO_INSPECTION_MISMATCH,
                destination.get(InspectorInfoInspectionMismatchException.class));
        assertSame(ServiceExceptionCodes.INSPECTOR_VARIABLE_NOT_EXISTS,
                destination.get(InspectorVariableNotExistsException.class));
        assertSame(ServiceExceptionCodes.INVALID_VARIABLE_VALUE_TYPE,
                destination.get(InvalidVariableValueTypeException.class));
        assertSame(ServiceExceptionCodes.VARIABLE_VALUE_TYPE_MISMATCH,
                destination.get(VariableValueTypeMismatchException.class));
    }
}
