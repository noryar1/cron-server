package org.noryar.timer.register.web;

import org.apache.commons.lang3.RandomUtils;
import org.noryar.timer.job.crontab.model.CrontabJob;
import org.noryar.timer.job.timeout.service.TimeoutJobService;
import org.noryar.timer.register.bean.CrontabJobBean;
import org.noryar.timer.job.crontab.service.CrontabJobService;
import org.noryar.timer.register.bean.TimeoutJobBean;
import org.noryar.timer.register.service.TimerJobRegisterService;
import org.quartz.SchedulerException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 任务注册api
 *
 * @author noryar
 */
@RestController
@RequestMapping("/api/job/register")
public class JobRegisterController {

    @Resource
    private CrontabJobService crontabJobService;
    @Resource
    private TimeoutJobService timeoutJobService;
    @Resource
    private TimerJobRegisterService timerJobRegisterService;

    @RequestMapping(value = "/crontab", method = RequestMethod.POST)
    public ResponseEntity<CrontabJobBean> create(@RequestBody CrontabJobBean crontabJobBean) {
        ResponseEntity<CrontabJobBean> response;
        try {
            CrontabJobBean result = timerJobRegisterService.register(crontabJobBean);
            response = new ResponseEntity<CrontabJobBean>(result, HttpStatus.OK);
        } catch (Exception e) {
            response = new ResponseEntity<CrontabJobBean>(HttpStatus.BAD_REQUEST);
        }
        return response;
    }

    @RequestMapping(value = "/crontab/{id}", method = RequestMethod.GET)
    public ResponseEntity get(@PathVariable Long id) {
        CrontabJob job = crontabJobService.get(id);
        return new ResponseEntity(job, HttpStatus.OK);
    }

    @RequestMapping(value = "/crontab/all", method = RequestMethod.GET)
    public ResponseEntity<List> getAll() {
        Iterable<CrontabJob> jobs = crontabJobService.getAll();
        return new ResponseEntity(jobs, HttpStatus.OK);
    }

    @RequestMapping(value = "/crontab/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<String> delete(@PathVariable Long id) throws SchedulerException {
        timerJobRegisterService.delete(id);
        return new ResponseEntity<String>(HttpStatus.NO_CONTENT);
    }

    @RequestMapping(value = "/timeout")
    public ResponseEntity<String> createTimeoutJob(@RequestParam int cnt,
                                                   @RequestParam(defaultValue = "1") int timeout)
            throws SchedulerException {
        ArrayList<TimeoutJobBean> timeoutJobBeans = new ArrayList<TimeoutJobBean>();
        for (int i = 0; i < cnt; i++) {
            TimeoutJobBean timeoutJobBean = new TimeoutJobBean();
            timeoutJobBean.setTimeout(timeout);
            timeoutJobBeans.add(timeoutJobBean);
        }
        timerJobRegisterService.register(timeoutJobBeans);
        return new ResponseEntity<String>(String.valueOf(cnt), HttpStatus.OK);
    }

}
