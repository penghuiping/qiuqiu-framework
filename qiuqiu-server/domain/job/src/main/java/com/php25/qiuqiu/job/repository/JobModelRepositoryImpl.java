package com.php25.qiuqiu.job.repository;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import com.php25.common.core.util.StringUtil;
import com.php25.qiuqiu.job.dao.JobModelDao;
import com.php25.qiuqiu.job.dao.po.JobModelPo;
import com.php25.qiuqiu.job.entity.JobModel;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author penghuiping
 * @date 2020/8/24 13:56
 */
@Repository
@RequiredArgsConstructor
public class JobModelRepositoryImpl implements JobModelRepository {
    private final JobModelDao jobModelDao;

    @Override
    public Boolean save(JobModel jobModel) {
        if (null == jobModel.getId()) {
            //新增
            JobModelPo jobModelPo = new JobModelPo();
            BeanUtils.copyProperties(jobModel, jobModelPo);
            return jobModelDao.insert(jobModelPo) > 0;
        } else {
            //更新
            JobModelPo jobModelPo = new JobModelPo();
            BeanUtils.copyProperties(jobModel, jobModelPo);
            return jobModelDao.updateById(jobModelPo) > 0;
        }
    }

    @Override
    public Optional<JobModel> findById(String id) {
        JobModelPo jobModelPo = jobModelDao.selectById(id);
        if(null == jobModelPo) {
            return Optional.empty();
        }
        JobModel jobModel = new JobModel();
        BeanUtils.copyProperties(jobModelPo,jobModel);
        return Optional.of(jobModel);
    }

    @Override
    public Boolean deleteById(String id) {
        return jobModelDao.deleteById(id)>0;
    }

    @Override
    public IPage<JobModel> page(String name, Integer pageNum, Integer pageSize) {
        IPage<JobModelPo> iPage = jobModelDao.selectPage(new Page<>(pageNum, pageSize)
                , Wrappers.<JobModelPo>lambdaQuery()
                        .eq(StringUtil.isNotBlank(name),JobModelPo::getName, name));
        IPage<JobModel> result = new Page<>();
        List<JobModel> jobModels = iPage.getRecords().stream().map(jobModelPo -> {
            JobModel jobModel = new JobModel();
            BeanUtils.copyProperties(jobModelPo, jobModel);
            return jobModel;
        }).collect(Collectors.toList());
        result.setRecords(jobModels);
        result.setTotal(iPage.getTotal());
        result.setCurrent(pageNum);
        result.setSize(pageSize);
        return result;
    }

    @Override
    public List<JobModel> findAll(List<Long> groupIds) {
        List<JobModelPo> jobModelPos = jobModelDao.selectList(Wrappers.<JobModelPo>lambdaQuery()
                .in(JobModelPo::getGroupId,groupIds));
        if(null != jobModelPos && !jobModelPos.isEmpty()) {
            return jobModelPos.stream().map(jobModelPo -> {
               JobModel jobModel = new JobModel();
               BeanUtils.copyProperties(jobModelPo,jobModel);
               return jobModel;
            }).collect(Collectors.toList());
        }
        return Lists.newArrayList();
    }
}
