package com.php25.qiuqiu.job.entity;

import lombok.Getter;
import lombok.Setter;

/**
 * @author penghuiping
 * @date 2020/8/24 13:52
 */
@Getter
@Setter
public class JobModel {

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
     * 用户组id
     */
    private Long groupId;
}
