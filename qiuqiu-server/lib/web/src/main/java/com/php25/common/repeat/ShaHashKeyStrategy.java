package com.php25.common.repeat;

import com.php25.common.core.util.DigestUtil;
import com.php25.common.core.util.JsonUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;

import java.lang.reflect.Method;

/**
 * @author penghuiping
 * @date 2023/2/11 22:26
 */
public class ShaHashKeyStrategy implements GetKeyStrategy{

    @Override
    public String getKey(Context context) {
        ProceedingJoinPoint pjp = context.getPjp();
        Method method = ((MethodSignature) pjp.getSignature()).getMethod();
        String className = method.getDeclaringClass().getName();
        String methodName = method.getName();
        Object[] obj = new Object[]{className,methodName,pjp.getArgs()};
        String key = DigestUtil.shaStr(JsonUtil.toJson(obj));
        return key;
    }
}
