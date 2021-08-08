package com.midea.cloud.srm.perf.common;

/**
 * <pre>
 * 绩效等级常量
 * </pre>
 *
 * @author wuwl18@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/6/3 10:18
 *  修改内容:
 * </pre>
 */
public class PerfLevelConst {
    /**等级名称*/
    public static final String LEVEL_NAME_NOT_NULL ="等级名称不能为空";
    /**等级说明*/
    public static final String LEVEL_DESCRIPTION_NOT_NULL = "等级说明不能为空";
    /**综合得分开始*/
    public static final String SCORE_START_NOT_NULL = "综合得分开始不能为空";
    /**综合得分结束*/
    public static final String SCORE_END_NOT_NULL = "综合得分结束不能为空";
    /**状态*/
    public static final String STATUS_NOT_NULL = "状态不能为空";
    /**采购组织ID*/
    public static final String ORGANIZATION_ID_NOT_NULL = "采购组织ID不能为空";
    /**采购组织CODE*/
    public static final String ORGANIZATION_NAME_NOT_NULL = "采购组织名称不能为空";
    /**当前用户还没有配置组织(请先到品类分工菜单配置)*/
    public static final String CURRENT_USER_NOT_ORG = "当前用户还没有配置组织(请先到品类分工菜单配置)";
    /**导入组织不属于当前用户所属组织(请修改或到品类分工菜单配置)*/
    public static final String ORG_NOT_BELONG_USER = "导入组织不属于当前用户所属组织(请修改或到品类分工菜单配置)";
    /**状态值只能为Y或N*/
    public static final String STATUS_IS_Y_N = "状态值只能为Y或N";
    /**"有效的等级名、采购组织已重复,请重新填写"*/
    public static final String LEVEL_NAME_AND_ORG_ID_SAME ="有效的等级名、采购组织已重复,请重新填写";
}
