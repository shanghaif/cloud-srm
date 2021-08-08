package com.midea.cloud.srm.model.suppliercooperate.deliver.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.midea.cloud.srm.model.common.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
*  <pre>
 *  到货计划维护表 模型
 * </pre>
*
* @author yourname@meiCloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-08-27 14:42:31
 *  修改内容:
 * </pre>
*/
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("ceea_sc_deliver_plan")
public class DeliverPlan extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 到货计划ID
     */
    @TableId("DELIVER_PLAN_ID")
    private Long deliverPlanId;

    /**
     * 到货计划号
     */
    @TableField("DELIVER_PLAN_NUM")
    private String deliverPlanNum;

    /**
     * 到货计划月度
     */
    @TableField("MONTHLY_SCH_DATE")
    private String monthlySchDate;

    /**
     * 计划状态
     */
    @TableField("DELIVER_PLAN_STATUS")
    private String deliverPlanStatus;

    /**
     * 业务实体ID
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
     * 交货地点
     */
    @TableField("DELIVERY_ADDRESS")
    private String deliveryAddress;

    /**
     * 供应商ID
     */
    @TableField("VENDOR_ID")
    private Long vendorId;

    /**
     * 供应商名称
     */
    @TableField("VENDOR_NAME")
    private String vendorName;

    /**
     * 供应商编码
     */
    @TableField("VENDOR_CODE")
    private String vendorCode;

    /**
     * 物料小类ID
     */
    @TableField("CATEGORY_ID")
    private Long categoryId;

    /**
     * 物料小类名称
     */
    @TableField("CATEGORY_NAME")
    private String categoryName;

    /**
     * 物料小类编码
     */
    @TableField("CATEGORY_CODE")
    private String categoryCode;

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
    @TableField("UNIT")
    private String unit;

    /**
     * 计划总数量
     */
    @TableField("SCH_TOTAL_QUANTITY")
    private BigDecimal schTotalQuantity;

    /**
     * 匹配度
     */
    @TableField("MATCH_DEGREE")
    private BigDecimal matchDegree;

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
     * 租户
     */
    @TableField("TENANT_ID")
    private Long tenantId;

    /**
     * 版本号
     */
    @TableField("VERSION")
    private Long version;

    /**
     * mrp到货计划号
     */
    @TableField("MRP_DELIVER_PLAN_NUM")
    private String mrpDeliverPlanNum;
    /**
     * 到货计划详情集合
     */
    @TableField(exist = false)
    private List<DeliverPlanDetail> deliverPlanDetails;

    /**
     * 0-需求 , 1-供应
     */
    @TableField(exist = false)
    private String projectFlag;

    /**
     * 需求详情(日期,数值)
     */
    @TableField(exist = false)
    private Map<String,String> demandNumMap;

    /**
     * 供应详情(日期,数值)
     */
    @TableField(exist = false)
    private Map<String,String> supplyNumMap;

    @TableField(exist = false)
    private String struct;
    /**
     * 送货计划单号
     */
    @TableField(value = "NOTIFY_NUMBER",exist = false)
    private String notifyNumber;

    /**
     * 数量
     */
    @TableField(value = "SCH_QUANTITY",exist = false)
    private String schQuantity;

}
