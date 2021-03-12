package com.php25.qiuqiu.monitor.service;

import com.php25.qiuqiu.monitor.dto.MetricsDto;

import java.util.List;
import java.util.Set;

/**
 * 支持对物理机器与jvm进程的基本信息监控
 * 基于micrometer
 * <p>
 * 1. 支持获取本机mac地址与ip地址
 * 2. 支持对部署物理机的cpu,内存,磁盘空间大小的监控
 * 3. jvm线程数据
 * 4. jvm堆内存数据,年轻代,老年代
 * 5. jvm gc数据,gc次数,gc停顿时间
 *
 * @author penghuiping
 * @date 2021/3/1 20:58
 */
public interface SystemMonitorService {

    /**
     * 获取本机ip地址
     *
     * @return ip地址
     */
    public String getIp();

    /**
     * 获取本机mac地址
     *
     * @return mac地址
     */
    public String getMac();

    /**
     * 获取某台spring boot应用服务的metrics信息
     *
     * @param ip   这台应用服务器ip
     * @param port 这台应用服务器metrics端口
     * @return metrics信息
     */
    public List<MetricsDto> getMetricsFromActuator(String ip, Integer port);
}
