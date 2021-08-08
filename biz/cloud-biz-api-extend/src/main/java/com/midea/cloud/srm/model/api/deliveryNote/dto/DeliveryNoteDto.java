package com.midea.cloud.srm.model.api.deliveryNote.dto;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.midea.cloud.srm.model.common.BaseDTO;

import lombok.Data;

@Data
public class DeliveryNoteDto implements Serializable {
	
	/**
     * 主键ID
     */
    private Long deliveryNoteId;
    /**
     * 备注
     */
    private String comments;
    
    /**
     * 创建人
     */
    private String createdBy;

    /**
     * 创建时间
     */
    private Date creationDate;

    /**
     * 最后更新人
     */
    private String lastUpdatedBy;

    /**
     * 最后更新时间
     */
    private Date lastUpdateDate;
    /**
     * 送货日期
     */
    private Date deliveryDate;
    /**
     * 送货单号
     */
    private String deliveryNumber;
    /**
     * 联系方式
     */
    private String contactNumber;
    /**
     * 组织名称
     */
    private String organizationCode;
    /**
     * 状态（单据状态）
     */
    private String deliveryNoteStatus;
    /**
     * 收货地点ID
     */
    private Long locationId;
    /**
     * 收货地址编码
     */
    private String locationName;
    /**
     * 收货地点描述
     */
    private String locationDescr;
    
    /**
     * ERP供应商ID
     */
    private String erpVendorId;
    /**
     * ERP供应商Code
     */
    private String erpVendorCode;
}
