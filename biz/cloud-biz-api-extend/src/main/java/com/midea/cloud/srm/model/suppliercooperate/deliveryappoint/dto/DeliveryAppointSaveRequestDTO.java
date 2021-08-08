package com.midea.cloud.srm.model.suppliercooperate.deliveryappoint.dto;

import com.midea.cloud.srm.model.suppliercooperate.deliveryappoint.entry.AppointDeliveryNote;
import com.midea.cloud.srm.model.suppliercooperate.deliveryappoint.entry.DeliveryAppoint;
import com.midea.cloud.srm.model.suppliercooperate.deliveryappoint.entry.DeliveryAppointVisitor;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;

/**
 * <pre>
 *  送货预约单保存 数据请求传输对象
 * </pre>
 *
 * @author huangbf3
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/4/23 13:51
 *  修改内容:
 * </pre>
 */
@Data
@Accessors(chain = true)
public class DeliveryAppointSaveRequestDTO {
    /**
     * 请求类型
     */
    private String opt;

    /**
     * 送货预约单
     */
    private DeliveryAppoint deliveryAppoint;

    /**
     * 送货预约来访人员列表
     */
    private List<DeliveryAppointVisitor> deliveryAppointVisitors;

    /**
     * 预约送货单明细列表
     */
    private List<AppointDeliveryNote> appointDeliveryNotes;
}
