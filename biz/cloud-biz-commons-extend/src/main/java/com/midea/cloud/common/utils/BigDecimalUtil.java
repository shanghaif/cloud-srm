package com.midea.cloud.common.utils;

import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;

public class BigDecimalUtil {

    private static final int DEF_DIV_SCALE = 10;
    public static final BigDecimal DIV_SCALE_2 = new BigDecimal(100);

    /**
     *
     * N个BigDecimal相加，忽略为null的项
     *
     * @param bigDecimals
     * @return
     */
    public static BigDecimal safePlus(BigDecimal... bigDecimals) {
        BigDecimal retDecimal = BigDecimal.ZERO;
        if (bigDecimals == null) {
            return retDecimal;
        }
        for (BigDecimal i : bigDecimals) {
            if (i == null) {
                continue;
            }
            retDecimal = retDecimal.add(i);
        }
        return retDecimal;
    }

    /**
     * 是否是一个正数
     *
     * @param src
     * @return
     */
    public static boolean isPositive(BigDecimal src) {
        return BigDecimal.ZERO.compareTo(src) < 0;
    }

    /**
     * 是否是0
     *
     * @param src
     * @return
     */
    public static boolean isZero(BigDecimal src) {
        return BigDecimal.ZERO.compareTo(src) == 0;
    }

    /**
     * 是否是负数
     *
     * @param src
     * @return
     */
    public static boolean isNegative(BigDecimal src) {
        return BigDecimal.ZERO.compareTo(src) > 0;
    }

    /**
     * 是否是非负数
     *
     * @param src
     * @return
     */
    public static boolean isNonNegative(BigDecimal src) {
        return !isNegative(src);
    }

    /**
     * Null值替换， 如果为null返回BigDecimal.ZERO，反之返回原值。
     * <p>
     * 与oracle中nvl函数类似，即 如果参数1的值为null，即使用第二个参数替换。本函数无第二个参数，使用
     * <code>BigDecimal.ZERO</code>替换
     *
     * @param src
     *            - 第一个参数为null，用此值替换
     * @return
     * @see #nvl(BigDecimal, BigDecimal)
     * @see #nvl2(BigDecimal, BigDecimal, BigDecimal)
     */
    public static BigDecimal nvl(BigDecimal src) {
        return src == null ? BigDecimal.ZERO : src;
    }

    /**
     * 如果参数1的值为null，即使用第二个参数替换。如果都为null，返回null， 类似oracle中nvl函数相同，
     *
     * @param src
     * @param replaceWith
     *            - 第一个参数为null，用此值替换
     * @return
     * @see #nvl(BigDecimal)
     * @see #nvl2(BigDecimal, BigDecimal, BigDecimal)
     */
    public static BigDecimal nvl(BigDecimal src, BigDecimal replaceWith) {
        return src == null ? replaceWith : src;
    }

    /**
     * 如果参数1的值为null，即使用第二个参数替换,否则返回第三个参数 与oracle中nvl2函数类似
     *
     * @param src
     * @param ifValue
     *            - 第一个参数为null返回该值
     * @param elseValue
     *            - 第一个参数非null返回该值
     * @return
     */
    public static BigDecimal nvl2(BigDecimal src, BigDecimal ifValue,
                                  BigDecimal elseValue) {
        return src == null ? ifValue : elseValue;
    }

    /**
     * 判断一个数是否大于另外一个数
     *
     * @param a
     * @param b
     * @return
     */
    public static boolean gt(BigDecimal a, BigDecimal b) {
        return a.compareTo(b) > 0;
    }

    /**
     * 判断一个数是否等于另外一个数
     *
     * @param a
     * @param b
     * @return
     */
    public static boolean eq(BigDecimal a, BigDecimal b) {
        return a.compareTo(b) == 0;
    }

    /**
     * 判断一个数是否小于另外一个数
     *
     * @param a
     * @param b
     * @return
     */
    public static boolean lt(BigDecimal a, BigDecimal b) {
        return a.compareTo(b) < 0;
    }

