package com.dwarfeng.audit.impl.handler;

import com.dwarfeng.audit.stack.bean.dto.PurgeFinishedResult;
import com.dwarfeng.audit.stack.bean.entity.InspectionTask;
import com.dwarfeng.audit.stack.handler.PushHandler;
import com.dwarfeng.audit.stack.service.InspectionTaskMaintainService;
import com.dwarfeng.dutil.basic.mea.TimeMeasurer;
import com.dwarfeng.dutil.basic.num.NumberUtil;
import com.dwarfeng.dutil.basic.num.unit.Time;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;

import java.text.DecimalFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;

/**
 * 清除处理器。
 *
 * <p>
 * 该处理器按保留时长分页删除已结束的自动审计任务，并通过推送处理器报告清除结果。
 *
 * @author DwArFeng
 * @since 1.1.0
 */
@Component
public class PurgeProcessor {

    private static final Logger LOGGER = LoggerFactory.getLogger(PurgeProcessor.class);

    private final InspectionTaskMaintainService inspectionTaskMaintainService;

    private final PushHandler pushHandler;

    private final ThreadPoolTaskScheduler scheduler;

    private final PurgeTask purgeTask = new PurgeTask();

    private final Lock lock = new ReentrantLock();

    private ScheduledFuture<?> purgeTaskScheduledFuture;

    /**
     * 上一次保留的任务距离清除时间的偏移量。
     *
     * <p>
     * 该变量用于判断清除任务是否发散，即每次清除的数据量是否小于生成的数据量。
     */
    private long lastRetentionTaskOffset = 0;

    @Value("${com.dwarfeng.audit.purge.retention_duration:0}")
    private long retentionDuration;

    @Value("${com.dwarfeng.audit.purge.task_cron:0 0/5 * * * *}")
    private String taskCron;

    @Value("${com.dwarfeng.audit.purge.max_page_size:100}")
    private int maxPageSize;

    @Value("${com.dwarfeng.audit.purge.max_deletion_size:1000}")
    private int maxDeletionSize;

    public PurgeProcessor(
            InspectionTaskMaintainService inspectionTaskMaintainService,
            PushHandler pushHandler,
            ThreadPoolTaskScheduler scheduler
    ) {
        this.inspectionTaskMaintainService = inspectionTaskMaintainService;
        this.pushHandler = pushHandler;
        this.scheduler = scheduler;
    }

    public void work() {
        lock.lock();
        try {
            if (Objects.nonNull(purgeTaskScheduledFuture)) {
                throw new IllegalStateException("不应该执行到此处, 请联系开发人员");
            }
            // 如果 retentionDuration <= 0 则不启动清除计划。
            if (retentionDuration <= 0) {
                LOGGER.info("由于保留时长小于等于 0, 清除计划不启动");
            }
            // 否则启动清除计划。
            else {
                LOGGER.info("清除计划启动, 保留时长: {} 毫秒, 任务执行周期: {}", retentionDuration, taskCron);
                LOGGER.info(
                        "保留时长约为 {} 天, 请注意配置是否正确",
                        new DecimalFormat("0.00").format(
                                NumberUtil.unitTrans(retentionDuration, Time.MS, Time.DAY).doubleValue()
                        )
                );
                purgeTaskScheduledFuture = scheduler.schedule(purgeTask, new CronTrigger(taskCron));
            }
        } finally {
            lock.unlock();
        }
    }

    public void rest() {
        lock.lock();
        try {
            if (Objects.isNull(purgeTaskScheduledFuture)) {
                return;
            }
            purgeTaskScheduledFuture.cancel(false);
            purgeTaskScheduledFuture = null;
        } finally {
            lock.unlock();
        }
    }

    private class PurgeTask implements Runnable {

        @Override
        public void run() {
            lock.lock();
            try {
                run0();
            } finally {
                lock.unlock();
            }
        }

