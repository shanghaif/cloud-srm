package com.midea.cloud.srm.model.inq.inquiry.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.midea.cloud.srm.model.common.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.Date;

/**
 *  <pre>
 *  询价-询价信息头表 模型
 * </pre>
 *
 * @author zhongbh
 * @version 1.00.00
 *
 *  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-03-14 17:32:14
 *  修改内容:
 * </pre>
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("scc_inquiry_header")
public class Header extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 询价单id
     */
    @TableId("INQUIRY_ID")
    private Long inquiryId;

    /**
     * 询价单号
     */
    @TableField("INQUIRY_NO")
    private String inquiryNo;

    /**
     * 询价标题
     */
    @TableField("INQUIRY_TITLE")
    private String inquiryTitle;

    /**
     * 组织id
     */
    @TableField("ORGANIZATION_ID")
    private Long organizationId;

    /**
     * 组织全路径虚拟ID
     */
    @TableField("FULL_PATH_ID")
    private String fullPathId;

    /**
     * 组织id
     */
    @TableField("ORGANIZATION_NAME")
    private String organizationName;

    /**
     * 公司id
     */
    @TableField("COMPANY_ID")
    private Long companyId;

    /**
     * 报价方式
     */
    @TableField("QUOTE_RULE")
    private String quoteRule;

    /**
     * 评分方式
     */
    @TableField("INQUIRY_RULE")
    private String inquiryRule;

    /**
     * 询价类型
     */
    @TableField("INQUIRY_TYPE")
    private String inquiryType;

    /**
     * 货币
     */
    @TableField("CURRENCY")
    private String currency;

    /**
     * 税率编码
     */
    @TableField("TAX_KEY")
    private String taxKey;

    /**
     * 税率
     */
    @TableField("TAX_RATE")
    private String taxRate;

    /**
     * 类型
     */
    @TableField("TYPE")
    private String type;

    /**
     * 地址
     */
    @TableField("ADDRESS")
    private String address;

    /**
     * 报价开始时间
     */
    @TableField("BEGIN_QUOTE")
    private Date beginQuote;
    /**
     * 截止时间
     */
    @TableField("DEADLINE")
    private Date deadline;

    /**
     * 目标价是否加密
     */
    @TableField("IS_TARGET_PRICE_ENCRY")
    private String isTargetPriceEncry;

    /**
     * 轮次
     */
    @TableField("ROUND")
    private String round;

    /**
     * 状态
     */
    @TableField("STATUS")
    private String status;

    /**
     * 审批状态
     */
    @TableField("AUDIT_STATUS")
    private String auditStatus;

    /**
     * 联系人
     */
    @TableField("LINKMAN")
    private String linkman;

    /**
     * 电话
     */
    @TableField("TEL")
    private String tel;

    /**
     * 邮箱
     */
    @TableField("EMAIL")
    private String email;

    /**
     * 备注
     */
    @TableField("REMARK")
    private String remark;

    /**
     * 子询价单id
     */
    @TableField("CHILD_INQUIRY_ID")
    private Long childInquiryId;

    /**
     * 父询价单id
     */
    @TableField("PARENT_INQUIRY_ID")
    private Long parentInquiryId;

    /**
     * 创建公司id
     */
    @TableField("CREATE_COMPANY_ID")
    private Long createCompanyId;

    /**
     * 报价供应商数量
     */
    @TableField("QUOTE_CNT")
    private BigDecimal quoteCnt;

    /**
     * 报价保留位数
     */
    @TableField("PRICE_NUM")
    private String priceNum;

    /**
     * 邀请数量
     */
    @TableField("INVITE_CNT")
    private BigDecimal inviteCnt;

    /**
     * 发布时间
     */
    @TableField("PUBLISH_DATE")
    private Date publishDate;

    /**流程ID*/
    @TableField("CBPM_INSTANCE_ID")
    private String cbpmInstaceId;

    /**
     * 删除标识
     */
    @TableField("DEL_FLAG")
    private String delFlag;

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
     * 版本号
     */
    @TableField("VERSION")
    private Long version;

    /**
     * 租户ID
     */
    @TableField("TENANT_ID")
    private Long tenantId;


}

