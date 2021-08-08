package com.midea.cloud.srm.model.api.aps;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;
import com.midea.cloud.srm.model.common.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
*  <pre>
 *  mrp到货计划行 模型
 * </pre>
*
* @author yourname@meiCloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-12-21 16:00:41
 *  修改内容:
 * </pre>
*/
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("ceea_mrp_deliver_plan_detail")
public class PiNotifyLine extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @TableField("MRP_DELIVER_PLAN_DETAIL_ID")
    private Long mrpDeliverPlanDetailId;

    @TableField("MRP_DELIVER_PLAN_ID")
    private Long mrpDeliverPlanId;

    @TableField("SOURCE_BATCH_ID")
    private String sourceBatchId;

    @TableField("INSERT_TIME")
    private Date insertTime;

    @TableField("DEAL_TIME")
    private String dealTime;

    @TableField("DEAL_FLAG")
    private String dealFlag;

    @TableField("GROUP_ID")
    private String groupId;

    @TableField("ACTION")
    private String action;

    @TableField("SUCCESS_FLAG")
    private String successFlag;

    @TableField("FAIL_MESSAGE")
    private String failMessage;

    @TableField("PLANT_CODE")
    private String plantCode;

    @TableField("NOTIFY_NUMBER")
    private String notifyNumber;

    @TableField("LINE_NUM")
    private BigDecimal lineNum;

    @TableField("PO_NUMBER")
    private String poNumber;

    @TableField("PO_LINE_NUM")
    private String poLineNum;

    @TableField("QUANTITY")
    private BigDecimal quantity;

    @TableField("DELIVERY_QUANTITY")
    private BigDecimal deliveryQuantity;

    @TableField("SRM_LINE_NO")
    private String srmLineNo;

    @TableField("SRM_SERIAL_NUMBER")
    private String srmSerialNumber;

    @TableField("VENDOR_QTY")
    private String vendorQty;

    @TableField("TRANSFER_FLAG")
    private String transferFlag;

    @TableField("NOTIFY_TYPE")
    private String notifyType;

    @TableField("NEEDBYDATE")
    private Date needbydate;

    @TableField("NEED_BY_DATE")
    private Date needByDate;

    @TableField("LOCK_FLAG")
    private String lockFlag;


}
