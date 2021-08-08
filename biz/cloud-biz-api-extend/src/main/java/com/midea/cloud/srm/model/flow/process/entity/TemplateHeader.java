package com.midea.cloud.srm.model.flow.process.entity;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableName;
import com.midea.cloud.srm.model.common.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
*  <pre>
 *  工作流模板头表
 * </pre>
*
* @author wuwl18@meiCloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-03-17 14:05:14
 *  修改内容:
 * </pre>
*/
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("srm_cbpm_template_header")
public class TemplateHeader extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**模板名称*/
    @TableField(exist = false)
    private String templateName;

    /**
     * 主键ID
     */
    @TableId("TEMPLATE_HEAD_ID")
    private Long templateHeadId;

    /**
     * cdpm流程fdTemplateId
     */
    @TableField("MODEL_ID")
    private String modelId;

    /**
     * 模板ID对应菜单表functionId/功能维护表Id
     */
    @TableField("TEMPLATE_ID")
    private Long templateId;

    /**
     * 模板编码(功能维护表functionId)
     */
    @TableField("TEMPLATE_CODE")
    private String templateCode;

    /**
     * 描述说明
     */
    @TableField("DESCRIPTION")
    private String description;

    /*** 代办url*/
    @TableField("PENDING_APPROVE_URL")
    private String pendingApproveUrl;

    /*** 分流服务(对应数据字典编码MODULE_DIVISION_FLOW)*/
    @TableField("FEIGN_CLIENT")
    private String feignClient;

    /*** 业务服务*/
    @TableField("BUSSINESS_CLASS")
    private String bussinessClass;

    /**
     * 开始日期
     */
    @TableField("START_DATE_ACTIVE")
    private Date startDateActive;

    /**
     * 结束日期
     */
    @TableField("END_DATE_ACTIVE")
    private Date endDateActive;

    /**
     * 语言
     */
    @TableField("LANGUAGE")
    private String language;

    /**创建人ID*/
    @TableField("CREATED_ID")
    private Long createdId;

    /**创建人IP*/
    @TableField("CREATED_BY_IP")
    private String createdByIp;

    /**最后修改人ID*/
    @TableField("LAST_UPDATED_ID")
    private Long lastUpdatedId;

    /**最后修改人IP*/
    @TableField("LAST_UPDATED_BY_IP")
    private String lastUpdatedByIp;

    /**租户ID*/
    @TableField("TENANT_ID")
    private String tenantId;

    /**
     * 创建人账号
     */
    @TableField("CREATED_BY")
    private String createdBy;

    /**
     * 创建时间
     */
    @TableField("CREATION_DATE")
    private Date creationDate;

    /**
     * 最后更新时间
     */
    @TableField("LAST_UPDATED_BY")
    private String lastUpdatedBy;

    /**
     * 最后更新时间
     */
    @TableField("LAST_UPDATE_DATE")
    private Date lastUpdateDate;

    /**
     * 创建人姓名
     */
    @TableField("CREATED_FULL_NAME")
    private String createdFullName;

    /**
     * 最后更新人姓名
     */
    @TableField("LAST_UPDATED_FULL_NAME")
    private String lastUpdatedFullName;

    /**
     * 是否删除 0不删除 1删除
     */
    @TableField("DELETE_FLAG")
    private Integer deleteFlag;

    /**是否有效(是-Y，否-N)*/
    @TableField("ENABLE_FLAG")
    private String enableFlag;

    /**
     * 版本号
     */
    @TableField("VERSION")
    private Long version;


    @TableField("ATTRIBUTE_CATEGORY")
    private String attributeCategory;

    /**记录initProcess接口使用时长*/
    @TableField("ATTRIBUTE1")
    private String attribute1;

    /**
     * 集成模式
     */
    @TableField("INTEGRATION_MODE")
    private String integrationMode;
    
    /**
     * 业务名称
     */
    @TableField("BUSINESS_NAME")
    private String businessName;
    
}
