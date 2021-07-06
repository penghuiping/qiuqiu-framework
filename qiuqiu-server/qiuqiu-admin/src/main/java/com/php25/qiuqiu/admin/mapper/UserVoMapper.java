package com.php25.qiuqiu.admin.mapper;

import com.php25.qiuqiu.admin.vo.in.user.UserCreateVo;
import com.php25.qiuqiu.admin.vo.in.user.UserUpdateVo;
import com.php25.qiuqiu.admin.vo.out.user.UserPageOutVo;
import com.php25.qiuqiu.admin.vo.out.user.UserVo;
import com.php25.qiuqiu.user.dto.user.UserCreateDto;
import com.php25.qiuqiu.user.dto.user.UserDto;
import com.php25.qiuqiu.user.dto.user.UserPageDto;
import com.php25.qiuqiu.user.dto.user.UserUpdateDto;
import org.mapstruct.Mapper;

/**
 * @author penghuiping
 * @date 2021/7/6 13:35
 */
@Mapper(componentModel = "spring")
public interface UserVoMapper {

    /**
     * dto转vo
     *
     * @param userDto dto
     * @return vo
     */
    UserVo toVo(UserDto userDto);

    /**
     * vo转dto
     *
     * @param userVo vo
     * @return dto
     */
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
