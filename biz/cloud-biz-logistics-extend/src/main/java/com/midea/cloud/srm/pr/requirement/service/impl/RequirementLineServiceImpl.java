package com.midea.cloud.srm.pr.requirement.service.impl;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.read.metadata.ReadSheet;
import com.alibaba.excel.util.DateUtils;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.alibaba.excel.write.metadata.style.WriteCellStyle;
import com.alibaba.excel.write.style.HorizontalCellStyleStrategy;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageInfo;
import com.midea.cloud.common.constants.SequenceCodeConstant;
import com.midea.cloud.common.enums.MainType;
import com.midea.cloud.common.enums.UserType;
import com.midea.cloud.common.enums.YesOrNo;
import com.midea.cloud.common.enums.order.JITOrder;
import com.midea.cloud.common.enums.order.ResponseStatus;
import com.midea.cloud.common.enums.pm.po.OrderTypeEnum;
import com.midea.cloud.common.enums.pm.po.SourceSystemEnum;
import com.midea.cloud.common.enums.pm.pr.requirement.*;
import com.midea.cloud.common.enums.pm.ps.BudgetUseStatus;
import com.midea.cloud.common.enums.pm.ps.CategoryEnum;
import com.midea.cloud.common.enums.pm.ps.FSSCResponseCode;
import com.midea.cloud.common.enums.review.CategoryStatus;
import com.midea.cloud.common.exception.BaseException;
import com.midea.cloud.common.handler.TitleColorSheetWriteHandler;
import com.midea.cloud.common.handler.TitleHandler;
import com.midea.cloud.common.listener.AnalysisEventListenerImpl;
import com.midea.cloud.common.result.BaseResult;
import com.midea.cloud.common.result.ResultCode;
import com.midea.cloud.common.utils.*;
import com.midea.cloud.common.utils.redis.RedisUtil;
import com.midea.cloud.component.context.i18n.LocaleHandler;
import com.midea.cloud.component.filter.HttpServletHolder;
import com.midea.cloud.srm.feign.base.BaseClient;
import com.midea.cloud.srm.feign.bid.BidClient;
import com.midea.cloud.srm.feign.contract.ContractClient;
import com.midea.cloud.srm.feign.file.FileCenterClient;
import com.midea.cloud.srm.feign.inq.InqClient;
import com.midea.cloud.srm.feign.supcooperate.SupcooperateClient;
import com.midea.cloud.srm.feign.supplier.SupplierClient;
import com.midea.cloud.srm.model.base.dict.dto.DictItemDTO;
import com.midea.cloud.srm.model.base.material.MaterialItem;
import com.midea.cloud.srm.model.base.material.dto.ItemCodeUserPurchaseDto;
import com.midea.cloud.srm.model.base.material.vo.MaterialMaxCategoryVO;
import com.midea.cloud.srm.model.base.material.MaterialOrg;
import com.midea.cloud.srm.model.base.organization.entity.Organization;
import com.midea.cloud.srm.model.base.purchase.entity.PurchaseTax;
import com.midea.cloud.srm.model.base.soap.DataSourceEnum;
import com.midea.cloud.srm.model.cm.contract.dto.ContractItemDto;
import com.midea.cloud.srm.model.cm.contract.entity.ContractPartner;
import com.midea.cloud.srm.model.cm.contract.vo.ContractVo;
import com.midea.cloud.srm.model.file.upload.entity.Fileupload;
import com.midea.cloud.srm.model.inq.price.dto.PriceLibraryDTO;
import com.midea.cloud.srm.model.inq.price.entity.PriceLibrary;
import com.midea.cloud.srm.model.pm.po.dto.NetPriceQueryDTO;
import com.midea.cloud.srm.model.pm.pr.requirement.dto.*;
import com.midea.cloud.srm.model.pm.pr.requirement.entity.RequirementHead;
import com.midea.cloud.srm.model.pm.pr.requirement.entity.RequirementLine;
import com.midea.cloud.srm.model.pm.pr.requirement.param.FollowNameParam;
import com.midea.cloud.srm.model.pm.pr.requirement.param.OrderQuantityParam;
import com.midea.cloud.srm.model.pm.pr.requirement.param.SourceBusinessGenParam;
import com.midea.cloud.srm.model.pm.pr.requirement.vo.RecommendVendorVO;
import com.midea.cloud.srm.model.pm.pr.requirement.vo.RequirementLineVO;
import com.midea.cloud.srm.model.pm.pr.requirement.vo.VendorAndEffectivePriceVO;
import com.midea.cloud.srm.model.pm.ps.http.BgtCheckReqParamDto;
import com.midea.cloud.srm.model.pm.ps.http.FSSCResult;
import com.midea.cloud.srm.model.pm.ps.http.Line;
import com.midea.cloud.srm.model.rbac.user.entity.LoginAppUser;
import com.midea.cloud.srm.model.supplier.info.entity.CompanyInfo;
import com.midea.cloud.srm.model.supplier.info.entity.FinanceInfo;
import com.midea.cloud.srm.model.supplier.info.entity.OrgCategory;
import com.midea.cloud.srm.model.supplier.info.entity.OrgInfo;
import com.midea.cloud.srm.model.suppliercooperate.order.dto.OrderSaveRequestDTO;
import com.midea.cloud.srm.model.suppliercooperate.order.entry.Order;
import com.midea.cloud.srm.model.suppliercooperate.order.entry.OrderDetail;
import com.midea.cloud.srm.pr.requirement.mapper.RequirementHeadMapper;
import com.midea.cloud.srm.pr.requirement.mapper.RequirementLineMapper;
import com.midea.cloud.srm.pr.requirement.service.IRequirementHeadService;
import com.midea.cloud.srm.pr.requirement.service.IRequirementLineService;
import com.midea.cloud.srm.ps.http.fssc.service.IFSSCReqService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.math.BigDecimal;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * <pre>
 *  采购需求行表 服务实现类
 * </pre>
 *
 * @author fengdc3@meiCloud.com
 * @version 1.00.00
 *
 *          <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-05-12 18:46:40
 *  修改内容:
 *          </pre>
 */
@Slf4j
@Service
public class RequirementLineServiceImpl extends ServiceImpl<RequirementLineMapper, RequirementLine> implements IRequirementLineService {

	@Autowired
	private InqClient inqClient;
	@Autowired
	private BaseClient baseClient;
	@Autowired
	private BidClient bidClient;
	@Autowired
	private SupplierClient supplierClient;
	@Autowired
	private IRequirementHeadService iRequirementHeadService;
	@Autowired
	private SupcooperateClient supcooperateClient;
	@Autowired
	private IRequirementLineService iRequirementLineService;
	@Autowired
	private IFSSCReqService iFSSCReqService;

	@Resource
	private RequirementLineMapper requirementLineMapper;

	@Resource
	private FileCenterClient fileCenterClient;
	@Autowired
	private ContractClient contractClient;

	/** 采购需求头Dao */
	@Resource
	private RequirementHeadMapper requirementHeadMapper;


	@Override
	@Transactional
	public void addRequirementLineBatch(LoginAppUser loginAppUser, RequirementHead requirementHead, List<RequirementLine> requirementLineList) {
//		Assert.isTrue(CollectionUtils.isNotEmpty(requirementLineList), "采购需求行不能为空");
		if (CollectionUtils.isNotEmpty(requirementLineList)) {

			List<String> uomCodeList = new ArrayList<>();
			for (int i = 0; i < requirementLineList.size(); i++) {
				RequirementLine line = requirementLineList.get(i);
//			checkAddBatchParam(line);
				uomCodeList.add(line.getUnitCode());
				Long id = IdGenrator.generate();
				line.setRequirementLineId(id)
						.setApplyStatus(RequirementApplyStatus.UNASSIGNED.getValue())
						.setRequirementHeadId(requirementHead.getRequirementHeadId())
						.setRowNum(i + 1)
						.setRequirementHeadNum(requirementHead.getRequirementHeadNum())
						.setOrderQuantity(line.getRequirementQuantity())
						.setRequirementSource(RequirementSourceSystem.SRM.value)
						.setOrderQuantity(line.getRequirementQuantity());//设置可下单数量为采购需求数量
//					.setCreatedFullName(loginAppUser.getNickname());
//			if (line.getNotaxPrice() != null) {
//				if (line.getRequirementQuantity() != null) {
//					line.setTotalAmount(line.getNotaxPrice().multiply(line.getRequirementQuantity()));
//				}
//				line.setHaveEffectivePrice(RequirementEffectivePrice.HAVE.value);
//				line.setHaveSupplier(RequirementSourcingSupplier.HAVE.value);
//			} else {
//				line.setHaveEffectivePrice(RequirementEffectivePrice.NOT.value);
//				line.setHaveSupplier(RequirementSourcingSupplier.NOT.value);
//			}
			}

			// 查询单位
//			List<PurchaseUnit> purchaseUnitList = baseClient.listPurchaseUnitByCodeList(uomCodeList);
//			Map<String, String> purchaseUnitMap = new HashMap<>();
//			if(CollectionUtils.isNotEmpty(purchaseUnitList)){
//				for(PurchaseUnit purchaseUnit :purchaseUnitList){
//					if (StringUtil.notEmpty(purchaseUnit.getUnitCode()) && StringUtil.notEmpty(purchaseUnit.getUnitName())) {
//						purchaseUnitMap.put(purchaseUnit.getUnitCode(),purchaseUnit.getUnitName());
//						break;
//					}
//				}
//			}
//			log.debug("查询单位信息：" + JSON.toJSONString(purchaseUnitMap));
//			requirementLineList.forEach(line -> {
//				line.setUnit(purchaseUnitMap.get(line.getUnitCode()));
//			});

			this.saveBatch(requirementLineList);
		}
	}

	@Override
	@Transactional
	public void updateBatch(RequirementHead requirementHead, List<RequirementLine> requirementLineList) {
		LoginAppUser loginAppUser = AppUserUtil.getLoginAppUser();
		RequirementLine requirementLine = new RequirementLine();
		requirementLine.setRequirementHeadId(requirementHead.getRequirementHeadId());
		QueryWrapper<RequirementLine> queryWrapper = new QueryWrapper<>(requirementLine);
		List<RequirementLine> oldLineList = this.list(queryWrapper);
		List<Long> oldLineIdList = oldLineList.stream().map(RequirementLine::getRequirementLineId).collect(Collectors.toList());
		List<Long> newLineIdList = new ArrayList<>();
		for (int i = 0; i < requirementLineList.size(); i++) {
			RequirementLine line = requirementLineList.get(i);
			Long requirementLineId = line.getRequirementLineId();
			line.setRowNum(i + 1);
			//设置剩余可下单数量等于采购需求数量
			line.setOrderQuantity(line.getRequirementQuantity());

			// 新增
			if (requirementLineId == null) {
				line.setRequirementLineId(IdGenrator.generate()).setApplyStatus(RequirementApplyStatus.UNASSIGNED.getValue()).setRequirementHeadId(requirementHead.getRequirementHeadId()).setRequirementHeadNum(requirementHead.getRequirementHeadNum()).setOrderQuantity(line.getRequirementQuantity()).setRequirementSource(RequirementSourceSystem.SRM.value).setCreatedFullName(loginAppUser.getNickname());
				if (line.getNotaxPrice() != null) {
					if (line.getRequirementQuantity() != null) {
						line.setTotalAmount(line.getNotaxPrice().multiply(line.getRequirementQuantity()));
					}
					line.setHaveEffectivePrice(RequirementEffectivePrice.HAVE.value);
					line.setHaveSupplier(RequirementSourcingSupplier.HAVE.value);
				} else {
					line.setHaveEffectivePrice(RequirementEffectivePrice.NOT.value);
					line.setHaveSupplier(RequirementSourcingSupplier.NOT.value);
				}
				this.save(line);
			} else {
				newLineIdList.add(requirementLineId);
				// 更新
				this.updateById(line);
			}
		}
		// 删除
		for (Long oldId : oldLineIdList) {
			if (!newLineIdList.contains(oldId)) {
				this.removeById(oldId);
			}
		}
	}

	@Override
	@Transactional
	public BaseResult<String> bachUpdateRequirementLine(List<RequirementLine> requirementLineList) {
		if (CollectionUtils.isEmpty(requirementLineList)) {
			Assert.isNull(ResultCode.MISSING_SERVLET_REQUEST_PART.getMessage());
		}
		BaseResult<String> result = BaseResult.build(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMessage());

		// 更新行表状态
		try {
			getBaseMapper().bachUpdateRequirementLine(requirementLineList);
		} catch (Exception e) {
			log.error("根据ID批量修改采购需求行信息时报错：", e);
			throw new BaseException(ResultCode.OPERATION_FAILED.getMessage());
		}

//		/**
//		 * 更新完行的状态再更新头的状态 1. 如果头下的所有行都没分配, 头表就改为拟定 2. 行没有全部分配, 头表就改为部分分配 3. 行全部分配,
//		 * 头表就改为已分配 状态枚举: RequirementHeadHandleStatus.PARTIAL
//		 */
//		if (CollectionUtils.isNotEmpty(requirementLineList)) {
//			// 获取行对应的行id
//			ArrayList<Long> requirementHeadIdList = new ArrayList<>();
//			requirementLineList.forEach(requirementLine -> {
//				RequirementLine requirementLine1 = this.getById(requirementLine.getRequirementLineId());
//				requirementHeadIdList.add(requirementLine1.getRequirementHeadId());
//			});
//			// 去重
//			List<Long> requirementHeadIds = requirementHeadIdList.stream().distinct().collect(Collectors.toList());
//
//			if (CollectionUtils.isNotEmpty(requirementHeadIds)) {
//				requirementHeadIds.forEach(requirementHeadId -> {
//					// 查询总行数
//					HashMap<String, Object> param = new HashMap<>();
//					param.put("requirementHeadId", requirementHeadId);
//					Long sunNum = this.baseMapper.queryLineNum(param);
//					// 查询未处理行数
//					param.put("applyStatus", RequirementApplyStatus.UNASSIGNED.getValue());
//					Long num = this.baseMapper.queryLineNum(param);
//
//					if (num == sunNum) {
//						// 拟定
//						iRequirementHeadService.updateById(new RequirementHead().setRequirementHeadId(requirementHeadId).setHandleStatus(RequirementHeadHandleStatus.EDIT.getValue()));
//					} else if (num == 0) {
//						// 已全部
//						iRequirementHeadService.updateById(new RequirementHead().setRequirementHeadId(requirementHeadId).setHandleStatus(RequirementHeadHandleStatus.ALL.getValue()));
//					} else {
//						// 部分
//						iRequirementHeadService.updateById(new RequirementHead().setRequirementHeadId(requirementHeadId).setHandleStatus(RequirementHeadHandleStatus.PARTIAL.getValue()));
//					}
//				});
//			}
//		}
		return result;
	}

