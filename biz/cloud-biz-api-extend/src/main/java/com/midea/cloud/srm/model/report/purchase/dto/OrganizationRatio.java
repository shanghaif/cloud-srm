package com.midea.cloud.srm.model.report.purchase.dto;

import java.io.Serializable;
import java.math.BigDecimal;

import lombok.Data;
@Data
public class OrganizationRatio implements Serializable {
	
	private String fullPathId; //组织路径
	
	private String organizationName; //组织名称
	
	private BigDecimal rate; //占比
}
