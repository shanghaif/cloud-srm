package com.midea.cloud.common.utils;

import com.googlecode.aviator.AviatorEvaluator;
import com.googlecode.aviator.runtime.type.AviatorDouble;
import com.googlecode.aviator.runtime.type.AviatorNumber;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collector;

/**
 * <pre>
 *  价格计算
 * </pre>
 *
 * @author linxc6@meicloud.com
 * @version 1.00.00
 * <p>
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-3-25 9:26
 *  修改内容:
 * </pre>
 */
public class NumberUtil {

    /**
     * 两数相加
     */
    public static double add(double num1, double num2) {
        BigDecimal decimal1 = doubleToBigDecimal(num1);
        BigDecimal decimal2 = doubleToBigDecimal(num2);
        return decimal1.add(decimal2).doubleValue();
    }

    /**
     * 两数相减
     */
    public static double sub(double num1, double num2) {
        BigDecimal decimal1 = doubleToBigDecimal(num1);
        BigDecimal decimal2 = doubleToBigDecimal(num2);
        return decimal1.subtract(decimal2).doubleValue();
    }

    /**
     * 两数相除
     */
    public static double div(double num1, double num2) {
        BigDecimal decimal1 = doubleToBigDecimal(num1);
        BigDecimal decimal2 = doubleToBigDecimal(num2);
        return decimal1.divide(decimal2, 5, RoundingMode.HALF_UP).doubleValue();
    }

    /**
     * 两数相乘
     */
    public static double mul(double num1, double num2) {
        BigDecimal decimal1 = doubleToBigDecimal(num1);
        BigDecimal decimal2 = doubleToBigDecimal(num2);
        return decimal1.multiply(decimal2).doubleValue();
    }

    /**
     * double转BigDecimal
     */
    public static BigDecimal doubleToBigDecimal(double doubleValue) {
        return new BigDecimal(String.valueOf(doubleValue));
    }

    /**
     * 四舍五入保留多少位小数
     * @param newScale 保留多少位小数
     */
    public static double formatDoubleByScale(double num, int newScale) {
        BigDecimal bg = doubleToBigDecimal(num).setScale(newScale, RoundingMode.UP);
        return bg.doubleValue();
    }

    /**
     * 不四舍五入保留多少位小数
     * @param newScale 保留多少位小数
     */
    public static double formatDoubleByScaleN(double num, int newScale) {
        BigDecimal bg = doubleToBigDecimal(num).setScale(newScale, RoundingMode.DOWN);
        return bg.doubleValue();
    }

    /**
     * 百分数转小数
     * @param str 百分数, 如:34%
     * @return
     */
    public static double percentageToDouble(String str){
        // 获取百分数处理对象
        NumberFormat pf = NumberFormat.getPercentInstance(Locale.CHINA);
        try {
            // 百分数转数字
            return (Double) pf.parse(str);
        } catch (Exception e) {
            return -1;
        }
    }

    /**
     * 百分数转小数
     * @param num 小数
     * @return
     */
    public static String doubleToPercentage(Double num){
        // 获取百分数处理对象
        NumberFormat pf = NumberFormat.getPercentInstance(Locale.CHINA);
        try {
            // 数字转百分数
            return pf.format(num);
        } catch (Exception e) {
            return "-1";
        }
    }

    /**
     * 公式解析
     * @param formula 公式
     * @param num 保留最大小数位
     * @return
     */
    public static BigDecimal formulaToBigDecimal(String formula,int num){
        BigDecimal bigDecimal = BigDecimal.ZERO;
        if (StringUtil.notEmpty(formula)) {
            Object execute = AviatorEvaluator.execute(formula);
            AviatorNumber number = AviatorDouble.valueOf(execute);
            String value = String.valueOf(number.doubleValue());
            bigDecimal = new BigDecimal(value);
            if(StringUtil.notEmpty(num != 0)){
                bigDecimal = bigDecimal.setScale(num, RoundingMode.UP);
            }
        }
        return bigDecimal;
    }

    public static <T>
    Collector<T, ?, BigDecimalSummaryStatistics> summarizingBigDecimal(ToBigDecimalFunction<? super T> mapper) {
        return Collector.of(BigDecimalSummaryStatistics::new,
                (s, t) -> s.accept(mapper.applyAsBigDecimal(t))
                , (s1, s2) -> s1.combine(s2),
                s -> s, Collector.Characteristics.IDENTITY_FINISH);
    }

    public static <T>
    Collector<T, ?, BigDecimal> sumBigDecimal(ToBigDecimalFunction<? super T> mapper) {
        return Collector.of(() -> new BigDecimal[]{BigDecimal.ZERO},
                (b, t) -> b[0] = b[0].add(mapper.applyAsBigDecimal(t))
                , (sum1, sum2) -> {
                    sum1[0] = sum1[0].add(sum2[0]);
                    return sum1;
                },
                bigDecimals -> bigDecimals[0]
                , Collector.Characteristics.UNORDERED
        );
    }

    public static void main(String[] args) {
        Map<String, Object> map = new HashMap<>(16);
        map.put("a",BigDecimal.valueOf(13.15));
        map.put("b",BigDecimal.valueOf(2));
        Object execute = AviatorEvaluator.execute("a/b", map);
        System.out.println(execute.getClass().getName());
        System.out.println(execute);
    }

}