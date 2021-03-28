package com.php25.qiuqiu.admin.vo.in.job;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

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
    @NotBlank
    private String className;

    /**
     * job名字可以用于搜索
     */
    @NotBlank
    private String name;

    /**
     * job描述
     */
    @NotBlank
    private String description;

    /**
     * 任务的cron表达式
     */
    @NotBlank
    private String cron;
}
