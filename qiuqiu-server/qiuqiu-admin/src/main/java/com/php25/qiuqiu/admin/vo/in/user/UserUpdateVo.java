package com.php25.qiuqiu.admin.vo.in.user;

import lombok.Getter;
import lombok.Setter;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import java.util.List;

/**
 * @author penghuiping
 * @date 2021/3/6 10:02
 */
@Setter
@Getter
public class UserUpdateVo {

    /**
     * 用户id
     */
    @NotNull
    @Positive
    private Long id;

    /**
     * 昵称
     */
    @NotBlank
    private String nickname;

    /**
     * 密码
     */
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

    /**
     * true:有效
     */
    @NotNull
    private Boolean enable;
}
