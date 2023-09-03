package com.php25.qiuqiu.admin.vo.in.job;

import lombok.Getter;
import lombok.Setter;

import jakarta.validation.constraints.NotBlank;

/**
 * @author penghuiping
 * @date 2021/3/16 10:39
 */
@Setter
@Getter
public class JobExecutionIdVo {

    /**
     * 计划任务id
     */
    @NotBlank
    private String id;
}
