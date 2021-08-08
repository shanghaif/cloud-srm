package com.midea.cloud.common.utils;

import com.midea.cloud.common.exception.BaseException;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.springframework.util.Assert;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * <pre>
 * 时间处理公共类
 * </pre>
 *
 * @author wuwl18@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/4/16 10:32
 *  修改内容:
 *          </pre>
 */
public class DateUtil {

    public static final String DATE_FORMAT_7 = "yyyy-MM";
    public static final String DATE_FORMAT_7_FORWARD_SLASH = "yyyy/MM";
    public static final String DATE_FORMAT_10 = "yyyy-MM-dd";
    public static final String DATE_FORMAT_10_FORWARD_SLASH = "yyyy/MM/dd";
    public static final String DATE_FORMAT_14 = "yyyyMMddHHmmss";
    public static final String DATE_FORMAT_17 = "yyyyMMdd HH:mm:ss";
    public static final String DATE_FORMAT_19 = "yyyy-MM-dd HH:mm:ss";
    public static final String DATE_FORMAT_19_FORWARD_SLASH = "yyyy/MM/dd HH:mm:ss";
    /** 日期格式为yyyy/M/d或者yyyy/M/dd或者yyyy/MM/d **/
    public static final String DATE_FORMAT_8 = "yyyy-M-d";
    public static final String DATE_FORMAT_8_FORWARD_SLASH = "yyyy/M/d";
    public static final String DATE_FORMAT_9_1 = "yyyy-MM-d";
    public static final String DATE_FORMAT_9_1_FORWARD_SLASH = "yyyy/MM/d";
    public static final String DATE_FORMAT_9_2 = "yyyy-M-dd";
    public static final String DATE_FORMAT_9_2_FORWARD_SLASH = "yyyy/M/dd";

    private static final String MINUS = "-";
    private static final String SLASH = "/";
    private static final Calendar calendar=Calendar.getInstance();
    private static final Object lock=new Object();

    /**
     * 时间格式为yyyy-MM-dd HH:mm:ss
     */
    public static final String YYYY_MM_DD = "yyyy-MM-dd";

    /**
     * 时间格式为yyyy-MM-dd HH:mm:ss
     */
    public static final String YYYYMMDD_HHMMSS = "yyyy-MM-dd HH:mm:ss";

    /**
     * Description 获取时间和当前时间的时间间隔,返回小时(四舍五入不保留小数) @Param @return @Author
     * wuwl18@meicloud.com @Date 2020.04.16 @throws
     **/
    public static String getTimeAndNowInterval(Long time) {
        Long nowTime = new Date().getTime();
        Long betweenHours = (nowTime - time) / (1000 * 60 * 60);
        if (null == betweenHours) {
            betweenHours = 0L;
        }
        return betweenHours + "小时";
    }

    /**
     * Description Long类型获取字符串时间(格式：yyyy-MM-dd HH:mm:ss) @Param @return @Author
     * wuwl18@meicloud.com @Date 2020.04.16 @throws
     **/
    public static String getDateStrByLong(Object longTime, String formatStr) {
        String timeStr = "";
        if (null != longTime) {
            Date fdHandlerTime = new Date(Long.parseLong(String.valueOf(longTime)));
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(formatStr);
            timeStr = simpleDateFormat.format(fdHandlerTime);
        }
        return timeStr;
    }

