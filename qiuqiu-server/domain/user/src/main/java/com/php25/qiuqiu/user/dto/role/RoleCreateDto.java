package com.php25.qiuqiu.user.dto.role;

import com.php25.qiuqiu.user.dto.resource.ResourcePermissionDto;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author penghuiping
 * @date 2021/3/6 10:36
 */
@Setter
@Getter
public class RoleCreateDto {

    /**
     * 角色名
     */
    private String name;

    /**
     * 角色描述
     */
    private String description;

    /**
     * (资源+权限)列表
     */
    private List<ResourcePermissionDto> resourcePermissions;
}
