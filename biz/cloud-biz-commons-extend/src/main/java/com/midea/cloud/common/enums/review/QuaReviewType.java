package com.midea.cloud.common.enums.review;

/**
 * <pre>
 *     供应商准入类型(资质审查类型)  字典码QUA_REVIEW_TYPE
 * </pre>
 *
 * @author yourname@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/9/14 16:44
 *  修改内容:
 * </pre>
 */
public enum QuaReviewType {

    NEW_VENDOR,//全新供应商认证
    NEW_CATEGORY,//现有供应商新增品类
    RED_VERIFY,//红牌供应商重新认证
    ONETIME_VENDOR;//一次供应商申请
}
