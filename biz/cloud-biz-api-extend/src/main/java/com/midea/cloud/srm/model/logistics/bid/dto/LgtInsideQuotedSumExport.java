package com.midea.cloud.srm.model.logistics.bid.dto;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <pre>
 * 业务类型 - 国内  报价汇总导出模板
 * </pre>
 *
 * @author wangpr@meiCloud.com
 * @version 1.00.00
 *  inside
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-05-27 09:24:20
 *  修改内容:
 * </pre>
 */
@Data
@Accessors(chain = true)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ColumnWidth(15)
public class LgtInsideQuotedSumExport implements Serializable {
    /**
     * 序号、供应商、起始地、目的地、是否往返、费项、计费方式、计费单位、装在量、
     * 单拖成本、运输距离、单公里成本、数量、最小收费、最大收费、币种、费率、总金额、备注
     *
     *
     * 字段转换:
     * 是否往返: Y/N
     * 币制:
     * 费项: CHARGE_NAME
     * 计费方式: CHARGE_LEVEL
     * 计费单位: SUB_LEVEL
     * 贸易术语: TRADE_TERM
     * 整柜/拼柜: FCL/LCL
     * LEG: LEG
     * 进出口方式: EXP/IMP
     */
    @ExcelProperty( value = "序号",index = 0)
    private Integer no;

    @ExcelProperty( value = "供应商",index = 1)
    private String vendorName;

    @ExcelProperty( value = "起始地",index = 2)
    private String startAddress;

    @ExcelProperty( value = "目的地",index = 3)
    private String endAddress;

    @ExcelProperty( value = "是否往返",index = 4)
    private String ifBack;

    @ExcelProperty( value = "费项",index = 5)
    private String expenseItem;

    @ExcelProperty( value = "计费方式",index = 6)
    private String chargeMethod;

    @ExcelProperty( value = "计费单位",index = 7)
    private String chargeUnit;

    @ExcelProperty( value = "装载量",index = 8)
    private BigDecimal loadNumber;

    @ExcelProperty( value = "单拖成本",index = 9)
    private BigDecimal singleDragCost;

    @ExcelProperty( value = "运输距离",index = 10)
    private String transportDistanceRevision;

    @ExcelProperty( value = "单公里成本",index = 11)
    private BigDecimal singleKmCost;

    @ExcelProperty( value = "数量",index = 12)
    private BigDecimal number;

    @ExcelProperty( value = "最小收费",index = 13)
    private String minCost;

    @ExcelProperty( value = "最大收费",index = 14)
    private String maxCost;

    @ExcelProperty( value = "币制",index = 15)
    private String currency;

    @ExcelProperty( value = "费率",index = 16)
    private BigDecimal expense;

    @ExcelProperty( value = "总金额",index = 17)
    private BigDecimal totalAmount;

    @ExcelProperty( value = "备注",index = 18)
    private String comments;

}