	@Override
	@Transactional
	public BaseResult<String> bachRejectRequirement(Long[] requirementLineIds, String rejectReason) {
		Assert.notNull(requirementLineIds, ResultCode.MISSING_SERVLET_REQUEST_PARAMETER.getMessage());
		Assert.notNull(rejectReason, ResultCode.MISSING_SERVLET_REQUEST_PARAMETER.getMessage());

		/** 获取用户登录相关信息 */
		Long userId = null;
		String userName = "";
		LoginAppUser user = AppUserUtil.getLoginAppUser();
		if (null != user) {
			userId = user.getUserId();
			userName = user.getUsername();
		}
		String loginIp = IPUtil.getRemoteIpAddr(HttpServletHolder.getRequest());

		/** 根据采购需求行获取信息 */
		List<Long> requirementLineIdList = Arrays.asList(requirementLineIds);
		List<RequirementLine> requirementLineList = getBaseMapper().selectBatchIds(requirementLineIdList);
		boolean flag = true;
		// 校验所有单据的状态是否都是未分配
		if(CollectionUtils.isNotEmpty(requirementLineList)) {
			for (RequirementLine requirementLine : requirementLineList) {
				String applyStatus = requirementLine.getApplyStatus();
				if (!RequirementApplyStatus.UNASSIGNED.getValue().equals(applyStatus)) {
					flag = false;
					break;
				}
			}
		}

		if (flag) {
			List<RequirementLine> updateLineList = new ArrayList<>();
			if (CollectionUtils.isNotEmpty(requirementLineList)) {
				for (RequirementLine line : requirementLineList) {
					if (null != line) {
						RequirementLine requirementLine = new RequirementLine();
						requirementLine.setRequirementLineId(line.getRequirementLineId());
						requirementLine.setApplyStatus(RequirementApplyStatus.REJECTED.getValue());
						requirementLine.setRejectReason(rejectReason);
						requirementLine.setLastUpdatedId(userId);
						requirementLine.setLastUpdatedBy(userName);
						requirementLine.setLastUpdatedByIp(loginIp);
						requirementLine.setLastUpdateDate(new Date());
						updateLineList.add(requirementLine);
					}
				}
			}
			return this.bachUpdateRequirementLine(updateLineList);
		}else {
			return BaseResult.build("R001", "只有未分配的才能驳回");
		}
	}

//	@Override
//	@Transactional
//	public BaseResult<String> bachAssigned(Long[] requirementLineIds, String applyStatus, String buyerId, String buyer, String buyerName) {
//		Assert.notNull(requirementLineIds, ResultCode.MISSING_SERVLET_REQUEST_PARAMETER.getMessage());
//		Assert.notNull(applyStatus, ResultCode.MISSING_SERVLET_REQUEST_PARAMETER.getMessage());
//		if (RequirementApplyStatus.ASSIGNED.getValue().equals(applyStatus)) { // 如果是状态为已分配，则采购员ID、采购员账号和采购员姓名不能为空
//			Assert.notNull(buyerId, ResultCode.MISSING_SERVLET_REQUEST_PARAMETER.getMessage());
//			Assert.notNull(buyer, ResultCode.MISSING_SERVLET_REQUEST_PARAMETER.getMessage());
//			Assert.notNull(buyerName, ResultCode.MISSING_SERVLET_REQUEST_PARAMETER.getMessage());
//		}
//
//		/** 获取用户登录相关信息 */
//		Long userId = null;
//		String userName = "";
//		LoginAppUser user = AppUserUtil.getLoginAppUser();
//		if (null != user) {
//			userId = user.getUserId();
//			userName = user.getUsername();
//		}
//		String loginIp = IPUtil.getRemoteIpAddr(HttpServletHolder.getRequest());
//
//		/** 根据采购需求行获取信息 */
//		List<Long> requirementLineIdList = Arrays.asList(requirementLineIds);
//		List<RequirementLine> requirementLineList = getBaseMapper().selectBatchIds(requirementLineIdList);
//		List<RequirementLine> updateLineList = new ArrayList<>();
//		if (CollectionUtils.isNotEmpty(requirementLineList)) {
//			for (RequirementLine line : requirementLineList) {
//				if (null != line) {
//					if (RequirementApplyStatus.ASSIGNED.getValue().equals(applyStatus) && applyStatus.equals(line.getApplyStatus())) {
//						Assert.isTrue(false, "存在采购需求不可分配采购员");
//					} else if (RequirementApplyStatus.UNASSIGNED.getValue().equals(applyStatus) && applyStatus.equals(line.getApplyStatus())) {
//						Assert.isTrue(false, "存在采购需求不可分配采购员");
//					}
//					RequirementLine requirementLine = new RequirementLine();
//					requirementLine.setRequirementLineId(line.getRequirementLineId());
//					requirementLine.setApplyStatus(applyStatus);
//					// 分配时填写采购员ID和采购员姓名；取消分配时采购员ID和采购员姓名为空
//					if (RequirementApplyStatus.ASSIGNED.getValue().equals(applyStatus)) {
//						requirementLine.setBuyerId(Long.parseLong(buyerId));
//						requirementLine.setBuyerName(buyerName);
//						requirementLine.setBuyer(buyer);
//					} else if (RequirementApplyStatus.UNASSIGNED.getValue().equals(applyStatus)) {
//						requirementLine.setBuyerId(null);
//						requirementLine.setBuyerName(null);
//						requirementLine.setBuyer(null);
//						requirementLine.setEnableUnAssigned(true); // 取消分配的时候为true
//					}
//
//					requirementLine.setLastUpdatedId(userId);
//					requirementLine.setLastUpdatedBy(userName);
//					requirementLine.setLastUpdatedByIp(loginIp);
//					requirementLine.setLastUpdateDate(new Date());
//					updateLineList.add(requirementLine);
//				}
//			}
//		}
//		return this.bachUpdateRequirementLine(updateLineList);
//	}

//  ToDo longi不需要需求合并功能
//	@Override
//	@Transactional
//	public BaseResult<String> isMergeRequirement(Long[] requirementLineIds) {
//		Assert.notNull(requirementLineIds, ResultCode.MISSING_SERVLET_REQUEST_PARAMETER.getMessage());
//
//		BaseResult<String> result = BaseResult.build(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMessage());
//		/** 根据采购需求行获取信息 */
//		List<Long> requirementLineIdList = Arrays.asList(requirementLineIds);
//		List<RequirementLine> requirementLineList = getBaseMapper().selectBatchIds(requirementLineIdList);
//		if (CollectionUtils.isEmpty(requirementLineList)) {
//			Assert.isTrue(false, "根据ID获取采购需求行信息为空");
//		}
//		if (CollectionUtils.isNotEmpty(requirementLineList)) {
//			for (RequirementLine line : requirementLineList) {
//				if (null != line) {
//					String applyStatus = line.getApplyStatus();
//					if (!RequirementApplyStatus.ASSIGNED.getValue().equals(applyStatus)) {
//						Assert.isTrue(false, "申请状态不符合不一致,无法进行需求合并");
//					}
//
//					// 申请状态”、“采购组织”、“工厂”、“需求部门”、“采购分类”、“物料编码”、“库存地点”、“成本类型”、“成本编号”字段必须保持一致
//					Long lineId = line.getRequirementLineId();
//					String orgCode = (StringUtils.isNotBlank(line.getOrgCode()) ? line.getOrgCode() : "");
//					String purchaseOrg = (StringUtils.isNotBlank(line.getPurchaseOrganization()) ? line.getPurchaseOrganization() : "");
//					String receivedFactory = (StringUtils.isNotBlank(line.getReceivedFactory()) ? line.getReceivedFactory() : "");
//					String requirementDepart = (StringUtils.isNotBlank(line.getRequirementDepartment()) ? line.getRequirementDepartment() : "");
//					Long categoryId = (null != line.getCategoryId() ? line.getCategoryId() : 0L);
//					Long itemId = (null != line.getItemId() ? line.getItemId() : 0L);
//					BigDecimal notaxPrice = (null != line.getNotaxPrice() ? line.getNotaxPrice() : BigDecimal.ZERO);
//					BigDecimal taxRate = (null != line.getTaxRate() ? line.getTaxRate() : BigDecimal.ZERO);
//					String inventoryPlace = (StringUtils.isNotBlank(line.getInventoryPlace()) ? line.getInventoryPlace() : "");
//					String costType = (StringUtils.isNotBlank(line.getCostType()) ? line.getCostType() : "");
//					String costNum = (StringUtils.isNotBlank(line.getCostNum()) ? line.getCostNum() : "");
//					for (RequirementLine lineEntity : requirementLineList) {
//						if (null != lineEntity && !lineId.equals(lineEntity.getRequirementLineId())) {
//							String applyStatusCompare = (StringUtils.isNoneBlank(lineEntity.getApplyStatus()) ? lineEntity.getApplyStatus() : "");
//							String orgCodeCompare = (StringUtils.isNotBlank(lineEntity.getOrgCode()) ? lineEntity.getOrgCode() : "");
//							String purchaseOrgCompare = (StringUtils.isNotBlank(lineEntity.getPurchaseOrganization()) ? lineEntity.getPurchaseOrganization() : "");
//							String requirementDepartCompare = (StringUtils.isNotBlank(lineEntity.getRequirementDepartment()) ? lineEntity.getRequirementDepartment() : "");
//							Long categoryIdCompare = (null != line.getCategoryId() ? lineEntity.getCategoryId() : 0L);
//							Long itemIdCompare = (null != line.getItemId() ? lineEntity.getItemId() : 0L);
//							BigDecimal notaxPriceCompare = (null != line.getNotaxPrice() ? line.getNotaxPrice() : BigDecimal.ZERO);
//							BigDecimal taxRateCompare = (null != line.getTaxRate() ? line.getTaxRate() : BigDecimal.ZERO);
//							String inventoryPlaceCompare = (StringUtils.isNotBlank(lineEntity.getInventoryPlace()) ? lineEntity.getInventoryPlace() : "");
//							String costTypeCompare = (StringUtils.isNotBlank(lineEntity.getCostType()) ? lineEntity.getCostType() : "");
//							String costNumCompare = (StringUtils.isNotBlank(lineEntity.getCostNum()) ? lineEntity.getCostNum() : "");
//							String receivedFactoryCompare = (StringUtils.isNotBlank(lineEntity.getReceivedFactory()) ? lineEntity.getReceivedFactory() : "");
//							if (!(applyStatus.equals(applyStatusCompare) && orgCode.equals(orgCodeCompare) && purchaseOrg.equals(purchaseOrgCompare) && requirementDepart.equals(requirementDepartCompare) && categoryId.longValue() == categoryIdCompare.longValue() && itemId.longValue() == itemIdCompare.longValue() && inventoryPlace.equals(inventoryPlaceCompare) && costNum.equals(costNumCompare) && costType.equals(costTypeCompare) && taxRate.compareTo(taxRateCompare) == 0)
//									&& notaxPrice.compareTo(notaxPriceCompare) == 0 && receivedFactory.equals(receivedFactoryCompare)) {
//								Assert.isTrue(false, "采购需求字段不一致,无法进行需求合并");
//							}
//						}
//					}
//				}
//			}
//		}
//		return result;
//	}

	@Override
	@Transactional
	public List<RequirementLine> findRequirementMergeList(Long[] requirementLineIds) {
		Assert.notNull(requirementLineIds, ResultCode.MISSING_SERVLET_REQUEST_PARAMETER.getMessage());
		List<RequirementLine> requirementLineList = new ArrayList<>();
		try {
			requirementLineList = getBaseMapper().findRequirementMergeList(Arrays.asList(requirementLineIds));
		} catch (Exception e) {
			log.error("根据" + JsonUtil.entityToJsonStr(requirementLineIds) + "获取采购需求合并信息时报错：" + e);
			throw new BaseException(ResultCode.OPERATION_FAILED.getMessage());
		}
		return requirementLineList;
	}

	@Override
	@Transactional
	public BaseResult<List<RequirementLine>> bachRequirementMergePreview(Long[] requirementLineIds) {
		Assert.notNull(requirementLineIds, ResultCode.MISSING_SERVLET_REQUEST_PARAMETER.getMessage());

		BaseResult<List<RequirementLine>> result = BaseResult.build(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMessage());
		/** 根据采购需求行获取信息 */
		List<RequirementLine> requirementLineList = this.findRequirementMergeList(requirementLineIds);
		result.setData(requirementLineList);
		return result;
	}


//	@Override
//	@Transactional(rollbackFor = Exception.class)
//	public BaseResult<String> bachRequirementMerge(Long[] requirementLineIds) {
//		Assert.notNull(requirementLineIds, ResultCode.MISSING_SERVLET_REQUEST_PARAMETER.getMessage());
//		this.isMergeRequirement(requirementLineIds); // 检查是否能合并,检测通过才能合并
//
//		BaseResult<String> result = BaseResult.build(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMessage());
//		List<RequirementLine> requirementLineList = this.findRequirementMergeList(requirementLineIds);
//		// 根据采购需求行表ID获取采购需求头和行集合信息
//		List<RequirementHeadQueryDTO> requirementHeadQueryDTOList = requirementHeadMapper.findRequirementHeadAndLineByLineIds(Arrays.asList(requirementLineIds));
//		if (CollectionUtils.isEmpty(requirementHeadQueryDTOList)) {
//			Assert.isTrue(false, "根据ID获取采购需求信息为空");
//		}
//		try {
//
//			/*** 保存采购需求头表和行表信息 */
//			// 获取采购员Id和采购员
//			Long buyerId = null;
//			String buyer = "";
//			String buyerName = "";
//			Long orgId = null;
//			String requirementDepart = "";
//			if (null != requirementHeadQueryDTOList.get(0) && CollectionUtils.isNotEmpty(requirementHeadQueryDTOList.get(0).getRequirementLineList()) && null != requirementHeadQueryDTOList.get(0).getRequirementLineList().get(0)) {
//				RequirementLine requirementLine = requirementHeadQueryDTOList.get(0).getRequirementLineList().get(0);
//				buyerId = requirementLine.getBuyerId();
//				buyer = requirementLine.getBuyer();
//				buyerName = requirementLine.getBuyerName();
//				orgId = requirementLine.getOrgId();
//				requirementDepart = requirementLine.getRequirementDepartment();
//			}
//			// 获取用户信息
//			LoginAppUser user = AppUserUtil.getLoginAppUser();
//			String userName = (null != user ? user.getNickname() : "");
//
//			// 保存采购需求头表审批状态为已审批、处理状态为已全部处理
//			String requirementHeadNum = baseClient.seqGen(SequenceCodeConstant.SEQ_PMP_PR_APPLY_NUM);
//			RequirementHead insertRequirementHead = new RequirementHead();
//			Long headId = IdGenrator.generate();
//			insertRequirementHead.setApplyDate(DateChangeUtil.asLocalDate(new Date()));
//			insertRequirementHead.setAuditStatus(RequirementApproveStatus.APPROVED.getValue());
//			insertRequirementHead.setHandleStatus(RequirementHeadHandleStatus.ALL.getValue());
//			insertRequirementHead.setRequirementHeadNum(requirementHeadNum);
//			insertRequirementHead.setCreatedFullName(userName);
//			insertRequirementHead.setCreateType(RequirementCreateType.MERGE_NEW.value);
//			insertRequirementHead.setRequirementHeadId(headId);
//
//			int insertHeadCount = requirementHeadMapper.insert(insertRequirementHead);
//			if (insertHeadCount == 0 || null == insertRequirementHead.getRequirementHeadId()) {
//				log.error("根据多个ID: " + JsonUtil.entityToJsonStr(requirementLineIds) + "采购需求行合并信息-保存头表时异常");
//				throw new BaseException(ResultCode.OPERATION_FAILED.getMessage());
//			}
//			for (int i = 0; i < requirementLineList.size(); i++) {
//				RequirementLine requirementLine = requirementLineList.get(i);
//				if (null != requirementLine) {
//					requirementLine.setRequirementLineId(IdGenrator.generate());
//					requirementLine.setBuyerId(buyerId);
//					requirementLine.setBuyer(buyer);
//					requirementLine.setBuyerName(buyerName);
//					requirementLine.setApplyStatus(RequirementApplyStatus.ASSIGNED.getValue());
//					requirementLine.setRequirementHeadId(headId);
//					requirementLine.setRequirementHeadNum(requirementHeadNum);
//					requirementLine.setOrgId(orgId);
//					requirementLine.setRequirementDepartment(requirementDepart);
//					requirementLine.setRowNum(i + 1);
//					requirementLine.setRequirementSource(RequirementSourceSystem.SRM.value);
//					requirementLine.setCreatedFullName(userName);
//					requirementLine.setOrderQuantity(requirementLine.getRequirementQuantity());
//					if (requirementLine.getNotaxPrice() != null) {
//						if (requirementLine.getRequirementQuantity() != null) {
//							requirementLine.setTotalAmount(requirementLine.getNotaxPrice().multiply(requirementLine.getRequirementQuantity()));
//						}
//						requirementLine.setHaveEffectivePrice(RequirementEffectivePrice.HAVE.value);
//						requirementLine.setHaveSupplier(RequirementSourcingSupplier.HAVE.value);
//					} else {
//						requirementLine.setHaveEffectivePrice(RequirementEffectivePrice.NOT.value);
//						requirementLine.setHaveSupplier(RequirementSourcingSupplier.NOT.value);
//					}
//				}
//			}
//			boolean isSave = super.saveBatch(requirementLineList);
//			if (!isSave) {
//				log.error("根据多个ID: " + JsonUtil.entityToJsonStr(requirementLineIds) + "采购需求行合并信息-保存时异常");
//				throw new BaseException(ResultCode.OPERATION_FAILED.getMessage());
//			}
//
//			/** 修改采购需求行状态为‘已合并’ */
//			for (RequirementHeadQueryDTO headQueryDTO : requirementHeadQueryDTOList) {
//				List<RequirementLine> updateLineList = new ArrayList<>();
//				if (null != headQueryDTO && CollectionUtils.isNotEmpty(headQueryDTO.getRequirementLineList())) {
//					for (RequirementLine requirementLine : headQueryDTO.getRequirementLineList()) {
//						if (null != requirementLine) {
//							RequirementLine updateLine = new RequirementLine();
//							updateLine.setRequirementLineId(requirementLine.getRequirementLineId());
//							updateLine.setApplyStatus(RequirementApplyStatus.MERGED.getValue());
//							updateLine.setFollowFormCode(requirementHeadNum);
//							updateLineList.add(updateLine);
//						}
//					}
//				}
//				if (CollectionUtils.isNotEmpty(updateLineList)) {
//					getBaseMapper().bachUpdateRequirementLine(updateLineList);
//				}
//			}
//
//		} catch (Exception e) {
//			log.error("根据多个ID: " + JsonUtil.entityToJsonStr(requirementLineIds) + "采购需求行合并信息-保存时报错" + e);
//			throw new BaseException(ResultCode.OPERATION_FAILED.getMessage());
//		}
//		return result;
//	}

