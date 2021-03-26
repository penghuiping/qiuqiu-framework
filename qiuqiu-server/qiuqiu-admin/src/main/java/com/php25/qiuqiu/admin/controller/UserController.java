package com.php25.qiuqiu.admin.controller;

import com.php25.common.core.dto.DataGridPageDto;
import com.php25.common.core.util.StringUtil;
import com.php25.common.flux.web.APIVersion;
import com.php25.common.flux.web.JSONController;
import com.php25.common.flux.web.JSONResponse;
import com.php25.qiuqiu.admin.vo.in.LoginVo;
import com.php25.qiuqiu.admin.vo.in.user.UserCreateVo;
import com.php25.qiuqiu.admin.vo.in.user.UserDeleteVo;
import com.php25.qiuqiu.admin.vo.in.user.UserDetailVo;
import com.php25.qiuqiu.admin.vo.in.user.UserPageVo;
import com.php25.qiuqiu.admin.vo.in.user.UserUpdateVo;
import com.php25.qiuqiu.admin.vo.out.PageResultVo;
import com.php25.qiuqiu.admin.vo.out.ResourcePermissionVo;
import com.php25.qiuqiu.admin.vo.out.TokenVo;
import com.php25.qiuqiu.admin.vo.out.user.UserPageOutVo;
import com.php25.qiuqiu.admin.vo.out.user.UserVo;
import com.php25.qiuqiu.monitor.aop.AuditLog;
import com.php25.qiuqiu.user.dto.resource.ResourcePermissionDto;
import com.php25.qiuqiu.user.dto.role.RoleDto;
import com.php25.qiuqiu.user.dto.user.TokenDto;
import com.php25.qiuqiu.user.dto.user.UserCreateDto;
import com.php25.qiuqiu.user.dto.user.UserDto;
import com.php25.qiuqiu.user.dto.user.UserPageDto;
import com.php25.qiuqiu.user.dto.user.UserUpdateDto;
import com.php25.qiuqiu.user.service.UserService;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author penghuiping
 * @date 2021/2/3 10:23
 */
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController extends JSONController {

    private final UserService userService;

    /**
     * 登入接口
     *
     * @param loginVo 登入信息
     */
    @AuditLog
    @ApiDoc(stringResult = "返回jwt令牌", url = "/qiuqiu_admin/v1/user/login")
    @APIVersion("v1")
    @PostMapping("/login")
    public JSONResponse login(@Valid @RequestBody LoginVo loginVo) {
        TokenDto tokenDto = userService.login(loginVo.getUsername(), loginVo.getPassword());
        TokenVo tokenVo = new TokenVo();
        BeanUtils.copyProperties(tokenDto, tokenVo);
        return succeed(tokenVo);
    }

    /**
     * 获取用户信息接口
     */
    @ApiDoc(result = UserVo.class, url = "/qiuqiu_admin/v1/user/info")
    @APIVersion("v1")
    @PostMapping("/info")
    public JSONResponse getUserInfo(@RequestAttribute @NotBlank String username) {
        UserDto userDto = userService.getUserInfo(username);
        UserVo userVo = new UserVo();
        BeanUtils.copyProperties(userDto, userVo);
        List<String> roleNames = userDto.getRoles().stream().map(RoleDto::getDescription).collect(Collectors.toList());
        userVo.setRoles(roleNames);
        List<Long> roleIds = userDto.getRoles().stream().map(RoleDto::getId).collect(Collectors.toList());
        userVo.setRoleIds(roleIds);

        Set<ResourcePermissionDto> resourcePermission0DtoSet = userDto.getPermissions();
        List<ResourcePermissionVo> resourcePermissionVos = new ArrayList<>();
        for(ResourcePermissionDto resourcePermission0Dto: resourcePermission0DtoSet) {
            String resource = resourcePermission0Dto.getResource();
            List<String> permissions = new ArrayList<>();
            for(ResourcePermissionDto tmp: resourcePermission0DtoSet) {
                if(tmp.getResource().equals(resource)) {
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

    /**
     * 获取用户信息接口
     */
    @ApiDoc(result = UserVo.class, url = "/qiuqiu_admin/v1/user/detail")
    @APIVersion("v1")
    @PostMapping("/detail")
    public JSONResponse detail(@RequestAttribute @NotBlank String username, @RequestBody UserDetailVo user) {
        UserDto userDto = userService.detail(user.getUserId());
        UserVo userVo = new UserVo();
        BeanUtils.copyProperties(userDto, userVo);
        List<String> roleNames = userDto.getRoles().stream().map(RoleDto::getDescription).collect(Collectors.toList());
        userVo.setRoles(roleNames);
        List<Long> roleIds = userDto.getRoles().stream().map(RoleDto::getId).collect(Collectors.toList());
        userVo.setRoleIds(roleIds);

        Set<ResourcePermissionDto> resourcePermission0DtoSet = userDto.getPermissions();
        List<ResourcePermissionVo> resourcePermissionVos = new ArrayList<>();
        for(ResourcePermissionDto resourcePermission0Dto: resourcePermission0DtoSet) {
            String resource = resourcePermission0Dto.getResource();
            List<String> permissions = new ArrayList<>();
            for(ResourcePermissionDto tmp: resourcePermission0DtoSet) {
                if(tmp.getResource().equals(resource)) {
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

    /**
     * 用户列表分页查询
     *
     * @param userPageVo 分页请求信息
     */
    @ApiDoc(result = UserPageOutVo.class, url = "/qiuqiu_admin/v1/user/page")
    @APIVersion("v1")
    @PostMapping("/page")
    public JSONResponse page(@RequestAttribute @NotBlank String username, @Valid @RequestBody UserPageVo userPageVo) {
        DataGridPageDto<UserPageDto> result = userService.page(userPageVo.getUsername(), userPageVo.getPageNum(), userPageVo.getPageSize());
        PageResultVo<UserPageOutVo> resultVo = new PageResultVo<>();
        resultVo.setCurrentPage(userPageVo.getPageNum());
        resultVo.setTotal(result.getRecordsTotal());
        List<UserPageOutVo> list = result.getData().stream().map(userPageDto -> {
            UserPageOutVo userPageOutVo = new UserPageOutVo();
            BeanUtils.copyProperties(userPageDto, userPageOutVo);
            return userPageOutVo;
        }).collect(Collectors.toList());
        resultVo.setData(list);
        return succeed(resultVo);
    }

    /**
     * 创建用户
     *
     * @param userCreateVo 创建用户信息
     */
    @AuditLog
    @ApiDoc(stringResult = "true:创建成功", url = "/qiuqiu_admin/v1/user/create")
    @APIVersion("v1")
    @PostMapping("/create")
    public JSONResponse create(@Valid @RequestBody UserCreateVo userCreateVo) {
        UserCreateDto userCreateDto = new UserCreateDto();
        BeanUtils.copyProperties(userCreateVo, userCreateDto);
        return succeed(userService.create(userCreateDto));
    }

    /**
     * 更新用户
     *
     * @param userUpdateVo 更新用户信息
     */
    @AuditLog
    @ApiDoc(stringResult = "true:更新成功", url = "/qiuqiu_admin/v1/user/update")
    @APIVersion("v1")
    @PostMapping("/update")
    public JSONResponse update(@Valid @RequestBody UserUpdateVo userUpdateVo) {
        UserUpdateDto userUpdateDto = new UserUpdateDto();
        BeanUtils.copyProperties(userUpdateVo, userUpdateDto);
        if(StringUtil.isBlank(userUpdateDto.getPassword())) {
            userUpdateDto.setPassword(null);
        }
        return succeed(userService.update(userUpdateDto));
    }

    /**
     * 删除用户
     * @param userDeleteVo 删除用户信息
     *
     */
    @AuditLog
    @ApiDoc(stringResult = "true:删除成功", url = "/qiuqiu_admin/v1/user/delete")
    @APIVersion("v1")
    @PostMapping("/delete")
    public JSONResponse delete(@Valid @RequestBody UserDeleteVo userDeleteVo) {
        for (Long userId : userDeleteVo.getUserIds()) {
            userService.delete(userId);
        }
        return succeed(true);
    }

    /**
     * 登出接口
     */
    @AuditLog
    @ApiDoc(stringResult = "是否成功登出", url = "/qiuqiu_admin/v1/user/logout")
    @APIVersion("v1")
    @PostMapping("/logout")
    public JSONResponse logout(@RequestAttribute @NotBlank String username) {
        return succeed(userService.logout(username));
    }
}
