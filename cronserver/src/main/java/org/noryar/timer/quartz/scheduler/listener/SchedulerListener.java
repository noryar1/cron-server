package org.noryar.timer.quartz.scheduler.listener;

import org.noryar.timer.quartz.scheduler.service.QuartzSchedulerService;
import org.noryar.timer.job.crontab.model.CrontabJob;
import org.noryar.timer.job.crontab.service.CrontabJobService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextRefreshedEvent;

/**
 * @author noryar.
 */
@Configuration
public class SchedulerListener implements ApplicationListener<ContextRefreshedEvent> {

    private static final Logger LOGGER = LoggerFactory.getLogger(SchedulerListener.class);

    @Autowired
    private QuartzSchedulerService quartzSchedulerService;
    @Autowired
    private CrontabJobService crontabJobService;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
//        Iterable<CrontabJob> jobs = crontabJobService.getAll();
//        for (CrontabJob job : jobs) {
//            try {
//                quartzSchedulerService.createCrontabJob(job.getId(), job.getCrontab());
//                LOGGER.info("create job: {}", job);
//            } catch (Exception e) {
//                LOGGER.error("create job error, job: {}", job, e);
//            }
//        }
    }
}
