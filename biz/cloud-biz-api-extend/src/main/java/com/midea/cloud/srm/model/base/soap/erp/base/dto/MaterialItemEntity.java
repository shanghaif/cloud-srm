package com.midea.cloud.srm.model.base.soap.erp.base.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.xml.bind.annotation.*;
import java.util.List;

/**
 * <pre>
 *  物料接口表实体类
 * </pre>
 *
 * @author xiexh12@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/8/20 15:29
 *  修改内容:
 * </pre>
 */

@XmlAccessorType(XmlAccessType.FIELD) //@XmlAccessorType(XmlAccessType.FIELD) 映射这个类中的所有字段到XML
@XmlType(name = "", propOrder = {
        "orgCode","itemId","itemNumber","itemName","itemDescZhs","itemLongDescZhs","itemLongDescUs","primaryUnitOfMeasure","unitOfMeasure",
        "lotControl","shelfLifeDays","itemStatus","stockEnableFlag","purchasingEnableFlag","mustApproveVendor","defaultBuyerNum","defaultBuyer",
        "bomEnabledFlag","buildInWipFlag","customerOrderFlag","internalOrderFlag","invoicedEnableFlag","transactionEnableFlag",
        "receiveRoutingId","extendAttributes","attr1", "attr2","attr3","attr4","attr5","categorySets"

}) //propOrder 指定映射XML时的节点顺序，使用该属性时，必须列出JavaBean对象中的所有字段，否则会报错
@Data
@Accessors(chain = true)
public class MaterialItemEntity {

    /**
     * 组织
     */
    @XmlElement(name = "ORG_CODE", required = false)
    private String orgCode;

    /**
     * 物料ID
     */
    @XmlElement(name = "ITEM_ID", required = false)
    private String itemId;

    /**
     * 物料编码
     */
    @XmlElement(name = "ITEM_NUMBER", required = false)
    private String itemNumber;

    /**
     * 物料名称
     */
    @XmlElement(name = "ITEM_NAME", required = false)
    private String itemName;

    /**
     * 物料说明（ZHS）
     */
    @XmlElement(name = "ITEM_DESC_ZHS", required = false)
    private String itemDescZhs;

    /**
     * 物料长描述-ZHS
     */
    @XmlElement(name = "ITEM_LONG_DESC_ZHS", required = false)
    private String itemLongDescZhs;

    /**
     * 物料长描述-US
     */
    @XmlElement(name = "ITEM_LONG_DESC_US", required = false)
    private String itemLongDescUs;

    /**
     * 物料主单位
     */
    @XmlElement(name = "PRIMARY_UNIT_OF_MEASURE", required = false)
    private String primaryUnitOfMeasure;

    /**
     * 物料辅助单位
     */
    @XmlElement(name = "UNIT_OF_MEASURE", required = false)
    private String unitOfMeasure;

    /**
     * 批次控制（1、不控制；2存储期限；4、自定义；）
     */
    @XmlElement(name = "LOT_CONTROL", required = false)
    private String lotControl;

    /**
     * 存储期限
     */
    @XmlElement(name = "SHELF_LIFE_DAYS", required = false)
    private String shelfLifeDays;

    /**
     * 物料状态（无效、有效）
     */
    @XmlElement(name = "ITEM_STATUS", required = false)
    private String itemStatus;

    /**
     * 可存储（Y/N）
     */
    @XmlElement(name = "STOCK_ENABLE_FLAG", required = false)
    private String stockEnableFlag;

    /**
     * 可采购（Y/N）
     */
    @XmlElement(name = "PURCHASING_ENABLE_FLAG", required = false)
    private String purchasingEnableFlag;

    /**
     * 使用批准供应商（Y/N）
     */
    @XmlElement(name = "MUST_APPROVE_VENDOR", required = false)
    private String mustApproveVendor;

    /**
     * 默认采购员编码
     */
    @XmlElement(name = "DEFAULT_BUYER_NUM", required = false)
    private String defaultBuyerNum;

    /**
     * 默认采购员姓名
     */
    @XmlElement(name = "DEFAULT_BUYER", required = false)
    private String defaultBuyer;

    /**
     * 允许BOM（Y/N）
     */
    @XmlElement(name = "BOM_ENABLED_FLAG", required = false)
    private String bomEnabledFlag;

    /**
     * 在WIP中制造（Y/N）
     */
    @XmlElement(name = "BUILD_IN_WIP_FLAG", required = false)
    private String buildInWipFlag;

    /**
     * 启用客户订单（Y/N）
     */
    @XmlElement(name = "CUSTOMER_ORDER_FLAG", required = false)
    private String customerOrderFlag;

    /**
     * 启用内部订单（Y/N）
     */
    @XmlElement(name = "INTERNAL_ORDER_FLAG", required = false)
    private String internalOrderFlag;

    /**
     * 启用开票（Y/N）
     */
    @XmlElement(name = "INVOICED_ENABLE_FLAG", required = false)
    private String invoicedEnableFlag;

    /**
     * 可处理（Y/N）
     */
    @XmlElement(name = "TRANSACTION_ENABLE_FLAG", required = false)
    private String transactionEnableFlag;

    /**
     * 接收方式（1、标准；2、检验；3、直接）
     */
    @XmlElement(name = "RECEIVE_ROUTING_ID", required = false)
    private String receiveRoutingId;

    /**
     * 物料扩展属性
     */
    @XmlElement(name = "EXTEND_ATTRIBUTES", required = false)
    private String extendAttributes;

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

    /**
     * 类别集
     */
    @XmlElement(name = "CATEGORY_SET", required = false)
    private List<CategorySetsEntity> categorySets;


}