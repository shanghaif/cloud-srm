package com.midea.cloud.srm.pr.shopcart.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.midea.cloud.common.constants.SequenceCodeConstant;
import com.midea.cloud.common.enums.ImportStatus;
import com.midea.cloud.common.enums.pm.po.OrderTypeEnum;
import com.midea.cloud.common.enums.pm.pr.requirement.RequirementApplyStatus;
import com.midea.cloud.common.exception.BaseException;
import com.midea.cloud.common.handler.TitleColorSheetWriteHandler;
import com.midea.cloud.common.result.ResultCode;
import com.midea.cloud.common.utils.*;
import com.midea.cloud.component.context.i18n.LocaleHandler;
import com.midea.cloud.srm.feign.base.BaseClient;
import com.midea.cloud.srm.feign.file.FileCenterClient;
import com.midea.cloud.srm.feign.inq.InqClient;
import com.midea.cloud.srm.feign.supplier.SupplierClient;
import com.midea.cloud.srm.model.base.dict.dto.DictItemDTO;
import com.midea.cloud.srm.model.base.material.MaterialItem;
import com.midea.cloud.srm.model.base.material.dto.MaterialQueryDTO;
import com.midea.cloud.srm.model.base.material.dto.PurchaseCatalogQueryDto;
import com.midea.cloud.srm.model.base.organization.entity.Organization;
import com.midea.cloud.srm.model.base.purchase.entity.PurchaseCategory;
import com.midea.cloud.srm.model.cm.contract.dto.LevelMaintainImportDto;
import com.midea.cloud.srm.model.common.ExportExcelParam;
import com.midea.cloud.srm.model.file.upload.entity.Fileupload;
import com.midea.cloud.srm.model.inq.price.entity.PriceLibrary;
import com.midea.cloud.srm.model.pm.pr.requirement.entity.RequirementHead;
import com.midea.cloud.srm.model.pm.pr.requirement.entity.RequirementLine;
import com.midea.cloud.srm.model.pm.pr.shopcart.dto.ShopCartImportModelDto;
import com.midea.cloud.srm.model.pm.pr.shopcart.dto.ShopCartModelDto;
import com.midea.cloud.srm.model.pm.pr.shopcart.entity.ShopCart;
import com.midea.cloud.srm.model.pm.pr.shopcart.enums.ShopCartStatus;
import com.midea.cloud.srm.model.rbac.user.entity.LoginAppUser;
import com.midea.cloud.srm.model.supplier.info.entity.CompanyInfo;
import com.midea.cloud.srm.pr.requirement.service.IRequirementHeadService;
import com.midea.cloud.srm.pr.requirement.service.IRequirementLineService;
import com.midea.cloud.srm.pr.shopcart.mapper.ShopCartMapper;
import com.midea.cloud.srm.pr.shopcart.service.IShopCartService;
import com.midea.cloud.srm.pr.shopcart.utils.ShopCartExportUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
*  <pre>
 *  购物车 服务实现类
 * </pre>
 *
 * @author haiping2.li@meiCloud.com
 * @version 1.00.00
 *
 *  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-09-12 16:11:56
 *  修改内容:
 * </pre>
*/
@Slf4j
@Service
public class ShopCartServiceImpl extends ServiceImpl<ShopCartMapper, ShopCart> implements IShopCartService {

	@Autowired
	private BaseClient baseClient;

	@Autowired
	private InqClient inqClient;

	@Autowired
	private IRequirementHeadService iRequirementHeadService;

	@Autowired
	private IRequirementLineService iRequirementLineService;

	@Resource
	private SupplierClient supplierClient;

	@Resource
	private FileCenterClient fileCenterClient;

	/**
	 * @Description 加入购物车
	 * @Param [material]
	 * @Author haiping2.li@meicloud.com
	 * @Date 2020.09.18 15:30
	 */
	@Override
	public void ceeaAddToShopCart(MaterialQueryDTO material) {
		Long id = IdGenrator.generate();
		// 获取当前登录用户
		LoginAppUser loginAppUser = AppUserUtil.getLoginAppUser();

		ShopCart shopCart = new ShopCart();
		shopCart.setShopCartId(id);
		shopCart.setOrgId(material.getOrgId()); // 业务实体
		shopCart.setOrgName(material.getOrgName());
		shopCart.setOrganizationId(material.getOrganizationId()); // 库存组织
		shopCart.setOrganizationName(material.getOrganizationName());
		shopCart.setCategoryId(material.getCategoryId()); // 小类
		shopCart.setCategoryName(material.getCategoryName());
		if (!StringUtils.isEmpty(material.getCategoryFullName())) { // 大类
			shopCart.setLargeCategoryName(material.getCategoryFullName().substring(0, material.getCategoryFullName().indexOf("-")));
		}
		shopCart.setMaterialId(material.getMaterialId());
		shopCart.setMaterialCode(material.getMaterialCode());
		shopCart.setMaterialName(material.getMaterialName());
		shopCart.setIfCatalog(StringUtils.isNoneBlank(material.getCeeaIfUse()) ? material.getCeeaIfUse() : "N"); // 是否上架=是否目录化
		shopCart.setSpecification(material.getSpecification());
		shopCart.setUnit(material.getUnit());
		shopCart.setUnitName(material.getUnitName());

		if ("Y".equals(material.getCeeaIfUse())) { // 是目录化物料
			shopCart.setContractNo(material.getCeeaContractNo());
			shopCart.setUnitPrice(material.getTaxPrice()); // 单价（含税）= 预算单价
			shopCart.setCurrencyId(material.getCurrencyId());
			shopCart.setCurrencyCode(material.getCurrencyCode());
			shopCart.setCurrencyName(material.getCurrencyName());
			shopCart.setSupplierId(material.getCeeaSupplierId());
			shopCart.setSupplierCode(material.getCeeaSupplierCode());
			shopCart.setSupplierName(material.getCeeaSupplierName());
		}
		shopCart.setStatus(ShopCartStatus.DRAFT.getCode()); // 未提交状态
		shopCart.setAddToUserId(loginAppUser.getUserId());
		shopCart.setAddToEmpNo(loginAppUser.getUsername());
		shopCart.setAddToNickname(loginAppUser.getNickname());
		baseMapper.insert(shopCart);
	}

