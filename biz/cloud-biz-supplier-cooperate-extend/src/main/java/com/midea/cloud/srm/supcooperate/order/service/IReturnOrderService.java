package com.midea.cloud.srm.supcooperate.order.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.midea.cloud.srm.feign.supcooperate.SupcooperateClient.ConfirmReturnOrders;
import com.midea.cloud.srm.feign.supcooperate.SupcooperateClient.SaveOrUpdateReturnOrder;
import com.midea.cloud.srm.model.suppliercooperate.order.dto.ReturnOrderRequestDTO;
import com.midea.cloud.srm.model.suppliercooperate.order.dto.ReturnOrderSaveRequestDTO;
import com.midea.cloud.srm.model.suppliercooperate.order.entry.ReturnOrder;
import com.midea.cloud.srm.model.suppliercooperate.order.vo.ReturnOrderVO;

import java.util.List;

/**
 * <pre>
 *  退货单表 服务类
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
public interface IReturnOrderService extends IService<ReturnOrder> {

	/**
	 * 分页列表
	 *
	 * @param requestDTO
	 * @return
	 */
	PageInfo<ReturnOrder> listPage(ReturnOrderRequestDTO requestDTO);

	/**
	 * 创建退货单
	 *
	 * @param returnOrder
	 * @param detailList
	 */
	void saveOrUpdateReturnOrder(SaveOrUpdateReturnOrder saveOrUpdateReturnOrder);

	/**
	 *
	 * @param updateReturnOrders
	 */
	void confirmReturnOrders(ConfirmReturnOrders updateReturnOrders);

	/**
	 * 暂存
	 * @param returnOrderSaveRequestDTO
	 * @return
	 */
	Long temporarySave(ReturnOrderSaveRequestDTO returnOrderSaveRequestDTO);

	/**
	 * 批量删除退货单
	 * @param ids
	 */
	void batchDelete(List<Long> ids);

	/**
	 * 提交退货单
	 * @param returnOrderSaveRequestDTO
	 * @return
	 */
	Long submit(ReturnOrderSaveRequestDTO returnOrderSaveRequestDTO);

	/**
	 * 批量确认
	 * @param ids
	 */
    void batchConfirm(List<Long> ids);

	/**
	 * 批量拒绝
	 * @param returnOrder
	 */
	void batchReject(ReturnOrder returnOrder);

	/**
	 * 详情
	 * @param returnOrderId
	 * @return
	 */
    ReturnOrderVO detail(Long returnOrderId);
}
