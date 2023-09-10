package com.php25.qiuqiu.admin.copyer;

import com.php25.qiuqiu.admin.vo.in.job.JobExecutionCreateVo;
import com.php25.qiuqiu.admin.vo.in.job.JobExecutionUpdateVo;
import com.php25.qiuqiu.admin.vo.out.job.JobExecutionVo;
import com.php25.qiuqiu.job.dto.JobExecutionCreateDto;
import com.php25.qiuqiu.job.dto.JobExecutionDto;
import com.php25.qiuqiu.job.dto.JobExecutionUpdateDto;
import org.mapstruct.Mapper;

/**
 * @author penghuiping
 * @date 2021/7/2 10:17
 */
@Mapper(componentModel = "spring")
public interface JobExecutionVoCopyer {

    /**
     * dto转成vo
     *
     * @param jobExecutionDto dto
     * @return vo
     */
    JobExecutionVo toJobExecutionVo(JobExecutionDto jobExecutionDto);

    /**
     * vo转dto
     *
     * @param jobExecutionCreateVo vo
     * @return dto
     */
    JobExecutionCreateDto toJobExecutionDto(JobExecutionCreateVo jobExecutionCreateVo);

    /**
     * vo转dto
     *
     * @param jobExecutionUpdateVo vo
     * @return dto
     */
    JobExecutionUpdateDto toJobExecutionDto(JobExecutionUpdateVo jobExecutionUpdateVo);
}
