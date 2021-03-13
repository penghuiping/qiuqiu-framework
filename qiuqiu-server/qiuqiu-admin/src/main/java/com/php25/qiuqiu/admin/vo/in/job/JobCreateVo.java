package com.php25.qiuqiu.admin.vo.in.job;

import lombok.Getter;
import lombok.Setter;

/**
 * @author penghuiping
 * @date 2021/3/13 18:47
 */
@Setter
@Getter
public class JobCreateVo {
    /*
     * 任务对应的java执行代码
     */
    private String className;

    /**
     * job名字可以用于搜索
     */
    private String name;

    /**
     * job描述
     */
    private String description;

    /**
     * 任务的cron表达式
     */
    private String cron;
}
