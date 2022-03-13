package com.php25.qiuqiu.job.repository;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import com.php25.common.core.util.StringUtil;
import com.php25.qiuqiu.job.dao.JobExecutionDao;
import com.php25.qiuqiu.job.dao.po.JobExecutionPo;
import com.php25.qiuqiu.job.entity.JobExecution;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
        JobExecutionPo jobExecutionPo = new JobExecutionPo();
        BeanUtils.copyProperties(jobExecution,jobExecutionPo);
        if(null == jobExecution.getId()) {
            //新增
            return jobExecutionDao.insert(jobExecutionPo)>0;
        }else {
            //更新
            return jobExecutionDao.updateById(jobExecutionPo)>0;
        }
    }

    @Override
    public Optional<JobExecution> findById(String id) {
        JobExecutionPo jobExecutionPo = jobExecutionDao.selectById(id);
        if(null == jobExecutionPo) {
            return Optional.empty();
        }
        JobExecution jobExecution =new JobExecution();
        BeanUtils.copyProperties(jobExecutionPo,jobExecution);
        return Optional.of(jobExecution);
    }

    @Override
    public Boolean deleteById(String id) {
        return jobExecutionDao.deleteById(id)>0;
    }

    @Override
    public List<JobExecution> findAll() {
        List<JobExecutionPo> jobExecutionPos = jobExecutionDao.selectList(Wrappers.<JobExecutionPo>lambdaQuery().select());
        if(null == jobExecutionPos || jobExecutionPos.isEmpty()) {
            return Lists.newArrayList();
        }

        return jobExecutionPos.stream().map(jobExecutionPo -> {
            JobExecution jobExecution = new JobExecution();
            BeanUtils.copyProperties(jobExecutionPo,jobExecution);
            return jobExecution;
        }).collect(Collectors.toList());
    }

    @Override
    public IPage<JobExecution> page(List<Long> groupIds, String jobName, Integer pageNum, Integer pageSize) {
        IPage<JobExecutionPo> iPage = jobExecutionDao.selectPage(new Page<>(pageNum, pageSize)
                , Wrappers.<JobExecutionPo>lambdaQuery()
                        .eq(StringUtil.isNotBlank(jobName),JobExecutionPo::getJobName, jobName)
                        .in(null != groupIds && !groupIds.isEmpty(),JobExecutionPo::getGroupId, groupIds));
        IPage<JobExecution> result = new Page<>();
        List<JobExecution> jobExecutions = iPage.getRecords().stream().map(jobExecutionPo -> {
            JobExecution jobExecution = new JobExecution();
            BeanUtils.copyProperties(jobExecutionPo, jobExecution);
            return jobExecution;
        }).collect(Collectors.toList());
        result.setRecords(jobExecutions);
        result.setTotal(iPage.getTotal());
        result.setCurrent(pageNum);
        result.setSize(pageSize);
        return result;
    }
}
