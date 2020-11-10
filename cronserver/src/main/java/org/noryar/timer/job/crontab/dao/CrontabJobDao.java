package org.noryar.timer.job.crontab.dao;

import org.noryar.timer.job.crontab.model.CrontabJob;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CrontabJobDao extends CrudRepository<CrontabJob, Long> {
}
