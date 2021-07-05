package com.php25.qiuqiu.admin.mapper;

import com.php25.qiuqiu.admin.vo.in.job.JobCreateVo;
import com.php25.qiuqiu.admin.vo.in.job.JobUpdateVo;
import com.php25.qiuqiu.admin.vo.out.job.JobVo;
import com.php25.qiuqiu.job.dto.JobCreateDto;
import com.php25.qiuqiu.job.dto.JobDto;
import com.php25.qiuqiu.job.dto.JobUpdateDto;
import org.mapstruct.Mapper;

/**
 * @author penghuiping
 * @date 2021/7/2 10:04
 */
@Mapper(componentModel = "spring")
public interface JobVoMapper {

    /**
     * dto转vo
     *
     * @param jobDto dto
     * @return vo
     */
    JobVo toVo(JobDto jobDto);

    /**
     * vo转dto
     *
     * @param jobCreateVo vo
     * @return dto
     */
    JobCreateDto toJobCreateDto(JobCreateVo jobCreateVo);

    /**
     * vo转dto
     *
     * @param jobUpdateVo vo
     * @return dto
     */
    JobUpdateDto toJobUpdateDto(JobUpdateVo jobUpdateVo);

    /**
     * dto转vo
     *
     * @param jobDto dto
     * @return vo
     */
    JobVo toJobVo(JobDto jobDto);
}
