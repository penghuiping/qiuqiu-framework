package com.php25.common.db;

import org.springframework.core.env.StandardEnvironment;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.util.ClassUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * @author penghuiping
 * @date 2023/9/9 21:14
 */
class FindAnnotationMethodUtil {

    private static final String DEFAULT_RESOURCE_PATTERN = "**/*.class";
    private static final String CLASSPATH_ALL_URL_PREFIX = "classpath*:";

    static List<Method> find(Class<? extends Annotation> target, String... basePackages) {
        List<Method> result = new ArrayList<>();
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
                        Class<?> cls = ClassUtils.getDefaultClassLoader().loadClass(className);
                        Method[] methods = cls.getDeclaredMethods();
                        for (Method method : methods) {
                            Annotation annotation = method.getDeclaredAnnotation(target);
                            if (null != annotation) {
                                //是需要找的方法
                                result.add(method);
                            }
                        }
                    }
                }
            } catch (Exception e) {
                throw new IllegalStateException("扫描包:" + basePackage + "出错", e);
            }
        }
        return result;
    }

    private static ResourcePatternResolver getResourcePatternResolver() {
        return new PathMatchingResourcePatternResolver();
    }

    private static String resolveBasePackage(String basePackage) {
        return ClassUtils.convertClassNameToResourcePath(new StandardEnvironment().resolveRequiredPlaceholders(basePackage));
    }
}
