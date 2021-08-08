package com.midea.cloud.srm.supcooperate.order.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.midea.cloud.srm.model.suppliercooperate.order.dto.WarehouseReceiptDTO;
import com.midea.cloud.srm.model.suppliercooperate.order.vo.WarehouseReceiptVO;
import com.midea.cloud.srm.model.suppliercooperate.reconciliation.entry.WarehouseReceiptDetail;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageInfo;
import com.midea.cloud.common.enums.UserType;
import com.midea.cloud.common.utils.AppUserUtil;
import com.midea.cloud.srm.feign.supcooperate.SupcooperateClient.SaveOrUpdateWarehouseReceiptAndUpdateOrderDetail;
import com.midea.cloud.srm.model.common.BaseController;
import com.midea.cloud.srm.model.pm.po.dto.WarehouseReceiptPageDTO;
import com.midea.cloud.srm.model.pm.po.dto.WarehouseReceiptPageQueryDTO;
import com.midea.cloud.srm.model.pm.ps.statement.dto.StatementReceiptDTO;
import com.midea.cloud.srm.model.rbac.user.entity.LoginAppUser;
import com.midea.cloud.srm.model.suppliercooperate.reconciliation.entry.WarehouseReceipt;
import com.midea.cloud.srm.supcooperate.reconciliation.service.IWarehouseReceiptService;

/**
 *
 * <pre>
 * 订单入库
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
 *  修改日期: May 25, 20203:14:58 PM
 *  修改内容:
 *          </pre>
 */
@RestController
@RequestMapping("/order/warehouseReceipt")
public class WarehouseReceiptController extends BaseController {

	@Autowired
	private IWarehouseReceiptService iWarehouseReceiptService;

	/**
	 * 入库单分页查询
	 * @param warehouseReceiptPageQueryDTO
	 * @return
	 */
	@PostMapping("/wareHouseReceiptListPage")
	public PageInfo<WarehouseReceiptVO> wareHouseReceiptListPage(@RequestBody WarehouseReceiptPageQueryDTO warehouseReceiptPageQueryDTO) {
		LoginAppUser loginAppUser = AppUserUtil.getLoginAppUser();
		if (StringUtils.equals(UserType.VENDOR.name(), loginAppUser.getUserType())) {
			warehouseReceiptPageQueryDTO.setVendorId(loginAppUser.getCompanyId());
		}
		return iWarehouseReceiptService.listPage(warehouseReceiptPageQueryDTO);
	}

	/**
	 * 对账单选择入库单
	 *
	 * @param warehouseReceiptPageQueryDTO
	 * @return
	 */
	@PostMapping("/listStatementReceiptDTOPage")
	public PageInfo<StatementReceiptDTO> listStatementReceiptDTOPage(@RequestBody WarehouseReceiptPageQueryDTO warehouseReceiptPageQueryDTO) {
		return iWarehouseReceiptService.listStatementReceiptDTOPage(warehouseReceiptPageQueryDTO);
	}

	/**
	 * 根据送货ID查询入库记录
	 *
	 * @param deliveryNoteDetailIds
	 * @return
	 */
	@PostMapping("/queryWarehouseReceiptByDeliveryNoteDetailIds")
	public List<WarehouseReceipt> queryWarehouseReceiptByDeliveryNoteDetailIds(@RequestBody List<Long> deliveryNoteDetailIds) {
		return CollectionUtils.isEmpty(deliveryNoteDetailIds) ? new ArrayList<WarehouseReceipt>() : iWarehouseReceiptService.list(new QueryWrapper<WarehouseReceipt>().in("DELIVERY_NOTE_DETAIL_ID", deliveryNoteDetailIds));
	}

	/**
	 * 根据主键查询入库记录
	 *
	 * @param ids
	 * @return
	 */
	@PostMapping("/queryWarehouseReceiptByIds")
	public List<WarehouseReceipt> queryWarehouseReceiptByIds(@RequestBody List<Long> ids) {
		return CollectionUtils.isEmpty(ids) ? new ArrayList<WarehouseReceipt>() : iWarehouseReceiptService.listByIds(ids);
	}

	/**
	 * 批量保存/更新入库记录
	 *
	 * @param warehouseReceiptList
	 */
	@PostMapping("/saveOrUpdateWarehouseReceipt")
	public void saveOrUpdateWarehouseReceipt(@RequestBody List<WarehouseReceipt> warehouseReceiptList) {
		iWarehouseReceiptService.saveOrUpdateBatch(warehouseReceiptList);
	}

	/**
	 * 保存入库&更新订单行
	 *
	 * @param param
	 */
	@PostMapping("/saveOrUpdateWarehouseReceiptAndUpdateOrderDetail")
	public void saveOrUpdateWarehouseReceiptAndUpdateOrderDetail(@RequestBody SaveOrUpdateWarehouseReceiptAndUpdateOrderDetail param) {
		iWarehouseReceiptService.saveOrUpdateWarehouseReceiptAndUpdateOrderDetail(param);
	}

	/**
	 * 暂存
	 * @param warehouseReceiptDTO
	 * @return
	 */
	@PostMapping("/temporarySave")
	public Long temporarySave(@RequestBody WarehouseReceiptDTO warehouseReceiptDTO){
		return iWarehouseReceiptService.temporarySave(warehouseReceiptDTO);
	}

	/**
	 * 提交
	 * @param warehouseReceiptDTO
	 * @return
	 */
	@PostMapping("/submit")
	public Long warehouseReceiptSubmit(@RequestBody WarehouseReceiptDTO warehouseReceiptDTO){
		return iWarehouseReceiptService.submit(warehouseReceiptDTO);
	}

	/**
	 * 批量删除入库单
	 * @param ids
	 */
	@PostMapping("/batchDelete")
	public void batchDeleteWarehouseReceipt(@RequestBody List<Long> ids){
		iWarehouseReceiptService.batchDelete(ids);
	}

	/**
	 * 查看入库单详情
	 * @param id
	 * @return
	 */
	@GetMapping("/detail")
	public WarehouseReceiptDTO detailWarehouseReceipt(@RequestParam("id") Long id){
		return iWarehouseReceiptService.detailWarehouseReceipt(id);
	}

	/**
	 * 批量确认入库单
	 * @param ids
	 */
	@PostMapping("/batchConfirm")
	public void batchConfirmWarehouseReceipts(@RequestBody List<Long> ids){
		iWarehouseReceiptService.batchConfirm(ids);
	}

	/**
	 * 批量冲销入库单
	 * @param ids
	 */
	@PostMapping("/batchWriteOff")
	public void batchWriteOff(@RequestBody List<Long> ids){
		iWarehouseReceiptService.batchWriteOff(ids);
	}
}
