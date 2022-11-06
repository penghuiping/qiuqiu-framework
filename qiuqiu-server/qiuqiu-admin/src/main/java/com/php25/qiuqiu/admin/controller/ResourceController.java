package com.php25.qiuqiu.admin.controller;

import com.php25.common.core.dto.PageDto;
import com.php25.common.web.JsonController;
import com.php25.common.web.JsonResponse;
import com.php25.qiuqiu.admin.mapper.ResourceVoMapper;
import com.php25.qiuqiu.admin.vo.in.resource.ResourceCreateVo;
import com.php25.qiuqiu.admin.vo.in.resource.ResourceDeleteVo;
import com.php25.qiuqiu.admin.vo.in.resource.ResourceIdVo;
import com.php25.qiuqiu.admin.vo.in.resource.ResourceUpdateVo;
import com.php25.qiuqiu.admin.vo.out.resource.ResourceDetailVo;
import com.php25.qiuqiu.admin.vo.out.resource.ResourcePermissionVo;
import com.php25.qiuqiu.admin.vo.out.resource.ResourceVo;
import com.php25.qiuqiu.monitor.aop.AuditLog;
import com.php25.qiuqiu.user.dto.resource.ResourceCreateDto;
import com.php25.qiuqiu.user.dto.resource.ResourceDetailDto;
import com.php25.qiuqiu.user.dto.resource.ResourceDto;
import com.php25.qiuqiu.user.dto.resource.ResourcePermissionDto;
import com.php25.qiuqiu.user.dto.resource.ResourceUpdateDto;
import com.php25.qiuqiu.user.service.ResourceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 资源管理
 *
 * @author penghuiping
 * @date 2021/3/26 13:56
 */
@Api(tags = "资管管理")
@RestController
@RequestMapping(value = "/api/resource",consumes = {"application/json"},produces = {"application/json"})
@RequiredArgsConstructor
public class ResourceController extends JsonController {

    private final ResourceService resourceService;

    private final ResourceVoMapper resourceVoMapper;


    @ApiOperation("创建资源")
    @AuditLog
    @PostMapping(value = "/create",headers = {"version=v1","jwt"})
    public JsonResponse<Boolean> create(@Valid @RequestBody ResourceCreateVo resourceCreateVo) {
        ResourceCreateDto resourceCreateDto = resourceVoMapper.toDto(resourceCreateVo);
        List<String> permissions = resourceCreateVo.getPermissions();
        List<ResourcePermissionDto> resourcePermissionDtos = permissions.stream().map(permission -> {
            ResourcePermissionDto resourcePermissionDto = new ResourcePermissionDto();
            resourcePermissionDto.setResource(resourceCreateVo.getName());
            resourcePermissionDto.setPermission(permission);
            return resourcePermissionDto;
        }).collect(Collectors.toList());
        resourceCreateDto.setResourcePermissions(resourcePermissionDtos);
        return succeed(resourceService.create(resourceCreateDto));
    }

    @ApiOperation("更新资源")
    @AuditLog
    @PostMapping(value = "/update",headers = {"version=v1","jwt"})
    public JsonResponse<Boolean> update(@Valid @RequestBody ResourceUpdateVo resourceUpdateVo) {
        ResourceUpdateDto resourceUpdateDto = resourceVoMapper.toDto(resourceUpdateVo);
        List<String> permissions = resourceUpdateVo.getPermissions();
        List<ResourcePermissionDto> resourcePermissionDtos = permissions.stream().map(permission -> {
            ResourcePermissionDto resourcePermissionDto = new ResourcePermissionDto();
            resourcePermissionDto.setResource(resourceUpdateVo.getName());
            resourcePermissionDto.setPermission(permission);
            return resourcePermissionDto;
        }).collect(Collectors.toList());
        resourceUpdateDto.setResourcePermissions(resourcePermissionDtos);
        resourceService.update(resourceUpdateDto);
        return succeed(true);
    }

    @ApiOperation("删除资源")
    @AuditLog
    @PostMapping(value = "/delete",headers = {"version=v1","jwt"})
    public JsonResponse<Boolean> delete(@Valid @RequestBody ResourceDeleteVo resourceDeleteVo) {
        List<String> resources = resourceDeleteVo.getResources();
        for (String resource : resources) {
            resourceService.delete(resource);
        }
        return succeed(true);
    }


    @ApiOperation("分页查询资源列表")
    @PostMapping(value = "/page",headers = {"version=v1","jwt"})
    public JsonResponse<List<ResourceVo>> page() {
        PageDto<ResourceDto> dataGrid = resourceService.page("", 1, 200);
        List<ResourceVo> res = dataGrid.getData().stream().map(resourceVoMapper::toVo).collect(Collectors.toList());
        return succeed(res);
    }

    @ApiOperation("获取系统中所有内置资源")
    @PostMapping(value = "/get_all",headers = {"version=v1","jwt"})
    public JsonResponse<List<ResourcePermissionVo>> getAll() {
        List<ResourceDetailDto> permissions = resourceService.getAll();
        List<ResourcePermissionVo> resourcePermissionVos = permissions.stream().map(resourceDetailDto -> {
            ResourcePermissionVo resourcePermissionVo = new ResourcePermissionVo();
            resourcePermissionVo.setResource(resourceDetailDto.getName());
            List<ResourcePermissionDto> resourcePermissionDtos = resourceDetailDto.getResourcePermissions();
            List<String> permission0s = resourcePermissionDtos.stream().map(ResourcePermissionDto::getPermission).collect(Collectors.toList());
            resourcePermissionVo.setPermissions(permission0s);
            return resourcePermissionVo;
        }).collect(Collectors.toList());
        return succeed(resourcePermissionVos);
    }

    @ApiOperation("获取资源详情")
    @PostMapping(value = "/detail",headers = {"version=v1","jwt"})
    public JsonResponse<ResourceDetailVo> detail(@Valid @RequestBody ResourceIdVo resourceIdVo) {
        ResourceDetailDto resourceDetailDto = resourceService.detail(resourceIdVo.getName());
        ResourceDetailVo resourceDetailVo = resourceVoMapper.toVo(resourceDetailDto);
        List<String> permissions = resourceDetailDto.getResourcePermissions().stream().map(ResourcePermissionDto::getPermission).collect(Collectors.toList());
        resourceDetailVo.setPermissions(permissions);
        return succeed(resourceDetailVo);
    }
}
