package com.midea.cloud.srm.pr.shopcart.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageInfo;
import com.midea.cloud.common.utils.AppUserUtil;
import com.midea.cloud.common.utils.EasyExcelUtil;
import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.common.utils.PageUtil;
import com.midea.cloud.srm.model.base.material.dto.MaterialQueryDTO;
import com.midea.cloud.srm.model.common.BaseController;
import com.midea.cloud.srm.model.common.ExportExcelParam;
import com.midea.cloud.srm.model.file.upload.entity.Fileupload;
import com.midea.cloud.srm.model.pm.pr.shopcart.dto.ShopcartDto;
import com.midea.cloud.srm.model.pm.pr.shopcart.entity.ShopCart;
import com.midea.cloud.srm.model.rbac.role.entity.Role;
import com.midea.cloud.srm.pr.shopcart.service.IShopCartService;
import com.midea.cloud.srm.pr.shopcart.utils.ShopCartExportUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.*;
import java.util.stream.Collectors;

/**
 * <pre>
 *  购物车 前端控制器
 * </pre>
 *
 * @author yourname@meiCloud.com
 * @version 1.00.00
 *          <p>
 *          <pre>
 *           修改记录
 *           修改后版本:
 *           修改人:
 *           修改日期: 2020-09-12 16:11:56
 *           修改内容:
 *          </pre>
 */
@RestController
@RequestMapping("/pr/shopCart")
public class ShopCartController extends BaseController {

	@Autowired
	private IShopCartService iShopCartService;

	/**
	 * 获取
	 *
	 * @param id
	 */
	@GetMapping("/get")
	public ShopCart get(Long id) {
		Assert.notNull(id, "id不能为空");
		return iShopCartService.getById(id);
	}

	/**
	 * 新增
	 *
	 * @param shopCart
	 */
	@PostMapping("/add")
	public void add(@RequestBody ShopCart shopCart) {
		Long id = IdGenrator.generate();
		shopCart.setShopCartId(id);
		iShopCartService.save(shopCart);
	}

	/**
	 * 删除
	 *
	 * @param id
	 */
	@GetMapping("/delete")
	public void delete(Long id) {
		Assert.notNull(id, "id不能为空");
		iShopCartService.removeById(id);
	}

	/**
	 * @Description 根据主键id删除（未提交的数据）
	 * @Param [ids]
	 * @return
	 * @Author haiping2.li@meicloud.com
	 * @Date 2020.09.23 08:47
	 **/
	@PostMapping("/ceeaDeleteByIds")
	public void ceeaDeleteByIds(@RequestBody List<Long> ids) {
		iShopCartService.ceeaDeleteByIds(ids);
	}

	/**
	 * 修改
	 *
	 * @param shopCart
	 */
	@PostMapping("/modify")
	public void modify(@RequestBody ShopCart shopCart) {
		iShopCartService.updateById(shopCart);
	}

	/**
	 * 购物车查看限制
	 * @return
	 */
	private void shoppingCartViewLimit(QueryWrapper<ShopCart> wrapper){
		boolean showYourself  = false;
		boolean showAll = false;
		String materialControl1 = "LONGI_IT";	//物控组IT
		String materialControl2 = "LONGI_DD";	//物控目录化
		String RequestGroup 	= "LONGI_DG";	//需求组
		List<Role> materialControlList = AppUserUtil.getLoginAppUser().getRolePermissions().stream()
				.filter(role -> materialControl1.equals(role.getRoleCode()) ||
						materialControl2.equals(role.getRoleCode()) ||
						RequestGroup.equals(role.getRoleCode())).collect(Collectors.toList());
		if(!materialControlList.isEmpty()){
			showYourself = true;
		}

		String systemAdmin = "SystemAdmin";	//系统管理员
		List<Role> adminList = AppUserUtil.getLoginAppUser().getRolePermissions().stream()
				.filter(role -> systemAdmin.equals(role.getRoleCode())).collect(Collectors.toList());
		if(!adminList.isEmpty()){
			showAll = true;
		}


		String userCode = AppUserUtil.getUserName();
		if(!showAll && showYourself){
			wrapper.eq("SUMMARY_USER_ID" , userCode).or()
					.eq("NOTICE_EMP_NO" , userCode).or()
					.eq("CREATED_BY" , userCode);
		}
	}

