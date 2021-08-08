package com.midea.cloud.srm.model.logistics.bid.entity;

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

/**
*  <pre>
 *  供应商报价汇总 模型
 * </pre>
*
* @author wangpr@meiCloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2021-01-06 09:08:34
 *  修改内容:
 * </pre>
*/
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("scc_lgt_vendor_quoted_sum")
public class LgtVendorQuotedSum extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 报价汇总ID
     */
    @TableId("QUOTED_SUM_ID")
    private Long quotedSumId;

    /**
     * 物流招标头ID
     */
    @TableField("BIDING_ID")
    private Long bidingId;

    /**
     * 供应商报价头ID
     */
    @TableField("QUOTED_HEAD_ID")
    private Long quotedHeadId;

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
     * 起运地
     */
    @TableField("START_ADDRESS")
    private String startAddress;

    /**
     * 目的地
     */
    @TableField("END_ADDRESS")
    private String endAddress;

    /**
     * 总价
     */
    @TableField("SUM_PRICE")
    private BigDecimal sumPrice;

    /**
     * 当前轮次
     */
    @TableField("ROUND")
    private Integer round;

    /**
     * 决标结果 BIDDING_SELECT_STATES
     * SelectionStatusEnum
     */
    @TableField("BID_RESULT")
    private String bidResult;

    /**
     * 下轮允许投标
     */
    @TableField("SHORTLISTED")
    private String shortlisted;

    /**
     * 是否代理报价(N/Y)
     */
    @TableField("IF_PROXY")
    private String ifProxy;

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
     * 更新人
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
    private String tenantId;

    /**
     * 版本号
     */
    @TableField("VERSION")
    private Long version;

    /**
     * 行号
     */
    @TableField("ROW_NUM")
    private Integer rowNum;

    /**
     * 物流招标项目需求ID
     */
    @TableField("BID_REQUIREMENT_LINE_ID")
    private Long bidRequirementLineId;

    /**
     * 排名
     */
    @TableField(exist = false)
    private Integer rank;


}
