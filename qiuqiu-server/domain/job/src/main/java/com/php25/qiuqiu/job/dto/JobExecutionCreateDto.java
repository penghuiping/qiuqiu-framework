package com.php25.qiuqiu.job.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * @author penghuiping
 * @date 2021/3/16 09:05
 */
@Setter
@Getter
public class JobExecutionCreateDto {
    /**
     * 任务的cron表达式
     */
    private String cron;

    /**
     * 任务名
     */
    private String jobId;

    /**
     * 执行入参 json格式
     */
    private String params;
}
