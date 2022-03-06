package com.php25.qiuqiu.job.dao.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;

/**
 * 任务执行日志
 *
 * @author penghuiping
 * @date 2021/3/15 13:56
 */
@Setter
@Getter
@TableName("t_timer_job_log")
public class JobLogPo {
    /**
     * 日志自增id
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 任务id
     */
    @TableField("job_id")
    private String jobId;

    /**
     * 任务名字
     */
    @TableField("job_name")
    private String jobName;

    /**
     * 任务执行时间
     */
    @TableField("execute_time")
    private LocalDateTime executeTime;

    /**
     * 任务执行 0:失败 1:成功
     */
    @TableField("result_code")
    private Integer resultCode;

    /**
     * 结果描述
     */
    @TableField("result_message")
    private String resultMessage;

    /**
     * 用户组id
     */
    @TableField("group_id")
    private Long groupId;
}
