package com.midea.cloud.srm.model.suppliercooperate.deliverynote.dto;

import com.midea.cloud.srm.model.file.upload.entity.Fileupload;
import com.midea.cloud.srm.model.suppliercooperate.deliverynote.entry.DeliveryNote;
import com.midea.cloud.srm.model.suppliercooperate.deliverynote.entry.DeliveryNoteDetail;
import com.midea.cloud.srm.model.suppliercooperate.deliverynote.entry.DeliveryNoteWms;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * <pre>
 *  送货单新增 数据请求传输对象
 * </pre>
 *
 * @author huangbf3
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/4/23 13:39
 *  修改内容:
 * </pre>
 */
@Data
public class DeliveryNoteSaveRequestDTO {

    /**
     * 送货单
     */
    private DeliveryNote deliveryNote;

    /**
     * 送货单明细列表
     */
    //private List<DeliveryNoteDetail> detailList;
    /**
     * 送货单明细列表
     */
    private List<DeliveryNoteDetailDTO> detailList;
    /**
     * 附件列表
     */
    private List<Fileupload> procurementFile;
    /**
     * wms清单列表
     */
    private  List<DeliveryNoteWms> deliveryNoteWms;
}
