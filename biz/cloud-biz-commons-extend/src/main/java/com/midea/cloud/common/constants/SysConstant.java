package com.midea.cloud.common.constants;

/**
 * <pre>
 *  系统级常量
 * </pre>
 *
 * @author huanghb14@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-2-14 9:34
 *  修改内容:
 * </pre>
 */
public class SysConstant {

    /**
     * 请求调用类型
     */
    public static final String INVOKE_TYPE = "invokeType";

    /**
     * 语言环境存储key
     */
    public static final String LOCALE_SESSION_ATTR_NAME = "CLOUD_LOCALE";

    /**
     * 默认中国大陆语言环境
     */
    public static final String DEFAULT_LOCALE = "zh_CN";

    /**
     * 树形组件根节点父ID
     */
    public static final Long TREE_PARENT_ID = -1L;

    /**
     * 系统管理员
     */
    public static class System {
        public static Long SYSTEM_ID = -1L;
        public static String SYSTEM_MANAGER = "系统管理员";
    }

    /**
     * 数据库表设计规范默认字段
     */
    public static class DefDbField {
        public static String CREATED_ID = "CREATED_ID";
        public static String CREATED_BY = "CREATED_BY";
        public static String CREATION_DATE = "CREATION_DATE";
        public static String CREATED_BY_IP = "CREATED_BY_IP";
        public static String LAST_UPDATED_ID = "LAST_UPDATED_ID";
        public static String LAST_UPDATED_BY = "LAST_UPDATED_BY";
        public static String LAST_UPDATE_DATE = "LAST_UPDATE_DATE";
        public static String LAST_UPDATED_BY_IP = "LAST_UPDATED_BY_IP";
        public static String VERSION = "VERSION";
        public static String TENANT_ID = "TENANT_ID";
    }

}