	/**
	 * 分页查询
	 *
	 * @param shopCart
	 * @return
	 */
	@PostMapping("/listPage")
	public PageInfo<ShopCart> listPage(@RequestBody ShopCart shopCart) {
//		PageUtil.startPage(shopCart.getPageNum(), shopCart.getPageSize());
//		QueryWrapper<ShopCart> wrapper = new QueryWrapper<ShopCart>(shopCart);
//		wrapper.in(CollectionUtils.isNotEmpty(shopCart.getOrgIds()), "ORG_ID", shopCart.getOrgIds());
//		wrapper.in(CollectionUtils.isNotEmpty(shopCart.getOrganizationIds()), "ORGANIZATION_ID", shopCart.getOrganizationIds());
//		return new PageInfo<ShopCart>(iShopCartService.list(wrapper));

		PageUtil.startPage(shopCart.getPageNum(), shopCart.getPageSize());
		QueryWrapper<ShopCart> wrapper = new QueryWrapper<ShopCart>();
		if (null != shopCart.getCategoryName() && !"".equals(shopCart.getCategoryName())) {
           wrapper.eq("CATEGORY_NAME", shopCart.getCategoryName());
		}
		if (null != shopCart.getMaterialName() && !"".equals(shopCart.getMaterialName())) {
			wrapper.eq("MATERIAL_NAME", shopCart.getMaterialName());
		}
		if (null != shopCart.getStatus() && !"".equals(shopCart.getStatus())) {
			wrapper.eq("STATUS", shopCart.getStatus());
		}
		if (null != shopCart.getSummaryNickname() && !"".equals(shopCart.getSummaryNickname())) {
			wrapper.eq("SUMMARY_NICKNAME", shopCart.getSummaryNickname());
		}
		if (null != shopCart.getNoticeNickname() && !"".equals(shopCart.getNoticeNickname())) {
			wrapper.eq("NOTICE_NICKNAME", shopCart.getNoticeNickname());
		}
		if (null != shopCart.getCreatedBy() && !"".equals(shopCart.getCreatedBy())) {
			wrapper.eq("CREATED_BY", shopCart.getCreatedBy());
		}
		/**
		 * 根据物料id查询
		 */
		if (!Objects.isNull(shopCart.getMaterialId())){
			wrapper.eq("MATERIAL_ID",shopCart.getMaterialId());
		}
	    wrapper.in(CollectionUtils.isNotEmpty(shopCart.getOrgIds()), "ORG_ID", shopCart.getOrgIds());
		wrapper.in(CollectionUtils.isNotEmpty(shopCart.getOrganizationIds()), "ORGANIZATION_ID", shopCart.getOrganizationIds());
		if (null != shopCart.getCreationDate()) {
			Date date = shopCart.getCreationDate();
			Calendar calendar = new GregorianCalendar();
			calendar.setTime(date);
			calendar.add(Calendar.DATE,1); //把日期往后增加一天,整数  往后推,负数往前移动
			Date newDate=calendar.getTime(); //这个时间就是日期往后推一天的结果
			wrapper.ge("CREATION_DATE", date).lt("CREATION_DATE", newDate);
		}
//		先屏蔽不发生产11-15
		shoppingCartViewLimit(wrapper);
		wrapper.orderByDesc("CREATION_DATE");
		return new PageInfo<ShopCart>(iShopCartService.list(wrapper));
	}

	/**
	 * 查询所有
	 *
	 * @return
	 */
	@PostMapping("/listAll")
	public List<ShopCart> listAll() {
		return iShopCartService.list();
	}

	/**
	 * @return
	 * @Description 根据条件查询购物车
	 * @Param [shopCart]
	 * @Author haiping2.li@meicloud.com
	 * @Date 2020.09.18 15:05
	 **/
	@PostMapping("/ceeaListByShopCart")
	public List<ShopCart> ceeaListByShopCart(@RequestBody ShopCart shopCart) {
		Assert.notNull(shopCart, "shopCart不能为空");
		return iShopCartService.list(new QueryWrapper<>(shopCart));
	}

