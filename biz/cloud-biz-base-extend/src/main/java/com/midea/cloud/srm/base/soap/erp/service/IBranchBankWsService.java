package com.midea.cloud.srm.base.soap.erp.service;

import com.midea.cloud.srm.model.base.soap.erp.base.dto.BranchBankRequest;
import com.midea.cloud.srm.model.base.soap.erp.base.dto.BranchBankRequest;
import com.midea.cloud.srm.model.base.soap.erp.dto.SoapResponse;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.bind.annotation.XmlSeeAlso;

/**
 * <pre>
 *  （隆基）银行分行WebService接口（Esb总线）
 * </pre>
 *
 * @update xiexh12@meicloud.com
 * <p>
 * 修改日期: 2020/9/14 12:36
 * </pre>
 */
@WebService(name = "iBranchBankWsService", targetNamespace = "http://www.aurora-framework.org/schema")
@SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
@XmlSeeAlso({
        BranchBankRequest.class
})
public interface IBranchBankWsService {
    @WebMethod(action = "execute")
    @WebResult(name = "soapResponse", targetNamespace = "http://www.aurora-framework.org/schema", partName = "branchBankResponse_part")
    SoapResponse execute(@WebParam(name = "branchBankRequest", targetNamespace = "http://www.aurora-framework.org/schema", partName = "branchBankRequest_part")
                                 BranchBankRequest request);
}
