package com.midea.cloud.srm.model.base.orgcompany.dto;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import lombok.Data;

import java.io.Serializable;

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
 *  修改日期: 2020/10/23
 *  修改内容:
 * </pre>
 */
@ColumnWidth(20)
@Data
public class CompanyBankDto implements Serializable {
    private static final long serialVersionUID = -2926444956866434339L;
    /**
     * 公司名称
     */
    @ExcelProperty(value = "公司名称", index = 0)
    private String companyName;

    /**
     * 银行编号
     */
    @ExcelProperty(value = "银行编号", index = 1)
    private String bankNum;

    /**
     * 银行名称
     */
    @ExcelProperty(value = "银行名称", index = 2)
    private String bankName;

    /**
     * 开户行名称
     */
    @ExcelProperty(value = "开户行名称", index = 3)
    private String branchBankName;

    /**
     * 账户名称
     */
    @ExcelProperty(value = "账户名称", index = 4)
    private String accountName;

    /**
     * 银行账号
     */
    @ExcelProperty(value = "银行账号", index = 5)
    private String bankAccount;

    /**
     * 是否主账号,只能有一个主账号(Y-是,N-否)
     */
    @ExcelProperty(value = "是否主账号", index = 6)
    private String isMain;

    /**
     * 是否激活(Y-是,N-否)
     */
    @ExcelProperty(value = "是否启用", index = 7)
    private String isActive;

    /**
     * 错误信息提示
     */
    @ExcelProperty(value = "错误信息提示", index = 8)
    private String errorMsg;
}
