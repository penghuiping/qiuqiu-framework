package com.php25.qiuqiu.user.dto.role;

import com.php25.qiuqiu.user.dto.permission.PermissionDto;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author penghuiping
 * @date 2021/3/9 13:32
 */
@Setter
@Getter
public class RoleDetailDto {

    /**
     * 角色id
     */
    private Long id;

    /**
     * 角色名
     */
    private String name;

    /**
     * 角色描述
     */
    private String description;

    /**
     * 权限列表
     */
    private List<PermissionDto> permissions;

    /**
     * 0:无效 1:有效
     */
    private Integer enable;
}
