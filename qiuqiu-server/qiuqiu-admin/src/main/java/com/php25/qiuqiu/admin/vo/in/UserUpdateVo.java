package com.php25.qiuqiu.admin.vo.in;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author penghuiping
 * @date 2021/3/6 10:02
 */
@Setter
@Getter
public class UserUpdateVo {

    /**
     * 昵称
     */
    @NotBlank(message = "昵称不能为空")
    private String nickname;

    /**
     * 密码
     */
    @NotBlank(message = "密码不能为空")
    private String password;

    /**
     * 组id
     */
    @NotNull(message = "组id不能为空")
    private Long groupId;

    /**
     * 角色列表id
     */
    @NotNull(message = "角色id不能为空")
    private List<Long> roleIds;
}
