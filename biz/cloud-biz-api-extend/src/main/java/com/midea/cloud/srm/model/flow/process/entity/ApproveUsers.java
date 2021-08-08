package com.midea.cloud.srm.model.flow.process.entity;

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
 *  流程审批人表 模型
 * </pre>
*
* @author wuwl18@meiCloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-03-18 17:59:53
 *  修改内容:
 * </pre>
*/
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("srm_cbpm_approve_users")
public class ApproveUsers extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @TableId("APPROVE_USER_ID")
    private Long approveUserId;

    @TableField("MODEL_ID")
    private String modelId;

    /**
     * 节点编码
     */
    @TableField("NODE_ID")
    private String nodeId;

    /**
     * 节点名称
     */
    @TableField("NODE_NAME")
    private String nodeName;

    /**
     * 组织类型
     */
    @TableField("ORG_TYPE")
    private String orgType;

    /**
     * 组织编码
     */
    @TableField("ORG_CODE")
    private String orgCode;

    /**
     * 组织名称
     */
    @TableField("ORG_NAME")
    private String orgName;

    /**
     * 组织全路径虚拟ID
     */
    @TableField("FULL_PATH_ID")
    private String fullPathId;

    @TableField("APPROVE_BY_NAME")
    private String approveByName;

    @TableField("APPROVE_BY_USER_NAME")
    private String approveByUserName;

    @TableField("APPROVE_BY")
    private String approveBy;

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
    private Long tenantId;

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

    /**
     * 版本号
     */
    @TableField("VERSION")
    private Long version;
    /**属性类别*/
    @TableField("ATTRIBUTE_CATEGORY")
    private String attributeCategory;
    /**备用字段1*/
    @TableField("ATTRIBUTE1")
    private String attribute1;

}