	private void checkAddBatchParam(RequirementLine line) {
		Assert.isTrue(line.getOrganizationId() == null, "库存组织不能为空");
//		Assert.isTrue((line.getBudget() != null && line.getBudget().compareTo(BigDecimal.ZERO) >= 0), "预算不能为空且大于等于0");
		Assert.isTrue(line.getCategoryId() == null, "物料小类不能为空");
		Assert.isTrue(StringUtils.isNotBlank(line.getMaterialCode()), "物料编码不能为空");
		Assert.isTrue(StringUtils.isNotBlank(line.getMaterialName()), "物料名称不能为空");
		Assert.isTrue((line.getRequirementQuantity() != null && line.getRequirementQuantity().compareTo(BigDecimal.ZERO) >= 0), "需求数量不能为空且大于等于0");
//		Assert.isTrue(StringUtils.isNotBlank(line.getApplyReason()), "申请原因不能为空");
//		Assert.isTrue(StringUtils.isNotBlank(line.getInventoryPlace()), "库存地点不能为空");
//		Assert.isTrue(StringUtils.isNotBlank(line.getCostType()), "成本类型不能为空");
//		Assert.isTrue(StringUtils.isNotBlank(line.getCostNum()), "成本编号不能为空");
		// 校验收货工厂是
	}

//
//	隆基不需要
//	@Override
//	@Transactional
//	public List<RequirementLine> importExcelInfo(List<Object> list) {
//		List<RequirementLine> requirementLineList = new ArrayList<>();
//		List<NetPriceQueryDTO> netPriceQueryDTOList = new ArrayList<>();
//		List<String> orgNameList = new ArrayList<>();
//		List<String> targetNumList = new ArrayList<>();
//		log.debug("objList: " + JSON.toJSONString(list));
//
//		for (Object obj : list) {
//			RequirementLineImportDTO vo = (RequirementLineImportDTO) obj;
//			log.debug("转换前：" + JSON.toJSONString(vo));
//			RequirementLine line = new RequirementLine();
//			BeanCopyUtil.copyProperties(line, vo);
//			line.setRequirementDate(DateChangeUtil.asLocalDate(vo.getRequirementDate()));
//			log.debug("转换后：" + JSON.toJSONString(line));
//			checkParam(line);
//
//			orgNameList.add(line.getPurchaseOrganization());
//			targetNumList.add(line.getMaterialCode());
//
//			requirementLineList.add(line);
//		}
//		// 批量查询的采购组织和物料存入map
//		log.debug("需求行列表：" + JSON.toJSONString(requirementLineList));
//		List<Organization> organizationList = baseClient.getOrganizationByNameList(orgNameList);
//		Map<String, Organization> organizatioMap = organizationList.stream().collect(Collectors.toMap(Organization::getOrganizationName, org -> org));
//		log.debug("查询库存组织信息：" + JSON.toJSONString(organizatioMap));
//
//		List<MaterialItem> materialItemList = baseClient.listMaterialByCodeBatch(targetNumList);
//		Map<String, MaterialItem> materialItemMap = materialItemList.stream().collect(Collectors.toMap(MaterialItem::getMaterialCode, materialItem -> materialItem));
//		log.debug("查询物料信息：" + JSON.toJSONString(materialItemMap));
//
//		for (int i = 0; i < requirementLineList.size(); i++) {
//			RequirementLine line = requirementLineList.get(i);
//			log.debug("第" + i + "行数据:" + line);
//			// 设置采购需求行信息
//			Organization organization = organizatioMap.get(line.getOrganizationName());
//			Assert.notNull(organization, LocaleHandler.getLocaleMsg("第行获取库存组织信息为空,请检查库存组织名称", "" + (i + 1)));
//			line.setOrganizationId(organization.getOrganizationId()).setOrganizationCode(organization.getOrganizationCode());
//
//			MaterialItem materialItem = materialItemMap.get(line.getMaterialCode());
//			Assert.notNull(materialItem, LocaleHandler.getLocaleMsg("第行获取物料信息为空,请检查物料编码", "" + (i + 1)));
//			line.setMaterialId(materialItem.getMaterialId()).setMaterialCode(materialItem.getMaterialCode()).setMaterialName(materialItem.getMaterialName());
//
//			NetPriceQueryDTO netPriceQueryDTO = new NetPriceQueryDTO();
//			netPriceQueryDTO.setMaterialId(line.getMaterialId()).setOrganizationId(line.getOrganizationId()).setRequirementDate(DateChangeUtil.asDate(line.getRequirementDate()));
//			netPriceQueryDTOList.add(netPriceQueryDTO);
//		}
//
//		// 批量查询价格目录,根据采购组织id和物料id作为key,由于可能有多条记录,则value是列表
//		List<PriceLibrary> priceLibraryList = inqClient.listPriceLibraryByParam(netPriceQueryDTOList);
//		Map<String, List<PriceLibrary>> priceLibrarymMap = new HashMap<>();
//		for (PriceLibrary priceLibrary : priceLibraryList) {
//			StringBuilder sb = new StringBuilder();
//			sb.append(priceLibrary.getOrganizationId()).append(priceLibrary.getItemId());
//			String keyStr = sb.toString();
//
//			List<PriceLibrary> tempList = priceLibrarymMap.get(keyStr);
//			if (tempList == null) {
//				List<PriceLibrary> newList = new ArrayList<>();
//				newList.add(priceLibrary);
//				priceLibrarymMap.put(keyStr, newList);
//			} else {
//				tempList.add(priceLibrary);
//				priceLibrarymMap.put(keyStr, tempList);
//			}
//		}
//		log.debug("查询价格目录信息：" + JSON.toJSONString(priceLibrarymMap));
//
//		for (RequirementLine line : requirementLineList) {
//			PriceLibrary priceLibrary = new PriceLibrary();
//			StringBuilder sb = new StringBuilder();
//			String orgIdAndMaterialStr = sb.append(line.getOrgId()).append(line.getMaterialId()).toString();
//			log.debug("寻找价格目录key: " + orgIdAndMaterialStr);
//			List<PriceLibrary> valuesList = priceLibrarymMap.get(orgIdAndMaterialStr);
//			log.debug("匹配到的价格目录列表: " + JSON.toJSONString(valuesList));
//			Date requirementDateTime = DateChangeUtil.asDate(line.getRequirementDate());
//			if (CollectionUtils.isNotEmpty(valuesList)) {
//				if (valuesList.size() == 1) {
//					priceLibrary = valuesList.get(0);
//					log.debug("唯一匹配到的价格目录: " + JSON.toJSONString(priceLibrary));
//				} else {
//					// 这里还需要根据需求时间匹配: 在有效时间和失效时间之间,由于数据来源是取最低未税价格,则这里只会有一条记录
//					for (PriceLibrary value : valuesList) {
//						if (requirementDateTime.compareTo(value.getEffectiveDate()) >= 0 && requirementDateTime.compareTo(value.getExpirationDate()) <= 0) {
//							priceLibrary = value;
//							log.debug("匹配到的价格目录: " + JSON.toJSONString(priceLibrary));
//						}
//					}
//				}
//			}
//
//			// 设置从价格目录获取的信息,可能为null
//			line.setCategoryId(priceLibrary.getCategoryId()).setCategoryName(priceLibrary.getCategoryName()).setUnit(priceLibrary.getUnit()).setNotaxPrice(priceLibrary.getNotaxPrice()).setCurrency(priceLibrary.getCurrency()).setTaxRate(priceLibrary.getTaxRate() != null ? new BigDecimal(priceLibrary.getTaxRate().toString()) : null).setTaxKey(priceLibrary.getTaxKey());
//
//			// 未税价格可能为null
//			if (line.getNotaxPrice() != null) {
//				line.setTotalAmount(line.getNotaxPrice().multiply(line.getRequirementQuantity()));
//			}
//		}
//
//		return requirementLineList;
//	}



	@Override
	public PageInfo<RequirementLine> listApprovedApplyByPage(RequirementLine requirementLine) {
		LoginAppUser user = AppUserUtil.getLoginAppUser();
		List<RequirementHead> requirementHeadList = iRequirementHeadService.list(new QueryWrapper<>(new RequirementHead().setAuditStatus(RequirementApproveStatus.APPROVED.getValue())));
		if (CollectionUtils.isEmpty(requirementHeadList)) {
			return new PageInfo<>();
		}
		List<Long> headIdList = requirementHeadList.stream().map(RequirementHead::getRequirementHeadId).collect(Collectors.toList());
		PageUtil.startPage(requirementLine.getPageNum(), requirementLine.getPageSize());
		QueryWrapper<RequirementLine> wrapper = new QueryWrapper<>();
		wrapper.like(StringUtils.isNoneBlank(requirementLine.getPurchaseOrganization()), "PURCHASE_ORGANIZATION", requirementLine.getPurchaseOrganization());
		wrapper.like(StringUtils.isNoneBlank(requirementLine.getMaterialCode()), "MATERIAL_CODE", requirementLine.getMaterialCode());
		wrapper.like(StringUtils.isNoneBlank(requirementLine.getCategoryName()), "CATEGORY_NAME", requirementLine.getCategoryName());
		wrapper.like(StringUtils.isNoneBlank(requirementLine.getRequirementDepartment()), "REQUIREMENT_DEPARTMENT", requirementLine.getRequirementDepartment());
		wrapper.like(StringUtils.isNoneBlank(requirementLine.getCostNum()), "COST_NUM", requirementLine.getCostNum());
		wrapper.like(StringUtils.isNoneBlank(requirementLine.getRequirementHeadNum()), "REQUIREMENT_HEAD_NUM", requirementLine.getRequirementHeadNum());
		wrapper.eq(StringUtils.isNoneBlank(requirementLine.getCostType()), "COST_TYPE", requirementLine.getCostType());
		wrapper.eq(StringUtils.isNoneBlank(requirementLine.getApplyStatus()), "APPLY_STATUS", requirementLine.getApplyStatus());
		if (StringUtils.equals(UserType.BUYER.name(), user.getUserType())) {
			if (StringUtils.equals(MainType.N.name(), user.getMainType())) {
				wrapper.eq(user.getUserId() != null, "BUYER_ID", user.getUserId());
			}
		}
		wrapper.in("REQUIREMENT_HEAD_ID", headIdList);
		wrapper.orderByDesc("LAST_UPDATE_DATE");
		return new PageInfo<>(this.list(wrapper));
	}

	@Override
	@Transactional
	public void updateOrderQuantity(OrderQuantityParam orderQuantityParam) {
		Assert.notNull(orderQuantityParam.getRequirementLineId(), "采购申请行ID不能为空");
		Assert.notNull(orderQuantityParam.getRevertAmount(), "回滚的可下单数量不能为空");

		RequirementLine requirementLine = this.getOne(new QueryWrapper<>(new RequirementLine().setRequirementLineId(orderQuantityParam.getRequirementLineId())));
		BigDecimal oldAmount = requirementLine.getOrderQuantity();
		BigDecimal revertAmount = orderQuantityParam.getRevertAmount();
		BigDecimal newAmount = oldAmount.add(revertAmount);
		this.updateById(new RequirementLine().setRequirementLineId(orderQuantityParam.getRequirementLineId()).setOrderQuantity(newAmount));
	}

	@Override
	@Transactional
	public void updateOrderQuantityBatch(List<OrderQuantityParam> orderQuantityParamList) {
		Assert.isTrue(CollectionUtils.isNotEmpty(orderQuantityParamList), "批量更改采购需求可下单数量入参列表不能为空");
		orderQuantityParamList.forEach(this::updateOrderQuantity);
	}

	@Override
	public VendorAndEffectivePriceVO getVendorAndEffectivePrice(RequirementLine requirementLine) {
		NetPriceQueryDTO netPriceQueryDTO = new NetPriceQueryDTO();
		netPriceQueryDTO.setMaterialId(requirementLine.getMaterialId());
		netPriceQueryDTO.setOrganizationId(requirementLine.getOrgId());
		netPriceQueryDTO.setRequirementDate(DateChangeUtil.asDate(requirementLine.getRequirementDate()));
		PriceLibrary priceLibraryByParam = inqClient.getPriceLibraryByParam(netPriceQueryDTO);
		if (priceLibraryByParam == null) {
			return new VendorAndEffectivePriceVO().setHaveEffectivePrice(RequirementEffectivePrice.NOT.value).setHaveSupplier(RequirementSourcingSupplier.NOT.value);
		} else {
			return new VendorAndEffectivePriceVO().setHaveEffectivePrice(RequirementEffectivePrice.HAVE.value).setHaveSupplier(RequirementSourcingSupplier.HAVE.value);
		}
	}

