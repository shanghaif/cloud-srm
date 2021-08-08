package com.midea.cloud.srm.supcooperate.soap.erp.service.impl;

import com.midea.cloud.common.utils.JsonUtil;
import com.midea.cloud.srm.model.base.soap.erp.dto.SoapResponse;
import com.midea.cloud.srm.model.base.soap.erp.suppliercooperate.dto.RequestSettlementOrder;
import com.midea.cloud.srm.supcooperate.order.service.IPoHeadSettlementErpService;
import com.midea.cloud.srm.supcooperate.order.service.IPoLineSettlementErpService;
import com.midea.cloud.srm.supcooperate.soap.erp.service.ISettlementOrderWsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.jws.WebService;
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
 *  修改日期: 2020/11/1 20:38
 *  修改内容:
 * </pre>
 */
@Slf4j
@WebService(targetNamespace = "http://www.aurora-framework.org/schema",
        endpointInterface = "com.midea.cloud.srm.supcooperate.soap.erp.service.ISettlementOrderWsService")
@Component("iSettlementOrderWsService")
public class SettlementOrderWsServiceImpl implements ISettlementOrderWsService {
    @Autowired
    private IPoHeadSettlementErpService poHeadSettlementErpService;
    @Autowired
    private IPoLineSettlementErpService poLineSettlementErpService;
    /**
     *
     * @param request
     * @return
     */
    @Override
    public SoapResponse execute(RequestSettlementOrder request) {
        log.info("erp获取地址接口数据: " + (null != request ? JsonUtil.entityToJsonStr(request) : "空"));
        /*获取instId和requestTime*/
        RequestSettlementOrder.EsbInfo esbInfo = request.getEsbInfo();
        String instId = "";
        String requestTime = "";
        if(null != esbInfo){
            instId = esbInfo.getInstId();
            requestTime = esbInfo.getRequestTime();
        }
        /*获取地址接口List，并保存数据*/
        RequestSettlementOrder.RequestInfo requestInfo = request.getRequestInfo();
        RequestSettlementOrder.RequestInfo.CONTEXT context = requestInfo.getCONTEXT();
        RequestSettlementOrder.RequestInfo.CONTEXT.PoHeader poHeader = context.getPoHeader();
        List<RequestSettlementOrder.RequestInfo.CONTEXT.PoHeader.RECORD> recordList = poHeader.getRECORD();
        return poHeadSettlementErpService.acceptErpData(recordList,instId,requestTime);
    }

}
