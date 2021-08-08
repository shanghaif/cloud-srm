package com.midea.cloud.srm.model.base.configguide.entity;

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
 *  配置导引表 模型
 * </pre>
*
* @author chensl26@meiCloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-04-17 10:14:16
 *  修改内容:
 * </pre>
*/
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("scc_base_config_guide")
public class ConfigGuide extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @TableId("CONFIG_GUIDE_ID")
    private Long configGuideId;

    /**
     * 组织设置状态
     */
    @TableField("ORG_CONFIG")
    private String orgConfig;

    /**
     * 管理层级设置状态
     */
    @TableField("LEVEL_CONFIG")
    private String levelConfig;

    /**
     * 币种维护
     */
    @TableField("CURRENCY_CONFIG")
    private String currencyConfig;

    /**
     * 供应商属性配置
     */
    @TableField("VENDOR_FIELD_CONFIG")
    private String vendorFieldConfig;

    /**
     * 业务状态控制配置
     */
    @TableId("STATE_CONTROL_CONFIG")
    private String stateControlConfig;

    /**
     * 准入流程配置
     */
    @TableField("FLOW_CONFIG")
    private String flowConfig;

    /**
     * 导入采购分类配置
     */
    @TableField("PURCHASE_CONFIG")
    private String purchaseConfig;

    /**
     * 导入物料
     */
    @TableField("MATERIAL_CONFIG")
    private String materialConfig;

    /**
     * 导入供应商
     */
    @TableField("VENDOR_CONFIG")
    private String vendorConfig;

    /**
     * 导入子账号
     */
    @TableField("CHILD_CONFIG")
    private String childConfig;

    /**
     * 单位
     */
    @TableField("UNIT_CONFIG")
    private String unitConfig;

    /**
     * 税费
     */
    @TableField("TAX_CONFIG")
    private String taxConfig;

    /**
     * 附件管理
     */
    @TableField("UPLOAD_CONFIG")
    private String uploadConfig;

    /**
     * 品类分工
     */
    @TableField("DV_CONFIG")
    private String dvConfig;

    /**
     * 流程模板
     */
    @TableField("FLOW_TEMPLATE_CONFIG")
    private String flowTemplateConfig;


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
     * 累计提醒次数
     */
    @TableField("REMINDER_SUM")
    private Long reminderSum;
}
