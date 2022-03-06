package com.php25.qiuqiu.user.dao.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

/**
 * 角色资源权限关系
 *
 * @author penghuiping
 * @date 2021/3/25 16:18
 */
@Setter
@Getter
@TableName("t_role_resource_permission")
public class RoleResourcePermissionPo {
    /**
     * 角色id
     */
    @TableField("role_id")
    private Long roleId;

    /**
     * 资源
     */
    @TableField("resource")
    private String resource;

    /**
     * 权限
     */
    @TableField("permission")
    private String permission;
}
