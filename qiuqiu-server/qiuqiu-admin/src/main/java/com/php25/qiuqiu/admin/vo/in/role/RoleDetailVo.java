package com.php25.qiuqiu.admin.vo.in.role;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

/**
 * @author penghuiping
 * @date 2021/3/9 13:30
 */
@Setter
@Getter
public class RoleDetailVo {

    /***
     * 用户id
     */
    @NotNull(message = "用户id不能为空")
    private Long roleId;
}
