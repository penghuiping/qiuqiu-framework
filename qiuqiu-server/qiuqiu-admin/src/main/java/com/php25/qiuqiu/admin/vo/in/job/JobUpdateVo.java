package com.php25.qiuqiu.admin.vo.in.job;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author penghuiping
 * @date 2021/3/13 18:48
 */
@Setter
@Getter
public class JobUpdateVo {

    /**
     * 任务id
     */
    @NotBlank
    private String id;

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
     * cron表达是
     */
    @NotBlank
    private String cron;

    /**
     * job对应的类名
     */
    @NotBlank
    private String className;

    /**
     * 0:无效 1:有效
     */
    @NotNull
    private Boolean enable;
}
