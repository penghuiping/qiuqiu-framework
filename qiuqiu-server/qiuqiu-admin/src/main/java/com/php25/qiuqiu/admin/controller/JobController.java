package com.php25.qiuqiu.admin.controller;

import com.google.common.collect.Lists;
import com.php25.common.core.dto.DataGridPageDto;
import com.php25.common.flux.web.JSONController;
import com.php25.common.flux.web.JSONResponse;
import com.php25.qiuqiu.admin.mapper.JobVoMapper;
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
 * 定时任务
 *
 * @author penghuiping
 * @date 2021/3/9 10:29
 */
@RestController
@RequestMapping("/job")
@RequiredArgsConstructor
@Slf4j
public class JobController extends JSONController {

    private final JobService jobService;

    private final JobVoMapper jobVoMapper;

    /**
     * 分页查询定时任务列表
     *
     * @ignoreParams username
     * @since v1
     */
    @PostMapping(value = "/page",headers = {"version=v1"})
    public JSONResponse<PageResultVo<JobVo>> page(@RequestAttribute String username, @Valid @RequestBody JobPageVo jobPageVo) {
        DataGridPageDto<JobDto> dataGrid = jobService.page(username, jobPageVo.getJobName(),
                jobPageVo.getPageNum(), jobPageVo.getPageSize());
        PageResultVo<JobVo> res = new PageResultVo<>();
        List<JobVo> jobVos = dataGrid.getData().stream().map(jobVoMapper::toVo).collect(Collectors.toList());
        res.setCurrentPage(jobPageVo.getPageNum());
        res.setData(jobVos);
        res.setTotal(dataGrid.getRecordsTotal());
        return succeed(res);
    }

    /**
     * 创建定时任务
     *
     * @ignoreParams username
     * @since v1
     */
    @AuditLog
    @PostMapping(value = "/create",headers = {"version=v1"})
    public JSONResponse<Boolean> create(@RequestAttribute String username, @Valid @RequestBody JobCreateVo jobCreateVo) {
        JobCreateDto jobCreateDto = jobVoMapper.toJobCreateDto(jobCreateVo);
        return succeed(jobService.create(username, jobCreateDto));
    }

    /**
     * 更新定时任务
     *
     * @ignoreParams username
     * @since v1
     */
    @AuditLog
    @PostMapping(value = "/update",headers = {"version=v1"})
    public JSONResponse<Boolean> update(@RequestAttribute String username, @Valid @RequestBody JobUpdateVo jobUpdateVo) {
        JobUpdateDto jobUpdateDto = jobVoMapper.toJobUpdateDto(jobUpdateVo);
        return succeed(jobService.update(username, jobUpdateDto));
    }

    /**
     * 删除定时任务
     *
     * @ignoreParams username
     * @since v1
     */
    @AuditLog
    @PostMapping(value = "/delete",headers = {"version=v1"})
    public JSONResponse<Boolean> delete(@RequestAttribute String username, @Valid @RequestBody JobIdVo jobIdVo) {
        return succeed(jobService.delete(username, jobIdVo.getJobId()));
    }

    /**
     * 获取所有任务列表
     *
     * @ignoreParams username
     * @since v1
     */
    @PostMapping(value = "/get_all",headers = {"version=v1"})
    public JSONResponse<List<JobVo>> findAll(@RequestAttribute String username) {
        List<JobDto> jobDtoList = jobService.findAll(username);
        if (null != jobDtoList && !jobDtoList.isEmpty()) {
            List<JobVo> res = jobDtoList.stream().map(jobVoMapper::toJobVo).collect(Collectors.toList());
            return succeed(res);
        }
        return succeed(Lists.newArrayList());
    }


}
