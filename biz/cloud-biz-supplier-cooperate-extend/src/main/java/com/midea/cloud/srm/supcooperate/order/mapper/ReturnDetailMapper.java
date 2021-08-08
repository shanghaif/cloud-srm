package com.midea.cloud.srm.supcooperate.order.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.midea.cloud.srm.model.pm.ps.statement.dto.StatementReturnDTO;
import com.midea.cloud.srm.model.suppliercooperate.order.dto.ReturnDetailDTO;
import com.midea.cloud.srm.model.suppliercooperate.order.dto.ReturnOrderRequestDTO;
import com.midea.cloud.srm.model.suppliercooperate.order.entry.ReturnDetail;

/**
 * <pre>
 *  退货单明细表 Mapper 接口
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
public interface ReturnDetailMapper extends BaseMapper<ReturnDetail> {

	List<ReturnDetailDTO> getReturnDetailListByReturnOrderId(@Param("returnOrderId") Long returnOrderId);

	List<StatementReturnDTO> listStatementReturnDTOPage(ReturnOrderRequestDTO returnOrderRequestDTO);

    List<ReturnDetail> list(@Param("id") Long id);
}
