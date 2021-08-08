package com.midea.cloud.srm.model.suppliercooperate.deliverynote.soap.wms.entity;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.xml.bind.annotation.*;
import java.util.List;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
        "deliveryNoteId",
        "deliveryNumber",
        "status",
        "sourceSystemCode",
        "noteDetails"

})
@Accessors(chain = true)
@Data
public class ResultWMS {
    @XmlElement(name = "DELIVERYNOTEID")
    @ExcelProperty(value = "送货单头ID", index = 0)
    private String deliveryNoteId;
    @XmlElement(name = "DELIVERYNUMBER")
    @ExcelProperty(value = "送货单号", index = 1)
    private String deliveryNumber;
    @XmlElement(name = "STATUS")
    @ExcelProperty(value = "状态", index = 2)
    private String status;
    @XmlElement(name = "SOURCESYSTEMCODE")
    @ExcelProperty(value = "来源系统编号", index = 3)
    private String sourceSystemCode;
    @XmlElement(name = "NOTEDETAILS")
    @ExcelProperty(value = "送货单行明细", index = 4)
    private List<ResultWMSLine> noteDetails;
}
