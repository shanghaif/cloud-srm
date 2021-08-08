package com.midea.cloud.srm.model.suppliercooperate.deliveryappoint.dto;

import com.midea.cloud.srm.model.suppliercooperate.deliveryappoint.entry.DeliveryAppoint;
import lombok.Data;

/**
 * <pre>
 *  送货预约列表 数据返回传输对象
 * </pre>
 *
 * @author huangbf3
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/3/28 19:10
 *  修改内容:
 * </pre>
 */
@Data
public class DeliveryAppointListDTO extends DeliveryAppoint {

    private static final long serialVersionUID = 1L;

    /**
     * 送货单号
     */
    private String deliveryNumber;
}
