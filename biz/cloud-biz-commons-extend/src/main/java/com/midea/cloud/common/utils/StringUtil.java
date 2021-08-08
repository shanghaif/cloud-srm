/**
 *
 */
package com.midea.cloud.common.utils;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static org.apache.commons.lang3.StringUtils.isNotBlank;

/**
 *
 * @author Nianhua Zhang
 * @since 1.0
 *
 */
public class StringUtil {

    // 标识忽略
    public static final int ignoreTg = 0;

    private static final char SEPARATOR = '_';

    // 标识已读取到'$'字符
    public static final int startTg = 1;
    // 标识已读取到'{'字符
    public static final int readTg = 2;

    private StringUtil() {
    }

    /**
     *
     * @param o
     * @return
     */
    public static boolean isEmpty(Object o) {
        return o == null || "".equals(o.toString().trim());
    }

    /**
     *
     * @param o
     * @return
     */
    public static boolean notEmpty(Object o) {
        return !isEmpty(o);
    }

    /**
     * 分割
     *
     * @param text
     * @param reg
     * @return
     */
    public static String[] split(String text, String reg) {
        String[] ss = text.split(reg);
        String[] result = new String[0];
        for (String s : ss) {
            if (isEmpty(s)) {
                continue;
            } else {
                int length = result.length + 1;
                result = Arrays.copyOf(result, length);
                result[length - 1] = s;
            }
        }
        return result;
    }

    /**
     * 是否数字
     *
     * @param text
     * @return
     */
    public static boolean isDigit(String text) {
        if (isEmpty(text)) {
            return false;
        }
        Pattern pattern = Pattern.compile("^[-\\+]?[0-9]*[.]?[0-9]*$");
        return pattern.matcher(text).matches();
    }

    //判断一个字符是否都为数字
    public static boolean isContaitNumber(String strNum) {
        boolean flag = false;
        Pattern p = Pattern.compile(".*\\d+.*");
        Matcher m = p.matcher(strNum);
        if (m.matches()) {
            flag = true;
        }
        return flag;
    }

    /**
     * 移除最后一个字符
     * @param str
     * @return
     */
    public static String removeLastSymbol(String str){
        if(str == null){
            return null;
        }
        return str.length() > 0 ? str.substring(0 , str.length()-1) : new String();
    }

    /**
     * 是否包含特殊字符
     */
    public static boolean checkString(String text) {
        if (isEmpty(text)) {
            return false;
        }
        Pattern pattern = Pattern.compile("[ `~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]|\n|\r|\t");
        return pattern.matcher(text).matches();
    }

    public static List<Long> stringConvertNumList(String target, String reg) {
        if (StringUtil.isEmpty(target))
            return new ArrayList<>();
        String[] strs = split(target, reg);
        Long[] longs = new Long[strs.length];
        for (int i = 0; i < strs.length; i++) {
            String str = strs[i];
            long thelong = Long.valueOf(str);
            longs[i] = thelong;
        }
        return Arrays.asList(longs);
    }

    /**
     * 随机字符生成
     *
     * @param length 生成字符长度
     * @return
     */
    public static String genChar(int length) {
        char[] ss = new char[length];
        int i = 0;
        while (i < length) {
            int f = (int) (Math.random() * 3);
            if (f == 0)
                ss[i] = (char) ('A' + Math.random() * 26);
            else if (f == 1)
                ss[i] = (char) ('a' + Math.random() * 26);
            else
                ss[i] = (char) ('0' + Math.random() * 10);
            i++;
        }
        String str = new String(ss);
        return str;
    }

    /**
     * 随机密码生成
     *
     * @param length 生成字符长度
     * @return
     */
    public static String genPwdChar(int length) {
        char[] special = new char[]{'~', '!', '@', '#', '$', '%', '^', '&', '(', ')', '{', '}'};
        char[] ss = new char[length];
        int i = 0;
        while (i < length) {
            int f = i % 4;
            if (f == 0)
                ss[i] = (char) ('A' + Math.random() * 26);
            else if (f == 1)
                ss[i] = (char) ('a' + Math.random() * 26);
            else if (f == 2)
                ss[i] = (char) (special[(int) (Math.random() * special.length)]);
            else
                ss[i] = (char) ('0' + Math.random() * 10);
            i++;
        }
        String str = new String(ss);
        return str;
    }

