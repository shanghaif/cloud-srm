package com.midea.cloud.srm.model.pm.pr.documents.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;
import com.midea.cloud.srm.model.common.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
*  <pre>
 *  后续单据表 模型
 * </pre>
*
* @author zhi1772778785@163.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-10-05 15:34:30
 *  修改内容:
 * </pre>
*/
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("ceea_pr_subsequent_documents")
public class SubsequentDocuments extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 后续单据主键ID
     */
    @TableId("SUBSEQUENT_DOCUMENTS_ID")
    private Long subsequentDocumentsId;

    /**
     * 后续单据编号
     */
    @TableField("SUBSEQUENT_DOCUMENTS_NUMBER")
    private String subsequentDocumentsNumber;

    /**
     * 后续单据类型
     */
    @TableField("ISUBSEQUENT_DOCUMENTSS_TYPE")
    private String isubsequentDocumentssType;

    /**
     * 单据ID
     */
    @TableField("FOLLOW_FORM_ID")
    private Long followFormId;
    /**
     * 单据行ID
     */
    @TableField("REQUIREMENT_LINE_ID")
    private Long requirementLineId;

    /**
     * 租户ID
     */
    @TableField("TENANT_ID")
    private Long tenantId;

    /**
     * 创建人ID
     */
    @TableField(value = "CREATED_ID", fill = FieldFill.INSERT)
    private Long createdId;

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
     * 创建人
     */
    @TableField(value = "CREATED_BY", fill = FieldFill.INSERT)
    private String createdBy;

    /**
     * 最后更新人ID
     */
    @TableField(value = "LAST_UPDATED_ID", fill = FieldFill.INSERT_UPDATE)
    private Long lastUpdatedId;

    /**
     * 最后更新人IP
     */
    @TableField(value = "LAST_UPDATED_BY", fill = FieldFill.INSERT_UPDATE)
    private String lastUpdatedBy;

    /**
     * 最后更新时间
     */
    @TableField(value = "LAST_UPDATE_DATE", fill = FieldFill.INSERT_UPDATE)
    private Date lastUpdateDate;

    /**
     * 版本号
     */
    @TableField("VERSION")
    private Long version;


}
