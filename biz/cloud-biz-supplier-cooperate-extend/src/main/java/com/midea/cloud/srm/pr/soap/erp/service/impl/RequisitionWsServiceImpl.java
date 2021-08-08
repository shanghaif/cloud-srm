package com.midea.cloud.srm.pr.soap.erp.service.impl;

import com.midea.cloud.srm.model.base.soap.erp.base.dto.EsbInfoRequest;
import com.midea.cloud.srm.model.base.soap.erp.dto.SoapResponse;
import com.midea.cloud.srm.model.pm.pr.requirement.dto.RequisitionDTO;
import com.midea.cloud.srm.model.pm.pr.requirement.entity.Requisition;
import com.midea.cloud.srm.model.pm.pr.requirement.entity.RequisitionDetail;
import com.midea.cloud.srm.model.pm.pr.soap.requisition.dto.RequisitionDetailEntity;
import com.midea.cloud.srm.model.pm.pr.soap.requisition.dto.RequisitionEntity;
import com.midea.cloud.srm.model.pm.pr.soap.requisition.dto.RequisitionRequest;
import com.midea.cloud.srm.pr.erp.service.IErpService;
import com.midea.cloud.srm.pr.requirement.service.IRequisitionService;
import com.midea.cloud.srm.pr.soap.erp.service.IRequisitionWsService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.jws.WebService;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * <pre>
 *  功能名称
 * </pre>
 *
 * @author xiexh12@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/9/1 10:16
 *  修改内容:
 * </pre>
 */
@Slf4j
@WebService(targetNamespace = "http://www.aurora-framework.org/schema",
        endpointInterface = "com.midea.cloud.srm.pr.soap.erp.service.IRequisitionWsService")
@Component("iRequisitionWsService")
public class RequisitionWsServiceImpl implements IRequisitionWsService {
    /**erp总线Service*/
    @Resource
    private IErpService iErpService;
    /**采购申请接口表Service*/
    @Resource
    private IRequisitionService iRequisitionService;

    @Override
    public SoapResponse execute(RequisitionRequest request) {
        Long startTime = System.currentTimeMillis();
        SoapResponse response = new SoapResponse();
        /**获取instId和requestTime*/
        EsbInfoRequest esbInfo = request.getEsbInfo();
        String instId = "";
        String requestTime = "";
        if (null != esbInfo) {
            instId = esbInfo.getInstId();
            requestTime = esbInfo.getRequestTime();
        }

        /**获取采购申请List,并保存数据（全量保存至基础表）*/
        RequisitionRequest.RequestInfo requestInfo = request.getRequestInfo();
        //RequisitionRequest.RequestInfo.Requisitions requisitionsClass;
        List<RequisitionEntity> requisitionsEntityList = new ArrayList<>();
        List<RequisitionDetailEntity> requisitionDetailEntityList = null;
        if (null != requestInfo&&null != requestInfo.getRequisitions()) {
                requisitionsEntityList = requestInfo.getRequisitions().getRequisition();
        }
        log.info("erp获取采购申请接口数据: " + (null != request ? request.toString() : "空"));
        List<RequisitionDTO> requisitionsList = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(requisitionsEntityList)) {
            for (RequisitionEntity requisitionEntity : requisitionsEntityList) {
                if (null != requisitionEntity) {
                    RequisitionDTO requisition = new RequisitionDTO();
                    BeanUtils.copyProperties(requisitionEntity, requisition);
                    /**获取采购申请明细List,并保存数据*/
                    requisitionDetailEntityList = requisitionEntity.getRequisitionLines();
                    List<RequisitionDetail> requisitionDetailList = new ArrayList<>();
                    if(CollectionUtils.isNotEmpty(requisitionDetailEntityList)) {
                        for(RequisitionDetailEntity requisitionDetailEntity : requisitionDetailEntityList){
                            if(null != requisitionDetailEntity){
                                RequisitionDetail requisitionDetail = new RequisitionDetail();
                                BeanUtils.copyProperties(requisitionDetailEntity, requisitionDetail);
                                requisitionDetail.setPrice(StringUtils.isNotEmpty(requisitionDetailEntity.getPrice()) ? new BigDecimal(requisitionDetailEntity.getPrice()) : null);
                                requisitionDetail.setQuantity(StringUtils.isNotEmpty(requisitionDetailEntity.getQuantity()) ? new BigDecimal(requisitionDetailEntity.getQuantity()) : null);
                                requisitionDetailList.add(requisitionDetail);
                            }
                        }
                    }
                    requisition.setRequisitionDetailList(requisitionDetailList);
                    requisitionsList.add(requisition);
                }
            }
            //response = iErpService.saveOrUpdateRequisitions(requisitionsList, instId, requestTime);
            response = iErpService.saveOrUpdateRequisitions(requisitionsList, instId, requestTime);
        }
        log.info("接收erp采购申请数据用时:"+(System.currentTimeMillis()-startTime)/1000 +"秒");
        return response;
    }


    public static void main(String[] args) {
        System.out.println(new BigDecimal(".11111"));
    }
}
