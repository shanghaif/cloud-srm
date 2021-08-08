package com.midea.cloud.srm.model.base.soap.erp.base.dto;

import sun.misc.Request;

import javax.xml.bind.annotation.*;
import java.util.List;

/**
*  <pre>
 *  地址接口表实体类
 * </pre>
*
* @author wuwl18@meiCloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-08-04 15:38:43
 *  修改内容:
 * </pre>
*/
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "locationsRequest")
@XmlType(name="", propOrder = {
    "esbInfo","requestInfo"
})
public class LocationsRequest {
    
    @XmlElement(required = true)
    protected EsbInfoRequest esbInfo;

    @XmlElement(required = true)
    private RequestInfo requestInfo;

    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(propOrder = {
            "locations"
    })
    public static class RequestInfo{

        @XmlElement
        private Locations locations;

        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(propOrder = {"location"})
        public static class Locations{
            @XmlElement
            private List<LocationsEntity> location;

            public List<LocationsEntity> getLocation() {
                return location;
            }

            public void setLocation(List<LocationsEntity> location) {
                this.location = location;
            }
        }

        public Locations getLocations() {
            return locations;
        }
        public void setLocations(Locations locations) {
            this.locations = locations;
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
