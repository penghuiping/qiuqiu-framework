package com.php25.qiuqiu.job.dto;

import com.php25.common.core.mess.SpringContextHolder;
import com.php25.qiuqiu.job.service.JobService;
import lombok.extern.log4j.Log4j2;

import java.time.LocalDateTime;

/**
 * @author penghuiping
 * @date 2021/3/15 14:46
 */
@Log4j2
public abstract class BaseRunnable implements Runnable {

    private final String jobName;

    private final String jobId;

    private final String jobExecutionId;

    private final JobService jobService;

    public BaseRunnable(String jobId,String jobName,String jobExecutionId) {
        this.jobId = jobId;
        this.jobName = jobName;
        this.jobExecutionId = jobExecutionId;
        this.jobService = SpringContextHolder.getBean0(JobService.class);
    }

    @Override
    public void run() {
        LocalDateTime now = LocalDateTime.now();
        try {
            JobExecutionDto jobExecutionDto = this.jobService.findOne(jobExecutionId);
            this.run0(jobExecutionDto.getParams());
            JobLogCreateDto jobLogCreateDto = new JobLogCreateDto();
            jobLogCreateDto.setExecutionId(jobExecutionId);
            jobLogCreateDto.setJobId(this.jobId);
            jobLogCreateDto.setJobName(this.jobName);
            jobLogCreateDto.setExecuteTime(now);
            jobLogCreateDto.setResultCode(1);
            jobLogCreateDto.setResultMessage("成功执行");
            jobService.createJobLog(jobLogCreateDto);
        } catch (Exception e) {
            log.error("系统通知出错!", e);
            JobLogCreateDto jobLogCreateDto = new JobLogCreateDto();
            jobLogCreateDto.setExecutionId(jobExecutionId);
            jobLogCreateDto.setJobId(this.jobId);
            jobLogCreateDto.setJobName(this.jobName);
            jobLogCreateDto.setExecuteTime(now);
            jobLogCreateDto.setResultCode(0);
            jobLogCreateDto.setResultMessage(e.getMessage());
            jobService.createJobLog(jobLogCreateDto);
        }
    }

    /**
     * 所有的自定义任务，都需要实现此方法，并且逻辑都写在此方法上
     *
     * @param params 任务参数
     */
    abstract public void run0(String params);
}
