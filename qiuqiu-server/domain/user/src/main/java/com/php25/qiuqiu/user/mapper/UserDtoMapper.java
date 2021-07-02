package com.php25.qiuqiu.user.mapper;

import com.php25.qiuqiu.user.dto.user.UserDto;
import com.php25.qiuqiu.user.entity.User;
import org.mapstruct.Mapper;

/**
 * @author penghuiping
 * @date 2021/7/2 17:22
 */
@Mapper(componentModel = "spring")
public interface UserDtoMapper {

    /**
     * dto转entity
     *
     * @param userDto dto
     * @return entity
     */
    User toEntity(UserDto userDto);

    /**
     * entity转dto
     *
     * @param user entity
     * @return dto
     */
    UserDto toDto(User user);
}
