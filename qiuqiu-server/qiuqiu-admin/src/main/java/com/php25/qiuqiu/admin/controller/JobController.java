package com.php25.qiuqiu.admin.controller;

import com.php25.qiuqiu.notify.MsgNotifyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author penghuiping
 * @date 2021/3/9 10:29
 */
@RestController
@RequestMapping("/job")
@RequiredArgsConstructor
@Log4j2
public class JobController {

    @Qualifier("WsMsgNotifyService")
    private final MsgNotifyService msgNotifyService;

    @Scheduled(cron = "0 * * * * ?")
    public void systemMsgBroadCast() {
        msgNotifyService.broadcastTextMsg("你好,这是测试系统通知");
    }
}