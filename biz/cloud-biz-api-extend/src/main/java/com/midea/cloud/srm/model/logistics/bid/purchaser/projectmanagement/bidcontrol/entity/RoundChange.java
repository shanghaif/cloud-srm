package com.midea.cloud.srm.model.logistics.bid.purchaser.projectmanagement.bidcontrol.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.midea.cloud.srm.model.common.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.Date;

/**
*  <pre>
 *  供应商投标轮次变更表 模型
 * </pre>
*
* @author fengdc3@meiCloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-04-10 15:13:53
 *  修改内容:
 * </pre>
*/
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("scc_lgt_round_change")
public class RoundChange extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 变更单主键ID
     */
    @TableId("CHANGE_ID")
    private Long changeId;

    /**
     * 变更供应商投标轮次表主键ID
     */
    @TableField("CHANGE_ROUND_ID")
    private Long changeRoundId;

    /**
     * 变更招标单ID
     */
    @TableField("CHANGE_BIDING_ID")
    private Long changeBidingId;

    /**
     * 变更轮次
     */
    @TableField("CHANGE_ROUND")
    private Integer changeRound;

    /**
     * 变更投标/报价开始时间
     */
    @TableField("CHANGE_START_TIME")
    private Date changeStartTime;

    /**
     * 变更投标/报价结束时间
     */
    @TableField("CHANGE_END_TIME")
    private Date changeEndTime;

    /**
     * 变更商务开标Y/N
     */
    @TableField("CHANGE_BUSINESS_OPEN_BID")
    private String changeBusinessOpenBid;

    /**
     * 变更商务开标时间
     */
    @TableField("CHANGE_BUSINESS_OPEN_BID_TIME")
    private Date changeBusinessOpenBidTime;

    /**
     * 变更公示结果Y/N
     */
    @TableField("CHANGE_PUBLIC_RESULT")
    private String changePublicResult;

    /**
     * 变更公示时间
     */
    @TableField("CHANGE_PUBLIC_RESULT_TIME")
    private Date changePublicResultTime;

    /**
     * 延长原因
     */
    @TableField("REASON")
    private String reason;

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
     * 更新人
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
    private String tenantId;

    /**
     * 版本号
     */
    @TableField("VERSION")
    private Long version;


}
