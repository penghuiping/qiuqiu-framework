package com.php25.qiuqiu.admin.vo.in.user;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

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
    @NotNull(message = "用户id不能为空")
    private Long userId;
}