	/**
	 * @Description 选择汇总人
	 * @Param [summaryUserId, summaryEmpNo, summaryNickname, noticeUserId, noticeEmpNo, noticeNickname, ids]
	 * @Author haiping2.li@meicloud.com
	 * @Date 2020.09.19 09:09
	 */
	@Override
	public String ceeaSetSummaryAndNoticeUser(Long summaryUserId, String summaryEmpNo, String summaryNickname,
											  Long noticeUserId, String noticeEmpNo, String noticeNickname,
											  List<Long> ids) {
		List<ShopCart> shopCarts = this.listByIds(ids);
		if (shopCarts.size() > 0) {
			for (ShopCart sc : shopCarts) {
				if (summaryUserId != null) {
					sc.setSummaryUserId(summaryUserId)
						.setSummaryEmpNo(summaryEmpNo)
						.setSummaryNickname(summaryNickname);
				}
				if (noticeUserId != null) {
					sc.setNoticeUserId(noticeUserId)
						.setNoticeEmpNo(noticeEmpNo)
						.setNoticeNickname(noticeNickname);
				}
			}
			this.saveOrUpdateBatch(shopCarts);
		} else {
			return "操作失败";
		}
		return "操作成功";
	}

	/**
	 * @Description 提交/退回需求
	 * @Param [status, returnReason, ids]
	 * @Author haiping2.li@meicloud.com
	 * @Date 2020.09.19 13:24
	 */
	@Override
	public String ceeaChangeShopCartStatus(String status, String returnReason, List<Long> ids) {
		if (CollectionUtils.isEmpty(ids)) {
			return "请选择购物车行数据";
		}
		List<ShopCart> shopCarts = this.listByIds(ids);
		if (shopCarts.size() > 0) {
			for (ShopCart sc : shopCarts) {
				/*已生成申请单的行，不可退回需求*/
				if(ShopCartStatus.APPLIED.getCode().equals(sc.getStatus())){
					throw new BaseException(LocaleHandler.getLocaleMsg("生成申请单的行，不可退回需求"));
				}

				sc.setStatus(status);
				if (ShopCartStatus.DRAFT.getCode().equals(status)) {
					sc.setReturnReason(returnReason);
				}
			}
			this.saveOrUpdateBatch(shopCarts);
		} else {
			return "操作失败";
		}
		return "操作成功";
	}

	/**
	 * @Description 勾选购物车行点击保存
	 * @Param [shopCarts]
	 * @Author haiping2.li@meicloud.com
	 * @Date 2020.09.19 16:59
	 */
	@Override
	public String ceeaUpdateShopCarts(List<ShopCart> shopCarts) {
		if (CollectionUtils.isEmpty(shopCarts)) {
			Assert.notNull(null, "未找到待保存数据");
		}
		List<Long> ids = new ArrayList<>();
		for (ShopCart sc : shopCarts) {
			if (sc.getShopCartId() != null) {
				ids.add(sc.getShopCartId());
			}
		}
		if (ids.size() > 0) {
			List<ShopCart> updates = this.listByIds(ids);
			for (ShopCart sc : updates) {
				for (ShopCart sc2 : shopCarts) {
					if (sc.getShopCartId().equals(sc2.getShopCartId())) {
						sc.setPurchaseType(sc2.getPurchaseType()); // 采购类型
						sc.setRequirementDate(sc2.getRequirementDate()); // 需求时间
						sc.setRequirementNum(sc2.getRequirementNum()); // 数量/requirementNum
						break;
					}
				}
			}
			this.updateBatchById(updates);
		}
		return "操作成功";
	}

	@Override
	public String ceeaUpdateShopCarts(List<Long> ids, String purchaseType, LocalDate requirementDate) {
		if (CollectionUtils.isEmpty(ids)) {
			Assert.notNull(null, "未找到待保存数据");
		}
		if (ids.size() > 0) {
			List<ShopCart> updates = this.listByIds(ids);
			for (ShopCart sc : updates) {
				sc.setPurchaseType(purchaseType); // 采购类型
				sc.setRequirementDate(requirementDate); // 需求时间
			}
			this.updateBatchById(updates);
		}
		return "操作成功";
	}

	/**
	 * @Description 创建申请单前校验“采购类型”、“需求时间”、“数量”是否已维护
	 * @Param [ids]
	 * @Author haiping2.li@meicloud.com
	 * @Date 2020.09.21 10:07
	 */
	@Override
	public String ceeaValidRequiredInfo(List<Long> ids) {
		if (CollectionUtils.isEmpty(ids)) {
			Assert.notNull(null, "请选择购物车行数据");
		}
		StringBuilder msg = new StringBuilder();
		List<ShopCart> shopCarts = this.listByIds(ids);
		for (ShopCart sc : shopCarts) {
			if (StringUtils.isEmpty(sc.getPurchaseType())) {
				msg.append("勾选行采购类型必填，请维护并保存");
				break;
			}
			if (sc.getRequirementDate() == null) {
				msg.append("勾选行需求时间必填，请维护并保存");
				break;
			}
			if (sc.getRequirementNum() == null) {
				msg.append("勾选行数量必填，请维护并保存");
				break;
			}
		}
		return msg.toString();
	}

