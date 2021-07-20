package com.php25.qiuqiu.job.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * @author penghuiping
 * @date 2021/3/16 09:08
 */
@Setter
@Getter
public class JobExecutionUpdateDto {
    /**
     * 任务执行id
     */
    private String id;

    /**
     * 任务的cron表达式
     */
    private String cron;

    /**
     * 任务名
     */
    private String jobId;

    /**
     * job执行入参,格式为json字符串
     */
    private String params;

    /**
     * 0:无效 1:有效
     */
    private Boolean enable;
}
