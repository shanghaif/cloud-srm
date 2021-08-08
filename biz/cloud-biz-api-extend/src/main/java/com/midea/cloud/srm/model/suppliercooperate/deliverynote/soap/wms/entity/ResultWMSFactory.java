package com.midea.cloud.srm.model.suppliercooperate.deliverynote.soap.wms.entity;


import com.midea.cloud.srm.model.logistics.soap.tms.request.RequestEsbInfo;
import lombok.Data;

import javax.xml.bind.annotation.*;

@XmlAccessorType(XmlAccessType.FIELD) //@XmlAccessorType(XmlAccessType.FIELD) 映射这个类中的所有字段到XML
@XmlType(name = "", propOrder = {
        "esbInfo","resultWMS"
}) //propOrder 指定映射XML时的节点顺序，使用该属性时，必须列出JavaBean对象中的所有字段，否则会报错
@XmlRootElement(name = "resultWMSFactory")
@Data
public class ResultWMSFactory {
    @XmlElement(name = "esbInfo", required = true)
    private RequestEsbInfo esbInfo;
    @XmlElement(name = "resultWMS", required = true)
    private ResultWMS resultWMS;


//    @XmlElement(name = "resultWMSLine", required = true)
//    protected ResultWMSLine resultWMSLine;

}