	/**
	 * @Description 创建申请单
	 * @Param [ids]
	 * @Author haiping2.li@meicloud.com
	 * @Date 2020.09.21 10:59
	 * 2020-10-30修改
	 * 原逻辑：
	 * i.	生成申请单时，按以下条件拆单生成多张申请单：“业务实体+库存组织+是否目录化+物料中类+采购类型”，相同的行生成一张申请单，如果不同，生成不同的申请单；
	 * 转订单：
	 * 1)	拆单条件中，去掉物料中类这个条件，即拆单条件为：“业务实体+库存组织+物料大类+是否目录化+采购类型”；
	 * 2)	如果生成申请单的行中，有物料小类为“招待用品”，招待用品的物料行单独生成一张单；
	 */
	@Override
	public List<Long> ceeaCreateRequirements(List<Long> ids) {
		if (CollectionUtils.isEmpty(ids)) {
			Assert.notNull(null, "请选择购物车行数据");
		}
		Map<ShopCart, List<ShopCart>> group = new HashMap<>(); // 按 采购类型+业务实体+库存组织+物料中类+是否目录化物料 分组购物车行数据
		List<ShopCart> shopCarts = this.listByIds(ids);
		for (ShopCart sc : shopCarts) {
			PurchaseCategory cat = null;
			if (sc.getCategoryId() != null) {
				cat = baseClient.getPurchaseCategoryByParm(new PurchaseCategory().setCategoryId(sc.getCategoryId()));
			} else if (sc.getCategoryName() != null) {
				cat = baseClient.getPurchaseCategoryByParm(new PurchaseCategory().setCategoryName(sc.getCategoryName()));
			} else {
				continue;
			}
			Assert.notNull(cat, "小类：" + sc.getCategoryName() + "，不存在，无法创建申请单");
			/*2020-10-30 update-start by chenwj92*/
			PurchaseCategory largeCategory = baseClient.queryMaxLevelCategory(new PurchaseCategory().setCategoryId(sc.getCategoryId()));
			sc.setBigPurchaseCategory(largeCategory);
			ShopCart cart = new ShopCart();
			if("701101".equals(cat.getCategoryCode())){
				/*判断小类为招待用品*/
				cart.setIfEntertainment("Y");
			}else{
				/*小类不为招待用品*/
				cart.setIfEntertainment("N");
			}
			cart.setPurchaseType(sc.getPurchaseType());
			cart.setOrgName(sc.getOrgName());
			cart.setOrganizationName(sc.getOrganizationName());
			cart.setLargeCategoryName(largeCategory.getCategoryId() + ""); // 中类ID
			cart.setIfCatalog(sc.getIfCatalog() != null ? sc.getIfCatalog() : "N");
			/*2020-10-30 update-end by chenwj92*/




			List<ShopCart> list = group.get(cart);
			if (list == null) {
				list = new ArrayList<>();
			}
			list.add(sc);
			group.put(cart, list);
		}
		// 获取当前登录用户
		LoginAppUser loginAppUser = AppUserUtil.getLoginAppUser();

		List<Long> requirementHeadIds = new ArrayList<>();
		Iterator<Map.Entry<ShopCart, List<ShopCart>>> entries = group.entrySet().iterator();
		while (entries.hasNext()) {
			Map.Entry<ShopCart, List<ShopCart>> entry = entries.next();
			ShopCart cart = entry.getKey();
			List<ShopCart> list = entry.getValue();

			Organization org = baseClient.getOrganizationByParam(new Organization().setOrganizationName(cart.getOrgName()));
			Assert.notNull(org, "创建申请单失败，未找到业务实体：" + cart.getOrgName());

			Organization organization = baseClient.getOrganizationByParam(new Organization().setOrganizationName(cart.getOrganizationName()));
			Assert.notNull(organization, "创建申请单失败，未找到库存组织：" + cart.getOrganizationName());

			// 获取大类
			/*PurchaseCategory lc = new PurchaseCategory().setCategoryId(Long.valueOf(cart.getLargeCategoryName()));*/
			PurchaseCategory largeCategory = entry.getValue().get(0).getBigPurchaseCategory();

			RequirementHead rh = new RequirementHead();
			rh.setRequirementHeadId(IdGenrator.generate())
				.setRequirementHeadNum(baseClient.seqGen(SequenceCodeConstant.SEQ_PMP_PR_APPLY_NUM))
				.setCeeaDepartmentName(loginAppUser.getDepartment()) // 部门
				.setCeeaPurchaseType(cart.getPurchaseType())
				.setOrgId(org.getOrganizationId())
				.setOrgCode(org.getOrganizationCode())
				.setOrgName(org.getOrganizationName())
				.setOrganizationId(organization.getOrganizationId())
				.setOrganizationCode(organization.getOrganizationCode())
				.setOrganizationName(organization.getOrganizationName())
				.setCategoryId(largeCategory.getCategoryId())
				.setCategoryCode(largeCategory.getCategoryCode())
				.setCategoryName(largeCategory.getCategoryName())
				.setAuditStatus("DRAFT") // 新建
				.setCreatedFullName(loginAppUser.getNickname())
				.setApplyDate(DateChangeUtil.asLocalDate(new Date()));
			requirementHeadIds.add(rh.getRequirementHeadId());

			// 保存采购申请行表
			List<RequirementLine> rls = new ArrayList<>();
			BigDecimal ceeaTotalBudget = new BigDecimal("0.00");
			for (ShopCart sc : list) {
				RequirementLine rl = new RequirementLine();
				rl.setRequirementLineId(IdGenrator.generate())
					.setRequirementHeadId(rh.getRequirementHeadId())
					.setApplyStatus(RequirementApplyStatus.UNASSIGNED.getValue())
					.setRequirementHeadNum(rh.getRequirementHeadNum())
					.setOrgId(org.getOrganizationId())
					.setOrgCode(org.getOrganizationCode())
					.setOrgName(org.getOrganizationName())
					.setOrganizationId(organization.getOrganizationId())
					.setOrganizationCode(organization.getOrganizationCode())
					.setOrganizationName(organization.getOrganizationName())
					.setCategoryId(sc.getCategoryId())
					.setCategoryCode(sc.getCategoryCode())
					.setCategoryName(sc.getCategoryName())
					.setCeeaIfDirectory(sc.getIfCatalog() != null ? sc.getIfCatalog() : "N")
					.setMaterialId(sc.getMaterialId())
					.setMaterialCode(sc.getMaterialCode())
					.setMaterialName(sc.getMaterialName())
					.setUnitCode(sc.getUnit())
					.setUnit(sc.getUnitName())
					.setCurrencyId(sc.getCurrencyId())
					.setCurrencyCode(sc.getCurrencyCode())
					.setCurrencyName(sc.getCurrencyName())
					.setRequirementQuantity(sc.getRequirementNum())
					.setRequirementDate(sc.getRequirementDate())
					.setShopCartId(sc.getShopCartId());
				if ("Y".equals(sc.getIfCatalog())) { // 是否上架=是否目录化
					// 预算价格=价格库中含税单价，指定供应商为价格库中供应商
					PriceLibrary pl = new PriceLibrary();
					pl.setItemCode(sc.getMaterialCode());
					pl.setItemDesc(sc.getMaterialName());
					pl.setCeeaIfUse("Y"); // 上架 = 目录化
					pl.setCeeaOrgId(sc.getOrgId());
					pl.setCeeaOrganizationId(sc.getOrganizationId());

					PriceLibrary price = inqClient.getOnePriceLibrary(pl);
					if (price != null) {
						rl.setNotaxPrice(price.getTaxPrice());
						rl.setVendorId(price.getVendorId());
						rl.setVendorCode(price.getVendorCode());
						rl.setVendorName(price.getVendorName());
						rl.setTotalAmount(price.getTaxPrice().multiply(rl.getRequirementQuantity()).setScale(8, RoundingMode.UP));
						ceeaTotalBudget = ceeaTotalBudget.add(rl.getTotalAmount());
					}
				}
				sc.setStatus(ShopCartStatus.APPLIED.getCode()); // 已生成申请单
				sc.setRequirementHeadNum(rh.getRequirementHeadNum()); // 采购申请单号
				rls.add(rl);
			}
			ceeaTotalBudget.setScale(8,RoundingMode.UP);
			rh.setCeeaTotalBudget(ceeaTotalBudget);
			// 保存采购申请头表
			iRequirementHeadService.save(rh);
			iRequirementLineService.saveBatch(rls);
			this.saveOrUpdateBatch(list); // 更新购物车状态
		}
		return requirementHeadIds;
	}

