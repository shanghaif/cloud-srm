package com.midea.cloud.srm.supcooperate.order.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.midea.cloud.srm.model.base.work.dto.WorkCount;
import com.midea.cloud.srm.model.file.upload.entity.Fileupload;
import com.midea.cloud.srm.model.suppliercooperate.deliverynote.dto.DeliveryNoteDTO;
import com.midea.cloud.srm.model.suppliercooperate.deliverynote.dto.DeliveryNoteDetailDTO;
import com.midea.cloud.srm.model.suppliercooperate.deliverynote.dto.DeliveryNoteRequestDTO;
import com.midea.cloud.srm.model.suppliercooperate.deliverynote.dto.DeliveryNoteSaveRequestDTO;
import com.midea.cloud.srm.model.suppliercooperate.deliverynote.entry.DeliveryNote;
import com.midea.cloud.srm.model.suppliercooperate.deliverynote.entry.DeliveryNoteWms;

import java.util.List;

/**
 * <pre>
 *  送货单表 服务类
 * </pre>
 *
 * @author huangbf3
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/3/23 20:14
 *  修改内容:
 * </pre>
 */
public interface IDeliveryNoteService extends IService<DeliveryNote> {
    DeliveryNoteSaveRequestDTO getDeliveryDTO(Long orderId);

    void saveOrUpdate(DeliveryNote deliveryNote, List<DeliveryNoteDetailDTO> detailList, List<Fileupload> procurementFile, List<DeliveryNoteWms> deliveryNoteWms);

    List<DeliveryNoteDTO> listPage(DeliveryNoteRequestDTO deliveryNoteRequestDTO);

    WorkCount countCreate(DeliveryNoteRequestDTO deliveryNoteRequestDTO);

    void submitBatch(List<Long> ids);

    void confirmDeliveryNoteStatus(Long deliveryNoteId);

    void cancelDeliveryNoteStatus(Long deliveryNoteId);

    void getAffirmDelivery(Long deliveryNoteId);

    void getCancelDelivery(Long deliveryNoteId);

    /**
     * 创建送货预约-选择送货单
     * @param deliveryNoteRequestDTO
     * @return
     */
    PageInfo<DeliveryNoteDTO> listInDeliveryAppoint(DeliveryNoteRequestDTO deliveryNoteRequestDTO);

    /**
     * 供应商删除送货单
     * @param deliveryNoteId
     */
    void delete(Long deliveryNoteId);
}
