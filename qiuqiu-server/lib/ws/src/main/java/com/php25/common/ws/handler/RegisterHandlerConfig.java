package com.php25.common.ws.handler;

import com.fasterxml.jackson.databind.jsontype.NamedType;
import com.fasterxml.jackson.databind.jsontype.SubtypeResolver;
import com.php25.common.core.util.JsonUtil;
import com.php25.common.core.util.StringUtil;
import com.php25.common.ws.protocal.BaseMsg;
import com.php25.common.ws.core.MsgDispatcher;
import com.php25.common.ws.core.WsException;
import com.php25.common.ws.handler.WsAction;
import com.php25.common.ws.handler.WsController;
import com.php25.common.ws.handler.WsMsg;
import com.php25.common.ws.handler.ProxyMsgHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.StandardEnvironment;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.util.ClassUtils;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * @author penghuiping
 * @date 2020/9/4 15:07
 */
@Slf4j
public class RegisterHandlerConfig {

    private final MsgDispatcher msgDispatcher;

    private final ConfigurableApplicationContext applicationContext;

    public RegisterHandlerConfig(MsgDispatcher msgDispatcher, ConfigurableApplicationContext applicationContext) {
        this.msgDispatcher = msgDispatcher;
        this.applicationContext = applicationContext;
    }

    private static final String DEFAULT_RESOURCE_PATTERN = "**/*.class";
    private static final String CLASSPATH_ALL_URL_PREFIX = "classpath*:";

    public void scanPackage(String... basePackages) {
        List<Class<?>> cls = new ArrayList<>();
        List<Class<?>> clsMessage = new ArrayList<>();
        for (String basePackage : basePackages) {
            try {
                String packageSearchPath = CLASSPATH_ALL_URL_PREFIX +
                        resolveBasePackage(basePackage) + '/' + DEFAULT_RESOURCE_PATTERN;
                Resource[] resources = getResourcePatternResolver().getResources(packageSearchPath);
                String basePackage0 = basePackage.replace(".", "/");
                for (Resource resource : resources) {
                    String path = resource.getURI().toString();
                    if (path.indexOf(basePackage0) > 0) {
                        String className = path.substring(path.indexOf(basePackage0)).split("\\.")[0].replace("/", ".");
                        Class class0 = ClassUtils.getDefaultClassLoader().loadClass(className);
                        if (class0.isAnnotationPresent(WsController.class)) {
                            cls.add(class0);
                        }

                        if (class0.isAnnotationPresent(WsMsg.class)) {
                            clsMessage.add(class0);
                        }
                    }
                }
            } catch (Exception e) {
                throw new WsException("ws????????????:" + basePackage + "??????", e);
            }
        }
        load(cls);
        loadMessage(clsMessage);
    }

    private ResourcePatternResolver getResourcePatternResolver() {
        return new PathMatchingResourcePatternResolver();
    }

    private String resolveBasePackage(String basePackage) {
        return ClassUtils.convertClassNameToResourcePath(new StandardEnvironment().resolveRequiredPlaceholders(basePackage));
    }

    private void load(List<Class<?>> classes) {
        try {
            for (Class<?> cls : classes) {
                Object obj = applicationContext.getBean(cls);
                Method[] methods = cls.getDeclaredMethods();
                for (Method method : methods) {
                    WsAction wsAction = method.getDeclaredAnnotation(WsAction.class);
                    if (null != wsAction) {
                        //?????????????????????
                        String action = wsAction.value();
                        ProxyMsgHandler proxyMsgHandler = new ProxyMsgHandler(obj, method, cls);
                        msgDispatcher.registerHandler(action, proxyMsgHandler);
                    }
                }
            }
        } catch (Exception e) {
            throw new WsException("ws??????????????????", e);
        }
    }


    private void loadMessage(List<Class<?>> classes) {
        try {
            SubtypeResolver subtypeResolver = JsonUtil.getObjectMapper().getSubtypeResolver();
            subtypeResolver.registerSubtypes(BaseMsg.class);
            for (Class<?> cls : classes) {
                Class<?> superCls = cls.getSuperclass();
                if (superCls.equals(BaseMsg.class)) {
                    WsMsg wsMsg = cls.getDeclaredAnnotation(WsMsg.class);
                    String action = wsMsg.action();
                    if (!StringUtil.isBlank(action)) {
                        subtypeResolver.registerSubtypes(new NamedType(cls, wsMsg.action()));
                    }
                }
            }
        } catch (Exception e) {
            throw new WsException("ws??????????????????", e);
        }
    }
}
