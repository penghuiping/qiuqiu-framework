package com.php25.qiuqiu.job.repository;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.php25.common.core.util.StringUtil;
import com.php25.common.core.util.TimeUtil;
import com.php25.qiuqiu.job.dao.JobLogDao;
import com.php25.qiuqiu.job.dao.po.JobLogPo;
import com.php25.qiuqiu.job.entity.JobLog;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author penghuiping
 * @date 2021/3/15 14:05
 */
@RequiredArgsConstructor
@Repository
public class JobLogRepositoryImpl implements JobLogRepository {

    private final JobLogDao jobLogDao;

    @Override
    public Boolean save(JobLog jobLog) {
        JobLogPo jobLogPo = new JobLogPo();
        BeanUtils.copyProperties(jobLog, jobLogPo);
        if (null == jobLog.getId()) {
            //新增
            return jobLogDao.insert(jobLogPo) > 0;
        } else {
            //更新
            return jobLogDao.updateById(jobLogPo) > 0;
        }
    }

    @Override
    public IPage<JobLog> page(List<Long> groupIds, String jobName, Integer pageNum, Integer pageSize) {
        IPage<JobLogPo> iPage = jobLogDao.selectPage(new Page<>(pageNum, pageSize)
                , Wrappers.<JobLogPo>lambdaQuery()
                        .eq(StringUtil.isNotBlank(jobName),JobLogPo::getJobName, jobName)
                        .in(null != groupIds && !groupIds.isEmpty(),JobLogPo::getGroupId, groupIds));
        IPage<JobLog> result = new Page<>();
        List<JobLog> jobLogs = iPage.getRecords().stream().map(jobLogPo -> {
            JobLog auditLog = new JobLog();
            BeanUtils.copyProperties(jobLogPo, auditLog);
            auditLog.setExecuteTime(TimeUtil.toLocalDateTime(jobLogPo.getExecuteTime()));
            return auditLog;
        }).collect(Collectors.toList());
        result.setRecords(jobLogs);
        result.setTotal(iPage.getTotal());
        result.setCurrent(pageNum);
        result.setSize(pageSize);
        return result;
    }
}
