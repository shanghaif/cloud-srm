package com.midea.cloud.srm.model.suppliercooperate.order.dto;

import lombok.Data;

import java.util.List;

/**
 * <pre>
 *  订单明细删除 数据请求传输对象
 * </pre>
 *
 * @author chenwj92
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/8/03 13:46
 *  修改内容:
 * </pre>
 */
@Data
public class OrderDetailDeleteRequestDTO {

    /**
     * 采购单ID
     */
    private Long orderId;

    /**
     * 采购单明细ID列表
     */
    List<Long> detailIds;

    /**
     * 订单附件ID
     */
    List<Long> attachIds;
}
