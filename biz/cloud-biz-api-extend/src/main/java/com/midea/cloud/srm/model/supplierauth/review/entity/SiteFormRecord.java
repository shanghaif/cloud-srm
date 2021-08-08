package com.midea.cloud.srm.model.supplierauth.review.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.midea.cloud.srm.model.common.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDate;
import java.util.Date;

/**
*  <pre>
 *  现场评审记录 模型
 * </pre>
*
* @author chensl26@meicloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-03-15 17:14:30
 *  修改内容:
 * </pre>
*/
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("scc_sup_auth_site_form_record")
public class SiteFormRecord extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @TableId("SITE_FORM_RECORD_ID")
    private Long siteFormRecordId;

    /**
     * 现场评审单ID
     */
    @TableField("SITE_FORM_ID")
    private Long siteFormId;

    /**
     * 文件上传ID
     */
    @TableField("FILEUPLOAD_ID")
    private Long fileuploadId;

    /**
     * 原始文件名
     */
    @TableField("FILE_SOURCE_NAME")
    private String fileSourceName;

    /**
     * 评审环节
     */
    @TableField("CEEA_REVIEW_LINK")
    private String ceeaReviewLink;

    /**
     * 评审人员
     */
    @TableField("CEEA_REVIEW_PEOPLE")
    private String ceeaReviewPeople;

    /**
     * 供方陪审员
     */
    @TableField("CEEA_VENDOR_ASSESSOR")
    private String ceeaVendorAssessor;

    /**
     * 评审日期
     */
    @TableField("CEEA_REVIEW_DATE")
    private LocalDate ceeaReviewDate;

    /**
     * 认证结果
     */
    @TableField("CEEA_AUTH_RESULT")
    private String ceeaAuthResult;

    /**
     * 评审领域,参考字典码REVIEW_FIELD_TYPE
     */
    @TableField("REVIEW_FIELD")
    private String reviewField;

    /**
     * 评分人
     */
    @TableField("REVIEW_PERSON")
    private String reviewPerson;

    /**
     * 得分
     */
    @TableField("SCORE")
    private Double score;

    /**
     * 生效日期(YYYY-MM-DD)
     */
    @TableField("START_DATE")
    private Date startDate;

    /**
     * 失效日期(YYYY-MM-DD)
     */
    @TableField("END_DATE")
    private Date endDate;

    /**
     * 备注
     */
    @TableField("REMARK")
    private String remark;

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
     * 最后更新人
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
