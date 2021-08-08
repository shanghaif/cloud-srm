package com.midea.cloud.srm.model.supplier.info.dto;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import lombok.Data;

import java.io.Serializable;

/**
 * <pre>
 * 银行信息(供应商导入)
 * </pre>
 *
 * @author wangpr@meiCloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/7/2
 *  修改内容:
 * </pre>
 */
@Data
@ColumnWidth(20)
public class BankInfoDto implements Serializable {

    private static final long serialVersionUID = -1792190421816596013L;
    /**
     * 用户名
     */
    @ExcelProperty( value = "用户名",index = 0)
    private String username;

    /**
     * 组织全路径
     */
    @ExcelProperty( value = "组织全路径",index = 1)
    private String orgName;

    /**
     * 开户行
     */
    @ExcelProperty( value = "开户行",index = 2)
    private String openingBank;

    /**
     * 联行编码
     */
    @ExcelProperty( value = "联行编码",index = 3)
    private String unionCode;

    /**
     * SWIFT CODE
     */
    @ExcelProperty( value = "SWIFT CODE",index = 4)
    private String swiftCode;

    /**
     * 账户名称
     */
    @ExcelProperty( value = "账户名称",index = 5)
    private String bankAccountName;

    /**
     * 银行账号
     */
    @ExcelProperty( value = "银行账号",index = 6)
    private String bankAccount;

    /**
     * 币种名称
     */
    @ExcelProperty( value = "币种名称",index = 7)
    private String currencyName;

    /**
     * 账户类型
     */
    @ExcelProperty( value = "账号类型",index = 8)
    private String accountType;

    /**
     * 错误提示信息
     */
    @ExcelProperty( value = "错误提示信息",index = 9)
    private String errorMsg;

}
