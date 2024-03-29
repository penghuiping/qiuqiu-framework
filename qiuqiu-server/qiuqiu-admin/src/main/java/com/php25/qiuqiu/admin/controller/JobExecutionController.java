package com.php25.qiuqiu.admin.controller;

import com.php25.common.core.dto.CurrentUser;
import com.php25.common.core.dto.PageDto;
import com.php25.common.web.JsonController;
import com.php25.common.web.JsonResponse;
import com.php25.common.web.RequestUtil;
import com.php25.qiuqiu.admin.copyer.JobExecutionVoCopyer;
import com.php25.qiuqiu.admin.vo.in.job.JobExecutionCreateVo;
import com.php25.qiuqiu.admin.vo.in.job.JobExecutionIdVo;
import com.php25.qiuqiu.admin.vo.in.job.JobExecutionPageVo;
import com.php25.qiuqiu.admin.vo.in.job.JobExecutionUpdateVo;
import com.php25.qiuqiu.admin.vo.in.job.JobIdVo;
import com.php25.qiuqiu.admin.vo.out.PageResultVo;
import com.php25.qiuqiu.admin.vo.out.job.JobExecutionVo;
import com.php25.qiuqiu.job.dto.JobExecutionCreateDto;
import com.php25.qiuqiu.job.dto.JobExecutionDto;
import com.php25.qiuqiu.job.dto.JobExecutionUpdateDto;
import com.php25.qiuqiu.job.service.JobService;
import com.php25.qiuqiu.monitor.aop.AuditLog;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 定时任务执行计划
 *
 * @author penghuiping
 * @date 2021/3/27 00:32
 */
@Slf4j
@Tag(name = "定时任务执行计划")
@RestController
@RequestMapping(value = "/api/job_execution",produces = {"application/json"})
@RequiredArgsConstructor
public class JobExecutionController extends JsonController {

    private final JobService jobService;

    private final JobExecutionVoCopyer jobExecutionVoMapper;


    @Operation(summary = "分页查询定时任务执行计划")
    @PostMapping(value = "/page", headers = {"version=v1","jwt"})
    public JsonResponse<PageResultVo<JobExecutionVo>> page(@Valid @RequestBody JobExecutionPageVo pageVo) {
        CurrentUser currentUser = RequestUtil.getCurrentUser();
        PageDto<JobExecutionDto> dataGrid = jobService.pageJobExecution(currentUser.getUsername(), pageVo.getJobName(), pageVo.getPageNum(), pageVo.getPageSize());
        PageResultVo<JobExecutionVo> result = new PageResultVo<>();
        List<JobExecutionVo> list = dataGrid.getData().stream().map(jobExecutionVoMapper::toJobExecutionVo).collect(Collectors.toList());
        result.setData(list);
        result.setTotal(dataGrid.getTotal());
        result.setCurrentPage(pageVo.getPageNum());
        return succeed(result);
    }

    @AuditLog
    @Operation(summary = "创建定时任务执行计划")
    @PostMapping(value = "/create", headers = {"version=v1","jwt"})
    public JsonResponse<Boolean> create(@Valid @RequestBody JobExecutionCreateVo jobExecution) {
        CurrentUser currentUser = RequestUtil.getCurrentUser();
        JobExecutionCreateDto jobExecutionCreateDto = jobExecutionVoMapper.toJobExecutionDto(jobExecution);
        return succeed(jobService.createJobExecution(currentUser.getUsername(), jobExecutionCreateDto));
    }

    @AuditLog
    @Operation(summary = "更新定时任务执行计划")
    @PostMapping(value = "/update", headers = {"version=v1","jwt"})
    public JsonResponse<Boolean> update(@Valid @RequestBody JobExecutionUpdateVo jobExecution) {
        CurrentUser currentUser = RequestUtil.getCurrentUser();
        JobExecutionUpdateDto jobExecutionUpdateDto = jobExecutionVoMapper.toJobExecutionDto(jobExecution);
        return succeed(jobService.updateJobExecution(currentUser.getUsername(), jobExecutionUpdateDto));
    }

    @AuditLog
    @Operation(summary = "删除定时任务执行计划")
    @PostMapping(value = "/delete", headers = {"version=v1","jwt"})
    public JsonResponse<Boolean> delete(@Valid @RequestBody JobExecutionIdVo jobExecutionIdVo) {
        CurrentUser currentUser = RequestUtil.getCurrentUser();
        return succeed(jobService.deleteJobExecution(currentUser.getUsername(), jobExecutionIdVo.getId()));
    }

    @AuditLog
    @Operation(summary = "刷新定时任务执行计划")
    @PostMapping(value = "/refresh", headers = {"version=v1","jwt"})
    public JsonResponse<Boolean> refresh(@Valid @RequestBody JobIdVo jobIdVo) {
        CurrentUser currentUser = RequestUtil.getCurrentUser();
        return succeed(jobService.refresh(currentUser.getUsername(), jobIdVo.getJobId()));
    }

    @AuditLog
    @Operation(summary = "刷新所有定时任务执行计划")
    @PostMapping(value = "/refresh_all", headers = {"version=v1","jwt"})
    public JsonResponse<Boolean> refreshAll() {
        CurrentUser currentUser = RequestUtil.getCurrentUser();
        return succeed(jobService.refreshAll(currentUser.getUsername()));
    }

    @AuditLog
    @Operation(summary = "统计定时任务执行计划加载情况")
    @PostMapping(value = "/statistic",headers = {"version=v1","jwt"})
    public JsonResponse<Boolean> statistic() {
        jobService.statisticLoadedJobExecutionInfo();
        return succeed(true);
    }
}
