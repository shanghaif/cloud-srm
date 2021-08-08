package com.midea.cloud.srm.model.inq.inquiry.entity;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.TableName;
import com.midea.cloud.srm.model.common.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
*  <pre>
 *  配额调整-关联寻源列表 模型
 * </pre>
*
* @author zhi1772778785@163.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-09-08 16:57:09
 *  修改内容:
 * </pre>
*/
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("ceea_inquiry_quota_source")
public class QuotaSource extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 配额调整-关联寻源列表ID
     */
    @TableId("QUOTA_SOURCE_ID")
    private Long quotaSourceId;

    /**
     * 配额调整ID
     */
    @TableField("QUOTA_ADJUST_ID")
    private Long quotaAdjustId;

    /**
     * 寻源单号
     */
    @TableField("CEEA_SOURCE_NO")
    private String ceeaSourceNo;

    /**
     * 寻源方式
     */
    @TableField("SOURCE_TYPE")
    private String sourceType;
    /**
     * 中标行ID
     */
    @TableField("APPROVAL_BIDDING_ITEM_ID")
    private Long approvalBiddingItemId;

    /**
     * 目标比例
     */
    @TableField("TARGET_PROPORTION")
    private BigDecimal targetProportion;

    /**
     * 金额差异
     */
    @TableField("AMOUNT_DIFFERENCE")
    private BigDecimal amountDifference;

    /**
     * 业务实体ID
     */
    @TableField("ORG_ID")
    private Long orgId;

    /**
     * 物料小类ID
     */
    @TableField("CATEGORY_ID")
    private Long categoryId;

    /**
     * 供应商名称
     */
    @TableField("VENDOR_NAME")
    private String vendorName;

    /**
     * 物料编码
     */
    @TableField("ITEM_CODE")
    private String itemCode;


}
