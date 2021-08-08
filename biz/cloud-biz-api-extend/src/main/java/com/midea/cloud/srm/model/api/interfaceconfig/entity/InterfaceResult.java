package com.midea.cloud.srm.model.api.interfaceconfig.entity;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.midea.cloud.srm.model.common.BaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
*  <pre>
 *  接口返回
 * </pre>
*
* @author kuangzm@meicloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-06-08 18:02:16
 *  修改内容:
 * </pre>
*/
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@TableName("scc_api_interface_result")
public class InterfaceResult extends BaseEntity {
	/**
     * 参数ID
     */
    @TableId("COLUMN_ID")
    private Long columnId;

    /**
     * 接口ID
     */
    @TableField("INTERFACE_ID")
    private Long interfaceId;

    /**
     * 参数名称
     */
    @TableField("COLUMN_NAME")
    private String columnName;
    
    /**
     * 参数类型
     */
    @TableField("COLUMN_TYPE")
    private String columnType;
    
    /**
     * 父层字段ID
     */
    @TableField("PARENT_ID")
    private Long parentId;
    
    /**
     * 正确值
     */
    @TableField("RESULT_VALUE")
    private String resultValue;
    
    /**
     * 行号
     */
    @TableField("LINE_NUM")
    private Integer lineNum;
    
    
    
    
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
     * 最后更新时间
     */
    @TableField(value = "LAST_UPDATE_DATE", fill = FieldFill.UPDATE)
    private Date lastUpdateDate;

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
     * 最后更新人IP
     */
    @TableField(value = "LAST_UPDATED_BY_IP", fill = FieldFill.UPDATE)
    private String lastUpdatedByIp;

    /**
     * 租户
     */
    @TableField("TENANT_ID")
    private Long tenantId;

    /**
     * 版本号
     */
    @TableField("VERSION")
    private Long version;

}
