package com.midea.cloud.srm.base.bpm.service.impl;


import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.midea.cloud.common.enums.ApproveStatusType;
import com.midea.cloud.common.enums.PriceApprovalStatus;
import com.midea.cloud.common.enums.VendorAssesFormStatus;
import com.midea.cloud.common.enums.bargaining.projectmanagement.projectpublish.BiddingApprovalStatus;
import com.midea.cloud.common.enums.bpm.TempIdToModuleEnum;
import com.midea.cloud.common.enums.contract.ContractStatus;
import com.midea.cloud.common.enums.pm.po.PurchaseOrderEnum;
import com.midea.cloud.common.enums.pm.po.StatusForBpmEnum;
import com.midea.cloud.common.enums.pm.pr.requirement.RequirementApproveStatus;
import com.midea.cloud.common.exception.BaseException;
import com.midea.cloud.common.utils.DateUtil;
import com.midea.cloud.common.utils.NamedThreadFactory;
import com.midea.cloud.srm.base.bpm.service.IBpmWsService;
import com.midea.cloud.srm.base.workflow.service.ISrmFlowBusWorkflowService;
import com.midea.cloud.srm.feign.bid.BidClient;
import com.midea.cloud.srm.feign.contract.ContractClient;
import com.midea.cloud.srm.feign.inq.InqClient;
import com.midea.cloud.srm.feign.perf.PerformanceClient;
import com.midea.cloud.srm.feign.pm.PmClient;
import com.midea.cloud.srm.feign.supplier.SupplierClient;
import com.midea.cloud.srm.feign.supplierauth.SupplierAuthClient;
import com.midea.cloud.srm.model.bargaining.purchaser.projectmanagement.bidinitiating.entity.Biding;
import com.midea.cloud.srm.model.base.soap.bpm.pr.Entity.Header;
import com.midea.cloud.srm.model.base.soap.bpm.pr.dto.FormForBpmRequest;
import com.midea.cloud.srm.model.base.soap.bpm.pr.dto.FormForBpmResponse;
import com.midea.cloud.srm.model.cm.accept.dto.AcceptDTO;
import com.midea.cloud.srm.model.cm.accept.dto.AcceptOrderDTO;
import com.midea.cloud.srm.model.cm.accept.entity.AcceptOrder;
import com.midea.cloud.srm.model.cm.contract.dto.ContractHeadDTO;
import com.midea.cloud.srm.model.cm.contract.entity.ContractHead;
import com.midea.cloud.srm.model.inq.price.entity.ApprovalHeader;
import com.midea.cloud.srm.model.inq.price.vo.ApprovalAllVo;
import com.midea.cloud.srm.model.perf.vendorasses.VendorAssesForm;
import com.midea.cloud.srm.model.pm.pr.requirement.vo.RequirementApplyRejectVO;
import com.midea.cloud.srm.model.supplier.change.dto.ChangeInfoDTO;
import com.midea.cloud.srm.model.supplier.change.entity.InfoChange;
import com.midea.cloud.srm.model.supplier.info.dto.InfoDTO;
import com.midea.cloud.srm.model.supplier.info.entity.CompanyInfo;
import com.midea.cloud.srm.model.supplier.vendorimport.dto.VendorImportSaveDTO;
import com.midea.cloud.srm.model.supplier.vendorimport.entity.VendorImport;
import com.midea.cloud.srm.model.supplierauth.review.entity.ReviewForm;
import com.midea.cloud.srm.model.supplierauth.review.entity.SiteForm;
import com.midea.cloud.srm.model.suppliercooperate.order.dto.OrderSaveRequestDTO;
import com.midea.cloud.srm.model.workflow.entity.SrmFlowBusWorkflow;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import javax.jws.WebService;
import java.util.Date;
import java.util.concurrent.*;

//import com.midea.cloud.gateway.controller.TokenController;

/**
 * <pre>
 *  审批流，回调接口
 * </pre>
 *
 * @author chenwt24@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-10-10
 *  修改内容:
 * </pre>
 */
@Slf4j
@WebService(targetNamespace = "http://www.aurora-framework.org/schema",
        endpointInterface = "com.midea.cloud.srm.base.bpm.service.IBpmWsService")
