package com.php25.qiuqiu.admin.job;

import com.fasterxml.jackson.core.type.TypeReference;
import com.php25.common.core.mess.SpringContextHolder;
import com.php25.common.core.util.JsonUtil;
import com.php25.qiuqiu.job.dto.BaseRunnable;
import com.php25.qiuqiu.monitor.dto.MetricsDto;
import com.php25.qiuqiu.monitor.service.DictionaryService;
import com.php25.qiuqiu.monitor.service.SystemMonitorService;
import lombok.extern.log4j.Log4j2;

import java.util.List;

/**
 * @author penghuiping
 * @date 2021/3/13 19:35
 */
@Log4j2
public class SystemMonitorInfoPrintJob extends BaseRunnable {

    private final DictionaryService dictionaryService;
    private final SystemMonitorService systemMonitorService;


    public SystemMonitorInfoPrintJob(String jobId,String jobName,String jobExecutionId) {
        super(jobId,jobName,jobExecutionId);
        this.dictionaryService = SpringContextHolder.getBean0(DictionaryService.class);
        this.systemMonitorService = SpringContextHolder.getBean0(SystemMonitorService.class);
    }

    @Override
    public void run0(String params) {
        String json = dictionaryService.get("apps_ip_port");
        List<String> ipPorts = JsonUtil.fromJson(json, new TypeReference<List<String>>() {
        });
        for (String ipPort : ipPorts) {
            String ip = ipPort.split(":")[0];
            int port = Integer.parseInt(ipPort.split(":")[1]);
            List<MetricsDto> res = systemMonitorService.getMetricsFromActuator(ip, port);
            log.info("系统metrics信息:{}", JsonUtil.toJson(res));
        }
    }
}
