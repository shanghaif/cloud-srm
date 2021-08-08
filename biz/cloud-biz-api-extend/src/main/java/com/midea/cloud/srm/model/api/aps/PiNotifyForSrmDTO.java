package com.midea.cloud.srm.model.api.aps;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;
import java.util.List;

import com.midea.cloud.srm.model.common.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
*  <pre>
 *  mrp送货预示送货通知[记录表] 模型
 * </pre>
*
* @author yourname@meiCloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-12-21 16:03:11
 *  修改内容:
 * </pre>
*/
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("ceea_sc_mrp_deliver_plan")
public class PiNotifyForSrmDTO extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @TableField("MRP_DELIVER_PLAN_ID")
    private Long mrpDeliverPlanId;

    @TableField("PLANT_CODE")
    private String plantCode;

    @TableField("NOTIFY_NUMBER")
    private String notifyNumber;

    @TableField("VENDOR_CODE")
    private String vendorCode;

    @TableField("VENDOR_SITE_CODE")
    private String vendorSiteCode;

    @TableField("STATUS")
    private String status;

    @TableField("NOTIFY_TYPE")
    private String notifyType;

    @TableField("DELIVERY_REGION")
    private String deliveryRegion;

    @TableField("SUBINVENTORY")
    private String subinventory;

    @TableField("MO_NUMBER")
    private String moNumber;

    @TableField("BUYER")
    private String buyer;

    @TableField("ITEM_CODE")
    private String itemCode;

    @TableField("RELEASED_DATE")
    private Date releasedDate;

    @TableField("NEED_BY_DATE")
    private Date needByDate;

    @TableField("QUANTITY")
    private BigDecimal quantity;

    @TableField("RELEASED_QUANTITY")
    private BigDecimal releasedQuantity;

    @TableField("PROMISED_QUANTITY")
    private BigDecimal promisedQuantity;

    @TableField("DELIVERY_QUANTITY")
    private BigDecimal deliveryQuantity;

    @TableField("CANCEL_QUANTITY")
    private BigDecimal cancelQuantity;

    @TableField("REJECT_REASON")
    private String rejectReason;

    @TableField("UNIT_OF_MEASURE")
    private String unitOfMeasure;

    @TableField("COMMENTS")
    private String comments;

    @TableField("PUBLISH_VERSION")
    private String publishVersion;

    @TableField("SUB_FACTORY_CODE")
    private String subFactoryCode;

    @TableField("VENDOR_ID")
    private String vendorId;

    @TableField("LOCATION_ID")
    private String locationId;

    @TableField("LOCATION_CODE")
    private String locationCode;

    @TableField("LOCATION_DESC")
    private String locationDesc;

    @TableField("SRM_DN_NUMBER")
    private String srmDnNumber;

    @TableField("FAIL_MESSAGE")
    private String failMessage;

    @TableField(exist = false)
    private List<PiNotifyLine> notifyLines;


}
