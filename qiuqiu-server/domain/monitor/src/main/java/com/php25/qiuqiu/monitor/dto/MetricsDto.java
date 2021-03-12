package com.php25.qiuqiu.monitor.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

/**
 * @author penghuiping
 * @date 2021/3/12 15:00
 */
@Setter
@Getter
@EqualsAndHashCode
public class MetricsDto implements Comparable<MetricsDto> {
    /**
     * 度量指标名字
     */
    private String name;

    /**
     * 度量指标维度¬
     */
    private Map<String,String> tags;

    /**
     * 度量指标值
     */
    private double value;

    @Override
    public int compareTo(@NotNull MetricsDto o) {
        return this.getName().compareTo(o.getName());
    }
}
