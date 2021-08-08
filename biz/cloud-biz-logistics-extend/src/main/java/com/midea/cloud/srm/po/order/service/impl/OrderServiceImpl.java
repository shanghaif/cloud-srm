package com.midea.cloud.srm.po.order.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.midea.cloud.common.constants.RepushConst;
import com.midea.cloud.common.constants.SequenceCodeConstant;
import com.midea.cloud.common.enums.OrgStatus;
import com.midea.cloud.common.enums.YesOrNo;
import com.midea.cloud.common.enums.order.OrderStatus;
import com.midea.cloud.common.enums.pm.po.*;
import com.midea.cloud.common.enums.pm.pr.requirement.RelatedDocumentsEnum;
import com.midea.cloud.common.enums.pm.ps.CategoryEnum;
import com.midea.cloud.common.enums.review.CategoryStatus;
import com.midea.cloud.common.exception.BaseException;
import com.midea.cloud.common.utils.*;
import com.midea.cloud.common.utils.redis.RedisUtil;
import com.midea.cloud.component.context.i18n.LocaleHandler;
import com.midea.cloud.repush.service.RepushHandlerService;
import com.midea.cloud.srm.feign.api.ApiClient;
import com.midea.cloud.srm.feign.base.BaseClient;
import com.midea.cloud.srm.feign.contract.ContractClient;
import com.midea.cloud.srm.feign.file.FileCenterClient;
import com.midea.cloud.srm.feign.rbac.RbacClient;
import com.midea.cloud.srm.feign.supcooperate.SupcooperateClient;
import com.midea.cloud.srm.feign.supplier.SupplierClient;
import com.midea.cloud.srm.model.api.interfacelog.dto.InterfaceLogDTO;
import com.midea.cloud.srm.model.base.material.MaterialItem;
import com.midea.cloud.srm.model.base.organization.entity.Location;
import com.midea.cloud.srm.model.base.organization.entity.Organization;
import com.midea.cloud.srm.model.base.organization.entity.OrganizationRelation;
import com.midea.cloud.srm.model.base.purchase.entity.PurchaseCategory;
import com.midea.cloud.srm.model.base.repush.entity.RepushStatus;
import com.midea.cloud.srm.model.base.soap.DataSourceEnum;
import com.midea.cloud.srm.model.base.soap.erp.erpacceptpurchaseordersoapbiz.*;
import com.midea.cloud.srm.model.cm.contract.dto.ContractMaterialDTO;
import com.midea.cloud.srm.model.pm.po.dto.ReturnOrderDTO;
import com.midea.cloud.srm.model.pm.pr.documents.entity.SubsequentDocuments;
import com.midea.cloud.srm.model.pm.pr.requirement.entity.RequirementLine;
import com.midea.cloud.srm.model.pm.soap.order.*;
import com.midea.cloud.srm.model.rbac.po.entity.PoAgent;
import com.midea.cloud.srm.model.rbac.user.entity.LoginAppUser;
import com.midea.cloud.srm.model.supplier.info.entity.CompanyInfo;
import com.midea.cloud.srm.model.supplier.info.entity.OrgCategory;
import com.midea.cloud.srm.model.supplier.info.entity.OrgInfo;
import com.midea.cloud.srm.model.suppliercooperate.order.dto.OrderDetailDTO;
import com.midea.cloud.srm.model.suppliercooperate.order.dto.OrderSaveRequestDTO;
import com.midea.cloud.srm.model.suppliercooperate.order.dto.WarehousingReturnDetailRequestDTO;
import com.midea.cloud.srm.model.suppliercooperate.order.entry.*;
import com.midea.cloud.srm.model.workflow.entity.SrmFlowBusWorkflow;
import com.midea.cloud.srm.po.PoSoapUrl;
import com.midea.cloud.srm.po.order.enums.CategoryTypeEnum;
import com.midea.cloud.srm.po.order.service.IOrderService;
import com.midea.cloud.srm.po.order.soap.service.ErpCancelPurchaseOrderSoapBizPtt;
import com.midea.cloud.srm.po.soap.erpacceptpurchaseordersoapbiz.ErpAcceptPurchaseOrderSoapBizPttBindingQSService;
import com.midea.cloud.srm.po.workflow.OrderFlow;
import com.midea.cloud.srm.pr.documents.service.ISubsequentDocumentsService;
import com.midea.cloud.srm.pr.requirement.service.IRequirementLineService;
import lombok.Data;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.cxf.interceptor.LoggingInInterceptor;
import org.apache.cxf.interceptor.LoggingOutInterceptor;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 *
 *
 *
 * <pre>
 * 采购订单头
 * </pre>
 *
 * @author zhizhao1.fan@meicloud.com
 * @version 1.00.00
 *
 *          <pre>
 *  修改记录
 *  修改后版本:
 *  修改人：
 *  修改日期: 2020年5月19日下午6:24:04
 *  修改内容:
 *          </pre>
 */
@Slf4j
@Service
public class OrderServiceImpl implements IOrderService {

	@Autowired
	private SupcooperateClient supcooperateClient;

	@Autowired
	private SupplierClient supplierClient;

	@Autowired
	private BaseClient baseClient;

	@Autowired
	private ContractClient contractClient;

	@Autowired
	private FileCenterClient fileCenterClient;

	@Autowired
	private IRequirementLineService iRequirementLineService;

	@Autowired
	private OrderFlow orderFlow;

	@Autowired
	private ISubsequentDocumentsService iSubsequentDocumentsService;

	@Autowired
	private RepushHandlerService repushHandlerService;

	@Value("${ERP_USER.erpName}")
	private String erpName;
	@Value("${ERP_USER.erpPassword}")
	private String erpPassword;
	@Value("${SOAP_URL.ERP_PURCHASE_URL}")
	private String erpPurchaseUrl;

	@Value("${ERP_USER.erpName2}")
	private String erpName2;
	@Value("${ERP_USER.erpPassword2}")
	private String erpPassword2;
	@Value("${SOAP_URL.URL_EDIT_PURCHASE_ORDER}")
	private String erpPurchaseUrl2;

	@Autowired
	private RbacClient rbacClient;
	@Autowired
	private RedisUtil redisUtil;
	@Resource
	private ApiClient apiClient;

