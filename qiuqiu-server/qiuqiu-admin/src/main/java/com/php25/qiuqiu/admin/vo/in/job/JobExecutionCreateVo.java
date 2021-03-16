package com.php25.qiuqiu.admin.vo.in.job;

import lombok.Getter;
import lombok.Setter;

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
    private String cron;

    /**
     * 任务名
     */
    private String jobId;
}
