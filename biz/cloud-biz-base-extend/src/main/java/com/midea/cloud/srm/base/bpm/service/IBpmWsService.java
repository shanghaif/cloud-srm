package com.midea.cloud.srm.base.bpm.service;


import com.midea.cloud.srm.model.base.soap.bpm.pr.dto.FormForBpmRequest;
import com.midea.cloud.srm.model.base.soap.bpm.pr.dto.FormForBpmResponse;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.bind.annotation.XmlSeeAlso;

@WebService(name = "IBpmWsService", targetNamespace = "http://www.aurora-framework.org/schema")
@SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
@XmlSeeAlso({
        FormForBpmRequest.class
})
public interface IBpmWsService {
    @WebMethod(action = "execute")
    @WebResult(name = "FormForBpmResponse", targetNamespace = "http://www.aurora-framework.org/schema", partName = "FormForBpmResponse_part")
    FormForBpmResponse execute(@WebParam(name = "FormForBpmRequest", targetNamespace = "http://www.aurora-framework.org/schema", partName = "FormForBpmRequest_part")
                                       FormForBpmRequest request);
}
