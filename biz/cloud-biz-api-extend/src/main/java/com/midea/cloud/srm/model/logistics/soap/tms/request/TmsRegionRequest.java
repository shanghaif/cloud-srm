package com.midea.cloud.srm.model.logistics.soap.tms.request;

import com.midea.cloud.srm.model.base.soap.erp.base.dto.EsbInfoRequest;
import sun.misc.Request;

import javax.xml.bind.annotation.*;
import java.util.List;

/**
 *  <pre>
 *  tms行政区域接口请求类
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
@XmlRootElement(name = "regionsRequest")
@XmlType(name="", propOrder = {
        "esbInfo","requestInfo"
})
public class TmsRegionRequest {

    @XmlElement(required = true)
    protected RequestEsbInfo esbInfo;

    @XmlElement(required = true)
    private RequestInfo requestInfo;

    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(propOrder = {
            "regions"
    })

    public static class RequestInfo{

        @XmlElement
        private Regions regions;

        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(propOrder = {"region"})
        public static class Regions{
            @XmlElement
            private List<TmsRegionEntity> region;

            public List<TmsRegionEntity> getRegion() {
                return region;
            }
            public void setRegion(List<TmsRegionEntity> region) {
                this.region = region;
            }
        }

        public Regions getRegions() {
            return regions;
        }
        public void setRegions(Regions regions) {
            this.regions = regions;
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
