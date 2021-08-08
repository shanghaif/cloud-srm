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
 *  工作流行表模板 模型
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
@TableName("srm_cbpm_template_lines")
public class TemplateLines extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @TableId("TEMPLATE_LINES_ID")
    private Long templateLinesId;

    /**
     * 流程模板头表ID
     */
    @TableField("TEMPLATE_HEAD_ID")
    private Long templateHeadId;

    /**
     * 事件类型(业务单据类型)
     */
    @TableField("BUSSINESS_TYPE")
    private String bussinessType;

    /**
     * 事件处理过程（业务方法）
     */
    @TableField("BUSSINESS_FUNCTION")
    private String bussinessFunction;

    /**
     * 事件名称(描述)
     */
    @TableField("DESCRIPTION")
    private String description;

    /**
     * 生效日期
     */
    @TableField("START_DATE_ACTIVE")
    private Date startDateActive;

    /**
     * 失效日期
     */
    @TableField("END_DATE_ACTIVE")
    private Date endDateActive;

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
