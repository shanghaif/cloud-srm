package com.midea.cloud.srm.model.logistics.soap.tms.request;

import javax.xml.bind.annotation.*;

/**
 * WebService请求头
 * @Param
 * @return
 * @Author xiexh12@meicloud.com
 * @Date 2020.11.26 16:30
 * @throws
 **/
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
        "instId",
        "requestTime",
        "attr1",
        "attr2",
        "attr3"
})
public class RequestEsbInfo {

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
}

