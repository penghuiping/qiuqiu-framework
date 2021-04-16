package com.php25.qiuqiu.user.dto.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * @author penghuiping
 * @date 2021/3/5 20:54
 */
@Setter
@Getter
@AllArgsConstructor
public class TokenDto {

    /**
     * 访问令牌(格式为jwt)
     */
    private String accessToken;

    /**
     * 刷新令牌
     */
    private String refreshToken;

    /**
     * 访问令牌有效时长(单位秒)
     */
    private Long expireTime;

}
