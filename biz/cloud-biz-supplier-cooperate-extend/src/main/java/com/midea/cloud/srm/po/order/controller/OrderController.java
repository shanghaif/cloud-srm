package com.midea.cloud.srm.po.order.controller;

import com.github.pagehelper.PageInfo;
import com.midea.cloud.common.enums.UserType;
import com.midea.cloud.common.enums.sup.VendorSiteCode;
import com.midea.cloud.common.exception.BaseException;
import com.midea.cloud.common.utils.AppUserUtil;
import com.midea.cloud.common.utils.PageUtil;
import com.midea.cloud.srm.feign.contract.ContractClient;
import com.midea.cloud.srm.feign.inq.InqClient;
import com.midea.cloud.srm.model.cm.contract.dto.ContractMaterialDTO;
import com.midea.cloud.srm.model.common.BaseController;
import com.midea.cloud.srm.model.inq.price.entity.PriceLibrary;
import com.midea.cloud.srm.model.pm.po.dto.NetPriceQueryDTO;
import com.midea.cloud.srm.model.rbac.user.entity.LoginAppUser;
import com.midea.cloud.srm.model.suppliercooperate.order.dto.OrderDTO;
import com.midea.cloud.srm.model.suppliercooperate.order.dto.OrderRequestDTO;
import com.midea.cloud.srm.model.suppliercooperate.order.dto.OrderSaveRequestDTO;
import com.midea.cloud.srm.model.suppliercooperate.order.entry.Order;
import com.midea.cloud.srm.model.suppliercooperate.order.entry.OrderDetail;
import com.midea.cloud.srm.po.order.enums.CategoryTypeEnum;
import com.midea.cloud.srm.po.order.service.IOrderService;
import com.midea.cloud.srm.supcooperate.order.service.IOrderPaymentProvisionService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * <pre>
 *   采购订单表 前端控制器
 * </pre>
 *
 * @author huangbf3
 * @version 1.00.00
 *
 *          <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/3/19 19:06
 *  修改内容:
 *          </pre>
 */
@RestController
@RequestMapping("/po/order")
public class OrderController extends BaseController {

	@Autowired
	private IOrderService iOrderService;

	@Autowired
	private com.midea.cloud.srm.supcooperate.order.service.IOrderService scIOrderService;

	@Autowired
	private IOrderPaymentProvisionService scIOrderPaymentProvisionService;

	@Autowired
	private InqClient inqClient;

	@Autowired
	private ContractClient contractClient;

	@Autowired
	private com.midea.cloud.srm.supcooperate.order.controller.OrderController orderController;

	/**
	 * 采购商分页查询
	 *
	 * @param orderRequestDTO 订单数据请求传输对象
	 * @return
	 */
	@PostMapping("/listPage")
	public PageInfo<OrderDTO> listPage(@RequestBody OrderRequestDTO orderRequestDTO) {
		LoginAppUser loginAppUser = AppUserUtil.getLoginAppUser();
		if (!StringUtils.equals(UserType.VENDOR.name(), loginAppUser.getUserType()) && !StringUtils.equals(UserType.BUYER.name(), loginAppUser.getUserType())) {
			Assert.isTrue(false, "用户类型不存在");
		}
		if (StringUtils.equals(UserType.VENDOR.name(), loginAppUser.getUserType())) {
			if(Objects.isNull(loginAppUser.getCompanyId())){
				return new PageInfo<>(new ArrayList<>());
			}
			orderRequestDTO.setVendorId(loginAppUser.getCompanyId());
		}else{
			orderRequestDTO.setCreatedId(loginAppUser.getUserId());
		}
		PageUtil.startPage(orderRequestDTO.getPageNum(), orderRequestDTO.getPageSize());
		return new PageInfo<OrderDTO>(scIOrderService.listPage(orderRequestDTO));
	}

