
package com.midea.cloud.srm.model.base.soap.erp.base.dto;

import javax.xml.bind.annotation.*;


/**
 * Description Erp总线接口入参必填实体类
 * @Param
 * @return
 * @Author wuwl18@meicloud.com
 * @Date 2020.08.12
 * @throws
 **/
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
        "instId",
        "requestTime",
        "attr1",
        "attr2",
        "attr3",
        "returnStatus",
        "returnCode",
        "returnMsg",
        "responseTime"
})
public class EsbInfoRequest {

    @XmlElement(required = true)
    protected String instId;
    @XmlElement(required = true)
    protected String requestTime;
    @XmlElement(required = true)
    protected String attr1;
    @XmlElement(required = true)
    protected String attr2;
    @XmlElement(required = true)
    protected String attr3;
    @XmlElement(required = true)
    protected String returnStatus;
    @XmlElement(required = true)
    protected String returnCode;
    @XmlElement(required = true)
    protected String returnMsg;
    @XmlElement(required = true)
    protected String responseTime;


    public String getInstId() {
        return instId;
    }

    public void setInstId(String value) {
        this.instId = value;
    }

    public String getRequestTime() {
        return requestTime;
    }

    public void setRequestTime(String value) {
        this.requestTime = value;
    }

    public String getAttr1() {
        return attr1;
    }

    public void setAttr1(String value) {
        this.attr1 = value;
    }

    public String getAttr2() {
        return attr2;
    }

    public void setAttr2(String value) {
        this.attr2 = value;
    }

    public String getAttr3() {
        return attr3;
    }

    public void setAttr3(String value) {
        this.attr3 = value;
    }

    public String getReturnStatus() {
        return returnStatus;
    }

    public void setReturnStatus(String returnStatus) {
        this.returnStatus = returnStatus;
    }

    public String getReturnCode() {
        return returnCode;
    }

    public void setReturnCode(String returnCode) {
        this.returnCode = returnCode;
    }

    public String getReturnMsg() {
        return returnMsg;
    }

    public void setReturnMsg(String returnMsg) {
        this.returnMsg = returnMsg;
    }

    public String getResponseTime() {
        return responseTime;
    }

    public void setResponseTime(String responseTime) {
        this.responseTime = responseTime;
    }
}

