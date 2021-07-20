package com.php25.common.timer;

import com.php25.common.timer.entity.TimerInnerLog;

/**
 * @author penghuiping
 * @date 2021/7/20 14:46
 */
public interface TimerInnerLogManager {

    /**
     * 使用分布式锁 同步执行任务
     * 在分布式环境中 多个相同任务只有一个任务获取分布式锁完成任务执行
     *
     * @param task 任务
     */
    void synchronizedExecutionJob(Job task);

    /**
     * 数据库中创建任务执行日志,如果已经存在就不能重复插入
     *
     * @param jobExecutionLog 任务执行日志
     */
    void create(TimerInnerLog jobExecutionLog);

    /**
     * 更新数据库的任务执行日志
     *
     * @param jobExecutionLog 任务执行日志
     */
    void update(TimerInnerLog jobExecutionLog);
}
