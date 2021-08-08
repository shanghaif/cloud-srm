package com.midea.cloud.srm.po.order.service;

import com.midea.cloud.srm.model.pm.po.dto.ReturnOrderDTO;
import com.midea.cloud.srm.model.suppliercooperate.order.dto.OrderRequestDTO;
import com.midea.cloud.srm.model.suppliercooperate.order.dto.OrderSaveRequestDTO;
import com.midea.cloud.srm.model.suppliercooperate.order.entry.Order;
import com.midea.cloud.srm.model.suppliercooperate.order.entry.OrderAttach;
import com.midea.cloud.srm.model.suppliercooperate.order.entry.OrderDetail;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 *
 *
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
 *  修改日期: 2020年4月27日 下午2:48:38
 *  修改内容:
 *          </pre>
 */
public interface IOrderService {


	void acceptReply(Long orderId);

	void returnOrder(ReturnOrderDTO returnOrderDTO);

	OrderSaveRequestDTO queryOrderById(Long orderId);

	Long save(OrderSaveRequestDTO param);

	void submitOrder(OrderSaveRequestDTO param);

	void approval(OrderSaveRequestDTO param);

	Long submitOrderNew(OrderSaveRequestDTO param);

	void supplierConfirm(OrderSaveRequestDTO param);

	void reject(OrderSaveRequestDTO param);

	void supplierReject(OrderSaveRequestDTO param);

	void deleteOrder(Long orderId);

	void abandon(Long requirementHeadId);

	void startEditStatus(Long orderId);

	void saveInEditStatus(OrderSaveRequestDTO param);

	void submitInEditStatus(OrderSaveRequestDTO param);

	void approvalInEditStatus(OrderSaveRequestDTO param);

	void rejectInEditStatus(OrderSaveRequestDTO param);

	void withdrawInEditStatus(OrderSaveRequestDTO param) throws Exception;

	void supplierConfirmInEditStatus(OrderSaveRequestDTO param);

	void supplierRejectInEditStatus(OrderSaveRequestDTO param);


	void cancelPurchaseOrderSoapBiz(Long orderId);

    void manualPush(List<String> orderNumbers);

	/**
	 * 批量删除采购订单
	 * @param ids
	 */
	void batchDelete(List<Long> ids);
}