	/**
	 * 1.必填的校验
	 *   (1)物料行不可为空
	 *   (2)订单数量为0提示不可提交
	 * 2.物料小类的校验
	 *   (1)校验同一订单中 检验所有小类同步erp是否相同
	 *   (2)小类为701101、701201、701301、701501、701502、701503、701504、701505、701506、701507 的不传ERP ，这些类别可以在同一订单，但不能混杂这里以外的小类在相同订单中
	 * 3.物料大类的校验
	 *   (1)允许同时存在生产材料和备品备件的物料明细，存在生产材料或备品备件的采购订单，不允许存在其他物料大类的物料明细
	 *   (2)采购订单，服务类和非服务类不能共存
	 * 4.采购类型的校验
	 *   (1)如果是紧急采购且订单为非标准,查看含税总金额是否大于两万，大于两万报错
	 *   (2)如果是研发采购且订单为非标准，订单总金额不可大于5000
	 * 5.业务实体，库存组织的校验
	 *  (1)判断库存组织是否属于该业务实体下
	 *  (2)判断库存组织是否相等
	 *  (3)判断 收货地址、收单地址校验：地址在订单行中的库存组织下时，允许提交订单
	 * 6.物料中类的校验（去掉）
	 *  (1)需限制订单行中类是7011,7022,7015的订单不传ERP，在做订单时就要控制不能和其他分类的行做一个订单里
	 * 7.供应商组织品类关系与是否样品小批量订单的认证
	 *  (1)供应商的行是否有认证中的，如果有认证中，必须是Y (供应商组织品类状态 认证中) 认证中供应商只能下样品小批量订单
	 *  (2)如果供应商组织品类关系 又有认证中，又有绿牌，报错 - 认证中品类不能与合格品类共同下订单，请拆分订单
	 *
	 *
	 * @param order
	 * @param detailList
	 * @param attachList
	 * @param maxPurchaseCategoryList
	 * @param minPurchaseCategoryList
	 */
	private void checkAddOrder(Order order, List<OrderDetail> detailList, List<OrderAttach> attachList,List<PurchaseCategory> maxPurchaseCategoryList,List<PurchaseCategory> minPurchaseCategoryList) {

		/**
		 * 1.必填的校验
		 */
		/*物料行不可为空*/
		if(CollectionUtils.isEmpty(detailList)){
			throw new BaseException(LocaleHandler.getLocaleMsg("采购订单物料明细行不可为空"));
		}
		/*订单数量为0是提示不可提交*/
		for(OrderDetail orderDetail:detailList){
			if(orderDetail.getOrderNum() == null || orderDetail.getOrderNum().compareTo(BigDecimal.ZERO) <= 0){
				throw new BaseException(LocaleHandler.getLocaleMsg("订单数量不可为空或负数,请检查"));
			}
		}
		/*判断订单头，订单物料是否为空*/
		Assert.notNull(order, LocaleHandler.getLocaleMsg("采购订单头不可为空"));
		Assert.notEmpty(detailList,LocaleHandler.getLocaleMsg("物料不可为空"));
		Assert.notNull(order.getCeeaOrgId(),LocaleHandler.getLocaleMsg("业务实体不可为空"));
		Assert.hasText(order.getCeeaIfSupplierConfirm(),LocaleHandler.getLocaleMsg("请选择是否供应商确认"));
		Assert.hasText(order.getOrderType(), LocaleHandler.getLocaleMsg("请选择订单类型"));
		Assert.hasText(order.getCeeaIfConSignment(),LocaleHandler.getLocaleMsg("请选择是否寄售"));
		Assert.hasText(order.getCeeaIfSupplierConfirm(),LocaleHandler.getLocaleMsg("请选择是否电站业务"));
		Assert.hasText(order.getCeeaReceiveAddress(),LocaleHandler.getLocaleMsg("收货地址不可为空"));
		Assert.hasText(order.getCeeaReceiveOrderAddress(),LocaleHandler.getLocaleMsg("收单地址不可为空"));
		Assert.hasText(order.getIfSample(),LocaleHandler.getLocaleMsg("是否为样品小批量订单不可为空"));
		Assert.notNull(order.getVendorId(),LocaleHandler.getLocaleMsg("供应商不可为空"));
		long dd3 = System.currentTimeMillis();
		/*判断订单附件信息*/
		if(attachList != null && attachList.size() > 0){
			attachList.stream().forEach(item -> {
				Assert.notNull(item.getFileuploadId(),LocaleHandler.getLocaleMsg("文件上传id不可为空"));
				Assert.notNull(item.getAttachName(),LocaleHandler.getLocaleMsg("文件名称不可为空"));
			});
		}
		/*校验订单明细的必填项*/
		detailList.stream().forEach(item -> {
			Assert.notNull(item.getCeeaUnitTaxPrice(),LocaleHandler.getLocaleMsg("含税单价不可为空"));
			Assert.hasText(item.getCeeaTaxKey(),LocaleHandler.getLocaleMsg("税率编码不可为空"));
			Assert.notNull(item.getCeeaTaxRate(),LocaleHandler.getLocaleMsg("税率不可为空"));
			Assert.notNull(item.getCurrencyId(),LocaleHandler.getLocaleMsg("币种id不可为空"));
			Assert.notNull(item.getCurrencyName(),LocaleHandler.getLocaleMsg("币种名称不可为空"));
			Assert.notNull(item.getCurrencyCode(),LocaleHandler.getLocaleMsg("币种编码不可为空"));
		});

		/**
		 * 2.物料小类的校验
		 */
		/*校验同一订单中，校验所有小类“是否同步erp”是否相同*/
		Set<String> enableSynErps = minPurchaseCategoryList.stream().map(item -> item.getCeeaEnableSynErp()).collect(Collectors.toSet());
		if(enableSynErps.size() > 1){
			throw new BaseException(LocaleHandler.getLocaleMsg("同一订单的物料小类是否同步ERP需相同"));
		}
		/*小类为701101、701201、701301、701501、701502、701503、701504、701505、701506、701507 的不传ERP ，这些类别可以在同一订单，但不能混杂这里以外的小类在相同订单中*/
		List<String> smallCategory = Arrays.asList("701101","701201","701301","701501","701502","701503","701504","701505","701506","701507");
		List<String> smallPurchaseCategoryCodes = minPurchaseCategoryList.stream().map(item -> item.getCategoryCode()).collect(Collectors.toList());
		/*取交集*/
		List<String> overlap = smallCategory.stream().filter(s -> smallPurchaseCategoryCodes.contains(s)).collect(Collectors.toList());
		if(overlap.size() > 0 && overlap.size() < smallPurchaseCategoryCodes.size()){
			throw new BaseException(LocaleHandler.getLocaleMsg("小类为701101、701201、701301、701501、701502、701503、701504、701505、701506、701507 的不可与其他物料混在一起"));
		}

		/**
		 * 6.物料中类的校验
		 * 需限制订单行中类是7011,7022,7015的订单不传ERP，在做订单时就要控制不能和其他分类的行做一个订单里
		 */
		/*获取所有物料中类*/
		List<Long> materialIds = detailList.stream().map(item -> item.getMaterialId()).collect(Collectors.toList());
		List<MaterialItem> materialItemList = baseClient.listMaterialItemsByIds(materialIds);
		List<String> middlePurchaseCategoryCode = materialItemList.stream().filter(item -> item.getStruct() != null).map(item -> item.getStruct().split("-")[1]).collect(Collectors.toList());
		if(middlePurchaseCategoryCode.size() != materialItemList.size()){
			throw new BaseException(LocaleHandler.getLocaleMsg("订单明细部分物料无中类，请维护物料数据"));
		}else{
			if(middlePurchaseCategoryCode.contains("7011") ||
					middlePurchaseCategoryCode.contains("7022") ||
					middlePurchaseCategoryCode.contains("7015")){
				List<String> list = new ArrayList<>(Arrays.asList("7011", "7022", "7015"));
				if(!list.containsAll(middlePurchaseCategoryCode)){
					throw new BaseException(LocaleHandler.getLocaleMsg("订单行中类是7011,7022,7015的物料不能喝其他分类的行在同一订单"));
				}
			}
		}

		/**
		 * 3.物料大类的校验
		 */
		/* 允许同时存在生产材料和备品备件的物料明细，存在生产材料或备品备件的采购订单，不允许存在其他物料大类的物料明细 */
		List<String> categoryCodes = maxPurchaseCategoryList.stream().map(item -> item.getCategoryCode()).collect(Collectors.toList());
		if(categoryCodes.contains(CategoryTypeEnum.PRODUCTION_MATERIAL.getValue()) ||
				categoryCodes.contains(CategoryTypeEnum.SPARE_PARTS.getValue())
		){
			for(int i = 0;i<categoryCodes.size();i++){
				if(!CategoryTypeEnum.PRODUCTION_MATERIAL.getValue().equals(categoryCodes.get(i)) ||
						!CategoryTypeEnum.SPARE_PARTS.getValue().equals(categoryCodes.get(i))
				){
					throw new BaseException(LocaleHandler.getLocaleMsg("存在生产材料或备品备件的采购订单，不允许存在其他物料大类的物料明细"));
				}
			}
		}
		/*采购订单，服务类和非服务类不能共存*/
		if (!isAllServiceClassOrNot(categoryCodes)){
			throw new BaseException("采购订单,服务类和非服务类不能共存");
		}

		/**
		 * 4.采购类型的校验
		 */
		Order existOrder = supcooperateClient.getOrderById(order.getOrderId());
		/*如果是紧急采购,查看含税总金额是否大于两万，大于两万报错*/
		Assert.notNull(order.getCeeaTaxAmount(), LocaleHandler.getLocaleMsg("含税总金额不可为空"));
		if(order.getOrderType().equals(OrderTypeEnum.URGENT.getValue()) && SourceSystemEnum.MANUAL.getValue().equals(existOrder.getSourceSystem())){
			if(order.getCeeaTaxAmount().compareTo(new BigDecimal(20000)) == 1){
				Assert.notNull(null,LocaleHandler.getLocaleMsg("含税总金额不可大于2W"));
			}
		}
		/*如果是研发采购，订单总金额不可大于20000*/
		if(order.getOrderType().equals(OrderTypeEnum.DEVELOP.getValue()) && SourceSystemEnum.MANUAL.getValue().equals(existOrder.getSourceSystem())){
			if(order.getCeeaTaxAmount().compareTo(new BigDecimal(20000)) == 1){
				throw new BaseException(LocaleHandler.getLocaleMsg("含税总金额不可大于2W"));
			}
		}
		/*寄售订单，仅允许选择物料大类为生产材料，备品备件(去掉)*/
		/*if(order.getOrderType().equals(OrderTypeEnum.CONSIGNMENT.getValue())){
			maxPurchaseCategoryList.forEach(item -> {
				if(!CategoryTypeEnum.SPARE_PARTS.getCode().equals(item.getCategoryCode())){
					throw new BaseException("寄售订单，仅允许选择物料大类为备品备件");
				}
			});
		}*/
		/*便捷采购订单，仅允许选择物料大类为综合类物资(去掉)*/
		/*if(order.getOrderType().equals(OrderTypeEnum.CONVENIENT.getValue())){
			maxPurchaseCategoryList.forEach(item -> {
				if(!CategoryTypeEnum.COMPREHENSIVE_MATERIALS.getCode().equals(item.getCategoryCode())){
					throw new BaseException("便捷采购订单，仅允许选择物料大类为综合类物资");
				}
			});
		}*/
		/*研发采购订单，仅允许选择物料大类为生产材料、设备、备品备件、服务类(去掉)*/
		/*if(order.getOrderType().equals(OrderTypeEnum.DEVELOP.getValue())){
			maxPurchaseCategoryList.forEach(item -> {
				if(!CategoryTypeEnum.PRODUCTION_MATERIAL.getCode().equals(item.getCategoryCode()) &&
						!CategoryTypeEnum.DEVICE.getCode().equals(item.getCategoryCode()) &&
						!CategoryTypeEnum.SPARE_PARTS.getCode().equals(item.getCategoryCode()) &&
						!CategoryTypeEnum.SERVICE.getCode().equals(item.getCategoryCode())
				){
					throw new BaseException(LocaleHandler.getLocaleMsg("研发采购订单，仅允许选择物料大类为生产材料、设备、备品备件、服务类"));
				}
			});
		}*/

		/**
		 * 5.业务实体，库存组织的校验
		 */
		/*判断库存组织是否属于该业务实体下*/
		List<OrganizationRelation> organizationRelationList = baseClient.listChildrenOrganization(order.getCeeaOrgId());
		if(organizationRelationList.size() <= 0){
			Assert.notNull(null,LocaleHandler.getLocaleMsg("该业务实体下没有库存组织"));
		}
		List<Long> organizationIds = organizationRelationList.stream().map(item -> item.getOrganizationId()).collect(Collectors.toList());
		detailList.stream().forEach(item -> {
			Assert.notNull(item.getCeeaOrganizationId(),LocaleHandler.getLocaleMsg("库存组织不可为空"));
			if(!organizationIds.contains(item.getCeeaOrganizationId())){
				Assert.notNull(null,LocaleHandler.getLocaleMsg("库存组织不在该业务实体下"));
			}
		});

		/*判断库存组织是否相等*/
		Set<Long> organizationSet = detailList.stream().filter(item -> item.getCeeaOrganizationId() != null).map(item -> item.getCeeaOrganizationId()).collect(Collectors.toSet());
		if(organizationSet.size() > 1){
			throw new BaseException(LocaleHandler.getLocaleMsg("采购订单明细不可存在多个库存组织"));
		}

		/**
		 * 判断 收货地址、收单地址校验：地址在订单行中的库存组织下时，允许提交订单
		 * 获取库存组织下的所有地点
		 */
		List<Location> locationList = baseClient.getLocationsByOrganization(new Organization()
				.setOrganizationId(detailList.get(0).getCeeaOrganizationId())
				.setOrganizationName(detailList.get(0).getCeeaOrganizationName())
				.setOrganizationCode(detailList.get(0).getCeeaOrganizationCode())
				.setOrganizationTypeCode("INV")
		);
		if(CollectionUtils.isEmpty(locationList)){
			throw new BaseException(LocaleHandler.getLocaleMsg(detailList.get(0).getCeeaOrganizationName()) + "查询不到地点，请检查");
		}else{
			List<String> locationNames = locationList.stream().map(item -> item.getLocationName()).collect(Collectors.toList());
			if(!locationNames.contains(order.getCeeaReceiveAddress())){
				throw new BaseException(LocaleHandler.getLocaleMsg(detailList.get(0).getCeeaOrganizationName() + "没有该收货地址:" + "["+order.getCeeaReceiveAddress()+"],请检查"));
			}
			if(!locationNames.contains(order.getCeeaReceiveOrderAddress())){
				throw new BaseException(LocaleHandler.getLocaleMsg(detailList.get(0).getCeeaOrganizationName() + "没有该收单地址:" + "["+order.getCeeaReceiveAddress()+"],请检查"));
			}
		}

		/**
		 * 7.供应商组织品类关系与是否样品小批量订单的认证
		 * 	 (1)订单业务实体下，供应商的行是否有认证中的，如果有认证中，必须是Y (供应商组织品类状态 认证中) 认证中供应商只能下样品小批量订单
		 * 	 (2)订单业务实体下，如果供应商组织品类关系 又有认证中，又有绿牌，报错 - 认证中品类不能与合格品类共同下订单，请拆分订单
		 */
		/*获取供应商的供应商组织品类关系*/
//		log.info("供应商组织品类关系验证：");
//		List<OrgCategory> orderOrgCategoryList = new ArrayList<>();
//		List<OrgCategory> orgCategoryList = supplierClient.listOrgCategoryByParam(
//				new OrgCategory().setCompanyId(order.getVendorId())
//					.setOrgId(order.getCeeaOrgId())
//		);
//		for(OrgCategory orgCategory:orgCategoryList){
//			if(smallPurchaseCategoryCodes.contains(orgCategory.getCategoryCode())){
//				orderOrgCategoryList.add(orgCategory);
//			}
//		}
//
//		Set<String> orgCategorySet = orderOrgCategoryList.stream().map(item -> item.getServiceStatus()).collect(Collectors.toSet());
////		2020年11月12日12:01:30  绿牌供应商也会校验这个问题，找不到原因，先屏蔽
//		if(orgCategorySet.contains(CategoryStatus.VERIFY.name()) && "N".equals(order.getIfSample())){
//			log.info("订单号为：" + order.getOrderNumber() + " 【认证中供应商只能下样品小批量订单】");
//			throw new BaseException(LocaleHandler.getLocaleMsg("认证中供应商只能下样品小批量订单"));
//		}
//		if(orgCategorySet.containsAll(new HashSet<String>(){{add(CategoryStatus.VERIFY.name());add(CategoryStatus.GREEN.name());}})){
//			log.info("订单号为：" + order.getOrderNumber() + "【认证中品类不能与合格品类共同下订单，请拆分订单】");
//			throw new BaseException(LocaleHandler.getLocaleMsg("认证中品类不能与合格品类共同下订单，请拆分订单"));
//		}

	}

	public void checkOrderNumEnough(List<OrderDetailDTO> orderDetailDTOListList){
		long dddd1 = System.currentTimeMillis();
		if(!supcooperateClient.judgeEnoughCount(orderDetailDTOListList)){
			throw new BaseException(LocaleHandler.getLocaleMsg("采购需求数量不足"));
		}
		long dddd2 = System.currentTimeMillis();
		System.out.println("dddd2-dddd1:" + (dddd2 - dddd1));
	}

	/**
	 * 创建订单-保存
	 */
	@Override
	@Transactional
	public Long save(OrderSaveRequestDTO param) {
		LoginAppUser loginAppUser = AppUserUtil.getLoginAppUser();
		Date date = new Date();
		Order order = param.getOrder();
		List<OrderDetail> orderDetailList = param.getDetailList();
		List<OrderAttach> orderAttachList = param.getAttachList();
		List<OrderPaymentProvision> orderPaymentProvisionList = param.getPaymentProvisionList();

		/*获取物料的所有物料小类*/
		List<PurchaseCategory> purchaseCategoryList = new ArrayList<>();
		//采购分类id无效判断 add by chenwt24@meicloud.com    2020-10-08 (因旧数据的问题加上该校验)
		for (OrderDetail od:orderDetailList){
			PurchaseCategory purchaseCategoryByParm = baseClient.getPurchaseCategoryByParm(new PurchaseCategory().setCategoryId(od.getCategoryId()));
			Assert.notNull(purchaseCategoryByParm,"采购分类id无效，id为："+od.getCategoryId());
			purchaseCategoryList.add(purchaseCategoryByParm);
		}

		/* Begin by chenwt24@meicloud.com   2020-10-08 */
		//字段赋值，保存，提交的时候，检查物料行中有无服务类，有 为N，没有Y
		/*String isSendErp = checkHavingServiceClass(purchaseCategoryList);*/
		/* End by chenwt24@meicloud.com     2020-10-08 */

		boolean ifAdd = true;
		if(order.getOrderId() == null){
			order.setOrderId(IdGenrator.generate())
					.setOrderNumber(baseClient.seqGenForAnon(SequenceCodeConstant.SEQ_SSC_ORDER_NUM))
					.setOrderStatus(PurchaseOrderEnum.DRAFT.getValue())
					.setCeeaPurchaseOrderDate(date)
					//.setIsSendErp(isSendErp) add by chenwt24@meicloud.com    2020-10-08
					.setVersion(0L);
			if (StringUtils.isBlank(order.getSourceSystem())) {
				order.setSourceSystem(SourceSystemEnum.MANUAL.getValue());
			}
			if(loginAppUser != null){
				/*对接审批流无登录人信息处理*/
				order.setCeeaSaveId(loginAppUser.getUserId())
						.setCeeaSaveDate(date)
						.setCeeaSaveBy(loginAppUser.getNickname());
			}

			ifAdd = true;
		}else {
			//order.setIsSendErp(isSendErp);
			ifAdd = false;
		}
		/*根据工号查询是否有采购员身份，没有提示请关联ERP采购员账号 scc_rbac_user的【CEEA_PO_AGENT_NUMBER】 对应 ceea_rbac_po_agent的【AGENT_NUMBER】*/
		if(loginAppUser != null){
			/*对接审批流无登录人信息处理*/
			if(StringUtils.isBlank(loginAppUser.getCeeaPoAgentNumber())){
				throw new BaseException(LocaleHandler.getLocaleMsg("请关联ERP采购员账号"));
			}
			List<PoAgent> poAgentList = rbacClient.listPoAgentByParams(new PoAgent().setAgentNumber(loginAppUser.getCeeaPoAgentNumber()));
			if(CollectionUtils.isEmpty(poAgentList)){
				throw new BaseException(LocaleHandler.getLocaleMsg("请关联ERP采购员账号"));
			}

			order.setCeeaEmpNo(poAgentList.get(0).getAgentNumber())
					.setCeeaEmpUsername(poAgentList.get(0).getAgentName())
					.setCeeaEmpUseId(poAgentList.get(0).getAgentId());
		}

		/*计算 合计数量，合计金额含税，合计金额不含税*/
		if(order.getCeeaTotalNum() == null){
			order.setCeeaTotalNum(BigDecimal.ZERO);
		}
		if(order.getCeeaTaxAmount() == null){
			order.setCeeaTaxAmount(BigDecimal.ZERO);
		}
		if(order.getCeeaNoTaxAmount() == null){
			order.setCeeaNoTaxAmount(BigDecimal.ZERO);
		}
		BigDecimal totalNum = BigDecimal.ZERO;
		BigDecimal taxAmount = BigDecimal.ZERO;
		BigDecimal noTaxAmount = BigDecimal.ZERO;

		for(OrderDetail item:orderDetailList){
			Long itemId = IdGenrator.generate();
			item.setOrderId(order.getOrderId())
					.setOrderDetailId(itemId);
			/*计算不含税单价*/
			if(item.getCeeaUnitTaxPrice() == null){
				item.setCeeaUnitTaxPrice(BigDecimal.ZERO);
			}
			if(item.getCeeaTaxRate() == null){
				item.setCeeaTaxRate(BigDecimal.ZERO);
			}
			BigDecimal ceeaTaxRate = item.getCeeaTaxRate().divide(new BigDecimal(100),8,BigDecimal.ROUND_HALF_UP).add(new BigDecimal(1));
			item.setCeeaUnitNoTaxPrice(item.getCeeaUnitTaxPrice().divide(ceeaTaxRate,8,BigDecimal.ROUND_HALF_UP));
			/*计算税额，含税金额，不含税金额*/
			if(item.getOrderNum() == null){
				item.setOrderNum(BigDecimal.ZERO);
			}
			if(item.getCeeaUnitNoTaxPrice() == null){
				item.setCeeaUnitNoTaxPrice(BigDecimal.ZERO);
			}
			if(item.getCeeaUnitTaxPrice() == null){
				item.setCeeaUnitNoTaxPrice(BigDecimal.ZERO);
			}
			BigDecimal ceeaAmountIncludingTax = item.getCeeaUnitTaxPrice().multiply(item.getOrderNum());  //含税金额
			BigDecimal ceeaAmountExcludingTax = item.getCeeaUnitNoTaxPrice().multiply(item.getOrderNum());  //不含税金额
			BigDecimal ceeaTaxAmount = ceeaAmountIncludingTax.subtract(ceeaAmountExcludingTax);  //税额
			item.setCeeaAmountExcludingTax(ceeaAmountExcludingTax.setScale(8,BigDecimal.ROUND_HALF_UP))
					.setCeeaAmountIncludingTax(ceeaAmountIncludingTax.setScale(8,BigDecimal.ROUND_HALF_UP))
					.setCeeaTaxAmount(ceeaTaxAmount.setScale(8,BigDecimal.ROUND_HALF_UP));
			totalNum = totalNum.add(item.getOrderNum());
			taxAmount = taxAmount.add(ceeaAmountIncludingTax);
			noTaxAmount = noTaxAmount.add(ceeaAmountExcludingTax);

		}
		order.setCeeaTotalNum(totalNum)
				.setCeeaTaxAmount(taxAmount)
				.setCeeaNoTaxAmount(noTaxAmount);

		orderAttachList.stream().forEach(item -> {
			Long itemId = IdGenrator.generate();
			item.setOrderId(order.getOrderId())
					.setVersion(0L)
					.setAttachId(itemId);
		});
		orderPaymentProvisionList.stream().forEach(item -> {
			Long itemId = IdGenrator.generate();
			item.setOrderPaymentProvisionId(itemId)
					.setOrderId(order.getOrderId())
					.setVersion(0L);
		});

		/*插入后续单据(先删除后续单据，在插入)*/
		iSubsequentDocumentsService.remove(new QueryWrapper<>(new SubsequentDocuments().setFollowFormId(order.getOrderId())));
		List<SubsequentDocuments> subsequentDocumentsList = new ArrayList<>();
		for(OrderDetail orderDetail:orderDetailList){
			if("Y".equals(orderDetail.getCeeaIfRequirement())){
				subsequentDocumentsList.add(new SubsequentDocuments()
						.setRequirementLineId(orderDetail.getCeeaRequirementLineId())
						.setSubsequentDocumentsId(IdGenrator.generate())
						.setSubsequentDocumentsNumber(order.getOrderNumber())
						.setIsubsequentDocumentssType(RelatedDocumentsEnum.PURCHASE.getName())
						.setFollowFormId(order.getOrderId())
				);
			}
		}
		iSubsequentDocumentsService.saveBatch(subsequentDocumentsList);

		/*扣减/释放采购申请行数量*/
		updateRequirementLineQuantitys(param,ifAdd);

		/*插入数据*/
		supcooperateClient.saveOrderForm(new OrderSaveRequestDTO()
				.setAttachList(orderAttachList)
				.setDetailList(orderDetailList)
				.setOrder(order)
				.setPaymentProvisionList(orderPaymentProvisionList)
		);
		return order.getOrderId();
	}

