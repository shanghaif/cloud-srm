package com.midea.cloud.srm.model.logistics.pr.requirement.dto;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <pre>
 *  功能名称
 * </pre>
 *
 * @author chenwj92@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人: chenwj92
 *  修改日期: 2021/1/20 17:47
 *  修改内容:
 * </pre>
 */
@Data
@ColumnWidth(20)
public class LogisticsRequirementLineExcelDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 起运地
     */
    @ExcelProperty(value = "起运地",index = 0)
    private String fromPlace;

    /**
     * 始发省
     */
    @ExcelProperty(value = "始发省",index = 1)
    private String fromProvince;

    /**
     * 始发市
     */
    @ExcelProperty(value = "始发市",index = 2)
    private String fromCity;

    /**
     * 始发区县
     */
    @ExcelProperty(value = "始发区县",index = 3)
    private String fromCounty;

    /**
     * 目的地
     */
    @ExcelProperty(value = "目的地",index = 4)
    private String toPlace;

    /**
     * 目的省
     */
    @ExcelProperty(value = "目的省",index = 5)
    private String toProvince;


    /**
     * 目的市
     */
    @ExcelProperty(value = "目的市",index = 6)
    private String toCity;

    /**
     * 目的区县
     */
    @ExcelProperty(value = "目的区县",index = 7)
    private String toCounty;

    /**
     * 是否往返
     */
    @ExcelProperty(value = "是否往返",index = 8)
    private String ifBack;

    /**
     * 最小收费
     */
    @ExcelProperty(value = "最小收费",index = 9)
    private String minCost;

    /**
     * 最大收费
     */
    @ExcelProperty(value = "最大收费",index = 10)
    private String maxCost;

    /**
     * 币制
     */
    @ExcelProperty(value = "币制",index = 11)
    private String currency;

    /**
     * 运输距离
     */
    @ExcelProperty(value = "运输距离",index = 12)
    private String transportDistance;

    /**
     * 备注
     */
    @ExcelProperty(value = "备注",index = 13)
    private String comments;

    /**
     * 时效
     */
    @ExcelProperty(value = "时效",index = 14)
    private String realTime;

    /**
     * 起运港编码
     */
    @ExcelProperty(value = "起运港编码",index = 15)
    private String fromPort;

    /**
     * 目的港编码
     */
    @ExcelProperty(value = "目的港编码",index = 16)
    private String toPort;

    /**
     * 提前预定时间
     */
    @ExcelProperty(value = "提前预定时间",index = 17)
    private String reserveTime;

    /**
     * 全程时效
     */
    @ExcelProperty(value = "全程时效",index = 18)
    private String fullRealTime;

    /**
     * 船期
     */
    @ExcelProperty(value = "船期",index = 19)
    private String shipDate;

    /**
     * 免费存放期
     */
    @ExcelProperty(value = "免费存放期",index = 20)
    private String freeStayPeriod;

    /**
     * 费项
     */
    @ExcelProperty(value = "费项",index = 21)
    private String expenseItem;

    /**
     * 计费方式
     */
    @ExcelProperty(value = "计费方式",index = 22)
    private String chargeMethod;

    /**
     * 计费单位
     */
    @ExcelProperty(value = "计费单位",index = 23)
    private String chargeUnit;

    /**
     * 费用
     */
    @ExcelProperty(value = "费用",index = 24)
    private BigDecimal expense;

    /**
     * 指定供应商
     */
    @ExcelProperty(value = "指定供应商",index = 25)
    private String specifiedVendor;

    /**
     * 贸易术语
     */
    @ExcelProperty(value = "贸易术语",index = 26)
    private String tradeTerm;

    /**
     * 整柜/拼柜
     */
    @ExcelProperty(value = "整柜/拼柜",index = 27)
    private String wholeArk;

    /**
     * 船期频率
     */
    @ExcelProperty(value = "船期频率",index = 28)
    private String shipDateFrequency;

    /**
     * 免堆期
     */
    @ExcelProperty(value = "免堆期",index = 29)
    private String freeStoragePeriod;

    /**
     * 免箱期
     */
    @ExcelProperty(value = "免箱期",index = 30)
    private String freeBoxPeriod;

    /**
     * 超期堆存费
     */
    @ExcelProperty(value = "超期堆存费",index = 31)
    private BigDecimal beyondStorageCost;

    /**
     * 超期用箱费
     */
    @ExcelProperty(value = "超期用箱费",index = 32)
    private BigDecimal beyondBoxCost;

    /**
     * LEG
     */
    @ExcelProperty(value = "LEG",index = 33)
    private String leg;

    /**
     * 进出口方式
     */
    @ExcelProperty(value = "进出口方式",index = 34)
    private String importExportMethod;

    /**
     * 单拖成本
     */
    @ExcelProperty(value = "单拖成本",index = 35)
    private BigDecimal singleDragCost;

    /**
     * 单公里成本
     */
    @ExcelProperty(value = "单公里成本",index = 36)
    private BigDecimal singleKmCost;

    /**
     * 起运国
     */
    @ExcelProperty(value = "起运国",index = 37)
    private String fromCountry;

    /**
     * 目的国
     */
    @ExcelProperty(value = "目的国",index = 38)
    private String toCountry;

    /**
     * 物流品类名称
     */
    @ExcelProperty(value = "物流品类名称",index = 39)
    private String logisticsCategoryName;

    /**
     * 总金额
     */
    @ExcelProperty(value = "总金额",index = 40)
    private BigDecimal totalAmount;

    /**
     * 备用字段1
     */
    @ExcelProperty(value = "备用字段1",index = 41)
    private String attr1;

    /**
     * 备用字段2
     */
    @ExcelProperty(value = "备用字段2",index = 42)
    private String attr2;

    /**
     * 备用字段3
     */
    @ExcelProperty(value = "备用字段3",index = 43)
    private String attr3;

    /**
     * 备用字段4
     */
    @ExcelProperty(value = "备用字段4",index = 44)
    private String attr4;

    /**
     * 备用字段5
     */
    @ExcelProperty(value = "备用字段5",index = 45)
    private String attr5;

    /**
     * 备用字段6
     */
    @ExcelProperty(value = "备用字段6",index = 46)
    private String attr6;

    /**
     * 备用字段7
     */
    @ExcelProperty(value = "备用字段7",index = 47)
    private String attr7;

    /**
     * 备用字段8
     */
    @ExcelProperty(value = "备用字段8",index = 48)
    private String attr8;

    /**
     * 备用字段9
     */
    @ExcelProperty(value = "备用字段9",index = 49)
    private String attr9;

    /**
     * 备用字段10
     */
    @ExcelProperty(value = "备用字段10",index = 50)
    private String attr10;


}
