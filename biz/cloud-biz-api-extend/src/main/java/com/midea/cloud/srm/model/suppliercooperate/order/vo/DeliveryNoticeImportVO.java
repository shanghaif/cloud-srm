package com.midea.cloud.srm.model.suppliercooperate.order.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.DateTimeFormat;
import com.alibaba.excel.annotation.format.NumberFormat;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * <pre>
 *  送货通知单导入
 * </pre>
 *
 * @author huangbf3
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/5/21 11:16
 *  修改内容:
 * </pre>
 */
@Data
public class DeliveryNoticeImportVO {
    @ExcelProperty(value = "采购组织")
    private String organizationName;

    @ExcelProperty(value = "采购订单编号")
    private String orderNumber;

    @ExcelProperty(value = "送货通知单行号")
    private Integer lineNum;

    @ExcelProperty(value = "采购订单行号")
    private Integer orderLineNum;

    @ExcelProperty(value = "物料编码")
    private String materialCode;

    @NumberFormat
    @ExcelProperty(value = "通知数量")
    private BigDecimal noticeSum;

    @DateTimeFormat
    @ExcelProperty(value = "送货时间")
    private Date deliveryTime;
}
