package com.midea.cloud.srm.supcooperate.order.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.midea.cloud.srm.model.suppliercooperate.order.vo.WarehouseReceiptDetailVO;
import com.midea.cloud.srm.model.suppliercooperate.reconciliation.entry.WarehouseReceiptDetail;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 入库单行表 Mapper 接口
 * </p>
 *
 * @author chenwj92@meicloud.com
 * @since Feb 19, 2021 1:40:47 PM
 */
public interface WarehouseReceiptDetailMapper extends BaseMapper<WarehouseReceiptDetail> {

    List<WarehouseReceiptDetail> list(@Param("warehouseReceiptId") Long warehouseReceiptId);

    List<WarehouseReceiptDetailVO> listInWarehouseReceipt(@Param("ids") List<Long> ids);
}
