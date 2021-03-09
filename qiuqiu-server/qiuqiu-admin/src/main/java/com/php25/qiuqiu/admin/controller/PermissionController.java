package com.php25.qiuqiu.admin.controller;

import com.php25.common.flux.web.APIVersion;
import com.php25.common.flux.web.JSONController;
import com.php25.common.flux.web.JSONResponse;
import com.php25.qiuqiu.admin.vo.out.PermissionVo;
import com.php25.qiuqiu.admin.vo.out.TreeVo;
import com.php25.qiuqiu.user.dto.permission.PermissionDto;
import com.php25.qiuqiu.user.service.PermissionService;
import io.github.yedaxia.apidocs.ApiDoc;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    @ApiDoc(result = PermissionVo.class, url = "/qiuqiu_admin/v1/permission/getAll")
    @APIVersion("v1")
    @PostMapping("/getAll")
    public JSONResponse getAll(@RequestAttribute @NotBlank String username) {
        List<PermissionDto> permissions = permissionService.getAll();
        List<TreeVo> permissionVos = permissions.stream().map(permissionDto -> {
            TreeVo permissionVo = new TreeVo();
            permissionVo.setId(permissionDto.getId()+"");
            permissionVo.setValue(permissionDto.getId()+"");
            permissionVo.setLabel(permissionDto.getDescription());
            return permissionVo;
        }).collect(Collectors.toList());
        return succeed(permissionVos);
    }
}
