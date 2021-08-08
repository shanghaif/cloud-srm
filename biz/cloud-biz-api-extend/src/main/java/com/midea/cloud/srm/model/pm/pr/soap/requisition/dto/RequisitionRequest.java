package com.midea.cloud.srm.model.pm.pr.soap.requisition.dto;

import com.midea.cloud.srm.model.base.soap.erp.base.dto.EsbInfoRequest;

import javax.xml.bind.annotation.*;
import java.util.List;

/**
 * <pre>
 *  采购申请接口表请求类
 * </pre>
 *
 * @author xiexh12@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/9/1 10:19
 *  修改内容:
 * </pre>
 */

@XmlAccessorType(XmlAccessType.FIELD)//@XmlAccessorType(XmlAccessType.FIELD) 映射这个类中的所有字段到XML
@XmlType(name = "", propOrder = {
        "esbInfo","requestInfo"
}) //propOrder 指定映射XML时的节点顺序，使用该属性时，必须列出JavaBean对象中的所有字段，否则会报错
@XmlRootElement(name = "requisitionRequest")
public class RequisitionRequest {

    @XmlElement(required = true)
    protected EsbInfoRequest esbInfo;

    @XmlElement(required = true)
    protected RequisitionRequest.RequestInfo requestInfo;

    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
//            "header",
            "requisitions"
    })

    public static class RequestInfo {
        //        @XmlElement(name = "HEADER", required = true)
//        protected HeaderRequest header;
        @XmlElement(required = true)
        protected RequisitionRequest.RequestInfo.Requisitions requisitions;

        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {
                "requisition"
        })
        public static class Requisitions {
            @XmlElement(required = true)
            protected List<RequisitionEntity> requisition;

            public List<RequisitionEntity> getRequisition() {
                return requisition;
            }
            public void setRequisition(List<RequisitionEntity> requisition) {
                this.requisition = requisition;
            }
        }

        public RequisitionRequest.RequestInfo.Requisitions getRequisitions() {
            return requisitions;
        }

        public void setRequisitions(RequisitionRequest.RequestInfo.Requisitions requisitions) {
            this.requisitions = requisitions;
        }
    }

    public EsbInfoRequest getEsbInfo() {
        return esbInfo;
    }

    public void setEsbInfo(EsbInfoRequest esbInfo) {
        this.esbInfo = esbInfo;
    }

    public RequisitionRequest.RequestInfo getRequestInfo() {
        return requestInfo;
    }

    public void setRequestInfo(RequisitionRequest.RequestInfo requestInfo) {
        this.requestInfo = requestInfo;
    }
}
