package com.php25.qiuqiu.admin.controller;

import com.php25.common.core.dto.CurrentUser;
import com.php25.common.core.dto.PageDto;
import com.php25.common.core.util.StringUtil;
import com.php25.common.redis.RedisManager;
import com.php25.common.repeat.AvoidRepeat;
import com.php25.common.repeat.ShaHashKeyStrategy;
import com.php25.common.repeat.SpElKeyStrategy;
import com.php25.common.web.JsonController;
import com.php25.common.web.JsonResponse;
import com.php25.common.web.RequestUtil;
import com.php25.qiuqiu.admin.mapper.UserVoMapper;
import com.php25.qiuqiu.admin.vo.in.user.UserCreateVo;
import com.php25.qiuqiu.admin.vo.in.user.UserDeleteVo;
import com.php25.qiuqiu.admin.vo.in.user.UserDetailVo;
import com.php25.qiuqiu.admin.vo.in.user.UserPageVo;
import com.php25.qiuqiu.admin.vo.in.user.UserUpdateVo;
import com.php25.qiuqiu.admin.vo.out.PageResultVo;
import com.php25.qiuqiu.admin.vo.out.resource.ResourcePermissionVo;
import com.php25.qiuqiu.admin.vo.out.user.UserPageOutVo;
import com.php25.qiuqiu.admin.vo.out.user.UserVo;
import com.php25.qiuqiu.media.service.ImageService;
import com.php25.qiuqiu.monitor.aop.AuditLog;
import com.php25.qiuqiu.user.dto.resource.ResourcePermissionDto;
import com.php25.qiuqiu.user.dto.role.RoleDto;
import com.php25.qiuqiu.user.dto.user.UserCreateDto;
import com.php25.qiuqiu.user.dto.user.UserDto;
import com.php25.qiuqiu.user.dto.user.UserPageDto;
import com.php25.qiuqiu.user.dto.user.UserUpdateDto;
import com.php25.qiuqiu.user.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 用户管理
 *
 * @author penghuiping
 * @date 2021/2/3 10:23
 */
@Slf4j
@RestController
@RequestMapping(value = "/api/user",produces = {"application/json"})
@RequiredArgsConstructor
@Api(tags = "角色管理")
public class UserController extends JsonController {

    private final UserService userService;

    private final UserVoMapper userVoMapper;

    private final ImageService imageService;

    private final RedisManager redisManager;


    @ApiOperation("获取用户信息接口")
    @PostMapping(value = "/info",headers = {"version=v1","jwt"})
    public JsonResponse<UserVo> getUserInfo() {
        CurrentUser currentUser = RequestUtil.getCurrentUser();
        UserDto userDto = userService.getUserInfo(currentUser.getUsername());
        UserVo userVo = userVoMapper.toVo(userDto);
        List<String> roleNames = userDto.getRoles().stream().map(RoleDto::getDescription).collect(Collectors.toList());
        userVo.setRoles(roleNames);
        List<Long> roleIds = userDto.getRoles().stream().map(RoleDto::getId).collect(Collectors.toList());
        userVo.setRoleIds(roleIds);

        Set<ResourcePermissionDto> resourcePermission0DtoSet = userDto.getPermissions();
        List<ResourcePermissionVo> resourcePermissionVos = new ArrayList<>();
        for (ResourcePermissionDto resourcePermission0Dto : resourcePermission0DtoSet) {
            String resource = resourcePermission0Dto.getResource();
            List<String> permissions = new ArrayList<>();
            for (ResourcePermissionDto tmp : resourcePermission0DtoSet) {
                if (tmp.getResource().equals(resource)) {
                    permissions.add(tmp.getPermission());
                }
            }
            ResourcePermissionVo resourcePermissionVo = new ResourcePermissionVo();
            resourcePermissionVo.setResource(resource);
            resourcePermissionVo.setPermissions(permissions);
            resourcePermissionVos.add(resourcePermissionVo);
        }
        userVo.setResourcePermissions(resourcePermissionVos);
        userVo.setGroupName(userDto.getGroup().getDescription());
        userVo.setGroupId(userDto.getGroup().getId());
        return succeed(userVo);
    }


