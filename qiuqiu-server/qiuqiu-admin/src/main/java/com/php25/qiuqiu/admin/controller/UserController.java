package com.php25.qiuqiu.admin.controller;

import com.php25.common.flux.web.APIVersion;
import com.php25.common.flux.web.JSONController;
import com.php25.common.flux.web.JSONResponse;
import com.php25.qiuqiu.admin.vo.in.LoginVo;
import com.php25.qiuqiu.admin.vo.in.UserPageVo;
import com.php25.qiuqiu.admin.vo.out.TokenVo;
import com.php25.qiuqiu.admin.vo.out.UserVo;
import com.php25.qiuqiu.user.service.UserService;
import com.php25.qiuqiu.user.service.dto.PermissionDto;
import com.php25.qiuqiu.user.service.dto.RoleDto;
import com.php25.qiuqiu.user.service.dto.TokenDto;
import com.php25.qiuqiu.user.service.dto.UserDto;
import io.github.yedaxia.apidocs.ApiDoc;
import lombok.AllArgsConstructor;
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
 * @date 2021/2/3 10:23
 */
@RestController
@RequestMapping("/user")
@AllArgsConstructor
public class UserController extends JSONController {

    private final UserService userService;

    /**
     * 登入接口
     *
     * @param loginVo 登入信息
     */
    @ApiDoc(stringResult = "返回jwt令牌", url = "/wxadmin/v1/user/login")
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
    @ApiDoc(result = UserVo.class, url = "/wxadmin/v1/user/getUserInfo")
    @APIVersion("v1")
    @PostMapping("/getUserInfo")
    public JSONResponse getUserInfo(@RequestAttribute @NotBlank String username) {
        UserDto userDto = userService.getUserInfo(username);
        UserVo userVo = new UserVo();
        BeanUtils.copyProperties(userDto, userVo);
        List<String> roleNames = userDto.getRoles().stream().map(RoleDto::getName).collect(Collectors.toList());
        userVo.setRoles(roleNames);
        List<String> permissionNames = userDto.getPermissions().stream().map(PermissionDto::getName).collect(Collectors.toList());
        userVo.setPermissions(permissionNames);
        userVo.setGroupName(userDto.getGroup().getName());
        return succeed(userVo);
    }

    /**
     * 用户列表分页查询
     */
    @ApiDoc(result = UserVo.class, url = "/wxadmin/v1/user/page")
    @APIVersion("v1")
    @PostMapping("/page")
    public JSONResponse page(@RequestAttribute @NotBlank String username, @Valid @RequestBody UserPageVo userPageVo) {
        return succeed(null);
    }

    /**
     * 登出接口
     */
    @ApiDoc(stringResult = "是否成功登出", url = "/wxadmin/v1/user/logout")
    @APIVersion("v1")
    @PostMapping("/logout")
    public JSONResponse logout(@RequestAttribute @NotBlank String username) {
        return succeed(userService.logout(username));
    }


}
