package com.midea.cloud.srm.model.pm.po.dto;

import java.util.List;

import lombok.Data;

/**
 * 
 * <pre>
 * 冲销DTO
 * </pre>
 * 
 * @author zhizhao1.fan@meicloud.com
 * @version 1.00.00
 * 
 *          <pre>
 *  修改记录
 *  修改后版本: 
 *  修改人: 
 *  修改日期: May 25, 20204:04:24 PM 
 *  修改内容:
 *          </pre>
 */
@Data
public class WriteOffDTO {

	private List<WarehouseReceiptPageDTO> originalList;
	
	private List<WarehouseReceiptPageDTO> writeOffList;
	
	private List<WarehouseReceiptPageDTO> newList;
}
