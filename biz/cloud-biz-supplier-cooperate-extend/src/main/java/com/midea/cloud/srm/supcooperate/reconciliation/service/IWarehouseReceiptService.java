package com.midea.cloud.srm.supcooperate.reconciliation.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.midea.cloud.common.enums.neworder.WarehouseReceiptStatus;
import com.midea.cloud.common.service.BaseService;
import com.midea.cloud.srm.feign.supcooperate.SupcooperateClient.SaveOrUpdateWarehouseReceiptAndUpdateOrderDetail;
import com.midea.cloud.srm.model.pm.po.dto.WarehouseReceiptPageQueryDTO;
import com.midea.cloud.srm.model.pm.ps.statement.dto.StatementReceiptDTO;
import com.midea.cloud.srm.model.suppliercooperate.order.dto.WarehouseReceiptDTO;
import com.midea.cloud.srm.model.suppliercooperate.order.vo.WarehouseReceiptVO;
import com.midea.cloud.srm.model.suppliercooperate.reconciliation.entry.WarehouseReceipt;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * <pre>
 *  入库单表 服务类
 * </pre>
 *
 * @author huangbf3
 * @version 1.00.00
 *
 *          <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/4/2 11:24
 *  修改内容:
 *          </pre>
 */
public interface IWarehouseReceiptService extends BaseService<WarehouseReceipt> {

	PageInfo<WarehouseReceiptVO> listPage(WarehouseReceiptPageQueryDTO warehouseReceiptPageQueryDTO);

	void saveOrUpdateWarehouseReceiptAndUpdateOrderDetail(@RequestBody SaveOrUpdateWarehouseReceiptAndUpdateOrderDetail param);

	PageInfo<StatementReceiptDTO> listStatementReceiptDTOPage(WarehouseReceiptPageQueryDTO warehouseReceiptPageQueryDTO);

	/**
	 * 暂存
	 * @param warehouseReceiptDTO
	 * @return
	 */
    Long temporarySave(WarehouseReceiptDTO warehouseReceiptDTO);

	/**
	 * 提交
	 * @param warehouseReceiptDTO
	 * @return
	 */
    Long submit(WarehouseReceiptDTO warehouseReceiptDTO);

	/**
	 * 批量删除入库单
	 * @param ids
	 */
    void batchDelete(List<Long> ids);

	/**
	 * 查看入库单详情
	 * @param id
	 * @return
	 */
	WarehouseReceiptDTO detailWarehouseReceipt(Long id);

	/**
	 * 批量确认
	 * @param ids
	 */
	void batchConfirm(List<Long> ids);

	/**
	 * 批量冲销
	 * @param ids
	 */
    void batchWriteOff(List<Long> ids);

	/**
	 * 批量更新入库状态
	 * @param ids
	 * @param warehouseReceiptStatus
	 * @return
	 */
	int updateWarehouseReceiptStatus(List<Long> ids, WarehouseReceiptStatus warehouseReceiptStatus);

	/**
	 * 获取未冲销的数据
	 * @param ids 入库单id
	 * @return
	 */
	List<WarehouseReceipt> listUnWriteOff(List<Long> ids);
}
