package com.midea.cloud.srm.model.suppliercooperate.order.vo;

import com.midea.cloud.srm.model.base.purchase.entity.PurchaseTax;
import com.midea.cloud.srm.model.suppliercooperate.order.entry.OrderDetail;
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
 *  修改日期: 2020/9/3 9:57
 *  修改内容:
 * </pre>
 */
@Data
@Accessors(chain = true)
public class CeeaOrderDetailVo extends OrderDetail {
    /**
     * 税率
     */
    private List<PurchaseTax> purchaseTaxList;

}
