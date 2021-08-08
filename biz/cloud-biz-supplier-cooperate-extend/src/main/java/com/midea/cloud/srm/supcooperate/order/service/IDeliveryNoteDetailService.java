package com.midea.cloud.srm.supcooperate.order.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.midea.cloud.srm.model.suppliercooperate.deliverynote.dto.DeliveryNoteDetailDTO;
import com.midea.cloud.srm.model.suppliercooperate.deliverynote.dto.DeliveryNoteDetailReceiveDTO;
import com.midea.cloud.srm.model.suppliercooperate.deliverynote.dto.DeliveryNoteRequestDTO;
import com.midea.cloud.srm.model.suppliercooperate.deliverynote.dto.ReceiveRequestDTO;
import com.midea.cloud.srm.model.suppliercooperate.deliverynote.entry.DeliveryNoteDetail;

import java.math.BigDecimal;
import java.util.List;

/**
 * <pre>
 *  送货单明细表 服务类
 * </pre>
 *
 * @author huangbf3
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/3/19 19:12
 *  修改内容:
 * </pre>
 */
public interface IDeliveryNoteDetailService extends IService<DeliveryNoteDetail> {

    List<DeliveryNoteDetailDTO> listPage(DeliveryNoteRequestDTO deliveryNoteRequestDTO);

    List<DeliveryNoteDetailReceiveDTO> receiveListPage(ReceiveRequestDTO receiveRequestDTO);

    void updateBatchByExcel(List<DeliveryNoteDetail> list);

    void confirmDeliveryNoteDetailStatus(Long deliveryNoteDetailId, String batchNum, BigDecimal deliveryQuantity);

    List<DeliveryNoteDetailDTO> listDeliveryNoteDetail(DeliveryNoteRequestDTO deliveryNoteRequestDTO);

    List<DeliveryNoteDetail> DeliveryNoteDetailList(DeliveryNoteDetail deliveryNoteDetail);

    /**
     * 创建入库单-查询送货单明细
     * @param deliveryNoteRequestDTO
     * @return
     */
    PageInfo<DeliveryNoteDetailDTO> listInWarehouseReceipt(DeliveryNoteRequestDTO deliveryNoteRequestDTO);

    /**
     * 创建退货单-查询送货单明细
     * @param deliveryNoteRequestDTO
     * @return
     */
    PageInfo<DeliveryNoteDetailDTO> listInReturnOrder(DeliveryNoteRequestDTO deliveryNoteRequestDTO);
}
