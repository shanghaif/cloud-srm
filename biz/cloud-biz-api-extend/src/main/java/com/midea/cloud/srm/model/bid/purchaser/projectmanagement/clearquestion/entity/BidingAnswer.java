package com.midea.cloud.srm.model.bid.purchaser.projectmanagement.clearquestion.entity;

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
 *  招标澄清表 模型
 * </pre>
*
* @author zhuomb1@midea.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-04-20 15:22:47
 *  修改内容:
 * </pre>
*/
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("scc_bid_biding_answer")
public class BidingAnswer extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @TableId("ANSWER_ID")
    private Long answerId;

    /**
     * 澄清编号
     */
    @TableField("ANSWER_NUM")
    private String answerNum;

    /**
     * 质疑主键ID,公开澄清，ID为空，质疑澄清，对应质疑表ID
     */
    @TableField("QUESTION_ID")
    private Long questionId;

    /**
     * 招标单主键ID
     */
    @TableField("BIDING_ID")
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
     * 澄清状态
     */
    @TableField("ANSWER_STATUS")
    private String answerStatus;

    /**
     * 澄清标题
     */
    @TableField("ANSWER_TITLE")
    private String answerTitle;

    /**
     * 澄清内容
     */
    @TableField("ANSWER_COMTENT")
    private String answerComtent;

    /**
     * 提交时间
     */
    @TableField("SUBMIT_TIME")
    private Date submitTime;

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
