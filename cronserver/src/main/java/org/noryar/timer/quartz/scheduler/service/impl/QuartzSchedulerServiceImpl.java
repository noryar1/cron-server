package org.noryar.timer.quartz.scheduler.service.impl;

import org.noryar.timer.quartz.bean.QuartzGroup;
import org.noryar.timer.quartz.job.ScheduledJob;
import org.noryar.timer.quartz.job.TimeoutJobDetail;
import org.noryar.timer.quartz.scheduler.service.QuartzSchedulerService;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * quartz调度服务实现
 *
 * @author noryar
 */
@Service
public class QuartzSchedulerServiceImpl implements QuartzSchedulerService {

    @Autowired
    private SchedulerFactoryBean schedulerFactoryBean;

    @Override
    public void createCrontabJob(Long id, String crontab) throws SchedulerException {
        removeJob(id, QuartzGroup.CRONTAB_GROUP);
        JobDetail jobDetail = JobBuilder.newJob(ScheduledJob.class)
                .withIdentity(String.valueOf(id), QuartzGroup.CRONTAB_GROUP.name()).build();
        CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(crontab);
        CronTrigger cronTrigger = TriggerBuilder.newTrigger()
                .withIdentity(String.valueOf(id), QuartzGroup.CRONTAB_GROUP.name())
                .withSchedule(scheduleBuilder)
                .build();
        schedulerFactoryBean.getScheduler().scheduleJob(jobDetail, cronTrigger);
    }

    @Override
    public void createTimeoutJob(Long id, Date startTime) throws SchedulerException {
        removeJob(id, QuartzGroup.TIMEOUT_GROUP);
        JobDetail jobDetail = JobBuilder.newJob(TimeoutJobDetail.class)
                .withIdentity(String.valueOf(id), QuartzGroup.TIMEOUT_GROUP.name()).build();
        SimpleTrigger simpleTrigger = TriggerBuilder.newTrigger()
                .startAt(startTime)
                .withIdentity(String.valueOf(id), QuartzGroup.TIMEOUT_GROUP.name())
                .withSchedule(SimpleScheduleBuilder.simpleSchedule())
                .build();
        schedulerFactoryBean.getScheduler().scheduleJob(jobDetail, simpleTrigger);
    }

    @Override
    public void removeJob(Long id, QuartzGroup group) throws SchedulerException {
        Scheduler scheduler = schedulerFactoryBean.getScheduler();
        scheduler.deleteJob(JobKey.jobKey(String.valueOf(id), group.name()));
    }
}

