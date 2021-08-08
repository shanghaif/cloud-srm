package com.midea.cloud.srm.logistics.soap.tms.webservice.service.impl;

import com.midea.cloud.common.utils.JsonUtil;
import com.midea.cloud.srm.logistics.soap.tms.webservice.service.ITmsLogisticsOrderWsService;
import com.midea.cloud.srm.model.base.soap.erp.dto.SoapResponse;
import com.midea.cloud.srm.model.logistics.soap.tms.request.TmsOrderRequest;
import lombok.extern.slf4j.Slf4j;
import net.minidev.json.JSONUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.jws.WebService;
import java.util.LinkedList;
import java.util.List;

/**
 * <pre>
 *  功能名称
 * </pre>
 *
 * @author chenwj92@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人: chenwj92
 *  修改日期: 2021/1/6 9:51
 *  修改内容:
 * </pre>
 */
@Slf4j
@WebService(targetNamespace = "http://www.aurora-framework.org/schema",
        endpointInterface = "com.midea.cloud.srm.logistics.soap.tms.webservice.service.ITmsLogisticsOrderWsService")
@Component("iTmsLogisticsOrderWsService")
public class TmsLogisticsOrderWsServiceImpl implements ITmsLogisticsOrderWsService {
    @Override
    public SoapResponse execute(TmsOrderRequest request) {
        SoapResponse response = null;

        List<TmsOrderRequest.RequestInfo.LogisticsOrder> logisticsOrderList = request.getRequestInfo().getLogisticsOrders();
        if(CollectionUtils.isNotEmpty(logisticsOrderList)){
            StringBuffer sb = new StringBuffer();
            for(TmsOrderRequest.RequestInfo.LogisticsOrder logisticsOrder : logisticsOrderList){
                sb.append(String.format("[%s]",logisticsOrder.getOrderHead().getOrderHeadNum()));
            }
            if(StringUtils.isNotBlank(sb.toString())){
                response = success(sb.toString(),request);
            }else{
                response = fail(request);
            }
        }else{
            response = fail(request);
        }

        return response;
    }

    private SoapResponse success(String orderHeadNums,TmsOrderRequest request){
        log.info(String.format("【TMS】接收到【SRM】的物流采购订单,订单号为：%s",orderHeadNums));
        log.info("参数：" + JsonUtil.entityToJsonStr(request));
        SoapResponse response = new SoapResponse();
        response.setSuccess("S");
        response.setResponse(new SoapResponse.RESPONSE());
        return response;
    }

    private SoapResponse fail(TmsOrderRequest request){
        log.info(String.format("Tms接收失败"));
        log.info("参数：" + JsonUtil.entityToJsonStr(request));
        SoapResponse response = new SoapResponse();
        response.setSuccess("F");
        response.setResponse(new SoapResponse.RESPONSE());
        return response;
    }
}
