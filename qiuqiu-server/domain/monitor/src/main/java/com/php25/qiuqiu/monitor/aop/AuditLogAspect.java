package com.php25.qiuqiu.monitor.aop;

import com.google.common.collect.Lists;
import com.php25.common.core.util.JsonUtil;
import com.php25.common.mq.MessageQueueManager;
import com.php25.qiuqiu.monitor.dto.AuditLogDto;
import com.php25.qiuqiu.monitor.service.AuditLogService;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.time.LocalDateTime;


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
        String params = JsonUtil.toJson(Lists.newArrayList(point.getArgs()));
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
