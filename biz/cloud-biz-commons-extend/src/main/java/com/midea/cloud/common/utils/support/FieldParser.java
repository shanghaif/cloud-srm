package com.midea.cloud.common.utils.support;

import com.midea.cloud.common.exception.BaseException;

import java.lang.invoke.SerializedLambda;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author tanjl11
 * @date 2020/12/18 19:25
 */
public class FieldParser {

    private static final Map<Class, String> map = new ConcurrentHashMap();

    public static <T> String resolveFieldName(SFunction<T> sFunction) {
        String lambda = map.get(sFunction.getClass());
        if (lambda == null) {
            try {
                //writeReplace方法用于获取SerializedLamda对象
                Method writeReplace = sFunction.getClass().getDeclaredMethod("writeReplace");
                writeReplace.setAccessible(true);
                SerializedLambda l = (SerializedLambda) writeReplace.invoke(sFunction);
                lambda = getFieldName(l);
                map.put(sFunction.getClass(), lambda);
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
        //获取属性名
        return lambda;
    }

    public static <T> String[] resolveFieldNames(SFunction<T>... sFunctions) {
        String[] fields = new String[sFunctions.length];
        for (int i = 0; i < sFunctions.length; i++) {
            fields[i] = resolveFieldName(sFunctions[i]);
        }
        return fields;
    }

    private static String getFieldName(SerializedLambda lambda) {
        String getMethodName = lambda.getImplMethodName();
        if (getMethodName.startsWith("get")) {
            getMethodName = getMethodName.substring(3);
        } else if (getMethodName.startsWith("is")) {
            getMethodName = getMethodName.substring(2);
        } else {
            throw new BaseException("没有相应的属性方法");
        }
        // 小写第一个字母
        return Character.isLowerCase(getMethodName.charAt(0)) ? getMethodName : Character.toLowerCase(getMethodName.charAt(0)) + getMethodName.substring(1);
    }
}
