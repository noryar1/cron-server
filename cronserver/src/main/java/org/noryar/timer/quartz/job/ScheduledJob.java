package org.noryar.timer.quartz.job;

import org.noryar.timer.common.HttpClientUtil;
import org.noryar.timer.common.SpringContextUtil;
import org.noryar.timer.job.crontab.model.CrontabJob;
import org.noryar.timer.job.crontab.service.CrontabJobService;
import org.noryar.timer.quartz.bean.CallBackInfoBean;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class ScheduledJob implements Job {

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        String id = context.getJobDetail().getKey().getName();
        System.out.println(concatCallBackUrl(Long.valueOf(id)));
        HttpClientUtil.get(concatCallBackUrl(Long.valueOf(id)), null);
    }

    private String concatCallBackUrl(Long id) {
        CrontabJobService crontabJobService = SpringContextUtil
                .getApplicationContext().getBean(CrontabJobService.class);
        CrontabJob crontabJob = crontabJobService.get(id);
        CallBackInfoBean callBackInfoBean = SpringContextUtil
                .getApplicationContext().getBean(CallBackInfoBean.class);
        StringBuilder url = new StringBuilder();
        url.append(callBackInfoBean.getHost())
                .append(callBackInfoBean.getApi()).append("?")
                .append("&user=").append(callBackInfoBean.getUsername())
                .append("&token=").append(callBackInfoBean.getToken())
                .append("&cmd=").append(crontabJob.getCmd());
        return url.toString();
    }
}