    /**
     * Description 日期转为字符串(格式：yyyy-MM-dd HH:mm:ss) @Param @return @Author
     * wuwl18@meicloud.com @Date 2020.04.18 @throws
     **/
    public static String parseDateToStr(Date time, String formatStr) {
        String timeStr = "";
        if (null != time) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(formatStr);
            timeStr = simpleDateFormat.format(time);
        }
        return timeStr;
    }

    /**
     * Date转LocalDateTime
     *
     * @param date
     * @return
     */
    public static LocalDateTime dateToLocalDateTime(Date date) {
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
    }

    /**
     * Date转LocalDate
     *
     * @param date
     * @return
     */
    public static LocalDate dateToLocalDate(Date date) {
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }

    /**
     * LocalDateTime转Date
     *
     * @param localDateTime
     * @return
     */
    public static Date localDateTimeToDate(LocalDateTime localDateTime) {
        ZoneId zoneId = ZoneId.systemDefault();
        ZonedDateTime zdt = localDateTime.atZone(zoneId);
        return Date.from(zdt.toInstant());
    }

    public static void main(String[] args) throws ParseException {
        String intervalTime = DateUtil.getTimeAndNowInterval(1513325574000L);
        System.out.println("时间间隔：" + intervalTime);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        // 日期格式类
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
//        // 字符串转日期
//        LocalDateTime localDateTime = LocalDateTime.parse("2020-07-23 11:11:11",dateTimeFormatter);
//        Date date = localDateTimeToDate(localDateTime);
//        System.out.println(dateFormat.format(date));
        System.out.println(LocalDate.now().format(dateTimeFormatter));

        Map<String, Date> firstAndLastDayOfMonth = getFirstAndLastDayOfMonth();
        System.out.println(parseDateToStr(firstAndLastDayOfMonth.get("endDate"),"yyyy-MM-dd HH:mm:ss"));
        System.out.println(parseDateToStr(firstAndLastDayOfMonth.get("startDate"),"yyyy-MM-dd HH:mm:ss"));
    }

    /**
     * LocalDate格式化
     *
     * @param localDate
     * @return
     */
    public static String localDateToStr(LocalDate localDate) {
        if(null == localDate){
            return "";
        }
        // 日期格式类
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return localDate.format(dateTimeFormatter);
    }

    /**
     * Description LocalDate格式化
     *
     * @return
     * @Param localDate LocalDate类型时间
     * @Param dateFormat 转换格式
     * @Author wuwl18@meicloud.com
     * @Date 2020.06.23
     **/
    public static String localDateToStr(LocalDate localDate, String dateFormat) {
        if(null == localDate){
            return "";
        }
        // 日期格式类
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(dateFormat);
        return localDate.format(dateTimeFormatter);
    }

    /**
     * 字符串转Date
     *
     * @param dateString
     * @param dateFormat
     * @return
     * @throws ParseException
     */
    public static Date parseDate(String dateString, String dateFormat) throws ParseException {
        if (StringUtils.isEmpty(dateFormat)) {
            dateFormat = switchDateFormat(dateString);
        }
        return new SimpleDateFormat(dateFormat).parse(dateString);
    }

    /**
     * 字符串转Date
     *
     * @param dateString
     * @return
     * @throws ParseException
     */
    public static Date parseDate(String dateString) throws ParseException {
        Date date = parseDate(dateString, switchDateFormat(dateString));
        if (null != date) {
            return date;
        } else {
            throw new RuntimeException("Date conversion failed");
        }
    }
    /**
     * 字符串转LocalDate "yyyy-MM-dd HH:mm:ss"
     *
     * @param dateString
     * @return
     * @throws ParseException
     */
    public static LocalDate parseLocalDate(String dateString){
        if (StringUtils.isNotEmpty(dateString)){
            return LocalDate.parse(dateString, DateTimeFormatter.ofPattern(DATE_FORMAT_19));
        }
        return null;
    }

    /**
     * 根据字符串查找对应日期格式
     *
     * @param dateString
     * @return
     */
    private static String switchDateFormat(String dateString) {
        int length = dateString.length();
        switch (length) {
            case 19:
                if (dateString.contains(MINUS)) {
                    return DATE_FORMAT_19;
                } else {
                    return DATE_FORMAT_19_FORWARD_SLASH;
                }
            case 17:
                return DATE_FORMAT_17;
            case 14:
                return DATE_FORMAT_14;
            case 10:
                if (dateString.contains(MINUS)) {
                    return DATE_FORMAT_10;
                } else {
                    return DATE_FORMAT_10_FORWARD_SLASH;
                }
            case 9:
                if (dateString.charAt(6) >= '0' && dateString.charAt(0) <= '9') {
                    if (dateString.contains(MINUS)) {
                        return DATE_FORMAT_9_1;
                    }else {
                        return DATE_FORMAT_9_1_FORWARD_SLASH;
                    }
                } else if (dateString.charAt(6) == '-') {
                    return DATE_FORMAT_9_2;
                } else if (dateString.charAt(6) == '/') {
                    return DATE_FORMAT_9_2_FORWARD_SLASH;
                }
            case 8:
                if (dateString.contains(MINUS)) {
                    return DATE_FORMAT_8;
                }else {
                    return DATE_FORMAT_8_FORWARD_SLASH;
                }
            case 7:
                if (dateString.contains(MINUS)) {
                    return DATE_FORMAT_7;
                } else {
                    return DATE_FORMAT_7_FORWARD_SLASH;
                }
            default:
                throw new IllegalArgumentException("can not find date format for：" + dateString);
        }
    }

    /**
     * Date转字符串
     * <p>
     * yyyy-MM-dd HH:mm:ss
     *
     * @param date
     * @return
     */
    public static String format(Date date) {
        return format(date, null);
    }
    /**
     * Date转字符串
     * <p>
     * yyyy-MM-dd HH:mm:ss
     *
     * @param date
     * @return
     */
    public static String formatByDate(Date date) {
        return format(date, DATE_FORMAT_10);
    }

    /**
     * date转字符串
     *
     * @param date
     * @param dateFormat
     * @return
     */
    public static String format(Date date, String dateFormat) {
        if (date == null) {
            return "";
        }
        if (StringUtils.isEmpty(dateFormat)) {
            dateFormat = DATE_FORMAT_19;
        }
        return new SimpleDateFormat(dateFormat).format(date);
    }

    /**
     * Description Date类型的返回所需要的Date格式类型
     *
     * @return
     * @throws ParseException
     * @Param date 时间
     * @Param formatStr 时间格式
     * @Author wuwl18@meicloud.com
     * @Date 2020.06.10
     **/
    public static Date getDate(Date date, String formatStr) throws ParseException {
        if (date == null) {
            return null;
        }
        return parseDate(parseDateToStr(date, formatStr), formatStr);
    }

    /**
     * 获取时间方位列表
     * @param startDate 开始时间
     * @param endDate   结束时间
     * @param format    时间格式
     * @return
     */
    public static List<String> getMonthBetween(LocalDate startDate, LocalDate endDate, String format){
        if (StringUtil.isEmpty(startDate) || StringUtil.isEmpty(endDate)){
            return null;
        }
        // 日期格式类
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(format);
        Assert.isTrue(startDate.compareTo(endDate) <= 0 ,"startDate必须小于等于endDate");
        ArrayList<String> monthBetween= new ArrayList<>();
        do {
            monthBetween.add(startDate.format(dateTimeFormatter));
            startDate = startDate.plusMonths(1);
        }while (startDate.compareTo(endDate) <= 0);

        return monthBetween;
    }

    /**
     * 获取当月起末
     * @return
     * @throws ParseException
     */
    public static Map<String, Date> getFirstAndLastDayOfMonth() throws ParseException {
        Map<String, Date> dateMap = new HashMap<>();
        LocalDate startDate = LocalDate.now().withDayOfMonth(1);
        String startFormat = "${DATE} 00:00:00";
        String endFormat = "${DATE} 23:59:59";
        String start = StringUtils.replace(startFormat,"${DATE}",localDateToStr(startDate, "yyyy-MM-dd"));
        LocalDate endDate = startDate.plusMonths(1).plusDays(-1);
        String end = StringUtils.replace(endFormat, "${DATE}", localDateToStr(endDate, "yyyy-MM-dd"));
        dateMap.put("startDate",parseDate(start));
        dateMap.put("endDate",parseDate(end));
        return dateMap;
    }

    /**
     * 根据日期和模式获取时间
     * @param current
     * @param dayCount
     * @param mode
     * @return
     */
    public static Date getDate(Date current, Integer dayCount, int mode){
        synchronized (lock){
            calendar.setTime(current);
            calendar.add(mode,dayCount);
            return calendar.getTime();
        }
    }

    /**
     * 获取当前月份的所有天的列表
     * @param localDate  指定月份任何一天的时间
     * @param format     返回日期格式
     * @return
     */
    public static List<String> getDayBetween(LocalDate localDate, String format){
        LocalDate firstDay, lastDay;
        firstDay = localDate.withDayOfMonth(1);
        System.out.println(firstDay);
        lastDay = firstDay.plusMonths(1).plusDays(-1);
        System.out.println(lastDay);
        // 日期格式类
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(format);
        ArrayList<String> dayBetween= new ArrayList<>();
        do {
            dayBetween.add(firstDay.format(dateTimeFormatter));
            firstDay = firstDay.plusDays(1);
        }while (firstDay.compareTo(lastDay) <= 0);
        return dayBetween;
    }
    /**
     * LocalDate转Date
     * @param localDate
     * @return
     */
    public static Date getLocalDateByDate(LocalDate localDate) {
        if(null == localDate) {
            return null;
        }
        ZonedDateTime zonedDateTime = localDate.atStartOfDay(ZoneId.systemDefault());
        return Date.from(zonedDateTime.toInstant());
    }

    @Test
    public void testGetLocalDateByDate(){
        LocalDate now = LocalDate.now();
        Date localDateByDate = getLocalDateByDate(now);
        System.out.println(parseDateToStr(localDateByDate,DATE_FORMAT_19));
    }

    /**
     * 字符串转LocalDate "yyyy-MM-dd HH:mm:ss"
     *
     * @param dateString
     * @return
     * @throws ParseException
     */
    public static LocalDate parseLocalDates(String dateString){
        if (StringUtils.isNotEmpty(dateString)){
            return LocalDate.parse(dateString, DateTimeFormatter.ofPattern(DATE_FORMAT_10));
        }
        return null;
    }

    /**
     * 26-10月-20 26-9月-20
     * 格式化日期
     */
    public static String formatDateByString(String inputDateString){
        if (!inputDateString.contains("月") || StringUtils.isEmpty(inputDateString)) {
            throw new BaseException("传入的日期格式不合法！以dd-mm月-yy格式为准");
        }
        inputDateString = inputDateString.replace(" ", "");
        String yearString = "20"+inputDateString.substring(inputDateString.length() - 2, inputDateString.length());
        int monthIndex = inputDateString.lastIndexOf("月");
        String dateAndMonth = inputDateString.substring(0, monthIndex);
        int intervalIndex = dateAndMonth.lastIndexOf("-");
        String dateString = dateAndMonth.substring(0, intervalIndex);
        String monthString = dateAndMonth.substring(intervalIndex+1, dateAndMonth.length());
        dateString = dateString.length()==1?"0"+dateString : dateString;
        monthString = monthString.length()==1?"0"+monthString : monthString;
        String returnDateString = yearString+"-"+monthString+"-"+dateString;
        return  returnDateString;
    }
    
	
	public static Date getMonthStart(int year,int month) {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		cal.set(Calendar.YEAR, year);
		cal.set(Calendar.MONTH, month-1);
		cal.set(Calendar.DATE, 1);
		return new Date(Timestamp.valueOf(getCalendarToTimestapStr(setStarthhmmss(cal))).getTime());
	}
	
	public static Date getMonthEnd(int year,int month) {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		cal.set(Calendar.YEAR, year);
		cal.set(Calendar.MONTH, month-1);
		cal.set(Calendar.DATE, 1);
		int total = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
		cal.add(Calendar.DAY_OF_MONTH, total - 1);
		return new Date(Timestamp.valueOf(getCalendarToTimestapStr(setEndhhmmss(cal))).getTime());
	}
	
	// 设置 开始时间的 时分秒
	private static Calendar setStarthhmmss(Calendar cal){
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		return cal;
	}
	
	// 设置 开始时间的 时分秒
	private static Calendar setEndhhmmss(Calendar cal){
		cal.set(Calendar.HOUR_OF_DAY, 23);
		cal.set(Calendar.MINUTE, 59);
		cal.set(Calendar.SECOND, 59);
		return cal;
	}
	
	// 获取 日历 的 时间戳字符串 （月 需要 +1）
	private static String getCalendarToTimestapStr(Calendar cal){
		String yyyymmdd = cal.get(Calendar.YEAR) + "-" + (cal.get(Calendar.MONTH) + 1) + "-" + cal.get(Calendar.DAY_OF_MONTH);
		String hhmmss = cal.get(Calendar.HOUR_OF_DAY) + ":" + cal.get(Calendar.MINUTE) + ":" + cal.get(Calendar.SECOND);
		return yyyymmdd + " "+ hhmmss;
	}
	
	/**
	 * 获取某年第几季度的开始日期
	 * @param year 某年
	 * @param quarterIndex 第几季度
	 * @return 时间戳格式的日期
	 */
	public static Date getStartDateOfQuarter(int year, int quarterIndex){
		Calendar cal = getCalendarForQuarter(year, quarterIndex);
		return new Date(Timestamp.valueOf(getCalendarToTimestapStr(setStarthhmmss(cal))).getTime());
	}

	/**
	 * 获取某年第几季度的结束日期
	 * @param year 某年
	 * @param quarterIndex 第几季度
	 * @return 时间戳格式的日期
	 */
	public static Date getEndDateOfQuarter(int year, int quarterIndex){
		Calendar cal = getCalendarForQuarter(year, quarterIndex);
		cal.add(Calendar.MONTH, 2);
		int total = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
		cal.add(Calendar.DAY_OF_MONTH, total - 1);
		return new Date(Timestamp.valueOf(getCalendarToTimestapStr(setEndhhmmss(cal))).getTime());
	}
	
	private static Calendar getCalendarForQuarter(int year, int quarterIndex){
		int startMonth = (quarterIndex - 1)* 3 + 1;
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		cal.set(Calendar.YEAR, year);
		cal.set(Calendar.MONTH, startMonth - 1);
		cal.set(Calendar.DATE, 1);
		return cal;
	}

    /**
     * LocalDate转Date
     * @param localDate
     * @return
     */
    public static Date localDateToDate(LocalDate localDate) {
        if(null == localDate) {
            return null;
        }
        ZonedDateTime zonedDateTime = localDate.atStartOfDay(ZoneId.systemDefault());
        return Date.from(zonedDateTime.toInstant());
    }

}