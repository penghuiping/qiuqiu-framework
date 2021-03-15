package com.php25.qiuqiu.admin.controller;

import com.php25.common.core.dto.DataGridPageDto;
import com.php25.common.flux.web.APIVersion;
import com.php25.common.flux.web.JSONController;
import com.php25.common.flux.web.JSONResponse;
import com.php25.qiuqiu.admin.vo.in.job.JobCreateVo;
import com.php25.qiuqiu.admin.vo.in.job.JobIdVo;
import com.php25.qiuqiu.admin.vo.in.job.JobLogPageVo;
import com.php25.qiuqiu.admin.vo.out.JobLogVo;
import com.php25.qiuqiu.admin.vo.in.job.JobPageVo;
import com.php25.qiuqiu.admin.vo.in.job.JobUpdateVo;
import com.php25.qiuqiu.admin.vo.out.JobVo;
import com.php25.qiuqiu.admin.vo.out.PageResultVo;
import com.php25.qiuqiu.job.dto.JobCreateDto;
import com.php25.qiuqiu.job.dto.JobDto;
import com.php25.qiuqiu.job.dto.JobLogDto;
import com.php25.qiuqiu.job.dto.JobUpdateDto;
import com.php25.qiuqiu.job.service.JobService;
import com.php25.qiuqiu.monitor.aop.AuditLog;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.PostMapping;
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
    public JSONResponse page(@Valid @RequestBody JobPageVo jobPageVo) {
        DataGridPageDto<JobDto> dataGrid = jobService.page(jobPageVo.getJobName(), jobPageVo.getPageNum(), jobPageVo.getPageSize());
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
    public JSONResponse create(@Valid @RequestBody JobCreateVo jobCreateVo) {
        JobCreateDto jobCreateDto = new JobCreateDto();
        BeanUtils.copyProperties(jobCreateVo, jobCreateDto);
        return succeed(jobService.create(jobCreateDto));
    }

    @AuditLog
    @APIVersion("v1")
    @PostMapping("/update")
    public JSONResponse update(@Valid @RequestBody JobUpdateVo jobUpdateVo) {
        JobUpdateDto jobUpdateDto = new JobUpdateDto();
        BeanUtils.copyProperties(jobUpdateVo, jobUpdateDto);
        return succeed(jobService.update(jobUpdateDto));
    }

    @AuditLog
    @APIVersion("v1")
    @PostMapping("/delete")
    public JSONResponse delete(@Valid @RequestBody JobIdVo jobIdVo) {
        return succeed(jobService.delete(jobIdVo.getJobId()));
    }

    @AuditLog
    @APIVersion("v1")
    @PostMapping("/refresh")
    public JSONResponse refresh(@Valid @RequestBody JobIdVo jobIdVo) {
        return succeed(jobService.refresh(jobIdVo.getJobId()));
    }

    @AuditLog
    @APIVersion("v1")
    @PostMapping("/refresh_all")
    public JSONResponse refreshAll() {
        return succeed(jobService.refreshAll());
    }

    @APIVersion("v1")
    @PostMapping("/log/page")
    public JSONResponse pageJobLog(@Valid @RequestBody JobLogPageVo jobLogPageVo) {
        DataGridPageDto<JobLogDto> dataGrid = jobService.pageJobLog(jobLogPageVo.getJobId(), jobLogPageVo.getPageNum(), jobLogPageVo.getPageSize());
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
}
