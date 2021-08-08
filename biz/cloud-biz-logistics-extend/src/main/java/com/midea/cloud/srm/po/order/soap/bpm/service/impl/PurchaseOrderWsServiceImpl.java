package com.midea.cloud.srm.po.order.soap.bpm.service.impl;

import com.midea.cloud.common.enums.pm.po.PurchaseOrderEnum;
import com.midea.cloud.common.enums.pm.pr.requirement.RequirementApproveStatus;
import com.midea.cloud.common.exception.BaseException;
import com.midea.cloud.common.utils.DateUtil;
import com.midea.cloud.srm.feign.pm.PmClient;
import com.midea.cloud.srm.model.base.soap.bpm.pr.Entity.Header;
import com.midea.cloud.srm.model.base.soap.bpm.pr.dto.FormForBpmRequest;
import com.midea.cloud.srm.model.base.soap.bpm.pr.dto.FormForBpmResponse;
import com.midea.cloud.srm.model.suppliercooperate.order.dto.OrderSaveRequestDTO;
import com.midea.cloud.srm.po.order.service.IOrderService;
import com.midea.cloud.srm.po.order.soap.bpm.service.IPurchaseOrderWsService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;

import javax.jws.WebService;
import java.util.Date;

/**
 * <pre>
 * 审批流回调接口：采购订单bpm
 * </pre>
 *
 * @author ex_lizp6@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020年10月20日
 *  修改内容:
 *          </pre>
 */
@Slf4j
@WebService(targetNamespace = "http://www.aurora-framework.org/schema",
        endpointInterface = "com.midea.cloud.srm.po.order.soap.bpm.service.IPurchaseOrderWsService")
@Component("iPurchaseOrderWsService")
public class PurchaseOrderWsServiceImpl implements IPurchaseOrderWsService {
    @Autowired
    private IOrderService iOrderService;
    @Autowired
    private PmClient pmClient;

    @Override
    public FormForBpmResponse execute(FormForBpmRequest request) {

        FormForBpmResponse response = new FormForBpmResponse();
        FormForBpmResponse.RESPONSE responses = new FormForBpmResponse.RESPONSE();

        FormForBpmRequest.EsbInfo esbInfo = request.getEsbInfo();

        String instId = "";
        String requestTime = "";
        if (null != esbInfo) {
            instId = esbInfo.getInstId();
            requestTime = esbInfo.getRequestTime();
        }

        FormForBpmRequest.RequestInfo requestInfo = request.getRequestInfo();

        log.info("bpm获取采购订单流程接口数据: " + (null != request ? request.toString() : "空"));
        //接收数据
        Header header = requestInfo.getContext().getApprove().getHeader();
        //默认返回信息
        String responseMessage = "SUCCESSED";
        String returnStatus = "S";
        String returnCode = "S";
        String returnMsg = "接收成功";

        try {
            Assert.notNull(header, "采购订单formInstanceId,不能为空");
            String formId = header.getFormId();
            String formInstanceId = header.getFormInstanceId();
            String status = header.getStatus();
            String opinion = header.getOpinion();
            //数据校验
            Assert.isTrue(StringUtils.isNotBlank(formInstanceId), "采购订单formInstanceId,不能为空");
            Assert.isTrue(StringUtils.isNotEmpty(formId), "模板ID不能为空");
            Assert.isTrue(StringUtils.isNotBlank(status), "审批状态不能为空");
            Long requirementHeadId;
            try {
                requirementHeadId = Long.parseLong(formInstanceId);
            } catch (Exception e) {
                throw new BaseException("采购订单formInstanceId格式错误");
            }

            OrderSaveRequestDTO byHead=null;
            try{
             byHead = iOrderService.queryOrderById(requirementHeadId);
            }catch (Exception e){
                if (e.getMessage()==null){
                    Assert.isTrue(false, "未查询到采购订单，单据id：" + requirementHeadId);
                }else {
                    Assert.isTrue(false, e.getMessage());
                }
            }
            Assert.isTrue(!ObjectUtils.isEmpty(byHead), "未查询到采购订单，单据id：" + requirementHeadId);
            Assert.isTrue(!ObjectUtils.isEmpty(byHead.getOrder()), "未查询到采购订单，单据id：" + requirementHeadId);


            String orderStatus = byHead.getOrder().getOrderStatus();
            boolean approveStatus = PurchaseOrderEnum.UNDER_APPROVAL.getValue().equals(orderStatus);
            Assert.isTrue(approveStatus, "审批状态错误");


            //根据返回的状态，回调相应的接口
            //已审批
            if (StringUtils.equals(status, RequirementApproveStatus.APPROVED.getValue())) {
                iOrderService.approvalInEditStatus(byHead);
                //已驳回
            } else if (StringUtils.equals(status, RequirementApproveStatus.REJECTED.getValue())) {
                iOrderService.rejectInEditStatus(byHead);
                //已撤回
            } else if (StringUtils.equals(status, RequirementApproveStatus.WITHDRAW.getValue())) {
                iOrderService.withdrawInEditStatus(byHead);
            } else {
                throw new BaseException("审批状态错误");
            }

        } catch (Exception e) {
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
}