	/**
	 * 创建订单-保存
	 */
	@Override
	@Transactional
	public Order saveCopy(OrderSaveRequestDTO param) {
		LoginAppUser loginAppUser = AppUserUtil.getLoginAppUser();
		Date date = new Date();
		Order order = param.getOrder();
		List<OrderDetail> orderDetailList = param.getDetailList();
		List<OrderAttach> orderAttachList = param.getAttachList();
		List<OrderPaymentProvision> orderPaymentProvisionList = param.getPaymentProvisionList();
		if(order.getOrderId() == null){
			order.setOrderId(IdGenrator.generate())
					.setOrderNumber(baseClient.seqGen(SequenceCodeConstant.SEQ_SSC_ORDER_NUM))
					.setOrderStatus(PurchaseOrderEnum.DRAFT.getValue())
					.setCeeaPurchaseOrderDate(date)
					.setCeeaSaveId(loginAppUser.getUserId())
					.setCeeaSaveDate(date)
					.setCeeaSaveBy(loginAppUser.getNickname())
					.setCeeaEmpNo(loginAppUser.getCeeaEmpNo())
					.setCeeaEmpUseId(null) //先不传
					.setCeeaEmpUsername(loginAppUser.getNickname())
					.setVersion(0L);
		}


		log.info("##########################");
		log.info(loginAppUser.getNickname());
		orderDetailList.stream().forEach(item -> {
			Long itemId = IdGenrator.generate();
			item.setOrderId(order.getOrderId())
					.setOrderDetailId(itemId);
		});
		orderAttachList.stream().forEach(item -> {
			Long itemId = IdGenrator.generate();
			item.setOrderId(order.getOrderId())
					.setVersion(0L)
					.setAttachId(itemId);
		});
		orderPaymentProvisionList.stream().forEach(item -> {
			Long itemId = IdGenrator.generate();
			item.setOrderPaymentProvisionId(itemId)
					.setOrderId(order.getOrderId())
					.setVersion(0L);
		});
		/*插入数据*/
		supcooperateClient.saveOrderForm(new OrderSaveRequestDTO()
				.setAttachList(orderAttachList)
				.setDetailList(orderDetailList)
				.setOrder(order)
				.setPaymentProvisionList(orderPaymentProvisionList)
		);
		return order;
	}

	/**
	 * 创建订单-提交
	 * @param param
	 */
	@Override
	@Transactional
	public void submitOrder(OrderSaveRequestDTO param) {
		Order order = param.getOrder();
		List<OrderDetail> orderDetailList = param.getDetailList();
		List<OrderAttach> orderAttachList = param.getAttachList();
		List<OrderPaymentProvision> orderPaymentProvisionList = param.getPaymentProvisionList();

		/*获取所有的采购小类*/
		/*因为旧数据做的校验*/
		List<PurchaseCategory> minPurchaseCategoryList = new ArrayList<>();
		for(OrderDetail orderDetail:orderDetailList){
			PurchaseCategory purchaseCategoryByParm = baseClient.getPurchaseCategoryByParm(new PurchaseCategory().setCategoryId(orderDetail.getCategoryId()));
			Assert.notNull(purchaseCategoryByParm,"采购分类id无效，id为："+orderDetail.getCategoryId());
			minPurchaseCategoryList.add(purchaseCategoryByParm);
		}

		/*获取所有的采购大类*/
		List<PurchaseCategory> maxPurchaseCategoryList = new ArrayList<>();
		for(OrderDetail od : orderDetailList){
			Assert.notNull(od.getCategoryId(),LocaleHandler.getLocaleMsg("materialCode: " + od.getMaterialCode() + " materialName: " + od.getMaterialName() + " 物料明细行categoryId不可为空"));
			PurchaseCategory purchaseCategory = baseClient.queryMaxLevelCategory(new PurchaseCategory().setCategoryId(od.getCategoryId()));
			maxPurchaseCategoryList.add(purchaseCategory);
		}

		/*校验数据*/
		long d1 = System.currentTimeMillis();
		checkAddOrder(order,orderDetailList,orderAttachList,maxPurchaseCategoryList,minPurchaseCategoryList);
		long d2 = System.currentTimeMillis();
		log.info("d2-d1:" + (d2-d1));
		Order existOrder = supcooperateClient.getOrderById(order.getOrderId());
		Assert.isTrue(PurchaseOrderEnum.DRAFT.getValue().equals(existOrder.getOrderStatus()) ||
				PurchaseOrderEnum.REJECT.getValue().equals(existOrder.getOrderStatus())||
				PurchaseOrderEnum.WITHDRAW.getValue().equals(existOrder.getOrderStatus()),LocaleHandler.getLocaleMsg("只能提交新建状态,撤回或者被驳回的的订单"));
		long d3 = System.currentTimeMillis();

		//字段赋值，保存，提交的时候，检查物料行中有无服务类，有 为N，没有Y
		String isSendErp = checkHavingServiceClass(maxPurchaseCategoryList);
		/* End by chenwt24@meicloud.com     2020-10-08 */
		/*通过小类控制该订单是否传递erp*/
		isSendErp = minPurchaseCategoryList.get(0).getCeeaEnableSynErp();

		long d4 = System.currentTimeMillis();
		/*设置属性*/
		LoginAppUser loginAppUser = AppUserUtil.getLoginAppUser();
		Date date = new Date();
		order.setOrderId(order.getOrderId())
				.setSubmittedId(loginAppUser.getUserId())
				.setSubmittedBy(loginAppUser.getNickname())
				.setSubmittedTime(date)
				.setIsSendErp(isSendErp)// add by chenwt24@meicloud.com    2020-10-08
				.setOrderStatus(PurchaseOrderEnum.UNDER_APPROVAL.getValue());

		/*根据工号查询是否有采购员身份，没有提示请关联ERP采购员账号 scc_rbac_user的【CEEA_PO_AGENT_NUMBER】 对应 ceea_rbac_po_agent的【AGENT_NUMBER】*/
		if(StringUtils.isBlank(loginAppUser.getCeeaPoAgentNumber())){
			throw new BaseException(LocaleHandler.getLocaleMsg("请关联ERP采购员账号"));
		}
		List<PoAgent> poAgentList = rbacClient.listPoAgentByParams(new PoAgent().setAgentNumber(loginAppUser.getCeeaPoAgentNumber()));
		if(CollectionUtils.isEmpty(poAgentList)){
			throw new BaseException(LocaleHandler.getLocaleMsg("请关联ERP采购员账号"));
		}
		order.setCeeaEmpNo(poAgentList.get(0).getAgentNumber())
				.setCeeaEmpUsername(poAgentList.get(0).getAgentName())
				.setCeeaEmpUseId(poAgentList.get(0).getAgentId());

		/*计算 合计数量，合计金额含税，合计金额不含税*/
		if(order.getCeeaTotalNum() == null){
			order.setCeeaTotalNum(BigDecimal.ZERO);
		}
		if(order.getCeeaTaxAmount() == null){
			order.setCeeaTaxAmount(BigDecimal.ZERO);
		}
		if(order.getCeeaNoTaxAmount() == null){
			order.setCeeaNoTaxAmount(BigDecimal.ZERO);
		}
		BigDecimal totalNum = BigDecimal.ZERO;
		BigDecimal taxAmount = BigDecimal.ZERO;
		BigDecimal noTaxAmount = BigDecimal.ZERO;

		for(OrderDetail item:orderDetailList){
			Long itemId = IdGenrator.generate();
			item.setOrderId(order.getOrderId())
					.setOrderDetailId(itemId);
			/*计算不含税单价*/
			BigDecimal ceeaTaxRate = item.getCeeaTaxRate().divide(new BigDecimal(100),8,BigDecimal.ROUND_HALF_UP).add(new BigDecimal(1));
			item.setCeeaUnitNoTaxPrice(item.getCeeaUnitTaxPrice().divide(ceeaTaxRate,8,BigDecimal.ROUND_HALF_UP));
			/*计算税额，含税金额，不含税金额*/
			if(item.getOrderNum() == null){
				item.setOrderNum(BigDecimal.ZERO);
			}
			if(item.getCeeaUnitNoTaxPrice() == null){
				item.setCeeaUnitNoTaxPrice(BigDecimal.ZERO);
			}
			if(item.getCeeaUnitTaxPrice() == null){
				item.setCeeaUnitNoTaxPrice(BigDecimal.ZERO);
			}
			BigDecimal ceeaAmountIncludingTax = item.getCeeaUnitTaxPrice().multiply(item.getOrderNum());  //含税金额
			BigDecimal ceeaAmountExcludingTax = item.getCeeaUnitNoTaxPrice().multiply(item.getOrderNum());  //不含税金额
			BigDecimal ceeaTaxAmount = ceeaAmountIncludingTax.subtract(ceeaAmountExcludingTax);  //税额
			item.setCeeaAmountExcludingTax(ceeaAmountExcludingTax.setScale(8,BigDecimal.ROUND_HALF_UP))
					.setCeeaAmountIncludingTax(ceeaAmountIncludingTax.setScale(8,BigDecimal.ROUND_HALF_UP))
					.setCeeaTaxAmount(ceeaTaxAmount.setScale(8,BigDecimal.ROUND_HALF_UP));
			totalNum = totalNum.add(item.getOrderNum());
			taxAmount = taxAmount.add(ceeaAmountIncludingTax);
			noTaxAmount = noTaxAmount.add(ceeaAmountExcludingTax);
		}

		order.setCeeaTotalNum(totalNum)
				.setCeeaTaxAmount(taxAmount)
				.setCeeaNoTaxAmount(noTaxAmount);

		orderAttachList.stream().forEach(item -> {
			Long itemId = IdGenrator.generate();
			item.setOrderId(order.getOrderId())
					.setVersion(0L)
					.setAttachId(itemId);
		});
		orderPaymentProvisionList.stream().forEach(item -> {
			Long itemId = IdGenrator.generate();
			item.setOrderPaymentProvisionId(itemId)
					.setOrderId(order.getOrderId())
					.setVersion(0L);
		});

		/*插入后续单据(先删除后续单据，在插入)*/
		iSubsequentDocumentsService.remove(new QueryWrapper<>(new SubsequentDocuments().setFollowFormId(order.getOrderId())));
		List<SubsequentDocuments> subsequentDocumentsList = new ArrayList<>();
		for(OrderDetail orderDetail:orderDetailList){
			if("Y".equals(orderDetail.getCeeaIfRequirement())){
				subsequentDocumentsList.add(new SubsequentDocuments()
						.setRequirementLineId(orderDetail.getCeeaRequirementLineId())
						.setSubsequentDocumentsId(IdGenrator.generate())
						.setSubsequentDocumentsNumber(order.getOrderNumber())
						.setIsubsequentDocumentssType(RelatedDocumentsEnum.PURCHASE.getName())
						.setFollowFormId(order.getOrderId())
				);
			}
		}
		iSubsequentDocumentsService.saveBatch(subsequentDocumentsList);

		/*扣减/释放采购申请行数量*/
		updateRequirementLineQuantitys(param,false);

		//todo，采购订单审批流
		String formId = null;
		try {
			formId = orderFlow.submitOrderConfFlow(param);
		} catch (Exception e) {
			throw new BaseException(e.getMessage());
		}
		if(StringUtils.isEmpty(formId)){
			throw new BaseException(LocaleHandler.getLocaleMsg("提交OA审批失败"));
		}
		/* Begin by chenwt24@meicloud.com   2020-10-08 */
		supcooperateClient.saveOrderForm(new OrderSaveRequestDTO()
				.setAttachList(orderAttachList)
				.setDetailList(orderDetailList)
				.setOrder(order)
				.setPaymentProvisionList(orderPaymentProvisionList)
		);

		if("NOT_TO_BPM".equals(formId)){
			/*不走审批流 - 直接审批*/
			approval(new OrderSaveRequestDTO().setOrder(new Order().setOrderId(order.getOrderId())));
		}
	}

