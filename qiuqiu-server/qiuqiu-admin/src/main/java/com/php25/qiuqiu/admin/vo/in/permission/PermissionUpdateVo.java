package com.php25.qiuqiu.admin.vo.in.permission;

import lombok.Getter;
import lombok.Setter;

/**
 * @author penghuiping
 * @date 2021/3/9 17:29
 */
@Setter
@Getter
public class PermissionUpdateVo {
    /**
     * 权限id
     */
    private Long id;

    /**
     * 权限名
     */
    private String name;

    /**
     * 权限描述
     */
    private String description;

    /**
     * 权限对应的接口uri地址
     */
    private String uri;

    /**
     * true:有效
     */
    private Boolean enable;
}
