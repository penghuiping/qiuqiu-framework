package com.php25.qiuqiu.monitor.dao.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * @author penghuiping
 * @date 2021/3/11 14:52
 */
@Setter
@Getter
@TableName("t_audit_log")
public class AuditLogPo {

    /**
     * 日志id
     */
    @TableId
    private Long id;

    /**
     * 系统用户名
     */
    @TableField
    private String username;

    /**
     * 系统接口地址
     */
    @TableField
    private String uri;

    /**
     * 接口入参
     */
    @TableField
    private String params;

    /**
     * 创建时间
     */
    @TableField("create_time")
    private LocalDateTime createTime;
}