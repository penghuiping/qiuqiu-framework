package com.php25.qiuqiu.admin.controller;

import com.php25.common.core.dto.PageDto;
import com.php25.common.flux.web.JSONController;
import com.php25.common.flux.web.JSONResponse;
import com.php25.qiuqiu.admin.mapper.JobLogVoMapper;
import com.php25.qiuqiu.admin.vo.in.job.JobLogPageVo;
import com.php25.qiuqiu.admin.vo.out.PageResultVo;
import com.php25.qiuqiu.admin.vo.out.job.JobLogVo;
import com.php25.qiuqiu.job.dto.JobLogDto;
import com.php25.qiuqiu.job.service.JobService;
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
 * 任务日志
 *
 * @author penghuiping
 * @date 2021/3/27 00:32
 */
@RestController
@RequestMapping("/job_log")
@RequiredArgsConstructor
@Slf4j
public class JobLogController extends JSONController {

    private final JobService jobService;

    private final JobLogVoMapper jobLogVoMapper;

    /**
     * 任务日志分页查询
     *
     * @ignoreParams username
     * @since v1
     */
    @PostMapping(value = "/page", headers = {"version=v1"})
    public JSONResponse<PageResultVo<JobLogVo>> page(@RequestAttribute String username, @Valid @RequestBody JobLogPageVo jobLogPageVo) {
        PageDto<JobLogDto> dataGrid = jobService.pageJobLog(username, jobLogPageVo.getJobName(), jobLogPageVo.getPageNum(), jobLogPageVo.getPageSize());
        PageResultVo<JobLogVo> result = new PageResultVo<>();
        List<JobLogDto> list = dataGrid.getData();
        List<JobLogVo> list0 = list.stream().map(jobLogVoMapper::toVo).collect(Collectors.toList());
        result.setData(list0);
        result.setTotal(dataGrid.getTotal());
        result.setCurrentPage(jobLogPageVo.getPageNum());
        return succeed(result);
    }
}
