package com.midea.cloud.srm.model.base.soap.erp.suppliercooperate.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
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
 *  修改日期: 2020/8/21 11:50
 *  修改内容:
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD) //@XmlAccessorType(XmlAccessType.FIELD) 映射这个类中的所有字段到XML
@XmlType(name = "", propOrder = {
        "txnId","txnType","txnTypeName","txnDate","quantity",
        "unitOfMeasure","receiveNum","receiveLineNum","sourceDocCode","destTypeCode","receiverNum","receiverName",
        "parentTxnId","poHeaderId","poLineId","shipLineId","releaseLineId","distId","poNumber","poLineNum","shipLineNum",
        "price","currency","exchangeType","exchangeDate","exchangeRate","vendorNumber","vendorSiteCode","vendorId",
        "vendorSiteId","organizationId","organizationCode","subinv","locatorName","amount","itemNumber","itemDescr",
        "asnNumber","asnLineNum","operationUnitId","operationUnit","localCurrency","companyName","srmTxnType","invPeriodName",
        "consignType","attr1","attr2","attr3","attr4","attr5"
}) //propOrder 指定映射XML时的节点顺序，使用该属性时，必须列出JavaBean对象中的所有字段，否则会报错
@Data
@Accessors(chain = true)
public class WarehousingReturnDetailEntity {
    /**
     * 事务处理Id（Long）
     */
    @XmlElement(name = "TXN_ID", required = false)
    private String txnId;

    /**
     * 事务处理类型代码
     */
    @XmlElement(name = "TXN_TYPE", required = false)
    private String txnType;

    /**
     * 事务处理类型描述
     */
    @XmlElement(name = "TXN_TYPE_NAME", required = false)
    private String txnTypeName;

    /**
     * 事务处理时间
     */
    @XmlElement(name = "TXN_DATE", required = false)
    private String txnDate;

    /**
     * 数量（Long）
     */
    @XmlElement(name = "QUANTITY", required = false)
    private String quantity;

    /**
     * 计量单位
     */
    @XmlElement(name = "UNIT_OF_MEASURE", required = false)
    private String unitOfMeasure;

    /**
     * 接收号
     */
    @XmlElement(name = "RECEIVE_NUM", required = false)
    private String receiveNum;

    /**
     * 接收行号
     */
    @XmlElement(name = "RECEIVE_LINE_NUM", required = false)
    private String receiveLineNum;

    /**
     * 来源单据代码
     */
    @XmlElement(name = "SOURCE_DOC_CODE", required = false)
    private String sourceDocCode;

    /**
     * 目的地类型代码
     */
    @XmlElement(name = "DEST_TYPE_CODE", required = false)
    private String destTypeCode;

    /**
     * 接收人编号
     */
    @XmlElement(name = "RECEIVER_NUM", required = false)
    private String receiverNum;

    /**
     * 接收人姓名
     */
    @XmlElement(name = "RECEIVER_NAME", required = false)
    private String receiverName;

    /**
     * 父事务处理Id（Long）
     */
    @XmlElement(name = "PARENT_TXN_ID", required = false)
    private String parentTxnId;

    /**
     * 采购订单头Id（Long）
     */
    @XmlElement(name = "PO_HEADER_ID", required = false)
    private String poHeaderId;

    /**
     * 采购订单行Id(Long)
     */
    @XmlElement(name = "PO_LINE_ID", required = false)
    private String poLineId;

    /**
     * 发运行Id（Long）
     */
    @XmlElement(name = "SHIP_LINE_ID", required = false)
    private String shipLineId;

    /**
     * 发放行Id（Long）
     */
    @XmlElement(name = "RELEASE_LINE_ID", required = false)
    private String releaseLineId;

    /**
     * 分配行Id（Long）
     */
    @XmlElement(name = "DIST_ID", required = false)
    private String distId;

    /**
     * 采购订单编号
     */
    @XmlElement(name = "PO_NUMBER", required = false)
    private String poNumber;

    /**
     * 采购订单行号（Integer）
     */
    @XmlElement(name = "PO_LINE_NUM", required = false)
    private String poLineNum;

