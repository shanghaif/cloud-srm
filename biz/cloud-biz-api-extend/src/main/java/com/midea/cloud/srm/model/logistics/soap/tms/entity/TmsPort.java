package com.midea.cloud.srm.model.logistics.soap.tms.entity;

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
 *  港口信息维护表 模型
 * </pre>
*
* @author xiexh12@meicloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-11-26 14:24:26
 *  修改内容:
 * </pre>
*/
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("scc_lgt_port")
public class TmsPort extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 港口ID
     */
    @TableId("PORT_ID")
    private Long portId;

    /**
     * 国家编号
     */
    @TableField("COUNTRY_CODE")
    private String countryCode;

    /**
     * 国家中文名
     */
    @TableField("COUNTRY_NAME_ZHS")
    private String countryNameZhs;

    /**
     * 国家英文名
     */
    @TableField("COUNTRY_NAME_EN")
    private String countryNameEn;

    /**
     * 港口代码
     */
    @TableField("PORT_CODE")
    private String portCode;

    /**
     * 港口中文名称
     */
    @TableField("PORT_NAME_ZHS")
    private String portNameZhs;

    /**
     * 港口英文名称
     */
    @TableField("PORT_NAME_EN")
    private String portNameEn;

    /**
     * 港口类型(字典编码PORT_TYPE)
     */
    @TableField("PORT_TYPE")
    private String portType;

    /**
     * 状态(字典编码LOGISTICS_STATUS)
     */
    @TableField("STATUS")
    private String status;

    /**
     * 来源系统
     */
    @TableField("SOURCE_SYSTEM")
    private String sourceSystem;

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
