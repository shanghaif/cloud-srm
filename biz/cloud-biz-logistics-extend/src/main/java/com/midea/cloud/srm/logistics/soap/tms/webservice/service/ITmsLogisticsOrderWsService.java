package com.midea.cloud.srm.logistics.soap.tms.webservice.service;

import com.midea.cloud.srm.model.base.soap.erp.dto.SoapResponse;
import com.midea.cloud.srm.model.logistics.soap.tms.request.TmsOrderRequest;
import com.midea.cloud.srm.model.logistics.soap.tms.request.TmsPortRequest;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.bind.annotation.XmlSeeAlso;

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
@WebService(name = "iTmsLogisticsOrderWsService", targetNamespace = "http://www.aurora-framework.org/schema")
@SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
@XmlSeeAlso({
        TmsOrderRequest.class
})
public interface ITmsLogisticsOrderWsService {
    @WebMethod(action = "execute")
    @WebResult(name = "soapResponse", targetNamespace = "http://www.aurora-framework.org/schema", partName = "tmsLogisticsOrderResponse_part")
    SoapResponse execute(@WebParam(name = "logisticsOrderRequest", targetNamespace = "http://www.aurora-framework.org/schema", partName = "tmsLogisticsOrderRequest_part")
                                 TmsOrderRequest request);
}
