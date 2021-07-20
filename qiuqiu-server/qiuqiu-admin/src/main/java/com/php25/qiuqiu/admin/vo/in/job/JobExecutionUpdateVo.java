package com.php25.qiuqiu.admin.vo.in.job;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author penghuiping
 * @date 2021/3/16 10:35
 */
@Setter
@Getter
public class JobExecutionUpdateVo {

    /**
     * 任务执行id
     */
    @NotBlank
    private String id;

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

    /**
     * 0:无效 1:有效
     */
    @NotNull
    private Boolean enable;
}
