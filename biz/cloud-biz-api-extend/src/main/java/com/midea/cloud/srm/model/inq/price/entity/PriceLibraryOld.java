package com.midea.cloud.srm.model.inq.price.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author tanjl11
 * @date 2020/12/03 19:50
 */
@TableName("price_library_old")
@Data
public class PriceLibraryOld {

    @TableId(value = "ORDER_LINE_ID")
    private Long orderLineId;
    @TableField(value = "ORG_CODE")
    private String orgCode;
    @TableField(value = "TARGET_NUM")
    private String targetNum;
    @TableField(value = "TARGET_DESC")
    private String targetDesc;
    @TableField(value = "VENDOR_NAME")
    private String vendorName;
    @TableField(value = "VENDOR_CODE")
    private String vendorCode;
    @TableField(value = "PRICE")
    private BigDecimal price;
    @TableField(value = "CATEGORY_CODE")
    private String categoryCode;
    @TableField(value = "CREATION_DATE")
    private Date creationDate;

}
