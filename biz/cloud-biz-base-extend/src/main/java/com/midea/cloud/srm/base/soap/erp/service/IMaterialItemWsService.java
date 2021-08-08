package com.midea.cloud.srm.base.soap.erp.service;


import com.midea.cloud.srm.model.base.soap.erp.base.dto.MaterialItemRequest;
import com.midea.cloud.srm.model.base.soap.erp.dto.SoapResponse;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.bind.annotation.XmlSeeAlso;

@WebService(name = "iMaterialItemWsService", targetNamespace = "http://www.aurora-framework.org/schema")
@SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
@XmlSeeAlso({
        MaterialItemRequest.class
})
public interface IMaterialItemWsService {
    @WebMethod(action = "execute")
    @WebResult(name = "soapResponse", targetNamespace = "http://www.aurora-framework.org/schema", partName = "materialItemResponse_part")
    SoapResponse execute(@WebParam(name = "materialItemRequest", targetNamespace = "http://www.aurora-framework.org/schema", partName = "materialItemResponse_part")
                        MaterialItemRequest materialItemRequest);
}