	@PostMapping("/listPageUpdates")
	public PageInfo<OrderDTO> listPageUpdates(@RequestBody OrderRequestDTO orderRequestDTO){
		LoginAppUser loginAppUser = AppUserUtil.getLoginAppUser();
		if (!StringUtils.equals(UserType.VENDOR.name(), loginAppUser.getUserType()) && !StringUtils.equals(UserType.BUYER.name(), loginAppUser.getUserType())) {
			Assert.isTrue(false, "用户类型不存在");
		}
		if (StringUtils.equals(UserType.VENDOR.name(), loginAppUser.getUserType())) {
			if(Objects.isNull(loginAppUser.getCompanyId())){
				return new PageInfo<>(new ArrayList<>());
			}
			orderRequestDTO.setVendorId(loginAppUser.getCompanyId());
		}else{
			orderRequestDTO.setCreatedId(loginAppUser.getUserId());
		}
		PageUtil.startPage(orderRequestDTO.getPageNum(), orderRequestDTO.getPageSize());
		return new PageInfo<OrderDTO>(scIOrderService.listPageUpdates(orderRequestDTO));
	}

	/**
	 * 创建订单-保存
	 *
	 * @param param
	 */
	@PostMapping("/save")
	public Long save(@RequestBody OrderSaveRequestDTO param) {
//		待验证2020年11月18日23:12:36，验证后开放，防止早上用户爆炸
//		saveOrderMaterialLogic(param);
		return iOrderService.save(param);
	}

	/**
	 * 保存订单。校验物料的逻辑；个性化逻辑；
	 * @param param
	 */
	private void saveOrderMaterialLogic(OrderSaveRequestDTO param){
		Order order = param.getOrder();
		List<OrderDetail> orderDetailList = param.getDetailList();
		//2020-11-18 采购订单-物料大类为“综合类物资”，且物料编码前两位不是61/78时，“供应商地点“ 只可选择“材料”、“费用”中的一个；
		for(OrderDetail orderDetail : orderDetailList){
			boolean comprehensive = CategoryTypeEnum.COMPREHENSIVE_MATERIALS.getCode().equals(orderDetail.getBigCategoryCode());
			String materialCode = orderDetail.getMaterialCode();
			boolean prefixNotMatch = StringUtils.isNotBlank(materialCode) &&
					materialCode.indexOf("61") != 0 &&
					materialCode.indexOf("78") != 0;
			boolean siteNotMatch = !VendorSiteCode.MATERIAL.getName().equals(order.getCeeaCostType()) &&
					!VendorSiteCode.EXPENSE.getName().equals(order.getCeeaCostType());
			if(comprehensive && prefixNotMatch && siteNotMatch){
				throw new BaseException(String.format("综合类物资物料[%s]编码不是61、78开头，供应商地点只能选择'材料'或'费用' 请确认;" , orderDetail.getMaterialName()));
			}
		}
	}


//	/**
//	 * 创建订单-提交
//	 */
//	@PostMapping("/submit")
//	public void submitOrder(@RequestBody OrderSaveRequestDTO param){
//		iOrderService.submitOrder(param);
//	}
	/**
	 * 创建订单-提交 新版界面发起审批流程
	 */
	@PostMapping("/submitOrder")
	public Long submitOrder (@RequestBody OrderSaveRequestDTO param) {
		return iOrderService.submitOrderNew(param);
	}
	/**
	 * 创建订单-审批
	 */
	@PostMapping("/approval")
	public void approval(@RequestBody OrderSaveRequestDTO param){
		iOrderService.approval(param);
	}

	/**
	 * 创建订单-驳回
	 */
	@PostMapping("/reject")
	public void reject(@RequestBody OrderSaveRequestDTO param){
		iOrderService.reject(param);
	}


	/**
	 * 创建订单-供方确认
	 */
	@PostMapping("/supplierConfirm")
	public void supplierConfirm(@RequestBody OrderSaveRequestDTO param){
		iOrderService.supplierConfirm(param);
	}

	/**
	 * 创建订单-供方拒绝
	 */
	@PostMapping("/supplierReject")
	public void supplierReject(@RequestBody OrderSaveRequestDTO param){
		iOrderService.supplierReject(param);
	}

	/**
	 * 查询订单详情
	 *
	 * @param orderId
	 * @return
	 */
	@GetMapping("/queryOrderById")
	public OrderSaveRequestDTO queryOrderById(Long orderId) {
		return iOrderService.queryOrderById(orderId);
	}


	/**
	 * 查询净价
	 *
	 * @param netPriceQueryDTO
	 * @return
	 */
	@PostMapping("/queryPrice")
	public PriceLibrary queryPrice(@RequestBody NetPriceQueryDTO netPriceQueryDTO) {
		return inqClient.getPriceLibraryByParam(netPriceQueryDTO);
	}

