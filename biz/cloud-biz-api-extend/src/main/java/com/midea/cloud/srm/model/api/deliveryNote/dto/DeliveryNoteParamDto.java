package com.midea.cloud.srm.model.api.deliveryNote.dto;

import java.io.Serializable;
import java.util.List;

import com.midea.cloud.srm.model.common.BaseDTO;

import lombok.Data;

@Data
public class DeliveryNoteParamDto implements Serializable{
	/**
	 * 库存编码
	 */
	private String organizationCode;
	/**
	 * 最后更新时间
	 */
	private String lastUpdateDate;
	
	private Long deliveryNoteId;
	
	private List<Long> deliveryNoteIds;
	
	private List<String> deliveryPlanDetailIds;
}
