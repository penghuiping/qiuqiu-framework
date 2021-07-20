package com.php25.qiuqiu.admin.vo.out.job;

import lombok.Getter;
import lombok.Setter;

/**
 * @author penghuiping
 * @date 2021/3/16 10:29
 */
@Setter
@Getter
public class JobExecutionVo {

    /**
     * 任务执行id
     */
    private String id;

    /**
     * 任务的cron表达式
     */
    private String cron;

    /**
     * job执行入参,格式为json字符串
     */
    private String params;

    /**
     * 任务id
     */
    private String jobId;

    /**
     * 任务名
     */
    private String jobName;

    /**
     * 已被多少定时器加载
     */
    private Integer timerLoadedNumber;

    /**
     * 0:无效 1:有效
     */
    private Boolean enable;
}