    /**
     * 判断一个数是否小于等于另外一个数
     *
     * @param a
     * @param b
     * @return
     */
    public static boolean le(BigDecimal a, BigDecimal b) {
        return lt(a, b) || eq(a, b);
    }

    /**
     * 判断一个数是否大于等于另外一个数
     *
     * @param a
     * @param b
     * @return
     */
    public static boolean ge(BigDecimal a, BigDecimal b) {
        return gt(a, b) || eq(a, b);
    }

    /**
     * 判断一个数是否大于0
     *
     * @param a
     * @return
     */
    public static boolean gtZero(BigDecimal a) {
        return gt(a, BigDecimal.ZERO);
    }

    /**
     * 判断一个数是否大于等于0
     *
     * @param a
     * @return
     */
    public static boolean geZero(BigDecimal a) {
        return ge(a, BigDecimal.ZERO);
    }

    /**
     * 判断一个数是否小于0
     *
     * @param a
     * @return
     */
    public static boolean ltZero(BigDecimal a) {
        return lt(BigDecimalUtil.isEmpty(a), BigDecimal.ZERO);
    }

    /**
     * 判断一个数是否小于等于0
     *
     * @param a
     * @return
     */
    public static boolean leZero(BigDecimal a) {
        return le(a, BigDecimal.ZERO);
    }

    /**
     * 与<code>BigDecimal.setScale(2, RoundingMode.HALF_UP)相同</code>
     * <p>
     * for example
     *
     * <pre>
     * BigDecimalUtils.scale2RoundingUp(new BigDecimal("2.555"))  = 2.56
     *  BigDecimalUtils.scale2RoundingUp(new BigDecimal("2.554"))  = 2.55
     *  BigDecimalUtils.scale2RoundingUp(new BigDecimal("-2.554"))  = 2.55
     *  BigDecimalUtils.scale2RoundingUp(new BigDecimal("-2.556"))  = 2.56
     * </pre>
     *
     * @param src
     * @return
     */
    public static BigDecimal scale2RoundingUp(BigDecimal src) {
        return src.setScale(2, RoundingMode.HALF_UP);
    }

    /**
     * 与<code>BigDecimal.setScale(4, RoundingMode.HALF_UP)相同</code>
     * <p>
     * for example
     *
     * <pre>
     * BigDecimalUtils.scale4RoundingUp(new BigDecimal("2.55555"))  = 2.5556
     *  BigDecimalUtils.scale4RoundingUp(new BigDecimal("2.55554"))  = 2.5555
     *  BigDecimalUtils.scale4RoundingUp(new BigDecimal("-2.55554"))  = 2.5555
     *  BigDecimalUtils.scale4RoundingUp(new BigDecimal("-2.55556"))  = 2.5556
     * </pre>
     *
     * @param src
     * @return
     */
    public static BigDecimal scale4RoundingUp(BigDecimal src) {
        return src.setScale(4, RoundingMode.HALF_UP);
    }

    /**
     * 与<code>BigDecimal.setScale(4, RoundingMode.HALF_UP)相同</code>
     * <p>
     * for example
     *
     * <pre>
     * BigDecimalUtils.scale4RoundingUp(new BigDecimal("2.55555"))  = 2.5556
     *  BigDecimalUtils.scale4RoundingUp(new BigDecimal("2.55554"))  = 2.5555
     *  BigDecimalUtils.scale4RoundingUp(new BigDecimal("-2.55554"))  = 2.5555
     *  BigDecimalUtils.scale4RoundingUp(new BigDecimal("-2.55556"))  = 2.5556
     * </pre>
     *
     * @param money
     * @return
     */
    public static BigDecimal scale4RoundingUp(String money) {
        BigDecimal src = new BigDecimal(String.valueOf(money));
        return src.setScale(4, RoundingMode.HALF_UP);
    }

    public static BigDecimal valueOf(String money) {
        if(StringUtils.isEmpty(money)){
            return new BigDecimal(0);
        }
        return new BigDecimal(String.valueOf(money));
    }

