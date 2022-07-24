package com.php25.qiuqiu.admin.controller;

import com.php25.common.core.dto.PageDto;
import com.php25.common.flux.web.JSONController;
import com.php25.common.flux.web.JSONResponse;
import com.php25.qiuqiu.admin.vo.in.role.RoleCreateVo;
import com.php25.qiuqiu.admin.vo.in.role.RoleDeleteVo;
import com.php25.qiuqiu.admin.vo.in.role.RoleDetailVo;
import com.php25.qiuqiu.admin.vo.in.role.RolePageVo;
import com.php25.qiuqiu.admin.vo.in.role.RoleUpdateVo;
import com.php25.qiuqiu.admin.mapper.RoleVoMapper;
import com.php25.qiuqiu.admin.vo.out.PageResultVo;
import com.php25.qiuqiu.admin.vo.out.resource.ResourcePermissionVo;
import com.php25.qiuqiu.admin.vo.out.role.RoleDetailOutVo;
import com.php25.qiuqiu.admin.vo.out.role.RolePageOutVo;
import com.php25.qiuqiu.admin.vo.out.role.RoleVo;
import com.php25.qiuqiu.monitor.aop.AuditLog;
import com.php25.qiuqiu.user.dto.resource.ResourcePermissionDto;
import com.php25.qiuqiu.user.dto.role.RoleCreateDto;
import com.php25.qiuqiu.user.dto.role.RoleDetailDto;
import com.php25.qiuqiu.user.dto.role.RoleDto;
import com.php25.qiuqiu.user.dto.role.RolePageDto;
import com.php25.qiuqiu.user.dto.role.RoleUpdateDto;
import com.php25.qiuqiu.user.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 角色管理
 *
 * @author penghuiping
 * @date 2021/3/6 17:20
 */
@RestController
@RequestMapping("/role")
@RequiredArgsConstructor
public class RoleController extends JSONController {

    private final RoleService roleService;

    private final RoleVoMapper roleVoMapper;

    /**
     * 获取系统中所有角色列表
     *
     * @since v1
     */
    @PostMapping(value = "/get_all",headers = {"version=v1"})
    public JSONResponse<List<RoleVo>> getAll() {
        List<RoleDto> roleDtoList = roleService.getAllRoles();
        List<RoleVo> roleVos = roleDtoList.stream().map(roleVoMapper::toVo).collect(Collectors.toList());
        return succeed(roleVos);
    }

    /**
     * 新增角色
     *
     * @since v1
     */
    @AuditLog
    @PostMapping(value = "/create",headers = {"version=v1"})
    public JSONResponse<Boolean> create(@Valid @RequestBody RoleCreateVo roleCreateVo) {
        List<ResourcePermissionVo> resourcePermissionVos = roleCreateVo.getResourcePermissions();
        List<ResourcePermissionDto> resourcePermissionDtos = new ArrayList<>();
        if (null != resourcePermissionVos && !resourcePermissionVos.isEmpty()) {
            for (ResourcePermissionVo resourcePermissionVo : resourcePermissionVos) {
                String resource = resourcePermissionVo.getResource();
                List<String> permissions = resourcePermissionVo.getPermissions();
                if (null != permissions && !permissions.isEmpty()) {
                    for (String permission : permissions) {
                        ResourcePermissionDto resourcePermissionDto = new ResourcePermissionDto();
                        resourcePermissionDto.setPermission(permission);
                        resourcePermissionDto.setResource(resource);
                        resourcePermissionDtos.add(resourcePermissionDto);
                    }
                }
            }
        }
        RoleCreateDto roleCreateDto = roleVoMapper.toDto(roleCreateVo);
        roleCreateDto.setResourcePermissions(resourcePermissionDtos);
        return succeed(roleService.create(roleCreateDto));
    }

