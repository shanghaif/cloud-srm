package com.midea.cloud.signature.electronicsignature.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @author xcm
 * @since 2018-4-24 14:29
 */
public class DateUtil {
    public final static Logger logger = LoggerFactory.getLogger(DateUtil.class);
    public static final String DATE_FORMAT_DEFAULT = "yyyy-MM-dd";
    public static final String DATE_FORMAT_US = "MM/dd/yyyy";
    public static final String DATE_FORMAT_CN = "yyyy年MM月dd日";
    public static final String DATE_FORMAT_SIMPLE = "yyyyMMdd";

    public static final String DATE_TIME_FORMAT_DEFAULT = "yyyy-MM-dd HH:mm:ss";
    public static final String DATE_TIME_FORMAT_US = "MM/dd/yyyy HH:mm:ss";
    public static final String DATE_TIME_FORMAT_CN = "yyyy年MM月dd日 HH时mm分ss秒";
    public static final String DATE_TIME_FORMAT_SIMPLE = "yyyyMMddHHmmss";
    public static final String DATE_TIME_FORMAT_ALL = "yyyyMMddHHmmssSSS";

    public static final int INT_FORMAT = 1;
    public static final int CHINAESE_STIRNG_FORMAT = 2;
    private static final String[] allDayInt = { "7", "1", "2", "3", "4", "5",
            "6" };
    private static final String[] allDayString = { "星期日", "星期一", "星期二", "星期三",
            "星期四", "星期五", "星期六" };

    /**
     * 将字符串转换成日期
     */
    public static Date strToDate(String str, String format) throws Exception {
        if (str == null) {
            return null;
        }
        if ("".equals(str)) {
            return null;
        }
        return new SimpleDateFormat(format).parse(str);
    }

    /**
     * 将日期转换成字符串
     */
    public static String dateToStr(Date date, String format) throws Exception {
        if (date == null) {
            return "";
        }
        return new SimpleDateFormat(format).format(date);
    }

    /**
     * 返回某一天是星期几
     *
     * @param date
     *            “某一天”的日期字符串 如2005－12－20
     * @param format
     *            返回的格式 ，整数型字符串或者是“星期几”方式的字符型
     * @return “1234567”之一, "1" 表示星期一，如此类推 或者是“星期一，星期二......"之一
     */
    public static String getDayOfWeek(String date, int format) {
        DateFormat df = DateFormat.getDateInstance();
        try {
            df.parse(date);
            Calendar c = df.getCalendar();
            int day = c.get(Calendar.DAY_OF_WEEK) - Calendar.SUNDAY;
            if (format == INT_FORMAT) {
                return allDayInt[day];
            } else {
                return allDayString[day];
            }
        } catch (ParseException e) {
            return "0";
        }
    }

    /**
     * 返回某一天是星期几
     *
     * @param date
     *            Date“某一天”的日期字符串 如2005－12－20
     * @param format
     *            返回的格式 ，整数型字符串或者是“星期几”方式的字符型
     * @return “1234567”之一, "1" 表示星期一，如此类推 或者是“星期一，星期二......"之一
     */
    public static String getDayofWeek(Date date, int format) {
        return getDayOfWeek(date.toLocaleString(), format);
    }

    /**
     * 返回当前星期的所有日期,从星期天开始到星期六
     *
     * @return List 列表每一个元素都是类似"2005-1-1"的字符串
     */
    public static List getCurrentWeek() {
        return getWeekList(new Date());
    }

    /**
     * 返回某一天的所属星期的所有天数
     *
     * @param date
     *            String 格式：2005-12-20 03:53:29
     * @return List 列表每一个元素都是类似"2005-1-1"的字符串
     */
    public static List getWeekList(String date) {
        try {
            return getWeekList(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                    .parse(date));
        } catch (ParseException ex) {
            return null;
        }
    }

    /**
     * 返回当前星期的所有日期
     *
     * @return List 列表每一个元素都是类似"2005-1-1"的字符串
     */
    public static List getWeekList(Date date) {
        List result = new ArrayList();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        // 星期几
        int currentDay = Integer.parseInt(getDayofWeek(date, INT_FORMAT));
        // 在当前日期基础上减去适当的天数，得到星期一的日期
        calendar.add(Calendar.DATE, (-currentDay + 1));
        // 加入一个星期的日期值到列表
        result.add(sdf.format(calendar.getTime()));
        for (int i = 1; i < 7; i++) {
            calendar.add(Calendar.DATE, 1);
            result.add(sdf.format(calendar.getTime()));
        }
        return result;
    }

