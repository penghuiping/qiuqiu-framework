package com.php25.qiuqiu.user.entity;

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
public class RoleResourcePermission {
    /**
     * 角色id
     */
    private Long roleId;

    /**
     * 资源
     */
    private String resource;

    /**
     * 权限
     */
    private String permission;
}
