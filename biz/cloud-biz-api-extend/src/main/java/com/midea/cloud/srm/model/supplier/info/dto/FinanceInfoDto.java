package com.midea.cloud.srm.model.supplier.info.dto;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import lombok.Data;

import java.io.Serializable;

/**
 * <pre>
 * 财务信息(供应商导入)
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
public class FinanceInfoDto implements Serializable {

    private static final long serialVersionUID = 7655730506699669712L;
    /**
     * 用户名
     */
    @ExcelProperty( value = "用户名",index = 0)
    private String username;

    /**
     * 合作组织名称
     */
    @ExcelProperty( value = "组织全路径",index = 1)
    private String orgName;

    /**
     * 结算币种
     */
    @ExcelProperty( value = "结算币种",index = 2)
    private String clearCurrency;

    /**
     * 付款方式
     */
    @ExcelProperty( value = "付款方式",index = 3)
    private String paymentMethod;

    /**
     * 付款条件
     */
    @ExcelProperty( value = "付款条件",index = 4)
    private String paymentTerms;

    /**
     * 税率编码
     */
    @ExcelProperty( value = "税率编码",index = 5)
    private String taxKey;

    /**
     * 发票限额(单位:万元)
     */
    @ExcelProperty( value = "发票限额(单位:万元)",index = 6)
    private String limitAmount;

    /**
     * 错误提示信息
     */
    @ExcelProperty( value = "错误提示信息",index = 7)
    private String errorMsg;

}
