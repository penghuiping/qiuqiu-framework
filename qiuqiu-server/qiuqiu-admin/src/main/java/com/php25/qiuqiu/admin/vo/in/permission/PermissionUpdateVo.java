package com.php25.qiuqiu.admin.vo.in.permission;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author penghuiping
 * @date 2021/3/9 17:29
 */
@Setter
@Getter
public class PermissionUpdateVo {
    /**
     * 权限名
     */
    @NotBlank
    private String name;

    /**
     * 权限描述
     */
    @NotBlank
    private String description;

    /**
     * true:有效
     */
    @NotNull
    private Boolean enable;
}