	/**
	 * 价格目录列表
	 *
	 * @param priceLibrary
	 * @return
	 */
	@PostMapping("/listPagePriceLibrary")
	public PageInfo<PriceLibrary> listPagePriceLibrary(@RequestBody PriceLibrary priceLibrary) {
		return inqClient.listPagePriceLibrary(priceLibrary);
	}

	/**
	 * 合同物料列表查询
	 *
	 * @param contractMaterialDTO
	 * @return
	 */
	@PostMapping("/queryContractMaterialPage")
	public PageInfo<ContractMaterialDTO> queryContractMaterialPage(@RequestBody ContractMaterialDTO contractMaterialDTO) {
		return contractClient.listPageMaterialDTOByParm(contractMaterialDTO);
	}

	/**
	 * 删除订单
	 */
	@GetMapping("/deleteOrder")
	public void deleteOrder(Long orderId){
		iOrderService.deleteOrder(orderId);
	}

	/**
	 * 废弃订单
	 * @param orderId
	 */
	@GetMapping("/abandon")
	public void abandon(Long orderId) {
		Assert.notNull(orderId,"废弃订单id不能为空");
		iOrderService.abandon(orderId);
	}

	/**
	 * 开始订单变更
	 */
	@PostMapping("/startEditStatus")
	public void startEditStatus(@RequestBody OrderSaveRequestDTO param){
		iOrderService.startEditStatus(param.getOrder().getOrderId());
	}

	/**
	 * 编辑订单-保存
	 */
	@PostMapping("/saveInEditStatus")
	public void saveInEditStatus(@RequestBody OrderSaveRequestDTO param){
		iOrderService.saveInEditStatus(param);
	}

	/**
	 * 编辑订单-提交
	 */
	@PostMapping("/submitInEditStatus")
	public void submitInEditStatus(@RequestBody OrderSaveRequestDTO param){
		iOrderService.submitInEditStatus(param);
	}

	/**
	 * 编辑订单-审批
	 */
	@PostMapping("/approvalInEditStatus")
	public void approvalInEditStatus(@RequestBody OrderSaveRequestDTO param){
		iOrderService.approvalInEditStatus(param);
	}

	/**
	 * 编辑订单-拒绝
	 */
//	@PostMapping("/rejectInEditStatus")
//	public void rejectInEditStatus(@RequestBody OrderSaveRequestDTO param){
//		iOrderService.rejectInEditStatus(param);
//	}

	/**
	 * 编辑订单-撤回
	 */
	@PostMapping("/withdrawInEditStatus")
	public void withdrawInEditStatus(@RequestBody OrderSaveRequestDTO param) throws Exception{
		iOrderService.withdrawInEditStatus(param);
	}

	/**
	 * 编辑订单-供方确认
	 */
	@PostMapping("/supplierConfirmInEditStatus")
	public void supplierConfirmInEditStatus(@RequestBody OrderSaveRequestDTO param){
		iOrderService.supplierConfirmInEditStatus(param);
	}

	/**
	 * 编辑订单-供方拒绝
	 */
	@PostMapping("/supplierRejectInEditStatus")
	public void supplierRejectInEditStatus(@RequestBody OrderSaveRequestDTO param){
		iOrderService.supplierRejectInEditStatus(param);
	}

	/**
	 * 保存付款条款
	 */
	@PostMapping("/saveOrderPaymentProvision")
	public void saveOrderPaymentProvision(@RequestBody OrderSaveRequestDTO param){
		scIOrderPaymentProvisionService.savePaymentProvision(param);
	}


	@PostMapping("/testOrder")
	public void testOrder(Long orderId){
		iOrderService.cancelPurchaseOrderSoapBiz(orderId);
	}


	/**
	 * 手工重推
	 * @param orderNumbers
	 */
	@PostMapping("/manualPush")
	public void manualPush(@RequestBody List<String> orderNumbers){
		iOrderService.manualPush(orderNumbers);
	}

	/**
	 * 批量删除采购订单
	 * @param ids
	 */
	@PostMapping("/batchDelete")
	public void batchDelete(@RequestBody List<Long> ids){
		iOrderService.batchDelete(ids);
	}

}
