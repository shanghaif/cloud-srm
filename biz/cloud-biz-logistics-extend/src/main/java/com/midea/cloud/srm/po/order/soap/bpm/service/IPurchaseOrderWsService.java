package com.midea.cloud.srm.po.order.soap.bpm.service;

import com.midea.cloud.srm.model.base.soap.bpm.pr.dto.FormForBpmRequest;
import com.midea.cloud.srm.model.base.soap.bpm.pr.dto.FormForBpmResponse;
import com.midea.cloud.srm.model.base.soap.bpm.pr.dto.PurchaseRequirementRequest;
import com.midea.cloud.srm.model.base.soap.erp.dto.SoapResponse;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.bind.annotation.XmlSeeAlso;


@WebService(name = "iPurchaseOrderWsService", targetNamespace = "http://www.aurora-framework.org/schema")
@SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
@XmlSeeAlso({
        FormForBpmRequest.class
})

public interface IPurchaseOrderWsService {
    @WebMethod(action = "execute")
    @WebResult(name = "formForBpmResponse", targetNamespace = "http://www.aurora-framework.org/schema", partName = "purchaseOrderResponse_part")
    FormForBpmResponse execute(@WebParam(name = "purchaseOrderRequest", targetNamespace = "http://www.aurora-framework.org/schema", partName = "purchaseOrderRequest_part")
                                       FormForBpmRequest request);
}
