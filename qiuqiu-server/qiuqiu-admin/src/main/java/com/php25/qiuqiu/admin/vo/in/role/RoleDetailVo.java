package com.php25.qiuqiu.admin.vo.in.role;

import lombok.Getter;
import lombok.Setter;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

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
    @NotNull
    @Positive
    private Long roleId;
}
