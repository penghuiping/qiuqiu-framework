package com.php25.qiuqiu.monitor.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * @author penghuiping
 * @date 2021/3/10 17:21
 */
@Setter
@Getter
public class AuditLogDto {

    /**
     * 日志id
     */
    private Long id;

    /**
     * 系统用户名
     */
    private String username;

    /**
     * 用户组id
     */
    private String groupId;

    /**
     * 用户组名
     */
    private String groupName;

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