	/**
	 * 创建订单-审批
	 * @param param
	 */
	@Override
	@Transactional
	public void approval(OrderSaveRequestDTO param) {
		/*校验数据*/
		Order order = supcooperateClient.getOrderById(param.getOrder().getOrderId());
		Assert.notNull(order,LocaleHandler.getLocaleMsg("找不到订单"));
		Assert.isTrue(PurchaseOrderEnum.UNDER_APPROVAL.getValue().equals(order.getOrderStatus()),LocaleHandler.getLocaleMsg("只能审批已提交的订单"));
		/*设置属性*/
		Long userId = order.getSubmittedId();
		String nickName = order.getSubmittedBy();
		Date date = new Date();
		List list = new ArrayList();
		String orderStatus = null;

		if(order.getCeeaIfSupplierConfirm().equals("Y")){
			/*修改订单状态*/
			orderStatus = PurchaseOrderEnum.APPROVED.getValue();
		}else{
			/*修改订单状态，推送erp*/
			orderStatus = PurchaseOrderEnum.ACCEPT.getValue();
			PurchaseOutputParameters result = null;
			//创建订单 逻辑补充  物料大类为服务类的订单 不用推送erp  add by chenwt24@meicloud.com    2020-10-08

			if(StringUtils.equals(order.getIsSendErp(),YesOrNo.YES.getValue())){
				result = pushErp(order);
				if("S".equals(result.getXesbresultinforec().getReturnstatus())){
					//重推订单(记录推送情况)
					repushHandlerService.save(
							"推送订单至erp，订单单号为：" + order.getOrderNumber(),
							order.getOrderNumber(),
							OrderServiceImpl.class.getName(),
							"pushErp",
							0,
							RepushStatus.SUCCESS,
							RepushConst.NOT_TO_REPUSH,
							null,
							null,
							order
					);
				}else{
					//重推订单
					repushHandlerService.save(
							"推送订单至erp，订单单号为：" + order.getOrderNumber(),
							order.getOrderNumber(),
							OrderServiceImpl.class.getName(),
							"pushErp",
							5,
							RepushStatus.FAIL,
							RepushConst.TO_REPUSH,
							new PushOrderCallback(),
							null,
							order
					);
					throw new BaseException(LocaleHandler.getLocaleMsg(result.getXesbresultinforec().getReturnmsg()));
				}
			}
			/*更新供应商状态*/
			supplierClient.modify(new CompanyInfo().setCompanyId(order.getVendorId()).setIfNewCompany("N"));
		}
		list.add(new Order().setOrderId(order.getOrderId())
				.setCeeaApprovalId(userId)
				.setCeeaApprovalBy(nickName)
				.setCeeaApprovalDate(date)
				.setOrderStatus(orderStatus)
		);
		/*确定 订单审批通过的数量*/
		List<OrderDetail> detailList = supcooperateClient.getOrderDetailByOrderId(order.getOrderId());
		List<OrderDetail> detailUpdates = new ArrayList<>();
		detailList.stream().forEach(item -> {
			detailUpdates.add(new OrderDetail()
					.setOrderDetailId(item.getOrderDetailId())
					.setCeeaApprovedNum(item.getOrderNum())
					.setCeeaFirstApprovedNum(item.getOrderNum())
			);
		});
		/*批量修改订单数据*/
		supcooperateClient.batchUpdateOrder(list);
		/*批量修改订单明细数据*/
		supcooperateClient.batchUpdateOrderDetail(detailUpdates);
	}

	/**
	 * 创建订单-驳回
	 * @param param
	 */
	@Override
	@Transactional
	public void reject(OrderSaveRequestDTO param) {
		/*校验数据*/
		Order order = supcooperateClient.getOrderById(param.getOrder().getOrderId());
		Assert.notNull(order,LocaleHandler.getLocaleMsg("找不到订单"));
		Assert.isTrue(PurchaseOrderEnum.UNDER_APPROVAL.getValue().equals(order.getOrderStatus()),LocaleHandler.getLocaleMsg("只能驳回审批中的订单"));
		/*设置属性*/
		LoginAppUser loginAppUser = AppUserUtil.getLoginAppUser();
		Date date = new Date();
		List list = new ArrayList();
		list.add(new Order().setOrderId(order.getOrderId())
				.setCeeaRejectId(loginAppUser==null?null:loginAppUser.getUserId())
				.setCeeaRejectBy(loginAppUser==null?null:loginAppUser.getNickname())
				.setCeeaRejectDate(date)
				.setOrderStatus(PurchaseOrderEnum.REJECT.getValue())
		);
		/*更改订单*/
		supcooperateClient.batchUpdateOrder(list);
	}

	/**
	 * 创建订单-供方确认
	 * @param param
	 */
	@Override
	@Transactional
	public void supplierConfirm(OrderSaveRequestDTO param) {
		/*校验数据*/
		Assert.notNull(param,"缺失参数: param");
		Assert.notNull(param.getOrder(),"缺失参数: order");
		Assert.notNull(param.getOrder().getOrderId(),"缺失参数: orderId");
		Order order = supcooperateClient.getOrderById(param.getOrder().getOrderId());
		Assert.notNull(order,LocaleHandler.getLocaleMsg("找不到订单"));
		Assert.isTrue(PurchaseOrderEnum.APPROVED.getValue().equals(order.getOrderStatus()),LocaleHandler.getLocaleMsg("只能确定审批通过的订单"));
		/*设置属性*/
		LoginAppUser loginAppUser = AppUserUtil.getLoginAppUser();
		Date date = new Date();
		PurchaseOutputParameters result = null;
		//创建订单 逻辑补充  物料大类为服务类的订单 不用推送erp  add by chenwt24@meicloud.com    2020-10-08
		if(YesOrNo.YES.getValue().equals(order.getIsSendErp())){
			// 日志类
			// ------------------------记录推送记录-------------------------
			InterfaceLogDTO interfaceLogDTO = new InterfaceLogDTO();
			try {
				interfaceLogDTO.setServiceInfo(JSON.toJSONString(order)); // 请求参数
			} catch (Exception e) {
				log.error("记录供方确认预执行推送ERP请求参数报错{}"+e);
			}
			interfaceLogDTO.setCreationDateBegin(new Date()); //创建开始时间
			interfaceLogDTO.setServiceName("供方确认预执行推送ERP"); // 服务名字
			interfaceLogDTO.setServiceType("WEBSERVICE"); // 请求方式
			interfaceLogDTO.setType("SEND"); // 发送方式
			interfaceLogDTO.setBillId(order.getOrderNumber()); // 业务单据ID
			interfaceLogDTO.setBillType("采购订单"); // 单据类型
			interfaceLogDTO.setTargetSys("ERP");
			// 正式业务
			try {
				result = pushErp(order);
				if("S".equals(result.getXesbresultinforec().getReturnstatus())){
					//重推订单(记录推送情况)
					repushHandlerService.save(
							"推送订单至erp，订单单号为：" + order.getOrderNumber(),
							order.getOrderNumber(),
							OrderServiceImpl.class.getName(),
							"pushErp",
							0,
							RepushStatus.SUCCESS,
							RepushConst.NOT_TO_REPUSH,
							null,
							null,
							order
					);
				}else{
					//重推订单
					repushHandlerService.save(
							"推送订单至erp，订单单号为：" + order.getOrderNumber(),
							order.getOrderNumber(),
							OrderServiceImpl.class.getName(),
							"pushErp",
							5,
							RepushStatus.FAIL,
							RepushConst.TO_REPUSH,
							new PushOrderCallback(),
							null,
							order
					);
					throw new BaseException(LocaleHandler.getLocaleMsg(result.getXesbresultinforec().getReturnmsg()));
				}



				try {
					interfaceLogDTO.setReturnInfo(JSON.toJSONString(result)); // 返回参数
				} catch (Exception e) {
					log.error("记录供方确认预执行推送ERP返回参数报错{}"+e);
				}
				interfaceLogDTO.setStatus("SUCCESS");
				if(!ObjectUtils.isEmpty(result) && !"S".equals(result.getXesbresultinforec().getReturnstatus())){
					interfaceLogDTO.setErrorInfo(result.getXesbresultinforec().getReturnmsg());
					interfaceLogDTO.setStatus("FAIL");
				}
			} catch (Exception e) {
				// 堆栈错误信息
				String stackTrace = Arrays.toString(e.getStackTrace());
				// 错误信息
				String message = e.getMessage();
				ConcurrentHashMap<String, String> errorMsg = new ConcurrentHashMap<>();
				errorMsg.put("message", e.getClass().getName() + ": " + message);
				errorMsg.put("stackTrace", stackTrace);
				interfaceLogDTO.setErrorInfo(JSON.toJSONString(errorMsg));
				interfaceLogDTO.setStatus("FAIL");
				throw e;
			}finally {
				interfaceLogDTO.setCreationDateEnd(new Date()); // 结束时间
				try {
					apiClient.createInterfaceLog(interfaceLogDTO);
				} catch (Exception e) {
					log.error("保存<供方确正式开始执行推送ERP>日志报错{}"+e);
				}
			}
		}

		if(!ObjectUtils.isEmpty(result) && result.getXesbresultinforec().getReturnstatus().equals("E")){
			log.info("订单同步erp报错，订单单号为：" + order.getOrderNumber());
			throw new BaseException(LocaleHandler.getLocaleMsg(result.getXesbresultinforec().getReturnmsg()));
		}else{
			List list = new ArrayList();
			list.add(new Order().setOrderId(order.getOrderId())
					.setComfirmId(loginAppUser.getUserId())
					.setComfirmBy(loginAppUser.getNickname())
					.setComfirmTime(date)
					.setOrderStatus(PurchaseOrderEnum.ACCEPT.getValue())
			);
			//更改订单
			supcooperateClient.batchUpdateOrder(list);
			//更改订单附件
			supcooperateClient.deleteOrderAttachByOrderId(order.getOrderId());
			List<OrderAttach> orderAttachList = param.getAttachList();
			orderAttachList.stream().forEach(item -> {
				item.setOrderId(param.getOrder().getOrderId());
				item.setAttachId(IdGenrator.generate());
				supcooperateClient.saveOrderAttach(item);
			});
			/*更新供应商字段*/
			supplierClient.modify(new CompanyInfo().setCompanyId(order.getVendorId()).setIfNewCompany("N"));
		}

	}

