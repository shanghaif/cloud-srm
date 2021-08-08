
package com.midea.cloud.srm.model.suppliercooperate.deliverynote.soap.wms.entity;

import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "wmsResultInfo", propOrder = {
    "attr1",
    "attr2",
    "attr3",
    "instId",
    "requestTime",
    "responseTime",
    "returnCode",
    "returnMsg",
    "returnStatus"
})
@Data
public class WmsResultInfo {

    @XmlElement(name = "attr1")
    protected String attr1;
    @XmlElement(name = "attr2")
    protected String attr2;
    @XmlElement(name = "attr3")
    protected String attr3;
    @XmlElement(name = "instId")
    protected String instId;
    @XmlElement(name = "requestTime")
    protected String requestTime;
    @XmlElement(name = "responseTime")
    protected String responseTime;
    @XmlElement(name = "returnCode")
    protected String returnCode;
    @XmlElement(name = "returnMsg")
    protected String returnMsg;
    @XmlElement(name = "returnStatus")
    protected String returnStatus;
}
