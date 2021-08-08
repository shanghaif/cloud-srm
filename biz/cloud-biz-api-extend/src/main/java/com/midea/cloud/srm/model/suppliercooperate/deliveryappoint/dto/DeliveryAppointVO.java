package com.midea.cloud.srm.model.suppliercooperate.deliveryappoint.dto;

import com.midea.cloud.srm.model.suppliercooperate.deliveryappoint.entry.AppointDeliveryNote;
import com.midea.cloud.srm.model.suppliercooperate.deliveryappoint.entry.DeliveryAppoint;
import com.midea.cloud.srm.model.suppliercooperate.deliveryappoint.entry.DeliveryAppointVisitor;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
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
 *  修改日期: 2021/2/5 15:37
 *  修改内容:
 * </pre>
 */
@Data
@Accessors(chain = true)
public class DeliveryAppointVO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 预约送货来访人员列表
     */
    List<DeliveryAppointVisitor> visitors;

    /**
     * 送货单信息
     */
    List<AppointDeliveryNote> appointDeliveryNotes;

    /**
     * 送货预约头
     */
    DeliveryAppoint deliveryAppoint;
}
