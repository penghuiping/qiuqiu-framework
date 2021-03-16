package com.php25.qiuqiu.job.repository;

import com.php25.common.db.repository.BaseDbRepository;
import com.php25.qiuqiu.job.model.JobExecution;

import java.util.Optional;

/**
 * @author penghuiping
 * @date 2021/3/15 20:36
 */
public interface JobExecutionRepository extends BaseDbRepository<JobExecution, String> {

    Optional<JobExecution> findByJobId(String jobId);
}
