package com.midea.cloud.srm.supcooperate.order.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageInfo;
import com.midea.cloud.common.enums.UserType;
import com.midea.cloud.common.exception.BaseException;
import com.midea.cloud.common.utils.AppUserUtil;
import com.midea.cloud.common.utils.ExcelUtil;
import com.midea.cloud.common.utils.PageUtil;
import com.midea.cloud.component.context.i18n.LocaleHandler;
import com.midea.cloud.srm.feign.file.FileCenterClient;
import com.midea.cloud.srm.model.base.work.dto.WorkCount;
import com.midea.cloud.srm.model.common.BaseController;
import com.midea.cloud.srm.model.file.upload.entity.Fileupload;
import com.midea.cloud.srm.model.rbac.user.entity.LoginAppUser;
import com.midea.cloud.srm.model.suppliercooperate.excel.ErrorCell;
import com.midea.cloud.srm.model.suppliercooperate.order.dto.*;
import com.midea.cloud.srm.model.suppliercooperate.order.entry.Order;
import com.midea.cloud.srm.model.suppliercooperate.order.entry.OrderDetail;
import com.midea.cloud.srm.supcooperate.order.service.IOrderDetailService;
import com.midea.cloud.srm.supcooperate.order.service.IOrderService;
import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.write.WriteException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

//com.midea.cloud.srm.model.suppliercooperate.order.entity.OrderDetail

/**
 * <pre>
 *   采购订单表 前端控制器
 * </pre>
 *
 * @author chenwj92
 * @version 1.00.00
 *
 *          <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/8/03 19:06
 *  修改内容:
 *          </pre>
 */
@RestController
@RequestMapping("/order/order")
@Slf4j
public class OrderController extends BaseController {

    private final static int HEAD_LINE_NUM = 2;// excel导入文件标题所占行数
    @Autowired
    private FileCenterClient fileCenterClient;

    @Autowired
    private IOrderService iOrderService;
    
    @Autowired
    private IOrderDetailService iOrderDetailService;

    /**
     * 通过订单编号查询订单
     *
     * @param orderNumber
     * @return
     */
    @GetMapping("/getOrderByOrderNumber")
    public Order getOrderByOrderNumber(String orderNumber) {
        return iOrderService.getOne(new QueryWrapper<Order>().eq("ORDER_NUMBER", orderNumber));
    }

    /**
     * 根据id查询订单
     *
     * @param orderId
     * @return
     */
    @GetMapping("/getOrderById")
    public Order getOrderById(Long orderId) {
        return iOrderService.getById(orderId);
    }

    /**
     * 根据id批量查询订单
     *
     * @param orderIdList
     * @return
     */
    @PostMapping("/getOrderByIds")
    public List<Order> getOrderByIds(@RequestBody List<Long> orderIdList) {
        return orderIdList == null || orderIdList.size() == 0 ? new ArrayList<Order>() : iOrderService.listByIds(orderIdList);
    }

    /**
     * 批量修改订单头(todo 只是修改订单头)
     *
     * @param orderList
     */
    @PostMapping("/batchUpdateOrder")
    public void batchUpdateOrder(@RequestBody List<Order> orderList) {
        iOrderService.updateBatchById(orderList);
    }

	/**
	 * 保存订单头(todo 保存订单头)
	 * @param order
	 */
	@PostMapping("/saveOrder")
	public void saveOrder(@RequestBody Order order) {
		iOrderService.saveOrUpdate(order);
	}

	/**
	 * ？？
	 * @param externalIds
	 * @return
	 */
	@PostMapping("/getOrderByExternalIds")
	public List<Order> getOrderByExternalId(@RequestBody List<Long> externalIds) {
		return externalIds == null || externalIds.size() == 0 ? new ArrayList<Order>() : iOrderService.list(new QueryWrapper<Order>().in("EXTERNAL_ID", externalIds));
	}

