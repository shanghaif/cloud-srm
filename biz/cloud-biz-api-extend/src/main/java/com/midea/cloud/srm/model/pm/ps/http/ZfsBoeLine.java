package com.midea.cloud.srm.model.pm.ps.http;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * <pre>
 *  功能名称   对应 onlineInvoiceDetail
 * </pre>
 *
 * @author yourname@meicloud.com
 * @version 1.00.00
 * <p>
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/9/3 10:39
 *  修改内容:
 * </pre>
 */
@Data
@Accessors(chain = true)
public class ZfsBoeLine {
    private String operationSubTypeCode;
    private String expenseAmount;//不含税金额
    private String useCurrencyCode;
    private String standardCurrencyAmount;
    private String abstracts;
    private String taxCode;
    private String taxAmount;
    private String taxValue;
    private String holdingTax;
    private String reservedField1;
    private String reservedField2;
    private String reservedField3;
    private String reservedField4;
    private String reservedField5;
    private String deptCode;
    private String documentNum;
    private String lineType;
    private String poUnitPriceChangeFlag;
    private String amount;
    private String poPrice;
    private String rateCode;
    private String taxRate;
    private String contract;
    private String projectNumber;
    private String projectName;
    private String taskNumber;
    private String taskName;
    private String poNum;
    private String poLineNum;
    private String poShipmentNum;
    private String poDistributionNum;
    private String consignOrder;
    private String consignLine;
    private String reciptNum;
    private String reciptLineNum;
    private String quantityInvoiced;
    private String unitOfMeas;
    private String unitPrice;
    private String poUnitPrice;
    private String buyerNum;
    private String buyerName;
    private String inventoryItemID;
    private String materialDescription;
    private String description;
    private String productType;
    private String isService;
    private String virtualInvoice;
    private String invoiceLine;
    private String itRelation;
}
