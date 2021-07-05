package com.php25.qiuqiu.admin.mapper;

import com.php25.qiuqiu.admin.vo.out.job.JobLogVo;
import com.php25.qiuqiu.job.dto.JobLogDto;
import org.mapstruct.Mapper;

/**
 * @author penghuiping
 * @date 2021/7/2 10:32
 */
@Mapper(componentModel = "spring")
public interface JobLogVoMapper {

    /**
     * dto转vo
     *
     * @param jobLogDto dto
     * @return vo
     */
    JobLogVo toVo(JobLogDto jobLogDto);

    /**
     * vo转dto
     *
     * @param jobLogVo vo
     * @return dto
     */
    JobLogDto toDto(JobLogVo jobLogVo);
}
