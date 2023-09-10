package com.php25.qiuqiu.monitor.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * @author penghuiping
 * @date 2023/9/10 22:42
 */
@Setter
@Getter
public class SystemLogDto {
    private String id;

    private String timestamp;

    private String message;

    private String threadName;

    private String loggerName;

    private String level;
}