	/**
	 * @Description 根据主键id删除（未提交的数据）
	 * @Param [ids]
	 * @Author haiping2.li@meicloud.com
	 * @Date 2020.09.23 08:48
	 */
	@Override
	public void ceeaDeleteByIds(List<Long> ids) {
		if (CollectionUtils.isEmpty(ids)) {
			Assert.notNull(null, "请选择购物车行数据");
		}
		QueryWrapper<ShopCart> qw = new QueryWrapper<>();
		qw.eq("STATUS", ShopCartStatus.DRAFT.getCode()); // 未提交的
		qw.in("SHOP_CART_ID", ids);
		this.remove(qw);
	}

	/**
	 * @Description 采购需求导入下载模板
	 * @Param: [httpServletResponse]
	 * @Return: void
	 * @Author: dengyl23@meicloud.com
	 * @Date: 2020/9/26 16:28
	 */
	@Override
	public void importModelDownload(HttpServletResponse httpServletResponse){
		try {
			String fileName = "采购目录需求行导入模板";
			ArrayList<ShopCartModelDto> shopCartModelDtos = new ArrayList<>();
			ServletOutputStream outputStream = EasyExcelUtil.getServletOutputStream(httpServletResponse, fileName);
			List<Integer> rows = Arrays.asList(0);
			List<Integer> columns = Arrays.asList(1,2,3,4,5,6,7);
			TitleColorSheetWriteHandler titleColorSheetWriteHandler = new TitleColorSheetWriteHandler(rows,columns, IndexedColors.RED.index);
			EasyExcelUtil.writeExcelWithModel(outputStream,shopCartModelDtos,ShopCartModelDto.class,fileName,titleColorSheetWriteHandler);
		} catch (IOException e) {
			log.info("download importModel error:{}",e);
			throw new BaseException(ResultCode.DOWNLOAD_EXCEPTIONS);
		}
	}

	/**
	 * @Description 购物车导入需求行
	 * @Param: [file]
	 * @Return: void
	 * @Author: dengyl23@meicloud.com
	 * @Date: 2020/9/26 14:33
	 */
	@Override
	public void importExcel(MultipartFile file) {
		try {
			String name =  file.getOriginalFilename();
			if(!EasyExcelUtil.isExcel(name)){
				throw new RuntimeException("请导入正确的Excel文件");
			}
			List<PurchaseCatalogQueryDto> queryParamList = new ArrayList<>();
			List<Object> objectList = EasyExcelUtil.readExcelWithModel(file.getInputStream(), ShopCartModelDto.class);
			List<ShopCartModelDto> shopCartModelDtoList = new ArrayList<>();
			List<ShopCart> addShopCartList = new ArrayList<>();
			if(CollectionUtils.isNotEmpty(objectList)){
				for(int i = 0;i<objectList.size();i++){
					Object object = objectList.get(i);
					Integer rowId = i+1;
					if(null != object){
						PurchaseCatalogQueryDto purchaseCatalogQueryDto = new PurchaseCatalogQueryDto();
						ShopCartModelDto shopCartModelDto = (ShopCartModelDto)object;
						shopCartModelDto.setRowId(rowId);
						checkParam(shopCartModelDto);
						shopCartModelDtoList.add(shopCartModelDto);
						BeanUtils.copyProperties(shopCartModelDto,purchaseCatalogQueryDto);
						queryParamList.add(purchaseCatalogQueryDto);
					}
				}
				//返回在采购目录下的导入行数据
				List<PurchaseCatalogQueryDto> purchaseCatalogQueryDtoList = baseClient.listCeeaListPurchaseCatalog(queryParamList);
				if(CollectionUtils.isNotEmpty(purchaseCatalogQueryDtoList)){
					purchaseCatalogQueryDtoList.forEach(purchaseCatalogQueryDto -> {
						shopCartModelDtoList.forEach(shopCartModelDto -> {
							if(purchaseCatalogQueryDto.getRowId().equals(shopCartModelDto.getRowId())){
								//最小起订量校验
								if(null == purchaseCatalogQueryDto.getCeeaOrderQuantityMinimum()){
									MaterialItem materialItem = new MaterialItem();
									materialItem.setMaterialId(purchaseCatalogQueryDto.getMaterialId());
									materialItem.setCeeaOrderQuantityMinimum(new BigDecimal(shopCartModelDto.getRequirementNum()));
									baseClient.updateMaterialItemById(materialItem);
								}else{
									if(new BigDecimal(shopCartModelDto.getRequirementNum()).compareTo(purchaseCatalogQueryDto.getCeeaOrderQuantityMinimum()) == -1){
										throw new BaseException("数量需大于等于最小起订量");
									}
								}
								if (StringUtils.isBlank(purchaseCatalogQueryDto.getUnit()) || StringUtils.isBlank(purchaseCatalogQueryDto.getUnitName())) {
									throw new BaseException("物料：" + purchaseCatalogQueryDto.getMaterialCode() + "，没有单位不能导入购物车");
								}
								try {
									addShopCartList.add(buildImportShopCart(purchaseCatalogQueryDto,shopCartModelDto));
								} catch (ParseException e) {
									throw new BaseException(ResultCode.IMPORT_EXCEPTIONS);
								}
							}
						});
					});
				}
			}
			//保存购物车
			saveBatch(addShopCartList);
		}
		catch (IOException e) {
			log.info("import ExcelData error:{}",e);
			throw new BaseException(ResultCode.IMPORT_EXCEPTIONS);
		}
		catch (BaseException be){
			throw be;
		}
		catch (Exception e1){
			log.info("import ExcelData error:{}",e1);
			throw new BaseException(ResultCode.IMPORT_EXCEPTIONS);
		}
	}

