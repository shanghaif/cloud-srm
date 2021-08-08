package com.midea.cloud.srm.model.suppliercooperate.order.dto;

import com.midea.cloud.srm.model.suppliercooperate.order.entry.*;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

/**
 * <pre>
 *  采购订单新增 数据请求传输对象
 * </pre>
 *
 * @author huangbf3
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/4/23 11:54
 *  修改内容:
 * </pre>
 */
@Data
@Accessors(chain = true)
public class OrderSaveRequestDTO implements Serializable{

	private static final long serialVersionUID = -2282059012608919599L;

	/**
     * 采购订单
     */
    private Order order;

    /**
     * 采购订单明细列表
     */
    private List<OrderDetail> detailList;

    /**
     * 订单附件表
     */
    private List<OrderAttach> attachList;

    /**
     * 订单付款条款
     */
    private List<OrderPaymentProvision> paymentProvisionList;

    /**
     * 入库退货明细
     */
    private List<WarehousingReturnDetail> warehousingReturnDetails;

    private String processType;//提交审批Y，废弃N
}
