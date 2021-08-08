package com.midea.cloud.srm.base.workflow.consumer.service.impl;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;

import com.midea.cloud.common.enums.bargaining.projectmanagement.projectpublish.BiddingApprovalStatus;
import com.midea.cloud.common.enums.bpm.TempIdToModuleEnum;
import com.midea.cloud.srm.base.workflow.consumer.service.IFlowService;
import com.midea.cloud.srm.feign.bid.BidClient;
import com.midea.cloud.srm.feign.contract.ContractClient;
import com.midea.cloud.srm.feign.inq.InqClient;
import com.midea.cloud.srm.feign.perf.PerformanceClient;
import com.midea.cloud.srm.feign.pm.PmClient;
import com.midea.cloud.srm.feign.supplier.SupplierClient;
import com.midea.cloud.srm.feign.supplierauth.SupplierAuthClient;
import com.midea.cloud.srm.model.bargaining.purchaser.projectmanagement.bidinitiating.entity.Biding;
import com.midea.cloud.srm.model.cm.accept.dto.AcceptDTO;
import com.midea.cloud.srm.model.cm.accept.dto.AcceptOrderDTO;
import com.midea.cloud.srm.model.cm.accept.entity.AcceptOrder;
import com.midea.cloud.srm.model.cm.contract.entity.ContractHead;
import com.midea.cloud.srm.model.inq.price.entity.ApprovalHeader;
import com.midea.cloud.srm.model.inq.price.vo.ApprovalAllVo;
import com.midea.cloud.srm.model.perf.vendorasses.VendorAssesForm;
import com.midea.cloud.srm.model.supplier.change.dto.ChangeInfoDTO;
import com.midea.cloud.srm.model.supplier.change.entity.InfoChange;
import com.midea.cloud.srm.model.supplier.info.dto.InfoDTO;
import com.midea.cloud.srm.model.supplier.info.entity.CompanyInfo;
import com.midea.cloud.srm.model.supplier.vendorimport.dto.VendorImportSaveDTO;
import com.midea.cloud.srm.model.supplier.vendorimport.entity.VendorImport;
import com.midea.cloud.srm.model.supplierauth.review.entity.ReviewForm;
import com.midea.cloud.srm.model.supplierauth.review.entity.SiteForm;
import com.midea.cloud.srm.model.suppliercooperate.order.dto.OrderSaveRequestDTO;
import com.midea.cloud.srm.model.workflow.dto.WorkflowCreateResponseDto;


@Service
public class FlowServiceImpl implements IFlowService{

	@Resource
	private PmClient pmClient;

	@Resource
	private ContractClient contractClient;
	@Resource
	private SupplierAuthClient supplierAuthClient;
	@Resource
	private SupplierClient supplierClient;

	@Resource
	private PerformanceClient performanceClient;
	@Resource
	private com.midea.cloud.srm.feign.bargaining.BidClient bidClient;
	@Resource
	private BidClient bidexClient;
	@Resource
	private InqClient InqClient;
	
	
	public WorkflowCreateResponseDto approved(String valueByCode, Long formInstanceId) throws Exception {
		if (StringUtils.equals(valueByCode, TempIdToModuleEnum.REQUIREMENT.getCode())) {
			doRequirement(formInstanceId);
		} else if (StringUtils.equals(valueByCode, TempIdToModuleEnum.ORDER.getCode())) {
			doOrder(formInstanceId);
		} else if (StringUtils.equals(valueByCode, TempIdToModuleEnum.CONTRACT.getCode())) {
			doContract(formInstanceId);
		} else if (StringUtils.equals(valueByCode, TempIdToModuleEnum.REVIEW.getCode())) {
			doReview(formInstanceId);
		} else if (StringUtils.equals(valueByCode, TempIdToModuleEnum.SUPPLIERAUTH.getCode())) {
			doSupplierauth(formInstanceId);
		} else if (StringUtils.equals(valueByCode, TempIdToModuleEnum.CHANGE.getCode())) {
			doChange(formInstanceId);
		} else if (StringUtils.equals(valueByCode, TempIdToModuleEnum.IMPORT.getCode())) {
			doImport(formInstanceId);
		} else if (StringUtils.equals(valueByCode, TempIdToModuleEnum.ACCEPT.getCode())) {
			doAccept(formInstanceId);
		} else if (StringUtils.equals(valueByCode, TempIdToModuleEnum.COMPANYINFO.getCode())) {
			doCompanyinfo(formInstanceId);
		} else if (StringUtils.equals(valueByCode, TempIdToModuleEnum.VENDORASSES.getCode())) {
			docVendorasses(formInstanceId);
		} else if (StringUtils.equals(valueByCode, TempIdToModuleEnum.FORCOMPARISON.getCode())) {
			doBiding(formInstanceId);
		} else if (StringUtils.equals(valueByCode, TempIdToModuleEnum.BIDING.getCode())) {
			docForComparison(formInstanceId);
		} else if (StringUtils.equals(valueByCode, TempIdToModuleEnum.APPROVAL.getCode())) {
			doApproval(formInstanceId);
		} else {
			Assert.isTrue(false, "ES_RETURN_ID参数模板ID：" + valueByCode + "找不到");
		}
		return WorkflowCreateResponseDto.build().processId(String.valueOf(formInstanceId));
	}
	
