package com.php25.qiuqiu.user.mapper;

import com.php25.qiuqiu.user.dto.group.GroupCreateDto;
import com.php25.qiuqiu.user.dto.group.GroupDto;
import com.php25.qiuqiu.user.entity.Group;
import org.mapstruct.Mapper;

/**
 * @author penghuiping
 * @date 2021/7/2 14:21
 */
@Mapper(componentModel = "spring")
public interface GroupDtoMapper {

    /**
     * entity转dto
     *
     * @param group entity
     * @return dto
     */
    GroupDto toDto(Group group);

    /**
     * dto转entity
     *
     * @param groupCreateDto dto
     * @return entity
     */
    Group toEntity(GroupCreateDto groupCreateDto);

    /**
     * dto转entity
     *
     * @param groupDto dto
     * @return entity
     */
    Group toEntity(GroupDto groupDto);
}
