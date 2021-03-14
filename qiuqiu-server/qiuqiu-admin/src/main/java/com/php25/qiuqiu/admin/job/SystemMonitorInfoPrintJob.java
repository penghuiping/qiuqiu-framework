package com.php25.qiuqiu.admin.job;

import com.fasterxml.jackson.core.type.TypeReference;
import com.php25.common.core.util.JsonUtil;
import com.php25.qiuqiu.monitor.dto.MetricsDto;
import com.php25.qiuqiu.monitor.service.DictionaryService;
import com.php25.qiuqiu.monitor.service.SystemMonitorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author penghuiping
 * @date 2021/3/13 19:35
 */
@Log4j2
@Component
@RequiredArgsConstructor
public class SystemMonitorInfoPrintJob implements Runnable {

    private final DictionaryService dictionaryService;
    private final SystemMonitorService systemMonitorService;

    @Override
    public void run() {
        try {
            String json = dictionaryService.get("apps_ip_port");
            List<String> ipPorts = JsonUtil.fromJson(json, new TypeReference<List<String>>() {
            });
            for (String ipPort : ipPorts) {
                String ip = ipPort.split(":")[0];
                int port = Integer.parseInt(ipPort.split(":")[1]);
                List<MetricsDto> res = systemMonitorService.getMetricsFromActuator(ip, port);
                log.info("系统metrics信息:{}", JsonUtil.toJson(res));
            }
        }catch (Exception e) {
            log.error("PrintJob出错啦!",e);
        }

    }
}
