package com.midea.cloud.srm.model.logistics.bargaining.purchaser.projectmanagement.bidinitiating.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.midea.cloud.srm.model.common.BaseEntity;
import lombok.*;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
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
@TableName("scc_brg_biding")
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
     * 申请模板code
     */
    @TableField("PURCHASE_REQUSET_TEMPLATE_CODE")
    @NotEmpty(message = "申请模板code不能为空")
    private String purchaseRequestTemplateCode;

    /**
     * 申请模板id
     */
    @TableField("PURCHASE_REQUSET_TEMPLATE_ID")
    @NotNull(message = "申请模板id不能为空")
    private Long purchaseRequestTemplateId;

    /**
     * 申请模板名称
     */
    @TableField("PURCHASE_REQUEST_TEMPLATENAME")
    @NotEmpty(message="申请模板名称不能为空")
    private String purchaseRequestTemplateName;

    /**
     * 招标流程配置id
     */
    @TableField("PROCESS_CONFIG_ID")
    private Long processConfigId;


    /**
     * 投标开始时间
     */
    @TableField("BIDING_START_DATETIME")
    private Date bidingStartDatetime;

    /**
     * 报名截止时间
     */
    @TableField("ENROLL_END_DATETIME")
    @NotNull(message = "投标截至时间不能为空")
    private Date enrollEndDatetime;
    /**
     * 预算金额
     */
    @TableField("BUDGET_AMOUNT")
    private BigDecimal budgeAmount;
    /**
     * 招标类型
     * 字典编码:BID_TYPE
     * BUSINESS:商务    TECHNOLOGY_BUSINESS:技术+商务
     */
    @TableField("BIDING_TYPE")
    private String bidingType;
    /**
     * 决标方式，总项和分项
     */
    @TableField("BIDING_AWARD_WAY")
    private String bidingAwardWay;
    /**
     * 税率编码
     */
    @TableField("TAX_CODE")
    @NotEmpty(message = "税率编码必填")
    private String taxCode;
    /**
     * 价格精确度
     */
    @TableField("DECIMAL_ACCURACY")
    private Integer decimalAccuracy;
    /**
     * 联系人
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
     * 结项审核状态
     */
    @TableField("AUDIT_STATUS")
    private String auditStatus;
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
     * 当前轮
     */
    @TableField("CURRENT_ROUND")
    private Integer currentRound;

    /*备注*/

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
    /**
     * 本位币
     */
    @TableField("CEEA_STANDARD_CURRENCY")
    @NotEmpty(message = "本位币不可为空")
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
     * 默认价格有效期开始
     */
    @TableField("CEEA_DEFAULT_PRICE_VALID_FROM")
    @NotNull(message = "价格有效开始时间不能为空")
    private Date defaultPriceValidFrom;

    /**
     * 默认价格有效期结束
     */
    @TableField("CEEA_DEFAULT_PRICE_VALID_TO")
    @NotNull(message = "价格有效结束时间不能为空")
    private Date defaultPriceValidTo;
    /**
     * 业务模式
     */
    @TableField("BUSINESS_MODE")
    @NotBlank(message = "业务模式不能为空")
    private String businessMode;
    /**
     * 业务类型 项目与非项目
     */
    @TableField("BUSINESS_TYPE")
    @NotEmpty(message = "业务类型不能为空")
    private String businessType;
    /**
     * 运输方式
     */
    @TableField("TRANSPORT_MODE")
    @NotEmpty(message = "运输方式不能为空")
    private String transportMode;
    /**
     * tms服务项目名称
     */
    @TableField("TMS_PROJECT_NAME")
    private String tmsProjectName;
    /**
     * tms服务项目id
     */
    @TableField("TMS_PROJECT_ID")
    private Long tmsProjectId;
    /**
     * 单位名称
     */
    @TableField("UNIT")
    @NotEmpty(message = "单位名称不能为空")
    private String unit;
    /**
     * 需求日期
     */
    @TableField("DEMAND_DATE")
    private Date demandDate;
    /**
     * 项目总量
     */
    @TableField("TOTAL_COUNT")
    @NotNull(message = "项目总量不能为空")
    private BigDecimal totalCount;

    /**
     * 项目最大可进车型
     */
    @TableField("LARGEST_VEHICLE_TYPE")
    private String largestVehicleType;

    /**
     * 装载量
     * 只有国内+陆运必填
     */
    @TableField("LOAD_COUNT")
    private BigDecimal loadCount;


    /**
     * 税率
     */
    @TableField("TAX_RATE")
    @NotNull(message = "税率值必填")
    private BigDecimal taxRate;

    /**
     * 是否提交船期
     */
    @TableField("WHETHER_SUBMIT_SCHEDULE")
    @NotEmpty(message = "是否提交船期必填")
    private String whetherSubmitSchedule;
    /**
     * 指定供应商
     */
    @TableField("DESIGNATED_VENDOR")
    private String designatedVendor;
    /**
     * 指定供应商编码
     */
    @TableField("DESIGNATED_VENDOR_CODE")
    private String designatedVendorCode;
    /**
     * 指定供应商id
     */
    @TableField("DESIGNATED_VENDOR_ID")
    private Long designatedVendorId;
    /**
     * 指定供应商原因
     */
    @TableField("DESIGNATED_VENDOR_REASON")
    private String designatedVendorReason;

    /**
     * 采购申请单号
     */
    @TableField("PURCHASE_REQUISITION_NUMBER")
    private String purchaseRequisitionNumber;
    /**
     * 采购申请人
     */
    @TableField("PURCHASE_APPLICANT")
    private String purchaseApplicant;
    /**
     * 采购申请部门
     */
    @TableField("PURCHASE_REQUISITION_DEPARTMENT")
    private String purchaseRequisitionDepartment;
    /**
     * 联系电话
     */
    @TableField("PURCHASE_APPLICANT_PHONE")
    private String purchaseApplicantPhone;
    /**
     * 采购申请创建人
     */
    @TableField("PURCHASE_REQUISITION_CREATOR")
    private String purchaseRequisitionCreator;
    /**
     * 采购申请创建日期
     */
    @TableField("PURCHASE_REQUISITION_CREATIONDATE")
    private Date purchaseRequisitionCreationDate;
    /**
     * 采购申请最后更新人
     */
    @TableField("PURCHASE_REQUISITION_UPDATOR")
    private String purchaseRequisitionUpdator;
    /**
     * 采购申请最后更新日期
     */
    @TableField("PURCHASE_REQUISITION_UPDATEDDATE")
    private Date purchaseRequisitionUpdatedDate;


    /**
     * 已提交报价供应商数量
     */
    @TableField("HAS_SUBMIT_VENDOR")
    private Integer hasSubmitVendor;
    /**
     * 邀请供应商数量
     */
    @TableField("VENDOR_TOTAL")
    private Integer vendorTotal;


    /**
     * 合同类型
     */
    @TableField("CONTRACT_TYPE")
    @NotEmpty(message = "合同类型必填")
    private String contractType;
    /**
     * 撤回原因
     */
    @TableField("WITHDRAW_REASON")
    private String withdrawReason;
    /**
     * 附件查看
     */
    @TableField(exist = false)
    private List<BidFile> fileList;
}
