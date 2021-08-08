package com.midea.cloud.srm.pr.soap.bpm.service.impl;


import com.midea.cloud.common.enums.pm.po.PurchaseOrderEnum;
import com.midea.cloud.common.enums.pm.po.StatusForBpmEnum;
import com.midea.cloud.common.enums.pm.pr.requirement.RequirementApproveStatus;
import com.midea.cloud.common.exception.BaseException;
import com.midea.cloud.common.utils.DateUtil;
import com.midea.cloud.srm.model.base.organization.entity.Position;
import com.midea.cloud.srm.model.base.soap.bpm.pr.Entity.Header;
import com.midea.cloud.srm.model.base.soap.bpm.pr.dto.FormForBpmRequest;
import com.midea.cloud.srm.model.base.soap.bpm.pr.dto.FormForBpmResponse;
import com.midea.cloud.srm.model.base.soap.bpm.pr.dto.PurchaseRequirementRequest;
import com.midea.cloud.srm.model.base.soap.erp.base.dto.EsbInfoRequest;
import com.midea.cloud.srm.model.base.soap.erp.base.dto.PositionEntity;
import com.midea.cloud.srm.model.base.soap.erp.base.dto.PositionRequest;
import com.midea.cloud.srm.model.base.soap.erp.dto.SoapResponse;
import com.midea.cloud.srm.model.pm.pr.requirement.dto.PurchaseRequirementDTO;
import com.midea.cloud.srm.model.pm.pr.requirement.vo.RequirementApplyRejectVO;
import com.midea.cloud.srm.pr.erp.service.IErpService;
import com.midea.cloud.srm.pr.requirement.controller.RequirementHeadController;
import com.midea.cloud.srm.pr.requirement.service.IRequirementHeadService;
import com.midea.cloud.srm.pr.requirement.service.impl.RequirementHeadServiceImpl;
import com.midea.cloud.srm.pr.soap.bpm.service.IPurchaseWsService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import javax.jws.WebService;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <pre>
 *  采购申请审批流，回调接口
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
        endpointInterface = "com.midea.cloud.srm.pr.soap.bpm.service.IPurchaseWsService")
@Component("iPurchaseWsService")
public class PurchaseWsServiceImpl implements IPurchaseWsService {
    @Autowired
    private RequirementHeadServiceImpl requirementHeadService;

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

        log.info("bpm获取采购申请流程接口数据: " + (null != request ? request.toString() : "空"));
        //接收数据
        Header header = requestInfo.getContext().getApprove().getHeader();
        String formId = header.getFormId();
        String formInstanceId = header.getFormInstanceId();
        String status = header.getStatus();
        String opinion = header.getOpinion();

        //默认返回信息
        String responseMessage= "SUCCESSED";
        String returnStatus= "S";
        String returnCode= "S";
        String returnMsg= "接收成功";

        try {
            //数据校验
            Assert.isTrue(StringUtils.isNotBlank(formInstanceId), "采购申请DOCUMENT_TYPE,不能为空");
            Assert.isTrue(StringUtils.isNotEmpty(formId),"模板ID不能为空");
            Assert.isTrue(StringUtils.isNotBlank(status), "审批状态不能为空");
            Long requirementHeadId;
            try {
                requirementHeadId = Long.parseLong(formInstanceId);
            } catch (Exception e) {
                throw new BaseException("采购申请formInstanceId格式错误");
            }
            PurchaseRequirementDTO byHeadId = requirementHeadService.getByHeadId(requirementHeadId);
            Assert.isTrue(!ObjectUtils.isEmpty(byHeadId.getRequirementHead()),"未查询到采购申请单，单据id："+requirementHeadId);
//

//            String orderStatus = byHeadId.getRequirementHead().getAuditStatus();
//            boolean approveStatus = PurchaseOrderEnum.UNDER_APPROVAL.getValue().equals(orderStatus);
//            Assert.isTrue(approveStatus, "审批状态错误");

            String srmStatus = StatusForBpmEnum.getValueByCode(status);
            System.out.println(srmStatus);

                    //根据返回的状态，回调相应的接口
            if(StringUtils.equals(srmStatus,RequirementApproveStatus.APPROVED.getValue())){
                requirementHeadService.updateApproved(requirementHeadId,RequirementApproveStatus.APPROVED.getValue());
            }else if(StringUtils.equals(srmStatus,RequirementApproveStatus.REJECTED.getValue())){
                requirementHeadService.reject(requirementHeadId,opinion);
            }else if(StringUtils.equals(srmStatus,RequirementApproveStatus.WITHDRAW.getValue())){
                requirementHeadService.withdraw(requirementHeadId,opinion);
            }else {
                throw new BaseException("审批状态错误");
            }

        }catch (Exception e){
            responseMessage= "FAILURE";
            returnStatus= "N";
            returnCode= "N";
            returnMsg="接收失败，错误信息："+e.getMessage();
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
