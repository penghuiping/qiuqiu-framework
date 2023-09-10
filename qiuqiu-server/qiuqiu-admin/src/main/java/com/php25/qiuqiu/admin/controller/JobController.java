package com.php25.qiuqiu.admin.controller;

import com.google.common.collect.Lists;
import com.php25.common.core.dto.CurrentUser;
import com.php25.common.core.dto.PageDto;
import com.php25.common.web.JsonController;
import com.php25.common.web.JsonResponse;
import com.php25.common.web.RequestUtil;
import com.php25.qiuqiu.admin.copyer.JobVoCopyer;
import com.php25.qiuqiu.admin.vo.in.job.JobCreateVo;
import com.php25.qiuqiu.admin.vo.in.job.JobIdVo;
import com.php25.qiuqiu.admin.vo.in.job.JobPageVo;
import com.php25.qiuqiu.admin.vo.in.job.JobUpdateVo;
import com.php25.qiuqiu.admin.vo.out.PageResultVo;
import com.php25.qiuqiu.admin.vo.out.job.JobVo;
import com.php25.qiuqiu.job.dto.JobCreateDto;
import com.php25.qiuqiu.job.dto.JobDto;
import com.php25.qiuqiu.job.dto.JobUpdateDto;
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
 * 定时任务
 *
 * @author penghuiping
 * @date 2021/3/9 10:29
 */
@Slf4j
@Tag(name = "定时任务")
@RestController
@RequestMapping(value = "/api/job",produces = {"application/json"})
@RequiredArgsConstructor
public class JobController extends JsonController {

    private final JobService jobService;

    private final JobVoCopyer jobVoMapper;


    @Operation(summary = "分页查询定时任务列表")
    @PostMapping(value = "/page",headers = {"version=v1","jwt"})
    public JsonResponse<PageResultVo<JobVo>> page(@Valid @RequestBody JobPageVo jobPageVo) {
        PageDto<JobDto> dataGrid = jobService.page(jobPageVo.getJobName(),
                jobPageVo.getPageNum(), jobPageVo.getPageSize());
        PageResultVo<JobVo> res = new PageResultVo<>();
        List<JobVo> jobVos = dataGrid.getData().stream().map(jobVoMapper::toVo).collect(Collectors.toList());
        res.setCurrentPage(jobPageVo.getPageNum());
        res.setData(jobVos);
        res.setTotal(dataGrid.getTotal());
        return succeed(res);
    }

    @AuditLog
    @Operation(summary = "创建定时任务")
    @PostMapping(value = "/create",headers = {"version=v1","jwt"})
    public JsonResponse<Boolean> create(@Valid @RequestBody JobCreateVo jobCreateVo) {
        CurrentUser currentUser = RequestUtil.getCurrentUser();
        JobCreateDto jobCreateDto = jobVoMapper.toJobCreateDto(jobCreateVo);
        return succeed(jobService.create(currentUser.getUsername(), jobCreateDto));
    }

    @AuditLog
    @Operation(summary = "更新定时任务")
    @PostMapping(value = "/update",headers = {"version=v1","jwt"})
    public JsonResponse<Boolean> update(@Valid @RequestBody JobUpdateVo jobUpdateVo) {
        CurrentUser currentUser = RequestUtil.getCurrentUser();
        JobUpdateDto jobUpdateDto = jobVoMapper.toJobUpdateDto(jobUpdateVo);
        return succeed(jobService.update(currentUser.getUsername(), jobUpdateDto));
    }

    @AuditLog
    @Operation(summary = "删除定时任务")
    @PostMapping(value = "/delete",headers = {"version=v1","jwt"})
    public JsonResponse<Boolean> delete(@Valid @RequestBody JobIdVo jobIdVo) {
        CurrentUser currentUser = RequestUtil.getCurrentUser();
        return succeed(jobService.delete(currentUser.getUsername(), jobIdVo.getJobId()));
    }


    @Operation(summary = "获取所有任务列表")
    @PostMapping(value = "/get_all",headers = {"version=v1","jwt"})
    public JsonResponse<List<JobVo>> findAll() {
        CurrentUser currentUser = RequestUtil.getCurrentUser();
        List<JobDto> jobDtoList = jobService.findAll(currentUser.getUsername());
        if (null != jobDtoList && !jobDtoList.isEmpty()) {
            List<JobVo> res = jobDtoList.stream().map(jobVoMapper::toJobVo).collect(Collectors.toList());
            return succeed(res);
        }
        return succeed(Lists.newArrayList());
    }


}