	public PurchaseInputParameters buildPurchaseInputParameters(OrderSaveRequestDTO orderSaveRequestDTO){
		Order order = orderSaveRequestDTO.getOrder();
		List<OrderDetail> orderDetailList = orderSaveRequestDTO.getDetailList();
		//*根据业务实体id获取erp_org_id*//*
		Organization organization = baseClient.get(order.getCeeaOrgId());
		if(null == organization){
			throw new BaseException(LocaleHandler.getLocaleMsg("找不到对应的业务实体，ceeaOrgId = " + order.getCeeaOrgId()));
		}
		if(StringUtils.isBlank(organization.getErpOrgId())){
			throw new BaseException(LocaleHandler.getLocaleMsg("erpOrgId为空，ceeaOrgId = " + order.getCeeaOrgId()));
		}
		/*获取库存组织 erp_org_id*/
		String erpOrganizationId = null;
		if(!CollectionUtils.isEmpty(orderDetailList)){
			Long organizationId = orderDetailList.get(0).getCeeaOrganizationId();
			if(null == organizationId){
				throw new BaseException(LocaleHandler.getLocaleMsg("库存组织id为空"));
			}
			Organization invOrganization = baseClient.get(organizationId);
			if(null == invOrganization){
				throw new BaseException(LocaleHandler.getLocaleMsg("找不到对应的库存组织， ceeaOrganizationId= " + organizationId));
			}
			if(StringUtils.isBlank(invOrganization.getErpOrgId())){
				throw new BaseException(LocaleHandler.getLocaleMsg("erpOrgId为空，ceeaOrgId = " + organizationId));
			}
			erpOrganizationId = invOrganization.getErpOrgId();
		}

		/*获取供应商erpVendorId,erpVendorCode*/
		CompanyInfo companyInfo = supplierClient.getCompanyInfo(order.getVendorId());
		if(null == companyInfo){
			throw new BaseException(LocaleHandler.getLocaleMsg("找不到对应的供应商, vendorId = " + order.getVendorId()));
		}
		if(StringUtils.isBlank(companyInfo.getErpVendorCode())){
			throw new BaseException(LocaleHandler.getLocaleMsg("找不到对应的erpVendorCode, vendorId = " + order.getVendorId()));
		}
		if(null == companyInfo.getErpVendorId()){
			throw new BaseException(LocaleHandler.getLocaleMsg("找不到对应的eprVendorId, vendorId = " + order.getVendorId()));
		}

		PurchaseInputParameters purchaseInputParameters = new PurchaseInputParameters();

		APPSCUXPOORDERSGEX1157981X1X1 appscuxpoordersgex1157981X1X1 = new APPSCUXPOORDERSGEX1157981X1X1();
		APPSCUXPOORDERSGEX1157981X1X7 appscuxpoordersgex1157981X1X7 = new APPSCUXPOORDERSGEX1157981X1X7();

		List<APPSCUXPOORDERSGEX1157981X1X8> appscuxpoordersgex1157981X1X8List = new ArrayList<>();

		String organizationSite = null;
		String currencyCode = null;
		String ceeaContractNo = null;
		BigDecimal ceeaTaxRate = null;
		if(!CollectionUtils.isEmpty(orderDetailList)){
			Organization inventoryOrg = baseClient.get(orderDetailList.get(0).getCeeaOrganizationId());
			organizationSite = inventoryOrg.getOrganizationSite();
			currencyCode = orderDetailList.get(0).getCurrencyCode();
			ceeaContractNo = orderDetailList.get(0).getCeeaContractNo();
			ceeaTaxRate = orderDetailList.get(0).getCeeaTaxRate();
		}

		APPSCUXPOORDERSGEX1157981X1X8 appscuxpoordersgex1157981X1X8 = new APPSCUXPOORDERSGEX1157981X1X8()
				.setOperationunitid(new BigDecimal(organization.getErpOrgId()))  //业务实体名称
				.setOperationname(order.getCeeaOrgName())   //业务实体名称
				.setDocumenttype("STANDARD")   //类型
				.setPonumber(order.getOrderNumber())   //订单编号
				.setCurrency(currencyCode)  //币种
				.setExchangetype(null)  //汇率类型
				.setExchangedate(null)  //汇率日期
				.setExchangerate(null)  //汇率
				.setAgentid(null)  //采购员Id(非必传)
				.setAgentname(order.getCeeaEmpUsername())  //采购员名称
				.setVendorid(new BigDecimal(companyInfo.getErpVendorId()))  //ERP供应商Id
				.setVendornumber(companyInfo.getErpVendorCode())  //供应商编码
				.setVendorsiteid(null)  //供应商地点Id
				.setVendorsitecode(order.getCeeaCostType())  //供应商地点名称
				.setContractorid(null)  //供应商联系人Id
				.setContractorname(null)  //供应商联系人名称 (非必填置空，文档上为必填后续沟通) order.getCeeaSupplierContacts
				.setBilltolocation(organizationSite)  //收单方
				.setShiptolocation(organizationSite)  //收货方
				.setPoordertype(order.getCeeaOrderTypeIdentification())  //采购订单类型
				.setPocontractnumber(ceeaContractNo)  //采购合同号
				.setContractor(null)  //联系人
				.setContractorphone(null)  //联系人电话
				.setContractorfax(null)  //联系人传真
				.setQacagrnumber(null)  //质量保证协议编号
				.setTecagrnumber(null)  //技术协议编号
				.setNegnumber(null)  //议标编号
				.setDescrother(order.getComments())  //备注(其他)
				.set_abstract(null)  //摘要
				.setTaxrate(ceeaTaxRate)  //税率
				.setPaymentmethod(null)  //付款方式
				.setPaymentterm(null)  //付款条件
				.setSourcesyscode(DataSourceEnum.NSRM_SYS.getKey())  //来源系统编码
				.setSourcerefence(null)  //来源系统参考
				.setSourcelineid(StringUtil.StringValue(order.getOrderId()))  //来源系统行Id
				.setIfacecode("PUR_PO_EXP")  //接口编码
				.setIfacemean(null);  //接口说明
		appscuxpoordersgex1157981X1X8List.add(appscuxpoordersgex1157981X1X8);
		APPSCUXPOORDERSGX1157981X1X50 appscuxpoordersgx1157981X1X50 = new APPSCUXPOORDERSGX1157981X1X50();
		List<APPSCUXPOORDERSGX1157981X1X51> appscuxpoordersgx1157981X1X51List = new ArrayList<>();

		for(int i=0;i<orderDetailList.size();i++){
			OrderDetail orderDetail = orderDetailList.get(i);

			APPSCUXPOORDERSGX1157981X1X51 appscuxpoordersgx1157981X1X51 = new APPSCUXPOORDERSGX1157981X1X51()
					.setLinenumber(new BigDecimal(orderDetail.getLineNum()))  //行编号
					.setLinetype("1")  //行类型 todo
					.setItemnumber(orderDetail.getMaterialCode())  //物料编码
					.setItemdescr(orderDetail.getMaterialName())  //物料说明
					.setUnitofmeasure(orderDetail.getUnitCode())  //计量单位
					.setQuantity(orderDetail.getOrderNum())  //数量
					.setPrice(orderDetail.getCeeaUnitNoTaxPrice())  //单价
					.setTaxprice(orderDetail.getCeeaUnitTaxPrice())  //含税价
					.setTaxratecode(orderDetail.getCeeaTaxKey())  //税码
					.setRequiredate(Objects.isNull(orderDetail.getCeeaPlanReceiveDate()) ? null : new SimpleDateFormat("yyyy-MM-dd").format(orderDetail.getCeeaPlanReceiveDate()))  //需求日期
					.setPromisedate(Objects.isNull(orderDetail.getCeeaPromiseReceiveDate()) ? null : new SimpleDateFormat("yyyy-MM-dd").format(orderDetail.getCeeaPromiseReceiveDate()))  //承诺日期
					.setNotaxflag(null)  //是否去税
					.setCustrequired(null)  //客指要求
					.setContractnumber(orderDetail.getCeeaContractNo())  //采购合同号
					.setLinedescr(orderDetail.getComments())  //行备注
					.setRequisitionnumber(StringUtils.isBlank(orderDetail.getCeeaRequirementHeadNum()) ? null : orderDetail.getCeeaRequirementHeadNum())  //采购申请号
					.setRequisitionlinenum(StringUtils.isBlank(orderDetail.getCeeaRowNum()) ? null : new BigDecimal(orderDetail.getCeeaRowNum()))  //采购申请行编号
					.setSourcesyscode(DataSourceEnum.NSRM_SYS.getKey())  //来源系统编码
					.setSourcerefence(null)  //来源系统参考
					.setSourcelineid(StringUtil.StringValue(orderDetail.getOrderDetailId()))  //来源系统行Id
					.setOrganizationid(new BigDecimal(erpOrganizationId)); //库存组织id
					/*.setOrganizationid()
					.setProjectid()
					.setTaskid()
					.setProjectnumber()
					.setTasknumber()*/

			appscuxpoordersgx1157981X1X51List.add(appscuxpoordersgx1157981X1X51);

		}
		appscuxpoordersgx1157981X1X50.setLinedetailsitem(appscuxpoordersgx1157981X1X51List);
		appscuxpoordersgex1157981X1X8.setLinedetails(appscuxpoordersgx1157981X1X50);

		appscuxpoordersgex1157981X1X7.setPpoinfotblitem(appscuxpoordersgex1157981X1X8List);
		purchaseInputParameters.setPpoinfotbl(appscuxpoordersgex1157981X1X7);
		purchaseInputParameters.setPesbinforec(appscuxpoordersgex1157981X1X1);
		return purchaseInputParameters;
	}

	/**
	 * 创建订单-供方拒绝
	 * @param param
	 */
	@Override
	@Transactional
	public void supplierReject(OrderSaveRequestDTO param) {
		/*校验数据*/
		Order order = supcooperateClient.getOrderById(param.getOrder().getOrderId());
		Assert.notNull(order,LocaleHandler.getLocaleMsg("找不到订单"));
		Assert.isTrue(PurchaseOrderEnum.APPROVED.getValue().equals(order.getOrderStatus()),LocaleHandler.getLocaleMsg("只能拒绝审批通过的订单"));
		/*设置属性*/
		LoginAppUser loginAppUser = AppUserUtil.getLoginAppUser();
		Date date = new Date();
		List list = new ArrayList();
		list.add(new Order().setOrderId(order.getOrderId())
				.setRefuseId(loginAppUser.getUserId())
				.setRefuseBy(loginAppUser.getNickname())
				.setRefuseTime(date)
				.setRefuseReason(param.getOrder().getRefuseReason())
				.setOrderStatus(PurchaseOrderEnum.REFUSED.getValue())
		);
		/*更改订单*/
		supcooperateClient.batchUpdateOrder(list);
	}

	/**
	 * 创建订单-删除
	 */
	@Override
	@Transactional
	public void deleteOrder(Long orderId){
		/*检验数据*/
		Order order = supcooperateClient.getOrderById(orderId);
		Assert.notNull(order,LocaleHandler.getLocaleMsg("找不到订单"));
		checkIfDelete(orderId);
		/*根据orderId 删除后续单据*/
		iSubsequentDocumentsService.remove(new QueryWrapper<>(new SubsequentDocuments().setFollowFormId(orderId)));

		/*释放订单持有的采购申请行数量*/
		OrderSaveRequestDTO orderSaveRequestDTO = new OrderSaveRequestDTO();
		orderSaveRequestDTO.setOrder(new Order().setOrderId(orderId));
		orderSaveRequestDTO.setDetailList(new ArrayList<>());
		updateRequirementLineQuantitys(orderSaveRequestDTO,false);

		supcooperateClient.deleteOrderByOrderId(orderId);

	}

	/**
	 * 废弃订单
	 * @param requirementHeadId
	 */
	@Override
	@Transactional
	public void abandon(Long requirementHeadId) {
		Assert.notNull(requirementHeadId,"废弃订单id不能为空");
		OrderSaveRequestDTO orderSaveRequestDTO = this.queryOrderById(requirementHeadId);
		Order order = orderSaveRequestDTO.getOrder();
		Assert.notNull(order,"找不到需要废弃的订单。");
		/*add by chenwj begin*/
		/*释放订单持有的采购申请数量*/
		OrderSaveRequestDTO abandonOrder = new OrderSaveRequestDTO();
		abandonOrder.setOrder(new Order().setOrderId(order.getOrderId()));
		abandonOrder.setDetailList(new ArrayList<>());
		updateRequirementLineQuantitys(abandonOrder,false);
		/*add by chenwj end*/
		String orderStatus = order.getOrderStatus();
		Assert.isTrue(PurchaseOrderEnum.REJECT.getValue().equals(orderStatus)|| PurchaseOrderEnum.WITHDRAW.getValue().equals(orderStatus),"找不到需要废弃的订单。");
		order.setOrderStatus(PurchaseOrderEnum.ABANDONED.getValue());
		supcooperateClient.saveOrder(order);
		SrmFlowBusWorkflow srmworkflowForm = baseClient.getSrmFlowBusWorkflow(requirementHeadId);
		if (srmworkflowForm!=null) {
			try {
				orderSaveRequestDTO.setProcessType("N");
				orderFlow.submitOrderConfFlow(orderSaveRequestDTO);
			} catch (Exception e) {
				Assert.isTrue(false, "废弃同步审批流失败。");
			}
		}
	}

	public void checkIfDelete(Long orderId){
		/*只有订单状态为新建 并且 订单变更状态为null时 才能删除*/
		Order order = supcooperateClient.getOrderById(orderId);
		if(order == null){
			Assert.notNull(null,LocaleHandler.getLocaleMsg("查询不到该订单"));
		}
		if(!order.getOrderStatus().equals(PurchaseOrderEnum.DRAFT.getValue()) || StringUtils.isNotBlank(order.getCeeaIfEditDone())){
			Assert.notNull(null,LocaleHandler.getLocaleMsg("订单不可删除"));
		}
	}

	/**
	 * 订单变更开始
	 */
	@Override
	@Transactional
	public void startEditStatus(Long orderId){
		/*检验数据*/
		Order order = supcooperateClient.getOrderById(orderId);
		Assert.notNull(order,LocaleHandler.getLocaleMsg("找不到订单"));
		checkIfEdit(order);
		/*更改订单*/
		List list = new ArrayList();
		list.add(
				new Order().setOrderId(orderId)
						.setCeeaIfEditDone("N")
						.setVersion(order.getVersion() + 1)
						.setOrderStatus(PurchaseOrderEnum.DRAFT.getValue())
		);
		supcooperateClient.batchUpdateOrder(list);
	}

