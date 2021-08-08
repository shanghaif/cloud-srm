package com.midea.cloud.srm.model.pm.po.dto;

import java.math.BigDecimal;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 
 * <pre>
 * 订单入库确认参数
 * </pre>
 * 
 * @author zhizhao1.fan@meicloud.com
 * @version 1.00.00
 * 
 *          <pre>
 *  修改记录
 *  修改后版本: 
 *  修改人: 
 *  修改日期: May 25, 20203:10:59 PM 
 *  修改内容:
 *          </pre>
 */
@Data
@Accessors(chain = true)
public class WarehouseReceiptConfirmDTO {

	private Long deliveryNoteId;// 送货单头ID
	private Long deliveryNoteDetailId;// 送货单行ID
	private BigDecimal warehouseReceiptQuantity;//入库数量

}
