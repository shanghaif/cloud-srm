package com.midea.cloud.common.utils;

import org.apache.commons.lang3.StringUtils;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public interface DateChangeUtil {
    /**
     * 转换时间和本地时间
     * @param localDate
     * @return
     */
    public static Date asDate(LocalDate localDate) {
        return Date.from(localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
    }

    public static Date asDate(LocalDateTime localDateTime) {
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    public static LocalDate asLocalDate(Date date) {
        return Instant.ofEpochMilli(date.getTime()).atZone(ZoneId.systemDefault()).toLocalDate();
    }

    public static LocalDateTime asLocalDateTime(Date date) {
        return Instant.ofEpochMilli(date.getTime()).atZone(ZoneId.systemDefault()).toLocalDateTime();
    }

    /**
     * 获取当天23：59：59
     */
    public static Date getDateMaxTime(Date date) {
        LocalDate localDate = asLocalDate(date);
        /*获取当天0点*/
        return asDate(LocalDateTime.of(localDate, LocalTime.MAX));
    }

    /**
     * 字符串转Date
     */
    public static Date formatDate(String strDate, String pattern) {
        if (StringUtils.isBlank(pattern)) {
            pattern = "yyyy-MM-dd HH:mm:ss";
        }
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(pattern);
        return asDate(LocalDateTime.parse(strDate,dateTimeFormatter));

    }
}
