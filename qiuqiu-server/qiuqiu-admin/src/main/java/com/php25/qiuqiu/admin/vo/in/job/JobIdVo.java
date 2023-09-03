package com.php25.qiuqiu.admin.vo.in.job;

import lombok.Getter;
import lombok.Setter;

import jakarta.validation.constraints.NotBlank;

/**
 * @author penghuiping
 * @date 2021/3/13 18:48
 */
@Setter
@Getter
public class JobIdVo {

    @NotBlank
    private String jobId;
}
