package com.midea.cloud.common.utils;

import com.midea.cloud.common.exception.BaseException;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import java.beans.PropertyDescriptor;
import java.util.*;
import java.util.function.Supplier;

/**
 * <pre>
 *  Bean复制工具类
 * </pre>
 *
 * @author huanghb14@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-3-11 10:05
 *  修改内容:
 * </pre>
 */
public class BeanCopyUtil {

    /**
     * 从源对象copy到目标对象
     *
     * @param dest 目标对象
     * @param src  源对象
     */
    public static void copyProperties(Object dest, Object src) {
        BeanUtils.copyProperties(src, dest);
    }

    /**
     * 从源对象copy到目标对象
     *
     * @param dest           目标对象
     * @param src            源对象
     * @param ignoreNullFlag 是否忽略null值
     * @throws Exception
     */
    public static void copyProperties(Object dest, Object src, boolean ignoreNullFlag) throws Exception {
        if (ignoreNullFlag) {
            BeanUtils.copyProperties(src, dest, getNullPropertyNames(src));
        } else {
            BeanUtils.copyProperties(src, dest);
        }
    }

    /**
     * 获取对象空值属性
     *
     * @param object
     * @return
     */
    public static String[] getNullPropertyNames(Object object) {
        final BeanWrapper src = new BeanWrapperImpl(object);
        PropertyDescriptor[] pds = src.getPropertyDescriptors();
        Set<String> emptyNames = new HashSet<>();
        for (PropertyDescriptor pd : pds) {
            Object srcValue = src.getPropertyValue(pd.getName());
            if (srcValue == null) {
                emptyNames.add(pd.getName());
            }
            //添加字符判空逻辑
            if (srcValue instanceof String && StringUtils.isBlank(srcValue.toString())) {
                emptyNames.add(pd.getName());
            }
        }
        String[] result = new String[emptyNames.size()];
        return emptyNames.toArray(result);
    }

    /**
     * 复制List集合
     *
     * @param origs
     * @param tClazz
     * @param <T>
     * @param <K>
     * @return
     */
    public static <T, K extends List> List<T> copyListProperties(K origs, Class<T> tClazz) {
        List<T> result = new ArrayList();
        if (CollectionUtils.isNotEmpty(origs)) {
            Iterator var3 = origs.iterator();

            while (var3.hasNext()) {
                Object orig = var3.next();

                try {
                    T instance = tClazz.newInstance();
                    BeanUtils.copyProperties(orig, instance);
                    result.add(instance);
                } catch (IllegalAccessException | InstantiationException var6) {
                    throw new BaseException(var6.getMessage());
                }
            }
        }

        return result;
    }

    public static <T> T copyProperties(Object source, Supplier<T> dest) {
        T result = dest.get();
        BeanUtils.copyProperties(source, result);
        return result;
    }
}
