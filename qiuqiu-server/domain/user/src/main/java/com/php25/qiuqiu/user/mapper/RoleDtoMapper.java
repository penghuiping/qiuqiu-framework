package com.php25.qiuqiu.user.mapper;

import com.php25.qiuqiu.user.dto.role.RoleCreateDto;
import com.php25.qiuqiu.user.dto.role.RoleDetailDto;
import com.php25.qiuqiu.user.dto.role.RoleDto;
import com.php25.qiuqiu.user.dto.role.RolePageDto;
import com.php25.qiuqiu.user.dto.role.RoleUpdateDto;
import com.php25.qiuqiu.user.entity.Role;
import org.mapstruct.Mapper;

/**
 * @author penghuiping
 * @date 2021/7/2 16:55
 */
@Mapper(componentModel = "spring")
public interface RoleDtoMapper {


    /**
     * dto转entity
     *
     * @param roleDto dto
     * @return entity
     */
    Role toEntity(RoleCreateDto roleDto);

    /**
     * entity转dto
     *
     * @param role entity
     * @return dto
     */
    RoleDto toDto0(Role role);

    /**
     * entity转dto
     *
     * @param role entity
     * @return dto
     */
    RoleDetailDto toRoleDetailDto(Role role);

    /**
     * dto转entity
     *
     * @param roleDto dto
     * @return entity
     */
    Role toEntity(RoleUpdateDto roleDto);

    /**
     * entity转dto
     *
     * @param role entity
     * @return dto
     */
    RolePageDto toDto(Role role);
}
