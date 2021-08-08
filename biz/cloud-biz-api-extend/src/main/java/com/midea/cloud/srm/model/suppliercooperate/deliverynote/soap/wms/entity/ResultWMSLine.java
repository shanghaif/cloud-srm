package com.midea.cloud.srm.model.suppliercooperate.deliverynote.soap.wms.entity;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.xml.bind.annotation.*;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
        "asnlineno"
        ,"asnlineid"
        ,"ordernumber"
        ,"linenum"
        ,"materialcode"
        ,"ceeabatchnum"
        ,"unit"
        ,"digit"

})
@Accessors(chain = true)
@Data
public class ResultWMSLine {
    @XmlElement(name = "ASNLINENO")
    @ExcelProperty(value = "送货单行号", index = 0)
    private String asnlineno;
    @XmlElement(name = "ASNLINEID")
    @ExcelProperty(value = "送货单行ID", index = 0)
    private String asnlineid;
    @XmlElement(name = "ORDERNUMBER")
    @ExcelProperty(value = "采购订单号", index = 0)
    private String ordernumber;
    @XmlElement(name = "LINENUM")
    @ExcelProperty(value = "采购订单行号", index = 0)
    private String linenum;
    @XmlElement(name = "MATERIALCODE")
    @ExcelProperty(value = "物料编号", index = 0)
    private String materialcode;
    @XmlElement(name = "CEEABATCHNUM")
    @ExcelProperty(value = "批次号", index = 0)
    private String ceeabatchnum;
    @XmlElement(name = "UNIT")
    @ExcelProperty(value = "单位", index = 0)
    private String unit;
    @XmlElement(name = "DIGIT")
    @ExcelProperty(value = "主数量", index = 0)
    private String digit;
}
