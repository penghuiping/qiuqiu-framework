package com.php25.qiuqiu.admin.controller;

import com.php25.common.core.dto.DataGridPageDto;
import com.php25.common.flux.web.APIVersion;
import com.php25.common.flux.web.JSONController;
import com.php25.common.flux.web.JSONResponse;
import com.php25.qiuqiu.admin.vo.in.permission.PermissionCreateVo;
import com.php25.qiuqiu.admin.vo.in.permission.PermissionDeleteVo;
import com.php25.qiuqiu.admin.vo.in.permission.PermissionUpdateVo;
import com.php25.qiuqiu.admin.vo.out.permission.PermissionVo;
import com.php25.qiuqiu.monitor.aop.AuditLog;
import com.php25.qiuqiu.user.dto.permission.PermissionCreateDto;
import com.php25.qiuqiu.user.dto.permission.PermissionDto;
import com.php25.qiuqiu.user.service.PermissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 权限管理
 * @author penghuiping
 * @date 2021/3/9 13:53
 */
@RestController
@RequestMapping("/permission")
@RequiredArgsConstructor
public class PermissionController extends JSONController {

    private final PermissionService permissionService;


    /**
     * 获取权限列表，用于table页面展示
     * @ignoreParams username
     * @since v1
     */
    @APIVersion("v1")
    @PostMapping("/page")
    public JSONResponse<List<PermissionVo>> page(@RequestAttribute @NotBlank String username) {
        DataGridPageDto<PermissionDto> page = permissionService.page("", 1, 100);
        List<PermissionVo> permissionVos = page.getData().stream().map(permissionDto -> {
            PermissionVo permissionVo = new PermissionVo();
            BeanUtils.copyProperties(permissionDto, permissionVo);
            return permissionVo;
        }).collect(Collectors.toList());
        return succeed(permissionVos);
    }

    /**
     * 更新权限信息
     * @since v1
     */
    @AuditLog
    @APIVersion("v1")
    @PostMapping("/update")
    public JSONResponse<Boolean> update(@Valid @RequestBody PermissionUpdateVo permissionVo) {
        PermissionDto permissionDto = new PermissionDto();
        BeanUtils.copyProperties(permissionVo, permissionDto);
        return succeed(permissionService.update(permissionDto));
    }

    /**
     * 创建权限信息
     * @since v1
     */
    @AuditLog
    @APIVersion("v1")
    @PostMapping("/create")
    public JSONResponse<Boolean> create(@Valid @RequestBody PermissionCreateVo permissionVo) {
        PermissionCreateDto permissionDto = new PermissionCreateDto();
        BeanUtils.copyProperties(permissionVo, permissionDto);
        return succeed(permissionService.create(permissionDto));
    }

    /**
     * 删除权限信息
     * @since v1
     */
    @AuditLog
    @APIVersion("v1")
    @PostMapping("/delete")
    public JSONResponse<Boolean> delete(@Valid @RequestBody PermissionDeleteVo permissionVo) {
        return succeed(permissionService.delete(permissionVo.getPermission()));
    }
}
