package com.php25.qiuqiu.job.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

/**
 * @author penghuiping
 * @date 2021/3/24 10:26
 */
@Setter
@Getter
public class JobExecutionStatisticResDto {

    /**
     * 执行计划统计情况,key:执行计划id，value:对应执行计划加载数
     */
    private Map<String, Integer> entries;
}
