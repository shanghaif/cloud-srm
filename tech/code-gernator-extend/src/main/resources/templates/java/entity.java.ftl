package com.midea.cloud.srm.model.${codeVo.moduleName}.${packageName}.entity;

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
* <pre>
 *  ${codeVo.functionDesc} 模型
 * </pre>
*
* @author ${codeVo.author}
* @version 1.00.00
*
* <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: ${.now}
 *  修改内容:
 * </pre>
*/

@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("${databaseTable}")
public class ${classFileName} extends BaseEntity {
private static final long serialVersionUID = ${currentTimeMillis}L;
 <#list selectHeadFileList as field>
  <#if (field.isPk == "1")>
  /**
  * ${field.filedDesc}
  */
  @TableId("${field.dbCode}")
  private ${field.javaType} ${field.javaCode};
  <#elseif field.javaCode == "createdId">
   /**
   * 创建人ID
   */
   @TableField(value ="CREATED_ID",fill = FieldFill.INSERT)
   private Long createdId;
  <#elseif field.javaCode == "creationDate">
  /**
  * 创建时间
  */
  @TableField(value = "CREATION_DATE",fill = FieldFill.INSERT)
  private Date creationDate;
  <#elseif field.javaCode == "createdByIp">
  /**
  * 创建人IP
  */
  @TableField(value ="CREATED_BY_IP" ,fill = FieldFill.INSERT)
  private String createdByIp;
  <#elseif field.javaCode == "lastUpdateDate">
  /**
  * 最后更新时间
  */
  @TableField(value = "LAST_UPDATE_DATE", fill = FieldFill.INSERT_UPDATE)
  private Date lastUpdateDate;
  <#elseif field.javaCode == "createdBy">
  /**
  * 创建人
  */
  @TableField(value = "CREATED_BY",fill = FieldFill.INSERT)
  private String createdBy;
  <#elseif field.javaCode == "lastUpdatedId">
  /**
  * 最后更新人ID
  */
  @TableField(value = "LAST_UPDATED_ID",fill = FieldFill.INSERT_UPDATE)
  private Long lastUpdatedId;
  <#elseif field.javaCode == "lastUpdatedBy">
  /**
  * 最后更新人
  */
  @TableField(value = "LAST_UPDATED_BY", fill = FieldFill.INSERT_UPDATE)
  private String lastUpdatedBy;
  <#elseif field.javaCode == "lastUpdatedByIp">
  /**
  * 最后更新人IP
  */
  @TableField(value = "LAST_UPDATED_BY_IP", fill = FieldFill.INSERT_UPDATE)
  private String lastUpdatedByIp;
  <#else>
  /**
  * ${field.filedDesc}
  */
  @TableField("${field.dbCode}")
  private ${field.javaType} ${field.javaCode};
  </#if>
</#list>
}