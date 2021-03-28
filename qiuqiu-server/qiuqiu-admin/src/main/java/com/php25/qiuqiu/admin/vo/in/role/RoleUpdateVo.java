package com.php25.qiuqiu.admin.vo.in.role;

import com.php25.qiuqiu.admin.vo.out.resource.ResourcePermissionVo;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * @author penghuiping
 * @date 2021/3/9 15:01
 */
@Setter
@Getter
public class RoleUpdateVo {

    /**
     * 角色id
     */
    @NotNull
    @Positive
    private Long id;

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
     * 权限id列表
     */
    @NotNull
    @Size(min = 1)
    private List<ResourcePermissionVo> resourcePermissions;

    /**
     * false:无效 true:有效
     */
    @NotNull
    private Boolean enable;
}
