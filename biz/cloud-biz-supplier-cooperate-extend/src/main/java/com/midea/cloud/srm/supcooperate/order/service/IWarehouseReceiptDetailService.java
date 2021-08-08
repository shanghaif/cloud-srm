package com.midea.cloud.srm.supcooperate.order.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.midea.cloud.common.enums.neworder.WarehouseReceiptStatus;
import com.midea.cloud.srm.model.suppliercooperate.order.vo.WarehouseReceiptDetailVO;
import com.midea.cloud.srm.model.suppliercooperate.reconciliation.entry.WarehouseReceipt;
import com.midea.cloud.srm.model.suppliercooperate.reconciliation.entry.WarehouseReceiptDetail;

import java.util.Collection;
import java.util.List;
import java.io.IOException;

/**
* <pre>
 *  入库单行表 服务类
 * </pre>
*
* @author chenwj92@meicloud.com
* @version 1.00.00
*
* <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: Feb 19, 2021 1:40:47 PM
 *  修改内容:
 * </pre>
*/
public interface IWarehouseReceiptDetailService extends IService<WarehouseReceiptDetail>{
    /*
    批量更新或者批量新增
    */
     void batchSaveOrUpdate(List<WarehouseReceiptDetail> warehouseReceiptDetailList) throws IOException;

    /*
     批量删除
     */
    void batchDeleted(List<Long> ids) throws IOException;
    /*
   分页查询
    */
    PageInfo<WarehouseReceiptDetail> listPage(WarehouseReceiptDetail warehouseReceiptDetail);

    /**
     * 查询入库单明细
     * @param id
     * @return
     */
    List<WarehouseReceiptDetail> list(Long id);

    List<WarehouseReceiptDetailVO> list(List<Long> ids);

    /**
     * 入库单ID集合查询
     * @param warehouseReceiptIdList 入库单ID集合
     * @return 入库单明细
     */
    List<WarehouseReceiptDetail> listByWarehouseReceiptId(List<Long> warehouseReceiptIdList);

    /**
     * 删除入库单下的所有明细
     * @param warehouseReceiptId 入库单明细
     * @return 删除条数
     */
    int deleteByWarehouseReceiptId(Long warehouseReceiptId);

    /**
     * 删除入库单下的所有明细
     * @param warehouseReceiptIdList 入库单明细
     * @return 删除条数
     */
    int deleteByWarehouseReceiptId(List<Long> warehouseReceiptIdList);

    /**
     * 送货单明细ID集合查询
     * @param deliveryNoteDetailIdList
     * @return
     */
    List<WarehouseReceiptDetail> listByDeliveryNoteDetailId(Collection<Long> deliveryNoteDetailIdList);

}
