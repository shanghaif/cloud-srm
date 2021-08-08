package com.midea.cloud.srm.model.suppliercooperate.deliveryappoint.dto;

import com.midea.cloud.srm.model.suppliercooperate.deliveryappoint.entry.DeliveryAppoint;
import lombok.Data;

import java.util.List;

/**
 * <pre>
 *  送货预约请求接收对象 数据请求传输对象
 * </pre>
 *
 * @author huangbf3
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/3/28 11:23
 *  修改内容:
 * </pre>
 */
@Data
public class DeliveryAppointRequestDTO extends DeliveryAppoint {

    private static final long serialVersionUID = 1L;
    /**
     * 送货单号
     */
    private String deliveryNumber;

    /**
     * 开始进入日期
     */
    private String startEntryDate;

    /**
     * 截止进入日期
     */
    private String endEntryDate;

    /**
     * 采购订单单号
     */
    private String orderNumber;

    private List<Long> ids;
}