@Component("iBpmWsService")
public class BpmWsServiceImpl implements IBpmWsService {

    @Resource
    private PmClient pmClient;

    @Resource
    private ContractClient contractClient;
    @Resource
    private SupplierAuthClient supplierAuthClient;
    @Resource
    private SupplierClient supplierClient;

    @Autowired
    private ISrmFlowBusWorkflowService workflowService;
    @Resource
    private PerformanceClient performanceClient;
    @Resource
    private com.midea.cloud.srm.feign.bargaining.BidClient bidClient;
    @Resource
    private BidClient bidexClient;
    @Resource
    private InqClient InqClient;

    private final ThreadPoolExecutor ioThreadPool;

    private final ForkJoinPool calculateThreadPool;


    //创建需求池
    public BpmWsServiceImpl() {
        int cpuCount = Runtime.getRuntime().availableProcessors();
        ioThreadPool = new ThreadPoolExecutor(cpuCount * 2 + 1, cpuCount * 2 + 1,
                0, TimeUnit.SECONDS, new LinkedBlockingQueue(),
                new NamedThreadFactory("审批流-http-sender", true), new ThreadPoolExecutor.CallerRunsPolicy());
        calculateThreadPool = new ForkJoinPool(cpuCount + 1);
    }

    @Override
    public FormForBpmResponse execute(FormForBpmRequest request) {
        FormForBpmRequest.RequestInfo requestInfo = request.getRequestInfo();

        FormForBpmResponse response = new FormForBpmResponse();
        FormForBpmResponse.RESPONSE responses = new FormForBpmResponse.RESPONSE();

        FormForBpmRequest.EsbInfo esbInfo = request.getEsbInfo();

        String instId = "";
        String requestTime = "";
        if (null != esbInfo) {
            instId = esbInfo.getInstId();
            requestTime = esbInfo.getRequestTime();
        }
        log.info("============================================================回调开始============================================================");
        log.info("bpm回调流程接口数据: " + (null != request ? request.toString() : "空"));
        //接收数据
        Header header = requestInfo.getContext().getApprove().getHeader();
        String formId = header.getFormId();
        String formInstanceId = header.getFormInstanceId();
        String status = header.getStatus();
        //String opinion = header.getOpinion();

        //默认返回信息
        String responseMessage = "SUCCESSED";
        String returnStatus = "S";
        String returnCode = "S";
        String returnMsg = "接收成功";


        try {
            SrmFlowBusWorkflow one = workflowService.getOne(Wrappers.lambdaQuery(SrmFlowBusWorkflow.class).eq(SrmFlowBusWorkflow::getFlowInstanceId, formInstanceId));
            Assert.isTrue(!ObjectUtils.isEmpty(one), "ID对应的单据不存在。");
            header.setFormInstanceId(one.getFormInstanceId());
            //数据校验
            Assert.isTrue(StringUtils.isNotBlank(formInstanceId), "DOCUMENT_TYPE,不能为空");
            Assert.isTrue(StringUtils.isNotEmpty(formId), "模板ID不能为空");
            Assert.isTrue(StringUtils.isNotBlank(status), "审批状态不能为空");

            String valueByCode = TempIdToModuleEnum.getValueByCode(formId);

            //校验状态
            String srmStatus = StatusForBpmEnum.getValueByCode(status);
            if (!RequirementApproveStatus.APPROVED.getValue().equals(srmStatus) &&
                    RequirementApproveStatus.REJECTED.getValue().equals(srmStatus) &&
                    RequirementApproveStatus.WITHDRAW.getValue().equals(srmStatus)) {
                throw new BaseException("审批状态错误");
            }

            //单据状态转换
            header.setStatus(srmStatus);

            if (StringUtils.equals(valueByCode, TempIdToModuleEnum.REQUIREMENT.getValue())) {
                doRequirement(header, one);
            } else if (StringUtils.equals(valueByCode, TempIdToModuleEnum.ORDER.getValue())) {
                doOrder(header, one);
            } else if (StringUtils.equals(valueByCode, TempIdToModuleEnum.CONTRACT.getValue())) {
                doContract(header, one);
            } else if (StringUtils.equals(valueByCode, TempIdToModuleEnum.REVIEW.getValue())) {
                doReview(header, one);
            } else if (StringUtils.equals(valueByCode, TempIdToModuleEnum.SUPPLIERAUTH.getValue())) {
                doSupplierauth(header, one);
            } else if (StringUtils.equals(valueByCode, TempIdToModuleEnum.CHANGE.getValue())) {
                doChange(header, one);
            } else if (StringUtils.equals(valueByCode, TempIdToModuleEnum.IMPORT.getValue())) {
                doImport(header, one);
            } else if (StringUtils.equals(valueByCode, TempIdToModuleEnum.ACCEPT.getValue())) {
                doAccept(header, one);
            } else if (StringUtils.equals(valueByCode, TempIdToModuleEnum.COMPANYINFO.getValue())) {
                doCompanyinfo(header, one);
            } else if (StringUtils.equals(valueByCode, TempIdToModuleEnum.VENDORASSES.getValue())) {
                docVendorasses(header, one);
            } else if (StringUtils.equals(valueByCode, TempIdToModuleEnum.FORCOMPARISON.getValue())) {
                doBiding(header, one);
            } else if (StringUtils.equals(valueByCode, TempIdToModuleEnum.BIDING.getValue())) {
                docForComparison(header, one);
            } else if (StringUtils.equals(valueByCode, TempIdToModuleEnum.APPROVAL.getValue())) {
                doApproval(header, one);
            } else {
                Assert.isTrue(false, "ES_RETURN_ID参数模板ID：" + formId + "找不到");
            }
            //状态入库
            one.setSrmOrderStatus(srmStatus);
            workflowService.updateById(one);

        } catch (Exception e) {
            log.error(e.toString());
            responseMessage = "FAILURE";
            returnStatus = "N";
            returnCode = "N";
            returnMsg = "接收失败，错误信息：" + e.getMessage();
        }

        String responseTime = DateUtil.format(new Date());

        //返回头信息
        FormForBpmResponse.ESBINFO esbinfo = new FormForBpmResponse.ESBINFO();
        esbinfo.setInstId(instId);
        esbinfo.setReturnStatus(returnStatus);
        esbinfo.setReturnCode(returnCode);
        esbinfo.setReturnMsg(returnMsg);
        esbinfo.setRequestTime(requestTime);
        esbinfo.setResponseTime(responseTime);
        responses.setEsbInfo(esbinfo);

        //返回尾信息
        FormForBpmResponse.RESULTINFO resultInfo = new FormForBpmResponse.RESULTINFO();
        resultInfo.setResponseMessage(responseMessage);
        resultInfo.setResponseStatus(responseMessage);
        responses.setResultInfo(resultInfo);
        response.setResponse(responses);

        return response;
    }

