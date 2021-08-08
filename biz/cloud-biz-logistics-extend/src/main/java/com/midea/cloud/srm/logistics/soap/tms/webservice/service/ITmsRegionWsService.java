package com.midea.cloud.srm.logistics.soap.tms.webservice.service;

import com.midea.cloud.srm.model.base.soap.erp.dto.SoapResponse;
import com.midea.cloud.srm.model.logistics.soap.tms.request.TmsRegionRequest;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.bind.annotation.XmlSeeAlso;

/**
 * <pre>
 *  TMS 行政区域数据 WebService 接口
 * </pre>
 *
 * @author xiexh12@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/11/26
 *  修改内容:
 * </pre>
 */
@WebService(name = "iTmsRegionWsService", targetNamespace = "http://www.aurora-framework.org/schema")
@SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
@XmlSeeAlso({
        TmsRegionRequest.class
})
public interface ITmsRegionWsService {
    @WebMethod(action = "execute")
    @WebResult(name = "soapResponse", targetNamespace = "http://www.aurora-framework.org/schema", partName = "tmsRegionResponse_part")
    SoapResponse execute(@WebParam(name = "tmsRegionRequest", targetNamespace = "http://www.aurora-framework.org/schema", partName = "tmsRegionRequest_part")
                                 TmsRegionRequest request);
}
