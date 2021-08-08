package com.midea.cloud.common.utils;

import com.googlecode.aviator.AviatorEvaluator;
import com.midea.cloud.common.exception.BaseException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.ObjectUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

/**
 * <pre>
 * 公式解析工具类
 * </pre>
 *
 * @author wangpr@meiCloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-05-27 09:24:20
 *  修改内容:
 * </pre>
 */
public class FormulaUtil {

    // 公式key
    public static String key;

    /**
     * 用来判断布尔值
     * 1. 解决公式的参数含有[]而不能解析问题
     * 适用范围,
     * 例如: ( [层数] == '二层' )
     */
    public static boolean analyticalFormulaByString(String formula, Map<String , Object> param){
        boolean flag = false;
        try {
            if(!ObjectUtils.isEmpty(formula)){
                // 替换圆角符号
                formula = StringUtil.replaceFillet(formula);
                AtomicReference<String> atomicFormula = new AtomicReference<>(formula);
                Map<String, Object> map = new HashMap<>(16);
                param.forEach((key, obj) -> {
                    // 获取一个新key
                    String newKey = getNewKey();
                    map.put(newKey,obj);
                    atomicFormula.set(StringUtils.replace(atomicFormula.get(), key, newKey));
                });
                flag = (boolean)AviatorEvaluator.execute(atomicFormula.get(), map);
            }
        } catch (Exception e) {
            throw new BaseException("公式解析失败!");
        }
        return flag;
    }

    /**
     * 用来计算数字
     * 解决公式的参数含有[]而不能解析问题
     * @param formula 例如 [孔径]*[厚]*[孔数]*2
     * @param param 公式参数
     * @param scale 保留几位小数
     * 注意: 这种情况前提是运算公式不会包含 中括号[] 运算
     */
    public static BigDecimal analyticalFormulaByNum(String formula, Map<String , BigDecimal> param,int scale){
        BigDecimal result = null;
        try {
            if(!ObjectUtils.isEmpty(formula)){
                // 替换圆角符号
                formula = StringUtil.replaceFillet(formula);
                AtomicReference<String> atomicFormula = new AtomicReference<>(formula);
                Map<String, Object> map = new HashMap<>(16);
                param.forEach((key, obj) -> {
                    // 获取一个新key
                    String newKey = getNewKey();
                    map.put(newKey,obj);
                    atomicFormula.set(StringUtils.replace(atomicFormula.get(), key, newKey));
                });
                Object execute = AviatorEvaluator.execute(atomicFormula.get(), map);
                result = new BigDecimal(execute.toString()).setScale(scale, RoundingMode.UP);
            }
        } catch (Exception e) {
            throw new BaseException("公式解析失败!");
        }
        return result;
    }

    /**
     * 获取一个新key
     * @param key
     * @return
     */
    public static String getNewKey(){
        if(null == key){
            key = "a1";
        }else {
            String mid = StringUtils.mid(key, 1, key.length());
            key = "a"+ (Integer.parseInt(mid)+1);
        }
        return key;
    }

    public static void main(String[] args) {
        // 测试
        String formula = "[层数] == '三层' && [箱数] == 3 && [数字] > 4";
        Map<String, Object> map = new HashMap<>(16);
        map.put("[层数]","三层");
        map.put("[箱数]",3);
        map.put("[数字]",4);
        boolean b = analyticalFormulaByString(formula, map);
        System.out.println(b);
    }
}