    /**
     * 1、采购申请
     *
     * @param header
     * @throws Exception
     */
    public void doRequirement(Header header, SrmFlowBusWorkflow one) throws Exception {
        //接收数据
        String formInstanceId = header.getFormInstanceId();
        String srmStatus = header.getStatus();
        //String opinion = header.getOpinion();
        Long requirementHeadId = Long.parseLong(formInstanceId);
        RequirementApplyRejectVO requirementApplyRejectVO = new RequirementApplyRejectVO();
        requirementApplyRejectVO.setRequirementHeadId(requirementHeadId);
        //requirementApplyRejectVO.setRequirementHeadId(requirementHeadId).setRejectReason(opinion);

        //异步处理
        CompletableFuture.runAsync(() -> {
            try {
                //根据返回的状态，回调相应的接口
                if (StringUtils.equals(srmStatus, RequirementApproveStatus.APPROVED.getValue())) {
                    pmClient.approval(requirementHeadId);
                } else if (StringUtils.equals(srmStatus, RequirementApproveStatus.REJECTED.getValue())) {
                    pmClient.reject(requirementApplyRejectVO);
                } else if (StringUtils.equals(srmStatus, RequirementApproveStatus.WITHDRAW.getValue())) {
                    pmClient.withdraw(requirementApplyRejectVO);
                }
                one.setSrmOrderStatus("");
                workflowService.updateById(one);
            } catch (Exception e) {
                log.error("回调审批流报错:", e);
            }
        }, ioThreadPool);
    }

