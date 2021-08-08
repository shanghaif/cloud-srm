
package com.midea.cloud.srm.model.base.soap.idm.updatepassword.schemas.updatepassword.v1;

import javax.xml.bind.annotation.XmlRegistry;


/**
 * Description 修改密码同步到IDM总线ObjectFactory实体类
 * @Author wuwl18@meicloud.com
 * @Date 2020.08.29
 **/
@XmlRegistry
public class IdmUpdUserObjectFactory {


    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.longi.idmsb.idm.updatepassword.schemas.updatepassword.v1
     * 
     */
    public IdmUpdUserObjectFactory() {
    }

    /**
     * Create an instance of {@link IdmUpdUserRequest }
     * 
     */
    public IdmUpdUserRequest createRequest() {
        return new IdmUpdUserRequest();
    }

    /**
     * Create an instance of {@link IdmUpdUserResponse }
     * 
     */
    public IdmUpdUserResponse createResponse() {
        return new IdmUpdUserResponse();
    }

    /**
     * Create an instance of {@link IdmUpdUserRequest.RequestInfo }
     * 
     */
    public IdmUpdUserRequest.RequestInfo createRequestRequestInfo() {
        return new IdmUpdUserRequest.RequestInfo();
    }

    /**
     * Create an instance of {@link IdmUpdUserRequest.RequestInfo.Employees }
     * 
     */
    public IdmUpdUserRequest.RequestInfo.Employees createRequestRequestInfoEmployees() {
        return new IdmUpdUserRequest.RequestInfo.Employees();
    }

    /**
     * Create an instance of {@link IdmUpdUserRequest.EsbInfo }
     * 
     */
    public IdmUpdUserRequest.EsbInfo createRequestEsbInfo() {
        return new IdmUpdUserRequest.EsbInfo();
    }

    /**
     * Create an instance of {@link IdmUpdUserResponse.EsbInfo }
     * 
     */
    public IdmUpdUserResponse.EsbInfo createResponseEsbInfo() {
        return new IdmUpdUserResponse.EsbInfo();
    }

    /**
     * Create an instance of {@link IdmUpdUserRequest.RequestInfo.Employees.Employee }
     * 
     */
    public IdmUpdUserRequest.RequestInfo.Employees.Employee createRequestRequestInfoEmployeesEmployee() {
        return new IdmUpdUserRequest.RequestInfo.Employees.Employee();
    }

}
