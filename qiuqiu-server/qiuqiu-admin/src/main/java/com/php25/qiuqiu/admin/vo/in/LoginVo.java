package com.php25.qiuqiu.admin.vo.in;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

/**
 * @author penghuiping
 * @date 2021/2/4 09:32
 */
@Setter
@Getter
public class LoginVo {

    /**
     * 用户名
     */
    @NotBlank(message = "用户名不能为空")
    private String username;

    /**
     * 密码
     */
    @NotBlank(message = "密码不能为空")
    private String password;
}