    /**
     * 2、采购订单
     *
     * @param header
     * @throws Exception
     */
    public void doOrder(Header header, SrmFlowBusWorkflow one) throws Exception {
        Long orderId;
        orderId = Long.parseLong(header.getFormInstanceId());
        OrderSaveRequestDTO orderSaveRequestDTO = pmClient.queryOrderById(orderId);
        Assert.isTrue(!ObjectUtils.isEmpty(orderSaveRequestDTO.getOrder()), "未查询到采购订单，单据id：" + orderId);
        String srmStatus = header.getStatus();
        //根据返回的状态，回调相应的接口
        //异步处理
        CompletableFuture.runAsync(() -> {
            try {
                Assert.isTrue(PurchaseOrderEnum.UNDER_APPROVAL.getValue().equals(orderSaveRequestDTO.getOrder().getOrderStatus()), "只有审批中的订单才可以进行审批操作。");
                if (StringUtils.equals(srmStatus, RequirementApproveStatus.APPROVED.getValue())) {
                    pmClient.approvalInEditStatus(orderSaveRequestDTO);
                } else if (StringUtils.equals(srmStatus, RequirementApproveStatus.REJECTED.getValue())) {
                    pmClient.rejectInEditStatus(orderSaveRequestDTO);
                } else if (StringUtils.equals(srmStatus, RequirementApproveStatus.WITHDRAW.getValue())) {
                    pmClient.withdrawInEditStatus(orderSaveRequestDTO);
                }
                one.setSrmOrderStatus("");
                workflowService.updateById(one);
            } catch (Exception e) {
                log.error("回调审批流报错:", e);
            }
        }, ioThreadPool);
    }

    /**
     * 3、合同管理
     *
     * @param header
     * @throws Exception
     */
    public void doContract(Header header, SrmFlowBusWorkflow one) throws Exception {
        Long contractHeadId;
        contractHeadId = Long.parseLong(header.getFormInstanceId());
        ContractHead contractHead = contractClient.getContractDTOSecond(contractHeadId, null).getContractHead();
        Assert.isTrue(!ObjectUtils.isEmpty(contractHead), "未查询到采购订单，单据id：" + contractHeadId);
        String srmStatus = header.getStatus();
        ContractHeadDTO contractHeadDTO = new ContractHeadDTO().setContractHeadId(contractHeadId);
        //根据返回的状态，回调相应的接口
        //异步处理
        CompletableFuture.runAsync(() -> {
            try {
                if (StringUtils.equals(srmStatus, RequirementApproveStatus.APPROVED.getValue())) {
                    contractClient.buyerApprove(contractHeadId);
                } else if (StringUtils.equals(srmStatus, RequirementApproveStatus.REJECTED.getValue())) {
                    contractClient.buyerRefused(contractHeadDTO);
                } else if (StringUtils.equals(srmStatus, RequirementApproveStatus.WITHDRAW.getValue())) {
                    contractClient.buyerWithdraw(contractHeadDTO);
                }
                one.setSrmOrderStatus("");
                workflowService.updateById(one);
            } catch (Exception e) {
                log.error("回调审批流报错:", e);
            }
        }, ioThreadPool);
    }

    /**
     * 4、供应商资质审查
     *
     * @param header
     * @throws Exception
     */
    public void doReview(Header header, SrmFlowBusWorkflow one) throws Exception {
        Long reviewFormId;
        reviewFormId = Long.parseLong(header.getFormInstanceId());
        ReviewForm reviewForm = supplierAuthClient.getReviewFormDTO(reviewFormId).getReviewForm();
        Assert.isTrue(!ObjectUtils.isEmpty(reviewForm), "未查询到采购订单，单据id：" + reviewFormId);
        String srmStatus = header.getStatus();

        //根据返回的状态，回调相应的接口
        //异步处理
        CompletableFuture.runAsync(() -> {
            try {
                if (StringUtils.equals(srmStatus, RequirementApproveStatus.APPROVED.getValue())) {
                    supplierAuthClient.ReviewFormPass(reviewForm);
                } else if (StringUtils.equals(srmStatus, RequirementApproveStatus.REJECTED.getValue())) {
                    supplierAuthClient.ReviewFormRejected(reviewForm);
                } else if (StringUtils.equals(srmStatus, RequirementApproveStatus.WITHDRAW.getValue())) {
                    supplierAuthClient.ReviewFormWithdraw(reviewForm);
                }
                one.setSrmOrderStatus("");
                workflowService.updateById(one);
            } catch (Exception e) {
                log.error("回调审批流报错:", e);
            }
        }, ioThreadPool);
    }