	@Override
	public List<RecommendVendorVO> listRecommendVendor(List<RequirementLine> requirementLineList) {
		List<RecommendVendorVO> recommendVendorVOList = new ArrayList<>();
		if (CollectionUtils.isNotEmpty(requirementLineList)) {
			// 判断是否同一个组织
			Long orgId = requirementLineList.get(0).getOrgId();
			requirementLineList.forEach(requirementLine -> {
				if(!orgId.equals(requirementLine.getOrgId())){
					throw new BaseException("多行必须同一个组织维度");
				}
			});
			for(RequirementLine requirementLine : requirementLineList){
				if (!RequirementSourcingSupplier.HAVE.value.equals(requirementLine.getHaveSupplier()) || !RequirementEffectivePrice.HAVE.value.equals(requirementLine.getHaveEffectivePrice())) {
					throw new BaseException(LocaleHandler.getLocaleMsg("该采购需求无法创建采购订单"));
				}
				if (!RequirementApplyStatus.ASSIGNED.getValue().equals(requirementLine.getApplyStatus()) && !RequirementApplyStatus.CREATED.getValue().equals(requirementLine.getApplyStatus()) && !RequirementApplyStatus.TRANSFERRED_ORDER.getValue().equals(requirementLine.getApplyStatus())) {
					throw new BaseException(LocaleHandler.getLocaleMsg("申请状态不是已分配、已创建寻源单据、已创建订单,不能创建采购订单"));
				}
				String fullPathId = requirementLine.getFullPathId();
				List<PriceLibrary> priceLibraryList = inqClient.listPriceLibrary(new NetPriceQueryDTO().setMaterialId(requirementLine.getMaterialId()).setOrganizationId(requirementLine.getOrgId()).setRequirementDate(DateChangeUtil.asDate(requirementLine.getRequirementDate())));
				if (CollectionUtils.isNotEmpty(priceLibraryList)) {
					for (PriceLibrary priceLibrary : priceLibraryList) {
						// 检查供应商去采购组织是否匹配
						OrgInfo orgInfo = supplierClient.getOrgInfoByOrgIdAndCompanyId(priceLibrary.getOrganizationId(), priceLibrary.getVendorId());
						if (null != orgInfo && StringUtil.notEmpty(orgInfo.getOrgCode())) {
							if (StringUtil.notEmpty(orgInfo.getServiceStatus()) && "EFFECTIVE".equals(orgInfo.getServiceStatus())) {
								RecommendVendorVO recommendVendorVO = new RecommendVendorVO();
								BeanCopyUtil.copyProperties(recommendVendorVO, priceLibrary);
								recommendVendorVO.setRequirementQuantity(requirementLine.getRequirementQuantity()).setRequirementLineId(requirementLine.getRequirementLineId()).setBuyerName(requirementLine.getBuyerName()).setRequirementHeadId(requirementLine.getRequirementHeadId()).setRequirementHeadNum(requirementLine.getRequirementHeadNum()).setInventoryPlace(requirementLine.getInventoryPlace()).setRowNum(requirementLine.getRowNum()).setCostNum(requirementLine.getCostNum())
										.setCostType(requirementLine.getCostType()).setRequirementDate(requirementLine.getRequirementDate()).setReceivedFactory(requirementLine.getReceivedFactory()).setOrderQuantity(requirementLine.getOrderQuantity()).setFullPathId(fullPathId);
								recommendVendorVOList.add(recommendVendorVO);
							}
						}
					}
				}
			}
		}
		return recommendVendorVOList;
	}

	/**
	 * 1. 一个行的配额和不能超过1, 物料可以
	 * 2. 一个供应商生成一个单, 一个供应商有多航
	 */
	public void checkRecommendVendorVOList (List<RecommendVendorVO> recommendVendorVOList){
		Assert.isTrue(CollectionUtils.isNotEmpty(recommendVendorVOList), "推荐供应商列表不能为空");
		// 检查每行对应的供应商配额和
		if(CollectionUtils.isNotEmpty(recommendVendorVOList)){
			// 按行进行分组
			HashMap<Long, List<RecommendVendorVO>> hashMap = new HashMap<>();
			// 采购组织
			Long organizationId = recommendVendorVOList.get(0).getOrganizationId();
			for(RecommendVendorVO recommendVendorVO : recommendVendorVOList){
				Assert.notNull(recommendVendorVO.getOrganizationId(), "采购组织不能为空");
				Assert.notNull(recommendVendorVO.getOrganizationName(), "采购组织名称不能为空");
				Assert.notNull(recommendVendorVO.getVendorId(), "供应商不能为空");
				Assert.notNull(recommendVendorVO.getVendorCode(), "供应商编号不能为空");
				Assert.notNull(recommendVendorVO.getVendorName(), "供应商名称不能为空");
				if(!organizationId.equals(recommendVendorVO.getOrganizationId())){
					throw new BaseException("创建采购订单必须都为同一个组织");
				}
				Long requirementLineId = recommendVendorVO.getRequirementLineId();
				if(hashMap.containsKey(requirementLineId)){
					List<RecommendVendorVO> recommendVendorVOS = hashMap.get(requirementLineId);
					recommendVendorVOS.add(recommendVendorVO);
					hashMap.put(requirementLineId,recommendVendorVOS);
				}else {
					ArrayList<RecommendVendorVO> recommendVendorVOS = new ArrayList<>();
					recommendVendorVOS.add(recommendVendorVO);
					hashMap.put(requirementLineId,recommendVendorVOS);
				}
			}
			if(!hashMap.isEmpty()){
				for(List<RecommendVendorVO> vendorVOList : hashMap.values()){
					if(CollectionUtils.isNotEmpty(vendorVOList)){
						// 校验每行数据
						RecommendVendorVO tempVO = vendorVOList.get(0);
						RequirementLine tempLine = this.getById(tempVO.getRequirementLineId());
						if (tempLine == null) {
							throw new BaseException(LocaleHandler.getLocaleMsg("查询采购需求行信息为空"));
						}
						if (RequirementApplyStatus.COMPLETED.getValue().equals(tempLine.getApplyStatus())) {
							throw new BaseException(LocaleHandler.getLocaleMsg("该采购需求已完成,不能创建采购订单"));
						}
						// 校验配额
						BigDecimal sum = new BigDecimal(0);
						for(RecommendVendorVO recommendVendorVO : vendorVOList){
							BigDecimal quota = recommendVendorVO.getQuota();
							Assert.isTrue(quota.doubleValue() >= 0 ,"配额不能小于0");
							sum.add(quota);
						}
						Assert.isTrue(sum.doubleValue() <= 1 , "单个物料配额总额必须小于或等于100");
					}
				}
			}
		}
	}

	@Override
	@Transactional
	public void genOrder(List<RecommendVendorVO> recommendVendorVOList) {
		// 校验
		checkRecommendVendorVOList(recommendVendorVOList);
		// 获取采购组织信息
		Organization organization = baseClient.getOrganizationByParam(new Organization().setOrganizationId(recommendVendorVOList.get(0).getOrganizationId()));
		Assert.notNull(organization, "根据采购组织ID查询采购组织信息为空");
		// 获取用户信息
		LoginAppUser loginAppUser = AppUserUtil.getLoginAppUser();
		/**
		 * 数据分组, 按供应商进行分组
		 */
		Map<String, List<RecommendVendorVO>> hashMap = new HashMap<>();
		for(RecommendVendorVO recommendVendorVO : recommendVendorVOList){
			String vendorCode = recommendVendorVO.getVendorCode();
			if(hashMap.containsKey(vendorCode)){
				List<RecommendVendorVO> vendorVOList = hashMap.get(vendorCode);
				vendorVOList.add(recommendVendorVO);
				hashMap.put(vendorCode,vendorVOList);
			}else {
				ArrayList<RecommendVendorVO> vendorVOS = new ArrayList<>();
				vendorVOS.add(recommendVendorVO);
				hashMap.put(vendorCode,vendorVOS);
			}
		}
		// 统计行信息
		HashMap<Long, RequirementLine> lineHashMap = new HashMap<>();
		if(!hashMap.isEmpty()){
			for(List<RecommendVendorVO> recommendVendorVOS : hashMap.values()){
				// 获取单据号
				String orderCode = baseClient.seqGen(SequenceCodeConstant.SEQ_SSC_ORDER_NUM);
				RecommendVendorVO recommend = recommendVendorVOS.get(0);
				String fullPathId = recommendVendorVOS.get(0).getFullPathId();
				// 开始创建一个供应商的单据
				// 订单头信息
				// 采购订单头表
				Order order = new Order();
				// 开始保存或新增订单明细
				order.setOrganizationId(organization.getOrganizationId());
				order.setOrganizationCode(organization.getOrganizationCode());
				order.setOrganizationName(organization.getOrganizationName());
				order.setFullPathId(fullPathId);
				order.setOrderId(IdGenrator.generate());
				order.setJitOrder(JITOrder.N.name());
				order.setOrderNumber(orderCode);
				order.setSourceSystem(SourceSystemEnum.PURCHASE_REQUIREMENT.getValue());
				/*order.setOrderStatus(PurchaseOrderEnum.UNISSUED.getValue());*/
				order.setSubmittedId(loginAppUser.getUserId());
				order.setSubmittedBy(loginAppUser.getUsername());
				order.setSubmittedTime(new Date());
				/*order.setOrderType(OrderTypeEnum.STANDARD.getValue());*/
				order.setVendorId(recommend.getVendorId());
				order.setVendorCode(recommend.getVendorCode());
				order.setVendorName(recommend.getVendorName());
				order.setTaxRate(new BigDecimal(recommend.getTaxRate()));
				order.setTaxKey(recommend.getTaxKey());
				order.setRfqSettlementCurrency(recommend.getCurrency());
				order.setBuyerName(recommend.getBuyerName());
				order.setResponseStatus(ResponseStatus.UNCOMFIRMED.name());
				order.setTel(loginAppUser.getPhone());
				// 获取财务信息
				FinanceInfo financeInfo = supplierClient.getFinanceInfoByCompanyIdAndOrgId(recommend.getVendorId(), recommend.getOrganizationId());
				if (financeInfo != null) {
					order.setPaymentMethod(financeInfo.getPaymentMethod());
					order.setTermOfPayment(financeInfo.getPaymentTerms());
				}

				// 行表信息
				List<OrderDetail> orderDetailList = new ArrayList<>();
				int lineNum = 1;
				BigDecimal orderAmountSum = new BigDecimal(0);
				for(RecommendVendorVO vendorVO : recommendVendorVOS){
					// 获取行信息
					RequirementLine tempLine = this.getById(vendorVO.getRequirementLineId());
					// 计算订单金额
					BigDecimal orderAmount = vendorVO.getNotaxPrice().multiply(tempLine.getOrderQuantity()).multiply(vendorVO.getQuota());
					// 累计订单金额
					orderAmountSum.add(orderAmount);

					// 统计下单总数
					BigDecimal totalAmount = BigDecimal.ZERO;
					// 可下单数量
					BigDecimal oldTotalAmount = tempLine.getOrderQuantity();
					// 采购订单行表
					OrderDetail orderDetail = new OrderDetail();
					orderDetail.setOrderId(order.getOrderId());
					orderDetail.setOrderDetailId(IdGenrator.generate());
					/*orderDetail.setOrderDetailStatus(PurchaseOrderEnum.UNISSUED.getValue());*/
					orderDetail.setOrderNum(oldTotalAmount.multiply(vendorVO.getQuota()));
					orderDetail.setLineNum(lineNum);
					orderDetail.setExternalType("REQUIREMENT");
					orderDetail.setExternalId(vendorVO.getRequirementHeadId());
					orderDetail.setExternalNum(vendorVO.getRequirementHeadNum());
					orderDetail.setExternalRowId(vendorVO.getRequirementLineId());
					orderDetail.setExternalRowNum(vendorVO.getRowNum().longValue());
					orderDetail.setReceiveSum(BigDecimal.ZERO);
					orderDetail.setCategoryId(vendorVO.getCategoryId());
					orderDetail.setCategoryName(vendorVO.getCategoryName());
					orderDetail.setMaterialId(vendorVO.getItemId());
					orderDetail.setMaterialName(vendorVO.getItemDesc());
					orderDetail.setMaterialCode(vendorVO.getItemCode());
					orderDetail.setCeeaUnitTaxPrice(vendorVO.getNotaxPrice().multiply((BigDecimal.ONE.add(new BigDecimal(vendorVO.getTaxRate()).divide(BigDecimal.valueOf(100), 5, BigDecimal.ROUND_HALF_UP)))));
					orderDetail.setCeeaUnitNoTaxPrice(vendorVO.getNotaxPrice());
					orderDetail.setUnit(vendorVO.getUnit());
					orderDetail.setRequirementDate(vendorVO.getRequirementDate());
					orderDetail.setRequirementQuantity(vendorVO.getQuota().multiply(oldTotalAmount));
					orderDetail.setInventoryPlace(vendorVO.getInventoryPlace());
					orderDetail.setPriceUnit(vendorVO.getPriceUnit());
					orderDetail.setCostNum(vendorVO.getCostNum());
					orderDetail.setCostType(vendorVO.getCostType());
					orderDetail.setCurrency(vendorVO.getCurrency());
					orderDetail.setReceivedFactory(vendorVO.getReceivedFactory());
					orderDetailList.add(orderDetail);
					totalAmount = totalAmount.add(vendorVO.getQuota().multiply(oldTotalAmount));

					Long requirementLineId = vendorVO.getRequirementLineId();
					if(lineHashMap.containsKey(requirementLineId)){
						RequirementLine requirementLine = lineHashMap.get(requirementLineId);
						// 累计下单数量
						BigDecimal orderQuantity = requirementLine.getOrderQuantity().add(totalAmount);
						requirementLine.setOrderQuantity(orderQuantity);
						// 回写后续单据号
						String followFormCode = requirementLine.getFollowFormCode();
						String newFollowFormCode = followFormCode + "," + orderCode ;
						requirementLine.setFollowFormCode(newFollowFormCode);
						lineHashMap.put(requirementLineId,requirementLine);
					}else {
						// 新
						RequirementLine requirementLine = new RequirementLine();
						requirementLine.setRequirementLineId(requirementLineId);
						// 回写后续单据号
						requirementLine.setFollowFormCode(orderCode);
						// 累计下单数量
						requirementLine.setOrderQuantity(totalAmount);
						//
						lineHashMap.put(requirementLineId,requirementLine);
					}
					lineNum++;
				}
				// 订单总额
				order.setOrderAmount(orderAmountSum);
				// 创建采购单
				supcooperateClient.saveOrderForm(new OrderSaveRequestDTO().setDetailList(orderDetailList).setOrder(order));
			}
			if(!lineHashMap.isEmpty()){
				lineHashMap.values().forEach(requirementLine->{
					Long requirementLineId = requirementLine.getRequirementLineId();
					RequirementLine line = this.getById(requirementLineId);
					// 累计下单数量
					BigDecimal orderQuantitySum = requirementLine.getOrderQuantity();
					// 可下单数量
					BigDecimal orderQuantity = line.getOrderQuantity();
					// 剩余可下单数量
					BigDecimal newTotalAmount = orderQuantity.subtract(orderQuantitySum);
					requirementLine.setOrderQuantity(newTotalAmount);
					// 状态,根据可下单数量
					if (newTotalAmount.doubleValue() > 0) {
						requirementLine.setApplyStatus(RequirementApplyStatus.TRANSFERRED_ORDER.getValue());
					}else {
						requirementLine.setApplyStatus(RequirementApplyStatus.COMPLETED.getValue());
					}
					String followFormCode = line.getFollowFormCode();
					if(StringUtil.notEmpty(followFormCode)){
						String followForm = requirementLine.getFollowFormCode();
						String code = followFormCode + "," + followForm ;
						requirementLine.setFollowFormCode(code);
					}
					this.updateById(requirementLine);
				});
			}
		}
	}

