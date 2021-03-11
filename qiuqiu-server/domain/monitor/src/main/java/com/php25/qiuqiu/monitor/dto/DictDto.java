package com.php25.qiuqiu.monitor.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * @author penghuiping
 * @date 2021/3/10 17:05
 */
@Setter
@Getter
public class DictDto {
    /**
     * id值
     */
    private Long id;

    /**
     * 键
     */
    private String key;

    /**
     * 值
     */
    private String value;

    /**
     * 描述
     */
    private String description;

    /**
     * true: 有效
     */
    private Boolean enable;
}
