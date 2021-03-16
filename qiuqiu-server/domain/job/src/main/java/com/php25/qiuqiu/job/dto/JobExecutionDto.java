package com.php25.qiuqiu.job.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * @author penghuiping
 * @date 2021/3/16 09:07
 */
@Setter
@Getter
public class JobExecutionDto {

    /**
     * 任务执行id
     */
    private String id;

    /**
     * 任务的cron表达式
     */
    private String cron;

    /**
     * 任务id
     */
    private String jobId;

    /**
     * 任务名
     */
    private String jobName;

    /**
     * 0:无效 1:有效
     */
    private Boolean enable;

    /**
     * 0: 没载入定时器
     * 1: 已载入定时器
     */
    private Integer status;
}
