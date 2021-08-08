package com.midea.cloud.srm.base.soap.erp.service;


import com.midea.cloud.srm.model.base.soap.erp.base.dto.PositionRequest;
import com.midea.cloud.srm.model.base.soap.erp.dto.SoapResponse;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.bind.annotation.XmlSeeAlso;

@WebService(name = "iPositionWsService", targetNamespace = "http://www.aurora-framework.org/schema")
@SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
@XmlSeeAlso({
        PositionRequest.class
})
public interface IPositionWsService {
    @WebMethod(action = "execute")
    @WebResult(name = "soapResponse", targetNamespace = "http://www.aurora-framework.org/schema", partName = "positionResponse_part")
    SoapResponse execute(@WebParam(name = "positionRequest", targetNamespace = "http://www.aurora-framework.org/schema", partName = "positionRequest_part")
                                 PositionRequest request);
}
