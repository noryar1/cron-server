package org.noryar.timer.job.timeout.dao;

import org.noryar.timer.job.timeout.model.TimeoutJob;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TimeoutJobDao extends CrudRepository<TimeoutJob, Long> {
}
