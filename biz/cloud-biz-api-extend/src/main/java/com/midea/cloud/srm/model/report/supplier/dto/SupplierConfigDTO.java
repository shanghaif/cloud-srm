package com.midea.cloud.srm.model.report.supplier.dto;

import java.io.Serializable;
import java.math.BigDecimal;

import lombok.Data;

@Data
public class SupplierConfigDTO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 配置ID
	 **/
	private Long configId;
	/**
	 * 订单金额
	 **/
	private BigDecimal orderAmount;
	/**
	 * 供应商区域分布阶段一
	 **/
	private Integer areaOne;
	/**
	 * 供应商区域分布阶段二开始值
	 **/
	private Integer areaTwoStart;
	/**
	 * 供应商区域分布阶段二结束值
	 **/
	private Integer areaTwoEnd;
	/**
	 * 供应商区域分布阶段三开始值
	 **/
	private Integer areaThreeStart;
	/**
	 * 供应商区域分布阶段三结束值
	 **/
	private Integer areaThreeEnd;
	/**
	 * 供应商区域分布阶段四
	 **/
	private Integer areaFour;
	/**
	 * 采购金额供方占比阶段一
	 **/
	private Integer purchaseOne;
	/**
	 * 采购金额供方占比阶段二开始值
	 **/
	private Integer purchaseTwoStart;
	/**
	 * 采购金额供方占比阶段二结束值
	 **/
	private Integer purchaseTwoEnd;
	/**
	 * 采购金额供方占比阶段三开始值
	 **/
	private Integer purchaseThreeStart;
	/**
	 * 采购金额供方占比阶段四
	 **/
	private Integer purchaseFour;
	/**
	 * 采购金额供方占比阶段三结束值
	 **/
	private Integer purchaseThreeEnd;
	/**
	 * 品类供方数占比阶段一
	 **/
	private Integer categoryOne;
	/**
	 * 品类供方数占比阶段二开始值
	 **/
	private Integer categoryTwoStart;
	/**
	 * 品类供方数占比阶段二结束值
	 **/
	private Integer categoryTwoEnd;
	/**
	 * 品类供方数占比阶段三开始值
	 **/
	private Integer categoryThreeStart;
	/**
	 * 品类供方数占比阶段三结束值
	 **/
	private Integer categoryThreeEnd;
	/**
	 * 品类供方数占比阶段四
	 **/
	private Integer categoryFour;
	
	/**
	 * 品类降本金额阶段一
	 **/
	private Integer categoryCrOne;
	/**
	 * 品类降本金额阶段二开始值
	 **/
	private Integer categoryCrTwoStart;
	/**
	 * 品类降本金额阶段二结束值
	 **/
	private Integer categoryCrTwoEnd;
	/**
	 * 品类降本金额阶段三开始值
	 **/
	private Integer categoryCrThreeStart;
	/**
	 * 品类降本金额阶段三结束值
	 **/
	private Integer categoryCrThreeEnd;
	/**
	 * 品类降本金额阶段四
	 **/
	private Integer categoryCrFour;
}
