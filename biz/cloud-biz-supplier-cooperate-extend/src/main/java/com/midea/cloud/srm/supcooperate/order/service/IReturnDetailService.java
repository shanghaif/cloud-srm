package com.midea.cloud.srm.supcooperate.order.service;

import java.util.Collection;
import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.midea.cloud.srm.model.pm.ps.statement.dto.StatementReturnDTO;
import com.midea.cloud.srm.model.suppliercooperate.order.dto.ReturnDetailDTO;
import com.midea.cloud.srm.model.suppliercooperate.order.dto.ReturnOrderRequestDTO;
import com.midea.cloud.srm.model.suppliercooperate.order.entry.ReturnDetail;
import com.midea.cloud.srm.model.suppliercooperate.reconciliation.entry.WarehouseReceiptDetail;

/**
 * <pre>
 *  退货单明细表 服务类
 * </pre>
 *
 * @author huangbf3
 * @version 1.00.00
 *
 *          <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/4/2 11:23
 *  修改内容:
 *          </pre>
 */
public interface IReturnDetailService extends IService<ReturnDetail> {

	List<ReturnDetailDTO> getReturnDetailListByReturnOrderId(Long returnOrderId);

	PageInfo<StatementReturnDTO> listStatementReturnDTOPage(ReturnOrderRequestDTO returnOrderRequestDTO);

	/**
	 * 查询退货单明细
	 * @param id
	 * @return
	 */
	List<ReturnDetail> list(Long id);

	/**
	 * 送货单明细ID集合查询
	 * @param deliveryNoteDetailIdList
	 * @return
	 */
	List<ReturnDetail> listByDeliveryNoteDetailId(Collection<Long> deliveryNoteDetailIdList);
}
