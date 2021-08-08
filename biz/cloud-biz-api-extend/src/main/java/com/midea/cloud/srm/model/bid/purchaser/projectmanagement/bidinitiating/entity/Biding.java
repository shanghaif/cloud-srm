package com.midea.cloud.srm.model.bid.purchaser.projectmanagement.bidinitiating.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.midea.cloud.srm.model.common.BaseEntity;
import lombok.*;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
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
@TableName("scc_bid_biding")
public class Biding extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID 招标ID
     */
    @TableId("BIDING_ID")
    private Long bidingId;

    /**
     * (项目)招标编码
     */
    @TableField("BIDING_NUM")
    private String bidingNum;

    /**
     * (项目)招标名称
     */
    @TableField("BIDING_NAME")
    private String bidingName;

    /**
     * 采购组织ID
     */
    @TableField("ORG_ID")
    private Long orgId;

    /**
     * 组织全路径虚拟ID
     */
    @TableField("FULL_PATH_ID")
    private String fullPathId;

    /**
     * 采购组织名称
     */
    @TableField("ORG_NAME")
    private String orgName;

    /**
     * 招标流程配置id
     */
    @TableField("PROCESS_CONFIG_ID")
    private Long processConfigId;


    /**
     * 立项审批外部CBPM实例ID
     */
    @TableField("INIT_CBPM_INSTANCE_ID")
    private String initCbpmInstanceId;

    /**
     * 结项审批外部CBPM实例ID
     */
    @TableField("END_CBPM_INSTANCE_ID")
    private String endCbpmInstanceId;

    /**
     * 投标开始时间
     */
    @TableField("BIDING_START_DATETIME")
    private Date bidingStartDatetime;

    /**
     * 预计投标地点
     */
    @TableField("BIDING_SITE")
    private String bidingSite;

    /**
     * 报名截止时间
     */
    @TableField("ENROLL_END_DATETIME")
    private Date enrollEndDatetime;

    /**
     * 招标范围
     * 字典编码：BID_SCOPE
     * OPEN_TENDER:公开招标  INVITE_TENDER:邀请招标
     */
    @TableField("BIDING_SCOPE")
    private String bidingScope;

    /**
     * 预算金额
     */
    @TableField("BUDGET_AMOUNT")
    private BigDecimal budgetAmount;

    /**
     * 标的类型
     * 字典编码:BID_OBJECT_TYPE
     * PRODUCE:生产性物料    SPARE_PART:备品备件
     * EQUIPMENT_ENGINEERING:设备/工程   SERVICE:服务类
     * IDLE_SALE:闲置资产出售     INVANTION:研发类
     */
    @TableField("TARGET_TYPE")
    private String targetType;

    /**
     * 招标类型
     * 字典编码:BID_TYPE
     * BUSINESS:商务    TECHNOLOGY_BUSINESS:技术+商务
     */
    @TableField("BIDING_TYPE")
    private String bidingType;

    /**
     * 评选方式
     * 字典编码:BID_GRADING
     * LOWER_PRICE:合理低价法    HIGH_PRICE:合理高价法
     * COMPOSITE_SCORE:综合评分法
     */
    @TableField("EVALUATE_METHOD")
    private String evaluateMethod;

    /**
     * 决标方式
     * 字典编码:BID_DECIDE_METHOD
     * INDIVIDUAL_DECISION:单项决标（按编码）
     * COMBINED_DECISION:组合决标（按物料分组）
     */
    @TableField("BIDING_AWARD_WAY")
    private String bidingAwardWay;

    /**
     * 保证金金额
     */
    @TableField("BOND_AMOUNT")
    private BigDecimal bondAmount;

    /**
     * 保证金说明
     */
    @TableField("BOND_DESC")
    private String bondDesc;

    /**
     * 保证金提交方式
     * 字典编码:BID_BOND_SUBMISSION
     * CASH:现金   BANK_DRAFT:银行汇票    BANK_PROMISSORY_NOTE:银行本票
     * CERTIFIED_CHEQUE:保兑支票    TENDER_GUARANTEE:投标保函
     */
    @TableField("BOND_METHOD")
    private String bondMethod;

    /**
     * 保证金提交截止时间
     */
    @TableField("BOND_END_DATETIME")
    private Date bondEndDatetime;

    /**
     * 报价是否含税
     */
    @TableField("TAX_INCLUSIVE_PRICE")
    private String taxInclusivePrice;

    /**
     * 招标币种
     * 字典编码:BID_TENDER_CURRENCY
     * CNY:人民币    USD:美元   HKD:港币   JPY:日元
     * GBP:英镑    EUR:欧元    CHF:瑞士法郎    FRF:法国法郎
     * CAD:加拿大元    AUD:澳大利亚元
     */
    @TableField("BIDING_CURRENCY")
    private String bidingCurrency;

    /**
     * 报价最多留几位小数
     */
    @TableField("DECIMAL_ACCURACY")
    private Integer decimalAccuracy;

    /**
     * 保证金缴纳账号
     */
    @TableField("BANK_ACCOUNT_NUM")
    private String bankAccountNum;

    /**
     * 账户名称
     */
    @TableField("BANK_ACCOUNT_NAME")
    private String bankAccountName;

    /**
     * 开户支行ID
     */
    @TableField("BANK_BRANCH_ID")
    private Long bankBranchId;

    /**
     * 开户支行
     */
    @TableField("BANK_BRANCH_NAME")
    private String bankBranchName;

    /**
     * 联系人姓名
     */
    @TableField("BID_CONTACT_NAME")
    private String bidContactName;

    /**
     * 手机
     */
    @TableField("BID_MOBILE_PHONE")
    private String bidMobilePhone;

    /**
     * 邮箱
     */
    @TableField("BID_EMAIL")
    private String bidEmail;

    /**
     * 企业编码
     */
    @TableField("COMPANY_CODE")
    private String companyCode;

    /**
     * 组织编码
     */
    @TableField("ORGANIZATION_CODE")
    private String organizationCode;

    /**
     * 是否允许供应商撤回投标
     */
    @TableField("WITHDRAW_BIDING")
    private String withdrawBiding;

    /**
     * 是否允许供应商只对部分商品报价
     */
    @TableField("PART_PRICE")
    private String partPrice;

    /**
     * 是否向供应商公开上轮最低价
     */
    @TableField("PUBLIC_LOWEST_PRICE")
    private String publicLowestPrice;

    /**
     * 是否向供应商公开上轮排名结果
     */
    @TableField("PUBLIC_TOTAL_RANK")
    private String publicTotalRank;

    /**
     * 是否向供应商公开拦标价
     */
    @TableField("PUBLIC_TARGET_PRICE")
    private String publicTargetPrice;

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
     * 技术开标标识
     */
    @TableField("TECH_OPEN_BID")
    private String techOpenBid;

    /**
     * 技术开标时间
     */
    @TableField("TECH_OPEN_BID_TIME")
    private Date techOpenBidTime;

    /**
     * 结束评选
     */
    @TableField("END_EVALUATION")
    private String endEvaluation;

    /**
     * 生成价格审批
     */
    @TableField("GENERATE_PRICE_APPROVAL")
    private String generatePriceApproval;

    /**
     * 当前轮
     */
    @TableField("CURRENT_ROUND")
    private Integer currentRound;

    /**
     * 是否最后一轮
     */
    @TableField("FINAL_ROUND")
    private String finalRound;

    /**
     * 审批人用户名
     */
    @TableField("APPROVED_BY")
    private String approvedBy;

    /**
     * 编制人用户名
     */
    @TableField("PREPARED_BY")
    private String preparedBy;

    /**
     * 备注
     */
    @TableField("COMMENTS")
    private String comments;

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
    @TableField(value = "LAST_UPDATED_ID", fill = FieldFill.UPDATE)
    private Long lastUpdatedId;

    /**
     * 最后更新人
     */
    @TableField(value = "LAST_UPDATED_BY", fill = FieldFill.UPDATE)
    private String lastUpdatedBy;

    /**
     * 最后更新时间
     */
    @TableField(value = "LAST_UPDATE_DATE", fill = FieldFill.INSERT_UPDATE)
    private Date lastUpdateDate;

    /**
     * 最后更新人IP
     */
    @TableField(value = "LAST_UPDATED_BY_IP", fill = FieldFill.UPDATE)
    private String lastUpdatedByIp;

    /**
     * 拥有者/租户/所属组等
     */
    @TableField("TENANT_ID")
    private Long tenantId;

    /**
     * 版本号
     */
    @TableField("VERSION")
    private Long version;


    /* ===================== 定制化字段 ===================== */

    /**
     * 月利率
     */
    @TableField("CEEA_MONTHLY_INTEREST")
    private BigDecimal monthlyInterest;
    /**
     * 本位币
     */
    @TableField("CEEA_STANDARD_CURRENCY")
    private String standardCurrency;
    /**
     * 价格精度
     */
    @TableField("CEEA_PRICE_PRECISION")
    private Integer pricePrecision;
    /**
     * 汇率类型
     */
    @TableField("CEEA_EXCHANGE_RATE_TYPE")
    private String exchangeRateType;
    /**
     * 向供应商展示汇率
     */
    @TableField("CEEA_SHOW_RATE_TYPE")
    private String showRateType;
    /**
     * 币种转换日期
     */
    @TableField("CEEA_CURRENCY_CHANGE_DATE")
    private Date currencyChangeDate;

    /**
     * 是否同步至价格库 （Y/N）
     */
    @TableField("CEEA_IS_SYNC_TO_PRICE_LIBRARY")
    private String      isSyncToPriceLibrary;

    /**
     * 默认价格有效期开始
     */
    @TableField("CEEA_DEFAULT_PRICE_VALID_FROM")
    private Date        defaultPriceValidFrom;

    /**
     * 默认价格有效期结束
     */
    @TableField("CEEA_DEFAULT_PRICE_VALID_TO")
    private Date        defaultPriceValidTo;

    /**
     * 配额分配类型
     */
    @TableField("CEEA_QUOTA_DISTRIBUTE_TYPE")
    private String      quotaDistributeType;

    /**
     * 寻源单来源
     *
     * @see com.midea.cloud.srm.model.bid.purchaser.projectmanagement.bidinitiating.enums.SourceFrom
     */
    @TableField("CEEA_SOURCE_FROM")
    private String sourceFrom;

    /**
     * 币种行信息
     */
    @TableField(exist = false)
    private List<BidBidingCurrency> currencies;

    @TableField(exist = false)
    private List<BidFile> fileList;

    /**
     * BPM起草人意见
     */
    @TableField("CEEA_DRAFTER_OPINION")
    private String ceeaDrafterOpinion;


    @TableField(exist = false)
    private String processType;//提交审批Y，废弃N
}
