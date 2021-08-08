package com.midea.cloud.srm.model.report.purchase.dto;

import java.io.Serializable;

import lombok.Data;

@Data
public class PurchaseConfigDTO implements Serializable {
	 /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
     * 配置ID
     */
    private Long configId;
    
    /**
     * 组织路径
     */
    private String fullPathIds;
    
    /**
     * 组织名称
     */
    private String organizationNames;
    
    /**
     * 类型(字典REPORT_CONFIG_PURCHASE_TYPE)
     */
    private String type;
    
    /**
     * 入库偏差天数
     */
    private Integer days;
}