	private void approvedThrow(String valueByCode, Long formInstanceId) {
		
	}

	/**
	 * 1、采购申请
	 *
	 * @param header
	 * @throws Exception
	 */
	public void doRequirement(Long formInstanceId) throws Exception {
		pmClient.approval(formInstanceId);
	}

	/**
	 * 2、采购订单
	 *
	 * @param header
	 * @throws Exception
	 */
	public void doOrder(Long formInstanceId) throws Exception {
		OrderSaveRequestDTO orderSaveRequestDTO = pmClient.queryOrderById(formInstanceId);
		Assert.isTrue(!ObjectUtils.isEmpty(orderSaveRequestDTO.getOrder()), "未查询到采购订单，单据id：" + formInstanceId);
		pmClient.approvalInEditStatus(orderSaveRequestDTO);
	}

	/**
	 * 3、合同管理
	 *
	 * @param header
	 * @throws Exception
	 */
	public void doContract(Long formInstanceId) throws Exception {
		ContractHead contractHead = contractClient.getContractDTOSecond(formInstanceId, null).getContractHead();
		Assert.isTrue(!ObjectUtils.isEmpty(contractHead), "未查询到采购订单，单据id：" + formInstanceId);
		contractClient.buyerApprove(formInstanceId);
	}

	/**
	 * 4、供应商资质审查
	 *
	 * @param header
	 * @throws Exception
	 */
	public void doReview(Long formInstanceId) throws Exception {
		ReviewForm reviewForm = supplierAuthClient.getReviewFormDTO(formInstanceId).getReviewForm();
		Assert.isTrue(!ObjectUtils.isEmpty(reviewForm), "未查询到采购订单，单据id：" + formInstanceId);
		supplierAuthClient.ReviewFormPass(reviewForm);
	}

	/**
	 * 5、供应商认证审批
	 *
	 * @param header
	 * @throws Exception
	 */
	public void doSupplierauth(Long formInstanceId) throws Exception {
		SiteForm siteForm = supplierAuthClient.SiteFormGet(formInstanceId);
		Assert.isTrue(!ObjectUtils.isEmpty(siteForm), "未查询到采购订单，单据id：" + formInstanceId);
		supplierAuthClient.SiteFormPass(siteForm);
	}

	/**
	 * 6、供应商信息变更审批（审批通过需要登录人信息问题）
	 *
	 * @param header
	 * @throws Exception
	 */
	public void doChange(Long formInstanceId) throws Exception {
		ChangeInfoDTO changeInfoDTO = supplierClient.getInfoByChangeId(formInstanceId);
		InfoChange infoChange = changeInfoDTO.getInfoChange();
		Assert.isTrue(!ObjectUtils.isEmpty(infoChange), "未查询到采购订单，单据id：" + formInstanceId);
		supplierClient.InfoChangeApprove(changeInfoDTO);
	}

	/**
	 * 7、供应商跨OU引入
	 *
	 * @param header
	 * @throws Exception
	 */
	public void doImport(Long formInstanceId) throws Exception {
		VendorImportSaveDTO vendorImportDetailDTO = supplierClient.getVendorImportDetail(formInstanceId);
		Assert.isTrue(!ObjectUtils.isEmpty(vendorImportDetailDTO), "未查询到供应商跨OU引入单，单据id：" + formInstanceId);
		VendorImport vendorImport = vendorImportDetailDTO.getVendorImport();
		Assert.isTrue(!ObjectUtils.isEmpty(vendorImport), "未查询供应商跨OU引入单，单据id：" + formInstanceId);
		supplierClient.VendorImportApprove(formInstanceId);
	}