    /**
     * 5、供应商认证审批
     *
     * @param header
     * @throws Exception
     */
    public void doSupplierauth(Header header, SrmFlowBusWorkflow one) throws Exception {
        Long siteFormId;
        siteFormId = Long.parseLong(header.getFormInstanceId());
        SiteForm siteForm = supplierAuthClient.SiteFormGet(siteFormId);
        Assert.isTrue(!ObjectUtils.isEmpty(siteForm), "未查询到采购订单，单据id：" + siteFormId);
        String srmStatus = header.getStatus();
        //根据返回的状态，回调相应的接口
        //异步处理
        CompletableFuture.runAsync(() -> {
            try {
                Assert.isTrue(ApproveStatusType.SUBMITTED.getValue().equals(siteForm.getApproveStatus()), "已提交状态才可驳回");
                if (StringUtils.equals(srmStatus, RequirementApproveStatus.APPROVED.getValue())) {
                    supplierAuthClient.SiteFormPass(siteForm);
                } else if (StringUtils.equals(srmStatus, RequirementApproveStatus.REJECTED.getValue())) {
                    supplierAuthClient.SiteFormRejected(siteForm);
                } else if (StringUtils.equals(srmStatus, RequirementApproveStatus.WITHDRAW.getValue())) {
                    supplierAuthClient.SiteFormWithdraw(siteForm);
                }
                one.setSrmOrderStatus("");
                workflowService.updateById(one);
            } catch (Exception e) {
                log.error("回调审批流报错:", e);
            }
        }, ioThreadPool);
    }

    /**
     * 6、供应商信息变更审批（审批通过需要登录人信息问题）
     *
     * @param header
     * @throws Exception
     */
    public void doChange(Header header, SrmFlowBusWorkflow one) throws Exception {
        Long changeId;
        changeId = Long.parseLong(header.getFormInstanceId());
        ChangeInfoDTO changeInfoDTO = supplierClient.getInfoByChangeId(changeId);
        InfoChange infoChange = changeInfoDTO.getInfoChange();
        Assert.isTrue(!ObjectUtils.isEmpty(infoChange), "未查询到采购订单，单据id：" + changeId);
        String srmStatus = header.getStatus();
        //根据返回的状态，回调相应的接口
        //异步处理
        CompletableFuture.runAsync(() -> {
            try {
                if (StringUtils.equals(srmStatus, RequirementApproveStatus.APPROVED.getValue())) {
                    supplierClient.InfoChangeApprove(changeInfoDTO);
                } else if (StringUtils.equals(srmStatus, RequirementApproveStatus.REJECTED.getValue())) {
                    supplierClient.InfoChangeRejected(changeInfoDTO);
                } else if (StringUtils.equals(srmStatus, RequirementApproveStatus.WITHDRAW.getValue())) {
                    supplierClient.InfoChangeWithdraw(changeInfoDTO);
                }
                one.setSrmOrderStatus("");
                workflowService.updateById(one);
            } catch (Exception e) {
                log.error("回调审批流报错:", e);
            }
        }, ioThreadPool);
    }

