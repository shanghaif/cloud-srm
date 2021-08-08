package com.midea.cloud.srm.model.api.aps;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import lombok.Data;

@Data
public class PiNotifyForSrmDTO1 implements Serializable {

    /**
     * 工厂编码
     */
    private String plantCode;
    /**
     * 送货通知单号
     */
    private String notifyNumber;
    /**
     * 供应商编码
     */
    private String vendorCode;
    /**
     * 供应商地点
     */
    private String vendorSiteCode;
    /**
     * 送货通知状态
     */
    private String status;
    /**
     * 送货通知类型
     */
    private String notifyType;
    /**
     * 送货区域
     */
    private String deliveryRegion;
    /**
     * 收货子库
     */
    private String subinventory;
    /**
     * 工单号
     */
    private String moNumber;
    /**
     * 采购员
     */
    private String buyer;
    /**
     * 物料编码
     */
    private String itemCode;
    /**
     * 下达日期
     */
    private Date releasedDate;
    /**
     * 需求时间
     */
    private Date needByDate;
    /**
     * 数量
     */
    private BigDecimal quantity;
    /**
     * 下单数量
     */
    private BigDecimal releasedQuantity;
    /**
     * 承诺数量
     */
    private BigDecimal promisedQuantity;
    /**
     * 发运数量
     */
    private BigDecimal deliveryQuantity;
    /**
     * 取消数量
     */
    private BigDecimal cancelQuantity;
    /**
     * 拒绝原因
     */
    private String rejectReason;
    /**
     * 单位
     */
    private String unitOfMeasure;
    /**
     * 备注
     */
    private String comments;
    /**
     * $column.comments
     */
    private String publishVersion;
    /**
     * 分厂
     */
    private String subFactoryCode;
    /**
     * 供应商ID
     */
    private String vendorId;
    /**
     * 地址ID
     */
    private String locationId;
    /**
     * 地址编号
     */
    private String locationCode;
    /**
     * 地址描述
     */
    private String locationDesc;
    /**
     * SRM到货计划号
     */
    private String srmDnNumber;
    /**
     * 失败原因
     */
    private String failMessage;
    /**
     * 行信息
     */
    private List<PiNotifyLine1> notifyLines;
}
