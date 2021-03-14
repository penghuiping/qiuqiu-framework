package com.php25.qiuqiu.job.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * @author penghuiping
 * @date 2021/3/13 17:54
 */
@Setter
@Getter
public class JobUpdateDto {
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
     * cron表达是
     */
    private String cron;

    /**
     * job对应的类名
     */
    private String className;

    /**
     * 0:无效 1:有效
     */
    private Boolean enable;
}
