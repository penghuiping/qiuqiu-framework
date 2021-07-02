package com.php25.qiuqiu.user.entity;

import com.php25.common.db.core.annotation.Column;
import com.php25.common.db.core.annotation.Table;
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
@Table("t_role_resource_permission")
public class RoleResourcePermission {
    /**
     * 角色id
     */
    @Column("role_id")
    private Long roleId;

    /**
     * 资源
     */
    @Column("resource")
    private String resource;

    /**
     * 权限
     */
    @Column("permission")
    private String permission;
}
