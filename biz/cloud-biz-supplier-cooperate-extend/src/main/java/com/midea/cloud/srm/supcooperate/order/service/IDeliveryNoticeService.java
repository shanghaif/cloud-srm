package com.midea.cloud.srm.supcooperate.order.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.midea.cloud.srm.model.suppliercooperate.deliverynote.dto.DeliveryNoteDetailDTO;
import com.midea.cloud.srm.model.suppliercooperate.order.dto.DeliveryNoticeDTO;
import com.midea.cloud.srm.model.suppliercooperate.order.dto.DeliveryNoticeRequestDTO;
import com.midea.cloud.srm.model.suppliercooperate.order.dto.OrderDetailDTO;
import com.midea.cloud.srm.model.suppliercooperate.order.dto.OrderRequestDTO;
import com.midea.cloud.srm.model.suppliercooperate.order.entry.DeliveryNotice;

import java.math.BigDecimal;
import java.util.List;

/**
 * <pre>
 *  送货通知单表 服务类
 * </pre>
 *
 * @author huangbf3
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/5/19 10:41
 *  修改内容:
 * </pre>
 */
public interface IDeliveryNoticeService extends IService<DeliveryNotice> {
    List<DeliveryNoticeDTO> findList(DeliveryNoticeRequestDTO requestDTO);

    void releasedBatch(List<DeliveryNotice> deliveryNotices);

    List<DeliveryNoticeDTO> listCreateDeliveryNoteDetail(List<Long> deliveryNoticeIds);

    BigDecimal getWarehouseReceiptQuantityByDeliveryNoticeId(Long deliveryNoticeId);

    void batchAdd(List<DeliveryNotice> deliveryNoticeList);

    void batchUpdate(List<DeliveryNotice> deliveryNoticeList);

    void batchDeleteDeliveryNotice(List<Long> ids);

    void supplierConfirm(List<Long> ids);

    void supplierReject(DeliveryNoticeRequestDTO deliveryNoticeRequestDTO);

    /**
     * 创建送货单-查询送货通知
     * @param orderRequestDTO
     * @return
     */
    PageInfo<OrderDetailDTO> listInDeliveryNote(OrderRequestDTO orderRequestDTO);
}
