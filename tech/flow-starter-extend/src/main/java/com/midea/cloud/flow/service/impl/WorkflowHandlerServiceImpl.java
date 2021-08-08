package com.midea.cloud.flow.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.midea.cloud.common.annotation.CacheData;
import com.midea.cloud.common.constants.RedisKey;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;

import com.midea.cloud.common.enums.bargaining.projectmanagement.projectpublish.BiddingApprovalStatus;
import com.midea.cloud.common.enums.bpm.TempIdToModuleEnum;
import com.midea.cloud.component.context.container.SpringContextHolder;
import com.midea.cloud.flow.common.constant.FlowConstant;
import com.midea.cloud.flow.model.dto.WorkflowCallbackDto;
import com.midea.cloud.flow.model.dto.WorkflowTodoDto;
import com.midea.cloud.flow.service.IWorkflowHandlerService;
import com.midea.cloud.flow.workflow.service.ITemplateHeaderService;
import com.midea.cloud.srm.feign.bid.BidClient;
import com.midea.cloud.srm.feign.contract.ContractClient;
import com.midea.cloud.srm.feign.inq.InqClient;
import com.midea.cloud.srm.feign.perf.PerformanceClient;
import com.midea.cloud.srm.feign.pm.PmClient;
import com.midea.cloud.srm.feign.supplier.SupplierClient;
import com.midea.cloud.srm.feign.supplierauth.SupplierAuthClient;
import com.midea.cloud.srm.feign.workflow.FlowBusinessCallbackClient;
import com.midea.cloud.srm.model.bargaining.purchaser.projectmanagement.bidinitiating.entity.Biding;
import com.midea.cloud.srm.model.cm.accept.dto.AcceptDTO;
import com.midea.cloud.srm.model.cm.accept.dto.AcceptOrderDTO;
import com.midea.cloud.srm.model.cm.accept.entity.AcceptOrder;
import com.midea.cloud.srm.model.cm.contract.entity.ContractHead;
import com.midea.cloud.srm.model.flow.process.entity.TemplateHeader;
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

import lombok.extern.slf4j.Slf4j;

/**
 * 工作流服务类，工作流的入口，适配第三方工作流接口
 * @author lizl7
 *
 */
@Service
@Slf4j
public class WorkflowHandlerServiceImpl implements IWorkflowHandlerService {

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
	
    @Autowired
    private ITemplateHeaderService iTemplateHeaderService; //流程配置表

//	private Map<String, TemplateHeader> mapTemplateHeaderCache;
	private Map<String, FlowBusinessCallbackClient> mapFlowBusinessCallbackClient;
	private Map<String, String> mapFlowImplementService;
	
	/**
	 * 回调流程(包含审批通过，驳回，撤回)，接收工作流引擎的回调事件
	 */
	@Override
	public String callback(WorkflowCallbackDto workflowCallbackDto) throws Exception {
		Long businessId =workflowCallbackDto.getBusinessId();
		String businessType =workflowCallbackDto.getBusinessType();
    	if (businessType==null || businessId==null ) {
    		return null;
    	}

    	FlowBusinessCallbackClient flowBusinessCallbackClient =getFlowBusinessCallbackClient(businessType);

    	//通过
    	if( FlowConstant.Flow_Status_APPROVED.equals(workflowCallbackDto.getFlowStatus()) ) {
    		//实现了FlowBusinessCallbackClient的功能直接回调
    		if( flowBusinessCallbackClient!=null ) {
    			flowBusinessCallbackClient.callbackFlow(getImplementService(businessType), "passFlow" ,businessId, (String)workflowCallbackDto.getParam());
    		}else {
    			//由于还没有切换完成，继续保留旧的方式
        		if (StringUtils.equals(businessType, TempIdToModuleEnum.REQUIREMENT.getValue())) {
        			doRequirement(businessId);
        		} else if (StringUtils.equals(businessType, TempIdToModuleEnum.ORDER.getValue())) {
        			doOrder(businessId);
        		} else if (StringUtils.equals(businessType, TempIdToModuleEnum.CONTRACT.getValue())) {
        			doContract(businessId);
        		} else if (StringUtils.equals(businessType, TempIdToModuleEnum.SUPPLIERAUTH.getValue())) {
        			doSupplierauth(businessId);
        		} else if (StringUtils.equals(businessType, TempIdToModuleEnum.CHANGE.getValue())) {
        			doChange(businessId);
        		} else if (StringUtils.equals(businessType, TempIdToModuleEnum.IMPORT.getValue())) {
        			doImport(businessId);
        		} else if (StringUtils.equals(businessType, TempIdToModuleEnum.ACCEPT.getValue())) {
        			doAccept(businessId);
        		} else if (StringUtils.equals(businessType, TempIdToModuleEnum.COMPANYINFO.getValue())) {
        			doCompanyinfo(businessId);
        		} else if (StringUtils.equals(businessType, TempIdToModuleEnum.VENDORASSES.getValue())) {
        			docVendorasses(businessId);
        		} else if (StringUtils.equals(businessType, TempIdToModuleEnum.FORCOMPARISON.getValue())) {
        			doBiding(businessId);
        		} else if (StringUtils.equals(businessType, TempIdToModuleEnum.BIDING.getValue())) {
        			docForComparison(businessId);
        		} else if (StringUtils.equals(businessType, TempIdToModuleEnum.APPROVAL.getValue())) {
        			doApproval(businessId);
        		} else {
        			Assert.isTrue(false, "ES_RETURN_ID参数模板ID：" + businessType + "找不到");
        		}
    		}
    	}
    	
    	//发起流程，按需实现调用业务模块
    	if( FlowConstant.Flow_Status_START.equals(workflowCallbackDto.getFlowStatus()) ) {
    		//目前只有产品工作流模式才会通过回调触发，其他模式会直接submitObjEntry触发，界面是引擎那边，是通过回调后在修改单据的状态，在这里调用业务的提交操作
    		//实现了FlowBusinessCallbackClient的功能直接回调
    		if( flowBusinessCallbackClient!=null ) {
    			flowBusinessCallbackClient.callbackFlow(getImplementService(businessType), "submitFlow" ,businessId, (String)workflowCallbackDto.getParam());
    		}
    	}
    	
    	//撤回，按需实现调用业务模块
    	if( FlowConstant.Flow_Status_WITHDRAW.equals(workflowCallbackDto.getFlowStatus()) ) {
			flowBusinessCallbackClient.callbackFlow(getImplementService(businessType), "withdrawFlow" ,businessId, (String)workflowCallbackDto.getParam());
    	}
    	
    	//驳回，按需实现调用业务模块
    	if( FlowConstant.Flow_Status_REJECTED.equals(workflowCallbackDto.getFlowStatus()) ) {
    		flowBusinessCallbackClient.callbackFlow(getImplementService(businessType), "rejectFlow" ,businessId, (String)workflowCallbackDto.getParam());
    	}
    	
    	//废弃，按需实现调用业务模块
    	if( FlowConstant.Flow_Status_DESTORY.equals(workflowCallbackDto.getFlowStatus()) ) {
    		flowBusinessCallbackClient.callbackFlow(getImplementService(businessType), "destoryFlow" ,businessId, (String)workflowCallbackDto.getParam());
    	}
    	
		return String.valueOf(businessId);
	}

