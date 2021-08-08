package com.midea.cloud.srm.model.report.purchase.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import lombok.Data;

@Data
public class PurchaseResultDTO implements Serializable {
	
	
	/**
	 * 申请量
	 */
	private Integer orderDetailLineNum;
	
	/**
	 * 采购金额
	 */
	private BigDecimal purchaseAmount;
	
	/**
	 * 入库金额
	 */
	private BigDecimal WarehousingAmount;
	
	/**
	 * 采购执行情况
	 */
	private List<OrganizationRatio> executionList;
	
	/**
	 * 采购接收情况
	 */
	private List<OrganizationRatio> receiveList;
	
	/**
	 * 采购准时率
	 */
	private List<OrganizationRatio> punctualityList;
	
	
	private List<PurchaseMonthRatio> months;
	
}
