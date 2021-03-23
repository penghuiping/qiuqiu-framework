package com.php25.qiuqiu.admin.vo.in.job;

import lombok.Getter;
import lombok.Setter;

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
     * 0:无效 1:有效
     */
    private Boolean enable;
}