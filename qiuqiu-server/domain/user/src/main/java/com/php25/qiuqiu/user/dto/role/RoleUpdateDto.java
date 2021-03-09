package com.php25.qiuqiu.user.dto.role;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author penghuiping
 * @date 2021/3/6 10:36
 */
@Setter
@Getter
public class RoleUpdateDto {

    /**
     * 角色id
     */
    private Long id;

    /**
     * 角色名
     */
    private String name;

    /**
     * 角色描述
     */
    private String description;

    /**
     * 权限列表
     */
    private List<Long> permissionIds;

    /**
     * true:有效
     */
    private Boolean enable;
}
