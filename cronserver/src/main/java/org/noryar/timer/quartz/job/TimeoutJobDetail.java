package org.noryar.timer.quartz.job;

import org.noryar.timer.common.SpringContextUtil;
import org.noryar.timer.job.timeout.model.TimeoutJob;
import org.noryar.timer.job.timeout.service.TimeoutJobService;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.util.Date;

public class TimeoutJobDetail implements Job {
    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        String idStr = jobExecutionContext.getJobDetail().getKey().getName();
        TimeoutJobService timeoutJobService = (TimeoutJobService) SpringContextUtil.getApplicationContext()
                .getBean("timeoutJobServiceImpl");
        TimeoutJob timeoutJob = timeoutJobService.get(Long.valueOf(idStr));
        if (timeoutJob != null) {
            timeoutJob.setEndTime(new Date());
            timeoutJobService.save(timeoutJob);
        }
    }
}
