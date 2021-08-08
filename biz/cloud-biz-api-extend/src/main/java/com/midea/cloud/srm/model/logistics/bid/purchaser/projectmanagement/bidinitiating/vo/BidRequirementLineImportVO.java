package com.midea.cloud.srm.model.logistics.bid.purchaser.projectmanagement.bidinitiating.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.NumberFormat;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * <pre>
 *  项目需求行导入
 * </pre>
 *
 * @author fengdc3@midea.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/4/23 15:10
 *  修改内容:
 * </pre>
 */
@Data
public class BidRequirementLineImportVO {

    @NumberFormat
    @ExcelProperty(value = "需求行ID")
    private Long requirementLineId;

    @NumberFormat
    @ExcelProperty(value = "招标需求ID")
    private Long requirementId;

    @NumberFormat
    @ExcelProperty(value = "招标ID")
    private Long bidingId;

    @ExcelProperty(value = "标的编码")
    private String targetNum;

    @ExcelProperty(value = "标的描述")
    private String targetDesc;

    @ExcelProperty(value = "税率编码")
    private String taxKey;

    @ExcelProperty(value = "含税现价")
    private BigDecimal taxCurrentPrice;


    @ExcelProperty(value = "事业部")
    private String orgName;


    @ExcelProperty(value = "采购分类")
    private String categoryName;


    @ExcelProperty(value = "物料组(组合)")
    private String itemGroup;


    @ExcelProperty(value = "预计采购数量")
    private Double quantity;


    @ExcelProperty(value = "预计采购金额(万元)")
    private BigDecimal amount;

    @ExcelProperty(value = "商务第一轮保留供应商数量")
    private Integer limitRound1;


    @ExcelProperty(value = "商务第二轮保留供应商数量")
    private Integer limitRound2;


    @ExcelProperty(value = "商务第三轮保留供应商数量")
    private Integer limitRound3;


    @ExcelProperty(value = "商务第四轮保留供应商数量")
    private Integer limitRound4;


    @ExcelProperty(value = "商务第五轮保留供应商数量")
    private Integer limitRound5;

    @ExcelProperty(value = "单位编码/名称对照")
    private String uomSpinner;

    @ExcelProperty(value = "单位编码")
    private String uomCode;

    @ExcelProperty(value = "单位描述")
    private String uomDesc;

    @ExcelProperty(value = "定价开始时间")
    private Date priceStartTime;

    @ExcelProperty(value = "定价结束时间")
    private Date priceEndTime;

    @ExcelProperty(value = "行类型")
    private String rowType;

    @ExcelProperty(value = "备注")
    private String comments;

}