package com.php25.qiuqiu.admin.job;

import com.php25.qiuqiu.notify.MsgNotifyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * @author penghuiping
 * @date 2021/3/13 20:34
 */
@Component
@RequiredArgsConstructor
public class SystemNotifyJob implements Runnable {

    private final MsgNotifyService msgNotifyService;

    @Override
    public void run() {
        msgNotifyService.broadcastTextMsg("你好,这是测试系统通知");
    }
}
