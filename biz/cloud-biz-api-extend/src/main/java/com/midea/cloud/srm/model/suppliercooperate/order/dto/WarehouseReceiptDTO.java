package com.midea.cloud.srm.model.suppliercooperate.order.dto;

import com.midea.cloud.srm.model.suppliercooperate.order.entry.WarehousingReturnDetail;
import com.midea.cloud.srm.model.suppliercooperate.reconciliation.entry.WarehouseReceipt;
import com.midea.cloud.srm.model.suppliercooperate.reconciliation.entry.WarehouseReceiptDetail;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

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
 *  修改日期: 2021/2/19 10:02
 *  修改内容:
 * </pre>
 */
@Data
@Accessors(chain = true)
public class WarehouseReceiptDTO {
    private WarehouseReceipt warehouseReceipt;

    private List<WarehouseReceiptDetail> warehouseReceiptDetailList;
}