    /**
     * 7、供应商跨OU引入
     *
     * @param header
     * @throws Exception
     */
    public void doImport(Header header, SrmFlowBusWorkflow one) throws Exception {
        Long importId;
        importId = Long.parseLong(header.getFormInstanceId());
        VendorImportSaveDTO vendorImportDetailDTO = supplierClient.getVendorImportDetail(importId);
        Assert.isTrue(!ObjectUtils.isEmpty(vendorImportDetailDTO), "未查询到供应商跨OU引入单，单据id：" + importId);
        VendorImport vendorImport = vendorImportDetailDTO.getVendorImport();
        Assert.isTrue(!ObjectUtils.isEmpty(vendorImport), "未查询供应商跨OU引入单，单据id：" + importId);
        String srmStatus = header.getStatus();
        //根据返回的状态，回调相应的接口
        //异步处理
        CompletableFuture.runAsync(() -> {
            try {
                if (StringUtils.equals(srmStatus, RequirementApproveStatus.APPROVED.getValue())) {
                    supplierClient.VendorImportApprove(importId);
                } else if (StringUtils.equals(srmStatus, RequirementApproveStatus.REJECTED.getValue())) {
                    supplierClient.VendorImportReject(importId);
                } else if (StringUtils.equals(srmStatus, RequirementApproveStatus.WITHDRAW.getValue())) {
                    supplierClient.VendorImportWithdraw(importId);
                }
                one.setSrmOrderStatus("");
                workflowService.updateById(one);
            } catch (Exception e) {
                log.error("回调审批流报错:", e);
            }
        }, ioThreadPool);
    }

    /**
     * 8、固定资产&服务验收
     *
     * @param header
     * @throws Exception
     */
    public void doAccept(Header header, SrmFlowBusWorkflow one) throws Exception {
        Long acceptOrderId;
        acceptOrderId = Long.parseLong(header.getFormInstanceId());
        AcceptDTO acceptDTO = contractClient.getAcceptDTO(acceptOrderId);
        AcceptOrder acceptOrder = acceptDTO.getAcceptOrder();
        Assert.isTrue(!ObjectUtils.isEmpty(acceptOrder), "未查询到验收单，单据id：" + acceptOrderId);
        AcceptOrderDTO acceptOrderDTO = new AcceptOrderDTO();
        BeanUtils.copyProperties(acceptOrder, acceptOrderDTO);
        String srmStatus = header.getStatus();
        //根据返回的状态，回调相应的接口
        //异步处理
        CompletableFuture.runAsync(() -> {
            try {
                if (StringUtils.equals(srmStatus, RequirementApproveStatus.APPROVED.getValue())) {
                    contractClient.vendorPass(acceptOrderDTO);
                } else if (StringUtils.equals(srmStatus, RequirementApproveStatus.REJECTED.getValue())) {
                    acceptOrderDTO.setRejectReason(header.getOpinion());
                    contractClient.buyerReject(acceptOrderDTO);
                } else if (StringUtils.equals(srmStatus, RequirementApproveStatus.WITHDRAW.getValue())) {
                    contractClient.acceptWithdraw(acceptOrderId);
                }
                one.setSrmOrderStatus("");
                workflowService.updateById(one);
            } catch (Exception e) {
                log.error("回调审批流报错:", e);
            }
        }, ioThreadPool);
    }

    /**
     * 9、供应商绿色通道审批（审批通过接口需要登录人信息）
     *
     * @param header
     * @throws Exception
     */
    public void doCompanyinfo(Header header, SrmFlowBusWorkflow one) throws Exception {
        Long companyId = Long.parseLong(header.getFormInstanceId());
        InfoDTO infoDTO = supplierClient.getInfoByParam(companyId);
        CompanyInfo companyInfo = infoDTO.getCompanyInfo();
        String status = companyInfo.getStatus();
        Assert.isTrue(!ObjectUtils.isEmpty(companyInfo), "未查询到绿色通道订单，单据id：" + companyId);

        String srmStatus = header.getStatus();
        //根据返回的状态，回调相应的接口
        //异步处理
        CompletableFuture.runAsync(() -> {
            try {
                if (StringUtils.equals(srmStatus, RequirementApproveStatus.APPROVED.getValue())) {
                    supplierClient.companyGreenChannelApprove(infoDTO);
                } else if (StringUtils.equals(srmStatus, RequirementApproveStatus.REJECTED.getValue())) {
                    Assert.isTrue(ApproveStatusType.SUBMITTED.getValue().equals(status), "只有已提状态的单据才可以审批操作，单据id：" + companyId);
                    //companyInfo.setCeeaDraftsmanOpinion(header.getOpinion());
                    supplierClient.CompanyInfoReject(companyInfo);
                } else if (StringUtils.equals(srmStatus, RequirementApproveStatus.WITHDRAW.getValue())) {
                    Assert.isTrue(ApproveStatusType.SUBMITTED.getValue().equals(status), "只有已提状态的单据才可以审批操作，单据id：" + companyId);
                    supplierClient.CompanyInfoWithdraw(companyInfo);
                }
                one.setSrmOrderStatus("");
                workflowService.updateById(one);
            } catch (Exception e) {
                log.error("回调审批流报错:", e);
            }
        }, ioThreadPool);
    }

