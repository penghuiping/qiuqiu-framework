package com.php25.qiuqiu.job.repository;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.php25.qiuqiu.job.entity.JobModel;

import java.util.List;
import java.util.Optional;

/**
 * @author penghuiping
 * @date 2021/3/13 12:02
 */
public interface JobModelRepository {

    /**
     * 保存与更新定时任务
     *
     * @param jobModel 定时任务
     * @return true:成功
     */
    Boolean save(JobModel jobModel);

    /**
     * 根据id获取定时任务
     *
     * @param id 唯一标识
     * @return 定时任务
     */
    Optional<JobModel> findById(String id);

    /**
     * 根据id删除定时任务
     *
     * @param id 唯一标识
     * @return true:成功
     */
    Boolean deleteById(String id);

    /**
     * 获取分页任务数据
     *
     * @param groupIds 组id
     * @param name     任务名字
     * @param pageNum  页码
     * @param pageSize 每页记录数
     * @return 分页数据
     */
    IPage<JobModel> page(List<Long> groupIds, String name, Integer pageNum, Integer pageSize);

    /**
     * 获取所有的任务
     * @return
     */
    List<JobModel> findAll(List<Long> groupIds);
}
