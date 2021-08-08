package com.midea.cloud.srm.model.suppliercooperate.order.dto;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * <pre>
 *  订单导出实体类
 * </pre>
 *
 * @author xiexh12@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/12/11 20:16
 *  修改内容:
 * </pre>
 */
@Data
@ColumnWidth(20)
@Accessors(chain = true)
public class OrderExport implements Serializable {

    @ExcelProperty(value = "采购订单编号", index = 0)
    private String orderNumber;

    @ExcelProperty(value = "订单日期", index = 1)
    private Date ceeaPurchaseOrderDate;

    /**
     * 字典编码：ORDER_TYPE
     */
    @ExcelProperty(value = "订单类型", index = 2)
    private String orderType;

    /**
     * 字典编码：PURCHASE_ORDER
     */
    @ExcelProperty(value = "订单状态", index = 3)
    private String orderStatus;

    @ExcelProperty(value = "业务实体", index = 4)
    private String ceeaOrgName;

    @ExcelProperty(value = "供应商编码", index = 5)
    private String vendorCode;

    @ExcelProperty(value = "供应商名称", index = 6)
    private String vendorName;

    @ExcelProperty(value = "erp订单编号", index = 7)
    private String eprOrderNumber;

    @ExcelProperty(value = "采购员", index = 8)
    private String ceeaEmpUsername;

    @ExcelProperty(value = "是否供方确认", index = 9)
    private String ceeaIfSupplierConfirm;

    @ExcelProperty(value = "是否寄售", index = 10)
    private String ceeaIfConSignment;

    @ExcelProperty(value = "是否电站业务", index = 11)
    private String ceeaIfPowerStationBusiness;

    @ExcelProperty(value = "拒绝订单原因", index = 12)
    private String refuseReason;

    /**
     * 字典编码：SOURCE_SYSTERM
     */
    @ExcelProperty(value = "来源系统", index = 13)
    private String sourceSystem;

    // =====================订单行=================================================================================

    @ExcelProperty(value = "申请行号", index = 14)
    private String ceeaRowNum;

    @ExcelProperty(value = "库存组织", index = 15)
    private String ceeaOrganizationName;

    @ExcelProperty(value = "物料小类", index = 16)
    private String categoryName;

    @ExcelProperty(value = "物料编码", index = 17)
    private String materialCode;

    @ExcelProperty(value = "物料名称", index = 18)
    private String materialName;

    @ExcelProperty(value = "单位", index = 19)
    private String unit;

    @ExcelProperty(value = "需求数量", index = 20)
    private BigDecimal requirementQuantity;

    @ExcelProperty(value = "订单数量", index = 21)
    private BigDecimal orderNum;

    @ExcelProperty(value = "已接收数量", index = 22)
    private BigDecimal receivedQuantity;

    @ExcelProperty(value = "要求到货日期", index = 23)
    private Date ceeaPlanReceiveDate;

    @ExcelProperty(value = "承诺到货日期", index = 24)
    private Date ceeaPromiseReceiveDate;

    @ExcelProperty(value = "含税单价", index = 25)
    private BigDecimal ceeaUnitTaxPrice;

    @ExcelProperty(value = "不含税单价", index = 26)
    private BigDecimal ceeaUnitNoTaxPrice;

    @ExcelProperty(value = "币种", index = 27)
    private String currencyName;

    @ExcelProperty(value = "税率", index = 28)
    private String ceeaTaxKey;

    @ExcelProperty(value = "含税金额", index = 29)
    private BigDecimal ceeaAmountIncludingTax;

    @ExcelProperty(value = "不含税金额", index = 30)
    private BigDecimal ceeaAmountExcludingTax;

    @ExcelProperty(value = "税额", index = 31)
    private BigDecimal ceeaTaxAmount;

    @ExcelProperty(value = "合同编号", index = 32)
    private String ceeaContractNo;

    @ExcelProperty(value = "明细备注", index = 33)
    private String detailComments;

}
