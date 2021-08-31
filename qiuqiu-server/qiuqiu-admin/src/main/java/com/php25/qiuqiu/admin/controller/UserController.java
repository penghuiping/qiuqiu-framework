package com.php25.qiuqiu.admin.controller;

import com.php25.common.core.dto.DataGridPageDto;
import com.php25.common.core.exception.Exceptions;
import com.php25.common.core.util.RandomUtil;
import com.php25.common.core.util.StringUtil;
import com.php25.common.flux.web.JSONController;
import com.php25.common.flux.web.JSONResponse;
import com.php25.qiuqiu.admin.mapper.UserVoMapper;
import com.php25.qiuqiu.admin.vo.in.LoginVo;
import com.php25.qiuqiu.admin.vo.in.user.UserCreateVo;
import com.php25.qiuqiu.admin.vo.in.user.UserDeleteVo;
import com.php25.qiuqiu.admin.vo.in.user.UserDetailVo;
import com.php25.qiuqiu.admin.vo.in.user.UserPageVo;
import com.php25.qiuqiu.admin.vo.in.user.UserUpdateVo;
import com.php25.qiuqiu.admin.vo.out.PageResultVo;
import com.php25.qiuqiu.admin.vo.out.TokenVo;
import com.php25.qiuqiu.admin.vo.out.resource.ResourcePermissionVo;
import com.php25.qiuqiu.admin.vo.out.user.UserPageOutVo;
import com.php25.qiuqiu.admin.vo.out.user.UserVo;
import com.php25.qiuqiu.media.service.ImageService;
import com.php25.qiuqiu.monitor.aop.AuditLog;
import com.php25.qiuqiu.user.constant.UserConstants;
import com.php25.qiuqiu.user.constant.UserErrorCode;
import com.php25.qiuqiu.user.dto.resource.ResourcePermissionDto;
import com.php25.qiuqiu.user.dto.role.RoleDto;
import com.php25.qiuqiu.user.dto.user.TokenDto;
import com.php25.qiuqiu.user.dto.user.UserCreateDto;
import com.php25.qiuqiu.user.dto.user.UserDto;
import com.php25.qiuqiu.user.dto.user.UserPageDto;
import com.php25.qiuqiu.user.dto.user.UserUpdateDto;
import com.php25.qiuqiu.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.WritableByteChannel;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 用户管理
 *
 * @author penghuiping
 * @date 2021/2/3 10:23
 */
