package org.noryar.timer.quartz.scheduler.service;

import org.noryar.timer.quartz.bean.QuartzGroup;
import org.quartz.SchedulerException;

import java.util.Date;

/**
 * quartz调度服务接口
 *
 * @author noryar
 */
public interface QuartzSchedulerService {

    /**
     * 创建定时任务.
     *
     * @param id      id.
     * @param crontab crontab.
     * @throws SchedulerException
     */
    void createCrontabJob(Long id, String crontab) throws SchedulerException;

    void createTimeoutJob(Long id, Date startTime) throws SchedulerException;

    void removeJob(Long id, QuartzGroup group) throws SchedulerException;
}

