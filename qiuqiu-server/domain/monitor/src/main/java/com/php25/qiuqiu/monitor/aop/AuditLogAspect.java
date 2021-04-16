package com.php25.qiuqiu.monitor.aop;

import com.php25.common.core.util.JsonUtil;
import com.php25.qiuqiu.monitor.dto.AuditLogDto;
import com.php25.qiuqiu.monitor.service.AuditLogService;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


/**
 * @author penghuiping
 * @date 2020/7/13 15:26
 */
@Aspect
@Component
@RequiredArgsConstructor
public class AuditLogAspect {

    private final AuditLogService auditLogService;

    @Pointcut("@annotation(com.php25.qiuqiu.monitor.aop.AuditLog)")
    public void auditLogPointCut() {
    }

    @Around("auditLogPointCut()")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        Object obj = point.proceed();

        MethodSignature signature = (MethodSignature) point.getSignature();
        String methodName = signature.getMethod().getName();
        String className = point.getTarget().getClass().getName();
        Object username0 = getRequestAttributes().getAttribute("username",0);
        String username = "anonymous";
        if(username0 != null) {
            username = username0.toString();
        }

        List<Object> args = Arrays.stream(point.getArgs()).filter(o -> !(o instanceof HttpServletResponse) && !(o instanceof HttpServletRequest)).collect(Collectors.toList());
        String params = JsonUtil.toJson(args);
        AuditLogDto auditLogDto = new AuditLogDto();
        auditLogDto.setParams(params);
        auditLogDto.setUri(className+"."+methodName);
        auditLogDto.setUsername(username);
        auditLogDto.setCreateTime(LocalDateTime.now());

        auditLogService.create(auditLogDto);
        return obj;
    }

    private RequestAttributes getRequestAttributes() {
        return RequestContextHolder.getRequestAttributes();
    }
}
