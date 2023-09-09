package com.php25.qiuqiu.admin.controller;

import com.php25.common.core.dto.PageDto;
import com.php25.common.web.JsonController;
import com.php25.common.web.JsonResponse;
import com.php25.qiuqiu.admin.mapper.PermissionVoMapper;
import com.php25.qiuqiu.admin.vo.in.permission.PermissionCreateVo;
import com.php25.qiuqiu.admin.vo.in.permission.PermissionDeleteVo;
import com.php25.qiuqiu.admin.vo.in.permission.PermissionUpdateVo;
import com.php25.qiuqiu.admin.vo.out.permission.PermissionVo;
import com.php25.qiuqiu.monitor.aop.AuditLog;
import com.php25.qiuqiu.user.dto.permission.PermissionCreateDto;
import com.php25.qiuqiu.user.dto.permission.PermissionDto;
import com.php25.qiuqiu.user.service.PermissionService;
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
 * 权限管理
 *
 * @author penghuiping
 * @date 2021/3/9 13:53
 */
@Tag(name = "权限管理")
@RestController
@RequestMapping(value = "/api/permission",produces = {"application/json"})
@RequiredArgsConstructor
public class PermissionController extends JsonController {

    private final PermissionService permissionService;

    private final PermissionVoMapper permissionVoMapper;

    @Operation(summary = "获取权限列表，用于table页面展示")
    @PostMapping(value = "/page",headers = {"version=v1","jwt"})
    public JsonResponse<List<PermissionVo>> page() {
        PageDto<PermissionDto> page = permissionService.page("", 1, 100);
        List<PermissionVo> permissionVos = page.getData().stream().map(permissionVoMapper::toVo).collect(Collectors.toList());
        return succeed(permissionVos);
    }

    @Operation(summary = "获取所有有效的权限列表")
    @PostMapping(value = "/get_all",headers = {"version=v1","jwt"})
    public JsonResponse<List<PermissionVo>> getAll() {
        List<PermissionDto> permissionDtos = permissionService.getAll();
        return succeed(permissionDtos.stream().map(permissionVoMapper::toVo).collect(Collectors.toList()));
    }

    @AuditLog
    @Operation(summary = "更新权限信息")
    @PostMapping(value = "/update",headers = {"version=v1","jwt"})
    public JsonResponse<Boolean> update(@Valid @RequestBody PermissionUpdateVo permissionVo) {
        PermissionDto permissionDto = permissionVoMapper.toDto(permissionVo);
        return succeed(permissionService.update(permissionDto));
    }

    @AuditLog
    @Operation(summary = "创建权限信息")
    @PostMapping(value = "/create",headers = {"version=v1","jwt"})
    public JsonResponse<Boolean> create(@Valid @RequestBody PermissionCreateVo permissionVo) {
        PermissionCreateDto permissionDto = permissionVoMapper.toCreateDto(permissionVo);
        return succeed(permissionService.create(permissionDto));
    }

    @AuditLog
    @Operation(summary = "删除权限信息")
    @PostMapping(value = "/delete",headers = {"version=v1","jwt"})
    public JsonResponse<Boolean> delete(@Valid @RequestBody PermissionDeleteVo permissionVo) {
        return succeed(permissionService.delete(permissionVo.getPermission()));
    }
}
