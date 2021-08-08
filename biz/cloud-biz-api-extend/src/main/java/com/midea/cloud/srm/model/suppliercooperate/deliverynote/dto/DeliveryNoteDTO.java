package com.midea.cloud.srm.model.suppliercooperate.deliverynote.dto;

import com.midea.cloud.srm.model.suppliercooperate.deliverynote.entry.DeliveryNote;
import lombok.Data;

import java.util.Date;

/**
 * <pre>
 *  送货单 数据返回传输对象
 * </pre>
 *
 * @author huangbf3
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/3/26 15:03
 *  修改内容:
 * </pre>
 */
@Data
public class DeliveryNoteDTO extends DeliveryNote {

    private static final long serialVersionUID = 1L;

    /**
     * 旧送货单号
     */
    private String oldDeliveryNumber;

}