    /**
     * 更新角色
     *
     * @since v1
     */
    @AuditLog
    @PostMapping(value = "/update",headers = "version=v1")
    public JSONResponse<Boolean> update(@Valid @RequestBody RoleUpdateVo roleUpdateVo) {
        List<ResourcePermissionVo> resourcePermissionVos = roleUpdateVo.getResourcePermissions();
        List<ResourcePermissionDto> resourcePermissionDtos = new ArrayList<>();
        if (null != resourcePermissionVos && !resourcePermissionVos.isEmpty()) {
            for (ResourcePermissionVo resourcePermissionVo : resourcePermissionVos) {
                String resource = resourcePermissionVo.getResource();
                List<String> permissions = resourcePermissionVo.getPermissions();
                if (null != permissions && !permissions.isEmpty()) {
                    for (String permission : permissions) {
                        ResourcePermissionDto resourcePermissionDto = new ResourcePermissionDto();
                        resourcePermissionDto.setPermission(permission);
                        resourcePermissionDto.setResource(resource);
                        resourcePermissionDtos.add(resourcePermissionDto);
                    }
                }
            }
        }
        RoleUpdateDto roleUpdateDto = roleVoMapper.toDto(roleUpdateVo);
        roleUpdateDto.setResourcePermissions(resourcePermissionDtos);
        return succeed(roleService.update(roleUpdateDto));
    }

    /**
     * 删除角色
     *
     * @since v1
     */
    @AuditLog
    @PostMapping(value = "/delete",headers = {"version=v1"})
    public JSONResponse<Boolean> delete(@Valid @RequestBody RoleDeleteVo roleDeleteVo) {
        return succeed(roleService.delete(roleDeleteVo.getRoleIds()));
    }

    /**
     * 获取角色信息接口
     *
     * @since v1
     */
    @PostMapping(value = "/detail",headers = {"version=v1"})
    public JSONResponse<RoleDetailOutVo> detail(@Valid @RequestBody RoleDetailVo roleDetailVo) {
        RoleDetailDto roleDetailDto = roleService.detail(roleDetailVo.getRoleId());
        RoleDetailOutVo roleDetailOutVo = roleVoMapper.toVo(roleDetailDto);
        List<ResourcePermissionDto> permissionDtos = roleDetailDto.getResourcePermissions();
        List<ResourcePermissionVo> resourcePermissionVos = new ArrayList<>();
        if (null != permissionDtos && !permissionDtos.isEmpty()) {
            for (ResourcePermissionDto resourcePermissionDto : permissionDtos) {
                String resource = resourcePermissionDto.getResource();
                List<String> permissions = new ArrayList<>();
                for (ResourcePermissionDto resourcePermissionDto0 : permissionDtos) {
                    if (resource.equals(resourcePermissionDto0.getResource())) {
                        permissions.add(resourcePermissionDto0.getPermission());
                    }
                }
                ResourcePermissionVo resourcePermissionVo = new ResourcePermissionVo();
                resourcePermissionVo.setResource(resource);
                resourcePermissionVo.setPermissions(permissions);
                resourcePermissionVos.add(resourcePermissionVo);
            }
            roleDetailOutVo.setResourcePermissions(resourcePermissionVos);
        }
        return succeed(roleDetailOutVo);
    }

    /**
     * 获取角色分页列表接口
     *
     * @since v1
     */
    @PostMapping(value = "/page",headers = {"version=v1"})
    public JSONResponse<PageResultVo<RolePageOutVo>> page(@Valid @RequestBody RolePageVo rolePageVo) {
        PageDto<RolePageDto> page = roleService.page(rolePageVo.getRoleName(), rolePageVo.getPageNum(), rolePageVo.getPageSize());
        PageResultVo<RolePageOutVo> res = new PageResultVo<>();
        List<RolePageOutVo> rolePageOutVos = page.getData().stream().map(roleVoMapper::toVo).collect(Collectors.toList());
        res.setCurrentPage(rolePageVo.getPageNum());
        res.setTotal(page.getTotal());
        res.setData(rolePageOutVos);
        return succeed(res);
    }
}
