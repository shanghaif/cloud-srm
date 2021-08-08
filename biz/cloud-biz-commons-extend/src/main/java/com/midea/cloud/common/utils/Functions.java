package com.midea.cloud.common.utils;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * Functions
 *
 * @author zixuan.yan@meicloud.com
 */
public class Functions {

    /**
     * 按某个Key值分组
     *
     * @param keyExtractor  keyExtractor
     * @return A new {@link Predicate<T>}.
     */
    public static <T> Predicate<T> distinctByKey(Function<? super T, Object> keyExtractor) {
        Map<Object, Boolean> seen = new ConcurrentHashMap<>();
        return t -> seen.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
    }

}
