package com.php25.qiuqiu.user.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * @author penghuiping
 * @date 2021/3/5 20:54
 */
@Setter
@Getter
public class TokenDto {

    /**
     * 令牌
     */
    private String token;

    /**
     * 令牌有效时长(单位秒)
     */
    private Long expireTime;

    public TokenDto(String token, Long expireTime) {
        this.token = token;
        this.expireTime = expireTime;
    }
}
