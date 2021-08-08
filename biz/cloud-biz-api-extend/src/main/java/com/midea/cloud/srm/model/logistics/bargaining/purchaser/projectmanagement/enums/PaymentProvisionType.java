package com.midea.cloud.srm.model.logistics.bargaining.purchaser.projectmanagement.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * <pre>
 *  功能名称
 * </pre>
 *
 * @author tanjl11@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:付款条款类型
 *  修改日期: 2020/9/9 13:35
 *  修改内容:
 * </pre>
 */
@Getter
@AllArgsConstructor
public enum PaymentProvisionType {
    LONGI_BILL("LONGI_BILL","LONGI_银行承兑汇票"),
    LONGI_BILL01("LONGI_BILL01","LONGI_商业承兑汇票"),
    LONGI_CASH("LONGI_CASH","LONGI_现金"),
    LONG_WIRE("LONG_WIRE","LONGI_电汇");
    private final String code;
    private final String value;

}
