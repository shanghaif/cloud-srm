package com.midea.cloud.srm.model.suppliercooperate.deliveryappoint.dto;

import com.midea.cloud.srm.model.suppliercooperate.deliveryappoint.entry.AppointDeliveryNote;
import com.midea.cloud.srm.model.suppliercooperate.deliveryappoint.entry.DeliveryAppoint;
import com.midea.cloud.srm.model.suppliercooperate.deliveryappoint.entry.DeliveryAppointVisitor;
import com.midea.cloud.srm.model.suppliercooperate.deliverynote.entry.DeliveryNote;
import lombok.Data;

import java.util.List;

/**
 * <pre>
 *  送货预约对象明细，包含送货单明细、来访联系人 数据返回传输对象
 * </pre>
 *
 * @author huangbf3
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/3/30 16:42
 *  修改内容:
 * </pre>
 */
@Data
public class DeliveryAppointDetailDTO extends DeliveryAppoint {

    private static final long serialVersionUID = 1L;
    /**
     * 预约送货来访人员列表
     */
    List<DeliveryAppointVisitor> visitors;

    /**
     * 预约送货单明细列表
     */
    List<DeliveryNote> deliveryNotes;

    /**
     * 送货单信息
     */
    List<AppointDeliveryNote> appointDeliveryNotes;
}
