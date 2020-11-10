package org.noryar.timer.job.timeout.service;

import org.noryar.timer.job.timeout.model.TimeoutJob;

import java.util.List;

/**
 * crontab任务服务层接口
 *
 * @author noryar
 */
public interface TimeoutJobService {

    void save(TimeoutJob timeoutJob);

    void saveAll(List<TimeoutJob> timeoutJobs);

    void update(TimeoutJob timeoutJob);

    TimeoutJob get(Long id);

    Iterable<TimeoutJob> getAll();

    void delete(Long id);

}