    /**
     * Description Object类型转为字符串 @Param object 类型 @return @Author
     * wuwl18@meicloud.com @Date 2020.03.25 @throws
     **/
    public static String StringValue(Object object) {
        if (object == null) {
            return "";
        } else {
            return object.toString();
        }
    }

    /**
     * Description 下划线转为杠(_转换为-)
     *
     * @Param
     * @return
     * @Author wuwl18@meicloud.com
     * @Date 2020.03.27
     **/
    public static String getUnderlineConverBar(String underlineStr) {
        String result = "";
        if (isNotBlank(underlineStr)) {
            result = underlineStr.replace("_", "-");
        }
        return result;
    }

    /**
     * obj对象string类型的属性，进行trim（）
     *
     * @param obj
     */
    public static void trim(Object obj) {
        if (obj != null) {
            Field[] declaredFields = obj.getClass().getDeclaredFields();
            for (Field field : declaredFields) {
                field.setAccessible(true);
                if (field.getType() == String.class) {
                    try {
                        field.set(obj, field.get(obj) == null ? null : field.get(obj).toString().trim());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    /**
     * 获取值,空则默认为默认值
     * @param param 目标值
     * @param def 默认值
     * @param <T>
     * @return
     */
    public static <T> T getValue(T param, T def) {
        if (notEmpty(param)) {
            return param;
        } else {
            return def;
        }
    }

    /**
     * 转换为Double类型
     */
    public static Double toDouble(Object val) {
        if (val == null) {
            return 0D;
        }
        try {
            return Double.valueOf(StringUtils.trim(val.toString()));
        } catch (Exception e) {
            return 0D;
        }
    }

    /**
     * 转换为Float类型
     */
    public static Float toFloat(Object val) {
        return toDouble(val).floatValue();
    }

    /**
     * 转换为Long类型
     */
    public static Long toLong(Object val) {
        return toDouble(val).longValue();
    }

    /**
     * 转换为Integer类型
     */
    public static Integer toInteger(Object val) {
        return toLong(val).intValue();
    }

    /**
     * 获得用户远程地址
     */
    public static String getRemoteAddr(HttpServletRequest request) {
        String remoteAddr = request.getHeader("X-Real-IP");
        if (isNotBlank(remoteAddr)) {
            remoteAddr = request.getHeader("X-Forwarded-For");
        } else if (isNotBlank(remoteAddr)) {
            remoteAddr = request.getHeader("Proxy-Client-IP");
        } else if (isNotBlank(remoteAddr)) {
            remoteAddr = request.getHeader("WL-Proxy-Client-IP");
        }
        return remoteAddr != null ? remoteAddr : request.getRemoteAddr();
    }

    /**
     * 是否包含字符串
     *
     * @param str  验证字符串
     * @param strs 字符串组
     * @return 包含返回true
     */
    public static boolean inString(String str, String... strs) {
        if (str != null) {
            for (String s : strs) {
                if (str.equals(StringUtils.trim(s))) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 驼峰命名法工具
     *
     * @return toCamelCase(" hello_world ") == "helloWorld"
     * toCapitalizeCamelCase("hello_world") == "HelloWorld"
     * toUnderScoreCase("helloWorld") = "hello_world"
     */
    public static String toCamelCase(String s) {
        if (s == null) {
            return null;
        }

        s = s.toLowerCase();

        StringBuilder sb = new StringBuilder(s.length());
        boolean upperCase = false;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);

            if (c == SEPARATOR) {
                upperCase = true;
            } else if (upperCase) {
                sb.append(Character.toUpperCase(c));
                upperCase = false;
            } else {
                sb.append(c);
            }
        }

        return sb.toString();
    }

    /**
     * 驼峰命名法工具
     *
     * @return toCamelCase(" hello_world ") == "helloWorld"
     * toCapitalizeCamelCase("hello_world") == "HelloWorld"
     * toUnderScoreCase("helloWorld") = "hello_world"
     */
    public static String toCapitalizeCamelCase(String s) {
        if (s == null) {
            return null;
        }
        s = toCamelCase(s);
        return s.substring(0, 1).toUpperCase() + s.substring(1);
    }

    /**
     * 驼峰命名法工具
     *
     * @return toCamelCase(" hello_world ") == "helloWorld"
     * toCapitalizeCamelCase("hello_world") == "HelloWorld"
     * toUnderScoreCase("helloWorld") = "hello_world"
     */
    public static String toUnderScoreCase(String s) {
        if (s == null) {
            return null;
        }

        StringBuilder sb = new StringBuilder();
        boolean upperCase = false;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);

            boolean nextUpperCase = true;

            if (i < (s.length() - 1)) {
                nextUpperCase = Character.isUpperCase(s.charAt(i + 1));
            }

            if ((i > 0) && Character.isUpperCase(c)) {
                if (!upperCase || !nextUpperCase) {
                    sb.append(SEPARATOR);
                }
                upperCase = true;
            } else {
                upperCase = false;
            }

            sb.append(Character.toLowerCase(c));
        }

        return sb.toString();
    }

    /**
     * 如果不为空，则设置值
     *
     * @param target
     * @param source
     */
    public static void setValueIfNotBlank(String target, String source) {
        if (isNotBlank(source)) {
            target = source;
        }
    }

    /**
     * 获取字符串里的占位符列表, 占位符样式 ${param}
     * @param content
     * @return
     */
    public static List<String> getPlaceholderList(String content) {
        char[] textChars = content.toCharArray();
        StringBuilder textSofar = new StringBuilder();
        StringBuilder sb = new StringBuilder();
        // 存储占位符 位置信息集合
        List<String> placeholderList = new ArrayList<>();
        // 当前标识
        int modeTg = ignoreTg;
        for (int m = 0; m < textChars.length; m++) {
            char c = textChars[m];
            textSofar.append(c);
            switch (c) {
                case '$': {
                    modeTg = startTg;
                    sb.append(c);
                }
                break;
                case '{': {
                    if (modeTg == startTg) {
                        sb.append(c);
                        modeTg = readTg;
                    } else {
                        if (modeTg == readTg) {
                            sb = new StringBuilder();
                            modeTg = ignoreTg;
                        }
                    }
                }
                break;
                case '}': {
                    if (modeTg == readTg) {
                        modeTg = ignoreTg;
                        sb.append(c);
                        String str = sb.toString();
                        if (StringUtil.notEmpty(str)) {
                            placeholderList.add(str);
                            textSofar = new StringBuilder();
                        }
                        sb = new StringBuilder();
                    } else if (modeTg == startTg) {
                        modeTg = ignoreTg;
                        sb = new StringBuilder();
                    }
                }
                default: {
                    if (modeTg == readTg) {
                        sb.append(c);
                    } else if (modeTg == startTg) {
                        modeTg = ignoreTg;
                        sb = new StringBuilder();
                    }
                }
            }
        }
        return placeholderList;
    }

    /**
     * 是否包含特殊字符(不包含/)
     */
    public static boolean checkStringNoSlash(String text) {
        if (isEmpty(text)) {
            return false;
        }
        Pattern pattern = Pattern.compile("[ `~!@#$%^&*()+=|{}':;',\\[\\].<>?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]|\n|\r|\t");
        return pattern.matcher(text).matches();
    }

    /**
     * 获取字符串里的占位符列表, 占位符样式 [param]
     * @param content
     * @return
     */
    public static List<String> getBracketsList(String content) {
        List<String> placeholderList = null;
        if (StringUtil.notEmpty(content)) {
            char[] textChars = content.toCharArray();
            StringBuffer sb = null;
            // 存储占位符 位置信息集合
            placeholderList = new ArrayList<>();
            boolean flag = false;

            if (ArrayUtils.isNotEmpty(textChars)) {
                for (char c : textChars) {
                    switch (c) {
                        case '[': {
                            sb = new StringBuffer();
                            sb.append(c);
                            flag = true;
                            break;
                        }
                        case ']': {
                            if (flag) {
                                sb.append(c);
                                placeholderList.add(sb.toString());
                                flag = false;
                                sb = null;
                            }
                        }
                        default: {
                            if (flag) {
                                sb.append(c);
                            }
                        }
                    }
                }
            }
        }
        // 去重
        ArrayList<String> collect = null;
        if (CollectionUtils.isNotEmpty(placeholderList)) {
            collect = placeholderList.stream().collect(Collectors.collectingAndThen(
                    Collectors.toCollection(() -> new TreeSet<>()), ArrayList::new
                    )
            );
        }
        return collect;
    }

    /**
     * 判断一个字符是否是中文
     * */
    public static boolean isChineseCharacter(char c) {
        return c >= 0x4E00 && c <= 0x9FA5;// 根据字节码判断
    }

    /**
     * 是否包含特殊字符(除了英文逗号)
     */
    public static boolean checkString1(String text) {
        if (isEmpty(text)) {
            return false;
        }
        Pattern pattern = Pattern.compile("[ `~!@#$%^&*()+=|{}':;'\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]|\n|\r|\t");
        return pattern.matcher(text).matches();
    }

    /**
     * 是否包含中文符号
     */
    public static boolean checkString2(String text) {
        if (isEmpty(text)) {
            return false;
        }
        Pattern pattern = Pattern.compile("[ `~@#$%^{}':;\\[\\]<>?！￥…*（【】‘；：”“’。，、？]|\n|\r|\t");
        return pattern.matcher(text).matches();
    }

	/**
	 * 字串串转int数组
	 * @param str
	 * @return
	 */
    public static int[] strArrayInt(String str) {
    	if (StringUtils.isNotEmpty(str)){
			//获取需要切割的字符串
			String[] split = str.split(",");
			for (String s:split){
                if (!StringUtils.isNumeric(s)) {
                    return null;
                }
            }
			//字符串数组转int数组
			return Arrays.asList(split).stream().mapToInt(Integer::parseInt).toArray();
		}else{
    		return null;
		}
    }

    /**
     * 使用java正则表达式去掉多余的.与0
     * @param s
     * @return
     */
    public static String subZeroAndDot(String s){
        if(notEmpty(s) &&  s.indexOf(".") > 0){
            s = s.replaceAll("0+?$", "");//去掉多余的0
            s = s.replaceAll("[.]$", "");//如最后一位是.则去掉
        }
        return s;
    }

    public static Pattern pattern1 = Pattern.compile("\\d{1,10}\\.?\\d{0,5}");
    public final static Pattern pattern = Pattern.compile("(-?\\d+\\.?\\d*)[Ee]{1}[\\+-]?[0-9]*");
    public final static DecimalFormat ds = new DecimalFormat("0");
    public final static boolean isENum(String input) {//判断输入字符串是否为科学计数法
        return pattern.matcher(input).matches();
    }

    /**
     * 判断是否为科学数值, 是则进行科学计数法转十进制数字
     * @param num 值
     * @return
     */
    public static String numConvert(String num){
        if(null != num){
            if(isENum(num)){
                Double d = 1.6D;
                //使得结果精确的初始化姿势
                BigDecimal bigDecimal = new BigDecimal(d.toString());
                bigDecimal = new BigDecimal(num);
                String str = bigDecimal.toPlainString();
                return subZeroAndDot(str);
            }
            return subZeroAndDot(num);
        }else {
            return "0";
        }
    }


    /**
     * 将圆角的符号替换成非圆角
     * 圆角符号：，。？；’：“”（）！
     * @return
     */
    public static String replaceFillet(String tmp){
        tmp = tmp.replaceAll("，", ",");
        tmp = tmp.replaceAll("。", ".");
        tmp = tmp.replaceAll("？", "?");
        tmp = tmp.replaceAll("；", ";");
        tmp = tmp.replaceAll("’", "'");
        tmp = tmp.replaceAll("：", ":");
        tmp = tmp.replaceAll("“", "\"");
        tmp = tmp.replaceAll("”", "\"");
        tmp = tmp.replaceAll("（", "(");
        tmp = tmp.replaceAll("）", ")");
        tmp = tmp.replaceAll("！", "!");
        return tmp;
    }
    //首字母大写方法
    public static String capitalFirstCase(String str){
        char[] chars = str.toCharArray();
        //首字母小写方法，
        //小写变大写
        chars[0] -=32;
        return String.valueOf(chars);
    }

    //首字母小写方法
    public static String lowerFirstCase(String str){
        char[] chars = str.toCharArray();
        //首字母小写方法，
        //大写会变成小写
        chars[0] +=32;
        return String.valueOf(chars);
    }

}
