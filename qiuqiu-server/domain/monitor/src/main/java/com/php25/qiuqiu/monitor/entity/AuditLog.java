package com.php25.qiuqiu.monitor.entity;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * @author penghuiping
 * @date 2021/3/11 14:52
 */
@Setter
@Getter
public class AuditLog {

    /**
     * 日志id
     */
    private Long id;

    /**
     * 系统用户名
     */
    private String username;

    /**
     * 系统接口地址
     */
    private String uri;

    /**
     * 接口入参
     */
    private String params;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;
}
