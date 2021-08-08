package com.midea.cloud.srm.po.order.controller;

import com.github.pagehelper.PageInfo;
import com.midea.cloud.common.enums.UserType;
import com.midea.cloud.common.enums.order.ReturnOrderStatusEnum;
import com.midea.cloud.common.utils.AppUserUtil;
import com.midea.cloud.srm.model.common.BaseController;
import com.midea.cloud.srm.model.rbac.user.entity.LoginAppUser;
import com.midea.cloud.srm.model.suppliercooperate.order.dto.ReturnOrderRequestDTO;
import com.midea.cloud.srm.model.suppliercooperate.order.dto.ReturnOrderSaveRequestDTO;
import com.midea.cloud.srm.model.suppliercooperate.order.entry.ReturnOrder;
import com.midea.cloud.srm.model.suppliercooperate.order.vo.ReturnOrderVO;
import com.midea.cloud.srm.po.order.service.IReturnOrderService;
import org.apache.commons.lang3.StringUtils;
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
	private com.midea.cloud.srm.supcooperate.order.service.IReturnOrderService scIReturnOrderService;

	@Autowired
	private IReturnOrderService iReturnOrderService;

	/**
	 * 分页列表
	 * @param returnOrderRequestDTO
	 * @return
	 */
	@PostMapping("/listPage")
	public PageInfo<ReturnOrder> listPage(@RequestBody ReturnOrderRequestDTO returnOrderRequestDTO) {
		LoginAppUser loginAppUser = AppUserUtil.getLoginAppUser();
		if (StringUtils.equals(UserType.VENDOR.name(), loginAppUser.getUserType())) {
			returnOrderRequestDTO.setVendorId(loginAppUser.getCompanyId());
		}
		return scIReturnOrderService.listPage(returnOrderRequestDTO);
	}

	/**
	 * 暂存
	 * @param returnOrderSaveRequestDTO
	 * @return
	 */
	@PostMapping("/temporarySave")
	public Long temporarySave(@RequestBody ReturnOrderSaveRequestDTO returnOrderSaveRequestDTO){
		return scIReturnOrderService.temporarySave(returnOrderSaveRequestDTO);
	}

	/**
	 * 批量删除
	 * @param ids
	 */
	@PostMapping("/batchDelete")
	public void batchDelete(@RequestBody List<Long> ids){
		scIReturnOrderService.batchDelete(ids);
	}

	/**
	 * 提交
	 * @param returnOrderSaveRequestDTO
	 * @return
	 */
	@PostMapping("/submit")
	public Long submit(@RequestBody ReturnOrderSaveRequestDTO returnOrderSaveRequestDTO){
		return scIReturnOrderService.submit(returnOrderSaveRequestDTO);
	}



	/**
	 * 查询详情
	 * @param returnOrderId
	 * @return
	 */
	@GetMapping("/getReturnOrderById")
	public ReturnOrderVO getReturnOrderById(@RequestParam("returnOrderId") Long returnOrderId) {
		return scIReturnOrderService.detail(returnOrderId);
	}

	/**
	 * 新增
	 *
	 * @param returnOrderSaveRequestDTO
	 */
	@PostMapping("/save")
	public void save(@RequestBody ReturnOrderSaveRequestDTO returnOrderSaveRequestDTO) {
		returnOrderSaveRequestDTO.getReturnOrder().setReturnOrderId(null);
		iReturnOrderService.saveOrUpdateReturnOrder(returnOrderSaveRequestDTO, ReturnOrderStatusEnum.EDIT);
	}

	/**
	 * 更新
	 *
	 * @param returnOrderSaveRequestDTO
	 */
	@PostMapping("/update")
	public void update(@RequestBody ReturnOrderSaveRequestDTO returnOrderSaveRequestDTO) {
		iReturnOrderService.saveOrUpdateReturnOrder(returnOrderSaveRequestDTO, ReturnOrderStatusEnum.EDIT);
	}

	/**
	 * 确认
	 *
	 * @param returnOrderSaveRequestDTO
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
