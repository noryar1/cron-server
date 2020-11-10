package org.noryar.timer.register.serivce.impl;

import com.alibaba.dubbo.config.annotation.Service;
import org.noryar.timer.job.crontab.model.CrontabJob;
import org.noryar.timer.job.timeout.model.TimeoutJob;
import org.noryar.timer.job.timeout.service.TimeoutJobService;
import org.noryar.timer.quartz.bean.QuartzGroup;
import org.noryar.timer.register.bean.CrontabJobBean;
import org.noryar.timer.job.crontab.service.CrontabJobService;
import org.noryar.timer.quartz.scheduler.service.QuartzSchedulerService;
import org.noryar.timer.register.bean.TimeoutJobBean;
import org.noryar.timer.register.service.TimerJobRegisterService;
import org.apache.commons.lang3.StringUtils;
import org.quartz.CronExpression;
import org.quartz.JobExecutionException;
import org.quartz.SchedulerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service(version = "1.0.0")
@org.springframework.stereotype.Service
@Transactional(rollbackOn = SchedulerException.class)
public class TimerJobRegisterServiceImpl implements TimerJobRegisterService {

    private static final Logger LOGGER = LoggerFactory.getLogger(TimerJobRegisterServiceImpl.class);
    @Resource
    private QuartzSchedulerService quartzSchedulerService;
    @Resource
    private CrontabJobService crontabJobService;
    @Resource
    private TimeoutJobService timeoutJobService;

    @Override
    public CrontabJobBean register(CrontabJobBean crontabJobBean) throws SchedulerException {
        checkJobBean(crontabJobBean, false);
        CrontabJob job = new CrontabJob();
        BeanUtils.copyProperties(crontabJobBean, job);
        try {
            crontabJobService.save(job);
            quartzSchedulerService.createCrontabJob(job.getId(), job.getCrontab());
            crontabJobBean.setId(job.getId());
            return crontabJobBean;
        } catch (SchedulerException e) {
            if (StringUtils.isNotBlank(e.getMessage()) && e.getMessage().endsWith("will never fire.")) {
                throw new JobExecutionException("表达式永远不会被触发,请检查!");
            }
            throw e;
        }
    }

    @Override
    public TimeoutJobBean register(TimeoutJobBean timeoutJobBean) {
        return null;
    }

    @Override
    public List<TimeoutJobBean> register(List<TimeoutJobBean> timeoutJobBeans) throws SchedulerException {
        LOGGER.info("register timeout jobs start. cnt: {}", timeoutJobBeans.size());
        List<TimeoutJob> timeoutJobs = new ArrayList<TimeoutJob>();
        for (TimeoutJobBean timeoutJobBean : timeoutJobBeans) {
            TimeoutJob timeoutJob = new TimeoutJob();
            timeoutJob.setStartTime(new Date());
            timeoutJob.setTimeout(timeoutJobBean.getTimeout());
            timeoutJobs.add(timeoutJob);
        }
        timeoutJobService.saveAll(timeoutJobs);
        for (int i = 0; i < timeoutJobs.size(); i++) {
            quartzSchedulerService.createTimeoutJob(timeoutJobs.get(i).getId(),
                    new Date(timeoutJobs.get(i).getStartTime().getTime() + timeoutJobs.get(i).getTimeout() * 60 * 1000));
            timeoutJobBeans.get(i).setId(timeoutJobs.get(i).getId());
        }
        LOGGER.info("register timeout jobs end.");
        return timeoutJobBeans;
    }

    @Override
    public CrontabJobBean update(CrontabJobBean crontabJobBean) throws SchedulerException {
        checkJobBean(crontabJobBean, true);
        CrontabJob crontabJob = crontabJobService.get(crontabJobBean.getId());
        if (crontabJob == null) {
            crontabJob = new CrontabJob();
            BeanUtils.copyProperties(crontabJobBean, crontabJob);
            crontabJob.setId(null);
            crontabJobService.save(crontabJob);
            crontabJob.setId(crontabJob.getId());
        } else {
            crontabJob.setCrontab(crontabJobBean.getCrontab());
            crontabJob.setCmd(crontabJobBean.getCmd());
            crontabJobService.update(crontabJob);
            quartzSchedulerService.removeJob(crontabJob.getId(), QuartzGroup.TIMEOUT_GROUP);
        }
        try {
            quartzSchedulerService.createCrontabJob(crontabJob.getId(), crontabJob.getCrontab());
            return crontabJobBean;
        } catch (SchedulerException e) {
            if (StringUtils.isNotBlank(e.getMessage()) && e.getMessage().endsWith("will never fire.")) {
                throw new JobExecutionException("表达式永远不会被触发,请检查!");
            }
            throw e;
        }
    }

    @Override
    public CrontabJobBean get(Long id) throws SchedulerException {
        CrontabJob job = crontabJobService.get(id);
        CrontabJobBean crontabJobBean = new CrontabJobBean();
        if (job != null) {
            BeanUtils.copyProperties(job, crontabJobBean);
        }
        return crontabJobBean;
    }

    @Override
    public void delete(Long id) throws SchedulerException {
        crontabJobService.delete(id);
        quartzSchedulerService.removeJob(id, QuartzGroup.CRONTAB_GROUP);
    }

    private void checkJobBean(CrontabJobBean jobBean, boolean checkId) throws JobExecutionException {
        if (jobBean == null || StringUtils.isBlank(jobBean.getCmd())
                || !CronExpression.isValidExpression(jobBean.getCrontab())) {
            throw new JobExecutionException("参数不正确");
        }
        if (checkId && (jobBean.getId() == null || jobBean.getId() < 0L)) {
            throw new JobExecutionException("参数不正确");
        }
    }
}
