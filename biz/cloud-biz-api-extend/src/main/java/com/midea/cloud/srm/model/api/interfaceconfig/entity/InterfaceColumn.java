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
 *  接口字段 模型
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
@TableName("scc_api_interface_column")
public class InterfaceColumn extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 字段ID
     */
    @TableId("COLUMN_ID")
    private Long columnId;
    
    /**
     * 排序
     */
    @TableField("LINE_NUM")
    private Integer lineNum;

    /**
     * 接口ID
     */
    @TableField("INTERFACE_ID")
    private Long interfaceId;

    /**
     * 字段名称
     */
    @TableField("COLUMN_DESC")
    private String columnDesc;

    /**
     * 字段名
     */
    @TableField("COLUMN_NAME")
    private String columnName;

    /**
     * 字段类型
     */
    @TableField("COLUMN_TYPE")
    private String columnType;

    /**
     * 转换后字段名
     */
    @TableField("CONVER_NAME")
    private String converName;

    /**
     * 转换后字段类型
     */
    @TableField("CONVER_TYPE")
    private String converType;

    /**
     * 父层字段ID
     */
    @TableField("PARENT_ID")
    private Long parentId;

    /**
     * 子层来源方式
     */
    @TableField("CHILD_TYPE")
    private String childType;
    /**
     * 数据源配置
     */
    @TableField("DATA_CONFIG")
    private String dataConfig;
    /**
     * 子层来源
     */
    @TableField("CHILD_SOURCE")
    private String childSource;

    /**
     * 值来源
     */
    @TableField("SOURCE")
    private String source;

    /**
     * 来源SQL
     */
    @TableField("SQL_TEXT")
    private String sqlText;
    
    /**
     * JAVA类型
     */
    @TableField("JAVA_TYPE")
    private String javaType;
    
    
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

    /**
     * 类型
     */
    @TableField("TYPE")
    private String type;
    
    /**
     * 正确值
     */
    @TableField("CORRECT_VALUE")
    private String correctValue;
    
    /**
     * 正确值
     */
    @TableField("IF_REQUIRYE")
    private String ifRequirye;

    
    /**
     * 值
     */
    @TableField(exist = false)
    private Object value;
}
