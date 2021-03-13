package com.php25.qiuqiu.admin.vo.in.job;

import lombok.Getter;
import lombok.Setter;

/**
 * @author penghuiping
 * @date 2021/3/13 18:43
 */
@Setter
@Getter
public class JobPageVo {
    /**
     * 任务名,用于搜索
     */
    private String jobName;

    /**
     * 分页页码
     */
    private Integer pageNum;

    /**
     * 每页几条记录
     */
    private Integer pageSize;
}
