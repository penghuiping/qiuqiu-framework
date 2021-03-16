package com.php25.qiuqiu.job.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * @author penghuiping
 * @date 2021/3/13 17:41
 */
@Setter
@Getter
public class JobCreateDto {
    /**
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
}