	/**
	 * 8、固定资产&服务验收
	 *
	 * @param header
	 * @throws Exception
	 */
	public void doAccept(Long formInstanceId) throws Exception {
		AcceptDTO acceptDTO = contractClient.getAcceptDTO(formInstanceId);
		AcceptOrder acceptOrder = acceptDTO.getAcceptOrder();
		Assert.isTrue(!ObjectUtils.isEmpty(acceptOrder), "未查询到验收单，单据id：" + formInstanceId);
		AcceptOrderDTO acceptOrderDTO = new AcceptOrderDTO();
		BeanUtils.copyProperties(acceptOrder, acceptOrderDTO);
		contractClient.vendorPass(acceptOrderDTO);
	}

	/**
	 * 9、供应商绿色通道审批（审批通过接口需要登录人信息）
	 *
	 * @param header
	 * @throws Exception
	 */
	public void doCompanyinfo(Long formInstanceId) throws Exception {
		InfoDTO infoDTO = supplierClient.getInfoByParam(formInstanceId);
		CompanyInfo companyInfo = infoDTO.getCompanyInfo();
		String status = companyInfo.getStatus();
		Assert.isTrue(!ObjectUtils.isEmpty(companyInfo), "未查询到绿色通道订单，单据id：" + formInstanceId);
		supplierClient.companyGreenChannelApprove(infoDTO);
	}

	/**
	 * 10、供应商考核审批
	 *
	 * @param header
	 * @throws Exception
	 */
	public void docVendorasses(Long formInstanceId) throws Exception {
		VendorAssesForm vendorAssesForm = performanceClient.queryById(formInstanceId);
		Assert.isTrue(!ObjectUtils.isEmpty(vendorAssesForm), "未查询到绩效考核单，单据id：" + formInstanceId);
		performanceClient.VendorAssesFormPass(vendorAssesForm);
	}

	/**
	 * 11、询比价文件审批
	 *
	 * @param header
	 * @throws Exception
	 */
	public void docForComparison(Long formInstanceId) throws Exception {
		Biding forComparison = bidClient.getBargaining(formInstanceId);
		Assert.isTrue(!ObjectUtils.isEmpty(forComparison), "未查询到询比价单，单据id：" + formInstanceId);
		forComparison.setAuditStatus(BiddingApprovalStatus.APPROVED.getValue());
		bidClient.callBackForWorkFlow(forComparison);
	}

	/**
	 * 12、招议标文件审批
	 *
	 * @param header
	 * @throws Exception
	 */
	public void doBiding(Long formInstanceId) throws Exception {
		com.midea.cloud.srm.model.bid.purchaser.projectmanagement.bidinitiating.entity.Biding forComparison = bidexClient
				.getBiding(formInstanceId);
		Assert.isTrue(!ObjectUtils.isEmpty(forComparison), "未查询到询比价单，单据id：" + formInstanceId);
		// Assert.isTrue(com.midea.cloud.common.enums.bid.projectmanagement.projectpublish.BiddingApprovalStatus.SUBMITTED.getValue().equals(forComparison.getAuditStatus()),
		// "只有已提交的单据才可进行审批操作，单据id：" + bidingId);
		forComparison.setAuditStatus(
				com.midea.cloud.common.enums.bid.projectmanagement.projectpublish.BiddingApprovalStatus.APPROVED
						.getValue());
		bidexClient.callBackForWorkFlow(forComparison);
	}

	/**
	 * 13、寻源结果审批(价格审批单)
	 *
	 * @param header
	 * @throws Exception
	 */
	public void doApproval(Long formInstanceId) throws Exception {
		ApprovalAllVo approvalAllVo = InqClient.ceeaGetApprovalDetail(formInstanceId);
		ApprovalHeader approvalHeader = approvalAllVo.getApprovalHeader();
		Assert.isTrue(!ObjectUtils.isEmpty(approvalHeader), "未查询到价格审批单，单据id：" + formInstanceId);
		InqClient.auditPass(formInstanceId);
	}
}
