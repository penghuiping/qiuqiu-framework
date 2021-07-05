package com.php25.qiuqiu.job.mapper;

import com.php25.qiuqiu.job.dto.JobCreateDto;
import com.php25.qiuqiu.job.dto.JobDto;
import com.php25.qiuqiu.job.dto.JobExecutionCreateDto;
import com.php25.qiuqiu.job.dto.JobExecutionDto;
import com.php25.qiuqiu.job.dto.JobExecutionUpdateDto;
import com.php25.qiuqiu.job.dto.JobLogCreateDto;
import com.php25.qiuqiu.job.dto.JobLogDto;
import com.php25.qiuqiu.job.dto.JobUpdateDto;
import com.php25.qiuqiu.job.entity.JobExecution;
import com.php25.qiuqiu.job.entity.JobLog;
import com.php25.qiuqiu.job.entity.JobModel;
import org.mapstruct.Mapper;

/**
 * @author penghuiping
 * @date 2021/7/5 13:58
 */
@Mapper(componentModel = "spring")
public interface JobDtoMapper {

    /**
     * entity转dto
     *
     * @param jobModel entity
     * @return dto
     */
    JobDto toDto(JobModel jobModel);

    /**
     * dto转entity
     *
     * @param jobDto dto
     * @return entity
     */
    JobModel toEntity(JobDto jobDto);

    /**
     * dto转entity
     *
     * @param jobDto dto
     * @return entity
     */
    JobModel toEntity(JobCreateDto jobDto);

    /**
     * dto转entity
     *
     * @param jobDto dto
     * @return entity
     */
    JobModel toEntity(JobUpdateDto jobDto);

    /**
     * dto转entity
     *
     * @param jobLogDto dto
     * @return entity
     */
    JobLog toEntity0(JobLogDto jobLogDto);

    /**
     * dto转entity
     *
     * @param jobLogDto dto
     * @return entity
     */
    JobLog toEntity0(JobLogCreateDto jobLogDto);


    /**
     * entity转dto
     *
     * @param jobLog entity
     * @return dto
     */
    JobLogDto toDto0(JobLog jobLog);

    /**
     * dto转entity
     *
     * @param jobExecutionCreateDto dto
     * @return entity
     */
    JobExecution toEntity1(JobExecutionCreateDto jobExecutionCreateDto);

    /**
     * dto转entity
     *
     * @param jobExecutionUpdateDto dto
     * @return entity
     */
    JobExecution toEntity1(JobExecutionUpdateDto jobExecutionUpdateDto);

    /**
     * entity转dto
     *
     * @param jobExecution entity
     * @return dto
     */
    JobExecutionDto toDto1(JobExecution jobExecution);
}
