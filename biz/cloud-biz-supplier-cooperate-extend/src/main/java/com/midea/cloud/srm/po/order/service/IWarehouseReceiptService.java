package com.midea.cloud.srm.po.order.service;

import java.util.List;

import org.springframework.web.bind.annotation.RequestBody;

import com.midea.cloud.srm.model.pm.po.dto.WarehouseReceiptConfirmDTO;
import com.midea.cloud.srm.model.pm.po.dto.WriteOffDTO;

/**
 * 
 * <pre>
 * 订单入库
 * </pre>
 * 
 * @author zhizhao1.fan@meicloud.com
 * @version 1.00.00
 * 
 *          <pre>
 *  修改记录
 *  修改后版本: 
 *  修改人: 
 *  修改日期: May 25, 20203:22:11 PM 
 *  修改内容:
 *          </pre>
 */
public interface IWarehouseReceiptService {

	/**
	 * 确认入库
	 * 
	 * @param warehouseReceiptConfirmDTOList
	 */
	void confirm(List<WarehouseReceiptConfirmDTO> warehouseReceiptConfirmDTOList);

	/**
	 * 冲销
	 * 
	 * @param writeOffDTO
	 */
	void writeOff(@RequestBody WriteOffDTO writeOffDTO);

}
