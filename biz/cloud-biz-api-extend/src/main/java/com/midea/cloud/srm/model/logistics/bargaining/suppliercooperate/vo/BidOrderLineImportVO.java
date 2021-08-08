package com.midea.cloud.srm.model.logistics.bargaining.suppliercooperate.vo;

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
 *  修改日期: 2020/9/29
 *  修改内容:
 * </pre>
 */
@Data
@ColumnWidth(20)
public class BidOrderLineImportVO implements Serializable {
    private static final long serialVersionUID = -1513962654034456218L;

    @ExcelProperty( value = "行唯一标识",index = 0)
    private String orderLineId;

    @ExcelProperty( value = "ou组名",index = 1)
    private String ouName;

    @ExcelProperty( value = "是否为基准ou",index = 2)
    private String baseOu;

    @ExcelProperty( value = "业务实体",index = 3)
    private String orgName;

    @ExcelProperty( value = "库存组织",index = 4)
    private String invName;

    @ExcelProperty( value = "交货地点",index = 5)
    private String deliveryPlace;

    @ExcelProperty( value = "贸易条款",index = 6)
    private String tradeTerm;

    @ExcelProperty( value = "运输方式",index = 7)
    private String transportType;

    @ExcelProperty( value = "供货周期",index = 8)
    private String leadTime;

    @ExcelProperty( value = "保修期",index = 9)
    private String warrantyPeriod;

    @ExcelProperty( value = "采购类型",index = 10)
    private String purchaseType;

    @ExcelProperty( value = "报价币种",index = 11)
    private String currencyType;

    @ExcelProperty( value = "最小订单量",index = 12)
    private String MQO;

    @ExcelProperty( value = "付款条款",index = 13)
    private String paymentTerm;

    @ExcelProperty( value = "物料编码",index = 14)
    private String targetNum;

    @ExcelProperty( value = "物料名称",index = 15)
    private String targetDesc;

    @ExcelProperty( value = "采购数量",index = 16)
    private String quantity;

    @ExcelProperty( value = "单位",index = 17)
    private String uomDesc;

    @ExcelProperty( value = "含税报价",index = 18)
    private String price;

    @ExcelProperty( value = "税率(%)",index = 19)
    private String taxRate;

    @ExcelProperty( value = "承诺交货期",index = 20)
    private String deliverDate;

    @ExcelProperty( value = "定价开始时间",index = 21)
    private String priceStartTime;

    @ExcelProperty( value = "定价结束时间",index = 22)
    private String priceEndTime;

    @ExcelProperty( value = "备注",index = 23)
    private String commnets;

    @ExcelProperty( value = "错误信息提示",index = 24)
    private String errorMsg;
}
