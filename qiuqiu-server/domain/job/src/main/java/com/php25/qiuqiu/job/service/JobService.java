package com.php25.qiuqiu.job.service;

import com.php25.common.core.dto.DataGridPageDto;
import com.php25.qiuqiu.job.dto.JobCreateDto;
import com.php25.qiuqiu.job.dto.JobDto;
import com.php25.qiuqiu.job.dto.JobUpdateDto;

/**
 * @author penghuiping
 * @date 2021/3/13 17:40
 */
public interface JobService {

    /**
     * Job列表分页查询
     *
     * @param name     job名字搜索
     * @param pageNum  页码
     * @param pageSize 当前第几页
     * @return job分页列表
     */
    DataGridPageDto<JobDto> page(String name, Integer pageNum, Integer pageSize);

    /**
     * 创建job(注意此操作只会更新数据库,不会影响timer)
     *
     * @param job 任务
     * @return true:创建成功
     */
    Boolean create(JobCreateDto job);

    /**
     * 更新job(注意此操作只会更新数据库,不会影响timer)
     *
     * @param job 任务
     * @return true: 更新成功
     */
    Boolean update(JobUpdateDto job);

    /**
     * 删除job(注意此操作只会更新数据库,不会影响timer)
     *
     * @param jobId 任务id
     * @return true:删除功能
     */
    Boolean delete(String jobId);


    /**
     * 通知timer,刷新任务状态,意思就是从数据库中读取对应jobId的任务的最新状态
     *
     * @param jobId 任务id
     * @return true:刷新成功
     */
    Boolean refresh(String jobId);

}
