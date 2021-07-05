package com.php25.qiuqiu.admin.mapper;

import com.php25.qiuqiu.admin.vo.in.dict.DictUpdateVo;
import com.php25.qiuqiu.admin.vo.out.DictVo;
import com.php25.qiuqiu.monitor.dto.DictDto;
import org.mapstruct.Mapper;

/**
 * @author penghuiping
 * @date 2021/7/2 09:44
 */
@Mapper(componentModel = "spring")
public interface DictVoMapper {

    /**
     * dto转vo
     *
     * @param dictDto dto
     * @return vo
     */
    DictVo toDictVo(DictDto dictDto);

    /**
     * vo转dto
     *
     * @param dictVo vo
     * @return dto
     */
    DictDto toDictDto(DictUpdateVo dictVo);
}
