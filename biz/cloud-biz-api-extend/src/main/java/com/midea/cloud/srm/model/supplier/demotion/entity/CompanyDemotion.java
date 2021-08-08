package com.midea.cloud.srm.model.supplier.demotion.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.midea.cloud.srm.model.common.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.Date;
import java.util.List;

/**
*  <pre>
 *  供应商升降级表 模型
 * </pre>
*
* @author xiexh12@meicloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2021-01-05 17:49:32
 *  修改内容:
 * </pre>
*/
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("ceea_sup_company_demotion")
public class CompanyDemotion extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 升降级主键ID
     */
    @TableId("COMPANY_DEMOTION_ID")
    private Long companyDemotionId;

    /**
     * 单据号
     */
    @TableField("DEMOTION_NUMBER")
    private String demotionNumber;

    /**
     * 单据名称
     */
    @TableField("DEMOTION_NAME")
    private String demotionName;

    /**
     * 供应商ID
     */
    @TableField("COMPANY_ID")
    private Long companyId;

    /**
     * 供应商编码
     */
    @TableField("COMPANY_CODE")
    private String companyCode;

    /**
     * 供应商名称
     */
    @TableField("COMPANY_NAME")
    private String companyName;

    /**
     * 单据状态
     */
    @TableField("STATUS")
    private String status;

    /**
     * 降级类型
     */
    @TableField("DEMOTION_TYPE")
    private String demotionType;

    /**
     * 升降级说明
     */
    @TableField("DEMOTION_DESRC")
    private String demotionDesrc;

    /**
     * 备用字段1
     */
    @TableField("ATTR1")
    private String attr1;

    /**
     * 备用字段2
     */
    @TableField("ATTR2")
    private String attr2;

    /**
     * 备用字段3
     */
    @TableField("ATTR3")
    private String attr3;

    /**
     * 备用字段4
     */
    @TableField("ATTR4")
    private String attr4;

    /**
     * 备用字段5
     */
    @TableField("ATTR5")
    private String attr5;

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
     * 创建人姓名
     */
    @TableField(value = "CREATED_FULL_NAME", fill = FieldFill.INSERT)
    private String createdFullName;

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
     * 评审人用户ids（可能没有，可能多个）
     */
    @TableField(value = "REVIEW_USER_IDS", updateStrategy = FieldStrategy.IGNORED)
    private String reviewUserIds;

    /**
     * 评审人工号（可能没有，可能多个）
     */
    @TableField(value = "REVIEW_USER_EMP_NOS", updateStrategy = FieldStrategy.IGNORED)
    private String reviewUserEmpNos;

    /**
     * 评审人（可能没有，可能多个）
     */
    @TableField(value = "REVIEW_USER_NICKNAMES", updateStrategy = FieldStrategy.IGNORED)
    private String reviewUserNicknames;

    /**
     * 评审人
     */
    @TableField(exist = false)
    private List<Long> reviewUserIdList;

    /**
     * 起草人意见
     */
    @TableField("DRAFTER_OPINION")
    private String drafterOpinion;

    /**
     * 降级时间
     */
    @TableField(value = "DEMOTION_DATE", updateStrategy = FieldStrategy.IGNORED)
    private Date demotionDate;

    /**
     * 是否已降级
     */
    @TableField("IF_DEMOTIONED")
    private String ifDemotioned;

}