	/**
	 * 编辑订单-保存
	 * 校验：
	 * 【订单状态只有为 新建 才能进行保存】
	 * 【变更数量不可大于原订单数量(第一次审批通过的数量)】
	 * 【变更数量不可小于已送达数量】
	 * 可修改的值：
	 * 订单头【合计数量】【合计金额含税】【合计金额不含税】
	 * 订单行【承诺到货日期】【订单数量】【税额】【含税金额】【不含税金额】
	 * 订单附件
	 */
	@Override
	@Transactional
	public void saveInEditStatus(OrderSaveRequestDTO param){
		/*检验数据*/
		Order order = supcooperateClient.getOrderById(param.getOrder().getOrderId());
		Assert.notNull(order,LocaleHandler.getLocaleMsg("找不到订单"));
		if(!PurchaseOrderEnum.DRAFT.getValue().equals(order.getOrderStatus())){
			Assert.notNull(null,"订单状态不可保存");
		}
		Assert.isTrue(PurchaseOrderEnum.DRAFT.getValue().equals(order.getOrderStatus()),LocaleHandler.getLocaleMsg("只能保存新建状态的订单"));
		List<OrderDetail> orderDetailList = param.getDetailList();

		orderDetailList.stream().forEach(item -> {
			OrderDetail orderDetail = supcooperateClient.getOrderDetail(new OrderDetail().setOrderDetailId(item.getOrderDetailId()));
			if(Objects.isNull(orderDetail.getCeeaApprovedNum())){
				orderDetail.setCeeaApprovedNum(new BigDecimal(0));
			}
			if(Objects.isNull(orderDetail.getReceiveSum())){
				orderDetail.setReceiveSum(new BigDecimal(0));
			}
			if(item.getOrderNum().compareTo(orderDetail.getCeeaApprovedNum()) == 1){
				Assert.notNull(null,"变更数量["+item.getOrderNum()+"]不可大于原订单数量["+orderDetail.getCeeaApprovedNum()+"]");
			}
			if(item.getOrderNum().compareTo(orderDetail.getReceiveSum()) == -1){
				Assert.notNull(null,"变更数量["+item.getOrderNum()+"]不可小于已送达订单数["+orderDetail.getReceiveSum()+"]");
			}
		});

		/*计算 合计数量，合计金额含税，合计金额不含税*/
		if(order.getCeeaTotalNum() == null){
			order.setCeeaTotalNum(BigDecimal.ZERO);
		}
		if(order.getCeeaTaxAmount() == null){
			order.setCeeaTaxAmount(BigDecimal.ZERO);
		}
		if(order.getCeeaNoTaxAmount() == null){
			order.setCeeaNoTaxAmount(BigDecimal.ZERO);
		}
		BigDecimal totalNum = BigDecimal.ZERO;
		BigDecimal taxAmount = BigDecimal.ZERO;
		BigDecimal noTaxAmount = BigDecimal.ZERO;

		/*修改订单*/
		List<OrderDetail> orderDetailUpdates = new ArrayList<>();
		for(OrderDetail item:orderDetailList){
			OrderDetail orderDetail = supcooperateClient.getOrderDetail(new OrderDetail().setOrderDetailId(item.getOrderDetailId()));
			/*计算税额，含税金额，不含税金额*/
			if(item.getOrderNum() == null){
				item.setOrderNum(BigDecimal.ZERO);
			}
			if(orderDetail.getCeeaUnitNoTaxPrice() == null){
				orderDetail.setCeeaUnitNoTaxPrice(BigDecimal.ZERO);
			}
			if(item.getCeeaUnitTaxPrice() == null){
				orderDetail.setCeeaUnitNoTaxPrice(BigDecimal.ZERO);
			}
			BigDecimal ceeaAmountIncludingTax = orderDetail.getCeeaUnitTaxPrice().multiply(item.getOrderNum());  //含税金额
			BigDecimal ceeaAmountExcludingTax = orderDetail.getCeeaUnitNoTaxPrice().multiply(item.getOrderNum());  //不含税金额
			BigDecimal ceeaTaxAmount = ceeaAmountIncludingTax.subtract(ceeaAmountExcludingTax);  //税额
			totalNum = totalNum.add(item.getOrderNum());
			taxAmount = taxAmount.add(ceeaAmountIncludingTax);
			noTaxAmount = noTaxAmount.add(ceeaAmountExcludingTax);
			orderDetailUpdates.add(
					new OrderDetail()
							.setOrderDetailId(item.getOrderDetailId())
							.setOrderNum(item.getOrderNum())
							.setCeeaPromiseReceiveDate(item.getCeeaPromiseReceiveDate())
							.setCeeaAmountExcludingTax(ceeaAmountExcludingTax.setScale(2,BigDecimal.ROUND_HALF_UP))
							.setCeeaAmountIncludingTax(ceeaAmountIncludingTax.setScale(2,BigDecimal.ROUND_HALF_UP))
							.setCeeaTaxAmount(ceeaTaxAmount.setScale(2,BigDecimal.ROUND_HALF_UP))
			);
		}

		Order updateOrder = new Order()
				.setOrderId(order.getOrderId())
				.setOrderStatus(PurchaseOrderEnum.DRAFT.getValue())
				.setCeeaTotalNum(totalNum)
				.setCeeaTaxAmount(taxAmount)
				.setCeeaNoTaxAmount(noTaxAmount);
		if(!Objects.equals(param.getOrder().getCeeaOpinion() , order.getCeeaOpinion()) ||
				!Objects.equals(param.getOrder().getCeeaReceiveAddress() , order.getCeeaReceiveAddress())){
			updateOrder.setCeeaOpinion(param.getOrder().getCeeaOpinion());
			updateOrder.setCeeaReceiveAddress(param.getOrder().getCeeaReceiveAddress());
		}
		/*释放采购申请*/
		if(CollectionUtils.isEmpty(param.getDetailList())){
			throw new BaseException("订单物料明细不可为空");
		}
		for(int i=0;i<param.getDetailList().size();i++){
			OrderDetail orderDetail = param.getDetailList().get(i);
			if(Objects.isNull(orderDetail.getCeeaRequirementLineId())){
				throw new BaseException("订单行ceeaRequirementLineId不可为空");
			}
			if(Objects.isNull(orderDetail.getOrderNum())){
				throw new BaseException("订单行orderNum不可为空");
			}
		}
		updateRequirementLineQuantitys(param,false);

		supcooperateClient.saveOrder(updateOrder);
		supcooperateClient.batchUpdateOrderDetail(orderDetailUpdates);
		/*更改订单附件*/
		supcooperateClient.deleteOrderAttachByOrderId(order.getOrderId());
		List<OrderAttach> orderAttachList = param.getAttachList();
		orderAttachList.stream().forEach(item -> {
			item.setOrderId(order.getOrderId());
			item.setAttachId(IdGenrator.generate());
			supcooperateClient.saveOrderAttach(item);
		});

	}

	/**
	 * 编辑订单-提交
	 * 校验：
	 * 【变更数量不可大于原订单数量】
	 * 【变更数量不能小于已接收数量】
	 * 【变更数量不能小于已接收数量】
	 * 【起草人节点意见不可为空】
	 * 可修改的值：
	 * 订单头【合计数量】【合计金额含税】【合计金额不含税】【起草人节点意见】
	 * 订单行【订单数量】【承诺到货日期】【税额】【含税金额】【不含税金额】【变更前数量】
	 * 订单附件
	 */
	@Override
	@Transactional
	public void submitInEditStatus(OrderSaveRequestDTO param){
		/*检验数据*/
		Order order = supcooperateClient.getOrderById(param.getOrder().getOrderId());
		Assert.isTrue(PurchaseOrderEnum.DRAFT.getValue().equals(order.getOrderStatus())
						|| PurchaseOrderEnum.REJECT.getValue().equals(order.getOrderStatus())
						||PurchaseOrderEnum.WITHDRAW.getValue().equals(order.getOrderStatus())
				,LocaleHandler.getLocaleMsg("只能提交新建状态或者已驳回的订单"));
		Assert.notNull(order,LocaleHandler.getLocaleMsg("找不到订单"));
		List<OrderDetail> orderDetailList = param.getDetailList();
		if(!CollectionUtils.isEmpty(orderDetailList)){
			orderDetailList.stream().forEach(item -> {
				if(Objects.nonNull(item)){
					OrderDetail orderDetail = supcooperateClient.getOrderDetail(new OrderDetail().setOrderDetailId(item.getOrderDetailId()));
					if(orderDetail.getReceivedQuantity() == null){
						orderDetail.setReceivedQuantity(BigDecimal.ZERO);
					}
					if(orderDetail.getCeeaFirstApprovedNum() == null){
						orderDetail.setCeeaFirstApprovedNum(BigDecimal.ZERO);
					}
					if(orderDetail.getReceiveSum() == null){
						orderDetail.setReceiveSum(BigDecimal.ZERO);
					}
					if(item.getOrderNum().compareTo(orderDetail.getReceivedQuantity()) == -1){
						throw new BaseException("变更数量不能小于已接收数量"+orderDetail.getReceivedQuantity());
					}
					if(item.getOrderNum().compareTo(orderDetail.getCeeaFirstApprovedNum()) == 1){
						Assert.notNull(null,"变更数量不可大于原订单数量");
					}
					if(item.getOrderNum().compareTo(orderDetail.getReceiveSum()) == -1){
						Assert.notNull(null,"变更数量不可小于送货数量"+orderDetail.getReceiveSum());
					}
				}
			});
		}
		Assert.notNull(param.getOrder().getCeeaOpinion(),LocaleHandler.getLocaleMsg("起草人节点意见不可为空"));

		/*计算 合计数量，合计金额含税，合计金额不含税*/
		if(order.getCeeaTotalNum() == null){
			order.setCeeaTotalNum(BigDecimal.ZERO);
		}
		if(order.getCeeaTaxAmount() == null){
			order.setCeeaTaxAmount(BigDecimal.ZERO);
		}
		if(order.getCeeaNoTaxAmount() == null){
			order.setCeeaNoTaxAmount(BigDecimal.ZERO);
		}
		BigDecimal totalNum = BigDecimal.ZERO;
		BigDecimal taxAmount = BigDecimal.ZERO;
		BigDecimal noTaxAmount = BigDecimal.ZERO;

		/*修改订单*/
		List list = new ArrayList();
		List<OrderDetail> orderDetailUpdates = new ArrayList<>();
		for(OrderDetail item: orderDetailList){
			OrderDetail orderDetail = supcooperateClient.getOrderDetail(new OrderDetail().setOrderDetailId(item.getOrderDetailId()));

			/*计算税额，含税金额，不含税金额*/
			if(item.getOrderNum() == null){
				item.setOrderNum(BigDecimal.ZERO);
			}
			if(orderDetail.getCeeaUnitNoTaxPrice() == null){
				orderDetail.setCeeaUnitNoTaxPrice(BigDecimal.ZERO);
			}
			if(orderDetail.getCeeaUnitTaxPrice() == null){
				orderDetail.setCeeaUnitNoTaxPrice(BigDecimal.ZERO);
			}
			BigDecimal ceeaAmountIncludingTax = orderDetail.getCeeaUnitTaxPrice().multiply(item.getOrderNum());  //含税金额
			BigDecimal ceeaAmountExcludingTax = orderDetail.getCeeaUnitNoTaxPrice().multiply(item.getOrderNum());  //不含税金额
			BigDecimal ceeaTaxAmount = ceeaAmountIncludingTax.subtract(ceeaAmountExcludingTax);  //税额
			totalNum = totalNum.add(item.getOrderNum());
			taxAmount = taxAmount.add(ceeaAmountIncludingTax);
			noTaxAmount = noTaxAmount.add(ceeaAmountExcludingTax);

			orderDetailUpdates.add(
					new OrderDetail()
							.setOrderDetailId(item.getOrderDetailId())
							.setOrderNum(item.getOrderNum())
							.setCeeaPromiseReceiveDate(item.getCeeaPromiseReceiveDate())
							.setCeeaAmountExcludingTax(ceeaAmountExcludingTax.setScale(2,BigDecimal.ROUND_HALF_UP))
							.setCeeaAmountIncludingTax(ceeaAmountIncludingTax.setScale(2,BigDecimal.ROUND_HALF_UP))
							.setCeeaTaxAmount(ceeaTaxAmount.setScale(2,BigDecimal.ROUND_HALF_UP))
							.setBeforeUpdateQuantity(orderDetail.getCeeaApprovedNum())
			);
		}

		Order updateOrder = new Order()
				.setOrderId(order.getOrderId())
				.setOrderStatus(PurchaseOrderEnum.UNDER_APPROVAL.getValue())
				.setCeeaTotalNum(totalNum)
				.setCeeaTaxAmount(taxAmount)
				.setCeeaNoTaxAmount(noTaxAmount);
		if(!Objects.equals(param.getOrder().getCeeaOpinion() , order.getCeeaOpinion()) ||
				!Objects.equals(param.getOrder().getCeeaReceiveAddress() , order.getCeeaReceiveAddress())){
			updateOrder.setCeeaOpinion(param.getOrder().getCeeaOpinion());
			updateOrder.setCeeaReceiveAddress(param.getOrder().getCeeaReceiveAddress());
		}

		/*释放订单持有的采购申请行数量*/
		if(CollectionUtils.isEmpty(param.getDetailList())){
			throw new BaseException("订单物料明细不可为空");
		}
		for(int i=0;i<param.getDetailList().size();i++){
			OrderDetail orderDetail = param.getDetailList().get(i);
			if(Objects.isNull(orderDetail.getCeeaRequirementLineId())){
				throw new BaseException("订单行ceeaRequirementLineId不可为空");
			}
			if(Objects.isNull(orderDetail.getOrderNum())){
				throw new BaseException("订单行orderNum不可为空");
			}
		}
		updateRequirementLineQuantitys(param,false);


		supcooperateClient.saveOrder(updateOrder);
		supcooperateClient.batchUpdateOrderDetail(orderDetailUpdates);
		/*更改订单附件*/
		supcooperateClient.deleteOrderAttachByOrderId(order.getOrderId());
		List<OrderAttach> orderAttachList = param.getAttachList();
		orderAttachList.stream().forEach(item -> {
			item.setOrderId(order.getOrderId());
			item.setAttachId(IdGenrator.generate());
			supcooperateClient.saveOrderAttach(item);
		});
	}

	/**
	 * 编辑订单-审批
	 * 如果是需要供方确认
	 *  可修改的字段
	 *    订单头 【订单状态】
	 *    订单行 【审批通过的数量】
	 *
	 * 如果是不需要供方确认
	 *  可修改的字段
	 *    订单头【订单状态】
	 *    订单行 【审批通过的数量】
	 * 	修改采购申请行的数量 释放之前的订单数量，重新扣减现有的订单数量
	 * 	判断是否要推送erp
	 */
	@Override
	@Transactional
	public void approvalInEditStatus(OrderSaveRequestDTO param){
		/*校验数据*/
		Order order = supcooperateClient.getOrderById(param.getOrder().getOrderId());
		Assert.notNull(order,LocaleHandler.getLocaleMsg("找不到订单"));
		Assert.isTrue(PurchaseOrderEnum.UNDER_APPROVAL.getValue().equals(order.getOrderStatus()),LocaleHandler.getLocaleMsg("只能审批已提交的订单"));
		List<OrderDetail> detailList = supcooperateClient.getOrderDetailByOrderId(order.getOrderId());
		if(order.getCeeaIfSupplierConfirm().equals("Y")){
			/*需要供方确认*/
			/*更新订单数据*/
			List<OrderDetail> orderDetailUpdate = new ArrayList<>();
			for(OrderDetail orderDetail:detailList){
				orderDetailUpdate.add(new OrderDetail()
						.setOrderDetailId(orderDetail.getOrderDetailId())
						.setOrderNum(orderDetail.getOrderNum())
						.setCeeaApprovedNum(orderDetail.getOrderNum())  //审批通过的数量
				);
			}
			if(!CollectionUtils.isEmpty(orderDetailUpdate)){
				supcooperateClient.batchUpdateOrderDetail(orderDetailUpdate);
			}
			String orderStatus = PurchaseOrderEnum.APPROVED.getValue();
			supcooperateClient.saveOrder(new Order().setOrderId(order.getOrderId())
					.setOrderStatus(orderStatus)
					.setCeeaIfEditDone("Y"));

		}else{
			/*不需供方确认*/
			/*判断是否要推送erp*/
			if(StringUtils.equals(order.getIsSendErp(),YesOrNo.YES.getValue())){
				cancelPurchaseOrderSoapBiz(order);
			}
			/*修改采购申请的数量*/
			OrderSaveRequestDTO orderSaveRequestDTO = new OrderSaveRequestDTO();
			orderSaveRequestDTO.setOrder(new Order().setOrderId(order.getOrderId()));
			orderSaveRequestDTO.setDetailList(detailList);
			updateRequirementLineQuantitys(orderSaveRequestDTO,false);

			/*更新订单数据*/
			List<OrderDetail> orderDetailUpdate = new ArrayList<>();
			for(OrderDetail orderDetail:detailList){
				orderDetailUpdate.add(new OrderDetail()
						.setOrderDetailId(orderDetail.getOrderDetailId())
						.setOrderNum(orderDetail.getOrderNum())
						.setCeeaApprovedNum(orderDetail.getOrderNum())  //审批通过的数量
				);
			}
			if(!CollectionUtils.isEmpty(orderDetailUpdate)){
				supcooperateClient.batchUpdateOrderDetail(orderDetailUpdate);
			}
			String orderStatus = PurchaseOrderEnum.ACCEPT.getValue();
			supcooperateClient.saveOrder(new Order().setOrderId(order.getOrderId())
					.setOrderStatus(orderStatus)
					.setCeeaIfEditDone("Y"));

		}
	}