	/**
	 * 保存订单
	 * @param param
	 */
	@PostMapping("/saveOrderForm")
	public void saveOrderForm(@RequestBody OrderSaveRequestDTO param) {
		iOrderService.saveOrUpdate(param);
	}

	/*todo 流程修改*/
	/**
	 * 待确认订单统计
	 *
	 * @return
	 */
	@GetMapping("/countSubmit")
	public WorkCount countSubmit() {
		LoginAppUser loginAppUser = AppUserUtil.getLoginAppUser();
		if (!StringUtils.equals(UserType.VENDOR.name(), loginAppUser.getUserType()) && !StringUtils.equals(UserType.BUYER.name(), loginAppUser.getUserType())) {
			Assert.isTrue(false, "用户类型不存在");
		}
		OrderRequestDTO orderRequestDTO = new OrderRequestDTO();
		if (StringUtils.equals(UserType.VENDOR.name(), loginAppUser.getUserType())) {
			orderRequestDTO.setVendorId(loginAppUser.getCompanyId());
		}
		/*orderRequestDTO.setOrderStatus(PurchaseOrderEnum.UNISSUED.getValue());*/

		return iOrderService.countSubmit(orderRequestDTO);
	}

	/**
	 * 分页查询
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
		return new PageInfo<OrderDTO>(iOrderService.listPage(orderRequestDTO));
	}

	/**
	 * 分页查询(订单变更页面)
	 * @param orderRequestDTO
	 * @return
	 */
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
		return new PageInfo<OrderDTO>(iOrderService.listPageUpdates(orderRequestDTO));
	}


	/**
	 * 采购商新增订单
	 *
	 * @param param
	 */
	@PostMapping("/save")
	public void save(@RequestBody OrderSaveRequestDTO param) {
		iOrderService.saveOrUpdate(param);
	}

	/**
	 * 采购商编辑订单
	 *
	 * @param param
	 */
	@PostMapping("/update")
	public void update(@RequestBody OrderSaveRequestDTO param) {
		Order order = param.getOrder();
		List<OrderDetail> detailList = param.getDetailList();
		iOrderService.saveOrUpdate(param);
	}

	/**
	 * 删除订单
	 */
	@PostMapping("/delete")
	public void delete(@RequestParam("orderId") Long orderId){
		Assert.notNull(orderId, LocaleHandler.getLocaleMsg("orderId不可为空"));
		iOrderService.delete(orderId);
	}

	/**
	 * 采购商批量提交订单
	 *
	 * @param ids 订单ids
	 */
	@PostMapping("/submitBatch")
	public void submitBatch(@RequestBody List<Long> ids) {
		iOrderService.submitBatch(ids);
	}

	/**
	 * 供应商批量确认订单
	 *
	 * @param ids 订单ids
	 */
	@PostMapping("/comfirmBatch")
	public void comfirmBatch(@RequestBody List<Long> ids) {
		iOrderService.comfirmBatch(ids);
	}

	/**
	 * 采购订单查询
	 *
	 * @param order 条件
	 */
	@PostMapping("/listParamOrder")
	public List<Order> listParamOrder(@RequestBody Order order) {
		Assert.notNull(order,"对象不能为空");
		QueryWrapper<Order> orderQueryWrapper = new QueryWrapper<>(order);
		return iOrderService.list(orderQueryWrapper);
	}

	/**
	 * 供应商确认订单
	 *
	 * @param param 订单
	 */
	@PostMapping("/comfirm")
	public void comfirm(@RequestBody OrderSaveRequestDTO param) {
		LoginAppUser loginAppUser = AppUserUtil.getLoginAppUser();
		if (!StringUtils.equals(UserType.VENDOR.name(), loginAppUser.getUserType())) {
			Assert.isTrue(false, "不是供应商操作");
		}

		Order order = param.getOrder();
		List<OrderDetail> detailList = param.getDetailList();

		Assert.notNull(order.getOrderId(), "订单ID不能为空");
		Assert.notNull(detailList, "订单列表不能为空");

		for (OrderDetail detail : detailList) {
			Assert.notNull(detail.getOrderDetailId(), "订单明细ID不能为空");
			Assert.notNull(detail.getVendorDeliveryDate(), "供方要求交期不能为空");
		}

		order.setComfirmId(loginAppUser.getUserId());
		order.setComfirmTime(new Date());
		order.setComfirmBy(loginAppUser.getUsername());

		iOrderService.comfirm(order, detailList);
	}

	/**
	 * 供应商批量拒绝订单
	 *
	 * @param list 订单列表
	 */
	@PostMapping("/refuseBatch")
	public void refuseBatch(@RequestBody List<Order> list) {
		iOrderService.refuseBatch(list);
	}

	/**
	 * 供应商拒绝订单
	 *
	 * @param order 订单
	 */
	@PostMapping("/refuse")
	public void refuseBatch(@RequestBody Order order) {
		Assert.notNull(order.getOrderId(), "订单ID不能为空");
		List<Order> list = new ArrayList<Order>();
		list.add(order);
		iOrderService.refuseBatch(list);
	}

	/**
	 * excel导入订单
	 *
	 * @param file
	 */
	@PostMapping("/saveByExcel")
	public void saveByExcel(@RequestParam("file") MultipartFile file, Fileupload fileupload) {
		Assert.notNull(fileupload, "文件参数不能为空");
		Assert.notNull(fileupload.getSourceType(), "来源类型不能为空");
		Assert.notNull(fileupload.getUploadType(), "上传介质类型不能为空");
		Assert.notNull(fileupload.getFileModular(), "文件所属模块不能为空");
		Assert.notNull(fileupload.getFileFunction(), "文件所属功能不能为空");
		Assert.notNull(fileupload.getFileType(), "文件所属类型不能为空");
		Assert.notNull(file, "文件上传失败");

		String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") + 1);
		Assert.isTrue((StringUtils.equals("xls", suffix.toLowerCase()) || StringUtils.equals("xlsx", suffix.toLowerCase())), "请上传excel文件");
		Assert.isTrue(new Double(147294) / (1024 * 1024) < 1, "文件大小不能超过1M");
		Workbook rwb = null;
		try {
			rwb = Workbook.getWorkbook(file.getInputStream());

			List<ErrorCell> errorCells = new ArrayList();
			List<ExcelOrderRequestDTO> list = checkExcelData(rwb, errorCells);

			if (errorCells.size() == 0) {
				errorCells = iOrderService.saveBatchByExcel(list);
			}
			if (errorCells.size() > 0) {
				ExcelUtil.uploadErrorFile(HEAD_LINE_NUM, fileCenterClient, rwb, errorCells, fileupload, file.getName(), file.getOriginalFilename(), file.getContentType());
			}

		} catch (Exception e) {
			log.error("操作失败",e);
			throw new BaseException(e.getMessage());
		} finally {
			if (rwb != null) {
				rwb.close();
			}
		}
	}

	/**
	 * 检验excel文件数据
	 *
	 * @param rwb
	 * @return execl订单导入接收对象列表
	 */
	private List checkExcelData(Workbook rwb, List<ErrorCell> errorCells) throws WriteException {
		List list = new ArrayList();
		for (int sheetIndex = 0; sheetIndex < rwb.getNumberOfSheets(); sheetIndex++) {
			Sheet sheet = rwb.getSheet(sheetIndex);
			// 行数(表头的目录不需要，从1开始)
			for (int i = HEAD_LINE_NUM; i < sheet.getRows(); i++) {
				boolean isBlack = true;
				for (int c = 0; c < sheet.getColumns(); c++) {
					if (StringUtils.isNotBlank(sheet.getCell(c, i).getContents())) {
						isBlack = false;
						break;
					}
				}
				if (isBlack) {
					break;
				}

				Cell cell = null;
				// 列数
				for (int j = 0; j < sheet.getColumns(); j++) {
					// 获取第i行，第j列的值
					cell = sheet.getCell(j, i);
					checkCell(sheetIndex, cell, list, errorCells);
				}
			}
		}
		return list;
	}

	/**
	 * 校验单元格数据及格式
	 *
	 * @param sheetIndex sheet下标
	 * @param cell       单元格
	 * @param list       订单接收对象列表
	 */
	private void checkCell(int sheetIndex, Cell cell, List<ExcelOrderRequestDTO> list, List<ErrorCell> errorCells) throws WriteException {
		ExcelOrderRequestDTO eord = null;
		if (list.size() != 0) {
			eord = list.get(list.size() - 1);
		}
		switch (cell.getColumn()) {
		case 0:// 供应商名称
			if (list.size() == 0 && StringUtils.isBlank(cell.getContents())) {
				ExcelUtil.addErrorCell(StringUtils.isBlank(cell.getContents()), errorCells, new ErrorCell(sheetIndex, cell.getColumn(), cell.getRow(), "供应商不能为空"));

				ExcelOrderRequestDTO newEord = new ExcelOrderRequestDTO();
				list.add(newEord);
			} else if (list.size() == 0) {
				ExcelOrderRequestDTO newEord = new ExcelOrderRequestDTO();
				newEord.setVendorName(cell.getContents());
				newEord.addErrorCell("vendorName", new ErrorCell(sheetIndex, cell.getColumn(), cell.getRow()));
				list.add(newEord);
			} else if (list.size() != 0 && StringUtils.isNotBlank(cell.getContents())) {
				// 当原来接收对象的供应商名称不为空， 供应商单元格有新的供应商名称，则开始用新接收对象接收订单数据
				ExcelOrderRequestDTO newEord = new ExcelOrderRequestDTO();
				newEord.setVendorName(cell.getContents());
				newEord.addErrorCell("vendorName", new ErrorCell(sheetIndex, cell.getColumn(), cell.getRow()));
				list.add(newEord);
			}
			break;
		case 1:// 采购组织名称
			if (StringUtils.isBlank(eord.getOrganizationName())) {
				if (eord.getList() == null) {
					ExcelUtil.addErrorCell(StringUtils.isBlank(cell.getContents()), errorCells, new ErrorCell(sheetIndex, cell.getColumn(), cell.getRow(), "采购组织不能为空"));
					eord.setOrganizationName(cell.getContents());
					eord.addErrorCell("organizationName", new ErrorCell(sheetIndex, cell.getColumn(), cell.getRow()));
				}
			}
			break;
		case 2:// 交期等级名称
			if (StringUtils.isBlank(eord.getDeliveryLevel())) {
				if (eord.getList() == null) {
					ExcelUtil.addErrorCell(StringUtils.isBlank(cell.getContents()), errorCells, new ErrorCell(sheetIndex, cell.getColumn(), cell.getRow(), "交期等级不能为空"));
					eord.setDeliveryLevel(cell.getContents());
					eord.addErrorCell("deliveryLevel", new ErrorCell(sheetIndex, cell.getColumn(), cell.getRow()));
				}
			}
			break;
		case 3:// 订单备注
			if (StringUtils.isBlank(eord.getComments())) {
				eord.setComments(cell.getContents());
				eord.addErrorCell("comments", new ErrorCell(sheetIndex, cell.getColumn(), cell.getRow()));
			}
			break;
		case 4:// 订单明细行号
			if (eord.getList() == null) {
				eord.setList(new ArrayList<>());
			}
			OrderDetailDTO orderDetailDTO = new OrderDetailDTO();
			eord.getList().add(orderDetailDTO);

			if (StringUtils.isBlank(cell.getContents())) {
				ExcelUtil.addErrorCell(true, errorCells, new ErrorCell(sheetIndex, cell.getColumn(), cell.getRow(), "行号不能为空"));
			} else if (!ExcelUtil.checkPositiveInt(cell.getContents())) {
				ExcelUtil.addErrorCell(true, errorCells, new ErrorCell(sheetIndex, cell.getColumn(), cell.getRow(), "行号格式错误"));
			} else {
				eord.getList().get(eord.getList().size() - 1).setLineNum(new Integer(cell.getContents()));
				eord.addErrorCell("lineNum", new ErrorCell(sheetIndex, cell.getColumn(), cell.getRow()));
			}

			break;
		case 5:// 订单明细物料编码
			ExcelUtil.addErrorCell(StringUtils.isBlank(cell.getContents()), errorCells, new ErrorCell(sheetIndex, cell.getColumn(), cell.getRow(), "物料编码不能为空"));

			eord.getList().get(eord.getList().size() - 1).setMaterialCode(cell.getContents());
			eord.addErrorCell("materialCode", new ErrorCell(sheetIndex, cell.getColumn(), cell.getRow()));
			break;
		case 6:// 订单明细数量
			if (StringUtils.isBlank(cell.getContents())) {
				ExcelUtil.addErrorCell(true, errorCells, new ErrorCell(sheetIndex, cell.getColumn(), cell.getRow(), "订单数量不能为空"));
			} else if (!ExcelUtil.checkPositiveInt(cell.getContents())) {
				ExcelUtil.addErrorCell(true, errorCells, new ErrorCell(sheetIndex, cell.getColumn(), cell.getRow(), "订单数量格式错误"));
			} else {
				eord.getList().get(eord.getList().size() - 1).setOrderNum(new BigDecimal(cell.getContents()));
				eord.addErrorCell("orderNum", new ErrorCell(sheetIndex, cell.getColumn(), cell.getRow()));
			}

			break;
		case 7:// 订单明细单价（含税）
			if (StringUtils.isBlank(cell.getContents())) {
				ExcelUtil.addErrorCell(true, errorCells, new ErrorCell(sheetIndex, cell.getColumn(), cell.getRow(), "单价（含税）不能为空"));
			} else if (!ExcelUtil.checkPositiveNumber(cell.getContents())) {
				ExcelUtil.addErrorCell(true, errorCells, new ErrorCell(sheetIndex, cell.getColumn(), cell.getRow(), "单价（含税）格式错误"));
			} else {
				eord.getList().get(eord.getList().size() - 1).setUnitPriceContainingTax(new BigDecimal(cell.getContents()));
				eord.addErrorCell("unitPriceContainingTax", new ErrorCell(sheetIndex, cell.getColumn(), cell.getRow()));
			}

			break;
		case 8:// 订单明细交货日期
			if (StringUtils.isBlank(cell.getContents())) {
				ExcelUtil.addErrorCell(true, errorCells, new ErrorCell(sheetIndex, cell.getColumn(), cell.getRow(), "交货日期不能为空"));
			} else {
				Date date = ExcelUtil.getDateByStr(cell);
				if (date == null) {
					ExcelUtil.addErrorCell(true, errorCells, new ErrorCell(sheetIndex, cell.getColumn(), cell.getRow(), "交货日期格式错误"));
				}
				eord.getList().get(eord.getList().size() - 1).setDeliveryDate(date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
				eord.addErrorCell("deliveryDate", new ErrorCell(sheetIndex, cell.getColumn(), cell.getRow()));
			}

			break;
		case 9:// 订单明细收货地点
			eord.getList().get(eord.getList().size() - 1).setReceiptPlace(cell.getContents());
			eord.addErrorCell("receiptPlace", new ErrorCell(sheetIndex, cell.getColumn(), cell.getRow()));
			break;
		case 10:// 订单明细备注
			eord.getList().get(eord.getList().size() - 1).setComments(cell.getContents());
			eord.addErrorCell("comments", new ErrorCell(sheetIndex, cell.getColumn(), cell.getRow()));
			eord.addErrorCell("lineErrorContents", new ErrorCell(sheetIndex, cell.getColumn() + 1, cell.getRow()));
			break;
		}
	}

	@PostMapping("/listOrderByOrderNumberOrErpNumber")
	public List<Order> listOrderByOrderNumberOrErpNumber(@RequestBody List<String> numbers) {
		return iOrderService.listOrderByOrderNumberOrErpNumber(numbers);
	}

	/**
	 * 根据订单编号批量查询订单
	 * @param orderNumbers
	 * @return
	 */
	@PostMapping("/listByOrderNumbers")
	public List<Order> listByOrderNumbers(@RequestBody List<String> orderNumbers){
		return iOrderService.listByOrderNumbers(orderNumbers);
	}


	/**
	 * 分页查询
	 *
	 * @param orderRequestDTO 订单数据请求传输对象
	 * @return
	 */
	@PostMapping("/listOrderDetailPage")
	public PageInfo<OrderDetailDTO> listOrderDetailPage(@RequestBody OrderRequestDTO orderRequestDTO) {
		LoginAppUser loginAppUser = AppUserUtil.getLoginAppUser();
		if (!StringUtils.equals(UserType.VENDOR.name(), loginAppUser.getUserType()) && !StringUtils.equals(UserType.BUYER.name(), loginAppUser.getUserType())) {
			Assert.isTrue(false, "用户类型不存在");
		}
		if (StringUtils.equals(UserType.VENDOR.name(), loginAppUser.getUserType())) {
			if(Objects.isNull(loginAppUser.getCompanyId())){
				return new PageInfo<>(new ArrayList<>());
			}
			orderRequestDTO.setVendorId(loginAppUser.getCompanyId());
			if ("ACCEPT".equals(orderRequestDTO.getOrderStatus())) {
				orderRequestDTO.setOrderStatus(null);
				orderRequestDTO.setOrderDetailStatus("ACCEPT");
			} else if ("REFUSED".equals(orderRequestDTO.getOrderStatus())) {
				orderRequestDTO.setOrderStatus(null);
				orderRequestDTO.setOrderDetailStatus("REJECT");
			} else if ("APPROVED".equals(orderRequestDTO.getOrderStatus())) {
				orderRequestDTO.setOrderStatus(null);
				orderRequestDTO.setOrderDetailStatus("WAITING_VENDOR_CONFIRM");
			}
		}else{
			orderRequestDTO.setCreatedId(loginAppUser.getUserId());
		}
		PageUtil.startPage(orderRequestDTO.getPageNum(), orderRequestDTO.getPageSize());
		return new PageInfo<OrderDetailDTO>(iOrderService.listOrderDetailPage(orderRequestDTO));
	}

	/**
	 * 供应商批量确认订单行
	 */
	@PostMapping("/batchAccept")
	public void batchAccept(@RequestBody OrderRequestDTO orderRequestDTO) {
		iOrderDetailService.supplierConfirm(orderRequestDTO.getIds());
	}

	/**
	 * 供应商批量拒绝订单
	 */
	@PostMapping("/batchRefuse")
	public void batchRefuse(@RequestBody OrderDetail orderDetail) {
		iOrderDetailService.supplierReject(orderDetail);
	}

	/**
	 * 订单审批
	 * @param orderId
	 */
	@GetMapping("/approval")
	public void approval(@RequestParam("orderId") Long orderId){
		iOrderService.approval(orderId);
	}

	/**
	 * 批量删除订单
	 * @param ids
	 */
	@PostMapping("/batchDelete")
	public void batchDelete(@RequestBody List<Long> ids){
		iOrderService.batchDelete(ids);
	}

	/**
	 * 供应商确认
	 * @param ids
	 */
	@PostMapping("/supplierConfirm")
	public void supplierConfirm(@RequestBody List<Long> ids){
		iOrderService.supplierConfirm(ids);
	}

	/**
	 * 供应商拒绝
	 * @param orderDetail
	 */
	@PostMapping("/supplierReject")
	public void supplierReject(@RequestBody OrderDetail orderDetail){
		iOrderService.supplierReject(orderDetail);
	}

}
