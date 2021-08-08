package com.midea.cloud.srm.model.logistics.po.order.vo;

import com.midea.cloud.srm.model.logistics.po.order.entity.*;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * <pre>
 *  物流采购订单视图
 * </pre>
 *
 * @author chenwj92@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人: chenwj92
 *  修改日期: 2021/1/4 17:46
 *  修改内容:
 * </pre>
 */
@Data
@Accessors(chain = true)
public class LogisticsOrderVO {
    private static final long serialVersionUID = 1L;

    /**
     * 采购订单头
     */
    private OrderHead orderHead;

    /**
     * 订单行
     */
    private List<OrderLine> orderLineList;

    /**
     * 订单合同行
     */
    private List<OrderLineContract> orderLineContractList;

    /**
     * 订单船期行
     */
    private List<OrderLineShip> orderLineShipList;

    /**
     * 订单附件行
     */
    private List<OrderFile> orderFileList;

    /**
     * 订单拒绝记录
     */
    private List<OrderRejectRecord> orderRejectRecordList;

}
