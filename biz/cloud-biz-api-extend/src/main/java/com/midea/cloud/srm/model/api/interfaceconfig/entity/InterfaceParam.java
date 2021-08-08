package com.midea.cloud.srm.model.api.interfaceconfig.entity;

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
 *  接口参数 模型
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
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("scc_api_interface_param")
public class InterfaceParam extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 参数ID
     */
    @TableId("PARAM_ID")
    private Long paramId;

    /**
     * 接口ID
     */
    @TableField("INTERFACE_ID")
    private Long interfaceId;

    /**
     * 参数名称
     */
    @TableField("PARAM_NAME")
    private String paramName;

    /**
     * 参数key
     */
    @TableField("PARAM_KEY")
    private String paramKey;

    /**
     * 参数类型
     */
    @TableField("PARAM_TYPE")
    private String paramType;

    /**
     * 是否必填
     */
    @TableField("IF_REQUIRYE")
    private String ifRequirye;
    
    /**
     * key转换
     */
    @TableField("CHANGE_KEY")
    private String changeKey;
    
    
    /**
     * 默认值
     */
    @TableField("DEFAULT_VALUE")
    private String defaultValue;
    

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
