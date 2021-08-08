package com.midea.cloud.srm.po.order.service;

import com.midea.cloud.common.enums.order.ReturnOrderStatusEnum;
import com.midea.cloud.srm.model.suppliercooperate.order.dto.ReturnOrderSaveRequestDTO;

import java.util.List;

/**
 * 
 * <pre>
 * 
 * </pre>
 * 
 * @author zhizhao1.fan@meicloud.com
 * @version 1.00.00
 * 
 *          <pre>
 *  修改记录
 *  修改后版本: 
 *  修改人: 
 *  修改日期: May 29, 20203:03:30 PM 
 *  修改内容:
 *          </pre>
 */
public interface IReturnOrderService {

	void saveOrUpdateReturnOrder(ReturnOrderSaveRequestDTO returnOrderSaveRequestDTO, ReturnOrderStatusEnum returnOrderStatusEnum);

	void batchConfirm(List<Long> returnOrderIds);

}
