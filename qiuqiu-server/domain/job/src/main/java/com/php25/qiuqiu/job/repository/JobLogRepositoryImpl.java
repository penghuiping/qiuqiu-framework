package com.php25.qiuqiu.job.repository;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.php25.qiuqiu.job.entity.JobLog;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author penghuiping
 * @date 2021/3/15 14:05
 */
@Repository
public class JobLogRepositoryImpl  implements JobLogRepository {

    @Override
    public Boolean save(JobLog jobLog) {
        return null;
    }

    @Override
    public IPage<JobLog> page(List<Long> groupIds, String jobName, Integer pageNum, Integer pageSize) {
        return null;
    }
}
