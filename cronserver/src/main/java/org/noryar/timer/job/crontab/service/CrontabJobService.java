package org.noryar.timer.job.crontab.service;

import org.noryar.timer.job.crontab.model.CrontabJob;

/**
 * crontab任务服务层接口
 *
 * @author noryar
 */
public interface CrontabJobService {

    void save(CrontabJob crontabJob);

    void update(CrontabJob crontabJob);

    CrontabJob get(Long id);

    Iterable<CrontabJob> getAll();

    void delete(Long id);

}
