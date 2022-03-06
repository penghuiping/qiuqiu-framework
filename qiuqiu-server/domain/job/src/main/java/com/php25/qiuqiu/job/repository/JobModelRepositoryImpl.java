package com.php25.qiuqiu.job.repository;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.php25.qiuqiu.job.dao.JobModelDao;
import com.php25.qiuqiu.job.dao.po.JobModelPo;
import com.php25.qiuqiu.job.entity.JobModel;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

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
        return Optional.empty();
    }

    @Override
    public Boolean deleteById(String id) {
        return null;
    }

    @Override
    public IPage<JobModel> page(List<Long> groupIds, String name, Integer pageNum, Integer pageSize) {
        return null;
    }
}