	@Override
	@Transactional
	public void genSourceBusiness(SourceBusinessGenParam param) {
		String businessGenType = param.getBusinessGenType();
		List<RequirementLine> requirementLineList = param.getRequirementLineList();
		/**
		 * 1. 校验是否都为用一个组织, 不是则不处理
		 * 2. 校验是否同一个物料, 同一个物料则合并需求数量
		 */
		HashMap<String, RequirementLine> requirementLines = new HashMap<>();
		if(CollectionUtils.isNotEmpty(requirementLineList)){
			Long orgId = requirementLineList.get(0).getOrgId();
			requirementLineList.forEach(requirement->{
				if (RequirementApplyStatus.COMPLETED.getValue().equals(requirement.getApplyStatus())) {
					throw new BaseException(LocaleHandler.getLocaleMsg("该采购需求已完成,不能创建寻源单据"));
				}
				if(!orgId.equals(requirement.getOrgId())){
					throw new BaseException("必须为同一个组织才能多条创建");
				}
				// 截取同一个物料数据进行需求数量合并
				String itemCode = requirement.getMaterialCode();
				if(!requirementLines.containsKey(itemCode)){
					requirementLines.put(itemCode,requirement);
				}else {
					RequirementLine requirementLine1 = requirementLines.get(itemCode);
					BigDecimal bigDecimal = requirementLine1.getRequirementQuantity().add(requirement.getRequirementQuantity());
					requirementLine1.setRequirementQuantity(bigDecimal);
					requirementLines.put(itemCode,requirementLine1);
				}
			});
			if(!requirementLines.isEmpty()){
				List<RequirementLine> list = new ArrayList<>();
				for(RequirementLine requirementLine : requirementLines.values()){
					list.add(requirementLine);
				}

				if (CollectionUtils.isNotEmpty(list)) {
					String code = null;
					if (BusinessGenType.BID.value.equals(businessGenType)) {
						// 创建招标单
						code = bidClient.requirementGenBiding(list);
					} else if (BusinessGenType.INQ.value.equals(businessGenType)) {
						// 创建询比价单
						code = inqClient.requirementGenInquiry(list);
					}

					// 更新行状态
					for(RequirementLine temp : requirementLineList){
						Long requirementLineId = temp.getRequirementLineId();
						RequirementLine requirementLine = this.getById(requirementLineId);
						if (null != requirementLine) {
							RequirementLine line = new RequirementLine();
							line.setRequirementLineId(requirementLine.getRequirementLineId());
							// 状态改为已完成
							line.setApplyStatus(RequirementApplyStatus.CREATED.getValue());
							// 后续单号拼接
							String followFormCode = requirementLine.getFollowFormCode();
							if(StringUtil.notEmpty(followFormCode)){
								String newFollowFormCode = followFormCode+","+code;
								line.setFollowFormCode(newFollowFormCode);
							}else {
								line.setFollowFormCode(code);
							}
							this.updateById(line);
						}
					}
				}
			}
		}
	}

	@Override
	@Transactional
	public void updateIfExistRequirementLine(FollowNameParam followNameParam) {
		FollowNameParam.SourceForm sourceForm = Optional.ofNullable(followNameParam.getSourceForm())
				.orElseThrow(() -> new BaseException("寻源单不允许为空"));
		updateFollowFormName(sourceForm.getFormNum(), sourceForm.getFormTitle());
	}

	private void updateFollowFormName(String code, String name) {
		RequirementLine tempLine = this.getOne(new QueryWrapper<>(new RequirementLine().setFollowFormCode(code)));
		if (tempLine != null) {
			this.updateById(new RequirementLine().setRequirementLineId(tempLine.getRequirementLineId()).setFollowFormName(name));
		}
	}

	private void checkParam(RequirementLine line) {
		Assert.notNull(line, "需求明细不能为空");
		Assert.notNull(line.getPurchaseOrganization(), "采购组织不能为空");
		Assert.notNull(line.getRequirementDepartment(), "需求部门不能为空");
		Assert.notNull(line.getBudget(), "预算不能为空");
		Assert.notNull(line.getMaterialCode(), "物料编码不能为空");
		Assert.notNull(line.getRequirementQuantity(), "需求数量不能为空");
		Assert.notNull(line.getRequirementDate(), "需求日期不能为空");
//		Assert.notNull(line.getApplyReason(), "申请原因不能为空");
		Assert.notNull(line.getInventoryPlace(), "库存地点不能为空");
		Assert.notNull(line.getCostType(), "成本类型不能为空");
		Assert.notNull(line.getCostNum(), "成本编号不能为空");
	}

	@Override
	public void orderReturn(List<OrderDetail> detailList) {
		// 获取订单行
		if(CollectionUtils.isNotEmpty(detailList)){
			//requirementQuantity
			for(OrderDetail orderDetail:detailList){
				// 寻求数量
				BigDecimal requirementQuantity = orderDetail.getRequirementQuantity();
				// 行id
				Long externalRowId = orderDetail.getExternalRowId();
				RequirementLine requirementLine = this.getById(externalRowId);
				// 更新需求行信息
				if (null != requirementLine) {
					RequirementLine line = new RequirementLine();
					// 可下单数量
					BigDecimal orderQuantity = requirementLine.getOrderQuantity();
					if(null != orderQuantity){
						line.setOrderQuantity(orderQuantity.add(requirementQuantity));
					}else {
						line.setOrderQuantity(requirementQuantity);
					}
					// 状态,根据可下单数量
					if (line.getOrderQuantity().doubleValue() > 0){
						requirementLine.setApplyStatus(RequirementApplyStatus.TRANSFERRED_ORDER.getValue());
					}else {
						requirementLine.setApplyStatus(RequirementApplyStatus.COMPLETED.getValue());
					}
					this.updateById(requirementLine);
				}

			}
		}
	}

	@Override
	@Transactional
	public void bachAssigned(RequirementManageDTO requirementManageDTO) {
		List<Long> requirementLineIds = requirementManageDTO.getRequirementLineIds();
		Assert.notEmpty(requirementLineIds, ResultCode.MISSING_SERVLET_REQUEST_PARAMETER.getMessage());
		List<RequirementLine> requirementLines = this.listByIds(requirementLineIds);
		for (RequirementLine requirementLine : requirementLines) {
			if (requirementLine == null) continue;
			requirementLine.setCeeaStrategyUserId(requirementManageDTO.getCeeaStrategyUserId() == null ? requirementLine.getCeeaStrategyUserId() : requirementManageDTO.getCeeaStrategyUserId())
					.setCeeaStrategyUserName(requirementManageDTO.getCeeaStrategyUserName() == null ? requirementLine.getCeeaStrategyUserName() : requirementManageDTO.getCeeaStrategyUserName())
					.setCeeaStrategyUserNickname(requirementManageDTO.getCeeaStrategyUserNickname() == null ? requirementLine.getCeeaStrategyUserNickname() : requirementManageDTO.getCeeaStrategyUserNickname())
					.setCeeaPerformUserId(requirementManageDTO.getCeeaPerformUserId() == null ? requirementLine.getCeeaPerformUserId() : requirementManageDTO.getCeeaPerformUserId())
					.setCeeaPerformUserName(requirementManageDTO.getCeeaPerformUserName() == null ? requirementLine.getCeeaPerformUserName() : requirementManageDTO.getCeeaPerformUserName())
					.setCeeaPerformUserNickname(requirementManageDTO.getCeeaPerformUserNickname() == null ? requirementLine.getCeeaPerformUserNickname() : requirementManageDTO.getCeeaPerformUserNickname());
			if (!RequirementApplyStatus.UNASSIGNED.getValue().equals(requirementLine.getApplyStatus())
					&& !RequirementApplyStatus.ASSIGNED.getValue().equals(requirementLine.getApplyStatus())) {
				throw new BaseException(LocaleHandler.getLocaleMsg("存在不为分配和未分配状态的行,无法执行分配或转办操作,请检查!"));
			}
			if ( requirementLine.getCeeaStrategyUserId() != null
					&& requirementLine.getCeeaPerformUserId() != null) {
				//各个采购员都不为空时,申请状态为已分配
				requirementLine.setApplyStatus(RequirementApplyStatus.ASSIGNED.getValue());
				this.updateById(requirementLine);
			} else {
				//否则,申请状态为未分配
				requirementLine.setApplyStatus(RequirementApplyStatus.UNASSIGNED.getValue());
				this.updateById(requirementLine);
			}
		}
	}

	@Override
	@Transactional
	public void batchReceive(RequirementManageDTO requirementManageDTO) {
		List<Long> requirementLineIds = requirementManageDTO.getRequirementLineIds();
		Assert.notEmpty(requirementLineIds, ResultCode.MISSING_SERVLET_REQUEST_PARAMETER.getMessage());
		List<RequirementLine> requirementLines = this.listByIds(requirementLineIds);
		for (RequirementLine requirementLine : requirementLines) {
			if (requirementLine == null) continue;
			if (RequirementApplyStatus.ASSIGNED.getValue().equals(requirementLine.getApplyStatus())) {
				requirementLine.setApplyStatus(RequirementApplyStatus.COMPLETED.getValue());
				this.updateById(requirementLine);
			}else {
				throw new BaseException(LocaleHandler.getLocaleMsg("存在不是已分配的采购申请行,请检查!"));
			}
		}
	}

	@Override
	public void resubmit(List<Long> requirementLineIds) {
		List<RequirementLine> requirementLines = this.listByIds(requirementLineIds);
		if (CollectionUtils.isNotEmpty(requirementLines)) {
			for (RequirementLine requirementLine : requirementLines) {
				if (requirementLine == null) continue;
				if (!RequirementApplyStatus.RETURNING.getValue().equals(requirementLine.getApplyStatus())) {
					throw new BaseException(LocaleHandler.getLocaleMsg("该状态不为待退回,无法重新提交"));
				} else {
					if (requirementLine.getCeeaSupUserId() != null
							&& requirementLine.getCeeaStrategyUserId() != null &&
							requirementLine.getCeeaPerformUserId() != null) {
						requirementLine.setApplyStatus(RequirementApplyStatus.ASSIGNED.getValue());
						this.updateById(requirementLine);
					} else {
						requirementLine.setApplyStatus(RequirementApplyStatus.UNASSIGNED.getValue());
						this.updateById(requirementLine);
					}
				}
			}
			//重新提交,需要重新自动分配品类规则
			Long requirementHeadId = requirementLines.get(0).getRequirementHeadId();
			RequirementHead byId = iRequirementHeadService.getById(requirementHeadId);
			iRequirementHeadService.assignByDivisionCategory(byId, requirementLines);
		}
	}

	@Override
	@Transactional
	public void batchReturn(RequirementManageDTO requirementManageDTO) {
		LoginAppUser loginAppUser = AppUserUtil.getLoginAppUser();
		List<Long> longList = requirementManageDTO.getRequirementLineIds();
		String rejectReason = requirementManageDTO.getRejectReason();
		List<RequirementLine> requirementLineList = listByIds(longList);
		if (CollectionUtils.isNotEmpty(requirementLineList)) {
			for (RequirementLine requirementLine : requirementLineList) {
				if (requirementLine == null) continue;
				checkBeforeReturn(requirementLine);
				iRequirementLineService.updateById(requirementLine
						.setApplyStatus(RequirementApplyStatus.RETURNING.getValue())
						.setRejectReason(rejectReason)
						.setReturnOperator(loginAppUser.getNickname()));
			}
		}
//		if (CollectionUtils.isNotEmpty(longList)) {
//			for (Long requirementLineId : longList) {
//				iRequirementLineService.updateById(new RequirementLine()
//						.setApplyStatus(RequirementApplyStatus.RETURNING.getValue())
//						.setRequirementLineId(requirementLineId)
//						.setRejectReason(rejectReason));
//			}
//		}
	}

	private void checkBeforeReturn(RequirementLine requirementLine) {
		if (StringUtils.isNotBlank(requirementLine.getFollowFormCode())) {
			throw new BaseException(LocaleHandler.getLocaleMsg("存在后续单据,不可执行退回操作,请检查!"));
		}
	}

	@Override
    @Transactional
    public FSSCResult cancel(RequirermentLineQueryDTO requirermentLineQueryDTO) {
        List<Long> requirementLineIds = requirermentLineQueryDTO.getRequirementLineIds();
        Assert.notEmpty(requirementLineIds, LocaleHandler.getLocaleMsg("未勾选需要取消的需求行,请检查!"));
        Long requirementHeadId = requirermentLineQueryDTO.getRequirementHeadId();
        List<RequirementLine> requirementLines = this.listByIds(requirementLineIds);
		FSSCResult fsscResult = new FSSCResult();
		if (CollectionUtils.isNotEmpty(requirementLines)) {
            LoginAppUser loginAppUser = AppUserUtil.getLoginAppUser();
            RequirementHead requirementHead = iRequirementHeadService.getById(requirementHeadId);
            Assert.notNull(requirementHead, LocaleHandler.getLocaleMsg("requirementHead不能为空"));
            com.midea.cloud.srm.model.pm.ps.http.BgtCheckReqParamDto bgtCheckReqParamDto = new com.midea.cloud.srm.model.pm.ps.http.BgtCheckReqParamDto();
            List<Line> lineList = new ArrayList<>();
            for (RequirementLine requirementLine : requirementLines) {
                if (requirementLine == null) continue;
                //操作前校验
				checkBeforeCancel(requirementHead, requirementLine);
				//获取已下单数量
                BigDecimal ceeaExecutedQuantity = requirementLine.getCeeaExecutedQuantity();
                //获取设置前的申请数量
                BigDecimal requirementQuantity = requirementLine.getRequirementQuantity();
                //重新设置申请数量=已下单数量
                requirementLine.setRequirementQuantity(ceeaExecutedQuantity);
                //重置剩余可下单数量为0
				requirementLine.setOrderQuantity(BigDecimal.ZERO);
                //更新采购申请数和行状态
                this.updateById(requirementLine.setApplyStatus(RequirementApplyStatus.CANCELED.getValue()));

                //头物料大类为服务类,才需转换释放金额对象
				if (CategoryEnum.BIG_CATEGORY_SERVER.getCategoryCode().equals(requirementHead.getCategoryCode())) {
					//释放金额换算   (需求数量-已下单数量)*预算单价
					String documentLineAmount = (requirementQuantity.subtract(ceeaExecutedQuantity))
							.multiply(requirementLine.getNotaxPrice() == null ? BigDecimal.ZERO : requirementLine.getNotaxPrice()).toString();
					//转换需求行
					convertRequirementLine(loginAppUser, requirementHead, lineList, requirementLine, documentLineAmount);
				}
            }
			//头物料大类为服务类,才需释放金额
			if (CategoryEnum.BIG_CATEGORY_SERVER.getCategoryCode().equals(requirementHead.getCategoryCode())) {
				fsscResult = applyRelease(loginAppUser, requirementHead, bgtCheckReqParamDto, lineList);
			}
			if (FSSCResponseCode.ERROR.getCode().equals(fsscResult.getCode())) {
				throw new BaseException(LocaleHandler.getLocaleMsg(fsscResult.getMsg()));
			}
		}
		return fsscResult;
	}

