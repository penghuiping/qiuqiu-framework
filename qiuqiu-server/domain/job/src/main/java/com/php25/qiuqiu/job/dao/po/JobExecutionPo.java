package com.php25.qiuqiu.job.dao.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

/**
 * @author penghuiping
 * @date 2021/3/15 20:23
 */
@Setter
@Getter
@TableName("t_timer_job_execution")
public class JobExecutionPo {

    /**
     * 任务执行id
     */
    @TableId(type = IdType.AUTO)
    private String id;

    /**
     * 任务的cron表达式
     */
    @TableField
    private String cron;

    /**
     * 用户组id
     */
    @TableField("group_id")
    private Long groupId;

    /**
     * 任务名
     */
    @TableField("job_id")
    private String jobId;

    /**
     * 任务名字
     */
    @TableField("job_name")
    private String jobName;

    /**
     * job执行入参,格式为json字符串
     */
    @TableField("params")
    private String params;

    /**
     * 0:无效 1:有效
     */
    @TableField
    private Boolean enable;

    /**
     * 已被多少定时器加载
     */
    @TableField("timer_loaded_number")
    private Integer timerLoadedNumber;
}
