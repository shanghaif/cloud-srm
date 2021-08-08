package com.midea.cloud.common.utils.support;

import java.io.Serializable;

/**
 * @author tanjl11
 * @date 2020/12/18 19:25
 */
@FunctionalInterface
public interface SFunction<T> extends Serializable {
    Object get(T object);
}
