package com.dwarfeng.audit.sdk.handler.inspreceiver;

import com.dwarfeng.audit.stack.exception.InspectionReceiverException;
import com.dwarfeng.audit.stack.exception.InspectionReceiverExecutionException;
import com.dwarfeng.audit.stack.handler.InspectionReceiver;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 接收器的抽象实现。
 *
 * @author DwArFeng
 * @since 1.1.0
 */
public abstract class AbstractInspectionReceiver implements InspectionReceiver {

    protected String receiverType;
    protected Context context;

    private final Lock lock = new ReentrantLock();
    private boolean startFlag;

    protected AbstractInspectionReceiver() {
    }

    protected AbstractInspectionReceiver(String receiverType) {
        this.receiverType = receiverType;
    }

    @Override
    public boolean supportType(String type) {
        lock.lock();
        try {
            return receiverType.equals(type);
        } finally {
            lock.unlock();
        }
    }

    @Override
    public void init(Context context) {
        lock.lock();
        try {
            this.context = context;
        } finally {
            lock.unlock();
        }
    }

    @Override
    public void start() throws InspectionReceiverException {
        lock.lock();
        try {
            if (startFlag) {
                return;
            }
            try {
                doStart();
            } catch (InspectionReceiverExecutionException e) {
                throw e;
            } catch (Exception e) {
                throw new InspectionReceiverExecutionException(e);
            }
            startFlag = true;
        } finally {
            lock.unlock();
        }
    }

    protected abstract void doStart() throws Exception;

    @Override
    public void stop() throws InspectionReceiverException {
        lock.lock();
        try {
            if (!startFlag) {
                return;
            }
            try {
                doStop();
            } catch (InspectionReceiverExecutionException e) {
                throw e;
            } catch (Exception e) {
                throw new InspectionReceiverExecutionException(e);
            }
            startFlag = false;
        } finally {
            lock.unlock();
        }
    }

    protected abstract void doStop() throws Exception;

    protected boolean isStarted() {
        lock.lock();
        try {
            return startFlag;
        } finally {
            lock.unlock();
        }
    }

    public String getReceiverType() {
        return receiverType;
    }

    public void setReceiverType(String receiverType) {
        this.receiverType = receiverType;
    }
}
