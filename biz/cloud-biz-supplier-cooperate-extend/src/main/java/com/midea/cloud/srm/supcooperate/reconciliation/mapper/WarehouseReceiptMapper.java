package com.midea.cloud.srm.supcooperate.reconciliation.mapper;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.midea.cloud.srm.model.pm.po.dto.WarehouseReceiptPageDTO;
import com.midea.cloud.srm.model.pm.po.dto.WarehouseReceiptPageQueryDTO;
import com.midea.cloud.srm.model.pm.ps.statement.dto.StatementReceiptDTO;
import com.midea.cloud.srm.model.suppliercooperate.order.vo.WarehouseReceiptVO;
import com.midea.cloud.srm.model.suppliercooperate.reconciliation.entry.WarehouseReceipt;

/**
 * <pre>
 *  入库单表 Mapper 接口
 * </pre>
 *
 * @author huangbf3
 * @version 1.00.00
 *
 *          <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/3/19 19:15
 *  修改内容:
 *          </pre>
 */
public interface WarehouseReceiptMapper extends BaseMapper<WarehouseReceipt> {

	List<WarehouseReceiptVO> listPage(WarehouseReceiptPageQueryDTO warehouseReceiptPageQueryDTO);

	List<StatementReceiptDTO> listStatementReceiptDTOPage(WarehouseReceiptPageQueryDTO warehouseReceiptPageQueryDTO);

}
