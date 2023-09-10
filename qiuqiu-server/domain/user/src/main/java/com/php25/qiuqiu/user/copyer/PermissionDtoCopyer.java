package com.php25.qiuqiu.user.copyer;

import com.php25.qiuqiu.user.dto.permission.PermissionCreateDto;
import com.php25.qiuqiu.user.dto.permission.PermissionDto;
import com.php25.qiuqiu.user.entity.Permission;
import org.mapstruct.Mapper;

/**
 * @author penghuiping
 * @date 2021/7/2 14:40
 */
@Mapper(componentModel = "spring")
public interface PermissionDtoCopyer {

    /**
     * dto转entity
     *
     * @param permissionDto dto
     * @return permission
     */
    Permission toEntity(PermissionCreateDto permissionDto);

    /**
     * dto转entity
     *
     * @param permissionDto dto
     * @return permission
     */
    Permission toEntity(PermissionDto permissionDto);

    /**
     * entity转dto
     *
     * @param permission entity
     * @return dto
     */
    PermissionDto toDto(Permission permission);
}
