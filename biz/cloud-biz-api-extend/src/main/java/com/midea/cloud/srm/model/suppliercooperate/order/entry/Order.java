package com.midea.cloud.srm.model.suppliercooperate.order.entry;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.Date;

/**
 * <pre>
 *  订单
 * </pre>
 *
 * @author chenwj92@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人: chenwj
 *  修改日期: 2020/8/3 11:38
 *  修改内容:
 * </pre>
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("scc_sc_order")
public class Order extends BaseErrorCell {
    private static final long serialVersionUID = 5565069059322448508L;
    /**
     * 主键ID
     */
    @TableId("ORDER_ID")
    private Long orderId;

    /**
     * 订单类型 字典编码：ORDER_TYPE
     */
    @TableField("ORDER_TYPE")
    private String orderType;

    /**
     * 备注
     */
    @TableField("COMMENTS")
    private String comments;

    /**
     * 供应商ID
     */
    @TableField("VENDOR_ID")
    private Long vendorId;

    /**
     * 供应商编码
     */
    @TableField("VENDOR_CODE")
    private String vendorCode;

    /**
     * 供应商名称
     */
    @TableField("VENDOR_NAME")
    private String vendorName;

    /**
     * 订单状态 字典编码：PURCHASE_ORDER
     */
    @TableField("ORDER_STATUS")
    private String orderStatus;

    /**
     * 采购订单号
     */
    @TableField("ORDER_NUMBER")
    private String orderNumber;

    /**
     * 创建人ID(订单拟定人id)
     */
    @TableField(value = "CREATED_ID",fill = FieldFill.INSERT)
    private Long createdId;

    /**
     * 创建人(订单拟定人)
     */
    @TableField(value = "CREATED_BY",fill = FieldFill.INSERT)
    private String createdBy;

    /**
     * 创建时间(订单拟定时间)
     */
    @TableField(value = "CREATION_DATE",fill = FieldFill.INSERT)
    private Date creationDate;

    /**
     * 创建人IP
     */
    @TableField(value = "CREATED_BY_IP",fill = FieldFill.INSERT)
    private String createdByIp;

    /**
     * 最后更新时间
     */
    @TableField(value = "LAST_UPDATE_DATE", fill = FieldFill.INSERT_UPDATE)
    private Date lastUpdateDate;

    /**
     * 最后更新人ID
     */
    @TableField(value = "LAST_UPDATED_ID", fill = FieldFill.INSERT_UPDATE)
    private Long lastUpdatedId;

    /**
     * 最后更新人
     */
    @TableField(value = "LAST_UPDATED_BY", fill = FieldFill.INSERT_UPDATE)
    private String lastUpdatedBy;

    /**
     * 最后更新人IP
     */
    @TableField(value = "LAST_UPDATED_BY_IP", fill = FieldFill.INSERT_UPDATE)
    private String lastUpdatedByIp;

    /**
     * 租户ID
     */
    @TableField("TENANT_ID")
    private Long tenantId;

    /**
     * 版本号
     */
    @TableField("VERSION")
    private Long version;

    /**
     * 订单提交人ID
     */
    @TableField("SUBMITTED_ID")
    private Long submittedId;

    /**
     * 订单提交人
     */
    @TableField("SUBMITTED_BY")
    private String submittedBy;

    /**
     * 提交时间
     */
    @TableField("SUBMITTED_TIME")
    private Date submittedTime;

    /**
     * 订单确认人ID
     */
    @TableField("COMFIRM_ID")
    private Long comfirmId;

    /**
     * 订单确认人
     */
    @TableField("COMFIRM_BY")
    private String comfirmBy;

    /**
     * 确认时间
     */
    @TableField("COMFIRM_TIME")
    private Date comfirmTime;

    /**
     * 订单拒绝人ID
     */
    @TableField("REFUSE_ID")
    private Long refuseId;

    /**
     * 订单拒绝人
     */
    @TableField("REFUSE_BY")
    private String refuseBy;

    /**
     * 拒绝时间
     */
    @TableField("REFUSE_TIME")
    private Date refuseTime;

    /**
     * 拒绝原因
     */
    @TableField("REFUSE_REASON")
    private String refuseReason;

    /**
     * 采购员名称
     */
    @TableField("BUYER_NAME")
    private String buyerName;

    /*不使用的字段*/
    /**
     * 业务实体ID
     */
    @TableField("ORGANIZATION_ID")
    private Long organizationId;

    /**
     * 业务实体编码
     */
    @TableField("ORGANIZATION_CODE")
    private String organizationCode;

    /**
     * 业务实体名称
     */
    @TableField("ORGANIZATION_NAME")
    private String organizationName;

    /**
     * 组织全路径虚拟ID
     */
    @TableField("FULL_PATH_ID")
    private String fullPathId;

    /**
     * 订单金额
     */
    @TableField("ORDER_AMOUNT")
    private BigDecimal orderAmount;

    /**
     * 币种
     */
    @TableField("RFQ_SETTLEMENT_CURRENCY")
    private String rfqSettlementCurrency;

    /**
     * 付款条件
     */
    @TableField("TERM_OF_PAYMENT")
    private String termOfPayment;

    /**
     * 付款方式
     */
    @TableField("PAYMENT_METHOD")
    private String paymentMethod;

    /**
     * 交期等级
     */
    @TableField("DELIVERY_LEVEL")
    private String deliveryLevel;

    /**
     * TEL
     */
    @TableField("TEL")
    private String tel;

    /**
     * 税率编码
     */
    @TableField("TAX_KEY")
    private String taxKey;

    /**
     * 税率
     */
    @TableField("TAX_RATE")
    private BigDecimal taxRate;

    /**
     * 公司代码
     */
    @TableField("COMPANY_CODE")
    private String companyCode;

    /**
     * 供应商反馈状态 字典编码：RESPONSE_STATUS
     */
    @TableField("RESPONSE_STATUS")
    private String responseStatus;

    /**
     * 来源系统 字典编码：SOURCE_SYSTERM
     */
    @TableField("SOURCE_SYSTEM")
    private String sourceSystem;

    /**
     * 采购商回复
     */
    @TableField("PURCHASE_RESPONSE")
    private String purchaseResponse;

    /**
     * JIT订单
     */
    @TableField("JIT_ORDER")
    private String jitOrder;

    @TableField("CBPM_INSTANCE_ID")
    private String cbpmInstaceId;

    @TableField("APPROVE_STATUS")
    private String approveStatus;

    /*新增的字段*/
    /**
     * 订单日期
     */
    @TableField("CEEA_PURCHASE_ORDER_DATE")
    private Date ceeaPurchaseOrderDate;

    /**
     * 是否供应商确认(longi)
     */
    @TableField("CEEA_IF_SUPPLIER_CONFIRM")
    private String ceeaIfSupplierConfirm;

    /**
     * 是否寄售(longi)
     */
    @TableField("CEEA_IF_CONSIGNMENT")
    private String ceeaIfConSignment;

    /**
     * 是否电站业务(longi)
     */
    @TableField("CEEA_IF_POWER_STATION_BUSINESS")
    private String ceeaIfPowerStationBusiness;

    /**
     * 部门id(logni) todo 改成String
     */
    @TableField("CEEA_DEPARTMENT_ID")
    private String ceeaDepartmentId;

    /**
     * 部门编码(longi)
     */
    @TableField("CEEA_DEPARTMENT_CODE")
    private String ceeaDepartmentCode;

    /**
     * 部门名称(longi)
     */
    @TableField("CEEA_DEPARTMENT_NAME")
    private String ceeaDepartmentName;

    /**
     * 起草节点人意见(longi)
     */
    @TableField("CEEA_OPINION")
    private String ceeaOpinion;

    /**
     * 收货地址(longi)
     */
    @TableField("CEEA_RECEIVE_ADDRESS")
    private String ceeaReceiveAddress;

    /**
     * 收货联系人(longi)
     */
    @TableField("CEEA_RECEIVE_CONTACTS")
    private String ceeaReceiveContacts;

    /**
     * 收货联系电话(longi)
     */
    @TableField("CEEA_RECEIVE_TELEPHONE")
    private String ceeaReceiveTelephone;

    /**
     * 收单联系人
     */
    @TableField("CEEA_RECEIVE_ORDER_CONTACT")
    private String ceeaReceiveOrderContact;

    /**
     * 收单联系电话
     */
    @TableField("CEEA_RECEIVE_ORDER_TELEPHONE")
    private String ceeaReceiveOrderTelephone;

    /**
     * 收单地址
     */
    @TableField("CEEA_RECEIVE_ORDER_ADDRESS")
    private String ceeaReceiveOrderAddress;

    /**
     * 供应商联系人(longi)
     */
    @TableField("CEEA_SUPPLIER_CONTACTS")
    private String ceeaSupplierContacts;

    /**
     * 成本类型(longi)
     */
    @TableField("CEEA_COST_TYPE")
    private String ceeaCostType;

    /**
     * 成本类型编码(对应ERP供应商地点ID)
     */
    @TableField("CEEA_COST_TYPE_CODE")
    private String ceeaCostTypeCode;

    /**
     * 合计数量(longi)
     */
    @TableField("CEEA_TOTAL_NUM")
    private BigDecimal ceeaTotalNum;

    /**
     * 合计金额含税(longi)
     */
    @TableField("CEEA_TAX_AMOUNT")
    private BigDecimal ceeaTaxAmount;

    /**
     * 合计金额不含税(longi)
     */
    @TableField("CEEA_NO_TAX_AMOUNT")
    private BigDecimal ceeaNoTaxAmount;

    /**
     * 业务实体ID(longi)
     */
    @TableField("CEEA_ORG_ID")
    private Long ceeaOrgId;

    /**
     * 业务实体编码(longi)
     */
    @TableField("CEEA_ORG_CODE")
    private String ceeaOrgCode;

    /**
     * 业务实体名称(longi)
     */
    @TableField("CEEA_ORG_NAME")
    private String ceeaOrgName;

    /**
     * 质量保证协议编号(longi)
     */
    @TableField("CEEA_Q_A_AGREEMENT_NO")
    private String ceeaQAAgreementNo;

    /**
     * 技术协议编号(longi)
     */
    @TableField("CEEA_T_AGREEMENT_NO")
    private String ceeaTAgreementNo;

    /**
     * 保存人id
     */
    @TableField("CEEA_SAVE_ID")
    private Long ceeaSaveId;

    /**
     * 保存人名称
     */
    @TableField("CEEA_SAVE_BY")
    private String ceeaSaveBy;

    /**
     * 保存时间
     */
    @TableField("CEEA_SAVE_DATE")
    private Date ceeaSaveDate;

    /**
     * 审核人id
     */
    @TableField("CEEA_APPROVAL_ID")
    private Long ceeaApprovalId;

    /**
     * 审核人名称
     */
    @TableField("CEEA_APPROVAL_BY")
    private String ceeaApprovalBy;

    /**
     * 审核时间
     */
    @TableField("CEEA_APPROVAL_DATE")
    private Date ceeaApprovalDate;

    /**
     * 驳回人id
     */
    @TableField("CEEA_REJECT_ID")
    private Long ceeaRejectId;

    /**
     * 驳回人
     */
    @TableField("CEEA_REJECT_BY")
    private String ceeaRejectBy;

    /**
     * 驳回时间
     */
    @TableField("CEEA_REJECT_DATE")
    private Date ceeaRejectDate;

    /**
     * 订单变更是否完成
     */
    @TableField("CEEA_IF_EDIT_DONE")
    private String ceeaIfEditDone;

    /**
     * 账户工号(采购员员工工号)
     */
    @TableField("CEEA_EMP_NO")
    private String ceeaEmpNo;

    /**
     * 采购员id
     */
    @TableField("CEEA_EMP_USER_ID")
    private Long ceeaEmpUseId;

    /**
     * 采购员名称
     */
    @TableField("CEEA_EMP_USERNAME")
    private String ceeaEmpUsername;


    /**
     * 采购订单类型条目标识（对接erp需要使用）
     */
    @TableField("CEEA_ORDER_TYPE_IDENTIFICATION")
    private String ceeaOrderTypeIdentification;


    /*确认后删除*/

    /**
     * 采购类型(longi)
     */
    @TableField("CEEA_PURCHASE_TYPE")
    private String ceeaPurchaseType;

    /**
     * 是否推送erp(longi)
     */
    @TableField("IS_SEND_ERP")
    private String isSendErp;

    /**
     * erp订单编号
     */
    @TableField("ERP_ORDER_NUMBER")
    private String eprOrderNumber;

    /**
     * 是否样品订单
     */
    @TableField(value = "IF_SAMPLE",fill = FieldFill.INSERT)
    private String ifSample;

    /**
     * 是否已经推送过erp Y/null
     */
    @TableField("HAS_PUSHED")
    private String hasPushed;

    /**
     * 是否海鲜价公式
     */
    @TableField(value ="IS_SEA_FOOD_FORMULA")
    private String isSeaFoodFormula;

    /**
     * 是否有订单结算
     */
    @TableField(value="HAS_SETTLEMENT")
    private String hasSettlement;

    /**
     * 付款阶段
     */
    @TableField("PAYMENT_STAGE")
    private String paymentStage;

    /**
     * 付款比例
     */
    @TableField("PAYMENT_RATIO")
    private BigDecimal paymentRatio;
    /**
     * 贸易条款/贸易术语
     */
    @TableField("TRADE_TERM")
    private String tradeTerm;

    /**
     * 订单的明细行是否已经确认/拒绝
     */
    @TableField("IF_DETAIL_HANDLE")
    private String ifDetailHandle;
}
