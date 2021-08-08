package com.midea.cloud.srm.model.suppliercooperate.deliverynote.dto;

import com.midea.cloud.srm.model.suppliercooperate.deliverynote.entry.DeliveryNote;
import com.midea.cloud.srm.model.suppliercooperate.deliverynote.entry.DeliveryNoteDetail;
import lombok.Data;

import java.util.List;

/**
 * <pre>
 *  功能名称
 * </pre>
 *
 * @author xiexh12@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/8/13 11:10
 *  修改内容:
 * </pre>
 */

@Data
public class DeliveryNoteConfirmDTO {
    /**
     * 送货单
     */
    private DeliveryNote deliveryNote;

    /**
     * 送货单明细列表
     */
    private List<DeliveryNoteDetailConfirmDTO> detailList;
}