@Log4j2
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController extends JSONController {

    private final UserService userService;

    private final UserVoMapper userVoMapper;

    private final ImageService imageService;

    /**
     * 登入接口
     *
     * @ignoreParams response
     * @since v1
     */
    @AuditLog
    @PostMapping(value = "/login",headers = {"version=v1"})
    public JSONResponse<TokenVo> login(HttpServletResponse response,
                                       @Valid @RequestBody LoginVo loginVo) {
        TokenDto tokenDto = userService.login(loginVo.getUsername(), loginVo.getPassword());
        TokenVo tokenVo = new TokenVo();
        tokenVo.setToken(tokenDto.getAccessToken());
        tokenVo.setExpireTime(tokenDto.getExpireTime());
        Cookie cookie = new Cookie(UserConstants.REFRESH_TOKEN, tokenDto.getRefreshToken());
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setPath("/");
        response.addCookie(cookie);
        return succeed(tokenVo);
    }

    /**
     * 获取登入验证码
     */
    @GetMapping(value = "/img_code")
    public void getImgCode(HttpServletResponse response) {
        String code = RandomUtil.getRandomNumbersAndLetters(6);
        try (
                ReadableByteChannel readableByteChannel = imageService.getCode(code);
                WritableByteChannel writableByteChannel = Channels.newChannel(response.getOutputStream());
        ) {
            ByteBuffer buffer = ByteBuffer.allocate(512);
            while (true) {
                buffer.clear();
                int size = readableByteChannel.read(buffer);
                if (size <= 0) {
                    break;
                }
                buffer.flip();
                writableByteChannel.write(buffer);
            }
        } catch (Exception e) {
            throw Exceptions.throwIllegalStateException("获取验证码失败", e);
        }
    }

    /**
     * 刷新token接口
     *
     * @ignoreParams request
     */
    @AuditLog
    @PostMapping(value = "/refresh",headers = {"version=v1"})
    public JSONResponse<TokenVo> refresh(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (null != cookies) {
            Optional<Cookie> cookieOptional = Arrays.stream(cookies)
                    .filter(cookie -> UserConstants.REFRESH_TOKEN.equals(cookie.getName()))
                    .findFirst();
            if (cookieOptional.isPresent()) {
                Cookie cookie = cookieOptional.get();
                TokenDto tokenDto = userService.refreshToken(cookie.getValue());
                TokenVo tokenVo = new TokenVo();
                tokenVo.setToken(tokenDto.getAccessToken());
                tokenVo.setExpireTime(tokenDto.getExpireTime());
                return succeed(tokenVo);
            }
        }
        throw Exceptions.throwBusinessException(UserErrorCode.REFRESH_TOKEN_ILLEGAL);
    }

    /**
     * 获取用户信息接口
     *
     * @ignoreParams username
     * @since v1
     */
    @PostMapping(value = "/info",headers = {"version=v1"})
    public JSONResponse<UserVo> getUserInfo(@RequestAttribute @NotBlank String username) {
        UserDto userDto = userService.getUserInfo(username);
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

    /**
     * 获取用户信息接口
     *
     * @ignoreParams username
     * @since v1
     */
    @PostMapping(value = "/detail",headers = {"version=v1"})
    public JSONResponse<UserVo> detail(@RequestAttribute @NotBlank String username, @RequestBody UserDetailVo user) {
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

    /**
     * 用户列表分页查询
     *
     * @ignoreParams username
     * @since v1
     */
    @PostMapping(value = "/page",headers = {"version=v1"})
    public JSONResponse<PageResultVo<UserPageOutVo>> page(@RequestAttribute @NotBlank String username, @Valid @RequestBody UserPageVo userPageVo) {
        DataGridPageDto<UserPageDto> result = userService.page(userPageVo.getUsername(), userPageVo.getPageNum(), userPageVo.getPageSize());
        PageResultVo<UserPageOutVo> resultVo = new PageResultVo<>();
        resultVo.setCurrentPage(userPageVo.getPageNum());
        resultVo.setTotal(result.getRecordsTotal());
        List<UserPageOutVo> list = result.getData().stream().map(userVoMapper::toVo).collect(Collectors.toList());
        resultVo.setData(list);
        return succeed(resultVo);
    }

    /**
     * 创建用户
     *
     * @since v1
     */
    @AuditLog
    @PostMapping(value = "/create",headers = {"version=v1"})
    public JSONResponse<Boolean> create(@Valid @RequestBody UserCreateVo userCreateVo) {
        UserCreateDto userCreateDto = userVoMapper.toDto(userCreateVo);
        return succeed(userService.create(userCreateDto));
    }

    /**
     * 更新用户
     *
     * @since v1
     */
    @AuditLog
    @PostMapping(value = "/update",headers = {"version=v1"})
    public JSONResponse<Boolean> update(@Valid @RequestBody UserUpdateVo userUpdateVo) {
        UserUpdateDto userUpdateDto = userVoMapper.toDto(userUpdateVo);
        if (StringUtil.isBlank(userUpdateDto.getPassword())) {
            userUpdateDto.setPassword(null);
        }
        return succeed(userService.update(userUpdateDto));
    }

    /**
     * 删除用户
     */
    @AuditLog
    @PostMapping(value = "/delete",headers = {"version=v1"})
    public JSONResponse<Boolean> delete(@Valid @RequestBody UserDeleteVo userDeleteVo) {
        for (Long userId : userDeleteVo.getUserIds()) {
            userService.delete(userId);
        }
        return succeed(true);
    }

    /**
     * 登出接口
     */
    @AuditLog
    @PostMapping(value = "/logout",headers = {"version=v1"})
    public JSONResponse<Boolean> logout(@RequestAttribute @NotBlank String username) {
        return succeed(userService.logout(username));
    }
}
