package com.php25.qiuqiu.admin.copyer;

import com.php25.qiuqiu.admin.vo.in.role.RoleCreateVo;
import com.php25.qiuqiu.admin.vo.in.role.RoleUpdateVo;
import com.php25.qiuqiu.admin.vo.out.role.RoleDetailOutVo;
import com.php25.qiuqiu.admin.vo.out.role.RolePageOutVo;
import com.php25.qiuqiu.admin.vo.out.role.RoleVo;
import com.php25.qiuqiu.user.dto.role.RoleCreateDto;
import com.php25.qiuqiu.user.dto.role.RoleDetailDto;
import com.php25.qiuqiu.user.dto.role.RoleDto;
import com.php25.qiuqiu.user.dto.role.RolePageDto;
import com.php25.qiuqiu.user.dto.role.RoleUpdateDto;
import org.mapstruct.Mapper;

/**
 * @author penghuiping
 * @date 2021/7/2 14:00
 */
@Mapper(componentModel = "spring")
public interface RoleVoCopyer {

    /**
     * 把dto转成vo
     *
     * @param roleDto dto
     * @return vo
     */
    RoleVo toVo(RoleDto roleDto);

    /**
     * 把vo转成dto
     *
     * @param roleVo vo
     * @return dto
     */
    RoleDto toDto(RoleVo roleVo);

    /**
     * 把vo转成dto
     *
     * @param roleCreateVo vo
     * @return dto
     */
    RoleCreateDto toDto(RoleCreateVo roleCreateVo);

    /**
     * 把vo转成dto
     *
     * @param roleUpdateVo vo
     * @return dto
     */
    RoleUpdateDto toDto(RoleUpdateVo roleUpdateVo);

    /**
     * 把dto转成vo
     *
     * @param roleDetailDto dto
     * @return vo
     */
    RoleDetailOutVo toVo(RoleDetailDto roleDetailDto);

    /**
     * 把dto转成vo
     *
     * @param rolePageDto dto
     * @return vo
     */
    RolePageOutVo toVo(RolePageDto rolePageDto);
}
