package com.midea.cloud.srm.model.suppliercooperate.order.entry;

import java.math.BigDecimal;

import com.baomidou.mybatisplus.annotation.*;

import java.util.Date;
import com.midea.cloud.srm.model.common.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
*  <pre>
 *  入库退货明细erp表 模型
 * </pre>
*
* @author chenwj92@meiCloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-08-25 14:30:44
 *  修改内容:
 * </pre>
*/
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("ceea_storage_return_erp")
public class WarehousingReturnDetailErp extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 入库退货明细erp id
     */
    @TableId("WAREHOUSING_RETURN_DETAIL_ERP_ID")
    private Long warehousingReturnDetailErpId;

    /**
     * 入库退货明细id
     */
    @TableField("WAREHOUSING_RETURN_DETAIL_ID")
    private Long warehousingReturnDetailId;

    /**
     * 处理顺序
     */
    @TableField("INSERT_SEQUENCE")
    private Integer insertSequence;

    /**
     * 事务处理Id
     */
    @TableField("TXN_ID")
    private Long txnId;

    /**
     * 事务处理类型代码
     */
    @TableField("TXN_TYPE")
    private String txnType;

    /**
     * 事务处理类型描述
     */
    @TableField("TXN_TYPE_NAME")
    private String txnTypeName;

    /**
     * 事务处理时间
     */
    @TableField("TXN_DATE")
    private Date txnDate;

    /**
     * 数量
     */
    @TableField("QUANTITY")
    private BigDecimal quantity;

    /**
     * 计量单位
     */
    @TableField("UNIT_OF_MEASURE")
    private String unitOfMeasure;

    /**
     * 接收号
     */
    @TableField("RECEIVE_NUM")
    private String receiveNum;

    /**
     * 接收行号
     */
    @TableField("RECEIVE_LINE_NUM")
    private String receiveLineNum;

    /**
     * 来源单据代码
     */
    @TableField("SOURCE_DOC_CODE")
    private String sourceDocCode;

    /**
     * 目的地类型代码
     */
    @TableField("DEST_TYPE_CODE")
    private String destTypeCode;

    /**
     * 接收人编号
     */
    @TableField("RECEIVER_NUM")
    private String receiverNum;

    /**
     * 接收人姓名
     */
    @TableField("RECEIVER_NAME")
    private String receiverName;

    /**
     * 父事务处理Id
     */
    @TableField("PARENT_TXN_ID")
    private Long parentTxnId;

    /**
     * 采购订单头Id
     */
    @TableField("PO_HEADER_ID")
    private Long poHeaderId;

    /**
     * 采购订单行Id
     */
    @TableField("PO_LINE_ID")
    private Long poLineId;

    /**
     * 发运行Id
     */
    @TableField("SHIP_LINE_ID")
    private Long shipLineId;

    /**
     * 发放行Id
     */
    @TableField("RELEASE_LINE_ID")
    private Long releaseLineId;

    /**
     * 分配行Id
     */
    @TableField("DIST_ID")
    private Long distId;

    /**
     * 采购订单编号
     */
    @TableField("PO_NUMBER")
    private String poNumber;

    /**
     * 采购订单行号
     */
    @TableField("PO_LINE_NUM")
    private Long poLineNum;

    /**
     * 采购发运行号
     */
    @TableField("SHIP_LINE_NUM")
    private Long shipLineNum;

    /**
     * 不含税单价
     */
    @TableField("PRICE")
    private BigDecimal price;

    /**
     * 采购币种
     */
    @TableField("CURRENCY")
    private String currency;

    /**
     * 汇率类型
     */
    @TableField("EXCHANGE_TYPE")
    private String exchangeType;

    /**
     * 汇率日期
     */
    @TableField("EXCHANGE_DATE")
    private Date exchangeDate;

    /**
     * 汇率
     */
    @TableField("EXCHANGE_RATE")
    private BigDecimal exchangeRate;

    /**
     * 供应商编码
     */
    @TableField("VENDOR_NUMBER")
    private String vendorNumber;

    /**s
     * 供应商地点名称
     */
    @TableField("VENDOR_SITE_CODE")
    private String vendorSiteCode;

    /**
     * 供应商ID
     */
    @TableField("VENDOR_ID")
    private Long vendorId;

    /**
     * 供应商地点Id
     */
    @TableField("VENDOR_SITE_ID")
    private Long vendorSiteId;

    /**
     * 库存组织Id
     */
    @TableField("ORGANIZATION_ID")
    private Long organizationId;

    /**
     * 库存组织编码
     */
    @TableField("ORGANIZATION_CODE")
    private String organizationCode;

    /**
     * 子库存
     */
    @TableField("SUBINV")
    private String subinv;

    /**
     * 货位名称
     */
    @TableField("LOCATOR_NAME")
    private String locatorName;

    /**
     * 金额
     */
    @TableField("AMOUNT")
    private BigDecimal amount;

    /**
     * 物料编码
     */
    @TableField("ITEM_NUMBER")
    private String itemNumber;

    /**
     * 物料说明
     */
    @TableField("ITEM_DESCR")
    private String itemDescr;

    /**
     * ASN编号
     */
    @TableField("ASN_NUMBER")
    private String asnNumber;

    /**
     * ASN行编号
     */
    @TableField("ASN_LINE_NUM")
    private String asnLineNum;

    /**
     * 业务实体Id
     */
    @TableField("OPERATION_UNIT_ID")
    private Long operationUnitId;

    /**
     * 业务实体名称
     */
    @TableField("OPERATION_UNIT")
    private String operationUnit;

    /**
     * 本位币币种
     */
    @TableField("LOCAL_CURRENCY")
    private String localCurrency;

    /**
     * 公司名称
     */
    @TableField("COMPANY_NAME")
    private String companyName;

    /**
     * SRM事务类代码
     */
    @TableField("SRM_TXN_TYPE")
    private String srmTxnType;

    /**
     * 库存期间名称
     */
    @TableField("INV_PERIOD_NAME")
    private String invPeriodName;

    /**
     * 寄售类型
     */
    @TableField("CONSIGN_TYPE")
    private String consignType;

    /**
     * 备用字段1
     */
    @TableField("ATTR1")
    private String attr1;

    /**
     * 备用字段4
     */
    @TableField("ATTR4")
    private String attr4;

    /**
     * 备用字段3
     */
    @TableField("ATTR3")
    private String attr3;

    /**
     * 备用字段2
     */
    @TableField("ATTR2")
    private String attr2;

    /**
     * 备用字段5
     */
    @TableField("ATTR5")
    private String attr5;

    /**
     * 创建人ID
     */
    @TableField(value = "CREATED_ID", fill = FieldFill.INSERT)
    private Long createdId;

    /**
     * 创建人
     */
    @TableField(value = "CREATED_BY", fill = FieldFill.INSERT)
    private String createdBy;

    /**
     * 创建日期
     */
    @TableField(value = "CREATION_DATE", fill = FieldFill.INSERT)
    private Date creationDate;

    /**
     * 创建人IP
     */
    @TableField(value = "CREATED_BY_IP", fill = FieldFill.INSERT)
    private String createdByIp;

    /**
     * 是否处理
     */
    @TableField("IF_Handle")
    private String ifHandle;

    /**
     * 当前定时器是否已经处理过
     */
    @TableField(value = "IF_HANDLE_NOW")
    private String ifHandleNow;


}
