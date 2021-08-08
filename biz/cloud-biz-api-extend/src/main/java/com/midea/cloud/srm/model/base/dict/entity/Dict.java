package com.midea.cloud.srm.model.base.dict.entity;

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
 *  字典表 模型
 * </pre>
*
* @author zhuwl7@meicloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-02-19 10:33:17
 *  修改内容:
 * </pre>
*/
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("scc_base_dict")
public class Dict extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @TableId("DICT_ID")
    private Long dictId;

    /**
     * 字典编码
     */
    @TableField("DICT_CODE")
    private String dictCode;

    /**
     * 字典名称
     */
    @TableField("DICT_NAME")
    private String dictName;

    /**
     * 字典描述
     */
    @TableField("DESCRIPTION")
    private String description;

    /**
     * 字典语言
     */
    @TableField("LANGUAGE")
    private String language;

    /**
     * 字典语言名称
     */
    @TableField("LANGUAGE_NAME")
    private String languageName;

    /**
     * 管控角色
     */
    @TableField("DICT_ROLE")
    private String dictRole;

    /**
     * 管控角色名称
     */
    @TableField("DICT_ROLE_Name")
    private String dictRoleName;

    /**
     * 生效日期
     */
    @TableField("ACTIVE_DATE")
    private Date activeDate;

    /**
     * 失效时间
     */
    @TableField("INACTIVE_DATE")
    private Date inactiveDate;

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
     * 字典编码
     */
    @TableField(exist = false)
    private String dictItemCode;

    /**
     * 字典名称
     */
    @TableField(exist = false)
    private String dictItemName;

}
