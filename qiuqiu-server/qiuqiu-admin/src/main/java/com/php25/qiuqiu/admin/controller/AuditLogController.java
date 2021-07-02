package com.php25.qiuqiu.admin.controller;

import com.php25.common.core.dto.DataGridPageDto;
import com.php25.common.flux.web.APIVersion;
import com.php25.common.flux.web.JSONController;
import com.php25.common.flux.web.JSONResponse;
import com.php25.qiuqiu.admin.vo.in.AuditLogPageVo;
import com.php25.qiuqiu.admin.vo.mapper.AuditLogVoMapper;
import com.php25.qiuqiu.admin.vo.out.AuditLogPageOutVo;
import com.php25.qiuqiu.admin.vo.out.PageResultVo;
import com.php25.qiuqiu.monitor.dto.AuditLogDto;
import com.php25.qiuqiu.monitor.service.AuditLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 审计日志
 * @author penghuiping
 * @date 2021/3/11 15:18
 */
@RestController
@RequestMapping("/audit_log")
@RequiredArgsConstructor
public class AuditLogController extends JSONController {

    private final AuditLogService auditLogService;

    private final AuditLogVoMapper auditLogVoMapper;

    /**
     * 审计日志分页查询
     * @since v1
     */
    @APIVersion("v1")
    @PostMapping("/page")
    public JSONResponse<PageResultVo<AuditLogPageOutVo>> page(@Valid @RequestBody AuditLogPageVo auditLogPageVo) {
        DataGridPageDto<AuditLogDto> dataGrid = auditLogService.page(auditLogPageVo.getUsername(),auditLogPageVo.getPageNum(), auditLogPageVo.getPageSize());
        List<AuditLogDto> list = dataGrid.getData();
        PageResultVo<AuditLogPageOutVo> res = new PageResultVo<>();
        res.setData(list.stream().map(auditLogVoMapper::toAuditLogPageOutVo).collect(Collectors.toList()));
        res.setTotal(dataGrid.getRecordsTotal());
        res.setCurrentPage(auditLogPageVo.getPageNum());
        return succeed(res);
    }
}
