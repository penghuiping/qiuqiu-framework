package com.php25.qiuqiu.monitor.mapper;

import com.php25.qiuqiu.monitor.dto.DictDto;
import com.php25.qiuqiu.monitor.entity.Dict;
import org.mapstruct.Mapper;

/**
 * @author penghuiping
 * @date 2021/7/5 13:48
 */
@Mapper(componentModel = "spring")
public interface DictDtoMapper {

    /**
     * entity转dto
     *
     * @param dict entity
     * @return do
     */
    DictDto toDto(Dict dict);

    /**
     * dto转entity
     *
     * @param dictDto dto
     * @return entity
     */
    Dict toEntity(DictDto dictDto);
}
