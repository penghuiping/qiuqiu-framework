package com.php25.qiuqiu.admin.mapper;

import com.php25.qiuqiu.admin.vo.in.permission.PermissionCreateVo;
import com.php25.qiuqiu.admin.vo.in.permission.PermissionUpdateVo;
import com.php25.qiuqiu.admin.vo.out.permission.PermissionVo;
import com.php25.qiuqiu.user.dto.permission.PermissionCreateDto;
import com.php25.qiuqiu.user.dto.permission.PermissionDto;
import org.mapstruct.Mapper;

/**
 * @author penghuiping
 * @date 2021/7/2 10:39
 */
@Mapper(componentModel = "spring")
public interface PermissionVoMapper {

    /**
     * dto转vo
     *
     * @param dto dto
     * @return vo
     */
    PermissionVo toVo(PermissionDto dto);

    /**
     * vo转dto
     *
     * @param vo vo
     * @return dto
     */
    PermissionDto toDto(PermissionUpdateVo vo);

    /**
     * vo转dto
     *
     * @param vo vo
     * @return dto
     */
    PermissionCreateDto toCreateDto(PermissionCreateVo vo);
}
