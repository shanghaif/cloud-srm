package com.midea.cloud.common.constants;

/**
 * <pre>
 *  功能名称
 * </pre>
 *
 * @author xiexh12@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/11/1 11:23
 *  修改内容:
 * </pre>
 */
public class RedisKey {
    public static final String VERIFY_CODE = "verifyCode:";
    public static final String USER_TRACE_USERNAME = "USER:TRACE:USERNAME:";
    public static final String TREE_NEW_LOCK = "TREE_NEW_LOCK";
    public static final String TREE_BY_PARENT = "TREE_BY_PARENT";
    public static final String GIDAILY_RATEWS_SERVICE_LOCK = "GIDAILY_RATEWS_SERVICE_LOCK";

    // 登录失败次数
    public static final String REDIS_LOGIN_ERROR_FREQUENCY = "REDIS_LOGIN_ERROR_FREQUENCY";
    // 最后一次登录错误时间
    public static final String REDIS_LOGIN_LAST_ERROR_TIME = "REDIS_LOGIN_LAST_ERROR_TIME";
    // 忘记密码验证码
    public static final String FORGET_PASSWORD_VERIFY_CODE = "FORGET_PASSWORD_VERIFY_CODE";
    // 送货通知单新增
    public static final String DELIVERY_NOTICE_ADD = "DELIVERY_NOTICE_ADD";
    // 送货通知单更新
    public static final String DELIVERY_NOTICE_UPDATE = "DELIVERY_NOTICE_UPDATE";
    // 送货通知单删除
    public static final String DELIVERY_NOTICE_DELETE = "DELIVERY_NOTICE_DELETE";
    // 无参数缓存示例
    public static final String NO_PARAM_CACHE_API = "NO_PARAM_CACHE_API";
    // 有参数缓存示例
    public static final String PARAM_CACHE_API = "PARAM_CACHE_API";
    // 字典KEY
    public static final String DICT_CACHE_KEY = "DICT_CACHE_KEY";

    public static final String WORKFLOW_TEMPLATE_HEADER = "WORKFLOW_TEMPLATE_HEADER";

    public static final String USER_CACHE = "USER_CACHE";
    // 邮件链接
    public static final String EMAIL_URL_CACHE = "EMAIL_URL_CACHE";
}
