package com.php25.qiuqiu.user.dto.mapper;

import com.php25.qiuqiu.user.dto.resource.ResourceCreateDto;
import com.php25.qiuqiu.user.dto.resource.ResourceDetailDto;
import com.php25.qiuqiu.user.dto.resource.ResourceDto;
import com.php25.qiuqiu.user.dto.resource.ResourcePermissionDto;
import com.php25.qiuqiu.user.dto.resource.ResourceUpdateDto;
import com.php25.qiuqiu.user.entity.Resource;
import com.php25.qiuqiu.user.entity.ResourcePermission;
import org.mapstruct.Mapper;

/**
 * @author penghuiping
 * @date 2021/7/2 14:58
 */
@Mapper(componentModel = "spring")
public interface ResourceDtoMapper {

    /**
     * dto转entity
     *
     * @param resourceDto dto
     * @return entity
     */
    Resource toEntity(ResourceCreateDto resourceDto);

    /**
     * dto转entity
     *
     * @param resourceDto dto
     * @return entity
     */
    Resource toEntity(ResourceDto resourceDto);

    /**
     * dto转entity
     *
     * @param resourceDto dto
     * @return entity
     */
    Resource toEntity(ResourceUpdateDto resourceDto);

    /**
     * dto转entity
     *
     * @param resourcePermissionDto dto
     * @return entity
     */
    ResourcePermission toEntity(ResourcePermissionDto resourcePermissionDto);


    /**
     * entity转dto
     *
     * @param resourcePermission entity
     * @return dto
     */
    ResourcePermissionDto toDto(ResourcePermission resourcePermission);

    /**
     * entity转dto
     *
     * @param resource entity
     * @return dto
     */
    ResourceDto toDto(Resource resource);

    /**
     * entity转dto
     *
     * @param resource entity
     * @return dto
     */
    ResourceDetailDto toResourceDetailDto(Resource resource);

}
