package com.midea.cloud.srm.po.order.service;

import com.midea.cloud.srm.model.suppliercooperate.order.entry.OrderDetail;

import java.util.List;

/**
 * <pre>
 * 采购订单相关校验逻辑，orderService太长了，个性化校验迁移出来
 * </pre>
 *
 * @author chenjj120@meicloud.com
 * @version 1.00.00
 *
 *          <pre>
 *  修改记录
 *  修改后版本:
 *  修改人：
 *  修改日期: 2020年12月8日14:09:15
 *  修改内容:
 *          </pre>
 */

public interface IOrderCheckService {
//    /**
//     * 校验订单行信息是否有足够的数量
//     * @param orderDetailList 订单行信息
//     */
//    void checkOrderNumEnough(List<OrderDetail> orderDetailList);

    /**
     * 校验订单行关联的合同信息
     * @param orderDetailList
     */
    void verifyTheOrderRelatedContractInfo(List<OrderDetail> orderDetailList);

}
