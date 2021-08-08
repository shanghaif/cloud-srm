package com.midea.cloud.srm.model.pm.ps.http;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * <pre>
 *  功能名称  对应 onlineInvoice
 * </pre>
 *
 * @author yourname@meicloud.com
 * @version 1.00.00
 * <p>
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/9/3 10:39
 *  修改内容:
 * </pre>
 */
@Data
@Accessors(chain = true)
public class BoeHeader {

    /**
     * 单据类型编码
     */
    private String boeTypeCode;
    /**
     * 集团编码
     */
    private String groupCode;
    /**
     * 来源系统
     */
    private String sourceSystem;
    /**
     * 外系统单号
     */
    private String sourceSystemNum;
    /**
     * 单据日期
     */
    private String boeDate;

    /**
     * 制单人
     */
    private String createByCode;

    /**
     * 报账人
     */
    private String employeeCode;

    private String postCode;

    /**
     * 所在部门
     */
    private String approvalDeptCode;

    /**
     * 费用承担部门
     */
    private String expenseDeptCode;

    /**
     * 成本中心
     */
    private String boeDeptCode;

    /**
     * 核算主体
     */
    private String leCode;

    /**
     * 业务类型
     */
    private String operationTypeCode;

    /**
     * 是否纸质附件
     */
    private String paperAccessories;

    /**
     * 支付币种
     */
    private String paymentCurrency;

    private String exchangeRate;

    /**
     * 发票总额（含税）
     */
    private String paymentAmount;

    /**
     * 事由
     */
    private String boeAbstract;

    /**
     * 提交人意见
     */
    private String comment;

    /**
     * 供应商
     */
    private String vendorCode;

    private String contractCode;

    private String projectCode;

    private String projectName;

    private String projectTaskCode;

    private String projectTaskName;

    /**
     * 是否手动推计划外
     */
    private String isManuallyPushPlan;

    /**
     * 是否代付
     */
    private String isAgencyPayment;

    /**
     * 代付方
     */
    private String leCodeLG;

    /**
     * 代付方对接人
     */
    private String empCodeLG;

    /**
     * 代付方部门
     */
    private String deptCodeLG;

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

    private String paymentName;//付款方式名称

    private String paymentCode;//付款方式编码

    private String payConditionName;//付款条件名称

    private String payConditionCode;//付款条件编码

    private String accountsPayableDueDate;

    private String taxInvoice;

    private String invoiceType;

    private String invoiceTotal;

    private String poAmount;

    /**
     * 汇率
     */
    private String rateValue;

    /**
     * 付款金额汇总
     */
    private String applyAmount;

    /**
     * 是否服务类
     */
    private String isService;

    /**
     * 是否虚拟发票
     */
    private String virtualInvoice;

    private String expenseAmount;

    /**
     * 供应商地点
     */
    private String vendorSiteID;

    /**
     * 纸质张数
     */
    private Integer bpCount;

    /**
     * 预算告警忽略标识
     */
    private String budgetIgnore;

    /**
     * 资金计划告警忽略标识
     */
    private String fundsPlanIgnore;
    /**
     * 合同付款计划阶段告警忽略标识 Y
     */
    private String contractIgnore;

}
