package com.php25.qiuqiu.job.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * @author penghuiping
 * @date 2021/3/13 17:40
 */
@Setter
@Getter
public class JobDto {
    /**
     * 任务id
     */
    private String id;

    /**
     * job名字可以用于搜索
     */
    private String name;

    /**
     * job描述
     */
    private String description;

    /**
     * 任务对应的java执行代码
     */
    private String className;

    /**
     * 任务的cron表达式
     */
    private String cron;

    /**
     * 0:无效 1:有效
     */
    private Integer enable;

}
