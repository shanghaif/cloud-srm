package com.midea.cloud.srm.model.supplier.info.dto;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

/**
 * <pre>
 *  功能名称 联系人导入模型DTO
 * </pre>
 *
 * @author chensl26@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/10/21 16:23
 *  修改内容:
 * </pre>
 */
@Data
@ColumnWidth(20)
public class ContactInfoModelDTO implements Serializable {

    @ExcelProperty(value = "新SRM供应商ID", index = 0)
    @NotEmpty(message = "新SRM供应商ID不能为空")
    private String companyId;

    @ExcelProperty(value = "老SRM供应商编码（C开头的）", index = 1)
    private String companyCode;

    @ExcelProperty(value = "ERP供应商ID", index = 2)
    private String erpVendorId;

    @ExcelProperty(value = "供应商ERP编码", index = 3)
    private String erpVendorCode;

    @ExcelProperty(value = "企业名称", index = 4)
    private String companyName;

    @ExcelProperty(value = "姓名", index = 5)
    @NotEmpty(message = "姓名不能为空")
    private String contactName;

    @ExcelProperty(value = "性别", index = 6)
    private String ceeaGender;

    @ExcelProperty(value = "部门", index = 7)
    private String ceeaDeptName;

    @ExcelProperty(value = "职位", index = 8)
    private String position;

    @ExcelProperty(value = "联系方式", index = 9)
    private String ceeaContactMethod;

    @ExcelProperty(value = "邮箱", index = 10)
    private String email;

    @ExcelProperty(value = "默认联系人", index = 11)
    private String ceeaDefaultContact;

    @ExcelProperty(value = "备注", index = 12)
    private String ceeaComments;

    @ExcelProperty( value = "错误提示信息",index = 13)
    private String errorMessage;
}
