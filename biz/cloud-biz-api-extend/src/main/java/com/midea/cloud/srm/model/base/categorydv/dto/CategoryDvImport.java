package com.midea.cloud.srm.model.base.categorydv.dto;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import lombok.Data;

import java.io.Serializable;

/**
*  <pre>
 *  品类分工 模型
 * </pre>
*
* @author zhuwl7@meicloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-03-06 10:04:24
 *  修改内容:
 * </pre>
*/
@Data
@ColumnWidth(20)
public class CategoryDvImport implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 品类名称
     */
    @ExcelProperty( value = "*品类名称",index = 0)
    private String categoryName;

    /**
     * 组织名称
     */
    @ExcelProperty( value = "组织名称",index = 1)
    private String orgName;

    /**
     * 员工账号
     */
    @ExcelProperty( value = "*员工账号",index = 2)
    private String userName;

    /**
     * 生效时间
     */
    @ExcelProperty( value = "*生效时间",index = 3)
    private String startDate;

    /**
     * 失效时间
     */
    @ExcelProperty( value = "失效时间",index = 4)
    private String endDate;

    /**
     * 错误信息提示
     */
    @ExcelProperty( value = "错误信息提示",index = 5)
    private String errorMsg;
}
