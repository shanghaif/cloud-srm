package com.midea.cloud.srm.model.api.deliveryNote.dto;

import java.io.Serializable;
import java.math.BigDecimal;

import lombok.Data;
@Data
public class DeliveryNoteDetailDto implements Serializable {
	
	private Long deliveryNoteDetailId;
	/**
	 * 送货单行号
	 */
	private Integer lineNum;
	/**
	 * 送货单号
	 */
	private String deliveryNumber;
	/**
	 * 送货数量
	 */
	private BigDecimal deliveryQuantity;
	/**
	 * 物料编码
	 */
	private String materialCode;
	/**
	 * 送货通知单号
	 */
	private String deliveryNoticeNum;
	/**
	 * 送货通知单行号
	 */
	private Integer deliveryNoticeLineNum;
	
	/**
	 * 库存组织编码
	 */
	private String organizationCode;
	/**
	 * 订单号
	 */
	private String orderNumber;
	/**
	 * 订单行号
	 */
	private Integer orderLineNumber;
	 /**
     * 收货数量
     */
	private BigDecimal receivedNum;
	
}
