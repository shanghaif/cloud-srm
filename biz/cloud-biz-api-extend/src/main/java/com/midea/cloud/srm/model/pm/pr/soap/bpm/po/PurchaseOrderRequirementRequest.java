package com.midea.cloud.srm.model.pm.pr.soap.bpm.po;

import com.midea.cloud.srm.model.base.soap.bpm.pr.Entity.Header;
import com.midea.cloud.srm.model.base.soap.bpm.pr.dto.PurchaseRequirementRequest;
import com.midea.cloud.srm.model.base.soap.erp.base.dto.EsbInfoRequest;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD) //@XmlAccessorType(XmlAccessType.FIELD) 映射这个类中的所有字段到XML
@XmlType(name = "", propOrder = {
        "esbInfo","requestInfo"
}) //propOrder 指定映射XML时的节点顺序，使用该属性时，必须列出JavaBean对象中的所有字段，否则会报错
public class PurchaseOrderRequirementRequest {
    @XmlElement(required = true)
    protected EsbInfoRequest esbInfo;

    @XmlElement(required = true)
    protected PurchaseOrderRequirementRequest.RequestInfo requestInfo;

    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
            "header",
    })
    public static class RequestInfo {
        @XmlElement(name = "HEADER", required = true)
        protected Header header;

        public Header getHeader() {
            return header;
        }

        public void setHeader(Header header) {
            this.header = header;
        }
    }

    public EsbInfoRequest getEsbInfo() {
        return esbInfo;
    }

    public void setEsbInfo(EsbInfoRequest esbInfo) {
        this.esbInfo = esbInfo;
    }

    public PurchaseOrderRequirementRequest.RequestInfo getRequestInfo() {
        return requestInfo;
    }

    public void setRequestInfo(PurchaseOrderRequirementRequest.RequestInfo requestInfo) {
        this.requestInfo = requestInfo;
    }
}
