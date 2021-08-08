package com.midea.cloud.srm.model.rbac.soap.erp.dto;

import com.midea.cloud.srm.model.base.soap.erp.base.dto.EsbInfoRequest;

import javax.xml.bind.annotation.*;
import java.util.List;

/**
 * <pre>
 *  采购员接口表请求类
 * </pre>
 *
 * @author xiexh12@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/8/28 14:34
 *  修改内容:
 * </pre>
 */

@XmlAccessorType(XmlAccessType.FIELD)//@XmlAccessorType(XmlAccessType.FIELD) 映射这个类中的所有字段到XML
@XmlType(name = "", propOrder = {
        "esbInfo","requestInfo"
}) //propOrder 指定映射XML时的节点顺序，使用该属性时，必须列出JavaBean对象中的所有字段，否则会报错
@XmlRootElement(name = "poAgentRequest")
public class PoAgentRequest {

    @XmlElement(required = true)
    protected EsbInfoRequest esbInfo;

    @XmlElement(required = true)
    protected PoAgentRequest.RequestInfo requestInfo;

    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
//            "header",
            "poAgents"
    })

    public static class RequestInfo {
        //        @XmlElement(name = "HEADER", required = true)
//        protected HeaderRequest header;
        @XmlElement(required = true)
        protected PoAgentRequest.RequestInfo.PoAgents poAgents;

        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {
                "poAgent"
        })
        public static class PoAgents {
            @XmlElement(required = true)
            protected List<PoAgentEntity> poAgent;

            public List<PoAgentEntity> getPoAgent() {
                return poAgent;
            }
            public void setPoAgent(List<PoAgentEntity> poAgent) {
                this.poAgent = poAgent;
            }
        }

        public PoAgentRequest.RequestInfo.PoAgents getPoAgents() {
            return poAgents;
        }

        public void setPoAgents(PoAgentRequest.RequestInfo.PoAgents poAgents) {
            this.poAgents = poAgents;
        }
    }

    public EsbInfoRequest getEsbInfo() {
        return esbInfo;
    }

    public void setEsbInfo(EsbInfoRequest esbInfo) {
        this.esbInfo = esbInfo;
    }

    public PoAgentRequest.RequestInfo getRequestInfo() {
        return requestInfo;
    }

    public void setRequestInfo(PoAgentRequest.RequestInfo requestInfo) {
        this.requestInfo = requestInfo;
    }
}
