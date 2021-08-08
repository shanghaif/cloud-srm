package com.midea.cloud.srm.base.soap.erp.service;

import com.midea.cloud.srm.model.base.soap.erp.base.dto.InvOrganizationsRequest;
import com.midea.cloud.srm.model.base.soap.erp.dto.SoapResponse;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.bind.annotation.XmlSeeAlso;

/**
 * <pre>
 *  ERP 获取库存组织数据接口
 * </pre>
 *
 * @author wuwl18@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/8/11
 *  修改内容:
 * </pre>
 */
@WebService(name = "invOrganizationsWsService", targetNamespace = "http://www.aurora-framework.org/schema")
@SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
@XmlSeeAlso({
        InvOrganizationsRequest.class
})
public interface IInvOrganizationsWsService {
    @WebMethod(action = "execute")
    @WebResult(name = "soapResponse", targetNamespace = "http://www.aurora-framework.org/schema", partName = "response_part")
    SoapResponse execute(@WebParam(name = "invOrganizationsRequest", targetNamespace = "http://www.aurora-framework.org/schema", partName = "request_part")
                                 InvOrganizationsRequest request);
}
