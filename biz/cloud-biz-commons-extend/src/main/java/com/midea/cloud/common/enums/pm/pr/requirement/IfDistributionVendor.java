package com.midea.cloud.common.enums.pm.pr.requirement;

/**
 * <pre>
 * 是否已分配供应商
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
public enum IfDistributionVendor {
    /**
     * 是
     */
    Y,
    /**
     * 否
     */
    N,
    /**
     * 分配中
     */
    ALLOCATING,
    /**
     * 分配失败
     */
    ALLOCATING_FAILED
}
