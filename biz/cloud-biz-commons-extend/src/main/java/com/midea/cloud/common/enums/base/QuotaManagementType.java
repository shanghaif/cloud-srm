package com.midea.cloud.common.enums.base;

/**
 * <pre>
 * 配额管控类型枚举
 * </pre>
 *
 * @author wangpr@meiCloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-05-27 09:24:20
 *  修改内容:
 * </pre>
 */
public enum QuotaManagementType {
    /**
     * 规定比例
     */
    FIXED_RATIO,
    /**
     * 综合比例
     */
    COMPREHENSIVE_RATIO,
    /**
     * 配额达成率
     */
    QUOTA_ACHIEVEMENT_RATE
}