        private void run0() {
            // 二次检查 retentionDuration 是否大于 0。
            if (retentionDuration <= 0) {
                LOGGER.warn("由于保留时长小于等于 0, 清除任务将不执行, 不该指定到此处, 请联系开发人员");
                return;
            }

            // 定义清除结果。
            PurgeFinishedResult purgeFinishedResult = null;

            try {
                // 日志记录。
                LOGGER.info("开始执行清除任务...");
                // 计算保留日期。
                LOGGER.debug("计算保留日期...");
                Date retentionDate = new Date(System.currentTimeMillis() - retentionDuration);
                LOGGER.debug("保留日期: {}", retentionDate);
                // 定义计时器。
                TimeMeasurer timeMeasurer = new TimeMeasurer();
                // 清除任务。
                LOGGER.info("清除任务...");
                timeMeasurer.start();
                PurgeResult taskPurgeResult = purgeTask(retentionDate);
                int taskDeletionCount = taskPurgeResult.getDeletionCount();
                boolean taskDivergent = taskPurgeResult.isDivergent();
                timeMeasurer.stop();
                LOGGER.info(
                        "清除任务完成, 共清除 {} 条数据, 耗时 {} 毫秒",
                        taskDeletionCount,
                        timeMeasurer.getTimeMs()
                );
                // 构造推送结果。
                purgeFinishedResult = new PurgeFinishedResult(taskDeletionCount, taskDivergent);
                // 日志记录。
                LOGGER.info("清除任务执行完成");
            } catch (Exception e) {
                // 日志记录。
                LOGGER.warn("清除任务执行失败, 本次清除中止, 异常信息如下: ", e);
            }

            // 日志记录。
            LOGGER.debug("清除任务执行结果: {}", purgeFinishedResult);
            try {
                if (Objects.nonNull(purgeFinishedResult)) {
                    pushHandler.purgeFinished(purgeFinishedResult);
                } else {
                    pushHandler.purgeFailed();
                }
            } catch (Exception e) {
                LOGGER.warn("推送清除结果时发生异常, 异常信息如下: ", e);
            }
        }

        private PurgeResult purgeTask(Date retentionDate) throws Exception {
            // 定义返回结果的参数。
            int deletionCount = 0;
            boolean divergent = false;

            // 分页查询并删除，直至全部删除或达到最大删除数量。
            while (deletionCount < maxDeletionSize) {
                // 确定当前页的大小。
                int pageSize = Math.min(maxPageSize, maxDeletionSize - deletionCount);
                // 按照当前页大小查询待清除的任务数据。
                List<InspectionTask> inspectionTasks = inspectionTaskMaintainService.lookupAsList(
                        InspectionTaskMaintainService.TO_PURGED,
                        new Object[]{retentionDate},
                        new PagingInfo(0, pageSize)
                );
                if (inspectionTasks.isEmpty()) {
                    // 没有更多数据可删除。
                    break;
                }
                // 调用维护服务批量删除当前页的数据。
                inspectionTaskMaintainService.batchDelete(
                        inspectionTasks.stream().map(InspectionTask::getKey).collect(Collectors.toList())
                );
                // 增加删除数量。
                deletionCount += inspectionTasks.size();
            }

            // 查询待清除的第一个数据。
            InspectionTask inspectionTask = inspectionTaskMaintainService.lookupFirst(
                    InspectionTaskMaintainService.TO_PURGED,
                    new Object[]{retentionDate}
            );

            // 如果第一个数据是 null，说明过期的数据全部删除完毕。
            if (Objects.isNull(inspectionTask)) {
                // 将 lastRetentionTaskOffset 置为 0。
                lastRetentionTaskOffset = 0;
            }
            // 如果第一个数据不是 null，说明有数据未被清除。
            else {
                // 计算偏移量。
                long currentRetentionTaskOffset =
                        retentionDate.getTime() - inspectionTask.getEndedDate().getTime();
                // 如果偏移量大于上一次的偏移量，说明清除发散，记录日志。
                if (currentRetentionTaskOffset > lastRetentionTaskOffset) {
                    LOGGER.warn("执行清除任务时检测到任务发散, 这意味着清除的数据小于生成的数据, 将会造成数据的积压");
                    LOGGER.warn("请减少清理任务的执行间隔或增加最大删除数量, 以避免任务的积压");
                    divergent = true;
                }
                // 更新 lastRetentionTaskOffset。
                lastRetentionTaskOffset = currentRetentionTaskOffset;
            }

            // 生成 PurgeResult 并返回。
            return new PurgeResult(deletionCount, divergent);
        }
    }

    private static final class PurgeResult {

        private final int deletionCount;
        private final boolean divergent;

        private PurgeResult(int deletionCount, boolean divergent) {
            this.deletionCount = deletionCount;
            this.divergent = divergent;
        }

        public int getDeletionCount() {
            return deletionCount;
        }

        public boolean isDivergent() {
            return divergent;
        }

        @Override
        public String toString() {
            return "PurgeResult{" +
                    "deletionCount=" + deletionCount +
                    ", divergent=" + divergent +
                    '}';
        }
    }
}
