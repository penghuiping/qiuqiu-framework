package com.php25.qiuqiu.user.copyer;

import com.php25.qiuqiu.user.dto.user.UserCreateDto;
import com.php25.qiuqiu.user.dto.user.UserDto;
import com.php25.qiuqiu.user.dto.user.UserPageDto;
import com.php25.qiuqiu.user.dto.user.UserUpdateDto;
import com.php25.qiuqiu.user.entity.User;
import org.mapstruct.Mapper;

/**
 * @author penghuiping
 * @date 2021/7/2 17:22
 */
@Mapper(componentModel = "spring")
public interface UserDtoCopyer {

    /**
     * dto转entity
     *
     * @param userDto dto
     * @return entity
     */
    User toEntity(UserDto userDto);

    /**
     * dto转entity
     *
     * @param userDto dto
     * @return entity
     */
    User toEntity(UserCreateDto userDto);

    /**
     * dto转entity
     *
     * @param userDto dto
     * @return entity
     */
    User toEntity(UserUpdateDto userDto);

    /**
     * entity转dto
     *
     * @param user entity
     * @return dto
     */
    UserDto toDto(User user);

    /**
     * entity转dto
     *
     * @param user entity
     * @return dto
     */
    UserPageDto toDto0(User user);
}
