package org.noryar.timer.job.timeout.service.impl;

import org.noryar.timer.job.timeout.dao.TimeoutJobDao;
import org.noryar.timer.job.timeout.model.TimeoutJob;
import org.noryar.timer.job.timeout.service.TimeoutJobService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * crontab任务服务层实现
 *
 * @author noryar
 */
@Service
@Transactional
public class TimeoutJobServiceImpl implements TimeoutJobService {

    @Resource
    private TimeoutJobDao timeoutJobDao;

    @Override
    public void save(TimeoutJob timeoutJob) {
        timeoutJobDao.save(timeoutJob);
    }

    @Override
    public void saveAll(List<TimeoutJob> timeoutJobs) {
        timeoutJobDao.save(timeoutJobs);
    }

    @Override
    public void update(TimeoutJob timeoutJob) {
        timeoutJobDao.save(timeoutJob);
    }

    @Override
    public TimeoutJob get(Long id) {
        return timeoutJobDao.findOne(id);
    }

    @Override
    public Iterable<TimeoutJob> getAll() {
        return timeoutJobDao.findAll();
    }

    @Override
    public void delete(Long id) {
        timeoutJobDao.delete(id);
    }

}
