package com.php25.qiuqiu.admin.controller;

import com.php25.common.core.dto.PageDto;
import com.php25.common.web.JsonController;
import com.php25.common.web.JsonResponse;
import com.php25.qiuqiu.admin.copyer.AuditLogVoCopyer;
import com.php25.qiuqiu.admin.vo.in.AuditLogPageVo;
import com.php25.qiuqiu.admin.vo.out.AuditLogPageOutVo;
import com.php25.qiuqiu.admin.vo.out.PageResultVo;
import com.php25.qiuqiu.monitor.dto.AuditLogDto;
import com.php25.qiuqiu.monitor.service.AuditLogService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 审计日志
 * @author penghuiping
 * @date 2021/3/11 15:18
 */
@Tag(name = "审计日志")
@RestController
@RequestMapping(value = "/api/audit_log",produces = {"application/json"})
@RequiredArgsConstructor
public class AuditLogController extends JsonController {

    private final AuditLogService auditLogService;

    private final AuditLogVoCopyer auditLogVoMapper;


    @Operation(summary = "审计日志分页查询")
    @PostMapping(value = "/page",headers = {"version=v1","jwt"})
    public JsonResponse<PageResultVo<AuditLogPageOutVo>> page(@Valid @RequestBody AuditLogPageVo auditLogPageVo) {
        PageDto<AuditLogDto> dataGrid = auditLogService.page(auditLogPageVo.getUsername(),auditLogPageVo.getPageNum(), auditLogPageVo.getPageSize());
        List<AuditLogDto> list = dataGrid.getData();
        PageResultVo<AuditLogPageOutVo> res = new PageResultVo<>();
        res.setData(list.stream().map(auditLogVoMapper::toAuditLogPageOutVo).collect(Collectors.toList()));
        res.setTotal(dataGrid.getTotal());
        res.setCurrentPage(auditLogPageVo.getPageNum());
        return succeed(res);
    }
}
