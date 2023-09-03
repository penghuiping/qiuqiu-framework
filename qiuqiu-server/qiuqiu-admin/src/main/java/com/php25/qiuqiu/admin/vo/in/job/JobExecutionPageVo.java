package com.php25.qiuqiu.admin.vo.in.job;

import lombok.Getter;
import lombok.Setter;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

/**
 * @author penghuiping
 * @date 2021/3/16 10:26
 */
@Setter
@Getter
public class JobExecutionPageVo {
    /**
     * 任务名,用于搜索
     */
    private String jobName;

    /**
     * 分页页码
     */
    @NotNull
    @Positive
    private Integer pageNum;

    /**
     * 每页几条记录
     */
    @NotNull
    @Positive
    @Max(100)
    private Integer pageSize;
}
