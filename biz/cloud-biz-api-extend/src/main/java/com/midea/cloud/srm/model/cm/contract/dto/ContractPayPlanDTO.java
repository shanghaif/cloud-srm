package com.midea.cloud.srm.model.cm.contract.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import com.midea.cloud.srm.model.common.BaseDTO;
import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

/**
 * <pre>
 *   合同付款计划DTO
 * </pre>
 *
 * @author yourname@meicloud.com
 * @version 1.00.00
 * <p>
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-6-16 10:18
 *  修改内容:
 * </pre>
 */
@Data
@Accessors(chain = true)
public class ContractPayPlanDTO extends BaseDTO{

    /**
     * 付款计划来源类型
     */
    private String sourceType;

    /**
     * 合同付款计划ID
     */
    private Long payPlanId;

    /**
     * 合同头信息ID
     */
    private Long contractHeadId;

    /**
     * 合同编号
     */
    private String contractNo;

    /**
     * 付款阶段
     */
    private Long payStage;

    /**
     * 付款类型ID
     */
    private Long payTypeId;

    /**
     * 付款类型
     */
    private String payType;

    /**
     * 付款方式（支付方式）
     */
    private String payMethod;

    /**
     * 供应商ID
     */
    private Long vendorId;

    /**
     * 供应商编码
     */
    private String vendorCode;

    /**
     * 供应商名称
     */
    private String vendorName;

    /**
     * 税率值
     */
    private BigDecimal taxRate;

    /**
     * 税率ID
     */
    private Long taxId;

    /**
     * 税率名称
     */
    private String taxName;

    /**
     * 税率编码
     */
    private String taxKey;

    /**
     * 总金额(未税)
     */
    private BigDecimal totalAmountNoTax;

    /**
     * 已付金额
     */
    private BigDecimal paidAmount;

    /**
     * 未付金额（未税）
     */
    private BigDecimal unpaidAmountNoTax;

    /*新增*/

    /**
     * 付款期数(合同期数)
     */
    private String paymentPeriod;

    /**
     * 付款阶段(字典)（付款条件）
     */
    private String paymentStage;

    /**
     * 天数
     */
    private String dateNum;




}
