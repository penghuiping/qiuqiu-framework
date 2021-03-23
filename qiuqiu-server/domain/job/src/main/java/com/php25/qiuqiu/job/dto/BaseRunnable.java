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

    private final JobService jobService;

    public BaseRunnable(String jobId,String jobName) {
        this.jobId = jobId;
        this.jobName = jobName;
        this.jobService = SpringContextHolder.getBean0(JobService.class);
    }

    @Override
    public void run() {
        LocalDateTime now = LocalDateTime.now();
        try {
            this.run0();
            JobLogCreateDto jobLogCreateDto = new JobLogCreateDto();
            jobLogCreateDto.setJobId(this.jobId);
            jobLogCreateDto.setJobName(this.jobName);
            jobLogCreateDto.setExecuteTime(now);
            jobLogCreateDto.setResultCode(1);
            jobLogCreateDto.setResultMessage("成功执行");
            jobService.createJobLog(jobLogCreateDto);
        } catch (Exception e) {
            log.error("系统通知出错!", e);
            JobLogCreateDto jobLogCreateDto = new JobLogCreateDto();
            jobLogCreateDto.setJobId(this.jobId);
            jobLogCreateDto.setJobName(this.jobName);
            jobLogCreateDto.setExecuteTime(now);
            jobLogCreateDto.setResultCode(0);
            jobLogCreateDto.setResultMessage(e.getMessage());
            jobService.createJobLog(jobLogCreateDto);
        }
    }

    abstract public void run0();
}