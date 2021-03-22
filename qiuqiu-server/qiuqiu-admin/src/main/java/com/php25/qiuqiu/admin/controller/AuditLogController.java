package com.php25.qiuqiu.admin.controller;

import com.php25.common.core.dto.DataGridPageDto;
import com.php25.common.flux.web.APIVersion;
import com.php25.common.flux.web.JSONController;
import com.php25.common.flux.web.JSONResponse;
import com.php25.qiuqiu.admin.vo.in.AuditLogPageVo;
import com.php25.qiuqiu.admin.vo.out.AuditLogPageOutVo;
import com.php25.qiuqiu.admin.vo.out.PageResultVo;
import com.php25.qiuqiu.admin.vo.out.role.RolePageOutVo;
import com.php25.qiuqiu.monitor.dto.AuditLogDto;
import com.php25.qiuqiu.monitor.service.AuditLogService;
import io.github.yedaxia.apidocs.ApiDoc;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author penghuiping
 * @date 2021/3/11 15:18
 */
@RestController
@RequestMapping("/audit_log")
@RequiredArgsConstructor
public class AuditLogController extends JSONController {

    private final AuditLogService auditLogService;

    /**
     * 审计日志分页查询
     *
     * @param auditLogPageVo 分页信息
     */
    @ApiDoc(result = RolePageOutVo.class, url = "/qiuqiu_admin/v1/audit_log/page")
    @APIVersion("v1")
    @PostMapping("/page")
    public JSONResponse page(@Valid @RequestBody AuditLogPageVo auditLogPageVo) {
        DataGridPageDto<AuditLogDto> dataGrid = auditLogService.page(auditLogPageVo.getUsername(),auditLogPageVo.getPageNum(), auditLogPageVo.getPageSize());
        List<AuditLogDto> list = dataGrid.getData();
        PageResultVo<AuditLogPageOutVo> res = new PageResultVo<>();
        res.setData(list.stream().map(auditLogDto -> {
            AuditLogPageOutVo auditLogPageOutVo = new AuditLogPageOutVo();
            BeanUtils.copyProperties(auditLogDto, auditLogPageOutVo);
            return auditLogPageOutVo;
        }).collect(Collectors.toList()));
        res.setTotal(dataGrid.getRecordsTotal());
        res.setCurrentPage(auditLogPageVo.getPageNum());
        return succeed(res);
    }
}
