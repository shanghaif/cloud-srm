package com.midea.cloud.srm.model.${codeVo.moduleName}.${packageName}.dto;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import lombok.Data;

import java.util.Date;

/**
* <pre>
 *  ${codeVo.functionDesc} excel导出模型
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
@ColumnWidth(15) //列宽
public class Excel${lineClassFileName}Dto {
private static final long serialVersionUID = ${currentTimeMillis}L;
<#list selectLineFileList as field>
 <#if (field.isPk == "1")>
  /**
  * ${field.filedDesc}
  */
  @ExcelProperty( value = "${field.filedDesc}",index = ${field_index})
  private ${field.javaType} ${field.javaCode};
 <#else>
 /**
 * ${field.filedDesc}
 */
 @ExcelProperty( value = "${field.filedDesc}",index = ${field_index})
 private ${field.javaType} ${field.javaCode};
 </#if>
 <#if !field_has_next>
 /**
 * 错误信息提示
 */
 @ExcelProperty( value = "错误信息提示",index = ${field_index+1})
 private String errorMsg;
 </#if>
</#list>

}