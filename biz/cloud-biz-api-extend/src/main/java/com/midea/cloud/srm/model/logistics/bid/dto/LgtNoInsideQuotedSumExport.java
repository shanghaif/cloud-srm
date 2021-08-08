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
public class LgtNoInsideQuotedSumExport implements Serializable {
    /**
     * 序号、供应商、起始地、目的地、贸易术语、进出口方式、整柜/拼柜、LEG、费项、计费方式、计费单位、最小费用、最大费用、
     * 数量、费率、币种、时效、船期频率、免堆期、免箱期、超期堆存费、超期用箱费、备注。
     *
     *      * 字段转换:
     *      * 是否往返: Y/N
     *      * 币制:
     *      * 费项: CHARGE_NAME
     *      * 计费方式: CHARGE_LEVEL
     *      * 计费单位: SUB_LEVEL
     *      * 贸易术语: TRADE_TERM
     *      * 整柜/拼柜: FCL/LCL
     *      * LEG: LEG
     *      * 进出口方式: EXP/IMP
     */
    @ExcelProperty( value = "序号",index = 0)
    private Integer no;

    @ExcelProperty( value = "供应商",index = 1)
    private String vendorName;

    @ExcelProperty( value = "起始地",index = 2)
    private String startAddress;

    @ExcelProperty( value = "目的地",index = 3)
    private String endAddress;

    @ExcelProperty( value = "贸易术语",index = 4)
    private String tradeTerm;

    @ExcelProperty( value = "进出口方式",index = 5)
    private String importExportMethod;

    @ExcelProperty( value = "整柜/拼柜",index = 6)
    private String wholeArk;

    @ExcelProperty( value = "LEG",index = 7)
    private String leg;

    @ExcelProperty( value = "费项",index = 8)
    private String expenseItem;

    @ExcelProperty( value = "计费方式",index = 9)
    private String chargeMethod;

    @ExcelProperty( value = "计费单位",index = 10)
    private String chargeUnit;

    @ExcelProperty( value = "最小收费",index = 11)
    private String minCost;

    @ExcelProperty( value = "最大收费",index = 12)
    private String maxCost;

    @ExcelProperty( value = "数量",index = 13)
    private BigDecimal number;

    @ExcelProperty( value = "费率",index = 14)
    private BigDecimal expense;

    @ExcelProperty( value = "币制",index = 15)
    private String currency;

    @ExcelProperty( value = "时效",index = 16)
    private String realTime;

    @ExcelProperty( value = "船期频率",index = 17)
    private String shipDateFrequency;

    @ExcelProperty( value = "免堆期",index = 18)
    private String freeStoragePeriod;

    @ExcelProperty( value = "免箱期",index = 19)
    private String freeBoxPeriod;

    @ExcelProperty( value = "超期堆存费",index = 20)
    private BigDecimal beyondStorageCost;

    @ExcelProperty( value = "超期用箱费",index = 21)
    private BigDecimal beyondBoxCost;

    @ExcelProperty( value = "备注",index = 22)
    private String comments;

}
