package com.php25.qiuqiu.user.entity;

import lombok.Getter;
import lombok.Setter;

/**
 * 资源与权限关系
 *
 * @author penghuiping
 * @date 2021/3/2 09:15
 */
@Setter
@Getter
public class ResourcePermission {

    /**
     * 资源id
     */
    private String resource;

    /**
     * 权限id
     */
    private String permission;
}
