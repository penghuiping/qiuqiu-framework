package com.php25.qiuqiu.admin.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.php25.common.core.util.JsonUtil;
import com.php25.common.core.util.StringUtil;
import com.php25.qiuqiu.monitor.dto.MetricsDto;
import com.php25.qiuqiu.monitor.service.DictionaryService;
import com.php25.qiuqiu.monitor.service.SystemMonitorService;
import com.php25.qiuqiu.notify.MsgNotifyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;

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

    private final DictionaryService dictionaryService;

    private final SystemMonitorService systemMonitorService;


    @Scheduled(cron = "0/5 * * * * ?")
    public void systemMsgBroadCast() {
        String notifyEnable = dictionaryService.get("sys_notify_enable");
        if(!StringUtil.isBlank(notifyEnable) && "true".equals(notifyEnable)) {
            msgNotifyService.broadcastTextMsg("你好,这是测试系统通知");
        }
    }

    @Scheduled(cron = "0/10 * * * * ?")
    public void systemStatusMonitor() {
        String json = dictionaryService.get("apps_ip_port");
        List<String> ipPorts = JsonUtil.fromJson(json, new TypeReference<List<String>>() {
        });
        for(String ipPort:ipPorts) {
            String ip = ipPort.split(":")[0];
            int port = Integer.parseInt(ipPort.split(":")[1]);
            List<MetricsDto> res = systemMonitorService.getMetricsFromActuator(ip,port);
            log.info("系统metrics信息:{}",JsonUtil.toJson(res));
        }
    }
}