	@Override
	public Map<String,Object> importExcelNew(MultipartFile file, Fileupload fileupload) {
		// 检查文件格式
		EasyExcelUtil.checkParam(file, fileupload);
		// 读取excel数据
		List<ShopCartImportModelDto> shopCartImportModelDtos = EasyExcelUtil.readExcelWithModel(file, ShopCartImportModelDto.class);
		AtomicBoolean errorFlag = new AtomicBoolean(false);
		List<ShopCart> shopCarts = new ArrayList<>();
		// 检查数据
		checkData(shopCartImportModelDtos,errorFlag,shopCarts);
		if(errorFlag.get()){
			// 有报错
			fileupload.setFileSourceName("采购需求行导入模板报错");
			Fileupload fileupload1 = EasyExcelUtil.uploadErrorFile(fileCenterClient, fileupload,
					shopCartImportModelDtos, ShopCartImportModelDto.class, file.getName(), file.getOriginalFilename(), file.getContentType());
			return ImportStatus.importError(fileupload1.getFileuploadId(),fileupload1.getFileSourceName());
		}else {
			if(CollectionUtils.isNotEmpty(shopCarts)){
				this.saveBatch(shopCarts);
			}
		}
		return ImportStatus.importSuccess();
	}

	/**
	 * 校验导入数据
	 * @param shopCartImportModelDtos
	 * @param errorFlag
	 * @param shopCarts
	 */
	public void checkData(List<ShopCartImportModelDto> shopCartImportModelDtos,
						  AtomicBoolean errorFlag,
						  List<ShopCart> shopCarts){
		if(CollectionUtils.isNotEmpty(shopCartImportModelDtos)){
			// 采购类型字典
			Map<String, String> purchaseTypeMap = EasyExcelUtil.getDicNameCode("PURCHASE_TYPE", baseClient);
			// 组织
			Map<String, Organization> orgMap = new HashMap<>();
			List<Organization> organizations = baseClient.listAllOrganization();
			if(CollectionUtils.isNotEmpty(organizations)){
				orgMap = organizations.stream().filter(organization -> "Y".equals(organization.getEnabled())).collect(Collectors.
						toMap(Organization::getOrganizationName, Function.identity(), (k1, k2) -> k1));
			}
			List<String> itemCodes = new ArrayList<>();
			List<String> vendorCodes = new ArrayList<>();
			shopCartImportModelDtos.forEach(shopCartImportModelDto -> {
				String materialCode = shopCartImportModelDto.getMaterialCode();
				if(StringUtil.notEmpty(materialCode)){
					itemCodes.add(materialCode.trim());
				}
				String supplierCode = shopCartImportModelDto.getSupplierCode();
				if(StringUtil.notEmpty(supplierCode)){
					vendorCodes.add(supplierCode.trim());
				}
			});
			// 物料
			Map<String, MaterialItem> itemMap = baseClient.listMaterialItemsByCodes(itemCodes);
			Map<String, CompanyInfo> companyMap = supplierClient.getComponyByCodeList(vendorCodes);
			Set<Object> hashSet = new HashSet<>();
			for(ShopCartImportModelDto shopCartImportModelDto:shopCartImportModelDtos) {
				StringBuffer errorMsg = new StringBuffer();
				ShopCart shopCart = new ShopCart();
				StringBuffer onlyKey = new StringBuffer();
				boolean lineFlag = true;
				// 是否目录化
				String isDirectory = shopCartImportModelDto.getIsDirectory();
				if(StringUtil.notEmpty(isDirectory)){
					isDirectory = isDirectory.trim();
					if ("Y".equals(isDirectory) || "N".equals(isDirectory)){
						shopCart.setIfCatalog(isDirectory);
					}else {
						errorFlag.set(true);
						lineFlag = false;
						errorMsg.append("是否为目录化只能填\"Y\"或\"N\"; ");
					}
				}else {
					errorFlag.set(true);
					lineFlag = false;
					errorMsg.append("是否为目录化不能为空; ");
				}

				// 采购类型
				String purchaseType = shopCartImportModelDto.getPurchaseType();
				if(StringUtil.notEmpty(purchaseType)){
					purchaseType = purchaseType.trim();
					String code = purchaseTypeMap.get(purchaseType);
					if(StringUtil.notEmpty(code)){
						shopCart.setPurchaseType(code);
					}else {
						errorFlag.set(true);
						lineFlag = false;
						errorMsg.append("采购类型字典值不存在; ");
					}
				}else {
					errorFlag.set(true);
					lineFlag = false;
					errorMsg.append("采购类型不能为空; ");
				}

				// 需求时间
				String requirementDate = shopCartImportModelDto.getRequirementDate();
				if(StringUtil.notEmpty(requirementDate)){
					requirementDate = requirementDate.trim();
					try {
						Date date = DateUtil.parseDate(requirementDate);
						LocalDate localDate = DateUtil.dateToLocalDate(date);
						shopCart.setRequirementDate(localDate);
					} catch (Exception e) {
						errorFlag.set(true);
						lineFlag = false;
						errorMsg.append("需求时间无法解析; ");
					}
				}else {
					errorFlag.set(true);
					lineFlag = false;
					errorMsg.append("需求时间不能为空; ");
				}

				// 数量
				String requirementNum = shopCartImportModelDto.getRequirementNum();
				if(StringUtil.notEmpty(requirementNum)){
					requirementNum = requirementNum.trim();
					if(StringUtil.isDigit(requirementNum)){
						shopCart.setRequirementNum(new BigDecimal(requirementNum));
					}else {
						errorFlag.set(true);
						lineFlag = false;
						errorMsg.append("数量格式非法; ");
					}
				}else {
					errorFlag.set(true);
					lineFlag = false;
					errorMsg.append("数量不能为空; ");
				}

				// 物料编码
				String materialCode = shopCartImportModelDto.getMaterialCode();
				if(StringUtil.notEmpty(materialCode)){
					materialCode = materialCode.trim();
					MaterialItem materialItem = itemMap.get(materialCode);
					onlyKey.append(materialItem);
					if(null != materialItem){
						shopCart.setMaterialId(materialItem.getMaterialId());
						shopCart.setMaterialCode(materialItem.getMaterialCode());
						shopCart.setMaterialName(materialItem.getMaterialName());
						shopCart.setCategoryId(materialItem.getCategoryId());
						shopCart.setCategoryCode(materialItem.getCategoryCode());
						shopCart.setCategoryName(materialItem.getCategoryName());
						shopCart.setUnit(materialItem.getUnit());
						shopCart.setUnitName(materialItem.getUnitName());
					}else {
						errorFlag.set(true);
						lineFlag = false;
						errorMsg.append("物料编码数据库不存在; ");
					}
				}else {
					errorFlag.set(true);
					lineFlag = false;
					errorMsg.append("物料编码不能为空; ");
				}

				// 物料名称
				String materialName = shopCartImportModelDto.getMaterialName();
				if(StringUtil.notEmpty(materialName)){
					materialName = materialName.trim();
					onlyKey.append(materialName);
					shopCart.setMaterialName(materialName);
				}else {
					errorFlag.set(true);
					lineFlag = false;
					errorMsg.append("物料描述不能为空; ");
				}

				// 业务实体
				String orgName = shopCartImportModelDto.getOrgName();
				if(StringUtil.notEmpty(orgName)){
					orgName = orgName.trim();
					Organization organization = orgMap.get(orgName);
					if(null != organization){
						shopCart.setOrgId(organization.getOrganizationId());
						shopCart.setOrgCode(organization.getOrganizationCode());
						shopCart.setOrgName(organization.getOrganizationName());
					}else{
						errorFlag.set(true);
						lineFlag = false;
						errorMsg.append("业务实体数据库不存在; ");
					}
				}else {
					errorFlag.set(true);
					lineFlag = false;
					errorMsg.append("业务实体不能为空; ");
				}

				// 库存组织
				String organizationName = shopCartImportModelDto.getOrganizationName();
				if(StringUtil.notEmpty(organizationName)){
					organizationName = organizationName.trim();
					onlyKey.append(organizationName);
					Organization organization = orgMap.get(organizationName);
					if(null != organization){
						String parentOrganizationIds = organization.getParentOrganizationIds();
						if(StringUtil.notEmpty(shopCart.getOrgId())){
							if(parentOrganizationIds.contains(String.valueOf(shopCart.getOrgId()))){
								shopCart.setOrganizationId(organization.getOrganizationId());
								shopCart.setOrganizationCode(organization.getOrganizationCode());
								shopCart.setOrganizationName(organization.getOrganizationName());
							}else {
								errorFlag.set(true);
								lineFlag = false;
								errorMsg.append("库存组织与业务实体不是父子关系; ");
							}
						}
					}else {
						errorFlag.set(true);
						lineFlag = false;
						errorMsg.append("库存组织数据库不存在; ");
					}
				}else {
					errorFlag.set(true);
					lineFlag = false;
					errorMsg.append("库存组织不能为空; ");
				}

				// 供应商编码
				String supplierCode = shopCartImportModelDto.getSupplierCode();
				if(StringUtil.notEmpty(supplierCode)){
					supplierCode = supplierCode.trim();
					CompanyInfo companyInfo = companyMap.get(supplierCode);
					if(null != companyInfo){
						shopCart.setSupplierId(companyInfo.getCompanyId());
						shopCart.setSupplierCode(companyInfo.getCompanyCode());
						shopCart.setSupplierName(companyInfo.getCompanyName());
					}else {
						errorFlag.set(true);
						lineFlag = false;
						errorMsg.append("供应商编码数据库不存在; ");
					}
				}else {
					// 如果是目录化的, 供应商编码必填
					if("Y".equals(shopCart.getIfCatalog())){
						errorFlag.set(true);
						lineFlag = false;
						errorMsg.append("目录化采购供应商编码不能为空; ");
					}
				}

				// 校验行内是否有重复
				if(lineFlag){
					if("Y".equals(shopCart.getIfCatalog())){
						// 目录化采购:按物料编码+物料描述+库存组织+供应商查询是否重复；
						onlyKey.append(supplierCode);
						if(!hashSet.add(onlyKey.toString())){
							errorFlag.set(true);
							lineFlag = false;
							errorMsg.append("导入数据目录化采购:物料编码+物料描述+库存组织+供应商存在重复; ");
						}
					}else {
						// 非目录化,物料编码+物料描述+库存组织查询是否重复；
						if(!hashSet.add(onlyKey.toString())){
							errorFlag.set(true);
							lineFlag = false;
							errorMsg.append("导入数据非目录化采购:物料编码+物料描述+库存组织存在重复; ");
						}
					}
				}

				// 如果行中，是否选择目录化物料为Y，需校验该行物料是否为目录化物料；
				if(lineFlag){
					/**
					 * 是否目录化物料判断条件：
					 * 按“库存组织、物料编码、物料描述、供应商、有效期、是否上架为是”在价格库中查找是否有记录，
					 * 有为目录化，否则不是目录化；(价格库查询)
					 */
					PriceLibrary priceLibrary = new PriceLibrary();
					priceLibrary.setCeeaOrganizationId(shopCart.getOrganizationId());
					priceLibrary.setItemCode(shopCart.getMaterialCode());
					priceLibrary.setItemDesc(shopCart.getMaterialName());
					priceLibrary.setVendorId(shopCart.getSupplierId());
					priceLibrary.setCeeaIfUse("Y");
					priceLibrary = inqClient.getOnePriceLibrary(priceLibrary);
					if(null != priceLibrary){
						// 将价格、合同、币种写入购物车
						shopCart.setUnitPrice(priceLibrary.getTaxPrice());
						shopCart.setCurrencyId(priceLibrary.getCurrencyId());
						shopCart.setCurrencyCode(priceLibrary.getCurrencyCode());
						shopCart.setCurrencyName(priceLibrary.getCurrencyName());
						shopCart.setContractNo(priceLibrary.getContractNo());
					}else {
						errorFlag.set(true);
						lineFlag = false;
						errorMsg.append("该物料不是目录化物料; ");
					}
				}

				// 页面上数据是否有重复校验：如果导入在页面上已有“未提交”状态的相同行，需提示重复；
				if(lineFlag){
					if("Y".equals(shopCart.getIfCatalog())){
						//目录化物料：按物料编码+物料描述+库存组织+供应商+未提交状态查询是否重复；(购物车查询)
						ShopCart cart = new ShopCart();
						cart.setMaterialCode(shopCart.getMaterialCode())
								.setMaterialName(shopCart.getMaterialName())
								.setOrganizationId(shopCart.getOrganizationId())
								.setSupplierId(shopCart.getSupplierId())
								.setStatus(ShopCartStatus.DRAFT.name());
						List<ShopCart> list = this.list(new QueryWrapper<>(cart));
						if(CollectionUtils.isNotEmpty(list)){
							errorFlag.set(true);
							lineFlag = false;
							errorMsg.append("购物车已存在该物料; ");
						}
					}else {
						//物料编码+物料描述+库存组织+未提交状态查询是否重复；(购物车查询)
						ShopCart cart = new ShopCart();
						cart.setMaterialCode(shopCart.getMaterialCode())
								.setMaterialName(shopCart.getMaterialName())
								.setOrganizationId(shopCart.getOrganizationId())
								.setStatus(ShopCartStatus.DRAFT.name());
						List<ShopCart> list = this.list(new QueryWrapper<>(cart));
						if(CollectionUtils.isNotEmpty(list)){
							errorFlag.set(true);
							lineFlag = false;
							errorMsg.append("购物车已存在该物料; ");
						}
					}
				}

				if (lineFlag){
					shopCart.setStatus(ShopCartStatus.DRAFT.name());
					shopCart.setShopCartId(IdGenrator.generate());
					shopCarts.add(shopCart);
					shopCartImportModelDto.setErrorMsg(null);
				}else {
					shopCartImportModelDto.setErrorMsg(errorMsg.toString());
				}
			}
		}
	}


