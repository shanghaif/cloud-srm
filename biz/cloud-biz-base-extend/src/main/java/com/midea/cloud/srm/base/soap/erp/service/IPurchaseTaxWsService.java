package com.midea.cloud.srm.base.soap.erp.service;

import com.midea.cloud.srm.model.base.soap.erp.base.dto.PurchaseTaxRequest;
import com.midea.cloud.srm.model.base.soap.erp.dto.SoapResponse;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.bind.annotation.XmlSeeAlso;

/**
 * <pre>
 *  （隆基）WebService接口（Esb总线）
 * </pre>
 *
 * @update xiexh12@meicloud.com
 * <p>
 * 修改日期: 2020/9/13 17:52
 * </pre>
 */
@WebService(name = "iPurchaseTaxWsService", targetNamespace = "http://www.aurora-framework.org/schema")
@SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
@XmlSeeAlso({
        PurchaseTaxRequest.class
})
public interface IPurchaseTaxWsService {
    @WebMethod(action = "execute")
    @WebResult(name = "soapResponse", targetNamespace = "http://www.aurora-framework.org/schema", partName = "purchaseTaxResponse_part")
    SoapResponse execute(@WebParam(name = "purchaseTaxRequest", targetNamespace = "http://www.aurora-framework.org/schema", partName = "purchaseTaxRequest_part")
                                 PurchaseTaxRequest request);
}
