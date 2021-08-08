package com.midea.cloud.srm.model.logistics.template.entity;

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
 *  物流采购申请模板头表 模型
 * </pre>
*
* @author xiexh12@meicloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-11-27 16:25:07
 *  修改内容:
 * </pre>
*/
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("scc_lgt_template_head")
public class LogisticsTemplateHead extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 物流采购申请模板头ID
     */
    @TableId("TEMPLATE_HEAD_ID")
    private Long templateHeadId;

    /**
     * 模板编码
     */
    @TableField("TEMPLATE_CODE")
    private String templateCode;

    /**
     * 模板名称
     */
    @TableField("TEMPLATE_NAME")
    private String templateName;

    /**
     * 业务模式(字典编码BUSINESS_MODE)
     */
    @TableField("BUSINESS_MODE_CODE")
    private String businessModeCode;

    /**
     * 运输方式(字典编码TRANSPORT_MODE)
     */
    @TableField("TRANSPORT_MODE_CODE")
    private String transportModeCode;

    /**
     * 备注
     */
    @TableField("COMMENTS")
    private String comments;

    /**
     * 状态(字典编码LOGISTICS_STATUS)
     * {{@link com.midea.cloud.common.enums.logistics.LogisticsStatus}}
     */
    @TableField("STATUS")
    private String status;

    /**
     * 供方是否提交船期
     */
    @TableField("VENDOR_IF_SUBMIT_SHIP")
    private String vendorIfSubmitShip;

    /**
     * 是否需要供方确认
     */
    @TableField("IF_NEED_VENDOR_SUBMIT")
    private String ifNeedVendorSubmit;

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
     * 备用字段6
     */
    @TableField("ATTR6")
    private String attr6;

    /**
     * 备用字段7
     */
    @TableField("ATTR7")
    private String attr7;

    /**
     * 备用字段8
     */
    @TableField("ATTR8")
    private String attr8;

    /**
     * 备用字段9
     */
    @TableField("ATTR9")
    private String attr9;

    /**
     * 备用字段10
     */
    @TableField("ATTR10")
    private String attr10;

    /**
     * 备用字段11
     */
    @TableField("ATTR11")
    private String attr11;

    /**
     * 备用字段12
     */
    @TableField("ATTR12")
    private String attr12;

    /**
     * 备用字段13
     */
    @TableField("ATTR13")
    private String attr13;

    /**
     * 备用字段14
     */
    @TableField("ATTR14")
    private String attr14;

    /**
     * 备用字段15
     */
    @TableField("ATTR15")
    private String attr15;

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
    private Long tenantId;

    /**
     * 版本号
     */
    @TableField("VERSION")
    private Long version;

    /**
     * 模板文件id
     */
    @TableField("TEMPLATE_FILE_ID")
    private Long templateFileId;

    /**
     * 模板文件名称
     */
    @TableField("TEMPLATE_FILE_NAME")
    private String templateFileName;


}
