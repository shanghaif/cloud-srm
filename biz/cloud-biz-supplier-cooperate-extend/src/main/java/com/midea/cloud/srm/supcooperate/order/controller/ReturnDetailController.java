package com.midea.cloud.srm.supcooperate.order.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageInfo;
import com.midea.cloud.srm.model.common.BaseController;
import com.midea.cloud.srm.model.pm.ps.statement.dto.StatementReturnDTO;
import com.midea.cloud.srm.model.suppliercooperate.order.dto.ReturnDetailDTO;
import com.midea.cloud.srm.model.suppliercooperate.order.dto.ReturnOrderRequestDTO;
import com.midea.cloud.srm.model.suppliercooperate.order.entry.ReturnDetail;
import com.midea.cloud.srm.supcooperate.order.service.IReturnDetailService;

/**
 * <pre>
 *   退货单明细表 前端控制器
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
@RequestMapping("/order/returnDetail")
public class ReturnDetailController extends BaseController {

	@Autowired
	private IReturnDetailService iReturnDetailService;

	/**
	 * 根据主键获取
	 *
	 * @param ids
	 * @return
	 */
	@PostMapping("/queryReturnDetailByIds")
	public List<ReturnDetail> queryReturnDetailByIds(@RequestBody List<Long> ids) {
		return CollectionUtils.isEmpty(ids) ? new ArrayList<ReturnDetail>() : iReturnDetailService.listByIds(ids);
	}

	/**
	 * 根据退货单ID查询退货明细行
	 *
	 * @param returnOrderId
	 * @return
	 */
	@GetMapping("/getReturnDetailListByReturnOrderId")
	public List<ReturnDetailDTO> getReturnDetailListByReturnOrderId(@RequestParam(required = true) Long returnOrderId) {
		return iReturnDetailService.getReturnDetailListByReturnOrderId(returnOrderId);
	}

	/**
	 * 根据退货单ID集合查询退货明细行
	 *
	 * @param returnOrderIds
	 * @return
	 */
	@PostMapping("/queryReturnDetailByReturnOrderIds")
	public List<ReturnDetail> queryReturnDetailByReturnOrderIds(@RequestBody(required = true) List<Long> returnOrderIds) {
		return CollectionUtils.isEmpty(returnOrderIds) ? new ArrayList<ReturnDetail>() : iReturnDetailService.list(new QueryWrapper<ReturnDetail>().in("RETURN_ORDER_ID", returnOrderIds));
	}

	/**
	 * 更新退货订单行
	 *
	 * @param returnDetails
	 */
	@PostMapping("/saveOrUpdateReturnDetails")
	public void saveOrUpdateReturnDetails(@RequestBody List<ReturnDetail> returnDetails) {
		iReturnDetailService.saveOrUpdateBatch(returnDetails);
	}

	/**
	 * 对账单选择退货单
	 *
	 * @param returnOrderRequestDTO
	 * @return
	 */
	@PostMapping("/listStatementReturnDTOPage")
	public PageInfo<StatementReturnDTO> listStatementReturnDTOPage(@RequestBody ReturnOrderRequestDTO returnOrderRequestDTO) {
		return iReturnDetailService.listStatementReturnDTOPage(returnOrderRequestDTO);
	}
}
