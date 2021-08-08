package com.midea.cloud.srm.model.suppliercooperate.reconciliation.entry;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.midea.cloud.srm.model.common.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.Date;
/**
 * <pre>
 *  未结算数量账单表 模型
 * </pre>
 *
 * @author huangbf3
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/3/21 14:45
 *  修改内容:
 * </pre>
 */

@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("scc_sc_unsettled_order")
public class UnsettledOrder extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @TableId("UNSETTLED_ORDER_ID")
    private Long unsettledOrderId;

    /**
     * 采购组织ID
     */
    @TableField("ORGANIZATION_ID")
    private Long organizationId;

    /**
     * 组织全路径虚拟ID
     */
    @TableField("FULL_PATH_ID")
    private String fullPathId;

    /**
     * 组织编号
     */
    @TableField("ORGANIZATION_CODE")
    private String organizationCode;

    /**
     * 组织名称
     */
    @TableField("ORGANIZATION_NAME")
    private String organizationName;

    /**
     * 供应商ID
     */
    @TableField("VENDOR_ID")
    private Long vendorId;

    /**
     * 供应商编号
     */
    @TableField("VENDOR_CODE")
    private String vendorCode;

    /**
     * 供应商名称
     */
    @TableField("VENDOR_NAME")
    private String vendorName;

    /**
     * 对账单号PS+YYYYMMDD+0001
     */
    @TableField("UNSETTLED_ORDER_NUMBER")
    private String unsettledOrderNumber;

    /**
     * 1.	拟定：创建对账单保存时
     * 2.	已提交：提交对账单后
     * 3.	已审核：采购商审核后
     * 4.	已驳回：采购商驳回后
     * 5.	已作废：拟定状态作废
     * 6.	供应商已确认：供应商盖章上传对账单确认函后
     * 7.	已完成：采购商确认供应商上传后
     */
    @TableField("STATUS")
    private String status;

    /**
     * 开始日期
     */
    @TableField("START_DATE")
    private Date startDate;

    /**
     * 截止日期
     */
    @TableField("END_DATE")
    private Date endDate;

    /**
     * 币种
     */
    @TableField("RFQ_SETTLEMENT_CURRENCY")
    private String rfqSettlementCurrency;

    /**
     * 驳回原因
     */
    @TableField("REFUSE_REASON")
    private String refuseReason;

    /**
     * 附件ID
     */
    @TableField("FILE_RELATION_ID")
    private Long fileRelationId;

    /**
     * 附件名称
     */
    @TableField("FILE_NAME")
    private String fileName;

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
    @TableField(value = "LAST_UPDATED_ID", fill = FieldFill.UPDATE)
    private Long lastUpdatedId;

    /**
     * 最后更新人
     */
    @TableField(value = "LAST_UPDATED_BY", fill = FieldFill.UPDATE)
    private String lastUpdatedBy;

    /**
     * 最后更新时间
     */
    @TableField(value = "LAST_UPDATE_DATE", fill = FieldFill.INSERT_UPDATE)
    private Date lastUpdateDate;

    /**
     * 最后更新人IP
     */
    @TableField(value = "LAST_UPDATED_BY_IP", fill = FieldFill.UPDATE)
    private String lastUpdatedByIp;

    /**
     * 版本号
     */
    @TableField("VERSION")
    private Long version;

    /**
     * 租户ID
     */
    @TableField("TENANT_ID")
    private Long tenantId;
}