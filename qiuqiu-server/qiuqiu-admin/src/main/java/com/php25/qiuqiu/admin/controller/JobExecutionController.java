package com.php25.qiuqiu.admin.controller;

import com.php25.common.core.dto.DataGridPageDto;
import com.php25.common.flux.web.APIVersion;
import com.php25.common.flux.web.JSONController;
import com.php25.common.flux.web.JSONResponse;
import com.php25.qiuqiu.admin.vo.in.job.JobExecutionIdVo;
import com.php25.qiuqiu.admin.vo.in.job.JobExecutionPageVo;
import com.php25.qiuqiu.admin.vo.in.job.JobIdVo;
import com.php25.qiuqiu.admin.vo.out.PageResultVo;
import com.php25.qiuqiu.admin.vo.out.job.JobExecutionVo;
import com.php25.qiuqiu.job.dto.JobExecutionCreateDto;
import com.php25.qiuqiu.job.dto.JobExecutionDto;
import com.php25.qiuqiu.job.dto.JobExecutionUpdateDto;
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
 * @date 2021/3/27 00:32
 */
@RestController
@RequestMapping("/job_execution")
@RequiredArgsConstructor
@Log4j2
public class JobExecutionController extends JSONController {

    private final JobService jobService;

    @APIVersion("v1")
    @PostMapping("/page")
    public JSONResponse page(@RequestAttribute String username, @Valid @RequestBody JobExecutionPageVo pageVo) {
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
    @PostMapping("/create")
    public JSONResponse create(@RequestAttribute String username, @Valid @RequestBody JobExecutionCreateDto jobExecution) {
        return succeed(jobService.createJobExecution(username, jobExecution));
    }

    @AuditLog
    @APIVersion("v1")
    @PostMapping("/update")
    public JSONResponse update(@RequestAttribute String username, @Valid @RequestBody JobExecutionUpdateDto jobExecution) {
        return succeed(jobService.updateJobExecution(username, jobExecution));
    }

    @AuditLog
    @APIVersion("v1")
    @PostMapping("/delete")
    public JSONResponse delete(@RequestAttribute String username, @Valid @RequestBody JobExecutionIdVo jobExecutionIdVo) {
        return succeed(jobService.deleteJobExecution(username, jobExecutionIdVo.getId()));
    }

    @AuditLog
    @APIVersion("v1")
    @PostMapping("/refresh")
    public JSONResponse refresh(@RequestAttribute String username, @Valid @RequestBody JobIdVo jobIdVo) {
        return succeed(jobService.refresh(username, jobIdVo.getJobId()));
    }

    @AuditLog
    @APIVersion("v1")
    @PostMapping("/refresh_all")
    public JSONResponse refreshAll(@RequestAttribute String username) {
        return succeed(jobService.refreshAll(username));
    }

    @AuditLog
    @APIVersion("v1")
    @PostMapping("/statistic")
    public JSONResponse statistic(@RequestAttribute String username) {
        jobService.statisticLoadedJobExecutionInfo();
        return succeed(true);
    }
}
