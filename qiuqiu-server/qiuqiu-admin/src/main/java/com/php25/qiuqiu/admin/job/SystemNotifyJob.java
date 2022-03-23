package com.php25.qiuqiu.admin.job;

import com.php25.common.core.mess.SpringContextHolder;
import com.php25.qiuqiu.job.dto.BaseRunnable;
import com.php25.qiuqiu.notify.service.MsgNotifyService;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;

/**
 * @author penghuiping
 * @date 2021/3/13 20:34
 */
@Slf4j
public class SystemNotifyJob extends BaseRunnable {

    private final MsgNotifyService msgNotifyService;

    public SystemNotifyJob(String jobId,String jobName,String jobExecutionId) {
        super(jobId,jobName,jobExecutionId);
        this.msgNotifyService = SpringContextHolder.getBean0(MsgNotifyService.class);
    }

    @Override
    public void run0(String params) {
        msgNotifyService.broadcastTextMsg(params);
    }
}
