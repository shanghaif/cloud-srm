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
 *  合同验收行 模型
 * </pre>
*
* @author chensl26@meiCloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-08-13 10:53:39
 *  修改内容:
 * </pre>
*/
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("ceea_cont_accept_detail_line")
public class DetailLine extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID,验收行ID
     */
    @TableId("ACCEPT_DETAIL_LINE_ID")
    private Long acceptDetailLineId;

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
     * 业务实体
     */
    @TableField("ORG_ID")
    private Long orgId;

    /**
     * 业务实体编码
     */
    @TableField("ORG_CODE")
    private String orgCode;

    /**
     * 业务实体名称
     */
    @TableField("ORG_NAME")
    private String orgName;

    /**
     * 库存组织ID
     */
    @TableField("ORGANIZATION_ID")
    private Long organizationId;

    /**
     * 库存组织编码
     */
    @TableField("ORGANIZATION_CODE")
    private String organizationCode;

    /**
     * 库存组织名称
     */
    @TableField("ORGANIZATION_NAME")
    private String organizationName;

    /**
     * 采购员
     */
    @TableField("BUYER")
    private String buyer;

    /**
     * 物料小类
     */
    @TableField("SMALL_CLASS_MAT")
    private String smallClassMat;

    /**
     * 物料编码
     */
    @TableField("ITEM_CODE")
    private String itemCode;

    /**
     * 物料描述
     */
    @TableField("ITEM_LONG_DESC")
    private String itemLongDesc;

    /**
     * 单位
     */
    @TableField("UNIT")
    private String unit;

    /**
     * 订单数量
     */
    @TableField("DELIVERY_QUANTITY")
    private BigDecimal deliveryQuantity;

    /**
     * 入库数量
     */
    @TableField("WAREHOUSE_RECEIPT_QUANTITY")
    private BigDecimal warehouseReceiptQuantity;

    /**
     * 已验收数量(本地验收数量)
     */
    @TableField("ACCEPT_QUANTITY")
    private BigDecimal acceptQuantity;

    /**
     * 重量
     */
    @TableField("WEIGHT")
    private BigDecimal weight;

    /**
     * 体积
     */
    @TableField("BULK")
    private BigDecimal bulk;

    /**
     * 出厂编号
     */
    @TableField("PRODUCTION_NUM")
    private String productionNum;

    /**
     * 出厂日期
     */
    @TableField("PRODUCTION_DATE")
    private Date productionDate;

    /**
     * 立项流水号
     */
    @TableField("PROJECT_APPROVAL_NUM")
    private String projectApprovalNum;

    /**
     * 未税单价
     */
    @TableField("UNTAXED_PRICE")
    private BigDecimal untaxedPrice;

    /**
     * 含税单价
     */
    @TableField("UNIT_PRICE")
    private BigDecimal unitPrice;

    /**
     * 税率
     */
    @TableField("TAXRATE")
    private BigDecimal taxrate;

    /**
     * 币种
     */
    @TableField("CURRENCY")
    private String currency;

    /**
     * 采购申请号
     */
    @TableField("REQUIREMENT_HEAD_NUM")
    private String requirementHeadNum;

    /**
     * 申请行号
     */
    @TableField("ROWNUM")
    private String rownum;

    /**
     * 验收申请单号
     */
    @TableField("ACCEPT_APPLICATION_NUM")
    private String acceptApplicationNum;

    /**
     * 合同编号
     */
    @TableField("CONTRACT_NO")
    private String contractNo;

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

    private BigDecimal remainingQuantity;

}