	/**
	 * @return void
	 * @Description 购物车Excel导出
	 * @Param [shopCartExportParam, response]
	 * @Author haiping2.li@meicloud.com
	 * @Date 2020.09.29 17:31
	 */
	@Override
	public void exportMaterialItemExcel(ExportExcelParam<ShopCart> shopCartExportParam, HttpServletResponse response) throws Exception {
		// 获取导出的数据
		ShopCart shopCart = shopCartExportParam.getQueryParam(); // 查询参数
		boolean flag = shopCartExportParam.getPageSize()!=null && shopCartExportParam.getPageNum() != null;
		if (flag) {
			// 设置分页
			PageUtil.startPage(shopCart.getPageNum(), shopCart.getPageSize());
		}
		QueryWrapper<ShopCart> wrapper = new QueryWrapper<ShopCart>(shopCart);
		wrapper.in(CollectionUtils.isNotEmpty(shopCart.getOrgIds()), "ORG_ID", shopCart.getOrgIds());
		wrapper.in(CollectionUtils.isNotEmpty(shopCart.getOrganizationIds()), "ORGANIZATION_ID", shopCart.getOrganizationIds());
		List<ShopCart> carts = list(wrapper);
		// 转Map
		List<Map<String, Object>> mapList = BeanMapUtils.objectsToMaps(carts);

		List<String> titleList = shopCartExportParam.getTitleList();
		List<List<Object>> dataList = new ArrayList<>();

		if (CollectionUtils.isNotEmpty(mapList)) {
			List<String> distCodes = new ArrayList<>();
			// distCodes.add("SHOP_CART_STATUS");
			distCodes.add("PURCHASE_TYPE"); // 采购类型字典
			distCodes.add("YES_OR_NO");
			List<DictItemDTO> dictItemDTOS = baseClient.listByDictCode(distCodes);
			Map<String, String> dict = new HashMap<>();
			for (DictItemDTO di : dictItemDTOS) {
				dict.put(di.getDictItemCode(), di.getDictItemName());
			}

			mapList.forEach((map) -> {
				List<Object> list = new ArrayList<>();
				if (CollectionUtils.isNotEmpty(titleList)) {
					titleList.forEach((title) -> {
						Object value = map.get(title);
						if ("status,purchaseType,ifCatalog,".indexOf(title + ",") > -1) {
							String status = (String) value;
							list.add(dict.get(status));
						} else if ("requirementDate".equals(title)) {
							if (value != null) {
								list.add(((LocalDate) value).toString());
							}
						} else {
							if (null != value) {
								list.add(value);
							} else {
								list.add("");
							}
						}
					});
				}
				dataList.add(list);
			});
		}

		// 导出字段
		List<String> head = shopCartExportParam.getMultilingualHeader(shopCartExportParam, ShopCartExportUtils.getShopCartTitles());

		// 文件名
		String fileName = shopCartExportParam.getFileName();

		// 开始导出
		EasyExcelUtil.exportStart(response, dataList, head, fileName);
	}

