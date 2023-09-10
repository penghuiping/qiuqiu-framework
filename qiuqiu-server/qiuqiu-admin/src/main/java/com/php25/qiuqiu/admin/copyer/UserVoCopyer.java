package com.php25.qiuqiu.admin.copyer;

import com.php25.qiuqiu.admin.vo.in.user.UserCreateVo;
import com.php25.qiuqiu.admin.vo.in.user.UserUpdateVo;
import com.php25.qiuqiu.admin.vo.out.user.UserPageOutVo;
import com.php25.qiuqiu.admin.vo.out.user.UserVo;
import com.php25.qiuqiu.user.dto.user.UserCreateDto;
import com.php25.qiuqiu.user.dto.user.UserDto;
import com.php25.qiuqiu.user.dto.user.UserPageDto;
import com.php25.qiuqiu.user.dto.user.UserUpdateDto;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * @author penghuiping
 * @date 2021/7/6 13:35
 */
@Mapper(componentModel = "spring")
@DecoratedWith(UserVoCopyerDecorator.class)
public interface UserVoCopyer {

    /**
     * dto转vo
     *
     * @param userDto dto
     * @return vo
     */
    @Mapping(target = "roles",ignore = true)
    UserVo toVo(UserDto userDto);


    /**
     * vo转dto
     *
     * @param userVo vo
     * @return dto
     */
    @Mapping(target = "roles",ignore = true)
    UserDto toDto(UserVo userVo);


    /**
     * dto转vo
     *
     * @param userPageDto dto
     * @return vo
     */
    UserPageOutVo toVo(UserPageDto userPageDto);


    /**
     * dto转vo
     *
     * @param userCreateDto dto
     * @return vo
     */
    UserCreateVo toVo(UserCreateDto userCreateDto);

    /**
     * vo转dto
     *
     * @param userCreateVo vo
     * @return dto
     */
    UserCreateDto toDto(UserCreateVo userCreateVo);

    /**
     * dto转vo
     *
     * @param userUpdateDto dto
     * @return vo
     */
    UserUpdateVo toVo(UserUpdateDto userUpdateDto);

    /**
     * vo转dto
     *
     * @param userUpdateVo vo
     * @return dto
     */
    UserUpdateDto toDto(UserUpdateVo userUpdateVo);
}