    /**
     * 采购发运行号（Long）
     */
    @XmlElement(name = "SHIP_LINE_NUM", required = false)
    private String shipLineNum;

    /**
     * 不含税单价（BigDecimal）
     */
    @XmlElement(name = "PRICE", required = false)
    private String price;

    /**
     * 采购币种
     */
    @XmlElement(name = "CURRENCY", required = false)
    private String currency;

    /**
     * 汇率类型
     */
    @XmlElement(name = "EXCHANGE_TYPE", required = false)
    private String exchangeType;

    /**
     * 汇率日期
     */
    @XmlElement(name = "EXCHANGE_DATE", required = false)
    private String exchangeDate;

    /**
     * 汇率(BigDecimal)
     */
    @XmlElement(name = "EXCHANGE_RATE", required = false)
    private String exchangeRate;

    /**
     * 供应商编码
     */
    @XmlElement(name = "VENDOR_NUMBER", required = false)
    private String vendorNumber;

    /**
     * 供应商地点名称
     */
    @XmlElement(name = "VENDOR_SITE_CODE", required = false)
    private String vendorSiteCode;

    /**
     * 供应商ID（Long）
     */
    @XmlElement(name = "VENDOR_ID", required = false)
    private String vendorId;

    /**
     * 供应商地点Id(Long)
     */
    @XmlElement(name = "VENDOR_SITE_ID", required = false)
    private String vendorSiteId;

    /**
     * 库存组织Id(Long)
     */
    @XmlElement(name = "ORGANIZATION_ID", required = false)
    private String organizationId;

    /**
     * 库存组织编码
     */
    @XmlElement(name = "ORGANIZATION_CODE", required = false)
    private String organizationCode;

    /**
     * 子库存
     */
    @XmlElement(name = "SUBINV", required = false)
    private String subinv;

    /**
     * 货位名称
     */
    @XmlElement(name = "LOCATOR_NAME", required = false)
    private String locatorName;

    /**
     * 金额(BigDecimal)
     */
    @XmlElement(name = "AMOUNT", required = false)
    private String amount;

    /**
     * 物料编码
     */
    @XmlElement(name = "ITEM_NUMBER", required = false)
    private String itemNumber;

    /**
     * 物料说明
     */
    @XmlElement(name = "ITEM_DESCR", required = false)
    private String itemDescr;

    /**
     * ASN编号
     */
    @XmlElement(name = "ASN_NUMBER", required = false)
    private String asnNumber;

    /**
     * ASN行编号
     */
    @XmlElement(name = "ASN_LINE_NUM", required = false)
    private String asnLineNum;

    /**
     * 业务实体Id(Long)
     */
    @XmlElement(name = "OPERATION_UNIT_ID", required = false)
    private String operationUnitId;

    /**
     * 业务实体名称
     */
    @XmlElement(name = "OPERATION_UNIT", required = false)
    private String operationUnit;

    /**
     * 本位币币种
     */
    @XmlElement(name = "LOCAL_CURRENCY", required = false)
    private String localCurrency;

    /**
     * 公司名称
     */
    @XmlElement(name = "COMPANY_NAME", required = false)
    private String companyName;

    /**
     * SRM事务类代码
     */
    @XmlElement(name = "SRM_TXN_TYPE", required = false)
    private String srmTxnType;

    /**
     * 库存期间名称
     */
    @XmlElement(name = "INV_PERIOD_NAME", required = false)
    private String invPeriodName;

    /**
     * 寄售类型
     */
    @XmlElement(name = "CONSIGN_TYPE", required = false)
    private String consignType;

    /**
     * 备用字段1
     */
    @XmlElement(name = "ATTR1", required = false)
    private String attr1;

    /**
     * 备用字段2
     */
    @XmlElement(name = "ATTR2", required = false)
    private String attr2;

    /**
     * 备用字段3
     */
    @XmlElement(name = "ATTR3", required = false)
    private String attr3;

    /**
     * 备用字段4
     */
    @XmlElement(name = "ATTR4", required = false)
    private String attr4;

    /**
     * 备用字段5
     */
    @XmlElement(name = "ATTR5", required = false)
    private String attr5;

}
