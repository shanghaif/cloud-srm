package com.midea.cloud.srm.model.base.dynamicsql.entity;

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
 * <pre>
 *  动态sql属性 模型
 * </pre>
 *
 * @author kuangzm
 * @version 1.00.00
 *
 *          <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: Jan 18, 2021 9:54:56 PM
 *  修改内容:
 *          </pre>
 */

@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("scc_base_dynamic_sql_attr")
public class DynamicSqlAttr extends BaseEntity {
	/**
	 * 主键ID
	 */
	@TableId("SQL_ATTR_ID")
	private Long sqlAttrId;
	/**
	 * 快速查询ID
	 */
	@TableField("SQL_ID")
	private Long sqlId;
	/**
	 * 别名
	 */
	@TableField("ALIAS")
	private String alias;
	/**
	 * 属性名
	 */
	@TableField("ATTR")
	private String attr;
	/**
	 * 标题
	 */
	@TableField("TITLE")
	private String title;
	/**
	 * 数据类型
	 */
	@TableField("DATA_TYPE")
	private String dataType;
	
	/**
	 * JAVA类型
	 */
	@TableField("JAVA_TYPE")
	private String javaType;
	
	/**
	 * 表名
	 */
	@TableField("TABLE_NAME")
	private String tableName;
	
	/**
	 * 代码表
	 */
	@TableField("CODE_LIST")
	private String codeList;
	/**
	 * 是否为查询项 Y:是 N:否
	 */
	@TableField("QUERY_ITEM_ENABLED")
	private String queryItemEnabled;
	/**
	 * 是否为显示项 Y:是 N:否
	 */
	@TableField("DISPLAY_ITEM_ENABLED")
	private String displayItemEnabled;
	/**
	 * 序号
	 */
	@TableField("SEQ")
	private Boolean seq;
	/**
	 * 列宽
	 */
	@TableField("COLUMN_WIDTH")
	private String columnWidth;
	/**
	 * 是否为对话框属性设置 Y:是 N:否
	 */
	@TableField("IS_DIALOG_ATTR")
	private String isDialogAttr;
	/**
	 * 属性顺序
	 */
	@TableField("ATTR_ORDER")
	private Long attrOrder;
	/**
	 * 元素类型(select,quicksearch)
	 */
	@TableField("FILEDTYPE")
	private String filedtype;
	/**
	 * 配置信息
	 */
	@TableField("FILEDOPTION")
	private String filedoption;
	
	/**
	 *配匹方式
	 */
	@TableField("QUERY_MATCH_OPERATOR")
	private String queryMatchOperator;
	
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
    
    @TableField(exist = false)
    private Object value;
    
    /**
     * 组件类型
     */
    @TableField("COMPONENT_TYPE")
    private String componentType;
    
    /**
     * 组件类型
     */
    @TableField("COMPONENT_PROPERTY")
    private String componentProperty;
}