	private void checkBeforeCancel(RequirementHead requirementHead, RequirementLine requirementLine) {
		String applyStatus = requirementLine.getApplyStatus();
		String auditStatus = requirementHead.getAuditStatus();
		if (!RequirementApproveStatus.APPROVED.getValue().equals(auditStatus)) {
			throw new BaseException(LocaleHandler.getLocaleMsg("该单据状态不为已审批,不可操作取消行,请检查!"));
		}
//		if (!RequirementApplyStatus.RETURNING.getValue().equals(applyStatus)) {
//			throw new BaseException(LocaleHandler.getLocaleMsg("存在不为待退回状态行"));
//		}
		if (StringUtils.isNotBlank(requirementLine.getFollowFormCode())) {
			throw new BaseException(LocaleHandler.getLocaleMsg("存在后续单据号的行,无法执行取消操作,请检查!"));
		}
	}

	private FSSCResult applyRelease(LoginAppUser loginAppUser, RequirementHead requirementHead, BgtCheckReqParamDto bgtCheckReqParamDto, List<Line> lineList) {
		FSSCResult fsscResult;//封装预算释放请求实体
		setBgtCheckReqParamDto(loginAppUser, requirementHead, bgtCheckReqParamDto, lineList);
		//开始预算释放
		fsscResult = iFSSCReqService.applyRelease(bgtCheckReqParamDto);
		log.info("请求返回参数:===============================================>" + JsonUtil.entityToJsonStr(fsscResult));
		if ("500".equals(fsscResult.getCode())) {
			throw new BaseException(fsscResult.getMsg());
		}
		return fsscResult;
	}

	/**
	 * 1.修改采购申请行
	 * todo 2.若为服务类，释放所有物料行变更部分对应的预算
	 * @param requirementLineUpdateDTO
	 * @return
	 */
	@Override
	@Transactional
	public void ceeaUpdateNum(RequirementLineUpdateDTO requirementLineUpdateDTO) {
		checkIfRequirementLineUpdate(requirementLineUpdateDTO);

		LoginAppUser loginAppUser = AppUserUtil.getLoginAppUser();
		RequirementLine requirementLine = iRequirementLineService.getById(requirementLineUpdateDTO.getRequirementLineId());
		RequirementHead requirementHead = iRequirementHeadService.getById(requirementLine.getRequirementHeadId());
		Assert.notNull(requirementHead,LocaleHandler.getLocaleMsg("查询不到该采购申请 requirementHeadId = " + requirementLineUpdateDTO.getRequirementHeadId()));
		if(requirementLine.getCeeaFirstQuantity() == null){
			/*未变更过*/
			BigDecimal requirementQuantity = requirementLine.getRequirementQuantity();
			iRequirementLineService.updateById(
					new RequirementLine()
							.setRequirementLineId(requirementLineUpdateDTO.getRequirementLineId())
							.setCeeaFirstQuantity(requirementQuantity)
							.setRequirementQuantity(requirementLineUpdateDTO.getThisUpdateNum()) //需求数量
							.setOrderQuantity(requirementLineUpdateDTO.getThisUpdateNum().subtract(requirementLine.getCeeaExecutedQuantity())) //可下单数量
			);
		}else{
			/*变更过*/
			iRequirementLineService.updateById(
					new RequirementLine()
							.setRequirementLineId(requirementLineUpdateDTO.getRequirementLineId())
							.setRequirementQuantity(requirementLineUpdateDTO.getThisUpdateNum()) //需求数量
							.setOrderQuantity(requirementLineUpdateDTO.getThisUpdateNum().subtract(requirementLine.getCeeaExecutedQuantity())) //可下单数量
			);
		}
		/*PurchaseCategory purchaseCategory = baseClient.queryMaxLevelCategory(new PurchaseCategory().setCategoryId(requirementLine.getCategoryId()));*/
		if(StringUtils.isBlank(requirementHead.getCategoryCode())){
			throw new BaseException(LocaleHandler.getLocaleMsg("物料大类编码为空,requirementHeadId = " + requirementHead.getRequirementHeadId()));
		}
		if(CategoryEnum.BIG_CATEGORY_SERVER.getCategoryCode().equals(requirementHead.getCategoryCode())){
			BgtCheckReqParamDto bgtCheckReqParamDto = new BgtCheckReqParamDto();
			bgtCheckReqParamDto.setPriKey(StringUtil.StringValue(requirementHead.getRequirementHeadId()));  //头id
			bgtCheckReqParamDto.setSourceSystem("SRM");
			bgtCheckReqParamDto.setGroupCode("LGi");
			bgtCheckReqParamDto.setTemplateCode("Budget2020");
			bgtCheckReqParamDto.setDocumentNum(requirementHead.getRequirementHeadNum());
			bgtCheckReqParamDto.setBudgetUseStatus("P");
			bgtCheckReqParamDto.setDocumentCreateBy(loginAppUser.getUsername());
			bgtCheckReqParamDto.setTransferTime(DateUtils.format(new Date(), DateUtils.DATE_FORMAT_19));
			List<Line> lineList = new ArrayList<>();
			lineList.add(
					new Line().setLineId(StringUtil.StringValue(requirementLine.getRequirementLineId()))
						.setDocumentLineNum(StringUtil.StringValue(requirementHead.getRequirementHeadId()) + StringUtil.StringValue(requirementLine.getRequirementLineId()))
						.setDocumentLineAmount(StringUtil.StringValue(requirementLine.getRequirementQuantity().subtract(requirementLineUpdateDTO.getThisUpdateNum())))
						.setTemplateCode("Budget2020")
						.setSegment29(loginAppUser.getCeeaDeptId())
						.setSegment30(requirementLine.getCeeaBusinessSmallCode())
			);
			bgtCheckReqParamDto.setLineList(lineList);
			FSSCResult fsscResult = iFSSCReqService.applyFreeze(bgtCheckReqParamDto);
			if(fsscResult.getCode().equals("500")){
				throw new BaseException(LocaleHandler.getLocaleMsg(fsscResult.getMsg()));
			}
		}
	}


	/**
	 * 手工创建订单-根据采购申请创建
	 * 采购类型为【零价格采购】
	 * 需根据订单供应商，及组织品类关系状态过滤可选择采购申请范围
	 * 无需校验是否存在有效价格；
	 * 订单单价默认为0，无需修改对应订单单价
	 * 零价格订单对应可选的采购类型为 【常规采购】【指定采购】
	 *
	 * 采购类型为【紧急采购】
	 * 根据订单供应商对应组织品类关系过滤可选择采购申请范围
	 * 无需校验是否存在有效价格；若价格库/合同存在有效价格可带出，否则可手工录入对应的订单单价；
	 *
	 * 采购类型为【研发采购】
	 * 根据订单供应商对应组织品类关系过滤可选择采购申请范围
	 * 无需校验是否存在有效价格；若价格库/合同存在有效价格可带出，否则可手工录入对应的订单单价；
	 * 2020-11-5优化
	 * 供应商组织品类关系查询 - 添加供应商条件 todo
	 * @param params
	 * @return
	 */
	@Override
	public PageInfo<RequirementLineVO> listPageForOrder(RequirermentLineQueryDTO params) {
		Assert.notNull(params.getVendorId(),LocaleHandler.getLocaleMsg("供应商id不可为空"));
		Assert.notNull(params.getOrgId(),LocaleHandler.getLocaleMsg("业务实体id不可为空"));
		if(StringUtil.isEmpty(params.getPurchaseType())){
			throw new BaseException(LocaleHandler.getLocaleMsg("采购类型不可为空"));
		}
		/*如果为零价格采购，可选的采购类型为【常规采购】【指定采购】*/
		if(OrderTypeEnum.ZERO_PRICE.getValue().equals(params.getPurchaseType())){
			params.setPurchaseTypeList(new ArrayList<String>(){{
				add(OrderTypeEnum.NORMAL.getValue());
				add(OrderTypeEnum.APPOINT.getValue());
			}});
		}

		/*分页插件自动拼接limit字段,使用代码获取*/
		Integer pageSize = params.getPageSize();
		Integer pageNum = params.getPageNum();
		params.setPageNum(1);
		params.setPageSize(10000);

		LoginAppUser loginAppUser = AppUserUtil.getLoginAppUser();
		params.setCeeaPerformUserId(loginAppUser.getUserId());  //设置采购履行采购员id
		List<RequirementLine> requirementLineList = requirementLineMapper.ceeaListForOrder(params);
		List<RequirementLineVO> result = new ArrayList<>();
		/*查询所有有效价格（通过供应商id过滤）*/
		List<PriceLibrary> priceLibraryAll = inqClient.listAllEffective(new PriceLibrary().setVendorId(params.getVendorId()));
		/*查询所有供应商品类组织关系（可以通过供应商过滤）*/
		List<OrgCategory> orgCategoryList = supplierClient.getOrgCategoryByOrgCategory(new OrgCategory().setOrgId(params.getOrgId()).setCompanyId(params.getVendorId()));
		/*查询出所有有效合同（通过供应商id过滤）*/
		List<Long> orgIds = new ArrayList<>() ;
		orgIds.add(params.getOrgId());
		List<ContractVo> contractVoList = contractClient.listAllEffectiveCM(new ContractItemDto().setMaterialIds(orgIds));
		//合同的合作伙伴
		List<Long> controlHeadIds = contractVoList.stream().map(c->c.getContractHeadId()).collect(Collectors.toList());
		List<ContractPartner> contractPartnerList = contractClient.listAllEffectiveCP(controlHeadIds);

		for(int i=0;i<requirementLineList.size();i++){
			RequirementLine item = requirementLineList.get(i);
			/*可下单数量为0的过滤*/
			if(item.getOrderQuantity() == null || item.getOrderQuantity().compareTo(BigDecimal.ZERO) <= 0){
				continue;
			}

			/*校验数据是否非空*/
			Assert.notNull(item.getMaterialName(),LocaleHandler.getLocaleMsg("requirementLineId为：" + item.getRequirementLineId() + "materialName为空"));
			Assert.notNull(item.getMaterialCode(),LocaleHandler.getLocaleMsg("requirementLineId为：" + item.getRequirementLineId() + "materialCode为空"));
			Assert.notNull(item.getOrgId(),LocaleHandler.getLocaleMsg("requirementLineId为：" + item.getRequirementLineId() + "orgId为空"));
			Assert.notNull(item.getCategoryId(),LocaleHandler.getLocaleMsg("requirementLineId为：" + item.getRequirementLineId() + "orgId为空"));

			/*检验是否有供应商品类组织关系 - 供应商组织品类关系判断方法【认证中，绿牌，黄牌，一次性】*/
			OrgCategory orgCategoryItem = null;
			for(OrgCategory orgCategory : orgCategoryList){
				if(null != orgCategory
						&& null != item.getOrgId() && Objects.equals(orgCategory.getOrgId(),item.getOrgId())
						&& Objects.equals(orgCategory.getCompanyId(),params.getVendorId())
						&& null != item.getCategoryId() && Objects.equals(orgCategory.getCategoryId(), item.getCategoryId())
						&& null != orgCategory.getServiceStatus()
						&& (orgCategory.getServiceStatus().equals(CategoryStatus.VERIFY.name()) || orgCategory.getServiceStatus().equals(CategoryStatus.ONE_TIME.name()) || orgCategory.getServiceStatus().equals(CategoryStatus.GREEN.name()) || orgCategory.getServiceStatus().equals(CategoryStatus.YELLOW.name()))
				){
					orgCategoryItem = orgCategory;
					break;
				}
			}
			if(orgCategoryItem == null){
				log.info("采购申请行：" + JsonUtil.entityToJsonStr(item) + " 无供应商组织品类关系");
				continue;
			}
			log.info("materialCode = " + item.getMaterialCode() + "  materialName = " + item.getMaterialName() + "  vendorName = " + item.getVendorName() + "的供应商组织品类关系为：" + JsonUtil.entityToJsonStr(orgCategoryItem));

			if(params.getPurchaseType().equals(OrderTypeEnum.ZERO_PRICE.getValue())){
				/*零价格采购 - 税率，币种需要手工填写 税率和币种为默认值*/
				RequirementLineVO requirementLineVO = new RequirementLineVO();
				BeanUtils.copyProperties(item,requirementLineVO);
				requirementLineVO.setNoTaxPrice(BigDecimal.ZERO);
				requirementLineVO.setTaxPrice(BigDecimal.ZERO);
				requirementLineVO.setTaxKey("IN 11");
				requirementLineVO.setTaxRate(new BigDecimal(11.00));
				/*requirementLineVO.setCurrencyId(7007437216088064L);
				requirementLineVO.setCurrencyCode("CNY");
				requirementLineVO.setCurrencyName("人民币");*/
				result.add(requirementLineVO);
			}else if(params.getPurchaseType().equals(OrderTypeEnum.URGENT.getValue()) || params.getPurchaseType().equals(OrderTypeEnum.DEVELOP.getValue())){
				/*紧急采购 研发采购*/
				/*判断价格库是否有价格 (订单指定的供应商，在库存组织下有物料编码，物料名称有有效价格)*/
				RequirementLineVO requirementLineVO = new RequirementLineVO();
				BeanUtils.copyProperties(item,requirementLineVO);
				PriceLibrary priceEntity = null;
				for(PriceLibrary priceLibrary : priceLibraryAll){
					//itemDesc,itemCode,orgId,vendorId,orgId,orgnizationId,priceType
					if(null != priceLibrary && StringUtils.isNotBlank(item.getMaterialName()) && item.getMaterialName().equals(priceLibrary.getItemDesc()) &&
							StringUtils.isNotBlank(item.getMaterialCode()) && item.getMaterialCode().equals(priceLibrary.getItemCode()) &&
							item.getOrgId() != null && !ObjectUtils.notEqual(item.getOrgId(),priceLibrary.getCeeaOrgId()) &&
							Objects.equals(priceLibrary.getVendorId(),params.getVendorId()) &&
							item.getOrganizationId() != null && Objects.equals(item.getOrganizationId(),priceLibrary.getCeeaOrganizationId()) &&
							StringUtils.isNotBlank(priceLibrary.getPriceType()) && "STANDARD".equals(priceLibrary.getPriceType())

					){
						/*判断价格库是否某些字段为空，为空则跳过该数据*/
						if(priceLibrary.getNotaxPrice() == null ||
								priceLibrary.getTaxPrice() == null ||
								StringUtils.isBlank(priceLibrary.getTaxKey()) ||
								StringUtils.isBlank(priceLibrary.getTaxRate()) ||
								priceLibrary.getCurrencyId() == null ||
								StringUtils.isBlank(priceLibrary.getCurrencyCode()) ||
								StringUtils.isBlank(priceLibrary.getCurrencyName())
						){
							log.info("requirementHeadNum = " + item.getRequirementHeadNum() + "  rowNum:" + item.getRowNum() + " 价格库某些字段为空:" + JsonUtil.entityToJsonStr(priceLibrary));
						}else{
							priceEntity = priceLibrary;
							requirementLineVO.setNoTaxPrice(priceEntity.getNotaxPrice());
							requirementLineVO.setTaxPrice(priceEntity.getTaxPrice());
							requirementLineVO.setTaxKey(priceEntity.getTaxKey());
							requirementLineVO.setTaxRate(new BigDecimal(priceEntity.getTaxRate()));
							requirementLineVO.setCurrencyId(priceEntity.getCurrencyId());
							requirementLineVO.setCurrencyCode(priceEntity.getCurrencyCode());
							requirementLineVO.setCurrencyName(priceEntity.getCurrencyName());
							/*关联框架合同 业务实体id，库存组织id，供应商id，合同为框架协议*/

//
//							for(ContractVo contractVo:contractVoList){
//								if(null != contractVo && contractVo.getBuId() != null && !ObjectUtils.notEqual(contractVo.getBuId(),item.getOrgId())
//										&& contractVo.getInvId() != null && Objects.equals(contractVo.getInvId(),item.getOrganizationId())
//										&& contractVo.getHeadVendorId() != null && Objects.equals(contractVo.getHeadVendorId(),priceEntity.getVendorId())
//										&& StringUtils.isNotBlank(contractVo.getIsFrameworkAgreement()) && contractVo.getIsFrameworkAgreement().equals("Y")
//								){
//									/*判断合同是否某些字段为空，为空则跳过该数据*/
//									if(StringUtils.isNotBlank(contractVo.getContractCode())){
//										requirementLineVO.setContractCode(contractVo.getContractCode());
//										break;
//									}else{
//										log.info("requirementHeadNum = " + item.getRequirementHeadNum() + "  rowNum:" + item.getRowNum() + " 合同的合同编号为空:" + JsonUtil.entityToJsonStr(contractVo));
//									}
//								}
//							}

							if(!StringUtil.isEmpty(priceLibrary.getContractCode())){
								requirementLineVO.setContractCode(priceLibrary.getContractCode());
							}else{
								//添加比对合作伙伴 逻辑2020年11月10日
								for (ContractVo contractVo : contractVoList) {
									boolean hitContractFlag =
//                                          null != contractVo &&
//                                          contractVo.getBuId() != null &&
//                                          !ObjectUtils.notEqual(contractVo.getBuId(), item.getOrgId())&&
											contractVo.getHeadVendorId() != null &&
//                                          contractVo.getInvId() != null &&
//                                          Objects.equals(contractVo.getInvId(), item.getOrganizationId()) &&
											Objects.equals(contractVo.getHeadVendorId(), priceLibrary.getVendorId()) &&
											StringUtils.isNotBlank(contractVo.getIsFrameworkAgreement()) &&
											StringUtils.isNotBlank(contractVo.getContractCode()) &&
											contractVo.getIsFrameworkAgreement().equals("Y");
									log.info("requirementHeadNum = " + item.getRequirementHeadNum() + "  rowNum:" + item.getRowNum() + " 合同信息为" + JsonUtil.entityToJsonStr(contractVo));

									boolean findContractNum = false;
									for(ContractPartner contractPartner : contractPartnerList){
										if(!contractPartner.getContractHeadId().equals(contractVo.getContractHeadId())) {
											continue;
										}
										if(findContractNum){
											break;
										}

										Long contractPartnerOUId = contractPartner.getOuId();
										//todo
										if(hitContractFlag && Objects.equals(contractVo.getBuId(), contractPartnerOUId)){
											/*判断合同是否某些字段为空，为空则跳过该数据*/
											requirementLineVO.setContractCode(contractVo.getContractCode());
											List<ContractVo> list = requirementLineVO.getContractVoList();
											if(null == list || list.isEmpty()){
												list = new ArrayList<>();
											}
											list.add(contractVo);
											requirementLineVO.setContractVoList(list);
											findContractNum = true;
											break;
										}
									}
								}
							}
						}

					}
				}
				/*当找不到有效价格，去合同里找数据*/
				if(priceEntity == null){
					List<ContractVo> contractVos = new ArrayList<>();
					int index = 0;
					log.info("requirementHeadNum = " + item.getRequirementHeadNum() + "  rowNum:" + item.getRowNum() + "  materialCode:" + item.getMaterialCode() + " materialName:" + item.getMaterialName() + " 无有效价格");
					/*判断是否有合同物料 条件为 订单指定的供应商，在库存组织下有物料编码，物料名称有 有效合同物料 的非框架合同*/
					for(ContractVo contractVo:contractVoList){
						if(null != contractVo && contractVo.getBuId() != null && !ObjectUtils.notEqual(contractVo.getBuId(),item.getOrgId())
								&& contractVo.getMaterialCode() != null && contractVo.getMaterialCode().equals(item.getMaterialCode())
								&& contractVo.getMaterialName() != null && contractVo.getMaterialName().equals(item.getMaterialName())
								&& Objects.equals(contractVo.getHeadVendorId(),params.getVendorId())
								&& contractVo.getInvId() != null && Objects.equals(contractVo.getInvId(),item.getOrganizationId())
								/*&& StringUtils.isNotBlank(contractVo.getIsFrameworkAgreement()) && contractVo.getIsFrameworkAgreement().equals("N")*/
						){
							/*判断合同是否某些字段为空，为空则跳过该数据*/
 							if(contractVo.getTaxedPrice() == null ||
									StringUtils.isBlank(contractVo.getTaxKey()) ||
									contractVo.getTaxRate() == null ||
									contractVo.getCurrencyId() == null ||
									StringUtils.isBlank(contractVo.getCurrencyCode()) ||
									StringUtils.isBlank(contractVo.getCurrencyName())
							){
								log.info("requirementHeadNum = " + item.getRequirementHeadNum() + "  rowNum:" + item.getRowNum() + " 合同某些字段为空:" + JsonUtil.entityToJsonStr(contractVo));
							}else{
								/*计算未税单价*/
								BigDecimal taxRate = contractVo.getTaxRate().divide(new BigDecimal(100),8,BigDecimal.ROUND_HALF_UP);
								BigDecimal untaxedPrice = contractVo.getTaxedPrice().divide((new BigDecimal(1).add(taxRate)),2,BigDecimal.ROUND_HALF_UP);
								contractVo.setUntaxedPrice(untaxedPrice);
								index++;
								contractVos.add(contractVo);
							}
						}else{
							log.info("requirementHeadNum = " + item.getRequirementHeadNum() + "  rowNum:" + item.getRowNum() + "  materialCode:" + item.getMaterialCode() + " materialName:" + item.getMaterialName() + " 合同数据不完整：" + JsonUtil.entityToJsonStr(contractVo));
						}

					}
					System.out.println("进来了" + index + "次");
					if(CollectionUtils.isNotEmpty(contractVos)){
						ContractVo contractVo = contractVos.get(0);
						requirementLineVO.setNoTaxPrice(contractVo.getUntaxedPrice());
						requirementLineVO.setTaxPrice(contractVo.getTaxedPrice());
						requirementLineVO.setTaxKey(contractVo.getTaxKey());
						requirementLineVO.setTaxRate(contractVo.getTaxRate());
						requirementLineVO.setCurrencyId(contractVo.getCurrencyId());
						requirementLineVO.setCurrencyCode(contractVo.getCurrencyCode());
						requirementLineVO.setCurrencyName(contractVo.getCurrencyName());
						requirementLineVO.setContractCode(contractVo.getContractCode());
						requirementLineVO.setContractVoList(contractVos);
					}else{
						log.info("requirementHeadNum = " + item.getRequirementHeadNum() + "  rowNum:" + item.getRowNum() + "  materialCode:" + item.getMaterialCode() + " materialName:" + item.getMaterialName() + " 无有效合同物料");
					}
				}
				result.add(requirementLineVO);
			}else{
				throw new BaseException(LocaleHandler.getLocaleMsg("采购类型只能选择【零价格采购】【紧急采购】【研发采购】"));
			}
		}
		PageInfo<RequirementLineVO> requirementLineResult = PageUtil.pagingByFullData(pageNum,pageSize,result);

		/*校验物料是否有物料分类(有些物料没有物料分类)*/
		/*List<Long> materialIds = requirementLineResult.getList().stream().map(item -> item.getMaterialId()).collect(Collectors.toList());
		if(CollectionUtils.isNotEmpty(materialIds)){
			List<MaterialItem> materialItemList = baseClient.listMaterialByIdBatch(materialIds);
			if(CollectionUtils.isNotEmpty(materialItemList)){
				for(MaterialItem materialItem:materialItemList){
					if(Objects.isNull(materialItem.getCategoryId())){
						throw new BaseException(LocaleHandler.getLocaleMsg("[" + materialItem.getMaterialCode() + "][" + materialItem.getMaterialName() + "]没有维护品类，请先进行维护"));
					}
				}
			}
		}*/

		/*设置物料大类*/
		List<Long> materialIds = requirementLineResult.getList().stream().map(item -> item.getMaterialId()).collect(Collectors.toList());
		List<MaterialMaxCategoryVO> materialMaxCategoryVOList = baseClient.queryCategoryMaxCodeByMaterialIds(materialIds);
		Map<Long,MaterialMaxCategoryVO> map = materialMaxCategoryVOList.stream().collect(Collectors.toMap(e->e.getMaterialId(),e->e));
		for(int i=0;i<requirementLineResult.getList().size();i++){
			RequirementLineVO requirementLineVO = requirementLineResult.getList().get(i);
			MaterialMaxCategoryVO materialMaxCategoryVO = map.get(requirementLineVO.getMaterialId());
			if(materialMaxCategoryVO != null){
				requirementLineVO.setBigCategoryId(materialMaxCategoryVO.getCategoryId());
				requirementLineVO.setBigCategoryCode(materialMaxCategoryVO.getCategoryCode());
			}
		}

		/*设置可用税率*/
		for(int i=0;i<requirementLineResult.getList().size();i++){
			RequirementLineVO requirementLineVO = requirementLineResult.getList().get(i);
			List<PurchaseTax> purchaseTaxList = baseClient.queryTaxByItemForOrder(requirementLineVO.getMaterialId());
			requirementLineVO.setPurchaseTaxList(purchaseTaxList);
		}
		return requirementLineResult;
	}



