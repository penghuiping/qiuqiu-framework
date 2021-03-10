package com.php25.qiuqiu.admin.controller;

import com.php25.common.core.dto.DataGridPageDto;
import com.php25.common.flux.web.APIVersion;
import com.php25.common.flux.web.JSONController;
import com.php25.common.flux.web.JSONResponse;
import com.php25.qiuqiu.admin.vo.in.role.RoleCreateVo;
import com.php25.qiuqiu.admin.vo.in.role.RoleDeleteVo;
import com.php25.qiuqiu.admin.vo.in.role.RoleDetailVo;
import com.php25.qiuqiu.admin.vo.in.role.RolePageVo;
import com.php25.qiuqiu.admin.vo.in.role.RoleUpdateVo;
import com.php25.qiuqiu.admin.vo.out.PageResultVo;
import com.php25.qiuqiu.admin.vo.out.role.RoleDetailOutVo;
import com.php25.qiuqiu.admin.vo.out.role.RolePageOutVo;
import com.php25.qiuqiu.admin.vo.out.role.RoleVo;
import com.php25.qiuqiu.user.dto.permission.PermissionDto;
import com.php25.qiuqiu.user.dto.role.RoleCreateDto;
import com.php25.qiuqiu.user.dto.role.RoleDetailDto;
import com.php25.qiuqiu.user.dto.role.RoleDto;
import com.php25.qiuqiu.user.dto.role.RolePageDto;
import com.php25.qiuqiu.user.dto.role.RoleUpdateDto;
import com.php25.qiuqiu.user.service.RoleService;
import io.github.yedaxia.apidocs.ApiDoc;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author penghuiping
 * @date 2021/3/6 17:20
 */
@RestController
@RequestMapping("/role")
@RequiredArgsConstructor
public class RoleController extends JSONController {

    private final RoleService roleService;

    /**
     * 获取系统中所有角色列表
     */
    @ApiDoc(result = RoleVo.class, url = "/qiuqiu_admin/v1/role/getAll")
    @APIVersion("v1")
    @PostMapping("/getAll")
    public JSONResponse getAll() {
        List<RoleDto> roleDtoList = roleService.getAllRoles();
        List<RoleVo> roleVos = roleDtoList.stream().map(roleDto -> {
            RoleVo roleVo = new RoleVo();
            BeanUtils.copyProperties(roleDto, roleVo);
            return roleVo;
        }).collect(Collectors.toList());
        return succeed(roleVos);
    }

    /**
     * 新增角色
     * @param roleCreateVo 新增角色信息
     */
    @ApiDoc(stringResult = "true:创建角色成功", url = "/qiuqiu_admin/v1/role/create")
    @APIVersion("v1")
    @PostMapping("/create")
    public JSONResponse create(@Valid @RequestBody RoleCreateVo roleCreateVo) {
        RoleCreateDto roleCreateDto = new RoleCreateDto();
        BeanUtils.copyProperties(roleCreateVo, roleCreateDto);
        return succeed(roleService.create(roleCreateDto));
    }

    /**
     * 更新角色
     * @param roleUpdateVo 更新角色信息
     */
    @ApiDoc(stringResult = "true:更新角色成功", url = "/qiuqiu_admin/v1/role/update")
    @APIVersion("v1")
    @PostMapping("/update")
    public JSONResponse update(@Valid @RequestBody RoleUpdateVo roleUpdateVo) {
        RoleUpdateDto roleUpdateDto = new RoleUpdateDto();
        BeanUtils.copyProperties(roleUpdateVo, roleUpdateDto);
        return succeed(roleService.update(roleUpdateDto));
    }

    /**
     * 删除角色
     * @param roleDeleteVo 删除角色ids列表
     */
    @ApiDoc(stringResult = "true:删除角色成功", url = "/qiuqiu_admin/v1/role/delete")
    @APIVersion("v1")
    @PostMapping("/delete")
    public JSONResponse delete(@Valid @RequestBody RoleDeleteVo roleDeleteVo) {
        return succeed(roleService.delete(roleDeleteVo.getRoleIds()));
    }

    /**
     * 获取角色信息接口
     * @param roleDetailVo 角色详情id
     */
    @ApiDoc(result = RoleDetailOutVo.class, url = "/qiuqiu_admin/v1/role/detail")
    @APIVersion("v1")
    @PostMapping("/detail")
    public JSONResponse detail(@Valid @RequestBody RoleDetailVo roleDetailVo) {
        RoleDetailDto roleDetailDto = roleService.detail(roleDetailVo.getRoleId());
        RoleDetailOutVo roleDetailOutVo = new RoleDetailOutVo();
        BeanUtils.copyProperties(roleDetailDto, roleDetailOutVo);
        List<PermissionDto> permissionDtos = roleDetailDto.getPermissions();
        if (null != permissionDtos && !permissionDtos.isEmpty()) {
            List<Long> permissionIds = permissionDtos.stream().map(PermissionDto::getId).collect(Collectors.toList());
            List<String> permissions = permissionDtos.stream().map(PermissionDto::getDescription).collect(Collectors.toList());
            roleDetailOutVo.setPermissions(permissions);
            roleDetailOutVo.setPermissionIds(permissionIds);
        }
        return succeed(roleDetailOutVo);
    }

    /**
     * 获取角色分页列表接口
     */
    @ApiDoc(result = RolePageOutVo.class, url = "/qiuqiu_admin/v1/role/page")
    @APIVersion("v1")
    @PostMapping("/page")
    public JSONResponse page(@Valid @RequestBody RolePageVo rolePageVo) {
        DataGridPageDto<RolePageDto> page = roleService.page(rolePageVo.getRoleName(), rolePageVo.getPageNum(), rolePageVo.getPageSize());
        PageResultVo<RolePageOutVo> res = new PageResultVo<>();
        List<RolePageOutVo> rolePageOutVos = page.getData().stream().map(rolePageDto -> {
            RolePageOutVo rolePageOutVo = new RolePageOutVo();
            BeanUtils.copyProperties(rolePageDto, rolePageOutVo);
            return rolePageOutVo;
        }).collect(Collectors.toList());
        res.setCurrentPage(rolePageVo.getPageNum());
        res.setTotal(page.getRecordsTotal());
        res.setData(rolePageOutVos);
        return succeed(res);
    }
}
