package com.midea.cloud.srm.supcooperate.order.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.midea.cloud.srm.model.suppliercooperate.order.dto.ReturnOrderPageDTO;
import com.midea.cloud.srm.model.suppliercooperate.order.dto.ReturnOrderRequestDTO;
import com.midea.cloud.srm.model.suppliercooperate.order.entry.ReturnOrder;

/**
 * <pre>
 *  退货单表 Mapper 接口
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
public interface ReturnOrderMapper extends BaseMapper<ReturnOrder> {
	/**
	 * 多少天后自动更新确认未确认的退货单
	 *
	 * @param exprDay
	 */
	void updateSureReturnStatus(@Param("exprDay") Integer exprDay);

	List<ReturnOrder> listPage(ReturnOrderRequestDTO param);
}
