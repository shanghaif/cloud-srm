package com.midea.cloud.srm.po.order.controller;

import com.github.pagehelper.PageInfo;
import com.midea.cloud.common.enums.order.ReturnOrderStatusEnum;
import com.midea.cloud.srm.feign.supcooperate.SupcooperateClient;
import com.midea.cloud.srm.model.common.BaseController;
import com.midea.cloud.srm.model.suppliercooperate.order.dto.ReturnOrderRequestDTO;
import com.midea.cloud.srm.model.suppliercooperate.order.dto.ReturnOrderSaveRequestDTO;
import com.midea.cloud.srm.model.suppliercooperate.order.entry.ReturnOrder;
import com.midea.cloud.srm.po.order.service.IReturnOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 *
 * <pre>
 * 退货单
 * </pre>
 *
 * @author zhizhao1.fan@meicloud.com
 * @version 1.00.00
 *
 *          <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: May 29, 202010:42:36 AM
 *  修改内容:
 *          </pre>
 */
@RestController
@RequestMapping("/po/returnOrder")
public class ReturnOrderController extends BaseController {

	@Autowired
	private SupcooperateClient supcooperateClient;

	@Autowired
	private IReturnOrderService iReturnOrderService;

	/**
	 * 分页列表
	 *
	 * @param requestDTO
	 * @return
	 */
	@PostMapping("/listPage")
	public PageInfo<ReturnOrder> listPage(@RequestBody ReturnOrderRequestDTO returnOrderRequestDTO) {
		return null;
	}

	/**
	 * 根据退货订单头ID查询订单详情
	 *
	 * @param returnOrderId
	 * @return
	 */
	@GetMapping("/getReturnOrderById")
	public ReturnOrderSaveRequestDTO getReturnOrderById(Long returnOrderId) {
//		ReturnOrder returnOrderById = supcooperateClient.getReturnOrderById(returnOrderId);
//		List<ReturnDetailDTO> returnDetailListByReturnOrderId = supcooperateClient.getReturnDetailListByReturnOrderId(returnOrderId);
//		return new ReturnOrderSaveRequestDTO().setReturnDetailDTOs(returnDetailListByReturnOrderId).setReturnOrder(returnOrderById);
		return null;
	}

	/**
	 * 新增
	 *
	 * @param param
	 */
	@PostMapping("/save")
	public void save(@RequestBody ReturnOrderSaveRequestDTO returnOrderSaveRequestDTO) {
		returnOrderSaveRequestDTO.getReturnOrder().setReturnOrderId(null);
		iReturnOrderService.saveOrUpdateReturnOrder(returnOrderSaveRequestDTO, ReturnOrderStatusEnum.EDIT);
	}

	/**
	 * 更新
	 *
	 * @param param
	 */
	@PostMapping("/update")
	public void update(@RequestBody ReturnOrderSaveRequestDTO returnOrderSaveRequestDTO) {
		iReturnOrderService.saveOrUpdateReturnOrder(returnOrderSaveRequestDTO, ReturnOrderStatusEnum.EDIT);
	}

	/**
	 * 确认
	 *
	 * @param param
	 */
	@PostMapping("/confirm")
	public void confirm(@RequestBody ReturnOrderSaveRequestDTO returnOrderSaveRequestDTO) {
		iReturnOrderService.saveOrUpdateReturnOrder(returnOrderSaveRequestDTO, ReturnOrderStatusEnum.CONFIRM);
	}

	/**
	 * 确认退货
	 *
	 * @param returnOrderIds
	 */
	@PostMapping("/batchConfirm")
	public void batchConfirm(@RequestBody List<Long> returnOrderIds) {
		iReturnOrderService.batchConfirm(returnOrderIds);
	}

}