	/**
	 * @return void
	 * @Description 加入购物车
	 * @Param [material]
	 * @Author haiping2.li@meicloud.com
	 * @Date 2020.09.18 15:28
	 **/
	@PostMapping("/ceeaAddToShopCart")
	public void ceeaAddToShopCart(@RequestBody MaterialQueryDTO material) {
		iShopCartService.ceeaAddToShopCart(material);
	}

	/**
	 * @Description 选择汇总人
	 * @Param [summaryUserId, summaryEmpNo, summaryNickname, noticeUserId, noticeEmpNo, noticeNickname, ids]
	 * @return
	 * @Author haiping2.li@meicloud.com
	 * @Date 2020.09.19 09:08
	 **/
	@PostMapping("/ceeaSetSummaryAndNoticeUser")
	public String ceeaSetSummaryAndNoticeUser(@RequestBody ShopCart shopCart) {
		Assert.notNull(shopCart, "shopCart不能为空");
		return iShopCartService.ceeaSetSummaryAndNoticeUser(shopCart.getSummaryUserId(), shopCart.getSummaryEmpNo(),
			shopCart.getSummaryNickname(), shopCart.getNoticeUserId(), shopCart.getNoticeEmpNo(), shopCart.getNoticeNickname(),
			shopCart.getIds());
	}

	/**
	 * @Description 提交/退回需求
	 * @Param [shopCart]
	 * @return java.lang.String
	 * @Author haiping2.li@meicloud.com
	 * @Date 2020.09.19 13:23
	 **/
	@PostMapping("/ceeaChangeShopCartStatus")
	public String ceeaChangeShopCartStatus(@RequestBody ShopCart shopCart) {
		Assert.notNull(shopCart, "shopCart不能为空");
		return iShopCartService.ceeaChangeShopCartStatus(shopCart.getStatus(), shopCart.getReturnReason(), shopCart.getIds());
	}

	/**
	 * @Description 勾选购物车行点击保存
	 * @Param [shopCarts]
	 * @return
	 * @Author haiping2.li@meicloud.com
	 * @Date 2020.09.19 16:56
	 **/
	@PostMapping("/ceeaUpdateShopCarts")
	public String ceeaUpdateShopCarts(@RequestBody List<ShopCart> shopCarts) {
		return iShopCartService.ceeaUpdateShopCarts(shopCarts);
	}

	/**
	 * @Description 批量维护采购类型和需求时间
	 * @Param [ids, purchaseType, requirementDate]
	 * @return
	 * @Author wuhx29@meicloud.com
	 * @Date 2020.10.16 13:40
	 **/
	@PostMapping("/ceeaBatchUpdateShopCarts")
	public String ceeaUpdateShopCarts(@RequestBody ShopcartDto shopcartDto) {
		return iShopCartService.ceeaUpdateShopCarts(shopcartDto.getIds(), shopcartDto.getPurchaseType(), shopcartDto.getRequirementDate());
	}

	/**
	 * @Description 创建申请单前校验“采购类型”、“需求时间”、“数量”是否已维护
	 * @Param [ids]
	 * @return
	 * @Author haiping2.li@meicloud.com
	 * @Date 2020.09.21 10:31
	 **/
	@PostMapping("/ceeaValidRequiredInfo")
	public String ceeaValidRequiredInfo(@RequestBody List<Long> ids) {
		return iShopCartService.ceeaValidRequiredInfo(ids);
	}

	/**
	 * @Description 创建申请单
	 * @Param [ids]
	 * @return
	 * @Author haiping2.li@meicloud.com
	 * @Date 2020.09.21 10:57
	 **/
	@PostMapping("/ceeaCreateRequirements")
	public List<Long> ceeaCreateRequirements(@RequestBody List<Long> ids) {
		return iShopCartService.ceeaCreateRequirements(ids);
	}


