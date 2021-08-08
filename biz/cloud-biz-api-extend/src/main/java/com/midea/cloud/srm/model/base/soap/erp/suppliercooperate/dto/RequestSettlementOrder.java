
package com.midea.cloud.srm.model.base.soap.erp.suppliercooperate.dto;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.*;
import java.util.List;

/**
 * <pre>
 *  结算订单报文
 * </pre>
 *
 * @author chenwj92@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人: chenwj92
 *  修改日期: 2020/11/1 16:51
 *  修改内容:
 * </pre>
 */

/**
 * <?xml version="1.0" encoding="gb2312"?>
 *
 * <soap-env:Envelope xmlns:soap-env="http://schemas.xmlsoap.org/soap/envelope/">
 *   <Header xmlns="http://schemas.xmlsoap.org/soap/envelope/"></Header>
 *   <soapenv:Body xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/">
 *     <ns2:REQUEST xmlns:ns2="http://www.aurora-framework.org/schema">
 *       <ns2:esbInfo>
 *         <ns2:instId>a00015d.7eed2c0d.19.1756e3cff0d.N4974</ns2:instId>
 *         <ns2:requestTime>2020-10-28 20:05:54.716</ns2:requestTime>
 *         <ns2:attr1/>
 *         <ns2:attr2/>
 *         <ns2:attr3/>
 *       </ns2:esbInfo>
 *       <ns2:requestInfo>
 *         <ns2:HEADER>
 *           <ns2:BUSINESS_GROUP>BG0000101</ns2:BUSINESS_GROUP>
 *           <ns2:SYSTEM_CODE>EBS_LONGI</ns2:SYSTEM_CODE>  //来源系统编号
 *           <ns2:REQUEST_ID>1</ns2:REQUEST_ID>
 *           <ns2:IF_CATE_CODE>PUR_PO</ns2:IF_CATE_CODE> //固定值
 *           <ns2:IF_CODE>PUR_PO_APPROVED</ns2:IF_CODE>//固定值
 *           <ns2:USER_NAME>86798847</ns2:USER_NAME> // 固定值，创建人
 *           <ns2:PASSWORD>0FBCECB6D99840D174E765F3CC7FEEE7</ns2:PASSWORD>
 *           <ns2:BATCH_NUM>1</ns2:BATCH_NUM> //固定值
 *           <ns2:TOTAL_SEG_COUNT>1</ns2:TOTAL_SEG_COUNT>
 *           <ns2:SEG_NUM>1</ns2:SEG_NUM>
 *         </ns2:HEADER>
 *         <ns2:CONTEXT>
 *           <ns2:PO_HEADER>
 *             <ns2:RECORD>
 *               <ns2:PO_HEADER_ID/>
 *               <ns2:PO_NUMBER>1020060313</ns2:PO_NUMBER> //采购订单号
 *               <ns2:AGENT_ID>65434</ns2:AGENT_ID> //ERP采购员ID，默认也为创建人
 *               <ns2:AGENT_CODE/>
 *               <ns2:AGENT_NAME>王旭</ns2:AGENT_NAME> //ERP采购员名称，默认也为创建人
 *               <ns2:BUSINESS_UNIT_ID>81</ns2:BUSINESS_UNIT_ID> //ERP业务实体ID
 *               <ns2:BUSINESS_UNIT_CODE>10_OU_西安隆基</ns2:BUSINESS_UNIT_CODE> //ERP业务实体编码
 *               <ns2:PO_TYPE>STANDARD</ns2:PO_TYPE> //固定值
 *               <ns2:PO_ORDER_TYPE>PO19JS</ns2:PO_ORDER_TYPE> //固定值，采购订单类型
 *               <ns2:VENDOR_ID>190</ns2:VENDOR_ID> //ERP供应商ID
 *               <ns2:VENDOR_CODE>10190</ns2:VENDOR_CODE>//ERP供应商code
 *               <ns2:VENDOR_SITE_ID>31</ns2:VENDOR_SITE_ID> //ERP供应商地点ID
 *               <ns2:VENDOR_SITE_CODE>材料</ns2:VENDOR_SITE_CODE>//ERP供应商地点code
 *               <ns2:VENDOR_CONTACT_ID/>//ERP供应商联系人ID
 *               <ns2:VENDOR_CONTACT_CODE/>
 *               <ns2:VENDOR_CONTACT_NAME/>//ERP供应商联系人名称
 *               <ns2:SHIP_TO_LOCATION_ID/>
 *               <ns2:SHIP_TO_LOCATION_CODE>西安</ns2:SHIP_TO_LOCATION_CODE>
 *               <ns2:SHIP_TO_LOCATION_ADDRESS/>
 *               <ns2:BILL_TO_LOCATION_ID/>
 *               <ns2:BILL_TO_LOCATION_CODE>西安</ns2:BILL_TO_LOCATION_CODE> //ERP收单方
 *               <ns2:CONTRACT_NUMBER>LGi·X-Pur-2009-107-A/001-SRM</ns2:CONTRACT_NUMBER> //采购合同号
 *               <ns2:SHIP_VIA_LOOKUP_CODE/>
 *               <ns2:FOB_LOOKUP_CODE/>
 *               <ns2:FREIGHT_TERMS_LOOKUP_CODE/>
 *               <ns2:PO_STATUS>APPROVED</ns2:PO_STATUS>//订单类型
 *               <ns2:CURRENCY_CODE>CNY</ns2:CURRENCY_CODE>//币种编号
 *               <ns2:RATE_TYPE/>
 *               <ns2:RATE_DATE/>
 *               <ns2:RATE/>
 *               <ns2:START_DATE/>
 *               <ns2:END_DATE/>
 *               <ns2:BLANKET_TOTAL_AMOUNT/>
 *               <ns2:REVISION_NUM/>
 *               <ns2:APPROVED_DATE/>
 *               <ns2:MIN_RELEASE_AMOUNT/>
 *               <ns2:NOTE_TO_AUTHORIZER/>
 *               <ns2:NOTE_TO_VENDOR/>
 *               <ns2:NOTE_TO_RECEIVER/>
 *               <ns2:PRINT_COUNT/>
 *               <ns2:PRINTED_DATE/>
 *               <ns2:CONTRACTOR/>
 *               <ns2:CONTRACTOR_PHONE/>
 *               <ns2:CONTRACTOR_FAX/>
 *               <ns2:QUALITY_AGREEMENT_NUMBER/>
 *               <ns2:TECHNOLOGY_AGREEMENT_NUMBER/>
 *               <ns2:NEGOTIATION_NUMBER/>
 *               <ns2:VENDOR_ORDER_NUM/>
 *               <ns2:CONFIRMING_ORDER_FLAG/>
 *               <ns2:REPLY_DATE/>
 *               <ns2:REPLY_METHOD_LOOKUP_CODE/>
 *               <ns2:RFQ_CLOSE_DATE/>
 *               <ns2:QUOTE_TYPE_LOOKUP_CODE/>
 *               <ns2:QUOTATION_CLASS_CODE/>
 *               <ns2:QUOTE_WARNING_DELAY_UNIT/>
 *               <ns2:QUOTE_WARNING_DELAY/>
 *               <ns2:QUOTE_VENDOR_QUOTE_NUMBER/>
 *               <ns2:ACCEPTANCE_REQUIRED_FLAG/>
 *               <ns2:ACCEPTANCE_DUE_DATE/>
 *               <ns2:CLOSED_DATE/>
 *               <ns2:USER_HOLD_FLAG/>
 *               <ns2:APPROVAL_REQUIRED_FLAG/>
 *               <ns2:CANCEL_FLAG/>
 *               <ns2:FIRM_STATUS_LOOKUP_CODE/>
 *               <ns2:FIRM_DATE/>
 *               <ns2:FROZEN_FLAG/>
 *               <ns2:CLOSED_CODE/>
 *               <ns2:USSGL_TRANSACTION_CODE/>
 *               <ns2:SUPPLY_AGREEMENT_FLAG/>
 *               <ns2:EDI_PROCESSED_FLAG/>
 *               <ns2:EDI_PROCESSED_STATUS/>
 *               <ns2:PO_RELEASE_ID/>
 *               <ns2:PO_RELEASE_NUM/>
 *               <ns2:ERP_PO_TYPE/>
 *               <ns2:ERP_PO_TYPE_DESC/>
 *               <ns2:ERP_CREATION_DATE/>
 *               <ns2:ERP_LAST_UPDATE_DATE/>
 *               <ns2:COMMENTS>自动创建寄售采购订单,调拨订单为:20200001093;对应的寄售订单为:202010090328</ns2:COMMENTS>
 *               <ns2:OTHER_COMMENTS/>
 *               <ns2:TAX_RATE>13</ns2:TAX_RATE>// 税率
 *               <ns2:PAYMENT_METHOD/>// 付款方式
 *               <ns2:PAYMENT_TERM>立即付款</ns2:PAYMENT_TERM> //付款条件（SRM付款账期）
 *               <ns2:MANUFACTURER_CODE/>
 *               <ns2:MANUFACTURER_NAME/>
 *               <ns2:C_ATTRIBUTE1/>
 *               <ns2:C_ATTRIBUTE2/>
 *               <ns2:C_ATTRIBUTE3/>
 *               <ns2:C_ATTRIBUTE4/>
 *               <ns2:C_ATTRIBUTE5/>
 *               <ns2:C_ATTRIBUTE6/>
 *               <ns2:C_ATTRIBUTE7/>
 *               <ns2:C_ATTRIBUTE8/>
 *               <ns2:C_ATTRIBUTE9/>
 *               <ns2:C_ATTRIBUTE10/>
 *               <ns2:C_ATTRIBUTE11/>
 *               <ns2:C_ATTRIBUTE12/>
 *               <ns2:C_ATTRIBUTE13/>
 *               <ns2:C_ATTRIBUTE14/>
 *               <ns2:C_ATTRIBUTE15/>
 *               <ns2:C_ATTRIBUTE16/>
 *               <ns2:C_ATTRIBUTE17/>
 *               <ns2:C_ATTRIBUTE18/>
 *               <ns2:C_ATTRIBUTE19/>
 *               <ns2:C_ATTRIBUTE20/>
 *               <ns2:C_ATTRIBUTE21/>
 *               <ns2:C_ATTRIBUTE22/>
 *               <ns2:C_ATTRIBUTE23/>
 *               <ns2:C_ATTRIBUTE24/>
 *               <ns2:C_ATTRIBUTE25/>
 *               <ns2:C_ATTRIBUTE26/>
 *               <ns2:C_ATTRIBUTE27/>
 *               <ns2:C_ATTRIBUTE28/>
 *               <ns2:C_ATTRIBUTE29/>
 *               <ns2:C_ATTRIBUTE30/>
 *               <ns2:C_ATTRIBUTE31/>
 *               <ns2:C_ATTRIBUTE32/>
 *               <ns2:C_ATTRIBUTE33/>
 *               <ns2:C_ATTRIBUTE34/>
 *               <ns2:C_ATTRIBUTE35/>
 *               <ns2:C_ATTRIBUTE36/>
 *               <ns2:C_ATTRIBUTE37/>
 *               <ns2:C_ATTRIBUTE38/>
 *               <ns2:C_ATTRIBUTE39/>
 *               <ns2:C_ATTRIBUTE40/>
 *               <ns2:C_ATTRIBUTE41/>
 *               <ns2:C_ATTRIBUTE42/>
 *               <ns2:C_ATTRIBUTE43/>
 *               <ns2:C_ATTRIBUTE44/>
 *               <ns2:C_ATTRIBUTE45/>
 *               <ns2:C_ATTRIBUTE46/>
 *               <ns2:C_ATTRIBUTE47/>
 *               <ns2:C_ATTRIBUTE48/>
 *               <ns2:C_ATTRIBUTE49/>
 *               <ns2:C_ATTRIBUTE50/>
 *               <ns2:PO_LINE>
 *                 <ns2:RECODR>
 *                   <ns2:PO_LINE_ID/>
 *                   <ns2:PO_HEADER_ID/>
 *                   <ns2:LINE_NUM>2</ns2:LINE_NUM>
 *                   <ns2:UNIT_PRICE>4.42477876</ns2:UNIT_PRICE> //不含税单价
 *                   <ns2:TAX_PRICE>4.9999999988</ns2:TAX_PRICE> //含税单价
 *                   <ns2:USER_HOLD_FLAG/>
 *                   <ns2:UN_NUMBER_ID/>
 *                   <ns2:UNORDERED_FLAG/>
 *                   <ns2:UNIT_MEAS_LOOKUP_CODE>PCS</ns2:UNIT_MEAS_LOOKUP_CODE> //单位
 *                   <ns2:TRANSACTION_REASON_CODE/>
 *                   <ns2:TAX_NAME>IN 13</ns2:TAX_NAME> //税码
 *                   <ns2:TAXABLE_FLAG>Y</ns2:TAXABLE_FLAG>//是否去税
 *                   <ns2:QUANTITY_COMMITTED/>
 *                   <ns2:QUANTITY>550</ns2:QUANTITY> //数量
 *                   <ns2:QTY_RCV_TOLERANCE/>
 *                   <ns2:OVER_TOLERANCE_ERROR_FLAG/>
 *                   <ns2:NOT_TO_EXCEED_PRICE/>
 *                   <ns2:NOTE_TO_VENDOR/>
 *                   <ns2:NEGOTIATED_BY_PREPARER_FLAG/>
 *                   <ns2:MIN_ORDER_QUANTITY/>
 *                   <ns2:MAX_ORDER_QUANTITY/>
 *                   <ns2:MARKET_PRICE/>
 *                   <ns2:LIST_PRICE_PER_UNIT/>
 *                   <ns2:ITEM_REVISION/>
 *                   <ns2:ITEM_ID/>
 *                   <ns2:ITEM_CODE>9130101001</ns2:ITEM_CODE> //ERP物料编码
 *                   <ns2:ITEM_TYPE/>
 *                   <ns2:ITEM_DESCRIPTION>纸箱_AB楞_413*343*210mm_图号LGiSW·T-DWG-030-01</ns2:ITEM_DESCRIPTION> //ERP物料名称
 *                   <ns2:ITEM_CATEGORY>91.0</ns2:ITEM_CATEGORY> //ERP物料类别
 *                   <ns2:AGENCY_CODE/>
 *                   <ns2:AGENCY_NAME/>
 *                   <ns2:LOTS_NUM/>
 *                   <ns2:LOTS_VALIDITY_DATE/>
 *                   <ns2:HAZARD_CLASS_ID/>
 *                   <ns2:FROM_LINE_ID/>
 *                   <ns2:FROM_HEADER_ID/>
 *                   <ns2:FIRM_STATUS_LOOKUP_CODE/>
 *                   <ns2:FIRM_DATE/>
 *                   <ns2:COMMITTED_AMOUNT/>
 *                   <ns2:CLOSED_REASON/>
 *                   <ns2:CLOSED_FLAG/>
 *                   <ns2:CLOSED_DATE/>
 *                   <ns2:CATEGORY_ID/>
 *                   <ns2:CAPITAL_EXPENSE_FLAG/>
 *                   <ns2:CANCEL_REASON/>
 *                   <ns2:CANCEL_FLAG/>
 *                   <ns2:CANCEL_DATE/>
 *                   <ns2:CANCELLED_BY/>
 *                   <ns2:ATTRIBUTE11/>
 *                   <ns2:ATTRIBUTE10/>
 *                   <ns2:ALLOW_PRICE_OVERRIDE_FLAG/>
 *                   <ns2:ERP_PO_LINE_TYPE/>
 *                   <ns2:ERP_PO_LINE_TYPE_DESC/>
 *                   <ns2:APPROVED_DATE/>
 *                   <ns2:ERP_CREATION_DATE/>
 *                   <ns2:ERP_LAST_UPDATE_DATE/>
 *                   <ns2:LINE_TYPE_CODE>1</ns2:LINE_TYPE_CODE> //行类型
 *                   <ns2:INV_ORGANIZATION_ID/>
 *                   <ns2:INV_ORGANIZATION_CODE/>
 *                   <ns2:IS_RETURNED_FLAG/>
 *                   <ns2:IS_FREE_FLAG/>
 *                   <ns2:IS_IMMED_SHIPPED_FLAG/>
 *                   <ns2:SHIP_TO_LOCATION_ADDRESS>西安</ns2:SHIP_TO_LOCATION_ADDRESS>//收货地点
 *                   <ns2:CONTACTS_INFO/>
 *                   <ns2:ACTUAL_RECEIVER_CODE/>
 *                   <ns2:ACTUAL_RECEIVER_NAME/>
 *                   <ns2:CURRENCY_CODE/>
 *                   <ns2:RATE_TYPE/>
 *                   <ns2:RATE_DATE/>
 *                   <ns2:RATE/>
 *                   <ns2:NEED_BY_DATE/>
 *                   <ns2:PROMISE_DATE>2020-10-28 16:39:13</ns2:PROMISE_DATE> //承诺到货日期
 *                   <ns2:CUSTOMER_REQUIREMENT/>
 *                   <ns2:CONTRACT_NUMBER>LGi·X-Pur-2009-107-A/001-SRM</ns2:CONTRACT_NUMBER> //采购合同号
 *                   <ns2:SOURCE_PO_NUM>202010090328</ns2:SOURCE_PO_NUM> //源采购订单号
 *                   <ns2:SOURCE_PO_LINE_NUM>1</ns2:SOURCE_PO_LINE_NUM>//源采购订单行号
 *                   <ns2:PRIMARY_UNIT>个</ns2:PRIMARY_UNIT>//主单位
 *                   <ns2:PRIMARY_QUANTITY>550</ns2:PRIMARY_QUANTITY>//数量
 *                   <ns2:C_ATTRIBUTE1/>
 *                   <ns2:C_ATTRIBUTE2/>
 *                   <ns2:C_ATTRIBUTE3/>
 *                   <ns2:C_ATTRIBUTE4/>
 *                   <ns2:C_ATTRIBUTE5/>
 *                   <ns2:C_ATTRIBUTE6/>
 *                   <ns2:C_ATTRIBUTE7/>
 *                   <ns2:C_ATTRIBUTE8/>
 *                   <ns2:C_ATTRIBUTE9/>
 *                   <ns2:C_ATTRIBUTE10/>
 *                   <ns2:C_ATTRIBUTE11/>
 *                   <ns2:C_ATTRIBUTE12/>
 *                   <ns2:C_ATTRIBUTE13/>
 *                   <ns2:C_ATTRIBUTE14/>
 *                   <ns2:C_ATTRIBUTE15/>
 *                   <ns2:C_ATTRIBUTE16/>
 *                   <ns2:C_ATTRIBUTE17/>
 *                   <ns2:C_ATTRIBUTE18/>
 *                   <ns2:C_ATTRIBUTE19/>
 *                   <ns2:C_ATTRIBUTE20/>
 *                   <ns2:C_ATTRIBUTE21/>
 *                   <ns2:C_ATTRIBUTE22/>
 *                   <ns2:C_ATTRIBUTE23/>
 *                   <ns2:C_ATTRIBUTE24/>
 *                   <ns2:C_ATTRIBUTE25/>
 *                   <ns2:C_ATTRIBUTE26/>
 *                   <ns2:C_ATTRIBUTE27/>
 *                   <ns2:C_ATTRIBUTE28/>
 *                   <ns2:C_ATTRIBUTE29/>
 *                   <ns2:C_ATTRIBUTE30/>
 *                   <ns2:C_ATTRIBUTE31/>
 *                   <ns2:C_ATTRIBUTE32/>
 *                   <ns2:C_ATTRIBUTE33/>
 *                   <ns2:C_ATTRIBUTE34/>
 *                   <ns2:C_ATTRIBUTE35/>
 *                   <ns2:C_ATTRIBUTE36/>
 *                   <ns2:C_ATTRIBUTE37/>
 *                   <ns2:C_ATTRIBUTE38/>
 *                   <ns2:C_ATTRIBUTE39/>
 *                   <ns2:C_ATTRIBUTE40/>
 *                   <ns2:C_ATTRIBUTE41/>
 *                   <ns2:C_ATTRIBUTE42/>
 *                   <ns2:C_ATTRIBUTE43/>
 *                   <ns2:C_ATTRIBUTE44/>
 *                   <ns2:C_ATTRIBUTE45/>
 *                   <ns2:C_ATTRIBUTE46/>
 *                   <ns2:C_ATTRIBUTE47/>
 *                   <ns2:C_ATTRIBUTE48/>
 *                   <ns2:C_ATTRIBUTE49/>
 *                   <ns2:C_ATTRIBUTE50/>
 *                   <ns2:PO_LINE_LOCATION/>
 *                 </ns2:RECODR>
 *                 <ns2:RECODR>
 *                   <ns2:PO_LINE_ID/>
 *                   <ns2:PO_HEADER_ID/>
 *                   <ns2:LINE_NUM>1</ns2:LINE_NUM>
 *                   <ns2:UNIT_PRICE>4.42477876</ns2:UNIT_PRICE>
 *                   <ns2:TAX_PRICE>4.9999999988</ns2:TAX_PRICE>
 *                   <ns2:USER_HOLD_FLAG/>
 *                   <ns2:UN_NUMBER_ID/>
 *                   <ns2:UNORDERED_FLAG/>
 *                   <ns2:UNIT_MEAS_LOOKUP_CODE>PCS</ns2:UNIT_MEAS_LOOKUP_CODE>
 *                   <ns2:TRANSACTION_REASON_CODE/>
 *                   <ns2:TAX_NAME>IN 13</ns2:TAX_NAME>
 *                   <ns2:TAXABLE_FLAG>Y</ns2:TAXABLE_FLAG>
 *                   <ns2:QUANTITY_COMMITTED/>
 *                   <ns2:QUANTITY>410</ns2:QUANTITY>
 *                   <ns2:QTY_RCV_TOLERANCE/>
 *                   <ns2:OVER_TOLERANCE_ERROR_FLAG/>
 *                   <ns2:NOT_TO_EXCEED_PRICE/>
 *                   <ns2:NOTE_TO_VENDOR/>
 *                   <ns2:NEGOTIATED_BY_PREPARER_FLAG/>
 *                   <ns2:MIN_ORDER_QUANTITY/>
 *                   <ns2:MAX_ORDER_QUANTITY/>
 *                   <ns2:MARKET_PRICE/>
 *                   <ns2:LIST_PRICE_PER_UNIT/>
 *                   <ns2:ITEM_REVISION/>
 *                   <ns2:ITEM_ID/>
 *                   <ns2:ITEM_CODE>9130101001</ns2:ITEM_CODE>
 *                   <ns2:ITEM_TYPE/>
 *                   <ns2:ITEM_DESCRIPTION>纸箱_AB楞_413*343*210mm_图号LGiSW·T-DWG-030-01</ns2:ITEM_DESCRIPTION>
 *                   <ns2:ITEM_CATEGORY>91.0</ns2:ITEM_CATEGORY>
 *                   <ns2:AGENCY_CODE/>
 *                   <ns2:AGENCY_NAME/>
 *                   <ns2:LOTS_NUM/>
 *                   <ns2:LOTS_VALIDITY_DATE/>
 *                   <ns2:HAZARD_CLASS_ID/>
 *                   <ns2:FROM_LINE_ID/>
 *                   <ns2:FROM_HEADER_ID/>
 *                   <ns2:FIRM_STATUS_LOOKUP_CODE/>
 *                   <ns2:FIRM_DATE/>
 *                   <ns2:COMMITTED_AMOUNT/>
 *                   <ns2:CLOSED_REASON/>
 *                   <ns2:CLOSED_FLAG/>
 *                   <ns2:CLOSED_DATE/>
 *                   <ns2:CATEGORY_ID/>
 *                   <ns2:CAPITAL_EXPENSE_FLAG/>
 *                   <ns2:CANCEL_REASON/>
 *                   <ns2:CANCEL_FLAG/>
 *                   <ns2:CANCEL_DATE/>
 *                   <ns2:CANCELLED_BY/>
 *                   <ns2:ATTRIBUTE11/>
 *                   <ns2:ATTRIBUTE10/>
 *                   <ns2:ALLOW_PRICE_OVERRIDE_FLAG/>
 *                   <ns2:ERP_PO_LINE_TYPE/>
 *                   <ns2:ERP_PO_LINE_TYPE_DESC/>
 *                   <ns2:APPROVED_DATE/>
 *                   <ns2:ERP_CREATION_DATE/>
 *                   <ns2:ERP_LAST_UPDATE_DATE/>
 *                   <ns2:LINE_TYPE_CODE>1</ns2:LINE_TYPE_CODE>
 *                   <ns2:INV_ORGANIZATION_ID/>
 *                   <ns2:INV_ORGANIZATION_CODE/>
 *                   <ns2:IS_RETURNED_FLAG/>
 *                   <ns2:IS_FREE_FLAG/>
 *                   <ns2:IS_IMMED_SHIPPED_FLAG/>
 *                   <ns2:SHIP_TO_LOCATION_ADDRESS>西安</ns2:SHIP_TO_LOCATION_ADDRESS>
 *                   <ns2:CONTACTS_INFO/>
 *                   <ns2:ACTUAL_RECEIVER_CODE/>
 *                   <ns2:ACTUAL_RECEIVER_NAME/>
 *                   <ns2:CURRENCY_CODE/>
 *                   <ns2:RATE_TYPE/>
 *                   <ns2:RATE_DATE/>
 *                   <ns2:RATE/>
 *                   <ns2:NEED_BY_DATE/>
 *                   <ns2:PROMISE_DATE>2020-10-28 16:39:13</ns2:PROMISE_DATE>
 *                   <ns2:CUSTOMER_REQUIREMENT/>
 *                   <ns2:CONTRACT_NUMBER>LGi·X-Pur-2009-107-A/001-SRM</ns2:CONTRACT_NUMBER>
 *                   <ns2:SOURCE_PO_NUM>202010090328</ns2:SOURCE_PO_NUM>
 *                   <ns2:SOURCE_PO_LINE_NUM>1</ns2:SOURCE_PO_LINE_NUM>
 *                   <ns2:PRIMARY_UNIT>个</ns2:PRIMARY_UNIT>
 *                   <ns2:PRIMARY_QUANTITY>410</ns2:PRIMARY_QUANTITY>
 *                   <ns2:C_ATTRIBUTE1/>
 *                   <ns2:C_ATTRIBUTE2/>
 *                   <ns2:C_ATTRIBUTE3/>
 *                   <ns2:C_ATTRIBUTE4/>
 *                   <ns2:C_ATTRIBUTE5/>
 *                   <ns2:C_ATTRIBUTE6/>
 *                   <ns2:C_ATTRIBUTE7/>
 *                   <ns2:C_ATTRIBUTE8/>
 *                   <ns2:C_ATTRIBUTE9/>
 *                   <ns2:C_ATTRIBUTE10/>
 *                   <ns2:C_ATTRIBUTE11/>
 *                   <ns2:C_ATTRIBUTE12/>
 *                   <ns2:C_ATTRIBUTE13/>
 *                   <ns2:C_ATTRIBUTE14/>
 *                   <ns2:C_ATTRIBUTE15/>
 *                   <ns2:C_ATTRIBUTE16/>
 *                   <ns2:C_ATTRIBUTE17/>
 *                   <ns2:C_ATTRIBUTE18/>
 *                   <ns2:C_ATTRIBUTE19/>
 *                   <ns2:C_ATTRIBUTE20/>
 *                   <ns2:C_ATTRIBUTE21/>
 *                   <ns2:C_ATTRIBUTE22/>
 *                   <ns2:C_ATTRIBUTE23/>
 *                   <ns2:C_ATTRIBUTE24/>
 *                   <ns2:C_ATTRIBUTE25/>
 *                   <ns2:C_ATTRIBUTE26/>
 *                   <ns2:C_ATTRIBUTE27/>
 *                   <ns2:C_ATTRIBUTE28/>
 *                   <ns2:C_ATTRIBUTE29/>
 *                   <ns2:C_ATTRIBUTE30/>
 *                   <ns2:C_ATTRIBUTE31/>
 *                   <ns2:C_ATTRIBUTE32/>
 *                   <ns2:C_ATTRIBUTE33/>
 *                   <ns2:C_ATTRIBUTE34/>
 *                   <ns2:C_ATTRIBUTE35/>
 *                   <ns2:C_ATTRIBUTE36/>
 *                   <ns2:C_ATTRIBUTE37/>
 *                   <ns2:C_ATTRIBUTE38/>
 *                   <ns2:C_ATTRIBUTE39/>
 *                   <ns2:C_ATTRIBUTE40/>
 *                   <ns2:C_ATTRIBUTE41/>
 *                   <ns2:C_ATTRIBUTE42/>
 *                   <ns2:C_ATTRIBUTE43/>
 *                   <ns2:C_ATTRIBUTE44/>
 *                   <ns2:C_ATTRIBUTE45/>
 *                   <ns2:C_ATTRIBUTE46/>
 *                   <ns2:C_ATTRIBUTE47/>
 *                   <ns2:C_ATTRIBUTE48/>
 *                   <ns2:C_ATTRIBUTE49/>
 *                   <ns2:C_ATTRIBUTE50/>
 *                   <ns2:PO_LINE_LOCATION/>
 *                 </ns2:RECODR>
 *               </ns2:PO_LINE>
 *             </ns2:RECORD>
 *           </ns2:PO_HEADER>
 *         </ns2:CONTEXT>
 *       </ns2:requestInfo>
 *     </ns2:REQUEST>
 *   </soapenv:Body>
 * </soap-env:Envelope>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
        "esbInfo",
        "requestInfo"
})
@XmlRootElement(name = "RequestSettlementOrder")
public class RequestSettlementOrder {

    @XmlElement(required = true,namespace = "http://www.aurora-framework.org/schema")
    protected EsbInfo esbInfo;
    @XmlElement(required = true,namespace = "http://www.aurora-framework.org/schema")
    protected RequestInfo requestInfo;

    /**
     * 获取esbInfo属性的值。
     *
     * @return
     *     possible object is
     *     {@link EsbInfo }
     *
     */
    public EsbInfo getEsbInfo() {
        return esbInfo;
    }

    /**
     * 设置esbInfo属性的值。
     *
     * @param value
     *     allowed object is
     *     {@link EsbInfo }
     *
     */
    public void setEsbInfo(EsbInfo value) {
        this.esbInfo = value;
    }

    /**
     * 获取requestInfo属性的值。
     *
     * @return
     *     possible object is
     *     {@link RequestInfo }
     *
     */
    public RequestInfo getRequestInfo() {
        return requestInfo;
    }

    /**
     * 设置requestInfo属性的值。
     *
     * @param value
     *     allowed object is
     *     {@link RequestInfo }
     *
     */
    public void setRequestInfo(RequestInfo value) {
        this.requestInfo = value;
    }


    /**
     * <p>anonymous complex type的 Java 类。
     *
     * <p>以下模式片段指定包含在此类中的预期内容。
     *
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element name="instId" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="requestTime" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="attr1" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="attr2" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="attr3" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *       &lt;/sequence>
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     *
     *
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
            "instId",
            "requestTime",
            "attr1",
            "attr2",
            "attr3"
    })
    public static class EsbInfo {

        @XmlElement(required = true,namespace = "http://www.aurora-framework.org/schema")
        protected String instId;
        @XmlElement(required = true,namespace = "http://www.aurora-framework.org/schema")
        protected String requestTime;
        @XmlElement(required = true,namespace = "http://www.aurora-framework.org/schema")
        protected String attr1;
        @XmlElement(required = true,namespace = "http://www.aurora-framework.org/schema")
        protected String attr2;
        @XmlElement(required = true,namespace = "http://www.aurora-framework.org/schema")
        protected String attr3;

        /**
         * 获取instId属性的值。
         *
         * @return
         *     possible object is
         *     {@link String }
         *
         */
        public String getInstId() {
            return instId;
        }

        /**
         * 设置instId属性的值。
         *
         * @param value
         *     allowed object is
         *     {@link String }
         *
         */
        public void setInstId(String value) {
            this.instId = value;
        }

        /**
         * 获取requestTime属性的值。
         *
         * @return
         *     possible object is
         *     {@link String }
         *
         */
        public String getRequestTime() {
            return requestTime;
        }

        /**
         * 设置requestTime属性的值。
         *
         * @param value
         *     allowed object is
         *     {@link String }
         *
         */
        public void setRequestTime(String value) {
            this.requestTime = value;
        }

        /**
         * 获取attr1属性的值。
         *
         * @return
         *     possible object is
         *     {@link String }
         *
         */
        public String getAttr1() {
            return attr1;
        }

        /**
         * 设置attr1属性的值。
         *
         * @param value
         *     allowed object is
         *     {@link String }
         *
         */
        public void setAttr1(String value) {
            this.attr1 = value;
        }

        /**
         * 获取attr2属性的值。
         *
         * @return
         *     possible object is
         *     {@link String }
         *
         */
        public String getAttr2() {
            return attr2;
        }

        /**
         * 设置attr2属性的值。
         *
         * @param value
         *     allowed object is
         *     {@link String }
         *
         */
        public void setAttr2(String value) {
            this.attr2 = value;
        }

        /**
         * 获取attr3属性的值。
         *
         * @return
         *     possible object is
         *     {@link String }
         *
         */
        public String getAttr3() {
            return attr3;
        }

        /**
         * 设置attr3属性的值。
         *
         * @param value
         *     allowed object is
         *     {@link String }
         *
         */
        public void setAttr3(String value) {
            this.attr3 = value;
        }

    }

    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
            "header",
            "context"
    })
    public static class RequestInfo {

        @XmlElement(name = "HEADER", required = true,namespace = "http://www.aurora-framework.org/schema")
        protected HEADER header;
        @XmlElement(name = "CONTEXT", required = true,namespace = "http://www.aurora-framework.org/schema")
        protected CONTEXT context;

        /**
         * 获取header属性的值。
         *
         * @return
         *     possible object is
         *     {@link HEADER }
         *
         */
        public HEADER getHEADER() {
            return header;
        }

        /**
         * 设置header属性的值。
         *
         * @param value
         *     allowed object is
         *     {@link HEADER }
         *
         */
        public void setHEADER(HEADER value) {
            this.header = value;
        }

        /**
         * 获取context属性的值。
         *
         * @return
         *     possible object is
         *     {@link CONTEXT }
         *
         */
        public CONTEXT getCONTEXT() {
            return context;
        }

        /**
         * 设置context属性的值。
         *
         * @param value
         *     allowed object is
         *     {@link CONTEXT }
         *
         */
        public void setCONTEXT(CONTEXT value) {
            this.context = value;
        }


        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {
                "poHeader"
        })
        public static class CONTEXT {

            @XmlElement(name = "PO_HEADER", required = true,namespace = "http://www.aurora-framework.org/schema")
            protected PoHeader poHeader;

            public PoHeader getPoHeader() {
                return poHeader;
            }

            public void setPoHeader(PoHeader value) {
                this.poHeader = value;
            }


            @XmlAccessorType(XmlAccessType.FIELD)
            @XmlType(name = "", propOrder = {
                    "record"
            })
            public static class PoHeader {

                @XmlElement(name = "RECORD", required = true,namespace = "http://www.aurora-framework.org/schema")
                protected List<RECORD> record;

                /**
                 * 获取record属性的值。
                 *
                 * @return
                 *     possible object is
                 *     {@link RECORD }
                 *
                 */
                public List<RECORD> getRECORD() {
                    return record;
                }

                /**
                 * 设置record属性的值。
                 *
                 * @param value
                 *     allowed object is
                 *     {@link RECORD }
                 *
                 */
                public void setRECORD(List<RECORD>  value) {
                    this.record = value;
                }


                @XmlAccessorType(XmlAccessType.FIELD)
                @XmlType(name = "", propOrder = {
                        "PO_HEADER_ID",
                        "PO_NUMBER",
                        "AGENT_ID",
                        "AGENT_CODE",
                        "AGENT_NAME",
                        "BUSINESS_UNIT_ID",
                        "BUSINESS_UNIT_CODE",
                        "PO_TYPE",
                        "PO_ORDER_TYPE",
                        "VENDOR_ID",
                        "VENDOR_CODE",
                        "VENDOR_SITE_ID",
                        "VENDOR_SITE_CODE",
                        "VENDOR_CONTACT_ID",
                        "VENDOR_CONTACT_CODE",
                        "VENDOR_CONTACT_NAME",
                        "SHIP_TO_LOCATION_ID",
                        "SHIP_TO_LOCATION_CODE",
                        "SHIP_TO_LOCATION_ADDRESS",
                        "BILL_TO_LOCATION_ID",
                        "BILL_TO_LOCATION_CODE",
                        "CONTRACT_NUMBER",
                        "SHIP_VIA_LOOKUP_CODE",
                        "FOB_LOOKUP_CODE",
                        "FREIGHT_TERMS_LOOKUP_CODE",
                        "PO_STATUS",
                        "CURRENCY_CODE",
                        "RATE_TYPE",
                        "RATE_DATE",
                        "RATE",
                        "START_DATE",
                        "END_DATE",
                        "BLANKET_TOTAL_AMOUNT",
                        "REVISION_NUM",
                        "APPROVED_DATE",
                        "MIN_RELEASE_AMOUNT",
                        "NOTE_TO_AUTHORIZER",
                        "NOTE_TO_VENDOR",
                        "NOTE_TO_RECEIVER",
                        "PRINT_COUNT",
                        "PRINTED_DATE",
                        "CONTRACTOR",
                        "CONTRACTOR_PHONE",
                        "CONTRACTOR_FAX",
                        "QUALITY_AGREEMENT_NUMBER",
                        "TECHNOLOGY_AGREEMENT_NUMBER",
                        "NEGOTIATION_NUMBER",
                        "VENDOR_ORDER_NUM",
                        "CONFIRMING_ORDER_FLAG",
                        "REPLY_DATE",
                        "REPLY_METHOD_LOOKUP_CODE",
                        "RFQ_CLOSE_DATE",
                        "QUOTE_TYPE_LOOKUP_CODE",
                        "QUOTATION_CLASS_CODE",
                        "QUOTE_WARNING_DELAY_UNIT",
                        "QUOTE_WARNING_DELAY",
                        "QUOTE_VENDOR_QUOTE_NUMBER",
                        "ACCEPTANCE_REQUIRED_FLAG",
                        "ACCEPTANCE_DUE_DATE",
                        "CLOSED_DATE",
                        "USER_HOLD_FLAG",
                        "APPROVAL_REQUIRED_FLAG",
                        "CANCEL_FLAG",
                        "FIRM_STATUS_LOOKUP_CODE",
                        "FIRM_DATE",
                        "FROZEN_FLAG",
                        "CLOSED_CODE",
                        "USSGL_TRANSACTION_CODE",
                        "SUPPLY_AGREEMENT_FLAG",
                        "EDI_PROCESSED_FLAG",
                        "EDI_PROCESSED_STATUS",
                        "PO_RELEASE_ID",
                        "PO_RELEASE_NUM",
                        "ERP_PO_TYPE",
                        "ERP_PO_TYPE_DESC",
                        "ERP_CREATION_DATE",
                        "ERP_LAST_UPDATE_DATE",
                        "COMMENTS",
                        "OTHER_COMMENTS",
                        "TAX_RATE",
                        "PAYMENT_METHOD",
                        "PAYMENT_TERM",
                        "MANUFACTURER_CODE",
                        "MANUFACTURER_NAME",
                        "C_ATTRIBUTE1",
                        "C_ATTRIBUTE2",
                        "C_ATTRIBUTE3",
                        "C_ATTRIBUTE4",
                        "C_ATTRIBUTE5",
                        "C_ATTRIBUTE6",
                        "C_ATTRIBUTE7",
                        "C_ATTRIBUTE8",
                        "C_ATTRIBUTE9",
                        "C_ATTRIBUTE10",
                        "C_ATTRIBUTE11",
                        "C_ATTRIBUTE12",
                        "C_ATTRIBUTE13",
                        "C_ATTRIBUTE14",
                        "C_ATTRIBUTE15",
                        "C_ATTRIBUTE16",
                        "C_ATTRIBUTE17",
                        "C_ATTRIBUTE18",
                        "C_ATTRIBUTE19",
                        "C_ATTRIBUTE20",
                        "C_ATTRIBUTE21",
                        "C_ATTRIBUTE22",
                        "C_ATTRIBUTE23",
                        "C_ATTRIBUTE24",
                        "C_ATTRIBUTE25",
                        "C_ATTRIBUTE26",
                        "C_ATTRIBUTE27",
                        "C_ATTRIBUTE28",
                        "C_ATTRIBUTE29",
                        "C_ATTRIBUTE30",
                        "C_ATTRIBUTE31",
                        "C_ATTRIBUTE32",
                        "C_ATTRIBUTE33",
                        "C_ATTRIBUTE34",
                        "C_ATTRIBUTE35",
                        "C_ATTRIBUTE36",
                        "C_ATTRIBUTE37",
                        "C_ATTRIBUTE38",
                        "C_ATTRIBUTE39",
                        "C_ATTRIBUTE40",
                        "C_ATTRIBUTE41",
                        "C_ATTRIBUTE42",
                        "C_ATTRIBUTE43",
                        "C_ATTRIBUTE44",
                        "C_ATTRIBUTE45",
                        "C_ATTRIBUTE46",
                        "C_ATTRIBUTE47",
                        "C_ATTRIBUTE48",
                        "C_ATTRIBUTE49",
                        "C_ATTRIBUTE50",
                        "PO_LINE"
                })
                public static class RECORD {
                    @XmlElement(name = "PO_HEADER_ID", namespace = "http://www.aurora-framework.org/schema", required = false)
                    protected String PO_HEADER_ID;

                    @XmlElement(name = "PO_NUMBER", namespace = "http://www.aurora-framework.org/schema", required = false)
                    protected String PO_NUMBER;

                    @XmlElement(name = "AGENT_ID", namespace = "http://www.aurora-framework.org/schema", required = false)
                    protected String AGENT_ID;

                    @XmlElement(name = "AGENT_CODE", namespace = "http://www.aurora-framework.org/schema", required = false)
                    protected String AGENT_CODE;

                    @XmlElement(name = "AGENT_NAME", namespace = "http://www.aurora-framework.org/schema", required = false)
                    protected String AGENT_NAME;

                    @XmlElement(name = "BUSINESS_UNIT_ID", namespace = "http://www.aurora-framework.org/schema", required = false)
                    protected String BUSINESS_UNIT_ID;

                    @XmlElement(name = "BUSINESS_UNIT_CODE", namespace = "http://www.aurora-framework.org/schema", required = false)
                    protected String BUSINESS_UNIT_CODE;

                    @XmlElement(name = "PO_TYPE", namespace = "http://www.aurora-framework.org/schema", required = false)
                    protected String PO_TYPE;

                    @XmlElement(name = "PO_ORDER_TYPE", namespace = "http://www.aurora-framework.org/schema", required = false)
                    protected String PO_ORDER_TYPE;

                    @XmlElement(name = "VENDOR_ID", namespace = "http://www.aurora-framework.org/schema", required = false)
                    protected String VENDOR_ID;

                    @XmlElement(name = "VENDOR_CODE", namespace = "http://www.aurora-framework.org/schema", required = false)
                    protected String VENDOR_CODE;

                    @XmlElement(name = "VENDOR_SITE_ID", namespace = "http://www.aurora-framework.org/schema", required = false)
                    protected String VENDOR_SITE_ID;

                    @XmlElement(name = "VENDOR_SITE_CODE", namespace = "http://www.aurora-framework.org/schema", required = false)
                    protected String VENDOR_SITE_CODE;

                    @XmlElement(name = "VENDOR_CONTACT_ID", namespace = "http://www.aurora-framework.org/schema", required = false)
                    protected String VENDOR_CONTACT_ID;

                    @XmlElement(name = "VENDOR_CONTACT_CODE", namespace = "http://www.aurora-framework.org/schema", required = false)
                    protected String VENDOR_CONTACT_CODE;

                    @XmlElement(name = "VENDOR_CONTACT_NAME", namespace = "http://www.aurora-framework.org/schema", required = false)
                    protected String VENDOR_CONTACT_NAME;

                    @XmlElement(name = "SHIP_TO_LOCATION_ID", namespace = "http://www.aurora-framework.org/schema", required = false)
                    protected String SHIP_TO_LOCATION_ID;

                    @XmlElement(name = "SHIP_TO_LOCATION_CODE", namespace = "http://www.aurora-framework.org/schema", required = false)
                    protected String SHIP_TO_LOCATION_CODE;

                    @XmlElement(name = "SHIP_TO_LOCATION_ADDRESS", namespace = "http://www.aurora-framework.org/schema", required = false)
                    protected String SHIP_TO_LOCATION_ADDRESS;

                    @XmlElement(name = "BILL_TO_LOCATION_ID", namespace = "http://www.aurora-framework.org/schema", required = false)
                    protected String BILL_TO_LOCATION_ID;

                    @XmlElement(name = "BILL_TO_LOCATION_CODE", namespace = "http://www.aurora-framework.org/schema", required = false)
                    protected String BILL_TO_LOCATION_CODE;

                    @XmlElement(name = "CONTRACT_NUMBER", namespace = "http://www.aurora-framework.org/schema", required = false)
                    protected String CONTRACT_NUMBER;

                    @XmlElement(name = "SHIP_VIA_LOOKUP_CODE", namespace = "http://www.aurora-framework.org/schema", required = false)
                    protected String SHIP_VIA_LOOKUP_CODE;

                    @XmlElement(name = "FOB_LOOKUP_CODE", namespace = "http://www.aurora-framework.org/schema", required = false)
                    protected String FOB_LOOKUP_CODE;

                    @XmlElement(name = "FREIGHT_TERMS_LOOKUP_CODE", namespace = "http://www.aurora-framework.org/schema", required = false)
                    protected String FREIGHT_TERMS_LOOKUP_CODE;

                    @XmlElement(name = "PO_STATUS", namespace = "http://www.aurora-framework.org/schema", required = false)
                    protected String PO_STATUS;

                    @XmlElement(name = "CURRENCY_CODE", namespace = "http://www.aurora-framework.org/schema", required = false)
                    protected String CURRENCY_CODE;

                    @XmlElement(name = "RATE_TYPE", namespace = "http://www.aurora-framework.org/schema", required = false)
                    protected String RATE_TYPE;

                    @XmlElement(name = "RATE_DATE", namespace = "http://www.aurora-framework.org/schema", required = false)
                    protected String RATE_DATE;

                    @XmlElement(name = "RATE", namespace = "http://www.aurora-framework.org/schema", required = false)
                    protected String RATE;

                    @XmlElement(name = "START_DATE", namespace = "http://www.aurora-framework.org/schema", required = false)
                    protected String START_DATE;

                    @XmlElement(name = "END_DATE", namespace = "http://www.aurora-framework.org/schema", required = false)
                    protected String END_DATE;

                    @XmlElement(name = "BLANKET_TOTAL_AMOUNT", namespace = "http://www.aurora-framework.org/schema", required = false)
                    protected String BLANKET_TOTAL_AMOUNT;

                    @XmlElement(name = "REVISION_NUM", namespace = "http://www.aurora-framework.org/schema", required = false)
                    protected String REVISION_NUM;

                    @XmlElement(name = "APPROVED_DATE", namespace = "http://www.aurora-framework.org/schema", required = false)
                    protected String APPROVED_DATE;

                    @XmlElement(name = "MIN_RELEASE_AMOUNT", namespace = "http://www.aurora-framework.org/schema", required = false)
                    protected String MIN_RELEASE_AMOUNT;

                    @XmlElement(name = "NOTE_TO_AUTHORIZER", namespace = "http://www.aurora-framework.org/schema", required = false)
                    protected String NOTE_TO_AUTHORIZER;

                    @XmlElement(name = "NOTE_TO_VENDOR", namespace = "http://www.aurora-framework.org/schema", required = false)
                    protected String NOTE_TO_VENDOR;

                    @XmlElement(name = "NOTE_TO_RECEIVER", namespace = "http://www.aurora-framework.org/schema", required = false)
                    protected String NOTE_TO_RECEIVER;

                    @XmlElement(name = "PRINT_COUNT", namespace = "http://www.aurora-framework.org/schema", required = false)
                    protected String PRINT_COUNT;

                    @XmlElement(name = "PRINTED_DATE", namespace = "http://www.aurora-framework.org/schema", required = false)
                    protected String PRINTED_DATE;

                    @XmlElement(name = "CONTRACTOR", namespace = "http://www.aurora-framework.org/schema", required = false)
                    protected String CONTRACTOR;

                    @XmlElement(name = "CONTRACTOR_PHONE", namespace = "http://www.aurora-framework.org/schema", required = false)
                    protected String CONTRACTOR_PHONE;

                    @XmlElement(name = "CONTRACTOR_FAX", namespace = "http://www.aurora-framework.org/schema", required = false)
                    protected String CONTRACTOR_FAX;

                    @XmlElement(name = "QUALITY_AGREEMENT_NUMBER", namespace = "http://www.aurora-framework.org/schema", required = false)
                    protected String QUALITY_AGREEMENT_NUMBER;

                    @XmlElement(name = "TECHNOLOGY_AGREEMENT_NUMBER", namespace = "http://www.aurora-framework.org/schema", required = false)
                    protected String TECHNOLOGY_AGREEMENT_NUMBER;

                    @XmlElement(name = "NEGOTIATION_NUMBER", namespace = "http://www.aurora-framework.org/schema", required = false)
                    protected String NEGOTIATION_NUMBER;

                    @XmlElement(name = "VENDOR_ORDER_NUM", namespace = "http://www.aurora-framework.org/schema", required = false)
                    protected String VENDOR_ORDER_NUM;

                    @XmlElement(name = "CONFIRMING_ORDER_FLAG", namespace = "http://www.aurora-framework.org/schema", required = false)
                    protected String CONFIRMING_ORDER_FLAG;

                    @XmlElement(name = "REPLY_DATE", namespace = "http://www.aurora-framework.org/schema", required = false)
                    protected String REPLY_DATE;

                    @XmlElement(name = "REPLY_METHOD_LOOKUP_CODE", namespace = "http://www.aurora-framework.org/schema", required = false)
                    protected String REPLY_METHOD_LOOKUP_CODE;

                    @XmlElement(name = "RFQ_CLOSE_DATE", namespace = "http://www.aurora-framework.org/schema", required = false)
                    protected String RFQ_CLOSE_DATE;

                    @XmlElement(name = "QUOTE_TYPE_LOOKUP_CODE", namespace = "http://www.aurora-framework.org/schema", required = false)
                    protected String QUOTE_TYPE_LOOKUP_CODE;

                    @XmlElement(name = "QUOTATION_CLASS_CODE", namespace = "http://www.aurora-framework.org/schema", required = false)
                    protected String QUOTATION_CLASS_CODE;

                    @XmlElement(name = "QUOTE_WARNING_DELAY_UNIT", namespace = "http://www.aurora-framework.org/schema", required = false)
                    protected String QUOTE_WARNING_DELAY_UNIT;

                    @XmlElement(name = "QUOTE_WARNING_DELAY", namespace = "http://www.aurora-framework.org/schema", required = false)
                    protected String QUOTE_WARNING_DELAY;

                    @XmlElement(name = "QUOTE_VENDOR_QUOTE_NUMBER", namespace = "http://www.aurora-framework.org/schema", required = false)
                    protected String QUOTE_VENDOR_QUOTE_NUMBER;

                    @XmlElement(name = "ACCEPTANCE_REQUIRED_FLAG", namespace = "http://www.aurora-framework.org/schema", required = false)
                    protected String ACCEPTANCE_REQUIRED_FLAG;

                    @XmlElement(name = "ACCEPTANCE_DUE_DATE", namespace = "http://www.aurora-framework.org/schema", required = false)
                    protected String ACCEPTANCE_DUE_DATE;

                    @XmlElement(name = "CLOSED_DATE", namespace = "http://www.aurora-framework.org/schema", required = false)
                    protected String CLOSED_DATE;

                    @XmlElement(name = "USER_HOLD_FLAG", namespace = "http://www.aurora-framework.org/schema", required = false)
                    protected String USER_HOLD_FLAG;

                    @XmlElement(name = "APPROVAL_REQUIRED_FLAG", namespace = "http://www.aurora-framework.org/schema", required = false)
                    protected String APPROVAL_REQUIRED_FLAG;

                    @XmlElement(name = "CANCEL_FLAG", namespace = "http://www.aurora-framework.org/schema", required = false)
                    protected String CANCEL_FLAG;

                    @XmlElement(name = "FIRM_STATUS_LOOKUP_CODE", namespace = "http://www.aurora-framework.org/schema", required = false)
                    protected String FIRM_STATUS_LOOKUP_CODE;

                    @XmlElement(name = "FIRM_DATE", namespace = "http://www.aurora-framework.org/schema", required = false)
                    protected String FIRM_DATE;

                    @XmlElement(name = "FROZEN_FLAG", namespace = "http://www.aurora-framework.org/schema", required = false)
                    protected String FROZEN_FLAG;

                    @XmlElement(name = "CLOSED_CODE", namespace = "http://www.aurora-framework.org/schema", required = false)
                    protected String CLOSED_CODE;

                    @XmlElement(name = "USSGL_TRANSACTION_CODE", namespace = "http://www.aurora-framework.org/schema", required = false)
                    protected String USSGL_TRANSACTION_CODE;

                    @XmlElement(name = "SUPPLY_AGREEMENT_FLAG", namespace = "http://www.aurora-framework.org/schema", required = false)
                    protected String SUPPLY_AGREEMENT_FLAG;

                    @XmlElement(name = "EDI_PROCESSED_FLAG", namespace = "http://www.aurora-framework.org/schema", required = false)
                    protected String EDI_PROCESSED_FLAG;

                    @XmlElement(name = "EDI_PROCESSED_STATUS", namespace = "http://www.aurora-framework.org/schema", required = false)
                    protected String EDI_PROCESSED_STATUS;

                    @XmlElement(name = "PO_RELEASE_ID", namespace = "http://www.aurora-framework.org/schema", required = false)
                    protected String PO_RELEASE_ID;

                    @XmlElement(name = "PO_RELEASE_NUM", namespace = "http://www.aurora-framework.org/schema", required = false)
                    protected String PO_RELEASE_NUM;

                    @XmlElement(name = "ERP_PO_TYPE", namespace = "http://www.aurora-framework.org/schema", required = false)
                    protected String ERP_PO_TYPE;

                    @XmlElement(name = "ERP_PO_TYPE_DESC", namespace = "http://www.aurora-framework.org/schema", required = false)
                    protected String ERP_PO_TYPE_DESC;

                    @XmlElement(name = "ERP_CREATION_DATE", namespace = "http://www.aurora-framework.org/schema", required = false)
                    protected String ERP_CREATION_DATE;

                    @XmlElement(name = "ERP_LAST_UPDATE_DATE", namespace = "http://www.aurora-framework.org/schema", required = false)
                    protected String ERP_LAST_UPDATE_DATE;

                    @XmlElement(name = "COMMENTS", namespace = "http://www.aurora-framework.org/schema", required = false)
                    protected String COMMENTS;

                    @XmlElement(name = "OTHER_COMMENTS", namespace = "http://www.aurora-framework.org/schema", required = false)
                    protected String OTHER_COMMENTS;

                    @XmlElement(name = "TAX_RATE", namespace = "http://www.aurora-framework.org/schema", required = false)
                    protected String TAX_RATE;

                    @XmlElement(name = "PAYMENT_METHOD", namespace = "http://www.aurora-framework.org/schema", required = false)
                    protected String PAYMENT_METHOD;

                    @XmlElement(name = "PAYMENT_TERM", namespace = "http://www.aurora-framework.org/schema", required = false)
                    protected String PAYMENT_TERM;

                    @XmlElement(name = "MANUFACTURER_CODE", namespace = "http://www.aurora-framework.org/schema", required = false)
                    protected String MANUFACTURER_CODE;

                    @XmlElement(name = "MANUFACTURER_NAME", namespace = "http://www.aurora-framework.org/schema", required = false)
                    protected String MANUFACTURER_NAME;

                    @XmlElement(name = "C_ATTRIBUTE1", namespace = "http://www.aurora-framework.org/schema", required = false)
                    protected String C_ATTRIBUTE1;

                    @XmlElement(name = "C_ATTRIBUTE2", namespace = "http://www.aurora-framework.org/schema", required = false)
                    protected String C_ATTRIBUTE2;

                    @XmlElement(name = "C_ATTRIBUTE3", namespace = "http://www.aurora-framework.org/schema", required = false)
                    protected String C_ATTRIBUTE3;
                    @XmlElement(name = "C_ATTRIBUTE4", namespace = "http://www.aurora-framework.org/schema", required = false)
                    protected String C_ATTRIBUTE4;
                    @XmlElement(name = "C_ATTRIBUTE5", namespace = "http://www.aurora-framework.org/schema", required = false)
                    protected String C_ATTRIBUTE5;
                    @XmlElement(name = "C_ATTRIBUTE6", namespace = "http://www.aurora-framework.org/schema", required = false)
                    protected String C_ATTRIBUTE6;
                    @XmlElement(name = "C_ATTRIBUTE7", namespace = "http://www.aurora-framework.org/schema", required = false)
                    protected String C_ATTRIBUTE7;
                    @XmlElement(name = "C_ATTRIBUTE8", namespace = "http://www.aurora-framework.org/schema", required = false)
                    protected String C_ATTRIBUTE8;
                    @XmlElement(name = "C_ATTRIBUTE9", namespace = "http://www.aurora-framework.org/schema", required = false)
                    protected String C_ATTRIBUTE9;
                    @XmlElement(name = "C_ATTRIBUTE10", namespace = "http://www.aurora-framework.org/schema", required = false)
                    protected String C_ATTRIBUTE10;

                    @XmlElement(name = "C_ATTRIBUTE11", namespace = "http://www.aurora-framework.org/schema", required = false)
                    protected String C_ATTRIBUTE11;
                    @XmlElement(name = "C_ATTRIBUTE12", namespace = "http://www.aurora-framework.org/schema", required = false)
                    protected String C_ATTRIBUTE12;
                    @XmlElement(name = "C_ATTRIBUTE13", namespace = "http://www.aurora-framework.org/schema", required = false)
                    protected String C_ATTRIBUTE13;
                    @XmlElement(name = "C_ATTRIBUTE14", namespace = "http://www.aurora-framework.org/schema", required = false)
                    protected String C_ATTRIBUTE14;
                    @XmlElement(name = "C_ATTRIBUTE15", namespace = "http://www.aurora-framework.org/schema", required = false)
                    protected String C_ATTRIBUTE15;
                    @XmlElement(name = "C_ATTRIBUTE16", namespace = "http://www.aurora-framework.org/schema", required = false)
                    protected String C_ATTRIBUTE16;
                    @XmlElement(name = "C_ATTRIBUTE17", namespace = "http://www.aurora-framework.org/schema", required = false)
                    protected String C_ATTRIBUTE17;
                    @XmlElement(name = "C_ATTRIBUTE18", namespace = "http://www.aurora-framework.org/schema", required = false)
                    protected String C_ATTRIBUTE18;
                    @XmlElement(name = "C_ATTRIBUTE19", namespace = "http://www.aurora-framework.org/schema", required = false)
                    protected String C_ATTRIBUTE19;
                    @XmlElement(name = "C_ATTRIBUTE20", namespace = "http://www.aurora-framework.org/schema", required = false)
                    protected String C_ATTRIBUTE20;

                    @XmlElement(name = "C_ATTRIBUTE21", namespace = "http://www.aurora-framework.org/schema", required = false)
                    protected String C_ATTRIBUTE21;
                    @XmlElement(name = "C_ATTRIBUTE22", namespace = "http://www.aurora-framework.org/schema", required = false)
                    protected String C_ATTRIBUTE22;
                    @XmlElement(name = "C_ATTRIBUTE23", namespace = "http://www.aurora-framework.org/schema", required = false)
                    protected String C_ATTRIBUTE23;
                    @XmlElement(name = "C_ATTRIBUTE24", namespace = "http://www.aurora-framework.org/schema", required = false)
                    protected String C_ATTRIBUTE24;
                    @XmlElement(name = "C_ATTRIBUTE25", namespace = "http://www.aurora-framework.org/schema", required = false)
                    protected String C_ATTRIBUTE25;
                    @XmlElement(name = "C_ATTRIBUTE26", namespace = "http://www.aurora-framework.org/schema", required = false)
                    protected String C_ATTRIBUTE26;
                    @XmlElement(name = "C_ATTRIBUTE27", namespace = "http://www.aurora-framework.org/schema", required = false)
                    protected String C_ATTRIBUTE27;
                    @XmlElement(name = "C_ATTRIBUTE28", namespace = "http://www.aurora-framework.org/schema", required = false)
                    protected String C_ATTRIBUTE28;
                    @XmlElement(name = "C_ATTRIBUTE29", namespace = "http://www.aurora-framework.org/schema", required = false)
                    protected String C_ATTRIBUTE29;
                    @XmlElement(name = "C_ATTRIBUTE30", namespace = "http://www.aurora-framework.org/schema", required = false)
                    protected String C_ATTRIBUTE30;
                    @XmlElement(name = "C_ATTRIBUTE31", namespace = "http://www.aurora-framework.org/schema", required = false)
                    protected String C_ATTRIBUTE31;
                    @XmlElement(name = "C_ATTRIBUTE32", namespace = "http://www.aurora-framework.org/schema", required = false)
                    protected String C_ATTRIBUTE32;
                    @XmlElement(name = "C_ATTRIBUTE33", namespace = "http://www.aurora-framework.org/schema", required = false)
                    protected String C_ATTRIBUTE33;
                    @XmlElement(name = "C_ATTRIBUTE34", namespace = "http://www.aurora-framework.org/schema", required = false)
                    protected String C_ATTRIBUTE34;
                    @XmlElement(name = "C_ATTRIBUTE35", namespace = "http://www.aurora-framework.org/schema", required = false)
                    protected String C_ATTRIBUTE35;
                    @XmlElement(name = "C_ATTRIBUTE36", namespace = "http://www.aurora-framework.org/schema", required = false)
                    protected String C_ATTRIBUTE36;
                    @XmlElement(name = "C_ATTRIBUTE37", namespace = "http://www.aurora-framework.org/schema", required = false)
                    protected String C_ATTRIBUTE37;
                    @XmlElement(name = "C_ATTRIBUTE38", namespace = "http://www.aurora-framework.org/schema", required = false)
                    protected String C_ATTRIBUTE38;
                    @XmlElement(name = "C_ATTRIBUTE39", namespace = "http://www.aurora-framework.org/schema", required = false)
                    protected String C_ATTRIBUTE39;
                    @XmlElement(name = "C_ATTRIBUTE40", namespace = "http://www.aurora-framework.org/schema", required = false)
                    protected String C_ATTRIBUTE40;

                    @XmlElement(name = "C_ATTRIBUTE41", namespace = "http://www.aurora-framework.org/schema", required = false)
                    protected String C_ATTRIBUTE41;
                    @XmlElement(name = "C_ATTRIBUTE42", namespace = "http://www.aurora-framework.org/schema", required = false)
                    protected String C_ATTRIBUTE42;
                    @XmlElement(name = "C_ATTRIBUTE43", namespace = "http://www.aurora-framework.org/schema", required = false)
                    protected String C_ATTRIBUTE43;
                    @XmlElement(name = "C_ATTRIBUTE44", namespace = "http://www.aurora-framework.org/schema", required = false)
                    protected String C_ATTRIBUTE44;
                    @XmlElement(name = "C_ATTRIBUTE45", namespace = "http://www.aurora-framework.org/schema", required = false)
                    protected String C_ATTRIBUTE45;
                    @XmlElement(name = "C_ATTRIBUTE46", namespace = "http://www.aurora-framework.org/schema", required = false)
                    protected String C_ATTRIBUTE46;
                    @XmlElement(name = "C_ATTRIBUTE47", namespace = "http://www.aurora-framework.org/schema", required = false)
                    protected String C_ATTRIBUTE47;
                    @XmlElement(name = "C_ATTRIBUTE48", namespace = "http://www.aurora-framework.org/schema", required = false)
                    protected String C_ATTRIBUTE48;
                    @XmlElement(name = "C_ATTRIBUTE49", namespace = "http://www.aurora-framework.org/schema", required = false)
                    protected String C_ATTRIBUTE49;
                    @XmlElement(name = "C_ATTRIBUTE50", namespace = "http://www.aurora-framework.org/schema", required = false)
                    protected String C_ATTRIBUTE50;

                    /*订单行*/
                    @XmlElement(name = "PO_LINE", namespace = "http://www.aurora-framework.org/schema", required = false)
                    protected PoLine PO_LINE;

                    public String getPO_HEADER_ID() {
                        return PO_HEADER_ID;
                    }

                    public void setPO_HEADER_ID(String PO_HEADER_ID) {
                        this.PO_HEADER_ID = PO_HEADER_ID;
                    }

                    public String getPO_NUMBER() {
                        return PO_NUMBER;
                    }

                    public void setPO_NUMBER(String PO_NUMBER) {
                        this.PO_NUMBER = PO_NUMBER;
                    }

                    public String getAGENT_ID() {
                        return AGENT_ID;
                    }

                    public void setAGENT_ID(String AGENT_ID) {
                        this.AGENT_ID = AGENT_ID;
                    }

                    public String getAGENT_CODE() {
                        return AGENT_CODE;
                    }

                    public void setAGENT_CODE(String AGENT_CODE) {
                        this.AGENT_CODE = AGENT_CODE;
                    }

                    public String getAGENT_NAME() {
                        return AGENT_NAME;
                    }

                    public void setAGENT_NAME(String AGENT_NAME) {
                        this.AGENT_NAME = AGENT_NAME;
                    }

                    public String getBUSINESS_UNIT_ID() {
                        return BUSINESS_UNIT_ID;
                    }

                    public void setBUSINESS_UNIT_ID(String BUSINESS_UNIT_ID) {
                        this.BUSINESS_UNIT_ID = BUSINESS_UNIT_ID;
                    }

                    public String getBUSINESS_UNIT_CODE() {
                        return BUSINESS_UNIT_CODE;
                    }

                    public void setBUSINESS_UNIT_CODE(String BUSINESS_UNIT_CODE) {
                        this.BUSINESS_UNIT_CODE = BUSINESS_UNIT_CODE;
                    }

                    public String getPO_TYPE() {
                        return PO_TYPE;
                    }

                    public void setPO_TYPE(String PO_TYPE) {
                        this.PO_TYPE = PO_TYPE;
                    }

                    public String getPO_ORDER_TYPE() {
                        return PO_ORDER_TYPE;
                    }

                    public void setPO_ORDER_TYPE(String PO_ORDER_TYPE) {
                        this.PO_ORDER_TYPE = PO_ORDER_TYPE;
                    }

                    public String getVENDOR_ID() {
                        return VENDOR_ID;
                    }

                    public void setVENDOR_ID(String VENDOR_ID) {
                        this.VENDOR_ID = VENDOR_ID;
                    }

                    public String getVENDOR_CODE() {
                        return VENDOR_CODE;
                    }

                    public void setVENDOR_CODE(String VENDOR_CODE) {
                        this.VENDOR_CODE = VENDOR_CODE;
                    }

                    public String getVENDOR_SITE_ID() {
                        return VENDOR_SITE_ID;
                    }

                    public void setVENDOR_SITE_ID(String VENDOR_SITE_ID) {
                        this.VENDOR_SITE_ID = VENDOR_SITE_ID;
                    }

                    public String getVENDOR_SITE_CODE() {
                        return VENDOR_SITE_CODE;
                    }

                    public void setVENDOR_SITE_CODE(String VENDOR_SITE_CODE) {
                        this.VENDOR_SITE_CODE = VENDOR_SITE_CODE;
                    }

                    public String getVENDOR_CONTACT_ID() {
                        return VENDOR_CONTACT_ID;
                    }

                    public void setVENDOR_CONTACT_ID(String VENDOR_CONTACT_ID) {
                        this.VENDOR_CONTACT_ID = VENDOR_CONTACT_ID;
                    }

                    public String getVENDOR_CONTACT_CODE() {
                        return VENDOR_CONTACT_CODE;
                    }

                    public void setVENDOR_CONTACT_CODE(String VENDOR_CONTACT_CODE) {
                        this.VENDOR_CONTACT_CODE = VENDOR_CONTACT_CODE;
                    }

                    public String getVENDOR_CONTACT_NAME() {
                        return VENDOR_CONTACT_NAME;
                    }

                    public void setVENDOR_CONTACT_NAME(String VENDOR_CONTACT_NAME) {
                        this.VENDOR_CONTACT_NAME = VENDOR_CONTACT_NAME;
                    }

                    public String getSHIP_TO_LOCATION_ID() {
                        return SHIP_TO_LOCATION_ID;
                    }

                    public void setSHIP_TO_LOCATION_ID(String SHIP_TO_LOCATION_ID) {
                        this.SHIP_TO_LOCATION_ID = SHIP_TO_LOCATION_ID;
                    }

                    public String getSHIP_TO_LOCATION_CODE() {
                        return SHIP_TO_LOCATION_CODE;
                    }

                    public void setSHIP_TO_LOCATION_CODE(String SHIP_TO_LOCATION_CODE) {
                        this.SHIP_TO_LOCATION_CODE = SHIP_TO_LOCATION_CODE;
                    }

                    public String getSHIP_TO_LOCATION_ADDRESS() {
                        return SHIP_TO_LOCATION_ADDRESS;
                    }

                    public void setSHIP_TO_LOCATION_ADDRESS(String SHIP_TO_LOCATION_ADDRESS) {
                        this.SHIP_TO_LOCATION_ADDRESS = SHIP_TO_LOCATION_ADDRESS;
                    }

                    public String getBILL_TO_LOCATION_ID() {
                        return BILL_TO_LOCATION_ID;
                    }

                    public void setBILL_TO_LOCATION_ID(String BILL_TO_LOCATION_ID) {
                        this.BILL_TO_LOCATION_ID = BILL_TO_LOCATION_ID;
                    }

                    public String getBILL_TO_LOCATION_CODE() {
                        return BILL_TO_LOCATION_CODE;
                    }

                    public void setBILL_TO_LOCATION_CODE(String BILL_TO_LOCATION_CODE) {
                        this.BILL_TO_LOCATION_CODE = BILL_TO_LOCATION_CODE;
                    }

                    public String getCONTRACT_NUMBER() {
                        return CONTRACT_NUMBER;
                    }

                    public void setCONTRACT_NUMBER(String CONTRACT_NUMBER) {
                        this.CONTRACT_NUMBER = CONTRACT_NUMBER;
                    }

                    public String getSHIP_VIA_LOOKUP_CODE() {
                        return SHIP_VIA_LOOKUP_CODE;
                    }

                    public void setSHIP_VIA_LOOKUP_CODE(String SHIP_VIA_LOOKUP_CODE) {
                        this.SHIP_VIA_LOOKUP_CODE = SHIP_VIA_LOOKUP_CODE;
                    }

                    public String getFOB_LOOKUP_CODE() {
                        return FOB_LOOKUP_CODE;
                    }

                    public void setFOB_LOOKUP_CODE(String FOB_LOOKUP_CODE) {
                        this.FOB_LOOKUP_CODE = FOB_LOOKUP_CODE;
                    }

                    public String getFREIGHT_TERMS_LOOKUP_CODE() {
                        return FREIGHT_TERMS_LOOKUP_CODE;
                    }

                    public void setFREIGHT_TERMS_LOOKUP_CODE(String FREIGHT_TERMS_LOOKUP_CODE) {
                        this.FREIGHT_TERMS_LOOKUP_CODE = FREIGHT_TERMS_LOOKUP_CODE;
                    }

                    public String getPO_STATUS() {
                        return PO_STATUS;
                    }

                    public void setPO_STATUS(String PO_STATUS) {
                        this.PO_STATUS = PO_STATUS;
                    }

                    public String getCURRENCY_CODE() {
                        return CURRENCY_CODE;
                    }

                    public void setCURRENCY_CODE(String CURRENCY_CODE) {
                        this.CURRENCY_CODE = CURRENCY_CODE;
                    }

                    public String getRATE_TYPE() {
                        return RATE_TYPE;
                    }

                    public void setRATE_TYPE(String RATE_TYPE) {
                        this.RATE_TYPE = RATE_TYPE;
                    }

                    public String getRATE_DATE() {
                        return RATE_DATE;
                    }

                    public void setRATE_DATE(String RATE_DATE) {
                        this.RATE_DATE = RATE_DATE;
                    }

                    public String getRATE() {
                        return RATE;
                    }

                    public void setRATE(String RATE) {
                        this.RATE = RATE;
                    }

                    public String getSTART_DATE() {
                        return START_DATE;
                    }

                    public void setSTART_DATE(String START_DATE) {
                        this.START_DATE = START_DATE;
                    }

                    public String getEND_DATE() {
                        return END_DATE;
                    }

                    public void setEND_DATE(String END_DATE) {
                        this.END_DATE = END_DATE;
                    }

                    public String getBLANKET_TOTAL_AMOUNT() {
                        return BLANKET_TOTAL_AMOUNT;
                    }

                    public void setBLANKET_TOTAL_AMOUNT(String BLANKET_TOTAL_AMOUNT) {
                        this.BLANKET_TOTAL_AMOUNT = BLANKET_TOTAL_AMOUNT;
                    }

                    public String getREVISION_NUM() {
                        return REVISION_NUM;
                    }

                    public void setREVISION_NUM(String REVISION_NUM) {
                        this.REVISION_NUM = REVISION_NUM;
                    }

                    public String getAPPROVED_DATE() {
                        return APPROVED_DATE;
                    }

                    public void setAPPROVED_DATE(String APPROVED_DATE) {
                        this.APPROVED_DATE = APPROVED_DATE;
                    }

                    public String getMIN_RELEASE_AMOUNT() {
                        return MIN_RELEASE_AMOUNT;
                    }

                    public void setMIN_RELEASE_AMOUNT(String MIN_RELEASE_AMOUNT) {
                        this.MIN_RELEASE_AMOUNT = MIN_RELEASE_AMOUNT;
                    }

                    public String getNOTE_TO_AUTHORIZER() {
                        return NOTE_TO_AUTHORIZER;
                    }

                    public void setNOTE_TO_AUTHORIZER(String NOTE_TO_AUTHORIZER) {
                        this.NOTE_TO_AUTHORIZER = NOTE_TO_AUTHORIZER;
                    }

                    public String getNOTE_TO_VENDOR() {
                        return NOTE_TO_VENDOR;
                    }

                    public void setNOTE_TO_VENDOR(String NOTE_TO_VENDOR) {
                        this.NOTE_TO_VENDOR = NOTE_TO_VENDOR;
                    }

                    public String getNOTE_TO_RECEIVER() {
                        return NOTE_TO_RECEIVER;
                    }

                    public void setNOTE_TO_RECEIVER(String NOTE_TO_RECEIVER) {
                        this.NOTE_TO_RECEIVER = NOTE_TO_RECEIVER;
                    }

                    public String getPRINT_COUNT() {
                        return PRINT_COUNT;
                    }

                    public void setPRINT_COUNT(String PRINT_COUNT) {
                        this.PRINT_COUNT = PRINT_COUNT;
                    }

                    public String getPRINTED_DATE() {
                        return PRINTED_DATE;
                    }

                    public void setPRINTED_DATE(String PRINTED_DATE) {
                        this.PRINTED_DATE = PRINTED_DATE;
                    }

                    public String getCONTRACTOR() {
                        return CONTRACTOR;
                    }

                    public void setCONTRACTOR(String CONTRACTOR) {
                        this.CONTRACTOR = CONTRACTOR;
                    }

                    public String getCONTRACTOR_PHONE() {
                        return CONTRACTOR_PHONE;
                    }

                    public void setCONTRACTOR_PHONE(String CONTRACTOR_PHONE) {
                        this.CONTRACTOR_PHONE = CONTRACTOR_PHONE;
                    }

                    public String getCONTRACTOR_FAX() {
                        return CONTRACTOR_FAX;
                    }

                    public void setCONTRACTOR_FAX(String CONTRACTOR_FAX) {
                        this.CONTRACTOR_FAX = CONTRACTOR_FAX;
                    }

                    public String getQUALITY_AGREEMENT_NUMBER() {
                        return QUALITY_AGREEMENT_NUMBER;
                    }

                    public void setQUALITY_AGREEMENT_NUMBER(String QUALITY_AGREEMENT_NUMBER) {
                        this.QUALITY_AGREEMENT_NUMBER = QUALITY_AGREEMENT_NUMBER;
                    }

                    public String getTECHNOLOGY_AGREEMENT_NUMBER() {
                        return TECHNOLOGY_AGREEMENT_NUMBER;
                    }

                    public void setTECHNOLOGY_AGREEMENT_NUMBER(String TECHNOLOGY_AGREEMENT_NUMBER) {
                        this.TECHNOLOGY_AGREEMENT_NUMBER = TECHNOLOGY_AGREEMENT_NUMBER;
                    }

                    public String getNEGOTIATION_NUMBER() {
                        return NEGOTIATION_NUMBER;
                    }

                    public void setNEGOTIATION_NUMBER(String NEGOTIATION_NUMBER) {
                        this.NEGOTIATION_NUMBER = NEGOTIATION_NUMBER;
                    }

                    public String getVENDOR_ORDER_NUM() {
                        return VENDOR_ORDER_NUM;
                    }

                    public void setVENDOR_ORDER_NUM(String VENDOR_ORDER_NUM) {
                        this.VENDOR_ORDER_NUM = VENDOR_ORDER_NUM;
                    }

                    public String getCONFIRMING_ORDER_FLAG() {
                        return CONFIRMING_ORDER_FLAG;
                    }

                    public void setCONFIRMING_ORDER_FLAG(String CONFIRMING_ORDER_FLAG) {
                        this.CONFIRMING_ORDER_FLAG = CONFIRMING_ORDER_FLAG;
                    }

                    public String getREPLY_DATE() {
                        return REPLY_DATE;
                    }

                    public void setREPLY_DATE(String REPLY_DATE) {
                        this.REPLY_DATE = REPLY_DATE;
                    }

                    public String getREPLY_METHOD_LOOKUP_CODE() {
                        return REPLY_METHOD_LOOKUP_CODE;
                    }

                    public void setREPLY_METHOD_LOOKUP_CODE(String REPLY_METHOD_LOOKUP_CODE) {
                        this.REPLY_METHOD_LOOKUP_CODE = REPLY_METHOD_LOOKUP_CODE;
                    }

                    public String getRFQ_CLOSE_DATE() {
                        return RFQ_CLOSE_DATE;
                    }

                    public void setRFQ_CLOSE_DATE(String RFQ_CLOSE_DATE) {
                        this.RFQ_CLOSE_DATE = RFQ_CLOSE_DATE;
                    }

                    public String getQUOTE_TYPE_LOOKUP_CODE() {
                        return QUOTE_TYPE_LOOKUP_CODE;
                    }

                    public void setQUOTE_TYPE_LOOKUP_CODE(String QUOTE_TYPE_LOOKUP_CODE) {
                        this.QUOTE_TYPE_LOOKUP_CODE = QUOTE_TYPE_LOOKUP_CODE;
                    }

                    public String getQUOTATION_CLASS_CODE() {
                        return QUOTATION_CLASS_CODE;
                    }

                    public void setQUOTATION_CLASS_CODE(String QUOTATION_CLASS_CODE) {
                        this.QUOTATION_CLASS_CODE = QUOTATION_CLASS_CODE;
                    }

                    public String getQUOTE_WARNING_DELAY_UNIT() {
                        return QUOTE_WARNING_DELAY_UNIT;
                    }

                    public void setQUOTE_WARNING_DELAY_UNIT(String QUOTE_WARNING_DELAY_UNIT) {
                        this.QUOTE_WARNING_DELAY_UNIT = QUOTE_WARNING_DELAY_UNIT;
                    }

                    public String getQUOTE_WARNING_DELAY() {
                        return QUOTE_WARNING_DELAY;
                    }

                    public void setQUOTE_WARNING_DELAY(String QUOTE_WARNING_DELAY) {
                        this.QUOTE_WARNING_DELAY = QUOTE_WARNING_DELAY;
                    }

                    public String getQUOTE_VENDOR_QUOTE_NUMBER() {
                        return QUOTE_VENDOR_QUOTE_NUMBER;
                    }

                    public void setQUOTE_VENDOR_QUOTE_NUMBER(String QUOTE_VENDOR_QUOTE_NUMBER) {
                        this.QUOTE_VENDOR_QUOTE_NUMBER = QUOTE_VENDOR_QUOTE_NUMBER;
                    }

                    public String getACCEPTANCE_REQUIRED_FLAG() {
                        return ACCEPTANCE_REQUIRED_FLAG;
                    }

                    public void setACCEPTANCE_REQUIRED_FLAG(String ACCEPTANCE_REQUIRED_FLAG) {
                        this.ACCEPTANCE_REQUIRED_FLAG = ACCEPTANCE_REQUIRED_FLAG;
                    }

                    public String getACCEPTANCE_DUE_DATE() {
                        return ACCEPTANCE_DUE_DATE;
                    }

                    public void setACCEPTANCE_DUE_DATE(String ACCEPTANCE_DUE_DATE) {
                        this.ACCEPTANCE_DUE_DATE = ACCEPTANCE_DUE_DATE;
                    }

                    public String getCLOSED_DATE() {
                        return CLOSED_DATE;
                    }

                    public void setCLOSED_DATE(String CLOSED_DATE) {
                        this.CLOSED_DATE = CLOSED_DATE;
                    }

                    public String getUSER_HOLD_FLAG() {
                        return USER_HOLD_FLAG;
                    }

                    public void setUSER_HOLD_FLAG(String USER_HOLD_FLAG) {
                        this.USER_HOLD_FLAG = USER_HOLD_FLAG;
                    }

                    public String getAPPROVAL_REQUIRED_FLAG() {
                        return APPROVAL_REQUIRED_FLAG;
                    }

                    public void setAPPROVAL_REQUIRED_FLAG(String APPROVAL_REQUIRED_FLAG) {
                        this.APPROVAL_REQUIRED_FLAG = APPROVAL_REQUIRED_FLAG;
                    }

                    public String getCANCEL_FLAG() {
                        return CANCEL_FLAG;
                    }

                    public void setCANCEL_FLAG(String CANCEL_FLAG) {
                        this.CANCEL_FLAG = CANCEL_FLAG;
                    }

                    public String getFIRM_STATUS_LOOKUP_CODE() {
                        return FIRM_STATUS_LOOKUP_CODE;
                    }

                    public void setFIRM_STATUS_LOOKUP_CODE(String FIRM_STATUS_LOOKUP_CODE) {
                        this.FIRM_STATUS_LOOKUP_CODE = FIRM_STATUS_LOOKUP_CODE;
                    }

                    public String getFIRM_DATE() {
                        return FIRM_DATE;
                    }

                    public void setFIRM_DATE(String FIRM_DATE) {
                        this.FIRM_DATE = FIRM_DATE;
                    }

                    public String getFROZEN_FLAG() {
                        return FROZEN_FLAG;
                    }

                    public void setFROZEN_FLAG(String FROZEN_FLAG) {
                        this.FROZEN_FLAG = FROZEN_FLAG;
                    }

                    public String getCLOSED_CODE() {
                        return CLOSED_CODE;
                    }

                    public void setCLOSED_CODE(String CLOSED_CODE) {
                        this.CLOSED_CODE = CLOSED_CODE;
                    }

                    public String getUSSGL_TRANSACTION_CODE() {
                        return USSGL_TRANSACTION_CODE;
                    }

                    public void setUSSGL_TRANSACTION_CODE(String USSGL_TRANSACTION_CODE) {
                        this.USSGL_TRANSACTION_CODE = USSGL_TRANSACTION_CODE;
                    }

                    public String getSUPPLY_AGREEMENT_FLAG() {
                        return SUPPLY_AGREEMENT_FLAG;
                    }

                    public void setSUPPLY_AGREEMENT_FLAG(String SUPPLY_AGREEMENT_FLAG) {
                        this.SUPPLY_AGREEMENT_FLAG = SUPPLY_AGREEMENT_FLAG;
                    }

                    public String getEDI_PROCESSED_FLAG() {
                        return EDI_PROCESSED_FLAG;
                    }

                    public void setEDI_PROCESSED_FLAG(String EDI_PROCESSED_FLAG) {
                        this.EDI_PROCESSED_FLAG = EDI_PROCESSED_FLAG;
                    }

                    public String getEDI_PROCESSED_STATUS() {
                        return EDI_PROCESSED_STATUS;
                    }

                    public void setEDI_PROCESSED_STATUS(String EDI_PROCESSED_STATUS) {
                        this.EDI_PROCESSED_STATUS = EDI_PROCESSED_STATUS;
                    }

                    public String getPO_RELEASE_ID() {
                        return PO_RELEASE_ID;
                    }

                    public void setPO_RELEASE_ID(String PO_RELEASE_ID) {
                        this.PO_RELEASE_ID = PO_RELEASE_ID;
                    }

                    public String getPO_RELEASE_NUM() {
                        return PO_RELEASE_NUM;
                    }

                    public void setPO_RELEASE_NUM(String PO_RELEASE_NUM) {
                        this.PO_RELEASE_NUM = PO_RELEASE_NUM;
                    }

                    public String getERP_PO_TYPE() {
                        return ERP_PO_TYPE;
                    }

                    public void setERP_PO_TYPE(String ERP_PO_TYPE) {
                        this.ERP_PO_TYPE = ERP_PO_TYPE;
                    }

                    public String getERP_PO_TYPE_DESC() {
                        return ERP_PO_TYPE_DESC;
                    }

                    public void setERP_PO_TYPE_DESC(String ERP_PO_TYPE_DESC) {
                        this.ERP_PO_TYPE_DESC = ERP_PO_TYPE_DESC;
                    }

                    public String getERP_CREATION_DATE() {
                        return ERP_CREATION_DATE;
                    }

                    public void setERP_CREATION_DATE(String ERP_CREATION_DATE) {
                        this.ERP_CREATION_DATE = ERP_CREATION_DATE;
                    }

                    public String getERP_LAST_UPDATE_DATE() {
                        return ERP_LAST_UPDATE_DATE;
                    }

                    public void setERP_LAST_UPDATE_DATE(String ERP_LAST_UPDATE_DATE) {
                        this.ERP_LAST_UPDATE_DATE = ERP_LAST_UPDATE_DATE;
                    }

                    public String getCOMMENTS() {
                        return COMMENTS;
                    }

                    public void setCOMMENTS(String COMMENTS) {
                        this.COMMENTS = COMMENTS;
                    }

                    public String getOTHER_COMMENTS() {
                        return OTHER_COMMENTS;
                    }

                    public void setOTHER_COMMENTS(String OTHER_COMMENTS) {
                        this.OTHER_COMMENTS = OTHER_COMMENTS;
                    }

                    public String getTAX_RATE() {
                        return TAX_RATE;
                    }

                    public void setTAX_RATE(String TAX_RATE) {
                        this.TAX_RATE = TAX_RATE;
                    }

                    public String getPAYMENT_METHOD() {
                        return PAYMENT_METHOD;
                    }

                    public void setPAYMENT_METHOD(String PAYMENT_METHOD) {
                        this.PAYMENT_METHOD = PAYMENT_METHOD;
                    }

                    public String getPAYMENT_TERM() {
                        return PAYMENT_TERM;
                    }

                    public void setPAYMENT_TERM(String PAYMENT_TERM) {
                        this.PAYMENT_TERM = PAYMENT_TERM;
                    }

                    public String getMANUFACTURER_CODE() {
                        return MANUFACTURER_CODE;
                    }

                    public void setMANUFACTURER_CODE(String MANUFACTURER_CODE) {
                        this.MANUFACTURER_CODE = MANUFACTURER_CODE;
                    }

                    public String getMANUFACTURER_NAME() {
                        return MANUFACTURER_NAME;
                    }

                    public void setMANUFACTURER_NAME(String MANUFACTURER_NAME) {
                        this.MANUFACTURER_NAME = MANUFACTURER_NAME;
                    }

                    public String getC_ATTRIBUTE1() {
                        return C_ATTRIBUTE1;
                    }

                    public void setC_ATTRIBUTE1(String c_ATTRIBUTE1) {
                        C_ATTRIBUTE1 = c_ATTRIBUTE1;
                    }

                    public String getC_ATTRIBUTE2() {
                        return C_ATTRIBUTE2;
                    }

                    public void setC_ATTRIBUTE2(String c_ATTRIBUTE2) {
                        C_ATTRIBUTE2 = c_ATTRIBUTE2;
                    }

                    public String getC_ATTRIBUTE3() {
                        return C_ATTRIBUTE3;
                    }

                    public void setC_ATTRIBUTE3(String c_ATTRIBUTE3) {
                        C_ATTRIBUTE3 = c_ATTRIBUTE3;
                    }

                    public String getC_ATTRIBUTE4() {
                        return C_ATTRIBUTE4;
                    }

                    public void setC_ATTRIBUTE4(String c_ATTRIBUTE4) {
                        C_ATTRIBUTE4 = c_ATTRIBUTE4;
                    }

                    public String getC_ATTRIBUTE5() {
                        return C_ATTRIBUTE5;
                    }

                    public void setC_ATTRIBUTE5(String c_ATTRIBUTE5) {
                        C_ATTRIBUTE5 = c_ATTRIBUTE5;
                    }

                    public String getC_ATTRIBUTE6() {
                        return C_ATTRIBUTE6;
                    }

                    public void setC_ATTRIBUTE6(String c_ATTRIBUTE6) {
                        C_ATTRIBUTE6 = c_ATTRIBUTE6;
                    }

                    public String getC_ATTRIBUTE7() {
                        return C_ATTRIBUTE7;
                    }

                    public void setC_ATTRIBUTE7(String c_ATTRIBUTE7) {
                        C_ATTRIBUTE7 = c_ATTRIBUTE7;
                    }

                    public String getC_ATTRIBUTE8() {
                        return C_ATTRIBUTE8;
                    }

                    public void setC_ATTRIBUTE8(String c_ATTRIBUTE8) {
                        C_ATTRIBUTE8 = c_ATTRIBUTE8;
                    }

                    public String getC_ATTRIBUTE9() {
                        return C_ATTRIBUTE9;
                    }

                    public void setC_ATTRIBUTE9(String c_ATTRIBUTE9) {
                        C_ATTRIBUTE9 = c_ATTRIBUTE9;
                    }

                    public String getC_ATTRIBUTE10() {
                        return C_ATTRIBUTE10;
                    }

                    public void setC_ATTRIBUTE10(String c_ATTRIBUTE10) {
                        C_ATTRIBUTE10 = c_ATTRIBUTE10;
                    }

                    public String getC_ATTRIBUTE11() {
                        return C_ATTRIBUTE11;
                    }

                    public void setC_ATTRIBUTE11(String c_ATTRIBUTE11) {
                        C_ATTRIBUTE11 = c_ATTRIBUTE11;
                    }

                    public String getC_ATTRIBUTE12() {
                        return C_ATTRIBUTE12;
                    }

                    public void setC_ATTRIBUTE12(String c_ATTRIBUTE12) {
                        C_ATTRIBUTE12 = c_ATTRIBUTE12;
                    }

                    public String getC_ATTRIBUTE13() {
                        return C_ATTRIBUTE13;
                    }

                    public void setC_ATTRIBUTE13(String c_ATTRIBUTE13) {
                        C_ATTRIBUTE13 = c_ATTRIBUTE13;
                    }

                    public String getC_ATTRIBUTE14() {
                        return C_ATTRIBUTE14;
                    }

                    public void setC_ATTRIBUTE14(String c_ATTRIBUTE14) {
                        C_ATTRIBUTE14 = c_ATTRIBUTE14;
                    }

                    public String getC_ATTRIBUTE15() {
                        return C_ATTRIBUTE15;
                    }

                    public void setC_ATTRIBUTE15(String c_ATTRIBUTE15) {
                        C_ATTRIBUTE15 = c_ATTRIBUTE15;
                    }

                    public String getC_ATTRIBUTE16() {
                        return C_ATTRIBUTE16;
                    }

                    public void setC_ATTRIBUTE16(String c_ATTRIBUTE16) {
                        C_ATTRIBUTE16 = c_ATTRIBUTE16;
                    }

                    public String getC_ATTRIBUTE17() {
                        return C_ATTRIBUTE17;
                    }

                    public void setC_ATTRIBUTE17(String c_ATTRIBUTE17) {
                        C_ATTRIBUTE17 = c_ATTRIBUTE17;
                    }

                    public String getC_ATTRIBUTE18() {
                        return C_ATTRIBUTE18;
                    }

                    public void setC_ATTRIBUTE18(String c_ATTRIBUTE18) {
                        C_ATTRIBUTE18 = c_ATTRIBUTE18;
                    }

                    public String getC_ATTRIBUTE19() {
                        return C_ATTRIBUTE19;
                    }

                    public void setC_ATTRIBUTE19(String c_ATTRIBUTE19) {
                        C_ATTRIBUTE19 = c_ATTRIBUTE19;
                    }

                    public String getC_ATTRIBUTE20() {
                        return C_ATTRIBUTE20;
                    }

                    public void setC_ATTRIBUTE20(String c_ATTRIBUTE20) {
                        C_ATTRIBUTE20 = c_ATTRIBUTE20;
                    }

                    public String getC_ATTRIBUTE21() {
                        return C_ATTRIBUTE21;
                    }

                    public void setC_ATTRIBUTE21(String c_ATTRIBUTE21) {
                        C_ATTRIBUTE21 = c_ATTRIBUTE21;
                    }

                    public String getC_ATTRIBUTE22() {
                        return C_ATTRIBUTE22;
                    }

                    public void setC_ATTRIBUTE22(String c_ATTRIBUTE22) {
                        C_ATTRIBUTE22 = c_ATTRIBUTE22;
                    }

                    public String getC_ATTRIBUTE23() {
                        return C_ATTRIBUTE23;
                    }

                    public void setC_ATTRIBUTE23(String c_ATTRIBUTE23) {
                        C_ATTRIBUTE23 = c_ATTRIBUTE23;
                    }

                    public String getC_ATTRIBUTE24() {
                        return C_ATTRIBUTE24;
                    }

                    public void setC_ATTRIBUTE24(String c_ATTRIBUTE24) {
                        C_ATTRIBUTE24 = c_ATTRIBUTE24;
                    }

                    public String getC_ATTRIBUTE25() {
                        return C_ATTRIBUTE25;
                    }

                    public void setC_ATTRIBUTE25(String c_ATTRIBUTE25) {
                        C_ATTRIBUTE25 = c_ATTRIBUTE25;
                    }

                    public String getC_ATTRIBUTE26() {
                        return C_ATTRIBUTE26;
                    }

                    public void setC_ATTRIBUTE26(String c_ATTRIBUTE26) {
                        C_ATTRIBUTE26 = c_ATTRIBUTE26;
                    }

                    public String getC_ATTRIBUTE27() {
                        return C_ATTRIBUTE27;
                    }

                    public void setC_ATTRIBUTE27(String c_ATTRIBUTE27) {
                        C_ATTRIBUTE27 = c_ATTRIBUTE27;
                    }

                    public String getC_ATTRIBUTE28() {
                        return C_ATTRIBUTE28;
                    }

                    public void setC_ATTRIBUTE28(String c_ATTRIBUTE28) {
                        C_ATTRIBUTE28 = c_ATTRIBUTE28;
                    }

                    public String getC_ATTRIBUTE29() {
                        return C_ATTRIBUTE29;
                    }

                    public void setC_ATTRIBUTE29(String c_ATTRIBUTE29) {
                        C_ATTRIBUTE29 = c_ATTRIBUTE29;
                    }

                    public String getC_ATTRIBUTE30() {
                        return C_ATTRIBUTE30;
                    }

                    public void setC_ATTRIBUTE30(String c_ATTRIBUTE30) {
                        C_ATTRIBUTE30 = c_ATTRIBUTE30;
                    }

                    public String getC_ATTRIBUTE31() {
                        return C_ATTRIBUTE31;
                    }

                    public void setC_ATTRIBUTE31(String c_ATTRIBUTE31) {
                        C_ATTRIBUTE31 = c_ATTRIBUTE31;
                    }

                    public String getC_ATTRIBUTE32() {
                        return C_ATTRIBUTE32;
                    }

                    public void setC_ATTRIBUTE32(String c_ATTRIBUTE32) {
                        C_ATTRIBUTE32 = c_ATTRIBUTE32;
                    }

                    public String getC_ATTRIBUTE33() {
                        return C_ATTRIBUTE33;
                    }

                    public void setC_ATTRIBUTE33(String c_ATTRIBUTE33) {
                        C_ATTRIBUTE33 = c_ATTRIBUTE33;
                    }

                    public String getC_ATTRIBUTE34() {
                        return C_ATTRIBUTE34;
                    }

                    public void setC_ATTRIBUTE34(String c_ATTRIBUTE34) {
                        C_ATTRIBUTE34 = c_ATTRIBUTE34;
                    }

                    public String getC_ATTRIBUTE35() {
                        return C_ATTRIBUTE35;
                    }

                    public void setC_ATTRIBUTE35(String c_ATTRIBUTE35) {
                        C_ATTRIBUTE35 = c_ATTRIBUTE35;
                    }

                    public String getC_ATTRIBUTE36() {
                        return C_ATTRIBUTE36;
                    }

                    public void setC_ATTRIBUTE36(String c_ATTRIBUTE36) {
                        C_ATTRIBUTE36 = c_ATTRIBUTE36;
                    }

                    public String getC_ATTRIBUTE37() {
                        return C_ATTRIBUTE37;
                    }

                    public void setC_ATTRIBUTE37(String c_ATTRIBUTE37) {
                        C_ATTRIBUTE37 = c_ATTRIBUTE37;
                    }

                    public String getC_ATTRIBUTE38() {
                        return C_ATTRIBUTE38;
                    }

                    public void setC_ATTRIBUTE38(String c_ATTRIBUTE38) {
                        C_ATTRIBUTE38 = c_ATTRIBUTE38;
                    }

                    public String getC_ATTRIBUTE39() {
                        return C_ATTRIBUTE39;
                    }

                    public void setC_ATTRIBUTE39(String c_ATTRIBUTE39) {
                        C_ATTRIBUTE39 = c_ATTRIBUTE39;
                    }

                    public String getC_ATTRIBUTE40() {
                        return C_ATTRIBUTE40;
                    }

                    public void setC_ATTRIBUTE40(String c_ATTRIBUTE40) {
                        C_ATTRIBUTE40 = c_ATTRIBUTE40;
                    }

                    public String getC_ATTRIBUTE41() {
                        return C_ATTRIBUTE41;
                    }

                    public void setC_ATTRIBUTE41(String c_ATTRIBUTE41) {
                        C_ATTRIBUTE41 = c_ATTRIBUTE41;
                    }

                    public String getC_ATTRIBUTE42() {
                        return C_ATTRIBUTE42;
                    }

                    public void setC_ATTRIBUTE42(String c_ATTRIBUTE42) {
                        C_ATTRIBUTE42 = c_ATTRIBUTE42;
                    }

                    public String getC_ATTRIBUTE43() {
                        return C_ATTRIBUTE43;
                    }

                    public void setC_ATTRIBUTE43(String c_ATTRIBUTE43) {
                        C_ATTRIBUTE43 = c_ATTRIBUTE43;
                    }

                    public String getC_ATTRIBUTE44() {
                        return C_ATTRIBUTE44;
                    }

                    public void setC_ATTRIBUTE44(String c_ATTRIBUTE44) {
                        C_ATTRIBUTE44 = c_ATTRIBUTE44;
                    }

                    public String getC_ATTRIBUTE45() {
                        return C_ATTRIBUTE45;
                    }

                    public void setC_ATTRIBUTE45(String c_ATTRIBUTE45) {
                        C_ATTRIBUTE45 = c_ATTRIBUTE45;
                    }

                    public String getC_ATTRIBUTE46() {
                        return C_ATTRIBUTE46;
                    }

                    public void setC_ATTRIBUTE46(String c_ATTRIBUTE46) {
                        C_ATTRIBUTE46 = c_ATTRIBUTE46;
                    }

                    public String getC_ATTRIBUTE47() {
                        return C_ATTRIBUTE47;
                    }

                    public void setC_ATTRIBUTE47(String c_ATTRIBUTE47) {
                        C_ATTRIBUTE47 = c_ATTRIBUTE47;
                    }

                    public String getC_ATTRIBUTE48() {
                        return C_ATTRIBUTE48;
                    }

                    public void setC_ATTRIBUTE48(String c_ATTRIBUTE48) {
                        C_ATTRIBUTE48 = c_ATTRIBUTE48;
                    }

                    public String getC_ATTRIBUTE49() {
                        return C_ATTRIBUTE49;
                    }

                    public void setC_ATTRIBUTE49(String c_ATTRIBUTE49) {
                        C_ATTRIBUTE49 = c_ATTRIBUTE49;
                    }

                    public String getC_ATTRIBUTE50() {
                        return C_ATTRIBUTE50;
                    }

                    public void setC_ATTRIBUTE50(String c_ATTRIBUTE50) {
                        C_ATTRIBUTE50 = c_ATTRIBUTE50;
                    }

                    public PoLine getPO_LINE() {
                        return PO_LINE;
                    }

                    public void setPO_LINE(PoLine PO_LINE) {
                        this.PO_LINE = PO_LINE;
                    }

                    @XmlAccessorType(XmlAccessType.FIELD)
                    @XmlType(name = "", propOrder = {
                            "record"
                    })
                    public static class PoLine{
                        /*报文为【RECODR】*/
                        @XmlElement(name = "RECODR",required = true,namespace = "http://www.aurora-framework.org/schema")
                        protected List<RECORD2> record;

                        @XmlAccessorType(XmlAccessType.FIELD)
                        @XmlType(name = "", propOrder = {
                                "PO_LINE_ID",
                                "PO_HEADER_ID",
                                "LINE_NUM",
                                "UNIT_PRICE",
                                "TAX_PRICE",
                                "USER_HOLD_FLAG",
                                "UN_NUMBER_ID",
                                "UNORDERED_FLAG",
                                "UNIT_MEAS_LOOKUP_CODE",
                                "TRANSACTION_REASON_CODE",
                                "TAX_NAME",
                                "TAXABLE_FLAG",
                                "QUANTITY_COMMITTED",
                                "QUANTITY",
                                "QTY_RCV_TOLERANCE",
                                "OVER_TOLERANCE_ERROR_FLAG",
                                "NOT_TO_EXCEED_PRICE",
                                "NOTE_TO_VENDOR",
                                "NEGOTIATED_BY_PREPARER_FLAG",
                                "MIN_ORDER_QUANTITY",
                                "MAX_ORDER_QUANTITY",
                                "MARKET_PRICE",
                                "LIST_PRICE_PER_UNIT",
                                "ITEM_REVISION",
                                "ITEM_ID",
                                "ITEM_CODE",
                                "ITEM_TYPE",
                                "ITEM_DESCRIPTION",
                                "ITEM_CATEGORY",
                                "AGENCY_CODE",
                                "AGENCY_NAME",
                                "LOTS_NUM",
                                "LOTS_VALIDITY_DATE",
                                "HAZARD_CLASS_ID",
                                "FROM_LINE_ID",
                                "FROM_HEADER_ID",
                                "FIRM_STATUS_LOOKUP_CODE",
                                "FIRM_DATE",
                                "COMMITTED_AMOUNT",
                                "CLOSED_REASON",
                                "CLOSED_FLAG",
                                "CLOSED_DATE",
                                "CATEGORY_ID",
                                "CAPITAL_EXPENSE_FLAG",
                                "CANCEL_REASON",
                                "CANCEL_FLAG",
                                "CANCEL_DATE",
                                "CANCELLED_BY",
                                "ATTRIBUTE11",
                                "ATTRIBUTE10",
                                "ALLOW_PRICE_OVERRIDE_FLAG",
                                "ERP_PO_LINE_TYPE",
                                "ERP_PO_LINE_TYPE_DESC",
                                "APPROVED_DATE",
                                "ERP_CREATION_DATE",
                                "ERP_LAST_UPDATE_DATE",
                                "LINE_TYPE_CODE",
                                "INV_ORGANIZATION_ID",
                                "INV_ORGANIZATION_CODE",
                                "IS_RETURNED_FLAG",
                                "IS_FREE_FLAG",
                                "IS_IMMED_SHIPPED_FLAG",
                                "SHIP_TO_LOCATION_ADDRESS",
                                "CONTACTS_INFO",
                                "ACTUAL_RECEIVER_CODE",
                                "ACTUAL_RECEIVER_NAME",
                                "CURRENCY_CODE",
                                "RATE_TYPE",
                                "RATE_DATE",
                                "RATE",
                                "NEED_BY_DATE",
                                "PROMISE_DATE",
                                "CUSTOMER_REQUIREMENT",
                                "CONTRACT_NUMBER",
                                "SOURCE_PO_NUM",
                                "SOURCE_PO_LINE_NUM",
                                "PRIMARY_UNIT",
                                "PRIMARY_QUANTITY",
                                "C_ATTRIBUTE1",
                                "C_ATTRIBUTE2",
                                "C_ATTRIBUTE3",
                                "C_ATTRIBUTE4",
                                "C_ATTRIBUTE5",
                                "C_ATTRIBUTE6",
                                "C_ATTRIBUTE7",
                                "C_ATTRIBUTE8",
                                "C_ATTRIBUTE9",
                                "C_ATTRIBUTE10",
                                "C_ATTRIBUTE11",
                                "C_ATTRIBUTE12",
                                "C_ATTRIBUTE13",
                                "C_ATTRIBUTE14",
                                "C_ATTRIBUTE15",
                                "C_ATTRIBUTE16",
                                "C_ATTRIBUTE17",
                                "C_ATTRIBUTE18",
                                "C_ATTRIBUTE19",
                                "C_ATTRIBUTE20",
                                "C_ATTRIBUTE21",
                                "C_ATTRIBUTE22",
                                "C_ATTRIBUTE23",
                                "C_ATTRIBUTE24",
                                "C_ATTRIBUTE25",
                                "C_ATTRIBUTE26",
                                "C_ATTRIBUTE27",
                                "C_ATTRIBUTE28",
                                "C_ATTRIBUTE29",
                                "C_ATTRIBUTE30",
                                "C_ATTRIBUTE31",
                                "C_ATTRIBUTE32",
                                "C_ATTRIBUTE33",
                                "C_ATTRIBUTE34",
                                "C_ATTRIBUTE35",
                                "C_ATTRIBUTE36",
                                "C_ATTRIBUTE37",
                                "C_ATTRIBUTE38",
                                "C_ATTRIBUTE39",
                                "C_ATTRIBUTE40",
                                "C_ATTRIBUTE41",
                                "C_ATTRIBUTE42",
                                "C_ATTRIBUTE43",
                                "C_ATTRIBUTE44",
                                "C_ATTRIBUTE45",
                                "C_ATTRIBUTE46",
                                "C_ATTRIBUTE47",
                                "C_ATTRIBUTE48",
                                "C_ATTRIBUTE49",
                                "C_ATTRIBUTE50",
                                "PO_LINE_LOCATION"
                        })
                        public static class RECORD2{
                            @XmlElement(name = "PO_LINE_ID", namespace = "http://www.aurora-framework.org/schema", required = false)
                            protected String PO_LINE_ID;

                            @XmlElement(name = "PO_HEADER_ID", namespace = "http://www.aurora-framework.org/schema", required = false)
                            protected String PO_HEADER_ID;

                            @XmlElement(name = "LINE_NUM", namespace = "http://www.aurora-framework.org/schema", required = false)
                            protected String LINE_NUM;

                            @XmlElement(name = "UNIT_PRICE", namespace = "http://www.aurora-framework.org/schema", required = false)
                            protected String UNIT_PRICE;

                            @XmlElement(name = "TAX_PRICE", namespace = "http://www.aurora-framework.org/schema", required = false)
                            protected String TAX_PRICE;

                            @XmlElement(name = "USER_HOLD_FLAG", namespace = "http://www.aurora-framework.org/schema", required = false)
                            protected String USER_HOLD_FLAG;

                            @XmlElement(name = "UN_NUMBER_ID", namespace = "http://www.aurora-framework.org/schema", required = false)
                            protected String UN_NUMBER_ID;

                            @XmlElement(name = "UNORDERED_FLAG", namespace = "http://www.aurora-framework.org/schema", required = false)
                            protected String UNORDERED_FLAG;

                            @XmlElement(name = "UNIT_MEAS_LOOKUP_CODE", namespace = "http://www.aurora-framework.org/schema", required = false)
                            protected String UNIT_MEAS_LOOKUP_CODE;

                            @XmlElement(name = "TRANSACTION_REASON_CODE", namespace = "http://www.aurora-framework.org/schema", required = false)
                            protected String TRANSACTION_REASON_CODE;

                            @XmlElement(name = "TAX_NAME", namespace = "http://www.aurora-framework.org/schema", required = false)
                            protected String TAX_NAME;

                            @XmlElement(name = "TAXABLE_FLAG", namespace = "http://www.aurora-framework.org/schema", required = false)
                            protected String TAXABLE_FLAG;

                            @XmlElement(name = "QUANTITY_COMMITTED", namespace = "http://www.aurora-framework.org/schema", required = false)
                            protected String QUANTITY_COMMITTED;

                            @XmlElement(name = "QUANTITY", namespace = "http://www.aurora-framework.org/schema", required = false)
                            protected String QUANTITY;

                            @XmlElement(name = "QTY_RCV_TOLERANCE", namespace = "http://www.aurora-framework.org/schema", required = false)
                            protected String QTY_RCV_TOLERANCE;

                            @XmlElement(name = "OVER_TOLERANCE_ERROR_FLAG", namespace = "http://www.aurora-framework.org/schema", required = false)
                            protected String OVER_TOLERANCE_ERROR_FLAG;

                            @XmlElement(name = "NOT_TO_EXCEED_PRICE", namespace = "http://www.aurora-framework.org/schema", required = false)
                            protected String NOT_TO_EXCEED_PRICE;

                            @XmlElement(name = "NOTE_TO_VENDOR", namespace = "http://www.aurora-framework.org/schema", required = false)
                            protected String NOTE_TO_VENDOR;

                            @XmlElement(name = "NEGOTIATED_BY_PREPARER_FLAG", namespace = "http://www.aurora-framework.org/schema", required = false)
                            protected String NEGOTIATED_BY_PREPARER_FLAG;

                            @XmlElement(name = "MIN_ORDER_QUANTITY", namespace = "http://www.aurora-framework.org/schema", required = false)
                            protected String MIN_ORDER_QUANTITY;

                            @XmlElement(name = "MAX_ORDER_QUANTITY", namespace = "http://www.aurora-framework.org/schema", required = false)
                            protected String MAX_ORDER_QUANTITY;

                            @XmlElement(name = "MARKET_PRICE", namespace = "http://www.aurora-framework.org/schema", required = false)
                            protected String MARKET_PRICE;

                            @XmlElement(name = "LIST_PRICE_PER_UNIT", namespace = "http://www.aurora-framework.org/schema", required = false)
                            protected String LIST_PRICE_PER_UNIT;

                            @XmlElement(name = "ITEM_REVISION", namespace = "http://www.aurora-framework.org/schema", required = false)
                            protected String ITEM_REVISION;

                            @XmlElement(name = "ITEM_ID", namespace = "http://www.aurora-framework.org/schema", required = false)
                            protected String ITEM_ID;

                            @XmlElement(name = "ITEM_CODE", namespace = "http://www.aurora-framework.org/schema", required = false)
                            protected String ITEM_CODE;

                            @XmlElement(name = "ITEM_TYPE", namespace = "http://www.aurora-framework.org/schema", required = false)
                            protected String ITEM_TYPE;

                            @XmlElement(name = "ITEM_DESCRIPTION", namespace = "http://www.aurora-framework.org/schema", required = false)
                            protected String ITEM_DESCRIPTION;

                            @XmlElement(name = "ITEM_CATEGORY", namespace = "http://www.aurora-framework.org/schema", required = false)
                            protected String ITEM_CATEGORY;

                            @XmlElement(name = "AGENCY_CODE", namespace = "http://www.aurora-framework.org/schema", required = false)
                            protected String AGENCY_CODE;

                            @XmlElement(name = "AGENCY_NAME", namespace = "http://www.aurora-framework.org/schema", required = false)
                            protected String AGENCY_NAME;

                            @XmlElement(name = "LOTS_NUM", namespace = "http://www.aurora-framework.org/schema", required = false)
                            protected String LOTS_NUM;

                            @XmlElement(name = "LOTS_VALIDITY_DATE", namespace = "http://www.aurora-framework.org/schema", required = false)
                            protected String LOTS_VALIDITY_DATE;

                            @XmlElement(name = "HAZARD_CLASS_ID", namespace = "http://www.aurora-framework.org/schema", required = false)
                            protected String HAZARD_CLASS_ID;

                            @XmlElement(name = "FROM_LINE_ID", namespace = "http://www.aurora-framework.org/schema", required = false)
                            protected String FROM_LINE_ID;

                            @XmlElement(name = "FROM_HEADER_ID", namespace = "http://www.aurora-framework.org/schema", required = false)
                            protected String FROM_HEADER_ID;

                            @XmlElement(name = "FIRM_STATUS_LOOKUP_CODE", namespace = "http://www.aurora-framework.org/schema", required = false)
                            protected String FIRM_STATUS_LOOKUP_CODE;

                            @XmlElement(name = "FIRM_DATE", namespace = "http://www.aurora-framework.org/schema", required = false)
                            protected String FIRM_DATE;

                            @XmlElement(name = "COMMITTED_AMOUNT", namespace = "http://www.aurora-framework.org/schema", required = false)
                            protected String COMMITTED_AMOUNT;

                            @XmlElement(name = "CLOSED_REASON", namespace = "http://www.aurora-framework.org/schema", required = false)
                            protected String CLOSED_REASON;

                            @XmlElement(name = "CLOSED_FLAG", namespace = "http://www.aurora-framework.org/schema", required = false)
                            protected String CLOSED_FLAG;

                            @XmlElement(name = "CLOSED_DATE", namespace = "http://www.aurora-framework.org/schema", required = false)
                            protected String CLOSED_DATE;

                            @XmlElement(name = "CATEGORY_ID", namespace = "http://www.aurora-framework.org/schema", required = false)
                            protected String CATEGORY_ID;

                            @XmlElement(name = "CAPITAL_EXPENSE_FLAG", namespace = "http://www.aurora-framework.org/schema", required = false)
                            protected String CAPITAL_EXPENSE_FLAG;

                            @XmlElement(name = "CANCEL_REASON", namespace = "http://www.aurora-framework.org/schema", required = false)
                            protected String CANCEL_REASON;

                            @XmlElement(name = "CANCEL_FLAG", namespace = "http://www.aurora-framework.org/schema", required = false)
                            protected String CANCEL_FLAG;

                            @XmlElement(name = "CANCEL_DATE", namespace = "http://www.aurora-framework.org/schema", required = false)
                            protected String CANCEL_DATE;

                            @XmlElement(name = "CANCELLED_BY", namespace = "http://www.aurora-framework.org/schema", required = false)
                            protected String CANCELLED_BY;

                            @XmlElement(name = "ATTRIBUTE11", namespace = "http://www.aurora-framework.org/schema", required = false)
                            protected String ATTRIBUTE11;

                            @XmlElement(name = "ATTRIBUTE10", namespace = "http://www.aurora-framework.org/schema", required = false)
                            protected String ATTRIBUTE10;

                            @XmlElement(name = "ALLOW_PRICE_OVERRIDE_FLAG", namespace = "http://www.aurora-framework.org/schema", required = false)
                            protected String ALLOW_PRICE_OVERRIDE_FLAG;

                            @XmlElement(name = "ERP_PO_LINE_TYPE", namespace = "http://www.aurora-framework.org/schema", required = false)
                            protected String ERP_PO_LINE_TYPE;

                            @XmlElement(name = "ERP_PO_LINE_TYPE_DESC", namespace = "http://www.aurora-framework.org/schema", required = false)
                            protected String ERP_PO_LINE_TYPE_DESC;

                            @XmlElement(name = "APPROVED_DATE", namespace = "http://www.aurora-framework.org/schema", required = false)
                            protected String APPROVED_DATE;

                            @XmlElement(name = "ERP_CREATION_DATE", namespace = "http://www.aurora-framework.org/schema", required = false)
                            protected String ERP_CREATION_DATE;

                            @XmlElement(name = "ERP_LAST_UPDATE_DATE", namespace = "http://www.aurora-framework.org/schema", required = false)
                            protected String ERP_LAST_UPDATE_DATE;

                            @XmlElement(name = "LINE_TYPE_CODE", namespace = "http://www.aurora-framework.org/schema", required = false)
                            protected String LINE_TYPE_CODE;

                            @XmlElement(name = "INV_ORGANIZATION_ID", namespace = "http://www.aurora-framework.org/schema", required = false)
                            protected String INV_ORGANIZATION_ID;

                            @XmlElement(name = "INV_ORGANIZATION_CODE", namespace = "http://www.aurora-framework.org/schema", required = false)
                            protected String INV_ORGANIZATION_CODE;

                            @XmlElement(name = "IS_RETURNED_FLAG", namespace = "http://www.aurora-framework.org/schema", required = false)
                            protected String IS_RETURNED_FLAG;

                            @XmlElement(name = "IS_FREE_FLAG", namespace = "http://www.aurora-framework.org/schema", required = false)
                            protected String IS_FREE_FLAG;

                            @XmlElement(name = "IS_IMMED_SHIPPED_FLAG", namespace = "http://www.aurora-framework.org/schema", required = false)
                            protected String IS_IMMED_SHIPPED_FLAG;

                            @XmlElement(name = "SHIP_TO_LOCATION_ADDRESS", namespace = "http://www.aurora-framework.org/schema", required = false)
                            protected String SHIP_TO_LOCATION_ADDRESS;

                            @XmlElement(name = "CONTACTS_INFO", namespace = "http://www.aurora-framework.org/schema", required = false)
                            protected String CONTACTS_INFO;

                            @XmlElement(name = "ACTUAL_RECEIVER_CODE", namespace = "http://www.aurora-framework.org/schema", required = false)
                            protected String ACTUAL_RECEIVER_CODE;

                            @XmlElement(name = "ACTUAL_RECEIVER_NAME", namespace = "http://www.aurora-framework.org/schema", required = false)
                            protected String ACTUAL_RECEIVER_NAME;

                            @XmlElement(name = "CURRENCY_CODE", namespace = "http://www.aurora-framework.org/schema", required = false)
                            protected String CURRENCY_CODE;

                            @XmlElement(name = "RATE_TYPE", namespace = "http://www.aurora-framework.org/schema", required = false)
                            protected String RATE_TYPE;

                            @XmlElement(name = "RATE_DATE", namespace = "http://www.aurora-framework.org/schema", required = false)
                            protected String RATE_DATE;

                            @XmlElement(name = "RATE", namespace = "http://www.aurora-framework.org/schema", required = false)
                            protected String RATE;

                            @XmlElement(name = "NEED_BY_DATE", namespace = "http://www.aurora-framework.org/schema", required = false)
                            protected String NEED_BY_DATE;

                            @XmlElement(name = "PROMISE_DATE", namespace = "http://www.aurora-framework.org/schema", required = false)
                            protected String PROMISE_DATE;

                            @XmlElement(name = "CUSTOMER_REQUIREMENT", namespace = "http://www.aurora-framework.org/schema", required = false)
                            protected String CUSTOMER_REQUIREMENT;

                            @XmlElement(name = "CONTRACT_NUMBER", namespace = "http://www.aurora-framework.org/schema", required = false)
                            protected String CONTRACT_NUMBER;

                            @XmlElement(name = "SOURCE_PO_NUM", namespace = "http://www.aurora-framework.org/schema", required = false)
                            protected String SOURCE_PO_NUM;

                            @XmlElement(name = "SOURCE_PO_LINE_NUM", namespace = "http://www.aurora-framework.org/schema", required = false)
                            protected String SOURCE_PO_LINE_NUM;

                            @XmlElement(name = "PRIMARY_UNIT", namespace = "http://www.aurora-framework.org/schema", required = false)
                            protected String PRIMARY_UNIT;

                            @XmlElement(name = "PRIMARY_QUANTITY", namespace = "http://www.aurora-framework.org/schema", required = false)
                            protected String PRIMARY_QUANTITY;

                            @XmlElement(name = "C_ATTRIBUTE1", namespace = "http://www.aurora-framework.org/schema", required = false)
                            protected String C_ATTRIBUTE1;

                            @XmlElement(name = "C_ATTRIBUTE2", namespace = "http://www.aurora-framework.org/schema", required = false)
                            protected String C_ATTRIBUTE2;

                            @XmlElement(name = "C_ATTRIBUTE3", namespace = "http://www.aurora-framework.org/schema", required = false)
                            protected String C_ATTRIBUTE3;

                            @XmlElement(name = "C_ATTRIBUTE4", namespace = "http://www.aurora-framework.org/schema", required = false)
                            protected String C_ATTRIBUTE4;

                            @XmlElement(name = "C_ATTRIBUTE5", namespace = "http://www.aurora-framework.org/schema", required = false)
                            protected String C_ATTRIBUTE5;

                            @XmlElement(name = "C_ATTRIBUTE6", namespace = "http://www.aurora-framework.org/schema", required = false)
                            protected String C_ATTRIBUTE6;

                            @XmlElement(name = "C_ATTRIBUTE7", namespace = "http://www.aurora-framework.org/schema", required = false)
                            protected String C_ATTRIBUTE7;

                            @XmlElement(name = "C_ATTRIBUTE8", namespace = "http://www.aurora-framework.org/schema", required = false)
                            protected String C_ATTRIBUTE8;

                            @XmlElement(name = "C_ATTRIBUTE9", namespace = "http://www.aurora-framework.org/schema", required = false)
                            protected String C_ATTRIBUTE9;

                            @XmlElement(name = "C_ATTRIBUTE10", namespace = "http://www.aurora-framework.org/schema", required = false)
                            protected String C_ATTRIBUTE10;

                            @XmlElement(name = "C_ATTRIBUTE11", namespace = "http://www.aurora-framework.org/schema", required = false)
                            protected String C_ATTRIBUTE11;

                            @XmlElement(name = "C_ATTRIBUTE12", namespace = "http://www.aurora-framework.org/schema", required = false)
                            protected String C_ATTRIBUTE12;

                            @XmlElement(name = "C_ATTRIBUTE13", namespace = "http://www.aurora-framework.org/schema", required = false)
                            protected String C_ATTRIBUTE13;

                            @XmlElement(name = "C_ATTRIBUTE14", namespace = "http://www.aurora-framework.org/schema", required = false)
                            protected String C_ATTRIBUTE14;

                            @XmlElement(name = "C_ATTRIBUTE15", namespace = "http://www.aurora-framework.org/schema", required = false)
                            protected String C_ATTRIBUTE15;

                            @XmlElement(name = "C_ATTRIBUTE16", namespace = "http://www.aurora-framework.org/schema", required = false)
                            protected String C_ATTRIBUTE16;

                            @XmlElement(name = "C_ATTRIBUTE17", namespace = "http://www.aurora-framework.org/schema", required = false)
                            protected String C_ATTRIBUTE17;

                            @XmlElement(name = "C_ATTRIBUTE18", namespace = "http://www.aurora-framework.org/schema", required = false)
                            protected String C_ATTRIBUTE18;

                            @XmlElement(name = "C_ATTRIBUTE19", namespace = "http://www.aurora-framework.org/schema", required = false)
                            protected String C_ATTRIBUTE19;

                            @XmlElement(name = "C_ATTRIBUTE20", namespace = "http://www.aurora-framework.org/schema", required = false)
                            protected String C_ATTRIBUTE20;

                            @XmlElement(name = "C_ATTRIBUTE21", namespace = "http://www.aurora-framework.org/schema", required = false)
                            protected String C_ATTRIBUTE21;

                            @XmlElement(name = "C_ATTRIBUTE22", namespace = "http://www.aurora-framework.org/schema", required = false)
                            protected String C_ATTRIBUTE22;

                            @XmlElement(name = "C_ATTRIBUTE23", namespace = "http://www.aurora-framework.org/schema", required = false)
                            protected String C_ATTRIBUTE23;

                            @XmlElement(name = "C_ATTRIBUTE24", namespace = "http://www.aurora-framework.org/schema", required = false)
                            protected String C_ATTRIBUTE24;

                            @XmlElement(name = "C_ATTRIBUTE25", namespace = "http://www.aurora-framework.org/schema", required = false)
                            protected String C_ATTRIBUTE25;

                            @XmlElement(name = "C_ATTRIBUTE26", namespace = "http://www.aurora-framework.org/schema", required = false)
                            protected String C_ATTRIBUTE26;

                            @XmlElement(name = "C_ATTRIBUTE27", namespace = "http://www.aurora-framework.org/schema", required = false)
                            protected String C_ATTRIBUTE27;

                            @XmlElement(name = "C_ATTRIBUTE28", namespace = "http://www.aurora-framework.org/schema", required = false)
                            protected String C_ATTRIBUTE28;

                            @XmlElement(name = "C_ATTRIBUTE29", namespace = "http://www.aurora-framework.org/schema", required = false)
                            protected String C_ATTRIBUTE29;

                            @XmlElement(name = "C_ATTRIBUTE30", namespace = "http://www.aurora-framework.org/schema", required = false)
                            protected String C_ATTRIBUTE30;

                            @XmlElement(name = "C_ATTRIBUTE31", namespace = "http://www.aurora-framework.org/schema", required = false)
                            protected String C_ATTRIBUTE31;

                            @XmlElement(name = "C_ATTRIBUTE32", namespace = "http://www.aurora-framework.org/schema", required = false)
                            protected String C_ATTRIBUTE32;

                            @XmlElement(name = "C_ATTRIBUTE33", namespace = "http://www.aurora-framework.org/schema", required = false)
                            protected String C_ATTRIBUTE33;

                            @XmlElement(name = "C_ATTRIBUTE34", namespace = "http://www.aurora-framework.org/schema", required = false)
                            protected String C_ATTRIBUTE34;

                            @XmlElement(name = "C_ATTRIBUTE35", namespace = "http://www.aurora-framework.org/schema", required = false)
                            protected String C_ATTRIBUTE35;

                            @XmlElement(name = "C_ATTRIBUTE36", namespace = "http://www.aurora-framework.org/schema", required = false)
                            protected String C_ATTRIBUTE36;

                            @XmlElement(name = "C_ATTRIBUTE37", namespace = "http://www.aurora-framework.org/schema", required = false)
                            protected String C_ATTRIBUTE37;

                            @XmlElement(name = "C_ATTRIBUTE38", namespace = "http://www.aurora-framework.org/schema", required = false)
                            protected String C_ATTRIBUTE38;

                            @XmlElement(name = "C_ATTRIBUTE39", namespace = "http://www.aurora-framework.org/schema", required = false)
                            protected String C_ATTRIBUTE39;

                            @XmlElement(name = "C_ATTRIBUTE40", namespace = "http://www.aurora-framework.org/schema", required = false)
                            protected String C_ATTRIBUTE40;

                            @XmlElement(name = "C_ATTRIBUTE41", namespace = "http://www.aurora-framework.org/schema", required = false)
                            protected String C_ATTRIBUTE41;

                            @XmlElement(name = "C_ATTRIBUTE42", namespace = "http://www.aurora-framework.org/schema", required = false)
                            protected String C_ATTRIBUTE42;

                            @XmlElement(name = "C_ATTRIBUTE43", namespace = "http://www.aurora-framework.org/schema", required = false)
                            protected String C_ATTRIBUTE43;

                            @XmlElement(name = "C_ATTRIBUTE44", namespace = "http://www.aurora-framework.org/schema", required = false)
                            protected String C_ATTRIBUTE44;

                            @XmlElement(name = "C_ATTRIBUTE45", namespace = "http://www.aurora-framework.org/schema", required = false)
                            protected String C_ATTRIBUTE45;

                            @XmlElement(name = "C_ATTRIBUTE46", namespace = "http://www.aurora-framework.org/schema", required = false)
                            protected String C_ATTRIBUTE46;

                            @XmlElement(name = "C_ATTRIBUTE47", namespace = "http://www.aurora-framework.org/schema", required = false)
                            protected String C_ATTRIBUTE47;

                            @XmlElement(name = "C_ATTRIBUTE48", namespace = "http://www.aurora-framework.org/schema", required = false)
                            protected String C_ATTRIBUTE48;

                            @XmlElement(name = "C_ATTRIBUTE49", namespace = "http://www.aurora-framework.org/schema", required = false)
                            protected String C_ATTRIBUTE49;

                            @XmlElement(name = "C_ATTRIBUTE50", namespace = "http://www.aurora-framework.org/schema", required = false)
                            protected String C_ATTRIBUTE50;

                            @XmlElement(name = "PO_LINE_LOCATION", namespace = "http://www.aurora-framework.org/schema", required = false)
                            protected String PO_LINE_LOCATION;



                            public String getPO_LINE_ID() {
                                return PO_LINE_ID;
                            }

                            public void setPO_LINE_ID(String PO_LINE_ID) {
                                this.PO_LINE_ID = PO_LINE_ID;
                            }

                            public String getPO_HEADER_ID() {
                                return PO_HEADER_ID;
                            }

                            public void setPO_HEADER_ID(String PO_HEADER_ID) {
                                this.PO_HEADER_ID = PO_HEADER_ID;
                            }

                            public String getLINE_NUM() {
                                return LINE_NUM;
                            }

                            public void setLINE_NUM(String LINE_NUM) {
                                this.LINE_NUM = LINE_NUM;
                            }

                            public String getUNIT_PRICE() {
                                return UNIT_PRICE;
                            }

                            public void setUNIT_PRICE(String UNIT_PRICE) {
                                this.UNIT_PRICE = UNIT_PRICE;
                            }

                            public String getTAX_PRICE() {
                                return TAX_PRICE;
                            }

                            public void setTAX_PRICE(String TAX_PRICE) {
                                this.TAX_PRICE = TAX_PRICE;
                            }

                            public String getUSER_HOLD_FLAG() {
                                return USER_HOLD_FLAG;
                            }

                            public void setUSER_HOLD_FLAG(String USER_HOLD_FLAG) {
                                this.USER_HOLD_FLAG = USER_HOLD_FLAG;
                            }

                            public String getUN_NUMBER_ID() {
                                return UN_NUMBER_ID;
                            }

                            public void setUN_NUMBER_ID(String UN_NUMBER_ID) {
                                this.UN_NUMBER_ID = UN_NUMBER_ID;
                            }

                            public String getUNORDERED_FLAG() {
                                return UNORDERED_FLAG;
                            }

                            public void setUNORDERED_FLAG(String UNORDERED_FLAG) {
                                this.UNORDERED_FLAG = UNORDERED_FLAG;
                            }

                            public String getUNIT_MEAS_LOOKUP_CODE() {
                                return UNIT_MEAS_LOOKUP_CODE;
                            }

                            public void setUNIT_MEAS_LOOKUP_CODE(String UNIT_MEAS_LOOKUP_CODE) {
                                this.UNIT_MEAS_LOOKUP_CODE = UNIT_MEAS_LOOKUP_CODE;
                            }

                            public String getTRANSACTION_REASON_CODE() {
                                return TRANSACTION_REASON_CODE;
                            }

                            public void setTRANSACTION_REASON_CODE(String TRANSACTION_REASON_CODE) {
                                this.TRANSACTION_REASON_CODE = TRANSACTION_REASON_CODE;
                            }

                            public String getTAX_NAME() {
                                return TAX_NAME;
                            }

                            public void setTAX_NAME(String TAX_NAME) {
                                this.TAX_NAME = TAX_NAME;
                            }

                            public String getTAXABLE_FLAG() {
                                return TAXABLE_FLAG;
                            }

                            public void setTAXABLE_FLAG(String TAXABLE_FLAG) {
                                this.TAXABLE_FLAG = TAXABLE_FLAG;
                            }

                            public String getQUANTITY_COMMITTED() {
                                return QUANTITY_COMMITTED;
                            }

                            public void setQUANTITY_COMMITTED(String QUANTITY_COMMITTED) {
                                this.QUANTITY_COMMITTED = QUANTITY_COMMITTED;
                            }

                            public String getQUANTITY() {
                                return QUANTITY;
                            }

                            public void setQUANTITY(String QUANTITY) {
                                this.QUANTITY = QUANTITY;
                            }

                            public String getQTY_RCV_TOLERANCE() {
                                return QTY_RCV_TOLERANCE;
                            }

                            public void setQTY_RCV_TOLERANCE(String QTY_RCV_TOLERANCE) {
                                this.QTY_RCV_TOLERANCE = QTY_RCV_TOLERANCE;
                            }

                            public String getOVER_TOLERANCE_ERROR_FLAG() {
                                return OVER_TOLERANCE_ERROR_FLAG;
                            }

                            public void setOVER_TOLERANCE_ERROR_FLAG(String OVER_TOLERANCE_ERROR_FLAG) {
                                this.OVER_TOLERANCE_ERROR_FLAG = OVER_TOLERANCE_ERROR_FLAG;
                            }

                            public String getNOT_TO_EXCEED_PRICE() {
                                return NOT_TO_EXCEED_PRICE;
                            }

                            public void setNOT_TO_EXCEED_PRICE(String NOT_TO_EXCEED_PRICE) {
                                this.NOT_TO_EXCEED_PRICE = NOT_TO_EXCEED_PRICE;
                            }

                            public String getNOTE_TO_VENDOR() {
                                return NOTE_TO_VENDOR;
                            }

                            public void setNOTE_TO_VENDOR(String NOTE_TO_VENDOR) {
                                this.NOTE_TO_VENDOR = NOTE_TO_VENDOR;
                            }

                            public String getNEGOTIATED_BY_PREPARER_FLAG() {
                                return NEGOTIATED_BY_PREPARER_FLAG;
                            }

                            public void setNEGOTIATED_BY_PREPARER_FLAG(String NEGOTIATED_BY_PREPARER_FLAG) {
                                this.NEGOTIATED_BY_PREPARER_FLAG = NEGOTIATED_BY_PREPARER_FLAG;
                            }

                            public String getMIN_ORDER_QUANTITY() {
                                return MIN_ORDER_QUANTITY;
                            }

                            public void setMIN_ORDER_QUANTITY(String MIN_ORDER_QUANTITY) {
                                this.MIN_ORDER_QUANTITY = MIN_ORDER_QUANTITY;
                            }

                            public String getMAX_ORDER_QUANTITY() {
                                return MAX_ORDER_QUANTITY;
                            }

                            public void setMAX_ORDER_QUANTITY(String MAX_ORDER_QUANTITY) {
                                this.MAX_ORDER_QUANTITY = MAX_ORDER_QUANTITY;
                            }

                            public String getMARKET_PRICE() {
                                return MARKET_PRICE;
                            }

                            public void setMARKET_PRICE(String MARKET_PRICE) {
                                this.MARKET_PRICE = MARKET_PRICE;
                            }

                            public String getLIST_PRICE_PER_UNIT() {
                                return LIST_PRICE_PER_UNIT;
                            }

                            public void setLIST_PRICE_PER_UNIT(String LIST_PRICE_PER_UNIT) {
                                this.LIST_PRICE_PER_UNIT = LIST_PRICE_PER_UNIT;
                            }

                            public String getITEM_REVISION() {
                                return ITEM_REVISION;
                            }

                            public void setITEM_REVISION(String ITEM_REVISION) {
                                this.ITEM_REVISION = ITEM_REVISION;
                            }

                            public String getITEM_ID() {
                                return ITEM_ID;
                            }

                            public void setITEM_ID(String ITEM_ID) {
                                this.ITEM_ID = ITEM_ID;
                            }

                            public String getITEM_CODE() {
                                return ITEM_CODE;
                            }

                            public void setITEM_CODE(String ITEM_CODE) {
                                this.ITEM_CODE = ITEM_CODE;
                            }

                            public String getITEM_TYPE() {
                                return ITEM_TYPE;
                            }

                            public void setITEM_TYPE(String ITEM_TYPE) {
                                this.ITEM_TYPE = ITEM_TYPE;
                            }

                            public String getITEM_DESCRIPTION() {
                                return ITEM_DESCRIPTION;
                            }

                            public void setITEM_DESCRIPTION(String ITEM_DESCRIPTION) {
                                this.ITEM_DESCRIPTION = ITEM_DESCRIPTION;
                            }

                            public String getITEM_CATEGORY() {
                                return ITEM_CATEGORY;
                            }

                            public void setITEM_CATEGORY(String ITEM_CATEGORY) {
                                this.ITEM_CATEGORY = ITEM_CATEGORY;
                            }

                            public String getAGENCY_CODE() {
                                return AGENCY_CODE;
                            }

                            public void setAGENCY_CODE(String AGENCY_CODE) {
                                this.AGENCY_CODE = AGENCY_CODE;
                            }

                            public String getAGENCY_NAME() {
                                return AGENCY_NAME;
                            }

                            public void setAGENCY_NAME(String AGENCY_NAME) {
                                this.AGENCY_NAME = AGENCY_NAME;
                            }

                            public String getLOTS_NUM() {
                                return LOTS_NUM;
                            }

                            public void setLOTS_NUM(String LOTS_NUM) {
                                this.LOTS_NUM = LOTS_NUM;
                            }

                            public String getLOTS_VALIDITY_DATE() {
                                return LOTS_VALIDITY_DATE;
                            }

                            public void setLOTS_VALIDITY_DATE(String LOTS_VALIDITY_DATE) {
                                this.LOTS_VALIDITY_DATE = LOTS_VALIDITY_DATE;
                            }

                            public String getHAZARD_CLASS_ID() {
                                return HAZARD_CLASS_ID;
                            }

                            public void setHAZARD_CLASS_ID(String HAZARD_CLASS_ID) {
                                this.HAZARD_CLASS_ID = HAZARD_CLASS_ID;
                            }

                            public String getFROM_LINE_ID() {
                                return FROM_LINE_ID;
                            }

                            public void setFROM_LINE_ID(String FROM_LINE_ID) {
                                this.FROM_LINE_ID = FROM_LINE_ID;
                            }

                            public String getFROM_HEADER_ID() {
                                return FROM_HEADER_ID;
                            }

                            public void setFROM_HEADER_ID(String FROM_HEADER_ID) {
                                this.FROM_HEADER_ID = FROM_HEADER_ID;
                            }

                            public String getFIRM_STATUS_LOOKUP_CODE() {
                                return FIRM_STATUS_LOOKUP_CODE;
                            }

                            public void setFIRM_STATUS_LOOKUP_CODE(String FIRM_STATUS_LOOKUP_CODE) {
                                this.FIRM_STATUS_LOOKUP_CODE = FIRM_STATUS_LOOKUP_CODE;
                            }

                            public String getFIRM_DATE() {
                                return FIRM_DATE;
                            }

                            public void setFIRM_DATE(String FIRM_DATE) {
                                this.FIRM_DATE = FIRM_DATE;
                            }

                            public String getCOMMITTED_AMOUNT() {
                                return COMMITTED_AMOUNT;
                            }

                            public void setCOMMITTED_AMOUNT(String COMMITTED_AMOUNT) {
                                this.COMMITTED_AMOUNT = COMMITTED_AMOUNT;
                            }

                            public String getCLOSED_REASON() {
                                return CLOSED_REASON;
                            }

                            public void setCLOSED_REASON(String CLOSED_REASON) {
                                this.CLOSED_REASON = CLOSED_REASON;
                            }

                            public String getCLOSED_FLAG() {
                                return CLOSED_FLAG;
                            }

                            public void setCLOSED_FLAG(String CLOSED_FLAG) {
                                this.CLOSED_FLAG = CLOSED_FLAG;
                            }

                            public String getCLOSED_DATE() {
                                return CLOSED_DATE;
                            }

                            public void setCLOSED_DATE(String CLOSED_DATE) {
                                this.CLOSED_DATE = CLOSED_DATE;
                            }

                            public String getCATEGORY_ID() {
                                return CATEGORY_ID;
                            }

                            public void setCATEGORY_ID(String CATEGORY_ID) {
                                this.CATEGORY_ID = CATEGORY_ID;
                            }

                            public String getCAPITAL_EXPENSE_FLAG() {
                                return CAPITAL_EXPENSE_FLAG;
                            }

                            public void setCAPITAL_EXPENSE_FLAG(String CAPITAL_EXPENSE_FLAG) {
                                this.CAPITAL_EXPENSE_FLAG = CAPITAL_EXPENSE_FLAG;
                            }

                            public String getCANCEL_REASON() {
                                return CANCEL_REASON;
                            }

                            public void setCANCEL_REASON(String CANCEL_REASON) {
                                this.CANCEL_REASON = CANCEL_REASON;
                            }

                            public String getCANCEL_FLAG() {
                                return CANCEL_FLAG;
                            }

                            public void setCANCEL_FLAG(String CANCEL_FLAG) {
                                this.CANCEL_FLAG = CANCEL_FLAG;
                            }

                            public String getCANCEL_DATE() {
                                return CANCEL_DATE;
                            }

                            public void setCANCEL_DATE(String CANCEL_DATE) {
                                this.CANCEL_DATE = CANCEL_DATE;
                            }

                            public String getCANCELLED_BY() {
                                return CANCELLED_BY;
                            }

                            public void setCANCELLED_BY(String CANCELLED_BY) {
                                this.CANCELLED_BY = CANCELLED_BY;
                            }

                            public String getATTRIBUTE11() {
                                return ATTRIBUTE11;
                            }

                            public void setATTRIBUTE11(String ATTRIBUTE11) {
                                this.ATTRIBUTE11 = ATTRIBUTE11;
                            }

                            public String getATTRIBUTE10() {
                                return ATTRIBUTE10;
                            }

                            public void setATTRIBUTE10(String ATTRIBUTE10) {
                                this.ATTRIBUTE10 = ATTRIBUTE10;
                            }

                            public String getALLOW_PRICE_OVERRIDE_FLAG() {
                                return ALLOW_PRICE_OVERRIDE_FLAG;
                            }

                            public void setALLOW_PRICE_OVERRIDE_FLAG(String ALLOW_PRICE_OVERRIDE_FLAG) {
                                this.ALLOW_PRICE_OVERRIDE_FLAG = ALLOW_PRICE_OVERRIDE_FLAG;
                            }

                            public String getERP_PO_LINE_TYPE() {
                                return ERP_PO_LINE_TYPE;
                            }

                            public void setERP_PO_LINE_TYPE(String ERP_PO_LINE_TYPE) {
                                this.ERP_PO_LINE_TYPE = ERP_PO_LINE_TYPE;
                            }

                            public String getERP_PO_LINE_TYPE_DESC() {
                                return ERP_PO_LINE_TYPE_DESC;
                            }

                            public void setERP_PO_LINE_TYPE_DESC(String ERP_PO_LINE_TYPE_DESC) {
                                this.ERP_PO_LINE_TYPE_DESC = ERP_PO_LINE_TYPE_DESC;
                            }

                            public String getAPPROVED_DATE() {
                                return APPROVED_DATE;
                            }

                            public void setAPPROVED_DATE(String APPROVED_DATE) {
                                this.APPROVED_DATE = APPROVED_DATE;
                            }

                            public String getERP_CREATION_DATE() {
                                return ERP_CREATION_DATE;
                            }

                            public void setERP_CREATION_DATE(String ERP_CREATION_DATE) {
                                this.ERP_CREATION_DATE = ERP_CREATION_DATE;
                            }

                            public String getERP_LAST_UPDATE_DATE() {
                                return ERP_LAST_UPDATE_DATE;
                            }

                            public void setERP_LAST_UPDATE_DATE(String ERP_LAST_UPDATE_DATE) {
                                this.ERP_LAST_UPDATE_DATE = ERP_LAST_UPDATE_DATE;
                            }

                            public String getLINE_TYPE_CODE() {
                                return LINE_TYPE_CODE;
                            }

                            public void setLINE_TYPE_CODE(String LINE_TYPE_CODE) {
                                this.LINE_TYPE_CODE = LINE_TYPE_CODE;
                            }

                            public String getINV_ORGANIZATION_ID() {
                                return INV_ORGANIZATION_ID;
                            }

                            public void setINV_ORGANIZATION_ID(String INV_ORGANIZATION_ID) {
                                this.INV_ORGANIZATION_ID = INV_ORGANIZATION_ID;
                            }

                            public String getINV_ORGANIZATION_CODE() {
                                return INV_ORGANIZATION_CODE;
                            }

                            public void setINV_ORGANIZATION_CODE(String INV_ORGANIZATION_CODE) {
                                this.INV_ORGANIZATION_CODE = INV_ORGANIZATION_CODE;
                            }

                            public String getIS_RETURNED_FLAG() {
                                return IS_RETURNED_FLAG;
                            }

                            public void setIS_RETURNED_FLAG(String IS_RETURNED_FLAG) {
                                this.IS_RETURNED_FLAG = IS_RETURNED_FLAG;
                            }

                            public String getIS_FREE_FLAG() {
                                return IS_FREE_FLAG;
                            }

                            public void setIS_FREE_FLAG(String IS_FREE_FLAG) {
                                this.IS_FREE_FLAG = IS_FREE_FLAG;
                            }

                            public String getIS_IMMED_SHIPPED_FLAG() {
                                return IS_IMMED_SHIPPED_FLAG;
                            }

                            public void setIS_IMMED_SHIPPED_FLAG(String IS_IMMED_SHIPPED_FLAG) {
                                this.IS_IMMED_SHIPPED_FLAG = IS_IMMED_SHIPPED_FLAG;
                            }

                            public String getSHIP_TO_LOCATION_ADDRESS() {
                                return SHIP_TO_LOCATION_ADDRESS;
                            }

                            public void setSHIP_TO_LOCATION_ADDRESS(String SHIP_TO_LOCATION_ADDRESS) {
                                this.SHIP_TO_LOCATION_ADDRESS = SHIP_TO_LOCATION_ADDRESS;
                            }

                            public String getCONTACTS_INFO() {
                                return CONTACTS_INFO;
                            }

                            public void setCONTACTS_INFO(String CONTACTS_INFO) {
                                this.CONTACTS_INFO = CONTACTS_INFO;
                            }

                            public String getACTUAL_RECEIVER_CODE() {
                                return ACTUAL_RECEIVER_CODE;
                            }

                            public void setACTUAL_RECEIVER_CODE(String ACTUAL_RECEIVER_CODE) {
                                this.ACTUAL_RECEIVER_CODE = ACTUAL_RECEIVER_CODE;
                            }

                            public String getACTUAL_RECEIVER_NAME() {
                                return ACTUAL_RECEIVER_NAME;
                            }

                            public void setACTUAL_RECEIVER_NAME(String ACTUAL_RECEIVER_NAME) {
                                this.ACTUAL_RECEIVER_NAME = ACTUAL_RECEIVER_NAME;
                            }

                            public String getCURRENCY_CODE() {
                                return CURRENCY_CODE;
                            }

                            public void setCURRENCY_CODE(String CURRENCY_CODE) {
                                this.CURRENCY_CODE = CURRENCY_CODE;
                            }

                            public String getRATE_TYPE() {
                                return RATE_TYPE;
                            }

                            public void setRATE_TYPE(String RATE_TYPE) {
                                this.RATE_TYPE = RATE_TYPE;
                            }

                            public String getRATE_DATE() {
                                return RATE_DATE;
                            }

                            public void setRATE_DATE(String RATE_DATE) {
                                this.RATE_DATE = RATE_DATE;
                            }

                            public String getRATE() {
                                return RATE;
                            }

                            public void setRATE(String RATE) {
                                this.RATE = RATE;
                            }

                            public String getNEED_BY_DATE() {
                                return NEED_BY_DATE;
                            }

                            public void setNEED_BY_DATE(String NEED_BY_DATE) {
                                this.NEED_BY_DATE = NEED_BY_DATE;
                            }

                            public String getPROMISE_DATE() {
                                return PROMISE_DATE;
                            }

                            public void setPROMISE_DATE(String PROMISE_DATE) {
                                this.PROMISE_DATE = PROMISE_DATE;
                            }

                            public String getCUSTOMER_REQUIREMENT() {
                                return CUSTOMER_REQUIREMENT;
                            }

                            public void setCUSTOMER_REQUIREMENT(String CUSTOMER_REQUIREMENT) {
                                this.CUSTOMER_REQUIREMENT = CUSTOMER_REQUIREMENT;
                            }

                            public String getCONTRACT_NUMBER() {
                                return CONTRACT_NUMBER;
                            }

                            public void setCONTRACT_NUMBER(String CONTRACT_NUMBER) {
                                this.CONTRACT_NUMBER = CONTRACT_NUMBER;
                            }

                            public String getSOURCE_PO_NUM() {
                                return SOURCE_PO_NUM;
                            }

                            public void setSOURCE_PO_NUM(String SOURCE_PO_NUM) {
                                this.SOURCE_PO_NUM = SOURCE_PO_NUM;
                            }

                            public String getSOURCE_PO_LINE_NUM() {
                                return SOURCE_PO_LINE_NUM;
                            }

                            public void setSOURCE_PO_LINE_NUM(String SOURCE_PO_LINE_NUM) {
                                this.SOURCE_PO_LINE_NUM = SOURCE_PO_LINE_NUM;
                            }

                            public String getPRIMARY_UNIT() {
                                return PRIMARY_UNIT;
                            }

                            public void setPRIMARY_UNIT(String PRIMARY_UNIT) {
                                this.PRIMARY_UNIT = PRIMARY_UNIT;
                            }

                            public String getPRIMARY_QUANTITY() {
                                return PRIMARY_QUANTITY;
                            }

                            public void setPRIMARY_QUANTITY(String PRIMARY_QUANTITY) {
                                this.PRIMARY_QUANTITY = PRIMARY_QUANTITY;
                            }

                            public String getC_ATTRIBUTE1() {
                                return C_ATTRIBUTE1;
                            }

                            public void setC_ATTRIBUTE1(String c_ATTRIBUTE1) {
                                C_ATTRIBUTE1 = c_ATTRIBUTE1;
                            }

                            public String getC_ATTRIBUTE2() {
                                return C_ATTRIBUTE2;
                            }

                            public void setC_ATTRIBUTE2(String c_ATTRIBUTE2) {
                                C_ATTRIBUTE2 = c_ATTRIBUTE2;
                            }

                            public String getC_ATTRIBUTE3() {
                                return C_ATTRIBUTE3;
                            }

                            public void setC_ATTRIBUTE3(String c_ATTRIBUTE3) {
                                C_ATTRIBUTE3 = c_ATTRIBUTE3;
                            }

                            public String getC_ATTRIBUTE4() {
                                return C_ATTRIBUTE4;
                            }

                            public void setC_ATTRIBUTE4(String c_ATTRIBUTE4) {
                                C_ATTRIBUTE4 = c_ATTRIBUTE4;
                            }

                            public String getC_ATTRIBUTE5() {
                                return C_ATTRIBUTE5;
                            }

                            public void setC_ATTRIBUTE5(String c_ATTRIBUTE5) {
                                C_ATTRIBUTE5 = c_ATTRIBUTE5;
                            }

                            public String getC_ATTRIBUTE6() {
                                return C_ATTRIBUTE6;
                            }

                            public void setC_ATTRIBUTE6(String c_ATTRIBUTE6) {
                                C_ATTRIBUTE6 = c_ATTRIBUTE6;
                            }

                            public String getC_ATTRIBUTE7() {
                                return C_ATTRIBUTE7;
                            }

                            public void setC_ATTRIBUTE7(String c_ATTRIBUTE7) {
                                C_ATTRIBUTE7 = c_ATTRIBUTE7;
                            }

                            public String getC_ATTRIBUTE8() {
                                return C_ATTRIBUTE8;
                            }

                            public void setC_ATTRIBUTE8(String c_ATTRIBUTE8) {
                                C_ATTRIBUTE8 = c_ATTRIBUTE8;
                            }

                            public String getC_ATTRIBUTE9() {
                                return C_ATTRIBUTE9;
                            }

                            public void setC_ATTRIBUTE9(String c_ATTRIBUTE9) {
                                C_ATTRIBUTE9 = c_ATTRIBUTE9;
                            }

                            public String getC_ATTRIBUTE10() {
                                return C_ATTRIBUTE10;
                            }

                            public void setC_ATTRIBUTE10(String c_ATTRIBUTE10) {
                                C_ATTRIBUTE10 = c_ATTRIBUTE10;
                            }

                            public String getC_ATTRIBUTE11() {
                                return C_ATTRIBUTE11;
                            }

                            public void setC_ATTRIBUTE11(String c_ATTRIBUTE11) {
                                C_ATTRIBUTE11 = c_ATTRIBUTE11;
                            }

                            public String getC_ATTRIBUTE12() {
                                return C_ATTRIBUTE12;
                            }

                            public void setC_ATTRIBUTE12(String c_ATTRIBUTE12) {
                                C_ATTRIBUTE12 = c_ATTRIBUTE12;
                            }

                            public String getC_ATTRIBUTE13() {
                                return C_ATTRIBUTE13;
                            }

                            public void setC_ATTRIBUTE13(String c_ATTRIBUTE13) {
                                C_ATTRIBUTE13 = c_ATTRIBUTE13;
                            }

                            public String getC_ATTRIBUTE14() {
                                return C_ATTRIBUTE14;
                            }

                            public void setC_ATTRIBUTE14(String c_ATTRIBUTE14) {
                                C_ATTRIBUTE14 = c_ATTRIBUTE14;
                            }

                            public String getC_ATTRIBUTE15() {
                                return C_ATTRIBUTE15;
                            }

                            public void setC_ATTRIBUTE15(String c_ATTRIBUTE15) {
                                C_ATTRIBUTE15 = c_ATTRIBUTE15;
                            }

                            public String getC_ATTRIBUTE16() {
                                return C_ATTRIBUTE16;
                            }

                            public void setC_ATTRIBUTE16(String c_ATTRIBUTE16) {
                                C_ATTRIBUTE16 = c_ATTRIBUTE16;
                            }

                            public String getC_ATTRIBUTE17() {
                                return C_ATTRIBUTE17;
                            }

                            public void setC_ATTRIBUTE17(String c_ATTRIBUTE17) {
                                C_ATTRIBUTE17 = c_ATTRIBUTE17;
                            }

                            public String getC_ATTRIBUTE18() {
                                return C_ATTRIBUTE18;
                            }

                            public void setC_ATTRIBUTE18(String c_ATTRIBUTE18) {
                                C_ATTRIBUTE18 = c_ATTRIBUTE18;
                            }

                            public String getC_ATTRIBUTE19() {
                                return C_ATTRIBUTE19;
                            }

                            public void setC_ATTRIBUTE19(String c_ATTRIBUTE19) {
                                C_ATTRIBUTE19 = c_ATTRIBUTE19;
                            }

                            public String getC_ATTRIBUTE20() {
                                return C_ATTRIBUTE20;
                            }

                            public void setC_ATTRIBUTE20(String c_ATTRIBUTE20) {
                                C_ATTRIBUTE20 = c_ATTRIBUTE20;
                            }

                            public String getC_ATTRIBUTE21() {
                                return C_ATTRIBUTE21;
                            }

                            public void setC_ATTRIBUTE21(String c_ATTRIBUTE21) {
                                C_ATTRIBUTE21 = c_ATTRIBUTE21;
                            }

                            public String getC_ATTRIBUTE22() {
                                return C_ATTRIBUTE22;
                            }

                            public void setC_ATTRIBUTE22(String c_ATTRIBUTE22) {
                                C_ATTRIBUTE22 = c_ATTRIBUTE22;
                            }

                            public String getC_ATTRIBUTE23() {
                                return C_ATTRIBUTE23;
                            }

                            public void setC_ATTRIBUTE23(String c_ATTRIBUTE23) {
                                C_ATTRIBUTE23 = c_ATTRIBUTE23;
                            }

                            public String getC_ATTRIBUTE24() {
                                return C_ATTRIBUTE24;
                            }

                            public void setC_ATTRIBUTE24(String c_ATTRIBUTE24) {
                                C_ATTRIBUTE24 = c_ATTRIBUTE24;
                            }

                            public String getC_ATTRIBUTE25() {
                                return C_ATTRIBUTE25;
                            }

                            public void setC_ATTRIBUTE25(String c_ATTRIBUTE25) {
                                C_ATTRIBUTE25 = c_ATTRIBUTE25;
                            }

                            public String getC_ATTRIBUTE26() {
                                return C_ATTRIBUTE26;
                            }

                            public void setC_ATTRIBUTE26(String c_ATTRIBUTE26) {
                                C_ATTRIBUTE26 = c_ATTRIBUTE26;
                            }

                            public String getC_ATTRIBUTE27() {
                                return C_ATTRIBUTE27;
                            }

                            public void setC_ATTRIBUTE27(String c_ATTRIBUTE27) {
                                C_ATTRIBUTE27 = c_ATTRIBUTE27;
                            }

                            public String getC_ATTRIBUTE28() {
                                return C_ATTRIBUTE28;
                            }

                            public void setC_ATTRIBUTE28(String c_ATTRIBUTE28) {
                                C_ATTRIBUTE28 = c_ATTRIBUTE28;
                            }

                            public String getC_ATTRIBUTE29() {
                                return C_ATTRIBUTE29;
                            }

                            public void setC_ATTRIBUTE29(String c_ATTRIBUTE29) {
                                C_ATTRIBUTE29 = c_ATTRIBUTE29;
                            }

                            public String getC_ATTRIBUTE30() {
                                return C_ATTRIBUTE30;
                            }

                            public void setC_ATTRIBUTE30(String c_ATTRIBUTE30) {
                                C_ATTRIBUTE30 = c_ATTRIBUTE30;
                            }

                            public String getC_ATTRIBUTE31() {
                                return C_ATTRIBUTE31;
                            }

                            public void setC_ATTRIBUTE31(String c_ATTRIBUTE31) {
                                C_ATTRIBUTE31 = c_ATTRIBUTE31;
                            }

                            public String getC_ATTRIBUTE32() {
                                return C_ATTRIBUTE32;
                            }

                            public void setC_ATTRIBUTE32(String c_ATTRIBUTE32) {
                                C_ATTRIBUTE32 = c_ATTRIBUTE32;
                            }

                            public String getC_ATTRIBUTE33() {
                                return C_ATTRIBUTE33;
                            }

                            public void setC_ATTRIBUTE33(String c_ATTRIBUTE33) {
                                C_ATTRIBUTE33 = c_ATTRIBUTE33;
                            }

                            public String getC_ATTRIBUTE34() {
                                return C_ATTRIBUTE34;
                            }

                            public void setC_ATTRIBUTE34(String c_ATTRIBUTE34) {
                                C_ATTRIBUTE34 = c_ATTRIBUTE34;
                            }

                            public String getC_ATTRIBUTE35() {
                                return C_ATTRIBUTE35;
                            }

                            public void setC_ATTRIBUTE35(String c_ATTRIBUTE35) {
                                C_ATTRIBUTE35 = c_ATTRIBUTE35;
                            }

                            public String getC_ATTRIBUTE36() {
                                return C_ATTRIBUTE36;
                            }

                            public void setC_ATTRIBUTE36(String c_ATTRIBUTE36) {
                                C_ATTRIBUTE36 = c_ATTRIBUTE36;
                            }

                            public String getC_ATTRIBUTE37() {
                                return C_ATTRIBUTE37;
                            }

                            public void setC_ATTRIBUTE37(String c_ATTRIBUTE37) {
                                C_ATTRIBUTE37 = c_ATTRIBUTE37;
                            }

                            public String getC_ATTRIBUTE38() {
                                return C_ATTRIBUTE38;
                            }

                            public void setC_ATTRIBUTE38(String c_ATTRIBUTE38) {
                                C_ATTRIBUTE38 = c_ATTRIBUTE38;
                            }

                            public String getC_ATTRIBUTE39() {
                                return C_ATTRIBUTE39;
                            }

                            public void setC_ATTRIBUTE39(String c_ATTRIBUTE39) {
                                C_ATTRIBUTE39 = c_ATTRIBUTE39;
                            }

                            public String getC_ATTRIBUTE40() {
                                return C_ATTRIBUTE40;
                            }

                            public void setC_ATTRIBUTE40(String c_ATTRIBUTE40) {
                                C_ATTRIBUTE40 = c_ATTRIBUTE40;
                            }

                            public String getC_ATTRIBUTE41() {
                                return C_ATTRIBUTE41;
                            }

                            public void setC_ATTRIBUTE41(String c_ATTRIBUTE41) {
                                C_ATTRIBUTE41 = c_ATTRIBUTE41;
                            }

                            public String getC_ATTRIBUTE42() {
                                return C_ATTRIBUTE42;
                            }

                            public void setC_ATTRIBUTE42(String c_ATTRIBUTE42) {
                                C_ATTRIBUTE42 = c_ATTRIBUTE42;
                            }

                            public String getC_ATTRIBUTE43() {
                                return C_ATTRIBUTE43;
                            }

                            public void setC_ATTRIBUTE43(String c_ATTRIBUTE43) {
                                C_ATTRIBUTE43 = c_ATTRIBUTE43;
                            }

                            public String getC_ATTRIBUTE44() {
                                return C_ATTRIBUTE44;
                            }

                            public void setC_ATTRIBUTE44(String c_ATTRIBUTE44) {
                                C_ATTRIBUTE44 = c_ATTRIBUTE44;
                            }

                            public String getC_ATTRIBUTE45() {
                                return C_ATTRIBUTE45;
                            }

                            public void setC_ATTRIBUTE45(String c_ATTRIBUTE45) {
                                C_ATTRIBUTE45 = c_ATTRIBUTE45;
                            }

                            public String getC_ATTRIBUTE46() {
                                return C_ATTRIBUTE46;
                            }

                            public void setC_ATTRIBUTE46(String c_ATTRIBUTE46) {
                                C_ATTRIBUTE46 = c_ATTRIBUTE46;
                            }

                            public String getC_ATTRIBUTE47() {
                                return C_ATTRIBUTE47;
                            }

                            public void setC_ATTRIBUTE47(String c_ATTRIBUTE47) {
                                C_ATTRIBUTE47 = c_ATTRIBUTE47;
                            }

                            public String getC_ATTRIBUTE48() {
                                return C_ATTRIBUTE48;
                            }

                            public void setC_ATTRIBUTE48(String c_ATTRIBUTE48) {
                                C_ATTRIBUTE48 = c_ATTRIBUTE48;
                            }

                            public String getC_ATTRIBUTE49() {
                                return C_ATTRIBUTE49;
                            }

                            public void setC_ATTRIBUTE49(String c_ATTRIBUTE49) {
                                C_ATTRIBUTE49 = c_ATTRIBUTE49;
                            }

                            public String getC_ATTRIBUTE50() {
                                return C_ATTRIBUTE50;
                            }

                            public void setC_ATTRIBUTE50(String c_ATTRIBUTE50) {
                                C_ATTRIBUTE50 = c_ATTRIBUTE50;
                            }

                            public String getPO_LINE_LOCATION() {
                                return PO_LINE_LOCATION;
                            }

                            public void setPO_LINE_LOCATION(String PO_LINE_LOCATION) {
                                this.PO_LINE_LOCATION = PO_LINE_LOCATION;
                            }
                        }

                        public List<RECORD2> getRecord() {
                            return record;
                        }

                        public void setRecord(List<RECORD2> record) {
                            this.record = record;
                        }
                    }

                }

            }

        }


        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {
                "businessgroup",
                "systemcode",
                "requestid",
                "ifcatecode",
                "ifcode",
                "username",
                "password",
                "batchnum",
                "totalsegcount",
                "segnum"
        })
        public static class HEADER {

            @XmlElement(name = "BUSINESS_GROUP", required = true,namespace = "http://www.aurora-framework.org/schema")
            protected String businessgroup;
            @XmlElement(name = "SYSTEM_CODE", required = true,namespace = "http://www.aurora-framework.org/schema")
            protected String systemcode;
            @XmlElement(name = "REQUEST_ID", required = true,namespace = "http://www.aurora-framework.org/schema")
            protected String requestid;
            @XmlElement(name = "IF_CATE_CODE", required = true,namespace = "http://www.aurora-framework.org/schema")
            protected String ifcatecode;
            @XmlElement(name = "IF_CODE", required = true,namespace = "http://www.aurora-framework.org/schema")
            protected String ifcode;
            @XmlElement(name = "USER_NAME", required = true,namespace = "http://www.aurora-framework.org/schema")
            protected String username;
            @XmlElement(name = "PASSWORD", required = true,namespace = "http://www.aurora-framework.org/schema")
            protected String password;
            @XmlElement(name = "BATCH_NUM", required = true,namespace = "http://www.aurora-framework.org/schema")
            protected String batchnum;
            @XmlElement(name = "TOTAL_SEG_COUNT", required = true,namespace = "http://www.aurora-framework.org/schema")
            protected String totalsegcount;
            @XmlElement(name = "SEG_NUM", required = true,namespace = "http://www.aurora-framework.org/schema")
            protected String segnum;

            /**
             * 获取businessgroup属性的值。
             *
             * @return
             *     possible object is
             *     {@link String }
             *
             */
            public String getBUSINESSGROUP() {
                return businessgroup;
            }

            /**
             * 设置businessgroup属性的值。
             *
             * @param value
             *     allowed object is
             *     {@link String }
             *
             */
            public void setBUSINESSGROUP(String value) {
                this.businessgroup = value;
            }

            /**
             * 获取systemcode属性的值。
             *
             * @return
             *     possible object is
             *     {@link String }
             *
             */
            public String getSYSTEMCODE() {
                return systemcode;
            }

            /**
             * 设置systemcode属性的值。
             *
             * @param value
             *     allowed object is
             *     {@link String }
             *
             */
            public void setSYSTEMCODE(String value) {
                this.systemcode = value;
            }

            /**
             * 获取requestid属性的值。
             *
             * @return
             *     possible object is
             *     {@link String }
             *
             */
            public String getREQUESTID() {
                return requestid;
            }

            /**
             * 设置requestid属性的值。
             *
             * @param value
             *     allowed object is
             *     {@link String }
             *
             */
            public void setREQUESTID(String value) {
                this.requestid = value;
            }

            /**
             * 获取ifcatecode属性的值。
             *
             * @return
             *     possible object is
             *     {@link String }
             *
             */
            public String getIFCATECODE() {
                return ifcatecode;
            }

            /**
             * 设置ifcatecode属性的值。
             *
             * @param value
             *     allowed object is
             *     {@link String }
             *
             */
            public void setIFCATECODE(String value) {
                this.ifcatecode = value;
            }

            /**
             * 获取ifcode属性的值。
             *
             * @return
             *     possible object is
             *     {@link String }
             *
             */
            public String getIFCODE() {
                return ifcode;
            }

            /**
             * 设置ifcode属性的值。
             *
             * @param value
             *     allowed object is
             *     {@link String }
             *
             */
            public void setIFCODE(String value) {
                this.ifcode = value;
            }

            /**
             * 获取username属性的值。
             *
             * @return
             *     possible object is
             *     {@link String }
             *
             */
            public String getUSERNAME() {
                return username;
            }

            /**
             * 设置username属性的值。
             *
             * @param value
             *     allowed object is
             *     {@link String }
             *
             */
            public void setUSERNAME(String value) {
                this.username = value;
            }

            /**
             * 获取password属性的值。
             *
             * @return
             *     possible object is
             *     {@link String }
             *
             */
            public String getPASSWORD() {
                return password;
            }

            /**
             * 设置password属性的值。
             *
             * @param value
             *     allowed object is
             *     {@link String }
             *
             */
            public void setPASSWORD(String value) {
                this.password = value;
            }

            /**
             * 获取batchnum属性的值。
             *
             * @return
             *     possible object is
             *     {@link String }
             *
             */
            public String getBATCHNUM() {
                return batchnum;
            }

            /**
             * 设置batchnum属性的值。
             *
             * @param value
             *     allowed object is
             *     {@link String }
             *
             */
            public void setBATCHNUM(String value) {
                this.batchnum = value;
            }

            /**
             * 获取totalsegcount属性的值。
             *
             * @return
             *     possible object is
             *     {@link String }
             *
             */
            public String getTOTALSEGCOUNT() {
                return totalsegcount;
            }

            /**
             * 设置totalsegcount属性的值。
             *
             * @param value
             *     allowed object is
             *     {@link String }
             *
             */
            public void setTOTALSEGCOUNT(String value) {
                this.totalsegcount = value;
            }

            /**
             * 获取segnum属性的值。
             *
             * @return
             *     possible object is
             *     {@link String }
             *
             */
            public String getSEGNUM() {
                return segnum;
            }

            /**
             * 设置segnum属性的值。
             *
             * @param value
             *     allowed object is
             *     {@link String }
             *
             */
            public void setSEGNUM(String value) {
                this.segnum = value;
            }

        }

    }

}
