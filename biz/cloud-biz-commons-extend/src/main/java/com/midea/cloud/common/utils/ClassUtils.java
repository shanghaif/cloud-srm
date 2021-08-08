package com.midea.cloud.common.utils;

import lombok.extern.log4j.Log4j2;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

/**
 * @author euler
 */
@Log4j2
public final class ClassUtils {

    private ClassUtils() {
        // do nothing
    }

    public static <T> Class<T> getFirstSuperClassGenericType(Class<?> clazz) {
        return getFirstSuperClassGenericType(clazz, 0);
    }
    public static <T> Class<T> getFirstSuperClassGenericType(Class<?> clazz, int index) {
        List<Class<?>> classList = org.apache.commons.lang3.ClassUtils.getAllSuperclasses(clazz);
        classList.add(0, clazz);
        for (Class<?> classItem: classList) {
            Class<?> type = getSuperClassGenericType(classItem, index);
            if (type != null) {
                return (Class<T>)type;
            }
        }
        return null;
    }

    public static Class<?> getSuperClassGenericType(Class<?> clazz, int index) {
        Type genType = clazz.getGenericSuperclass();
        if (!(genType instanceof ParameterizedType)) {
            return null;
        }
        Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
        if (index >= params.length || index < 0) {
            return null;
        }
        if (!(params[index] instanceof Class)) {
            return null;
        }
        return (Class<?>) params[index];
    }

}
