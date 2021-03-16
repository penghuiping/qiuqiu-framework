package com.php25.qiuqiu.admin.job;

import com.php25.common.core.mess.SpringContextHolder;
import com.php25.qiuqiu.job.dto.BaseRunnable;
import com.php25.qiuqiu.notify.MsgNotifyService;

/**
 * @author penghuiping
 * @date 2021/3/16 14:40
 */
public class NotifyTextJob extends BaseRunnable {

    private final MsgNotifyService msgNotifyService;

    public NotifyTextJob(String jobId, String jobName) {
        super(jobId, jobName);
        this.msgNotifyService = SpringContextHolder.getBean0(MsgNotifyService.class);
    }

    @Override
    public void run0() {
        this.msgNotifyService.broadcastTextMsg("普通通知");
    }
}
