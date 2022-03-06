package com.php25.qiuqiu.user.entity;

import lombok.Getter;
import lombok.Setter;

/**
 * 用户与角色关系
 *
 * @author penghuiping
 * @date 2021/3/2 09:06
 */
@Setter
@Getter
public class UserRole {

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 角色id
     */
    private Long roleId;
}
