package com.midea.cloud.srm.po.order.controller;

import com.github.pagehelper.PageInfo;
import com.midea.cloud.common.enums.UserType;
import com.midea.cloud.common.utils.AppUserUtil;
import com.midea.cloud.srm.model.common.BaseController;
import com.midea.cloud.srm.model.pm.po.dto.WarehouseReceiptConfirmDTO;
import com.midea.cloud.srm.model.pm.po.dto.WarehouseReceiptPageQueryDTO;
import com.midea.cloud.srm.model.pm.po.dto.WriteOffDTO;
import com.midea.cloud.srm.model.rbac.user.entity.LoginAppUser;
import com.midea.cloud.srm.model.suppliercooperate.order.dto.WarehouseReceiptDTO;
import com.midea.cloud.srm.model.suppliercooperate.order.vo.WarehouseReceiptVO;
import com.midea.cloud.srm.po.order.service.IWarehouseReceiptService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
@RequestMapping("/po/warehouseReceipt")
public class WarehouseReceiptController extends BaseController {

	@Autowired
	private IWarehouseReceiptService iWarehouseReceiptService;

	@Autowired
	private com.midea.cloud.srm.supcooperate.reconciliation.service.IWarehouseReceiptService scIWarehouseReceiptService;

	/**
	 * 分页查询入库单信息
	 * @param warehouseReceiptPageQueryDTO
	 * @return
	 */
	@PostMapping("/listPage")
	public PageInfo<WarehouseReceiptVO> listPage(@RequestBody WarehouseReceiptPageQueryDTO warehouseReceiptPageQueryDTO) {
		LoginAppUser loginAppUser = AppUserUtil.getLoginAppUser();
		if (StringUtils.equals(UserType.VENDOR.name(), loginAppUser.getUserType())) {
			warehouseReceiptPageQueryDTO.setVendorId(loginAppUser.getCompanyId());
		}
		return scIWarehouseReceiptService.listPage(warehouseReceiptPageQueryDTO);
	}

	/**
	 * 暂存
	 * @param warehouseReceiptDTO
	 * @return
	 */
	@PostMapping("/temporarySave")
	public Long temporarySave(@RequestBody WarehouseReceiptDTO warehouseReceiptDTO){
		return scIWarehouseReceiptService.temporarySave(warehouseReceiptDTO);
	}

	/**
	 * 提交
	 * @param warehouseReceiptDTO
	 * @return
	 */
	@PostMapping("/submit")
	public Long submit(@RequestBody WarehouseReceiptDTO warehouseReceiptDTO){
		return scIWarehouseReceiptService.submit(warehouseReceiptDTO);
	}

	/**
	 * 批量删除入库单
	 * @param ids
	 */
	@PostMapping("/batchDelete")
	public void batchDelete(@RequestBody List<Long> ids){
		scIWarehouseReceiptService.batchDelete(ids);
	}

	/**
	 * 查看入库单详情
	 * @param id
	 * @return
	 */
	@GetMapping("/detail")
	public WarehouseReceiptDTO detail(@RequestParam("id") Long id){
		return scIWarehouseReceiptService.detailWarehouseReceipt(id);
	}

	/**
	 * 批量确认入库
	 * @param ids
	 */
	@PostMapping("/batchConfirm")
	public void batchConfirm(@RequestBody List<Long> ids){
		scIWarehouseReceiptService.batchConfirm(ids);
	}

	/**
	 * 批量退货
	 * @param ids
	 */
	@PostMapping("/batchWriteOff")
	public void batchWriteOff(@RequestBody List<Long> ids){
		scIWarehouseReceiptService.batchWriteOff(ids);
	}



	/**
	 * 确认入库
	 *
	 * @param warehouseReceiptConfirmDTOList
	 */
	@PostMapping("/confirm")
	public void confirm(@RequestBody List<WarehouseReceiptConfirmDTO> warehouseReceiptConfirmDTOList) {
		iWarehouseReceiptService.confirm(warehouseReceiptConfirmDTOList);
	}

	/**
	 * 冲销
	 *
	 * @param writeOffDTO
	 */
	@PostMapping("/writeOff")
	public void writeOff(@RequestBody WriteOffDTO writeOffDTO) {
		iWarehouseReceiptService.writeOff(writeOffDTO);
	}

}
