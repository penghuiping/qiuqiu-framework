package com.php25.qiuqiu.admin.interceptor;

import com.php25.common.core.exception.Exceptions;
import com.php25.common.core.util.StringUtil;
import com.php25.qiuqiu.user.constant.UserErrorCode;
import com.php25.qiuqiu.user.service.UserService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * jwt有效性认证拦截器
 *
 * @author penghuiping
 * @date 2021/2/4 10:00
 */
@Log4j2
@Component
public class JwtAuthInterceptor extends HandlerInterceptorAdapter {

    @Lazy
    @Autowired
    private UserService userService;

    private String[] excludeUris = new String[]{"/user/info","/user/logout"};

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info("进入jwt验证拦截器");
        //1. 先认证身份
        String jwt = request.getHeader("jwt");
        if (StringUtil.isBlank(jwt)) {
            throw Exceptions.throwBusinessException(UserErrorCode.JWT_NOT_FIND);
        }
        boolean isValid = userService.isTokenValid(jwt);
        if (!isValid) {
            throw Exceptions.throwBusinessException(UserErrorCode.JWT_ILLEGAL);
        }
        String username = userService.getUsernameFromJwt(jwt);
        request.setAttribute("username", username);

        //2. 在认证权限
        String uri = request.getRequestURI();

        //在例外中的uri不需要校验权限
        for(String excludeUri: excludeUris) {
            if(uri.endsWith(excludeUri)) {
                return true;
            }
        }

        boolean hasPermission = userService.hasPermission(username, uri);
        if (!hasPermission) {
            throw Exceptions.throwBusinessException(UserErrorCode.HAS_NO_PERMISSION);
        }

        return true;
    }
}
