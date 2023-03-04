package com.php25.common.core.util;

import com.php25.common.core.exception.Exceptions;
import org.springframework.util.ConcurrentReferenceHashMap;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * 反射帮助类
 *
 * @author penghuiping
 * @date 2018/8/10 16:04
 */
public abstract class ReflectUtil {
    private static final ConcurrentReferenceHashMap<String, Field> fieldMap = new ConcurrentReferenceHashMap<>();
    private static final ConcurrentReferenceHashMap<String, Method> methodMap = new ConcurrentReferenceHashMap<>();
    private static final String DEFAULT_FOR_NULL = "-1";
    private static final String DATE_DEFAULT = "9999-12-31 23:59:59";
    private static final String GET = "get";
    private static final String SET = "set";

    public static Method getMethod(Class<?> cls, String name, Class<?>... parameterTypes) {
        String key = cls.getName() + name;
        if (null != parameterTypes && parameterTypes.length > 0) {
            for (int i = 0; i < parameterTypes.length; i++) {
                key = key + parameterTypes[i];
            }
        }
        Method method = methodMap.get(key);
        if (null == method) {
            try {
                method = cls.getDeclaredMethod(name, parameterTypes);
                methodMap.putIfAbsent(key, method);
            } catch (NoSuchMethodException e) {
                throw Exceptions.throwIllegalStateException("NoSuchMethodException", e);
            }
        }
        return method;
    }


    public static Field getField(Class cls, String name) {
        String key = cls.getName() + name;
        Field field = fieldMap.get(key);
        if (field == null) {
            try {
                field = cls.getDeclaredField(name);
                fieldMap.putIfAbsent(name, field);
            } catch (NoSuchFieldException e) {
                throw Exceptions.throwIllegalStateException("NoSuchFieldException", e);
            }
        }
        return field;
    }

    /**
     * 重置对象实例的属性值为-1的为null
     * @param obj 对象实例
     */
    public static void resetFieldValueFromDefaultToNull(Object obj) {
        Field[] fields = obj.getClass().getDeclaredFields();
        Class<?> cls = obj.getClass();
        for(Field field:fields) {
            Class<?> fieldType = field.getType();
            try {
                if (Number.class.isAssignableFrom(fieldType)  ) {
                    Object value = ReflectUtil.getMethod(cls, GET + StringUtil.capitalizeFirstLetter(field.getName())).invoke(obj);
                    if(null != value && DEFAULT_FOR_NULL.equals(value.toString())) {
                        ReflectUtil.getMethod(cls, SET + StringUtil.capitalizeFirstLetter(field.getName()), field.getType()).invoke(obj, (Object) null);
                    }
                }else if(String.class.isAssignableFrom(fieldType)) {
                    Object value = ReflectUtil.getMethod(cls, GET + StringUtil.capitalizeFirstLetter(field.getName())).invoke(obj);
                    if(null != value && DEFAULT_FOR_NULL.equals(value.toString())) {
                        ReflectUtil.getMethod(cls, SET + StringUtil.capitalizeFirstLetter(field.getName()), field.getType()).invoke(obj, (Object) null);
                    }
                }else if(BigDecimal.class.isAssignableFrom(fieldType)) {
                    Object value = ReflectUtil.getMethod(cls, GET + StringUtil.capitalizeFirstLetter(field.getName())).invoke(obj);
                    if(null != value && DEFAULT_FOR_NULL.equals(value.toString())) {
                        ReflectUtil.getMethod(cls, SET + StringUtil.capitalizeFirstLetter(field.getName()), field.getType()).invoke(obj, (Object) null);
                    }
                }

            } catch (IllegalAccessException | InvocationTargetException e) {
                throw Exceptions.throwIllegalStateException("把所有实例为默认值的重置成null或空字符失败", e);
            }

        }

    }



    /**
     * 把对象的所有为null属性,初始化为默认值
     * @param obj 对象实例
     */
    public static void resetFieldValueFromNullToDefault(Object obj) {
        Field[] fields = obj.getClass().getDeclaredFields();
        Class<?> cls = obj.getClass();
        for (Field field : fields) {
            Class<?> fieldType = field.getType();
            try {
                if (Integer.class.isAssignableFrom(fieldType)) {
                    Object value = ReflectUtil.getMethod(cls, GET + StringUtil.capitalizeFirstLetter(field.getName())).invoke(obj);
                    if (null == value) {
                        ReflectUtil.getMethod(cls, SET + StringUtil.capitalizeFirstLetter(field.getName()), field.getType()).invoke(obj, Integer.valueOf(DEFAULT_FOR_NULL));
                    }
                } else if (Long.class.isAssignableFrom(fieldType)) {
                    Object value = ReflectUtil.getMethod(cls, GET + StringUtil.capitalizeFirstLetter(field.getName())).invoke(obj);
                    if (null == value) {
                        ReflectUtil.getMethod(cls, SET + StringUtil.capitalizeFirstLetter(field.getName()), field.getType()).invoke(obj, Long.valueOf(DEFAULT_FOR_NULL));
                    }
                } else if (Byte.class.isAssignableFrom(fieldType)) {
                    Object value = ReflectUtil.getMethod(cls, GET + StringUtil.capitalizeFirstLetter(field.getName())).invoke(obj);
                    if (null == value) {
                        ReflectUtil.getMethod(cls, SET + StringUtil.capitalizeFirstLetter(field.getName()), field.getType()).invoke(obj, Byte.valueOf(DEFAULT_FOR_NULL));
                    }
                } else if (String.class.isAssignableFrom(fieldType)) {
                    Object value = ReflectUtil.getMethod(cls, GET + StringUtil.capitalizeFirstLetter(field.getName())).invoke(obj);
                    if (null == value) {
                        ReflectUtil.getMethod(cls, SET + StringUtil.capitalizeFirstLetter(field.getName()), field.getType()).invoke(obj, DEFAULT_FOR_NULL);
                    }
                } else if (BigDecimal.class.isAssignableFrom(fieldType)) {
                    Object value = ReflectUtil.getMethod(cls, GET + StringUtil.capitalizeFirstLetter(field.getName())).invoke(obj);
                    if (null == value) {
                        ReflectUtil.getMethod(cls, SET + StringUtil.capitalizeFirstLetter(field.getName()), field.getType()).invoke(obj, new BigDecimal(DEFAULT_FOR_NULL));
                    }
                } else if (Date.class.isAssignableFrom(fieldType)) {
                    Object value = ReflectUtil.getMethod(cls, GET + StringUtil.capitalizeFirstLetter(field.getName())).invoke(obj);
                    if (null == value) {
                        ReflectUtil.getMethod(cls, SET + StringUtil.capitalizeFirstLetter(field.getName()), field.getType())
                                .invoke(obj, TimeUtil.parseDate(DATE_DEFAULT, DateTimeFormatter.ofPattern(TimeUtil.STD_FORMAT)));
                    }
                }
            } catch (IllegalAccessException | InvocationTargetException e) {
                throw Exceptions.throwIllegalStateException("把对象的所有为null属性,初始化为默认值失败", e);
            }
        }
    }
}
