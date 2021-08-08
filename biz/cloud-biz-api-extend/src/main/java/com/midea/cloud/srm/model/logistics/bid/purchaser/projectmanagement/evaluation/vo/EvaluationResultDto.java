package com.midea.cloud.srm.model.logistics.bid.purchaser.projectmanagement.evaluation.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

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
 *  修改日期: 2020/9/28
 *  修改内容:
 * </pre>
 */
@Data
@ColumnWidth(20)
public class EvaluationResultDto implements Serializable {
    private static final long serialVersionUID = 1L;

    // Long 转 String
    @ExcelProperty( value = "行唯一标识",index = 0)
    private String orderLineId;

    // Y - 入围
    @ExcelProperty( value = "下轮允许招标",index = 1)
    private String win;

    @ExcelProperty( value = "业务实体",index = 2)
    private String orgName;

    @ExcelProperty( value = "库存组织",index = 3)
    private String invName;

    @ExcelProperty( value = "OU组(编码)",index = 4)
    private String ouNumber;

    @ExcelProperty( value = "OU组",index = 5)
    private String ouName;

    // 查字典
    @ExcelProperty( value = "价格类型",index = 6)
    private String priceType;

    @ExcelProperty( value = "物料编码",index = 7)
    private String 		targetNum;

    @ExcelProperty( value = "物料名称",index = 8)
    private String 		targetDesc;

    @ExcelProperty( value = "小类",index = 9)
    private String 		categoryName;

    @ExcelProperty( value = "需求数量",index = 10)
    private Double 		quantity;

    @ExcelProperty( value = "公式明细",index = 11)
    private String		pricingFormulaValue;

    // 把key和值拼起来(L3/3)
    @ExcelProperty( value = "税率",index = 12)
    private BigDecimal	taxRate;

    @ExcelProperty( value = "单位",index = 13)
    private String 		uomDesc;

    @ExcelProperty( value = "本轮最低价",index = 14)
    private BigDecimal currentRoundMinPrice;

    @ExcelProperty( value = "价格生效自",index = 15)
    private Date priceStartTime;

    @ExcelProperty( value = "价格生效至",index = 16)
    private Date priceEndTime;

    @ExcelProperty( value = "招标供应商",index = 17)
    private String vendorName;

    @ExcelProperty( value = "招标供应商编码",index = 18)
    private String vendorCode;

    @ExcelProperty( value = "原币含税价",index = 19)
    private BigDecimal 	price;

    @ExcelProperty( value = "原币未税价",index = 20)
    private BigDecimal	noTaxPrice;

    @ExcelProperty( value = "原币币种",index = 21)
    private String	currencyName;

    @ExcelProperty( value = "本币含税价",index = 22)
    private BigDecimal	standardCurrencyPrice;

    @ExcelProperty( value = "本币未税价",index = 23)
    private BigDecimal	standardCurrencyNoTaxPrice;

    @ExcelProperty( value = "本币币种",index = 24)
    private String		standardCurrency;

    @ExcelProperty( value = "MQO",index = 25)
    private String MQO;

    @ExcelProperty( value = "汇率",index = 26)
    private BigDecimal	priceTax;

    @ExcelProperty( value = "折息价",index = 27)
    private BigDecimal 	discountPrice;

    @ExcelProperty( value = "评分总金额",index = 28)
    private BigDecimal 	totalAmount;

    @ExcelProperty( value = "配额分配类型",index = 29)
    private String		quotaDistributeType;

    @ExcelProperty( value = "配额比例(%)",index = 30)
    private BigDecimal	quotaRatio;

    // 配额数量
    @ExcelProperty( value = "中标数量",index = 31)
    private BigDecimal quotaQuantity;

    @ExcelProperty( value = "贸易条款",index = 32)
    private String 		tradeTerm;

    @ExcelProperty( value = "付款条款",index = 33)
    private String paymentTermsTemp;

    @ExcelProperty( value = "价格得分",index = 34)
    private BigDecimal 	priceScore;

    @ExcelProperty( value = "保修期",index = 35)
    private Integer 	warrantyPeriod;

    @ExcelProperty( value = "承诺交货期",index = 36)
    private Date 		deliverDate;

    @ExcelProperty( value = "供货周期",index = 37)
    private String 	    leadTime;

    @ExcelProperty( value = "是否创建寻源结果审批单",index = 38)
    private String		isCreateSourcingResult;

    @ExcelProperty( value = "采购申请号",index = 39)
    private String		purchaseRequestNum;

    @ExcelProperty( value = "采购申请行号",index = 40)
    private String		purchaseRequestRowNum;

    @ExcelProperty( value = "是否代理报价",index = 41)
    private String		isProxyBidding;

    @ExcelProperty( value = "技术得分",index = 42)
    private BigDecimal 	techScore;

    @ExcelProperty( value = "绩效得分",index = 43)
    private BigDecimal 	perfScore;

    @ExcelProperty( value = "综合得分",index = 44)
    private BigDecimal 	compositeScore;

    @ExcelProperty( value = "排名",index = 45)
    private Integer 	rank;

    @ExcelProperty( value = "评选结果",index = 46)
    private String 		selectionStatus;

    @ExcelProperty( value = "是否跟标",index = 47)
    private String 		withStandard;

    @ExcelProperty( value = "预计采购金额(万元)",index = 48)
    private BigDecimal 	amount;

    @ExcelProperty( value = "序号",index = 49)
    private Integer no;

    @ExcelProperty( value = "错误信息提示",index = 50)
    private String 	errorMsg;
}
