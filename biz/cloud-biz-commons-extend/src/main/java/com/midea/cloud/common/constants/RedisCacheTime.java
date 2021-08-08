package com.midea.cloud.common.constants;

/**
 * <pre>
 * redis常用的缓存时间配置
 * </pre>
 *
 * @author wangpr@meiCloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/6/8
 *  修改内容:
 * </pre>
 */
public class RedisCacheTime {
    /**
     * 缓存时效 5秒钟
     */
    public static int CACHE_EXP_FIVE_SECONDS = 5;

    /**
     * 缓存时效 1分钟
     */
    public static int CACHE_EXP_MINUTE = 60;

    /**
     * 缓存时效 2分钟
     */
    public static int CACHE_EXP_TWO_MINUTE = 120;

    /**
     * 缓存时效 5分钟
     */
    public static int CACHE_EXP_FIVE_MINUTES = 60 * 5;

    /**
     * 缓存时效 10分钟
     */
    public static int CACHE_EXP_TEN_MINUTES = 60 * 10;

    /**
     * 缓存时效 15分钟
     */
    public static int CACHE_EXP_QUARTER_MINUTES = 60 * 15;

    /**
     * 缓存时效 60分钟
     */
    public static int CACHE_EXP_HOUR = 60 * 60;

    /**
     * 缓存时效 12小时
     */
    public static int CACHE_EXP_HALF_DAY = 12 * 60 * 60;

    /**
     * 缓存时效 1天
     */
    public static int CACHE_EXP_DAY = 3600 * 24;

    /**
     * 缓存时效 1周
     */
    public static int CACHE_EXP_WEEK = 3600 * 24 * 7;

    /**
     * 缓存时效 1月
     */
    public static int CACHE_EXP_MONTH = 3600 * 24 * 30 * 7;

    /**
     * 缓存时效 永久
     */
    public static int CACHE_EXP_FOREVER = 0;
}
