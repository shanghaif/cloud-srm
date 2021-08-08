package com.midea.cloud.srm.model.pm.po.dto;

import javax.validation.constraints.NotNull;

import lombok.Data;

/**
 * 
 * 
 * 
 * <pre>
 * 采购商驳回
 * </pre>
 * 
 * @author zhizhao1.fan@meicloud.com
 * @version 1.00.00
 * 
 *          <pre>
 *  修改记录
 *  修改后版本: 
 *  修改人: 
 *  修改日期: 2020年4月27日 下午2:48:38  
 *  修改内容:
 *          </pre>
 */
@Data
public class ReturnOrderDTO {

	@NotNull
	private Long orderId;

	@NotNull
	private String purchaseResponse;
}
