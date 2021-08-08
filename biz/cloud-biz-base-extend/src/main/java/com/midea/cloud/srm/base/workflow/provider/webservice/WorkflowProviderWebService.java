/**
 * 
 */
package com.midea.cloud.srm.base.workflow.provider.webservice;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.bind.annotation.XmlSeeAlso;

import com.midea.cloud.srm.model.base.soap.erp.dto.ObjectFactory;
import com.midea.cloud.srm.model.base.soap.erp.dto.REQUEST;
import com.midea.cloud.srm.model.base.soap.erp.dto.SoapResponse;

/**
 * <pre>
 *  流程回调webservice
 * </pre>
 *
 * @author nianhuanh@meicloud.com
 * @version 1.00.00
 *
 * <pre>
  *  修改记录
  *  修改后版本:
  *  修改人:
  *  修改日期: 2020年8月7日 下午6:31:50
  *  修改内容:
 * </pre>
 */

@WebService(targetNamespace = "http://www.aurora-framework.org/schema")
@SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
@XmlSeeAlso({
        ObjectFactory.class
})
public interface WorkflowProviderWebService {
    @WebMethod(action = "execute")
	public @WebResult(name = "soapResponse", partName = "response_part")SoapResponse execute(@WebParam(name = "REQUEST", partName = "request_part") REQUEST request);

}
