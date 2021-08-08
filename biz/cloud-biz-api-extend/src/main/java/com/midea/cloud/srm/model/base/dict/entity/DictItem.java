package com.midea.cloud.srm.model.base.dict.entity;

import java.util.Date;
import java.time.LocalDate;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableName;
import com.midea.cloud.srm.model.common.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
*  <pre>
 *  字典条目 模型
 * </pre>
*
* @author zhuwl7@meicloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-02-19 10:33:18
 *  修改内容:
 * </pre>
*/
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("scc_base_dict_item")
public class DictItem extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @TableId("DICT_ITEM_ID")
    private Long dictItemId;

    /**
     * 字典ID
     */
    @TableField("DICT_ID")
    private Long dictId;

    /**
     * 条目编码
     */
    @TableField("DICT_ITEM_CODE")
    private String dictItemCode;

    /**
     * 条目语言
     */
    @TableField("ITEM_LANGUAGE")
    private String itemLanguage;

    /**
     * 条目语言名称
     */
    @TableField("ITEM_LANGUAGE_NAME")
    private String itemLanguageName;


    /**
     * 条目名称
     */
    @TableField("DICT_ITEM_NAME")
    private String dictItemName;

    /**
     * 条目描述
     */
    @TableField("ITEM_DESCRIPTION")
    private String itemDescription;

    /**
     * 条目顺序
     */
    @TableField("DICT_ITEM_NO")
    private Integer dictItemNo;

    /**
     * 条目标识
     */
    @TableField("DICT_ITEM_MARK")
    private String dictItemMark;

    /**
     * 生效日期
     */
    @TableField("ACTIVE_DATE")
    private LocalDate activeDate;

    /**
     * 失效日期
     */
    @TableField("INACTIVE_DATE")
    private LocalDate inactiveDate;

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


}
