package com.midea.cloud.common.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;

/**
 * @author tanjl11
 * 仿照
 * {@link java.util.DoubleSummaryStatistics}
 */
public class BigDecimalSummaryStatistics implements Consumer<BigDecimal> {
    private long count;
    private BigDecimal sum = BigDecimal.ZERO;
    private BigDecimal min = null;
    private BigDecimal max = null;

    public BigDecimalSummaryStatistics() {
    }


    @Override
    public void accept(BigDecimal value) {
        if(Objects.isNull(value)){
            throw new NullPointerException("can not handle with null value");
        }
        ++count;
        min = min(min, value);
        max = max(max, value);
        sum = sum.add(value);
    }


    public BigDecimalSummaryStatistics combine(BigDecimalSummaryStatistics other) {
        count += other.count;
        sum = sum.add(other.sum);
        min = min(min, other.min);
        max = max(max, other.max);
        return this;
    }


    public final long getCount() {
        return count;
    }


    public final BigDecimal getSum() {
        return sum;
    }


    public final BigDecimal getMin() {
        return min;
    }

    public final BigDecimal getMax() {
        return max;
    }


    public final BigDecimal getAverage() {
        return getCount() > 0 ? getSum().divide(BigDecimal.valueOf(getCount()), 8, RoundingMode.HALF_UP) : BigDecimal.ZERO;
    }

    @Override
    public String toString() {
        return String.format(
                "%s{count=%d, sum=%f, min=%f, average=%f, max=%f}",
                this.getClass().getSimpleName(),
                getCount(),
                getSum(),
                getMin(),
                getAverage(),
                getMax());
    }

    private BigDecimal min(BigDecimal one, BigDecimal other) {
        return Optional.ofNullable(ifHasNull(one, other))
                .orElseGet(()->one.compareTo(other) > 0 ? other : one);
    }

    private BigDecimal max(BigDecimal one, BigDecimal other) {
        return Optional.ofNullable(ifHasNull(one, other))
                .orElseGet(()->one.compareTo(other) > 0 ? one : other);
    }

    private BigDecimal ifHasNull(BigDecimal one, BigDecimal other) {
        if (Objects.isNull(one) && Objects.isNull(other)) {
            throw new NullPointerException("can not handle with both null value");
        }
        if (Objects.isNull(one)) {
            return other;
        }
        if (Objects.isNull(other)) {
            return one;
        }
        return null;
    }
}