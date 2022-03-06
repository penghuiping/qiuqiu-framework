package com.php25.qiuqiu.job.repository;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.php25.qiuqiu.job.dao.JobExecutionDao;
import com.php25.qiuqiu.job.dao.po.JobExecutionPo;
import com.php25.qiuqiu.job.entity.JobExecution;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * @author penghuiping
 * @date 2021/3/15 20:37
 */
@Repository
@RequiredArgsConstructor
public class JobExecutionRepositoryImpl implements JobExecutionRepository {

    private final JobExecutionDao jobExecutionDao;

    @Override
    public Optional<JobExecution> findByJobId(String jobId) {
        JobExecutionPo jobExecutionPo = jobExecutionDao.selectOne(Wrappers.<JobExecutionPo>lambdaQuery().eq(JobExecutionPo::getJobId, jobId));
        JobExecution jobExecution = new JobExecution();
        BeanUtils.copyProperties(jobExecutionPo, jobExecution);
        return Optional.of(jobExecution);
    }

    @Override
    public Boolean resetTimerLoadedNumber() {
        return jobExecutionDao.update(null,Wrappers.<JobExecutionPo>lambdaUpdate().set(JobExecutionPo::getTimerLoadedNumber,0))>0;
    }

    @Override
    public Boolean save(JobExecution jobExecution) {
        return null;
    }

    @Override
    public Optional<JobExecution> findById(String id) {
        return Optional.empty();
    }

    @Override
    public Boolean deleteById(String id) {
        return null;
    }

    @Override
    public List<JobExecution> findAll() {
        return null;
    }

    @Override
    public IPage<JobExecution> page(List<Long> groupIds, String jobName, Integer pageNum, Integer pageSize) {
        return null;
    }
}
