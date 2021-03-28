package com.php25.qiuqiu.admin.vo.in.job;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

/**
 * @author penghuiping
 * @date 2021/3/15 14:24
 */
@Setter
@Getter
public class JobLogPageVo {

    /**
     * 任务id,用于搜索
     */
    private String jobId;

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
