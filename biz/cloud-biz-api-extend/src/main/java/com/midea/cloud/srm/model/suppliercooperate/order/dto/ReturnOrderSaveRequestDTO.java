package com.midea.cloud.srm.model.suppliercooperate.order.dto;

import java.util.List;

import com.midea.cloud.srm.model.suppliercooperate.order.entry.ReturnDetail;
import com.midea.cloud.srm.model.suppliercooperate.order.entry.ReturnOrder;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * <pre>
 *  送货单新增 数据请求传输对象
 * </pre>
 *
 * @author huangbf3
 * @version 1.00.00
 *
 *          <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/4/23 13:37
 *  修改内容:
 *          </pre>
 */
@Data
@Accessors(chain = true)
public class ReturnOrderSaveRequestDTO {

	/**
	 * 退货单
	 */
	private ReturnOrder returnOrder;

	/**
	 * 退货单明细列表
	 */
//	private List<ReturnDetailDTO> returnDetailDTOs;

	/**
	 * 退货单明细表
	 */
	private List<ReturnDetail> returnDetailList;

}
