package com.midea.cloud.common.utils;

import java.math.BigDecimal;

@FunctionalInterface
public interface ToBigDecimalFunction<T> {

    BigDecimal applyAsBigDecimal(T value);
}