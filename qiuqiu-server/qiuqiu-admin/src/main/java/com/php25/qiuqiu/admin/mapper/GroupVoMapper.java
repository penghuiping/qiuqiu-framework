package com.php25.qiuqiu.admin.mapper;

import com.php25.qiuqiu.admin.vo.in.group.GroupCreateVo;
import com.php25.qiuqiu.admin.vo.in.group.GroupUpdateVo;
import com.php25.qiuqiu.user.dto.group.GroupCreateDto;
import com.php25.qiuqiu.user.dto.group.GroupDto;
import org.mapstruct.Mapper;

/**
 * @author penghuiping
 * @date 2021/7/2 09:51
 */
@Mapper(componentModel = "spring")
public interface GroupVoMapper {

    /**
     * vo转dto
     *
     * @param vo vo
     * @return dto
     */
    GroupCreateDto toCreateDto(GroupCreateVo vo);

    /**
     * vo转dto
     *
     * @param vo vo
     * @return dto
     */
    GroupDto toGroupDto(GroupUpdateVo vo);
}
