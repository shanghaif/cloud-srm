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
 *  动态sql 模型
 * </pre>
 *
 * @author kuangzm
 * @version 1.00.00
 *
 *          <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: Jan 18, 2021 8:24:06 PM
 *  修改内容:
 *          </pre>
 */

@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("scc_base_dynamic_sql_config")
public class DynamicSqlConfig extends BaseEntity {

	/**
	 * 主键ID
	 */
	@TableId("SQL_ID")
	private Long sqlId;
	/**
	 * 名称
	 */
	@TableField("NAME")
	private String name;
	/**
	 * 查询数据源
	 */
	@TableField("QUERY_DATASOURCE")
	private String queryDatasource;
	/**
	 * 查询模块
	 */
	@TableField("QUERY_MODULE")
	private String queryModule;
	/**
	 * 查询语句类型
	 */
	@TableField("QUERY_LANGUAGE_TYPE")
	private String queryLanguageType;
	/**
	 * 主键字段
	 */
	@TableField("VALUE_ATTR")
	private String valueAttr;
	/**
	 * 查询语句
	 */
	@TableField("QUERY_SQL")
	private String querySql;
	/**
	 * 描述
	 */
	@TableField("DESCRIPTION")
	private String description;
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