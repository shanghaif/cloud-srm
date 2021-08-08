package com.midea.cloud.srm.model.suppliercooperate.deliver.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import com.midea.cloud.srm.model.suppliercooperate.deliver.entity.OrderDeliveryDetail;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
public class OrderDeliveryDetailDTO extends OrderDeliveryDetail {
    /**
     * 准入日期开始
     */
    private Date startDate;
    /**
     * 准入日期结束
     */
    private Date endDate;
    /**
     * 采购订单号多选
     */
    private List<Long> orderIdList;
    /**
     * 收货地址(longi)
     */
    @TableField("CEEA_RECEIVE_ADDRESS")
    private String ceeaReceiveAddress;
    /**
     * 订单行号
     */
    @TableField("LINE_NUM")
    private Integer lineNum;

    /**
     * 剩余未送货数量
     */
    private String numberRemaining;
    /**
     * 剩余未送货数量
     */
    private String planNumberRemaining;

    /**
     * 单位
     */
    @TableField("UNIT")
    private String unit;
    /**
     * 含税单价(longi)
     */
    @TableField("CEEA_UNIT_TAX_PRICE")
    private BigDecimal ceeaUnitTaxPrice;

}
