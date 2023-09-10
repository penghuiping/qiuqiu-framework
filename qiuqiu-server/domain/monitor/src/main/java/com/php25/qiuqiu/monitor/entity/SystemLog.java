package com.php25.qiuqiu.monitor.entity;

import lombok.Getter;
import lombok.Setter;

/**
 * @author penghuiping
 * @date 2023/9/10 23:15
 */
@Setter
@Getter
public class SystemLog {
    private String id;

    private String timestamp;

    private String message;

    private String threadName;

    private String loggerName;

    private String level;
}
