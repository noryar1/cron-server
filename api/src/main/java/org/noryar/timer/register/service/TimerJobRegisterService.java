package org.noryar.timer.register.service;

import org.noryar.timer.register.bean.CrontabJobBean;
import org.noryar.timer.register.bean.TimeoutJobBean;
import org.quartz.SchedulerException;

import java.util.List;

public interface TimerJobRegisterService {

    /**
     * 注册任务.
     *
     * @param jobBean jobBean.
     * @return
     * @throws SchedulerException
     */
    CrontabJobBean register(CrontabJobBean jobBean) throws SchedulerException;

    TimeoutJobBean register(TimeoutJobBean timeoutJobBean);

    List<TimeoutJobBean> register(List<TimeoutJobBean> timeoutJobBeans) throws SchedulerException;

    /**
     * 更新任务.
     *
     * @param jobBean jobBean.
     * @throws SchedulerException
     */
    CrontabJobBean update(CrontabJobBean jobBean) throws SchedulerException;

    /**
     * 查询任务.
     *
     * @param id id.
     * @return job.
     */
    CrontabJobBean get(Long id) throws SchedulerException;

    /**
     * 删除任务.
     *
     * @param id id.
     */
    void delete(Long id) throws SchedulerException;
}
