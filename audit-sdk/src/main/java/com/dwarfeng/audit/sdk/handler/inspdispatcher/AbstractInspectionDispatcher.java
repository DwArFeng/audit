package com.dwarfeng.audit.sdk.handler.inspdispatcher;

import com.dwarfeng.audit.stack.exception.InspectionDispatcherException;
import com.dwarfeng.audit.stack.exception.InspectionDispatcherExecutionException;
import com.dwarfeng.audit.stack.exception.InspectionDispatcherNotStartException;
import com.dwarfeng.audit.stack.handler.InspectionDispatcher;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 调度器的抽象实现。
 *
 * @author DwArFeng
 * @since 1.1.0
 */
public abstract class AbstractInspectionDispatcher implements InspectionDispatcher {

    protected String dispatcherType;

    private final Lock lock = new ReentrantLock();
    private boolean startFlag;

    protected AbstractInspectionDispatcher() {
    }

    protected AbstractInspectionDispatcher(String dispatcherType) {
        this.dispatcherType = dispatcherType;
    }

    @Override
    public boolean supportType(String type) {
        lock.lock();
        try {
            return dispatcherType.equals(type);
        } finally {
            lock.unlock();
        }
    }

    @Override
    public void start() throws InspectionDispatcherException {
        lock.lock();
        try {
            if (startFlag) {
                return;
            }
            try {
                doStart();
            } catch (InspectionDispatcherExecutionException e) {
                throw e;
            } catch (Exception e) {
                throw new InspectionDispatcherExecutionException(e);
            }
            startFlag = true;
        } finally {
            lock.unlock();
        }
    }

    protected abstract void doStart() throws Exception;

    @Override
    public void stop() throws InspectionDispatcherException {
        lock.lock();
        try {
            if (!startFlag) {
                return;
            }
            try {
                doStop();
            } catch (InspectionDispatcherExecutionException e) {
                throw e;
            } catch (Exception e) {
                throw new InspectionDispatcherExecutionException(e);
            }
            startFlag = false;
        } finally {
            lock.unlock();
        }
    }

    protected abstract void doStop() throws Exception;

    @Override
    public void dispatch(LongIdKey inspectionKey) throws InspectionDispatcherException {
        lock.lock();
        try {
            if (!startFlag) {
                throw new InspectionDispatcherNotStartException();
            }
            try {
                doDispatch(inspectionKey);
            } catch (InspectionDispatcherExecutionException e) {
                throw e;
            } catch (Exception e) {
                throw new InspectionDispatcherExecutionException(e);
            }
        } finally {
            lock.unlock();
        }
    }

    protected abstract void doDispatch(LongIdKey inspectionKey) throws Exception;

    protected boolean isStarted() {
        lock.lock();
        try {
            return startFlag;
        } finally {
            lock.unlock();
        }
    }

    public String getDispatcherType() {
        return dispatcherType;
    }

    public void setDispatcherType(String dispatcherType) {
        this.dispatcherType = dispatcherType;
    }
}
