package com.php25.qiuqiu.admin.controller;

import com.php25.common.core.dto.DataGridPageDto;
import com.php25.common.flux.web.APIVersion;
import com.php25.common.flux.web.JSONController;
import com.php25.common.flux.web.JSONResponse;
import com.php25.qiuqiu.admin.vo.in.job.JobLogPageVo;
import com.php25.qiuqiu.admin.vo.out.PageResultVo;
import com.php25.qiuqiu.admin.vo.out.job.JobLogVo;
import com.php25.qiuqiu.job.dto.JobLogDto;
import com.php25.qiuqiu.job.service.JobService;
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
@RequestMapping("/job_log")
@RequiredArgsConstructor
@Log4j2
public class JobLogController extends JSONController {

    private final JobService jobService;

    @APIVersion("v1")
    @PostMapping("/page")
    public JSONResponse page(@RequestAttribute String username, @Valid @RequestBody JobLogPageVo jobLogPageVo) {
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
}
