package com.midea.cloud.srm.model.suppliercooperate.order.vo;

import com.midea.cloud.srm.model.suppliercooperate.order.entry.ReturnDetail;
import com.midea.cloud.srm.model.suppliercooperate.order.entry.ReturnOrder;
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
 *  修改日期: 2021/2/22 11:32
 *  修改内容:
 * </pre>
 */
@Data
@Accessors(chain = true)
public class ReturnOrderVO {
    /**
     * 退货单
     */
    private ReturnOrder returnOrder;

    /**
     * 退货单明细表
     */
    private List<ReturnDetail> returnDetailList;
}
