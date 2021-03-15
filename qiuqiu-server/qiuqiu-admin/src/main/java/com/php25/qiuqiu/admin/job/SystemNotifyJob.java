package com.php25.qiuqiu.admin.job;

import com.php25.common.core.mess.SpringContextHolder;
import com.php25.qiuqiu.job.dto.BaseRunnable;
import com.php25.qiuqiu.notify.MsgNotifyService;
import lombok.extern.log4j.Log4j2;

/**
 * @author penghuiping
 * @date 2021/3/13 20:34
 */
@Log4j2
public class SystemNotifyJob extends BaseRunnable {

    private final MsgNotifyService msgNotifyService;

    public SystemNotifyJob(String jobId) {
        super(jobId);
        this.msgNotifyService = SpringContextHolder.getBean0(MsgNotifyService.class);
    }

    @Override
    public void run0() {
        msgNotifyService.broadcastTextMsg("你好,这是测试系统通知");
    }
}