	/**
	 * 编辑订单-驳回
	 */
	@Override
	@Transactional
	public void rejectInEditStatus(OrderSaveRequestDTO param){
		/*校验数据*/
		Order order = supcooperateClient.getOrderById(param.getOrder().getOrderId());
		Assert.notNull(order,LocaleHandler.getLocaleMsg("找不到订单"));
		Assert.isTrue(PurchaseOrderEnum.UNDER_APPROVAL.getValue().equals(order.getOrderStatus()),LocaleHandler.getLocaleMsg("只能驳回审批中的订单"));
		/*设置属性*/
		List list = new ArrayList();
		list.add(new Order().setOrderId(order.getOrderId())
				.setOrderStatus(PurchaseOrderEnum.REJECT.getValue())
		);
		/*更改订单*/
		supcooperateClient.batchUpdateOrder(list);
	}

	@Override
	public void withdrawInEditStatus(OrderSaveRequestDTO param) throws Exception{
		Order order = param.getOrder();
		Assert.isTrue(PurchaseOrderEnum.UNDER_APPROVAL.getValue().equals(order.getOrderStatus()),"只能驳回审批中的订单");
		order.setOrderStatus(PurchaseOrderEnum.WITHDRAW.getValue());
		supcooperateClient.saveOrder(order);
	}

	/**
	 * 编辑订单-供方确认
	 * 可变更的字段
	 *   订单头【订单状态】
	 * 推送erp
	 * 修改采购申请行的数量 释放之前的订单数量，重新扣减现有的订单数量
	 */
	@Override
	@Transactional
	public void supplierConfirmInEditStatus(OrderSaveRequestDTO param){
		/*校验数据*/
		Order order = supcooperateClient.getOrderById(param.getOrder().getOrderId());
		Assert.notNull(order,LocaleHandler.getLocaleMsg("找不到订单"));
		Assert.isTrue(PurchaseOrderEnum.APPROVED.getValue().equals(order.getOrderStatus()),LocaleHandler.getLocaleMsg("只能确定审批通过的订单"));
		List<OrderDetail> detailList = supcooperateClient.getOrderDetailByOrderId(order.getOrderId());
		/*判断是否要推送erp*/
		if(StringUtils.equals(order.getIsSendErp(),YesOrNo.YES.getValue())){
			cancelPurchaseOrderSoapBiz(order);
		}

		/*修改采购申请数量*/
		OrderSaveRequestDTO orderSaveRequestDTO = new OrderSaveRequestDTO();
		orderSaveRequestDTO.setOrder(new Order().setOrderId(order.getOrderId()));
		orderSaveRequestDTO.setDetailList(detailList);
		updateRequirementLineQuantitys(orderSaveRequestDTO,false);

		/*更新订单信息*/
		List list = new ArrayList();
		list.add(new Order().setOrderId(order.getOrderId())
				.setOrderStatus(PurchaseOrderEnum.ACCEPT.getValue())
		);
		supcooperateClient.batchUpdateOrder(list);
		supcooperateClient.deleteOrderAttachByOrderId(order.getOrderId());
		List<OrderAttach> orderAttachList = param.getAttachList();
		orderAttachList.stream().forEach(item -> {
			item.setAttachId(IdGenrator.generate());
			item.setOrderId(param.getOrder().getOrderId());
			supcooperateClient.saveOrderAttach(item);
		});
	}

	/**
	 * 编辑订单-供方拒绝
	 */
	@Override
	@Transactional
	public void supplierRejectInEditStatus(OrderSaveRequestDTO param){
		/*校验数据*/
		Order order = supcooperateClient.getOrderById(param.getOrder().getOrderId());
		Assert.notNull(order,LocaleHandler.getLocaleMsg("找不到订单"));
		Assert.isTrue(PurchaseOrderEnum.APPROVED.getValue().equals(order.getOrderStatus()),LocaleHandler.getLocaleMsg("只能拒绝审批通过的订单"));
		/*设置属性*/
		LoginAppUser loginAppUser = AppUserUtil.getLoginAppUser();
		Date date = new Date();
		List list = new ArrayList();
		list.add(new Order().setOrderId(order.getOrderId())
				.setLastUpdateDate(date)
				.setLastUpdatedBy(loginAppUser.getNickname())
				.setLastUpdatedId(loginAppUser.getUserId())
				.setRefuseReason(param.getOrder().getRefuseReason())
				.setOrderStatus(PurchaseOrderEnum.REFUSED.getValue())
		);
		supcooperateClient.batchUpdateOrder(list);
	}

	/**
	 * 校验是否为可编辑状态
	 * 检验订单行是否已经都接收完成
	 * @param order
	 */
	public void checkIfEdit(Order order){
		/*校验是否为可编辑状态*/
		if(!order.getOrderStatus().equals(PurchaseOrderEnum.ACCEPT.getValue())){
			Assert.notNull(null,"订单状态为【" + order.getOrderStatus() + "】不允许编辑");
		}
		List<OrderDetail> detailList = supcooperateClient.getOrderDetailByOrderId(order.getOrderId());

		/*检验订单行是否已经都接收完成*/
		boolean allowChange = true;
		for(OrderDetail orderDetail:detailList){
			if(orderDetail.getReceivedQuantity() == null){
				orderDetail.setReceivedQuantity(BigDecimal.ZERO);
			}
			if(orderDetail.getOrderNum() == null){
				orderDetail.setOrderNum(BigDecimal.ZERO);
			}
			if(orderDetail.getReceiveNum() == null){
				orderDetail.setReceiveNum(BigDecimal.ZERO);
			}

			PurchaseCategory purchaseCategory = baseClient.queryMaxLevelCategory(
					new PurchaseCategory().setCategoryId(orderDetail.getCategoryId()));
			boolean bigCategoryServerFlag = CategoryEnum.BIG_CATEGORY_SERVER.getCategoryCode()
					.equals(purchaseCategory.getCategoryCode());
			//接收/验收 小于订单数量时 允许变更
			if(bigCategoryServerFlag){
				allowChange = orderDetail.getReceiveNum().compareTo(orderDetail.getOrderNum()) < 0;
			}else{
				allowChange = orderDetail.getReceivedQuantity().compareTo(orderDetail.getOrderNum()) < 0;
			}
		}
		if(!allowChange){
			throw new BaseException(LocaleHandler.getLocaleMsg("您选择的订单已全部接收完成，不允许变更"));
		}

	}

	/**
	 * 绑定合同
	 *
	 * @param verifyQuotaResult
	 */
	private void bindingContract(VerifyQuotaResult verifyQuotaResult) {
		List<ContractMaterialDTO> contractMaterialDTOList = new ArrayList<ContractMaterialDTO>();
		for (OrderDetail d : verifyQuotaResult.getOrderDetailResultList()) {
			if (d.getExternalType() != null && ExternalTypeEnum.get(d.getExternalType()) == ExternalTypeEnum.CONTRACT) {
				ContractMaterialDTO t = new ContractMaterialDTO();
				t.setLineNumber(d.getExternalRowNum());
				t.setContractHeadId(d.getExternalId());
				t.setMaterialId(d.getMaterialId());
				t.setOrderNumber(verifyQuotaResult.getOrderMap().get(d.getOrderId()).getOrderNumber());
				t.setOrderLineNumber(d.getLineNum().longValue());
			}
		}
		if (contractMaterialDTOList.size() > 0) {
			contractClient.updateContractMaterials(contractMaterialDTOList);
		}
	}

	@Data
	@Accessors(chain = true)
	public static class VerifyQuotaResult {
		private Map<Long, Order> orderMap;
		private Map<Long, List<OrderDetail>> orderDetailMap;
		private List<Order> orderResultList;
		private List<OrderDetail> orderDetailResultList;
	}

	/**
	 * 校验配额
	 *
	 * @param orderIdList
	 * @return
	 */
	private VerifyQuotaResult verifyQuota(List<Long> orderIdList) {
		List<Order> orderList = supcooperateClient.getOrderByIds(orderIdList);
		List<OrderDetail> externalOrderDetailList = supcooperateClient.getOrderDetailByOrderIds(orderIdList);
		Map<Long, Order> orderResultMap = orderList.stream().collect(Collectors.toMap(Order::getOrderId, Function.identity(), (k1, k2) -> k1));
		Map<Long, List<OrderDetail>> orderDetailResultMap = externalOrderDetailList.stream().collect(Collectors.groupingBy(OrderDetail::getOrderId));
		// TODO 校验配额
		return new VerifyQuotaResult().setOrderMap(orderResultMap).setOrderDetailMap(orderDetailResultMap).setOrderResultList(orderList).setOrderDetailResultList(externalOrderDetailList);
	}

	@Override
	public void acceptReply(Long orderId) {
		Order order = supcooperateClient.getOrderById(orderId);
		List<OrderDetail> orderDetailByOrderId = supcooperateClient.getOrderDetailByOrderId(orderId);
		order.setOrderStatus(com.midea.cloud.common.enums.pm.po.PurchaseOrderEnum.ACCEPT.getValue());
		for (OrderDetail orderDetail : orderDetailByOrderId) {
			orderDetail.setOrderDetailStatus(PurchaseOrderRowStatusEnum.ACCEPTED.getValue());
		}
		supcooperateClient.saveOrder(order);
		supcooperateClient.batchUpdateOrderDetail(orderDetailByOrderId);
	}

	/*todo 因为流程修改了*/
	@Override
	public void returnOrder(ReturnOrderDTO returnOrderDTO) {
		Order order = supcooperateClient.getOrderById(returnOrderDTO.getOrderId());
		order.setOrderStatus(com.midea.cloud.common.enums.pm.po.PurchaseOrderEnum.REFUSED.getValue());
		order.setPurchaseResponse(returnOrderDTO.getPurchaseResponse());
		supcooperateClient.saveOrder(order);
	}

	@Override
	public OrderSaveRequestDTO queryOrderById(Long orderId) {
		Order order = supcooperateClient.getOrderById(orderId);
		List<OrderDetail> orderDetailList = supcooperateClient.getOrderDetailByOrderId(orderId);
		List<OrderAttach> orderAttachList = supcooperateClient.getOrderAttachByOrderId(orderId);
		List<OrderPaymentProvision> paymentProvisionList = supcooperateClient.getOrderPaymentProvisionByOrderId(orderId);

		WarehousingReturnDetailRequestDTO warehousingReturnDetailRequestDTO = new WarehousingReturnDetailRequestDTO();
		warehousingReturnDetailRequestDTO.setOrderNumber(order.getOrderNumber());
		List<WarehousingReturnDetail> warehousingReturnDetail = supcooperateClient.warehousingReturnDetailList(warehousingReturnDetailRequestDTO);

		return new OrderSaveRequestDTO()
				.setOrder(order)
				.setDetailList(orderDetailList)
				.setAttachList(orderAttachList)
				.setPaymentProvisionList(paymentProvisionList)
				.setWarehousingReturnDetails(warehousingReturnDetail);
	}

	/**
	 * 检验合作组织的有效性
	 *
	 * @return
	 */
	private static boolean checkOrgEffective(OrgInfo orgInfo) {
		if (orgInfo == null || orgInfo.getStartDate() == null || !StringUtils.equals(orgInfo.getServiceStatus(), OrgStatus.EFFECTIVE.name())) {
			return false;
		}
		LocalDate now = LocalDate.now();
		if (orgInfo.getStartDate().isAfter(now)) {
			return false;
		}
		if (orgInfo.getEndDate() != null && (now.isAfter(orgInfo.getEndDate()) || now.isEqual(orgInfo.getEndDate()))) {
			return false;
		}
		return true;
	}

