package com.midea.cloud.srm.pr.soap.bpm.service;


import com.midea.cloud.srm.model.base.soap.bpm.pr.dto.FormForBpmRequest;
import com.midea.cloud.srm.model.base.soap.bpm.pr.dto.FormForBpmResponse;
import com.midea.cloud.srm.model.base.soap.erp.dto.SoapResponse;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.swing.*;
import javax.xml.bind.annotation.XmlSeeAlso;

@WebService(name = "iPurchaseWsService", targetNamespace = "http://www.aurora-framework.org/schema")
@SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
@XmlSeeAlso({
        FormForBpmRequest.class
})
public interface IPurchaseWsService {
    @WebMethod(action = "execute")
    @WebResult(name = "soapResponse", targetNamespace = "http://www.aurora-framework.org/schema", partName = "purchaseResponse_part")
    FormForBpmResponse execute(@WebParam(name = "purchaseRequest", targetNamespace = "http://www.aurora-framework.org/schema", partName = "purchaseRequest_part")
                                 FormForBpmRequest request);
}
