package com.php25.qiuqiu.job.repository;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.php25.qiuqiu.job.entity.JobLog;

import java.util.List;

/**
 * @author penghuiping
 * @date 2021/3/15 14:04
 */
public interface JobLogRepository {

    /**
     * 保存与更新任务日志
     *
     * @param jobLog 任务日志
     * @return true:成功
     */
    Boolean save(JobLog jobLog);

    /**
     * 分页
     *
     * @param jobName  任务名
     * @param pageNum  页码
     * @param pageSize 每页大小
     * @return 分页数据
     */
    IPage<JobLog> page(String jobName, Integer pageNum, Integer pageSize);
}