    @ApiOperation("获取用户详细信息接口")
    @PostMapping(value = "/detail",headers = {"version=v1","jwt"})
    public JsonResponse<UserVo> detail(@RequestBody UserDetailVo user) {
        UserDto userDto = userService.detail(user.getUserId());
        UserVo userVo = userVoMapper.toVo(userDto);
        List<String> roleNames = userDto.getRoles().stream().map(RoleDto::getDescription).collect(Collectors.toList());
        userVo.setRoles(roleNames);
        List<Long> roleIds = userDto.getRoles().stream().map(RoleDto::getId).collect(Collectors.toList());
        userVo.setRoleIds(roleIds);

        Set<ResourcePermissionDto> resourcePermission0DtoSet = userDto.getPermissions();
        List<ResourcePermissionVo> resourcePermissionVos = new ArrayList<>();
        for (ResourcePermissionDto resourcePermission0Dto : resourcePermission0DtoSet) {
            String resource = resourcePermission0Dto.getResource();
            List<String> permissions = new ArrayList<>();
            for (ResourcePermissionDto tmp : resourcePermission0DtoSet) {
                if (tmp.getResource().equals(resource)) {
                    permissions.add(tmp.getPermission());
                }
            }
            ResourcePermissionVo resourcePermissionVo = new ResourcePermissionVo();
            resourcePermissionVo.setResource(resource);
            resourcePermissionVo.setPermissions(permissions);
            resourcePermissionVos.add(resourcePermissionVo);
        }
        userVo.setResourcePermissions(resourcePermissionVos);
        userVo.setGroupName(userDto.getGroup().getDescription());
        userVo.setGroupId(userDto.getGroup().getId());
        return succeed(userVo);
    }


    @ApiOperation("用户列表分页查询")
    @PostMapping(value = "/page",headers = {"version=v1","jwt"})
    public JsonResponse<PageResultVo<UserPageOutVo>> page(@Valid @RequestBody UserPageVo userPageVo) {
        PageDto<UserPageDto> result = userService.page(userPageVo.getUsername(), userPageVo.getPageNum(), userPageVo.getPageSize());
        PageResultVo<UserPageOutVo> resultVo = new PageResultVo<>();
        resultVo.setCurrentPage(userPageVo.getPageNum());
        resultVo.setTotal(result.getTotal());
        List<UserPageOutVo> list = result.getData().stream().map(userVoMapper::toVo).collect(Collectors.toList());
        resultVo.setData(list);
        return succeed(resultVo);
    }

    @AvoidRepeat(keyStrategy = SpElKeyStrategy.class,expression = "#userCreateVo.username")
    @AuditLog
    @ApiOperation("创建用户")
    @PostMapping(value = "/create",headers = {"version=v1","jwt"})
    public JsonResponse<Boolean> create(@Valid @RequestBody UserCreateVo userCreateVo) {
        UserCreateDto userCreateDto = userVoMapper.toDto(userCreateVo);
        return succeed(userService.create(userCreateDto));
    }

    @AuditLog
    @ApiOperation("更新用户")
    @PostMapping(value = "/update",headers = {"version=v1","jwt"})
    public JsonResponse<Boolean> update(@Valid @RequestBody UserUpdateVo userUpdateVo) {
        UserUpdateDto userUpdateDto = userVoMapper.toDto(userUpdateVo);
        if (StringUtil.isBlank(userUpdateDto.getPassword())) {
            userUpdateDto.setPassword(null);
        }
        return succeed(userService.update(userUpdateDto));
    }

    @AuditLog
    @ApiOperation("删除用户")
    @PostMapping(value = "/delete",headers = {"version=v1","jwt"})
    public JsonResponse<Boolean> delete(@Valid @RequestBody UserDeleteVo userDeleteVo) {
        for (Long userId : userDeleteVo.getUserIds()) {
            userService.delete(userId);
        }
        return succeed(true);
    }
}
