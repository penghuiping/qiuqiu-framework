package com.php25.qiuqiu.admin.vo.in.user;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

/**
 * @author penghuiping
 * @date 2021/3/6 19:56
 */
@Setter
@Getter
public class UserDetailVo {

    /***
     * 用户id
     */
    @Positive
    private Long userId;
}
