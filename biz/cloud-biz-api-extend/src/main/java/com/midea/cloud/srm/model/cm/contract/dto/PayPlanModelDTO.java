package com.midea.cloud.srm.model.cm.contract.dto;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import lombok.Data;

import java.io.Serializable;

/**
 * <pre>
 *  合同付款计划导入模型
 * </pre>
 *
 * @author fansb3@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/10/13 15:58
 *  修改内容:
 * </pre>
 */
@Data
@ColumnWidth(20)
public class PayPlanModelDTO implements Serializable {

    @ExcelProperty( value = "合同序列号",index = 0)
    private String contractNo;

    @ExcelProperty( value = "合同编号",index = 1)
    private String contractCode;

    @ExcelProperty( value = "*合同名称",index = 2)
    private String contractName;

    @ExcelProperty( value = "*付款期数",index = 3)
    private String paymentPeriod;

    @ExcelProperty( value = "*付款阶段",index = 4)
    private String paymentStage;

    @ExcelProperty( value = "*付款条件",index = 5)
    private String payExplain;

    @ExcelProperty( value = "*付款账期",index = 6)
    private String dateNum;

    @ExcelProperty( value = "*付款比例",index = 7)
    private String paymentRatio;

    @ExcelProperty( value = "*付款方式",index = 8)
    private String payMethod;

    @ExcelProperty( value = "阶段付款金额",index = 9)
    private String stagePaymentAmount;

    @ExcelProperty( value = "计划还款日期",index = 10)
    private String plannedPaymentDate;

    @ExcelProperty( value = "已付金额",index = 11)
    private String paidAmount;

    @ExcelProperty( value = "错误提示信息",index = 12)
    private String errorMessage;
}
