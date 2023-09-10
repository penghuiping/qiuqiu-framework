package com.php25.qiuqiu.admin.controller;

import com.php25.common.core.dto.PageDto;
import com.php25.common.web.JsonController;
import com.php25.common.web.JsonResponse;
import com.php25.qiuqiu.admin.copyer.SystemLogVoCopyer;
import com.php25.qiuqiu.admin.vo.in.AuditLogPageVo;
import com.php25.qiuqiu.admin.vo.in.SystemLogPageVo;
import com.php25.qiuqiu.admin.vo.out.AuditLogPageOutVo;
import com.php25.qiuqiu.admin.vo.out.PageResultVo;
import com.php25.qiuqiu.admin.vo.out.SystemLogPageOutVo;
import com.php25.qiuqiu.monitor.dto.AuditLogDto;
import com.php25.qiuqiu.monitor.dto.SystemLogDto;
import com.php25.qiuqiu.monitor.service.SystemLogService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author penghuiping
 * @date 2023/9/10 22:38
 */
@Tag(name = "系统日志")
@RestController
@RequestMapping(value = "/api/system_log",produces = {"application/json"})
@RequiredArgsConstructor
public class SystemLogController extends JsonController {

    private final SystemLogService systemLogService;

    private final SystemLogVoCopyer systemLogVoCopyer;

    @Operation(summary = "系统日志分页查询")
    @PostMapping(value = "/page",headers = {"version=v1","jwt"})
    public JsonResponse<PageResultVo<SystemLogPageOutVo>> page(@Valid @RequestBody SystemLogPageVo systemLogPageVo) {
        PageDto<SystemLogDto> dataGrid = systemLogService.page(systemLogPageVo.getKeywords(),systemLogPageVo.getPageNum(), systemLogPageVo.getPageSize());
        List<SystemLogDto> list = dataGrid.getData();
        PageResultVo<SystemLogPageOutVo> res = new PageResultVo<>();
        res.setData(list.stream().map(systemLogVoCopyer::toSystemLogPageOutVo).collect(Collectors.toList()));
        res.setTotal(dataGrid.getTotal());
        res.setCurrentPage(systemLogPageVo.getPageNum());
        return succeed(res);
    }

}
