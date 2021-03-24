package com.php25.qiuqiu.job.service;

import com.php25.common.core.dto.DataGridPageDto;
import com.php25.qiuqiu.job.dto.JobCreateDto;
import com.php25.qiuqiu.job.dto.JobDto;
import com.php25.qiuqiu.job.dto.JobExecutionCreateDto;
import com.php25.qiuqiu.job.dto.JobExecutionDto;
import com.php25.qiuqiu.job.dto.JobExecutionUpdateDto;
import com.php25.qiuqiu.job.dto.JobLogCreateDto;
import com.php25.qiuqiu.job.dto.JobLogDto;
import com.php25.qiuqiu.job.dto.JobUpdateDto;

import java.util.List;

/**
 * @author penghuiping
 * @date 2021/3/13 17:40
 */
public interface JobService {

    /**
     * Job列表分页查询
     *
     * @param username 登入用户名
     * @param name     job名字搜索
     * @param pageNum  页码
     * @param pageSize 当前第几页
     * @return job分页列表
     */
    DataGridPageDto<JobDto> page(String username, String name, Integer pageNum, Integer pageSize);

    /**
     * 获取属于当前用户的所有job列表
     *
     * @param username 登入用户名
     * @return job列表
     */
    List<JobDto> findAll(String username);

    /**
     * 创建job
     *
     * @param username 登入用户名
     * @param job      任务
     * @return true:创建成功
     */
    Boolean create(String username, JobCreateDto job);

    /**
     * 更新job
     *
     * @param username 登入用户名
     * @param job      任务
     * @return true: 更新成功
     */
    Boolean update(String username, JobUpdateDto job);

    /**
     * 删除job
     *
     * @param username 登入用户名
     * @param jobId    任务id
     * @return true:删除功能
     */
    Boolean delete(String username, String jobId);


    /**
     * 任务日志分页查询
     *
     * @param username 登入用户名
     * @param jobName  任务名搜索
     * @param pageNum  页码
     * @param pageSize 每页大小
     * @return 任务日志分页结果
     */
    DataGridPageDto<JobLogDto> pageJobLog(String username, String jobName, Integer pageNum, Integer pageSize);


    /**
     * 新增任务日志
     *
     * @param jobLog 任务日志
     * @return true:新增成功
     */
    Boolean createJobLog(JobLogCreateDto jobLog);


    /**
     * 新增任务执行计划(注意此操作只会更新数据库,不会影响timer)
     *
     * @param username     登入用户名
     * @param jobExecution 任务执行计划
     * @return true: 新增成功
     */
    Boolean createJobExecution(String username, JobExecutionCreateDto jobExecution);

    /**
     * 更新任务执行计划(注意此操作只会更新数据库,不会影响timer)
     *
     * @param username     登入用户名
     * @param jobExecution 任务执行计划
     * @return true: 更新成功
     */
    Boolean updateJobExecution(String username, JobExecutionUpdateDto jobExecution);

    /**
     * 删除任务执行计划(注意此操作只会更新数据库,不会影响timer)
     *
     * @param username 登入用户名
     * @param id       执行任务id
     * @return true: 删除成功
     */
    Boolean deleteJobExecution(String username, String id);

    /**
     * 分页查询任务执行计划(注意此操作只会更新数据库,不会影响timer)
     *
     * @param username 登入用户名
     * @param jobName  任务名
     * @param pageNum  当前页码
     * @param pageSize 每页几条数据
     * @return 任务执行计划分页信息
     */
    DataGridPageDto<JobExecutionDto> pageJobExecution(String username, String jobName, Integer pageNum, Integer pageSize);


    /**
     * 通知timer,刷新任务状态,意思就是从数据库中读取对应jobId的任务的最新状态
     *
     * @param username    登入用户名
     * @param executionId 任务执行id
     * @return true:刷新成功
     */
    Boolean refresh(String username, String executionId);

    /**
     * 通知timer,刷新所有的任务状态
     *
     * @param username 登入用户名
     * @return true: 成功
     */
    Boolean refreshAll(String username);


    /**
     * 全量统计执行任务加载情况
     */
    void statisticLoadedJobExecutionInfo();

}
