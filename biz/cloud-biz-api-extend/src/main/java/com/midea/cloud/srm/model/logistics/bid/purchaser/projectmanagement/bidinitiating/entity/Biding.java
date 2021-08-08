package com.midea.cloud.srm.model.logistics.bid.purchaser.projectmanagement.bidinitiating.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.midea.cloud.srm.model.common.BaseEntity;
import lombok.*;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

/**
 * <pre>
 *  招标基础信息表 模型
 * </pre>
 *
 * @author fengdc3
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-03-18 10:10:11
 *  修改内容:
 * </pre>
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("scc_lgt_biding")
public class Biding extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @TableId("BIDING_ID")
    private Long bidingId;

    /**
     * 招标流程配置id
     */
    @TableField("PROCESS_CONFIG_ID")
    private Long processConfigId;

    /**
     * 项目编码
     */
    @TableField("BIDING_NUM")
    private String bidingNum;

    /**
     * 物流采购申请模板头ID
     */
    @TableField("TEMPLATE_HEAD_ID")
    private Long templateHeadId;

    /**
     * 模板编码
     */
    @TableField("TEMPLATE_CODE")
    private String templateCode;

    /**
     * 模板名称
     */
    @TableField("TEMPLATE_NAME")
    private String templateName;

    /**
     * 业务模式
     */
    @TableField("BUSINESS_MODE_CODE")
    private String businessModeCode;

    /**
     * 运输方式
     */
    @TableField("TRANSPORT_MODE_CODE")
    private String transportModeCode;

    /**
     * 业务类型
     */
    @TableField("BUSINESS_TYPE")
    private String businessType;

    /**
     * 服务项目ID
     */
    @TableField("PROJECT_ID")
    private Long projectId;

    /**
     * 服务项目编码
     */
    @TableField("PROJECT_CODE")
    private String projectCode;

    /**
     * 服务项目名称
     */
    @TableField("PROJECT_NAME")
    private String projectName;

    /**
     * 单位ID
     */
    @TableField("UNIT_ID")
    private Long unitId;

    /**
     * 单位编码
     */
    @TableField("UNIT_CODE")
    private String unitCode;

    /**
     * 单位名称
     */
    @TableField("UNIT_NAME")
    private String unitName;

    /**
     * 项目总量
     */
    @TableField("PROJECT_TOTAL")
    private BigDecimal projectTotal;

    /**
     * 项目名称
     */
    @TableField("BIDING_NAME")
    private String bidingName;

    /**
     * 需求日期
     */
    @TableField("DEMAND_DATE")
    private LocalDate demandDate;

    /**
     * 预算金额
     */
    @TableField("BUDGET_AMOUNT")
    private BigDecimal budgetAmount;

    /**
     * 币种ID
     */
    @TableField("CURRENCY_ID")
    private Long currencyId;

    /**
     * 币种编码
     */
    @TableField("CURRENCY_CODE")
    private String currencyCode;

    /**
     * 币种名称
     */
    @TableField("CURRENCY_NAME")
    private String currencyName;

    /**
     * 项目地可进最大车型
     */
    @TableField("LARGEST_MODEL")
    private String largestModel;

    /**
     * 价格有效开始时间
     */
    @TableField("PRICE_TIME_START")
    private LocalDate priceTimeStart;

    /**
     * 价格有效开始时间
     */
    @TableField("PRICE_TIME_END")
    private LocalDate priceTimeEnd;

    /**
     * 装载量
     */
    @TableField("LOADING")
    private String loading;

    /**
     * 投标截止时间
     */
    @TableField("ENROLL_END_DATETIME")
    private Date enrollEndDatetime;

    /**
     * 税率ID
     */
    @TableField("TAX_ID")
    private Long taxId;

    /**
     * 税率值
     */
    @TableField("TAX_CODE")
    private BigDecimal taxCode;

    /**
     * 税率编码
     */
    @TableField("TAX_KEY")
    private String taxKey;

    /**
     * 是否提交船期(N/Y)
     */
    @TableField("IF_SUBMIT_SHIPPING_SCHEDULE")
    private String ifSubmitShippingSchedule;

    /**
     * 合同类型
     */
    @TableField("CONTRACT_TYPE")
    private String contractType;

    /**
     * 指定供应商ID
     */
    @TableField("COMPANY_ID")
    private Long companyId;

    /**
     * 指定供应商CODE
     */
    @TableField("COMPANY_CODE")
    private String companyCode;

    /**
     * 指定供应商名称
     */
    @TableField("COMPANY_NAME")
    private String companyName;

    /**
     * 指定供应商原因
     */
    @TableField("SPECIFY_SUP_REASON")
    private String specifySupReason;

    /**
     * 备注
     */
    @TableField("COMMENTS")
    private String comments;

    /**
     * 申请单号
     */
    @TableField("APPLICATION_NUM")
    private String applicationNum;

    /**
     * 申请人ID
     */
    @TableField("APPLICANT_USER_ID")
    private Long applicantUserId;

    /**
     * 申请人账号
     */
    @TableField("APPLICANT_USER_NAME")
    private String applicantUserName;

    /**
     * 申请人名称
     */
    @TableField("APPLICANT_NIKE_NAME")
    private String applicantNikeName;

    /**
     * 申请部门ID
     */
    @TableField("APPLICATION_DEPART_ID")
    private Long applicationDepartId;

    /**
     * 申请部门
     */
    @TableField("APPLICATION_DEPART")
    private String applicationDepart;

    /**
     * 联系电话
     */
    @TableField("PHONE")
    private String phone;

    /**
     * 创建人名字
     */
    @TableField("CREATED_BY_NAME")
    private String createdByName;

    /**
     * 内部说明
     */
    @TableField("INTERNAL_DESC")
    private String internalDesc;

    /**
     * 供方说明
     */
    @TableField("SUPPLIER_DESC")
    private String supplierDesc;

    /**
     * 本位币
     */
    @TableField("STANDARD_CURRENCY")
    private String standardCurrency;

    /**
     * 价格精度
     */
    @TableField("PRICE_PRECISION")
    private Integer pricePrecision;

    /**
     * 汇率类型
     */
    @TableField("EXCHANGE_RATE_TYPE")
    private String exchangeRateType;

    /**
     * 币种转换日期
     */
    @TableField("CURRENCY_CHANGE_DATE")
    private LocalDate currencyChangeDate;

    /**
     * 联系人姓名
     */
    @TableField("BID_USER_NAME")
    private String bidUserName;

    /**
     * 手机
     */
    @TableField("BID_USER_PHONE")
    private String bidUserPhone;

    /**
     * 邮箱
     */
    @TableField("BID_USER_EMAIL")
    private String bidUserEmail;

    /**
     * 是否报价截止前允许供应商撤回报价
     */
    @TableField("WITHDRAW_BIDING")
    private String withdrawBiding;

    /**
     * 是否向供应商公开上轮排名结果
     */
    @TableField("PUBLIC_TOTAL_RANK")
    private String publicTotalRank;

    /**
     * 是否向供应商公开上轮最低价
     */
    @TableField("PUBLIC_LOWEST_PRICE")
    private String publicLowestPrice;

    /**
     * 允许供应商查看最终排名结果
     */
    @TableField("VISIBLE_RANK_RESULT")
    private String visibleRankResult;

    /**
     * 允许供应商查看中标价
     */
    @TableField("VISIBLE_FINAL_PRICE")
    private String visibleFinalPrice;

    /**
     * 允许供应商查看中标供应商
     */
    @TableField("VISIBLE_WIN_VENDOR")
    private String visibleWinVendor;

    /**
     * 来源单据类型
     */
    @TableField("SOURCE_FROM")
    private String sourceFrom;

    /**
     * 当前轮
     */
    @TableField("CURRENT_ROUND")
    private Integer currentRound;

    /**
     * 撤回原因
     */
    @TableField("REVOKE_REASON")
    private String revokeReason;

    /**
     * 发布时间
     */
    @TableField("RELEASE_DATETIME")
    private Date releaseDatetime;

    /**
     * 发布标识
     */
    @TableField("RELEASE_FLAG")
    private String releaseFlag;

    /**
     * 结束评选
     */
    @TableField("END_EVALUATION")
    private String endEvaluation;

    /**
     * 已投标供应商
     */
    @TableField("BIDDING_SUPPLIERS")
    private String biddingSuppliers;

    /**
     * 招标项目状态
     */
    @TableField("BIDING_STATUS")
    private String bidingStatus;

    /**
     * 立项审核状态
     */
    @TableField("AUDIT_STATUS")
    private String auditStatus;

    /**
     * 结项审核状态
     */
    @TableField("END_AUDIT_STATUS")
    private String endAuditStatus;

    /**
     * 是否需要供方确认
     */
    @TableField("VENDOR_IF_SUBMIT_SHIP")
    private String vendorIfSubmitShip;

    /**
     * 生成采购订单(Y/N)
     */
    @TableField("GENERATE_PURCHASE_APPROVAL")
    private String generatePurchaseApproval;

    /**
     * 后续单据ID
     */
    @TableField("AFTER_ORDER_ID")
    private Long afterOrderId;

    /**
     * 后续单据号
     */
    @TableField("AFTER_ORDER_NO")
    private Long afterOrderNo;

    /**
     * 创建人ID
     */
    @TableField(value = "CREATED_ID", fill = FieldFill.INSERT)
    private Long createdId;

    /**
     * 创建人
     */
    @TableField(value = "CREATED_BY", fill = FieldFill.INSERT)
    private String createdBy;

    /**
     * 创建时间
     */
    @TableField(value = "CREATION_DATE", fill = FieldFill.INSERT)
    private Date creationDate;

    /**
     * 创建人IP
     */
    @TableField(value = "CREATED_BY_IP", fill = FieldFill.INSERT)
    private String createdByIp;

    /**
     * 最后更新人ID
     */
    @TableField(value = "LAST_UPDATED_ID", fill = FieldFill.INSERT_UPDATE)
    private Long lastUpdatedId;

    /**
     * 更新人
     */
    @TableField(value = "LAST_UPDATED_BY", fill = FieldFill.INSERT_UPDATE)
    private String lastUpdatedBy;

    /**
     * 最后更新时间
     */
    @TableField(value = "LAST_UPDATE_DATE", fill = FieldFill.INSERT_UPDATE)
    private Date lastUpdateDate;

    /**
     * 最后更新人IP
     */
    @TableField(value = "LAST_UPDATED_BY_IP", fill = FieldFill.INSERT_UPDATE)
    private String lastUpdatedByIp;

    /**
     * 租户ID
     */
    @TableField("TENANT_ID")
    private String tenantId;

    /**
     * 版本号
     */
    @TableField("VERSION")
    private Long version;

    /**
     * 币种行信息
     */
    @TableField(exist = false)
    private List<BidBidingCurrency> currencies;

    @TableField(exist = false)
    private List<BidFile> fileList;

    @TableField(exist = false)
    private String processType;//提交审批Y，废弃N
}
