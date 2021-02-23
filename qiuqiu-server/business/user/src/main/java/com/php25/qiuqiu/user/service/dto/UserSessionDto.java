package com.php25.qiuqiu.user.service.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

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
    private List<String> roles;
}
