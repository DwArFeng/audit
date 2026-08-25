package com.dwarfeng.audit.impl.handler;

import com.dwarfeng.audit.stack.bean.dto.AuditRecordInfo;
import com.dwarfeng.audit.stack.handler.AuditRecordHandler;
import com.dwarfeng.subgrade.impl.handler.GeneralStartableHandler;
import com.dwarfeng.subgrade.impl.handler.Worker;
import com.dwarfeng.subgrade.sdk.interceptor.analyse.BehaviorAnalyse;
import com.dwarfeng.subgrade.stack.exception.HandlerException;
import org.springframework.stereotype.Component;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Component
public class AuditRecordHandlerImpl implements AuditRecordHandler {

    private final GeneralStartableHandler startableHandler;

    private final AuditRecordProcessor auditRecordProcessor;

    private final Lock lock = new ReentrantLock();

    public AuditRecordHandlerImpl(RecordWorker recordWorker, AuditRecordProcessor auditRecordProcessor) {
        this.startableHandler = new GeneralStartableHandler(recordWorker);
        this.auditRecordProcessor = auditRecordProcessor;
    }

    @BehaviorAnalyse
    @Override
    public boolean isStarted() {
        lock.lock();
        try {
            return startableHandler.isStarted();
        } finally {
            lock.unlock();
        }
    }

    @BehaviorAnalyse
    @Override
    public void start() throws HandlerException {
        lock.lock();
        try {
            startableHandler.start();
        } finally {
            lock.unlock();
        }
    }

    @BehaviorAnalyse
    @Override
    public void stop() throws HandlerException {
        lock.lock();
        try {
            startableHandler.stop();
        } finally {
            lock.unlock();
        }
    }

    @BehaviorAnalyse
    @Override
    public void record(AuditRecordInfo auditRecordInfo) throws HandlerException {
        lock.lock();
        try {
            auditRecordProcessor.record(auditRecordInfo);
        } finally {
            lock.unlock();
        }
    }

    @BehaviorAnalyse
    @Override
    public int bufferedSize() {
        lock.lock();
        try {
            return auditRecordProcessor.bufferedSize();
        } finally {
            lock.unlock();
        }
    }

    @BehaviorAnalyse
    @Override
    public int getBufferSize() {
        lock.lock();
        try {
            return auditRecordProcessor.getBufferSize();
        } finally {
            lock.unlock();
        }
    }

    @BehaviorAnalyse
    @Override
    public void setBufferSize(int bufferSize) {
        lock.lock();
        try {
            auditRecordProcessor.setBufferSize(bufferSize);
        } finally {
            lock.unlock();
        }
    }

    @BehaviorAnalyse
    @Override
    public int getThread() {
        lock.lock();
        try {
            return auditRecordProcessor.getThread();
        } finally {
            lock.unlock();
        }
    }

    @BehaviorAnalyse
    @Override
    public void setThread(int thread) {
        lock.lock();
        try {
            auditRecordProcessor.setThread(thread);
        } finally {
            lock.unlock();
        }
    }

    @BehaviorAnalyse
    @Override
    public boolean isIdle() {
        lock.lock();
        try {
            return auditRecordProcessor.isIdle();
        } finally {
            lock.unlock();
        }
    }

    @Component
    public static class RecordWorker implements Worker {

        private final AuditRecordProcessor auditRecordProcessor;

        public RecordWorker(AuditRecordProcessor auditRecordProcessor) {
            this.auditRecordProcessor = auditRecordProcessor;
        }

        @Override
        public void work() throws Exception {
            auditRecordProcessor.workerWork();
        }

        @Override
        public void rest() throws Exception {
            auditRecordProcessor.workerRest();
        }
    }
}
