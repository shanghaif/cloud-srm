package com.midea.cloud.srm.model.suppliercooperate.reconciliation.dto;

import com.midea.cloud.srm.model.suppliercooperate.reconciliation.entry.UnsettledDetail;
import com.midea.cloud.srm.model.suppliercooperate.reconciliation.entry.UnsettledOrder;
import lombok.Data;

import java.util.List;

/**
 * <pre>
 *  未结算数量账单 数据查询请求传输对象
 * </pre>
 *
 * @author huangbf3
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/4/27 21:22
 *  修改内容:
 * </pre>
 */
@Data
public class UnsettledOrderRequestDTO extends UnsettledOrder {

    /**
     * 开始日期
     */
    private String startDateStr;
    /**
     * 截止日期
     */
    private String endDateStr;
}