	private void checkIfRequirementLineUpdate(RequirementLineUpdateDTO requirementLineUpdateDTO){
		RequirementLine requirementLine = iRequirementLineService.getById(requirementLineUpdateDTO.getRequirementLineId());
		Assert.notNull(requirementLine,LocaleHandler.getLocaleMsg("查询不到采购申请行"));
		RequirementHead requirementHead = iRequirementHeadService.getById(requirementLine.getRequirementHeadId());
		Assert.notNull(requirementHead,LocaleHandler.getLocaleMsg("查询不到采购申请"));
		if(!requirementHead.getAuditStatus().equals(RequirementApproveStatus.APPROVED.getValue())){
			Assert.notNull(null,LocaleHandler.getLocaleMsg("采购申请状态不为已审批"));
		}
		if(requirementLineUpdateDTO.getThisUpdateNum().compareTo(requirementLine.getRequirementQuantity()) == 1){
			Assert.notNull(null,LocaleHandler.getLocaleMsg("修改后的数量不可大于原需求数量"));
		}
		if(requirementLineUpdateDTO.getThisUpdateNum().compareTo(requirementLine.getCeeaExecutedQuantity()) == -1){
			Assert.notNull(null,LocaleHandler.getLocaleMsg("修改后的数量不可小于已执行数量"));
		}
		/*校验物料行是否存在新建或执行过程中的寻源单据*/
		if(StringUtils.isNoneEmpty(requirementLine.getFollowFormCode())){
			throw new BaseException(LocaleHandler.getLocaleMsg("物料行存在新建或执行过程中的寻源单据"));
		}
	}

	private void setBgtCheckReqParamDto(LoginAppUser loginAppUser, RequirementHead requirementHead, com.midea.cloud.srm.model.pm.ps.http.BgtCheckReqParamDto bgtCheckReqParamDto, List<Line> lineList) {
		bgtCheckReqParamDto.setPriKey(requirementHead.getRequirementHeadId().toString());
		bgtCheckReqParamDto.setSourceSystem(DataSourceEnum.NSRM_SYS.getKey());
		bgtCheckReqParamDto.setGroupCode("LGi");
		bgtCheckReqParamDto.setTemplateCode("Budget2020");
		bgtCheckReqParamDto.setDocumentNum(requirementHead.getRequirementHeadNum());
		bgtCheckReqParamDto.setBudgetUseStatus(BudgetUseStatus.P.name());
		bgtCheckReqParamDto.setDocumentCreateBy(StringUtil.StringValue(loginAppUser.getCeeaEmpNo()));
		bgtCheckReqParamDto.setLineList(lineList);
		bgtCheckReqParamDto.setTransferTime(new Date().toString());
	}

    private void convertRequirementLine(LoginAppUser loginAppUser, RequirementHead requirementHead, List<Line> lineList, RequirementLine requirementLine, String documentLineAmount) {
        Line line = new Line();
        line.setLineId(requirementLine.getRequirementLineId().toString());
        line.setDocumentLineNum(requirementHead.getRequirementHeadId().toString() + requirementLine.getRequirementLineId().toString());
        line.setTemplateCode("Budget2020");
        line.setDocumentLineAmount(documentLineAmount);
		//如果需求部门为空，传申请部门,否则传需求部门 modify by chensl26 20201215
		String segment29 = requirementHead.getDemandDepartmentId();
		if (StringUtils.isBlank(requirementHead.getDemandDepartmentName()) && StringUtils.isBlank(requirementHead.getDemandDepartmentId())) {
			segment29 = loginAppUser.getCeeaDeptId();
		}
        line.setSegment29(segment29);
        line.setSegment30(requirementHead.getCeeaBusinessSmallCode());
        lineList.add(line);
    }

	@Override
	public void importMaterialItemModelDownload(HttpServletResponse response) throws Exception {
		String fileName = "采购需求物料明细导入模版";
		List<RequirementLineImport> requirementLineImports  = new ArrayList<>();
		OutputStream outputStream = EasyExcelUtil.getServletOutputStream(response, fileName);
		List<Integer> column = Arrays.asList(0,1);
		HashMap<Integer,String> titleConfig = new HashMap();
		titleConfig.put(0 , "必填，需与ERP物料编码保持一致");
		titleConfig.put(1 , "必填，维护本物料行对应的申请数量");
		titleConfig.put(2 , "非必填，服务类时必填");
		titleConfig.put(3 , "必填，日期格式必须为YYYY-MM-DD或YYYY/MM/DD");
		titleConfig.put(4 , "非必填，若为指定采购时可维护对应供应商编码");
		titleConfig.put(5 , "非必填，若为指定采购时可维护对应供应商名称");
		titleConfig.put(6 , "非必填");
		titleConfig.put(7 , "非必填");
		//泛型有问题
		HashMap<Integer, String[]> dropDownMap = new HashMap<>();
		// 指定下拉框
		String requestManCode ="DMAND_LINE_REQUEST";	//需求人字典编码
		List<String> requestList = baseClient.listAllByDictCode(requestManCode)
				.stream().map(DictItemDTO::getDictItemName).distinct().collect(Collectors.toList());

		// 测试多sheel导出
		ExcelWriter excelWriter = EasyExcel.write(outputStream).build();
		// 头的策略
		WriteCellStyle headWriteCellStyle = new WriteCellStyle();
		// 单元格策略
		WriteCellStyle contentWriteCellStyle = new WriteCellStyle();
		// 初始化表格样式
		WriteSheet test1 = EasyExcel.writerSheet(0, fileName).head(RequirementLineImport.class).build();
		List<List<String>> head = new ArrayList<>();
		HorizontalCellStyleStrategy horizontalCellStyleStrategy = new HorizontalCellStyleStrategy(headWriteCellStyle, contentWriteCellStyle);
		WriteSheet test2 = EasyExcel.writerSheet(1, "需求人清单").head(head).
				registerWriteHandler(horizontalCellStyleStrategy).build();

		List<List<String>> requestManData = new ArrayList<>();
		for(int i = 0 ; i< requestList.size() ; i++){
			List<String> temp = new ArrayList<>();
			temp.add(requestList.get(i));
			requestManData.add(temp);
		}
		excelWriter.write(requirementLineImports, test1).write(requestManData, test2);
		excelWriter.finish();

//		EasyExcelUtil.writeExcelWithModel(outputStream, requirementLineImports, RequirementLineImport.class, fileName ,titleHandler);
	}



