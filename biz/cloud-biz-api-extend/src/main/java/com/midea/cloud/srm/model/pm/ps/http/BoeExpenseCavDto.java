package com.midea.cloud.srm.model.pm.ps.http;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * <pre>
 *  功能名称  对应onlineInvoiceAdvance
 * </pre>
 *
 * @author chenwj92@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人: chenwj92
 *  修改日期: 2020/9/3 15:04
 *  修改内容:
 * </pre>
 */
@Data
@Accessors(chain = true)
public class BoeExpenseCavDto {
    /**
     * 来源系统挂账单号
     */
    private String sourceSystemNum;

    /**
     * 本次申请付款金额(作废)
     */
    private String appliedPayLineAmount;

    /**
     * 核销金额
     */
    private String cavAmount;

    /**
     * 预留字段1
     */
    private String reservedField1;

    /**
     * 预留字段2
     */
    private String reservedField2;

    /**
     * 预留字段3
     */
    private String reservedField3;

    /**
     * 预留字段4
     */
    private String reservedField4;

    /**
     * 预留字段5
     */
    private String reservedField5;

}
