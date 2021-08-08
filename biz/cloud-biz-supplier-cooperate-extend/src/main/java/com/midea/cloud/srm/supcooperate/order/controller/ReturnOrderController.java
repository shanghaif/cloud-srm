package com.midea.cloud.srm.supcooperate.order.controller;

import java.util.ArrayList;
import java.util.List;

import com.midea.cloud.srm.model.suppliercooperate.order.dto.ReturnOrderSaveRequestDTO;
import com.midea.cloud.srm.model.suppliercooperate.order.vo.ReturnOrderVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.github.pagehelper.PageInfo;
import com.midea.cloud.common.enums.UserType;
import com.midea.cloud.common.exception.BaseException;
import com.midea.cloud.common.utils.AppUserUtil;
import com.midea.cloud.srm.feign.supcooperate.SupcooperateClient.ConfirmReturnOrders;
import com.midea.cloud.srm.feign.supcooperate.SupcooperateClient.SaveOrUpdateReturnOrder;
import com.midea.cloud.srm.model.common.BaseController;
import com.midea.cloud.srm.model.rbac.user.entity.LoginAppUser;
import com.midea.cloud.srm.model.suppliercooperate.order.dto.ReturnOrderPageDTO;
import com.midea.cloud.srm.model.suppliercooperate.order.dto.ReturnOrderRequestDTO;
import com.midea.cloud.srm.model.suppliercooperate.order.entry.ReturnOrder;
import com.midea.cloud.srm.supcooperate.order.service.IReturnOrderService;

/**
 * <pre>
 *   退货单表 前端控制器
 * </pre>
 *
 * @author huangbf3
 * @version 1.00.00
 *
 *          <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/4/17 15:06
 *  修改内容:
 *          </pre>
 */
@RestController
@RequestMapping("/order/returnOrder")
public class ReturnOrderController extends BaseController {

	@Autowired
	private IReturnOrderService iReturnOrderService;

	/**
	 * 分页
	 *
	 * @param requestDTO
	 * @return
	 */
	@PostMapping("/listPage")
	public PageInfo<ReturnOrder> listPage(@RequestBody ReturnOrderRequestDTO requestDTO) {
		LoginAppUser loginAppUser = AppUserUtil.getLoginAppUser();
		if (StringUtils.equals(UserType.VENDOR.name(), loginAppUser.getUserType())) {
			requestDTO.setVendorId(loginAppUser.getCompanyId());
		}
		return iReturnOrderService.listPage(requestDTO);
	}

	/**
	 * 暂存
	 * @param returnOrderSaveRequestDTO
	 * @return
	 */
	@PostMapping("/temporarySave")
	public Long temporarySaveReturnOrder(@RequestBody ReturnOrderSaveRequestDTO returnOrderSaveRequestDTO){
		return iReturnOrderService.temporarySave(returnOrderSaveRequestDTO);
	}

	/**
	 * 批量删除退货单
	 * @param ids
	 */
	@PostMapping("/batchDelete")
	public void batchDelete(@RequestBody List<Long> ids){
		iReturnOrderService.batchDelete(ids);
	}

	/**
	 * 提交退货单
	 * @param returnOrderSaveRequestDTO
	 * @return
	 */
	@PostMapping("/submit")
	public Long submitReturnOrder(@RequestBody ReturnOrderSaveRequestDTO returnOrderSaveRequestDTO){
		return iReturnOrderService.submit(returnOrderSaveRequestDTO);
	}

	/**
	 * 批量确认
	 * @param ids
	 */
	@PostMapping("/batchConfirm")
	public void batchConfirm(@RequestBody List<Long> ids){
		iReturnOrderService.batchConfirm(ids);
	}

	/**
	 * 批量拒绝
	 * @param returnOrder
	 */
	@PostMapping("/batchReject")
	public void batchReject(@RequestBody ReturnOrder returnOrder){
		iReturnOrderService.batchReject(returnOrder);
	}

	/**
	 * 退货单明细
	 * @param returnOrderId
	 * @return
	 */
	@PostMapping("/detail")
	public ReturnOrderVO detailReturnOrder(@RequestParam("returnOrderId") Long returnOrderId){
		return iReturnOrderService.detail(returnOrderId);
	}


	/**
	 * 采购商新增退货单
	 *
	 * @param param
	 */
	@PostMapping("/saveOrUpdateReturnOrder")
	public void saveOrUpdateReturnOrder(@RequestBody(required = true) @Validated SaveOrUpdateReturnOrder saveOrUpdateReturnOrder, BindingResult bindingResult) {
		if (bindingResult.getAllErrors() != null && bindingResult.getAllErrors().size() > 0) {
			throw new BaseException(bindingResult.getAllErrors().get(0).getDefaultMessage());
		}
		iReturnOrderService.saveOrUpdateReturnOrder(saveOrUpdateReturnOrder);
	}

	/**
	 * 根据ID获取退货订单
	 *
	 * @param returnOrderId
	 * @return
	 */
	@GetMapping("/getReturnOrderById")
	public ReturnOrder getReturnOrderById(Long returnOrderId) {
		return iReturnOrderService.getById(returnOrderId);
	}

	/**
	 * 确认退货订单
	 *
	 * @param returnOrder
	 */
	@PostMapping("/confirmReturnOrders")
	public void updateReturnOrders(@RequestBody(required = true) @Validated ConfirmReturnOrders confirmReturnOrders, BindingResult bindingResult) {
		if (bindingResult.getAllErrors() != null && bindingResult.getAllErrors().size() > 0) {
			throw new BaseException(bindingResult.getAllErrors().get(0).getDefaultMessage());
		}
		iReturnOrderService.confirmReturnOrders(confirmReturnOrders);
	}

	/**
	 * 根据id查询退货单头表
	 *
	 * @param ids
	 * @return
	 */
	@PostMapping("/getReturnOrderByIds")
	public List<ReturnOrder> getReturnOrderByIds(@RequestBody(required = true) List<Long> ids) {
		return CollectionUtils.isEmpty(ids) ? new ArrayList<ReturnOrder>() : iReturnOrderService.listByIds(ids);
	}

}
