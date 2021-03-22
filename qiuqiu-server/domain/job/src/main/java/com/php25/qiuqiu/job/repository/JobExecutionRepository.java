package com.php25.qiuqiu.job.repository;

import com.php25.common.db.repository.BaseDbRepository;
import com.php25.qiuqiu.job.model.JobExecution;

import java.util.Optional;

/**
 * @author penghuiping
 * @date 2021/3/15 20:36
 */
public interface JobExecutionRepository extends BaseDbRepository<JobExecution, String> {

    /**
     * 通过jobId获取任务执行信息
     *
     * @param jobId 任务id
     * @return 任务执行信息
     */
    Optional<JobExecution> findByJobId(String jobId);

    /**
     * 把TimerLoadedNumber字段加一
     *
     * @param executionId 任务执行id
     * @return true:成功
     */
    Boolean addTimerLoadedNumber(String executionId);

    /**
     * 把TimerLoadedNumber字段减一
     *
     * @param executionId 任务执行id
     * @return true:成功
     */
    Boolean minusTimerLoadedNumber(String executionId);
}
