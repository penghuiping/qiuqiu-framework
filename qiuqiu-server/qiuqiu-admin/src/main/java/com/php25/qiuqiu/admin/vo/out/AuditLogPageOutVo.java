package com.php25.qiuqiu.admin.vo.out;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * @author penghuiping
 * @date 2021/3/11 15:29
 */
@Setter
@Getter
public class AuditLogPageOutVo {
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
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime createTime;
}
