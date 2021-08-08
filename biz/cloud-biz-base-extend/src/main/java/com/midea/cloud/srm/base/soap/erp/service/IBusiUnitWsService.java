package com.midea.cloud.srm.base.soap.erp.service;

import com.midea.cloud.srm.model.base.soap.erp.dto.ObjectFactory;
import com.midea.cloud.srm.model.base.soap.erp.dto.REQUEST;
import com.midea.cloud.srm.model.base.soap.erp.dto.SoapResponse;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.bind.annotation.XmlSeeAlso;

/**
 * <pre>
 *  ERP 获取业务实体数据接口
 * </pre>
 *
 * @author wuwl18@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/7/21 19:24
 *  修改内容:
 * </pre>
 */
//如果不添加的话,动态调用invoke的时候,会报找不到接口内的方法,具体原因未知
//@WebService(targetNamespace = "http://service.erp.soap.cxf.srm.cloud.com")
@WebService(name = "busiUnitWsService", targetNamespace = "http://www.aurora-framework.org/schema")
@SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
@XmlSeeAlso({
        ObjectFactory.class
})
public interface IBusiUnitWsService {
    @WebMethod(action = "execute")
    @WebResult(name = "soapResponse", targetNamespace = "http://www.aurora-framework.org/schema", partName = "response_part")
    SoapResponse execute(@WebParam(name = "REQUEST", targetNamespace = "http://www.aurora-framework.org/schema", partName = "request_part")
                                 REQUEST request);
}
