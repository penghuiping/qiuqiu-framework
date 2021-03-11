package com.php25.qiuqiu.admin.controller;

import com.php25.common.core.dto.DataGridPageDto;
import com.php25.common.flux.web.APIVersion;
import com.php25.common.flux.web.JSONController;
import com.php25.common.flux.web.JSONResponse;
import com.php25.qiuqiu.admin.vo.in.permission.PermissionCreateVo;
import com.php25.qiuqiu.admin.vo.in.permission.PermissionDeleteVo;
import com.php25.qiuqiu.admin.vo.in.permission.PermissionUpdateVo;
import com.php25.qiuqiu.admin.vo.out.permission.PermissionVo;
import com.php25.qiuqiu.admin.vo.out.TreeVo;
import com.php25.qiuqiu.monitor.aop.AuditLog;
import com.php25.qiuqiu.user.dto.permission.PermissionCreateDto;
import com.php25.qiuqiu.user.dto.permission.PermissionDto;
import com.php25.qiuqiu.user.service.PermissionService;
import io.github.yedaxia.apidocs.ApiDoc;
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
 * @author penghuiping
 * @date 2021/3/9 13:53
 */
@RestController
@RequestMapping("/permission")
@RequiredArgsConstructor
public class PermissionController extends JSONController {

    private final PermissionService permissionService;

    /**
     * 获取系统中所有内置权限
     */
    @ApiDoc(result = TreeVo.class, url = "/qiuqiu_admin/v1/permission/getAll")
    @APIVersion("v1")
    @PostMapping("/getAll")
    public JSONResponse getAll(@RequestAttribute @NotBlank String username) {
        List<PermissionDto> permissions = permissionService.getAll();
        List<TreeVo> permissionVos = permissions.stream().map(permissionDto -> {
            TreeVo permissionVo = new TreeVo();
            permissionVo.setId(permissionDto.getId() + "");
            permissionVo.setValue(permissionDto.getId() + "");
            permissionVo.setLabel(permissionDto.getDescription());
            return permissionVo;
        }).collect(Collectors.toList());
        return succeed(permissionVos);
    }

    /**
     * 获取权限列表，用于table页面展示
     */
    @ApiDoc(result = PermissionVo.class, url = "/qiuqiu_admin/v1/permission/page")
    @APIVersion("v1")
    @PostMapping("/page")
    public JSONResponse page(@RequestAttribute @NotBlank String username) {
        DataGridPageDto<PermissionDto> page = permissionService.page("", 1, 1000);
        List<PermissionVo> permissionVos = page.getData().stream().map(permissionDto -> {
            PermissionVo permissionVo = new PermissionVo();
            BeanUtils.copyProperties(permissionDto, permissionVo);
            return permissionVo;
        }).collect(Collectors.toList());
        return succeed(permissionVos);
    }

    /**
     * 更新权限信息
     * @param permissionVo 权限更新数据
     */
    @AuditLog
    @ApiDoc(stringResult = "true: 更新权限成功", url = "/qiuqiu_admin/v1/permission/update")
    @APIVersion("v1")
    @PostMapping("/update")
    public JSONResponse update(@Valid @RequestBody PermissionUpdateVo permissionVo) {
        PermissionDto permissionDto = new PermissionDto();
        BeanUtils.copyProperties(permissionVo, permissionDto);
        return succeed(permissionService.update(permissionDto));
    }

    /**
     * 创建权限信息
     * @param permissionVo 权限创建数据
     */
    @AuditLog
    @ApiDoc(stringResult = "true: 创建权限成功", url = "/qiuqiu_admin/v1/permission/create")
    @APIVersion("v1")
    @PostMapping("/create")
    public JSONResponse create(@Valid @RequestBody PermissionCreateVo permissionVo) {
        PermissionCreateDto permissionDto = new PermissionCreateDto();
        BeanUtils.copyProperties(permissionVo, permissionDto);
        return succeed(permissionService.create(permissionDto));
    }

    /**
     * 删除权限信息
     * @param permissionVo 权限删除数据
     */
    @AuditLog
    @ApiDoc(stringResult = "true: 删除权限成功", url = "/qiuqiu_admin/v1/permission/delete")
    @APIVersion("v1")
    @PostMapping("/delete")
    public JSONResponse delete(@Valid @RequestBody PermissionDeleteVo permissionVo) {
        return succeed(permissionService.delete(permissionVo.getPermissionIds()));
    }
}
