package com.midea.cloud.gernator.util;

import java.net.URLEncoder;
import java.util.Date;
import java.util.Random;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtil {

	public static final char UNDERLINE = '_';

	public static String camelToUnderline(String param) {
		if (param == null || "".equals(param.trim())) {
			return "";
		}
		int len = param.length();
		StringBuilder sb = new StringBuilder(len);
		for (int i = 0; i < len; i++) {
			char c = param.charAt(i);
			if (Character.isUpperCase(c)) {
				sb.append(UNDERLINE);
				sb.append(Character.toLowerCase(c));
			} else {
				sb.append(c);
			}
		}
		return sb.toString();
	}

	public static String underlineToCamel(String param) {
		if (param == null || "".equals(param.trim())) {
			return "";
		}
		int len = param.length();
		StringBuilder sb = new StringBuilder(len);
		for (int i = 0; i < len; i++) {
			char c = param.charAt(i);
			if (c == UNDERLINE) {
				if (++i < len) {
					sb.append(Character.toUpperCase(param.charAt(i)));
				}
			} else {
				sb.append(c);
			}
		}
		return sb.toString();
	}

	public static String underlineToCamelSub(String param) {
		if (param == null || "".equals(param.trim())) {
			return "";
		}
		StringBuilder sb = new StringBuilder(param);
		Matcher mc = Pattern.compile("_").matcher(param);
		int i = 0;
		while (mc.find()) {
			int position = mc.end() - (i++);
			// String.valueOf(Character.toUpperCase(sb.charAt(position)));
			sb.replace(position - 1, position + 1, sb.substring(position, position + 1).toUpperCase());
		}
		return sb.toString();
	}

	/**
	 * 对数组字符串中的元素作为key，判断是否匹配，如aafff是否匹配aa*,bb*
	 * @param arrKeys
	 * @param strParam
	 * @return
	 */
	public static int getIndexByKeys(String strKeys,String strParam) {
		int intIndex =-1;
		if( strKeys==null || "".equals(strKeys) ){
			return -1;
		}
		String[] arrKeys =strKeys.split(",");
		for(String strTemp : arrKeys){
			int intIndexNew =strParam.indexOf(strTemp);
			
			if( intIndexNew>intIndex ){
				intIndex =intIndexNew;
			}
		}
		return intIndex;
	}
	
	/**
	 * 获取随机机键值
	 * @param charLength 随机字母长数
	 * @return 格式：时间+随机字母
	 */
	public static String getRandomKey(Integer charLength){
		if(null == charLength || 3 > charLength){
			return null;
		}
		Long time = new Date().getTime();
		String chars = "abcdefghijklmnopqrstuvwxyz";
		chars += chars.toUpperCase();
		String randomKey = time.toString();
		for (int i = 0; i < charLength; i++) {
			randomKey += chars.charAt(new Random().nextInt(52));
		}
		return randomKey;
	}
	/**
	 * 获取随机字母
	 * @return
	 */
	public static Character getRandomChar(){
		String chars = "abcdefghijklmnopqrstuvwxyz";
		chars += chars.toUpperCase();
		return chars.charAt(new Random().nextInt(52));
	}
	
	/**
	 * 获取随机数字
	 * @param charLength 随机字母长数
	 * @return 格式：时间+随机数字
	 */
	public static String getRandomNumberByNum(Integer num) { 
		Long time = System.currentTimeMillis();
		String nowTime = time.toString();  
        StringBuffer sb = new StringBuffer();  
        for (int i = 0; i < num; i++) {  
            long randomNum = Math.round(Math.floor(Math.random() * 10.0D));  
            sb.append(randomNum);  
        }
        nowTime += sb.toString();
        return nowTime.toString();  
    }  
	
	
	public static String fromatStringByUserAgent(String strUserAgent,String strInfo){
		try {
			if( strUserAgent==null ){
				return strInfo;
			}
			//解决中文文件名乱码问题
			if (strUserAgent.toLowerCase().indexOf("firefox") > 0){
				return new String(strInfo.getBytes("UTF-8"), "ISO8859-1");//firefox浏览器
			}else if (strUserAgent.toUpperCase().indexOf("MSIE") > 0){
				return  URLEncoder.encode(strInfo, "UTF-8");//IE浏览器			
			}else{
				return new String(strInfo.getBytes("UTF-8"), "ISO8859-1");
			}
		} catch (Exception e) {
		
		}
		return strInfo;
	}
	
	public static String formatFirstUpper(String sValue){
		String sResult =sValue.substring(0,1).toUpperCase()+sValue.substring(1);
		return sResult;
	}
	
	
	/** 
     * 取字符串中第一个子串右边的内容 
     *  
     * <pre> 
     * 例如： 
     * strRight("","")="" 
     * strRight("",null)="" 
     * strRight("","@")="" 
     * strRight(null,"")="" 
     * strRight(null,null)="" 
     * strRight(null,"@")="" 
     * strRight("test@gmail.com@test.com","")="" 
     * strRight("test@gmail.com@test.com",null)="" 
     * strRight("test@gmail.com@test.com","@")="gmail.com@test.com" 
     * strRight("test@gmail.com@test.com","co")="m@test.com" 
     * strRight("test@gmail.com@test.com","abc")="" 
     * </pre> 
     *  
     * @param text 字符串 
     * @param subtext 搜索子串 
     * @return 
     */  
    public static String strRight(final String text, String subtext) {  
        if (!hasText(text) || !hasText(subtext)) {  
            return "";  
        }  
  
        int find = text.indexOf(subtext);  
        return (find != -1) ? text.substring(find + subtext.length()) : "";  
    }  
  
    /** 
     * 取字符串中最后一个子串左边的内容 
     *  
     * <pre> 
     * 例如： 
     * strLeftBack("","")="" 
     * strLeftBack("",null)="" 
     * strLeftBack("","@")="" 
     * strLeftBack(null,"")="" 
     * strLeftBack(null,null)="" 
     * strLeftBack(null,"@")="" 
     * strLeftBack("test@gmail.com@test.com","")="" 
     * strLeftBack("test@gmail.com@test.com",null)="" 
     * strLeftBack("test@gmail.com@test.com","@")="test@gmail.com" 
     * strLeftBack("test@gmail.com@test.com","co")="test@gmail.com@test." 
     * strLeftBack("test@gmail.com@test.com","abc")="" 
     * </pre> 
     *  
     * @param text 字符串 
     * @param subtext 搜索子串 
     * @return 
     */  
    public static String strLeftBack(final String text, String subtext) {  
        if (!hasText(text) || !hasText(subtext)) {  
            return "";  
        }  
  
        int find = text.lastIndexOf(subtext);  
        return (find != -1) ? text.substring(0, find) : "";  
    }  
  
    /** 
     * 取字符串中最后一个子串右边的内容 
     *  
     * <pre> 
     * 例如： 
     * strRightBack("","")="" 
     * strRightBack("",null)="" 
     * strRightBack("","@")="" 
     * strRightBack(null,"")="" 
     * strRightBack(null,null)="" 
     * strRightBack(null,"@")="" 
     * strRightBack("test@gmail.com@test.com","")="" 
     * strRightBack("test@gmail.com@test.com",null)="" 
     * strRightBack("test@gmail.com@test.com","@")="test.com" 
     * strRightBack("test@gmail.com@test.com","co")="m" 
     * strRightBack("test@gmail.com@test.com","abc")="" 
     * </pre> 
     *  
     * @param text 字符串 
     * @param subtext 搜索子串 
     * @return 
     */  
    public static String strRightBack(final String text, String subtext) {  
        if (!hasText(text) || !hasText(subtext)) {  
            return "";  
        }  
  
        int find = text.lastIndexOf(subtext);  
        return (find != -1) ? text.substring(find + subtext.length()) : "";  
    }  
  
    /** 
     * 校验给定字符串中是否有文本 
     *  
     * <pre> 
     * 例如： 
     * hasText("")=false 
     * hasText(null)=false 
     * hasText("test@gmail.com@test.com")=true 
     * hasText("@")=true 
     * </pre> 
     *  
     * @param text 字符串 
     * @return 
     */  
    public static boolean hasText(String text) {  
        return (text != null) && (!"".equals(text));  
    }
	public static String getUuid(){
		String uuid = UUID.randomUUID().toString().replaceAll("-","");
		return  uuid;
	}
	/*
	首个字母大写
	 */
	public static String upperCase(String str) {
		return str.substring(0, 1).toUpperCase() + str.substring(1);
	}
    
	public static void main(String[] args) {

		System.out.println(StringUtil.getIndexByKeys("aa,bb", "bbfff"));
		
	}
}
