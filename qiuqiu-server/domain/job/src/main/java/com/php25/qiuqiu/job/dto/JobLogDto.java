package com.php25.qiuqiu.job.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * @author penghuiping
 * @date 2021/3/15 14:07
 */
@Setter
@Getter
public class JobLogDto {

    /**
     * 日志自增id
     */
    private Long id;

    /**
     * 执行id
     */
    private String executionId;

    /**
     * 任务id
     */
    private String jobId;

    /**
     * 任务名
     */
    private String jobName;

    /**
     * 任务执行时间
     */
    private LocalDateTime executeTime;

    /**
     * 任务执行 0:失败 1:成功
     */
    private Integer resultCode;

    /**
     * 结果描述
     */
    private String resultMessage;
}
