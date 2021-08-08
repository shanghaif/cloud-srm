package com.midea.cloud.srm.model.cm.accept.entity;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;
import java.time.LocalDate;
import com.midea.cloud.srm.model.common.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
*  <pre>
 *  合同验收明细 模型
 * </pre>
*
* @author chensl26@meiCloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-08-13 10:52:45
 *  修改内容:
 * </pre>
*/
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("scc_contract_accept_detail")
public class AcceptDetail extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID,合同验收单明细ID
     */
    @TableId("ACCEPT_DETAIL_ID")
    private Long acceptDetailId;

    /**
     * 合同验收单ID
     */
    @TableField("ACCEPT_ORDER_ID")
    private Long acceptOrderId;

    /**
     * 合同头ID
     */
    @TableField("CONTRACT_HEAD_ID")
    private Long contractHeadId;


    /**
     * 送货单号
     */
    @TableField("DELIVERY_NUMBER")
    private String deliveryNumber;

    /**
     * 送货项次
     */
    @TableField("DELIVERY_LINE_NUM")
    private Long deliveryLineNum;

    /**
     * 规格类型
     */
    @TableField("SPECIFICATION")
    private String specification;

    /**
     * 送货数量
     */
    @TableField("DELIVERY_QUANTITY")
    private BigDecimal deliveryQuantity;

    /**
     * 入库数量
     */
    @TableField("WAREHOUSE_RECEIPT_QUANTITY")
    private BigDecimal warehouseReceiptQuantity;



    /**
     * 待验收数量
     */
    @TableField("WAIT_ACCEPT_QUANTITY")
    private BigDecimal waitAcceptQuantity;

    /**
     * 本次验收数量
     */
    @TableField("ACCEPT_QUANTITY")
    private BigDecimal acceptQuantity;

    /**
     * 收货日期
     */
    @TableField("ACCEPT_DATE")
    private LocalDate acceptDate;

    /**
     * 是否与合同相符
     */
    @TableField("IS_EQUAL_CONTRACT")
    private String isEqualContract;

    /**
     * 入库单号
     */
    @TableField("WAREHOUSE_RECEIPT_NUM")
    private String warehouseReceiptNum;

    /**
     * 入库单行号
     */
    @TableField("WAREHOUSE_LINE_NUM")
    private Long warehouseLineNum;

    /**
     * 发票号码
     */
    @TableField("INVOICE_NUMBER")
    private String invoiceNumber;

    /**
     * 采购订单号
     */
    @TableField("ORDER_NUMBER")
    private String orderNumber;

    /**
     * 采购订单行号
     */
    @TableField("ORDER_LINE_NUMBER")
    private Long orderLineNumber;

    /**
     * 收货备注
     */
    @TableField("ACCEPT_REMARK")
    private String acceptRemark;

    /**
     * 业务实体ID
     */
    @TableField("CEEA_ORG")
    private Long ceeaOrg;

    /**
     * 业务实体编码
     */
    @TableField("CEEA_ORG_CODE")
    private String ceeaOrgCode;

    /**
     * 业务实体名称
     */
    @TableField("CEEA_ORG_NAME")
    private String ceeaOrgName;

    /**
     * 采购员
     */
    @TableField("CEEA_BUYER")
    private String ceeaBuyer;

    /**
     * 物料小类
     */
    @TableField("CEEA_SMALL_CLASS_MAT")
    private String ceeaSmallClassMat;


    /**
     * 物料描述
     */
    @TableField("CEEA_DESC")
    private String ceeaDesc;

    /**
     * 未税单价
     */
    @TableField("CEEA_UNTAXED_PRICE")
    private BigDecimal ceeaUntaxedPrice;

    /**
     * 含税单价
     */
    @TableField("CEEA_UNIT_PRICE")
    private BigDecimal ceeaUnitPrice;

    /**
     * 税率
     */
    @TableField("CEEA_TAXRATE")
    private BigDecimal ceeaTaxrate;

    /**
     * 币种
     */
    @TableField("CEEA_CURRENCY")
    private String ceeaCurrency;

    /**
     * 采购申请号
     */
    @TableField("CEEA_REQUIREMENT_HEAD_NUM")
    private String ceeaRequirementHeadNum;

    /**
     * 申请行号
     */
    @TableField("CEEA_ROWNUM")
    private String ceeaRownum;


    /**
     * 验收申请单号
     */
    @TableField("CEEA_ACCEPT_APPLICATION_NUM")
    private String ceeaAcceptApplicationNum;
    /**
     * 物料ID
     */
    @TableField("MATERIAL_ID")
    private Long materialId;

    /**
     * 物料编码
     */
    @TableField("MATERIAL_CODE")
    private String materialCode;

    /**
     * 物料名称
     */
    @TableField("MATERIAL_NAME")
    private String materialName;

    /**
     * 单位
     */
    @TableField("CEEA_UNIT")
    private String ceeaUnit;

    /**
     * 重量
     */
    @TableField("CEEA_WEIGHT")
    private BigDecimal ceeaWeight;

    /**
     * 体积
     */
    @TableField("CEEA_BULK")
    private BigDecimal ceeaBulk;

    /**
     * 出厂编号
     */
    @TableField("CEEA_PRODUCTION_NUM")
    private String ceeaProductionNum;

    /**
     * 出厂日期
     */
    @TableField("CEEA_PRODUCTION_DATE")
    private Date ceeaProductionDate;

    /**
     * 立项流水号
     */
    @TableField("CEEA_PROJECT_APPROVAL_NUM")
    private String ceeaProjectApprovalNum;
    /**
     * 服务区域
     */
    @TableField("CEEA_SERVICE_ZONE")
    private String ceeaServiceZone;
    /**
     * 服务日期从
     */
    @TableField("CEEA_SERVICE_DATE_START")
    private LocalDate ceeaServiceDateStart;
    /**
     * 服务日期至
     */
    @TableField("CEEA_SERVICE_DATE_END")
    private LocalDate ceeaServiceDateEnd;

    /**
     * 生效日期(YYYY-MM-DD)
     */
    @TableField("START_DATE")
    private LocalDate startDate;

    /**
     * 失效日期(YYYY-MM-DD)
     */
    @TableField("END_DATE")
    private LocalDate endDate;

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
     * 创建时间
     */
    @TableField(value = "CREATION_DATE", fill = FieldFill.INSERT)
    private Date creationDate;

    /**
     * 创建人IP
     */
    @TableField(value = "CREATED_BY_IP", fill = FieldFill.INSERT)
    private String createdByIp;

    /**
     * 最后更新人ID
     */
    @TableField(value = "LAST_UPDATED_ID", fill = FieldFill.INSERT_UPDATE)
    private Long lastUpdatedId;

    /**
     * 最后更新人
     */
    @TableField(value = "LAST_UPDATED_BY", fill = FieldFill.INSERT_UPDATE)
    private String lastUpdatedBy;

    /**
     * 最后更新时间
     */
    @TableField(value = "LAST_UPDATE_DATE", fill = FieldFill.INSERT_UPDATE)
    private Date lastUpdateDate;

    /**
     * 最后更新人IP
     */
    @TableField(value = "LAST_UPDATED_BY_IP", fill = FieldFill.INSERT_UPDATE)
    private String lastUpdatedByIp;

    /**
     * 租户ID
     */
    @TableField("TENANT_ID")
    private Long tenantId;

    /**
     * 版本号
     */
    @TableField("VERSION")
    private Long version;
    /**
     * 采购申请单号
     */
    @TableField("CEEA_ORDER_DETAIL_ID")
    private Long orderDetailId;
    /**
     * 缺陷数量
     */
    @TableField("CEEA_DAMAGE_QUANTITY")
    private BigDecimal ceeaDamageQuantity;

}
