package com.dwarfeng.audit.stack.service;

import com.dwarfeng.audit.stack.bean.dto.AuditRecordInfo;
import com.dwarfeng.audit.stack.struct.AuditRecordLocalCache;
import com.dwarfeng.subgrade.stack.bean.Bean;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.dwarfeng.subgrade.stack.service.Service;

/**
 * 审计记录服务质量服务。
 *
 * @author DwArFeng
 * @since 1.0.0-beta
 */
public interface AuditRecordQosService extends Service {

    /**
     * 获取指定审计类别的审计记录本地缓存。
     *
     * @param categoryKey 指定的审计类别主键。
     * @return 指定审计类别的审计记录本地缓存，或者是 null。
     * @throws ServiceException 服务异常。
     */
    AuditRecordLocalCache getAuditRecordLocalCache(StringIdKey categoryKey) throws ServiceException;

    /**
     * 清除审计记录本地缓存。
     *
     * @throws ServiceException 服务异常。
     */
    void clearAuditRecordLocalCache() throws ServiceException;

    /**
     * 获取逻辑侧记录服务是否已经开始。
     *
     * @return 逻辑侧记录服务是否已经开始。
     * @throws ServiceException 服务异常。
     */
    boolean isLogicStarted() throws ServiceException;

    /**
     * 开启逻辑测记录服务。
     *
     * @throws ServiceException 服务异常。
     */
    void logicStart() throws ServiceException;

    /**
     * 关闭逻辑测记录服务。
     *
     * @throws ServiceException 服务异常。
     */
    void logicStop() throws ServiceException;

    /**
     * 记录审计数据。
     *
     * @param auditRecordInfo 记录信息。
     * @throws ServiceException 服务异常。
     */
    void record(AuditRecordInfo auditRecordInfo) throws ServiceException;

    /**
     * 获取逻辑侧消费者的状态。
     *
     * @return 逻辑侧消费者的状态。
     * @throws ServiceException 服务异常。
     */
    LogicStatus getLogicStatus() throws ServiceException;

    /**
     * 设置逻辑侧消费者的参数。
     *
     * @param bufferSize 逻辑侧消费者的缓冲器的大小。
     * @param thread     逻辑侧消费者的线程数量。
     * @throws ServiceException 服务异常。
     */
    void setLogicParameters(Integer bufferSize, Integer thread) throws ServiceException;

    /**
     * 获取持久测消费者的消费者状态。
     *
     * @return 持久测消费者状态。
     * @throws ServiceException 服务异常。
     */
    PersistenceStatus getPersistenceStatus() throws ServiceException;

    /**
     * 设置持久测消费者的参数。
     *
     * @param bufferSize  持久测消费者的缓冲器的大小。
     * @param batchSize   持久测消费者的数据的批处理量。
     * @param maxIdleTime 持久测消费者的最大空闲时间。
     * @param thread      持久测消费者的线程数量。
     * @throws ServiceException 服务异常。
     */
    void setPersistenceParameters(Integer bufferSize, Integer batchSize, Long maxIdleTime, Integer thread)
            throws ServiceException;

    /**
     * 逻辑测消费者状态。
     *
     * @author DwArFeng
     * @since 1.0.0-beta
     */
    class LogicStatus implements Bean {

        private static final long serialVersionUID = 4403154342621902915L;

        private int bufferedSize;
        private int bufferSize;
        private int thread;
        private boolean idle;

        public LogicStatus() {
        }

        public LogicStatus(int bufferedSize, int bufferSize, int thread, boolean idle) {
            this.bufferedSize = bufferedSize;
            this.bufferSize = bufferSize;
            this.thread = thread;
            this.idle = idle;
        }

        public int getBufferedSize() {
            return bufferedSize;
        }

        public void setBufferedSize(int bufferedSize) {
            this.bufferedSize = bufferedSize;
        }

        public int getBufferSize() {
            return bufferSize;
        }

        public void setBufferSize(int bufferSize) {
            this.bufferSize = bufferSize;
        }

        public int getThread() {
            return thread;
        }

        public void setThread(int thread) {
            this.thread = thread;
        }

        public boolean isIdle() {
            return idle;
        }

        public void setIdle(boolean idle) {
            this.idle = idle;
        }
    }

    /**
     * 持久测消费者状态。
     *
     * @author DwArFeng
     * @since 1.0.0-beta
     */
    class PersistenceStatus implements Bean {

        private static final long serialVersionUID = -5738524060387995781L;

        private int bufferedSize;
        private int bufferSize;
        private int batchSize;
        private long maxIdleTime;
        private int thread;
        private boolean idle;

        public PersistenceStatus() {
        }

        public PersistenceStatus(
                int bufferedSize, int bufferSize, int batchSize, long maxIdleTime, int thread, boolean idle
        ) {
            this.bufferedSize = bufferedSize;
            this.bufferSize = bufferSize;
            this.batchSize = batchSize;
            this.maxIdleTime = maxIdleTime;
            this.thread = thread;
            this.idle = idle;
        }

        public int getBufferedSize() {
            return bufferedSize;
        }

        public void setBufferedSize(int bufferedSize) {
            this.bufferedSize = bufferedSize;
        }

        public int getBufferSize() {
            return bufferSize;
        }

        public void setBufferSize(int bufferSize) {
            this.bufferSize = bufferSize;
        }

        public int getBatchSize() {
            return batchSize;
        }

        public void setBatchSize(int batchSize) {
            this.batchSize = batchSize;
        }

        public long getMaxIdleTime() {
            return maxIdleTime;
        }

        public void setMaxIdleTime(long maxIdleTime) {
            this.maxIdleTime = maxIdleTime;
        }

        public int getThread() {
            return thread;
        }

        public void setThread(int thread) {
            this.thread = thread;
        }

        public boolean isIdle() {
            return idle;
        }

        public void setIdle(boolean idle) {
            this.idle = idle;
        }
    }
}
