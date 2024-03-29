package com.php25.qiuqiu.admin.vo.in.permission;

import lombok.Getter;
import lombok.Setter;

import jakarta.validation.constraints.NotBlank;

/**
 * @author penghuiping
 * @date 2021/3/9 17:31
 */
@Setter
@Getter
public class PermissionCreateVo {
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
}