    /**
     * 10、供应商考核审批
     *
     * @param header
     * @throws Exception
     */
    public void docVendorasses(Header header, SrmFlowBusWorkflow one) throws Exception {
        Long vendorAssesId;
        vendorAssesId = Long.parseLong(header.getFormInstanceId());
        VendorAssesForm vendorAssesForm = performanceClient.queryById(vendorAssesId);
        Assert.isTrue(!ObjectUtils.isEmpty(vendorAssesForm), "未查询到绩效考核单，单据id：" + vendorAssesId);
        String srmStatus = header.getStatus();
        //根据返回的状态，回调相应的接口
        CompletableFuture.runAsync(() -> {
            try {
                if (StringUtils.equals(srmStatus, RequirementApproveStatus.APPROVED.getValue())) {
                    performanceClient.VendorAssesFormPass(vendorAssesForm);
                } else if (StringUtils.equals(srmStatus, RequirementApproveStatus.REJECTED.getValue())) {
                    Assert.isTrue(VendorAssesFormStatus.SUBMITTED.getKey().equals(vendorAssesForm.getStatus()), "只有已提交的单据才可进行审批操作，单据id：" + vendorAssesId);
                    performanceClient.VendorAssesFormRejected(vendorAssesForm);
                } else if (StringUtils.equals(srmStatus, RequirementApproveStatus.WITHDRAW.getValue())) {
                    Assert.isTrue(VendorAssesFormStatus.SUBMITTED.getKey().equals(vendorAssesForm.getStatus()), "只有已提交的单据才可进行审批操作，单据id：" + vendorAssesId);
                    performanceClient.VendorAssesFormWithdraw(vendorAssesForm);
                }
                one.setSrmOrderStatus("");
                workflowService.updateById(one);
            } catch (Exception e) {
                log.error("回调审批流报错:", e);
            }
        }, ioThreadPool);
    }

    /**
     * 11、询比价文件审批
     *
     * @param header
     * @throws Exception
     */
    public void docForComparison(Header header, SrmFlowBusWorkflow one) throws Exception {
        Long bidingId = Long.parseLong(header.getFormInstanceId());
        Biding forComparison = bidClient.getBargaining(bidingId);
        Assert.isTrue(!ObjectUtils.isEmpty(forComparison), "未查询到询比价单，单据id：" + bidingId);
        String srmStatus = header.getStatus();
        //根据返回的状态，回调相应的接口
        CompletableFuture.runAsync(() -> {
            try {
                if (StringUtils.equals(srmStatus, RequirementApproveStatus.APPROVED.getValue())) {
                    forComparison.setAuditStatus(BiddingApprovalStatus.APPROVED.getValue());
                    bidClient.callBackForWorkFlow(forComparison);
                } else if (StringUtils.equals(srmStatus, RequirementApproveStatus.REJECTED.getValue())) {
                    forComparison.setAuditStatus(BiddingApprovalStatus.REJECTED.getValue());
                    //forComparison.setCeeaDrafterOpinion(header.getOpinion());
                    bidClient.callBackForWorkFlow(forComparison);
                } else if (StringUtils.equals(srmStatus, RequirementApproveStatus.WITHDRAW.getValue())) {
                    forComparison.setAuditStatus(BiddingApprovalStatus.WITHDRAW.getValue());
                    //forComparison.setCeeaDrafterOpinion(header.getOpinion());
                    bidClient.callBackForWorkFlow(forComparison);
                }
                one.setSrmOrderStatus("");
                workflowService.updateById(one);
            } catch (Exception e) {
                log.error("回调审批流报错:", e);
            }
        }, ioThreadPool);
    }

