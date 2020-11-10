package org.noryar.timer.job.crontab.service.impl;

import org.noryar.timer.job.crontab.dao.CrontabJobDao;
import org.noryar.timer.job.crontab.model.CrontabJob;
import org.noryar.timer.job.crontab.service.CrontabJobService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * crontab任务服务层实现
 *
 * @author noryar
 */
@Service
@Transactional
public class CrontabJobServiceImpl implements CrontabJobService {

    @Resource
    private CrontabJobDao crontabJobDao;

    @Override
    public void save(CrontabJob crontabJob) {
        crontabJobDao.save(crontabJob);
    }

    @Override
    public void update(CrontabJob crontabJob) {
        crontabJobDao.save(crontabJob);
    }

    @Override
    public CrontabJob get(Long id) {
        return crontabJobDao.findOne(id);
    }

    @Override
    public Iterable<CrontabJob> getAll() {
        return crontabJobDao.findAll();
    }

    @Override
    public void delete(Long id) {
        crontabJobDao.delete(id);
    }

}
