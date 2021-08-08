package com.midea.cloud.srm.base.soap.erp.service;

import com.midea.cloud.srm.model.base.soap.erp.base.dto.ProjectTaskRequest;
import com.midea.cloud.srm.model.base.soap.erp.dto.SoapResponse;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.bind.annotation.XmlSeeAlso;

/**
 * <pre>
 *  erp 项目任务数据 WebService 接口
 * </pre>
 *
 * @author xiexh12@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/12/06
 *  修改内容:
 * </pre>
 */
@WebService(name = "iProjectTaskWsService", targetNamespace = "http://www.aurora-framework.org/schema")
@SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
@XmlSeeAlso({
        ProjectTaskRequest.class
})
public interface IProjectTaskWsService {
    @WebMethod(action = "execute")
    @WebResult(name = "soapResponse", targetNamespace = "http://www.aurora-framework.org/schema", partName = "projectTaskResponse_part")
    SoapResponse execute(@WebParam(name = "projectTaskRequest", targetNamespace = "http://www.aurora-framework.org/schema", partName = "projectTaskRequest_part")
                                 ProjectTaskRequest request);
}
