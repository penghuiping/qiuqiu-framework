package com.php25.qiuqiu.user.dto.user;

import com.php25.common.core.dto.DataAccessLevel;
import com.php25.qiuqiu.user.dto.group.GroupDto;
import com.php25.qiuqiu.user.dto.resource.ResourcePermissionDto;
import com.php25.qiuqiu.user.dto.role.RoleDto;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Set;

/**
 * @author penghuiping
 * @date 2021/2/3 13:24
 */
@Setter
@Getter
public class UserDto {

    private Long id;

    private String nickname;

    private String username;

    private String password;

    private Set<RoleDto> roles;

    private Set<ResourcePermissionDto> permissions;

    private GroupDto group;

    private LocalDateTime createTime;

    private LocalDateTime lastModifiedTime;

    private DataAccessLevel dataAccessLevel;

    private Boolean enable;
}
