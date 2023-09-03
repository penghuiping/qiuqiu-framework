package com.php25.qiuqiu.admin.vo.in.role;

import com.php25.qiuqiu.admin.vo.out.resource.ResourcePermissionVo;
import lombok.Getter;
import lombok.Setter;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.List;

/**
 * @author penghuiping
 * @date 2021/3/9 15:35
 */
@Setter
@Getter
public class RoleCreateVo {

    /**
     * 角色名
     */
    @NotBlank
    private String name;

    /**
     * 角色描述
     */
    @NotBlank
    private String description;

    /**
     * 权限列表
     */
    @NotNull
    @Size(min = 1)
    private List<ResourcePermissionVo> resourcePermissions;
}
