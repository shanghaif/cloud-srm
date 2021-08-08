package com.midea.cloud.srm.model.base.dept.dto;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import lombok.Data;

/**
 * <pre>
 *
 * </pre>
 *
 * @author wangpr@meiCloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/10/22
 *  修改内容:
 * </pre>
 */
@Data
@ColumnWidth(20)
public class VirtualDepartImportModel {

    /**
     * 公司名称
     */
    @ExcelProperty(value = "公司名称", index = 0)
    private String companyDescr;

    /**
     * 部门编码/部门ID
     */
    @ExcelProperty(value = "部门编码", index = 1)
    private String deptid;

    /**
     * 部门名称
     */
    @ExcelProperty(value = "部门名称", index = 2)
    private String descr;

    /**
     * 父部门ID
     */
    @ExcelProperty(value = "上级部门编码", index = 3)
    private String partDeptidChn;

    /**
     * 父部门名称
     */
    @ExcelProperty(value = "上级部门名称", index = 4)
    private String partDescrChn;

    /**
     * 信息提示
     */
    @ExcelProperty(value = "信息提示", index = 5)
    private String errorMsg;

}
