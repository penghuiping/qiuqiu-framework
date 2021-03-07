package com.php25.qiuqiu.admin.controller;

import com.php25.common.flux.web.APIVersion;
import com.php25.common.flux.web.JSONController;
import com.php25.common.flux.web.JSONResponse;
import com.php25.qiuqiu.admin.vo.out.RoleVo;
import com.php25.qiuqiu.user.dto.role.RoleDto;
import com.php25.qiuqiu.user.service.RoleService;
import io.github.yedaxia.apidocs.ApiDoc;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    @ApiDoc(stringResult = "返回jwt令牌", url = "/qiuqiu_admin/v1/role/getAll")
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
}