	@Override
	@Transactional
	public Map<String, Object> importExcel(String requirementHeadId ,MultipartFile file, Fileupload fileupload) throws Exception {
		RequirementHead requirementHead = iRequirementHeadService.getById(requirementHeadId);
		//校验附件基本参数（不涉及业务）
		EasyExcelUtil.checkParam(file,fileupload);
		//校验头部信息参数
		checkImportParam(requirementHead);
		// 读取excel数据并封装
		List<RequirementLineImport> requirementLineImports = this.readData(file);
		List<RequirementLine> requirementLines = new ArrayList<>();
		Map<String, Object> result = new HashMap<>();
		if(CollectionUtils.isNotEmpty(requirementLineImports)){
			Long d1 = System.currentTimeMillis();
			boolean errorFlag = importCheckData(requirementHead , requirementLineImports , requirementLines);
			log.info("==============导入校验花费时间:"+(System.currentTimeMillis() - d1)+"ms===============");
			if(errorFlag){
				// 有报错
				fileupload.setFileSourceName("采购申请物料明细导入报错");
				Fileupload fileuploadResult = EasyExcelUtil.uploadErrorFile(fileCenterClient, fileupload,
						requirementLineImports, RequirementLineImport.class, file.getName(), file.getOriginalFilename(), file.getContentType());
				result.put("status", YesOrNo.NO.getValue());
				result.put("message","error");
				result.put("fileuploadId",fileuploadResult.getFileuploadId());
				result.put("fileName",fileuploadResult.getFileSourceName());
				return result;
			}
				// 开始导入
			if(CollectionUtils.isNotEmpty(requirementLines)){
				for (RequirementLine requirementLine : requirementLines){
					save(requirementLine);
				}
			}
		}
		result.put("status", YesOrNo.YES.getValue());
		result.put("message","success");
		return result;
	}

	/**
	 * 导入接口参数校验
	 * @param requirementHead
	 */
	private void checkImportParam(RequirementHead requirementHead){
		Assert.notNull(requirementHead.getOrgId() , "业务实体ID不能为空");
		Assert.notNull(requirementHead.getOrganizationId() , "库存组织ID不能为空");
		Assert.notNull(requirementHead.getCategoryId(), "物料大类ID不能为空");
		if(CategoryEnum.BIG_CATEGORY_SERVER.getCategoryCode().equals(requirementHead.getCategoryCode())){
			Assert.notNull(requirementHead.getCeeaBusinessSmallCode() ,"物料大类为服务类时业务小类不能为空");
		}
	}


	/**
	 * 采购申请-物料导入-校验数据
	 * @param requirementHead			采购申请头部信息
	 * @param requirementLineImports	excel内容
	 * @param requirementLines			需要插入数据库的信息
	 * @return
	 */
	public boolean importCheckData(RequirementHead requirementHead,
								   List<RequirementLineImport> requirementLineImports,
								   List<RequirementLine> requirementLines) {
		boolean errorFlag = false;
		int roundSize = 1000;
		int importSize = requirementLineImports.size();
		int round = importSize / roundSize;
		if (importSize % roundSize > 0) {
			round += 1;
		}
		round = round == 0 ? 1 : round;
		//获取库存组织信息
		Organization organization = baseClient.get(requirementHead.getOrganizationId());
		//需求人信息
		String requestManCode ="DMAND_LINE_REQUEST";	//需求人字典编码
		Set<String> requestManSet = baseClient.listAllByDictCode(requestManCode)
				.stream().map(DictItemDTO::getDictItemName).collect(Collectors.toSet());

		for( int i = 0; i < round ; i++){
			int startIndex = i * roundSize;
			int endIndex = (i+1) * roundSize > importSize ? importSize : (i+1) * roundSize;
			List<RequirementLineImport> curLineImport = requirementLineImports.subList(startIndex , endIndex);

			//获取供应商信息
			List<String> companyCodes = curLineImport.stream().map(o -> o.getVendorCode())
					.collect(Collectors.toList());
			List<CompanyInfo> companyInfoList = supplierClient.listCompanyByCodes(companyCodes);
			Map<String , CompanyInfo> companyInfoMap = companyInfoList.stream().collect(
					Collectors.toMap(
							c -> c.getCompanyCode(),
							c -> c,
							(c1 , c2) -> c1
					)
			);
			//获取物料信息
			List<String> materialCodes = curLineImport.stream().map(o -> o.getMaterialCode())
					.collect(Collectors.toList());
			ItemCodeUserPurchaseDto purchaseDto = new ItemCodeUserPurchaseDto();
			purchaseDto.setOrgId(requirementHead.getOrgId());
			purchaseDto.setInvId(requirementHead.getOrganizationId());
			purchaseDto.setItemCodes(materialCodes);
			Map<String, String> userPurchaseMap = baseClient.queryItemIdUserPurchase(purchaseDto);
			List<MaterialItem> materialItemList = baseClient.listMaterialByCodeBatch(materialCodes);
			Map<String , MaterialItem> materialItemMap = materialItemList.stream().collect(
					Collectors.toMap(
							m -> m.getMaterialCode(),
							m -> m,
							(m1,m2)->m1
					)
			);
			//获取物料组织信息
			List<Long> materialIds = materialItemList.stream().map(o -> o.getMaterialId()).collect(Collectors.toList());
			Map<String , MaterialOrg> materialOrgMap = baseClient.listMaterialOrgByMaterialIds(materialIds)
				.stream().collect(
					Collectors.toMap(
							m -> new StringBuilder().append(m.getMaterialId()).append(m.getOrganizationId()).toString(),
							m -> m,
							(m1 , m2)->m1
					)
			);
			//获取价格清单
			NetPriceQueryDTO priceDto = new NetPriceQueryDTO().setOrganizationId(requirementHead.getOrganizationId());
			Map<String , PriceLibrary> priceLibraryMap = inqClient.listPriceLibrary(priceDto).stream().collect(
					Collectors.toMap(
							p -> new StringBuilder()
										.append(p.getItemCode())
										.append(p.getCeeaOrgId())
										.append(p.getCeeaOrganizationId()).toString(),
							p -> p,
							(p1 ,p2) -> p1
					)
			);
			//获取组织品类信息
			List<Long> companyIds = companyInfoList.stream().map(o -> o.getCompanyId()).collect(Collectors.toList());
			Map<String , OrgCategory> orgCategoryMap = supplierClient.listOrgCategoryByCompanyIds(companyIds).stream().collect(
					Collectors.toMap(
							o -> new StringBuilder().append(o.getCategoryId()).append(o.getCompanyId()).toString(),
							o -> o,
							(o1 , o2) -> o1
					)
			);

			for(int row = 0; row < curLineImport.size(); row++){
				RequirementLineImport requirementLineImport = curLineImport.get(row);
				RequirementLine requirementLine = new RequirementLine();
				boolean rowErrorFlag = false;
				StringBuffer errorMsg = new StringBuffer();
				BeanUtils.copyProperties(requirementHead , requirementLine);
				//校验必填字典
				if(StringUtils.isBlank(requirementLineImport.getMaterialCode())){
					errorFlag = true;
					rowErrorFlag = true;
					errorMsg.append("物料编码不能为空;");
				}
				if(Objects.isNull(requirementLineImport.getOrderQuantity())){
					errorFlag = true;
					rowErrorFlag = true;
					errorMsg.append("申请数量不能为空;");
				}
				if(Objects.isNull(requirementLineImport.getRequirementDate()) ||
						StringUtils.isBlank(requirementLineImport.getRequirementDate())){
					errorFlag = true;
					rowErrorFlag = true;
					errorMsg.append("需求日期不能为空;");
				}
				// 物料编码
				String materialCode = requirementLineImport.getMaterialCode();
				if(StringUtil.notEmpty(materialCode)){
					materialCode = materialCode.trim();
					String purchase = userPurchaseMap.get(materialCode);
					if(!YesOrNo.YES.getValue().equals(purchase)){
						errorFlag = true;
						rowErrorFlag = true;
						errorMsg.append("该库存组织下不允许采购或编码已淘汰;");
					}
				}

				if(rowErrorFlag){
					requirementLineImport.setErrorMsg(errorMsg.toString());
					continue;
				}

				MaterialItem materialItem = materialItemMap.get(requirementLineImport.getMaterialCode());
				if(Objects.isNull(materialItem.getCategoryId()) ||
						Objects.isNull(materialItem.getCategoryCode())){
					errorFlag = true;
					errorMsg.append("该物料没有SRM分类，请在ERP维护;");
					requirementLineImport.setErrorMsg(errorMsg.toString());
					continue;
				}
				if(Objects.isNull(materialItem)){
					errorFlag = true;
					errorMsg.append("当前物料不存在;");
					requirementLineImport.setErrorMsg(errorMsg.toString());
					continue;
				}

				//需求人
				if(StringUtils.isNotBlank(requirementLineImport.getDmandLineRequest()) &&
					!requestManSet.contains(requirementLineImport.getDmandLineRequest())){
					errorFlag = true;
					errorMsg.append("需求人不存在；");
				}

				//查找物料对应的 【采购分类(物料小类)】是否在物料大类下
				if(materialItem.getStruct().indexOf(requirementHead.getCategoryId().toString()) < 0){
					errorFlag = true;
					errorMsg.append("物料所属采购分类与当前采购申请物料大类不符；");
				}
				// 查找物料是否在库存组织下
				requirementLine.setCategoryId(materialItem.getCategoryId());
				requirementLine.setCategoryCode(materialItem.getCategoryCode());
				requirementLine.setCategoryName(materialItem.getCategoryName());
				requirementLine.setCeeaIfDirectory(YesOrNo.NO.getValue());
				MaterialOrg materialOrg =  materialOrgMap.get(new StringBuilder().append(materialItem.getMaterialId()).append(requirementHead.getOrganizationId()).toString());
				boolean materialInOrgFlag = Objects.nonNull(materialOrg);
				if(materialInOrgFlag){
					boolean noPurchase = YesOrNo.NO.getValue().equals(materialOrg.getUserPurchase());
					if(noPurchase){
						errorFlag = true;
						errorMsg.append("物料无效，非采购类型;");
					}

					//按“物料编码+物料描述+业务实体+库存组织+上架状态（已上架）+有效期”在价格库中查找是否有记录，
					// 如果有，导入后，是否目录化标记为是；如果没有，仍为空；--2020-10-30统一目录化为否
					PriceLibrary priceParam = priceLibraryMap.get(
							new StringBuilder().append(materialItem.getMaterialCode())
									.append(requirementHead.getOrgId())
									.append(requirementHead.getOrganizationId()
									).toString()
					);
					if(Objects.nonNull(priceParam)){

						requirementLineImport.setVendorCode(priceParam.getVendorCode());
						requirementLineImport.setNotaxPrice(priceParam.getTaxPrice());
						requirementLineImport.setVendorName(priceParam.getVendorName());
					}
				}else{
					errorFlag = true;
					errorMsg.append("库存组织下不存在该物料请检查物料名称和物料编号;");
				}

				//指定供应商必须为系统中，供应商与当前物料品类关系为非红牌的供应商；
				if(StringUtils.isNotBlank(requirementLineImport.getVendorCode())){
					CompanyInfo companyInfo = companyInfoMap.get(requirementLineImport.getVendorCode());
					//supplierClient.getCompanyInfoByParam(new CompanyInfo().setCompanyCode(requirementLineImport.getVendorCode()));
					AtomicBoolean redCardFlag = new AtomicBoolean(false);
					if(Objects.nonNull(companyInfo)){
						//读取供应商名称
						requirementLineImport.setVendorName(companyInfo.getCompanyName());
						OrgCategory orgCategory = orgCategoryMap.get(new StringBuilder()
								.append(materialItem.getCategoryId())
								.append(companyInfo.getCompanyId()));
						if(Objects.nonNull(orgCategory) && CategoryStatus.RED.toString().equals(orgCategory.getServiceStatus())){
							redCardFlag.set(true);
						}
					}
					if(redCardFlag.get()){
						errorFlag = true;
						errorMsg.append("当前供应商为红牌供应商，不符合要求；");
					}
				}

				//需求时间格式校验
				LocalDate requirementDate = null;
				if (StringUtils.isNotBlank(requirementLineImport.getRequirementDate())) {
					try {
						Date date = DateUtil.parseDate(requirementLineImport.getRequirementDate());
						requirementDate = DateUtil.dateToLocalDate(date);
					} catch (Exception e) {
						errorFlag = true;
						errorMsg.append("需求时间格式错误；");
					}
				}
				//需求时间不能大于当前时间
				if(requirementDate.compareTo(LocalDate.now()) < 0){
					errorFlag = true;
					errorMsg.append("需求日期不能小于当前日期");
				}
				//交货地点为 库存促织收货地点
				//baseClient.getOrganizationByParam(new Organization().setOrganizationId(requirementHead.getOrganizationId()));
				if(Objects.isNull(organization)){
					errorFlag = true;
					errorMsg.append("库存组织不存在；");
				}
				if(!errorFlag){
					requirementLine.setMaterialCode(materialItem.getMaterialCode())
							.setMaterialId(materialItem.getMaterialId())
							.setMaterialName(materialItem.getMaterialName())
							.setApplyStatus(RequirementApplyStatus.UNASSIGNED.getValue())
							// 交货地点
//							.setCeeaDeliveryPlace(Objects.isNull(organization)? "" : organization.getOrganizationSite())
							.setOrderQuantity(requirementLineImport.getOrderQuantity())
							.setRequirementQuantity(requirementLineImport.getOrderQuantity())
							.setUnitCode(materialItem.getUnit())
							.setUnit(materialItem.getUnitName())
							.setComments(requirementLineImport.getComments())
							//指定供应商名称
							.setVendorCode(requirementLineImport.getVendorCode())
							.setVendorName(requirementLineImport.getVendorName())
							.setRequirementLineId(IdGenrator.generate())
							.setDmandLineRequest(requirementLineImport.getDmandLineRequest())
							.setRequirementDate(requirementDate);

					if(Objects.isNull(requirementLineImport.getNotaxPrice())){
						requirementLine.setTotalAmount(new BigDecimal(0));
						requirementLine.setNotaxPrice(new BigDecimal(0));
					}else{
						requirementLine.setTotalAmount(requirementLineImport.getNotaxPrice().multiply(requirementLineImport.getOrderQuantity()));
						requirementLine.setNotaxPrice(requirementLineImport.getNotaxPrice());
					}
				}
				requirementLineImport.setErrorMsg(errorMsg.toString());
				requirementLines.add(requirementLine);
			}
		}
		return errorFlag;
	}



	/**
	 * 读取excel内容
	 * @param file
	 * @return
	 */
	private List<RequirementLineImport> readData(MultipartFile file) {
		List<RequirementLineImport> requirementLineImports = null;
		try {
			// 获取输入流
			InputStream inputStream = file.getInputStream();
			// 数据收集器
			AnalysisEventListenerImpl<RequirementLineImport> listener = new AnalysisEventListenerImpl<>();
			ExcelReader excelReader = EasyExcel.read(inputStream,listener).build();
			// 第一个sheet读取类型
			ReadSheet readSheet = EasyExcel.readSheet(0).head(RequirementLineImport.class).build();
			// 开始读取第一个sheet
			excelReader.read(readSheet);
			requirementLineImports = listener.getDatas();
		} catch (IOException e) {
			throw new BaseException("excel解析出错");
		}
		return requirementLineImports;
	}
}
