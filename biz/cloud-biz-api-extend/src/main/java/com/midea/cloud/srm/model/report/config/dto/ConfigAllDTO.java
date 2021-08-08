package com.midea.cloud.srm.model.report.config.dto;

import java.io.Serializable;
import java.util.List;

import com.midea.cloud.srm.model.report.costreduction.entity.CrSet;
import com.midea.cloud.srm.model.report.purchase.dto.PurchaseConfigDTO;
import com.midea.cloud.srm.model.report.purchase.entity.PurchaseConfig;
import com.midea.cloud.srm.model.report.supplier.dto.SupplierConfigDTO;

import lombok.Data;
@Data
public class ConfigAllDTO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 采购分析配置
	 */
	private List<PurchaseConfigDTO> purchaseList;
	/**
	 * 供应商配置
	 */
	private SupplierConfigDTO supplier;
	
	/**
	 * 降本分析配置
	 */
	private List<CrSet> costReductionList;
}
