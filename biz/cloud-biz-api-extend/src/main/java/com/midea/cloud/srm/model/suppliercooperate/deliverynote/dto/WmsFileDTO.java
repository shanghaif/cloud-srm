package com.midea.cloud.srm.model.suppliercooperate.deliverynote.dto;


import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@ColumnWidth(20)
@Data
public class WmsFileDTO implements Serializable {
    /**
     * 订单行号
     */
    @ExcelProperty(value = "订单行号", index = 0)
    private String lineNum;
    /**
     * 采购订单号
     */
    @ExcelProperty(value ="采购订单号",index = 1)
    private String orderNumber;
    /**
     * 物料编码
     */
    @ExcelProperty(value = "物料编码", index = 2)
    private String materialCode;

    /**
     * 物料名称
     */
    @ExcelProperty(value = "物料名称", index = 3)
    private String materialName;
    /**
     * 批次号
     */
    @ExcelProperty(value = "生产批次号", index = 4)
    private String batchNum;
    /**
     * 浆料型号
     */
    @ExcelProperty(value = "浆料型号", index = 5)
    private String pasteType;
    /**
     * 制造商
     */
    @ExcelProperty(value = "制造商", index = 6)
    private String manufacturer;
    /**
     * 托盘号
     */
    @ExcelProperty(value = "托盘号", index = 7)
    private String consignmentNum;
    /**
     * 大托号
     */
    @ExcelProperty(value = "大托号", index = 8)
    private String bigConsignmentNum;
    /**
     * 箱号
     */
    @ExcelProperty(value = "箱号", index = 9)
    private String boxNum;
    /**
     * 单位
     */
    @ExcelProperty(value = "单位", index = 10)
    private String unit;

    /**
     * 本次送货数量
     */
    @ExcelProperty(value = "本次送货数量", index = 11)
    private String deliveryQuantity;

    /**
     * 生产日期
     */
    @ExcelProperty(value = "生产日期", index = 12)
    private String produceDate;
    /**
     * 有效日期
     */
    @ExcelProperty(value = "有效日期", index = 13)
    private String effectiveDate;

    /**
     * 承诺到货日期
     */
    @ExcelProperty(value = "承诺到货日期", index = 14)
    private String planReceiveDate;

    /**
     * 明细备注
     */
    @ExcelProperty(value = "明细备注", index = 15)
    private String comments;

    @ExcelIgnore
    private int row;
}
