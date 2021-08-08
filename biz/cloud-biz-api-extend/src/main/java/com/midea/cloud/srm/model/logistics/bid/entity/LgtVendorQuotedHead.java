package com.midea.cloud.srm.model.logistics.bid.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.midea.cloud.srm.model.common.BaseEntity;
import lombok.*;
import lombok.experimental.Accessors;

import java.util.Date;

/**
*  <pre>
 *  供应商报价头表 模型
 * </pre>
*
* @author wangpr@meiCloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2021-01-06 09:08:34
 *  修改内容:
 * </pre>
*/
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("scc_lgt_vendor_quoted_head")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LgtVendorQuotedHead extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 供应商报价头ID
     */
    @TableId("QUOTED_HEAD_ID")
    private Long quotedHeadId;

    /**
     * 物流招标头ID
     */
    @TableField("BIDING_ID")
    private Long bidingId;

    /**
     * 当前轮次
     */
    @TableField("ROUND")
    private Integer round;

    /**
     * 供应商投标编号
     */
    @TableField("QUOTED_HEAD_CODE")
    private String quotedHeadCode;

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
     * 项目编码
     */
    @TableField("BIDING_NUM")
    private String bidingNum;

    /**
     * 提交日期
     */
    @TableField("SUBMIT_DATE")
    private Date submitDate;

    /**
     * 提交人ID
     */
    @TableField("SUBMIT_USER_ID")
    private Long submitUserId;

    /**
     * 提交人账号
     */
    @TableField("SUBMIT_USERNAME")
    private String submitUsername;

    /**
     * 提交人名字
     */
    @TableField("SUBMIT_NIKE_NAME")
    private String submitNikeName;

    /**
     * 作废原因
     */
    @TableField("INVALID_REASON")
    private String invalidReason;

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
     * 投标状态   BIDDING_ORDER_STATES
     * BiddingOrderStates
     * {{@link com.midea.cloud.common.enums.logistics.BiddingOrderStates}}
     */
    @TableField("STATUS")
    private String status;

    /**
     * 是否代理报价(N/Y)
     */
    @TableField("IF_PROXY")
    private String ifProxy;

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
     * 撤回原因
     */
    @TableField("WITHDRAW_REASON")
    private String withdrawReason;
    /**
     * 投标说明
     */
    @TableField("SUBMIT_COMMENT")
    private String submitComment;
}
