package com.midea.cloud.srm.model.supplierauth.entry.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.midea.cloud.srm.model.common.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.Date;
import java.util.List;

/**
*  <pre>
 *  供应商准入流程 模型
 * </pre>
*
* @author chensl26@meicloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-03-05 13:54:48
 *  修改内容:
 * </pre>
*/
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("scc_sup_entry_config")
public class EntryConfig extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @TableId("ENTRY_CONFIG_ID")
    private Long entryConfigId;

    /**
     * 供方准入配置单号
     */
    @TableField("ENTRY_CONFIG_NUM")
    private String entryConfigNum;

    /**
     * 供方准入类型,参考字典码QUA_REVIEW_TYPE
     */
    @TableField("QUA_REVIEW_TYPE")
    private String quaReviewType;

    /**
     * 品类ID
     */
    @TableField("CATEGORY_ID")
    private Long categoryId;

    /**
     * 品类编码
     */
    @TableField("CATEGORY_CODE")
    private String categoryCode;

    /**
     * 品类名称
     */
    @TableField("CATEGORY_NAME")
    private String categoryName;

    /**
     * 准入流程,参考字典码ACCESS_PROCESS_TYPE
     */
    @TableField("ACCESS_PROCESS")
    private String accessProcess;

    /**
     * 样品试用流程,参考字典码TRIAL_PROCESS
     */
    @TableField("TRIAL_PROCESS")
    private String trialProcess;

    @TableField(exist = false)
    private List<EntryCategoryConfig> entryCategoryConfigList;

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

    /**
     * 是否需要供方认证
     */
    @TableField(value = "IF_AUTH")
    private String ifAuth;

    /**
     * 是否QPA/QSA
     */
    @TableField(value = "IF_QPA_QSA")
    private String ifQpaQsa;

    /**
     * 是否现场评审
     */
    @TableField(value = "IF_AUTH_ON_SITE")
    private String ifAuthOnSite;

    /**
     * 是样品认证
     */
    @TableField(value = "IF_AUTH_SAMPLE")
    private String ifAuthSample;

    /**
     * 是否物料试用
     */
    @TableField(value = "IF_MATERIAL")
    private String ifMaterial;
}