	/**
	 * 获取流程配置，包含feign，service，集成模式
	 * @param businessType
	 * @return
	 */
	@Override
	public TemplateHeader getTemplateHeaderCache(String businessType) {
		if (StringUtils.isBlank(businessType)) {
			return null;
		}

		LambdaQueryWrapper<TemplateHeader> queryWrapper = Wrappers.lambdaQuery(TemplateHeader.class).eq(TemplateHeader::getTemplateCode, businessType);
		List<TemplateHeader> listTemplateHeader = iTemplateHeaderService.list(queryWrapper);
		if (listTemplateHeader.size() != 1) { // 配置了重复的key，逻辑错误
			return null;
		}
		return listTemplateHeader.get(0);
	}

	/**
	 * 根据业务类型获取回调客户端
	 * @param businessType
	 * @return
	 */
	private FlowBusinessCallbackClient getFlowBusinessCallbackClient(String businessType) {
		try {
			TemplateHeader templateHeader =getTemplateHeaderCache(businessType);
			if( templateHeader!=null ) {
		        Class clazz=Class.forName(templateHeader.getFeignClient());
		        Object bean = SpringContextHolder.getApplicationContext().getBean(clazz);
		        return (FlowBusinessCallbackClient)bean;
			}	
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return null;
//		if( mapFlowBusinessCallbackClient==null ) {
//			mapFlowBusinessCallbackClient =new HashMap<String, FlowBusinessCallbackClient>();
//		}
//		//初始化所有回调client到map中
//		mapFlowBusinessCallbackClient.put(TempIdToModuleEnum.REVIEW.getValue(), supplierAuthClient);
//		return mapFlowBusinessCallbackClient.get(businessType);
	}

	/**
	 * 根据业务类型获取业务回调的实现类
	 * @param businessType
	 * @return
	 */
	private String getImplementService(String businessType) {
		TemplateHeader templateHeader =getTemplateHeaderCache(businessType);
		if( templateHeader!=null ) {
			return templateHeader.getBussinessClass();
		}
		return null;
//		if( mapFlowImplementService==null ) {
//			mapFlowImplementService =new HashMap<String, String>();
//			//初始化所有回调client到map中
//			mapFlowImplementService.put(TempIdToModuleEnum.REVIEW.getValue(), "com.midea.cloud.srm.supauth.review.service.IReviewFormService");
//		}
//		return mapFlowImplementService.get(businessType);		
	}
	
	/**
	 * 获取集成模式
	 * @return
	 * @throws Exception
	 */
	@Override
	public String getFlowIntegrationMode(String businessType) throws Exception {
////		//根据不同的集成模式，处理callback
////	    Boolean isProductIntegrationMode =true;//产品工作流模式
////	    Boolean isIframeIntegrationMode =false;//iframe嵌入页面模式
////	    Boolean isSelfIntegrationMode =false;//自带页面模式
////	    Boolean isNouiIntegrationMode =false;//无页面模式
////	    Boolean isNoneIntegrationMode =false;//无工作流
//
//		//这里维护启用工作流的功能，从配置功能上获取，目前临时代码指定
//		if( !this.getIsEnableFlow(businessType) ) {
//			return FlowConstant.IntegrationMode_None;
//		}else {
//			//再根据具体的模式,在这里全局配置
//		    return FlowConstant.IntegrationMode_Product;
//		}

		TemplateHeader templateHeader =getTemplateHeaderCache(businessType);
		if( templateHeader!=null && "Y".equals(templateHeader.getEnableFlag()) ) {
			return templateHeader.getIntegrationMode();
		}
		return FlowConstant.IntegrationMode_None;
	}

	/**
	 * 根据业务类型获取是否启用，用于页面调用，控制界面
	 */
	@Override
	public Boolean getIsEnableFlow(String businessType) throws Exception {
		TemplateHeader templateHeader =getTemplateHeaderCache(businessType);
		if( templateHeader!=null ) {
			if( "Y".equals(templateHeader.getEnableFlag()) ) {
				return true;
			}else {
				return false;
			}
		}
		return false;
//		Map<String, String> mapFlowEnable =new HashMap<>();
//		//这里维护启用工作流的功能，从配置功能上获取，目前临时代码指定
//		//mapFlowEnable.put(TempIdToModuleEnum.REVIEW.getValue(), TempIdToModuleEnum.REVIEW.getValue());
//		
//		if( mapFlowEnable.containsKey(businessType) ) {
//			return true;
//		}
//		return false;
	}
	
	/**
	 * 统一待办接收入口
	 * @param id
	 * @param type
	 * @return
	 * @throws Exception
	 */
	public String todo(WorkflowTodoDto workflowTodoDto)  throws Exception {
		//目前系统采用的方案是不保存待办，待办信息由源头提供，所以只需要实现发送邮件
		
		//根据业务类型，构造审批页面的URL
		
		//根据待办人获取邮件

		//发送邮件

		return null;
	}
	
	/**
	 * 提交流程，由业务模块的提交动作触发，根据不同的集成模式处理后续操作，例如推送oa,直接审批通过（一定要业务事务完成后才能做），如果是iframe集成模式的话用不需要这个
	 */
	@Override
	public String submitEngine(Long businessId, String businessType, String businessData) throws Exception {
    	if (businessType==null || businessId==null ) {
    		return null;
    	}

    	FlowBusinessCallbackClient flowBusinessCallbackClient =getFlowBusinessCallbackClient(businessType);
    	String integrationMode =this.getFlowIntegrationMode(businessType);
    	
        //回调业务回调接口
    	WorkflowCallbackDto workflowCallbackDto =new WorkflowCallbackDto();
    	workflowCallbackDto.setBusinessId(businessId);
    	workflowCallbackDto.setBusinessType(businessType);
		workflowCallbackDto.setFlowStatus(FlowConstant.Flow_Status_START);
        this.callback(workflowCallbackDto);
        
    	//具体功能没有启用工作流的话，直接审批通过，不需要需要判断模式
        if( !this.getIsEnableFlow(businessType) ) {
        	workflowCallbackDto =new WorkflowCallbackDto();
        	workflowCallbackDto.setBusinessId(businessId);
        	workflowCallbackDto.setBusinessType(businessType);
			workflowCallbackDto.setFlowStatus(FlowConstant.Flow_Status_APPROVED);
        	return this.callback(workflowCallbackDto);
        }else {
        	//根据具体的模式特殊处理
        	
            //不启用工作流模式
            if( FlowConstant.IntegrationMode_None.equals(integrationMode) ) {
            	//直接审批通过
            	workflowCallbackDto =new WorkflowCallbackDto();
            	workflowCallbackDto.setBusinessId(businessId);
            	workflowCallbackDto.setBusinessType(businessType);
    			workflowCallbackDto.setFlowStatus(FlowConstant.Flow_Status_APPROVED);
            	return this.callback(workflowCallbackDto);
            }
            
            //无页面模式
            if( FlowConstant.IntegrationMode_Noui.equals(integrationMode) ) {
            	//实现推送到oa，对接第三方工作流
            	String dataPush =flowBusinessCallbackClient.callbackFlow(getImplementService(businessType), "getDataPushFlow" ,businessId, (String)workflowCallbackDto.getParam());
            	log.info("getDataPushFlow - "+dataPush);
            	
            }
        	
        }
        return String.valueOf(businessId);
	}

	/**
	 * 获取流程用到的业务变量，个别工作流是通过回调用的方式获取
	 */
	@Override
	public String getVariableFlow(Long businessId, String businessType) throws Exception {
    	FlowBusinessCallbackClient flowBusinessCallbackClient =getFlowBusinessCallbackClient(businessType);
    	String dataVariable =flowBusinessCallbackClient.callbackFlow(getImplementService(businessType), "getVariableFlow" ,businessId, null);
    	log.info("getVariableFlow - "+dataVariable);
		return dataVariable;
	}
	
	
	
	/***********隆基项目实现的回调*****************/
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