    /**
     * 返回本周的星期一的日期 得到本周周一
     */
    public static Date getFirstDateOfThisWeek() {
        Calendar calendar = Calendar.getInstance();
        Date now = new Date();
        // 设置日期为今天
        calendar.setTime(now);
        // 今天是星期几
        int currentDay = Integer.parseInt(getDayofWeek(now, INT_FORMAT));
        // 在当前日期基础上减去适当的天数，得到星期一的日期
        calendar.add(Calendar.DATE, (-currentDay + 1));
        return calendar.getTime();
    }

    /* 返回某一天所属的周的最后一天 */
    public static Date getLastDateOfWeekByDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        // 那天是星期几
        int currentDay = Integer.parseInt(getDayofWeek(date, INT_FORMAT));
        // 在该天日期基础上加上适当的天数，得到星期天的日期
        calendar.add(Calendar.DATE, 7 - currentDay);
        return calendar.getTime();
    }

    // 获取本月第一天日期信息
    public static Date getFirstDateOfCurrentMonth() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-01 00:00:00");
        try {
            return sdf.parse(sdf.format(new Date()));
        } catch (ParseException ex) {
            return null;
        }
    }

    /**
     * 返回本月第一天的日期信息
     *
     * @return String
     */
    public static String getFirstDateStringOfCurrentMonth() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-01 00:00:00");
        return sdf.format(new Date());
    }

    // 得到本周的下周一的日期
    public static String getFirstDateStringOfNextWeek() {
        return getFirstDateStringOfNextWeek(new Date());
    }

    // 得到本周的上周一的日期
    public static String getFirstDateStringOfPrevWeek() {
        return getFirstDateStringOfPrevWeek(new Date());
    }

    // 得到指定日期所在周的下周一的日期
    public static String getFirstDateStringOfNextWeek(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        // 那天是星期几
        int currentDay = Integer.parseInt(getDayofWeek(date, INT_FORMAT));
        calendar.add(Calendar.DATE, 7 - currentDay + 1);

        return new SimpleDateFormat("yyyy-MM-dd 00:00:00").format(calendar
                .getTime());
    }

    // 得到指定日期所在周的上周一的日期
    public static String getFirstDateStringOfPrevWeek(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        // 那天是星期几
        int currentDay = Integer.parseInt(getDayofWeek(date, INT_FORMAT));
        calendar.add(Calendar.DATE, (-currentDay + 1) - 7);

        return new SimpleDateFormat("yyyy-MM-dd 00:00:00").format(calendar
                .getTime());
    }

    // 计算给定日期所在周的上一周是否在本月内
    public static boolean prevWeekInThisMonthOfTheDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        // 那天是星期几
        int currentDay = Integer.parseInt(getDayofWeek(date, INT_FORMAT));
        // 上一周的周日
        calendar.add(Calendar.DATE, (-currentDay + 1) - 1);
        return new SimpleDateFormat("yyyy-MM").format(new Date()).equals(
                new SimpleDateFormat("yyyy-MM").format(calendar.getTime()));
    }

    // 计算给定日期所在周的下一周是否在本月内
    public static boolean nextWeekInThisMonthOfTheDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        // 那天是星期几
        int currentDay = Integer.parseInt(getDayofWeek(date, INT_FORMAT));
        // 下一周的周日
        calendar.add(Calendar.DATE, (7 - currentDay) + 1);
        return new SimpleDateFormat("yyyy-MM").format(new Date()).equals(
                new SimpleDateFormat("yyyy-MM").format(calendar.getTime()));
    }

    // 计算给定日期是否在本月内
    public static boolean theDateInThisMonth(Date date) {
        return new SimpleDateFormat("yyyy-MM").format(new Date()).equals(
                new SimpleDateFormat("yyyy-MM").format(date));
    }

    // 在给定日期上加若干天
    public static Date addDayToDate(Date date, int daysToAdd) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, daysToAdd);
        return calendar.getTime();
    }

    public static Date addDateBy(Date date, int field, int add) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(field, add);
        return calendar.getTime();
    }

    // 本月最后一天
    public static Date getTheLastDayOfThisMonth(Date curDate) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(curDate);

        String year = new SimpleDateFormat("yyyy").format(curDate);
        String month = new SimpleDateFormat("MM").format(curDate);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date last;
        try {
            if (month.equals("12")) {
                last = sdf.parse(year + "-12-31");
            } else {
                last = sdf.parse(year + "-" + (Integer.parseInt(month) + 1)
                        + "-1");
                calendar.setTime(last);
                calendar.add(Calendar.DATE, -1); // 减一天，得到上一个月最后一天
                last = calendar.getTime();
            }
        } catch (ParseException ex) {
            return null;
        }
        return last;
    }

    /**
     * 根据传入的字符串得到日期,带时间
     *
     * @param date
     *            String
     * @return Date
     */
    public static Date getDateWithOur(String date) {
        try {
            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(date);
        } catch (ParseException ex) {
            return null;
        }
    }

    /**
     * 根据传入的字符串得到日期
     *
     * @param date
     *            String
     * @return Date
     */
    public static Date getDate(String date) {
        try {
            return new SimpleDateFormat("yyyy-MM-dd").parse(date);
        } catch (ParseException ex) {
            return null;
        }
    }

    /**
     * 根据传入的字符串得到日期字符串
     *
     * @param date
     *            String
     * @return Date
     */
    public static String getDateString(String date) {
        try {
            Date da = new SimpleDateFormat("yyyy-MM-dd").parse(date);
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            return df.format(da);
        } catch (ParseException ex) {
            return null;
        }
    }

    /**
     * 格式化日期为简单字符串
     *
     * @param
     *
     * @return String
     */
    public static String formatSimplyDate(Date date) {
        if (date == null) {
            return "";
        }
        return new SimpleDateFormat("MM-dd HH:mm:ss").format(date);
    }

    /**
     * 格式化日期为字符串
     *
     * @param
     *
     * @return String
     */
    public static String formatDate(Date date) {
        if (date == null) {
            return "";
        }
        return new SimpleDateFormat("yyyy-MM-dd").format(date);
    }

    /**
     * 返回当前日期
     *
     * @return 日期字符串 格式为 yyyy-mm-dd
     */
    public synchronized static String getCurDateString() {
        return toDateString(getCurTime());
    }

    /**
     * 返回当前日期时间
     *
     * @return 日期字符串 格式为 yyyy-mm-dd 24hh:mi:ss
     */
    public synchronized static String getCurDateTimeString() {
        return toTimeString(getCurTime());
    }

    /**
     * 返回当前时间
     *
     * @return 时间字符串 格式为 yyyy-mm-dd 24hh:mi:ss
     */
    public synchronized static Timestamp getCurTime() {
        return getTimestamp();
    }

    /**
     * 根据字符串获取timestamp对象 可能抛出异常
     *
     * @param Date
     *            日期字符串 自由日期
     * @return 时间戳对象
     */
    public synchronized static Timestamp toTimeStamp(String Date) {
        Timestamp ts = getCurTime();
        if (Date != null && Date.length() >= 10) {
            ts = Timestamp.valueOf(Date.substring(0, 10)
                    + " 00:00:00.000000000");
        }
        return ts;
    }

    /**
     * 根据字符串获取timestamp对象 可能抛出异常
     *
     * @param Date
     *            时间字符串 包含时间
     * @return 时间戳对象
     */
    public synchronized static Timestamp toDateTimeStamp(String Date) {
        // ts = Timestamp.valueOf(Date.substring(0, 19) + ".000000000");
        Timestamp ts = getCurTime();
        if (Date != null) {
            if (Date.length() <= 10) {
                ts = Timestamp.valueOf(Date + " 00:00:00.000000000");
            } else if (Date.length() > 10 && Date.length() <= 19) {
                ts = Timestamp.valueOf(Date + ".000000000");
            } else {
                ts = Timestamp.valueOf(Date);
            }
        }
        return ts;
    }

    public synchronized static String getCurYear() {
        return toYearString(getTimestamp());
    }

    /**
     * 获取年字符串
     *
     * @param ts
     *            时间
     * @return 年份 如2005
     */
    public synchronized static String toYearString(Timestamp ts) {
        String strReturn = "";
        if (ts != null) {
            strReturn = ts.toString().substring(0, 4);
        }
        return strReturn;
    }

    /**
     * 获取日期字符串
     *
     * @param ts
     *            时间
     * @return 日期字符串 格式为yyyy-mm-dd
     */
    public synchronized static String toDateString(Timestamp ts) {
        String sDate = "";
        if (ts != null) {
            Date now = new Date(ts.getTime());
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            sDate = df.format(now);
        }
        return sDate;
    }

    /**
     * 获取日期字符串
     *
     * @param ts
     *            时间
     * @return 日期字符串 格式为yyyy-mm-dd
     */
    public static String toCHNDateString(Timestamp ts) {
        String sDate = "";
        if (ts != null) {
            Date now = new Date(ts.getTime());
            DateFormat df = new SimpleDateFormat("yyyy年MM月dd日");
            sDate = df.format(now);
        }
        return sDate;
    }

    /**
     * 获取时间字符串
     *
     * @param ts
     *            时间
     * @return 时间字符串 格式为yyyy-mm-dd 24hh:mi:ss
     */
    public synchronized static String toTimeString(Timestamp ts) {
        String strReturn = "";
        if (ts != null) {
            strReturn = ts.toString().substring(0, 19);
        }
        return strReturn;
    }

    /**
     * 获取中文日期字符串
     *
     * @param ts
     *            时间
     * @return 中文日期字符串
     */
    public String toCHN(Timestamp ts) {
        String strCHNDate = "";
        if (ts != null) {
            String strDate = toDateString(ts);

            String strYear = parseCHNYear(strDate.substring(0, 4));
            String strMonth = parseCHNMonth(strDate.substring(5, 7));
            String strDay = parseCHNDay(strDate.substring(8, 10));
            strCHNDate = strYear + "年" + strMonth + "月" + strDay + "日";
        }
        return strCHNDate;
    }

    /**
     * 把数字的年份转为中文
     *
     * @param strNumber
     *            String 如：2004
     * @return String 如二��五
     */
    private String parseCHNYear(String strNumber) {
        String strCHN = "";
        String CHN[] = new String[] { "�", "一", "二", "三", "四", "五", "六", "七",
                "八", "九", "十" };
        for (int i = 0; i < strNumber.length(); i++) {
            strCHN += CHN[strNumber.charAt(i) - 48];
        }
        return strCHN;
    }

    /**
     * 把数字的月份转为中文
     *
     * @param strMonth
     *            String 如：1
     * @return String 如：一
     */
    private String parseCHNMonth(String strMonth) {
        String strCHN = "";
        strMonth = strMonth.substring(0, 2);
        String CHN[] = new String[] { "", "一", "二", "三", "四", "五", "六", "七",
                "八", "九", "十" };

        if (strMonth.charAt(0) == 49) {
            strCHN += "十";
        }
        strCHN += CHN[strMonth.charAt(1) - 48];
        return strCHN;
    }

    /**
     * 把数字的日期转为中文
     *
     * @param strDay
     *            String 如：15
     * @return String 如：十五
     */
    private String parseCHNDay(String strDay) {
        String strCHN = "";
        strDay = strDay.substring(0, 2);
        String CHN[] = new String[] { "", "一", "二", "三", "四", "五", "六", "七",
                "八", "九", "十" };
        if (strDay.charAt(0) > 48) {
            if (strDay.charAt(0) != 49) {
                strCHN += CHN[strDay.charAt(0) - 48] + "十";
            } else {
                strCHN += "十";
            }
        }
        strCHN += CHN[strDay.charAt(1) - 48];
        return strCHN;
    }

    /**
     * 得到系统时间
     *
     * @return String 返回的时间格式：年-月-日-小时-分-秒
     */
    public static String getSystemTimeStamp() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        String year = String.valueOf(calendar.get(Calendar.YEAR));
        String month = String.valueOf(calendar.get(Calendar.MONTH) + 1);
        String day = String.valueOf(calendar.get(Calendar.DATE));
        String hour = String.valueOf(calendar.get(Calendar.HOUR));
        String minute = String.valueOf(calendar.get(Calendar.MINUTE));
        String second = String.valueOf(calendar.get(Calendar.SECOND));
        String result = year + "-" + month + "-" + day + " " + hour + ":"
                + minute + ":" + second;
        return result;
    }

    /**
     * 返回系统时间
     *
     * @return String 返回的时间格式：时-分-秒
     */
    public String getSystemTime() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        String hour = String.valueOf(calendar.get(Calendar.HOUR));
        String minute = String.valueOf(calendar.get(Calendar.MINUTE));
        String second = String.valueOf(calendar.get(Calendar.SECOND));
        String result = hour + ":" + minute + ":" + second;
        return result;
    }

    /**
     * 返回系统的年月日
     *
     * @return String 返回的时间格式：年-月-日
     */
    public String getSystemDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        String year = String.valueOf(calendar.get(Calendar.YEAR));
        String month = String.valueOf(calendar.get(Calendar.MONTH) + 1);
        String day = String.valueOf(calendar.get(Calendar.DATE));
        String result = year + "-" + month + "-" + day;
        return result;
    }

    /**
     * 返回系统时间戳
     *
     * @return Timestamp 系统时间戳
     */
    public static Timestamp getTimestamp() {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        return timestamp;
    }

    /**
     * 得到系统当前时间，返回类型为java.sql.Date ，为
     *
     * @return java.sql.Date
     */
    public static java.sql.Date getSystemCurrentTime() {
        return new java.sql.Date(System.currentTimeMillis());
    }

    /*
     * 返回当前年月，格式为yyyymm
     */
    public synchronized static String getCurYearMonth() {
        String str = getCurDateString().replaceAll("-", "");
        return str.substring(0, 6);
    }

    /*
     * 返回当前月，格式为mm
     */
    public synchronized static String getCurMonth() {
        String str = getCurDateString().replaceAll("-", "");
        return str.substring(4, 6);
    }

    // 获取2个日期间相隔的月份
    public static int getBetweenMonth(String startDate, String endDate) {

        if (startDate == null || startDate.equals("") || endDate == null
                || endDate.equals("")) {
            return 0;
        }

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date start = new Date();
        Date end = new Date();

        try {
            start = format.parse(startDate);
            end = format.parse(endDate);
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
        }
        if (start.after(end)) {
            Date t = start;
            start = end;
            end = t;
        }
        Calendar startCalendar = Calendar.getInstance();
        startCalendar.setTime(start);
        Calendar endCalendar = Calendar.getInstance();
        endCalendar.setTime(end);
        Calendar temp = Calendar.getInstance();
        temp.setTime(end);
        temp.add(Calendar.DATE, 1);

        int year = endCalendar.get(Calendar.YEAR)
                - startCalendar.get(Calendar.YEAR);
        int month = endCalendar.get(Calendar.MONTH)
                - startCalendar.get(Calendar.MONTH);

        if ((startCalendar.get(Calendar.DATE) == 1)
                && (temp.get(Calendar.DATE) == 1)) {
            return year * 12 + month + 1;
        } else if ((startCalendar.get(Calendar.DATE) != 1)
                && (temp.get(Calendar.DATE) == 1)) {
            return year * 12 + month;
        } else if ((startCalendar.get(Calendar.DATE) == 1)
                && (temp.get(Calendar.DATE) != 1)) {
            return year * 12 + month;
        } else {
            return (year * 12 + month - 1) < 0 ? 0 : (year * 12 + month);
        }

    }
    //获取2个日期间相隔的条数，采用取整获取结果
    public static long getBetweenDay(Date startDate,Date endDate){

        if(startDate == null  || endDate==null ){
            return 0;
        }
        long diffTime = endDate.getTime()-startDate.getTime();

        return  (diffTime/1000)/3600/24;

    }
    //获取2个日期间相隔的小时，采用取整获取结果
    public static long getBetweenHour(Date startDate,Date endDate){

        if(startDate == null  || endDate==null ){
            return 0;
        }
        long diffTime = endDate.getTime()-startDate.getTime();

        return (diffTime/1000)/3600;

    }
    //获取2个日期间相隔的分钟，采用取整获取结果，例如4分多返回结果为4
    public static long getBetweenMinute(Date startDate,Date endDate){

        if(startDate == null  || endDate==null ){
            return 0;
        }
        long diffTime = endDate.getTime()-startDate.getTime();

        return (diffTime/1000)/60;

    }
    //获取2个日期间相隔的秒数，采用取整获取结果，例如30秒多返回结果为30
    public static long getBetweenSecond(Date startDate,Date endDate){

        if(startDate == null  || endDate==null ){
            return 0;
        }
        long diffTime = endDate.getTime()-startDate.getTime();
        return (diffTime/1000);
    }

    /**
     * 将字符串转换成时间擢
     */
    public static String getTimestamp(String time) {
        String re_time = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date d;
        try {
            d = sdf.parse(time);
            long l = d.getTime();
            String str = String.valueOf(l);
            re_time = str.substring(0, 10);
            return re_time;
        }catch (Exception e) {
            logger.error(e.getMessage(),e);
            return re_time;
        }
    }

    /**
     *字符串的日期格式的计算
     */
    public static int daysBetween(String smdate,String bdate) throws ParseException{
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        cal.setTime(sdf.parse(smdate));
        long time1 = cal.getTimeInMillis();
        cal.setTime(sdf.parse(bdate));
        long time2 = cal.getTimeInMillis();
        long between_days=(time2-time1)/(1000*3600*24);

        return Integer.parseInt(String.valueOf(between_days));
    }


    /**
     * 计算2个日期之间相差的  相差多少年月日
     * 比如：2011-02-02 到  2017-03-02 相差 6年，1个月，0天
     * @param fromDate
     * @param toDate
     * @return
     */
    public static int dayComparePrecise(Date fromDate,Date toDate){
        Calendar  from  =  Calendar.getInstance();
        from.setTime(fromDate);
        Calendar  to  =  Calendar.getInstance();
        to.setTime(toDate);
        int fromYear = from.get(Calendar.YEAR);
        int fromMonth = from.get(Calendar.MONTH);
        int fromDay = from.get(Calendar.DAY_OF_MONTH);
        int toYear = to.get(Calendar.YEAR);
        int toMonth = to.get(Calendar.MONTH);
        int toDay = to.get(Calendar.DAY_OF_MONTH);
        int year = toYear  -  fromYear;
        int month = toMonth  - fromMonth;
        int day = toDay  - fromDay;
        return year;
    }


    public static boolean isSameDay(Date date1, Date date2) {
        if(date1 != null && date2 != null) {
            Calendar cal1 = Calendar.getInstance();
            cal1.setTime(date1);
            Calendar cal2 = Calendar.getInstance();
            cal2.setTime(date2);
            return isSameDay(cal1, cal2);
        } else {
            throw new IllegalArgumentException("The date must not be null");
        }
    }

    public static boolean isSameDay(Calendar cal1, Calendar cal2) {
        if(cal1 != null && cal2 != null) {
            return cal1.get(0) == cal2.get(0) && cal1.get(1) == cal2.get(1) && cal1.get(6) == cal2.get(6);
        } else {
            throw new IllegalArgumentException("The date must not be null");
        }
    }

    /**
     * 通过时间秒毫秒数判断两个时间的间隔
     * @param date1
     * @param date2
     * @return
     */
    public static int differentDaysByMillisecond(Date date1,Date date2)
    {
        int days = (int) ((date2.getTime() - date1.getTime()) / (1000*3600*24));
        return days;
    }

    public static void main(String[] args) throws Exception {
        Date startDate = DateUtil.strToDate("2016-06-01", "yyyy-MM-dd");
        Date endDate = DateUtil.strToDate("2017-06-02", "yyyy-MM-dd");
        long betweenDay  = DateUtil.getBetweenDay(startDate, endDate);
        System.out.println(betweenDay);
        long betweenHour  = DateUtil.getBetweenHour(startDate, endDate);
        System.out.println(betweenHour);
        int a= DateUtil.dayComparePrecise(startDate,endDate);
        System.out.println(a);
    }

}
