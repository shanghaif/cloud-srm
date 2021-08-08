package com.midea.cloud.common.enums.pm.ps;

/**
 * <pre>
 *  功能名称
 * </pre>
 *
 * @author chensl26@meicloud.com
 * @version 1.00.00
 * <p>
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/9/2 16:44
 *  修改内容:
 * </pre>
 */
public enum BoeTypeCode {

    FOREIGN_ADVANCE_PAYMENT,//预付款报账单
    PURCHASE_BOE_LGi,//三单匹配单
    FOREIGN_PAYMENT_BOE,//挂账付款单（付款提交-非代付）
    AGENCY_PAYMENT_BOE;//付款提交-代付方式 2020年10月18日22:30:52 费控系统偷偷加的字段坑
}
