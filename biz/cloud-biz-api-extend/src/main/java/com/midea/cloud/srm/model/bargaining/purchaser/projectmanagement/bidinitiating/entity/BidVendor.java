package com.midea.cloud.srm.model.bargaining.purchaser.projectmanagement.bidinitiating.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.midea.cloud.srm.model.bargaining.purchaser.projectmanagement.quoteauthorize.vo.QuoteAuthorizeVO;
import com.midea.cloud.srm.model.common.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * <pre>
 *  供应商表 模型
 * </pre>
 *
 * @author fengdc3@meiCloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-03-27 17:36:33
 *  修改内容:
 *          </pre>
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("scc_brg_vendor")
public class BidVendor extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @TableField(exist = false)
    private List<QuoteAuthorizeVO> quoteAuthorizeList;

    /**
     * 主键ID
     */
    @TableId("BID_VENDOR_ID")
    private Long bidVendorId;

    /**
     * 招标单ID
     */
    @TableField("BIDING_ID")
    private Long bidingId;

    /**
     * 供应商ID
     */
    @TableField("VENDOR_ID")
    private Long vendorId;

    /**
     * 供应商编号
     */
    @TableField("VENDOR_CODE")
    private String vendorCode;

    /**
     * 供应商名称
     */
    @TableField("VENDOR_NAME")
    private String vendorName;

    /**
     * 招标范围
     */
    @TableField("BIDING_SCOPE")
    private String bidingScope;

    /**
     * 联系人
     */
    @TableField("LINK_MAN_NAME")
    private String linkManName;

    /**
     * 电话
     */
    @TableField("PHONE")
    private String phone;

    /**
     * 邮箱
     */
    @TableField("EMAIL")
    private String email;

    /**
     * 保证金
     */
    @TableField("BOND")
    private BigDecimal bond;

    /**
     * 报名标识
     */
    @TableField("JOIN_FLAG")
    private String joinFlag;

    /**
     * 备注
     */
    @TableField("COMMENTS")
    private String comments;

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
     * 租户ID
     */
    @TableField("TENANT_ID")
    private Long tenantId;

    /**
     * 版本号
     */
    @TableField("VERSION")
    private Long version;

    @TableField("CEEA_CHOOSE_BASE_MATERIAL_PRICE")
    private String chooseBaseMaterialPrice;
    /**
     * 海鲜价相关业务逻辑
     */
    @TableField(exist = false)
    private String isSeaFoodPrice;
    @TableField(exist = false)
    private String priceJSON;
    @TableField(exist = false)
    private Integer round;
}