	/**
	 * @Description 构造导入购物车对象
	 * @Param: [purchaseCatalogQueryDto, shopCartModelDto]
	 * @Return: com.midea.cloud.srm.model.pm.pr.shopcart.entity.ShopCart
	 * @Author: dengyl23@meicloud.com
	 * @Date: 2020/9/28 11:55
	 */
	private ShopCart buildImportShopCart(PurchaseCatalogQueryDto purchaseCatalogQueryDto,ShopCartModelDto shopCartModelDto) throws ParseException{
		ShopCart shopCart = new ShopCart();
		Long id = IdGenrator.generate();
		// 获取当前登录用户
		LoginAppUser loginAppUser = AppUserUtil.getLoginAppUser();
		shopCart.setShopCartId(id);
		// 未提交状态
		shopCart.setStatus(ShopCartStatus.DRAFT.getCode());
		shopCart.setAddToUserId(loginAppUser.getUserId());
		shopCart.setAddToEmpNo(loginAppUser.getUsername());
		shopCart.setAddToNickname(loginAppUser.getNickname());
		shopCart.setRequirementDate(DateUtil.dateToLocalDate(DateUtil.parseDate(shopCartModelDto.getRequirementDate())));
		shopCart.setMaterialCode(shopCartModelDto.getMaterialCode());
		shopCart.setMaterialName(shopCartModelDto.getMaterialName());
		shopCart.setRequirementNum(new BigDecimal(shopCartModelDto.getRequirementNum()));
		shopCart.setOrgName(shopCartModelDto.getOrgName());
		shopCart.setOrganizationName(shopCartModelDto.getOrganizationName());
		shopCart.setPurchaseType(shopCartModelDto.getPurchaseType());
		shopCart.setCategoryName(shopCartModelDto.getCategoryName());
		shopCart.setIfCatalog(StringUtils.isNoneBlank(purchaseCatalogQueryDto.getCeeaIfUse())?purchaseCatalogQueryDto.getCeeaIfUse():"N");
		shopCart.setMaterialId(purchaseCatalogQueryDto.getMaterialId());
		shopCart.setOrganizationId(purchaseCatalogQueryDto.getCeeaOrganizationId()); // 库存组织
		shopCart.setOrganizationCode(purchaseCatalogQueryDto.getCeeaOrganizationCode());
		shopCart.setOrgId(purchaseCatalogQueryDto.getCeeaOrgId());
		shopCart.setCategoryId(purchaseCatalogQueryDto.getCategoryId()); // 小类
		if (!StringUtils.isEmpty(purchaseCatalogQueryDto.getCategoryFullName())) { // 大类
			shopCart.setLargeCategoryName(purchaseCatalogQueryDto.getCategoryFullName().substring(0, purchaseCatalogQueryDto.getCategoryFullName().indexOf("-")));
		}
		shopCart.setMaterialId(purchaseCatalogQueryDto.getMaterialId());
		shopCart.setSpecification(purchaseCatalogQueryDto.getSpecification());
		shopCart.setUnit(purchaseCatalogQueryDto.getUnit());
		shopCart.setUnitName(purchaseCatalogQueryDto.getUnitName());
		if ("Y".equals(shopCart.getIfCatalog())) { // 是目录化物料
			shopCart.setContractNo(purchaseCatalogQueryDto.getCeeaContractNo());
			shopCart.setUnitPrice(purchaseCatalogQueryDto.getTaxPrice()); // 单价（含税）= 预算单价
			shopCart.setCurrencyId(purchaseCatalogQueryDto.getCurrencyId());
			shopCart.setCurrencyCode(purchaseCatalogQueryDto.getCurrencyCode());
			shopCart.setCurrencyName(purchaseCatalogQueryDto.getCurrencyName());
			shopCart.setSupplierId(purchaseCatalogQueryDto.getCeeaSupplierId());
			shopCart.setSupplierCode(purchaseCatalogQueryDto.getCeeaSupplierCode());
			shopCart.setSupplierName(purchaseCatalogQueryDto.getCeeaSupplierName());
		}
		return shopCart;
	}

