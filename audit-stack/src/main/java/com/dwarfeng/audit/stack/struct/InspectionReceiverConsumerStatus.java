package com.dwarfeng.audit.stack.struct;

/**
 * 自动审计接收器消费者状态。
 *
 * @author DwArFeng
 * @since 1.1.0
 */
public final class InspectionReceiverConsumerStatus {

    private final int bufferedSize;
    private final int bufferSize;
    private final int thread;
    private final boolean idle;

    public InspectionReceiverConsumerStatus(int bufferedSize, int bufferSize, int thread, boolean idle) {
        this.bufferedSize = bufferedSize;
        this.bufferSize = bufferSize;
        this.thread = thread;
        this.idle = idle;
    }

    public int getBufferedSize() {
        return bufferedSize;
    }

    public int getBufferSize() {
        return bufferSize;
    }

    public int getThread() {
        return thread;
    }

    public boolean isIdle() {
        return idle;
    }

    @Override
    public String toString() {
        return "InspectionReceiverConsumerStatus{" +
                "bufferedSize=" + bufferedSize +
                ", bufferSize=" + bufferSize +
                ", thread=" + thread +
                ", idle=" + idle +
                '}';
    }
}
