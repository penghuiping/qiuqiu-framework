package com.php25.qiuqiu.user.entity;

import lombok.Getter;
import lombok.Setter;

/**
 * 权限
 *
 * @author penghuiping
 * @date 2021/3/2 08:54
 */
@Setter
@Getter
public class Permission {

    /**
     * 权限名
     */
    private String name;

    /**
     * 权限描述
     */
    private String description;

    /**
     * 是否有效 0:无效 1:有效
     */
    private Boolean enable;
}
