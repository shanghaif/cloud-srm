package com.midea.cloud.srm.model.suppliercooperate.orderreceive.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.midea.cloud.srm.model.common.BaseEntity;
import jdk.nashorn.internal.ir.annotations.Ignore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class ReceiveDetailDTO extends BaseEntity{
    /**
     * 送货数量
     */
    private Long deliveryQuantity;
    /**
     * 累计收货数量
     */
    private Long receiveSum;
    /**
     * 业务实体id列表
     */
    @JSONField(serialize = false)
    private List<Long> orgIds;
    /**
     * 业务实体id
     */
    private Long orgId;
    /**
     * 业务实体名称
     */
    private String orgName;
    /**
     * 供应商名称
     */
    private String vendorName;
    /**
     * 供应商id
     */
    private Long vendorId;
    /**
     * 供应商编码
     */
    private String vendorCode;
    /**
     * 送货单号
     */
    private String deliveryNumber;
    /**
     * 送货单行
     */
    private String deliveryLineNum;
    /**
     * 送货日期
     */
    private String deliveryDate;
    /**
     * 采购订单号
     */
    private String orderNumber;
    /**
     * 采购订单行号
      */
    private String orderLineNum;
    /**
     * 采购分类
     */
    private String categoryName;
    /**
     * 物料编码
     */
    private String materialCode;
    /**
     * 物料名称
     */
    private String materialName;
    /**
     * 订单数量
     */
    private Long orderNum;
    /**
     * 收货地点
     */
    private String receiptPlace;
    /**
     * 收单地址
     */
    private String receiveOrderAddress;
}
