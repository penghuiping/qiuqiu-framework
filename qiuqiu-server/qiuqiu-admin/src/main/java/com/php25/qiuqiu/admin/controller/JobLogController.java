package com.php25.qiuqiu.admin.controller;

import com.php25.common.core.dto.CurrentUser;
import com.php25.common.core.dto.PageDto;
import com.php25.common.web.JsonController;
import com.php25.common.web.JsonResponse;
import com.php25.common.web.RequestUtil;
import com.php25.qiuqiu.admin.mapper.JobLogVoMapper;
import com.php25.qiuqiu.admin.vo.in.job.JobLogPageVo;
import com.php25.qiuqiu.admin.vo.out.PageResultVo;
import com.php25.qiuqiu.admin.vo.out.job.JobLogVo;
import com.php25.qiuqiu.job.dto.JobLogDto;
import com.php25.qiuqiu.job.service.JobService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 任务日志
 *
 * @author penghuiping
 * @date 2021/3/27 00:32
 */
@Slf4j
@Api(tags = "任务日志")
@RestController
@RequestMapping(value = "/api/job_log",produces = {"application/json"})
@RequiredArgsConstructor
public class JobLogController extends JsonController {

    private final JobService jobService;

    private final JobLogVoMapper jobLogVoMapper;


    @ApiOperation("任务日志分页查询")
    @PostMapping(value = "/page", headers = {"version=v1","jwt"})
    public JsonResponse<PageResultVo<JobLogVo>> page(@Valid @RequestBody JobLogPageVo jobLogPageVo) {
        CurrentUser currentUser = RequestUtil.getCurrentUser();
        PageDto<JobLogDto> dataGrid = jobService.pageJobLog(currentUser.getUsername(), jobLogPageVo.getJobName(), jobLogPageVo.getPageNum(), jobLogPageVo.getPageSize());
        PageResultVo<JobLogVo> result = new PageResultVo<>();
        List<JobLogDto> list = dataGrid.getData();
        List<JobLogVo> list0 = list.stream().map(jobLogVoMapper::toVo).collect(Collectors.toList());
        result.setData(list0);
        result.setTotal(dataGrid.getTotal());
        result.setCurrentPage(jobLogPageVo.getPageNum());
        return succeed(result);
    }
}
