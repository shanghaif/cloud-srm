package com.midea.cloud.srm.model.bid.suppliercooperate.vo;

import com.midea.cloud.srm.model.common.BaseEntity;
import lombok.Data;
import java.io.Serializable;
import java.util.Date;

/**
 * <pre>
 *  招标（项目）列表信息--供应商端
 * </pre>
 *
 * @author zhuomb1@midea.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/3/31 16:35
 *  修改内容:
 * </pre>
 */
@Data
public class SupplierBidingVO extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    /** 招标单ID */
    private Long bidingId;
    /** (项目)招标编码 */
    private String bidingNum;
    /** 邀请供应商投标表单ID */
    private Long bidVendorId;
    /** (项目)招标名称 */
    private String bidingName;
    /** 标的类型 */
    private String targetType;
    /** 招标范围 */
    private String bidingScope;
    /** 招标项目状态 */
    private String bidingStatus;
    /** 报名状态 */
    private String signUpStatus;
    /** 报名表ID */
    private Long signUpId;
    /** 投标状态 */
    private String orderStatus;
    /** 投标表ID */
    private Long orderHeadId;
    /** 轮次 */
    private Integer round;
    /** biding表当前轮次 */
    private String currentRound;
    /** 招标类型 */
    private String bidingType;
    /** 供应商ID */
    private Long vendorId;
    /** 供应商编码 */
    private String vendorCode;
    /** round--roundId */
    private Long roundId;
    /** round--商务开标Y/N */
    private String businessOpenBid;
    /** 轮次表--投标/报价开始时间 */
    private Date startTime;
    /** 轮次表--投标/报价结束时间 */
    private Date endTime;

    /** 报名截止时间 */
    private Date enrollEndDatetime;
    /** 投标开始时间 */
    private Date bidingStartDatetime;
    /** 投标单号 */
    private String bidOrderNum;
    /** 发布时间 */
    private Date releaseDatetime;
    /** 企业编码 */
    private String companyCode;
    /** 组织编码 */
    private String organizationCode;
    /** 附件类型 报名:Enroll,投标:Biding */
    private String fileType;

    /** 报价保留小位数 */
    private Integer decimalAccuracy;
    /** 投标币种 */
    private String bidingCurrency;
    /** 是否含税 */
    private String taxInclusivePrice;
    /** 税率编码 */
    private String taxKey;
    /** 税率 */
    private String taxRate;
    /** 评分规则 */
    private String evaluateMethod;
    /** 决标方式 */
    private String bidingAwardWay;
    /** 跟标权限 */
    private String bidPerssion;
    /**
     * 是否允许供应商撤回投标
     */
    private String withdrawBiding;

}