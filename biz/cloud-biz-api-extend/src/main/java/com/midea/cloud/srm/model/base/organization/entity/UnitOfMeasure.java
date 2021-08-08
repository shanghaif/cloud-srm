package com.midea.cloud.srm.model.base.organization.entity;

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
 *  erp单位接口表(erp系统的数据, 接口表) 模型
 * </pre>
*
* @author xiexh12@meicloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-12-22 15:03:56
 *  修改内容:
 * </pre>
*/
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("ceea_erp_unit_of_measure")
public class UnitOfMeasure extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 单位id
     */
    @TableId("UNIT_ID")
    private Long unitId;

    /**
     * 单位名称
     */
    @TableField("UOM_NAME")
    private String uomName;

    /**
     * 单位编码
     */
    @TableField("UOM_CODE")
    private String uomCode;

    /**
     * 单位说明
     */
    @TableField("UOM_CODE_DESCR")
    private String uomCodeDescr;

    /**
     * 基本单位标记
     */
    @TableField("BASE_UOM_FLAG")
    private String baseUomFlag;

    /**
     * 单位分类名称
     */
    @TableField("UOM_CLASS_NAME")
    private String uomClassName;

    /**
     * 失效日期
     */
    @TableField("DISABLE_DATE")
    private String disableDate;

    /**
     * 语言代码
     */
    @TableField("LANGUAGE_CODE")
    private String languageCode;

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
     * 来源系统
     */
    @TableField("SOURCE_SYSTEM")
    private String sourceSystem;

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
     * 同步业务表时间
     */
    @TableField("IMPORT_DATE")
    private Date importDate;

    /**
     * 导入业务表状态,0:未导入,1:已导入,-1:导入失败,
     */
    @TableField("IMPORT_STATUS")
    private Integer importStatus;

    /**
     * 报错信息
     */
    @TableField("ERROR_MSG")
    private String errorMsg;


}