	/**
	 * @Description 返回待导出的字段属性
	 * @Param []
	 * @return
	 * @Author haiping2.li@meicloud.com
	 * @Date 2020.09.29 17:29
	 **/
	@PostMapping("/exportShopCartTitle")
	public Map<String, String> exportShopCartTitle() {
		return ShopCartExportUtils.getShopCartTitles();
	}

	/**
	 * @Description 导出Excel
	 * @Param [shopCartExportParam, response]
	 * @return
	 * @Author haiping2.li@meicloud.com
	 * @Date 2020.09.29 18:14
	 **/
	@PostMapping("/exportShopCartExcel")
	public void exportShopCartExcel(@RequestBody ExportExcelParam<ShopCart> shopCartExportParam,
									HttpServletResponse response) throws Exception {
		iShopCartService.exportMaterialItemExcel(shopCartExportParam, response);
	}

	/**
	 * @Description 购物车导入需求行
	 * @Param: [file]
	 * @Return: void
	 * @Author: dengyl23@meicloud.com
	 * @Date: 2020/9/26 14:29
	 */
	@RequestMapping("/importExcel")
	public Map<String,Object> importExcel(@RequestParam("file") MultipartFile file, Fileupload fileupload){
		return iShopCartService.importExcelNew(file,fileupload);
	}

	/**
	 * @Description 下载采购需求导入模板
	 * @Param: [response]
	 * @Return: void
	 * @Author: dengyl23@meicloud.com
	 * @Date: 2020/9/26 14:18
	 */
	@RequestMapping("/importModelDownload")
	public void importModelDownload(HttpServletResponse response) throws Exception {
//		iShopCartService.importModelDownload(response);
		InputStream inputStream = this.getClass().getResourceAsStream("/excel-model/采购需求行导入模板.xlsx");
		OutputStream outputStream = null;
		try {
			XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
			outputStream = EasyExcelUtil.getServletOutputStream(response, "采购需求行导入模板");
			workbook.write(outputStream);
		} finally {
			if (null != outputStream) {
				outputStream.flush();
				outputStream.close();
			}
		}
	}

	/**
	 * @Description: 点击汇总人按钮之前要校验购物车状态是否为未提交/点击提交需求之前校验是否为未提交状态
	 * @param: [shopCart]
	 * @return: int 1表示可以操作, 0表示不可以操作,只有未提交状态的行才可以操作，2表示没有勾选到行
	 * @auther: wuhx29@meicloud.com
	 * @date: 2020/10/17 15:25
	 */
	@PostMapping("/ceeaCheckSummaryAndSubmitRequest")
	public int ceeaCheckSummaryAndSubmitRequest(@RequestBody List<Long> shopCartIds) {
		return iShopCartService.ceeaCheckSummaryAndSubmitRequest(shopCartIds);
	}

	/**
	 * @Description 勾选购物车行点击保存之前校验是否为未提交或已提交
	 * @Param [shopCarts]
	 * @return  int 1表示可以操作, 0表示不可以操作,只有未提交状态和已提交状态的行才可以操作，2表示没有勾选到行
	 * @Author haiping2.li@meicloud.com
	 * @Date 2020.09.19 16:56
	 **/
	@PostMapping("/ceeaCheckUpdateShopCarts")
	public int ceeaCheckUpdateShopCarts(@RequestBody List<Long> shopCartIds) {
		return iShopCartService.ceeaCheckUpdateShopCarts(shopCartIds);
	}

	/**
	 * @Description: 调用创建申请单方法之前先检查行数据状态是否为已提交
	 * @param: [shopCartIds]
	 * @return: int  1为可操作申请单方法，0表示不可以操作,只有状态为已提交的行才可操作，2表示没有勾选到行
	 * @auther: wuhx29@meicloud.com
	 * @date: 2020/10/17 16:46
	 */
	@PostMapping("/ceeaCheckCreateRequirements")
	public int ceeaCheckCreateRequirements(@RequestBody List<Long> shopCartIds) {
		return iShopCartService.ceeaCheckCreateRequirements(shopCartIds);
	}
}
