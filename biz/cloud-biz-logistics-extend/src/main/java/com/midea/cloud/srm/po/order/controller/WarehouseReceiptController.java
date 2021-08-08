package com.midea.cloud.srm.po.order.controller;

import com.midea.cloud.srm.model.common.BaseController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

//	@Autowired
//	private IWarehouseReceiptService iWarehouseReceiptService;
//
//	@Autowired
//	private SupcooperateClient supcooperateClient;
//
//	/**
//	 * 分页查询
//	 *
//	 * @param orderRequestDTO 订单数据请求传输对象
//	 * @return
//	 */
//	@PostMapping("/listPage")
//	public PageInfo<WarehouseReceiptPageDTO> listPage(@RequestBody WarehouseReceiptPageQueryDTO warehouseReceiptPageQueryDTO) {
//		return supcooperateClient.wareHouseReceiptPage(warehouseReceiptPageQueryDTO);
//	}
//
//	/**
//	 * 确认入库
//	 *
//	 * @param warehouseReceiptConfirmDTO
//	 */
//	@PostMapping("/confirm")
//	public void confirm(@RequestBody List<WarehouseReceiptConfirmDTO> warehouseReceiptConfirmDTOList) {
//		iWarehouseReceiptService.confirm(warehouseReceiptConfirmDTOList);
//	}
//
//	/**
//	 * 冲销
//	 *
//	 * @param writeOffDTO
//	 */
//	@PostMapping("/writeOff")
//	public void writeOff(@RequestBody WriteOffDTO writeOffDTO) {
//		iWarehouseReceiptService.writeOff(writeOffDTO);
//	}

}
