package com.midea.cloud.srm.model.cm.model.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
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
 *  合同模板元素表 模型
 * </pre>
*
* @author wangpr@meicloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-06-24 17:30:16
 *  修改内容:
 * </pre>
*/
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("scc_contract_model_element")
public class ModelElement extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID,合同模板行ID
     */
    @TableId("ELEMENT_ID")
    private Long elementId;

    /**
     * 变量名
     */
    @TableField("VARIABLE_NAME")
    private String variableName;

    /**
     * 初始值
     */
    @TableField("INIT_VALUE")
    private String initValue;

    /**
     * 变量名说明
     */
    @TableField("VARIABLE_NAME_INFO")
    private String variableNameInfo;

    /**
     * 变量符号
     */
    @TableField("VARIABLE_SIGN")
    private String variableSign;

    /**
     * 变量符号说明
     */
    @TableField("VARIABLE_SIGN_INFO")
    private String variableSignInfo;

    /**
     * 是否激活,Y-是,N-否
     */
    @TableField("IS_ACT")
    private String isAct;

    /**
     * 是否固定元素,Y-是,N-否
     */
    @TableField("IS_FIXED")
    private String isFixed;

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
     * 更新人
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
     * 租户ID
     */
    @TableField("TENANT_ID")
    private String tenantId;

    /**
     * 版本号
     */
    @TableField("VERSION")
    private Long version;

}
