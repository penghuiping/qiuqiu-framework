package com.php25.qiuqiu.admin.copyer;

import com.php25.qiuqiu.admin.vo.in.resource.ResourceCreateVo;
import com.php25.qiuqiu.admin.vo.in.resource.ResourceUpdateVo;
import com.php25.qiuqiu.admin.vo.out.resource.ResourceDetailVo;
import com.php25.qiuqiu.admin.vo.out.resource.ResourceVo;
import com.php25.qiuqiu.user.dto.resource.ResourceCreateDto;
import com.php25.qiuqiu.user.dto.resource.ResourceDetailDto;
import com.php25.qiuqiu.user.dto.resource.ResourceDto;
import com.php25.qiuqiu.user.dto.resource.ResourceUpdateDto;
import org.mapstruct.Mapper;

/**
 * @author penghuiping
 * @date 2021/7/2 13:25
 */
@Mapper(componentModel = "spring")
public interface ResourceVoCopyer {

    /**
     * vo转dto
     *
     * @param resourceCreateVo vo
     * @return dto
     */
    ResourceCreateDto toDto(ResourceCreateVo resourceCreateVo);


    /**
     * vo转dto
     *
     * @param resourceUpdateVo vo
     * @return dto
     */
    ResourceUpdateDto toDto(ResourceUpdateVo resourceUpdateVo);

    /**
     * dto转vo
     *
     * @param resourceDto dto
     * @return vo
     */
    ResourceVo toVo(ResourceDto resourceDto);

    /**
     * dto转vo
     *
     * @param resourceDetailDto dto
     * @return vo
     */
    ResourceDetailVo toVo(ResourceDetailDto resourceDetailDto);
}
