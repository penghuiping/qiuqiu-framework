package com.php25.qiuqiu.admin.controller;

import com.google.common.collect.Lists;
import com.php25.common.core.dto.DataGridPageDto;
import com.php25.common.flux.web.APIVersion;
import com.php25.common.flux.web.JSONController;
import com.php25.common.flux.web.JSONResponse;
import com.php25.qiuqiu.admin.vo.in.job.JobCreateVo;
import com.php25.qiuqiu.admin.vo.in.job.JobExecutionIdVo;
import com.php25.qiuqiu.admin.vo.in.job.JobExecutionPageVo;
import com.php25.qiuqiu.admin.vo.in.job.JobIdVo;
import com.php25.qiuqiu.admin.vo.in.job.JobLogPageVo;
import com.php25.qiuqiu.admin.vo.out.job.JobExecutionVo;
import com.php25.qiuqiu.admin.vo.out.job.JobLogVo;
import com.php25.qiuqiu.admin.vo.in.job.JobPageVo;
import com.php25.qiuqiu.admin.vo.in.job.JobUpdateVo;
import com.php25.qiuqiu.admin.vo.out.job.JobVo;
import com.php25.qiuqiu.admin.vo.out.PageResultVo;
import com.php25.qiuqiu.job.dto.JobCreateDto;
import com.php25.qiuqiu.job.dto.JobDto;
import com.php25.qiuqiu.job.dto.JobExecutionCreateDto;
import com.php25.qiuqiu.job.dto.JobExecutionDto;
import com.php25.qiuqiu.job.dto.JobExecutionUpdateDto;
import com.php25.qiuqiu.job.dto.JobLogDto;
import com.php25.qiuqiu.job.dto.JobUpdateDto;
import com.php25.qiuqiu.job.service.JobService;
import com.php25.qiuqiu.monitor.aop.AuditLog;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 定时任务
 *
 * @author penghuiping
 * @date 2021/3/9 10:29
 */
@RestController
@RequestMapping("/job")
@RequiredArgsConstructor
@Log4j2
public class JobController extends JSONController {

    private final JobService jobService;

    /**
     * 分页查询定时任务列表
     * @ignoreParams username
     * @since v1
     */
    @APIVersion("v1")
    @PostMapping("/page")
    public JSONResponse<PageResultVo<JobVo>> page(@RequestAttribute String username, @Valid @RequestBody JobPageVo jobPageVo) {
        DataGridPageDto<JobDto> dataGrid = jobService.page(username, jobPageVo.getJobName(), jobPageVo.getPageNum(), jobPageVo.getPageSize());
        PageResultVo<JobVo> res = new PageResultVo<>();
        List<JobVo> jobVos = dataGrid.getData().stream().map(jobDto -> {
            JobVo jobVo = new JobVo();
            BeanUtils.copyProperties(jobDto, jobVo);
            return jobVo;
        }).collect(Collectors.toList());
        res.setCurrentPage(jobPageVo.getPageNum());
        res.setData(jobVos);
        res.setTotal(dataGrid.getRecordsTotal());
        return succeed(res);
    }

    /**
     * 创建定时任务
     * @ignoreParams username
     * @since v1
     */
    @AuditLog
    @APIVersion("v1")
    @PostMapping("/create")
    public JSONResponse<Boolean> create(@RequestAttribute String username, @Valid @RequestBody JobCreateVo jobCreateVo) {
        JobCreateDto jobCreateDto = new JobCreateDto();
        BeanUtils.copyProperties(jobCreateVo, jobCreateDto);
        return succeed(jobService.create(username, jobCreateDto));
    }

    /**
     * 更新定时任务
     * @ignoreParams username
     * @since v1
     */
    @AuditLog
    @APIVersion("v1")
    @PostMapping("/update")
    public JSONResponse<Boolean> update(@RequestAttribute String username, @Valid @RequestBody JobUpdateVo jobUpdateVo) {
        JobUpdateDto jobUpdateDto = new JobUpdateDto();
        BeanUtils.copyProperties(jobUpdateVo, jobUpdateDto);
        return succeed(jobService.update(username, jobUpdateDto));
    }

    /**
     * 删除定时任务
     * @ignoreParams username
     * @since v1
     */
    @AuditLog
    @APIVersion("v1")
    @PostMapping("/delete")
    public JSONResponse<Boolean> delete(@RequestAttribute String username, @Valid @RequestBody JobIdVo jobIdVo) {
        return succeed(jobService.delete(username, jobIdVo.getJobId()));
    }

    /**
     * 获取所有任务列表
     * @ignoreParams username
     * @since v1
     */
    @APIVersion("v1")
    @PostMapping("/get_all")
    public JSONResponse<List<JobVo>> findAll(@RequestAttribute String username) {
        List<JobDto> jobDtoList = jobService.findAll(username);
        if (null != jobDtoList && !jobDtoList.isEmpty()) {
            List<JobVo> res = jobDtoList.stream().map(jobDto -> {
                JobVo jobVo = new JobVo();
                BeanUtils.copyProperties(jobDto, jobVo);
                return jobVo;
            }).collect(Collectors.toList());
            return succeed(res);
        }
        return succeed(Lists.newArrayList());
    }


}
