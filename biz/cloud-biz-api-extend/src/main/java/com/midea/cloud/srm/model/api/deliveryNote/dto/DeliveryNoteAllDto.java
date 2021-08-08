package com.midea.cloud.srm.model.api.deliveryNote.dto;

import java.util.List;

import lombok.Data;

@Data
public class DeliveryNoteAllDto extends DeliveryNoteDto {
	
	private List<DeliveryNoteDetailDto> deliveryLines;
	
}