    /**
     * 12、招议标文件审批
     *
     * @param header
     * @throws Exception
     */
    public void doBiding(Header header, SrmFlowBusWorkflow one) throws Exception {
        Long bidingId = Long.parseLong(header.getFormInstanceId());
        com.midea.cloud.srm.model.bid.purchaser.projectmanagement.bidinitiating.entity.Biding forComparison = bidexClient.getBiding(bidingId);
        Assert.isTrue(!ObjectUtils.isEmpty(forComparison), "未查询到询比价单，单据id：" + bidingId);
        //Assert.isTrue(com.midea.cloud.common.enums.bid.projectmanagement.projectpublish.BiddingApprovalStatus.SUBMITTED.getValue().equals(forComparison.getAuditStatus()), "只有已提交的单据才可进行审批操作，单据id：" + bidingId);

        String srmStatus = header.getStatus();
        //根据返回的状态，回调相应的接口
        CompletableFuture.runAsync(() -> {
            try {
                if (StringUtils.equals(srmStatus, RequirementApproveStatus.APPROVED.getValue())) {
                    forComparison.setAuditStatus(com.midea.cloud.common.enums.bid.projectmanagement.projectpublish.BiddingApprovalStatus.APPROVED.getValue());
                    bidexClient.callBackForWorkFlow(forComparison);
                } else if (StringUtils.equals(srmStatus, RequirementApproveStatus.REJECTED.getValue())) {
                    forComparison.setAuditStatus(com.midea.cloud.common.enums.bid.projectmanagement.projectpublish.BiddingApprovalStatus.REJECTED.getValue());
                    bidexClient.callBackForWorkFlow(forComparison);
                } else if (StringUtils.equals(srmStatus, RequirementApproveStatus.WITHDRAW.getValue())) {
                    forComparison.setAuditStatus(com.midea.cloud.common.enums.bid.projectmanagement.projectpublish.BiddingApprovalStatus.WITHDRAW.getValue());
                    bidexClient.callBackForWorkFlow(forComparison);
                }
                one.setSrmOrderStatus("");
                workflowService.updateById(one);
            } catch (Exception e) {
                log.error("回调审批流报错:", e);
            }
        }, ioThreadPool);
    }

    /**
     * 13、寻源结果审批(价格审批单)
     *
     * @param header
     * @throws Exception
     */
    public void doApproval(Header header, SrmFlowBusWorkflow one) throws Exception {
        Long approvalHeaderId = Long.parseLong(header.getFormInstanceId());
        ApprovalAllVo approvalAllVo = InqClient.ceeaGetApprovalDetail(approvalHeaderId);
        ApprovalHeader approvalHeader = approvalAllVo.getApprovalHeader();

        Assert.isTrue(!ObjectUtils.isEmpty(approvalHeader), "未查询到价格审批单，单据id：" + approvalHeaderId);
        String srmStatus = header.getStatus();
        //根据返回的状态，回调相应的接口
        CompletableFuture.runAsync(() -> {
            try {
                if (StringUtils.equals(srmStatus, RequirementApproveStatus.APPROVED.getValue())) {
                    InqClient.auditPass(approvalHeaderId);
                } else if (StringUtils.equals(srmStatus, RequirementApproveStatus.REJECTED.getValue())) {
                    Assert.isTrue(PriceApprovalStatus.RESULT_NOT_APPROVED.getValue().equals(approvalHeader.getStatus()), "只有已提交的单据才可进行审批操作，单据id：" + approvalHeaderId);
                    InqClient.reject(approvalHeaderId, null);
                } else if (StringUtils.equals(srmStatus, RequirementApproveStatus.WITHDRAW.getValue())) {
                    Assert.isTrue(PriceApprovalStatus.RESULT_NOT_APPROVED.getValue().equals(approvalHeader.getStatus()), "只有已提交的单据才可进行审批操作，单据id：" + approvalHeaderId);
                    InqClient.withdraw(approvalHeaderId);
                }
                one.setSrmOrderStatus("");
                workflowService.updateById(one);
            } catch (Exception e) {
                log.error("回调审批流报错:", e);
            }
        }, ioThreadPool);
    }
}
