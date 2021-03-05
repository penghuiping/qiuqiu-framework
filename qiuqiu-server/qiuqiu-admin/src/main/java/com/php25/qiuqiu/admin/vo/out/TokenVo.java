package com.php25.qiuqiu.admin.vo.out;

import lombok.Getter;
import lombok.Setter;

/**
 * @author penghuiping
 * @date 2021/3/5 20:50
 */
@Setter
@Getter
public class TokenVo {
    /**
     * 令牌有效时长(单位秒)
     */
    private Long expireTime;

    /**
     * 令牌值
     */
    private String token;
}
