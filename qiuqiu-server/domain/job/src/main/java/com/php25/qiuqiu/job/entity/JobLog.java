package com.php25.qiuqiu.job.entity;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * 任务执行日志
 *
 * @author penghuiping
 * @date 2021/3/15 13:56
 */
@Setter
@Getter
public class JobLog  {
    /**
     * 日志自增id
     */
    private Long id;

    /**
     * 任务id
     */
    private String jobId;

    /**
     * 任务名字
     */
    private String jobName;

    /**
     * 任务执行时间
     */
    private LocalDateTime executeTime;

    /**
     * 任务执行 0:失败 1:成功
     */
    private Integer resultCode;

    /**
     * 结果描述
     */
    private String resultMessage;

    /**
     * 用户组id
     */
    private Long groupId;
}
