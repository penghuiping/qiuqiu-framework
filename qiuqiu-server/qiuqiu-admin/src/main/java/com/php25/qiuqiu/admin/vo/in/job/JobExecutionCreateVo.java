package com.php25.qiuqiu.admin.vo.in.job;

import lombok.Getter;
import lombok.Setter;

import jakarta.validation.constraints.NotBlank;

/**
 * @author penghuiping
 * @date 2021/3/16 10:34
 */
@Setter
@Getter
public class JobExecutionCreateVo {
    /**
     * 任务的cron表达式
     */
    @NotBlank
    private String cron;

    /**
     * 任务名
     */
    @NotBlank
    private String jobId;

    /**
     * job执行入参,格式为json字符串
     */
    private String params;
}
