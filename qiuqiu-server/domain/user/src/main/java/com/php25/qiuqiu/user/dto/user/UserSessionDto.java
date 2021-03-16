package com.php25.qiuqiu.user.dto.user;

import com.php25.qiuqiu.user.dto.role.RoleDto;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

/**
 * @author penghuiping
 * @date 2021/2/3 14:04
 */
@Setter
@Getter
public class UserSessionDto {

    /**
     * 用户名信息
     */
    private String username;

    /**
     * jwt令牌唯一表示
     */
    private String jti;

    /**
     * 此用户具有的角色
     */
    private Set<RoleDto> roles;

    /**
     * 此用户对应的组id
     */
    private Long groupId;
}
