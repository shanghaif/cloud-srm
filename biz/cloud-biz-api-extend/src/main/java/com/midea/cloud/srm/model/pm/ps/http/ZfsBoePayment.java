package com.midea.cloud.srm.model.pm.ps.http;

import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

/**
 * <pre>
 *  功能名称 对应bankInfo信息
 * </pre>
 *
 * @author yourname@meicloud.com
 * @version 1.00.00
 * <p>
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/9/3 10:40
 *  修改内容:
 * </pre>
 */
@Data
@Accessors(chain = true)
public class ZfsBoePayment {
    /**
     * 付款方式(支付方式)
     */
    private String paymentModeCode;
    /**
     * 付款方式编码(支付方式)
     */
    private String paymentCode;

    /**
     * 付款方式名称(支付方式名称)
     */
    private String paymentName;

    /**
     * 收款人(账户)
     */
    private String accountName;
    /**
     * 供应商地点
     */
    private String vendorSitesID;
    /**
     * 收款银行
     */
    private String bankBranchName;

    /**
     * 银行账号
     */
    private String bankAccount;

    /**
     * 银行账号
     */
    private String bankAccountNum;

    /**
     * 联行号
     */
    private String bankUnitedCode;

    /**
     * 付款类型
     */
    private String paymentType;

    /**
     * 本位币金额
     */
    private String standardCurrencyAmount;

    /**
     * 支付币种
     */
    private String paymentCurrencyCode;

    /**
     * 支付币金额
     */
    private BigDecimal paymentAmount;

    private String reservedField1;
    private String reservedField2;
    private String reservedField3;
    private String reservedField4;
    private String reservedField5;

    /**
     * 付款明细行号
     */
    private String payDetailsLineNum;

    /**
     * 合同付款阶段
     */
    private String payType;


}
