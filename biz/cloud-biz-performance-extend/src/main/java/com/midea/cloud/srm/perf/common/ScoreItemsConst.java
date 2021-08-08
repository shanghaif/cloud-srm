package com.midea.cloud.srm.perf.common;

/**
 * <pre>
 *  绩效评分项目常量 公共类
 * </pre>
 *
 * @author wuwl18@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/6/10 9:28
 *  修改内容:
 * </pre>
 */
public class ScoreItemsConst {

    /** 通知评分人操作时项目状态只能为拟定**/
    public static final String SCORE_ITEMS_NOTIFY_SCORERS = "项目状态只有拟定时才能通知评分人";
    /** 通知评分人操作时小于评分开始时间**/
    public static final String SCORE_ITEMS_CALCULATE_START_TIME = "当前评分未开始,暂不能进行评分计算";
    /** 通知评分人操作时超过评分截止时间**/
    public static final String SCORE_ITEMS_CALCULATE_END_TIME = "已经超过评分截止时间,请修改评分截止时间";
    /** 废弃操作**/
    public static final String SCORE_ITEMS_ABANDON = "项目状态已是已废弃,不需要重新操作";
    /** 计算操作**/
    public static final String SCORE_ITEMS_CALCULATE = "项目状态只能为通知评分人";
    /** 提交流程操作**/
    public static final String SCORE_ITEMS_SUBMIT_PROCESS = "项目状态只能为已计算得分";
    /** 发布操作**/
    public static final String SCORE_ITEMS_PUBLISH = "项目状态只能为结果未发布";
    /**不是废弃/计算/流程提交和发布操作,不能操作*/
    public static final String SCORE_ITEMS_CAN_NOT_OPERATION="不是废弃/计算/流程提交和发布操作,不能操作";
    /** 绩效评分项目信息不能为空*/
    public static final String PER_SCORE_ITEMS_NOT_NULL="绩效评分项目信息不能为空";
    /** 绩效评分供应商信息不能为空*/
    public static final String PER_SCORE_ITEMS_SUP_NOT_NULL="绩效评分供应商信息不能为空";
    /** 绩效模型详细信息不能为空*/
    public static final String PER_TEMPLATE_NOT_NULL="绩效模型详细信息不能为空";
    /** 绩效模型采购分类信息不能为空*/
    public static final String PER_TEMPLATE_CATEGORY_NOT_NULL="绩效模型采购分类信息不能为空";
    /** 绩效模型指标维度信息不能为空*/
    public static final String PER_TEMPLATE_DIM_WEIGHT_NOT_NULL="绩效模型指标维度信息不能为空";
    /** 绩效模型指标行信息不能为空*/
    public static final String PER_TEMPLATE_LING_NOT_NULL="绩效模型指标行信息不能为空";
    /** 绩效评分项目评分人信息不能为空*/
    public static final String PER_SCORE_ITEMS_MAN_NOT_NULL="绩效评分项目评分人信息不能为空";
    /** 绩效评分项目供应商ID不能为空*/
    public static final String PER_SCORE_ITEMS_SUP_ID_NOT_NULL="绩效评分项目供应商ID不能为空";
    /** 绩效评分项目评分人-绩效维度ID不能为空*/
    public static final String PER_SCORE_ITEMS_MAN_DIM_WEIGHT_ID_NOT_NULL="绩效评分项目评分人-绩效维度ID不能为空";
    /** 项目状态为拟定才可以废弃*/
    public static final String IS_NOT_OBSOLETE="项目状态为拟定才可以废弃";

    /** 删除绩效评分项目时报错*/
    public static final String DELETE_SCORE_ITEMS_ERROR="删除绩效评分项目时报错";
    /** 批量删除绩效评分项目供应商时报错*/
    public static final String DELETE_SCORE_ITEMS_SUP_ERROR="批量删除绩效评分项目供应商时报错";
    /** 批量删除绩效评分项目评分人时报错*/
    public static final String DELETE_SCORE_ITEMS_MAN_ERROR="批量删除绩效评分项目评分人时报错";
    /** 批量删除绩效评分项目评分人-供应商指标时报错*/
    public static final String DELETE_SCORE_ITEMS_MAN_SUP_ERROR="批量删除绩效评分项目评分人-供应商指标时报错";
    /** 批量删除绩效评分项目评分人-绩效指标时报错*/
    public static final String DELETE_SCORE_ITEMS_MAN_INDICATOR_ERROR="批量删除绩效评分项目评分人-绩效指标时报错";
    /** 绩效评分项目没有配置评分人,不能保存、编辑/启动*/
    public static final String NOT_SET_SCORE_ITEMS_MAN ="绩效评分项目没有配置评分人,不能保存、编辑/启动";
    /** 绩效评分项目没有配置供应商权限,不能保存、编辑/启动*/
    public static final String NOT_SET_SCORE_ITEMS_MAN_SUP ="绩效评分项目没有配置供应商权限,不能保存、编辑/启动";
    /** 绩效评分项目没有配置指标权限,不能保存、编辑/启动*/
    public static final String NOT_SET_SCORE_ITEMS_MAN_INDICATORS ="绩效评分项目没有配置指标权限,不能保存、编辑/启动";
    /** 绩效评分项目指标权限配置重复,不能保存、编辑/启动*/
    public static final String REPEAT_SCORE_ITEMS_MAN_INDICATORS ="绩效评分项目指标权限配置重复,不能保存、编辑/启动";
    /**绩效模型被绩效评分项目引用,不能删除*/
    public static final String IS_NOT_DELETE_TEMPALTE_HEADER ="绩效模型被绩效评分项目引用,不能删除";
}
