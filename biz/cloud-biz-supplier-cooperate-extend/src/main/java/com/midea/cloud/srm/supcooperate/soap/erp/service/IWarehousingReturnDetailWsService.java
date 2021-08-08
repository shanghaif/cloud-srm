package com.midea.cloud.srm.supcooperate.soap.erp.service;

import com.midea.cloud.srm.model.base.soap.erp.dto.SoapResponse;
import com.midea.cloud.srm.model.base.soap.erp.suppliercooperate.dto.WarehousingReturnDetailRequest;
import com.midea.cloud.srm.model.suppliercooperate.order.entry.WarehousingReturnDetail;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.bind.annotation.XmlSeeAlso;

/**
 * <pre>
 *  功能名称
 * </pre>
 *
 * @author chenwj92@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人: chenwj92
 *  修改日期: 2020/8/22 14:35
 *  修改内容:
 * </pre>
 */
@WebService(name = "iWarehousingReturnDetailWsService", targetNamespace = "http://www.aurora-framework.org/schema")
@SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
@XmlSeeAlso({
        WarehousingReturnDetailRequest.class
})
public interface IWarehousingReturnDetailWsService {
    @WebMethod(action = "execute")
    @WebResult(name = "soapResponse", targetNamespace = "http://www.aurora-framework.org/schema", partName = "responseWarehousingReturnDetail_part")
    SoapResponse execute(@WebParam(name = "warehousingReturnDetailRequest", targetNamespace = "http://www.aurora-framework.org/schema", partName = "requestWarehousingReturnDetail_part")
                                 WarehousingReturnDetailRequest request);
}
