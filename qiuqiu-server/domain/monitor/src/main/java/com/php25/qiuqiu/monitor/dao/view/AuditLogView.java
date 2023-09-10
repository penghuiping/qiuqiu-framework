package com.php25.qiuqiu.monitor.dao.view;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * @author penghuiping
 * @date 2023/9/10 11:14
 */
@Setter
@Getter
public class AuditLogView {

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
    @TableField("group_id")
    private String groupId;

    /**
     * 用户组名
     */
    @TableField("group_name")
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
    @TableField("create_time")
    private Date createTime;
}
