package com.midea.cloud.srm.model.logistics.soap.tms.request;

import com.midea.cloud.srm.model.base.soap.erp.base.dto.EsbInfoRequest;
import sun.misc.Request;

import javax.xml.bind.annotation.*;
import java.util.List;

/**
 *  <pre>
 *  tms港口接口表实体类
 * </pre>
 *
 * @author xiexh12@meiCloud.com
 * @version 1.00.00
 *
 *  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-11-26 15:38:43
 *  修改内容:
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "portsRequest")
@XmlType(name="", propOrder = {
        "esbInfo","requestInfo"
})
public class TmsPortRequest {

    @XmlElement(required = true)
    protected RequestEsbInfo esbInfo;

    @XmlElement(required = true)
    private RequestInfo requestInfo;

    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(propOrder = {
            "ports"
    })
    public static class RequestInfo{

        @XmlElement
        private Ports ports;

        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(propOrder = {"port"})
        public static class Ports{
            @XmlElement
            private List<TmsPortEntity> port;

            public List<TmsPortEntity> getPort() {
                return port;
            }
            public void setPort(List<TmsPortEntity> port) {
                this.port = port;
            }
        }

        public Ports getPorts() {
            return ports;
        }
        public void setPorts(Ports ports) {
            this.ports = ports;
        }
    }

    public RequestEsbInfo getEsbInfo() {
        return esbInfo;
    }

    public void setEsbInfo(RequestEsbInfo esbInfo) {
        this.esbInfo = esbInfo;
    }

    public RequestInfo getRequestInfo() {
        return requestInfo;
    }

    public void setRequestInfo(RequestInfo requestInfo) {
        this.requestInfo = requestInfo;
    }
}
