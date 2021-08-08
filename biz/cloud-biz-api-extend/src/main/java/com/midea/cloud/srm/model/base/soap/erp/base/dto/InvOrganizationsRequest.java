package com.midea.cloud.srm.model.base.soap.erp.base.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.xml.bind.annotation.*;
import java.util.List;

/**
 *  <pre>
 *  库存组织接口表最外层实体类
 * </pre>
 *
 * @author wuwl18@meiCloud.com
 * @version 1.00.00
 *
 *  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-08-04 15:39:50
 *  修改内容:
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD) //@XmlAccessorType(XmlAccessType.FIELD) 映射这个类中的所有字段到XML
@XmlType(name = "", propOrder = {
        "esbInfo","requestInfo"
}) //propOrder 指定映射XML时的节点顺序，使用该属性时，必须列出JavaBean对象中的所有字段，否则会报错
@XmlRootElement(name = "invOrganizationsRequest")
public class InvOrganizationsRequest {

    @XmlElement(required = true)
    protected EsbInfoRequest esbInfo;

    @XmlElement(required = true)
    protected RequestInfo requestInfo;

    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
//            "header",
            "invOrganizations"
    })
    public static class RequestInfo {
//        @XmlElement(name = "HEADER", required = true)
//        protected HeaderRequest header;

        @XmlElement(required = true)
        protected InvOrganizations invOrganizations;

        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {
                "invOrganization"
        })
        public static class InvOrganizations {
            @XmlElement(required = true)
            protected List<InvOrganizationsEntity> invOrganization;

            public List<InvOrganizationsEntity> getInvOrganization() {
                return invOrganization;
            }
            public void setInvOrganization(List<InvOrganizationsEntity> invOrganization) {
                this.invOrganization = invOrganization;
            }
        }

        public InvOrganizations getInvOrganizations() {
            return invOrganizations;
        }

        public void setInvOrganizations(InvOrganizations invOrganizations) {
            this.invOrganizations = invOrganizations;
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
