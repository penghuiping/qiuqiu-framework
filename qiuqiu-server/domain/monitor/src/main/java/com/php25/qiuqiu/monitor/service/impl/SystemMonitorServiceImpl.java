package com.php25.qiuqiu.monitor.service.impl;

import com.php25.common.core.util.StringUtil;
import com.php25.qiuqiu.monitor.dto.MetricsDto;
import com.php25.qiuqiu.monitor.service.DictionaryService;
import com.php25.qiuqiu.monitor.service.SystemMonitorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author penghuiping
 * @date 2021/3/12 14:19
 */
@Log4j2
@Service
@RequiredArgsConstructor
public class SystemMonitorServiceImpl implements SystemMonitorService {

    private final RestTemplate restTemplate;

    private final DictionaryService dictionaryService;

    @Override
    public String getIp() {
        return null;
    }

    @Override
    public String getMac() {
        return null;
    }

    @Override
    public List<MetricsDto> getMetricsFromActuator(String ip, Integer port) {
        String url = dictionaryService.get(ip + ":" + port);
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(url, String.class);
        String prometheusResult = responseEntity.getBody();
        if (!StringUtil.isBlank(prometheusResult)) {
            List<MetricsDto> metricsDtoList = new ArrayList<>();
            MetricsDto metricsDto = new MetricsDto();
            char[] arr = prometheusResult.toCharArray();
            int start = 0;
            int end = 0;
            int textEnd = arr.length;
            while (end < textEnd) {
                char ch = arr[end];
                if ('\n' == ch) {
                    //遇到换行符,表示一行结束,新的一行开始
                    String value = prometheusResult.substring(start, end);
                    metricsDto.setValue(Double.parseDouble(value));
                    metricsDtoList.add(metricsDto);
                    start = end + 1;
                    metricsDto = new MetricsDto();
                } else if ('#' == ch) {
                    //遇到井号表示注释,直接掉过此行
                    start = end;
                    while (arr[end] != '\n') {
                        end++;
                    }
                    start = end + 1;
                } else if ('{' == ch) {
                    //进入tag
                    String name = prometheusResult.substring(start, end);
                    metricsDto.setName(name);
                    metricsDto.setTags(new HashMap<>());
                    start = end + 1;
                } else if ('}' == ch) {
                    //tag结束
                    start = end + 1;
                } else if (',' == ch) {
                    String tag = prometheusResult.substring(start, end);
                    String tagName = tag.split("=")[0];
                    String tagValue = tag.split("=")[1].replace("\"","");
                    metricsDto.getTags().put(tagName, tagValue);
                    start = end + 1;
                }
                end++;
            }
            return metricsDtoList.stream().sorted().collect(Collectors.toList());
        }
        return null;
    }

}
