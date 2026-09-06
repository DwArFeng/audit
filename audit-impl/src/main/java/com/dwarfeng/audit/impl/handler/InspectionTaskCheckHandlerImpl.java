package com.dwarfeng.audit.impl.handler;

import com.dwarfeng.audit.stack.bean.dto.InspectionTaskDieInfo;
import com.dwarfeng.audit.stack.bean.dto.InspectionTaskExpireInfo;
import com.dwarfeng.audit.stack.bean.entity.InspectionTask;
import com.dwarfeng.audit.stack.handler.InspectionTaskCheckHandler;
import com.dwarfeng.audit.stack.handler.InspectionTaskOperateHandler;
import com.dwarfeng.audit.stack.service.InspectionTaskMaintainService;
import com.dwarfeng.subgrade.impl.handler.CuratorDistributedLockHandler;
import com.dwarfeng.subgrade.impl.handler.Worker;
import com.dwarfeng.subgrade.sdk.interceptor.analyse.BehaviorAnalyse;
import com.dwarfeng.subgrade.stack.exception.HandlerException;
import org.apache.curator.framework.CuratorFramework;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.Future;

/**
 * 自动审计任务检查处理器实现。
 *
 * @author DwArFeng
 * @since 1.1.0
 */
@Component
@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
public class InspectionTaskCheckHandlerImpl implements InspectionTaskCheckHandler {

    private final CuratorDistributedLockHandler handler;

    public InspectionTaskCheckHandlerImpl(
            CuratorFramework curatorFramework,
            @Value("${com.dwarfeng.audit.curator.latch_path.task_check.leader_latch}") String leaderLatchPath,
            InspectionTaskCheckWorker worker
    ) {
        handler = new CuratorDistributedLockHandler(curatorFramework, leaderLatchPath, worker);
    }

    @BehaviorAnalyse
    @Override
    public boolean isOnline() {
        return handler.isOnline();
    }

    @BehaviorAnalyse
    @Override
    public void online() throws HandlerException {
        handler.online();
    }

    @BehaviorAnalyse
    @Override
    public void offline() throws HandlerException {
        handler.offline();
    }

    @BehaviorAnalyse
    @Override
    public boolean isStarted() {
        return handler.isStarted();
    }

    @BehaviorAnalyse
    @Override
    public void start() throws HandlerException {
        handler.start();
    }

    @BehaviorAnalyse
    @Override
    public void stop() throws HandlerException {
        handler.stop();
    }

    @BehaviorAnalyse
    @Override
    public boolean isLockHolding() {
        return handler.isLockHolding();
    }

    @BehaviorAnalyse
    @Override
    public boolean isWorking() {
        return handler.isWorking();
    }

    @Component
    public static class InspectionTaskCheckWorker implements Worker {

        private static final Logger LOGGER = LoggerFactory.getLogger(InspectionTaskCheckWorker.class);

        private final InspectionTaskMaintainService inspectionTaskMaintainService;
        private final InspectionTaskOperateHandler inspectionTaskOperateHandler;
        private final ThreadPoolTaskScheduler scheduler;

        @Value("${com.dwarfeng.audit.inspection_task.check.expire_check.cron:0 */1 * * * *}")
        private String expireCheckCron;
        @Value("${com.dwarfeng.audit.inspection_task.check.die_check.cron:0 */1 * * * *}")
        private String dieCheckCron;

        private Future<?> expireCheckFuture;
        private Future<?> dieCheckFuture;

        public InspectionTaskCheckWorker(
                InspectionTaskMaintainService inspectionTaskMaintainService,
                InspectionTaskOperateHandler inspectionTaskOperateHandler,
                ThreadPoolTaskScheduler scheduler
        ) {
            this.inspectionTaskMaintainService = inspectionTaskMaintainService;
            this.inspectionTaskOperateHandler = inspectionTaskOperateHandler;
            this.scheduler = scheduler;
        }

        @Override
        public void work() {
            if (Objects.isNull(expireCheckFuture)) {
                expireCheckFuture = scheduler.schedule(this::expireCheck, new CronTrigger(expireCheckCron));
            }
            if (Objects.isNull(dieCheckFuture)) {
                dieCheckFuture = scheduler.schedule(this::dieCheck, new CronTrigger(dieCheckCron));
            }
        }

        @Override
        public void rest() {
            if (Objects.nonNull(expireCheckFuture)) {
                expireCheckFuture.cancel(true);
                expireCheckFuture = null;
            }
            if (Objects.nonNull(dieCheckFuture)) {
                dieCheckFuture.cancel(true);
                dieCheckFuture = null;
            }
        }

        /**
         * 检查并过期超过启动等待时间的自动审计任务。
         */
        public void expireCheck() {
            try {
                LOGGER.info("检查过期自动审计任务...");
                List<InspectionTask> tasksToExpire = inspectionTaskMaintainService.lookupAsList(
                        InspectionTaskMaintainService.SHOULD_EXPIRE, new Object[0]
                );
                for (InspectionTask task : tasksToExpire) {
                    inspectionTaskOperateHandler.expire(new InspectionTaskExpireInfo(task.getKey()));
                }
            } catch (Exception e) {
                LOGGER.warn("检查过期自动审计任务时发生异常，异常信息如下", e);
            }
        }

        /**
         * 检查并死亡超过心跳等待时间的自动审计任务。
         */
        public void dieCheck() {
            try {
                LOGGER.info("检查死亡自动审计任务...");
                List<InspectionTask> tasksToDie = inspectionTaskMaintainService.lookupAsList(
                        InspectionTaskMaintainService.SHOULD_DIE, new Object[0]
                );
                for (InspectionTask task : tasksToDie) {
                    inspectionTaskOperateHandler.die(new InspectionTaskDieInfo(task.getKey()));
                }
            } catch (Exception e) {
                LOGGER.warn("检查死亡自动审计任务时发生异常，异常信息如下", e);
            }
        }
    }
}
