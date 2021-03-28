package com.php25.qiuqiu.admin.vo.in.user;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * @author penghuiping
 * @date 2021/3/6 10:02
 */
@Setter
@Getter
public class UserCreateVo {

    /**
     * 用户名
     */
    @NotBlank
    private String username;

    /**
     * 昵称
     */
    @NotBlank
    private String nickname;

    /**
     * 密码
     */
    @NotBlank
    private String password;

    /**
     * 组id
     */
    @NotNull
    @Positive
    private Long groupId;

    /**
     * 角色列表id
     */
    @NotNull
    @Size(min = 1)
    private List<Long> roleIds;
}
