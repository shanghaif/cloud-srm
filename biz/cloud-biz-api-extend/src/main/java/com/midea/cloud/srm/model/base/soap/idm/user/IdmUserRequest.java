package com.midea.cloud.srm.model.base.soap.idm.user;

import com.midea.cloud.srm.model.base.soap.erp.base.dto.EsbInfoRequest;
import com.midea.cloud.srm.model.base.soap.erp.base.dto.LocationsEntity;

import javax.xml.bind.annotation.*;
import java.util.List;

/**
*  <pre>
 *  IDM推送密码实体类
 * </pre>
*
* @author wuwl18@meiCloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-08-27
 *  修改内容:
 * </pre>
*/
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "idmUserRequest")
@XmlType(name="", propOrder = {
    "esbInfo","requestInfo"
})
public class IdmUserRequest {
    
    @XmlElement(required = true)
    private EsbInfoRequest esbInfo;

    @XmlElement(required = true)
    private RequestInfo requestInfo;

    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(propOrder = {
            "idmUsers"
    })
    public static class RequestInfo{

        @XmlElement
        private IdmUsers idmUsers;

        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(propOrder = {"idmUser"})
        public static class IdmUsers{
            @XmlElement
            private List<IdmUserEntity> idmUser;

            public List<IdmUserEntity> getIdmUser() {
                return idmUser;
            }

            public void setIdmUser(List<IdmUserEntity> idmUser) {
                this.idmUser = idmUser;
            }
        }

        public IdmUsers getIdmUsers() {
            return idmUsers;
        }

        public void setIdmUsers(IdmUsers idmUsers) {
            this.idmUsers = idmUsers;
        }
    }

    public EsbInfoRequest getEsbInfo() {
        return esbInfo;
    }

    public void setEsbInfo(EsbInfoRequest esbInfo) {
        this.esbInfo = esbInfo;
    }

    public RequestInfo getRequestInfo() {
        return requestInfo;
    }

    public void setRequestInfo(RequestInfo requestInfo) {
        this.requestInfo = requestInfo;
    }
}
