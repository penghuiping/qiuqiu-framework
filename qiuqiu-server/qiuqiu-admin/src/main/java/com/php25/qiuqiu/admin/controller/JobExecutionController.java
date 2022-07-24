package com.php25.qiuqiu.admin.controller;

import com.php25.common.core.dto.PageDto;
import com.php25.common.flux.web.JSONController;
import com.php25.common.flux.web.JSONResponse;
import com.php25.qiuqiu.admin.mapper.JobExecutionVoMapper;
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
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 定时任务执行计划
 *
 * @author penghuiping
 * @date 2021/3/27 00:32
 */
@RestController
@RequestMapping("/job_execution")
@RequiredArgsConstructor
@Slf4j
public class JobExecutionController extends JSONController {

    private final JobService jobService;

    private final JobExecutionVoMapper jobExecutionVoMapper;

    /**
     * 分页查询定时任务执行计划
     *
     * @ignoreParams username
     * @since v1
     */
    @PostMapping(value = "/page", headers = {"version=v1"})
    public JSONResponse<PageResultVo<JobExecutionVo>> page(@RequestAttribute String username, @Valid @RequestBody JobExecutionPageVo pageVo) {
        PageDto<JobExecutionDto> dataGrid = jobService.pageJobExecution(username, pageVo.getJobName(), pageVo.getPageNum(), pageVo.getPageSize());
        PageResultVo<JobExecutionVo> result = new PageResultVo<>();
        List<JobExecutionVo> list = dataGrid.getData().stream().map(jobExecutionVoMapper::toJobExecutionVo).collect(Collectors.toList());
        result.setData(list);
        result.setTotal(dataGrid.getTotal());
        result.setCurrentPage(pageVo.getPageNum());
        return succeed(result);
    }

    /**
     * 创建定时任务执行计划
     *
     * @ignoreParams username
     * @since v1
     */
    @AuditLog
    @PostMapping(value = "/create", headers = {"version=v1"})
    public JSONResponse<Boolean> create(@RequestAttribute String username, @Valid @RequestBody JobExecutionCreateVo jobExecution) {
        JobExecutionCreateDto jobExecutionCreateDto = jobExecutionVoMapper.toJobExecutionDto(jobExecution);
        return succeed(jobService.createJobExecution(username, jobExecutionCreateDto));
    }

    /**
     * 更新定时任务执行计划
     *
     * @ignoreParams username
     * @since v1
     */
    @AuditLog
    @PostMapping(value = "/update", headers = {"version=v1"})
    public JSONResponse<Boolean> update(@RequestAttribute String username, @Valid @RequestBody JobExecutionUpdateVo jobExecution) {
        JobExecutionUpdateDto jobExecutionUpdateDto = jobExecutionVoMapper.toJobExecutionDto(jobExecution);
        return succeed(jobService.updateJobExecution(username, jobExecutionUpdateDto));
    }

    /**
     * 删除定时任务执行计划
     *
     * @ignoreParams username
     * @since v1
     */
    @AuditLog
    @PostMapping(value = "/delete", headers = {"version=v1"})
    public JSONResponse<Boolean> delete(@RequestAttribute String username, @Valid @RequestBody JobExecutionIdVo jobExecutionIdVo) {
        return succeed(jobService.deleteJobExecution(username, jobExecutionIdVo.getId()));
    }

    /**
     * 刷新定时任务执行计划
     *
     * @ignoreParams username
     * @since v1
     */
    @AuditLog
    @PostMapping(value = "/refresh", headers = {"version=v1"})
    public JSONResponse<Boolean> refresh(@RequestAttribute String username, @Valid @RequestBody JobIdVo jobIdVo) {
        return succeed(jobService.refresh(username, jobIdVo.getJobId()));
    }

    /**
     * 刷新所有定时任务执行计划
     *
     * @ignoreParams username
     * @since v1
     */
    @AuditLog
    @PostMapping(value = "/refresh_all", headers = {"version=v1"})
    public JSONResponse<Boolean> refreshAll(@RequestAttribute String username) {
        return succeed(jobService.refreshAll(username));
    }

    /**
     * 统计定时任务执行计划加载情况
     *
     * @ignoreParams username
     * @since v1
     */
    @AuditLog
    @PostMapping(value = "/statistic",headers = {"version=v1"})
    public JSONResponse<Boolean> statistic(@RequestAttribute String username) {
        jobService.statisticLoadedJobExecutionInfo();
        return succeed(true);
    }
}
