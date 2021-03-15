package com.php25.qiuqiu.job.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * @author penghuiping
 * @date 2021/3/15 14:10
 */
@Setter
@Getter
public class JobLogCreateDto {
    /**
     * 任务id
     */
    private String jobId;

    /**
     * 任务执行时间
     */
    private Date executeTime;

    /**
     * 任务执行 0:失败 1:成功
     */
    private Integer resultCode;

    /**
     * 结果描述
     */
    private String resultMessage;
}