    /**
     * N个BigDecimal相乘，忽略为null的项
     *
     * @param bigDecimals
     * @return
     */
    public static BigDecimal safeMultiply(BigDecimal... bigDecimals) {

        if (bigDecimals == null) {
            return BigDecimal.ZERO;
        }
        BigDecimal retDecimal = new BigDecimal("1");
        for (BigDecimal i : bigDecimals) {
            if (i == null) {
                continue;
            }
            retDecimal = retDecimal.multiply(i);
        }
        return retDecimal;
    }

    /**
     * 数值保留两位小数
     *
     *            转换前数值
     * @return 转换后字符串
     */
    public static String changeNumberType(BigDecimal big) {
        if (big == null) {
            return "";
        }
        big.setScale(2, RoundingMode.HALF_UP);
        DecimalFormat format = new DecimalFormat("##0.00");
        return format.format(big);
    }

    /**
     * 数值保留4位小数
     *
     *            转换前数值
     * @return 转换后字符串
     */
    public static String change4NumberType(BigDecimal big) {
        if (big == null) {
            return "";
        }
        big.setScale(4, RoundingMode.HALF_UP);
        DecimalFormat format = new DecimalFormat("##0.0000");
        return format.format(big);
    }

    /**
     * 加
     *
     * @param v1
     * @param v2
     * @return
     */
    public static BigDecimal add(BigDecimal v1, BigDecimal v2) {
        if (v1 == null) {
            v1 = BigDecimal.ZERO;
        }

        if (v2 == null) {
            v2 = BigDecimal.ZERO;
        }
        return v1.add(v2);
    }

    /**
     * 减
     *
     * @param v1
     * @param v2
     * @return
     */
    public static BigDecimal sub(BigDecimal v1, BigDecimal v2) {
        if (v1 == null) {
            v1 = BigDecimal.ZERO;
        }

        if (v2 == null) {
            v2 = BigDecimal.ZERO;
        }
        return v1.subtract(v2);
    }

    /**
     * 乘
     *
     * @param v1
     * @param v2
     * @return
     */
    public static BigDecimal mul(BigDecimal v1, BigDecimal v2) {
        if (v1 == null) {
            v1 = BigDecimal.ZERO;
        }

        if (v2 == null) {
            v2 = BigDecimal.ZERO;
        }
        return v1.multiply(v2);
    }

    /**
     * 除
     *
     * @param v1
     * @param v2
     * @return
     */
    public static BigDecimal div(BigDecimal v1, BigDecimal v2) {
        if (v1 == null) {
            v1 = BigDecimal.ZERO;
        }

        if (v2 == null) {
            v2 = BigDecimal.ZERO;
        }
        return div(v1, v2, DEF_DIV_SCALE);
    }

    /**
     * 除
     *
     * @param v1
     * @param v2
     * @return
     */
    public static BigDecimal div(BigDecimal v1, double v2) {
        if (v1 == null) {
            v1 = BigDecimal.ZERO;
        }

        BigDecimal b2 = new BigDecimal(v2);
        return div(v1, b2, DEF_DIV_SCALE);
    }

    public static BigDecimal div(BigDecimal v1, BigDecimal v2, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException(
                    "The scale must be a positive integer or zero");
        }

        if (v1 == null) {
            v1 = BigDecimal.ZERO;
        }

        if (v2 == null) {
            v2 = BigDecimal.ZERO;
        }

        return v1.divide(v2, scale, BigDecimal.ROUND_HALF_UP);
    }

    public static BigDecimal isEmpty(BigDecimal bigDecimal){
        if(null==bigDecimal){
            return new BigDecimal(0);
        }
        if(new BigDecimal(0E-8)==bigDecimal){
            return new BigDecimal(0);
        }
        if(new BigDecimal(0E-12)==bigDecimal) {
            return new BigDecimal(0);
        }
        return bigDecimal;
    }
}
