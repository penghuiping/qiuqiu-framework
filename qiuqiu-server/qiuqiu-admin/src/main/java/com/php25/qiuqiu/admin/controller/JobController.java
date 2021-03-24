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
 * @author penghuiping
 * @date 2021/3/9 10:29
 */
@RestController
@RequestMapping("/job")
@RequiredArgsConstructor
@Log4j2
public class JobController extends JSONController {

    private final JobService jobService;

    @APIVersion("v1")
    @PostMapping("/page")
    public JSONResponse page(@RequestAttribute String username, @Valid @RequestBody JobPageVo jobPageVo) {
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

    @AuditLog
    @APIVersion("v1")
    @PostMapping("/create")
    public JSONResponse create(@RequestAttribute String username, @Valid @RequestBody JobCreateVo jobCreateVo) {
        JobCreateDto jobCreateDto = new JobCreateDto();
        BeanUtils.copyProperties(jobCreateVo, jobCreateDto);
        return succeed(jobService.create(username, jobCreateDto));
    }

    @AuditLog
    @APIVersion("v1")
    @PostMapping("/update")
    public JSONResponse update(@RequestAttribute String username, @Valid @RequestBody JobUpdateVo jobUpdateVo) {
        JobUpdateDto jobUpdateDto = new JobUpdateDto();
        BeanUtils.copyProperties(jobUpdateVo, jobUpdateDto);
        return succeed(jobService.update(username, jobUpdateDto));
    }

    @AuditLog
    @APIVersion("v1")
    @PostMapping("/delete")
    public JSONResponse delete(@RequestAttribute String username, @Valid @RequestBody JobIdVo jobIdVo) {
        return succeed(jobService.delete(username, jobIdVo.getJobId()));
    }

    @APIVersion("v1")
    @PostMapping("/find_all")
    public JSONResponse findAll(@RequestAttribute String username) {
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

    @APIVersion("v1")
    @PostMapping("/log/page")
    public JSONResponse pageJobLog(@RequestAttribute String username, @Valid @RequestBody JobLogPageVo jobLogPageVo) {
        DataGridPageDto<JobLogDto> dataGrid = jobService.pageJobLog(username, jobLogPageVo.getJobId(), jobLogPageVo.getPageNum(), jobLogPageVo.getPageSize());
        PageResultVo<JobLogVo> result = new PageResultVo<>();
        List<JobLogDto> list = dataGrid.getData();
        List<JobLogVo> list0 = list.stream().map(jobLogDto -> {
            JobLogVo jobLogVo = new JobLogVo();
            BeanUtils.copyProperties(jobLogDto, jobLogVo);
            return jobLogVo;
        }).collect(Collectors.toList());
        result.setData(list0);
        result.setTotal(dataGrid.getRecordsTotal());
        result.setCurrentPage(jobLogPageVo.getPageNum());
        return succeed(result);
    }

    @APIVersion("v1")
    @PostMapping("/execution/page")
    public JSONResponse pageExecution(@RequestAttribute String username, @Valid @RequestBody JobExecutionPageVo pageVo) {
        DataGridPageDto<JobExecutionDto> dataGrid = jobService.pageJobExecution(username, pageVo.getJobName(), pageVo.getPageNum(), pageVo.getPageSize());
        PageResultVo<JobExecutionVo> result = new PageResultVo<>();
        List<JobExecutionVo> list = dataGrid.getData().stream().map(jobExecutionDto -> {
            JobExecutionVo jobExecutionVo = new JobExecutionVo();
            BeanUtils.copyProperties(jobExecutionDto, jobExecutionVo);
            return jobExecutionVo;
        }).collect(Collectors.toList());
        result.setData(list);
        result.setTotal(dataGrid.getRecordsTotal());
        result.setCurrentPage(pageVo.getPageNum());
        return succeed(result);
    }

    @AuditLog
    @APIVersion("v1")
    @PostMapping("/execution/create")
    public JSONResponse createExecution(@RequestAttribute String username, @Valid @RequestBody JobExecutionCreateDto jobExecution) {
        return succeed(jobService.createJobExecution(username, jobExecution));
    }

    @AuditLog
    @APIVersion("v1")
    @PostMapping("/execution/update")
    public JSONResponse updateExecution(@RequestAttribute String username, @Valid @RequestBody JobExecutionUpdateDto jobExecution) {
        return succeed(jobService.updateJobExecution(username, jobExecution));
    }

    @AuditLog
    @APIVersion("v1")
    @PostMapping("/execution/delete")
    public JSONResponse deleteExecution(@RequestAttribute String username, @Valid @RequestBody JobExecutionIdVo jobExecutionIdVo) {
        return succeed(jobService.deleteJobExecution(username, jobExecutionIdVo.getId()));
    }

    @AuditLog
    @APIVersion("v1")
    @PostMapping("/execution/refresh")
    public JSONResponse refreshExecution(@RequestAttribute String username, @Valid @RequestBody JobIdVo jobIdVo) {
        return succeed(jobService.refresh(username, jobIdVo.getJobId()));
    }

    @AuditLog
    @APIVersion("v1")
    @PostMapping("/execution/refresh_all")
    public JSONResponse refreshAllExecution(@RequestAttribute String username) {
        return succeed(jobService.refreshAll(username));
    }

    @AuditLog
    @APIVersion("v1")
    @PostMapping("/execution/statistic")
    public JSONResponse statistic(@RequestAttribute String username) {
        jobService.statisticLoadedJobExecutionInfo();
        return succeed(true);
    }
}
