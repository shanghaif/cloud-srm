package com.midea.cloud.srm.model.supplier.info.dto;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

/**
 * <pre>
 *  功能名称 银行信息导入模型DTO
 * </pre>
 *
 * @author chensl26@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/10/21 16:24
 *  修改内容:
 * </pre>
 */
@Data
@ColumnWidth(20)
public class BankInfoModelDTO implements Serializable {

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

    @ExcelProperty(value = "银行代码", index = 5)
    private String bankCode;

    @ExcelProperty(value = "银行名称", index = 6)
    private String bankName;

    @ExcelProperty(value = "开户行名称", index = 7)
    private String openingBank;

    @ExcelProperty(value = "账户名称", index = 8)
    @NotEmpty(message = "账户名称不能为空")
    private String bankAccountName;

    @ExcelProperty(value = "银行账号", index = 9)
    @NotEmpty(message = "银行账号不能为空")
    private String bankAccount;

    @ExcelProperty(value = "币种", index = 10)
    private String currencyName;

    @ExcelProperty(value = "主账户", index = 11)
    private String ceeaMainAccount;

    @ExcelProperty(value = "启用", index = 12)
    private String ceeaEnabled;

    @ExcelProperty( value = "错误提示信息",index = 13)
    private String errorMessage;

}
