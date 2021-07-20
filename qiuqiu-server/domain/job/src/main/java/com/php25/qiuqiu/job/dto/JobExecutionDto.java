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
     * job执行入参,格式为json字符串
     */
    private String params;

    /**
     * 0:无效 1:有效
     */
    private Boolean enable;

    /**
     * 已被多少定时器加载
     */
    private Integer timerLoadedNumber;
}