	/**
	 *	采购订单变更推送
	 *
	 * @param order 订单信息
	 */
	private void cancelPurchaseOrderSoapBiz(Order order) throws BaseException{
		// 修改密码接口地址
		String address = this.erpPurchaseUrl2;
		/*String address = PoSoapUrl.urlEditPurchaseOrder;*/
		// 代理工厂
		JaxWsProxyFactoryBean jaxWsProxyFactoryBean = new JaxWsProxyFactoryBean();
		jaxWsProxyFactoryBean.getOutInterceptors().add(new LoggingOutInterceptor());
		jaxWsProxyFactoryBean.getInInterceptors().add(new LoggingInInterceptor());
		// 设置代理地址
		jaxWsProxyFactoryBean.setAddress(address);
		jaxWsProxyFactoryBean.setUsername(this.erpName2);
		jaxWsProxyFactoryBean.setPassword(this.erpPassword2);
		// 设置接口类型
		jaxWsProxyFactoryBean.setServiceClass(ErpCancelPurchaseOrderSoapBizPtt.class);
		// 创建一个代理接口实现
		ErpCancelPurchaseOrderSoapBizPtt service = (ErpCancelPurchaseOrderSoapBizPtt) jaxWsProxyFactoryBean.create();
		APPSCUXPOORDERSMOX1362358X4X8 ppoinfotblitem = new APPSCUXPOORDERSMOX1362358X4X8();
		//订单编号
		ppoinfotblitem.setPONUMBER(order.getOrderNumber());
		Organization organization = baseClient.getOrganizationByParam(new Organization().setOrganizationId(order.getCeeaOrgId()));
		if(ObjectUtils.isEmpty(organization) || StringUtils.isBlank(organization.getErpOrgId())){
			new BaseException("订单变更同步失败，业务实体中ERPID不存在;");
		}
		ppoinfotblitem.setOPERATIONUNITID(new BigDecimal(organization.getErpOrgId()));
		ppoinfotblitem.setOPERATIONNAME(order.getCeeaOrgName());
		//订单类型
		ppoinfotblitem.setDOCUMENTTYPE(order.getOrderType());
		ppoinfotblitem.setSOURCESYSCODE(DataSourceEnum.NSRM_SYS.getKey());  //来源系统编码
		ppoinfotblitem.setSOURCEHEADERID(StringUtil.StringValue(order.getOrderId()));  //来源系统行Id
		ppoinfotblitem.setIFACECODE("PUR_PO_EXP");
		//订单详情
		List<OrderDetail> orderDetailList = supcooperateClient.getOrderDetailByOrderId(order.getOrderId());
		List<APPSCUXPOORDERSMX1362358X4X14> linedetailsitemList = new ArrayList<>();
		Iterator<OrderDetail> iterable = orderDetailList.iterator();
		while(iterable.hasNext()){
			OrderDetail orderDetail = iterable.next();
			APPSCUXPOORDERSMX1362358X4X14 linedetailsitem = new APPSCUXPOORDERSMX1362358X4X14();
			linedetailsitem.setLINESTATUS(orderDetail.getCeeaUsingStatus());
			linedetailsitem.setPOLINENUMBER(orderDetail.getLineNum() == null ? null : new BigDecimal(orderDetail.getLineNum()));
			linedetailsitem.setPROMISEDDATE(DateUtil.format(orderDetail.getCeeaPromiseReceiveDate() , "yyyy-MM-dd"));
			linedetailsitem.setQUANTITYRECEIVED(orderDetail.getOrderNum());
			linedetailsitem.setSOURCELINEID(StringUtil.StringValue(orderDetail.getOrderDetailId()));
			linedetailsitemList.add(linedetailsitem);
		}
		APPSCUXPOORDERSMX1362358X4X13 linedetail = new APPSCUXPOORDERSMX1362358X4X13();
		linedetail.setLINEDETAILSITEM(linedetailsitemList);
		ppoinfotblitem.setLINEDETAILS(linedetail);

		List<APPSCUXPOORDERSMOX1362358X4X8> ppoinfotblitemList = new ArrayList<>();
		ppoinfotblitemList.add(ppoinfotblitem);

		APPSCUXPOORDERSMOX1362358X4X7 ppoinfotbl = new APPSCUXPOORDERSMOX1362358X4X7();
		ppoinfotbl.setPPOINFOTBLITEM(ppoinfotblitemList);

		OrderChangeInputParameters orderChangeInputParameters = new OrderChangeInputParameters();
		orderChangeInputParameters.setPPOINFOTBL(ppoinfotbl);

		APPSCUXPOORDERSMOX1362358X4X1 pesbinforec = new APPSCUXPOORDERSMOX1362358X4X1();
		orderChangeInputParameters.setPESBINFOREC(pesbinforec);


		OrderChangeOutputParameters response = service.erpCancelPurchaseOrderSoapBiz(orderChangeInputParameters);
		log.info("erp取消采购订单接口返回数据：{}", JsonUtil.entityToJsonStr(response));
		if(response.getXESBRESULTINFOREC().getRETURNSTATUS().equals("E")) {
			throw new BaseException(LocaleHandler.getLocaleMsg(response.getXESBRESULTINFOREC().getRETURNMSG()));
		}
	}


	@Override
	@Transactional
	public void cancelPurchaseOrderSoapBiz(Long orderId) {

		Order order = supcooperateClient.getOrderById(orderId);
		Assert.notNull(order,LocaleHandler.getLocaleMsg("找不到订单"));
//		Assert.isTrue(PurchaseOrderEnum.APPROVED.getValue().equals(order.getOrderStatus()),LocaleHandler.getLocaleMsg("只能确定审批通过的订单"));
		//推送 取消采购订单
		cancelPurchaseOrderSoapBiz(order);
	}

	@Override
	public void manualPush(List<String> orderNumbers) {
		//获取外围系统数据 OrderList
		List<Order> orderList = supcooperateClient.batchGetOrderIdsByOrderNumbers(orderNumbers);
		for(int i=0;i<orderList.size();i++){
			Order order = orderList.get(i);
			PurchaseOutputParameters result = pushErp(order);
			if("S".equals(result.getXesbresultinforec().getReturnstatus())){
				/*supcooperateClient.updateOrderStatus(order);*/
			}else{
				throw new BaseException("推送失败");
			}
		}
	}

	public PurchaseOutputParameters pushErp(Order order){
		//给erp推送采购订单
		// 创建主账号接口地址
		String address = this.erpPurchaseUrl;
		// 代理工厂
		JaxWsProxyFactoryBean jaxWsProxyFactoryBean = new JaxWsProxyFactoryBean();
		// 设置代理地址
		jaxWsProxyFactoryBean.setAddress(address);
		jaxWsProxyFactoryBean.setUsername(this.erpName);
		jaxWsProxyFactoryBean.setPassword(this.erpPassword);
		// 设置接口类型
		jaxWsProxyFactoryBean.setServiceClass(ErpAcceptPurchaseOrderSoapBizPttBindingQSService.class);
		// 创建一个代理接口实现
		ErpAcceptPurchaseOrderSoapBizPttBindingQSService service = (ErpAcceptPurchaseOrderSoapBizPttBindingQSService) jaxWsProxyFactoryBean.create();
		OrderSaveRequestDTO orderSaveRequestDTO = this.queryOrderById(order.getOrderId());
		if(null == orderSaveRequestDTO.getOrder() || CollectionUtils.isEmpty(orderSaveRequestDTO.getDetailList())){
			throw new BaseException(LocaleHandler.getLocaleMsg("orderId = " + order.getOrderId() + " 查询不到订单或者订单明细"));
		}
		PurchaseInputParameters purchaseInputParameters = buildPurchaseInputParameters(orderSaveRequestDTO);
		log.info("请求的报文是：" + purchaseInputParameters);


		// ------------------------记录推送记录-------------------------
		InterfaceLogDTO interfaceLogDTO = new InterfaceLogDTO();
		try {
			interfaceLogDTO.setServiceInfo(JSON.toJSONString(purchaseInputParameters)); // 请求参数
		} catch (Exception e) {
			log.error("记录供方确正式开始执行推送ERP请求参数报错{}"+e);
		}
		interfaceLogDTO.setCreationDateBegin(new Date()); //创建开始时间
		interfaceLogDTO.setServiceName("供方确正式开始执行推送ERP"); // 服务名字
		interfaceLogDTO.setServiceType("WEBSERVICE"); // 请求方式
		interfaceLogDTO.setType("SEND"); // 发送方式
		interfaceLogDTO.setBillType("采购订单"); // 单据类型
		interfaceLogDTO.setBillId(order.getOrderNumber());
		interfaceLogDTO.setTargetSys("ERP");
		// 正式业务
		PurchaseOutputParameters result = null;
		try {
			// 正式业务
			result = service.erpAcceptPurchaseOrderSoapBiz(purchaseInputParameters);
			try {
				interfaceLogDTO.setReturnInfo(JSON.toJSONString(result)); // 返回参数
			} catch (Exception e) {
				log.error("记录<供方确正式开始执行推送ERP>返回参数报错{}"+e);
			}
			interfaceLogDTO.setStatus("SUCCESS");
			if(!ObjectUtils.isEmpty(result) && !"S".equals(result.getXesbresultinforec().getReturnstatus())){
				interfaceLogDTO.setErrorInfo(result.getXesbresultinforec().getReturnmsg());
				interfaceLogDTO.setStatus("FAIL");
			}
		} catch (Exception e) {
			// 堆栈错误信息
			String stackTrace = Arrays.toString(e.getStackTrace());
			// 错误信息
			String message = e.getMessage();
			ConcurrentHashMap<String, String> errorMsg = new ConcurrentHashMap<>();
			errorMsg.put("message", e.getClass().getName() + ": " + message);
			errorMsg.put("stackTrace", stackTrace);
			interfaceLogDTO.setErrorInfo(JSON.toJSONString(errorMsg));
			interfaceLogDTO.setStatus("FAIL");
			throw e;
		}finally {
			interfaceLogDTO.setCreationDateEnd(new Date()); // 结束时间
			try {
				apiClient.createInterfaceLog(interfaceLogDTO);
			} catch (Exception e) {
				log.error("保存<供方确正式开始执行推送ERP>日志报错{}"+e);
			}
		}

		// ------------------------------------------------------------
		log.info("返回的结果是：" +result.getXesbresultinforec());

		return result;
	}

	/**
	 * <pre>
	 *  字段赋值，保存，提交的时候，检查物料行中有无服务类，有 为N，没有Y
	 * </pre>
	 *
	 * @author chenwt24@meicloud.com
	 *
	 * <pre>
	 *  修改记录
	 *  修改后版本:
	 *  修改人:
	 *  修改日期: 2020-10-08
	 *  修改内容:
	 * </pre>
	 */
	private String checkHavingServiceClass(List<PurchaseCategory> purchaseCategoryList){
		if(purchaseCategoryList.size()==0){
			return null;
		}
		String isSendErp = YesOrNo.YES.getValue();
		for (PurchaseCategory purchaseCategory:purchaseCategoryList){
			if(!ObjectUtils.isEmpty(purchaseCategory) && StringUtils.equals(purchaseCategory.getCategoryCode(), CategoryTypeEnum.SERVICE.getCode())){
				isSendErp = YesOrNo.NO.getValue();
				break;
			}
		}
		return isSendErp;
	}

	/**
	 * <pre>
	 *  校验，订单提交的时候，校验 服务类和非服务类不能共存
	 * </pre>
	 *
	 * @author chenwt24@meicloud.com
	 * @version 1.00.00
	 *
	 * <pre>
	 *  修改记录
	 *  修改后版本:
	 *  修改人:
	 *  修改日期: 2020-10-08
	 *  修改内容:
	 * </pre>
	 */
	private boolean isAllServiceClassOrNot(List<String> categoryCodes){
		//判断服务类和非服务类不能共存的逻辑，
		// 先查出第一行的类别，如果是服务类，后续如果有非服务类，返回false
		// 如果第一行为非服务类，后续有服务类，返回false
		// 否则返回ture

		if(categoryCodes.size()<2){
			return true;
		}

		String categoryCode = categoryCodes.get(0);
		if(StringUtils.equals(categoryCode,CategoryTypeEnum.SERVICE.getCode())){
			for (int i = 1; i < categoryCodes.size(); i++) {
				String categoryCodeItem = categoryCodes.get(i);
				if(!StringUtil.isEmpty(categoryCodeItem) && !StringUtils.equals(categoryCodeItem, CategoryTypeEnum.SERVICE.getCode())){
					return false;
				}
			}
		}else {
			for (int i = 1; i < categoryCodes.size(); i++) {
				String categoryCodeItem = categoryCodes.get(i);
				if(!StringUtil.isEmpty(categoryCodeItem) && StringUtils.equals(categoryCodeItem, CategoryTypeEnum.SERVICE.getCode())){
					return false;
				}
			}
		}
		return true;
	}


	/**
	 * 变更 采购申请行数量
	 * @param orderSaveRequestDTO
	 * @param ifAdd  是否为新增 true-新增 false-修改
	 */
	public void updateRequirementLineQuantitys(OrderSaveRequestDTO orderSaveRequestDTO,boolean ifAdd){
		List<OrderDetail> orderDetailList = orderSaveRequestDTO.getDetailList();
		List<RequirementLine> requirementLineUpdate = new ArrayList<>();
		Boolean isLock = redisUtil.tryLock("updateRequirementLineQuantitys" + orderSaveRequestDTO.getOrder().getOrderId(),20, TimeUnit.MINUTES);
		if(!isLock){
			throw new BaseException("当前单据已被占用，请稍后再试");
		}
		try{
			/*将当前订单明细持有的数量释放*/
			if(!ifAdd){
				List<RequirementLine> requirementLineRelease = new ArrayList<>();
				List<OrderDetail> oldOrderDetailList = supcooperateClient.getOrderDetailByOrderId(orderSaveRequestDTO.getOrder().getOrderId());
				for(OrderDetail orderDetail:oldOrderDetailList){
					if("Y".equals(orderDetail.getCeeaIfRequirement())){
						if(orderDetail.getCeeaRequirementLineId() != null){
							RequirementLine requirementLine = iRequirementLineService.getById(orderDetail.getCeeaRequirementLineId());
							if(requirementLine != null){
								BigDecimal orderQuantity = requirementLine.getOrderQuantity().add(orderDetail.getOrderNum());
								BigDecimal ceeaExecutedQuantity = requirementLine.getCeeaExecutedQuantity().subtract(orderDetail.getOrderNum());
								requirementLineRelease.add(new RequirementLine()
										.setRequirementLineId(requirementLine.getRequirementLineId())
										.setOrderQuantity(orderQuantity)      //剩余可下单数量
										.setCeeaExecutedQuantity(ceeaExecutedQuantity)    //已执行数量
								);
							}
						}
					}
				}
				if(!CollectionUtils.isEmpty(requirementLineRelease)){
					iRequirementLineService.updateBatchById(requirementLineRelease);
				}

			}

			/*重新扣减采购申请数量*/
			for(OrderDetail orderDetail:orderDetailList){
				if("Y".equals(orderDetail.getCeeaIfRequirement())){
					if(orderDetail.getCeeaRequirementLineId() != null){
						RequirementLine requirementLine = iRequirementLineService.getById(orderDetail.getCeeaRequirementLineId());
						if(requirementLine != null){
							BigDecimal orderQuantity = requirementLine.getOrderQuantity().subtract(orderDetail.getOrderNum());
							BigDecimal ceeaExecutedQuantity = requirementLine.getCeeaExecutedQuantity().add(orderDetail.getOrderNum());
							/*判断数量是否足够*/
							if(orderQuantity.compareTo(BigDecimal.ZERO) == -1){
								log.info("采购申请数量不足：【申请编号】 = " + requirementLine.getRequirementHeadNum() + ";【行号】 = " + requirementLine.getRowNum() + "; 当前数量为：" + requirementLine.getOrderQuantity());
								throw new BaseException(LocaleHandler.getLocaleMsg("采购申请数量不足：【申请编号】 = " + requirementLine.getRequirementHeadNum() + ";【行号】 = " + requirementLine.getRowNum() + ";"));
							}

							requirementLineUpdate.add(new RequirementLine()
									.setRequirementLineId(requirementLine.getRequirementLineId())
									.setOrderQuantity(orderQuantity)  //剩余可下单数量
									.setCeeaExecutedQuantity(ceeaExecutedQuantity)  //已执行数量
							);
						}
					}
				}
			}
			if(!CollectionUtils.isEmpty(requirementLineUpdate)){
				iRequirementLineService.updateBatchById(requirementLineUpdate);
			}
		}finally {
			redisUtil.unLock("updateRequirementLineQuantitys" + orderSaveRequestDTO.getOrder().getOrderId());
		}
	}




}
