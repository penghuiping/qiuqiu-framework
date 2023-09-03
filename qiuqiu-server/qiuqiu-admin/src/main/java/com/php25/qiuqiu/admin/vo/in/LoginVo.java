package com.php25.qiuqiu.admin.vo.in;

import com.php25.common.validation.annotation.Mobile;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;

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
    @NotBlank
    private String username;

    /**
     * 密码
     */
    @NotBlank
    private String password;

    /**
     * 验证码
     */
    @NotBlank
    private String code;

    /**
     * 验证码id
     */
    @NotBlank
    @Length(max = 32)
    private String imgCodeId;
}
