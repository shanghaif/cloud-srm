package com.midea.cloud.srm.po.order.controller;

import com.github.pagehelper.PageInfo;
import com.midea.cloud.common.enums.UserType;
import com.midea.cloud.common.utils.AppUserUtil;
import com.midea.cloud.common.utils.PageUtil;
import com.midea.cloud.srm.model.common.BaseController;
import com.midea.cloud.srm.model.rbac.user.entity.LoginAppUser;
import com.midea.cloud.srm.model.suppliercooperate.order.dto.OrderDetailDTO;
import com.midea.cloud.srm.model.suppliercooperate.order.dto.OrderRequestDTO;
import com.midea.cloud.srm.model.suppliercooperate.order.entry.OrderDetail;
import com.midea.cloud.srm.po.order.service.IOrderDetailService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

/**
 * <pre>
 *   采购订单明细表 前端控制器
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
@RequestMapping("/po/orderDetail")
public class OrderDetailController extends BaseController {

	@Autowired
	private IOrderDetailService iOrderDetailService;

	@Autowired
	private com.midea.cloud.srm.supcooperate.order.service.IOrderDetailService scIOrderDetailService;

	/**
	 * 分页查询订单明细
	 *
	 * @param orderRequestDTO 订单数据请求传输对象
	 * @return
	 */
	@PostMapping("/listPage")
	public PageInfo<OrderDetailDTO> listPage(@RequestBody OrderRequestDTO orderRequestDTO) {
		LoginAppUser loginAppUser = AppUserUtil.getLoginAppUser();
		if (!StringUtils.equals(UserType.VENDOR.name(), loginAppUser.getUserType())
				&& !StringUtils.equals(UserType.BUYER.name(), loginAppUser.getUserType())) {
			Assert.isTrue(false, "用户类型不存在");
		}
		if (StringUtils.equals(UserType.VENDOR.name(), loginAppUser.getUserType())) {
			orderRequestDTO.setVendorId(loginAppUser.getCompanyId());
		}
		PageUtil.startPage(orderRequestDTO.getPageNum(), orderRequestDTO.getPageSize());
		return scIOrderDetailService.listPageNew(orderRequestDTO);
	}

	/**
	 * 关闭订单
	 *
	 * @param ids
	 */
	@PostMapping("/closeOrderDetail")
	public void closeOrderDetail(@RequestBody List<Long> ids) {
		iOrderDetailService.closeOrderDetail(ids);
	}

	/**
	 * 下载模板
	 */
	@GetMapping("/downloadTemplate")
	public void downloadTemplate(HttpServletRequest request, HttpServletResponse response) throws Exception{
		Map<String, Object> result = iOrderDetailService.downloadTemplate();
		String fileName = URLEncoder.encode(result.get("fileName").toString(), "UTF-8");
		response.setContentType("application/vnd.ms-excel");
		response.setCharacterEncoding("UTF-8");
		response.setHeader("Access-Control-Expose-Headers", "Content-Disposition"); // 解决axios下载后取不到文件名的问题
		response.setHeader("FileName", fileName); // 解决axios下载后取不到文件名的问题
		response.addHeader("Content-Disposition", "attachment;filename=" + fileName);
		response.getOutputStream().write((byte[]) result.get("buffer"));
		response.getOutputStream().close();
	}

	/**
	 * 导入
	 *
	 * @param vendorId
	 * @param organizationId
	 * @param file
	 * @return
	 */
	@PostMapping("/importExcel")
	public List<OrderDetail> importExcel(Long vendorId, Long organizationId, @RequestParam("file") MultipartFile file) throws Exception{
		return iOrderDetailService.importExcel(vendorId, organizationId, file);
	}

	/**
	 * 送货通知查询订单明细
	 * @return
	 */
	@PostMapping("/listInDeliveryNotice")
	public PageInfo<OrderDetailDTO> listInDeliveryNotice(@RequestBody OrderDetailDTO orderDetailDTO){
		return iOrderDetailService.listInDeliveryNotice(orderDetailDTO);
	}

}
