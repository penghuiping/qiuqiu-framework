package com.php25.qiuqiu.job.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * @author penghuiping
 * @date 2021/3/24 10:24
 */
@Setter
@Getter
public class JobExecutionStatisticReqDto {

    /**
     * 执行计划请求发起者所在队列
     */
    private String queue;

    /**
     * 执行计划请求发起者所在组
     */
    private String group;

}
