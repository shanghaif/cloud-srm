package com.midea.cloud.srm.model.base.soap.bpm.pr.dto;

import com.midea.cloud.srm.model.base.soap.bpm.pr.Entity.Header;
import com.midea.cloud.srm.model.base.soap.erp.base.dto.EsbInfoRequest;

import javax.xml.bind.annotation.*;

/**
 * <pre>
 *  采购申请审批流request
 * </pre>
 *
 * @author chenwt24@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-10-09
 *  修改内容:
 * </pre>
 */

@XmlAccessorType(XmlAccessType.FIELD) //@XmlAccessorType(XmlAccessType.FIELD) 映射这个类中的所有字段到XML
@XmlType(name = "", propOrder = {
        "esbInfo","requestInfo"
}) //propOrder 指定映射XML时的节点顺序，使用该属性时，必须列出JavaBean对象中的所有字段，否则会报错
@XmlRootElement(name = "PurchaseRequirementRequest")
public class PurchaseRequirementRequest {
    @XmlElement(required = true)
    protected EsbInfoRequest esbInfo;

    @XmlElement(required = true)
    protected PurchaseRequirementRequest.RequestInfo requestInfo;

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

    public PurchaseRequirementRequest.RequestInfo getRequestInfo() {
        return requestInfo;
    }

    public void setRequestInfo(PurchaseRequirementRequest.RequestInfo requestInfo) {
        this.requestInfo = requestInfo;
    }
}
