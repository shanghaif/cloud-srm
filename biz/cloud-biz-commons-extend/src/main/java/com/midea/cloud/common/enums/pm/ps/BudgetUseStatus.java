package com.midea.cloud.common.enums.pm.ps;

/**
 * <pre>
 *    FSSC预算使用状态枚举
 * </pre>
 *
 * @author chensl26@meicloud.com
 * @version 1.00.00
 * <p>
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/8/28 15:12
 *  修改内容:
 * </pre>
 */
public enum BudgetUseStatus {

    S,//整单释放
    P,//部分释放:为P时,请求行非空
    D,//整单失效
    F;//冻结
}
