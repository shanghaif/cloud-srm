package com.midea.cloud.srm.model.suppliercooperate.order.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.midea.cloud.srm.model.suppliercooperate.reconciliation.entry.WarehouseReceipt;
import com.midea.cloud.srm.model.suppliercooperate.reconciliation.entry.WarehouseReceiptDetail;
import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

/**
 * <pre>
 *  功能名称
 * </pre>
 *
 * @author chenwj92@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人: chenwj92
 *  修改日期: 2021/2/20 11:39
 *  修改内容:
 * </pre>
 */
@Data
@Accessors(chain = true)
public class WarehouseReceiptDetailVO extends WarehouseReceiptDetail {
    private Long orgId;

    private String orgName;

    private String orgCode;

    private Long organizationId;

    private String organizationName;

    private String organizationCode;

    private Long vendorId;

    private String vendorName;

    private String vendorCode;

    private Long categoryId;

    private String categoryName;

    private String categoryCode;

    private Long materialId;

    private String materialCode;

    private String materialName;

    private String unit;

    private String unitCode;

    private String orderNumber;

    private Integer deliveryLineNum;

    private String contractNo;

    private String contractCode;

    private Long contractHeadId;

    private String taxKey;

    private BigDecimal taxRate;

    private BigDecimal unitPriceContainingTax;

    private BigDecimal unitPriceExcludingTax;

    private Long currencyId;

    private String currencyName;

    private String currencyCode;

    private Long poLineId; //orderDetailId

    private String warehouseReceiptNumber;



}
