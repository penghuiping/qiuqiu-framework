package com.php25.qiuqiu.job.repository;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.php25.qiuqiu.job.entity.JobExecution;

import java.util.List;
import java.util.Optional;

/**
 * @author penghuiping
 * @date 2021/3/15 20:36
 */
public interface JobExecutionRepository {

    /**
     * 通过jobId获取任务执行信息
     *
     * @param jobId 任务id
     * @return 任务执行信息
     */
    Optional<JobExecution> findByJobId(String jobId);

    /**
     * 把所有的执行计划timer加载数重置为0
     *
     * @return
     */
    Boolean resetTimerLoadedNumber();

    /**
     * 保存与更新任务执行实例
     *
     * @param jobExecution 任务执行实例
     * @return true:成功
     */
    Boolean save(JobExecution jobExecution);

    /**
     * 根据id获取任务执行实例
     *
     * @param id 唯一标识
     * @return 任务执行实例
     */
    Optional<JobExecution> findById(String id);

    /**
     * 根据id删除任务执行实例
     *
     * @param id 唯一标识
     * @return true:成功
     */
    Boolean deleteById(String id);

    List<JobExecution> findAll();

    IPage<JobExecution> page(List<Long> groupIds, String jobName, Integer pageNum, Integer pageSize);
}
