package com.midea.cloud.srm.model.suppliercooperate.order.dto;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import lombok.Data;

import java.io.Serializable;

@ColumnWidth(20)
@Data
public class WarehousingReturnDetailFileDTO implements Serializable {
    /**
     * 事务处理类型
     */
    @ExcelProperty(value = "事务处理类型", index = 0)
    private String type;

    /**
     * 接收单号
     */
    @ExcelProperty(value = "接收单号", index = 1)
    private String receiveOrderNo;

    /**
     * 接收行号
     */
    @ExcelProperty(value = "接收行号", index = 2)
    private String receiveOrderLineNo;

    /**
     * 事务处理日期
     */
    @ExcelProperty(value = "事务处理日期", index = 3)
    private String receiveDate;

    /**
     * 业务实体名称
     */
    @ExcelProperty(value = "业务实体", index = 4)
    private String orgName;

    /**
     * 库存组织名称
     */
    @ExcelProperty(value = "库存组织", index = 5)
    private String organizationName;

    /**
     * 供应商编码
     */
    @ExcelProperty(value = "供应商编码", index = 6)
    private String vendorCode;

    /**
     * 供应商名称
     */
    @ExcelProperty(value = "供应商名称", index = 7)
    private String vendorName;
    /**
     * 物料小类名称
     */
    @ExcelProperty(value = "物料小类", index = 8)
    private String categoryName;

    /**
     * 物料编码
     */
    @ExcelProperty(value = "物料编码", index = 9)
    private String itemCode;

    /**
     * 物料名称
     */
    @ExcelProperty(value = "物料名称", index = 10)
    private String itemName;

    /**
     * 单位名称
     */
    @ExcelProperty(value = "单位", index = 11)
    private String unit;

    /**
     * 接收数量
     */
    @ExcelProperty(value = "接收数量", index = 12)
    private String receiveNum;

    /**
     * 采购申请单号
     */
    @ExcelProperty(value = "采购申请单号", index = 13)
    private String requirementHeadNum;

    /**
     * 申请行号
     */
    @ExcelProperty(value = "申请行号", index = 14)
    private String rowNum;

    /**
     * 采购订单号
     */
    @ExcelProperty(value = "采购订单号", index = 15)
    private String orderNumber;

    /**
     * 订单行号
     */
    @ExcelProperty(value = "订单行号", index = 16)
    private String lineNum;

    /**
     * 合同编号
     */
    @ExcelProperty(value = "合同编号", index = 17)
    private String contractNo;
    /**
     * 创建人
     */
    @ExcelProperty(value = "创建人", index = 18)
    private String createdBy;

    /**
     * 创建日期
     */
    @ExcelProperty(value = "创建日期", index = 19)
    private String creationDate;

    @ExcelIgnore
    private int row;
}
