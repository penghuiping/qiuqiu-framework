package com.php25.qiuqiu.admin.controller;

import com.php25.common.core.dto.DataGridPageDto;
import com.php25.common.flux.web.APIVersion;
import com.php25.common.flux.web.JSONController;
import com.php25.common.flux.web.JSONResponse;
import com.php25.qiuqiu.admin.vo.in.resource.ResourceCreateVo;
import com.php25.qiuqiu.admin.vo.in.resource.ResourceDeleteVo;
import com.php25.qiuqiu.admin.vo.in.resource.ResourcePermission0Vo;
import com.php25.qiuqiu.admin.vo.in.resource.ResourceUpdateVo;
import com.php25.qiuqiu.admin.vo.out.ResourceVo;
import com.php25.qiuqiu.monitor.aop.AuditLog;
import com.php25.qiuqiu.user.dto.resource.ResourceCreateDto;
import com.php25.qiuqiu.user.dto.resource.ResourceDto;
import com.php25.qiuqiu.user.dto.resource.ResourcePermissionDto;
import com.php25.qiuqiu.user.dto.resource.ResourceUpdateDto;
import com.php25.qiuqiu.user.service.ResourceService;
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
 * @date 2021/3/26 13:56
 */
@RestController
@RequestMapping("/resource")
@RequiredArgsConstructor
public class ResourceController extends JSONController {

    private final ResourceService resourceService;

    @AuditLog
    @APIVersion("v1")
    @PostMapping("/create")
    public JSONResponse create(@Valid @RequestBody ResourceCreateVo resourceCreateVo) {
        ResourceCreateDto resourceCreateDto = new ResourceCreateDto();
        BeanUtils.copyProperties(resourceCreateVo, resourceCreateDto);
        List<ResourcePermission0Vo> permissions = resourceCreateVo.getResourcePermissions();
        List<ResourcePermissionDto> resourcePermissionDtos = permissions.stream().map(resourcePermission0Vo -> {
            ResourcePermissionDto resourcePermissionDto = new ResourcePermissionDto();
            resourcePermissionDto.setResource(resourceCreateVo.getName());
            resourcePermissionDto.setPermission(resourcePermission0Vo.getPermission());
            resourcePermissionDto.setUri(resourcePermission0Vo.getUri());
            return resourcePermissionDto;
        }).collect(Collectors.toList());
        resourceCreateDto.setResourcePermissions(resourcePermissionDtos);
        return succeed(resourceService.create(resourceCreateDto));
    }

    @AuditLog
    @APIVersion("v1")
    @PostMapping("/update")
    public JSONResponse update(@Valid @RequestBody ResourceUpdateVo resourceUpdateVo) {
        ResourceUpdateDto resourceUpdateDto = new ResourceUpdateDto();
        BeanUtils.copyProperties(resourceUpdateVo, resourceUpdateDto);
        List<ResourcePermission0Vo> resourcePermission0Vos = resourceUpdateVo.getResourcePermissions();
        List<ResourcePermissionDto> resourcePermissionDtos = resourcePermission0Vos.stream().map(resourcePermission0Vo -> {
            ResourcePermissionDto resourcePermissionDto = new ResourcePermissionDto();
            resourcePermissionDto.setResource(resourceUpdateVo.getName());
            resourcePermissionDto.setPermission(resourcePermission0Vo.getPermission());
            resourcePermissionDto.setUri(resourcePermission0Vo.getUri());
            return resourcePermissionDto;
        }).collect(Collectors.toList());
        resourceUpdateDto.setResourcePermissions(resourcePermissionDtos);
        resourceService.update(resourceUpdateDto);
        return succeed(true);
    }

    @APIVersion("v1")
    @PostMapping("/delete")
    public JSONResponse delete(@Valid @RequestBody ResourceDeleteVo resourceDeleteVo) {
        List<String> resources = resourceDeleteVo.getResources();
        for(String resource:resources) {
            resourceService.delete(resource);
        }
        return succeed(true);
    }


    @APIVersion("v1")
    @PostMapping("/page")
    public JSONResponse page() {
        DataGridPageDto<ResourceDto> dataGrid = resourceService.page("",1,200);
        List<ResourceVo> res= dataGrid.getData().stream().map(resourceDto -> {
            ResourceVo resourceVo = new ResourceVo();
            BeanUtils.copyProperties(resourceDto,resourceVo);
            return resourceVo;
        }).collect(Collectors.toList());
        return succeed(res);
    }
}
