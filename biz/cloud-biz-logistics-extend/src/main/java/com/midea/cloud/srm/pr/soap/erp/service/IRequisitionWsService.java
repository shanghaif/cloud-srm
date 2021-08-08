package com.midea.cloud.srm.pr.soap.erp.service;

import com.midea.cloud.srm.model.base.soap.erp.dto.SoapResponse;
import com.midea.cloud.srm.model.pm.pr.soap.requisition.dto.RequisitionRequest;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.bind.annotation.XmlSeeAlso;

@WebService(name = "iRequisitionWsService", targetNamespace = "http://www.aurora-framework.org/schema")
@SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
@XmlSeeAlso({
        RequisitionRequest.class
})
public interface IRequisitionWsService {

    @WebMethod(action = "execute")
    @WebResult(name = "soapResponse", targetNamespace = "http://www.aurora-framework.org/schema", partName = "requisitionResponse_part")
    SoapResponse execute(@WebParam(name = "requisitionRequest", targetNamespace = "http://www.aurora-framework.org/schema", partName = "requisitionRequest_part")
                                 RequisitionRequest request);
}