	/**
	 * @Description 导入前必填参数校验
	 * @Param: [shopCartModelDto]
	 * @Return: void
	 * @Author: dengyl23@meicloud.com
	 * @Date: 2020/9/27 19:50
	 */
	private void checkParam(ShopCartModelDto shopCartModelDto){
		String materialCode = shopCartModelDto.getMaterialCode();
		String materialName = shopCartModelDto.getMaterialName();
		String orgName =  shopCartModelDto.getOrgName();
		String organizationName = shopCartModelDto.getOrganizationName();
		String requirementDate = shopCartModelDto.getRequirementDate();
		String requirementNum = shopCartModelDto.getRequirementNum();
		String purchaseType =  shopCartModelDto.getPurchaseType();
		String categoryName =  shopCartModelDto.getCategoryName();
		if(StringUtils.isBlank(materialCode)){
			throw new BaseException("物料编码必填，请检查导入数据行合法性再进行导入");
		}
		if(StringUtils.isBlank(materialName)){
			throw new BaseException("物料描述必填，请检查导入数据行合法性再进行导入");
		}
		if(StringUtils.isBlank(orgName)){
			throw new BaseException("业务实体必填，请检查导入数据行合法性再进行导入");
		}
		if(StringUtils.isBlank(organizationName)){
			throw new BaseException("库存组织必填，请检查导入数据行合法性再进行导入");
		}
		if(StringUtils.isBlank(requirementDate)){
			throw new BaseException("需求时间必填，请检查导入数据行合法性再进行导入");
		}
		if(StringUtils.isBlank(requirementNum)){
			throw new BaseException("数量必填，请检查导入数据行合法性再进行导入");
		}
		if(StringUtils.isBlank(categoryName)){
			throw new BaseException("小类名称必填，请检查导入数据行合法性再进行导入");
		}
		PurchaseCategory purchaseCategory = baseClient.getPurchaseCategoryByParm(new PurchaseCategory().setCategoryName(categoryName));
		if(null == purchaseCategory){
			throw new BaseException("小类名称不存在，请导入存在的品类");
		}
		if(StringUtils.isBlank(OrderTypeEnum.getValueByname(purchaseType))){
			throw new BaseException("采购类型非法输入，请填入合适的采购类型");
		}else{
			shopCartModelDto.setPurchaseType(OrderTypeEnum.getValueByname(purchaseType));
		}
	}

	/**
	 * @Description:点击汇总人按钮之前要校验购物车状态是否为未提交/点击提交需求之前校验是否为未提交状态
	 * @param: [shopCartIds]
	 * @return: int 1表示可以操作, 0表示不可以操作,只有未提交状态的行才可以操作，2表示没有勾选到行
	 * @auther: wuhx29@meicloud.com
	 * @date: 2020/10/17 15:12
	 */
	@Override
	public int ceeaCheckSummaryAndSubmitRequest(List<Long> shopCartIds) {
		if (CollectionUtils.isEmpty(shopCartIds)) {
			return 2;
		}
		List<ShopCart> shopCarts = this.getBaseMapper().selectBatchIds(shopCartIds);
		Boolean tag = true;
		if (shopCarts.size() > 0) {
			for (ShopCart sc: shopCarts) {
				if (!ShopCartStatus.DRAFT.getCode().equals(sc.getStatus())) {
					tag = false;
				}
			}
		}
		if (tag) {
			return 1;
		}
		return 0;
	}
	/**
	 * @Description:
	 * @param: [shopCartIds]
	 * @return: int 1表示可以操作, 0表示不可以操作,只有未提交状态和已提交状态的行才可以操作，2表示没有勾选到行
	 * @auther: wuhx29@meicloud.com
	 * @date: 2020/10/17 16:07
	 */
	@Override
	public int ceeaCheckUpdateShopCarts(List<Long> shopCartIds) {
		if (CollectionUtils.isEmpty(shopCartIds)) {
			return 2;
		}
		List<ShopCart> shopCarts = this.getBaseMapper().selectBatchIds(shopCartIds);
		Boolean tag = true;
		if (shopCarts.size() > 0) {
			for (ShopCart sc: shopCarts) {
				if (ShopCartStatus.APPLIED.getCode().equals(sc.getStatus())) {
					tag = false;
				}
			}
		}
		if (tag) {
			return 1;
		}
		return 0;
	}

	/**
	 * @Description: 调用创建申请单方法之前先检查行数据状态是否为已提交
	 * @param: [shopCartIds]
	 * @return: int  1为可操作申请单方法，0表示不可以操作,只有状态为已提交的行才可操作，2表示没有勾选到行
	 * @auther: wuhx29@meicloud.com
	 * @date: 2020/10/17 16:50
	 */
	@Override
	public int ceeaCheckCreateRequirements(List<Long> shopCartIds) {
		if (CollectionUtils.isEmpty(shopCartIds)) {
			return 2;
		}
		List<ShopCart> shopCarts = this.getBaseMapper().selectBatchIds(shopCartIds);
		Boolean tag = true;
		if (shopCarts.size() > 0) {
			for (ShopCart sc: shopCarts) {
				if (!ShopCartStatus.SUBMITTED.getCode().equals(sc.getStatus())) {
					tag = false;
				}
			}
		}
		if (tag) {
			return 1;
		}
		return 0;
	}

}
































